package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.OrderSyncToMongo;

public interface B2bOrderSyncToMongoService {




    /**
     * 查询
     *
     * @param map
     * @param company
     * @return
     */
    PageModel<OrderSyncToMongo> searchOrderSyncToMongoList(Map<String, Object> map, String storePk);


    void syncOrderNumberToMongo(String orderNumber, B2bStoreDto b2bStoreDto, String detail) throws Exception;
}