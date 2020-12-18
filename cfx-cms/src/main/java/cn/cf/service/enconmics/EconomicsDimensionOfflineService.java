package cn.cf.service.enconmics;


import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsOfflineSales;
import cn.cf.entity.EconomicsOfflineScale;
import cn.cf.entity.EconomicsOfflineInvoice;

import java.util.List;

public interface EconomicsDimensionOfflineService {

    //获取全部的线下数据销售维度列表
    List<EconomicsOfflineSales> getOfflineSalesList();
    //获取启用的线下数据销售维度列表
    List<EconomicsOfflineSales> getOpenOfflineSalesList();
    //获取线下数据数据销售维度
    PageModel<EconomicsOfflineSales> getOfflineSalesData(QueryModel<EconomicsOfflineSales> qm);
    //更新线下数据销售维度
    int updateOfflineSalesStatus(EconomicsOfflineSales economicsOfflineSales);
    //更新线下数据销售维度
    int updateOfflineSales(EconomicsOfflineSales economicsOfflineSales);
    //新增线下数据销售维度
    int  insertOfflineSales(EconomicsOfflineSales economicsOfflineSales);
    //根据pk获取线下数据销售维度
    EconomicsOfflineSales getOfflineSalesByPk(String pk);




    //获取全部的线下数据规模维度列表
    List<EconomicsOfflineScale> getOfflineScaleList();
    //获取启用的线下数据规模维度列表
    List<EconomicsOfflineScale> getOpenOfflineScaleList();
    //获取线下数据规模数据维度
    PageModel<EconomicsOfflineScale> getOfflineScaleData(QueryModel<EconomicsOfflineScale> qm);
    //更新线下数据规模维度
    int updateOfflineScaleStatus(EconomicsOfflineScale economicsOfflineScale);
    //更新线下数据规模维度
    int updateOfflineScale(EconomicsOfflineScale economicsOfflineScale);
    //新增线下数据规模维度
    int  insertOfflineScale(EconomicsOfflineScale economicsOfflineScale);
    //根据pk获取线下数据规模维度
    EconomicsOfflineScale getOfflineScaleByPk(String pk);



    //获取全部的线下数据发票维度列表
    List<EconomicsOfflineInvoice> getOfflineInvoiceList();
    //获取启用的线下数据发票维度列表
    List<EconomicsOfflineInvoice> getOpenOfflineInvoiceList();
    //获取线下数据数据发票维度
    PageModel<EconomicsOfflineInvoice> getOfflineInvoiceData(QueryModel<EconomicsOfflineInvoice> qm);
    //更新线下数据发票维度
    int updateOfflineInvoiceStatus(EconomicsOfflineInvoice economicsOfflineInvoice);
    //更新线下数据发票维度
    int updateOfflineInvoice(EconomicsOfflineInvoice economicsOfflineInvoice);
    //新增线下数据发票维度
    int  insertOfflineInvoice(EconomicsOfflineInvoice economicsOfflineInvoice);
    //根据pk获取线下数据发票维度
    EconomicsOfflineInvoice getOfflineInvoiceByPk(String pk);
}
