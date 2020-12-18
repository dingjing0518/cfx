package cn.cf.service;

import com.alibaba.fastjson.JSONArray;
import java.util.List;

import cn.cf.dto.B2bAddressDtoEx;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.B2bOrderGoodsUpdateOrderShiped;
import cn.cf.entity.B2bOrderGoodsUpdateOrderStatus;
import cn.cf.entity.B2bOrderGoodsUpdateWeight;
import cn.cf.entity.ErpGoodsSync;



public interface HuaxianhuiService {
	
	/**
	 * 同步商品
	 * @param storePk
	 * @param jsonArray
	 * @throws Exception
	 */
	@Transactional
	void syncGoods(String storePk,ErpGoodsSync eGoods,Boolean openFlag,Boolean showFlag);
	
	
	/**
	 * 同步商品库存
	 * @param storePk
	 * @param JSONArray
	 * @throws Exception
	 */
	@Transactional
	void syncGoodsStock(String storePk,List<B2bGoodsDtoEx> list,Boolean openFlag);
	
	
	
	/**
	 * 同步商品价格
	 * @param storePk
	 * @param list
	 * @param openFlag
	 * @throws Exception
	 */
	@Transactional
	void syncGoodsPrice(String storePk,List<B2bGoodsDtoEx> list,Boolean openFlag);
	
	/**
	 * 同步商品隐藏状态
	 * @param storePk
	 * @param jsonData
	 * @throws Exception
	 */
	void syncGoodsShow(String storePk,JSONArray jsonArray);
	
	
	
	/**
	 * 同步竞价商品
	 * @param storePk
	 * @param jsonData
	 * @throws Exception
	 */
	void syncAuctionGoods(String storePk,JSONArray jsonArray);
	
	
	
	/**
	 * 同步拼团商品
	 * @param storePk
	 * @param jsonData
	 * @throws Exception
	 */
	@Transactional
	void syncBindGoods(String storePk,JSONArray jsonArray);
	
	
	
	/**
	 * 同步客戶地址
	 * @param storePk
	 * @param jsonData
	 * @throws Exception
	 */
	@Transactional
	void syncAddress(String companyPk,List<B2bAddressDtoEx> addressList);
	
	/**
	 * 订单分货改价
	 * @param orderNumber
	 * @param items
	 * @throws Exception
	 */
	@Transactional
	void updateDivision(String orderNumber, List<B2bOrderGoodsUpdateWeight> items);
	
	/**
	 * 订单修改状态
	 * @param orderNumber
	 * @param items
	 * @throws Exception
	 */
	@Transactional
	void updateOrderStatus(String orderNumber,List<B2bOrderGoodsUpdateOrderStatus> items);
	
	/**
	 * 订单发货
	 * @param orderNumber
	 * @param items
	 * @throws Exception
	 */
	@Transactional
	void updateOrderShiped(String orderNumber,List<B2bOrderGoodsUpdateOrderShiped> items);
	
	
	/**
	 * 处理盛虹物流模板的数据
	 * @param storePk
	 * @param jsonData
	 * @throws Exception
	 */
	@Transactional
	void updateAllLogistics(String storePk,String jsonData,String block);
	
}
