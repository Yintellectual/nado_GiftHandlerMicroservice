package com.nado.douyuConnectorMicroservice.douyuClient.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nado.douyuConnectorMicroservice.douyuClient.DouyuApiClient;
import com.nado.douyuConnectorMicroservice.douyuClient.entity.DouyuApiRoom;

@Service
public class DouyuApiClientNaiveImpl implements DouyuApiClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public DouyuApiRoom queryRoom(String room) {
		
		DouyuApiRoom douyuApiRoom = restTemplate.getForObject("http://open.douyucdn.cn/api/RoomApi/room/"+room, DouyuApiRoom.class);
		
		return douyuApiRoom;
	}

	@Override
	public DouyuApiRoom queryRoom(int room) {
		return queryRoom(""+room);
	}
}
