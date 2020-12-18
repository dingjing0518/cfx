package cn.cf.controler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bInvitationRecordDtoEx;
import cn.cf.dto.B2bLotteryActivityDtoEx;
import cn.cf.dto.B2bLotteryRecordDto;
import cn.cf.dto.B2bLotteryRecordDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bLotteryActivityService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * @author:ZLB
 * @describe:抽奖邀请活动
 */
@RestController
@RequestMapping("/lottery")
public class B2BLotteryActivityController extends BaseController{
	
	private final static Logger logger = LoggerFactory.getLogger(B2BLotteryActivityController.class);
	
	@Autowired 
	private B2bLotteryActivityService activityService;
	/**
	 * 抽奖操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/luckDraw", method = RequestMethod.POST)
	public String luckDraw(HttpServletRequest request) {
		String result = RestCode.CODE_0000.toJson();
		Sessions session = this.getSessions(request);
		B2bMemberDto member=this.getMemberBysessionsId(request);
		//抽奖记录
	 	String activityPk= ServletUtils.getStringParameter(request, "activityPk","");
	 	if(null==session.getCompanyPk()||"".equals(session.getCompanyPk())){
	 		return RestCode.CODE_M008.toJson();
	 	}
	 	if("".equals(activityPk)){
	 		return RestCode.CODE_A004.toJson();
	 	}else{
	 		result=activityService.luckDraw(activityPk,member);
	 	}
	  
		return result;
	}
	/**
	 * 抽奖活动首頁
	 * activityType 活动类型1.抽奖活动 2白条新客户见面礼 3白条老客户尊享礼
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/luckDrawHomePage", method = RequestMethod.POST)
	public String luckDrawHomePage(HttpServletRequest request) {
		String result="";
		try {
			Sessions session = this.getSessions(request);
		    Integer activityType=ServletUtils.getIntParameterr(request, "activityType", 1);
		    B2bLotteryActivityDtoEx dto=activityService.searchLotteryActivity(activityType,session);
		    if(null!=session&&activityType==1&&null!=dto){
		    	int SameDay =	activityService.searchToDayByActivityPk(session.getMemberPk());
		    	if(SameDay==0){
		    		dto.setLuckyNumber(1);
		    	}else{
		    		dto.setLuckyNumber(0);
		    	}
		    }
		    result= RestCode.CODE_0000.toJson(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("luckDrawHomePage exception:", e);
			result= RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	/**
	 * 我的奖品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchDrawRecordList", method = RequestMethod.POST)
	public String searchDrawRecordList(HttpServletRequest request) {
		String result="";
		try {
			Sessions session =this.getSessions(request);
			B2bMemberDto member = this.getMemberBysessionsId(request);
			Map<String, Object> map = this.paramsToMap(request);
			if(session.getIsAdmin()==1){
				map.put("isAdmin", member.getPk()); 
			}else{
				map.put("memberPk", member.getPk()); 
			} 
			
			map.put("companyPk", session.getCompanyPk());
			map.put("orderName", ServletUtils.getStringParameter(request, "orderName", "insertTime"));
			map.put("orderType", ServletUtils.getStringParameter(request, "orderType", "desc"));
			map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
	        map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
			PageModel<B2bLotteryRecordDto> pm = activityService.searchluckDrawRecordList(map);
			result = RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchDrawRecordList exception:", e);;
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 我的奖品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lotteryRecordDetail", method = RequestMethod.POST)
	public String lotteryRecordDetail(HttpServletRequest request) {
		String result="";
		try {
			String pk = ServletUtils.getStringParameter(request, "pk","");
			if ("".equals(pk)) {
				return RestCode.CODE_A001.toJson();
			}
			B2bLotteryRecordDto dto =  activityService.getlotteryRecordDetailByPk(pk);
			result = RestCode.CODE_0000.toJson(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("lotteryRecordDetail exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
 
	/***
	 * 奖品邮寄地址/更改收货信息
	 */
	@RequestMapping(value = "updatelotteryRecord", method = RequestMethod.POST)
	public String updatelotteryRecord(HttpServletRequest req) {
		String result="";
		try {
			B2bLotteryRecordDtoEx dto = new B2bLotteryRecordDtoEx();
			dto.bind(req);
			RestCode restCode = activityService.updatelotteryRecord(dto);
			result = restCode.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updatelotteryRecord exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	
	
	/**
	 * 邀请管理页面，返回PageModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/invitationRecordList", method = RequestMethod.POST)
	public String invitationRecordList(HttpServletRequest request) {
		String result="";
		try {
			B2bMemberDto member=this.getMemberBysessionsId(request);
		    Map<String, Object> map=this.paramsToMap(request);
		    map.put("member", member.getPk());
		    map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
			map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
			map.put("orderName", request.getParameter("orderName")!=null?request.getParameter("orderName"):"insertTime");
			map.put("orderType", request.getParameter("orderType")!=null?request.getParameter("orderType"):"desc");
			PageModel<B2bInvitationRecordDtoEx> pm = activityService.searchinvitationRecordList(map);
			result = RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("invitationRecordList exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	/***
	 * 邀请管理列表标签数量
	 */
	@RequestMapping(value = "searchinvitationStatusCounts", method = RequestMethod.POST)
	public String searchinvitationStatusCounts(HttpServletRequest request) {
		String result="";
		try {
			B2bMemberDto member=this.getMemberBysessionsId(request);
			Map<String, Object> map = this.paramsToMap(request);
		    map.put("member", member.getPk());
			List<B2bInvitationRecordDtoEx> list = activityService.searchinvitationStatusCounts(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchinvitationStatusCounts exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 邀请记录详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/invitationRecordDetail", method = RequestMethod.POST)
	public String invitationRecordDetail(HttpServletRequest request) {
		String result="";
		try {
			String pk = ServletUtils.getStringParameter(request, "pk","");
			if ("".equals(pk)) {
				return RestCode.CODE_A001.toJson();
			}
			B2bInvitationRecordDtoEx dto =  activityService.getinvitationRecordDetailByPk(pk);
			result = RestCode.CODE_0000.toJson(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("invitationRecordDetail exception:", e);
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	 
	
	 
	 
	
	 
	
	 
	
	


}
