package cn.cf.service.operation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.FlowDataReport;
import cn.cf.entity.FlowDataReportExt;
import cn.cf.service.operation.FlowDataReportService;
import net.sf.json.JSONArray;

@Service
public class FlowDataReportServiceImpl implements FlowDataReportService {
	
	@Autowired
	private MongoTemplate  mongoTemplate;

	@Override
	public PageModel<FlowDataReport> searchflowDataList(QueryModel<FlowDataReportExt> qm) {

		PageModel<FlowDataReport> pm = new PageModel<FlowDataReport>();

		Query query = new Query();
		if (qm.getEntity().getStartTime() != null && !qm.getEntity().getStartTime().equals("")) {
			if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
				query.addCriteria(
						Criteria.where("date").gte(qm.getEntity().getStartTime()).lte(qm.getEntity().getEndTime()));
			} else {
				query.addCriteria(Criteria.where("date").gte(qm.getEntity().getStartTime()));
			}
		} else if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
			query.addCriteria(Criteria.where("date").lte(qm.getEntity().getEndTime()));
		}

		int counts = (int) mongoTemplate.count(query, FlowDataReport.class);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());
		pm.setTotalCount(counts);
		pm.setDataList(mongoTemplate.find(query, FlowDataReport.class));
		return pm;

	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<FlowDataReport> exportFlowData(String jsonData,FlowDataReportExt flow) {
//		List<FlowDataReport>  list  = new ArrayList<FlowDataReport>() ;
//		JSONArray json = JSONArray.fromObject(jsonData);
//		if (json.size()>0) {
//			list =	(List<FlowDataReport>) JSONArray.toCollection(json,FlowDataReport.class);
//		}else{
//			Query query = new Query();
//			if (flow.getStartTime() != null && !flow.getStartTime().equals("")) {
//				if (flow.getEndTime() != null && !flow.getEndTime().equals("")) {
//					query.addCriteria(
//							Criteria.where("date").gte(flow.getStartTime()).lte(flow.getEndTime()));
//				} else {
//					query.addCriteria(Criteria.where("date").gte(flow.getStartTime()));
//				}
//			} else if (flow.getEndTime() != null && !flow.getEndTime().equals("")) {
//				query.addCriteria(Criteria.where("date").lte(flow.getEndTime()));
//			}
//			query.with(new Sort(Direction.DESC, "date"));
//			list =	mongoTemplate.find(query, FlowDataReport.class);
//		}
//		return   list;
//	}
}
