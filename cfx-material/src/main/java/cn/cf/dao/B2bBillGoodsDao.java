/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillGoods;
import cn.cf.dto.B2bBillGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillGoodsDao {
	int insert(B2bBillGoods model);
	int update(B2bBillGoods model);
	int delete(String id);
	List<B2bBillGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bBillGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillGoodsDto getByPk(java.lang.String pk); 
	 B2bBillGoodsDto getByName(java.lang.String name); 
	 B2bBillGoodsDto getByShotName(java.lang.String shotName); 
	 B2bBillGoodsDto getByBankPk(java.lang.String bankPk); 
	 B2bBillGoodsDto getByBankName(java.lang.String bankName); 
	 B2bBillGoodsDto getByGateway(java.lang.String gateway); 
	 B2bBillGoodsDto getByImgUrl(java.lang.String imgUrl); 

}
