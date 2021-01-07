package com.kplan.phonecard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;



public class DateUtils {
	
	public static String getDate12() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 12);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	/**yyyyMMddHHmm
	 * @return
	 */
	public static String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        Random random=new Random();
//        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 12);
        String time = sdf.format(calendar.getTime());
        time=time+random.nextInt(3);
       return time;
	}
	
	
	public static String getDate24() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 24);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	
	/**yyyy-MM-dd
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayNumT(int num) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - num);
        String time = sdf.format(calendar.getTime());
       return sdf.parse(time);
	}
	
	/**获取前几分钟
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static String getBeforeDayNumMint(int num) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式化
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE, - num);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	public static Date getDayNumT(Date Tdate,int num) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=Tdate;
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - num);
        String time = sdf.format(calendar.getTime());
       return sdf.parse(time);
	}
	
	public static Date getDayNum(int num) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - num*24);
        String time = sdf.format(calendar.getTime());
       return sdf.parse(time);
	}
	
	public static String getDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
//        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 24);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	/**yyyy-MM-dd 昨天
	 * @return
	 */
	public static String getyesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 24);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	/**7天 根据天获取时间
	 * @return
	 */
	public static String getSevenDay(int dayNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 24*dayNum);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	public static String getSevenDayT(Date Tdate,int dayNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=Tdate;
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 24*dayNum);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	/**
	 * @return yyyy-MM-dd 当天
	 */
	public static String getoDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	public static String getDate48() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式化
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 48);
        String time = sdf.format(calendar.getTime());
       return time;
	}
	
	
}
