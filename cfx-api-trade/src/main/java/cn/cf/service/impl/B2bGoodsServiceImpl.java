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

import cn.cf.common.RestCode;
import cn.cf.constant.ApiServer;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bAuctionOfferDaoEx;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.FirmOrder;
import cn.cf.http.HttpHelper;
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
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bAuctionGoodsDaoEx auctionGoodsDaoEx;
	
	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;
	
	@Autowired
	private B2bAuctionOfferDaoEx auctionOfferDaoEx;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
 

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
	public B2bGoodsDtoEx getDetailsById(String pk) {
		return goodsDao.getDetailsById(pk);
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
		map.put(json.get("productName"), "商品品名");
		map.put(json.get("batchNumber"), "商品批号");
		map.put(json.get("gradeName"), "商品等级");
		map.put(json.get("tareWeight"), "箱重");
		map.put(json.get("totalBoxes"), "可用箱数");
		map.put(json.get("totalWeight"), "可用库存");
		map.put(json.get("tonPrice"), "商品挂牌价格");
		map.put(json.get("salePrice"), "商品特卖价格");
		map.put(json.get("unitName"), "商品单位");
		map.put(json.get("upDown"), "商品上下架状态");
		restCode = isNull(map);
		if (restCode.getCode().equals("0000")) {
			map = new HashMap<Object, String>();
			map.put(json.get("tareWeight"), "箱重");
			map.put(json.get("totalBoxes"), "可用箱数");
			map.put(json.get("totalWeight"), "可用库存");
			map.put(json.get("tonPrice"), "商品挂牌价格");
			map.put(json.get("salePrice"), "商品特卖价格");
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
	public List<FirmOrder> searchFirmOrder(String goodsPk, int boxes,Double weight,String offerPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsPk", goodsPk);
		map.put("boxes", boxes);
		map.put("weight", weight);
		List<FirmOrder> list = this.goodsDao.searchFirmOrder(map);
		if(null != offerPk && !"".equals(offerPk)){
			B2bAuctionOfferDto offer = auctionOfferDaoEx.getByPk(offerPk);
			if(null != offer && offer.getBidStatus() == 1 && offer.getIsFinished() == 2){
				for(FirmOrder o :list){
					o.setPrice(offer.getAuctionPrice());
				}
			}
		}
		return list;
	}

	
	/**
	 * 查询供选拼团商品
	 */
	@Override
	public List<B2bGoodsDtoEx> searchBindGoodsList(Map<String, Object> map) {
		map.put("isUpdown", 2);
		return goodsDao.searchBindGoodsList(map);
	}

	/**
	 * 供选拼团商品数量
	 */
	@Override
	public int searchBindGoodsCount(Map<String, Object> map) {
		return goodsDao.searchBindGoodsCount(map);
	}

	@Override
	public B2bGoodsDto getB2bGoods(String pk) {
		// TODO Auto-generated method stub
		return goodsDao.getByPk(pk);
	}

	

}
