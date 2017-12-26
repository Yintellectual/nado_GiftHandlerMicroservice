package com.nado.GiftHandlerMicroservice.gift.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TypedStringRepository {
	void save(String message, String type);

	int countNew();// count new message since last time called

	Set<String> summary();

	Map<String, List<String>> detailsByType(String... types);

	void clear();
}
