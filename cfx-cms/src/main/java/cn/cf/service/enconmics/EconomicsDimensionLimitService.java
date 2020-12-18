package cn.cf.service.enconmics;


import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.EconomicsLimitDto;
import cn.cf.entity.EconomicsLimit;
import cn.cf.model.B2bEconomicsDimensionModel;

import java.util.List;

public interface EconomicsDimensionLimitService {
    //获取全部的最高分限制列表
    List<EconomicsLimit> getLimitList();
    //获取启用的最高分限制列表
    List<EconomicsLimit> getOpenLimitList();
    //新增最高分限制
    int insert(B2bEconomicsDimensionModel b2bEconomicsDimensionModel);
    //获取最高分限制数据
    PageModel<EconomicsLimitDto> getLimitData(QueryModel<EconomicsLimitDto> qm);
    //更新最高分限制
    int updateLimitStatus(EconomicsLimit economicsLimit);
    //更新最高分限制
    int updateLimit(EconomicsLimit economicsLimit);
    //新增最高分限制
    int  insertLimit(EconomicsLimit economicsLimit);
    //根据pk获取最高分限制
    EconomicsLimit getLimitByPk(String pk);
}
