package cn.cf.service.platform;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bEconomicsBankCompanyDto;

public interface B2bEconomicsBankCompanyService {
	/**
	 * 根据公司查询授信额度信息
	 * @param companyPk
	 * @return
	 */
	List<B2bEconomicsBankCompanyDto> searchList(String companyPk);
	
	/**
	 * 更新授信额度
	 */
	@Transactional
	void updateCredit(String companyPk,String bankPk, List<B2bEconomicsBankCompanyDto> list);
	/**
	 * 查询银行额度
	 * @param companyPk
	 * @param bankPk
	 * @param goodsType
	 * @return
	 */
	B2bEconomicsBankCompanyDto searchGoods(String companyPk,String bankPk,Integer goodsType);
	/**
	 * 删除银行3个月以前数据
	 * @param companyPk
	 * @param bankPk
	 * @param goodsType
	 * @return
	 */
	void deleteBankInfo();
}
