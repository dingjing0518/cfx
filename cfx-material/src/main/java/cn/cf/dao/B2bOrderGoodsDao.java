/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bOrderGoods;
import cn.cf.dto.B2bOrderGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOrderGoodsDao {
	int insert(B2bOrderGoods model);
	int update(B2bOrderGoods model);
	int delete(String id);
	List<B2bOrderGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bOrderGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bOrderGoodsDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bOrderGoodsDto getByChildOrderNumber(java.lang.String childOrderNumber); 
	 B2bOrderGoodsDto getByLogisticsPk(java.lang.String logisticsPk); 
	 B2bOrderGoodsDto getByLogisticsStepInfoPk(java.lang.String logisticsStepInfoPk); 
	 B2bOrderGoodsDto getByLogisticsCarrierPk(java.lang.String logisticsCarrierPk); 
	 B2bOrderGoodsDto getByLogisticsCarrierName(java.lang.String logisticsCarrierName); 
	 B2bOrderGoodsDto getByGoodsType(java.lang.String goodsType); 
	 B2bOrderGoodsDto getByGoodsInfo(java.lang.String goodsInfo); 
	 B2bOrderGoodsDto getByChildContractNo(java.lang.String childContractNo); 

}
