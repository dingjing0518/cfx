package cn.cf.service.platform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.constant.SmsCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.dto.B2bEconomicsCreditcustomerDto;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.jedis.JedisUtils;
import cn.cf.service.CuccSmsService;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.foreign.B2bMemberService;
import cn.cf.service.platform.B2bCreditGoodsService;
import cn.cf.service.platform.B2bCreditService;
import cn.cf.service.platform.B2bEconomicsCreditCustomerService;
import cn.cf.service.platform.B2bEconomicsCustomerService;
import cn.cf.service.platform.EconomicsFacadeService;

@Service
public class EconomicsFacadeServiceImpl implements EconomicsFacadeService {
	
	@Autowired
	private B2bCreditService creditService;
	
	@Autowired
	private CuccSmsService  commonSmsService;
	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	
	@Autowired
	private B2bCreditGoodsService b2bCreditGoodsService;
	
	@Autowired
	private B2bEconomicsCustomerService b2bEconomicsCustomerService;
	
	@Autowired
	private B2bEconomicsCreditCustomerService b2bEconomicsCreditCustomerService; 
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SMS_CODE = "code";
	
	@Override
	public String applicationLous(B2bCreditDtoEx credit,
			B2bCompanyDto company, B2bMemberDto member) {
		String rest = RestCode.CODE_0000.toJson();
		try {
			
			//不能邀请自己的公司下的人员
			if(null != credit.getBeInvitedCode()){
				Map<String,Object> serMembersMap = new HashMap<String,Object>();
				serMembersMap.put("companyPk", member.getCompanyPk());
				serMembersMap.put("isDelete", "1");
				serMembersMap.put("isVisable", "1");
				List<B2bMemberDto> tempMembers=b2bMemberService.searchList(serMembersMap);
				for (B2bMemberDto b2bMemberDto : tempMembers) {
					if (credit.getBeInvitedCode().equals(b2bMemberDto.getInvitationCode())) {
						rest=RestCode.CODE_A003.toJson();
						return rest;
					}
				}
			}
			
			int result = creditService.addCredit(credit, company);
			//发送短信
			if(1 == result){
				try {
				 
					Map<String, String> smsMap = new HashMap<String, String>();
					smsMap.put("uname", company.getName());
					commonSmsService.sendMSM(member, member.getMobile(),  SmsCode.AD_CREDIT.getValue(), smsMap);
				} catch (Exception e) {
					logger.error("errorSmsCreditApply",e);
				}
				 
			}
		} catch (Exception e) {
			rest = RestCode.CODE_S999.toJson();
			logger.error("errorApplicationLous",e);
		}
		return rest;
	}

	@Override
	public String searchLous(B2bCompanyDto company) {
		
	 	B2bCreditDto credit =  creditService.getCredit(company.getPk(), null);
	 	B2bCreditDtoEx dtoEx = new B2bCreditDtoEx();
	 	if(null != credit){
	 		dtoEx.UpdateMine(credit);
		}
	 	List<B2bCreditGoodsDtoMa> list = b2bCreditGoodsService.searchAllCreditGoodsByCompany(company.getPk());
	 	if(null != list && list.size() > 0){
	 		dtoEx.setCreditGoods(list);
	 	}
		return RestCode.CODE_0000.toJson(dtoEx);
	
	}

	@Override
	public String updatePayPassword(String companyPk, String oldPassword,
			String newPassword) {
		RestCode code = RestCode.CODE_0000;
		B2bCreditDto credit = creditService.getCredit(companyPk, null);
		if(null == credit){
			code = RestCode.CODE_C0013;
		}else{
			if(null != oldPassword && !oldPassword.equals(credit.getVirtualPayPassword())){
				code = RestCode.CODE_C007;
			}else{
				B2bCreditDtoEx b2bcredit = new B2bCreditDtoEx();
				b2bcredit.setCompanyPk(companyPk);
				b2bcredit.setVirtualPayPassword(newPassword);
				creditService.updateByCompany(b2bcredit);
			}
		}	
		return code.toJson();
	}

	@Override
	public String updateLous(B2bCreditDtoEx b2bCreditDtoEx, String code) {
		RestCode restCode = RestCode.CODE_0000;
		String mobile = b2bCreditDtoEx.getCreditContactsTel();
		if(null == mobile || "".equals(mobile)){
			B2bCreditDto credit = creditService.getCredit(b2bCreditDtoEx.getCompanyPk(), null);
			mobile = credit.getCreditContactsTel();
		}
		String messageCode = JedisUtils.get(SMS_CODE + mobile) != null ? JedisUtils
				.get(SMS_CODE + mobile).toString() : "";
		if(!code.equals(messageCode)){
			restCode = RestCode.CODE_S004;
		}else{
			creditService.updateByCompany(b2bCreditDtoEx);
		}		
		return restCode.toJson();
	}

	@Override
	public String searchCredit(B2bCompanyDto company) {
		B2bCreditDtoEx dtoEx = new B2bCreditDtoEx();
		//查询客户是否是已申请的客户
		B2bEconomicsCustomerDto customer = b2bEconomicsCustomerService.getEconomicsCustomer(company.getPk());
	 	Integer status = null;//未申请
	 	if(null != customer){
	 		dtoEx.setCreditContacts(customer.getContacts());
	 		dtoEx.setCreditContactsTel(customer.getContactsTel());
	 		dtoEx.setCreditAuditTime(customer.getUpdateTime());
	 		dtoEx.setIsAmount(1);//未授额
	 		if(customer.getAuditStatus() == 3){
	 			B2bCreditDto creditDto = creditService.getCredit(company.getPk(), null);
	 			if(null != creditDto){
	 				if(creditDto.getCreditStatus()== 2){
	 					status = 2;//审核通过
	 					List<B2bCreditGoodsDtoMa> list = b2bCreditGoodsService.searchCreditGoodsByCompany(company.getPk());
	 					if(null != list && list.size() > 0){
	 						dtoEx.setIsAmount(2);//已授额
							for(B2bCreditGoodsDtoMa g : list){
								g.setCreditAuditTime(dtoEx.getCreditAuditTime());
							}
	 						dtoEx.setCreditGoods(list);
	 					}
	 				}else if(creditDto.getCreditStatus()== 3){
	 					status = 3;//审核失败
	 				}else{
	 					status = 1;//审核中
	 				}
	 			}else{
	 				status = 1;//审核中
	 			}
	 		}else if(customer.getAuditStatus() == 4){
	 			status = 3;//审核失败
	 		}else if(customer.getAuditStatus() == 2){
	 			status = 1;//审核中
	 		}else{
	 			status = 0;//资料收集中
	 		}
		}
	 	//查询客户有没有申请记录(有申请记录并且数据待受理的一律属于待审核状态)
	    List<B2bEconomicsCreditcustomerDto> customerList = b2bEconomicsCreditCustomerService.searchCustomerList(company.getPk(), 1,customer);
	 	if(null != customerList && customerList.size() >0){
	 		status = 0;
	 	}
	 	dtoEx.setCreditStatus(status);
		return RestCode.CODE_0000.toJson(dtoEx);
	
	}

}
