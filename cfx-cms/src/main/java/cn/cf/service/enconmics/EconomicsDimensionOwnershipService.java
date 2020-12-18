package cn.cf.service.enconmics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsOwnership;

import java.util.List;

public interface EconomicsDimensionOwnershipService {
    //获取全部的场所权属列表
    List<EconomicsOwnership> getOwnershipList();
    //获取启用的场所权属列表
    List<EconomicsOwnership> getOpenOwnershipList();
    //获取场所权属数据
    PageModel<EconomicsOwnership> getOwnershipData(QueryModel<EconomicsOwnership> qm);
    //更新场所权属
    int updateOwnershipStatus(EconomicsOwnership economicsOwnership);
    //更新场所权属
    int updateOwnership(EconomicsOwnership economicsOwnership);
    //新增场所权属
    int  insertOwnership(EconomicsOwnership economicsOwnership);
    //根据pk获取场所权属
    EconomicsOwnership getOwnershipByPk(String pk);
}
