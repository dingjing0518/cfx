/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBindOrder;
import cn.cf.dto.B2bBindOrderDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBindOrderDao {
	int insert(B2bBindOrder model);
	int update(B2bBindOrder model);
	int delete(String id);
	List<B2bBindOrderDto> searchGrid(Map<String, Object> map);
	List<B2bBindOrderDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBindOrderDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bBindOrderDto getByBindPk(java.lang.String bindPk); 
	 B2bBindOrderDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bBindOrderDto getByMeno(java.lang.String meno); 
	 B2bBindOrderDto getByMemberPk(java.lang.String memberPk); 
	 B2bBindOrderDto getByMemberName(java.lang.String memberName); 
	 B2bBindOrderDto getByStoreName(java.lang.String storeName); 
	 B2bBindOrderDto getByStorePk(java.lang.String storePk); 
	 B2bBindOrderDto getByLogisticsModelPk(java.lang.String logisticsModelPk); 
	 B2bBindOrderDto getByLogisticsModelName(java.lang.String logisticsModelName); 
	 B2bBindOrderDto getByBindProductId(java.lang.String bindProductId); 
	 B2bBindOrderDto getByPurchaserInfo(java.lang.String purchaserInfo); 
	 B2bBindOrderDto getByAddressInfo(java.lang.String addressInfo); 
	 B2bBindOrderDto getByGoodsJson(java.lang.String goodsJson); 

}
