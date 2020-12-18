package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsCapacity;
import cn.cf.model.B2bEconomicsDimensionModel;

public interface EconomicsDimensionCapacityService {
    //获取全部的产能列表
    List<EconomicsCapacity> getCapacityList();
    //获取启用的产能列表
    List<EconomicsCapacity> getOpenCapacityList();
    //新增维度
    int insert(B2bEconomicsDimensionModel b2bEconomicsDimensionModel);
    //获取产能数据
    PageModel<EconomicsCapacity> getCapacityData(QueryModel<EconomicsCapacity> qm);
    //更新
    int updateCapacityStatus(EconomicsCapacity economicsCapacity);
    //更新
    int updateCapacity(EconomicsCapacity economicsCapacity);
    //新增
    int  insertCapacity(EconomicsCapacity economicsCapacity);
    //根据pk获取产能
    EconomicsCapacity getCapacityByPk(String pk);
}
