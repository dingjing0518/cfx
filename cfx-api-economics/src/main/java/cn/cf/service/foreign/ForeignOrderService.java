package cn.cf.service.foreign;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.OrderGoodsDtoEx;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bOrder;

/**
 * 
 * @description:此service提供给别的系统操作订单
 * @author FJM
 * @date 2018-5-17 下午7:33:31
 */
public interface ForeignOrderService {
	
	/**
	 * 订单状态修改
	 * @param orderNumber
	 * @param status
	 */
	@Transactional
	void updateOrderStatus(B2bOrder order,SysCompanyBankcardDto card);
	
	/**
	 * 合同订单状态修改
	 * @param orderNumber
	 * @param status
	 */
	@Transactional
	void updateContractStatus(B2bContract contract,SysCompanyBankcardDto card);

	/**
	 * 查询订单信息
	 * @param orderNumber
	 * @return
	 */
	B2bOrderDtoMa getOrder(String orderNumber);
	
	/**
	 * 查询合同订单信息
	 * @param orderNumber
	 * @return
	 */
	B2bOrderDtoMa getContract(String contractNo);
	
	/**
	 * 查询订单详情
	 * @param orderNumber
	 * @return
	 */
	List<OrderGoodsDtoEx> getOrderGoods(String orderNumber);
	/**
	 * 查询合同详情
	 * @param orderNumber
	 * @return
	 */
	List<OrderGoodsDtoEx> getContractGoods(String contractNo);
	/**
	 * 查询支付方式
	 * @param type
	 * @return
	 */
	B2bPaymentDto searchPayment(Integer type);
}
