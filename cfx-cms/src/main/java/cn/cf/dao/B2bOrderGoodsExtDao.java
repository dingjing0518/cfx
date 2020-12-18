/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.B2bOrderGoodsExtDto;
import cn.cf.entity.OrderGoodsWeightAmount;
import cn.cf.entity.SupplierSaleDataReport;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOrderGoodsExtDao extends B2bOrderGoodsDao{
	
	List<B2bOrderGoodsExtDto> getB2bOrderGoods(Map<String,Object> map);
	
	int getB2bOrderGoodsCount(Map<String,Object> map);
	
	List<OrderGoodsWeightAmount> getB2bOrderGoodsWA(@Param("orderNumberList")List<String> orderNumberList);
	
	/**
	 * 关闭订单
	 * @param orderNumber
	 * @return
	 */
	int closeOrder(String orderNumber);

	
	List<B2bOrderGoodsExtDto> getSxOrderGoods(Map<String, Object> map);	
	
	List<SupplierSaleDataReport> searchStoreByOrder(@Param("date") String date);

	List<B2bOrderGoodsDto> getByStorePk(Map<String, Object> map);
}
