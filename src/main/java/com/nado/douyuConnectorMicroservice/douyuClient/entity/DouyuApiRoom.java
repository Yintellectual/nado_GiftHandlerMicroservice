package com.nado.douyuConnectorMicroservice.douyuClient.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class DouyuApiRoom {
	@Data
	public static class DouyuApiData{
		private String room_id;
		private String room_thumb;
		private String cate_id;
		private String room_name;
		private String room_status;
		private String owner_name;
		private String avatar;
		private Integer online;//online now == hn, hn is "热度"
		private Integer hn;//hn == 0 if room status == 2
		private String owner_weight;
		private String fans_num;
		private String start_time;//format  "2017-12-15 20:02"
		private DouyuApiGift[] gift;
	}
	private String error;
	private DouyuApiData data;
}
