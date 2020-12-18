package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.BussEconPurchaserDataReport;
import cn.cf.entity.BussEconPurchaserDataReportExt;
import cn.cf.model.ManageAccount;

/**
 * 采购商日交易金融数据
 * 
 * @author xht
 *
 *         2018年10月16日
 */
public interface BussEconPurchaserDataService {
	

	/**
	 * 查询采购商日交易金融数据数据
	 * 
	 * @param qm
	 * @return
	 */
	PageModel<BussEconPurchaserDataReport> searchBussEconPurchaserDataList(
			QueryModel<BussEconPurchaserDataReportExt> qm, ManageAccount account);

	/**
	 * 导出采购商日交易金融数据数据
	 * 
	 * @param jsonData
	 * @param buss
	 * @return
	 */
	//List<BussEconPurchaserDataReport> exportBussEconPurchaserData(String jsonData, BussEconPurchaserDataReportExt buss,ManageAccount account);

}
