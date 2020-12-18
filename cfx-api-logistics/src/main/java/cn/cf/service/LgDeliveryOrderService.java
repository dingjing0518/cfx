package cn.cf.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.common.RestCode;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgDeliveryOrderForB2BDto;
import cn.cf.dto.OrderDeliveryDto;
import cn.cf.dto.PageModelOrderVo;

/**
 * Created by Thinkpad on 2017/9/29.
 */
public interface LgDeliveryOrderService {
	
	/**
	 * 平台用户我的订单
	 * @param par 参数
	 * @return
	 */
	PageModelOrderVo<OrderDeliveryDto> selectMemberDeliveryList(Map<String,Object> par);
	
	/**
	 * 平台用户导出文件
	 * @param par 参数
	 * @return
	 */
	List<OrderDeliveryDto>  exportDeliveryOrder(Map<String,Object> par);
	
	/**
	 * 异常订单列表
	 * @param par 参数
	 * @return
	 */
	PageModelOrderVo<OrderDeliveryDto> selectAbDeliveryList(Map<String,Object> par);
	
	/**
	 * 平台用户-订单详情
	 * @param par 参数
	 * @return
	 */
    HashMap<String,Object> selectDetailByDeliveryPkOnMemeber(Map<String,Object> par);
    
    /**
     * 根据pk检查订单的状态
     * @param deliveryPk 运货单pk
     * @return
     */
    public int checkOrderStatusByPk(String deliveryPk);
    
    /**
     * 取消订单
     * @param deliveryPk  运货单pk
     * @throws Exception
     */
    @Transactional
    void cancelDelivery(String deliveryPk) throws Exception;
    
    /**
     * 平台用户下单
     * @param map  参数
     * @return
     */
    @Transactional
    RestCode commitOrder(Map<String, Object> map);
    
    /**
     * 付款前获取付款信息
     * @param deliveryPk  运货单pk
     * @return
     */
    LgDeliveryOrderDto getInfoBeforePay(String deliveryPk);
    
    /**
     * 用户付款（orderStatus:9->8）
     * @param deliveryPk  运货单pk
     * @param paymentPk  支付方式pk
     * @param paymentName  支付方式名称
     * @param tempDate  支付时间
     * @return
     */
    @Transactional
    public Integer payForLogistics(String deliveryPk,String paymentPk,String paymentName,Date tempDate);
    
    /**
     * 查询订单信息（再来一单）
     * @param par 参数
     * @return
     */
    public HashMap<String, Object> getInfo4MoreOrder(Map<String, Object> par);
    
    /**
     * 平台用户开票订单管理
     * @param map  参数
     * @return
     */
    PageModelOrderVo<OrderDeliveryDto>  memberDeliveryOrder(Map<String,Object> map);
    
    /**
     * 平台用户开票操作
     * @param map 参数
     * @return
     * @throws Exception
     */
    @Transactional
    RestCode bill4member(Map<String,Object> map) throws Exception;
	
    /**
     * 承运商我的订单
     * @param par  参数
     * @return
     */
    PageModelOrderVo<OrderDeliveryDto> selectDeliveryList(Map<String,Object> par);
    
    /**
     * 承运商订单详情
     * @param par 参数
     * @return
     */
    HashMap<String,Object> selectDetailByDeliveryPkOnSupplier(Map<String,Object> par);
    
    /**
     * 承运商拆单
     * @param deliveryPk  运货单pk
     * @param boxes  拆单箱数
     * @throws Exception
     */
    @Transactional
    void orderSplit(String deliveryPk,Integer boxes) throws Exception;
    
    /**
     * 承运商指派车辆
     * @param par  参数
     * @return
     * @throws Exception
     */
    @Transactional
    boolean assignVehicle(Map<String,Object> par) throws Exception;
    
    /**
     * 承运商确认提货
     * @param deliveryPk  运货单pk
     * @throws Exception
     */
    @Transactional
    void deliveryConfirmation(String deliveryPk) throws Exception;
    
    /**
     * 承运商取消指派车辆
     * @param deliveryPk  运货单pk
     * @throws Exception
     */
    @Transactional
    void cancelAssign(String deliveryPk) throws Exception;
    
    /**
     * 承运商确认送达
     * @param deliveryPk 运货单pk
     * @throws Exception
     */
    @Transactional
    void confirmReceived(String deliveryPk) throws Exception;
    
    /**
     * 查询运货单详情
     * @param pk 运货单pk
     * @return
     */
    LgDeliveryOrderDto getBypk(String pk);
    
    /**
     * 根据orderPk查询所有订单（包括子订单）的状态
     * @param orderPk  运货单pk
     * @return
     */
    List<Integer> getAllStatusByOrderPk(String orderPk);
    
    /**
     * 承运商派送中发货单-异常反馈
     * @param map  参数
     * @throws Exception
     */
    @Transactional
    void abnormalFeedback(Map<String,Object> map) throws Exception;
    
    /**
     * 根据运货单查询运货单详情
     * @param map 参数
     * @return
     */
    OrderDeliveryDto selectDetailByDeliveryPk(Map<String,Object> map);
    
    /**
     * 承运商开票订单列表
     * @param map  参数
     * @return
     */
    PageModelOrderVo<OrderDeliveryDto>  supplierBillsDeliveryOrder(Map<String,Object> map);
    
    /**
     * 承运商开票发货单导出
     * @param map 参数
     * @return
     */
    List<OrderDeliveryDto>  exportInvoiceDeliveryOrder(Map<String,Object> map);
    
    /**
     * 承运商开票申请
     * @param map 参数
     * @return
     */
    @Transactional
    boolean supplierBillOrders(Map<String, Object> map);

    /**
     * 承运商收入明细
     * @param map  参数
     * @return
     */
    PageModelOrderVo<OrderDeliveryDto> supplierIncomeDetail(Map<String, Object> map);
    
    /**
     * 承运商收入明细导出
     * @param map 参数
     * @return
     */
    List<OrderDeliveryDto> supplierIncomeDetailExport(Map<String, Object> map);
    
    /**
     * 未付款的订单第二天自动关闭
     */
    public void autoClosePay();
    
    /**
     * 商城确认发货，同步订单到物流系统
     * @param lgDeliveryOrderForB2BDto 参数
     * @return
     */
    boolean confirmFaHuoForB2B(LgDeliveryOrderForB2BDto lgDeliveryOrderForB2BDto );
    
    /**
     * 未付款的订单第二天自动关闭
     */
    public void closeConfirmOrder();
    
    
}
