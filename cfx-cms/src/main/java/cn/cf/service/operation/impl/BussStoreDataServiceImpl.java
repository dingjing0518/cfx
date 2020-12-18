package cn.cf.service.operation.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.model.ManageAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.entity.BussStoreDataReport;
import cn.cf.entity.BussStoreDataReportExt;
import cn.cf.service.operation.BussStoreDataService;
import net.sf.json.JSONArray;

@Service
public class BussStoreDataServiceImpl implements BussStoreDataService {

	@Autowired
	private B2bOrderExtDao b2bOrderDaoExt;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<BussStoreDataReport> searchOnedayData(String date) {
		return b2bOrderDaoExt.searchOneDayBussStoreData(date);
	}

	@Override
	public PageModel<BussStoreDataReport> searchBussStoreDataList(QueryModel<BussStoreDataReportExt> qm, ManageAccount account) {
		List<Order> orderList = new ArrayList<Order>();
		PageModel<BussStoreDataReport> pm = new PageModel<BussStoreDataReport>();

		Query query = new Query();
		if (qm.getEntity().getStoreName() != null && !qm.getEntity().getStoreName().equals("")) {
			Pattern pattern = Pattern.compile("^.*" + qm.getEntity().getStoreName() + ".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("storeName").regex(pattern));
		}

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
		
		int counts = (int) mongoTemplate.count(query, BussStoreDataReport.class);
		if ("desc".equals(qm.getFirstOrderType())) {
			orderList.add(new Order(Direction.DESC, qm.getFirstOrderName()));
		} else {
			orderList.add(new Order(Direction.ASC, qm.getFirstOrderName()));
		}
		orderList.add(new Order(Direction.DESC, "weight"));
		orderList.add(new Order(Direction.DESC, "amount"));
		query.with(new Sort(orderList));
		query.skip(qm.getStart()).limit(qm.getLimit());
		String regex = "*****";
		List<BussStoreDataReport> list = mongoTemplate.find(query, BussStoreDataReport.class);
		if (list.size()>0) {
			for (BussStoreDataReport buss : list) {
				buss.setAccumWeight(buss.getAccumWeight().setScale(4, BigDecimal.ROUND_DOWN));
				buss.setWeight(buss.getWeight().setScale(4, BigDecimal.ROUND_DOWN));
				if (account != null){
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_REPORTFORMS_TRANSSTORENUM_COL_STORENAME)){
							buss.setStoreName(CommonUtil.setColRegexParams(regex,buss.getStoreName()));
					}
				}
			}
		}
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm; 
	}


//	@SuppressWarnings("unchecked")
//	@Override
//	public List<BussStoreDataReport> exportBussStoreData(String data,BussStoreDataReportExt buss,ManageAccount account) {
//		List<BussStoreDataReport> list = new ArrayList<BussStoreDataReport>();
//		List<Order> orderList = new ArrayList<Order>();
//		JSONArray json = JSONArray.fromObject(data);
//		if (json.size()>0) {
//			list = 	(List<BussStoreDataReport>) JSONArray.toCollection(json,BussStoreDataReport.class);
//		}else{
//			Query query = new Query();
//			if (buss.getStoreName() != null && !buss.getStoreName().equals("")) {
//				Pattern pattern = Pattern.compile("^.*" + buss.getStoreName() + ".*$", Pattern.CASE_INSENSITIVE);
//				query.addCriteria(Criteria.where("storeName").regex(pattern));
//			}
//			if (buss.getStartTime() != null && !buss.getStartTime().equals("")) {
//				if (buss.getEndTime() != null && !buss.getEndTime().equals("")) {
//					query.addCriteria(
//							Criteria.where("date").gte(buss.getStartTime()).lte(buss.getEndTime()));
//				} else {
//					query.addCriteria(Criteria.where("date").gte(buss.getStartTime()));
//				}
//			} else if (buss.getEndTime() != null && !buss.getEndTime().equals("")) {
//				query.addCriteria(Criteria.where("date").lte(buss.getEndTime()));
//			}
//			orderList.add(new Order(Direction.DESC, "date"));
//			orderList.add(new Order(Direction.DESC, "weight"));
//			orderList.add(new Order(Direction.DESC, "amount"));
//			query.with(new Sort(orderList));
//			list =mongoTemplate.find(query, BussStoreDataReport.class);
//		}
//		String regex = "*****";
//		if (list.size()>0) {
//			for (BussStoreDataReport report : list) {
//				if(buss != null && buss.getAccumWeight() != null){
//					report.setAccumWeight(buss.getAccumWeight().setScale(4, BigDecimal.ROUND_DOWN));
//				}
//				if(buss != null && buss.getWeight() != null) {
//					report.setWeight(buss.getWeight().setScale(4, BigDecimal.ROUND_DOWN));
//				}
//				if (account != null){
//					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_REPORTFORMS_TRANSSTORENUM_COL_STORENAME)){
//						report.setStoreName(CommonUtil.setColRegexParams(regex,report.getStoreName()));
//					}
//				}
//			}
//		}
//		
//		return  list; 
//	}
}
