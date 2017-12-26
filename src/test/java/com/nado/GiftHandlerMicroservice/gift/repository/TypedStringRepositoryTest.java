package com.nado.GiftHandlerMicroservice.gift.repository;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.service.GiftDictionary;
import com.nado.douyuConnectorMicroservice.douyuClient.DouyuApiClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypedStringRepositoryTest {

	@Autowired
	private TypedStringRepository repository;

	@Before
	public void before() {
		repository.clear();
		for(int i=1;i<=3;i++){
			String type = "type"+i;
			for(int j=1;j<=4;j++){
				String message = i+"-"+j;
				repository.save(message, type);
			}
			if(i==1){
				repository.countNew();
			}
		}
	}

	@After
	public void after() {
		repository.clear();
	}
	@Test
	public void alwaysPassTest(){
		assertTrue(true);
	}
	@Test
	public void countNewReturn8(){
		assertTrue(8==repository.countNew());
	}
	@Test
	public void summaryIsSetOf3(){
		assertTrue(3==repository.summary().size());
	}
	@Test
	public void summaryContainsTypeAndCount(){
		assertTrue(repository.summary().contains("type1-4"));
	}
	@Test
	public void detailsByTypeNoArgMeansAll(){
		assertTrue(3==repository.detailsByType().size());
	}
	@Test
	public void type1DetailsContains4Messages(){
		assertTrue(4==repository.detailsByType("type1").get("type1").size());
	}
	@Test
	public void type1DetailsContainsCorrectMessage(){
		for(int i=1;i<=4;i++){
			String message = "1-"+i;
			assertTrue(repository.detailsByType("type1")!=null);
			assertTrue(repository.detailsByType("type1").get("type1")!=null);
			assertTrue(repository.detailsByType("type1").get("type1").contains(message));
		}
	}
}
