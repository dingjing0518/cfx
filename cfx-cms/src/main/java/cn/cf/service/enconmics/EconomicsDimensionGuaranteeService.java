package cn.cf.service.enconmics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.EconomicsGuaranteeCountDto;
import cn.cf.dto.EconomicsGuaranteeTargetDto;
import cn.cf.dto.EconomicsGuaranteeTypeDto;
import cn.cf.entity.*;

import java.util.List;

public interface EconomicsDimensionGuaranteeService {
    //获取全部的担保数量维度列表
    List<EconomicsGuaranteeCount> getGuaranteeCountList();
    //获取启用的担保数量维度列表
    List<EconomicsGuaranteeCount> getOpenGuaranteeCountList();
    //获取担保数量维度
    PageModel<EconomicsGuaranteeCountDto> getGuaranteeCountData(QueryModel<EconomicsGuaranteeCountDto> qm);
    //更新担保数量维度
    int updateGuaranteeCountStatus(EconomicsGuaranteeCount economicsGuaranteeCount);
    //更新担保数量维度
    int updateGuaranteeCount(EconomicsGuaranteeCount economicsGuaranteeCount);
    //新增担保数量维度
    int  insertGuaranteeCount(EconomicsGuaranteeCount economicsGuaranteeCount);
    //根据pk获取担保数量维度
    EconomicsGuaranteeCount getGuaranteeCountByPk(String pk);




    //获取全部的担保对象维度列表
    List<EconomicsGuaranteeTarget> getGuaranteeTargetList();
    //获取启用的担保对象维度列表
    List<EconomicsGuaranteeTarget> getOpenGuaranteeTargetList();
    //获取担保对象维度
    PageModel<EconomicsGuaranteeTargetDto> getGuaranteeTargetData(QueryModel<EconomicsGuaranteeTargetDto> qm);
    //更新担保对象维度
    int updateGuaranteeTargetStatus(EconomicsGuaranteeTarget economicsGuaranteeTarget);
    //更新担保对象维度
    int updateGuaranteeTarget(EconomicsGuaranteeTarget economicsGuaranteeTarget);
    //新增担保对象维度
    int  insertGuaranteeTarget(EconomicsGuaranteeTarget economicsGuaranteeTarget);
    //根据pk获取担保对象维度
    EconomicsGuaranteeTarget getGuaranteeTargetByPk(String pk);


    //获取全部的担保类型维度列表
    List<EconomicsGuaranteeType> getGuaranteeTypeList();
    //获取启用的担保类型维度列表
    List<EconomicsGuaranteeType> getOpenGuaranteeTypeList();
    //获取担保类型维度
    PageModel<EconomicsGuaranteeTypeDto> getGuaranteeTypeData(QueryModel<EconomicsGuaranteeTypeDto> qm);
    //更新担保类型维度
    int updateGuaranteeTypeStatus(EconomicsGuaranteeType economicsGuaranteeType);
    //更新担保类型维度
    int updateGuaranteeType(EconomicsGuaranteeType economicsGuaranteeType);
    //新增担保类型维度
    int  insertGuaranteeType(EconomicsGuaranteeType economicsGuaranteeType);
    //根据pk获取担保类型维度
    EconomicsGuaranteeType getGuaranteeTypeByPk(String pk);


}
