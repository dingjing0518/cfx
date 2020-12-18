/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillCusgoodsApply;
import cn.cf.dto.B2bBillCusgoodsApplyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCusgoodsApplyDao {
	int insert(B2bBillCusgoodsApply model);
	int update(B2bBillCusgoodsApply model);
	int delete(String id);
	List<B2bBillCusgoodsApplyDto> searchGrid(Map<String, Object> map);
	List<B2bBillCusgoodsApplyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillCusgoodsApplyDto getByPk(java.lang.String pk); 
	 B2bBillCusgoodsApplyDto getByCustomerPk(java.lang.String customerPk); 
	 B2bBillCusgoodsApplyDto getByCompanyPk(java.lang.String companyPk); 
	 B2bBillCusgoodsApplyDto getByGoodsPk(java.lang.String goodsPk); 
	 B2bBillCusgoodsApplyDto getByGoodsName(java.lang.String goodsName); 
	 B2bBillCusgoodsApplyDto getByGoodsShotName(java.lang.String goodsShotName); 

}
