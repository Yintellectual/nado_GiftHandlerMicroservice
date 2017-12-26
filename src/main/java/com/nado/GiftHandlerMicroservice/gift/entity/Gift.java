package com.nado.GiftHandlerMicroservice.gift.entity;

import com.nado.GiftHandlerMicroservice.entity.ExtractedGivingInfo;
import com.nado.GiftHandlerMicroservice.enums.GivingRelatedMessageTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gift {
	String code;
	GivingRelatedMessageTypes type;
	String name;
	Integer price;
	
	@Override
	public boolean equals(Object o){
		if(o!=null && (o instanceof Gift)){
			Gift other = (Gift) o;
			return code.equals(other.getCode()) 
					&& type.equals(other.getType());
		}else{
			return false;
		}
	}
	@Override
	public int hashCode(){
		return (type.name()+code).hashCode();
	}
}
