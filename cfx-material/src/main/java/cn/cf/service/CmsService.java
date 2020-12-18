package cn.cf.service;

import java.util.Map;

public interface CmsService {
    
    /**
     * 
     * 新增订单中，采购商、店铺业务员
     * 
     * @param map（orderPk，block，type：1化纤订单3化纤合同订单）
     * @return
     */
    String insertSaleManByOrder(Map<String, Object> map);


}
