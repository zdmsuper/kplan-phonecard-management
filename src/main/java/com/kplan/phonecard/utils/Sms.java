package com.kplan.phonecard.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sms {

	private static final Logger logger = LoggerFactory.getLogger(Sms.class);
	private static final String CorpID="CDJS002929";//账户名
	private static final String Pwd="zm0513@";//密码
	
	/**
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	public static int send(String mobile, String content) throws UnsupportedEncodingException, MalformedURLException {
		URL url = null;
		String send_content = URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");//发送内容
		url = new URL("https://sdk2.028lk.com/sdk2/BatchSend2.aspx?CorpID="+CorpID+"&Pwd="+Pwd+"&Mobile="+mobile+"&Content="+send_content+"&Cell=&SendTime=");
		BufferedReader in = null;
		int inputLine = 0;
		try {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			inputLine = new Integer(in.readLine()).intValue();
		} catch (Exception e) {
			logger.error("短息发送失败  " + e.getMessage());
			inputLine=-2;
		}
		logger.info("结束发送短信返回值：  "+inputLine);
		return inputLine;
	}
	
	/**
	 * 随机产生验证码
	 * @return
	 */
	public static String randomCode() {
		return String.valueOf(Math.random()).substring(2, 8);
	}
	
	/**
	 * 发送验证码
	 * @param mobile
	 * @return
	 */
	public static int sendCode(String mobile) {
		String code = randomCode();
		int r = -1;
		try {
			r = send(mobile, code + "请在5分钟内使用！【盟客多多】");
		} catch (Exception e) {
			logger.error("短息发送失败");
			return -1;
		}
		
		if(r < 0) return r;
		return Integer.valueOf(code);
	}
}
