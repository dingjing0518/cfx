package cn.cf.service.operation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.cf.dto.*;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.ManageAccount;
import org.springframework.web.multipart.MultipartFile;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.LotteryActivityRule;
import jxl.read.biff.BiffException;

public interface ActivityService {

	// =============================================活动场次管理=================================================

	/**
	 *  删除活动规则
	 * @param pk
	 * @return
	 */
	int delAuctionActivityRule(String pk);

	// =========================================抽奖活动======================================================
	/**
	 *  抽奖活动一览页面，返回pageModel
	 * @param qm
	 * @return
	 */
	PageModel<B2bLotteryActivityExDto> searchLotteryList(QueryModel<B2bLotteryActivityExDto> qm);


	/**
	 *  新增或者编辑活动
	 * @param b2bLotteryActivityExDto
	 * @return
	 */
	int updateLottery(B2bLotteryActivityExDto b2bLotteryActivityExDto);

	/**
	 * 根据活动类型获取活动对象
	 * @param type
	 * @return
	 */
	B2bLotteryActivityExDto getLotteryByActivityType(Integer type);

	/**
	 * 根据活动类型获取活动对象集合
	 * @param activityType
	 * @return
	 */
	List<B2bLotteryActivityExDto> getAllLotteryActivity(Integer activityType);

	/**
	 * 获取所有实体奖项集合
	 * @return
	 */
	List<B2bLotteryMaterialDto> getAllLotteryMaterailList();

	/**
	 * 根据活动pk获取活动
	 * @param pk
	 * @return
	 */
	B2bLotteryActivityExDto getLotteryByPk(String pk);
	/**
	 * 根据条件获取公司集合
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getB2bCompayDto(Map<String, Object> map);

	/**
	 * 根据Pk删除奖项规则
	 * @param pk
	 * @return
	 */
	int delLotteryRule(String pk);
	/**
	 * 获取奖项集合
	 * @param qm
	 * @return
	 */
	PageModel<B2bLotteryAwardExDto> searchLotteryAwardList(QueryModel<B2bLotteryAwardExDto> qm);
	
	/**
	 * 编辑，新增，删除奖项
	 * @param dto
	 * @return
	 */
	int editLotteryAward(B2bLotteryAwardExDto dto);
	/**
	 * 修改奖项
	 * @param dto
	 * @return
	 */
	int updateLotteryAward(B2bLotteryAwardExDto dto);

	/**
	 * 邀请管理列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bInvitationRecordExtDto> searchLotteryInvitationRecordList(QueryModel<B2bInvitationRecordExtDto> qm);
	
	
	/**
	 * 邀请管理列表
	 * @param qm
	 * @return
	 */
	List<B2bInvitationRecordExtDto> exportLotteryInvitationRecord(QueryModel<B2bInvitationRecordExtDto> qm);
	
	/**
	 * 邀请管理发放方式和状态导入
	 * @param file
	 * @return
	 */
	String importLotteryInvitationRecord(MultipartFile file) throws IOException, BiffException;
	
	/**
	 * 根据Pk查询获奖记录
	 * @param pk
	 * @return
	 */
	B2bInvitationRecordExtDto getInvitationRecordByPk(String pk,String tcompanyName);
	
	/**
	 * 修改邀请记录
	 * @param recordDto
	 * @return
	 */
	int updateInvitationRecord(B2bInvitationRecordExtDto recordDto);
	
	
	
	/**
	 * 根据pk查询奖项
	 * @param pk
	 * @return
	 */
	B2bLotteryAwardExDto getLotteryAwardByPk(String pk);
	
	/**
	 * 获取邀请人好友公司
	 * @return
	 */
	List<B2bCompanyDto> getB2bCompayDto();

	/**
	 * 获奖名单pagemodel
	 * @param qm
	 * @return
	 */
	PageModel<B2bLotteryRecordExDto> lotteryAwardRosterList(QueryModel<B2bLotteryRecordExDto> qm, ManageAccount account);
	
	/**
	 * 根据pk查询抽奖记录对象
	 * @param pk
	 * @return
	 */
	B2bLotteryRecordExDto getLotteryRecordByPk(String pk);
	
	/**
	 * 编辑抽奖记录
	 * @param dto
	 * @return
	 */
	int editLotteryRecord(B2bLotteryRecordExDto dto);

	/**
	 * 导出获奖名单List
	 * @param qm
	 * @return
	 */
	List<B2bLotteryRecordExDto> searchExportLotteryAwardList(QueryModel<B2bLotteryRecordExDto> qm,ManageAccount account);

	/**
	 * 保存抽奖导出信息记录
	 * @param params
	 * @return
	 */
	int saveLotteryAwardToOss(CustomerDataTypeParams params, ManageAccount account);
	
	/**
	 * 导入获奖名单
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	int importUpdateLotteryRecord (B2bLotteryRecordExDto dto) throws Exception;
	
	
	/**
	 * 搜索实体奖项列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bLotteryMaterialDto> searchLotteryMaterialList(QueryModel<B2bLotteryMaterialDto> qm);
	
	/**
	 * 修改实体奖项
	 * @param couponDto
	 * @return
	 */
	int updateLotteryMaterial(B2bLotteryMaterialDto couponDto);
	/**
	 * 查询类型为1的优惠券使用规则
	 * @param type
	 * @return
	 */
	LotteryActivityRule searchLotteryCouponUseRule(Integer type);
	
	/**
	 * 修改优惠券使用规则
	 * @param rule
	 * @return
	 */
	String updateLotteryCouponUseRule(LotteryActivityRule rule);

}
