package cn.cf.service.bill;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillCustomerApplyDto;
import cn.cf.dto.B2bBillCustomerApplyDtoEx;
import cn.cf.dto.B2bCompanyDto;

public interface BillCusGoodsService {
	/**
	 * 根据pk查询客户产品
	 * @param pk
	 * @return
	 */
	B2bBillCusgoodsDto getByPk(String pk);
	/**
	 * 根据产品pk 公司pk查询客户产品
	 * @param goodsk
	 * @param companyPk
	 * @return
	 */
	B2bBillCusgoodsDto getByCompanyPk(String goodsk,String companyPk);
	
	/***
	 * 票付通登录客户申请
	 * @param billCus
	 * @param company
	 * @return
	 */
	String applyForBill(B2bBillCustomerApplyDtoEx billCus,B2bCompanyDto company);
	/**
	 * 获取票付通产品
	 */
	List<B2bBillCusgoodsDtoEx> searchBillCusGoodsByCompany(String companyPk);
	
	List<B2bBillCusgoodsDtoEx> searchBillCusGoodsList(Map<String, Object> map);

	B2bBillCustomerApplyDto searchBillCusApply(B2bCompanyDto company);
	/**
	 * 更新bindstatus
	 * @param map
	 * @param status
	 */
	void updateBindStatus(String companyPk,String shotName,Integer status);
	/**
	 * 更新可用额度
	 */
	void updateUseAmount(B2bBillCusgoodsDto gdto);
	/**
	 * 清空可用额度
	 */
	void cleanUseAmount();
 
}
