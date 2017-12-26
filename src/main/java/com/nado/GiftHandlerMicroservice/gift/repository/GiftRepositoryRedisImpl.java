package com.nado.GiftHandlerMicroservice.gift.repository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class GiftRepositoryRedisImpl implements GiftRepository {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	SetOperations<String, String> setOperations;
	HashOperations<String, String, String> hashOperations;
	ListOperations<String, String> listOperations;

	static final String giftSetKey = "nado:gifts";
	private String giftKey(String code, GivingRelatedMessageTypes type){
		return "nado:gift:code:"+code+":type:"+type.name();
	}
	private String giftKey(Gift gift){
		return giftKey(gift.getCode(), gift.getType());
	} 
	private void put(Gift gift){
		Map<String, String> temp = new HashMap<>();
		temp.put("code", gift.getCode());
		temp.put("type", gift.getType().name());
		temp.put("price", ""+gift.getPrice());
		temp.put("name", gift.getName());
		hashOperations.putAll(giftKey(gift), temp);
	}
	private boolean validStr(String str){
		return str != null && !str.isEmpty();
	} 
	private Gift get(String k){
		String codeStr = hashOperations.get(k, "code");
		String typeStr = hashOperations.get(k, "type");
		String nameStr = hashOperations.get(k, "name");
		String priceStr = hashOperations.get(k, "price");
		if(validStr(priceStr)&&validStr(nameStr)&&validStr(typeStr)&&validStr(codeStr)){
			return new Gift(
					codeStr, 
					GivingRelatedMessageTypes.valueOf(typeStr), 
					nameStr, 
					new Integer(priceStr));
		}else{
			return null;
		}
	}
	@PostConstruct
	public void init() {
		setOperations = stringRedisTemplate.opsForSet();
		hashOperations = stringRedisTemplate.opsForHash();
		listOperations = stringRedisTemplate.opsForList();
		loadInitialData();
	}
	public void loadInitialData(){
		ArrayList<Gift> initialData = new ArrayList<>();
		addData("1174",	1, "魔法棒", "", "");
		addData("1191",	0, "盛典星光", "", "");
		addData("1192",	0, "仙鱼撒花", "", "");
		addData("1193",	0, "飞鱼破浪", "", "");
		addData("1195",	0, "鱼翔九天", "", "");
		addData("1200",	1, "特制鱼丸", "", "");
		addData("1202",	2, "打call", "", "");
		addData("1203",	60, "年度办卡", "white", "gray");
		addData("1204",	1000, "盛典飞机", "gray", "gold");
		addData("1205",	5000, "盛典火箭", "gold", "black");
		addData("1207",	20000, "荣耀超级火箭", "gold", "red");
		addData("1226",	60, "办卡", "white", "gray");
		addData("191",	1, "魚丸", "", "");
		addData("192",	0, "赞", "", "");
		addData("193",	0, "弱鸡	", "", "");
		addData("195",	1000, "飞机", "gray", "gold");
		addData("196",	5000, "火箭", "gold", "black");
		addData("519",	0, "呵呵", "", "");
		addData("520",	0, "稳", "", "");
		addData("529",	2, "猫耳", "", "");
		addData("712",	0, "棒棒哒", "", "");
		addData("713",	0, "怂", "", "");
		addData("714",	0, "辣眼睛", "", "");
		addData("750",	60, "办卡", "white", "gray");
		addData("754",	0, "水手服", "", "");
		addData("824",	0, "荧光棒", "", "");
		addData("918",	1, "双马尾", "", "");
		addData("924",	60, "办卡", "white", "gray");
		addData("947",	0, "狼爪手", "", "");
		addData("956",	0, "打call", "", "");
		addData("975",	0, "吸星大法", "", "");
		addData("988",	5000, "猫耳火箭", "gold", "black");
		addData("989",	2, "猫耳", "", "");
		addData("997",	20000, "大头火箭", "gold", "red");
		addData("999",	20000, "超级火箭", "gold", "red");
		addData("1340",	0, "一张选票", "", "");
		addData("1327",	0, "平安果", "", "");
		for(GiftInfo giftInfo:data){
			initialData.add(new Gift(giftInfo.getGid(), GivingRelatedMessageTypes.dgb, giftInfo.getName(), giftInfo.getPrice()));
		}
		initialData.add(new Gift("1", GivingRelatedMessageTypes.bc_buy_deserve,"初级酬勤" , 150));
		initialData.add(new Gift("2", GivingRelatedMessageTypes.bc_buy_deserve,"中级酬勤" , 300));
		initialData.add(new Gift("3", GivingRelatedMessageTypes.bc_buy_deserve,"高级酬勤" , 500));
		initialData.add(new Gift("1", GivingRelatedMessageTypes.anbc,"开通骑士" , 2000));
		initialData.add(new Gift("2", GivingRelatedMessageTypes.anbc,"开通子爵" , 5000));
		initialData.add(new Gift("3", GivingRelatedMessageTypes.anbc,"开通伯爵" , 20000));
		initialData.add(new Gift("4", GivingRelatedMessageTypes.anbc,"开通公爵" , 50000));
		initialData.add(new Gift("5", GivingRelatedMessageTypes.anbc,"开通国王" , 100000));
		initialData.add(new Gift("6", GivingRelatedMessageTypes.anbc,"开通皇帝" , 200000));
		initialData.add(new Gift("7", GivingRelatedMessageTypes.anbc,"开通游侠" , 260));
		initialData.add(new Gift("1", GivingRelatedMessageTypes.renewbc,"续费骑士" , 0));
		initialData.add(new Gift("2", GivingRelatedMessageTypes.renewbc,"续费子爵" , 0));
		initialData.add(new Gift("3", GivingRelatedMessageTypes.renewbc,"续费伯爵" , 0));
		initialData.add(new Gift("4", GivingRelatedMessageTypes.renewbc,"续费公爵" , 0));
		initialData.add(new Gift("5", GivingRelatedMessageTypes.renewbc,"续费国王" , 0));
		initialData.add(new Gift("6", GivingRelatedMessageTypes.renewbc,"续费皇帝" , 0));
		initialData.add(new Gift("7", GivingRelatedMessageTypes.renewbc,"续费游侠" , 0));
		initialData.forEach(g->{
			String giftKey = giftKey(g);
			if(!setOperations.isMember(giftSetKey, giftKey)){
				System.out.println("\n\n\n!!!!!!!!!!!!!!!!!!!"+giftKey+"!!!!!!!!!!!!!!!!!!\n\n\n\n");
				save(g);
			}
		});
	}
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GiftInfo{
		private String gid;
		private Integer price;
		private String name;
		private String bgColor;
		private String color;
	}
	List<GiftInfo> data = new ArrayList<>();
	private void addData(String gid,Integer price, String name, String bgColor, String color){
		data.add(new GiftInfo(gid, price, name, bgColor, color));
	}
	@Override
	public void save(Gift gift) {
		// TODO Auto-generated method stub
		setOperations.add(giftSetKey, giftKey(gift));
		put(gift);
	}

	@Override
	public Set<Gift> load() {
		// TODO Auto-generated method stub
		Set<Gift> result = new HashSet<>();
		Set<String> giftKeys = setOperations.members(giftSetKey);
		giftKeys.forEach(key->{
			result.add(get(key));
		});
		return result;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		Set<String> giftKeys = setOperations.members(giftSetKey);
		giftKeys.forEach(key->{
			stringRedisTemplate.delete(key);
		});
		stringRedisTemplate.delete(giftKeys);
	}
	
}
