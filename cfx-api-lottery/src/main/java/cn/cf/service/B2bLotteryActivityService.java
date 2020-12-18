package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bInvitationRecordDtoEx;
import cn.cf.dto.B2bLotteryActivityDtoEx;
import cn.cf.dto.B2bLotteryAwardDto;
import cn.cf.dto.B2bLotteryRecordDemoEx;
import cn.cf.dto.B2bLotteryRecordDto;
import cn.cf.dto.B2bLotteryRecordDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;

public interface B2bLotteryActivityService {
	
	/**
	 * 抽奖活动首页
	 * @param activityType
	 * @param session
	 * @return
	 */
	B2bLotteryActivityDtoEx searchLotteryActivity(Integer activityType, Sessions session);
 
 
	
	/**
	 *当前登陆人根据活动查询该活动有哪些奖品
	 * @param activityPk
	 * @return
	 */
	List<B2bLotteryAwardDto> searchAwards(B2bLotteryActivityDtoEx activit, B2bMemberDto member);
	
	/**
	 * 根据pk查询奖品
	 * @param awardPk
	 * @return
	 */
	B2bLotteryAwardDto searchAwardByPk(String awardPk);
	
	/**
	 * 插入中奖记录
	 * @param awardDto
	 * @param member
	 * @return
	 */
	B2bLotteryRecordDemoEx insertEntity(B2bLotteryAwardDto awardDto, B2bMemberDto member);
	
	/**
	 * 抽奖记录列表
	 * @param map
	 * @return
	 */
	PageModel<B2bLotteryRecordDto> searchluckDrawRecordList(Map<String, Object> map);
	
	
 
 
	
	/**
	 * 邀请管理pageModel
	 * @param map
	 * @return
	 */
	PageModel<B2bInvitationRecordDtoEx> searchinvitationRecordList(Map<String, Object> map);
	
	/**
	 * 邀请管理列表标签数量
	 * @param map
	 * @return
	 */
	List<B2bInvitationRecordDtoEx> searchinvitationStatusCounts(Map<String, Object> map);

	/**
	 * 邀请记录详情
	 * @param pk
	 * @return
	 */
	B2bInvitationRecordDtoEx getinvitationRecordDetailByPk(String pk);
	
	 
	
	
	/**
	 * 抽奖记录详情
	 * @param pk
	 * @return
	 */
	B2bLotteryRecordDto getlotteryRecordDetailByPk(String pk);
	
	
	/**
	 * 更新抽奖记录（地址）
	 * @param dto
	 * @return
	 */
	RestCode updatelotteryRecord(B2bLotteryRecordDtoEx dto);
	
 
	
	 
	
	
 
	
	 
	
	 

 
	

	 
 
 

	 

	 
 
 
	
 
 

 

 
 
   /****
     查询该活动当天抽奖记录
    */
	int searchToDayByActivityPk(String memberPk);
/***
 * 判断活动是否有效
 * @param activityPk
 * @return
 */
    B2bLotteryActivityDtoEx searchOnlineEntity(String activityPk);
/**
 * 抽奖
 * @param activityPk
 * @param member
 * @return
 */
    String luckDraw(String activityPk, B2bMemberDto member);
 
 

	/**
     * 执行时间:每天凌程0:30执行一次
     * 发布白条活动 订单累计金额，推送站内信
     */
	void sendMailByLotteryActivity();


	/**
     * 执行时间:每天凌程0:50执行一次
     * 尊享礼昨日结束的活动   白条累计金额  赠送对应京东券
     */
	void sendCardByActivity();


/**
 * 执行时间，每十分钟执行一次
 * 新用户白条进行时活动，赠送京东卡
 */
	void sendDayDayCardByActivity();



}
