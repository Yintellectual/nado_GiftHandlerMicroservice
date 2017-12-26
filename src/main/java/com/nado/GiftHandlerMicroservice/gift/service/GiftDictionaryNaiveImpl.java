package com.nado.GiftHandlerMicroservice.gift.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.repository.GiftRepository;
import com.nado.douyuConnectorMicroservice.douyuClient.DouyuApiClient;
import com.nado.douyuConnectorMicroservice.douyuClient.entity.DouyuApiGift;
@Service
public class GiftDictionaryNaiveImpl implements GiftDictionary {

	private HashMap<Gift,Gift> gifts = new HashMap<>();
	@Autowired
	private GiftRepository giftRepository;
	@Autowired
	private DouyuApiClient douyuApiClient;
	@PostConstruct
	public void init(){
		Set<Gift> loaded = load();
		if(loaded!=null){
			loaded.forEach(g->{
				gifts.put(g, g);
			});	
		}
		autoUpdateGiftList();
	}
	
	@Scheduled(cron = "22 3 * * * *")
	public void autoUpdateGiftList(){
		List<Gift> gifts=fetchGiftInfoFromDouyuAPI();
		if(gifts!=null){
			fetchGiftInfoFromDouyuAPI().forEach(this::save);
		}
	} 
	
	public List<Gift> fetchGiftInfoFromDouyuAPI(){
		DouyuApiGift[] gifts = douyuApiClient.queryRoom("2020877").getData().getGift();
		List<Gift> result = new ArrayList<>();
		Arrays.stream(gifts).forEach(g->{
			result.add(new Gift(g.getId()
					, GivingRelatedMessageTypes.dgb
					, g.getName()
					, ((g.getType().equals("2"))?(int)(Float.parseFloat(g.getPc())*10):0)));
		});
		return result;
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
