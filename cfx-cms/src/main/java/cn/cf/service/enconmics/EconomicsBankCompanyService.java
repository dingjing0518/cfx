package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCreditGoodsExtDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;

public interface EconomicsBankCompanyService {
    /**
     * 根据公司pk查询银行授信额度
     * @param companyPk
     * @return
     */
	B2bEconomicsBankCompanyDto getDtoByCompanyPk(String companyPk);
	/**
	 * 
	 * 编辑客户号
	 * @param companyPk
	 * @param customerNumber
	 * @return
	 */
	int updateCustomerNumber(String companyPk,String customerNumber);
	/**
	 * 银行的可用额度
	 * //TODO 添加方法功能描述
	 * @param map1
	 * @return
	 */
	List<B2bEconomicsBankCompanyExtDto> countAvailableAmount(Map<String, Object> map1);
	/**
	 * 银行审批额度获取昨日最新额度
	 * @param map
	 * @return
	 */
	List<B2bEconomicsBankCompanyExtDto> searchBankApproveAmount(Map<String, Object> map);
	/**
	 * 按月获取银行的授信额度
	 * //TODO 添加方法功能描述
	 * @param map
	 * @return
	 */
	List<B2bEconomicsBankCompanyExtDto> searchAmountByMonth(Map<String, Object> map);
	
	/**
	 * 更新b2b_credit表银行客户号
	 * @param creditPk
	 * @param bankAccountNumber
	 * @return
	 */
	boolean updateBankAccountNumber(B2bCreditGoodsExtDto dto);
}
