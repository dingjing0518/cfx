package cn.cf.task.marketing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bGoodsExtDao;
import cn.cf.entity.GoodsUpdateReport;
/**
 * 营销报表：产品更新
 * @author xht
 *
 * 2018年11月13日
 */
@Component
@EnableScheduling
public class ProductUpdateFormsSchedule {

	private final static Logger logger = LoggerFactory.getLogger(ProductUpdateFormsSchedule.class);
	
	@Autowired
	private B2bGoodsExtDao b2bGoodsExtDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void productUpdate() {
		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();
		if (isExsit(yesterDay) == 0) {//昨日数据是否统计过
			List<GoodsUpdateReport> list = b2bGoodsExtDao.getProductUpdateByOneday(yesterDay);
			if (list.size()>0) {
				mongoTemplate.insertAll(list);
			}
		}
		
		// 初始化以前数据
		if (countDataBeforeYesterDay(yesterDay) == 0) {
			String originalDate = b2bGoodsExtDao.searchOriginalUpdateDate();
			List<String> dateList = CommonUtil.getBetweenDate(originalDate, yesterDay);
			for (String date : dateList) {
				List<GoodsUpdateReport> list = b2bGoodsExtDao.getProductUpdateByOneday(date);
				if (list.size()>0) {
					mongoTemplate.insertAll(list);
				}
			}
		}
	}
		
		private int countDataBeforeYesterDay(String date) {
			Query query = new Query();
			query.addCriteria(Criteria.where("date").lt(date));
			return (int) mongoTemplate.count(query, GoodsUpdateReport.class);
	}

		private int isExsit(String date) {
			Query query = new Query();
			query.addCriteria(Criteria.where("date").is(date));
			return (int) mongoTemplate.count(query, GoodsUpdateReport.class);
		}


}

	
	