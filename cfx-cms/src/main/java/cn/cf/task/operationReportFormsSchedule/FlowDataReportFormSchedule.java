package cn.cf.task.operationReportFormsSchedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.entity.FlowCountTempleEntity;
import cn.cf.entity.FlowDataReport;
import cn.cf.entity.PVHandlerInfo;
import cn.cf.entity.UVHandlerInfo;

/**
 * 流量数据定时器
 * 
 * @author xht
 *
 *         2018年10月16日
 */
@Component
@EnableScheduling
public class FlowDataReportFormSchedule {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 流量数据 每天0:30处理
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void FlowDataReport() {
		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();// 昨日
		if (isExsit(yesterDay) == 0) {
			insertOneDayData(yesterDay);
		}
		// 初始化以前数据
		if (countDateBeforeYesterDay(yesterDay) == 0) {
			Query query = new Query();
			query.addCriteria(Criteria.where("type").ne("").ne(null));
			query.with(new Sort(new Order(Direction.ASC, "date")));
			PVHandlerInfo pvOrigin = mongoTemplate.findOne(query, PVHandlerInfo.class);
			UVHandlerInfo uvOrigin = mongoTemplate.findOne(query, UVHandlerInfo.class);
			if (uvOrigin != null && pvOrigin != null) {
				String dateOrigin = CommonUtil.compare_date(pvOrigin.getDate(), uvOrigin.getDate());
				if (!dateOrigin.equals("")) {
					List<String> dateList = CommonUtil.getBetweenDate(dateOrigin, yesterDay);
					for (String date : dateList) {
						insertOneDayData(date);
					}
				}
			}
		}
	}

	private int isExsit(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		return (int) mongoTemplate.count(query, FlowDataReport.class);
	}

	/**
	 * 插入指定日期的数据
	 * 
	 * @param day
	 */
	private void insertOneDayData(String day) {
		FlowDataReport report = new FlowDataReport();
		report.setDate(day);
		report = getOneDayPVData(day, report);// 查询PV统计
		report = getOneDayIPData(day, report);// 查询IP统计
		mongoTemplate.insert(report);
	}

	private FlowDataReport getOneDayIPData(String day, FlowDataReport report) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		query.addCriteria(Criteria.where("type").is("1"));
		Integer pcIpCount = (int) mongoTemplate.count(query, UVHandlerInfo.class);
		report.setPcWithUV(pcIpCount);
		report.setPcWithIP(pcIpCount);
		query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		query.addCriteria(Criteria.where("type").is("2"));
		Integer wapIpCount = (int) mongoTemplate.count(query, UVHandlerInfo.class);
		report.setWapWithUV(wapIpCount);
		report.setWapWithIP(wapIpCount);
		return report;
	}

	private FlowDataReport getOneDayPVData(String day, FlowDataReport report) {
		TypedAggregation<PVHandlerInfo> aggregation = Aggregation.newAggregation(PVHandlerInfo.class,
				Aggregation.match(Criteria.where("date").is(day)),
				Aggregation.group("type").first("type").as("type").sum("count").as("total"));
		AggregationResults<FlowCountTempleEntity> ar = mongoTemplate.aggregate(aggregation,
				FlowCountTempleEntity.class);
		List<FlowCountTempleEntity> list = ar.getMappedResults();
		if (list.size() > 0) {
			for (FlowCountTempleEntity flowCountTempleEntity : list) {
				if (flowCountTempleEntity.getType()!=null&&!flowCountTempleEntity.getType().equals("")) {
					if (flowCountTempleEntity.getType().equals("1")) {
						report.setPcWithPV(Integer.parseInt(flowCountTempleEntity.getTotal()));
					} else if (flowCountTempleEntity.getType().equals("2")) {
						report.setWapWithPV(Integer.parseInt(flowCountTempleEntity.getTotal()));
					}
				}
				
			}
		}
		return report;
	}

	private int countDateBeforeYesterDay(String yesterDay) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(yesterDay));
		return (int) mongoTemplate.count(query, FlowDataReport.class);
	}

}
