package com.nado.GiftHandlerMicroservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.repository.GiftRepository;
import com.nado.GiftHandlerMicroservice.gift.repository.TypedStringRepository;
import com.nado.GiftHandlerMicroservice.gift.service.GiftDictionary;

@Controller
public class DebugController {

	@Autowired
	GiftDictionary giftDictionary;
	@Autowired
	private TypedStringRepository unknownGiftRepository;
	
	@RequestMapping("/api/debug/countMessages")
	@ResponseBody
	public Map<String, Long> countMessages(){
		Map<String, Long> result = new HashMap<>();
		for(GivingRelatedMessageTypes type: GivingRelatedMessageTypes.values()){
			result.put(type.name(), type.getCount());
		}
		return result;
	}
	@RequestMapping("/api/debug/unknownGift/count")
	@ResponseBody
	public int unknownGiftCount(){
		return unknownGiftRepository.countNew();
	}
	@RequestMapping("/api/debug/unknownGift/summary")
	@ResponseBody
	public Set<String>unknownGiftSummary(){
		return unknownGiftRepository.summary();
	}
	@RequestMapping("/api/debug/unknownGift/flush")
	@ResponseBody
	public void unknownGiftFlush(){
		unknownGiftRepository.flush();
	}
	@RequestMapping("/api/debug/unknownGift/details")
	@ResponseBody
	public Map<String, List<String>>unknownGiftSummary(String... types){
		return unknownGiftRepository.detailsByType(types);
	}
	@RequestMapping("/api/debug/listGifts")
	@ResponseBody
	public Set<Gift> listGifts(){
		return giftDictionary.load();
	}
}
