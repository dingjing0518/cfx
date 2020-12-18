package cn.cf.service.platform;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.CreditInfo;


public interface B2bCreditService {
	
	B2bCreditDto getCredit(String companyPk,Integer creditStatus);
	
	List<B2bCreditDto> searchCreditList();
	
	@Transactional
	int addCredit(B2bCreditDtoEx b2bcredit,B2bCompanyDto company);
	
	int updateByCompany(B2bCreditDtoEx b2bcredit);
	
	/**
	 * 更新银行客户号
	 * @param companyName
	 */
	void updateCreditAudit(String companyPk,CreditInfo info);
}
