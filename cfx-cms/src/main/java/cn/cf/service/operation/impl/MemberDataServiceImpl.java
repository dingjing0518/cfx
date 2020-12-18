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
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bEconomicsCreditcustomerExDao;
import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.entity.MemberDataReport;
import cn.cf.entity.MemberDataReportExt;
import cn.cf.service.operation.MemberDataService;
import net.sf.json.JSONArray;

@Service
public class MemberDataServiceImpl implements MemberDataService {
	
	@Autowired 
	private B2bCompanyExtDao  B2bCompanyExtDao ;
	
	@Autowired
	private B2bEconomicsCreditcustomerExDao economicsCreditcustomerExDao;
	
	@Autowired
	private  MongoTemplate  mongoTemplate;
	
	@Override
	public MemberDataReport searchOnedayData(String date) {
		
		return B2bCompanyExtDao.searchOnedayData(date);
	}

	@Override
	public PageModel<MemberDataReport> searchMemberDataList(QueryModel<MemberDataReportExt> qm) {
		PageModel<MemberDataReport> pm = new PageModel<MemberDataReport>();
		Query query = new Query();
		if (qm.getEntity().getStartTime() != null && !qm.getEntity().getStartTime().equals("")) {
			if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
				query.addCriteria(Criteria.where("date").gte(qm.getEntity().getStartTime()).lte(qm.getEntity().getEndTime()));
			}else{
				query.addCriteria(Criteria.where("date").gte(qm.getEntity().getStartTime()));
			}
		}else if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
			query.addCriteria(Criteria.where("date").lte(qm.getEntity().getEndTime()));
		}

		int counts = (int) mongoTemplate.count(query, MemberDataReport.class);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());
		pm.setTotalCount(counts);
		pm.setDataList(mongoTemplate.find(query, MemberDataReport.class));
		return pm;
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<MemberDataReport> exportMemberData(String jsonData,MemberDataReportExt reportForm) {
//		 List<MemberDataReport>  list = new ArrayList<MemberDataReport>();
//		 JSONArray json = JSONArray.fromObject(jsonData);
//		if(json.size()>0){
//			list=	(List<MemberDataReport>) JSONArray.toCollection(json,MemberDataReport.class);
//		}else{
//			Query query = new Query();
//			if (reportForm.getStartTime() != null && !reportForm.getStartTime().equals("")) {
//				if (reportForm.getEndTime() != null && !reportForm.getEndTime().equals("")) {
//					query.addCriteria(Criteria.where("date").gte(reportForm.getStartTime()).lte(reportForm.getEndTime()));
//				}else{
//					query.addCriteria(Criteria.where("date").gte(reportForm.getStartTime()));
//				}
//			}else if (reportForm.getEndTime() != null && !reportForm.getEndTime().equals("")) {
//				query.addCriteria(Criteria.where("date").lte(reportForm.getEndTime()));
//			}
//				query.with(new Sort(Direction.DESC, "date"));
//				list = mongoTemplate.find(query, MemberDataReport.class);
//		}
//		return list ;
//	}

	
	
	@Override
	public List<B2bEconomicsCreditcustomerDtoEx> searchOneDayApplyCompany(String day) {
		
		return economicsCreditcustomerExDao.searchOneDayApplyCompany(day);
	}

	@Override
	public List<B2bEconomicsCreditcustomerDtoEx> searchAccDayApplyCompany(String day) {
		return economicsCreditcustomerExDao.searchAccDayApplyCompany(day);
	}

}
