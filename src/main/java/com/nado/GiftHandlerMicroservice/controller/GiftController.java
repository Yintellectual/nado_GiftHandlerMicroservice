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
public class GiftController {

	@Autowired
	GiftDictionary giftDictionary;
	@Autowired
	private TypedStringRepository unknownGiftRepository;
	
	@RequestMapping("/api/gift/save")
	@ResponseBody
	public Gift countMessages(String code, GivingRelatedMessageTypes type, String name, Integer price){
		Gift gift=new Gift(code, type, name, price);
		Gift oldGift = giftDictionary.get(code, type);
		giftDictionary.save(gift);
		return oldGift;
	}
}
