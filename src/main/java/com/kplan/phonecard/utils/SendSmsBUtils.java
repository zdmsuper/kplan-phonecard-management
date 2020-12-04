package com.kplan.phonecard.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class SendSmsBUtils {
	private static final Logger logger = LoggerFactory.getLogger(SendSmsBUtils.class);
    public static String[] sendSms(String phone, String provinceCode, String content, String spCode) throws Exception {
        String url = "http://sms.operate.terabyte.com.cn/SMS_API/sms/send?appKey={APP_KEY}&data={DATA}";
        String appKey = "bc1673489dcbd008";
        String appSerect = "4BC4BC96A9802DDE3A3D5674DB966E5D";
        String orderNo = phone + System.currentTimeMillis() + "";
        String level = "NO1";
        //sp 编码:为空，则采用不忙通道发送(针对第4级)
        //sp number 子编码:保留
        String spNumberSub = "";
        //短信类型
        String type = "sms";
        //附加数据:保留
        JSONObject attachData = null;
        JSONObject jo = new JSONObject();
        jo.put("orderNo", orderNo);
        jo.put("phone", phone);
        jo.put("content", content);
        jo.put("level", level);
        jo.put("provinceCode", provinceCode);
        jo.put("spCode", spCode);
        jo.put("spNumberSub", spNumberSub);
        jo.put("type", type);
        jo.put("attachData", attachData);
        logger.info(phone+"发送短信:" + jo.toJSONString());
        AesUtils aesUtils = new AesUtils(appSerect);
        String value = aesUtils.encrypt(jo.toJSONString());
        String[] strs = HttpUtils.doGet(url.replace("{APP_KEY}", appKey).replace("{DATA}", value), 10000);
        logger.info("strs[0] = " + strs[0] + ",strs[1]" + strs[1]);
        return strs;
    }
    
    public static boolean qryMobile(String phoneNum) {
    	String url="http://apis.juhe.cn/mobile/get?phone="+phoneNum+"&key=744c42e9f257df979dbf0c21a39c5db4";
    	String[] result=HttpUtils.doGet(url, 60000);
    	if(result.length!=0&&"SUCCEESS".equals(result[0])) {
    		 JSONObject jo = JSONObject.parseObject(result[1]);
    		 JSONObject resultJson=jo.getJSONObject("result");
    		 String company=resultJson.getString("company");
    		 String province=resultJson.getString("province");
    		 if("联通".equals(company)&&"四川".equals(province)) {
    			 return true;
    		 }
    		
    	}
    	return false;
    }

    public static void main(String[] args)  {
    	qryMobile("18628019863");
    	try {
    		sendSms("18628019863", "sccu", "测试短信", "sccu999");
		} catch (Exception e) {
		e.printStackTrace();
		}
    	
	}
}
