package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.ManageAccount;

public interface EconReportCustomerService {
    /**
     * 获取昨天为止的申请记录
     * 
     * @return
     */
    List<EconCustomerMongo> getApproveBeforeDay();

    /**
     * 获取昨天为止的申请记录
     * 
     * @param list
     * @return
     */
    List<EconCustomerApproveExport> getYesterdayCustomerApprove(List<EconCustomerMongo> list);

    /**
     * 更新周数据
     * 
     * @param ecelist
     */
    void UpdateWeekData(List<EconCustomerApproveExport> ecelist);

    /**
     * 更新月数据
     * 
     * @param ecelist
     */
    void UpdateMonthData(List<EconCustomerApproveExport> ecelist);

    /**
     * 审批表
     * 
     * @param pk
     * @param year
     * @return
     */
    List<EconCustomerApproveExport> approvalManage_data(String pk, String year);


    /**
     * 保存审批表导出记录
     * @param params
     * @param account
     * @return
     */
    int saveApproveManageToOss(ReportFormsDataTypeParams params, ManageAccount account);


}
