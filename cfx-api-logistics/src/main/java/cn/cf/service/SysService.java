/**
 * 
 */
package cn.cf.service;

import java.util.Map;
import java.util.SortedMap;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.SysSmsTemplateDto;

/**
 * @author bin
 * 
 */
public interface SysService {

	/**
	 * 查询token
	 * @param map
	 * @return
	 */
	B2bTokenDto searchToken(Map<String, Object> map);

	SortedMap<Object, Object> mySignByParameters(B2bTokenDto b2btoken);

	RestCode entityHas(Object object) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * map转对象
	 * @param map  map参数
	 * @param beanClass  对象类
	 * @return
	 * @throws Exception
	 */
	Object mapToObject(Map<String, String> map, Class<?> beanClass) throws Exception;

	/**
	 * 查询短信模板
	 * @param templateCode  短信模板code
	 * @return
	 */
	SysSmsTemplateDto getSmsByTemplateCode(String templateCode);

	/**
	 * 查询短信模板
	 * @param string  短信模板名称
	 * @return
	 */
	SysSmsTemplateDto getSmsByName(String string);

	SysSmsTemplateDto getContent(Map<String, String> map, SysSmsTemplateDto sdto);



	
	 
	 

	/**
	 * 发送短信
	 * @param member 物流系统会员
	 * @param smsName  短信模版名称
	 * @param map  参数
	 */
	void sendMessageContent(LgMemberDto member, String smsName, Map<String, String> map);
	
}
