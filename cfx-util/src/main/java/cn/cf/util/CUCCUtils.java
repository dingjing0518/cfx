package cn.cf.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.cf.http.HttpHelper;

public class CUCCUtils {
	
	public  static final String smsUrl = "https://api.ums86.com:9600/sms/Api/Send.do";
	public  static final String SpCode = "249254";
	public  static final String LoginName = "sz_xldz";
	public  static final String Password = "xldz12345";
	
	private static final Logger logger = LoggerFactory.getLogger(CUCCUtils.class);
	
	public static String sendMessage(String mobile,String message){
		Map<String,String> map = new  HashMap<String,String>();
		map.put("SpCode", SpCode);
		map.put("LoginName", LoginName);
		map.put("Password", Password);
		map.put("MessageContent", message);
		map.put("UserNumber", mobile);
		map.put("SerialNumber", DateUtil.formatYearMonthDayHMS(new Date()));
		map.put("ScheduleTime", "");
		map.put("f", "1");
		String rest  =  HttpHelper.doPost(smsUrl, map,"gbk");
		logger.info(rest);
		return rest;
	}
}
