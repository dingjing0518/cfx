package cn.cf.service;

import java.util.List;

import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.ErpGoodsSync;

/**
 * crm公共同步接口
 * @description:
 * @author FJM
 * @date 2019-3-4 下午2:33:01
 */
public interface CrmBaseService {
	/**
	 * 订单
	 * @param jsonData
	 * @return
	 */
	String syncOrder(String jsonData);
	/**
	 * 合同
	 * @param jsonData
	 * @return
	 */
	String syncContract(String jsonData);
	
	/**
	 * 价格
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	List<B2bGoodsDtoEx> getGoodsPriceList(String storePk, String jsonData,Boolean openFlag);
	/**
	 * 库存
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	List<B2bGoodsDtoEx> getGoodsStockList(String storePk, String jsonData,Boolean openFlag);
	/**
	 * 商品
	 * @param storePk
	 * @param jsonData
	 * @return
	 */
	ErpGoodsSync getGoodsList(String storePk,String jsonData,Boolean openFlag);
	
}
