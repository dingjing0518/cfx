package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.model.B2bCustomerSalesman;

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
	
	/**
	 * 根据业务员是否存在对应的采购商
	 * @param purchaserPk
	 * @param memberPk
	 * @return
	 */
	Boolean isEmployee(String purchaserPk,String memberPk);

	/**
	 * 分配业务员 ：是否可匹配该类型
	 * @return false 不可匹配
	 */
	boolean  isCanSuit(B2bCustomerSalesmanDto cus);
	/**
	 * 分配业务员 ： 采购商与品名是否已匹配
	 * @param cus
	 * @return true 已匹配
	 */
	boolean isRepeat(B2bCustomerSalesmanDto cus);

	RestCode insertSaleman(B2bCustomerSalesmanDto cus);

	PageModel<B2bCustomerSalesmanDto> searchCusSalesManList(Map<String, Object> map);

	RestCode deleteCusSalesman(String customerPk);
	
	/**
	 * 业务员商品列表：业务员匹配的商品
	 * @param memberPk
	 * @return
	 */
	Map<String, Object> getGoodsByMember(Map<String, Object> map);
	/**
	 * 删除业务员采购商绑定
	 * @param pk
	 */
	void deleteByMemberPk(String memberPk);
	/**
	 * 
	 * @param dto
	 * @return
	 */
	RestCode addCustomerManagement(B2bCustomerManagementDto dto);
	
	void insertOrUpdateSaleMan(B2bCustomerSalesman b2bCustomerSalesman);

}
