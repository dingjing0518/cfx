package cn.cf.service.impl;



import org.springframework.stereotype.Service;

import cn.cf.jedis.JedisUtils;
import cn.cf.service.SendSmsService;

@Service
public class SendSmsServiceImpl implements SendSmsService {
	private static final String SMS_CODE = "code";
	 



	@Override
	public boolean checkSmsCode(String mobile, String code) {
		String messageCode = JedisUtils.get(SMS_CODE + mobile) != null ? JedisUtils
				.get(SMS_CODE + mobile).toString() : "";
		if(!messageCode.equals(code)){
			return false;
		}
		return true;
	}


 
 

}
