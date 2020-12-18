package cn.cf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.OrderDeliveryDto;
import cn.cf.entity.SearchLgLine;

public interface LgDeliveryOrderDaoEx extends LgDeliveryOrderDao{
	
	//我的订单
	List<OrderDeliveryDto> selectDeliveryList(Map<String,Object> par);
	
	//承运商业务员的订单
	List<OrderDeliveryDto> selectDeliveryListForMember(Map<String,Object> par);
	
	//订单数量
	Integer selectDeliveryCount(Map<String,Object> par);
	
	//承运商业务员的订单数量
	Integer selectDeliveryCountForMember(Map<String,Object> par);
	
	//查询订单详情
	OrderDeliveryDto selectDetailByPk(Map<String,Object> par);
	
	//根据Pk检查订单的状态
	int checkOrderStatusByPk(@Param("deliveryPk") String deliveryPk);

	//更新部分字段
	int updateByPkSelective(LgDeliveryOrderDto dto);
	
    //查询未匹配的订单
	List<SearchLgLine> selectByStatus(Map<String, Object> map);
	
	int savePushLgCompanyName(Map<String, Object> map);
	
    //付款前获取相关的付款信息
    LgDeliveryOrderDto getInfoBeforePay(String deliveryPk);
    
    //平台用户付款（orderStatus:9->8）
    Integer payForLogistics(@Param("deliveryPk") String deliveryPk,@Param("paymentPk") String paymentPk,
    		@Param("paymentName") String paymentName,@Param("tempDate")Date tempDate);
    
    //查询订单信息（再来一单）
    OrderDeliveryDto getInfo4MoreOrder(Map<String, Object> par);
    
    //平台用户开票订单管理
    List<OrderDeliveryDto> selectInvoiceList4member(Map<String,Object> par);
    
    //平台用户开票订单数量
    Integer selectInvoiceCount4member(Map<String,Object> par);
    
	//根据订单编号查询子订单的数量
	int getDeliveryCountByOrderPk(@Param("orderPk") String orderPk);
	
    int updateByPk(LgDeliveryOrderDto dto);
    
	//根据orderPk查询所有订单（包括子订单）的状态
	List<Integer> getAllStatusByOrderPk(@Param("orderPk") String orderPk);
	
    //查询承运商开票订单列表
    List<OrderDeliveryDto> selectInvoiceList4supplier(Map<String, Object> map);
    
    //查询承运商开票订单数量
    Integer selectInvoiceCount4supplier(Map<String,Object> par);
    
    //承运商导开票管理导出订单
    List<OrderDeliveryDto> exportInvoiceDeliveryOrder(Map<String,Object> map);
    
    //承运商收入明细订单列表
    List<OrderDeliveryDto> supplierIncomeDetailList(Map<String, Object> map);
    
    //承运商收入明细订单数量
    Integer supplierIncomeDetailCount(Map<String,Object> par);
    
    //承运商收入明细订单导出
    List<OrderDeliveryDto> supplierIncomeDetailExport(Map<String, Object> map);
    
	//关闭订单
	int closeOrder(String pk);
	
	//查询  第二天未付款的订单自动关闭   的所有订单
	List<String> searchAutoClosePayOrders();
	
	
	//待财务确认订单，48小时后关闭
	List<String> searchCloseConfirmOrder();
	
	
	//根据deliveryNumber查询主键
	String getPkByDeliveryNumber(@Param("deliveryNumber") String deliveryNumber);
	
	//根据pk修改deliveryNumber
	int updateDeliveryNumberByPk(@Param("pk") String pk,@Param("deliveryNumber") String deliveryNumber);
	
}
