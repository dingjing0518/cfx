package cn.cf.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import cn.cf.dto.B2bAddressDtoEx;

/**
 * crm-盛虹
 * @description:
 * @author FJM
 * @date 2019-3-4 下午2:35:34
 */
public interface CrmShService extends CrmBaseService{

	/**
	 * 盛虹同步商品，去盛虹取数据
	 * @return 盛虹返回的值
	 */
	String syncGoods();
	
	
	/**
	 * 盛虹同步商品库存，去盛虹取数据
	 * @return 盛虹返回的值
	 */
	String syncGoodsStock();
	
	
	/**
	 * 盛虹同步商品价格,去盛虹取数据
	 * @return 盛虹返回的值
	 */
	String syncGoodsPrice();
	
	
	/**
	 * 盛虹同步商品隐藏状态，去盛虹取数据
	 * @return 盛虹返回的值
	 */
	String syncGoodsShow();
	
	
	/**
	 * 盛虹同步竞价商品，去盛虹取数据
	 * @return 盛虹返回的值
	 */
	String syncAuctionGoods();
	
	
	/**
	 * 盛虹同步捆绑商品，去盛虹取数据
	 * @return  盛虹返回的值
	 */
	String syncBindGoods();
	
	/**
	 * 盛虹同步地址
	 * @return  盛虹返回的值
	 */
	String syncAddress(LinkedHashMap<String,Object> params);
	
	/**
	 * 地址封装
	 * @param jsonData
	 * @return
	 */
	List<B2bAddressDtoEx> getAddressList(String jsonData);
	/**
	 * 盛虹同步运费规则，去盛虹取数据
	 * @return 盛虹返回的值
	 */
	String syncLogistics();
	
	
	/**
	 * 盛虹合同提交审核
	 * @param contractId 合同单号
	 * @return
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	String submitAudit(String contractId) throws MalformedURLException, RemoteException;
	
	
}
