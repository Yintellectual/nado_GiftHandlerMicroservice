package com.nado.GiftHandlerMicroservice.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExtractedGivingInfo {
	private String uid;
	private Integer price;
	private Long timestamp;
	private String messageId;
	
	@Override
	public boolean equals(Object o){
		if(o!=null && (o instanceof ExtractedGivingInfo)){
			ExtractedGivingInfo other = (ExtractedGivingInfo) o;
			return timestamp.equals(other.getTimestamp()) 
					&& messageId.equals(other.getMessageId());
		}else{
			return false;
		}
	}
}
