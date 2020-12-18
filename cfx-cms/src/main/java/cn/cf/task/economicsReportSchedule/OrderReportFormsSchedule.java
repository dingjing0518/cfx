package cn.cf.task.economicsReportSchedule;

import java.text.SimpleDateFormat;
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
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.entity.EconCreditOrderAmountEntry;
import cn.cf.entity.SupplierEconomicsOrderReportForms;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class OrderReportFormsSchedule {
	private final static Logger logger = LoggerFactory.getLogger(OrderReportFormsSchedule.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;

	@Autowired
	private B2bEconomicsBankExtDao b2bEconomicsBankExtDao;

	/**
	 * 供应商金融产品交易统计,每天0:30执行
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void countEconomicsOrder() {

		Date date = new Date();
		logger.error("**********************OrderReportFormsSchedule_START************************"+date);
		// 上月21号
		String lastMonth = CommonUtil.specifyMonth(-1, 21, date);
		// 本月20号
		String selfMonth = CommonUtil.specifyMonth(0, 20, date);

		Calendar calendar = Calendar.getInstance();// 日历对象
		calendar.setTime(date);// 设置当前日期
		int month = calendar.get(Calendar.MONTH) + 1;// 当前月
		// 统计本月数据
		//countSupplierOrder(date, lastMonth, selfMonth, month);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		// 补充统计以前数据到本月数据
		countBeforeDay(date, month);

		//补充2018年数据
		//countBeforeDay(getYearLast(2018), 12);
	}

	//获取某年的最后一天
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}
	/**
	 * 补充统计以前数据
	 * 
	 * @param date
	 * @param month
	 */
	private void countBeforeDay(Date date, int month) {
		//先清除表
		mongoTemplate.dropCollection(SupplierEconomicsOrderReportForms.class);
		for (int i = 0; i < month; i++) {
			// 上月21号
			String lastMonth = CommonUtil.specifyMonth((i+1) - (month + 1), 21, date);
			// 本月20号
			String selfMonth = CommonUtil.specifyMonth((i+1) - month, 20, date);
			// 统计
			countSupplierOrder(date, lastMonth, selfMonth, i+1);
		}
	}

	private void countSupplierOrder(Date date, String startTime, String endTime, int month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		List<B2bEconomicsBankDto> bankCreditList = b2bEconomicsBankExtDao.searchList(map);

		if (bankCreditList != null && bankCreditList.size() > 0) {
			for (B2bEconomicsBankDto economicsBankDto : bankCreditList) {
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				map.put("bankPk", economicsBankDto.getPk());
				// 设置化纤贷统计
				List<EconCreditOrderAmountEntry> loanList = b2bOrderExtDao.countAndAmountCreditOrder(map);
				List<EconCreditOrderAmountEntry> loanContractList = b2bOrderExtDao.countAndAmountCreditContract(map);
				// 判断月份，对应月份设置值
				if (loanList != null && loanList.size() > 0) {
					for (EconCreditOrderAmountEntry loanEntry : loanList) {
						setReportToMongo(date, month, economicsBankDto, loanEntry,Constants.ONE);
					}
				}
				if (loanContractList != null && loanContractList.size() > 0) {
					for (EconCreditOrderAmountEntry loanEntry : loanContractList) {
						setReportToMongo(date, month, economicsBankDto, loanEntry,Constants.TWO);
					}
				}
			}
		}
	}

	private void setReportToMongo(Date date, int month, B2bEconomicsBankDto economicsBankDto, EconCreditOrderAmountEntry loanEntry
	                               ,int type) {
		SupplierEconomicsOrderReportForms reportForms = null;
		// 查询是否存在
		reportForms = findOrderCountMongo(date, loanEntry.getStorePk(),economicsBankDto.getPk());
		if(reportForms == null){
			reportForms = new SupplierEconomicsOrderReportForms();
			reportForms.setId(KeyUtils.getUUID());
			reportForms.setStorePk(loanEntry.getStorePk());
			reportForms.setStoreName(loanEntry.getStoreName());
			reportForms.setBankPk(economicsBankDto.getPk());
			reportForms.setBankName(economicsBankDto.getBankName());
		}
		reportForms.setYear(new SimpleDateFormat("yyyy").format(date));
		reportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		reportForms.setMaxMonth(month);
		//根据来源和月份赋值
		setValueBySourceAndMonth(month, loanEntry, reportForms,type);
		// 计算合计
		countAllData(reportForms);

		mongoTemplate.save(reportForms);
	}

	private void setValueBySourceAndMonth(int month, EconCreditOrderAmountEntry loanEntry,
			SupplierEconomicsOrderReportForms orderReportForms,int type) {
		if (month == 1 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount1(type == 1?loanEntry.getAmounts():orderReportForms.getLoanOrderAmount1()+loanEntry.getAmounts());
			orderReportForms.setLoanOrderCount1(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount1());

		}
		if (month == 1 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount1(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount1());
			orderReportForms.setBlankOrderCount1(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount1());

		}

		if (month == 2 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount2(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount2());
			orderReportForms.setLoanOrderCount2(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount2());

		}
		if (month == 2 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount2(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount2());
			orderReportForms.setBlankOrderCount2(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount2());

		}
		if (month == 3 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount3(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount3());
			orderReportForms.setLoanOrderCount3(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount3());

		}
		if (month == 3 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount3(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount3());
			orderReportForms.setBlankOrderCount3(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount3());

		}
		if (month == 4 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount4(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount4());
			orderReportForms.setLoanOrderCount4(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount4());

		}
		if (month == 4 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount4(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount4());
			orderReportForms.setBlankOrderCount4(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount4());

		}
		if (month == 5 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount5(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount5());
			orderReportForms.setLoanOrderCount5(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount5());

		}
		if (month == 5 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount5(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount5());
			orderReportForms.setBlankOrderCount5(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount5());

		}
		if (month == 6 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount6(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount6());
			orderReportForms.setLoanOrderCount6(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount6());

		}
		if (month == 6 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount6(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount6());
			orderReportForms.setBlankOrderCount6(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount6());

		}
		if (month == 7 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount7(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount7());
			orderReportForms.setLoanOrderCount7(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount7());

		}
		if (month == 7 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount7(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount7());
			orderReportForms.setBlankOrderCount7(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount7());

		}
		if (month == 8 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount8(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount8());
			orderReportForms.setLoanOrderCount8(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount8());

		}
		if (month == 8 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount8(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount8());
			orderReportForms.setBlankOrderCount8(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount8());

		}
		if (month == 9 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount9(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount9());
			orderReportForms.setLoanOrderCount9(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount9());

		}
		if (month == 9 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount9(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount9());
			orderReportForms.setBlankOrderCount9(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount9());

		}
		if (month == 10 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount10(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount10());
			orderReportForms.setLoanOrderCount10(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount10());

		}
		if (month == 10 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount10(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount10());
			orderReportForms.setBlankOrderCount10(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount10());

		}
		if (month == 11 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount11(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount11());
			orderReportForms.setLoanOrderCount11(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount11());

		}
		if (month == 11 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount11(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount11());
			orderReportForms.setBlankOrderCount11(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getBlankOrderCount11());

		}
		if (month == 12 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 2) {
			orderReportForms.setLoanOrderAmount12(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getLoanOrderAmount12());
			orderReportForms.setLoanOrderCount12(type == 1?loanEntry.getCounts():loanEntry.getCounts()+orderReportForms.getLoanOrderCount12());

		}
		if (month == 12 && loanEntry.getGoodsType() != null && loanEntry.getGoodsType() == 1) {
			orderReportForms.setBlankOrderAmount12(type == 1?loanEntry.getAmounts():loanEntry.getAmounts()+orderReportForms.getBlankOrderAmount12());
			orderReportForms.setBlankOrderCount12(type == 1?loanEntry.getCounts():orderReportForms.getBlankOrderCount12()+loanEntry.getCounts());
		}
	}

	/**
	 * 计算合计
	 * 
	 * @param reportForms
	 */
	private void countAllData(SupplierEconomicsOrderReportForms reportForms) {
		int loanCount = 0;
		double loanAmount = 0d;
		int blankCount = 0;
		double blankAmount = 0d;
		// 化纤贷订单数合计
		loanCount += setOrderCount(reportForms.getLoanOrderCount1());
		loanCount += setOrderCount(reportForms.getLoanOrderCount2());
		loanCount += setOrderCount(reportForms.getLoanOrderCount3());
		loanCount += setOrderCount(reportForms.getLoanOrderCount4());
		loanCount += setOrderCount(reportForms.getLoanOrderCount5());
		loanCount += setOrderCount(reportForms.getLoanOrderCount6());
		loanCount += setOrderCount(reportForms.getLoanOrderCount7());
		loanCount += setOrderCount(reportForms.getLoanOrderCount8());
		loanCount += setOrderCount(reportForms.getLoanOrderCount9());
		loanCount += setOrderCount(reportForms.getLoanOrderCount10());
		loanCount += setOrderCount(reportForms.getLoanOrderCount11());
		loanCount += setOrderCount(reportForms.getLoanOrderCount12());
		reportForms.setLoanOrderAllCount(loanCount);
		// 化纤贷订单金额合计
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount1());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount2());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount3());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount4());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount5());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount6());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount7());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount8());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount9());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount10());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount11());
		loanAmount += setOrderAmount(reportForms.getLoanOrderAmount12());
		reportForms.setLoanOrderAllAmount(loanAmount);

		// 化纤白条订单数合计
		blankCount += setOrderCount(reportForms.getBlankOrderCount1());
		blankCount += setOrderCount(reportForms.getBlankOrderCount2());
		blankCount += setOrderCount(reportForms.getBlankOrderCount3());
		blankCount += setOrderCount(reportForms.getBlankOrderCount4());
		blankCount += setOrderCount(reportForms.getBlankOrderCount5());
		blankCount += setOrderCount(reportForms.getBlankOrderCount6());
		blankCount += setOrderCount(reportForms.getBlankOrderCount7());
		blankCount += setOrderCount(reportForms.getBlankOrderCount8());
		blankCount += setOrderCount(reportForms.getBlankOrderCount9());
		blankCount += setOrderCount(reportForms.getBlankOrderCount10());
		blankCount += setOrderCount(reportForms.getBlankOrderCount11());
		blankCount += setOrderCount(reportForms.getBlankOrderCount12());
		reportForms.setBlankOrderAllCount(blankCount);
		// 化纤白条订单金额合计
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount1());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount2());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount3());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount4());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount5());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount6());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount7());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount8());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount9());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount10());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount11());
		blankAmount += setOrderAmount(reportForms.getBlankOrderAmount12());
		reportForms.setBlankOrderAllAmount(blankAmount);
	}


	public Double setOrderAmount(Double amount){
		return amount == null ? 0d : amount;
	}
	public Integer setOrderCount(Integer count){
		return count == null ? 0 : count;
	}

	private SupplierEconomicsOrderReportForms findOrderCountMongo(Date date, String storePk,String bankPk) {
		Query query = new Query();
		query.addCriteria(Criteria.where("storePk").is(storePk).and("year")
				.is(new SimpleDateFormat("yyyy").format(date)).and("bankPk").is(bankPk));
		List<SupplierEconomicsOrderReportForms> list = mongoTemplate.find(query,
				SupplierEconomicsOrderReportForms.class);
		if (list != null && list.size() > 0) {
			SupplierEconomicsOrderReportForms orderReportForms = list.get(0);
			return orderReportForms;
		}else{
			return null;
		}
	}

}
