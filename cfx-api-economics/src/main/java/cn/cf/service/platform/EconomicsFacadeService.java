package cn.cf.service.platform;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.dto.B2bMemberDto;

public interface EconomicsFacadeService {
	/**
	 * 白条申请接口
	 * @param credit
	 * @param company
	 * @param member
	 * @return
	 */
	String applicationLous(B2bCreditDtoEx credit,B2bCompanyDto company,B2bMemberDto member);
	
	/**
	 * 白条信息查询
	 * @param company
	 * @return
	 */
	String searchLous(B2bCompanyDto company);
	
	/**
	 * 白条信息查询
	 * @param company
	 * @return
	 */
	String searchCredit(B2bCompanyDto company);
	
	/**
	 * 修改白条支付密码
	 * @param companyPk
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	String updatePayPassword(String companyPk,String oldPassword,String newPassword);
	
	/**
	 * 根据手机验证码修改白条信息
	 * @param b2bCreditDtoEx
	 * @param code
	 * @return
	 */
	String updateLous(B2bCreditDtoEx b2bCreditDtoEx,String code);
	
}
