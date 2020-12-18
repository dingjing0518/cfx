package cn.cf.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.LotteryUtil;
import cn.cf.common.RestCode;
import cn.cf.common.SendMails;
import cn.cf.dao.B2bAddressDaoEx;
import cn.cf.dao.B2bCreditGoodsDao;
import cn.cf.dao.B2bInvitationRecordDaoEx;
import cn.cf.dao.B2bLoanNumberDaoEx;
import cn.cf.dao.B2bLotteryActivityDaoEx;
import cn.cf.dao.B2bLotteryAwardDaoEx;
import cn.cf.dao.B2bLotteryRecordDaoEx;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bInvitationRecordDtoEx;
import cn.cf.dto.B2bLoanNumberDtoEx;
import cn.cf.dto.B2bLotteryActivityDtoEx;
import cn.cf.dto.B2bLotteryAwardDto;
import cn.cf.dto.B2bLotteryRecordDemoEx;
import cn.cf.dto.B2bLotteryRecordDto;
import cn.cf.dto.B2bLotteryRecordDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.entity.B2bAddressEntity;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bAddress;
import cn.cf.model.B2bLotteryAward;
import cn.cf.model.B2bLotteryRecord;
import cn.cf.service.B2bLotteryActivityService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class B2bLotteryActivityServiceImpl implements B2bLotteryActivityService {

	@Autowired
	private B2bLotteryActivityDaoEx lotteryActivityDaoEx;

	@Autowired
	private B2bLotteryAwardDaoEx lotteryAwardDaoEx;

	@Autowired
	private B2bLotteryRecordDaoEx lotteryRecordDaoEx;

	@Autowired
	private B2bCreditGoodsDao creditGoodsDao;

	@Autowired
	private B2bAddressDaoEx addressDaoEx;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bMemberDaoEx b2bMemberDaoEx;

	@Autowired
	private B2bLoanNumberDaoEx loanNumberDaoEx;

	@Autowired
	private B2bInvitationRecordDaoEx invitationRecordDaoEx;

	/**
	 * 查询抽奖记录
	 */
	@Override
	public PageModel<B2bLotteryRecordDto> searchluckDrawRecordList(
			Map<String, Object> map) {
		PageModel<B2bLotteryRecordDto> pm = new PageModel<B2bLotteryRecordDto>();
		map.put("status", 2);
		List<B2bLotteryRecordDto> list = lotteryRecordDaoEx.searchGridEx(map);
		int count = lotteryRecordDaoEx.searchGridCountEx(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	/**
	 * 抽奖活动首页
	 */
	@Override
	public B2bLotteryActivityDtoEx searchLotteryActivity(Integer activityType,
			Sessions session) {
		int flag = 0;
		List<B2bLotteryActivityDtoEx> list;
		B2bLotteryActivityDtoEx dto = new B2bLotteryActivityDtoEx();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityType", activityType);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("NowFlag", 1);// 有效活动
		map.put("orderName", "startTime");
		map.put("orderType", "asc");
		list = lotteryActivityDaoEx.searchActivityList(map);
		if (null == list || list.size() == 0) {// 如果没有进行时的活动，则查看最近结束的活动
			flag = 1;
			map.remove("NowFlag");
			map.put("orderName", "endTime");
			map.put("orderType", "desc");
			list = lotteryActivityDaoEx.searchActivityList(map);
		}
		if (null != list && list.size() > 0) {
			dto = list.get(0);
			if (activityType == 1) {// 抽奖活动
				Map<String, Object> Sourcemap = new HashMap<String, Object>();
				Sourcemap.put("activityPk", dto.getPk());
				Sourcemap.put("isDelete", 1);
				Sourcemap.put("isVisable", 1);
				Sourcemap.put("orderType", "asc");

				// 奖品所有集合
				Sourcemap.put("orderName", "awardSort");
				List<B2bLotteryAwardDto> awardAll = lotteryAwardDaoEx
						.searchGrid(Sourcemap);
				dto.setAwardAll(awardAll);
				// 有奖项的奖品集合
				Sourcemap.put("orderName", "showSort");
				Sourcemap.put("status", 2);
				List<B2bLotteryAwardDto> awards = lotteryAwardDaoEx
						.searchGrid(Sourcemap);
				dto.setAwards(awards);
				// 中奖记录
				Sourcemap.remove("isDelete");
				Sourcemap.remove("isVisable");
				Sourcemap.put("orderName", "insertTime");
				Sourcemap.put("orderType", "desc");
				List<B2bLotteryRecordDto> records = lotteryRecordDaoEx
						.searchGrid(Sourcemap);
				dto.setRecords(records);
			} else {
				if(flag==1){
					dto.setCreditStatus(5);// 活动已结束
				}else{
					if (null != session && !"".equals(session.getCompanyPk())) {
						if (activityType == 2) {
							Map<String, Object> creditMap = new HashMap<String, Object>();
							creditMap.put("companyPk", session.getCompanyPk());
							creditMap.put("goodsType", 1);
							creditMap.put("status", 2);
							List<B2bCreditGoodsDto> greditGood = creditGoodsDao
									.searchList(creditMap);
							if (null != greditGood && greditGood.size() > 0) {
								int status = lotteryActivityDaoEx
										.searchCreditStatus(greditGood.get(0)
												.getCreditPk(), dto.getPk());
								if (status == 1) {// 活动期间内授信成功的用户
									Map<String, Object> recordMap = new HashMap<String, Object>();
									recordMap.put("companyPk",
											session.getCompanyPk());
									recordMap.put("activityPk", dto.getPk());
									List<B2bLotteryRecordDto> recordList = lotteryRecordDaoEx
											.searchList(recordMap);
									if (null != recordList && recordList.size() > 0) {
										dto.setCreditStatus(2);// 活动已参加
									} else {
										dto.setCreditStatus(3);// 马上采购
									}
								} else {// 非活动期间内授信成功的用户
									dto.setCreditStatus(4);// 您不符合该活动资格
								}

							} else {
								dto.setCreditStatus(1);// 马上申请
							}
						}
					} 
					
				}
				
			}
		}
		return dto;
	}

	/**
	 * 当前登陆人根据活动查询该活动有哪些奖品
	 * 
	 * @param activityPk
	 * @return
	 */
	@Override
	public List<B2bLotteryAwardDto> searchAwards(
			B2bLotteryActivityDtoEx activity, B2bMemberDto member) {
		List<B2bLotteryAwardDto> list = null;
		Map<String, Object> AwardMap = new HashMap<String, Object>();
		AwardMap.put("activityPk", activity.getPk());
		if (null == activity.getMaximumNumber()) {
			AwardMap.put("betweenFlag", 1);// 满足区间内
		} else if (null != activity.getMaximumNumber()) {
			if (0 == activity.getMaximumNumber()) {
				AwardMap.put("status", 1);// 1未中奖，2已中奖
			} else if (activity.getMaximumNumber() > 0) {
				// 查询该活动已中奖次数
				Map<String, Object> map = new HashMap<String, Object>();
				if (activity.getBindRole() == 1) {// 绑定角色1公司 2个人',
					map.put("companyPk", member.getCompanyPk());
				} else {
					map.put("memberPk", member.getPk());
				}
				map.put("activityPk", activity.getPk());
				map.put("status", 2);
				map.put("activityType", 1);
				// 已中奖次数
				int lotteryCounts = lotteryRecordDaoEx.searchGridCount(map);
				// 未满足中奖最大次数
				if (activity.getMaximumNumber() > lotteryCounts) {
					AwardMap.put("betweenFlag", 1);// 满足区间内
				} else {
					AwardMap.put("status", 1);// 1未中奖，2已中奖
				}
			}

		}
		list = lotteryAwardDaoEx.searchAwardList(AwardMap);
		if (null != list && list.size() > 0) {
			// 区间内，返回奖项
			return list;
		} else {
			// 查询非区间内，获奖奖项状态都为status 1未中奖 status（1未中奖，2已中奖）
			AwardMap.put("status", 1);
			AwardMap.remove("betweenFlag");
		}
		return lotteryAwardDaoEx.searchAwardList(AwardMap);
	}

	// 根据pk查询奖品
	@Override
	public B2bLotteryAwardDto searchAwardByPk(String awardPk) {
		return lotteryAwardDaoEx.getByPk(awardPk);
	}

	// 根据奖品pk,member信息，插入抽奖记录
	@Override
	public B2bLotteryRecordDemoEx insertEntity(B2bLotteryAwardDto awardDto,
			B2bMemberDto member) {
		Integer AwardStatus = 3; // '奖品发放状态 1未发放，2已发放', 3 无,
		if (null != awardDto.getStatus() && awardDto.getStatus() == 2) {// 中奖
			if (awardDto.getAwardType() == 2) {// 奖品类型：1优惠券(暂无) 2.实物 3.再接再厉、明日再来
				AwardStatus = 1;// '奖品发放状态 1未发放，2已发放', 3 无,
			}
		}
		B2bLotteryRecordDemoEx recordADD = new B2bLotteryRecordDemoEx(member,
				awardDto, AwardStatus, null);

		// 如果是实物
		if (awardDto.getAwardVariety() == 2) {
			B2bAddressDto addressA = searchAddress(member);

			if (null != addressA && null != addressA.getPk()
					&& !"".equals(addressA.getPk())) {
				recordADD = new B2bLotteryRecordDemoEx(member, awardDto,
						AwardStatus, addressA);
			}

		}
		B2bLotteryRecord record = new B2bLotteryRecord();
		record.UpdateDTO(recordADD);
		record.setActivityType(1);
		lotteryRecordDaoEx.insert(record);// 抽奖记录
		B2bLotteryAward model = new B2bLotteryAward();
		model.UpdateDTO(awardDto);
		int minAmount = 0;
		if (null != model.getAmount() && model.getAmount() > 0) {
			minAmount = model.getAmount() - 1;
		}
		model.setAmount(minAmount);
		lotteryAwardDaoEx.update(model);// 更新奖品库存数量
		recordADD.setAwardSort(awardDto.getAwardSort());

		return recordADD;

	}

	/**
	 * 获取地址，先抽奖记录已有地址，后默认地址，随机，没有则无
	 * 
	 * @param member
	 * @return
	 */
	private B2bAddressDto searchAddress(B2bMemberDto member) {
		// 查询是否有抽奖过实物
		List<B2bLotteryRecord> beforeRecords = lotteryRecordDaoEx
				.searchBeforeRecords(member.getPk());
		B2bAddressDto addressA = new B2bAddressDto();
		if (beforeRecords.size() > 0) {
			// 获取最新记录
			B2bLotteryRecord beforeRecord = beforeRecords.get(0);
			addressA.UpdateModel(beforeRecord);
			addressA.setPk(beforeRecord.getAddressPk());
		} else {
			addressA = searchCompanyAddress(member.getCompanyPk(), 1);
		}

		return addressA;
	}

	private B2bAddressDto searchCompanyAddress(String companyPk, int flag) {
		B2bAddressDto address = new B2bAddressDto();
		Map<String, Object> mapAdd = new HashMap<String, Object>();
		mapAdd.put("companyPk", companyPk);
		mapAdd.put("isDefault", 1);
		mapAdd.put("isDelete", 1);
		mapAdd.put("orderName", "insertTime");
		mapAdd.put("orderType", "desc");
		// 查询是否有设默认收货地址??
		List<B2bAddressDto> addressT = addressDaoEx.searchListEx(mapAdd);
		if (addressT.size() > 0) {
			address = addressT.get(0);
		} else if (flag == 1) {
			mapAdd.remove("isDefault");
			List<B2bAddressDto> addressB = addressDaoEx.searchListEx(mapAdd);
			if (addressB.size() > 0) {
				address = addressB.get(0);
			}
		}
		return address;
	}

	// 邀请管理页面，返回pagemodel
	@Override
	public PageModel<B2bInvitationRecordDtoEx> searchinvitationRecordList(
			Map<String, Object> map) {
		PageModel<B2bInvitationRecordDtoEx> pm = new PageModel<B2bInvitationRecordDtoEx>();
		List<B2bInvitationRecordDtoEx> list = invitationRecordDaoEx
				.searchGridEx(map);
		int count = invitationRecordDaoEx.searchGridCountEx(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	/**
	 * 邀请管理页签数字
	 */
	@Override
	public List<B2bInvitationRecordDtoEx> searchinvitationStatusCounts(
			Map<String, Object> map) {
		return invitationRecordDaoEx.searchinvitationStatusCounts(map);
	}

	@Override
	public B2bInvitationRecordDtoEx getinvitationRecordDetailByPk(String pk) {
		return invitationRecordDaoEx.getDtoExByPk(pk);
	}

	/**
	 * 抽奖记录详情
	 */
	@Override
	public B2bLotteryRecordDto getlotteryRecordDetailByPk(String pk) {
		return lotteryRecordDaoEx.getByPk(pk);
	}

	/**
	 * 更新抽奖记录（地址）
	 */
	@Override
	public RestCode updatelotteryRecord(B2bLotteryRecordDtoEx dto) {
		int result = 0;
		String addressPk = "";
		B2bLotteryRecordDto recordEntity = lotteryRecordDaoEx.getByPk(dto
				.getPk());// 已存在记录
		if (null != recordEntity) {
			if (null != dto.getAddressId() && !"".equals(dto.getAddressId())) {// 活动页面提交收货地址pk
				B2bAddressDto address = addressDaoEx.getByPkEx(dto
						.getAddressId());
				if (null == address) {
					B2bLotteryRecordDtoEx oldRecord = new B2bLotteryRecordDtoEx();
					oldRecord.UpdateMine(recordEntity);
					addressPk = addAddress(oldRecord,
							recordEntity.getCompanyPk(),
							recordEntity.getCompanyName());
					dto.setAddressPk(addressPk);
					address = addressDaoEx.getByPkEx(addressPk);
				}
				B2bLotteryRecordDemoEx demoEx = new B2bLotteryRecordDemoEx(
						address);
				dto.UpdateMine(demoEx);
			} else {// 个人中心编辑
				if (null != recordEntity.getAddressPk()
						&& !"".equals(recordEntity.getAddressPk())) {// 记录里面有addresspk值,更新address表
					B2bAddressDto address = addressDaoEx.getByPkEx(recordEntity
							.getAddressPk());// 已存在收货地址
					if (null != address) {
						B2bAddressEntity addressEntity = new B2bAddressEntity(
								address.getPk(), dto, null);
						addressDaoEx.updateEx(addressEntity);// 更新
					} else {// 收货地址表未查到，则新增一条
						addressPk = addAddress(dto,
								recordEntity.getCompanyPk(),
								recordEntity.getCompanyName());
						dto.setAddressPk(addressPk);
					}
				} else {// 记录里面没有addresspk值,
					addressPk = addAddress(dto, recordEntity.getCompanyPk(),
							recordEntity.getCompanyName());
					dto.setAddressPk(addressPk);
				}
			}
			B2bLotteryRecord record = new B2bLotteryRecord();

			record.UpdateDTO(dto);
			result = lotteryRecordDaoEx.updateEx(record);
			if (result == 0) {
				return RestCode.CODE_A004;
			}
		}
		return RestCode.CODE_0000;
	}

	private String addAddress(B2bLotteryRecordDtoEx dto, String companyPk,
			String companyName) {
		String key = KeyUtils.getUUID();
		B2bAddress addressM = new B2bAddress();
		addressM.UpdateDTO(dto);
		addressM.setCompanyPk(companyPk);
		List<B2bAddress> addressLlist = addressDaoEx.searchDTO(addressM);// 地址表是否存在同地址
		if (null == addressLlist || addressLlist.size() == 0) {// 没有则新增
			addressM.setSigningCompany(companyName);
			addressM.setPk(key);
			addressM.setIsDefault(2);
			addressM.setIsDelete(1);
			addressM.setInsertTime(new Date());
			addressDaoEx.insert(addressM);
		} else {
			key = addressLlist.get(0).getPk();

		}
		return key;
	}

	@Override
	public int searchToDayByActivityPk(String memberPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberPk", memberPk);
		return lotteryRecordDaoEx.searchToDayByActivityPk(map);
	}

	@Override
	public B2bLotteryActivityDtoEx searchOnlineEntity(String activityPk) {
		return lotteryActivityDaoEx.searchOnlineEntity(activityPk);
	}

	 
	
	
	@Override
	public String luckDraw(String activityPk, B2bMemberDto member) {
		// 判断活动是否有效
		B2bLotteryActivityDtoEx activity = searchOnlineEntity(activityPk);
		if (null != activity) {
			// 判断今日是否抽奖
			int SameDay = searchToDayByActivityPk(member.getPk());
			if (SameDay > 0) {
				return RestCode.CODE_C019.toJson();
			} else {
				// 查看该活动下的奖项
				List<B2bLotteryAwardDto> awardList = searchAwards(activity,
						member);
				if (null != awardList && awardList.size() > 0) {
					LotteryUtil ll = new LotteryUtil(awardList);
					int index = ll.randomColunmIndex();
					B2bLotteryAwardDto award = searchAwardByPk(ll
							.getLotteryList().get(index).getAwardPk());
					if (null != award) {
						B2bLotteryRecordDemoEx recordEx = insertEntity(award,
								member);
						return RestCode.CODE_0000.toJson(recordEx);
					}

				} else {// 没有奖品
					B2bLotteryRecordDemoEx recordEx = addRecordDemo(activityPk,
							member);// 添加没有中奖记录
					return RestCode.CODE_0000.toJson(recordEx);
				}
			}
		} else {
			return RestCode.CODE_C018.toJson();
		}
		return RestCode.CODE_0000.toJson();
	}

	/***
	 * 添加一条未中奖记录
	 * 
	 * @param activityPk
	 * @param member
	 * @return
	 */
	private B2bLotteryRecordDemoEx addRecordDemo(String activityPk,
			B2bMemberDto member) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityPk", activityPk);
		map.put("awardType", 3);
		map.put("awardVariety", 3);
		map.put("status", 1);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		B2bLotteryAwardDto award = new B2bLotteryAwardDto();
		List<B2bLotteryAwardDto> awardList = lotteryAwardDaoEx.searchList(map);
		if (null != awardList && awardList.size() > 0) {
			award = awardList.get(0);
		}
		award.setActivityPk(activityPk);
		B2bLotteryRecordDemoEx recordDemo = new B2bLotteryRecordDemoEx(member,
				award, 3, null);
		B2bLotteryRecord record = new B2bLotteryRecord();
		record.UpdateDTO(recordDemo);
		record.setActivityType(1);
		lotteryRecordDaoEx.insert(record);// 抽奖记录

		return recordDemo;
	}

	@Override
	public void sendMailByLotteryActivity() {
		List<B2bLotteryActivityDtoEx> activityList;
		B2bLotteryActivityDtoEx activity = new B2bLotteryActivityDtoEx();
		// 当前进行时的活动
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityType", 3);// 老用户尊享礼
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("NowFlag", 1);// 有效活动
		map.put("orderName", "startTime");
		map.put("orderType", "asc");
		activityList = lotteryActivityDaoEx.searchActivityList(map);
		if (null != activityList && activityList.size() > 0) {
			activity = activityList.get(0);
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("startTime", activity.getStartTime());
			orderMap.put("endTime", activity.getEndTime());
			List<B2bMemberDtoEx> memberList = b2bMemberDaoEx
					.searchMemberByOrderForEconomicsGoodsTypeIsOne(orderMap);
			if (null != memberList && memberList.size() > 0) {
				SendMails mail = new SendMails();
				mail.setFreeSignName("白条活动");
				String str = "<a href='/lottery'>白条狂欢月活动期间，截至"
						+ DateUtil.formatYearMonthDay(new Date());
				mail.setIsRead(1);// 1未读 2已读
				mail.setInsertTime(DateUtil.formatDateAndTime(new Date()));

				for (B2bMemberDtoEx member : memberList) {
					if (member.getLoanAmount() > 0) {
						String addStr = "您白条累计下单"
								+ ArithUtil.roundBigDecimal(new BigDecimal(
										member.getLoanAmount()), 2) + "元，即将获得"
								+ member.getCardAmount() + "元京东卡。查看活动详情>>></a>";
						mail.setContent(str + addStr);
						mail.setId(KeyUtils.getUUID());
						mail.setCompanyPk(member.getCompanyPk());
						mail.setCompanyName(member.getCompanyName());
						mail.setMemberPk(member.getPk());
						mail.setMobile(member.getMobile());
						mongoTemplate.insert(mail);
					}
				}
			}
		}

	}

	/**
	 * 白条新客户见面礼 赠送京东卡
	 */
	@Override
	public void sendDayDayCardByActivity() {
		List<B2bLotteryActivityDtoEx> activityList;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityType", 2);// 活动类型1.抽奖活动 2白条新客户见面礼 3白条老客户尊享礼',
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("NowFlag", 1);// 有效活动
		map.put("orderName", "startTime");
		map.put("orderType", "asc");
		activityList = lotteryActivityDaoEx.searchActivityList(map);// 进行中的活动
		if (null != activityList && activityList.size() > 0) {
			for (B2bLotteryActivityDtoEx activityDto : activityList) {
				Map<String, Object> orderMap = new HashMap<String, Object>();
				orderMap.put("startTime", activityDto.getStartTime());
				orderMap.put("endTime", activityDto.getEndTime());
				List<B2bLoanNumberDtoEx> loanList = loanNumberDaoEx
						.searchNewUsersLoanByEconomicsGoodsTypeIsOne(orderMap);// 新用户
				if (null != loanList && loanList.size() > 0) {
					for (B2bLoanNumberDtoEx loan : loanList) {
						// 查看b2blotteryRecord是否有该活动的记录
						Map<String, Object> recordMmap = new HashMap<String, Object>();
						recordMmap.put("activityType", 2);
						recordMmap.put("companyPk", loan.getPurchaserPk());
						List<B2bLotteryRecordDto> recordList = lotteryRecordDaoEx
								.searchList(recordMmap);
						if (null == recordList || recordList.size() == 0) {
							if (loan.getLoanAmount() > 0) {
								B2bAddressDto address = searchCompanyAddress(
										loan.getPurchaserPk(), 2);
								B2bLotteryRecord addRecord = new B2bLotteryRecord();
								B2bLotteryRecordDemoEx recordEx = new B2bLotteryRecordDemoEx(
										loan, activityDto, "白条新用户", address);
								recordEx.setActivityType(2);
								addRecord.UpdateDTO(recordEx);
								lotteryRecordDaoEx.insert(addRecord);
							}
						}

					}

				}

			}
		}

	}

	/***
	 * 白条老客户尊享礼
	 */
	@Override
	public void sendCardByActivity() {
		List<B2bLotteryActivityDtoEx> activityList;
		// 昨天结束的尊享礼活动
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityType", 3);// 活动类型1.抽奖活动 2白条新客户见面礼 3白条老客户尊享礼',
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("Yesterday", 1);// 昨天结束的
		map.put("orderName", "startTime");
		map.put("orderType", "asc");
		activityList = lotteryActivityDaoEx.searchActivityList(map);// 昨天结束的活动
		if (null != activityList && activityList.size() > 0) {
			for (B2bLotteryActivityDtoEx activityDto : activityList) {
				// 查看b2blotteryRecord是否有该活动的记录
				Map<String, Object> recordMmap = new HashMap<String, Object>();
				recordMmap.put("activityPk", activityDto.getPk());
				List<B2bLotteryRecordDto> recordList = lotteryRecordDaoEx
						.searchList(recordMmap);
				if (null == recordList || recordList.size() == 0) {
					Map<String, Object> orderMap = new HashMap<String, Object>();
					orderMap.put("startTime", activityDto.getStartTime());
					orderMap.put("endTime", activityDto.getEndTime());
					List<B2bLoanNumberDtoEx> loanList = loanNumberDaoEx
							.searchOldUsersLoanByEconomicsGoodsTypeIsOne(orderMap);// 白条使用用户
					if (null != loanList && loanList.size() > 0) {
						for (B2bLoanNumberDtoEx loan : loanList) {
							if (ArithUtil.sub(loan.getLoanAmount(), 50000)>=0) {
								B2bAddressDto address = searchCompanyAddress(
										loan.getPurchaserPk(), 2);
								B2bLotteryRecord addRecord = new B2bLotteryRecord();
								B2bLotteryRecordDemoEx recordEx = new B2bLotteryRecordDemoEx(
										loan, activityDto, "白条达标用户", address);
								recordEx.setActivityType(3);
								addRecord.UpdateDTO(recordEx);
								lotteryRecordDaoEx.insert(addRecord);
							}
						}

					}

				}

			}

		}
	}

}
