package cn.cf.service;

import java.util.List;

import cn.cf.entity.OrderSyncToMongo;


/**
 * 化纤汇与crm数据桥接层
 * @description:
 * @author FJM
 * @date 2019-3-4 下午2:38:00
 */
public interface HuaxianhuiCrmService {
	/**
	 * 同步所有商品信息
	 * @param tokenDto
	 * @param jsonData
	 * @return
	 */
	String syncGoods(String storePk,String jsonData);
	/**
	 * 同步商品所有库存
	 * @param tokenDto
	 * @param jsonData
	 * @return
	 */
	String syncGoodsStock(String storePk,String jsonData);
	/**
	 * 同步商品所有价格
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	String syncGoodsPrice(String storePk,String jsonData);
	/**
	 * 同步商品所有隐藏状态
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	String syncGoodsShow(String storePk,String jsonData);
	/**
	 * 同步所有竞拍商品
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	String syncAuctionGoods(String storePk,String jsonData);
	/**
	 * 同步所有拼团商品
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	String syncBindGoods(String storePk,String jsonData);
	/**
	 * 同步订单
	 * @param orderNumber
	 * @return
	 */
	String syncOrder(String orderNumber);
	/**
	 * 同步合同
	 * @param contractNo
	 * @return
	 */
	String syncContract(String contractNo);

	
	/**
	 * 同步客户收货地址
	 * @param companyPk
	 * @param storePk
	 * @return
	 */
	String syncAddress(String companyPk,String storePk,String jsonData);
	
	/**
	 * 同步客户收货地址(单条)
	 * @param jsonData
	 * @return
	 */
	String syncAddressOne(String jsonData);
	
	/**
	 * 修改订单数据
	 * @param orderStatus 1分货 、3确认收款 、-1关闭订单、 4发货
	 * @param jsonData
	 * @return
	 */
	String updateOrders(Integer orderStatus,String jsonData);
	
	
	/**
	 * 物流模板同步
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	String syncLogistics(String storePk,String jsonData);
	
	
	/**
	 * 订单提交(并发同步)
	 * @param olist
	 */
	void syncNoPushOrder(List<OrderSyncToMongo> olist);
	
	/**
	 * 盛虹合同提交审批
	 * @param contractNo
	 * @return
	 */
	String submitAudit(String contractNo);
	
}
