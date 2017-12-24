package com.nado.GiftHandlerMicroservice.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExtractedGivingInfo {
	private String uid;
	private Integer price;
	private Long timestamp;
	private String id;
}
