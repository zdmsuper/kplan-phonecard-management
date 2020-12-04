package com.kplan.phonecard.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SendSmsUtils {
    private static final Logger logger = LoggerFactory.getLogger(SendSmsUtils.class);

    /**
     * 第三方短信发送
     */
    public static void sendAgentSms(String mobile, String copyLabel, String agent, JSONObject data) {
        String url = "http://sms.label.terabyte.com.cn/rest/gateway?appKey=bc1673489dcbd008&sign=<SIGN>";
        JSONObject joHead = new JSONObject();
        joHead.put("action", "agent");
        joHead.put("method", "send");
        joHead.put("module", "sms");
        JSONObject joBody = new JSONObject();
        joBody.put("copyLabel", copyLabel);
        joBody.put("mobile", mobile);
        joBody.put("agent", agent);
        joBody.put("data", data);
        JSONObject jo = new JSONObject();
        jo.put("body", joBody);
        jo.put("head", joHead);
        logger.info("sendAgenSms-post,url:{},jo:{}", url, jo.toJSONString());
        String resposne = null;
        try {
            resposne = OkHttp3Utils.postJson(url.replace("<SIGN>", MD5Utils.getMD5String(jo.toJSONString() + "4BC4BC96A9802DDE3A3D5674DB966E5D")), jo.toJSONString(), 6000);
        } catch (IOException e) {
            logger.error("sendAgentSms-ex:{}", e.getMessage());
        }
        logger.info("sendAgenSms-return:{}", resposne);  logger.info("jo.toJSONString() :{}" , jo.toJSONString());
    }
    
    /**
     * @param phone订购号码
     * @param phone_num 接受短信号码
     */
    public static void senMsg(String phone,String userId,String userName) {
    	String sex=nameSex(userName,userId);
    	String userIdNo=userId.substring(14, 18);
    	JSONObject data = new JSONObject();
    	data.put("code1",sex);
    	data.put("code2",userIdNo);
    	sendAgentSms(phone, "多次联系不上", "众成", data);
    }
    
    public static String nameSex(String userName,String userId) {
    	String name=userName.substring(0, 1);
    	if(userId.trim().length()==18) {
    		if(Integer.parseInt(userId.substring(16, 17))%2!=0) {
    			name=name+"先生";
    		}else {
    			name=name+"女士";
    		}
    	}
    	return name;
    }
    
    public static void main(String[] args) {
//    	JSONObject data = new JSONObject();
//    	data.put("code1","温先生");
//    	data.put("code2","003X");
//    	sendAgentSms("18683571285", "多次联系不上", "众成", data);
//    	System.out.println("张德明".substring(0,1));
//    	Integer num=Integer.parseInt()%2!=0;
    	System.out.println("53262719860220003X".substring(14, 18));
    	
	}
}
