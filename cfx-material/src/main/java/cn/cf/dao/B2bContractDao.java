/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bContract;
import cn.cf.dto.B2bContractDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bContractDao {
	int insert(B2bContract model);
	int update(B2bContract model);
	int delete(String id);
	List<B2bContractDto> searchGrid(Map<String, Object> map);
	List<B2bContractDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bContractDto getByContractNo(java.lang.String contractNo); 
	 B2bContractDto getBySaleDepartment(java.lang.String saleDepartment); 
	 B2bContractDto getByCovenant(java.lang.String covenant); 
	 B2bContractDto getBySupplementary(java.lang.String supplementary); 
	 B2bContractDto getByPriceType(java.lang.String priceType); 
	 B2bContractDto getByMember(java.lang.String member); 
	 B2bContractDto getByEmployeePk(java.lang.String employeePk); 
	 B2bContractDto getBySalesman(java.lang.String salesman); 
	 B2bContractDto getBySalesmanNumber(java.lang.String salesmanNumber); 
	 B2bContractDto getBySupplierPk(java.lang.String supplierPk); 
	 B2bContractDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bContractDto getByStorePk(java.lang.String storePk); 
	 B2bContractDto getByStoreName(java.lang.String storeName); 
	 B2bContractDto getByContractRate(java.lang.String contractRate); 
	 B2bContractDto getByLogisticsModel(java.lang.String logisticsModel); 
	 B2bContractDto getByLogisticsModelPk(java.lang.String logisticsModelPk); 
	 B2bContractDto getByCarrier(java.lang.String carrier); 
	 B2bContractDto getByCarrierPk(java.lang.String carrierPk); 
	 B2bContractDto getByPaymentName(java.lang.String paymentName); 
	 B2bContractDto getByEconomicsGoodsName(java.lang.String economicsGoodsName); 
	 B2bContractDto getByPlantName(java.lang.String plantName); 
	 B2bContractDto getByPlantPk(java.lang.String plantPk); 
	 B2bContractDto getBySupplierInfo(java.lang.String supplierInfo); 
	 B2bContractDto getByPurchaserInfo(java.lang.String purchaserInfo); 
	 B2bContractDto getByAddressInfo(java.lang.String addressInfo); 
	 B2bContractDto getByBlock(java.lang.String block); 

}
