package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.entity.BankApproveAmountExport;

public interface EconReportBankApproveAmountService {
    /**
     * 银行审批额度：获取昨日的新有效的额度,按银行排序
     * 
     * @return
     */
    List<B2bEconomicsBankCompanyExtDto> searchBankApproveAmount();

    /**
     * 银行审批额度：昨日数据格式处理
     * 
     * @param list
     * @return
     */
    List<BankApproveAmountExport> getYesterdayBankApproveAmount(List<B2bEconomicsBankCompanyExtDto> list);

    /**
     * 银行审批额度： 更新每周
     * 
     * @param bamList
     */
    void UpdateWeekData(List<BankApproveAmountExport> bamList);

    /**
     * 银行审批额度： 更新每月
     */
    void UpdateMonthData();

    /**
     * 银行审批额度列表
     * 
     * @param bamList
     */
    List<BankApproveAmountExport> bankApproveAmount_data(String pk, String year);

}
