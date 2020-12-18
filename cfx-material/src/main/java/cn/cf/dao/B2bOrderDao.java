/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bOrder;
import cn.cf.dto.B2bOrderDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOrderDao {
	int insert(B2bOrder model);
	int update(B2bOrder model);
	int delete(String id);
	List<B2bOrderDto> searchGrid(Map<String, Object> map);
	List<B2bOrderDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bOrderDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bOrderDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bOrderDto getBySupplierPk(java.lang.String supplierPk); 
	 B2bOrderDto getByMeno(java.lang.String meno); 
	 B2bOrderDto getByMemberPk(java.lang.String memberPk); 
	 B2bOrderDto getByMemberName(java.lang.String memberName); 
	 B2bOrderDto getByStoreName(java.lang.String storeName); 
	 B2bOrderDto getByStorePk(java.lang.String storePk); 
	 B2bOrderDto getByPaymentName(java.lang.String paymentName); 
	 B2bOrderDto getByEconomicsGoodsName(java.lang.String economicsGoodsName); 
	 B2bOrderDto getByLogisticsModelPk(java.lang.String logisticsModelPk); 
	 B2bOrderDto getByLogisticsModelName(java.lang.String logisticsModelName); 
	 B2bOrderDto getByEmployeeNumber(java.lang.String employeeNumber); 
	 B2bOrderDto getByEmployeeName(java.lang.String employeeName); 
	 B2bOrderDto getByEmployeePk(java.lang.String employeePk); 
	 B2bOrderDto getByDeliverDetails(java.lang.String deliverDetails); 
	 B2bOrderDto getByOtherNumber(java.lang.String otherNumber); 
	 B2bOrderDto getByPurchaserInfo(java.lang.String purchaserInfo); 
	 B2bOrderDto getBySupplierInfo(java.lang.String supplierInfo); 
	 B2bOrderDto getByAddressInfo(java.lang.String addressInfo); 
	 B2bOrderDto getByBlock(java.lang.String block); 

}
