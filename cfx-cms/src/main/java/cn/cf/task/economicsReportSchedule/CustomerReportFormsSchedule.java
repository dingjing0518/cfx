package cn.cf.task.economicsReportSchedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bEconomicsCustomerExtDao;
import cn.cf.entity.EconomicsCustomerReportForms;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class CustomerReportFormsSchedule {

	private final static Logger logger = LoggerFactory.getLogger(CustomerReportFormsSchedule.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bEconomicsCustomerExtDao b2bEconomicsCustomerExtDao;

//	@Autowired
//	private B2bEconomicsGoodsExDao b2bEconomicsGoodsExDao;

	/**
	 * 执行时间:每天0:30执行一次 定时任务:统计前天的金融产品用户申请量
	 */
	//@Scheduled(cron = "0 0/5 * * * ?")
	 @Scheduled(cron = "0 30 0 * * ?")
	public void countCustomer() {
		Date date = new Date();
		logger.error("**********************CustomerReportFormsSchedule_START************************" + date);
		// 昨日
		String yesterDay = CommonUtil.yesterDay();
		// 上周
		String lastWeekOne = CommonUtil.lastWeekOne();
		String lastWeekDay = CommonUtil.lastWeekDay();
		// 本周
		String selfWeekOne = CommonUtil.selfWeekOne();

		String selfWeekDay = new SimpleDateFormat("yyyy-MM-dd").format(date);
		// 上月21号
		String lastMonth = CommonUtil.specifyMonth(-1, 21, date);
		// 本月20号
		String selfMonth = CommonUtil.specifyMonth(0, 20, date);
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询金融产品类型组合
		List<String> goodsTypesPk = setGoodsTypeArr();

		// 统计昨日
		yesterdayCount(yesterDay, map, goodsTypesPk, date);
		// 统计本周
		lastDayCount(selfWeekOne, selfWeekDay, map, goodsTypesPk, date, Constants.MINUS_ONE, Constants.TWO);
		// 统计上周
		lastDayCount(lastWeekOne, lastWeekDay, map, goodsTypesPk, date, Constants.ZERO, Constants.THREE);
		//
		Calendar calendar = Calendar.getInstance();// 日历对象
		calendar.setTime(date);// 设置当前日期
		int month = calendar.get(Calendar.MONTH) + 1;// 当前月
		// // 统计本月
		lastDayCount(lastMonth, selfMonth, map, goodsTypesPk, date, month, Constants.FOUR);

		// 补充以前月份的统计
		countBeforeMonth(month, goodsTypesPk, map, Constants.FOUR, date);
	}

	private List<String> setGoodsTypeArr() {
		// 两种品类型
		String[] sourceType = Constants.ECONOMICS_REPORT_FORMS_TYPE;
		String[] goodsType = Constants.ECONOMICS_REPORT_FORMS_GOODSTYPE;
		List<String> goodsTypes = new ArrayList<String>();
		for (int i = 0; i < goodsType.length; i++) {
			for (int n = 0; n < sourceType.length; n++) {
				String type = goodsType[i];
				String source = sourceType[n];
				String s = type + "_" + source;
				goodsTypes.add(s);
			}
		}
		return goodsTypes;

	}

	private void yesterdayCount(String yesterDay, Map<String, Object> map, List<String> goodsTypesPk, Date date) {
		EconomicsCustomerReportForms reportForms = new EconomicsCustomerReportForms();
		map.put("staticTime", yesterDay);
		for (int i = 0; i < goodsTypesPk.size(); i++) {
			String[] type = goodsTypesPk.get(i).split("_");
			String source = type[1];
			String goodsType = type[0];
			map.put("source", source);
			map.put("goodsType", goodsType);
			Integer count = b2bEconomicsCustomerExtDao.ecoCustApplyYesterdayCounts(map);
			if (count == null) {
				count = 0;
			}
			setEconomicsCount(reportForms, i, count);
		}
		// 设置每个类型合计
		setGoodsTypeCount(reportForms);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy");
		reportForms.setId(KeyUtils.getUUID());
		reportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		reportForms.setTimes(Constants.ONE);
		reportForms.setMonth(Constants.MINUS_TWO);
		reportForms.setYear(sd.format(date));
		// 查询是否存在，如果存在，更新数据
		findMongo(date, Constants.MINUS_TWO, reportForms, sd);
		reportForms.setAddUpCount(0);
		reportForms.setAddUpSH(0);
		reportForms.setAddUpXFM(0);
		reportForms.setAddUpYX(0);
		reportForms.setAddUpPT(0);
		reportForms.setAddUpOther(0);

		setSelfDayAddUp(date, -2, reportForms, sd);
		mongoTemplate.save(reportForms);
	}

	private void lastDayCount(String startWeekTime, String endWeekTime, Map<String, Object> map,
			List<String> goodsTypesPk, Date date, int months, int times) {
		EconomicsCustomerReportForms reportForms = new EconomicsCustomerReportForms();
		map.put("startWeekTime", startWeekTime);
		map.put("endWeekTime", endWeekTime);
		for (int i = 0; i < goodsTypesPk.size(); i++) {
			String[] type = goodsTypesPk.get(i).split("_");
			String source = type[1];
			String goodsType = type[0];
			map.put("source", source);
			map.put("goodsType", goodsType);
			Integer count = b2bEconomicsCustomerExtDao.ecoCustApplyLastWeekCounts(map);
			if (count == null) {
				count = 0;
			}
			setEconomicsCount(reportForms, i, count);
		}
		// 设置每个类型合计
		setGoodsTypeCount(reportForms);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy");
		reportForms.setId(KeyUtils.getUUID());
		reportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		reportForms.setTimes(times);
		reportForms.setMonth(months);
		reportForms.setYear(sd.format(date));
		// 查询是否存在，如果存在，更新数据
		findMongo(date, months, reportForms, sd);
		mongoTemplate.save(reportForms);
		// 统计本月每种类型累计
		reportForms.setAddUpCount(0);
		reportForms.setAddUpSH(0);
		reportForms.setAddUpXFM(0);
		reportForms.setAddUpYX(0);
		reportForms.setAddUpPT(0);
		reportForms.setAddUpOther(0);

		setSelfDayAddUp(date, months, reportForms, sd);
	}

	/**
	 * 补充以前月份
	 */
	private void countBeforeMonth(int months, List<String> goodsTypesPk, Map<String, Object> map, int times,
			Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		// 查看本年度之前月份是否有统计
		for (int i = 0; i < months - 1; i++) {
			Query query = new Query();
			query.addCriteria(
					Criteria.where("times").is(Constants.FOUR).and("month").is(i + 1).and("year").is(sdf.format(date)));
			List<EconomicsCustomerReportForms> list = mongoTemplate.find(query, EconomicsCustomerReportForms.class);
			// 如果上个月没有统计，就补上
			if (list == null || list.size() == 0) {
				// 补齐以前月份，月份开始时间
				String startMonth = CommonUtil.specifyMonth((i + 1) - (months + 1), 21, date);
				// 补齐以前月份，月份结束时间
				String endMonth = CommonUtil.specifyMonth((i + 1) - months, 20, date);

				EconomicsCustomerReportForms rf = new EconomicsCustomerReportForms();
				map.put("startWeekTime", startMonth);
				map.put("endWeekTime", endMonth);
				for (int n = 0; n < goodsTypesPk.size(); n++) {
					String[] type = goodsTypesPk.get(i).split("_");
					String source = type[1];
					String goodsType = type[0];
					map.put("source", source);
					map.put("goodsType", goodsType);
					Integer count = b2bEconomicsCustomerExtDao.ecoCustApplyLastWeekCounts(map);
					if (count == null) {
						count = 0;
					}
					setEconomicsCount(rf, n, count);
				}
				// 设置每个类型合计
				setGoodsTypeCount(rf);
				rf.setId(KeyUtils.getUUID());
				rf.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
				rf.setTimes(times);
				rf.setMonth(i + 1);
				rf.setYear(sdf.format(date));
				mongoTemplate.save(rf);
				// 统计以前月份每种类型累计
				if (times == 4) {
					setSelfDayAddUp(date, i + 1, rf, sdf);
				}
			}
		}

		// 把以后月份设置为零
		if (months < 12) {
			for (int i = months + 1; i < 13; i++) {
				setObjVlue(i, sdf.format(date));
			}
		}
	}

	/**
	 * 默认先设置为零
	 */
	public void setObjVlue(int month, String date) {
		EconomicsCustomerReportForms nrf = new EconomicsCustomerReportForms();
		nrf.setId(KeyUtils.getUUID());
		nrf.setMonth(month);
		nrf.setYear(date);
		nrf.setTimes(Constants.FOUR);

		Query query = new Query();
		query.addCriteria(Criteria.where("month").is(month).and("year").is(date));
		List<EconomicsCustomerReportForms> list = mongoTemplate.find(query, EconomicsCustomerReportForms.class);
		if (list != null && list.size() > 0) {
		} else {
			mongoTemplate.save(nrf);
		}
	}

	private void setSelfDayAddUp(Date date, int months, EconomicsCustomerReportForms reportForms, SimpleDateFormat sd) {
		Query query = new Query();
		query.addCriteria(Criteria.where("times").is(Constants.FOUR).and("month").gte(1).lte(months).and("year")
				.is(sd.format(date)));
		List<EconomicsCustomerReportForms> list = mongoTemplate.find(query, EconomicsCustomerReportForms.class);
		// 本月的月份统计数量(做累计计算)

		int addUpSH = 0;
		int addUpPT = 0;
		int addUpXFM = 0;
		int addUpYX = 0;
		int addUpOther = 0;

		if (list != null && list.size() > 0) {
			for (EconomicsCustomerReportForms ecoCustRF : list) {
				// 本月之前月份的月份累计相加为本月累计
				addUpSH += ecoCustRF.getBlankSH() + ecoCustRF.getLoanSH() + ecoCustRF.getBlaLoSH();
				addUpPT += ecoCustRF.getBlankPT() + ecoCustRF.getLoanPT() + ecoCustRF.getBlaLoPT();
				addUpXFM += ecoCustRF.getBlankXFM() + ecoCustRF.getLoanXFM() + ecoCustRF.getBlaLoXFM();
				addUpYX += ecoCustRF.getBlankYX() + ecoCustRF.getLoanYX() + ecoCustRF.getBlaLoYX();
				addUpOther += ecoCustRF.getBlankOther() + ecoCustRF.getLoanOther() + ecoCustRF.getBlaLoOther();
			}
			reportForms.setAddUpSH(addUpSH);
			reportForms.setAddUpOther(addUpOther);
			reportForms.setAddUpPT(addUpPT);
			reportForms.setAddUpXFM(addUpXFM);
			reportForms.setAddUpYX(addUpYX);
			// 累计合计
			reportForms.setAddUpCount(addUpSH + addUpOther + addUpPT + addUpXFM + addUpYX);
			// 保存累计数据
		}
		mongoTemplate.save(reportForms);
	}

	private void setGoodsTypeCount(EconomicsCustomerReportForms reportForms) {
		reportForms.setBlankGoodsTypeCount(reportForms.getBlankSH() + reportForms.getBlankPT()
				+ reportForms.getBlankXFM() + reportForms.getBlankYX() + reportForms.getBlankOther());
		reportForms.setLoanGoodsTypeCount(reportForms.getLoanSH() + reportForms.getLoanPT() + reportForms.getLoanXFM()
				+ reportForms.getLoanYX() + reportForms.getLoanOther());
		reportForms.setBlaLoGoodsTypeCount(reportForms.getBlaLoSH() + reportForms.getBlaLoPT()
				+ reportForms.getBlaLoXFM() + reportForms.getBlaLoYX() + reportForms.getBlaLoOther());
	}

	private void findMongo(Date date, Integer months, EconomicsCustomerReportForms reportForms, SimpleDateFormat sd) {
		Query query = new Query();
		query.addCriteria(Criteria.where("times").is(reportForms.getTimes()).and("month").is(months).and("year")
				.is(sd.format(date)));
		List<EconomicsCustomerReportForms> list = mongoTemplate.find(query, EconomicsCustomerReportForms.class);
		if (list != null && list.size() > 0) {
			EconomicsCustomerReportForms customerReportForms = list.get(0);
			reportForms.setId(customerReportForms.getId());
		}
	}

	private void setEconomicsCount(EconomicsCustomerReportForms reportForms, int i, int count) {

		if (i == 0) {
			reportForms.setBlankSH(count);
		}
		if (i == 1) {
			reportForms.setBlankPT(count);
		}
		if (i == 2) {
			reportForms.setBlankXFM(count);
		}
		if (i == 3) {
			reportForms.setBlankYX(count);
		}
		if (i == 4) {
			reportForms.setBlankOther(count);
		}

		if (i == 5) {
			reportForms.setLoanSH(count);
		}
		if (i == 6) {
			reportForms.setLoanPT(count);
		}
		if (i == 7) {
			reportForms.setLoanXFM(count);
		}
		if (i == 8) {
			reportForms.setLoanYX(count);
		}
		if (i == 9) {
			reportForms.setLoanOther(count);
		}

		if (i == 10) {
			reportForms.setBlaLoSH(count);
		}
		if (i == 11) {
			reportForms.setBlaLoPT(count);
		}
		if (i == 12) {
			reportForms.setBlaLoXFM(count);
		}
		if (i == 13) {
			reportForms.setBlaLoYX(count);
		}
		if (i == 14) {
			reportForms.setBlaLoOther(count);
		}

		if (i == 15) {
			reportForms.setBlaLoSH(reportForms.getBlaLoSH()+count);
		}
		if (i == 16) {
			reportForms.setBlaLoPT(reportForms.getBlaLoPT()+count);
		}
		if (i == 17) {
			reportForms.setBlaLoXFM(reportForms.getBlaLoXFM()+count);
		}
		if (i == 18) {
			reportForms.setBlaLoYX(reportForms.getBlaLoYX()+count);
		}
		if (i == 19) {
			reportForms.setBlaLoOther(reportForms.getBlaLoOther()+count);
		}
	}
}
