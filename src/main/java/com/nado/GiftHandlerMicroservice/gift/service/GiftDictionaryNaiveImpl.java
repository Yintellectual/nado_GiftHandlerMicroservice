package com.nado.GiftHandlerMicroservice.gift.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.repository.GiftRepository;
@Service
public class GiftDictionaryNaiveImpl implements GiftDictionary {

	private HashMap<Gift,Gift> gifts = new HashMap<>();
	@Autowired
	private GiftRepository giftRepository;
	
	@PostConstruct
	public void init(){
		Set<Gift> loaded = load();
		if(loaded!=null){
			loaded.forEach(g->{
				gifts.put(g, g);
			});	
		}
	}
	
	@Override
	public void save(Gift gift) {
		// TODO Auto-generated method stub
		gifts.put(gift, gift);
		giftRepository.save(gift);
	}

	@Override
	public Set<Gift> load() {
		// TODO Auto-generated method stub
		return giftRepository.load();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		gifts = new HashMap<>();
		giftRepository.clear();
	}

	@Override
	public Gift get(String gfid, GivingRelatedMessageTypes type) {
		// TODO Auto-generated method stub
		return gifts.get(new Gift(gfid, type, "", new Integer(-1)));
	}

	@Override
	public void update(Gift gift) {
		// TODO Auto-generated method stub
		save(gift);
	}
}
