/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bBindGoods;
import cn.cf.dto.B2bBindGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBindGoodsDao {
	int insert(B2bBindGoods model);
	int update(B2bBindGoods model);
	int delete(String id);
	List<B2bBindGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bBindGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBindGoodsDto getByPk(java.lang.String pk); 
	 List<B2bBindGoodsDto>  getByBindPk(java.lang.String bindPk); 
	 B2bBindGoodsDto getByGoodsPk(java.lang.String goodsPk); 

}
