package cn.cf.service.operation.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.entity.BussEconPurchaserDataReport;
import cn.cf.entity.BussEconPurchaserDataReportExt;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.BussEconPurchaserDataService;
import net.sf.json.JSONArray;
/**
 * 采购商日交易金融数据
 * @author xht
 *
 * 2018年10月16日
 */
@Service
public class BussEconPurchaserDataServiceImpl  implements BussEconPurchaserDataService{

	@Autowired
	private B2bOrderExtDao  b2bOrderExtDao;
	
	@Autowired
	private MongoTemplate  mongoTemplate;
	

	@Override
	public PageModel<BussEconPurchaserDataReport> searchBussEconPurchaserDataList(QueryModel<BussEconPurchaserDataReportExt> qm, ManageAccount account) {
		List<Order> orderList = new ArrayList<Order>();
		PageModel<BussEconPurchaserDataReport> pm = new PageModel<BussEconPurchaserDataReport>();

		Query query = new Query();
		if (qm.getEntity().getPurchaserName() != null && !qm.getEntity().getPurchaserName().equals("")) {
			Pattern pattern = Pattern.compile("^.*" + qm.getEntity().getPurchaserName() + ".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("purchaserName").regex(pattern));
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
		
		int counts = (int) mongoTemplate.count(query, BussEconPurchaserDataReport.class);
		if ("desc".equals(qm.getFirstOrderType())) {
			orderList.add(new Order(Direction.DESC, qm.getFirstOrderName()));
		} else {
			orderList.add(new Order(Direction.ASC, qm.getFirstOrderName()));
		}
		orderList.add(new Order(Direction.DESC, "btWeight"));
		orderList.add(new Order(Direction.DESC, "dWeight"));
		orderList.add(new Order(Direction.DESC, "btAmount"));
		orderList.add(new Order(Direction.DESC, "dAmount"));
		query.with(new Sort(orderList));
		query.skip(qm.getStart()).limit(qm.getLimit());
		String regex = "*****";
		List<BussEconPurchaserDataReport> list = mongoTemplate.find(query, BussEconPurchaserDataReport.class);
		if (list.size()>0) {
			for (BussEconPurchaserDataReport buss : list) {
				buss.setBtWeight(buss.getBtWeight().setScale(4, BigDecimal.ROUND_DOWN));
				buss.setdWeight(buss.getdWeight().setScale(4, BigDecimal.ROUND_DOWN));
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_REPORTFORMS_TRANS_PURCHECON_COL_PURCHNAME)){
					buss.setPurchaserName(CommonUtil.setColRegexParams(regex,buss.getPurchaserName()));
				}
			}
		}
		
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}


//	@SuppressWarnings("unchecked")
//	@Override
//	public List<BussEconPurchaserDataReport> exportBussEconPurchaserData(String data,BussEconPurchaserDataReportExt buss,ManageAccount account) {
//		List<BussEconPurchaserDataReport> list = new ArrayList<BussEconPurchaserDataReport>();
//		JSONArray json = JSONArray.fromObject(data);
//		if (json.size()>0) {
//			list = (List<BussEconPurchaserDataReport>) JSONArray.toCollection(json,BussEconPurchaserDataReport.class);
//		}else{
//			List<Order> orderList = new ArrayList<Order>();
//			Query query = new Query();
//			if (buss.getPurchaserName() != null && !buss.getPurchaserName().equals("")) {
//				Pattern pattern = Pattern.compile("^.*" + buss.getPurchaserName() + ".*$", Pattern.CASE_INSENSITIVE);
//				query.addCriteria(Criteria.where("purchaserName").regex(pattern));
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
//			orderList.add(new Order(Direction.DESC, "btWeight"));
//			orderList.add(new Order(Direction.DESC, "dWeight"));
//			orderList.add(new Order(Direction.DESC, "btAmount"));
//			orderList.add(new Order(Direction.DESC, "dAmount"));
//			query.with(new Sort(orderList));
//			list =	mongoTemplate.find(query, BussEconPurchaserDataReport.class);
//		}
//		String regex = "*****";
//		if (list.size()>0) {
//			for (BussEconPurchaserDataReport report : list) {
//				report.setBtWeight(report.getBtWeight().setScale(4, BigDecimal.ROUND_DOWN));
//				report.setdWeight(report.getdWeight().setScale(4, BigDecimal.ROUND_DOWN));
//				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_REPORTFORMS_TRANS_PURCHECON_COL_PURCHNAME)){
//					report.setPurchaserName(CommonUtil.setColRegexParams(regex,report.getPurchaserName()));
//				}
//			}
//		}
//		return  list;
//	}
}

