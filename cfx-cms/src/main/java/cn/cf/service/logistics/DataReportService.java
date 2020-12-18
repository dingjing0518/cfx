package cn.cf.service.logistics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.DataReportDto;
import cn.cf.entity.DataReportExtDto;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.ManageAccount;
import cn.cf.model.SearchReport;

public interface DataReportService {
    /**
     * 毛利列表
     * 
     * @param qm
     * @param i
     * @param accountPk 
     * @return
     */
    PageModel<DataReportExtDto> searchGrossProfitList(QueryModel<SearchReport> qm, int i, String accountPk);

    /**
     * 客户管理列表
     * 
     * @param qm
     * @param i
     * @param accountPk 
     * @return
     */
    PageModel<DataReportDto> searchCustomerDataReportList(QueryModel<SearchReport> qm, int i, String accountPk);

    /**
     * 物流供应商报表列表
     * 
     * @param qm
     * @param i
     * @param accountPk 
     * @return
     */
    PageModel<DataReportDto> searchSupplierDataReportList(QueryModel<SearchReport> qm, int i, String accountPk);

}
