package cn.cf.task.schedule;

import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.task.schedule.chemifiber.*;
import cn.cf.task.schedule.economics.*;
import cn.cf.task.schedule.logistics.*;
import cn.cf.task.schedule.marketing.*;
import cn.cf.task.schedule.operation.*;
import cn.cf.task.schedule.yarn.YarnGoodsRunnable;
import cn.cf.util.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * 营销报表：产品更新
 * @author xht
 *
 * 2018年11月13日
 */
@Component
@EnableScheduling
public class AllDynamicTask {

	private final static Logger logger = LoggerFactory.getLogger(AllDynamicTask.class);
	@Autowired
	private SysExcelStoreExtDao storeDao;

	//@Scheduled(cron = "0 50 0 * * ?")
	@Scheduled(cron = "0 0/1 * * * ?")
	public void runAllExport() {


		SysExcelStoreExtDto dto = storeDao.getIsFirstTimeExcelStore();
		if (dto != null) {

			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportBankApproveAmountList_")) {
				BankApproveAmountRunnable bankApproveAmountThread = new BankApproveAmountRunnable();
				bankApproveAmountThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportEconProdBussRF_")) {
				EconProdBussRunnable econProdBussThread = new EconProdBussRunnable();
				econProdBussThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportEconProductUseList_")) {
				EconProductUseRunnable econProductUseThread = new EconProductUseRunnable();
				econProductUseThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportProductBalance_")) {
				ProductBalanceRunnable productBalanceThread = new ProductBalanceRunnable();
				productBalanceThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportCustomerReport_")) {
				CustomerReportRunnable customerReportThread = new CustomerReportRunnable();
				customerReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportGrossProfitReport_")) {
				GrossProfitReportRunnable grossProfitReportThread = new GrossProfitReportRunnable();
				grossProfitReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportLgOrderForm_")) {
				LogisticsOrderRunnable logisticsOrderThread = new LogisticsOrderRunnable(1);
				logisticsOrderThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportLgAbOrderForm_")) {
				LogisticsOrderRunnable logisticsOrderThreadAb = new LogisticsOrderRunnable(2);
				logisticsOrderThreadAb.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportLogisticsRoute_")) {
				LogisticsRouteRunnable logisticsRouteThread = new LogisticsRouteRunnable();
				logisticsRouteThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportPurchaserInvoiceForm_")) {
				PurchaserInvoiceFormRunnable purchaserInvoiceFormThread = new PurchaserInvoiceFormRunnable();
				purchaserInvoiceFormThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSupplierDataReport_")) {
				SupplierDataReportRunnable supplierDataReportThread = new SupplierDataReportRunnable();
				supplierDataReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSupplierInvoiceForm_")) {
				SupplierInvoiceFormRunnable supplierInvoiceFormThread = new SupplierInvoiceFormRunnable();
				supplierInvoiceFormThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSettleForm_")) {
				SupplierSettleFormRunnable supplierSettleFormThread = new SupplierSettleFormRunnable();
				supplierSettleFormThread.run();
			}

			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportGoodsUpdateReport_")) {
				GoodsUpdateReportRunnable goodsUpdateReportThread = new GoodsUpdateReportRunnable();
				goodsUpdateReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportPurchaserSaleList_")) {
				PurchaserSaleReportRunnable purchaserSaleReportThread = new PurchaserSaleReportRunnable();
				purchaserSaleReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportsalemanSaleDetailReport_")) {
				SalemanSaleDetailReportRunnable salemanSaleDetailReportThread = new SalemanSaleDetailReportRunnable();
				salemanSaleDetailReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportShopSaleDataReport_")) {
				ShopSaleDataReportRunnable shopSaleDataReportThread = new ShopSaleDataReportRunnable();
				shopSaleDataReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSupplierSaleDataReport_")) {
				SupplierSaleDataReportRunnable supplierSaleDataReportThread = new SupplierSaleDataReportRunnable();
				supplierSaleDataReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSupplierSaleReport_")) {
				SupplierSaleReportRunnable supplierSaleReportThread = new SupplierSaleReportRunnable();
				supplierSaleReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSysNewsStorageEntity_")) {
				SysNewsStorageRunnable sysNewsStorageThread = new SysNewsStorageRunnable();
				sysNewsStorageThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportBussEconPurchaserData_")) {
				BussEconPurchaserDataRunnable bussEconPurchaserDataThread = new BussEconPurchaserDataRunnable();
				bussEconPurchaserDataThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportBussEconStoreData_")) {
				BussEconStoreRunnable bussEconStoreThread = new BussEconStoreRunnable();
				bussEconStoreThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportBussOverview_")) {
				BussOverviewRunnable bussOverviewThread = new BussOverviewRunnable();
				bussOverviewThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportBussStoreData_")) {
				BussStoreDataRunnable bussStoreDataThread = new BussStoreDataRunnable();
				bussStoreDataThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportDimensionalityExtPresent_")) {
				DimensionalityExtPresentRunnable dimensionalityExtPresentThread = new DimensionalityExtPresentRunnable();
				dimensionalityExtPresentThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportDimensionalityHistoryList_")) {
				DimensionalityHistoryRunnable DimensionalityHistoryThread = new DimensionalityHistoryRunnable();
				DimensionalityHistoryThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportFlowData_")) {
				FlowDataRunnable flowDataThread = new FlowDataRunnable();
				flowDataThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportMemberData_")) {
				MemberDataReportRunnable memberDataReportThread = new MemberDataReportRunnable();
				memberDataReportThread.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportOrder_")) {
				OrderRunnable threadOrder = new OrderRunnable();
				threadOrder.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportOrderGoods_")) {
				OrderGoodsRunnable threadOrderGoods = new OrderGoodsRunnable();
				threadOrderGoods.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportGoodsList_")) {
				GoodsRunnable threadGoods = new GoodsRunnable();
				threadGoods.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSxPriceTrendHistoryList_")) {
				PriceTrendSxHistoryRunnable threadPriceHistorySx = new PriceTrendSxHistoryRunnable();
				threadPriceHistorySx.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportPriceTrendHistoryList_")) {
				PriceTrendCfHistoryRunnable threadPriceHistoryCf = new PriceTrendCfHistoryRunnable();
				threadPriceHistoryCf.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportPriceTrend_")) {
				PriceTrendRunnable threadPrice = new PriceTrendRunnable();
				threadPrice.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportB2bCompany_")) {
				CompanyRunnable threadCompany = new CompanyRunnable();
				threadCompany.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportAwardRoster_")) {
				LotteryAwardRunnable threadLottery = new LotteryAwardRunnable();
				threadLottery.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportMembersList_")) {
				MemberRunnable threadMember = new MemberRunnable();
				threadMember.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportSxGoodsList_")) {
				YarnGoodsRunnable threadYarnGoods = new YarnGoodsRunnable();
				threadYarnGoods.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportOrderM_")) {
				MkOrderRunnable threadMkOrder = new MkOrderRunnable();
				threadMkOrder.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportCustomerRF_")) {
				CustomApproveRunnable threadCustom = new CustomApproveRunnable();
				threadCustom.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportCreditOrders_")) {
				CreditOrderRunnable threadCredit = new CreditOrderRunnable();
				threadCredit.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportBankApproveCustomer_")) {
				BankApproveCustomerRunnable threadBankApprove = new BankApproveCustomerRunnable();
				threadBankApprove.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportApprovalManageList_")) {
				ApproveManageRunnable threadApprove = new ApproveManageRunnable();
				threadApprove.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportImprovingdata_")) {
				ImprovingdataRunnable threadApprove = new ImprovingdataRunnable();
				threadApprove.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportMContact_")) {
				MContractRunnable threadApprove = new MContractRunnable();
				threadApprove.run();
			}

			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("creditInvoice_")) {
				CreditInvoiceRunnable threadApprove = new CreditInvoiceRunnable();
				threadApprove.run();
			}
			if (CommonUtil.isNotEmpty(dto.getMethodName()) && dto.getMethodName().startsWith("exportPriceIndex_")) {
				ProductPriceIndexRunnable threadApprove = new ProductPriceIndexRunnable();
				threadApprove.run();
			}

		}
	}
}

	
	