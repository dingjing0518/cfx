package cn.cf.task.operationReportFormsSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bEconomicsGoodsExDao;
import cn.cf.dao.B2bMemberExtDao;
import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.entity.LoginCompany;
import cn.cf.entity.MemberDataReport;
import cn.cf.service.operation.MemberDataService;

/**
 * 会员数据
 * 
 * @author xht 2018年10月17日
 */
@Component
@EnableScheduling
public class MemberDataFormsSchedule {

	@Autowired
	private MemberDataService memberDataService;

	@Autowired
	private B2bMemberExtDao b2bMemberExtDao;

	@Autowired
	private B2bEconomicsGoodsExDao economicsGoodsExDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 会员数据 每天0:30处理
	 */
	 @Scheduled(cron = "0 30 0 * * ?")
	public void MemberDataReport() {

		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();// 昨日
		if (isExsit(yesterDay) == 0) {
			mongoTemplate.insert(getOneday(yesterDay));
		}

		// 初始化以前数据
		if (countDataBeforeYesterDay(yesterDay) == 0) {
			// 查询会员最早的日期
			String originalDate = b2bMemberExtDao.searchOriginalDate();
			List<String> dateList = CommonUtil.getBetweenDate(originalDate, yesterDay);
			for (String date : dateList) {
				mongoTemplate.insert(getOneday(date));
			}
		}
	}

	

	private int isExsit(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		return (int) mongoTemplate.count(query, MemberDataReport.class);
	}

	private MemberDataReport getOneday(String day) {
		MemberDataReport report = memberDataService.searchOnedayData(day);
		// 计算登录相关的数据
		report.setPcLoginPurNum(countPCLoginPurNum(day));// PC端登录采购商数
		report.setWapLoginPurNum(countWapLoginPurNum(day));// Wap端登录采购商数
		report.setLoginSupNum(countLoginSupNum(day));// PC端登录供应商数
		report.setWapLoginSupNum(countWapLoginSupNum(day));// Wap端登录供应商数
		// 查询有效的申请公司
		
		List<B2bEconomicsCreditcustomerDtoEx> list = memberDataService.searchOneDayApplyCompany(day);
		getApplyNum(list, report);// 化纤白条申请数（客户数）,化纤贷申请数（客户数）
		List<B2bEconomicsCreditcustomerDtoEx> acclist = memberDataService.searchAccDayApplyCompany(day);
		getAccApplyNum(acclist, report);// 累计申请白条数,累计化纤贷申请数
		return report;
	}

	/**
	 * 计算累计申请公司数
	 * 
	 * @param list
	 * @param report
	 *            1白条，2化纤贷
	 * @return
	 */
	private void getAccApplyNum(List<B2bEconomicsCreditcustomerDtoEx> list, cn.cf.entity.MemberDataReport report) {
		Integer btNum = 0;
		Integer dNum = 0;
		if (list != null && list.size() > 0) {
			for (B2bEconomicsCreditcustomerDtoEx dto : list) {
				List<B2bEconomicsGoodsDto> goodsTypeList = getProductType(dto.getProductPks());
				if (goodsTypeList.size() > 0) {
					for (B2bEconomicsGoodsDto b2bEconomicsGoodsDto : goodsTypeList) {
						if (b2bEconomicsGoodsDto.getGoodsType() == 1) {
							btNum = btNum + 1;
						} else if (b2bEconomicsGoodsDto.getGoodsType() == 2) {
							dNum = dNum + 1;
						}
					}
				}
			}
			
		}
		report.setBtAccumApplyNum(btNum);
		report.setdAccumApplyNum(dNum);
	}

	/**
	 * 计算申请公司数
	 * 
	 * @param list
	 * @param report
	 *            1白条，2化纤贷
	 * @return
	 */
	private void getApplyNum(List<B2bEconomicsCreditcustomerDtoEx> list, MemberDataReport report) {
		Integer btNum = 0;
		Integer dNum = 0;
		if (list != null && list.size() > 0) {
			for (B2bEconomicsCreditcustomerDtoEx dto : list) {
				List<B2bEconomicsGoodsDto> goodsTypeList = getProductType(dto.getProductPks());
				if (goodsTypeList.size() > 0) {
					for (B2bEconomicsGoodsDto b2bEconomicsGoodsDto : goodsTypeList) {
						if (b2bEconomicsGoodsDto.getGoodsType() == 1) {
							btNum = btNum + 1;
						} else if (b2bEconomicsGoodsDto.getGoodsType() == 2) {
							dNum = dNum + 1;
						}
					}
				}
			}
		}
		report.setBtApplyNum(btNum);
		report.setdApplyNum(dNum);
	}

	private List<B2bEconomicsGoodsDto> getProductType(String productPks) {
		List<B2bEconomicsGoodsDto> list = new ArrayList<B2bEconomicsGoodsDto>();

		if (productPks != null && !productPks.equals("")) {
			List<B2bEconomicsGoodsDto> str = new ArrayList<B2bEconomicsGoodsDto>();
			String[] arr = productPks.split(",");
			if (arr.length > 0) {
				for (String pk : arr) {
					B2bEconomicsGoodsDto dto = new B2bEconomicsGoodsDto();
					dto.setPk(pk);
					str.add(dto);
				}
			}
			list = economicsGoodsExDao.getGoodsType(str);
		}
		return list;
	}

	private int countDataBeforeYesterDay(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(day));
		return (int) mongoTemplate.count(query, MemberDataReport.class);
	}

	private Integer countWapLoginSupNum(String day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditSpStatus", 2);
		map.put("source", 2);
		map.put("loginTime", day);
		return (int) mongoTemplate.count(getQuery(map), LoginCompany.class);
	}

	private Integer countLoginSupNum(String day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditSpStatus", 2);
		map.put("source", 1);
		map.put("loginTime", day);
		return (int) mongoTemplate.count(getQuery(map), LoginCompany.class);
	}

	private Integer countWapLoginPurNum(String day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditStatus", 2);
		map.put("source", 2);
		map.put("loginTime", day);
		return (int) mongoTemplate.count(getQuery(map), LoginCompany.class);
	}

	private Integer countPCLoginPurNum(String day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditStatus", 2);
		map.put("source", 1);
		map.put("loginTime", day);
		return (int) mongoTemplate.count(getQuery(map), LoginCompany.class);
	}

	private Query getQuery(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		return query;
	}

}
