package cn.cf.task.operationReportFormsSchedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.entity.BussStoreDataReport;
import cn.cf.service.operation.BussStoreDataService;

/**
 * 店铺日交易数定时器
 * 
 * @author xht
 *
 *         2018年10月16日
 */
@Component
@EnableScheduling
public class BussStoreDataReportFormsSchedule {

	@Autowired
	private BussStoreDataService bussStoreDataService;

	@Autowired
	private B2bOrderExtDao b2bOrderDaoExt;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 店铺日交易数 每天0:30处理
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	// @Scheduled(cron = "0 15 10 * * ?")
	public void BussStoreDataReport() {	
		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();// 昨日
		if (isExsit(yesterDay) == 0) {
			List<BussStoreDataReport> list = bussStoreDataService.searchOnedayData(yesterDay);
			mongoTemplate.insertAll(list);
		}
		// 初始化以前数据
		if (countDateBeforeYesterDay(yesterDay) == 0) {
			// 查询支付时间最早的订单的支付时间
			String originalDate = b2bOrderDaoExt.searchOriginalDate();
			List<String> dateList = CommonUtil.getBetweenDate(originalDate, yesterDay);
			for (String date : dateList) {
				List<BussStoreDataReport> beforeList = bussStoreDataService.searchOnedayData(date);
				mongoTemplate.insertAll(beforeList);
			}
		}
	}

	private int isExsit(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		return (int) mongoTemplate.count(query, BussStoreDataReport.class);
	}

	private int countDateBeforeYesterDay(String yesterDay) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(yesterDay));
		return (int) mongoTemplate.count(query, BussStoreDataReport.class);
	}

}
