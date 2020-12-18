package cn.cf.controler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bFuturesTypeDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.SysBannerDto;
import cn.cf.dto.SysCategoryDto;
import cn.cf.dto.SysHomeBannerProductnameDtoEx;
import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.dto.SysSupplierRecommedDtoEx;
import cn.cf.entity.B2bFutures;
import cn.cf.entity.B2bPriceMovementEntity;
import cn.cf.entity.Meg;
import cn.cf.entity.Oil;
import cn.cf.entity.Pta;
import cn.cf.entity.SearchBox;
import cn.cf.entity.Sessions;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.service.B2bBrandService;
import cn.cf.service.B2bFriendLinkService;
import cn.cf.service.B2bGradeService;
import cn.cf.service.B2bKeywordHotService;
import cn.cf.service.B2bPriceHistoryService;
import cn.cf.service.B2bPriceMovementService;
import cn.cf.service.B2bProductsService;
import cn.cf.service.B2bShoppingService;
import cn.cf.service.B2bSpecHotService;
import cn.cf.service.B2bSpecService;
import cn.cf.service.B2bStoreService;
import cn.cf.service.B2bVarietiesService;
import cn.cf.service.MarketService;
import cn.cf.service.SysBannerService;
import cn.cf.service.SysCategoryService;
import cn.cf.service.SysMarketLiveBroadService;
import cn.cf.service.SysNewsService;
import cn.cf.service.SysSupplierRecommedService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import cn.cf.util.StringUtils;
import cn.cf.utils.BaseController;

/**
 * @author:WQP
 * @describe:首页接口汇总
 * @time:2018-4-12 上午9:32:11
 */
@RestController
@RequestMapping("/shop")
public class HomeController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	public final static Integer bannerDeleteFlag = 1;
	public final static Integer bannerVisiableFlag = 1;
	public final static Integer categoryDeleteFlag = 1;
	public final static Integer categoryVisiableFlag = 1;
	public final static Integer newsDeleteFlag = 1;
	public final static Integer newsVisiableFlag = 1;
	public final static Integer newsStatusFlag = 2;
	public final static Integer supplierRecommondDeleteFlag = 1;
	public final static Integer supplierRecommondVisiableFlag = 1;

	@Autowired
	private SysBannerService bannerService;

	@Autowired
	private SysCategoryService sysCategoryService;

	@Autowired
	private SysNewsService sysNewsService;

	@Autowired
	private SysSupplierRecommedService sysSupplierRecommedService;
	
	@Autowired
	private B2bShoppingService b2bShoppingService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bSpecHotService b2bSpecHotService;

	@Autowired
	private SysMarketLiveBroadService marketLiveBroadService;

	@Autowired
	private MarketService marketService;

	@Autowired
	private B2bKeywordHotService b2bKeywordHotService;

	@Autowired
	private B2bFriendLinkService b2bFriendLinkService;

	@Autowired
	private B2bPriceHistoryService b2bPriceHistoryService;

	@Autowired
	private B2bPriceMovementService b2bPriceMovementService;
	
	@Autowired
	private B2bStoreService storeService;
	
	@Autowired
	private B2bProductsService productsService;

	@Autowired
	private B2bVarietiesService varietiesService;
	
	@Autowired
	private B2bBrandService brandService;
	
	@Autowired
	private B2bGradeService gradeService;
	
	@Autowired
	private B2bSpecService b2bSpecService;

	/**
	 * 首页banner图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "banner", method = RequestMethod.POST)
	public String banner(HttpServletRequest request) {
		String result = RestCode.CODE_0000.toJson();
		Integer type = ServletUtils.getIntParameterr(request, "type", 1);
		Integer platform = ServletUtils.getIntParameterr(request, "platform", 1);
		Integer position = ServletUtils.getIntParameterr(request, "position", 1);
		Integer start = ServletUtils.getIntParameterr(request, "start", 0);
		Integer limit = ServletUtils.getIntParameterr(request, "limit", 10);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("platform", platform);
			map.put("position", position);
			map.put("isDelete", bannerDeleteFlag);
			map.put("isVisable", bannerVisiableFlag);
			map.put("start", start);
			map.put("limit", limit);
			List<SysBannerDto> list = bannerService.searchBanners(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			logger.error("sysbannerError", e);
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 左侧品名导航(废弃)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "leftProductDetails", method = RequestMethod.POST)
	public String leftProductDetails(HttpServletRequest req) {
		String restCode = RestCode.CODE_0000.toJson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderName", "sort");
			map.put("orderType", "desc");
			map.put("isDelete", 1);
			map.put("isVisable", 1);
			List<SysHomeBannerProductnameDtoEx> list = b2bShoppingService.homeProductList(map);
			restCode = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			logger.error("leftProductDetails", e);
			e.printStackTrace();
			restCode = RestCode.CODE_S999.toJson();
		}
		return restCode;

	}

	/**
	 * 左侧导航推荐供应商
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "leftNavigationCompany", method = RequestMethod.POST)
	public String leftNavigationCompany(HttpServletRequest req) {
		Map<String, Object> map = this.paramsToMap(req);
		map.put("block", ServletUtils.getStringParameter(req, "block", "cf"));//cf(化纤)；sx（纱线）
		return RestCode.CODE_0000.toJson(b2bShoppingService.nagigationSupplierRecommedList(map));
	}

	/**
	 * 左侧导航品名
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "leftNavigationProduct", method = RequestMethod.POST)
	public String leftNavigationProduct(HttpServletRequest req) {
		Map<String, Object> map = this.paramsToMap(req);
		return RestCode.CODE_0000.toJson(b2bShoppingService.nagigationProductList(map));
	}

	/**
	 * 左侧导航
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "leftNavigationAll", method = RequestMethod.POST)
	public String leftNavigationAll(HttpServletRequest req) {

		return RestCode.CODE_0000.toJson(b2bShoppingService.getLeftAllList());
	}

	/**
	 * 资讯快报分类
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "categories", method = RequestMethod.POST)
	public String categorys(HttpServletRequest req) {
		String result = RestCode.CODE_0000.toJson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Integer showType = ServletUtils.getIntParameter(req, "showType");
			Integer start = ServletUtils.getIntParameterr(req, "start", 0);
			Integer limit = ServletUtils.getIntParameterr(req, "limit", 10);
			Integer parentId = ServletUtils.getIntParameterr(req, "parentId", 1);
			if (null != showType && !"".equals(showType)) {
				map.put("showType", showType);
			}
			map.put("start", start);
			map.put("limit", limit);
			map.put("parentId", parentId);
			map.put("isDelete", categoryDeleteFlag);
			map.put("isVisable", categoryVisiableFlag);
			map.put("orderName", "sort");
			map.put("orderType", "desc");
			List<SysCategoryDto> list = sysCategoryService.searchCategorys(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			logger.error("syscategorysError", e);
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 资讯快报列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "news", method = RequestMethod.POST)
	public String news(HttpServletRequest req) {
		String result = "";
		try {
			String categoryPk = ServletUtils.getStringParameter(req,"categoryPk", "");
			String pk = ServletUtils.getStringParameter(req, "pk", "");
			Integer start = ServletUtils.getIntParameterr(req, "start", 0);
			Integer limit = ServletUtils.getIntParameterr(req, "limit", 10);
			String title = ServletUtils.getStringParameter(req, "title", "");
			Integer coordinate = ServletUtils.getIntParameterr(req,"coordinate", 3);
			String block = ServletUtils.getStringParameter(req, "block", "cf");
			Integer parentId = ServletUtils.getIntParameterr(req, "parentId", 1);//1电商系统2物流系统3金融系统
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryPk", categoryPk);
			map.put("start", start);
			map.put("limit", limit);
			map.put("title", title);
			map.put("coordinate", coordinate);
			map.put("pk", pk);
			map.put("block", block);
			map.put("parentId", parentId);
			PageModel<SysNewsStorageEntity> pm = sysNewsService.searchNews(map);
			result = RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sysnewsError:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 推荐供应商(首页)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "recommendSuppliers", method = RequestMethod.POST)
	public String recommendSuppliers(HttpServletRequest req) {
		String result = RestCode.CODE_0000.toJson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", supplierRecommondDeleteFlag);
		map.put("isRecommed", ServletUtils.getStringParameter(req, "isRecommed"));
		map.put("position",ServletUtils.getStringParameter(req,"position","2"));
		map.put("platform",ServletUtils.getStringParameter(req, "platform"));
		map.put("isOnline", 1);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 6));
		map.put("block", ServletUtils.getStringParameter(req, "block"));//cf(化纤)；sx（纱线）
		try {
			List<SysSupplierRecommedDtoEx> list = sysSupplierRecommedService.getSysSupplierRecommeds(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("recommendSuppliers Error", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 品牌专区
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "recommendSupplierByPage", method = RequestMethod.POST)
	public String recommendSupplierByPage(HttpServletRequest req) {
		String result = RestCode.CODE_0000.toJson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", ServletUtils.getIntParameter(req, "start", 0));
			map.put("limit", ServletUtils.getIntParameter(req, "limit", 10));
			map.put("isDelete", supplierRecommondDeleteFlag);
			map.put("position", 2);
			map.put("platform",ServletUtils.getStringParameter(req, "platform"));
			map.put("isOnline", 1);
			map.put("block", ServletUtils.getStringParameter(req, "block"));//cf(化纤)；sx（纱线）
            map.put("abbreviated",ServletUtils.getStringParameter(req, "abbreviated"));
			map.put("region",ServletUtils.getIntParameter(req, "region"));
			map.put("brandName",ServletUtils.getStringParameter(req, "brandName"));
			Sessions session = this.getSessions(req);
			if (null != session && null != session.getMemberPk()) {
				map.put("memberPk", session.getMemberPk());
			}
			PageModel<SysSupplierRecommedDtoEx> pm = sysSupplierRecommedService.getSysSupplierRecommedsByPage(map);
			result = RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			logger.error("recommendSupplierByPage Error", e);
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	// /**
	// * 首页订单
	// *
	// * @param req
	// * @return
	// */
	// @RequestMapping(value = "homeOrders", method = RequestMethod.POST)
	// public String homeOrders(HttpServletRequest req) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("start", ServletUtils.getIntParameter(req, "start", 0));
	// map.put("limit", ServletUtils.getIntParameter(req, "limit", 10));
	// map.put("orderName",
	// ServletUtils.getStringParameter(req, "orderName", "insertTime"));
	// map.put("orderType",
	// ServletUtils.getStringParameter(req, "orderType", "desc"));
	// return RestCode.CODE_0000.toJson(orderService.searchOrderList(map));
	// }

	/**
	 * 存搜索框数据
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "saveSearchBox", method = RequestMethod.POST)
	public String saveSearchBox(HttpServletRequest req) {
		// 搜搜索框内容
		try {
			B2bMemberDto member = this.getMemberBysessionsId(req);
			if (null != member) {
				saveSearchBox(req, member);
			}
		} catch (Exception e) {
			logger.error("errorSearchBox", e);
		}
		return RestCode.CODE_0000.toJson();
	}

	private void saveSearchBox(HttpServletRequest request, B2bMemberDto member) {
		String searchKey = ServletUtils.getStringParameter(request,"content", "");
		if (!"".equals(searchKey)) {
			Criteria c = new Criteria();
			c.and("memberPk").is(member.getPk());
			c.and("content").is(searchKey);
			Query query = new Query(c);
			List<SearchBox> list = mongoTemplate.find(query, SearchBox.class);
			if (null == list || list.size() == 0) {
				SearchBox box = new SearchBox();
				box.setMemberPk(member.getPk());
				box.setInsertTime(DateUtil.formatDateAndTime(new Date()));
				box.setContent(searchKey);
				box.setId(KeyUtils.getUUID());
				mongoTemplate.save(box);
			}else{
				Update update = new Update();
				update.set("insertTime", DateUtil.formatDateAndTime(new Date()));
				mongoTemplate.upsert(new Query(Criteria.where("id").is(list.get(0).getId())),update, SearchBox.class);
			}
		}
	}

	/**
	 * 商城搜索框
	 */
	@RequestMapping(value = "searchBox", method = RequestMethod.POST)
	public String searchBox(HttpServletRequest req) {
		B2bMemberDto member = this.getMemberBysessionsId(req);
		String content = ServletUtils.getStringParameter(req, "content", "");
		Criteria c = new Criteria();
		c.and("memberPk").is(member.getPk());
		if (!"".equals(content)) {
			c.and("content").regex(content);
		}
		Query query = new Query(c);
		Integer limit = ServletUtils.getIntParameterr(req, "limit", 10);
		query.with(new Sort(Sort.Direction.DESC, "insertTime"));
		query.skip(0).limit(limit);
		List<SearchBox> list = mongoTemplate.find(query, SearchBox.class);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 删除商城搜索框
	 */
	@RequestMapping(value = "flushSearchHistory", method = RequestMethod.POST)
	public String flushSearchHistory(HttpServletRequest req) {
		String rest = RestCode.CODE_0000.toJson();
		B2bMemberDto member = this.getMemberBysessionsId(req);
		Query query = new Query(Criteria.where("memberPk").is(member.getPk()));
		try {
			mongoTemplate.remove(query, SearchBox.class);
		} catch (Exception e) {
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}

	/**
	 * 首页热门规格
	 */
	@RequestMapping(value = "hotSpec", method = RequestMethod.POST)
	public String hotSpec(HttpServletRequest req) {
		Map<String, Object> map = this.paramsToMap(req);
        return RestCode.CODE_0000.toJson(b2bSpecHotService.searchSpecHotList( map));
	}

	/**
	 * 首页行情分类
	 */
	@RequestMapping(value = "liveBroadCategory", method = RequestMethod.POST)
	public String LiveBroadCategory(HttpServletRequest request) {
		Map<String, Object> map = this.paramsToMap(request);
//		String block = ServletUtils.getStringParameter(request, "block", "cf");
		List<SysLivebroadCategoryDto> list = marketLiveBroadService.searchLiveBroadCateGoryList(map);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 首页行情列表
	 */
	@RequestMapping(value = "liveBroadList", method = RequestMethod.POST)
	public String LiveBroadList(HttpServletRequest req) {
		Map<String, Object> map = this.paramsToMap(req);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		map.put("livebroadcategoryPk", StringUtils.splitStrs(ServletUtils.getStringParameter(req, "livebroadcategoryPk", "")));
		map.put("block", ServletUtils.getStringParameter(req, "block", "cf"));
		return RestCode.CODE_0000.toJson(marketLiveBroadService.searchsMarketLivebroadList(map));
	}

	/**
	 * 首页关键词热搜
	 */
	@RequestMapping(value = "searchKeyWordList", method = RequestMethod.POST)
	public String searchKeyWordList(HttpServletRequest req) {
		Map<String, Object> map = this.paramsToMap(req);
		Integer start = ServletUtils.getIntParameterr(req, "start", 0);
		Integer limit = ServletUtils.getIntParameterr(req, "limit", 100);
		map.put("start", start);
		map.put("limit", limit);
		return RestCode.CODE_0000.toJson(b2bKeywordHotService.searchKeyWordList(map));
	}

	/**
	 * 首页友情链接
	 */
	@RequestMapping(value = "searchFriendLink", method = RequestMethod.POST)
	public String searchFriendLink(HttpServletRequest req) {
		Integer start = ServletUtils.getIntParameterr(req, "start", 0);
		Integer limit = ServletUtils.getIntParameterr(req, "limit", 100);
		return RestCode.CODE_0000.toJson(b2bFriendLinkService.searchFriendLinkList(start, limit));
	}

	/**
	 * 首页行情数据MEG
	 */
	@RequestMapping(value = "megHistory", method = RequestMethod.POST)
	public String megHistory(HttpServletRequest request) {
		Integer days = ServletUtils.getIntParameterr(request, "days", 1);
		String name = ServletUtils.getStringParameter(request, "name","MEG1810");
		//String name = ServletUtils.getStringParameter(request, "name","MEG1810");
		List<Meg> list = marketService.searchMegList(days, name);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 首页行情数据OIL
	 */
	@RequestMapping(value = "oilHistory", method = RequestMethod.POST)
	public String oilHistory(HttpServletRequest request) {
		Integer days = ServletUtils.getIntParameterr(request, "days", 1);
		List<Oil> list = marketService.searchOilList(days);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 首页行情数据PTA
	 */
	@RequestMapping(value = "ptaHistory", method = RequestMethod.POST)
	public String ptaHistory(HttpServletRequest request) {
		Integer days = ServletUtils.getIntParameterr(request, "days", 1);
		String name = ServletUtils.getStringParameter(request, "name","PTA1901");
		List<Pta> list = marketService.searchPtaList(days, name);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 首页期货品种
	 */
	@RequestMapping(value = "searchFuturesType", method = RequestMethod.POST)
	public String searchFuturesType(HttpServletRequest request) {
		String block = ServletUtils.getStringParameter(request, "block", "cf");
		Map<String, Object> map=new HashMap<>();
		map.put("block", block);
		List<B2bFuturesTypeDto> list = marketService.searchFuturesType(map);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 首页行情数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchFutures", method = RequestMethod.POST)
	public String searchFutures(HttpServletRequest request) {
		Integer days = ServletUtils.getIntParameterr(request, "days", 1);
		String futuresPk = ServletUtils.getStringParameter(request, "futuresPk");
		String block =  ServletUtils.getStringParameter(request, "block", "cf");
		List<B2bFutures> list = marketService.searchFutures(days, futuresPk,block);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 商品价格趋势
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchPriceMovement", method = RequestMethod.POST)
	public String searchPriceMovement(HttpServletRequest request) {
		Map<String, Object> map = paramsToMap(request);
		String block = ServletUtils.getStringParameter(request, "block", "cf");
		map.put("block", block);
		if (null == map.get("start")) {
			map.put("start", 0);
		}
		if (null == map.get("limit")) {
			map.put("limit", 10);
		}
		 map.put("productPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "productPk", "")));
		 map.put("varietiesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "varietiesPk", "")));
		 map.put("seriesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seriesPk", "")));
		  map.put("technologyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "technologyPk", "")));
          map.put("rawMaterialPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialPk", "")));
          map.put("rawMaterialParentPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialParentPk", "")));
         
		 return RestCode.CODE_0000.toJson(b2bPriceMovementService.searchB2bPriceMovementList(map));
	}

	/**
	 * 商品价格历史记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goodsPriceHistory", method = RequestMethod.POST)
	public String goodsPriceHistory(HttpServletRequest request) {
		Integer days = ServletUtils.getIntParameterr(request, "days", 1);
		String pk = ServletUtils.getStringParameter(request, "pk", "-1");
		List<B2bPriceMovementEntity> list = b2bPriceHistoryService.searchB2bPriceHistoryList(pk, days);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 根据brandPk 查询店铺
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchStoreByBrandPk", method = RequestMethod.POST)
	public String searchStoreByBrandPk(HttpServletRequest request) {
		String brandPk = ServletUtils.getStringParameter(request, "brandPk");
		List<B2bStoreDto> list = storeService.searchStoreByBrandPk(brandPk);
		return RestCode.CODE_0000.toJson(list);
	}

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchSomeNameByPk", method = RequestMethod.POST)
	public String searchSomeNameByPk(HttpServletRequest request) {
		String productPks = ServletUtils.getStringParameter(request,"productPks");
		String varietiesPks = ServletUtils.getStringParameter(request,"varietiesPks");
		String brandPks = ServletUtils.getStringParameter(request, "brandPks");
		String gradePks = ServletUtils.getStringParameter(request, "gradePks");
		String seriesPks = ServletUtils.getStringParameter(request, "seriesPks");
		try {
			if (null != productPks && !"".equals(productPks)) {
				return RestCode.CODE_0000.toJson(productsService.searchProductNameByProductPks(productPks));
			} else if (null != varietiesPks && !"".equals(varietiesPks)  ) {
				return RestCode.CODE_0000.toJson(varietiesService.searchVarietiesNameByVarietiesPks(varietiesPks));
			} else if (null != brandPks && !"".equals(brandPks)  ) {
				return RestCode.CODE_0000.toJson(brandService.searchBrandNameByBrandPks(brandPks));
			}else if (null != gradePks && !"".equals(gradePks) ) {
				return RestCode.CODE_0000.toJson(gradeService.searchGradeNameByGradePks(gradePks));
			}else if (null != seriesPks  && !"".equals(seriesPks)) {
				return RestCode.CODE_0000.toJson(b2bSpecService.searchSeriesNameBySeriesPks(seriesPks));
			}

		} catch (Exception e) {
			logger.error("searchSomeNameByPk", e);
			return RestCode.CODE_S999.toJson();
		}
		return seriesPks;
	}

}
