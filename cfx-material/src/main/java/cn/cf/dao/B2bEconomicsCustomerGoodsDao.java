/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsCustomerGoods;
import cn.cf.dto.B2bEconomicsCustomerGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsCustomerGoodsDao {
	int insert(B2bEconomicsCustomerGoods model);
	int update(B2bEconomicsCustomerGoods model);
	int delete(String id);
	List<B2bEconomicsCustomerGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsCustomerGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsCustomerGoodsDto getByPk(java.lang.String pk); 
	 B2bEconomicsCustomerGoodsDto getByEconomicsGoodsPk(java.lang.String economicsGoodsPk); 
	 B2bEconomicsCustomerGoodsDto getByEconomicsGoodsName(java.lang.String economicsGoodsName); 
	 B2bEconomicsCustomerGoodsDto getByBankPk(java.lang.String bankPk); 
	 B2bEconomicsCustomerGoodsDto getByBankName(java.lang.String bankName); 
	 B2bEconomicsCustomerGoodsDto getByEconomicsCustomerPk(java.lang.String economicsCustomerPk); 

}
