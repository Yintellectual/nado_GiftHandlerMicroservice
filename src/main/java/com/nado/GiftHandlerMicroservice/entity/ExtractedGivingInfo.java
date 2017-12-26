package com.nado.GiftHandlerMicroservice.entity;

import java.time.LocalDateTime;

import com.nado.GiftHandlerMicroservice.gift.entity.Gift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractedGivingInfo {
	private String uid;
	private Gift gift;
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
	@Override
	public int hashCode(){
		return (messageId+timestamp).hashCode();
	}
}
