package com.nado.GiftHandlerMicroservice.gift.service;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.repository.GiftRepository;
import com.nado.GiftHandlerMicroservice.gift.repository.GiftRepositoryRedisImpl;
import com.nado.douyuConnectorMicroservice.douyuClient.DouyuApiClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages={"com.nado.douyuConnectorMicroservice.douyuClient", "com.nado.GiftHandlerMicroservice"})
public class GiftDictionaryTest {
	
	@Autowired
	private GiftRepositoryRedisImpl giftRepository;
	@Autowired
	private DouyuApiClient douyuApiClient;
	private GiftDictionary dictionary;
	private GiftDictionary dictionary2;
	Gift dgb1203;
	Gift dgb1204;
	Gift dgb1205;
	Gift bc_buy_deserve1;
	@Before
	public void saveSampleGifts() {
		dictionary = new GiftDictionaryNaiveImpl();
		setField(dictionary, "giftRepository", giftRepository);
		setField(dictionary, "douyuApiClient", douyuApiClient);
		//addData("1203",	60l, "年度办卡", "white", "gray");
		//addData("1204",	1000l, "盛典飞机", "gray", "gold");
		//addData("1205",	5000l, "盛典火箭", "gold", "black");
		((GiftDictionaryNaiveImpl)dictionary).init();
		dgb1203 = new Gift("1203", GivingRelatedMessageTypes.dgb, "年度办卡", 60);
		dgb1204 = new Gift("1204", GivingRelatedMessageTypes.dgb, "盛典飞机", 1000);
		dgb1205 = new Gift("1205", GivingRelatedMessageTypes.dgb, "盛典火箭", 10000);
		bc_buy_deserve1 = new Gift("1", GivingRelatedMessageTypes.bc_buy_deserve, "初级酬勤", 150);
		dictionary.save(dgb1203);
		dictionary.save(dgb1204);
		dictionary.save(dgb1205);
		dictionary.save(bc_buy_deserve1);
		dgb1205 = new Gift("1205", GivingRelatedMessageTypes.dgb, "盛典火箭", 5000);
		dictionary.save(dgb1205);
		dictionary2 = new GiftDictionaryNaiveImpl();
		setField(dictionary2, "giftRepository", giftRepository);
		setField(dictionary2, "douyuApiClient", douyuApiClient);
		((GiftDictionaryNaiveImpl)dictionary2).init();
	}
	@After
	public void after(){
		dictionary.clear();
		dictionary2.clear();
	}
	@Test
	public void alwaysPassTest(){
		assertTrue(true);
	}
	@Test
	public void savedGiftsLoaded_dgb1203(){
		assertTrue(dgb1203.equals(dictionary2.get("1203", GivingRelatedMessageTypes.dgb)));
	}
	@Test
	public void savedGiftsLoaded_dgb1204(){
		assertTrue(dgb1204.equals(dictionary2.get("1204", GivingRelatedMessageTypes.dgb)));
	}
	@Test
	public void savedGiftsLoaded_dgb1205(){
		assertTrue(dgb1205.equals(dictionary2.get("1205", GivingRelatedMessageTypes.dgb)));
	}
	@Test
	public void savedGiftsLoaded_bc_buy_deserve1(){
		assertTrue(bc_buy_deserve1.equals(dictionary2.get("1", GivingRelatedMessageTypes.bc_buy_deserve)));
	}
	@Test
	public void threadSafe() throws InterruptedException{
		Thread t1 = new Thread(new QueryGift(dictionary2));
		Thread t2 = new Thread(new QueryGift(dictionary2));
		Thread t3 = new Thread(new UpdateGift(dictionary2));
		Thread t4 = new Thread(new AddGift(dictionary2));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		Gift dgb1205 = new Gift("1205", GivingRelatedMessageTypes.dgb, "盛典火箭", 6999);
		assertTrue(dgb1205.equals(dictionary2.get("1205", GivingRelatedMessageTypes.dgb)));
	}
	private class QueryGift implements Runnable{
		private GiftDictionary dictionary;
		public QueryGift(GiftDictionary dictionary) {
			// TODO Auto-generated constructor stub
			this.dictionary = dictionary;
		}
		@Override
		//query gift 
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<2000;i++){
				Gift gift = dictionary2.get("1205", GivingRelatedMessageTypes.dgb);
			}
		}
	} 
	private class UpdateGift implements Runnable{
		private GiftDictionary dictionary;
		public UpdateGift(GiftDictionary dictionary) {
			// TODO Auto-generated constructor stub
			this.dictionary = dictionary;
		}
		@Override
		//update gift 
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<2000;i++){
				Gift gift = new Gift("1205", GivingRelatedMessageTypes.dgb, "盛典火箭", 5000+i);
				dictionary.save(gift);
			}
		}
	} 	
	private class AddGift implements Runnable{
		private GiftDictionary dictionary;
		public AddGift(GiftDictionary dictionary) {
			// TODO Auto-generated constructor stub
			this.dictionary = dictionary;
		}
		@Override
		//update gift every 10ms
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<2000;i++){
				Gift gift = new Gift(1206+i+"", GivingRelatedMessageTypes.dgb, "盛典火箭", 5000+i);
				dictionary.save(gift);
			}
		}
	} 
}
