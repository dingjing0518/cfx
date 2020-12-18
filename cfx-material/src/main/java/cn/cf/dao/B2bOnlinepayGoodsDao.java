/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bOnlinepayGoods;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOnlinepayGoodsDao {
	int insert(B2bOnlinepayGoods model);
	int update(B2bOnlinepayGoods model);
	int delete(String id);
	List<B2bOnlinepayGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bOnlinepayGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bOnlinepayGoodsDto getByPk(java.lang.String pk); 
	 B2bOnlinepayGoodsDto getByName(java.lang.String name); 
	 B2bOnlinepayGoodsDto getByShotName(java.lang.String shotName); 
	 B2bOnlinepayGoodsDto getByBankPk(java.lang.String bankPk); 
	 B2bOnlinepayGoodsDto getByBankName(java.lang.String bankName); 

}
