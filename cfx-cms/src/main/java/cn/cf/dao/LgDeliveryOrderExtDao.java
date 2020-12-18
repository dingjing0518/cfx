package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.DataReportDto;
import cn.cf.dto.LgOrderDetailDto;
import cn.cf.dto.PurchaserInvoiceDto;
import cn.cf.dto.SettleAccountDto;
import cn.cf.dto.SupplierInvoiceDto;
import cn.cf.entity.DataReportExtDto;
import cn.cf.entity.DeliveryForm;
import cn.cf.entity.LogisticsOrderAddress;
import cn.cf.entity.SearchLgLine;
import cn.cf.model.LgOrderGridModel;

public interface LgDeliveryOrderExtDao extends LgDeliveryOrderDao{

    List<LgOrderGridModel> getlgOrder(Map<String, Object> map);

    int countlgOrder(Map<String, Object> map);

    LgOrderDetailDto selectDetailByPk(String pk);

    int savePushLgCompanyName(Map<String, Object> map);

    int sureMoney(Map<String, Object> map);

    int submitFeedBack(@Param("pk") String pk, @Param("abnormalRemark") String abnormalRemark);

    LogisticsOrderAddress selectAddress(String pk);

    int saveOrder(LogisticsOrderAddress logisticsOrderAddress);

    int sureFeedback(String pk);

    DeliveryForm exportDeliveryForm(String pk);

    List<SearchLgLine> selectByStatus(Map<String, Object> map);

    int searchInvoiceCount(Map<String, Object> map);


    int updateInvoice(Map<String, Object> map);


    int searchSupplierSettleAccountCount(Map<String, Object> map);

    int updateSettleFreight(Map<String, Object> map);

    int updateSupplierInvoice(Map<String, Object> map);

    int searchPurchaserInvoiceCount(Map<String, Object> map);

    int searchGrossProfitCount(Map<String, Object> map);

    SearchLgLine matchAddress(String pk);
    
	List<DataReportExtDto> searchGrossProfit(Map<String, Object> map);
	
	List<PurchaserInvoiceDto> searchPurchaserInvoice(Map<String, Object> map);
	
	List<SupplierInvoiceDto> searchInvoice(Map<String, Object> map);
	
	List<SettleAccountDto> searchSupplierSettleAccountList(Map<String, Object> map);

	List<DataReportDto> searchCustomGrossProfit(Map<String, Object> map);

    LgOrderDetailDto selectHideDetailByPk(Map<String, Object> map);
}
