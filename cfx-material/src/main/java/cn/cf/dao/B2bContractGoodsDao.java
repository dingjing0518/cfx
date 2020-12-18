/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bContractGoods;
import cn.cf.dto.B2bContractGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bContractGoodsDao {
	int insert(B2bContractGoods model);
	int update(B2bContractGoods model);
	int delete(String id);
	List<B2bContractGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bContractGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bContractGoodsDto getByChildOrderNumber(java.lang.String childOrderNumber); 
	 B2bContractGoodsDto getByContractNo(java.lang.String contractNo); 
	 B2bContractGoodsDto getByBrandName(java.lang.String brandName); 
	 B2bContractGoodsDto getByCarrierPk(java.lang.String carrierPk); 
	 B2bContractGoodsDto getByCarrier(java.lang.String carrier); 
	 B2bContractGoodsDto getByLogisticsPk(java.lang.String logisticsPk); 
	 B2bContractGoodsDto getByLogisticsStepInfoPk(java.lang.String logisticsStepInfoPk); 
	 B2bContractGoodsDto getByGoodsInfo(java.lang.String goodsInfo); 

}
