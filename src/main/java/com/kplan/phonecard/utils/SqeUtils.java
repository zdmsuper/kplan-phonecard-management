package com.kplan.phonecard.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SqeUtils {
	private static int Sequence=0;
	
	/**生成25位流水号渠道名+时间戳+随机数
	 * @return
	 */
	public static String getSqeNo() {
		String timestamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append("TZJ").append(timestamp);
		Random random=new Random();
		for(int i=0;i<8;i++){
		str.append(random.nextInt(10));
		}
		return str.toString();
	}
	
	public static String getSqeNoC() {
		String timestamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append("C").append(timestamp);
		Random random=new Random();
		for(int i=0;i<8;i++){
		str.append(random.nextInt(10));
		}
		return str.toString();
	}
	public static String getBILIBILISqeNo() {
		String timestamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append(timestamp);
		Random random=new Random();
		for(int i=0;i<8;i++){
		str.append(random.nextInt(5));
		}
		return str.toString();
	}
	
	public static String getExterNal() {
		String timestamp =new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append(timestamp);
		Random random=new Random();
		for(int i=0;i<3;i++){
		str.append(random.nextInt(3));
		}
		return str.toString();
	}
	
	public static String getBILIBILISqeNo(String title) {
		String timestamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append(title).append(timestamp);
		Random random=new Random();
		for(int i=0;i<8;i++){
		str.append(random.nextInt(5));
		}
		return str.toString();
	}
	
	public static int getDataAndTime() {
		 int min = 1000;
		 int max = 30000;
		 System.out.println(new Random().nextInt(max-min)+min);
		 int dataTime=0;
		 dataTime= new Random().nextInt(max-min)+min;
		 return dataTime;
	         
		
	}
			
			/**
			 * 取17位序列号
			 * @return
			 */
			public synchronized static String getSequence(){
				Sequence++;
				if(Sequence==10000){
					Sequence=1;
				}
				String str=String.valueOf(Sequence);
				while(str.length()<4){
					str="0"+str;
				}
				return Calendar.getInstance().getTimeInMillis()+str;
			}
			public static void main(String[] args) {
				System.out.println(getExterNal());
			}
}
