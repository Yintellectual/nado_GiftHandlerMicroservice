package com.nado.douyuConnectorMicroservice.douyuClient.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class DouyuApiGift {
	private String id;
	private String name;
	private String type;//2 for cash gifts, 1 for non-cash gifts
	private String pc;
	private Integer gx;//money in 0.1CYN
	private String desc;
	private String intro;
	private String mimg;
	private String himg;
}
