package com.nado.GiftHandlerMicroservice.gift.repository;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;

@Service
public class TypedStringRepositoryRedisImpl implements TypedStringRepository {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	private SetOperations<String, String> setOperations;
	private HashOperations<String, String, String> hashOperations;
	private ListOperations<String, String> listOperations;
	private int counter = 0;
	private int olderCounter = 0;
	private Lock lock = new ReentrantLock();

	@PostConstruct
	public void init() {
		setOperations = stringRedisTemplate.opsForSet();
		hashOperations = stringRedisTemplate.opsForHash();
		listOperations = stringRedisTemplate.opsForList();
	}

	String typeSet = "nado:unknownGift:types";

	private String messageListKey(String type) {
		return "nado:unknownGift:type:" + type;
	}

	public static String type(GivingRelatedMessageTypes type, String code) {
		return type + code;
	}

	@Override
	public void save(String message, String type) {
		// TODO Auto-generated method stub
		// count++
		lock.lock();
		counter++;
		lock.unlock();
		// add to typeSet
		setOperations.add(typeSet, type);
		// add to list
		listOperations.leftPush(messageListKey(type), message);
	}

	@Override
	public int countNew() {
		lock.lock();
		// TODO Auto-generated method stub
		int result = counter - olderCounter;
		olderCounter = counter;
		lock.unlock();
		return result;
	}

	@Override
	public Set<String> summary() {
		// TODO Auto-generated method stub
		return detailsByType(false).keySet();
	}

	@Override
	public Map<String, List<String>> detailsByType(String... types) {
		return detailsByType(true, types);
	}

	public Map<String, List<String>> detailsByType(boolean includeDetails, String... types) {
		LinkedHashMap<String, List<String>> result = new LinkedHashMap<>();
		if (types == null || types.length == 0) {
			types = setOperations.members(typeSet).toArray(new String[0]);
		}
		Arrays.stream(types).forEach(type -> {
			List<String> messages = null;
			if (includeDetails) {
				messages = listOperations.range(messageListKey(type), 0, -1);
			}else{
				Long size = listOperations.size(messageListKey(type));
				type = type+"-"+size;
			}
			result.put(type, messages);
		});
		return result;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		setOperations.members(typeSet).forEach(type -> {
			stringRedisTemplate.delete(messageListKey(type));
		});
		stringRedisTemplate.delete(typeSet);
	}

}
