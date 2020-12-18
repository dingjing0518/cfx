package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bCreditDtoEx;

public interface B2bCreditDaoEx extends B2bCreditDao{

	int updateByCompany(B2bCreditDtoEx credit);

	List<B2bCreditDtoEx> getCreditExpirationReminder();
	


}
