package cn.cf.service.onlinepay;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.model.B2bOnlinepayRecord;

public interface OnlinepayService {

	/**
	 * 查询所有线上支付产品
	 * @return
	 */
	List<B2bOnlinepayGoodsDto> searchOnlinePayGoods();
	
	
	B2bOnlinepayGoodsDto getByPk(String pk);
	
	/**
	 * 根据订单号查询上线支付申请单
	 * @param orderNumber
	 * @return
	 */
	B2bOnlinepayRecordDto getByOrderNumer(String orderNumber);
	/**
	 * 查询线上支付申请单
	 * @param map
	 * @return
	 */
	List<B2bOnlinepayRecordDto> searchList(Map<String,Object> map);
	/**
	 * 更新线上支付记录
	 * @param record
	 */
	void updateOnlineRecord(B2bOnlinepayRecord record);
	
	/**
	 * 新增线上支付记录
	 * @param record
	 */
	void insertOnlineRecord(B2bOnlinepayRecord record);
}
