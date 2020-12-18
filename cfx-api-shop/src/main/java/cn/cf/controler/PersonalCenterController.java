package cn.cf.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.constant.Source;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.PersonalCenterDetail;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.CommonService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;


/**
 * @author:FJM
 * @describe:个人中心接口列表
 * @time:2017-6-13 下午8:41:16
 */
@RestController
@RequestMapping("/shop")
public class PersonalCenterController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(PersonalCenterController.class);

	@Autowired
	private B2bCompanyService b2bCompanyService;

	@Autowired
	private B2bGoodsService goodsService;

    @Autowired
    private B2bFacadeService b2bFacadeService;
    
	@Autowired
	private CommonService commonService;
	
	/**
	 * 个人中心
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "personalCenter", method = RequestMethod.POST)
	public String personalCenter(HttpServletRequest req) {
		String result="";
		try {
			Sessions session = this.getSessions(req);
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			if(null==company){
				return RestCode.CODE_M008.toJson();
			}
			JSONObject json = new JSONObject();
			json.put("mobile", session.getMobile());
			json.put("companyName", session.getCompanyName());
			json.put("lastLoginTime", session.getLastLoginTime());
			json.put("headPortrait", company.getHeadPortrait() == null ? "" : company.getHeadPortrait());
			result = RestCode.CODE_0000.toJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("personalCenter Exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	/**
	 * 采购商个人中心
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "personalCenterByPurchase", method = RequestMethod.POST)
	public String personalCenterByPurchase(HttpServletRequest req) {
		String result="";
		try {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			if(null==company){
				return RestCode.CODE_M008.toJson();
			}
			if(null != company.getAuditStatus() && company.getAuditStatus()== 1){
				company = b2bCompanyService.getCompany(company.getPk());
//				if(null != company){
//					JedisUtils.set(company.getPk(), company,PropertyConfig.getIntProperty("session_time", 3600));
//				}
			}
			PersonalCenterDetail person = new PersonalCenterDetail();
			person.setCompany(company);
//			person.setLeftMenus(session.getPurMenus());
//			person.setCompanyName(session.getCompanyName());
//			Map<String, Object> map = new HashMap<String, Object>();
//			String[] companyPks = b2bCompanyService.findCompanys(session.getCompanyPk(),1);
//			map.put("purchaserPks", companyPks);
//			map.put("isDelete", 1);
			//person.setOrderCount(newOrderService.orderStatusCounts(map));
//			B2bCreditDtoEx credit = b2bCreditService.searchB2bCredit(session.getCompanyPk());
//			if (credit != null) {
//				person.setTotalAmount(credit.getCreditPlatfromAmount() == null ? 0 : credit.getCreditPlatfromAmount());
//				person.setAvailableAmount(
//						(credit.getCreditPlatfromAmount() == null ? 0 : credit.getCreditPlatfromAmount())
//								- (credit.getCreditUsAmount() == null ? 0 : credit.getCreditUsAmount()));
//				if (credit.getCreditStartTime() != null && credit.getCreditEndTime() != null) {
//					person.setCreditStartTime(credit.getCreditStartTime().toString());
//					person.setCreditEndTime(credit.getCreditEndTime().toString());
//				}
//			} else {
//				person.setTotalAmount(0d);
//				person.setAvailableAmount(0d);
//			}
//			Query read = new Query(
//					Criteria.where("memberPk").is(session.getMemberPk()).orOperator(Criteria.where("isRead").is(2)));
//			Query Unread = new Query(
//					Criteria.where("memberPk").is(session.getMemberPk()).orOperator(Criteria.where("isRead").is(1)));
//			int allRead = (int) mongoTemplate.count(read, SendMails.class);
//			int unreads = (int) mongoTemplate.count(Unread, SendMails.class);
//			person.setReadCounts(allRead);
//			person.setUnReadCounts(unreads);
			result = RestCode.CODE_0000.toJson(person);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("personalCenterByPurchase exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	/**
	 * 供应商个人中心
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "personalCenterBySupplier", method = RequestMethod.POST)
	public String personalCenterBySupplier(HttpServletRequest req) {
		try {
			Sessions session = this.getSessions(req);
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			if(null==company){
				return RestCode.CODE_M008.toJson();
			}
			company = b2bCompanyService.getCompanyDto(company.getPk());
			PersonalCenterDetail person = new PersonalCenterDetail();
			person.setCompany(company);
//			person.setLeftMenus(session.getSupMenus());
			person.setCompanyName(session.getCompanyName());
			Map<String, Object> map = new HashMap<String, Object>();
			String[] companyPks = b2bCompanyService.findCompanys(session.getCompanyPk(),2);
			map.put("supplierPks", companyPks);
			map.put("isDeleteSp", 1);
			//Map<String, Object> orderCounts = newOrderService.orderStatusCounts(map);
			//person.setOrderCount(orderCounts);
//			Query read = new Query(
//					Criteria.where("memberPk").is(session.getMemberPk()).orOperator(Criteria.where("isRead").is(2)));
//			Query Unread = new Query(
//					Criteria.where("memberPk").is(session.getMemberPk()).orOperator(Criteria.where("isRead").is(1)));
//			int allRead = (int) mongoTemplate.count(read, SendMails.class);
//			int unreads = (int) mongoTemplate.count(Unread, SendMails.class);
//			person.setReadCounts(allRead);
//			person.setUnReadCounts(unreads);
			//person.setOrderCounts(Integer.parseInt(orderCounts.get("allCounts").toString()));
			map.put("companyPks", companyPks);
			//List<B2bGoodsDtoEx> auditLists = goodsService.searchAuditCounts(map);
//			List<B2bGoodsDtoEx> updownLists = goodsService.searchUpdownCounts(map);
			Integer auditCounts = 0;
//			Integer updownCounts = 0;
			//销售中的商品数量
		/*	if (auditLists != null && auditLists.size() > 0) {
				for (int i = 0; i < auditLists.size(); i++) {
					if (auditLists.get(i).getAuditStatus() == 2) {
						auditCounts = auditLists.get(i).getCounts();
					}
				}
			}*/
			auditCounts = goodsService.searchSaleGoodsCounts(map);
			
//			// 已上架的商品数量
//			if (updownLists != null && updownLists.size() > 0) {
//				for (int i = 0; i < updownLists.size(); i++) {
//					if (updownLists.get(i).getIsUpdown() == 2) {
//						updownCounts = updownLists.get(i).getCounts();
//					}
//				}
//			}
			person.setSaleGoodCounts(auditCounts);
//			person.setAuditGoodCounts(updownCounts);
//			map.put("supplierPks", companyPks);
			// Map<String,Object> maps =
			// newOrderService.totalVolumesAndAmounts(map);
			// person.setVolumes(Double.parseDouble(maps.get("volumes").toString()));
			// person.setAllAmounts(Double.parseDouble(maps.get("amounts").toString()));
			return RestCode.CODE_0000.toJson(person);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}

	/**
	 * 保存头像 
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "saveHeadPortrait", method = RequestMethod.POST)
	public String saveHeadPortrait(HttpServletRequest req) {
		String result="";
		try {
			Sessions session = this.getSessions(req);
			String headPortrait = ServletUtils.getStringParameter(req, "headPortrait", "");
			if ("".equals(headPortrait)) {
				return RestCode.CODE_A001.toJson();
			}
			String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
			if("".equals(companyPk)){
				companyPk = session.getCompanyPk();
			}
			b2bFacadeService.saveHeadPortrait(companyPk, headPortrait);
			//更新缓存里的头像
			B2bCompanyDto company  = b2bCompanyService.getCompany(session.getCompanyPk());
			company.setHeadPortrait(headPortrait);
			Sessions sessions = this.getSessions(req);
			sessions.setCompanyDto(company);
			JedisUtils.set(sessions.getSessionId(), sessions, Source.getBySource(this.getSource(req)).getSessionTime());
			//首次添加头像加积分
			addHeadPoint(session);
			result = RestCode.CODE_0000.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveHeadPortrait exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}

	private void addHeadPoint(Sessions session) {
		try {
			String dimenType =MemberSys.ACCOUNT_DIMEN_LOG.getValue();
			Map<String, String> paraMap=new HashMap<>();
			paraMap.put("dimenType", dimenType);
			paraMap.put("companyPk", session.getCompanyPk());
			/*String result= HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/searchPointList", paraMap);
			try {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonarray = JSONArray.fromObject(JsonUtils.getJsonData(result));  
			if (jsonarray == null || jsonarray.size() == 0) {
				paraMap.remove("dimenType");
				paraMap.remove("companyPk");
				paraMap.put("memberPk", session.getMemberPk());
				paraMap.put("companyPk", session.getCompanyPk());
				paraMap.put("pointType", "1");
				paraMap.put("active", dimenType);
				HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/addPoint", paraMap);
			}*/
			List<MangoMemberPoint> list = commonService.searchPointList(paraMap);
			if (null==list || list.size()==0) {
				paraMap.remove("dimenType");
				paraMap.remove("companyPk");
				paraMap.put("memberPk", session.getMemberPk());
				paraMap.put("companyPk", session.getCompanyPk());
				paraMap.put("pointType", "1");
				paraMap.put("active", dimenType);
				commonService.addPoint(session.getMemberPk(), session.getCompanyPk(), 1, dimenType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
