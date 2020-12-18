/**
 * 
 */
package cn.cf.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.dao.B2bTokenDao;
import cn.cf.dao.LgMemberDaoEx;
import cn.cf.dao.SysSmsTemplateDao;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.service.CommonService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.SysService;

/**
 * @author bin
 * 
 */
@Service
public class SysServiceImpl implements SysService {

	@Autowired
	private B2bTokenDao b2bTokenDao;

	@Autowired
	private SysSmsTemplateDao sysSmsTemplateDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private LgMemberDaoEx lgMemberDaoExt;
	
	@Autowired
	private CuccSmsService commonSmsService;
	@Override
	public B2bTokenDto searchToken(Map<String, Object> map) {
		List<B2bTokenDto> list = b2bTokenDao.searchGrid(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public SortedMap<Object, Object> mySignByParameters(B2bTokenDto b2btoken) {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appId", b2btoken.getAppId());
		parameters.put("appSecret", b2btoken.getAppSecret());
		parameters.put("pk", b2btoken.getPk());
		parameters.put("storePk", b2btoken.getStorePk());
		parameters.put("storeName", b2btoken.getStoreName());
		return parameters;
	}

	@Override
	public RestCode entityHas(Object object) {
		Field[] flds = object.getClass().getDeclaredFields();
		if (flds != null) {
			for (int i = 0; i < flds.length; i++) {
				flds[i].setAccessible(true);
				// String name = flds[i].getName();
				Object value;
				try {
					value = flds[i].get(object);
				} catch (Exception e) {
					e.printStackTrace();
					return RestCode.CODE_A004;
				}
				if ("".equals(value) || value == null) {
					return RestCode.CODE_A001;
				}

			}
		}
		return RestCode.CODE_S999;
	}

	public Object mapToObject(Map<String, String> map, Class<?> beanClass) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = beanClass.newInstance();
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}

	@Override
	public SysSmsTemplateDto getSmsByTemplateCode(String templateCode) {
		return sysSmsTemplateDao.getByTemplateCode(templateCode);
	}

	@Override
	public SysSmsTemplateDto getSmsByName(String string) {
		return sysSmsTemplateDao.getByName(string);
	}

	/*
	 * @Override public void userAudit(B2bMemberDto dto) { SysSmsTemplateDto
	 * sdto =getSmsByName("user_audit");//提醒用户审核中 JSONObject js = new
	 * JSONObject(); js.put("uname", dto.getCompanyName());
	 * sdto.setContent(js.toString()); if(sdto.getIsMail()==1){ String
	 * content=sdto.getContentStr(); content=content.replace("${uname}",
	 * dto.getCompanyName()); sdto.setContentStr(content); } SmsUtils.send(sdto,
	 * dto, mongoTemplate);
	 * 
	 * }
	 */
	// 模板没有变量时

	@Override
	public SysSmsTemplateDto getContent(Map<String, String> map, SysSmsTemplateDto sdto) {
		String content = sdto.getContent();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			content = content.replace("${" + entry.getKey() + "}", entry.getValue());
		}
		sdto.setContent(content);
		return sdto;
	}

	 
	/**
	*
	*/
	@Override
	public void sendMessageContent(LgMemberDto member, String smsName, Map<String, String> map) {
		LgMemberDto dto = lgMemberDaoExt.getByMobile(member.getMobile());
		SysSmsTemplateDto sdto = getSmsByName(smsName);
		if (sdto != null) {
			if (map.size() > 0) {
				sdto = getContent(map, sdto);
			}
			commonSmsService.sendCUCCMsg(member.getMobile(), smsName, sdto.getContent(), dto.getCompanyPk(), dto.getCompanyName());
		}
	}

	 
 

 

}
