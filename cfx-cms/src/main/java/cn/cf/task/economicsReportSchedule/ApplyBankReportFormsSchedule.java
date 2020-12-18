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
import cn.cf.dao.B2bCreditExtDao;
import cn.cf.dao.B2bEconomicsBankCompanyExtDao;
import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.entity.EconCreditBankEntry;
import cn.cf.entity.EconomicsCustomerBankReportForms;
import cn.cf.json.JsonUtils;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class ApplyBankReportFormsSchedule {

	private final static Logger logger = LoggerFactory.getLogger(CustomerReportFormsSchedule.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private B2bEconomicsBankExtDao b2bEconomicsBankExtDao;

	@Autowired
	private B2bCreditExtDao b2bCreditExtDao;

	@Autowired
	private B2bEconomicsBankCompanyExtDao b2bEconomicsBankCompanyExtDao;

	/**
	 * 银行审批客户数统计 每天0:30处理
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	// 每天凌晨12:30执行一次
	public void countBankCustomer() {

		Date date = new Date();
		logger.error("**********************ApplyBankReportFormsSchedule_START************************" + date);
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

		// 本月
		Calendar calendar = Calendar.getInstance();// 日历对象
		calendar.setTime(date);// 设置当前日期
		int month = calendar.get(Calendar.MONTH) + 1;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		List<B2bEconomicsBankDto> list = b2bEconomicsBankExtDao.searchGrid(map);
		if (list != null && list.size() > 0) {
			for (B2bEconomicsBankDto economicsBankDto : list) {
				map.put("bankPk", economicsBankDto.getPk());
				// // 统计昨日
				countBankLastday(null, null, yesterDay, map, economicsBankDto, date, Constants.MINUS_TWO,
						Constants.ONE);
				// 统计本周
				countBankLastday(selfWeekOne, selfWeekDay, null, map, economicsBankDto, date, Constants.MINUS_ONE,
						Constants.TWO);
				// 统计上周
				countBankLastday(lastWeekOne, lastWeekDay, null, map, economicsBankDto, date, Constants.ZERO,
						Constants.THREE);
				// 统计本月
				countBankLastday(lastMonth, selfMonth, null, map, economicsBankDto, date, month, Constants.FOUR);
				// // 补齐以前数据
				countBeforeMonth(month, map, date, economicsBankDto, Constants.FOUR);
			}
		}
	}

	/**
	 * 补充以前月份数据
	 */
	private void countBeforeMonth(int months, Map<String, Object> map, Date date, B2bEconomicsBankDto economicsBankDto,
			int times) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		// 查看本年度之前月份是否有统计
		for (int i = 0; i < months - 1; i++) {
			Query query = new Query();
			query.addCriteria(Criteria.where("times").is(Constants.FOUR).and("month").is(i + 1).and("year")
					.is(sdf.format(date)).and("bankPk").is(economicsBankDto.getPk()));
			List<EconomicsCustomerBankReportForms> list = mongoTemplate.find(query,
					EconomicsCustomerBankReportForms.class);
			// 如果上个月没有统计，就补上
			if (list == null || list.size() == 0) {
				// 补齐以前月份，月份开始时间
				String startMonth = CommonUtil.specifyMonth((i + 1) - (months + 1), 21, date);
				// 补齐以前月份，月份结束时间
				String endMonth = CommonUtil.specifyMonth((i + 1) - months, 20, date);
				map.put("startTime", startMonth);
				map.put("endTime", endMonth);
				EconomicsCustomerBankReportForms reportForms = new EconomicsCustomerBankReportForms();

				List<EconCreditBankEntry> goodsTypeList = b2bCreditExtDao.searchCreditByBankLastDay(map);
				// 统计每个商品类型每种来源的数据
				if (goodsTypeList != null && goodsTypeList.size() > 0) {
					List<EconCreditBankEntry> tempList = new ArrayList<EconCreditBankEntry>();
					// 规范goodsType类型
					for (EconCreditBankEntry entry : goodsTypeList) {
						if (entry.getTypeGroup() != null && entry.getTypeGroup().contains("1")
								&& entry.getTypeGroup().contains("2")) {
							entry.setTypeGroup("1,2");
						}
						if (entry.getTypeGroup() != null && !entry.getTypeGroup().contains("1")) {
							entry.setTypeGroup("2");
						}
						if (entry.getTypeGroup() != null && !entry.getTypeGroup().contains("2")) {
							entry.setTypeGroup("1");
						}
						tempList.add(entry);
					}
					setEconomicsBankCount(reportForms, tempList, startMonth);
				}
				// 合计每种产品类型
				setGoodsTypeSourceCount(reportForms);
				SimpleDateFormat sd = new SimpleDateFormat("yyyy");
				reportForms.setId(KeyUtils.getUUID());
				reportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
				reportForms.setYear(sd.format(date));
				reportForms.setMonth((i + 1));
				reportForms.setTimes(Constants.FOUR);
				reportForms.setBankPk(economicsBankDto.getPk());
				reportForms.setBankName(economicsBankDto.getBankName());
				// 查看统计数据是否存在，若存在就更新
				findBankMongo(date, (i + 1), reportForms, sd);
				mongoTemplate.save(reportForms);
				// 设置累计、合计
				setBankCreditAddUp(date, (i + 1), reportForms, sd);

			}
		}
		// 把以后月份设置为零
		if (months < 12) {
			for (int n = months + 1; n < 13; n++) {
				setObjVlue(n, sdf.format(date), economicsBankDto);
			}
		}
	}

	/**
	 * 默认先设置为零
	 */
	public void setObjVlue(int month, String date, B2bEconomicsBankDto economicsBankDto) {
		EconomicsCustomerBankReportForms nrf = new EconomicsCustomerBankReportForms();
		nrf.setId(KeyUtils.getUUID());
		nrf.setMonth(month);
		nrf.setYear(date);
		nrf.setTimes(Constants.FOUR);
		nrf.setBankName(economicsBankDto.getBankName());
		nrf.setBankPk(economicsBankDto.getPk());

		Query query = new Query();
		query.addCriteria(
				Criteria.where("month").is(month).and("year").is(date).and("bankPk").is(economicsBankDto.getPk()));
		List<EconomicsCustomerBankReportForms> list = mongoTemplate.find(query, EconomicsCustomerBankReportForms.class);
		if (list != null && list.size() > 0) {
		} else {
			mongoTemplate.save(nrf);
		}
	}

	/**
	 * 统计昨日，本周，上周，本月数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @param yesterDay
	 * @param map
	 * @param economicsBankDto
	 * @param date
	 * @param month
	 * @param times
	 */
	private void countBankLastday(String startTime, String endTime, String yesterDay, Map<String, Object> map,
			B2bEconomicsBankDto economicsBankDto, Date date, int month, int times) {

		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("creditStartTime", yesterDay);
		EconomicsCustomerBankReportForms reportForms = new EconomicsCustomerBankReportForms();
		reportForms.setBankPk(economicsBankDto.getPk());
		reportForms.setBankName(economicsBankDto.getBankName());
		List<EconCreditBankEntry> goodsTypeList = b2bCreditExtDao.searchCreditByBankLastDay(map);
		// 统计每个商品类型每种来源的数据
		if (goodsTypeList != null && goodsTypeList.size() > 0)
		{
			List<EconCreditBankEntry> tempList = new ArrayList<>();
			// 规范goodsType类型
			for (EconCreditBankEntry entry : goodsTypeList) {
				if (entry.getTypeGroup() != null && entry.getTypeGroup().contains("1")
						&& entry.getTypeGroup().contains("2")) {
					entry.setTypeGroup("1,2");
				}
				if (entry.getTypeGroup() != null && !entry.getTypeGroup().contains("1")) {
					entry.setTypeGroup("2");
				}
				if (entry.getTypeGroup() != null && !entry.getTypeGroup().contains("2")) {
					entry.setTypeGroup("1");
				}
				tempList.add(entry);
			}
			logger.error("**********************setEconomicsBankCount_START:" + JsonUtils.convertToString(tempList));
			setEconomicsBankCount(reportForms, tempList, startTime);
		}
		// 合计每种产品类型
		setGoodsTypeSourceCount(reportForms);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy");
		reportForms.setId(KeyUtils.getUUID());
		reportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		reportForms.setYear(sd.format(date));
		reportForms.setMonth(month);
		reportForms.setTimes(times);
		// 查看统计数据是否存在，若存在就更新
		findBankMongo(date, month, reportForms, sd);
		mongoTemplate.save(reportForms);
		// 设置累计、合计
		if (times == Constants.FOUR) {
			setBankCreditAddUp(date, month, reportForms, sd);
		}
	}

	private void setBankCreditAddUp(Date date, int month, EconomicsCustomerBankReportForms reportForms,
			SimpleDateFormat sd) {
		Query query = new Query();
		query.addCriteria(Criteria.where("times").is(Constants.FOUR).and("month").gte(1).lte(month).and("year")
				.is(sd.format(date)).and("bankPk").is(reportForms.getBankPk()));
		List<EconomicsCustomerBankReportForms> monthCountList = mongoTemplate.find(query,
				EconomicsCustomerBankReportForms.class);

		// 化纤白条
		int blankAddUpSH = 0;// 盛虹累计
		int blankAddUpXFM = 0;// 新凤鸣累计
		int blankAddUpYX = 0;// 营销累计
		int blankAddUpPT = 0;// 平台累计
		int blankAddUpOther = 0;// 其他累计

		// 化纤贷
		int loanAddUpSH = 0;// 盛虹累计
		int loanAddUpXFM = 0;// 新凤鸣累计
		int loanAddUpYX = 0;// 营销累计
		int loanAddUpPT = 0;// 平台累计
		int loanAddUpOther = 0;// 其他累计

		// 化纤白条+化纤贷
		int blaLoAddUpSH = 0;// 盛虹累计
		int blaLoAddUpXFM = 0;// 新凤鸣累计
		int blaLoAddUpYX = 0;// 营销累计
		int blaLoAddUpPT = 0;// 平台累计
		int blaLoAddUpOther = 0;// 其他累计
		if (monthCountList != null && monthCountList.size() > 0) {
			// 计算累计
			for (EconomicsCustomerBankReportForms econCustBankRF : monthCountList) {

				if (econCustBankRF.getBlankSH() != null) {
					blankAddUpSH += econCustBankRF.getBlankSH();
				}

				if (econCustBankRF.getBlankXFM() != null) {
					blankAddUpXFM += econCustBankRF.getBlankXFM();
				}
				if (econCustBankRF.getBlankAddUpYX() != null) {
					blankAddUpYX += econCustBankRF.getBlankYX();
				}
				if (econCustBankRF.getBlankPT() != null) {
					blankAddUpPT += econCustBankRF.getBlankPT();
				}
				if (econCustBankRF.getBlankAddUpOther() != null) {
					blankAddUpOther += econCustBankRF.getBlankOther();
				}

				if (econCustBankRF.getLoanSH() != null) {
					loanAddUpSH += econCustBankRF.getLoanSH();
				}
				if (econCustBankRF.getLoanXFM() != null) {
					loanAddUpXFM += econCustBankRF.getLoanXFM();
				}
				if (econCustBankRF.getLoanYX() != null) {
					loanAddUpYX += econCustBankRF.getLoanYX();
				}
				if (econCustBankRF.getLoanPT() != null) {
					loanAddUpPT += econCustBankRF.getLoanPT();
				}
				if (econCustBankRF.getLoanOther() != null) {
					loanAddUpOther += econCustBankRF.getLoanOther();
				}
				if (econCustBankRF.getBlaLoSH() != null) {
					blaLoAddUpSH += econCustBankRF.getBlaLoSH();
				}
				if (econCustBankRF.getBlaLoXFM() != null) {
					blaLoAddUpXFM += econCustBankRF.getBlaLoXFM();
				}
				if (econCustBankRF.getBlaLoYX() != null) {
					blaLoAddUpYX += econCustBankRF.getBlaLoYX();
				}
				if (econCustBankRF.getBlaLoPT() != null) {
					blaLoAddUpPT += econCustBankRF.getBlaLoPT();
				}
				if (econCustBankRF.getBlaLoOther() != null) {
					blaLoAddUpOther += econCustBankRF.getBlaLoOther();
				}
			}
		}
		// 每种产品按来源累计
		reportForms.setBlankAddUpSH(blankAddUpSH);
		reportForms.setBlankAddUpXFM(blankAddUpXFM);
		reportForms.setBlankAddUpYX(blankAddUpYX);
		reportForms.setBlankAddUpPT(blankAddUpPT);
		reportForms.setBlankAddUpOther(blankAddUpOther);

		reportForms.setLoanAddUpSH(loanAddUpSH);
		reportForms.setLoanAddUpXFM(loanAddUpXFM);
		reportForms.setLoanAddUpYX(loanAddUpYX);
		reportForms.setLoanAddUpPT(loanAddUpPT);
		reportForms.setLoanAddUpOther(loanAddUpOther);

		reportForms.setBlaLoAddUpSH(blaLoAddUpSH);
		reportForms.setBlaLoAddUpXFM(blaLoAddUpXFM);
		reportForms.setBlaLoAddUpYX(blaLoAddUpYX);
		reportForms.setBlaLoAddUpPT(blaLoAddUpPT);
		reportForms.setBlaLoAddUpOther(blaLoAddUpOther);

		// 累计合计
		reportForms.setBlankAddUpCount(blankAddUpSH + blankAddUpXFM + blankAddUpYX + blankAddUpPT + blankAddUpOther);

		reportForms.setLoanAddUpCount(loanAddUpSH + loanAddUpXFM + loanAddUpYX + loanAddUpPT + loanAddUpOther);

		reportForms.setBlaLoAddUpCount(blaLoAddUpSH + blaLoAddUpXFM + blaLoAddUpYX + blaLoAddUpPT + blaLoAddUpOther);
		// 保存累计，合计
		mongoTemplate.save(reportForms);
	}

	private void setGoodsTypeSourceCount(EconomicsCustomerBankReportForms reportForms) {

		// 五中来源的合计
		int blankSH = reportForms.getBlankSH() == null ? 0 : reportForms.getBlankSH();
		int blankXFM = reportForms.getBlankXFM() == null ? 0 : reportForms.getBlankXFM();
		int blankYX = reportForms.getBlankYX() == null ? 0 : reportForms.getBlankYX();
		int blankPT = reportForms.getBlankPT() == null ? 0 : reportForms.getBlankPT();
		int blankOther = reportForms.getBlankOther() == null ? 0 : reportForms.getBlankOther();

		reportForms.setBlankGoodsTypeCount(blankSH + blankXFM + blankYX + blankPT + blankOther);

		int loanSH = reportForms.getLoanSH() == null ? 0 : reportForms.getLoanSH();
		int loanXFM = reportForms.getLoanXFM() == null ? 0 : reportForms.getLoanXFM();
		int loanYX = reportForms.getLoanYX() == null ? 0 : reportForms.getLoanYX();
		int loanPT = reportForms.getLoanPT() == null ? 0 : reportForms.getLoanPT();
		int loanOther = reportForms.getLoanOther() == null ? 0 : reportForms.getLoanOther();

		reportForms.setLoanGoodsTypeCount(loanSH + loanXFM + loanYX + loanPT + loanOther);

		int blaLoSH = reportForms.getBlaLoSH() == null ? 0 : reportForms.getBlaLoSH();
		int blaLoXFM = reportForms.getBlaLoXFM() == null ? 0 : reportForms.getBlaLoXFM();
		int blaLoYX = reportForms.getBlaLoYX() == null ? 0 : reportForms.getBlaLoYX();
		int blaLoPT = reportForms.getBlaLoPT() == null ? 0 : reportForms.getBlaLoPT();
		int blaLoOther = reportForms.getBlaLoOther() == null ? 0 : reportForms.getBlaLoOther();

		reportForms.setBlaLoGoodsTypeCount(blaLoSH + blaLoXFM + blaLoYX + blaLoPT + blaLoOther);
	}

	private void findBankMongo(Date date, Integer months, EconomicsCustomerBankReportForms reportForms,
			SimpleDateFormat sd) {
		Query query = new Query();
		query.addCriteria(Criteria.where("times").is(reportForms.getTimes()).and("month").is(months).and("year")
				.is(sd.format(date)).and("bankPk").is(reportForms.getBankPk()));
		List<EconomicsCustomerBankReportForms> list = mongoTemplate.find(query, EconomicsCustomerBankReportForms.class);
		if (list != null && list.size() > 0) {
			EconomicsCustomerBankReportForms customerReportForms = list.get(0);
			reportForms.setId(customerReportForms.getId());
		}
	}

	private void setEconomicsBankCount(EconomicsCustomerBankReportForms reportForms,
			List<EconCreditBankEntry> goodsTypeList, String startTime) {

		if (goodsTypeList != null && goodsTypeList.size() > 0) {

			int blankSH = 0;
			int blankXFM = 0;
			int blankYX = 0;
			int blankPT = 0;
			int blankOther = 0;

			int loanSH = 0;
			int loanXFM = 0;
			int loanYX = 0;
			int loanPT = 0;
			int loanOther = 0;

			int blaLoSH = 0;
			int blaLoXFM = 0;
			int blaLoYX = 0;
			int blaLoPT = 0;
			int blaLoOther = 0;

			for (EconCreditBankEntry entry : goodsTypeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("bankPk", reportForms.getBankPk());
				map.put("companyPk", entry.getCompanyPk());
				map.put("creditStartTime", startTime);
				List<B2bEconomicsBankCompanyExtDto> list = b2bEconomicsBankCompanyExtDao.searchListByCreditTime(map);

				// 如果不在同一个月并且和本月申请的产品类型不相同，则本月记，化纤贷+化纤白条和本月申请的化纤产品类型
				// 如果产品如果不在同一个月的产品类型相同，则本统计月不做统计，只统计本月申请的产品类型
				if (list != null && list.size() > 0) {
					boolean isSource = true;
					for (B2bEconomicsBankCompanyExtDto bankCom : list) {
						if (bankCom.getType() != null
								&& String.valueOf(bankCom.getType()).contains(entry.getTypeGroup())) {
							isSource = false;
						}
						// 本月存在相同的产品类型
						if (isSource == false) {
							if (entry.getSource() == 1) {
								blaLoSH++;
							}
							if (entry.getSource() == 2) {
								blaLoPT++;
							}
							if (entry.getSource() == 3) {
								blaLoXFM++;
							}
							if (entry.getSource() == 4) {
								blaLoYX++;
							}
							if (entry.getSource() == 5) {
								blaLoOther++;
							}
						}

					}
				}
				if ("1".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 1) {
					reportForms.setBlankSH(++blankSH);
				}

				if ("1".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 2) {

					reportForms.setBlankPT(++blankPT);
				}
				if ("1".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 3) {

					reportForms.setBlankXFM(++blankXFM);
				}
				if ("1".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 4) {

					reportForms.setBlankYX(++blankYX);
				}
				if ("1".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 5) {
					reportForms.setBlankOther(++blankOther);
				}

				if ("2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 1) {

					reportForms.setLoanSH(++loanSH);
				}
				if ("2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 2) {

					reportForms.setLoanPT(++loanPT);
				}
				if ("2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 3) {

					reportForms.setLoanXFM(++loanXFM);
				}
				if ("2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 4) {

					reportForms.setLoanYX(++loanYX);
				}
				if ("2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 5) {

					reportForms.setLoanPT(++loanOther);
				}
				// 判断授信是否在同一个月，如果在同一个月不同申请产品（化纤贷，化纤白条）则本月记三次：化纤贷，化纤白条，化纤贷+化纤白条
				if ("1,2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 1) {

					reportForms.setBlaLoSH(++blaLoSH);
					reportForms.setLoanSH(++loanSH);
					reportForms.setBlankSH(++blankSH);
				}
				if ("1,2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 2) {

					reportForms.setBlaLoPT(++blaLoPT);
					reportForms.setLoanPT(++loanPT);
					reportForms.setBlankPT(++blankPT);
				}
				if ("1,2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 3) {

					reportForms.setBlaLoXFM(++blaLoXFM);
					reportForms.setLoanXFM(++loanXFM);
					reportForms.setBlankXFM(++blankXFM);
				}
				if ("1,2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 4) {

					reportForms.setBlaLoYX(++blaLoYX);
					reportForms.setLoanYX(++loanYX);
					reportForms.setBlankYX(++blankYX);
				}

				if ("1,2".equals(entry.getTypeGroup()) && entry.getSource() != null && entry.getSource() == 5) {
					reportForms.setBlaLoOther(++blaLoOther);
					reportForms.setLoanOther(++loanOther);
					reportForms.setBlankOther(++blankOther);
				}
			}
			logger.error("**********************setEconomicsBankCount_Obj:" + JsonUtils.convertToString(reportForms));
		}
	}

}
