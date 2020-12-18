package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.cf.dao.*;
import cn.cf.dto.B2bGoodsDtoEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.CollectionCompany;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.CollectionService;
import cn.cf.service.CommonService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private B2bGoodsService goodsService;

	@Autowired
	private B2bGoodsDaoEx goodsDao;

	@Autowired
	private B2bCompanyDaoEx companyDaoEx;

	@Autowired
	private B2bStoreDaoEx storeDaoEx;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CommonService commonService;
	@Autowired
	private B2bAuctionGoodsDaoEx b2bAuctionGoodsDaoEx;

	/**
	 * 收藏商品
	 * 
	 * @param goodsPk
	 * @param session
	 * @return
	 */
	@Override
	public RestCode collectGoods(String goodsPk, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			B2bGoodsDto goods = goodsDao.getByPk(goodsPk);
			if (goods != null) {
				// 商品是否已收藏
				List<CollectionGoods> collectionList = goodsService
						.iscollected(goods.getPk(), session.getMemberPk());
				if (null != collectionList && collectionList.size() > 0) {
					restCode = RestCode.CODE_G005;
				} else {
					// 收藏商品
					restCode = addCollectionGoods(session, goods);
				}
			} else {
				restCode = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 收藏公司
	 * 
	 * @param companyPk
	 * @param session
	 * @return
	 */
	@Override
	public RestCode collectCompany(String companyPk, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			B2bCompanyDtoEx company = companyDaoEx.getCompany(companyPk);
			B2bStoreDto store = storeDaoEx.getByCompanyPk(companyPk);
			if (null != store) {
				Query qu = new Query(Criteria
						.where("storePk")
						.is(store.getPk())
						.orOperator(
								Criteria.where("memberPk").is(
										session.getMemberPk())));
				List<CollectionCompany> c = mongoTemplate.find(qu,
						CollectionCompany.class);
				// 如果已收藏了把它删除
				if (c != null && c.size() > 0) {
					mongoTemplate.remove(c.get(0));
					restCode = RestCode.CODE_0000;
					// memberPointService.addPoint(session.getMemberPk(),companyPk,2,"collectCompany");
				} else {
					restCode = addCollectionCompany(session, company);
					// memberPointService.addPoint(session.getMemberPk(),companyPk,1,"collectCompany");
				}
			} else {
				return RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 收藏店铺
	 * 
	 * @param storePk
	 * @param session
	 * @return
	 */
	@Override
	public RestCode collectStore(String storePk, Sessions session) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			B2bStoreDto store = storeDaoEx.getByPk(storePk);
			if (null != store) {
				Query qu = new Query(Criteria
						.where("storePk")
						.is(store.getPk())
						.orOperator(
								Criteria.where("memberPk").is(
										session.getMemberPk())));
				List<CollectionCompany> c = mongoTemplate.find(qu,
						CollectionCompany.class);
				// 如果已收藏了把它删除
				if (c != null && c.size() > 0) {
					mongoTemplate.remove(c.get(0));
					restCode = RestCode.CODE_0000;
				} else {
					restCode = addCollectionStore(session, store);
				}
			} else {
				return RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 是否收藏店铺
	 */
	@Override
	public Boolean isCollection(String memberPk, String storePk) {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("memberPk").is(memberPk), Criteria
				.where("storePk").is(storePk));
		List<CollectionCompany> list = mongoTemplate.find(new Query(criatira),
				CollectionCompany.class);
		return list != null && list.size() > 0;
	}

	/**
	 * 公司收藏列表
	 */
	@Override
	public PageModel<CollectionCompany> searchCompanyList(
			Map<String, Object> map) {
		PageModel<CollectionCompany> pm = new PageModel<CollectionCompany>();
		try {
			Query query = new Query(Criteria.where("memberPk").is(
					map.get("memberPk")));
			int start = Integer.valueOf(map.get("start").toString());
			query.with(new Sort(Sort.Direction.DESC, "insertTime"));
			query.skip(start).limit(
					Integer.valueOf(map.get("limit").toString()));
			int counts = (int) mongoTemplate.count(query,
					CollectionCompany.class);
			pm.setTotalCount(counts);
			List<CollectionCompany> list = mongoTemplate.find(query,
					CollectionCompany.class);
			B2bStoreDto storeDto = new B2bStoreDto();
			for (CollectionCompany c : list) {
				Query qu = new Query(Criteria.where("storePk").is(
						c.getStorePk()));
				int companyCount = (int) mongoTemplate.count(qu,
						CollectionCompany.class);
				storeDto = storeDaoEx.getByPk(c.getStorePk());
				c.setCounts(companyCount);
				c.setCompanyPk(storeDto.getCompanyPk());
				c.setStartTime(storeDto.getStartTime());
				c.setEndTime(storeDto.getEndTime());
				B2bStoreDto storeIsOpen = storeDaoEx
						.storeIsOpen(c.getStorePk());
				if (null != storeIsOpen && storeIsOpen.getIsOpen() == 1) {
					c.setIsOpen(1);
				} else {
					c.setIsOpen(2);
				}
			}
			pm.setDataList(list);
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pm;
	}

	/**
	 * 商品收藏列表
	 */
	@Override
	public PageModel<B2bGoodsDtoEx> searchGoodList(Map<String, Object> map) {
		PageModel<B2bGoodsDtoEx> pm = new PageModel<B2bGoodsDtoEx>();
		try {
			List<B2bGoodsDtoEx> newList=new ArrayList<B2bGoodsDtoEx>();
			int count=0;
			Query query = queryVoucher(map);
			List<CollectionGoods> list = mongoTemplate.find(query,
					CollectionGoods.class);
		 
			if (null != list && list.size() > 0) {
				String[] goodPks = new String[list.size()];
				for (int i = 0; i < goodPks.length; i++) {
					goodPks[i] = list.get(i).getGoodPk();
				}
				map.put("goodPks", goodPks);
				newList = goodsDao.searchGoodsDetail(map);
				count = goodsDao.searchGoodsGridCountList(map);
			}
			
			
			pm.setTotalCount(count);
			pm.setDataList(newList);
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pm;
	}

	/**
	 * 收藏商品
	 * 
	 * @param session
	 * @param goods
	 * @return
	 */
	private RestCode addCollectionGoods(Sessions session, B2bGoodsDto goods) {
		RestCode restCode = RestCode.CODE_0000;

		try {
			CollectionGoods collectiongoods = new CollectionGoods(session,
					goods);
			mongoTemplate.save(collectiongoods);
			// http请求
			/*
			 * Map<String, String> paraMap=new HashMap<>();
			 * paraMap.put("memberPk", session.getMemberPk());
			 * paraMap.put("companyPk", session.getCompanyPk());
			 * paraMap.put("thing", goods.getPk()); paraMap.put("active",
			 * MemberSys.STORE_DIMEN_GOODS.getValue());
			 * HttpHelper.doPost(PropertyConfig
			 * .getProperty("api_member_url")+"member/addPointForThing",
			 * paraMap);
			 */
			commonService.addPointForThing(session.getMemberPk(),
					session.getCompanyPk(), goods.getPk(),
					MemberSys.STORE_DIMEN_GOODS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 收藏公司
	 * 
	 * @param session
	 * @param companyDto
	 * @return
	 */
	private RestCode addCollectionCompany(Sessions session,
			B2bCompanyDtoEx companyDto) {
		RestCode restCode = RestCode.CODE_0000;
		B2bStoreDto store = storeDaoEx.getByCompanyPk(companyDto.getPk());
		try {
			if (null != store) {
				CollectionCompany collectionCompany = new CollectionCompany();
				collectionCompany.setId(KeyUtils.getUUID());
				collectionCompany.setInsertTime(DateUtil
						.formatDateAndTime(new Date()));
				collectionCompany.setMemberPk(session.getMemberPk());
				collectionCompany.setSessionId(session.getSessionId());
				collectionCompany.setStoreName(store.getName());
				collectionCompany.setStorePk(store.getPk());
				collectionCompany.setHeadPortrait(companyDto.getHeadPortrait());
				mongoTemplate.save(collectionCompany);
			} else {
				restCode = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	/**
	 * 收藏店铺
	 * 
	 * @param session
	 * @param store
	 * @return
	 */
	private RestCode addCollectionStore(Sessions session, B2bStoreDto store) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			if (null != store) {
				CollectionCompany collectionCompany = new CollectionCompany();
				collectionCompany.setId(KeyUtils.getUUID());
				collectionCompany.setInsertTime(DateUtil
						.formatDateAndTime(new Date()));
				collectionCompany.setMemberPk(session.getMemberPk());
				collectionCompany.setSessionId(session.getSessionId());
				collectionCompany.setStoreName(store.getName());
				collectionCompany.setStorePk(store.getPk());
				collectionCompany.setHeadPortrait(store.getLogoSettings());
				mongoTemplate.save(collectionCompany);
				// http请求
				/*
				 * Map<String, String> paraMap=new HashMap<>();
				 * paraMap.put("memberPk", session.getMemberPk());
				 * paraMap.put("companyPk", session.getCompanyPk());
				 * paraMap.put("thing", store.getPk()); paraMap.put("active",
				 * MemberSys.STORE_DIMEN_STORE.getValue());
				 * HttpHelper.doPost(PropertyConfig
				 * .getProperty("api_member_url")+"member/addPointForThing",
				 * paraMap);
				 */
				commonService.addPointForThing(session.getMemberPk(),
						session.getCompanyPk(), store.getPk(),
						MemberSys.STORE_DIMEN_STORE.getValue());
			} else {
				restCode = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

	@Override
	public void removeByUserId(String sessionId) throws Exception {
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		if (session != null) {
			Query query = Query.query(Criteria.where("memberPk").is(
					session.getMemberPk()));
			mongoTemplate.remove(query, CollectionGoods.class);
			// 发起http请求
			/*
			 * Map<String, String> paraMap=new HashMap<>();
			 * paraMap.put("thingPk", null); paraMap.put("member",
			 * session.getMemberPk());
			 * HttpHelper.doPost(PropertyConfig.getProperty
			 * ("api_member_url")+"member/cancelThing", paraMap);
			 */
			commonService.cancelThing(null, session.getMemberPk());
		}
	}

	@Override
	public RestCode removeByGoodsId(Sessions session, CollectionGoods dto) {
		try {
			String[] strs = StringUtils.splitStrs(dto.getGoodPk());
			for (String str : strs) {
				Query query = Query.query(Criteria
						.where("goodPk")
						.is(str)
						.orOperator(
								Criteria.where("memberPk").is(
										session.getMemberPk())));
				mongoTemplate.remove(query, CollectionGoods.class);
			}
			// 发起http请求
			/*
			 * Map<String, String> paraMap = new HashMap<>();
			 * paraMap.put("thingPk", dto.getGoodPk()); paraMap.put("member",
			 * session.getMemberPk());
			 * HttpHelper.doPost(PropertyConfig.getProperty("api_member_url") +
			 * "member/cancelThing", paraMap);
			 */
			commonService.cancelThing(dto.getGoodPk(), session.getMemberPk());
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_A002;
		}
		return RestCode.CODE_0000;
	}

	@Override
	public RestCode removeByCompanyIds(CollectionCompany dto, Sessions session) {
		try {

			String[] strs = StringUtils.splitStrs(dto.getStorePk());
			for (String str : strs) {
				if (StringUtils.isBlank(str)) {
					continue;
				}
				Query query = Query.query(Criteria
						.where("storePk")
						.is(str)
						.orOperator(
								Criteria.where("memberPk").is(
										session.getMemberPk())));
				mongoTemplate.remove(query, CollectionCompany.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_A002;
		}

		return RestCode.CODE_0000;
	}

	private Query queryVoucher(Map<String, Object> map) {
		Criteria c = new Criteria();
		if (null != map.get("memberPk") && !"".equals(map.get("memberPk"))) {
			c.and("memberPk").is(map.get("memberPk"));
		}
		if (null != map.get("collectionTimeBegin")
				&& null == map.get("collectionTimeEnd")) {
			c.and("insertTime").gte(
					map.get("collectionTimeBegin").toString() + " 00:00:00");
		}
		if (null != map.get("collectionTimeEnd")
				&& null == map.get("collectionTimeBegin")) {
			c.and("insertTime").lte(
					map.get("collectionTimeEnd").toString() + " 23:59:59");
		}
		if (null != map.get("collectionTimeEnd")
				&& null != map.get("collectionTimeBegin")) {
			c.andOperator(
					Criteria.where("insertTime").gte(
							map.get("collectionTimeBegin").toString()
									+ " 00:00:00"),
					Criteria.where("insertTime").lte(
							map.get("collectionTimeEnd").toString()
									+ " 23:59:59"));
		}

		return new Query(c);
	}

}
