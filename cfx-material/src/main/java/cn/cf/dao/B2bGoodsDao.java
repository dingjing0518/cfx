/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bGoods;
import cn.cf.dto.B2bGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bGoodsDao {
	int insert(B2bGoods model);
	int update(B2bGoods model);
	int delete(String id);
	List<B2bGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bGoodsDto getByPk(java.lang.String pk); 
	 B2bGoodsDto getByStorePk(java.lang.String storePk); 
	 B2bGoodsDto getByStoreName(java.lang.String storeName); 
	 B2bGoodsDto getByCompanyPk(java.lang.String companyPk); 
	 B2bGoodsDto getByCompanyName(java.lang.String companyName); 
	 B2bGoodsDto getByBrandPk(java.lang.String brandPk); 
	 B2bGoodsDto getByBrandName(java.lang.String brandName); 
	 B2bGoodsDto getByType(java.lang.String type); 
	 B2bGoodsDto getByBlock(java.lang.String block); 
	 B2bGoodsDto getByGoodsInfo(java.lang.String goodsInfo); 
	 B2bGoodsDto getByPk1(java.lang.String pk1); 
	 B2bGoodsDto getByPk2(java.lang.String pk2); 
	 B2bGoodsDto getByPk3(java.lang.String pk3); 
	 B2bGoodsDto getByPk4(java.lang.String pk4); 

}
