/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgDeliveryOrderGoods;
import cn.cf.dto.LgDeliveryOrderGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgDeliveryOrderGoodsDao {
	int insert(LgDeliveryOrderGoods model);
	int update(LgDeliveryOrderGoods model);
	int delete(String id);
	List<LgDeliveryOrderGoodsDto> searchGrid(Map<String, Object> map);
	List<LgDeliveryOrderGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgDeliveryOrderGoodsDto getByPk(java.lang.String pk); 
	 LgDeliveryOrderGoodsDto getByDeliveryPk(java.lang.String deliveryPk); 
	 LgDeliveryOrderGoodsDto getByGoodsPk(java.lang.String goodsPk); 
	 LgDeliveryOrderGoodsDto getByProductName(java.lang.String productName); 
	 LgDeliveryOrderGoodsDto getByProductPk(java.lang.String productPk); 
	 LgDeliveryOrderGoodsDto getByVarietiesName(java.lang.String varietiesName); 
	 LgDeliveryOrderGoodsDto getBySeedvarietyName(java.lang.String seedvarietyName); 
	 LgDeliveryOrderGoodsDto getBySpecName(java.lang.String specName); 
	 LgDeliveryOrderGoodsDto getBySeriesName(java.lang.String seriesName); 
	 LgDeliveryOrderGoodsDto getByGradeName(java.lang.String gradeName); 
	 LgDeliveryOrderGoodsDto getByGradePk(java.lang.String gradePk); 
	 LgDeliveryOrderGoodsDto getByBatchNumber(java.lang.String batchNumber); 
	 LgDeliveryOrderGoodsDto getByOrderNumber(java.lang.String orderNumber); 
	 LgDeliveryOrderGoodsDto getByGoodsName(java.lang.String goodsName); 

}
