package cn.cf.service;

import cn.cf.dto.B2bMemberDto;

public interface B2bCustomerSaleManService {
	/**
	 * 根据条件查询业务员
	 * @param companyPk 供应商公司
	 * @param purchaserPk 采购商公司
	 * @param productPk   品名
	 * @param storePk    店铺
	 * @return
	 */
	B2bMemberDto getSaleMan(String companyPk, String purchaserPk,String productPk,String storePk);
	
	
	void addCustomerManagement(String purchaserPk,String storePk);
}
