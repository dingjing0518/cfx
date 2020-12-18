/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsGoods;
import cn.cf.dto.B2bEconomicsGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsGoodsDao {
	int insert(B2bEconomicsGoods model);
	int update(B2bEconomicsGoods model);
	int delete(String id);
	List<B2bEconomicsGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsGoodsDto getByPk(java.lang.String pk); 
	 B2bEconomicsGoodsDto getByName(java.lang.String name); 
	 B2bEconomicsGoodsDto getByUrl(java.lang.String url); 
	 B2bEconomicsGoodsDto getByQualityValue(java.lang.String qualityValue); 

}
