/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillCusgoods;
import cn.cf.dto.B2bBillCusgoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCusgoodsDao {
	int insert(B2bBillCusgoods model);
	int update(B2bBillCusgoods model);
	int delete(String id);
	List<B2bBillCusgoodsDto> searchGrid(Map<String, Object> map);
	List<B2bBillCusgoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillCusgoodsDto getByPk(java.lang.String pk); 
	 B2bBillCusgoodsDto getByCustomerPk(java.lang.String customerPk); 
	 B2bBillCusgoodsDto getByCompanyPk(java.lang.String companyPk); 
	 B2bBillCusgoodsDto getByGoodsPk(java.lang.String goodsPk); 
	 B2bBillCusgoodsDto getByGoodsName(java.lang.String goodsName); 
	 B2bBillCusgoodsDto getByGoodsShotName(java.lang.String goodsShotName); 
	 B2bBillCusgoodsDto getByAccount(java.lang.String account); 
	 B2bBillCusgoodsDto getByBankName(java.lang.String bankName); 
	 B2bBillCusgoodsDto getByBankNo(java.lang.String bankNo); 

}
