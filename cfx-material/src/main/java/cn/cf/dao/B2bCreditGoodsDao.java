/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.model.B2bCreditGoods;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCreditGoodsDao {
	int insert(B2bCreditGoods model);
	int update(B2bCreditGoods model);
	int delete(String id);
	List<B2bCreditGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bCreditGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCreditGoodsDto getByPk(java.lang.String pk); 
	 B2bCreditGoodsDto getByCreditPk(java.lang.String creditPk); 
	 B2bCreditGoodsDto getByCompanyPk(java.lang.String companyPk); 
	 B2bCreditGoodsDto getByCompanyName(java.lang.String companyName); 
	 B2bCreditGoodsDto getByEconomicsGoodsPk(java.lang.String economicsGoodsPk); 
	 B2bCreditGoodsDto getByEconomicsGoodsName(java.lang.String economicsGoodsName); 
	 B2bCreditGoodsDto getByBankPk(java.lang.String bankPk); 
	 B2bCreditGoodsDto getByBank(java.lang.String bank); 
	 B2bCreditGoodsDto getByBankAccountNumber(java.lang.String bankAccountNumber);
	 

}
