package cn.cf.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bBindOrderDtoEx;
import cn.cf.model.B2bBindOrder;

public interface B2bBindOrderDaoEx extends B2bBindOrderDao{
    int insertBatch(List<B2bBindOrder> list);
    
    void updateBindOrder(Map<String,Object> map);

	int updateStatus(@Param("bindPk") String bindPk);
	
	int updateOverdue();
	
	List<B2bBindOrderDtoEx> searchBindToOrder();

	List<B2bBindOrderDtoEx> searchB2bBindOrderList(Map<String, Object> map);

	int searchB2bBindOrderCounts(Map<String, Object> map);
}
