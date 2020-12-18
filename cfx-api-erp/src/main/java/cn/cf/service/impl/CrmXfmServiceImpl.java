package cn.cf.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.EncodeUtil;
import cn.cf.constant.Block;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.entity.ErpGoodsSync;
import cn.cf.http.HttpHelper;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.CrmXfmService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CrmXfmServiceImpl implements CrmXfmService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private B2bGoodsService b2bGoodsService;
	
	@Override
	public String syncOrder(String jsonData) {
		String data = null;
		try {
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("encodeData", EncodeUtil.des3Encrypt3Base64(jsonData));
			String rest =HttpHelper.doPost(PropertyConfig.getProperty("xfm_url")+"aip/component/http/JSON/aip_default/HXH005", getHeaders(), paraMap, "utf-8");
			logger.info("xfm-orderResult:---------"+rest);
			if(null != rest && !"".equals(rest)){
				JSONObject json = JSONObject.parseObject(rest);
				if(json.containsKey("rows")){
					json = (JSONObject) JSONArray.parseArray(json.getString("rows")).get(0);
					//成功
					if(null != json && json.containsKey("success") && "true".equals(json.getString("success"))){
						data="{\"status\":\"S\",\"message\":\"操作成功\"}";
					}else if(null != json && json.containsKey("success") && "false".equals(json.getString("success")) && json.containsKey("message")){
						data="{\"status\":\"F\",\"message\":\"" + json.getString("message") + " \"}";
					}else{
						data="{\"status\":\"F\",\"message\":\"" + rest + " \"}";
					}
				}else{
					data="{\"status\":\"F\",\"message\":\""+rest+"\"}";
				}
			}else{
				data="{\"status\":\"F\",\"message\":\"crm无返回数据\"}";
			}
		} catch (Exception e) {
			logger.error("syncOrderByXinfengming");
			data="{\"status\":\"F\",\"message\":\""+e+"\"}";
		}
		return data;
	}

	@Override
	public String syncContract(String jsonData) {
		String data = null;
		try {
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("encodeData", EncodeUtil.des3Encrypt3Base64(jsonData));
			String rest =HttpHelper.doPost(PropertyConfig.getProperty("xfm_url")+"aip/component/http/JSON/aip_default/HXH007", getHeaders(), paraMap, "utf-8");
			logger.info("xfm-contractResult:---------"+rest);
			if(null != rest && !"".equals(rest)){
				JSONObject json = JSONObject.parseObject(rest);
				if(json.containsKey("rows")){
					if (JSONArray.parseArray(json.getString("rows")).size()>0) {
						json = (JSONObject) JSONArray.parseArray(json.getString("rows")).get(0);
						//成功
						if (null!=json && json.containsKey("CODE") && "S".equals(json.getString("CODE"))) {
							data="{\"status\":\"S\",\"message\":\"操作成功\"}";
						}else{
							data="{\"status\":\"F\",\"message\":\"" +json.getString("MSG")+ " \"}";
						}
					}else {
						data="{\"status\":\"F\",\"message\":\"crm 返回数据rows内无数据\"}";
					}
				}else{
					data="{\"status\":\"F\",\"message\":\""+rest+"\"}";
				}
			}else{
				data="{\"status\":\"F\",\"message\":\"crm无返回数据\"}";
			}	
		} catch (Exception e) {
			logger.error("syncContractByXinfengming",e);
			data="{\"status\":\"F\",\"message\":\""+e+"\"}";
		}
		return data;
	}
	
	private Header[] getHeaders(){
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		headers[1] = new BasicHeader("Authorization",getXFMAuthorization());
		return headers;
	}
	
	private String getXFMAuthorization() {
	    String auth = PropertyConfig.getProperty("xfm_username") + ":" + PropertyConfig.getProperty("xfm_password");
	    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	    String authHeader = "Basic " + new String(encodedAuth);
	    return authHeader;
	  }

	
	
	@Override
	public List<B2bGoodsDtoEx> getGoodsPriceList(String storePk, String jsonData,Boolean openFlag) {
		List<B2bGoodsDtoEx> list = new ArrayList<B2bGoodsDtoEx>();
		JSONArray array = JSONArray.parseArray(jsonData);
		B2bGoodsDtoEx b2bgoodsEx = new B2bGoodsDtoEx();
		if (null!=array && array.size()>0) {
			for (int i = 0; i < array.size(); i++) {
				b2bgoodsEx = JSON.parseObject(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				b2bgoodsEx.setStorePk(storePk);
				b2bgoodsEx.setPk1(null == b2bgoodsEx.getBatchNumber()?"":b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(null == b2bgoodsEx.getDistinctNumber()?"":b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(null == b2bgoodsEx.getGradeName()?"":b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(null == b2bgoodsEx.getPackNumber()?"":b2bgoodsEx.getPackNumber());
				//如果是开店状态不处理上架的商品
//				if (openFlag) {
//					b2bgoodsEx.setIsUpDown(2);
//				}
				B2bGoodsDto gdto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				b2bgoodsEx.setPk(gdto.getPk());
				b2bgoodsEx.setGoodsInfo(gdto.getGoodsInfo());
				Double price = b2bgoodsEx.getPrice();
				b2bgoodsEx.setPrice(ArithUtil.div(price, 1000, 2));
				b2bgoodsEx.setTonPrice(price);
				if (null == b2bgoodsEx.getSalePrice() || 0d == b2bgoodsEx.getSalePrice()) {
					b2bgoodsEx.setSalePrice(price);
				}
				if (b2bgoodsEx.getPackageFee() == null) {
					b2bgoodsEx.setPackageFee(0.0);
				}
				if (b2bgoodsEx.getLoadFee() == null) {
					b2bgoodsEx.setLoadFee(0.0);
				}
				if (b2bgoodsEx.getAdminFee() == null) {
					b2bgoodsEx.setAdminFee(0.0);
				}
				list.add(b2bgoodsEx);
			}
		}
		return list;
	}

	@Override
	public List<B2bGoodsDtoEx> getGoodsStockList(String storePk, String jsonData,Boolean openFlag) {
		JSONArray array = JSON.parseArray(jsonData);
		List<B2bGoodsDtoEx> list = new ArrayList<B2bGoodsDtoEx>();
		B2bGoodsDtoEx b2bgoodsEx = null;
		if (null != array && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				b2bgoodsEx = JSON.parseObject(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				if (null == b2bgoodsEx) {
					continue;
				}
				b2bgoodsEx.setStorePk(storePk);
				b2bgoodsEx.setPk1(null == b2bgoodsEx.getBatchNumber() ? "" : b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(null == b2bgoodsEx.getDistinctNumber() ? "" : b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(null == b2bgoodsEx.getGradeName() ? "" : b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(null == b2bgoodsEx.getPackNumber() ? "" : b2bgoodsEx.getPackNumber());
				B2bGoodsDto gdto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				if (null!=gdto) {
					B2bGoodsDtoMa goodsDtoMa = new B2bGoodsDtoMa();
					goodsDtoMa.UpdateMine(gdto);
					CfGoods cfGoods = goodsDtoMa.getCfGoods();
					if (null!=cfGoods && null!=cfGoods.getPk()) {
						// 如果是开店状态并且商品是上架状态 则不做处理
						// if (openFlag) {
						// b2bgoodsEx.setIsUpDown(2);
						// }
						if (null != b2bgoodsEx.getAvailableWeight()) {
							b2bgoodsEx.setTotalWeight(b2bgoodsEx.getAvailableWeight().doubleValue());
						}
						if (null != b2bgoodsEx.getAvailableBoxes()) {
							b2bgoodsEx.setTotalBoxes(b2bgoodsEx.getAvailableBoxes());
						}
						//如果原来的箱重为0， 则 根据总重量，总箱数重新计算箱重
						b2bgoodsEx.setTareWeight(gdto.getTareWeight());
						if ((null==gdto.getTareWeight()||gdto.getTareWeight()==0d) &&  null != b2bgoodsEx.getAvailableWeight() && null != b2bgoodsEx.getAvailableBoxes()) {
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
	public ErpGoodsSync getGoodsList(String storePk, String jsonData,Boolean openFlag) {
		JSONArray array = JSONArray.parseArray(jsonData);
		List<B2bGoodsDtoEx> insertGoodsList = new ArrayList<B2bGoodsDtoEx>();
		List<B2bGoodsDtoEx> updateGoodsList = new ArrayList<B2bGoodsDtoEx>();
		ErpGoodsSync eGoods = new ErpGoodsSync();
		B2bGoodsDtoEx b2bgoodsEx = null;
		if (null!=array && array.size()>0) {
			for (int i = 0; i < array.size(); i++) {
				b2bgoodsEx = JSON.parseObject(array.getJSONObject(i).toString(), B2bGoodsDtoEx.class);
				b2bgoodsEx.setPk1(b2bgoodsEx.getBatchNumber());
				b2bgoodsEx.setPk2(b2bgoodsEx.getDistinctNumber());
				b2bgoodsEx.setPk3(b2bgoodsEx.getGradeName());
				b2bgoodsEx.setPk4(b2bgoodsEx.getPackageNumber());
				b2bgoodsEx.setStorePk(storePk);
				B2bGoodsDto gdto = b2bGoodsService.searchGoodsIs(b2bgoodsEx);
				//上下架状态不一致的不更新
				if (null != gdto && gdto.getIsUpdown() != b2bgoodsEx.getIsUpDown()) {
					b2bgoodsEx.setPk(gdto.getPk());
					b2bgoodsEx.setBlock(Block.CF.getValue());
					updateGoodsList.add(b2bgoodsEx);
				}
				// 如果商品不存在则做新增
				if (null == gdto) {
					b2bgoodsEx.setPk(KeyUtils.getUUID());
					b2bgoodsEx.setIsNewProduct(0);
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

	
	/**
	 * 设置商品重量和价格
	 * @param b2bgoodsEx
	 */
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
	
	
	public static void main(String[] args) {
		CrmXfmServiceImpl service=new CrmXfmServiceImpl();
		String data = null;
		try {
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("encodeData", EncodeUtil.des3Encrypt3Base64("{\"actualAmount\":7224.24,\"address\":\"\",\"areaName\":\"\",\"bindProductId\":\"\",\"cityName\":\"\",\"contacts\":\"\",\"contactsTel\":\"\",\"employeeName\":\"\",\"employeeNumber\":\"50016983\",\"items\":[{\"adminFee\":0.0,\"afterBoxes\":0,\"afterWeight\":0.0,\"batchNumber\":\"EP06031-X\",\"childOrderNumber\":\"10\",\"distinctNumber\":\"000000000060020246\",\"gradeName\":\"AA\",\"loadFee\":0.0,\"logisticsPk\":\"\",\"logisticsStepInfoPk\":\"\",\"originalFreight\":0.0,\"originalPrice\":9710.0,\"originalTotalFreight\":0.0,\"packNumber\":\"NEP744\",\"packageFee\":0.0,\"presentFreight\":0.0,\"presentPrice\":9710.0,\"presentTotalFreight\":0.0,\"purchQuantity\":0.744,\"purchWeight\":1,\"warehouseNumber\":\"\",\"warehouseType\":\"正常类型\"}],\"logisticsModelId\":\"3\",\"meno\":\"\",\"orderAmount\":7224.24,\"orderClassify\":1,\"orderNumber\":\"201904041315428173797\",\"orderTime\":\"2019-04-04\",\"organizationCode\":\"9133042468786391X8\",\"purchaseType\":2,\"purchaserId\":\"4df109d21f6b4c0b818d1ecaff50ca61\",\"purchaserName\":\"苏州化纤新区公司\",\"signingCompany\":\"\",\"supplierName\":\"桐乡市中维化纤有限公司\",\"townName\":\"\"}"));
			String rest =HttpHelper.doPost(PropertyConfig.getProperty("xfm_url")+"aip/component/http/JSON/aip_default/HXH005", service.getHeaders(), paraMap, "utf-8");
			if(null != rest && !"".equals(rest)){
				JSONObject json = JSONObject.parseObject(rest);
				if(json.containsKey("rows")){
					json = (JSONObject) JSONArray.parseArray(json.getString("rows")).get(0);
					//成功
					if(null != json && json.containsKey("success") && "true".equals(json.getString("success"))){
						data="{\"status\":\"S\",\"message\":\"操作成功\"}";
					}else if(null != json && json.containsKey("success") && "false".equals(json.getString("success")) && json.containsKey("message")){
						data="{\"status\":\"F\",\"message\":\"" + json.getString("message") + " \"}";
					}else{
						data="{\"status\":\"F\",\"message\":\"" + rest + " \"}";
					}
				}else{
					data="{\"status\":\"F\",\"message\":\""+rest+"\"}";
				}
			}else{
				data="{\"status\":\"F\",\"message\":\"crm无返回数据\"}";
			}
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
