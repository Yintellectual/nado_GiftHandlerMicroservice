package com.nado.GiftHandlerMicroservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nado.GiftHandlerMicroservice.entity.ExtractedGivingInfo;
import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.repository.TypedStringRepository;
import com.nado.GiftHandlerMicroservice.gift.service.GiftDictionary;
import com.nado.douyuConnectorMicroservice.util.CommonUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.nado.douyuConnectorMicroservice.douyuClient", "com.nado.GiftHandlerMicroservice" })
public class GiftHandlerMicroserviceApplication {

	private static final Logger logger = LoggerFactory.getLogger(GiftHandlerMicroserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GiftHandlerMicroserviceApplication.class, args);
	}

	@Autowired
	private GiftDictionary dictionary;
	@Autowired
	private TypedStringRepository unknownGiftRepository;

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	// Of course , you can define the Executor too
	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	public RestTemplate getRestClient() {
		RestTemplate restClient = new RestTemplate(
				new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		// Add one interceptor like in your example, except using anonymous
		// class.
		restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {
			logger.debug("Intercepting...");
			return execution.execute(request, body);
		}));

		return restClient;
	}

	@Bean
	public Channel channel() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.basicQos(1);
		for (GivingRelatedMessageTypes type : GivingRelatedMessageTypes.values()) {
			String queue = channel.queueDeclare().getQueue();
			channel.queueBind(queue, "douyu-cooked-messages", type.name());
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) {
					try {
						String message = "";
						ExtractedGivingInfo extractedUserInfo = null;
						try {
							message = new String(body, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							logger.error(e.getMessage());
						}
						if(message ==null || message.isEmpty()){
							logger.error(message);
						}else{
							if(type.equals(GivingRelatedMessageTypes.anbc)||type.equals(GivingRelatedMessageTypes.renewbc)){
								if(!"2020877".equals(CommonUtil.matchStringValue(message, "drid"))){
									
								}else{
									extractedUserInfo = type.extractGivingInfo(message, dictionary,unknownGiftRepository);
								}
							}else{
								extractedUserInfo = type.extractGivingInfo(message, dictionary,unknownGiftRepository);
							}
						}
						LocalDateTime now = LocalDateTime.now();
						
						if(extractedUserInfo!=null){
							// use the repositories here
						}
						
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						try {
							channel.basicAck(envelope.getDeliveryTag(), false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							logger.error(e.getMessage());
						}
					}
				}
			};
			channel.basicConsume(queue, false, consumer);
		}
		return channel;
	}

	@Component
	public class MyRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {

		}
	}
}
