package cn.cf.service.operation;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.GoodsUpdateReport;
import cn.cf.entity.GoodsUpdateReportExt;
import cn.cf.entity.OperationSupplierSaleRF;
import cn.cf.entity.PurchaserSaleReportForms;
import cn.cf.entity.SalemanSaleDetailReport;
import cn.cf.entity.SalemanSaleDetailReportExt;
import cn.cf.entity.SupplierSaleDataReport;
import cn.cf.entity.SupplierSaleDataReportExt;

public interface OperationReportService {
    /**
     * 查询采购商交易统计列表
     * 
     * @param qm
     * @param accountPk
     * @return
     */
    PageModel<PurchaserSaleReportForms> searchPuchaserSaleList(QueryModel<PurchaserSaleReportForms> qm, String accountPk);

    /**
     * 根据年份导出采购商交易数据
     * 
     * @param year
     * @param accountPk 
     * @return
     */
    List<PurchaserSaleReportForms> exporthPuchaserSaleList(String year, String accountPk);

    /**
     * 查询供应商销售报表列表
     * 
     * @param qm
     * @param accountPk
     * @return
     */
    PageModel<OperationSupplierSaleRF> searchSupplierSaleList(QueryModel<OperationSupplierSaleRF> qm, String accountPk);

    /**
     * 导出供应商销售报表
     * 
     * @param map
     * @param accountPk
     * @return
     */
    List<OperationSupplierSaleRF> exportSupplierSaleList(Map<String, Object> map, String accountPk);

    /**
     * 每月平台销售数据
     * 
     * @param map
     * @return
     */
    Map<String, Object> searchSupplierSaleData(Map<String, Object> map);

    /**
     * 搜索产品更新表:列表
     * 
     * @param qm
     * @param i
     * @return
     */
    PageModel<GoodsUpdateReport> searchGoodsUpdateReportList(QueryModel<GoodsUpdateReportExt> qm, int i, String accountPk);

    /**
     * 搜索业务员交易明细列表
     * 
     * @param qm
     * @param i
     * @param accountPk
     * @return
     */
    PageModel<SalemanSaleDetailReport> searchSalemanSaleList(QueryModel<SalemanSaleDetailReportExt> qm, int i, String accountPk);

    /**
     * 搜索供应商销售数据列表
     * 
     * @param qm
     * @param i
     * @param accountPk 
     * @return
     */
    PageModel<SupplierSaleDataReport> supplierSaleDataReportList(QueryModel<SupplierSaleDataReportExt> qm, int i, String accountPk);

}
