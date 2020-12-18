package cn.cf.service.enconmics;


import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsEquipment;

import java.util.List;

public interface EconomicsDimensionEquipmentService {
    //获取全部的设备列表
    List<EconomicsEquipment> getEquipmentList();
    //获取启用的设备列表
    List<EconomicsEquipment> getOpenEquipmentList();
    //获取设备数据
    PageModel<EconomicsEquipment> getEquipmentData(QueryModel<EconomicsEquipment> qm);
    //更新设备
    int updateEquipmentStatus(EconomicsEquipment economicsEquipment);
    //更新设备
    int updateEquipment(EconomicsEquipment economicsEquipment);
    //新增设备
    int  insertEquipment(EconomicsEquipment economicsEquipment);
    //根据pk获取设备
    EconomicsEquipment getEquipmentByPk(String pk);
}
