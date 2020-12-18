package cn.cf.service;


import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bBindOrderDto;
import cn.cf.dto.B2bBindOrderDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;

public interface B2bBindOrderService {

   

 
    
    /**
     * 查询拼团需求单
     * @param orderNumber
     * @return
     */
    B2bBindOrderDto searchBindOrder(String orderNumber);
    
    /**
     * 修改拼团
     * @param orderNumber
     */
    void updateB2bBindOrderStatus(String orderNumber,Integer status);

    
 

RestCode submitBindOrder(Porder o, Map<String, List<Pgoods>> map,BatchOrder bo);
/**需求单列表
 * 
 * @param map
 * @param store
 * @param memberDto
 * @param isAdmin
 * @return
 */
PageModel<B2bBindOrderDtoEx> searchBindOrderList(Map<String, Object> map,
												 B2bStoreDto store, B2bMemberDto memberDto, Integer isAdmin);
 
 void checkBindBoxes(String bindPk);
 
 List<B2bBindOrderDtoEx> searchBindToOrder();

 	/**
 	 * 根据bindPk取消需求单
 	 * @param bindPk
 	 */
 	void cancelBindOrderByBindPk(String bindPk);

 	  Map<String, Object> searchBindOrderCounts(Map<String, Object> map,
			B2bStoreDto store, B2bMemberDto memberDto, Integer isAdmin);
 
}
