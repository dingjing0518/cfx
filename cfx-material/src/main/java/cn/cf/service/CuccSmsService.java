package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bMemberDto;


public interface CuccSmsService {
/***
 * 根据角色，查询发送人员
 * @param memberPk 当前操作人
 * @param companyPk 接收方公司PK
 * @param isAdmin 如果没有接收方是否发送给超级管理员
 * @param smsName   name主键
 * @param sendMap   参数
 */
	void sendMessage(String memberPk, String companyPk,boolean isAdmin,
			String smsName, Map<String, String> sendMap);
	
	
	
	/**
	 * 发送个人，不需要根据角色分配发送
	 * @param member 操作人
	 * @param mobile 要发送的手机号
	 * @param smsName    name主键
	 * @param map     参数
	 */
	void sendMSM(B2bMemberDto member,String mobile,String smsName,Map<String,String> map);
	
	/**
	 * 短信发送(新) 
	 * @param mobile
	 * @param smsCode
	 * @param content
	 */
	void sendCUCCMsg(String mobile,String smsCode,String content,String companyPk,String companyName);


/**
 * 联通验证码
 * @param mobile
 * @return
 */
	String sendCuccCode(String mobile);
	
	/**
	 * 拼接短信模板
	 * @param map
	 * @param smsName
	 * @return
	 */

	String getCUCCMsgContent(Map<String, String> smsMap, String smsName);
	
}
