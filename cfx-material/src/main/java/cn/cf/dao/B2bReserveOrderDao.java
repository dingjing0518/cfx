/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bReserveOrder;
import cn.cf.dto.B2bReserveOrderDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bReserveOrderDao {
	int insert(B2bReserveOrder model);
	int update(B2bReserveOrder model);
	int delete(String id);
	List<B2bReserveOrderDto> searchGrid(Map<String, Object> map);
	List<B2bReserveOrderDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bReserveOrderDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bReserveOrderDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bReserveOrderDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bReserveOrderDto getByMeno(java.lang.String meno); 
	 B2bReserveOrderDto getByDeliverDetails(java.lang.String deliverDetails); 
	 B2bReserveOrderDto getByAddressJson(java.lang.String addressJson); 
	 B2bReserveOrderDto getByMemberPk(java.lang.String memberPk); 
	 B2bReserveOrderDto getByMemberName(java.lang.String memberName); 
	 B2bReserveOrderDto getByStoreName(java.lang.String storeName); 
	 B2bReserveOrderDto getByStorePk(java.lang.String storePk); 
	 B2bReserveOrderDto getByLogisticsModelPk(java.lang.String logisticsModelPk); 
	 B2bReserveOrderDto getByLogisticsModelName(java.lang.String logisticsModelName); 
	 B2bReserveOrderDto getByInvoiceJson(java.lang.String invoiceJson); 
	 B2bReserveOrderDto getByPurchaserMobile(java.lang.String purchaserMobile); 
	 B2bReserveOrderDto getByGoodsJson(java.lang.String goodsJson); 

}
