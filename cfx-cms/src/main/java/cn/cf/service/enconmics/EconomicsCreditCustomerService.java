package cn.cf.service.enconmics;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.dto.MemberShip;
import cn.cf.model.B2bEconomicsCreditcustomer;

public interface EconomicsCreditCustomerService {
	/**
	 * 申请客户记录列表
	 * @param qm
	 * @param currentMemberShip
	 * @return
	 */
	PageModel<B2bEconomicsCreditcustomerDtoEx> searchEconCustList(QueryModel<B2bEconomicsCreditcustomerDtoEx> qm,MemberShip currentMemberShip);
	
	/**
	 * 编辑申请记录表
	 * @param customer
	 */
	void updateB2bEconomicsCreditcustomer(B2bEconomicsCreditcustomer customer,boolean isUpdateStatic);
	
	/**
	 * 客户受理
	 * @param customer
	 * @return
	 */
	@Transactional
	String acceptanceCustomer(String pk);


	String insert(B2bEconomicsCreditcustomer model);

	
}
