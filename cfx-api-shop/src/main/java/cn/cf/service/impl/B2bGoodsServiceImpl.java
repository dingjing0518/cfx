package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.ApiServer;
import cn.cf.constant.Block;
import cn.cf.constant.UnitType;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dao.LuceneDao;
import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.Sessions;
import cn.cf.http.HttpHelper;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bGoodsEx;
import cn.cf.property.PropertyConfig;
import cn.cf.service.B2bGoodsService;
import cn.cf.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

@Service
public class B2bGoodsServiceImpl implements B2bGoodsService {

	@Autowired
	private B2bGoodsDaoEx goodsDao;

	@Autowired
	private LuceneDao luceneDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bAuctionGoodsDaoEx auctionGoodsDaoEx;
	
	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public PageModel<B2bGoodsDtoEx> searchGoodsList(Map<String, Object> map) {
		if(map.containsKey("searchType")){
			map=searchGoodsBySearchKey(map);
		}
		PageModel<B2bGoodsDtoEx> pm = new PageModel<B2bGoodsDtoEx>();
		List<B2bGoodsDtoEx> list =goodsDao.searchGoodsGrid(map);
		int counts =goodsDao.searchGoodsGridCount(map);
//		if(list.size()==0&&map.containsKey("searchType")){
//			list=goodsDao.searchGoodsGrid(searchGoodsBySearchKey(map));
//			counts =goodsDao.searchGoodsGridCount(searchGoodsBySearchKey(map));
//		}
		pm.setDataList(list);
		pm.setTotalCount(counts);
		if (null != map.get("start")) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}
	private Map<String, Object>   searchGoodsBySearchKey(Map<String, Object> map) {
		Map<String, Object> mapAll=new HashMap<String, Object>();
		mapAll.putAll(map);
		if(null != mapAll.get("searchKey")&&!"".equals(mapAll.get("searchKey"))){
			String searchKeys = luceneDao.luceneBySearchKey(mapAll.get("searchKey").toString());
			if(!"".equals(searchKeys)){
				mapAll.put("searchKeys", searchKeys);
				mapAll.remove("searchKey");
			}
		}
		return mapAll;
	}
 
	 
//	@Override
//	public PageModel<B2bGoodsDtoEx> findIndex(Map<String, Object> map) {
//		PageModel<B2bGoodsDtoEx> pm = new PageModel<B2bGoodsDtoEx>();
//		try {
//			pm = luceneDao.findIndex(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return pm;
//	}

	/**
	 * 当前账号是否已收藏
	 * 
	 * @param goodPk
	 * @param memberPk
	 * @return
	 */
	@Override
	public List<CollectionGoods> iscollected(String goodPk, String memberPk) {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("memberPk").is(memberPk), Criteria
				.where("goodPk").is(goodPk));
		List<CollectionGoods> collections = mongoTemplate.find(new Query(
				criatira), CollectionGoods.class);
		return collections;
	}

 

	@Override
	public Long countCollectGoods(String goodPk) {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("goodPk").is(goodPk));
		return mongoTemplate.count(new Query(criatira), CollectionGoods.class);
	}

 

	@Override
	public void updateGoods(B2bGoodsEx goods) {
		goodsDao.updateGoods(goods);
	}

	@Override
	public void insert(B2bGoodsEx goods) {
		goodsDao.insert(goods);
	}

 

 

	@Override
	public List<B2bGoodsDtoEx> searchUpdownCounts(Map<String, Object> map) {

		return goodsDao.searchUpdownCounts(map);
	}

	@Override
	public RestCode updateMore(B2bGoodsEx good) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			String[] pks = StringUtils.splitStrs(good.getPk());
			//竞拍活动中的商品后无法操作
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pks", pks);
			List<B2bAuctionGoodsDto> alist = auctionGoodsDaoEx.searchAuctingGoods(map);
			if(null != alist && alist.size() > 0){
				restCode = RestCode.CODE_G010;
			}
			//拼团活动中的商品后无法操作
			List<B2bBindGoodsDto> bList = b2bBindGoodsDaoEx.searchBindingGoods(map);
			if(null != bList && bList.size() > 0){
				restCode = RestCode.CODE_G014;
			}
			
			if("0000".equals(restCode.getCode())){
				B2bGoodsEx g = new B2bGoodsEx();
				for (int i = 0; i < pks.length; i++) {
					g.setIsUpdown(good.getIsUpdown());
					g.setUpdateTime(new Date());
					g.setPk(pks[i]);
					goodsDao.updateGoods(g);
					//如果商品下架，1:删除商品在团里的记录;2:删除商品在竞拍里的记录
					if (good.getIsUpdown()==3) {
							b2bBindGoodsDaoEx.delBindGoodsByGoodsPk(pks[i]);
							auctionGoodsDaoEx.deleteByGoodsPk(pks[i]);
					}
				}
			}
		} catch (Exception e) {
			logger.error("updateMore",e);
			restCode = RestCode.CODE_S999;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return restCode;

	}

	@Override
	public RestCode IsGoodsParamNull(JSONObject json) {
		RestCode restCode = RestCode.CODE_0000;
		Map<Object, String> map = new HashMap<Object, String>();
		map.put(json.get("tareWeight"), "箱重");
		map.put(json.get("totalBoxes"), "可用箱数");
		map.put(json.get("totalWeight"), "可用库存");
		map.put(json.get("tonPrice"), "商品挂牌价格");
		map.put(json.get("salePrice"), "商品特卖价格");
		map.put(json.get("unitName"), "商品单位");
		map.put(json.get("upDown"), "商品上下架状态");
		map.put(json.get("pk"), "商品主键");
		restCode = isNull(map);
		if (restCode.getCode().equals("0000")) {
			map = new HashMap<Object, String>();
			map.put(json.get("tareWeight"), "箱重");
			map.put(json.get("totalBoxes"), "可用箱数");
			map.put(json.get("totalWeight"), "可用库存");
			map.put(json.get("tonPrice"), "商品挂牌价格");
			map.put(json.get("salePrice"), "商品特卖价格");
			map.put(json.get("packageFee"), "包装费");
			map.put(json.get("loadFee"), "装车费");
			map.put(json.get("adminFee"), "自提管理费");
			restCode = isLegal(map);
		}
		return restCode;
	}

	private RestCode isNull(Map<Object, String> map) {
		RestCode restCode = RestCode.CODE_0000;
		for (Map.Entry<Object, String> entry : map.entrySet()) {
			if (null == entry.getKey() || "".equals(entry.getKey().toString())) {
				RestCode.CODE_B005.setMsg(entry.getValue() + "不能为空");
				restCode = RestCode.CODE_B005;
				break;
			}
		}
		return restCode;
	}

	private RestCode isLegal(Map<Object, String> map) {
		RestCode restCode = RestCode.CODE_0000;
		for (Map.Entry<Object, String> entry : map.entrySet()) {
			if (null == entry.getKey() || "".equals(entry.getKey().toString())) {
				RestCode.CODE_B005.setMsg(entry.getValue() + "不合法");
				restCode = RestCode.CODE_B005;
				break;
			}
		}
		return restCode;
	}

	 
	

	
 
 

	@Override
	public Map<String, Object> getGoodsByMember(Map<String, Object> pareMap) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberPk", pareMap.get("memberPk").toString());
		String data = HttpHelper
				.doPost(PropertyConfig.getProperty("api_server")
						+ ApiServer.MEMBER.getValue() + "getGoodsByMember", map);
		JSONObject jsonObject = JSONObject.parseObject(data);

		if (jsonObject.getString("code").equals("0000")) {
			if (jsonObject.get("sqlStr").toString().equals("")) {
				pareMap.put("salesmanType", jsonObject.get("salesmanType")
						.toString());
			} else {
				pareMap.put("sqlStr", jsonObject.get("sqlStr").toString());
			}
		}
		return pareMap;
	}

	@Override
	public B2bGoodsDto getOpenDetails(String goodsPk) {
		return goodsDao.getOpenDetails(goodsPk);
	}

	@Override
	public PageModel<B2bGoodsDtoEx> searchGoodsListBySalesMan(
			Map<String, Object> map) {
		PageModel<B2bGoodsDtoEx> pm = new PageModel<B2bGoodsDtoEx>();
		try {
			// ("sqlStr$$$$$$$$$$$$$$$"+map.get("sqlStr"));
			List<B2bGoodsDtoEx> list = goodsDao.searchListBySalesMan(map);
			int count = goodsDao.searchGridCountBySalesMan(map);
			pm.setTotalCount(count);
			pm.setDataList(list);
			if (null != map.get("start")) {
				pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
				pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
			}
		} catch (Exception e) {
			logger.error("searchGoodsListBySalesMan",e);
		}
		return pm;
	}

 

	@Override
	public int searchGridCount(Map<String, Object> map) {
		return goodsDao.searchGridCount(map);
	}

	// 查询供应商销售中的商品数量
	@Override
	public int searchSaleGoodsCounts(Map<String, Object> map) {
		return goodsDao.searchSaleGoodsCounts(map);
	}

	@Override
	public List<B2bGoodsDtoEx> searchGoodsList(Map<String, Object> map,Sessions sessions) {
		B2bCompanyDto b2bCompanyDto = sessions.getCompanyDto();
		String block = b2bCompanyDto.getBlock();
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		if ("-1".equals(sessions.getCompanyDto().getParentPk())) {
			map.put("storePk",
					sessions.getStoreDto() == null || sessions.getStoreDto().getPk()==null ? "" : sessions.getStoreDto().getPk());
		} else {
			map.put("companyPk", sessions.getCompanyDto().getPk());
		}
		map.put("isDelete", 1);
		if (null!= map.get("productPk") && !"".equals(map.get("productPk").toString())) {
			String[] productPk = map.get("productPk").toString().split(",");
			map.put("productPk", productPk);
		}
		if (null!= map.get("technologyPk") && !"".equals(map.get("technologyPk").toString())) {
			String[] technologyPk = map.get("technologyPk").toString().split(",");
			map.put("technologyPk", technologyPk);
		}
		if (null!= map.get("rawMaterialPk") && !"".equals(map.get("rawMaterialPk").toString())) {
			String[] rawMaterialPk = map.get("rawMaterialPk").toString().split(",");
			map.put("rawMaterialPk", rawMaterialPk);
		}
		if (null!= map.get("rawMaterialParentPk") && !"".equals(map.get("rawMaterialParentPk").toString())) {
			String[] rawMaterialParentPk = map.get("rawMaterialParentPk").toString().split(",");
			map.put("rawMaterialParentPk", rawMaterialParentPk);
		}
		if (null!= map.get("specPk") && !"".equals(map.get("specPk").toString())) {
			String[] specPk = map.get("specPk").toString().split(",");
			map.put("specPk", specPk);
		}
		if (null!= map.get("seriesPk") && !"".equals(map.get("seriesPk").toString())) {
			String[] seriesPk = map.get("seriesPk").toString().split(",");
			map.put("seriesPk", seriesPk);
		}
		if (null!= map.get("gradePk") && !"".equals(map.get("gradePk").toString())) {
			String[] gradePk = map.get("gradePk").toString().split(",");
			map.put("gradePk", gradePk);
		}
		if (null!= map.get("varietiesPk") && !"".equals(map.get("varietiesPk").toString())) {
			String[] varietiesPk = map.get("varietiesPk").toString().split(",");
			map.put("varietiesPk", varietiesPk);
		}
		if (null!= map.get("seedvarietyPk") && !"".equals(map.get("seedvarietyPk").toString())) {
			String[] seedvarietyPk = map.get("seedvarietyPk").toString().split(",");
			map.put("seedvarietyPk", seedvarietyPk);
		}
		List<B2bGoodsDtoEx> list=goodsDao.searchGoodsGrid(map);
		Map<String,Object> info = new HashMap<String,Object>();
		//1：化纖類型的公司
		if (Block.CF.getValue().equals(block)) {
			for (B2bGoodsDtoEx dto : list) {
				if (null != dto.getBlock() && Block.CF.getValue().equals(dto.getBlock())) {
					String goodsInfo = dto.getGoodsInfo();
					info = JsonUtils.stringToCollect(goodsInfo);
					if (null != info && !"".equals(info)) {
						dto.setProductName(null!=info.get("productName")&&!"".equals(info.get("productName"))? info.get("productName").toString():"");
						dto.setVarietiesName(null!=info.get("varietiesName")&&!"".equals(info.get("varietiesName"))? info.get("varietiesName").toString():"");
						dto.setSeedvarietyName(null!=info.get("seedvarietyName")&&!"".equals(info.get("seedvarietyName"))? info.get("seedvarietyName").toString():"");
						dto.setSpecName(null!=info.get("specName")&&!"".equals(info.get("specName"))? info.get("specName").toString():"");
						dto.setSpecifications(null!=info.get("specifications")&&!"".equals(info.get("specifications"))? info.get("specifications").toString():"");
						dto.setSeriesName(null!=info.get("seriesName")&&!"".equals(info.get("seriesName"))? info.get("seriesName").toString():"");
						dto.setBatchNumber(null!=info.get("batchNumber")&&!"".equals(info.get("batchNumber"))? info.get("batchNumber").toString():"");
						dto.setGradeName(null!=info.get("gradeName")&&!"".equals(info.get("gradeName"))? info.get("gradeName").toString():"");
						dto.setDistinctNumber(null!=info.get("distinctNumber")&&!"".equals(info.get("distinctNumber"))? info.get("distinctNumber").toString():"");
						dto.setPackNumber(null!=info.get("packNumber")&&!"".equals(info.get("packNumber"))? info.get("packNumber").toString():"");
						dto.setAdminFee(null != info.get("adminFee") ? Double.parseDouble(info.get("adminFee").toString()) : 0d);
						dto.setLoadFee(null != info.get("loadFee") ? Double.parseDouble(info.get("loadFee").toString()) : 0d);
						dto.setPackageFee(null != info.get("packageFee")? Double.parseDouble(info.get("packageFee").toString()) : 0d);
						dto.setTotalWeightUnit("吨");
						dto.setPurposeDescribe(null != info.get("purposeDescribe") ? info.get("purposeDescribe").toString() : "");
						dto.setSuitRangeDescribe(null != info.get("suitRangeDescribe") ? info.get("suitRangeDescribe").toString() : "");
					}
				}
				dto.setUnitName(UnitType.fromInt(null == info.get("unit") ? null : info.get("unit").toString()).toString());
			}
		}
		//2：沙縣類型的公司
		if (Block.SX.getValue().equals(block)) {
			for (B2bGoodsDtoEx dto : list) {
				if (null != dto.getBlock() && Block.SX.getValue().equals(dto.getBlock())) {
					String goodsInfo = dto.getGoodsInfo();
					info = JsonUtils.stringToCollect(goodsInfo);
					if (null != info && !"".equals(info)) {
						dto.setTechnologyName(null != info.get("technologyName") && !"".equals(info.get("technologyName"))? info.get("technologyName").toString() : "");
						dto.setRawMaterialParentName(null != info.get("rawMaterialParentName") && !"".equals(info.get("rawMaterialParentName"))? info.get("rawMaterialParentName").toString() : "");
						dto.setMaterialName(null != info.get("materialName") && !"".equals(info.get("materialName"))? info.get("materialName").toString() : "");
						dto.setRawMaterialName(null != info.get("rawMaterialName") && !"".equals(info.get("rawMaterialName"))? info.get("rawMaterialName").toString() : "");
						dto.setSpecName(null != info.get("specName") && !"".equals(info.get("specName"))? info.get("specName").toString() : "");
						dto.setPlantName(null != info.get("plantName") && !"".equals(info.get("plantName"))? info.get("plantName").toString() : "");
						dto.setWareName(null != info.get("wareName") && !"".equals(info.get("wareName"))? info.get("wareName").toString() : "");
						dto.setSuitRangeDescribe(null != info.get("meno") ? info.get("meno").toString() : "");
						dto.setCertificateName(null != info.get("certificateName") && !"".equals(info.get("certificateName").toString().trim())? info.get("certificateName").toString() : "无");
						

					}
					dto.setTotalWeightUnit("千克");
				}
				dto.setUnitName(UnitType.fromInt(null == info.get("unit") ? null : info.get("unit").toString()).toString());
			}
		}
		//3:沙縣 和 化纖 類型的公司
		if (block.contains(Block.CF.getValue()) && block.contains(Block.SX.getValue())) {
			for (B2bGoodsDtoEx dto : list) {
				if (null != dto.getBlock() && Block.CF.getValue().equals(dto.getBlock())) {
					String goodsInfo = dto.getGoodsInfo();
					info = JsonUtils.stringToCollect(goodsInfo);
					if (null != info && !"".equals(info)) {
						StringBuffer buf = new StringBuffer();
						buf.append(null != info.get("productName") && !"".equals(info.get("productName"))? info.get("productName") : "");
						buf.append(null != info.get("varietiesName") && !"".equals(info.get("varietiesName"))? info.get("varietiesName") : "");
						buf.append(null != info.get("seedvarietyName") && !"".equals(info.get("seedvarietyName"))? info.get("seedvarietyName") : "");
						buf.append(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName"): "");
						buf.append(null != info.get("seriesName") && !"".equals(info.get("seriesName"))? info.get("seriesName") : "");
						buf.append(null != info.get("distinctNumber") && !"".equals(info.get("distinctNumber"))? info.get("distinctNumber") : "");
						buf.append(null != info.get("batchNumber") && !"".equals(info.get("batchNumber"))? info.get("batchNumber") : "");
						buf.append(null != info.get("packNumber") && !"".equals(info.get("packNumber"))? info.get("packNumber") : "");
						buf.append(null != info.get("gradeName") && !"".equals(info.get("gradeName"))? info.get("gradeName") : "");
						String d = buf.toString();
						dto.setGoodsInfo(d);
						dto.setAdminFee(null != info.get("adminFee") ? Double.parseDouble(info.get("adminFee").toString()) : 0d);
						dto.setLoadFee(null != info.get("loadFee") ? Double.parseDouble(info.get("loadFee").toString()) : 0d);
						dto.setPackageFee(null != info.get("packageFee")? Double.parseDouble(info.get("packageFee").toString()) : 0d);
						dto.setTotalWeightUnit("吨");
						dto.setPurposeDescribe(null != info.get("purposeDescribe") ? info.get("purposeDescribe").toString() : "");
						dto.setSuitRangeDescribe(null != info.get("suitRangeDescribe") ? info.get("suitRangeDescribe").toString() : "");
					}
				} else if (null != dto.getBlock() && Block.SX.getValue().equals(dto.getBlock())) {
					String goodsInfo = dto.getGoodsInfo();
					info = JsonUtils.stringToCollect(goodsInfo);
					if (null != info && !"".equals(info)) {
						StringBuffer buf = new StringBuffer();
						buf.append(null != info.get("rawMaterialParentName") && !"".equals(info.get("rawMaterialParentName"))? info.get("rawMaterialParentName") : "");
						buf.append(null != info.get("rawMaterialName") && !"".equals(info.get("rawMaterialName"))? info.get("rawMaterialName") : "");
						buf.append(null != info.get("technologyName") && !"".equals(info.get("technologyName"))? info.get("technologyName") : "");
						buf.append(null != info.get("materialName") && !"".equals(info.get("materialName"))? info.get("materialName") : "");
						buf.append(null != info.get("specName") && !"".equals(info.get("specName")) ? info.get("specName"): "");
						buf.append(null != info.get("certificateName") && !"".equals(info.get("certificateName")) ? info.get("certificateName"): "");
						
						String d = buf.toString();
						dto.setGoodsInfo(d);
						dto.setSuitRangeDescribe(null != info.get("meno") ? info.get("meno").toString() : "");
					}
					dto.setTotalWeightUnit("千克");
				}
				dto.setUnitName(UnitType.fromInt(null == info.get("unit") ? null : info.get("unit").toString()).toString());
			}
		}
		return list;
	}

 
 
	
	 
	@Override
	public List<B2bGoodsDtoEx> searchRecomendGoodsList(Map<String, Object> map) {
		if(map.containsKey("productPk") && !"".equals(map.get("productPk").toString())){
			map.put("productPk", new String[]{map.get("productPk").toString()});
		}
		if(map.containsKey("specPk") && !"".equals(map.get("specPk").toString())){
			map.put("specPk", new String[]{map.get("specPk").toString()});
		}
		if(map.containsKey("seriesPk") && !"".equals(map.get("seriesPk").toString())){
			map.put("seriesPk", new String[]{map.get("seriesPk").toString()});
		}
		if(map.containsKey("varietiesPk") && !"".equals(map.get("varietiesPk").toString())){
			map.put("varietiesPk", new String[]{map.get("varietiesPk").toString()});
		}
		if(map.containsKey("seedvarietyPk") && !"".equals(map.get("seedvarietyPk").toString())){
			map.put("seedvarietyPk", new String[]{map.get("seedvarietyPk").toString()});
		}
		if(map.containsKey("gradePk") && !"".equals(map.get("gradePk").toString())){
			map.put("gradePk", new String[]{map.get("gradePk").toString()});
		}
		if(map.containsKey("technologyPk") && !"".equals(map.get("technologyPk").toString())){
			map.put("technologyPk", new String[]{map.get("technologyPk").toString()});
		}
		if(map.containsKey("rawMaterialParentPk") && !"".equals(map.get("rawMaterialParentPk").toString())){
			map.put("rawMaterialParentPk", new String[]{map.get("rawMaterialParentPk").toString()});
		}
		map.put("goodsType", new String[]{"presale","normal","sale"});
		map.put("isUpdown", 2);
		map.put("isDelete", 1);
		map.put("companyStatus", 1);
		return goodsDao.searchGoodsGrid(map);
	}
	@Override
	public List<B2bGoodsDto> getGoodDetailsByPks(String pks) {
		String[] goodPks = StringUtils.splitStrs(pks);
		return goodsDao.getGoodDetailsByPks(goodPks);
	}
 
	@Override
	public B2bGoodsDto getDetailsById(String pk) {
		// TODO Auto-generated method stub
		return goodsDao.getDetailsById(pk);
	}


}
