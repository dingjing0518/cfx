package cn.cf.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.ExportOrderDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bOrderGoods;

public interface B2bOrderService {
	/**
	 * 分页查询订单列表
	 * 
	 * @param map
	 * @return
	 */
	PageModel<B2bOrderDtoEx> searchOrderList(Map<String, Object> map);

	/**
	 * 查询订单数量
	 * 
	 * @param map
	 * @return
	 */
	Map<String, Object> searchOrderCounts(Map<String, Object> map);

	/**
	 * 导出订单列表查询
	 * 
	 * @param map
	 * @return
	 */
	List<ExportOrderDto> exportOrderList(Map<String, Object> map);

	/**
	 * 获取单个订单信息
	 * 
	 * @param orderNumber
	 * @return
	 */
	B2bOrderDtoEx getOrder(String orderNumber);

	/**
	 * 查询订单商品
	 * 
	 * @param orderNumber
	 * @return
	 */
	List<B2bOrderGoodsDtoEx> searchOrderGoodsList(String orderNumber);

	/**
	 * 查询订单商品
	 * 
	 * @param orderNumber
	 * @param flag
	 *            true 查所有 false 查未发货的
	 * @return
	 */
	List<B2bOrderGoodsDtoEx> searchOrderGoodsList(String orderNumber,
			boolean flag,boolean cancel);

	/**
	 * 查询订单商品总价和运费总价
	 * 
	 * @param orderNumber
	 * @return
	 */
	B2bOrderGoodsDtoEx getTotalPrice(String orderNumber);

	/**
	 * 订单提交
	 * 
	 * @param o
	 * @param company
	 *            下单采购商
	 * @param member
	 *            下单操作人
	 * @param address
	 *            收货地址
	 * @param invoice
	 *            发票信息
	 * @param map
	 *            商品详细
	 * @return
	 */
	@Transactional
	RestCode submitOrder(Porder o, Map<String, List<Pgoods>> map, BatchOrder bo);

	/**
	 * 订单状态修改
	 * 
	 * @param orderNumber
	 * @param status
	 */
	@Transactional
	B2bOrderDtoEx updateOrderStatus(B2bOrder order);
	
	/**
	 * 修改支付状态
	 * @param order
	 * @param bankName
	 * @param bankAccount
	 * @return
	 */
	@Transactional
	B2bOrderDtoMa updateOrderStatus(B2bOrder order,SysCompanyBankcardDto card);

	/**
	 * 订单内容修改
	 * 
	 * @param orderNumber
	 */
	@Transactional
	B2bOrderDtoEx updateOrder(B2bOrder order, List<B2bOrderGoods> goodsList);

	/**
	 * 取消订单
	 * 
	 * @param orderEx
	 * @param goodsList
	 */
	void cancelOrder(B2bOrderDtoEx orderEx, List<B2bOrderGoodsDtoEx> goodsList);

	/**
	 * 根据订单增加会员积分
	 * 
	 * @param o
	 */
	void memberPointByOrder(B2bMemberDto memberDto, B2bOrderDtoEx o);
	/***
	 * 合同转订单
	 * @param cgList
	 * @return
	 */
	List<B2bOrder> submitContractOrder(B2bContractDto contract,List<B2bContractGoods> cgList,B2bMemberDto member,String address);

	
	/**
	 * 自动完成订单
	 * @param days 天数
	 * @param orderStatus 订单状态：0待审核，1待付款，2确认付款，3待发货，4已发货，5部分发货
	 */
	void autoCompleteOrder(int days,int orderStatus);
	
}
