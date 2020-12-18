package cn.cf.service.impl;

import cn.cf.constant.RestCode;
import cn.cf.constant.SmsCode;
import cn.cf.dao.CommonDao;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.entity.SendMails;
import cn.cf.entity.Sms;
import cn.cf.jedis.JedisUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.CuccSmsService;
import cn.cf.util.CUCCUtils;
import cn.cf.util.DateUtil;
import cn.cf.util.EncodeUtil;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CuccSmsServiceImpl implements CuccSmsService {
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	
	
	@Override
	public void sendMessage(String memberPk, String companyPk,
			boolean isAdmin, String smsCode, Map<String, String> sendMap) {
		B2bMemberDto member=commonDao.getMemberByPk(memberPk);
		if(null!=companyPk&&!"".equals(companyPk)){
			SysSmsTemplateDto smsDto= commonDao.getTemplateByName(smsCode);
			//若模板 已配置角色走此步骤
			List<B2bMemberDto> memberList = new ArrayList<B2bMemberDto>();
			if(null!=smsDto && 2 == smsDto.getFlag()){
				Map<String, String> roleMap = new HashMap<String,String>();
				roleMap.put("name", smsCode);
				roleMap.put("companyPk", companyPk);
				//查询绑定短信模板的角色用户
				 memberList = commonDao.searchMemberBySmsRoleM(roleMap);
				if(null == memberList || memberList.size() == 0){
					//是否发送给超管
					if(isAdmin){
						memberList = commonDao.getAdminM(companyPk);
					//否则发送给当前登录人	
					}
					/*else{
						memberList.add(member);
					}*/
				}
			}else{
				memberList.add(member);
			}	 
			//发送短信
			for(B2bMemberDto m : memberList){
				sendMSM(member, m.getMobile(), smsCode, sendMap);
			}
			
		} 
		
	}
 
	@Override
	public void sendMSM(B2bMemberDto member, String mobile, String smsName,
			Map<String, String> map) {
				//发送短信用户
				B2bMemberDto sendMailMember = null;
				if(null == mobile){
					sendMailMember = member;
				}else{
					sendMailMember = commonDao.getMemberByMobile(mobile);
				}
				if(null!=sendMailMember){
					SysSmsTemplateDto sdto =  commonDao.getTemplateByName(smsName);
					if(sdto!=null){
						if(null != map){
							sdto=getContent(map,sdto);
						}
						sendCUCCMsg(mobile, smsName, sdto.getContent(), sendMailMember.getCompanyPk(), sendMailMember.getCompanyName());
					}
				}
	} 
 
	public SysSmsTemplateDto getContent(Map<String, String> map,SysSmsTemplateDto sdto) {
		String content=sdto.getContent();
		for (String key : map.keySet()) {  
			content=content.replace("${"+key+"}",map.get(key));
		}  
		sdto.setContent(content);
		return sdto;
	}

		@Override
		public String  getCUCCMsgContent(Map<String, String> map,String smsName) {
			SysSmsTemplateDto sdto =  commonDao.getTemplateByName(smsName);
			String content=sdto.getContent();
			for (String key : map.keySet()) {  
				content=content.replace("${"+key+"}",map.get(key));
			}  
			return content;
		}

		
	@Override
	public void sendCUCCMsg(String mobile, String smsCode, String content,
			String companyPk, String companyName) {
		SysSmsTemplateDto smsDto = commonDao.getTemplateByName(smsCode);
		if (PropertyConfig.getBooleanProperty("is_test", true)) {
			if(null !=smsDto ){
				if(smsDto.getIsSms()==1){
					String rest = CUCCUtils.sendMessage(mobile, content);
					Sms sms = new Sms();
					sms.setId(KeyUtils.getUUID());
					sms.setMobile(mobile);
					sms.setTemplateCode(smsDto.getTemplateCode());
					sms.setFreeSignName(smsDto.getFreeSignName());
					sms.setInsertTime(new Date());
					sms.setCompanyPk(companyPk);
					sms.setCompanyName(companyName);
					sms.setContent(content);
					sms.setResult(rest);
					if(null != rest && rest.contains("result=0")){
						sms.setStatus("成功");
					}else{
						sms.setStatus("失败");
					}
					mongoTemplate.save(sms);
				}
				
			
			}
		}
		if(null!=smsDto&&smsDto.getIsMail()==1){//发送邮件
			sendMail( mobile,content,smsDto.getFreeSignName());
		}
	}
	public   void sendMail( String  mobile ,String content,String freeSignName) {
		B2bMemberDto mdto=commonDao.getMemberByMobile(mobile);
		SendMails mail = new SendMails();
		mail.setCompanyPk(mdto.getCompanyPk());
		mail.setCompanyName(mdto.getCompanyName());
		mail.setFreeSignName(freeSignName);
		mail.setContent(content);
		mail.setIsRead(1);// 1未读 2已读
		mail.setMemberPk(mdto.getPk());
		mail.setMobile(mdto.getMobile());
		mail.setId(KeyUtils.getUUID());
		mail.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		mongoTemplate.insert(mail);
	}
	
	@Override
	public String sendCuccCode(String mobile) {
		 RestCode code = RestCode.CODE_0000;	
		SysSmsTemplateDto smsDto= commonDao.getTemplateByName(SmsCode.SEND_CODE.getValue());
 	if (PropertyConfig.getBooleanProperty("is_test", true)) {
		 if (null !=smsDto) {
			 String content=smsDto.getContent();
			 String smsCode= EncodeUtil.VerificationCode();
			 content=content.replace("${code}",smsCode);
			 String rest = CUCCUtils.sendMessage(mobile, content);
			 Sms sms = new Sms();
				sms.setId(KeyUtils.getUUID());
				sms.setMobile(mobile);
				sms.setTemplateCode(smsDto.getTemplateCode());
				sms.setFreeSignName(smsDto.getFreeSignName());
				sms.setInsertTime(new Date());
				sms.setContent(content);
				sms.setResult(rest);
				
				sms.setStatus("失败");
				code = RestCode.CODE_S999;
				if(null != rest){
					if( rest.contains("result=0")){
						sms.setStatus("成功");
						JedisUtils.setInvalid("code" + mobile, smsCode, 120);
						code = RestCode.CODE_0000;	 
					} else if(rest.contains("result=32")||rest.contains("result=33")){
						sms.setStatus("失败");
						code = RestCode.CODE_S998;	 
					} 
				}  
				mongoTemplate.save(sms);
		 
			} else {
				code = RestCode.CODE_S999;
			}
	}else{
		JedisUtils.setInvalid("code" + mobile, "123456", 120);
	}
		return code.toJson();
	}

	 
}
