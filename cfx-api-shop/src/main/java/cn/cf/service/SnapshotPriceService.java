package cn.cf.service;


import java.util.List;

import cn.cf.entity.GoodsPriceData;

public interface SnapshotPriceService {
    void action(); //保存当天商品价格

    void remove();//删除100天前的价格数据

    List<GoodsPriceData> searchList(String pk, int days);
}
