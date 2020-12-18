package cn.cf.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.BidGoodsAll;
import org.tempuri.BidGoodsAllResponse;
import org.tempuri.BindGoodsAll;
import org.tempuri.BindGoodsAllResponse;
import org.tempuri.ContractSync;
import org.tempuri.ContractSyncResponse;
import org.tempuri.GoodsAll;
import org.tempuri.GoodsAllResponse;
import org.tempuri.GoodsPrice;
import org.tempuri.GoodsPriceResponse;
import org.tempuri.GoodsStock;
import org.tempuri.GoodsStockResponse;
import org.tempuri.GoodsVisable;
import org.tempuri.GoodsVisableResponse;
import org.tempuri.LogisticsRuleResponse;
import org.tempuri.OrderSync;
import org.tempuri.OrderSyncResponse;
import org.tempuri.SubmitContract;
import org.tempuri.SubmitContractResponse;
import org.tempuri.SyncAddress;
import org.tempuri.SyncAddressResponse;

import CRM.B2B.shenghong.BidGoodsAll_Out_SynBindingStub;
import CRM.B2B.shenghong.BidGoodsAll_Out_SynServiceLocator;
import CRM.B2B.shenghong.BindGoodsAll_Out_SynBindingStub;
import CRM.B2B.shenghong.BindGoodsAll_Out_SynServiceLocator;
import CRM.B2B.shenghong.ContractSubmit_Out_SynBindingStub;
import CRM.B2B.shenghong.ContractSubmit_Out_SynServiceLocator;
import CRM.B2B.shenghong.ContractSync_Out_SynBindingStub;
import CRM.B2B.shenghong.ContractSync_Out_SynServiceLocator;
import CRM.B2B.shenghong.GoodsAll_Out_SynBindingStub;
import CRM.B2B.shenghong.GoodsAll_Out_SynServiceLocator;
import CRM.B2B.shenghong.GoodsPrice_Out_SynBindingStub;
import CRM.B2B.shenghong.GoodsPrice_Out_SynServiceLocator;
import CRM.B2B.shenghong.GoodsStock_Out_SynBindingStub;
import CRM.B2B.shenghong.GoodsStock_Out_SynServiceLocator;
import CRM.B2B.shenghong.GoodsVisable_Out_SynBindingStub;
import CRM.B2B.shenghong.GoodsVisable_Out_SynServiceLocator;
import CRM.B2B.shenghong.LogisticsRule_Out_SynBindingStub;
import CRM.B2B.shenghong.LogisticsRule_Out_SynServiceLocator;
import CRM.B2B.shenghong.OrderSync_Out_SynBindingStub;
import CRM.B2B.shenghong.OrderSync_Out_SynServiceLocator;
import CRM.B2B.shenghong.SyncAddress_Out_SynBindingStub;
import CRM.B2B.shenghong.SyncAddress_Out_SynServiceLocator;
import cn.cf.common.Constant;
import cn.cf.constant.Block;
import cn.cf.dto.B2bAddressDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.entity.ErpGoodsSync;
import cn.cf.json.JsonUtils;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bWareHouseNumberService;
import cn.cf.service.CommonService;
import cn.cf.service.CrmShService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CrmShServiceImpl implements CrmShService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private B2bGoodsService b2bGoodsService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private B2bWareHouseNumberService b2bWareHouseNumberService;
	
	/**
	 * 盛虹同步订单
	 */
	@Override
	public String syncOrder(String jsonData) {
		String data = null;
		try {
			OrderSync orderSyncW = new OrderSync(jsonData);
			URL realUrl = new URL(new OrderSync_Out_SynServiceLocator().getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			OrderSync_Out_SynBindingStub orderSync_Out_SynBindingStub = new OrderSync_Out_SynBindingStub(realUrl,service);
			OrderSyncResponse rsp = orderSync_Out_SynBindingStub.orderSync_Out_Syn(orderSyncW);
			data = rsp.getOrderSyncResult();
		} catch (Exception e) {
			logger.error("syncOrderByShenghong",e);
			data="{\"status\":\"F\",\"message\":\""+e+"\"}";
		}
		return data;
	}

	
	/**
	 * 盛虹同步合同
	 */
	@Override
	public String syncContract(String jsonData) {
		String data = null;
		try {
			ContractSync contractSync = new ContractSync(jsonData);
			URL realUrl = new URL(new ContractSync_Out_SynServiceLocator().getHTTPS_PortAddress());	
			org.apache.axis.client.Service	service = new org.apache.axis.client.Service();
			ContractSync_Out_SynBindingStub contractSync_Out_SynBindingStub = new ContractSync_Out_SynBindingStub(realUrl,service);
			ContractSyncResponse rsp = contractSync_Out_SynBindingStub.contractSync_Out_Syn(contractSync);
			data = rsp.getContractSyncResult();
		} catch (Exception e) {
			logger.error("syncContractByShenghong",e);
			data="{\"status\":\"F\",\"message\":\""+e+"\"}";
		}
		return data;
	}

	/**
	 * 盛虹同步商品
	 */
	@Override
	public String syncGoods() {
		String data = null;
		try {
			GoodsAll s = new GoodsAll();
			URL realURL=new URL(new GoodsAll_Out_SynServiceLocator().getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();	
			GoodsAll_Out_SynBindingStub goodsAll_Out_SynBindingStub = new GoodsAll_Out_SynBindingStub(realURL,service);
			GoodsAllResponse response = goodsAll_Out_SynBindingStub.goodsAll_Out_Syn(s);
			data = response.getGoodsAllResult();
			logger.info("------SH-syncGoods-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsByShenghong",e);
		}
		return data;
	}
	
	
	/**
	 * 盛虹同步库存
	 */
	@Override
	public String syncGoodsStock() {
		String data = null;
		try {
			GoodsStock s = new GoodsStock();
			URL realURL=new URL(new GoodsStock_Out_SynServiceLocator().getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();	
			GoodsStock_Out_SynBindingStub goodsStock_Out_SynBindingStub = new GoodsStock_Out_SynBindingStub(realURL,service);
			GoodsStockResponse response = goodsStock_Out_SynBindingStub.goodsStock_Out_Syn(s);
			data = response.getGoodsStockResult();
			logger.info("------SH-syncGoodsStock-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsStockByShenghong",e);
		}
		return data;
	}
	
	
	/**
	 * 盛虹同步商品价格
	 */
	@Override
	public String syncGoodsPrice() {
		String data = null;
		try {
			GoodsPrice s = new GoodsPrice();
			URL realURL=new URL(new GoodsPrice_Out_SynServiceLocator().getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();	
			GoodsPrice_Out_SynBindingStub goodsPrice_Out_SynBindingStub = new GoodsPrice_Out_SynBindingStub(realURL,service);
			GoodsPriceResponse response = goodsPrice_Out_SynBindingStub.goodsPrice_Out_Syn(s);
			data = response.getGoodsPriceResult();
			logger.info("------SH-syncGoodsPrice-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsPriceByShenghong",e);
		}
		return data;
	}
	
	
	/**
	 * 盛虹同步商品隐藏状态接口
	 */
	@Override
	public String syncGoodsShow() {
		String data = null;
		try {
			GoodsVisable goodsVisable = new GoodsVisable();
			URL realUrl = new URL(new GoodsVisable_Out_SynServiceLocator().getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			GoodsVisable_Out_SynBindingStub goodsVisable_Out_SynBindingStub = new GoodsVisable_Out_SynBindingStub(realUrl,service);
			GoodsVisableResponse response = goodsVisable_Out_SynBindingStub.goodsVisable_Out_Syn(goodsVisable);
			data = response.getGoodsVisableResult();
			logger.info("------SH-syncGoodsShow-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsShowByShenghong",e);
		}
		return data;
	}
	
	
	/**
	 * 盛虹同步竞价商品
	 */
	@Override
	public String syncAuctionGoods() {
		String data = null;
		try {
			BidGoodsAll s = new BidGoodsAll();
	    	URL realURL=new URL(new BidGoodsAll_Out_SynServiceLocator().getHTTPS_PortAddress());
	    	org.apache.axis.client.Service service = new org.apache.axis.client.Service();	
	    	BidGoodsAll_Out_SynBindingStub bidGoodsAll_Out_SynBindingStub = new BidGoodsAll_Out_SynBindingStub(realURL,service);
	    	BidGoodsAllResponse response = bidGoodsAll_Out_SynBindingStub.bidGoodsAll_Out_Syn(s);
	    	data = response.getBidGoodsAllResult();
	    	logger.info("------SH-syncAuctionGoods-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsAuctionByShenghong",e);
		}
		return data;
	}
	
	
	/**
	 * 盛虹同步捆绑商品
	 */
	@Override
	public String syncBindGoods() {
		String data = null;
		try {
			BindGoodsAll s = new BindGoodsAll();
        	URL realURL=new URL(new BindGoodsAll_Out_SynServiceLocator().getHTTPS_PortAddress());
        	org.apache.axis.client.Service service = new org.apache.axis.client.Service();	
        	BindGoodsAll_Out_SynBindingStub bindGoodsAll_Out_SynBindingStub = new BindGoodsAll_Out_SynBindingStub(realURL,service);
        	BindGoodsAllResponse response = bindGoodsAll_Out_SynBindingStub.bindGoodsAll_Out_Syn(s);
        	data = response.getBindGoodsAllResult();
        	logger.info("------SH-syncBindGoods-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsAuctionByShenghong",e);
		}
		return data;
	}
	
	
	/**
	 * 盛虹同步客户地址
	 */
	@Override
	public String syncAddress(LinkedHashMap<String,Object> params) {
		String data = null;
		try {
        	String companyName = params.get("companyName").toString();
			String organizationCode = params.get("organizationCode").toString();
			SyncAddress s = new SyncAddress(companyName,organizationCode);
        	URL realURL=new URL(new SyncAddress_Out_SynServiceLocator().getHTTPS_PortAddress());
        	org.apache.axis.client.Service service = new org.apache.axis.client.Service();	
        	SyncAddress_Out_SynBindingStub address_Out_SynBindingStub = new SyncAddress_Out_SynBindingStub(realURL,service);
        	SyncAddressResponse response = address_Out_SynBindingStub.syncAddress_Out_Syn(s);
        	data = response.getSyncAddressResult();
        	logger.info("------SH-syncAddress-jsonData-----------------:" + data);
		} catch (Exception e) {
			logger.error("syncGoodsAuctionByShenghong",e);
		}
		return data;
	}
	
	@Override
	public List<B2bGoodsDtoEx> getGoodsPriceList(String storePk, String jsonData,Boolean openFlag) {
		List<B2bGoodsDtoEx> goodslist =new ArrayList<B2bGoodsDtoEx>();
		JSONArray array = getJsonArray(jsonData);
		if (null!=array && array.size()>0) {
			for (int i = 0; i < array.size(); i++) {
				B2bGoodsDtoEx b2bgoodsEx = JsonUtils.toBean(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				b2bgoodsEx.setStorePk(storePk);
				b2bgoodsEx.setPk1(null == b2bgoodsEx.getBatchNumber()?"":b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(null == b2bgoodsEx.getDistinctNumber()?"":b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(null == b2bgoodsEx.getGradeName()?"":b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(null == b2bgoodsEx.getPackNumber()?"":b2bgoodsEx.getPackNumber());
				//如果是开店状态不处理上架的商品
//				if (openFlag) {
//					b2bgoodsEx.setIsUpDown(2);
//				}
				Double price = b2bgoodsEx.getPrice();
				List<B2bGoodsDtoEx> gdtoList = b2bGoodsService.searchGoodsList(b2bgoodsEx);
				for (B2bGoodsDtoEx tempGoodsDto : gdtoList) {
					if (null == b2bgoodsEx.getSalePrice()) {
						tempGoodsDto.setSalePrice(price);
					}else {
						tempGoodsDto.setSalePrice(b2bgoodsEx.getSalePrice());
					}
					tempGoodsDto.setPrice(ArithUtil.div(price, 1000, 2));
					tempGoodsDto.setTonPrice(price);
					B2bGoodsDtoMa goodsDtoMa = commonService.getB2bGoodsDtoMa(tempGoodsDto.getPk());
					CfGoods cfGoods = goodsDtoMa.getCfGoods();
					if (null == b2bgoodsEx.getPackageFee()) {
						cfGoods.setPackageFee(0.0);
					}else {
						cfGoods.setPackageFee(b2bgoodsEx.getPackageFee());
					}
					if (null == b2bgoodsEx.getLoadFee()) {
						cfGoods.setLoadFee(0.0);
					}else {
						cfGoods.setLoadFee(b2bgoodsEx.getLoadFee());
					}
					if (null == b2bgoodsEx.getAdminFee()) {
						cfGoods.setAdminFee(0.0);
					}else{
						cfGoods.setAdminFee(b2bgoodsEx.getAdminFee());
					}
					tempGoodsDto.setGoodsInfo(JSON.toJSONString(cfGoods));
					goodslist.add(tempGoodsDto);
				}
			}
		}
		return goodslist;
	}


	@Override
	public List<B2bGoodsDtoEx> getGoodsStockList(String storePk, String jsonData, Boolean openFlag) {
		JSONArray array = getJsonArray(jsonData);
		List<B2bGoodsDtoEx> list = new ArrayList<B2bGoodsDtoEx>();
		if (null != array && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				B2bGoodsDtoEx b2bgoodsEx = JSON.parseObject(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				if (null == b2bgoodsEx) {
					continue;
				}
				b2bgoodsEx.setStorePk(storePk);
				b2bgoodsEx.setPk1(null == b2bgoodsEx.getBatchNumber()?"":b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(null == b2bgoodsEx.getDistinctNumber()?"":b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(null == b2bgoodsEx.getGradeName()?"":b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(null == b2bgoodsEx.getPackNumber()?"":b2bgoodsEx.getPackNumber());
				B2bGoodsDto gdto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				if (null!=gdto) {
					B2bGoodsDtoMa goodsDtoMa = new B2bGoodsDtoMa();
					goodsDtoMa.UpdateMine(gdto);
					CfGoods cfGoods = goodsDtoMa.getCfGoods();
					if (null!=cfGoods&&null!= cfGoods.getPk()) {
						//1:如果库位号没给，按“正常产品”去查询库位号
						if (null == b2bgoodsEx.getWarehouseNumber() ||  b2bgoodsEx.getWarehouseNumber().equals("")) {
							Map<String, Object> map = new HashMap<>();
							map.put("type","正常产品");
							map.put("isDelete", 1);
							map.put("storePk", storePk);
							List<B2bWarehouseNumberDto> warehouseNumberList = b2bWareHouseNumberService.searchB2bWareHouseNumber(map);
							if (null!=warehouseNumberList && warehouseNumberList.size()>0) {
								cfGoods.setWarehouseNumber(warehouseNumberList.get(0).getNumber());
								cfGoods.setWarehouseType(warehouseNumberList.get(0).getType());
							}else {
								cfGoods.setWarehouseNumber("");
								cfGoods.setWarehouseType("");
							}
						}else{
							//2:库位号给了，去数据库查询
							cfGoods.setWarehouseNumber(b2bgoodsEx.getWarehouseNumber());
							Map<String, Object> map = new HashMap<>();
							map.put("number", b2bgoodsEx.getWarehouseNumber());
							map.put("isDelete", 1);
							map.put("storePk", storePk);
							List<B2bWarehouseNumberDto> warehouseNumberList = b2bWareHouseNumberService.searchB2bWareHouseNumber(map);
							if (null!=warehouseNumberList && warehouseNumberList.size()>0) {
								cfGoods.setWarehouseType(warehouseNumberList.get(0).getType());
							}else {
								cfGoods.setWarehouseType("正常产品");
							}
						}
						//总重量
						if (null != b2bgoodsEx.getAvailableWeight()) {
							b2bgoodsEx.setTotalWeight(b2bgoodsEx.getAvailableWeight().doubleValue());
						}
						//总箱数
						if (null != b2bgoodsEx.getAvailableBoxes()) {
							b2bgoodsEx.setTotalBoxes(b2bgoodsEx.getAvailableBoxes());
						}
						//如果原来的箱重为0， 则 根据总重量，总箱数重新计算箱重
						b2bgoodsEx.setTareWeight(gdto.getTareWeight());
						if ((null==gdto.getTareWeight()||gdto.getTareWeight()==0d) &&  null != b2bgoodsEx.getAvailableWeight() && b2bgoodsEx.getAvailableWeight().compareTo(BigDecimal.ZERO)==1
								&& null != b2bgoodsEx.getAvailableBoxes() && b2bgoodsEx.getAvailableBoxes()>0) {
							Double tareWeight = 0d;
							if (ArithUtil.mul(ArithUtil.div(b2bgoodsEx.getTotalWeight(), b2bgoodsEx.getTotalBoxes()), 1000)>1000  ) {
								tareWeight = 0d;
							}else {
								tareWeight = ArithUtil.mul(ArithUtil.div(b2bgoodsEx.getTotalWeight(), b2bgoodsEx.getTotalBoxes()), 1000);
							}
							cfGoods.setTareWeight(tareWeight);
							b2bgoodsEx.setTareWeight(tareWeight);
						}
						b2bgoodsEx.setGoodsInfo(JSON.toJSONString(cfGoods));
					}
					b2bgoodsEx.setStorePk(storePk);
					b2bgoodsEx.setPk(gdto.getPk());
					list.add(b2bgoodsEx);
				}
			}
		}
		return list;
	}

	@Override
	public ErpGoodsSync getGoodsList(String storePk, String jsonData, Boolean openFlag) {
		JSONArray array = getJsonArray(jsonData);
		List<B2bGoodsDtoEx> insertGoodsList = new ArrayList<B2bGoodsDtoEx>();
		List<B2bGoodsDtoEx> updateGoodsList = new ArrayList<B2bGoodsDtoEx>();
		ErpGoodsSync eGoods = new ErpGoodsSync();
		if (null != array && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				B2bGoodsDtoEx b2bgoodsEx = JSON.parseObject(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				b2bgoodsEx.setStorePk(storePk);
				b2bgoodsEx.setPk1(null == b2bgoodsEx.getBatchNumber() ? "" : b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(null == b2bgoodsEx.getDistinctNumber() ? "" : b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(null == b2bgoodsEx.getGradeName() ? "" : b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(null == b2bgoodsEx.getPackageNumber() ? "" : b2bgoodsEx.getPackageNumber());
				B2bGoodsDto gdto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				// 如果店铺开启中并且该商品是上架的 则不做处理
				// if (openFlag && null != gdto && gdto.getIsUpdown() == 2) {
				// continue;
				// }
				// 如果店铺开启中并且该商品是下架(或者店铺未开启并且该商品已存在的)的则做更新
				// if ((openFlag && null != gdto && gdto.getIsUpdown() != 2)
				// || (!openFlag && null != gdto)) {
				// b2bgoodsEx.setPk(gdto.getPk());
				// updateGoodsList.add(b2bgoodsEx);
				// }

				// 上下架状态不一致的不更新箱重
				if (null != gdto && ((b2bgoodsEx.getIsUpDown() == 1 && gdto.getIsUpdown() != 2)
						|| (b2bgoodsEx.getIsUpDown() == 2 && gdto.getIsUpdown() != 3) || !openFlag)) {
					b2bgoodsEx.setPk(gdto.getPk());
					// 如果是开店状态箱重为0的不更新
					if (openFlag && null != b2bgoodsEx.getTareWeight() && 0d == b2bgoodsEx.getTareWeight()) {
						b2bgoodsEx.setTareWeight(null);
					}
					b2bgoodsEx.setBlock(Block.CF.getValue());
					updateGoodsList.add(b2bgoodsEx);
				}
				// 如果商品不存在则做新增
				if (null == gdto) {
					b2bgoodsEx.setPk(KeyUtils.getUUID());
					b2bgoodsEx.setIsNewProduct(1);
					b2bgoodsEx.setBlock(Block.CF.getValue());
					setWeightAndPrice(b2bgoodsEx);
					insertGoodsList.add(b2bgoodsEx);
				}
			}
		}
		eGoods.setInsertGoodsList(insertGoodsList);
		eGoods.setUpdateGoodsList(updateGoodsList);
		return eGoods;
	}


	private void setWeightAndPrice(B2bGoodsDtoEx b2bgoodsEx) {
		if(null==b2bgoodsEx.getPackageFee()){
			b2bgoodsEx.setPackageFee(0d);
		}
		if(null==b2bgoodsEx.getLoadFee()){
			b2bgoodsEx.setLoadFee(0d);
		}
		if(null==b2bgoodsEx.getAdminFee()){
			b2bgoodsEx.setAdminFee(0d);
		}
		if(null==b2bgoodsEx.getTotalBoxes()){
			b2bgoodsEx.setTotalBoxes(0);
		}
		if(null==b2bgoodsEx.getTotalWeight()){
			b2bgoodsEx.setTotalWeight(0d);
		}
		if (null == b2bgoodsEx.getPrice()) {
			b2bgoodsEx.setPrice(0.0);
		}
		if (null == b2bgoodsEx.getTonPrice()) {
			b2bgoodsEx.setTonPrice(0.0);
		}
		if (null == b2bgoodsEx.getSalePrice()) {
			b2bgoodsEx.setSalePrice(0.0);
		}
	}


	private JSONArray getJsonArray(String jsonData) {
		JSONArray array = null; 
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		if (null != jsonObject && !"".equals(jsonObject.get("status")) && Constant.RESPONSE_STATUS_SUCCESS.equals(jsonObject.get("status"))) {
			array = new JSONArray();
			if (jsonObject.get("message") != null || !"".equals(jsonObject.get("message").toString())) {
				array = JSONArray.parseArray(jsonObject.get("message").toString());
			}
		}
		return array;
	}


	
	/**
	 * 同步盛虹物流模板
	 */
	@Override
	public String syncLogistics() {
		String data="";
		try {
			org.tempuri.LogisticsRule logisticsRule = new org.tempuri.LogisticsRule();
			URL realUrl = new URL(new LogisticsRule_Out_SynServiceLocator().getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			LogisticsRule_Out_SynBindingStub logisticsRule_Out_SynBindingStub = new LogisticsRule_Out_SynBindingStub(realUrl,service);
			LogisticsRuleResponse rsp = logisticsRule_Out_SynBindingStub.logisticsRule_Out_Syn(logisticsRule);
			data = rsp.getLogisticsRuleResult();
			logger.error("sh-updateAllLogistics-------------jsonData:"+data);
		} catch (Exception e) {
			logger.error("syncLogisticsByShenghong",e);
		}
		return data;
	}


	@Override
	public List<B2bAddressDtoEx> getAddressList(String jsonData) {
		List<B2bAddressDtoEx> list = null;
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		if (null != jsonObject && !"".equals(jsonObject.get("status")) && Constant.RESPONSE_STATUS_SUCCESS.equals(jsonObject.get("status"))) {
			if (jsonObject.get("message") != null || !"".equals(jsonObject.get("message").toString())) {
				list = JSON.parseArray(jsonObject.get("message").toString(), B2bAddressDtoEx.class);
			}
		}
		return list;
	}


	/**
	 * 合同提交审核
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	@Override
	public String submitAudit(String contractId) throws MalformedURLException, RemoteException {
		String data = null;
		SubmitContract submitContract = new SubmitContract(contractId);
		URL realUrl = new URL(new ContractSubmit_Out_SynServiceLocator().getHTTPS_PortAddress());
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		ContractSubmit_Out_SynBindingStub contractSubmit_Out_SynBindingStub = new ContractSubmit_Out_SynBindingStub(realUrl, service);
		SubmitContractResponse rsp = contractSubmit_Out_SynBindingStub.contractSubmit_Out_Syn(submitContract);
		data = rsp.getSubmitContractResult();
		return data;
	}
	

}
