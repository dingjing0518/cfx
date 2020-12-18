package cn.cf;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.entity.PVHandlerInfo;
import cn.cf.util.DateUtil;

@Service(value = "pvhandler")
public class PVHandler extends Handler {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * PV统计
	 */
	// mongo里面pv表结构:pageUrl,count,date
	// 从mongo里面根据pageUrl和当前日期(yyyy-MM-dd)查找，如果查到，增加count;
	// 如果没有查找到，插入pageUrl,当前日期(yyyy-MM-dd)和count为1
	@Override
	public void handleRequest(HttpServletRequest request) {

		buildPV(request);
		successor.handleRequest(request);
	}

	private synchronized void buildPV(HttpServletRequest request) {
		String pageUrl = request.getParameter("url");
		String type = request.getParameter("type");
		String today = DateUtil.formatYearMonthDay(new Date());
		// 查询是否当前日期该页面是否浏览
		Criteria pv = new Criteria();
		pv.andOperator(Criteria.where("pageUrl").is(pageUrl), Criteria.where("date").is(today),Criteria.where("type").is(type));
		Query query = new Query(pv);
		PVHandlerInfo pvHandlerInfo = mongoTemplate.findOne(query, PVHandlerInfo.class);
		if (null != pvHandlerInfo) {
			updatePV(query, pvHandlerInfo);
		} else {
			// 插入当前页面浏览记录
			addPV(pageUrl, today,type, pvHandlerInfo);
		}
	}

	private void addPV(String pageUrl, String today,String type, PVHandlerInfo pvHandlerInfo) {
		PVHandlerInfo model = new PVHandlerInfo();
		model.setDate(today);
		model.setPageUrl(pageUrl);
		model.setCount(1);
		model.setType(type);
		mongoTemplate.insert(model);
	}

	private void updatePV(Query query, PVHandlerInfo pvHandlerInfo) {
		Update update = Update.update("count", pvHandlerInfo.getCount() + 1);
		mongoTemplate.updateFirst(query, update, PVHandlerInfo.class);
	}

}