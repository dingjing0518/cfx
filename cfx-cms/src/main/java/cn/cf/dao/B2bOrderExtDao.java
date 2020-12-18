/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberExtDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.B2bOrderGoodsExtDto;
import cn.cf.entity.BussEconPurchaserDataReport;
import cn.cf.entity.BussStoreDataReport;
import cn.cf.entity.EconCreditOrderAmountEntry;
import cn.cf.entity.EconomicsProductOrder;
import cn.cf.entity.IndustryProductSpecTopRF;
import cn.cf.entity.IndustryPurchaserTopRF;
import cn.cf.entity.IndustryStoreTopRF;
import cn.cf.entity.OperationPurchaserSaleRF;
import cn.cf.entity.OperationSupplierSaleRF;
import cn.cf.entity.OrderNumEntity;
import cn.cf.entity.SupplierSaleData;
import cn.cf.entity.TransactionDataStoreEntity;
import cn.cf.model.B2bOrder;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOrderExtDao extends  B2bOrderDao{
	
	
	int searchGridExtCount(Map<String,Object> map);
	
	List<B2bOrderExtDto> searchGridExt(Map<String,Object> map);
	
	Double getTotalPriceAndFreight(Map<String,Object> map);
	
	B2bOrderExtDto getByOrderNumberExt(String orderNumber);
	
	List<B2bOrderGoodsExtDto> getByOrderNumberListExt(String orderNumber);
	
	Map<String,Object> getTotalPriceAndFreight(String orderNumber);
	
	int searchOrderMCount(Map<String,Object> map);
	 //重构新增
	List<B2bOrderExtDto> searchOrderMList(Map<String, Object> map);
	
	
	
	List<B2bLoanNumberExtDto> searchCreditOrderList(Map<String,Object> map);
	
	int searchCreditOrders(Map<String,Object> map);
	
	/**
	 * 授信管理-订单管理    导出订单
	 * @param map
	 * @return
	 */
	List<B2bOrderExtDto> searchOrderGoodsList(Map<String, Object> map);

	int searchOrderGoodsListCounts(Map<String, Object> map);

	int updateOrder(B2bOrder order);

	LinkedList<B2bOrderExtDto> exportOrderList(Map<String, Object> map);

	List<B2bOrderExtDto> exportOrderGoodsMList(Map<String, Object> map);
	
	/**
	 * 关闭订单
	 * @param orderNumber
	 * @return
	 */
	int closeOrder(String orderNumber);
	
	/**
	 * 统计一段时间订单总金额和数量
	 * @param map
	 * @return
	 */
	List<EconCreditOrderAmountEntry> countAndAmountCreditOrder(Map<String, Object> map);

	List<EconCreditOrderAmountEntry> countAndAmountCreditContract(Map<String, Object> map);
	
	List<EconomicsProductOrder> searchEconomicsOrders(Map<String, Object> map);

	B2bOrderExtDto getEconProductByOrderNumber(String orderNumber);
	
	
	List<OperationSupplierSaleRF> searchSupplierSaleRF(Map<String,Object> map);
	
	int countSupplierSaleRF(Map<String,Object> map);
	
	List<OperationPurchaserSaleRF> searchPurchaserSaleRF(Map<String,Object> map);
	
	List<SupplierSaleData> searchSupplierSaleDataRF(Map<String,Object> map);
	//店铺数量
	List<Integer> searchStoreCounts(Map<String,Object> map);
	List<Integer> searchPurchaserCounts(Map<String,Object> map);
	List<Integer> searchOrderCounts(Map<String,Object> map);
	List<Double> searchAmountCounts(Map<String,Object> map);
	List<Double> searchWeightCounts(Map<String,Object> map);
	
	/**
	 * 查询所有已收款订单，用于统计历史数据
	 * @param
	 * @return
	 */
	List<String> searchReceivablesTimeList();

	List<TransactionDataStoreEntity> searchStoreTransDataCounts(Map<String,Object> map);

	List<TransactionDataStoreEntity> searchStoreContractTransDataCounts(Map<String,Object> map);
	
	Double getStoreTransWeightCounts(@Param("orderNumberList")List<String> orderNumberList);

	Double getStoreContractTransWeightCounts(@Param("orderNumberList")List<String> orderNumberList);
	
	//行情信息Top10（品名规格统计）
	List<IndustryProductSpecTopRF> searchIndustryProductSpecCounts(Map<String,Object> map);
	//行情信息Top10（店铺名称统计）
	List<IndustryStoreTopRF> searchIndustryStoreCounts(Map<String,Object> map);

	//行情信息Top10（采购商名称统计）
	List<IndustryPurchaserTopRF> searchIndustryPurchaserCounts(Map<String,Object> map);

	List<BussStoreDataReport> searchOneDayBussStoreData(@Param(value="date") String date);

	String searchOriginalDate();

	List<BussEconPurchaserDataReport> searchOnedayDataforBussEconPur(@Param(value="date") String date);
	/**
	 * 某日产生订单的采购商
	 * @param date
	 * @return
	 */
	List<B2bCompanyDto> getPurByOrder(String date);

	OrderNumEntity getOrderByPurchaserPk(Map<String, Object> map);

}
