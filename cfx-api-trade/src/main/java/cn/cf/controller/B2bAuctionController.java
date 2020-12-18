package cn.cf.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bAuctionOfferDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.PageModelAuction;
import cn.cf.dto.PageModelAuctionBid;
import cn.cf.dto.PageModelAuctionGoods;
import cn.cf.dto.PageModelAuctionGoodsByMember;
import cn.cf.dto.PageModelAuctionOrder;
import cn.cf.dto.PageModelBidRecord;
import cn.cf.dto.PageModelOfferRecord;
import cn.cf.dto.PageModelOrderRecord;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bAuctionOffer;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.service.B2bAuctionOfferService;
import cn.cf.service.B2bAuctionService;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.B2bMemberService;
import cn.cf.util.DateUtil;
import cn.cf.util.ServletUtils;
import cn.cf.util.StringUtils;
import cn.cf.utils.BaseController;

/**
 * @description:竞拍接口管理
 * @author wangc
 * @date 2018-07-30 下午3:13:34
 */
@RestController
@RequestMapping("trade")
public class B2bAuctionController extends BaseController {

	@Autowired
	private B2bAuctionService b2bAuctionService;

    @Autowired
    private B2bFacadeService b2bFacadeService;
	
	@Autowired
	private B2bGoodsService b2bGoodsService;
	
	@Autowired
	private B2bAuctionGoodsService b2bAuctionGoodsService;
	
	@Autowired
	private B2bAuctionOfferService b2bAuctionOfferService;
	
	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	
	
	/**
	 * 竞拍场次管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchAuctionList", method = RequestMethod.POST)
	public String searchAuctionScreenings(HttpServletRequest request) {
		Map<String, Object> map = this.paramsToMap(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		map.put("orderName", ServletUtils.getStringParameter(request, "orderName", "updateTime"));
		map.put("orderType", ServletUtils.getStringParameter(request, "orderType", "desc"));
		map.put("storePk", store.getPk() == null ? "" : store.getPk());
		PageModelAuction<B2bAuctionDto> pm = new PageModelAuction<B2bAuctionDto>();
		pm = b2bAuctionService.searchAuctionList(map);
		return RestCode.CODE_0000.toJson(pm);
	}
	

	

	/**
	 * 添加竞拍场次
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addAuction", method = RequestMethod.POST)
	public String addAuction(HttpServletRequest request) {
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String auctionName=ServletUtils.getStringParameter(request, "auctionName");
		String startTime=ServletUtils.getStringParameter(request, "startTime");
	    String endTime=ServletUtils.getStringParameter(request, "endTime");
		int sort=ServletUtils.getIntParameter(request, "sort", -1);
		if ("".equals(auctionName)||"".equals(startTime)||"".equals(endTime)) {
			return RestCode.CODE_A001.toJson();
		}
		if (sort<0||sort==0) {
			return RestCode.CODE_P002.toJson();
		}
		RestCode restCode = b2bAuctionService.addAuction(auctionName, startTime, endTime, sort, store.getPk());
		return restCode.toJson();
	}

	
	/**
	 * 删除竞拍场次
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delAuction", method = RequestMethod.POST)
	public String delAuction(HttpServletRequest request) {
		String pk=ServletUtils.getStringParameter(request, "pk");
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		RestCode restCode = b2bAuctionService.delAuction(pk);
		return restCode.toJson();
	}
	
	/**
	 * 编辑 竞拍场次
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "editAuction", method = RequestMethod.POST)
	public String editAuction(HttpServletRequest request) {
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String pk=ServletUtils.getStringParameter(request, "pk");
		String auctionName=ServletUtils.getStringParameter(request, "auctionName");
		String startTime=ServletUtils.getStringParameter(request, "startTime");
	    String endTime=ServletUtils.getStringParameter(request, "endTime");
	    int sort=ServletUtils.getIntParameter(request, "sort", -1);
	    if (null==pk||"".equals(pk)||"".equals(auctionName)||"".equals(startTime)||"".equals(endTime)) {
			return RestCode.CODE_A001.toJson();
		}
	    if (sort==-1) {
			return RestCode.CODE_P002.toJson();
		}
	    B2bAuctionDto dto=new B2bAuctionDto();
	    dto.setPk(pk);
	    dto.setAuctionName(auctionName);
	    dto.setStartTime(startTime);
	    dto.setEndTime(endTime);
	    dto.setSort(sort);
	    dto.setStorePk(store.getPk());
		RestCode restCode = b2bAuctionService.editAuction(dto);
		return restCode.toJson();
	}
	
	
	/**
	 * 禁用/启用   竞拍场次
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "visableAuction", method = RequestMethod.POST)
	public String visableAuction(HttpServletRequest request) {
		String pk=ServletUtils.getStringParameter(request, "pk");
		int isVisable=ServletUtils.getIntParameter(request, "isVisable", -1);
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		if (isVisable==-1) {
			return RestCode.CODE_A002.toJson();
		}
		B2bAuctionDto dto = b2bAuctionService.visableAuction(pk,isVisable);
		if (null!=dto) {
			return RestCode.CODE_0000.toJson(dto);
		}else{
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 根据sort排序查询某店铺的竞拍场次
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAucListSort", method = RequestMethod.POST)
	public String getAucListSort(HttpServletRequest request) {
		B2bStoreDto store = this.getStoreBysessionsId(request);
		List<B2bAuctionDto> list = b2bAuctionService.getAucListSort(store.getPk());
		return RestCode.CODE_0000.toJson(list);
	}
	
	
	/**
	 * 普通商品上线至竞拍
	 * @param request
	 * @param goodsPks  多个商品逗号隔开
	 * @param auctionPks  多个场次逗号隔开
	 * @return
	 */
	@RequestMapping(value = "upToAuction", method = RequestMethod.POST)
	public String upToAuction(HttpServletRequest request) {
		String goodsPks=ServletUtils.getStringParameter(request, "goodsPks");
		String auctionPks=ServletUtils.getStringParameter(request, "auctionPks");
		String activityTime=ServletUtils.getStringParameter(request, "activityTime");
		if (null==goodsPks||"".equals(goodsPks)) {
			return RestCode.CODE_A001.toJson();
		}
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date temp=null;
		if (null!=activityTime&&!"".equals(activityTime)) {
			try {
				temp=format1.parse(activityTime);
			} catch (Exception e) {
				return RestCode.CODE_P004.toJson();
			}
		}
		RestCode restCode = b2bFacadeService.upToAuction(goodsPks,auctionPks,temp);
		return restCode.toJson();
	}
	
	
	
	/**
	 * 竞拍中商品
	 * 
	 * @param req
	 * @param acStatus 0：全部，1：未上线，2：未开始， 3：进行中， 4： 已结束
	 * @return
	 */
	@RequestMapping(value = "searchAuctionGoodsList", method = RequestMethod.POST)
	public String searchAuctionGoodsList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String searchKey=ServletUtils.getStringParameter(request, "searchKey");//搜索关键字
		String activityTimeStart=ServletUtils.getStringParameter(request, "activityTimeStart");//活动时间开始
		String activityTimeEnd=ServletUtils.getStringParameter(request, "activityTimeEnd");//活动时间结束
		int acStatus=ServletUtils.getIntParameter(request, "acStatus", 0);//状态，0：全部，1：未上线，2：未开始， 3：进行中， 4： 已结束
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date activityTimeStartTemp=null;
		Date activityTimeEndTemp=null;
		if (!"".equals(activityTimeStart)) {
			try {
				activityTimeStartTemp=format1.parse(activityTimeStart);
			} catch (Exception e) {
				return RestCode.CODE_P004.toJson();
			}
		}
		if (!"".equals(activityTimeEnd)) {
			try {
				activityTimeEndTemp=format1.parse(activityTimeEnd);
			} catch (Exception e) {
				return RestCode.CODE_P004.toJson();
			}
		}
		map = this.paramsToMap(request);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		map.put("searchKey", searchKey);
		map.put("activityTimeStart", activityTimeStartTemp);
		map.put("activityTimeEnd", activityTimeEndTemp);
		map.put("storePk", store.getPk()==null?"":store.getPk());
		map.put("acStatus", acStatus);
		try {
			PageModelAuctionGoods<B2bAuctionGoodsDtoEx> pm = b2bFacadeService.searchAuctionGoodsList(map);
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	
	/**
	 * 竞拍商品修改商品类型
	 * @param request
	 * @param goodsPks  多个商品逗号隔开
	 * @return
	 */
	@RequestMapping(value = "downToNormal", method = RequestMethod.POST)
	public String downToNormal(HttpServletRequest request) {
		String goodsPks=ServletUtils.getStringParameter(request, "goodsPks");//商品pk
		String type=ServletUtils.getStringParameter(request, "type");//类型
		if (null==goodsPks||"".equals(goodsPks)) {
			return RestCode.CODE_A001.toJson();
		}
		RestCode restCode = b2bFacadeService.downToNormal(goodsPks,type);
		return restCode.toJson();
	}
	
	
	/**
	 * 设置最低起拍量
	 * @param request
	 * @param goodsPks  多个商品逗号隔开
	 * @return
	 */
	@RequestMapping(value = "setMinimumBoxes", method = RequestMethod.POST)
	public String setMinimumBoxes(HttpServletRequest request) {
		String goodsPks=ServletUtils.getStringParameter(request, "goodsPks");//商品pk
		int minimumBoxes=ServletUtils.getIntParameter(request, "minimumBoxes", 0);
		if (null==goodsPks||"".equals(goodsPks)) {
			return RestCode.CODE_A001.toJson();
		}
		if (minimumBoxes==0) {
			return RestCode.CODE_A002.toJson();
		}
		RestCode restCode = b2bFacadeService.setMinimumBoxes(goodsPks,minimumBoxes);
		return restCode.toJson();
	}
	
	
	/**
	 * 设置场次
	 * @param request
	 * @param pks  多个时逗号隔开
	 * @param auctionPk  竞拍活动pk
	 * @return
	 */
	@RequestMapping(value = "setAuction", method = RequestMethod.POST)
	public String setAuction(HttpServletRequest request) {
		String goodsPks=ServletUtils.getStringParameter(request, "goodsPks");//商品pk
		String auctionPk=ServletUtils.getStringParameter(request, "auctionPk");//场次pk
		String activityTime=ServletUtils.getStringParameter(request, "activityTime");
		if (null==goodsPks||"".equals(goodsPks)||null==auctionPk||"".equals(auctionPk)||null==activityTime||"".equals(activityTime)) {
			return RestCode.CODE_A001.toJson();
		}
		Date temp =DateUtil.parseDateFormat(activityTime,"yyyy-MM-dd");
		if (null==temp) {
			return RestCode.CODE_P004.toJson();
		}
        B2bAuctionDto auctionDto = b2bAuctionService.getByPk(auctionPk);
        String startTime = auctionDto.getStartTime();
        String startTimestamp = activityTime + " " + startTime + ":00";
        Date date = DateUtil.parseDateFormat(startTimestamp);
        if (null==date) {
        	return RestCode.CODE_P004.toJson();
		}
        if (date.before(new Date())) {
            return RestCode.CODE_P008.toJson();
        }
		RestCode restCode = b2bFacadeService.setAuction(goodsPks,auctionPk,temp);
		return restCode.toJson();
	}
	
	
	/**
	 * 代拍-竞拍中的商品列表
	 * 
	 * @param req
	 * @param acStatus 状态，0：全部， 1：进行中， 2： 已结束
	 * @return
	 */
	@RequestMapping(value = "auctionGoodsListByMember", method = RequestMethod.POST)
	public String auctionGoodsListByMember(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Sessions session = this.getSessions(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String productPk=ServletUtils.getStringParameter(request, "productPk");//品名
		String varietiesPk=ServletUtils.getStringParameter(request, "varietiesPk");//品种
		String seedvarietyPk=ServletUtils.getStringParameter(request, "seedvarietyPk");//子品种
		String specifications=ServletUtils.getStringParameter(request, "specifications");//规格
		String seriesPk=ServletUtils.getStringParameter(request, "seriesPk");//规格系列
		int acStatus=ServletUtils.getIntParameter(request, "acStatus", 0);//状态，0：全部， 1：进行中， 2： 已结束
		String companyPk=ServletUtils.getStringParameter(request, "companyPk");//代拍公司pk
		String searchKey=ServletUtils.getStringParameter(request, "searchKey");
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		map.put("storePk", store.getPk() == null ? "" : store.getPk());
		map.put("productPk", productPk);
		map.put("varietiesPk", varietiesPk);
		map.put("seedvarietyPk", seedvarietyPk);
		map.put("specifications", specifications);
		map.put("seriesPk", seriesPk);
		map.put("acStatus", acStatus);
		map.put("searchKey", searchKey);
		try {
			PageModelAuctionGoodsByMember<B2bAuctionGoodsDtoEx> pm = b2bFacadeService.auctionGoodsListByMember(map,session.getMemberPk(),companyPk);
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	
	/**
	 *	竞拍出价 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "auctionOffer", method = RequestMethod.POST)
	public String auctionOffer(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		String pk=ServletUtils.getStringParameter(req, "pk");//竞拍活动pk
		String companyPk=ServletUtils.getStringParameter(req, "companyPk");//代拍公司pk
		Double tareWeight = ServletUtils.getDoubleParameter(req, "tareWeight",0.0);//箱重
		Double auctionPrice = ServletUtils.getDoubleParameter(req, "auctionPrice",0.0);//竞拍价格
		int boxes = ServletUtils.getIntParameter(req, "boxes", 0);//竞拍箱数
		if (null==pk||"".equals(pk)||null==companyPk||"".equals(companyPk)) {
			return RestCode.CODE_S999.toJson();
		}
		if (boxes==0) {
			return RestCode.CODE_G017.toJson();
		}
		if (tareWeight==0.0) {
			return RestCode.CODE_G018.toJson();
		}
		if (auctionPrice==0.0) {
			return RestCode.CODE_G019.toJson();
		}
		B2bAuctionGoodsDto auctionGoodsDto= b2bAuctionGoodsService.getById(pk);
		B2bGoodsDtoEx g=b2bGoodsService.getDetailsById(auctionGoodsDto.getGoodsPk());
		B2bGoodsDtoMa bm = new B2bGoodsDtoMa();
		bm.UpdateMine(g);
		B2bMemberDto saleMan = b2bCustomerSaleManService.getSaleMan(g.getCompanyPk(),companyPk,null!=bm.getCfGoods()?bm.getCfGoods().getProductPk():null,g.getStorePk());//业务员
		B2bMemberDto temp=this.getMemberBysessionsId(req);
		B2bMemberDto operator = b2bMemberService.getByPk(temp.getPk());//当前操作人
		try {
			restCode = b2bFacadeService.auctionOffer(pk, boxes, tareWeight,auctionPrice,operator,saleMan,companyPk);
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	
	/**
	 * 一键出价
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "auctionOfferOneStep", method = RequestMethod.POST)
	public String auctionOfferOneStep(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		String pks=ServletUtils.getStringParameter(req, "pks");//竞拍活动pks
		String companyPk=ServletUtils.getStringParameter(req, "companyPk");//代拍公司pk
		Double andMore = ServletUtils.getDoubleParameter(req, "andMore",0.0);//统一加价金额
		if (null==pks||"".equals(pks)||null==companyPk||"".equals(companyPk)||andMore==0.0) {
			return RestCode.CODE_A001.toJson();
		}
		String[] pksTemp = StringUtils.splitStrs(pks);
		B2bAuctionGoodsDto auctionGoodsDto= b2bAuctionGoodsService.getById(pksTemp[0]);
		B2bGoodsDtoEx g=b2bGoodsService.getDetailsById(auctionGoodsDto.getGoodsPk());
		B2bGoodsDtoMa bm = new B2bGoodsDtoMa();
		bm.UpdateMine(g);
		B2bMemberDto saleMan = b2bCustomerSaleManService.getSaleMan(g.getCompanyPk(),companyPk,null!=bm.getCfGoods()?bm.getCfGoods().getProductPk():null, g.getStorePk());
		B2bMemberDto temp=this.getMemberBysessionsId(req);
		B2bMemberDto operator = b2bMemberService.getByPk(temp.getPk());//当前操作人
		try {
			restCode = b2bFacadeService.auctionOfferOneStep(pks,companyPk,operator,saleMan,andMore);
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	
	
	
	/**
	 * 某个场次-某个客户的出价记录
	 * 
	 * @param req
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "auctionOfferRecordsByMember", method = RequestMethod.POST)
	public String auctionOfferRecordsByMember(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Sessions session = this.getSessions(request);
		B2bMemberDto member = this.getMemberBysessionsId(request);
		String offerTimeStart=ServletUtils.getStringParameter(request, "offerTimeStart");//出价时间开始
		String offerTimeEnd=ServletUtils.getStringParameter(request, "offerTimeEnd");//出价时间结束
		String pk=ServletUtils.getStringParameter(request, "pk");//竞拍场次pk
		String companyPk=ServletUtils.getStringParameter(request, "companyPk");//代拍公司pk
		if (null==pk||"".equals(pk)||null==companyPk||"".equals(companyPk)) {
			return RestCode.CODE_A001.toJson();
		}

		if (!"".equals(offerTimeStart)) {
			try {
			} catch (Exception e) {
				return RestCode.CODE_P004.toJson();
			}
		}
		if (!"".equals(offerTimeEnd)) {
			try {
			} catch (Exception e) {
				return RestCode.CODE_P004.toJson();
			}
		}
		//map.put("memberPk", session.getMemberPk());
		//session.getIsAdmin()
		map.put("auctionPk", pk);
		map.put("companyPk", companyPk);
		map.put("offerTimeStart", offerTimeStart);
		map.put("offerTimeEnd", offerTimeEnd);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		// 超级管理员查看所有,业务员查看自己的出价记录,业务助理查看对应业务员的出价记录
		if (null != session.getIsAdmin() && session.getIsAdmin() != 1) {
			if (null == member.getParentPk() || "".equals(member.getParentPk())
					|| "-1".equals(member.getParentPk())) {
				map.put("saleManPk", member.getPk());
			} else {
				map.put("saleManPk", member.getParentPk());
			}
		}
		try {
			PageModel<B2bAuctionOfferDtoEx> pm = b2bFacadeService.auctionOfferRecordsByMember(map);
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 取消出价
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "cancelOffer", method = RequestMethod.POST)
	public String cancelOffer(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		String recordPk=ServletUtils.getStringParameter(req, "recordPk");//出价记录pk
		Integer type = ServletUtils.getIntParameterr(req, "type", 0);//取消类型（1：取消该条，2：取消所有）
		if (null==recordPk||"".equals(recordPk)||type==0) {
			return RestCode.CODE_A001.toJson();
		}
		try {
			restCode = b2bAuctionOfferService.cancelOfferRecord(recordPk,type);
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}

	
	
	/**
	 * 出价管理
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "offerManagement", method = RequestMethod.POST)
	public String offerManagement(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		Map<String, Object> map=new HashMap<>();
		String pk=ServletUtils.getStringParameter(req, "pk");//竞拍场次pk
		String searchKey=ServletUtils.getStringParameter(req, "searchKey");//搜索关键词
		map.put("pk", pk);
		map.put("searchKey", searchKey);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		PageModel<B2bAuctionOfferDtoEx> pm = new PageModel<B2bAuctionOfferDtoEx>();
		pm = b2bAuctionOfferService.offerManagement(map);
		return restCode.toJson(pm);
	}
	
	
	/**
	 * 竞拍中商品-中标管理
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "bidManagement", method = RequestMethod.POST)
	public String bidManagement(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		Map<String, Object> map=new HashMap<>();
		String pk=ServletUtils.getStringParameter(req, "pk");//竞拍场次pk
		String searchKey=ServletUtils.getStringParameter(req, "searchKey");//搜索关键词
		int bidStatus =ServletUtils.getIntParameter(req, "bidStatus", 0);//中标状态，0：全部，1：已中标，2：未中标
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		map.put("pk", pk);
		map.put("searchKey", searchKey);
		map.put("bidStatus", bidStatus);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		PageModelAuctionBid<B2bAuctionOfferDtoEx> pm = new PageModelAuctionBid<B2bAuctionOfferDtoEx>();
		pm = b2bAuctionOfferService.bidManagement(map);
		return restCode.toJson(pm);
	}
	
	/**
	 * 查询可用库存
	 */
	@RequestMapping(value="searchStockByAuctionPk", method = RequestMethod.POST)
	public String searchStockByAuctionPk(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		Map<String, Object> map=new HashMap<String, Object>();
		String pk=ServletUtils.getStringParameter(req, "pk");
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		map.put("stock", b2bAuctionOfferService.searchStockByAuctionPk(pk));
		return restCode.toJson(map);
		
	}
	/**
	 * 更改状态，库存
	 */
	@RequestMapping(value="editAuctionOfferStatus", method = RequestMethod.POST)
	public String editAuctionOfferStatus(HttpServletRequest req){
		B2bAuctionOffer offer=new B2bAuctionOffer();
		offer.bind(req);
		RestCode restCode = b2bAuctionOfferService.updateAuctionOffer(offer);
		return restCode.toJson();
	}
 
	
	/**
	 * 订单管理
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "orderManagement", method = RequestMethod.POST)
	public String orderManagement(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		Map<String, Object> map=new HashMap<>();
		String pk=ServletUtils.getStringParameter(req, "pk");//竞拍场次pk
		String searchKey=ServletUtils.getStringParameter(req, "searchKey");//搜索关键词
		int submitStatus =ServletUtils.getIntParameter(req, "submitStatus", 0);//提交状态，0：全部，1：未提交，2：已提交
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		map.put("auctionPk", pk);
		map.put("searchKey", searchKey);
		map.put("submitStatus", submitStatus);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		PageModelAuctionOrder<B2bAuctionOfferDtoEx> pm = new PageModelAuctionOrder<B2bAuctionOfferDtoEx>();
		pm = b2bAuctionOfferService.orderManagement(map);
		return restCode.toJson(pm);
	}
	
	
	
	/**
	 * 根据场次pk 查询商品信息，以及活动时间
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getInfoByAuctionPk", method = RequestMethod.POST)
	public String getInfoByAuctionPk(HttpServletRequest req){
		RestCode restCode = RestCode.CODE_0000;
		String pk=ServletUtils.getStringParameter(req, "pk");//竞拍场次pk
		if (null==pk||"".equals(pk)) {
			return RestCode.CODE_A001.toJson();
		}
		B2bAuctionGoodsDtoEx dtoEx = b2bAuctionGoodsService.getInfoByAuctionPk(pk);
		return restCode.toJson(dtoEx);
	}
	
	
	/**
	 * 出价记录
	 * 
	 * @param req
	 * @param acStatus 0：全部，1：进行中  2：已结束，3 ： 已过期
	 * @return
	 */
	@RequestMapping(value = "offerRecords", method = RequestMethod.POST)
	public String offerRecords(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Sessions session = this.getSessions(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String productPk=ServletUtils.getStringParameter(request, "productPk");//品名
		String varietiesPk=ServletUtils.getStringParameter(request, "varietiesPk");//品种
		String seedvarietyPk=ServletUtils.getStringParameter(request, "seedvarietyPk");//子品种
		String specifications=ServletUtils.getStringParameter(request, "specifications");//规格
		String seriesPk=ServletUtils.getStringParameter(request, "seriesPk");//规格系列
		String auctionPk=ServletUtils.getStringParameter(request, "auctionPk");//活动场次pk
		String searchKey=ServletUtils.getStringParameter(request, "searchKey");
		String companyName=ServletUtils.getStringParameter(request, "companyName");//公司名称
		B2bMemberDto memberDto = this.getMemberBysessionsId(request);
		map.put("storePk", store.getPk() == null ? "" : store.getPk());
		map.put("productPk", productPk);
		map.put("varietiesPk", varietiesPk);
		map.put("seedvarietyPk", seedvarietyPk);
		map.put("specifications", specifications);
		map.put("seriesPk", seriesPk);
		if (null!=auctionPk&&!"".equals(auctionPk)) {
			map.put("auctionPk", auctionPk);
		}
		map.put("searchKey", searchKey);
		map.put("companyName", companyName);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		int acStatus=ServletUtils.getIntParameter(request, "acStatus", 0);//状态，0：全部，1：进行中， 2：已结束， 3： 已过期
		map.put("acStatus", acStatus);
		try {
			PageModelOfferRecord<B2bAuctionOfferDtoEx> pm = b2bAuctionOfferService.offerRecords(map, memberDto, session.getIsAdmin());
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 中标详情
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getOffer", method = RequestMethod.POST)
	public String getOffer(HttpServletRequest request) {
		try {
			String pk = ServletUtils.getStringParameter(request, "pk","");
			B2bAuctionOfferDto offer = b2bAuctionOfferService.getAuctionOffer(pk);
			return RestCode.CODE_0000.toJson(offer);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 中标记录
	 * @param req
	 * @param bidStatus 状态，0：全部，1：未中标， 2：已中标， 3： 已过期
	 * @return
	 */
	@RequestMapping(value = "bidRecords", method = RequestMethod.POST)
	public String bidRecords(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Sessions session = this.getSessions(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		B2bMemberDto memberDto = this.getMemberBysessionsId(request);
		String productPk=ServletUtils.getStringParameter(request, "productPk");//品名
		String varietiesPk=ServletUtils.getStringParameter(request, "varietiesPk");//品种
		String seedvarietyPk=ServletUtils.getStringParameter(request, "seedvarietyPk");//子品种
		String specifications=ServletUtils.getStringParameter(request, "specifications");//规格
		String seriesPk=ServletUtils.getStringParameter(request, "seriesPk");//规格系列
		String auctionPk=ServletUtils.getStringParameter(request, "auctionPk");//场次pk
		String searchKey=ServletUtils.getStringParameter(request, "searchKey");
		String companyInfo = ServletUtils.getStringParameter(request, "companyInfo");
		map.put("storePk", store.getPk() == null ? "" : store.getPk());
		map.put("productPk", productPk);
		map.put("varietiesPk", varietiesPk);
		map.put("seedvarietyPk", seedvarietyPk);
		map.put("specifications", specifications);
		map.put("seriesPk", seriesPk);
		if (null!=auctionPk&&!"".equals(auctionPk)) {
			map.put("auctionPk", auctionPk);
		}
		map.put("searchKey", searchKey);
		map.put("companyInfo", companyInfo);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		int bidStatus=ServletUtils.getIntParameter(request, "bidStatus", 0);//状态，0：全部，1：未中标， 2：已中标， 3： 已过期
		map.put("bidStatus", bidStatus);
		try {
			PageModelBidRecord<B2bAuctionOfferDtoEx> pm = b2bAuctionOfferService.bidRecords(map,memberDto,session.getIsAdmin());
			String result=RestCode.CODE_0000.toJson(pm);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	
	
	/**
	 * 订单记录
	 * @param req
	 * @param acStatus 状态，0：全部，1：未提交， 2：已提交， 3： 已过期
	 * @return
	 */
	@RequestMapping(value = "orderRecords", method = RequestMethod.POST)
	public String orderRecords(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Sessions session = this.getSessions(request);
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String productPk=ServletUtils.getStringParameter(request, "productPk");//品名
		String varietiesPk=ServletUtils.getStringParameter(request, "varietiesPk");//品种
		String seedvarietyPk=ServletUtils.getStringParameter(request, "seedvarietyPk");//子品种
		String specifications=ServletUtils.getStringParameter(request, "specifications");//规格
		String seriesPk=ServletUtils.getStringParameter(request, "seriesPk");//规格系列
		String auctionPk=ServletUtils.getStringParameter(request, "auctionPk");//规格系列
		String searchKey=ServletUtils.getStringParameter(request, "searchKey");
		String companyInfo = ServletUtils.getStringParameter(request, "companyInfo");
		String batchNumber = ServletUtils.getStringParameter(request, "batchNumber");
		String gradePk = ServletUtils.getStringParameter(request, "gradePk");
		map.put("storePk", store.getPk() == null ? "" : store.getPk());
		map.put("productPk", productPk);
		map.put("varietiesPk", varietiesPk);
		map.put("seedvarietyPk", seedvarietyPk);
		map.put("specifications", specifications);
		map.put("seriesPk", seriesPk);
		if (null!=auctionPk&&!"".equals(auctionPk)) {
			map.put("auctionPk", auctionPk);
		}
		map.put("searchKey", searchKey);
		map.put("companyInfo", companyInfo);
		map.put("batchNumber", batchNumber);
		map.put("gradePk", gradePk);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		int orderStatus=ServletUtils.getIntParameter(request, "orderStatus", 0);//状态，0：全部，1：未提交， 2：已提交， 3： 已过期
		map.put("orderStatus", orderStatus);
		B2bMemberDto memberDto = this.getMemberBysessionsId(request);
		try {
			PageModelOrderRecord<B2bAuctionOfferDtoEx> pm = b2bAuctionOfferService.orderRecords(map,memberDto,session.getIsAdmin());
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	
}
