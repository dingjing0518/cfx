package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.OrderRecord;
import cn.cf.model.B2bOrder;


public interface B2bOrderRecordService {
	
	/**
	 * 查询订单日志(图标形式)
	 * @param orderNumber
	 * @return
	 */
	List<OrderRecord> searchOrderRecordList(String orderNumber);
	
	/**
	 * 录入订单日志
	 * @param order
	 * @param content
	 */
	void insertOrderRecord(B2bMemberDto m,B2bOrder order,String type,String content);
	
}
