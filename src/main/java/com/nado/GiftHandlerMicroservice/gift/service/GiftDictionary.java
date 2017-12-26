package com.nado.GiftHandlerMicroservice.gift.service;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.repository.GiftRepository;

public interface GiftDictionary extends GiftRepository {

	Gift get(String gfid, GivingRelatedMessageTypes type);

	void update(Gift gift);
}
