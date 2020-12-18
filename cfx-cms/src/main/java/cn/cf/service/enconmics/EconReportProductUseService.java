package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.entity.EcnoProductUseExport;

public interface EconReportProductUseService {
    /**
     * 查询昨日新增金融产品数据
     * 
     * @return
     */
    List<EcnoProductUseExport> searchEcnoProductUse();

    /**
     * 编辑每周的金融产品数据
     * 
     * @param list
     */
    void UpdateWeekData(List<EcnoProductUseExport> list);

    /**
     * 编辑每月的金融产品数据
     * 
     * @param list
     */
    void UpdateMonthData(List<EcnoProductUseExport> list);

    /**
     * 金融产品使用列表
     * 
     * @param pk
     * @param year
     * @return
     */
    List<EcnoProductUseExport> econProductUse_data(String pk, String year);

}
