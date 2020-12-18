package cn.cf.service.logistics;

import java.io.UnsupportedEncodingException;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.LogisticsRouteExport;
import cn.cf.model.LogisticsRouteGridModel;
import cn.cf.model.LogisticsRouteModel;
import cn.cf.model.ManageAccount;
import jxl.Sheet;

public interface LogisticsRouteService {
    /**
     * 线路列表
     * 
     * @param qm
     * @param accountPk 
     * @return
     */
    PageModel<LogisticsRouteGridModel> getLogisticsRoute(QueryModel<LogisticsRouteModel> qm, String accountPk);

    /**
     * 删除线路
     * 
     * @param pk
     * @return
     */
    int delLogisticsRoute(String pk);

    /**
     * 根据pk查询线路
     * 
     * @param pk
     * @return
     */
    LogisticsRouteDto selectOne(String pk);

    /**
     * 新增/编辑线路
     * 
     * @param dto
     * @param showstartweights
     * @param showendweights
     * @param showlogisticsprices
     * @param showinlogisticsprices
     * @param pricePks
     * @return
     */
    @Transactional
    int saveLogisticsRoute(LogisticsRouteDto dto, String showstartweights, String showendweights, String showlogisticsprices,
            String showinlogisticsprices, String pricePks);

    /**
     * 线路导出
     * 
     * @param qm
     * @param account 
     * @return
     */
   PageModel<LogisticsRouteExport> searchLogisticsRouteList(QueryModel<LogisticsRouteModel> qm, ManageAccount account);

    /**
     * 线路导入
     * 
     * @param sheet
     * @return
     * @throws UnsupportedEncodingException
     */
    @Transactional
    int saveImportRoute(Sheet sheet) throws UnsupportedEncodingException;

}
