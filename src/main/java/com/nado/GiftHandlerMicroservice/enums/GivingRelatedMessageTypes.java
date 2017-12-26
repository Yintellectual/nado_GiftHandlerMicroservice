package com.nado.GiftHandlerMicroservice.enums;

import com.nado.GiftHandlerMicroservice.GiftHandlerMicroserviceApplication;
import com.nado.GiftHandlerMicroservice.entity.ExtractedGivingInfo;
import com.nado.GiftHandlerMicroservice.gift.entity.Gift;
import com.nado.GiftHandlerMicroservice.gift.repository.TypedStringRepository;
import com.nado.GiftHandlerMicroservice.gift.service.GiftDictionary;
import static com.nado.douyuConnectorMicroservice.util.CommonUtil.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
public enum GivingRelatedMessageTypes {
	dgb {	
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message, GiftDictionary dictionary, TypedStringRepository unknownGiftRepository) {
			//"/timestamp@=1514289975018/messageId@=151597/type@=dgb/rid@=2020877/gfid@=1340/gs@=3/uid@=151562524/nn@=善恶衍生/ic@=avanew@Sface@S201708@S06@S00@S0b4552f7704f732f737111772984b971/eid@=0/level@=17/dw@=91833500/hits@=235/ct@=0/el@=eid@AA=1500000113@ASetp@AA=1@ASsc@AA=1@ASef@AA=0@AS@S/cm@=0/bnn@=豆霸霸/bl@=11/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/sahf@=0/fc@=0/"
			this.count++;
			return extractedGivingInfo("gfid", "uid", message, dictionary,unknownGiftRepository);
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	}, bc_buy_deserve {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message, GiftDictionary dictionary , TypedStringRepository unknownGiftRepository) {
			// "/timestamp@=1514289387016/messageId@=148937/type@=bc_buy_deserve/level@=30/lev@=3/rid@=2020877/gid@=0/cnt@=1/hits@=1/sid@=6618417/sui@=id@A=6618417@Sname@A=@Snick@A=斜揽殘箫@Sicon@A=avanew@ASface@AS201712@AS13@AS19@AS2504aa11dd52ba2ebf6b995824565098@Srg@A=1@Spg@A=1@Srt@A=0@Sbg@A=0@Sweight@A=0@Scps_id@A=0@Sps@A=0@Ses@A=0@Sver@A=0@Sglobal_ban_lev@A=0@Sexp@A=0@Slevel@A=30@Scurr_exp@A=0@Sup_need@A=0@Sgt@A=0@Sit@A=0@Sits@A=0@Scm@A=0@Srni@A=0@Shcre@A=0@Screi@A=0@Sel@A=@Shfr@A=32609@Sfs@A=0@S/sahf@=0/bnn@=/bl@=0/brid@=0/hc@=/"
			this.count++;
			return extractedGivingInfo("lev", "sid", message, dictionary,unknownGiftRepository);
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	}, anbc {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message, GiftDictionary dictionary, TypedStringRepository unknownGiftRepository) {
			//   "/timestamp@=1514277116016/messageId@=111205/type@=anbc/rid@=2020877/gid@=0/bt@=2/uid@=30334664/hrp@=1/unk@=豆国九品小吏/uic@=avanew@Sface@S201712@S20@S22@Sb3a73edb325de7c9e78cd8f677488b7c/drid@=2020877/donk@=纳豆nado/nl@=1/ts@=1514277115/",
			this.count++;
			String drid = matchStringValue(message, "drid");
			if(drid.equals("2020877")){
				return extractedGivingInfo("nl", "uid", message, dictionary,unknownGiftRepository);	
			}else{
				return null;
			}
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	}, renewbc {
		private Long count = 0l;
		@Override
		public ExtractedGivingInfo extractGivingInfo(String message, GiftDictionary dictionary, TypedStringRepository unknownGiftRepository) {
			//   "/timestamp@=1514277116016/messageId@=111205/type@=anbc/rid@=2020877/gid@=0/bt@=2/uid@=30334664/hrp@=1/unk@=豆国九品小吏/uic@=avanew@Sface@S201712@S20@S22@Sb3a73edb325de7c9e78cd8f677488b7c/drid@=2020877/donk@=纳豆nado/nl@=1/ts@=1514277115/",
			this.count++;
			String drid = matchStringValue(message, "drid");
			if(drid.equals("2020877")){
				return extractedGivingInfo("nl", "uid", message, dictionary, unknownGiftRepository);	
			}else{
				return null;
			}
		}
		@Override
		public Long getCount() {
			// TODO Auto-generated method stub
			return count;
		}
	};
	public ExtractedGivingInfo extractedGivingInfo(String code_field, String uid_field, String message, GiftDictionary dictionary, TypedStringRepository unknownGiftRepository){
		// "/timestamp@=1514289387016/messageId@=148937/type@=bc_buy_deserve/level@=30/lev@=3/rid@=2020877/gid@=0/cnt@=1/hits@=1/sid@=6618417/sui@=id@A=6618417@Sname@A=@Snick@A=斜揽殘箫@Sicon@A=avanew@ASface@AS201712@AS13@AS19@AS2504aa11dd52ba2ebf6b995824565098@Srg@A=1@Spg@A=1@Srt@A=0@Sbg@A=0@Sweight@A=0@Scps_id@A=0@Sps@A=0@Ses@A=0@Sver@A=0@Sglobal_ban_lev@A=0@Sexp@A=0@Slevel@A=30@Scurr_exp@A=0@Sup_need@A=0@Sgt@A=0@Sit@A=0@Sits@A=0@Scm@A=0@Srni@A=0@Shcre@A=0@Screi@A=0@Sel@A=@Shfr@A=32609@Sfs@A=0@S/sahf@=0/bnn@=/bl@=0/brid@=0/hc@=/"
		String code = matchDigitalValue(message, code_field);
		System.out.println(this+":code:"+code);
		Gift gift = dictionary.get(code, this);
		if(gift==null){
			//unknown gift handler
			unknownGiftRepository.save(message, this+"-"+code);
			return null;
		}else{
			return new ExtractedGivingInfo(matchDigitalValue(message, uid_field), gift, new Long(matchDigitalValue(message, "timestamp")), matchDigitalValue(message, "messageId"));
		}
	}
	public abstract ExtractedGivingInfo extractGivingInfo(String message, GiftDictionary dictionary, TypedStringRepository unknownGiftRepository);
	public abstract Long getCount();
}
