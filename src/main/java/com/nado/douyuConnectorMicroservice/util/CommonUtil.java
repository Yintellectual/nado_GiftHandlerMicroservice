package com.nado.douyuConnectorMicroservice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	
	
	public static String matchDigitalValue(String message, String fieldName) {
		Pattern pattern = Pattern.compile("/"+fieldName + "@=(\\d*)");
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static String matchStringValue(String message, String fieldName) {
		Pattern pattern = Pattern.compile("/"+fieldName + "@=(\\w*)");
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static List<String> extractFields(String template) {
		List<String> result = new ArrayList<>();
		Pattern pattern = Pattern.compile("(\\w*)@=.*?/");
		Matcher matcher = pattern.matcher(template);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		return result;
	}
	
	public static Map<String, String> generateSampleRecords(){
		Map<String, String> result = new LinkedHashMap<>();
		LocalTime time = LocalTime.of(7, 0);
		for(int i=0;i<288;i++){
			String timeString = time.format(DateTimeFormatter.ofPattern("HH:mm"));
			time = time.plus(5,ChronoUnit.MINUTES);
			result.put(timeString, ""+i);
		}
		return result;
	}
	public static LinkedHashMap<String, String> sortTrafficRecordByTimeOrder(Map<String, String> raw){
		System.out.println("\n\n\n\n\n!!!!!!!!!!!!!!!!!!!"+raw);
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		LocalTime initTime = LocalTime.of(7, 0);
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
		List<LocalTime> listOfKeys = new LinkedList<>();
		for(String rawKeyString:raw.keySet()){
			LocalTime rawKeyTime = LocalTime.parse(rawKeyString, pattern);
			listOfKeys.add(rawKeyTime);
		}
		listOfKeys.sort((a,b)->{
			if(a.isAfter(initTime)&&(b.isAfter(initTime))){
				return a.compareTo(b);
			}else if(a.isBefore(initTime)&&(b.isBefore(initTime))){
				return a.compareTo(b);
			}else if(a.isAfter(initTime)&&(b.isBefore(initTime))){
				return -1;
			}else if(a.isBefore(initTime)&&(b.isAfter(initTime))){
				return 1;
			}else if(a.equals(initTime)){
				if(b.isBefore(initTime)){
					return 1;
				}else{
					return -1;
				}
			}else if(b.equals(initTime)){
				if(a.isBefore(initTime)){
					return -1;
				}else{
					return 1;
				}
			}else if(a.equals(initTime)&&b.equals(initTime)){
				return 0;
			}else{
				throw new RuntimeException();
			}
		});
		listOfKeys.forEach(time->{
			String key = time.format(pattern);
			result.put(key, raw.get(key));	
		});
		return result;
	}
	
	public static LocalDateTime virtualizeDateTime(LocalDateTime now){
		if(now.toLocalTime().isBefore(LocalTime.of(7, 0))){
			return now.minus(1, ChronoUnit.DAYS);
		}else{
			return now;
		}
	}
	public static String getvirtualizedDate(LocalDateTime now){
		return virtualizeDateTime(now).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	public static Map<String, List<String>> mapOfTypeAndFields = new HashMap<>();
	static {
		String[] templates = new String[]{
				"/timestamp@=1513727757027/messageId@=11/type@=loginres/userid@=0/roomgroup@=0/pg@=0/sessionid@=0/username@=/nickname@=/live_stat@=0/is_illegal@=0/ill_ct@=/ill_ts@=0/now@=0/ps@=0/es@=0/it@=0/its@=0/npv@=0/best_dlev@=0/cur_lev@=0/nrc@=2098404416/ih@=0/sid@=72963/sahf@=0/",
				//nl 贵族等级， nl 6 是皇帝， nl 7 是游侠。
				"/type@=chatmsg/rid@=58839/uid@=123456/nn@=test/txt@=666/cid@=1111/ic@=icon/sahf@=0/level@=1/bnn@=test/bl@=0/brid@=58839/hc@=0/el@=eid@AA=1@ASetp@AA=1@ASsc@AA=1@AS/",
				"/type@=onlinegift/rid@=1/uid@=1/gid@=-9999/sil@=1/if@=1/ct@=1/nn@=tester/ur@=1/level@=6/btype@=1/",
				"/type@=dgb/gfid@=1/gs@=59872/uid@=1/rid@=1/nn@=someone/level@=1/dw@=1/",
				"/type@=uenter/rid@=1/uid@=1/nn@=someone/level@=1/el@=eid@AA=1@ASetp@AA=1@ASsc@AA=1@AS@S/",
				"/type@=ranklist/rid@=1/gid@=-9999/list_all@=榜单结构体/list@=榜单结构体/list_day@=榜单结构体/",
				"/type@=ssd/rid@=1/gid@=-9999/sdid@=1/trid@=1/content@=test/clitp@=1/url@=test_url/jmptp@=1/",
				"/type@=spbc/rid@=1/gid@=1/gfid@=1/sn@=name/dn@=name/gn@=1/gc@=1/gb@=1/es@=1/gfid@=1/eid@=1/",
				"/type@=online_noble_list/rid@=1/num@=3/nl@=贵族列表结构/",
				"/type@=blab/rid@=1/uid@=10002/nn@=test/lbl@=2/bl@=3/ba@=1/bnn@=ttt/",
				"/type@=frank/rid@=10111/fc@=200/bnn@=test/ver@=1/list@=榜单结构/",
				"/type@=synexp/o_exp@=14053053060/o_lev@=91/o_minexp@=13954500000/o_upexp@=801446940/rid@=2020877/timestamp@=1513593136037/messageId@=20/",
				"/uid@=2020877/rid@=2020877/cate_id@=2/rid@=2020877/ri@=sc@A=519000@Sidx@A=109@S/type@=rri",
				"/type@=upgrade/rid@=1/gid@=-9999/uid@=12001/nn@=test/level@=3/ic@=icon/",
				"/type@=bc_buy_deserve/rid@=1/gid@=-9999/level@=1/cnt@=1/hits@=1/lev@=1/sui@=用户信息序列化/",
				"/type@=rss/rid@=1/gid@=1/ss@=1/code@=1/rt@=0/notify@=1/endtime@=1/",
				"/type@=ggbb/rid@=1/gid@=1/gt@=1/sl@=100/sid@=123/did@=321/snk@=name1/dnk@=name2/rpt@=1/",
				"/type@=rankup/uid@=1/rn@=3/rid@=1/rkt@=1/gid@=-9999/rt@=0/nk@=test/sz@=3/drid@=1/bt@=1/",
				"/type@=gpbc/rid@=1/cnt@=1/sid@=10000/did@=20001/rpt@=1/snk@=test1/dnk@=test2/pnm@=prop_name/",
				"/type@=newblackres/rid@=1/gid@=-9999/ret@=0/otype@=2/sid@=10002/did@=10003/snic@=stest/dnic@=dtest/endtime@=1501920157/",
				"/type@=srres/rid@=1/gid@=-9999/uid@=12001/nickname@=test/exp@=3/",
				"/type@=mrkl/timestamp@=1513636442016/messageId@=159222/",
				"/rid@=2020877/sc@=1505300/sctn@=0/rid@=-1/type@=qausrespond/timestamp@=1513633742013/messageId@=158426/",
				"/timestamp@=1513630449014/messageId@=157728/type@=wiru/pos@=8/rid@=2020877/",
				"/timestamp@=1513608253025/messageId@=91666/type@=wirt/uid@=128027279/nick@=纳豆nado/pos@=5/rid@=2020877/",
				"/timestamp@=1513610743019/messageId@=122335/roomset@=/catelv1@=/catelv2@=/type@=task_qmii/rid@=2020877/ftype@=0/fid@=87146/uid@=26229649/itype@=3/",
				"/timestamp@=1513609391029/messageId@=112952/type@=setadminres/rescode@=0/userid@=73219005/opuid@=128027279/group@=4/adnick@=被坦克豆打中的二狗/rid@=2020877/gid@=0/level@=42/",
				"/timestamp@=1513612801016/messageId@=129269/type@=rsm/rid@=2020877/gid@=0/t@=2/bt@=1513612800/vt@=86399/c@=校花梦工厂，超人气女友手游，中日声优齐助阵，每天送礼约会可以增加亲密度，还有各种隐藏福利等你发现!/url@=http:@S@Suri6.com@Stkio@SRzEZr2a/id@=789/clitp@=4/jmptp@=0/distm@=15/mtype@=1/wis@=/mis@=/weburl@=/",
				"/timestamp@=1513629018950/messageId@=157336/type@=rnewbc/rid@=2020877/gid@=0/bt@=1/uid@=4098623/unk@=Em丶丶/uic@=avanew@Sface@S201707@S12@S15@Scbe121cc133a38be5e8be26588bb5501/drid@=3226952/donk@=小恶魔没有钱/nl@=4/",
				"/timestamp@=1513620522094/messageId@=153670/type@=rlcn/rn@=ctrnz/cate_id@=2/seq@=518343/rls@=sc@AA=52566800@ASidx@AA=1@ASrid@AA=3408931@ASuid@AA=169547106@ASrn@AA=小狸姑娘@ASic@AA=avanew@AASface@AAS201710@AAS13@AAS02@AAS9d8d07c036152a792d14429a7be66b36@AS@Ssc@AA=35441000@ASidx@AA=2@ASrid@AA=485503@ASuid@AA=29842938@ASrn@AA=彡彡九户外@ASic@AA=avanew@AASface@AAS201612@AAS12@AAS16@AAS6207ee53080654d0711a4062a71a8616@AS@Ssc@AA=34556800@ASidx@AA=3@ASrid@AA=430489@ASuid@AA=25059227@ASrn@AA=长沙乡村敢死队@ASic@AA=avanew@AASface@AAS201711@AAS10@AAS01@AASe59ef76a6b37104e1816e34410864106@AS@S/rid@=2020877/",
				//ids 弹幕抽奖
				"/timestamp@=1513608215033/messageId@=90035/roomset@=/catelv1@=/catelv2@=/type@=lds/ftype@=0/tuid@=0/prize_name@=游侠~/prize_img@=/prize_num@=9/activity_type@=1/join_type@=1/join_condition@={\"command_content\":\"\u8c22\u8c22\u6c6a\u6c6a\u7684\u8d5e\u52a9~\",\"expire_time\":180,\"lottery_range\":3}/now_time@=1513608215/expire_time@=1513608395/rid@=2020877/",
				"/timestamp@=1513607802023/messageId@=80525/roomset@=/catelv1@=/catelv2@=/ftype@=0/type@=lde/tuid@=0/rid@=2020877/activity_id@=550257/prize_name@=游侠~/prize_img@=/activity_type@=1/prize_type@=0/end_type@=1/join_type@=1/lottery_range@=3/join_count@=1637/gift_name@=弹幕/gift_count@=3290/join_time@=184/fans_count@=13/follow_count@=49/win_list@=[{\"uid\":\"51910229\",\"nickname\":\"\u846b\u82a6\u7237\u6551\u5a03\u5a03\u513f\",\"level\":\"25\"},{\"uid\":\"1100921\",\"nickname\":\"Somnus\u4e36\u660e\",\"level\":\"8\"},{\"uid\":\"53670539\",\"nickname\":\"lionelup\",\"level\":\"10\"},{\"uid\":\"3948300\",\"nickname\":\"caojian\",\"level\":\"24\"},{\"uid\":\"14383502\",\"nickname\":\"\u5403\u8d27\u6700\u6015\u505a\u997f\u68a6\u556600\",\"level\":\"14\"},{\"uid\":\"148316234\",\"nickname\":\"Q\u4e36\u5bc2\u9759\u65f6\u5149\u7b49\u8c46\u51e0\",\"level\":\"25\"},{\"uid\":\"110489352\",\"nickname\":\"\u5728\u89c1\u5355\u7eaf\",\"level\":\"19\"},{\"uid\":\"39079830\",\"nickname\":\"Mr\u5c0f\u8ce4\u54e5\",\"level\":\"20\"},{\"uid\":\"107361884\",\"nickname\":\"\u6211\u4eec\u8bf4\u597d\u7684o\",\"level\":\"16\"}]/ad_list@={\"alp\":2}/",
				"/timestamp@=1513609197020/messageId@=109169/rid@=2020877/gid@=0/type@=gbroadcast/ft@=1/gbcc@={\"type\":\"redirectnw\",\"msgtype\":\"emperor_push\",\"nickname\":\"\",\"rid\":3688992,\"rnickname\":\"少女欣ovo\"}/",
				//cthn 飞屏
				"/timestamp@=1513608194034/messageId@=89816/type@=cthn/rid@=2020877/gid@=0/cl@=2/cid@=174/uid@=45491421/nl@=3/unk@=豆子的future丶husband/drid@=2020877/onk@=纳豆nado/chatmsg@=欢迎收看花落宫廷错流年～～/ts@=1513608194/",
				//anbc 爵位, /gvuid@=153885927/gvnk@=汪可呦 是埋单用户
				"/timestamp@=1513611088023/messageId@=123772/type@=anbc/rid@=2020877/gid@=0/bt@=2/uid@=37216637/hrp@=0/unk@=南风之环/uic@=avatar@S037@S21@S66@S37_avatar/drid@=2020877/donk@=纳豆nado/nl@=7/ts@=1513611088/",
				"/timestamp@=1513605013041/messageId@=28028/roomset@=/catelv1@=/catelv2@=/type@=task_qmyq/rid@=2020877/ftype@=0/fid@=87146/ktfid@=0/uid@=26229649/aid@=128027279/tn@=能不能让骑士团热一下.../sn@=独占大脑阔/gfid@=191/gfc@=60/",
				"/timestamp@=1513607382019/messageId@=69985/roomset@=/catelv1@=/catelv2@=/ftype@=0/tuid@=0/rid@=2020877/type@=rtmp60/nickname@=豆霸霸丶影流/share@=截取了主播的/topic_name@=/",
				"/timestamp@=1513819868038/messageId@=4/type@=noble_num_info/sum@=29/list@=lev@AA=3@ASnum@AA=3@AS@Slev@AA=2@ASnum@AA=1@AS@Slev@AA=1@ASnum@AA=6@AS@Slev@AA=7@ASnum@AA=19@AS@S/rid@=2020877/",
				
		};
		for(String template:templates){
			mapOfTypeAndFields.put(matchStringValue(template, "type"), extractFields(template));
		}
	}
}
