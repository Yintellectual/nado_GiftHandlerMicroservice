package com.nado.GiftHandlerMicroservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;

@Controller
public class DebugController {

	@RequestMapping("/api/debug/countMessages")
	@ResponseBody
	public Map<String, Long> countMessages(){
		Map<String, Long> result = new HashMap<>();
		for(GivingRelatedMessageTypes type: GivingRelatedMessageTypes.values()){
			result.put(type.name(), type.getCount());
		}
		return result;
	}
}
