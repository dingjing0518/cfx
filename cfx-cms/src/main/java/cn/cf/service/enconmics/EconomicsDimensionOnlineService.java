package cn.cf.service.enconmics;


import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.EconomicsOnlineDto;
import cn.cf.entity.EconomicsOnline;
import cn.cf.model.B2bEconomicsDimensionModel;

import java.util.List;

public interface EconomicsDimensionOnlineService {
    //获取全部的线上数据维度列表
    List<EconomicsOnline> getOnlineList();
    //获取启用的线上数据维度列表
    List<EconomicsOnline> getOpenOnlineList();
    //新增线上数据维度
    int insert(B2bEconomicsDimensionModel b2bEconomicsDimensionModel);
    //获取线上数据数据维度
    PageModel<EconomicsOnlineDto> getOnlineData(QueryModel<EconomicsOnlineDto> qm);
    //更新线上数据维度
    int updateOnlineStatus(EconomicsOnline economicsOnline);
    //更新线上数据维度
    int updateOnline(EconomicsOnline economicsOnline);
    //新增线上数据维度
    int  insertOnline(EconomicsOnline economicsOnline);
    //根据pk获取线上数据维度
    EconomicsOnline getOnlineByPk(String pk);
}
