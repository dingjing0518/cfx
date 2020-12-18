package cn.cf.service.operation;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.IndustryProductSpecTopRF;
import cn.cf.entity.IndustryPurchaserTopRF;
import cn.cf.entity.IndustryStoreTopRF;
import cn.cf.entity.TransactionDataReportForm;
import cn.cf.entity.TransactionDataStoreReportForm;
import cn.cf.model.ManageAccount;

public interface OperationCentreReportService {
	/**
	 * 根据条件查询交易数据总览
	 * @param qm
	 * @return
	 */
	public PageModel<TransactionDataReportForm> searchTransactionDataList(QueryModel<TransactionDataReportForm> qm);
	/**
	 * 根据条件导出交易数据总览
	 * @param map
	 * @return
	 */
//	public List<TransactionDataReportForm> exportTransactionDataList(Map<String,Object> map);
	
	/**
	 * 查询店铺日交易金融数据
	 * @param qm
	 * @return
	 */
	public PageModel<TransactionDataStoreReportForm> searchTransDataStoreList(QueryModel<TransactionDataStoreReportForm> qm,ManageAccount account);
	/**
	 * 导出店铺日交易金融数据
	 * @param map
	 * @return
	 */
	//public List<TransactionDataStoreReportForm> exportTransDataStoreList(Map<String,Object> map, ManageAccount account);
	
	/**
	 * 行业数据Top10，规格统计
	 * @param year
	 * @param month
	 * @return
	 */
	public List<IndustryProductSpecTopRF> searchIndustryProductSpecList(String year,int month);
	/**
	 * 行业数据Top10，店铺统计
	 * @param year
	 * @param month
	 * @return
	 */
	public List<IndustryStoreTopRF> searchIndustryStoreList(String year,int month);
	/**
	 * 行业数据Top10，采购商统计
	 * @param year
	 * @param month
	 * @return
	 */
	public List<IndustryPurchaserTopRF> searchIndustryPurchaserList(String year,int month);
	
}
