package cn.cf.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bDimensionalityPresentExDao;
import cn.cf.dao.B2bDimensionalityRewardDao;
import cn.cf.dao.B2bMemberExtDao;
import cn.cf.dao.B2bMemberGradeDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bDimensionalityRewardDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.MemberPointErrorInfo;
import cn.cf.entity.MemberPointPeriod;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bDimensionalityPresent;
import cn.cf.model.B2bMember;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Component
public class AddMemberSysPoint {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bMemberExtDao b2bMemberExtDao;

	@Autowired
	private B2bDimensionalityRewardDao b2bDimensionalityRewardDao;

	@Autowired
	private B2bMemberGradeDao b2bMemberGradeDao;

	@Autowired
	private B2bCompanyExtDao b2bCompanyExtDao;

	@Autowired
	private B2bDimensionalityPresentExDao dimensionalityPresentExDao;


	/**
	 * 会员体系埋点方法（无额外奖励的情况）
	 * 
	 * @param memberPk
	 * @param companyPk
	 * @return
	 */

	public String addPoint(String memberPk, String companyPk, String dimenType, Integer flag) {
		int result = 0;
		MangoMemberPoint point = new MangoMemberPoint();
		point.setDimenType(dimenType);
		try {
			B2bDimensionalityRewardDto dto = null;
				String[] strArr = dimenType.split("_");
				Map<String, Object> map = new HashMap<>();
				map.put("dimenCategory", strArr[0]);
				map.put("dimenType", strArr[1]);
				map.put("isDelete", 1);
				map.put("isVisable", 1);
				List<B2bDimensionalityRewardDto> list = b2bDimensionalityRewardDao.searchList(map);
				if (list != null && list.size() > 0) {
					dto = list.get(0);
				}
			if (dto != null) {
				insertMember(memberPk, companyPk, dto, point, flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// addErrorRecord(point,e.getMessage());
		}
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	private void insertMember(String memberPk, String companyPk, B2bDimensionalityRewardDto dto, MangoMemberPoint point,
			Integer flag) throws Exception {
		B2bMemberDto memberDto = b2bMemberExtDao.getByPk(memberPk);
		B2bCompanyDto companyDto = b2bCompanyExtDao.getByPk(companyPk);
		Double memberfbGradeRatio = 1d;
		Double memberemGradeRatio = 1d;
		// 1.加分所需的系数比例
		// 1.1等级系数
		if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
			B2bMemberGradeDto gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(
					(memberDto.getLevel() == null || memberDto.getLevel().equals("")) ? 1 : memberDto.getLevel());
			if (gradeDto != null) {
				memberfbGradeRatio = gradeDto.getFbGradeRatio();
				memberemGradeRatio = gradeDto.getEmGradeRatio();
			}
		}

		// 1.2加权系数
		Double totalfbGradeRatio = ArithUtil.round(ArithUtil.mul(dto.getFibreShellNumber(), dto.getFibreShellRatio()), 2);
		Double totalemGradeRatio = ArithUtil.round(ArithUtil.mul(dto.getEmpiricalValue(), dto.getEmpiricalRatio()), 2);

		// 2.插入mongo
		point.setCompanyPk(companyPk);
		if (companyDto!=null) {//会员被删除
			point.setParentCompanyPk(companyDto.getParentPk().equals("-1") ? companyDto.getPk() : companyDto.getParentPk());
			point.setMemberPk(memberDto.getPk());
			point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
			point.setExpValue(ArithUtil.round(ArithUtil.mul(memberemGradeRatio, totalemGradeRatio), 2));
			point.setFiberValue(ArithUtil.round(ArithUtil.mul(memberfbGradeRatio, totalfbGradeRatio), 2));
			point.setFlag(flag);
			mongoTemplate.insert(point);
	
			// 3. 更新用户总积分
			B2bMember member = new B2bMember();
			member.setPk(memberDto.getPk());
			member.setShell((memberDto.getShell()==null?0:memberDto.getShell())+ArithUtil.round(ArithUtil.mul(memberfbGradeRatio, totalfbGradeRatio),0));
			member.setExperience((memberDto.getExperience()==null?0:memberDto.getExperience())+ArithUtil.round(ArithUtil.mul( memberemGradeRatio, totalemGradeRatio),0));
			member.setFeedTime(new Date());
			b2bMemberExtDao.update(member);
	
			// 4.插入加分明细
			insertB2bDimensionalityPresent(dto, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio,
					totalfbGradeRatio, totalemGradeRatio);
	
			// 5.添加赠送方式:赠送周期类型1.一次赠送，2每天，3每周，
			if (dto.getPeriodType() > 1) {
				insertMemberPointPeriod(dto, point, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio,
						totalfbGradeRatio, totalemGradeRatio);
			}
		}
	}

	private void insertMemberPointPeriod(B2bDimensionalityRewardDto dto, MangoMemberPoint point, B2bMemberDto memberDto,
			B2bCompanyDto companyDto, Double memberfbGradeRatio, Double memberemGradeRatio, Double totalfbGradeRatio,
			Double totalemGradeRatio) {
		MemberPointPeriod p = new MemberPointPeriod();
		p.setRewardPk(dto.getPk());
		p.setDimenCategory(dto.getDimenCategory());
		p.setDimenType(dto.getDimenType());
		p.setPeriodType(dto.getPeriodType());
		p.setFibreShellNumber(dto.getFibreShellNumber());
		p.setFibreShellRatio(dto.getFibreShellRatio());
		p.setEmpiricalValue(dto.getEmpiricalValue());
		p.setEmpiricalRatio(dto.getEmpiricalRatio());
		p.setCompanyPk(companyDto.getPk());
		p.setMemberPk(memberDto.getPk());
		p.setFbGradeRatio(memberfbGradeRatio);
		p.setEmGradeRatio(memberemGradeRatio);
		p.setInsertTime(new Date());
		p.setExpValue(point.getExpValue());
		p.setFiberValue(point.getFiberValue());
		p.setCompanyName(companyDto.getName());
		p.setDimenName(dto.getDimenName());
		p.setDimenTypeName(dto.getDimenTypeName());
		p.setContactsTel(companyDto.getContactsTel());
		p.setFbShellNumberWeighting(totalfbGradeRatio);
		p.setEmpiricalValueWeighting(totalemGradeRatio);
		p.setIsDelete(1);
		mongoTemplate.insert(p);
	}

	private void insertB2bDimensionalityPresent(B2bDimensionalityRewardDto dto, B2bMemberDto memberDto,
			B2bCompanyDto companyDto, Double memberfbGradeRatio, Double memberemGradeRatio, Double totalfbGradeRatio,
			Double totalemGradeRatio) {
		B2bDimensionalityPresent d = new B2bDimensionalityPresent();
		d.setPk(KeyUtils.getUUID());
		d.setRewardPk(dto.getPk());
		d.setCompanyPk(companyDto.getPk());
		d.setCompanyName(companyDto.getName());
		d.setMemberPk(memberDto.getPk());
		d.setContactsTel(companyDto.getContactsTel());
		d.setDimenCategory(dto.getDimenCategory());
		d.setDimenName(dto.getDimenName());
		d.setDimenType(dto.getDimenType());
		d.setDimenTypeName(dto.getDimenTypeName());
		d.setPeriodType(dto.getPeriodType());
		d.setFibreShellNumber(dto.getFibreShellNumber());
		d.setFibreShellRatio(dto.getFibreShellRatio());
		d.setEmpiricalValue(dto.getEmpiricalValue());
		d.setEmpiricalRatio(dto.getEmpiricalRatio());
		d.setFbGradeRatio(memberfbGradeRatio);
		d.setEmGradeRatio(memberemGradeRatio);
		d.setFbShellNumberWeighting(totalfbGradeRatio);
		d.setEmpiricalValueWeighting(totalemGradeRatio);
		d.setType(1);
		dimensionalityPresentExDao.insert(d);
	}

	@SuppressWarnings("unused")
	private void addErrorRecord(MangoMemberPoint point, String errorMsg) {
		MemberPointErrorInfo error = new MemberPointErrorInfo();
		try {
			BeanUtils.copyProperties(error, point);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mongoTemplate.insert(error);
	}

	/**
	 * 查询记录
	 */
	public List<MangoMemberPoint> searchPointList(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		// 1查询用户积分记录
		List<MangoMemberPoint> list = mongoTemplate.find(query, MangoMemberPoint.class);
		return list;
	}

	/**
	 * 取消订单
	 */
	public void cancelOrder(String orderNumber) {
		try {
			Criteria c = new Criteria();
			c.andOperator(Criteria.where("orderNumber").is(orderNumber));
			Query query = new Query(c);
			Update update = Update.update("isDelete", 2);
			mongoTemplate.updateMulti(query, update, MemberPointPeriod.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
