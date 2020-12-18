package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.model.ManageAccount;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.entity.SupplierEconomicsOrderReportForms;

public interface EconomicsCustomerRFService {
	
	/**
	 * 查询金融产品申请客户数
	 * @return
	 */
	Map<String,Object> getCustomerCounts(String years);

	/**
	 * 查询金融产品申请客户数
	 * @return
	 */
	int saveCustomApproveToOss(ReportFormsDataTypeParams params, ManageAccount account);


	/**
	 * 保存银行审批客户数记录
	 * @return
	 */
	int saveBankApproveCustomerToOss(ReportFormsDataTypeParams params, ManageAccount account);
	
	/**
	 * 查询银行审批客户数
	 * @return
	 */
	Map<String,Object> getBankCreditCustomerCounts(String years,String bankPk);
	
	/**
	 * 查询供应商金融产品交易列表
	 * @param accountPk 
	 * @return
	 */
	PageModel<SupplierEconomicsOrderReportForms> searchEconProdBussCountsList(QueryModel<SupplierEconomicsOrderReportForms> qm, String accountPk);
	
	/**
	 * 导出供应商金融产品交易列表
	 * @param accountPk 
	 * @return
	 */
	List<SupplierEconomicsOrderReportForms> exportEconProdBussCountsList(String years,String bankPk, String accountPk);

}
