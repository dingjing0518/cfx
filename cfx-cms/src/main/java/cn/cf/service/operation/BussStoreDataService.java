package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.BussStoreDataReport;
import cn.cf.entity.BussStoreDataReportExt;
import cn.cf.model.ManageAccount;

/**
 * 运营中心/数据报表/店铺日交易数据
 * @author xht
 *
 * 2018年10月16日
 */
public interface  BussStoreDataService {

	/**
	 * 查询某一天所有店铺的交易数据
	 * @param date
	 * @return
	 */
	List<BussStoreDataReport> searchOnedayData(String date);
	

	/**
	 * 列表
	 * @param qm
	 * @return
	 */

	PageModel<BussStoreDataReport> searchBussStoreDataList(QueryModel<BussStoreDataReportExt> qm,ManageAccount account);

	/**
	 * 导出店铺日交易数据
	 * @param json
	 * @param buss
	 * @return
	 */
	//List<BussStoreDataReport> exportBussStoreData(String json, BussStoreDataReportExt buss,ManageAccount account);

}
