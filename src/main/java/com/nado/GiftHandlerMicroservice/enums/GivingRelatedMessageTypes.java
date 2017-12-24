package com.nado.GiftHandlerMicroservice.enums;

import com.nado.GiftHandlerMicroservice.entity.ExtractedGivingInfo;

public enum GivingRelatedMessageTypes {
	dgb {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message) {
			// TODO Auto-generated method stub
			System.out.println("\n\ndgb           :" +message);
			this.count++;
			return null;
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	}, bc_buy_deserve {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message) {
			// TODO Auto-generated method stub
			System.out.println("\n\nbc_buy_deserve:" +message);
			this.count++;
			return null;
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	}, anbc {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message) {
			// TODO Auto-generated method stub
			System.out.println("\n\nanbc          :" +message);
			this.count++;
			return null;
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	}, renewbc {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message) {
			System.out.println("\n\nrenewbc       :" +message);
			// TODO Auto-generated method stub
			this.count++;
			return null;
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	};
	
	public abstract ExtractedGivingInfo extractGivingInfo(String message);
	public abstract Long getCount();
}
