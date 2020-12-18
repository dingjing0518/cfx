package cn.cf.service;

/**
 * 短信业务层
 * @description:
 * @author FJM
 * @date 2018-4-12 下午1:58:43
 */
public interface SendSmsService {
	 	
	/**
	    * 校验短信验证码
	    * @param mobile
	    * @param code
	    * @return
	    */
	boolean checkSmsCode(String mobile,String code);
 
}
