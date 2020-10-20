package com.kplan.phonecard.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.kplan.phonecard.domain.PhoneRuleResult;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

public class PhoneRuleUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 规则，号码里出现最多次数4次数字
	 * 
	 * @param phoNe
	 * @return
	 */

	private static List<String> repeatePartitionPhone(String phone, int len) {
		List<String> list = Lists.newArrayList();
		if (phone.length() <= len) {
			list.add(phone);
		} else {
			for (int i = 0; i <= phone.length() - len; i++) {
				String tripleNum = phone.substring(i, i + len);
				list.add(tripleNum);

			}
		}

		return list;

	}

	private List<String> partitionPhone(String phone, int len) {
		return StreamEx.of(Lists.partition(Lists.newArrayList(phone.split("")), len)).map(list -> String.join("", list))
				.toList();

	}

	/**
	 * AAAA
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_aaa(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 3);
		boolean matched = StreamEx.of(numList).anyMatch(str -> {
			String num = String.valueOf(str.charAt(0));
			
			if (str.replace(num, "").length() == 0) {
				return true;
			} else {
				return false;
			}

		});
		if (matched) {
			return "AAA";
		} else {
			return "";
		}
	}

	/**
	 * ABAB
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_abab(String phone) {
		phone=phone.substring(3, 11);
		String numStr="";
		List<String> numList = repeatePartitionPhone(phone, 2);
		for (int i = 0; i < numList.size()-2 ; i++) {
			String num = numList.get(i);
			String nextNum = numList.get(i + 2);
			if(num.equalsIgnoreCase(nextNum)) {
				System.out.println(nextNum);
				numStr="ABAB";
			}
//			String nextNum = numList.get(i);

		}
		if(StringUtils.trimToNull(numStr)!=null) {
			return "ABAB";
		}else {
			return "";
		}
//		if (num.equalsIgnoreCase(nextNum)) {
////			System.out.println("abab匹配" + nextNum);
//			return "ABAB";
//		} else {
//			return "";
//		}
//		return "";

	}

	/**
	 * AABB
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_aabb(String phone) {
		phone=phone.substring(3, 11);
		List<String> numList = repeatePartitionPhone(phone, 2);
		for (int i = 0; i < numList.size() - 2; i++) {
			String num = numList.get(i);
			String nextNum = numList.get(i + 2);

			if (num.replace(String.valueOf(num.charAt(0)), "").length() == 0
					&& nextNum.replace(String.valueOf(nextNum.charAt(0)), "").length() == 0) {
				System.out.println("aabb匹配" + nextNum);
				return "AABB";
			}

		}

//		System.out.println("aabb未匹配");
		return "";

	}

	/**
	 * ABCD
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_abcd(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 4);
		boolean matched = StreamEx.of(numList).filter(numStr -> {
			return StreamEx.of(numStr.split("")).distinct().count() == 4;
		}).map(numStr -> {
			return StringUtils.reverse(numStr).compareTo(numStr) > 0 ? numStr : StringUtils.reverse(numStr);
		}).filter(numStr -> {
			return StreamEx.of(numStr.split("")).sorted().joining().equals(numStr);
		}).anyMatch(numStr -> {

			Integer num0 = Integer.parseInt(String.valueOf(numStr.charAt(0)));
			Integer num3 = Integer.parseInt(String.valueOf(numStr.charAt(3)));
			System.out.println(numStr);
			return (num0 + 3) == num3;
		});
//		System.out.println(matched);
		if (matched) {
			return "四连号";
		}

		return "";

	}

	/**
	 * ABC
	 * 
	 * @param phone
	 */
	public static String rule_abc(String phone) {
		if (StringUtils.trimToNull(phone) == null) {
			return "";
		}
		phone = phone.substring(8, 11);
		List<String> numList = repeatePartitionPhone(phone, 3);
		boolean matched = StreamEx.of(numList).filter(numStr -> {
			return StreamEx.of(numStr.split("")).distinct().count() == 3;
		}).map(numStr -> {
			return StringUtils.reverse(numStr).compareTo(numStr) > 0 ? numStr : StringUtils.reverse(numStr);
		}).filter(numStr -> {
			return StreamEx.of(numStr.split("")).sorted().joining().equals(numStr);
		}).anyMatch(numStr -> {
			System.out.println(numStr);
			Integer num0 = Integer.parseInt(String.valueOf(numStr.charAt(0)));
			Integer num3 = Integer.parseInt(String.valueOf(numStr.charAt(2)));
			return (num0 + 2) == num3;
		});
		
		if (matched) {
			return "末三连";
		}

		return "";

	}

	/**
	 * AABA
	 * 
	 * @param phone
	 */

	public static String rule_aaba(String phone) {
		phone=phone.substring(7, 11);
		List<String> numList = repeatePartitionPhone(phone, 4);

		boolean matched = StreamEx.of(numList).filter(numStr -> {
			return StreamEx.of(numStr.split("")).distinct().count() == 2;
		}).anyMatch(numStr -> {
			System.out.println(numStr);
			List<String> distinctNumList = StreamEx.of(numStr.split("")).distinct().toList();
			return StringUtils.countMatches(numStr, distinctNumList.get(0)) == 1
					|| StringUtils.countMatches(numStr, distinctNumList.get(1)) == 1;
		});

		if (matched) {
			return "末8AABA";
		}
		return "";

	}

	/**
	 * AABAA
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_aabaa(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 5);
		boolean matched = StreamEx.of(numList).filter(numStr -> {
			System.out.println(numStr);
			return StreamEx.of(numStr.split("")).distinct().count() == 2;
		}).anyMatch(numStr -> {
			StringBuilder sb = new StringBuilder(numStr).replace(2, 3, "");
			return StreamEx.of(sb.toString().split("")).distinct().count() == 1;
		});
//		System.out.println(matched);
		if (matched) {
			return "AABAA";
		}

		return "";

	}

	/**
	 * ABCABC
	 * 
	 * @param phone
	 */
	public static String rule_abcabc(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 3);
		for (int i = 0; i < numList.size() - 3; i++) {
			String num = numList.get(i);
			String nextNum = numList.get(i + 3);
			System.out.println(nextNum);
			if (num.equalsIgnoreCase(nextNum)) {
//				System.out.println(nextNum.indexOf(phone));
				return "ABCABC";
			}

		}

//		System.out.println("abcabc未匹配");
		return "";

	}

	private static char[] indexOf(String nextNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ABCAB
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_abcab(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 5);
		boolean matched = StreamEx.of(numList).filter(numStr -> {
			return StreamEx.of(numStr.split("")).distinct().count() == 3;
		}).anyMatch(numStr -> {
			System.out.println(numStr);
			String num = new StringBuilder(numStr).replace(2, 3, "").toString();
			return num.substring(0, 2).equals(num.substring(2, 4));
		});
		if (matched) {
			return "ABCAB";
		} else {
			return "";
		}

	}

	/**
	 * SR
	 */
	public static String rule_birthday(String phone) {

		Integer month = Integer.parseInt(phone.substring(7, 9));
		Integer day = Integer.parseInt(phone.substring(9, 11));
		if (month >= 1 && month <= 12) {
			LocalDate now = LocalDate.now().withMonth(month);
			if (day <= now.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
				return "SR";
			} else {
				return "";
			}
		}
		return "";

	}

	/**
	 * 4GP
	 * 
	 * @param phoNe
	 * @return
	 */
	public static String phoNeRuleNumFour(String phoNe) {
		List<?> l = EntryStream.of(StreamEx.of(phoNe).flatArray(str -> str.split("")).groupingBy(item -> item))
				.mapToValue((k, v) -> v.size()).filterValues(s -> s >= 4).mapKeys(k -> k).toList();
		if (l.size() > 0 && StringUtils.trim(l.get(0).toString()) != null) {
			String[] str = l.get(0).toString().split("=");
			if (str.length >= 2) {
				if (Integer.parseInt(str[1]) >= 4) {
					logger.info("4次以上高频靓号号码：" + phoNe);
					return "5GP";
				} else {
					return "";
				}
			}
		}
		return "";
	}

	public static String phoNeRuleNumFive(String phoNe) {
		List<?> l = EntryStream.of(StreamEx.of(phoNe).flatArray(str -> str.split("")).groupingBy(item -> item))
				.mapToValue((k, v) -> v.size()).filterValues(s -> s >= 5).mapKeys(k -> k).toList();
		if (l.size() > 0 && StringUtils.trim(l.get(0).toString()) != null) {
			String[] str = l.get(0).toString().split("=");
			if (str.length >= 2) {
				if (Integer.parseInt(str[1]) >= 5) {
					System.out.println(str[0]);
					logger.info("5次以上高频靓号号码：" + phoNe);
					return "5次高频";
				} else {
					return "";
				}
			}
		}
		return "";
	}

	/**
	 * 高频出现3次
	 * 
	 * @param phoNe
	 * @return
	 */
	public static Boolean phoNeRuleNumThree(String phoNe) {
		List<?> l = EntryStream.of(StreamEx.of(phoNe).flatArray(str -> str.split("")).groupingBy(item -> item))
				.mapToValue((k, v) -> v.size()).filterValues(s -> s >= 3).mapKeys(k -> k).toList();
		if (l.size() > 0 && StringUtils.trim(l.get(0).toString()) != null) {
			String[] str = l.get(0).toString().split("=");
			if (str.length >= 2) {
				if (Integer.parseInt(str[1]) >= 3) {
					logger.info("3次以上高频靓号号码：" + phoNe);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * ababcaa
	 * 
	 * @param phone
	 * @return
	 */
	public static String rule_ABABCAA(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 7);
		boolean matched = StreamEx.of(numList).filter(numStr -> {
			return StreamEx.of(numStr.split("")).distinct().count() == 3;
		}).anyMatch(numStr -> {
			String[] numArray = numStr.split("");
			System.out.println(numStr);
			return numArray[0].equals(numArray[2]) && numArray[1].equals(numArray[3]) && numArray[5].equals(numArray[6])
					&& (numArray[0].equals(numArray[5]) || numArray[1].equals(numArray[5]));
		});
		if (matched) {
			return "ABABCAA";
		} else {
			return "";
		}
	}

	public static String rule_ABABCBB(String phone) {
		List<String> numList = repeatePartitionPhone(phone, 7);
		boolean matched = StreamEx.of(numList).filter(numStr -> {
			return StreamEx.of(numStr.split("")).distinct().count() == 3;
		}).anyMatch(numStr -> {
			String[] numArray = numStr.split("");
			return numArray[0].equals(numArray[2]) && numArray[1].equals(numArray[3]) && numArray[1].equals(numArray[5])
					&& numArray[1].equals(numArray[6]);
		});
		if (matched) {
			return "ABABCBB";
		} else {
			return "";
		}
	}

	/**
	 * @param phone
	 * @return
	 */
	public static String reluPhoneNumTo(String phone) {
		String str = "";
		str = rule_ABABCAA(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		return "";
	}

	/**
	 * 筛选靓号
	 * 
	 * @param phone
	 * @return
	 */
	public static String reluPhoneNum(String phone) {
		String str = "";
		str = rule_aaa(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_ABABCAA(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_ABABCBB(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_aabaa(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abcd(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_aabb(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abcabc(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abab(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_aaba(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = phoNeRuleNumFive(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abc(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_birthday(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		return str;

	}
	
	
	/**
	 * 抓单下单筛选靓号
	 * 
	 * @param phone
	 * @return
	 */
	public static String orderReluPhoneNum(String phone) {
		String str = "";
		if(StringUtils.trimToNull(phone)==null||phone.length()<11) {
			return "";
		}
		str = rule_aaa(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_ABABCAA(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_ABABCBB(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_aabaa(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abcd(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_aabb(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abcabc(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abab(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_aaba(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = phoNeRuleNumFive(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
		str = rule_abc(phone);
		if (StringUtils.trimToNull(str) != null) {
			return str;
		}
//		str = rule_birthday(phone);
//		if (StringUtils.trimToNull(str) != null) {
//			return str;
//		}
		return str;

	}
	
	

	/**
	 * 联通返回數組为电话号码
	 * 
	 * @param phones
	 */
	public static List<PhoneRuleResult> listToPhone(String phones, String province_code, String city_code) {
		List<List<String>> lists = Lists.partition(Arrays.asList(phones.split(",")), 12);
		List<PhoneRuleResult> phoneList = new ArrayList<PhoneRuleResult>();
		for (int i = 0; i < lists.size(); i++) {
			PhoneRuleResult phoneReslut = new PhoneRuleResult();
			List<String> phone = lists.get(i);
			String phoneNum = phone.get(0);
			phoneNum = phoneNum.replace("[", "");
			phoneNum = phoneNum.replace("]", "");
			phoneReslut.setPhone(phoneNum);
			phoneReslut.setProvincode(province_code);
			phoneReslut.setCitycode(city_code);
			phoneList.add(phoneReslut);
		}
		
		return phoneList;
	}
	
	/**抓单获取号码列表处理返回电话集合
	 * @param phones
	 * @return
	 */
	public static List<PhoneRuleResult> ordersToPhone(String phones) {
		List<List<String>> lists = Lists.partition(Arrays.asList(phones.split(",")), 12);
		List<PhoneRuleResult> phoneList = new ArrayList<PhoneRuleResult>();
		for (int i = 0; i < lists.size(); i++) {
			PhoneRuleResult phoneReslut = new PhoneRuleResult();
			List<String> phone = lists.get(i);
			String phoneNum = phone.get(0);
			phoneNum = phoneNum.replace("[", "");
			phoneNum = phoneNum.replace("]", "");
			if(StringUtils.trimToNull(phoneNum)!=null) {
			phoneReslut.setPhone(phoneNum);
			phoneList.add(phoneReslut);
			}
		}
		
		return phoneList;
	}

	public static void main(String[] args) {
		
//		System.out.println(rule_abcabc("12312338975"));;
//		System.out.println("0123"+rule_abcd("18065980123"));
//		System.out.println("7890"+rule_abcd("18065987890"));
//		System.out.println("8901"+rule_abcd("18065988901"));
//		System.out.println("0987"+rule_abcd("18065980987"));
//		System.out.println("2109"+rule_abcd("18065982109"));
//		System.out.println("13213238975".substring(0,6));
//			System.out.println("1861115658".indexOf("111"));
//			System.out.println(rule_aaa("1861115658"));
//			System.out.println(rule_aabb("18611226588"));
//			System.out.println(rule_abcd("18612346588"));
//			System.out.println(rule_abc("18612245678"));
//			System.out.println(rule_aaba("18611211121"));
//			System.out.println(rule_ABABCAA("1868678845"));
//			System.out.println(rule_abcab("18686786952"));
//			System.out.println(rule_aabaa("18611511256"));
//			System.out.println(rule_abcabc("18612312325"));
//			System.out.println(rule_abcabc("18612312325"));
//			System.out.println(phoNeRuleNumFive("186112312125"));
//			System.out.println("18612346588".indexOf("1234"));
//			System.out.println("18612246123".indexOf("123"));
			System.out.println("53262719860220003x".substring(6, 10));
			  Calendar cal = Calendar.getInstance();
		        int year = cal.get(Calendar.YEAR);
		        System.out.println(year);
		        System.out.println("18628019863".substring(3, 11));
	}

}
