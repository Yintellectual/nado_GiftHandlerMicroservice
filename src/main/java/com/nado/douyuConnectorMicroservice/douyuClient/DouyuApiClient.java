package com.nado.douyuConnectorMicroservice.douyuClient;

import com.nado.douyuConnectorMicroservice.douyuClient.entity.DouyuApiRoom;

public interface DouyuApiClient{
	DouyuApiRoom queryRoom(String room);
	DouyuApiRoom queryRoom(int room);
}
