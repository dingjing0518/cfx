package cn.cf.dao;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface B2bOrderDaoEx extends B2bOrderDao{
	
	/**
	 * 查询再一定时间范围内累计成交采购商家数量
	 * @param map
	 * @return
	 */
	Integer selectTotalPurCountInPeriodTime(Map<String,Object> map);
	
	/**
	 * 查询在一定时间范围内累计成交供应商家数量
	 * @param map
	 * @return
	 */
	Integer selectTotalSupCountInPeriodTime(Map<String,Object> map);
	
	/**
	 * 查询在一定时间范围内累计成交供应商家数量,排除本次订单
	 * @param map
	 * @return
	 */
	Integer checkLastTotalSupCountInPeriodTime(@Param("memberPk")String memberPk,@Param("periodTime")int periodTime,
			@Param("orderNumber")String orderNumber,@Param("periodTimeStart")Date periodTimeStart);
	
	/**
	 * 查询在一定时间内某个业务员白条订单的累计成交供应商家数
	 * @param map
	 * @return
	 */
	//Integer selectTotalSupCountInPeriodTimeFor1(Map<String,Object> map);
	
	/**
	 * 查询在一定时间内某个业务员白条订单的累计成交供应商家数(除了当前订单)
	 * @param map
	 * @return
	 */
	//Integer selectBeforeTotalSupCountInPeriodTimeFor1(Map<String,Object> map);
	
	/**
	 * 一个业务员 线下支付 订单累计成交的供应商家数
	 * @param map
	 * @return
	 */
	//Integer selectTotalSupCountInPeriodTimeFor2(Map<String, Object> map);
	
	
	/**
	 * 一个业务员 线下支付 订单累计成交供应商家数（除了当前订单）
	 * @param map
	 * @return
	 */
	//Integer selectBeforeTotalSupCountInPeriodTimeFor2(Map<String, Object> map);
	
	
	
	
	
}
