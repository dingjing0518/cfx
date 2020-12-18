package cn.cf.dao;

import java.util.Map;

public interface LgDeliveryOrderGoodsExtDao extends LgDeliveryOrderGoodsDao{
    int updateOrderGoodsByDeliveryPk(Map<String, Object> map);

}
