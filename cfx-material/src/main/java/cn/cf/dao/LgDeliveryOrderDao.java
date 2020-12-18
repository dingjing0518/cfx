/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgDeliveryOrder;
import cn.cf.dto.LgDeliveryOrderDto;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgDeliveryOrderDao {
	int insert(LgDeliveryOrder model);
	int update(LgDeliveryOrder model);
	int delete(String id);
	List<LgDeliveryOrderDto> searchGrid(Map<String, Object> map);
	List<LgDeliveryOrderDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgDeliveryOrderDto getByPk(java.lang.String pk); 
	 LgDeliveryOrderDto getByOrderPk(java.lang.String orderPk); 
	 LgDeliveryOrderDto getByParentPk(java.lang.String parentPk); 
	 LgDeliveryOrderDto getByLogisticsCompanyPk(java.lang.String logisticsCompanyPk); 
	 LgDeliveryOrderDto getByLogisticsCompanyName(java.lang.String logisticsCompanyName); 
	 LgDeliveryOrderDto getByFromAddressPk(java.lang.String fromAddressPk); 
	 LgDeliveryOrderDto getByFromCompanyName(java.lang.String fromCompanyName); 
	 LgDeliveryOrderDto getByFromAddress(java.lang.String fromAddress); 
	 LgDeliveryOrderDto getByFromContacts(java.lang.String fromContacts); 
	 LgDeliveryOrderDto getByFromContactsTel(java.lang.String fromContactsTel); 
	 LgDeliveryOrderDto getByFromProvincePk(java.lang.String fromProvincePk); 
	 LgDeliveryOrderDto getByFromProvinceName(java.lang.String fromProvinceName); 
	 LgDeliveryOrderDto getByFromCityPk(java.lang.String fromCityPk); 
	 LgDeliveryOrderDto getByFromCityName(java.lang.String fromCityName); 
	 LgDeliveryOrderDto getByFromAreaPk(java.lang.String fromAreaPk); 
	 LgDeliveryOrderDto getByFromAreaName(java.lang.String fromAreaName); 
	 LgDeliveryOrderDto getByFromTownPk(java.lang.String fromTownPk); 
	 LgDeliveryOrderDto getByFromTownName(java.lang.String fromTownName); 
	 LgDeliveryOrderDto getByToAddressPk(java.lang.String toAddressPk); 
	 LgDeliveryOrderDto getByToCompanyPk(java.lang.String toCompanyPk); 
	 LgDeliveryOrderDto getByToCompanyName(java.lang.String toCompanyName); 
	 LgDeliveryOrderDto getByToAddress(java.lang.String toAddress); 
	 LgDeliveryOrderDto getByToContacts(java.lang.String toContacts); 
	 LgDeliveryOrderDto getByToContactsTel(java.lang.String toContactsTel); 
	 LgDeliveryOrderDto getByToProvincePk(java.lang.String toProvincePk); 
	 LgDeliveryOrderDto getByToProvinceName(java.lang.String toProvinceName); 
	 LgDeliveryOrderDto getByToCityPk(java.lang.String toCityPk); 
	 LgDeliveryOrderDto getByToCityName(java.lang.String toCityName); 
	 LgDeliveryOrderDto getByToAreaPk(java.lang.String toAreaPk); 
	 LgDeliveryOrderDto getByToAreaName(java.lang.String toAreaName); 
	 LgDeliveryOrderDto getByToTownPk(java.lang.String toTownPk); 
	 LgDeliveryOrderDto getByToTownName(java.lang.String toTownName); 
	 LgDeliveryOrderDto getBySupplierInvoicePk(java.lang.String supplierInvoicePk); 
	 LgDeliveryOrderDto getByMemberInvoicePk(java.lang.String memberInvoicePk); 
	 LgDeliveryOrderDto getByDriver(java.lang.String driver); 
	 LgDeliveryOrderDto getByDriverPk(java.lang.String driverPk); 
	 LgDeliveryOrderDto getByDriverContact(java.lang.String driverContact); 
	 LgDeliveryOrderDto getByCarPk(java.lang.String carPk); 
	 LgDeliveryOrderDto getByCarPlate(java.lang.String carPlate); 
	 LgDeliveryOrderDto getByMember(java.lang.String member); 
	 LgDeliveryOrderDto getByMemberPk(java.lang.String memberPk); 
	 LgDeliveryOrderDto getByAbnormalRemark(java.lang.String abnormalRemark); 
	 LgDeliveryOrderDto getByRemark(java.lang.String remark); 
	 LgDeliveryOrderDto getByDeliveryNumber(java.lang.String deliveryNumber); 
	 LgDeliveryOrderDto getByPaymentPk(java.lang.String paymentPk); 
	 LgDeliveryOrderDto getByPaymentName(java.lang.String paymentName); 
	 LgDeliveryOrderDto getByPurchaserName(java.lang.String purchaserName); 
	 LgDeliveryOrderDto getByPurchaserPk(java.lang.String purchaserPk); 
	 LgDeliveryOrderDto getBySupplierName(java.lang.String supplierName); 
	 LgDeliveryOrderDto getBySupplierPk(java.lang.String supplierPk); 
	 LgDeliveryOrderDto getByInvoicePk(java.lang.String invoicePk); 
	 LgDeliveryOrderDto getByInvoiceName(java.lang.String invoiceName); 
	 LgDeliveryOrderDto getByInvoiceTaxidNumber(java.lang.String invoiceTaxidNumber); 
	 LgDeliveryOrderDto getByInvoiceRegPhone(java.lang.String invoiceRegPhone); 
	 LgDeliveryOrderDto getByInvoiceBankAccount(java.lang.String invoiceBankAccount); 
	 LgDeliveryOrderDto getByInvoiceBankName(java.lang.String invoiceBankName); 
	 LgDeliveryOrderDto getByInvoiceProvinceName(java.lang.String invoiceProvinceName); 
	 LgDeliveryOrderDto getByInvoiceCityName(java.lang.String invoiceCityName); 
	 LgDeliveryOrderDto getByInvoiceAreaName(java.lang.String invoiceAreaName); 
	 LgDeliveryOrderDto getByInvoiceRegAddress(java.lang.String invoiceRegAddress); 
	 LgDeliveryOrderDto getByLinePricePk(java.lang.String linePricePk); 
	 LgDeliveryOrderDto getByMandatePk(java.lang.String mandatePk); 
	 LgDeliveryOrderDto getByMandateUrl(java.lang.String mandateUrl); 
	 LgDeliveryOrderDto getByOrderNumber2(java.lang.String orderNumber2); 
		//根据订单编号查询子订单的数量
		int getDeliveryCountByOrderPkM(@Param("orderPk") String orderPk);

		//根据deliveryNumber查询主键
		String getPkByDeliveryNumberM(@Param("deliveryNumber") String deliveryNumber);
		//根据pk修改deliveryNumber
		int updateDeliveryNumberByPkM(@Param("pk") String pk,@Param("deliveryNumber") String deliveryNumber);
		
}
