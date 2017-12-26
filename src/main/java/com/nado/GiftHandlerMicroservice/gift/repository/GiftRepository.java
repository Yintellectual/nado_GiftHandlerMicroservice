package com.nado.GiftHandlerMicroservice.gift.repository;

import java.util.Set;

import com.nado.GiftHandlerMicroservice.gift.entity.Gift;

public interface GiftRepository {
	void save(Gift gift);

	Set<Gift> load();

	void clear();
}
