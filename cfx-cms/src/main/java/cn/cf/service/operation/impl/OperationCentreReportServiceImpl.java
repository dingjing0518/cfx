package cn.cf.service.operation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.entity.IndustryProductSpecTopRF;
import cn.cf.entity.IndustryPurchaserTopRF;
import cn.cf.entity.IndustryStoreTopRF;
import cn.cf.entity.TransactionDataReportForm;
import cn.cf.entity.TransactionDataStoreReportForm;
import cn.cf.service.operation.OperationCentreReportService;

@Service
public class OperationCentreReportServiceImpl implements OperationCentreReportService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public PageModel<TransactionDataReportForm> searchTransactionDataList(QueryModel<TransactionDataReportForm> qm) {

		PageModel<TransactionDataReportForm> pm = new PageModel<TransactionDataReportForm>();
		Criteria criteria = new Criteria();
		criteria = Criteria.where("_id").nin("");

		String countTimeStart = qm.getEntity().getCountTimeStart();
		String countTimeEnd = qm.getEntity().getCountTimeEnd();
		if (null != countTimeStart && !"".equals(countTimeStart) && null != countTimeEnd && !"".equals(countTimeEnd)) {
			criteria.and("countTime").gte(countTimeStart).lte(countTimeEnd);
		}
		if (null != countTimeStart && !"".equals(countTimeStart) && (null == countTimeEnd || "".equals(countTimeEnd))) {
			criteria.and("countTime").gte(countTimeStart);
		}
		if (null != countTimeEnd && !"".equals(countTimeEnd) && (null == countTimeStart || "".equals(countTimeStart))) {
			criteria.and("countTime").lte(countTimeEnd);
		}
		Query query = new Query();
		query.addCriteria(criteria);
		long count = mongoTemplate.count(query, TransactionDataReportForm.class);
		query.with(new Sort(new Order(Direction.DESC, qm.getFirstOrderName())));
		query.skip(qm.getStart());
		query.limit(qm.getLimit());
		List<TransactionDataReportForm> list = mongoTemplate.find(query, TransactionDataReportForm.class);
		pm.setTotalCount((int) count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<TransactionDataStoreReportForm> searchTransDataStoreList(
			QueryModel<TransactionDataStoreReportForm> qm, ManageAccount account) {
		PageModel<TransactionDataStoreReportForm> pm = new PageModel<TransactionDataStoreReportForm>();
		Criteria criteria = new Criteria();
		criteria = Criteria.where("_id").nin("");
		String countTimeStart = qm.getEntity().getCountTimeStart();
		String countTimeEnd = qm.getEntity().getCountTimeEnd();
		if (null != countTimeStart && !"".equals(countTimeStart) && null != countTimeEnd && !"".equals(countTimeEnd)) {
			criteria.and("countTime").gte(countTimeStart).lte(countTimeEnd);
		}
		if (null != countTimeStart && !"".equals(countTimeStart) && (null == countTimeEnd || "".equals(countTimeEnd))) {
			criteria.and("countTime").gte(countTimeStart);
		}
		if (null != countTimeEnd && !"".equals(countTimeEnd) && (null == countTimeStart || "".equals(countTimeStart))) {
			criteria.and("countTime").lte(countTimeEnd);
		}
		if (null != qm.getEntity().getStoreName() && !"".equals(qm.getEntity().getStoreName())) {
			criteria.and("storeName").regex(qm.getEntity().getStoreName());
		}

		Query query = new Query();
		query.addCriteria(criteria);
		long count = mongoTemplate.count(query, TransactionDataStoreReportForm.class);
		query.with(new Sort(new Order(Direction.DESC, qm.getFirstOrderName())));
		query.skip(qm.getStart());
		query.limit(qm.getLimit());
		String regex = "*****";
		List<TransactionDataStoreReportForm> list = mongoTemplate.find(query, TransactionDataStoreReportForm.class);
		if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_REPORTFORMS_TRANSECON_COL_STORENAME)){
			if(list != null && list.size() > 0){
				for (TransactionDataStoreReportForm report:list){
					report.setStoreName(CommonUtil.setColRegexParams(regex,report.getStoreName()));
				}
			}
		}
		pm.setTotalCount((int) count);
		pm.setDataList(list);
		return pm;
	}


//	@Override
//	public List<TransactionDataReportForm> exportTransactionDataList(Map<String, Object> map) {
//		Criteria criteria = new Criteria();
//		criteria = Criteria.where("insertTime").nin("");
//
//		String countTimeStart = (String) map.get("countTimeStart");
//		String countTimeEnd = (String) map.get("countTimeEnd");
//		String ids = (String) map.get("ids");
//		if (null != countTimeStart && !"".equals(countTimeStart) && null != countTimeEnd && !"".equals(countTimeEnd)) {
//			criteria.and("countTime").gte(countTimeStart).lte(countTimeEnd);
//		}
//		if (null != countTimeStart && !"".equals(countTimeStart) && (null == countTimeEnd || "".equals(countTimeEnd))) {
//			criteria.and("countTime").gte(countTimeStart);
//		}
//		if (null != countTimeEnd && !"".equals(countTimeEnd) && (null == countTimeStart || "".equals(countTimeStart))) {
//			criteria.and("countTime").lte(countTimeEnd);
//		}
//		
//		if (null != ids && !"".equals(ids)) {
//			List<String> idsList = new ArrayList<>();
//			if (ids.contains(",")) {
//				String[] idarr = ids.split(",");
//				for (int i = 0; i < idarr.length; i++) {
//					idsList.add(idarr[i]);
//				}
//			} else {
//				idsList.add(ids);
//			}
//			criteria.and("_id").in(idsList);
//		}
//		Query query = new Query();
//		query.addCriteria(criteria);
//		query.with(new Sort(new Order(Direction.DESC, "countTime")));
//		List<TransactionDataReportForm> list = mongoTemplate.find(query, TransactionDataReportForm.class);
//		return list;
//	}

//	@Override
//	public List<TransactionDataStoreReportForm> exportTransDataStoreList(Map<String, Object> map,ManageAccount account) {
//		Criteria criteria = new Criteria();
//		criteria = Criteria.where("insertTime").nin("");
//		String countTimeStart = (String) map.get("countTimeStart");
//		String countTimeEnd = (String) map.get("countTimeEnd");
//		String ids = (String) map.get("ids");
//		String storeName = (String) map.get("storeName");
//
//		if (null != countTimeStart && !"".equals(countTimeStart) && null != countTimeEnd && !"".equals(countTimeEnd)) {
//			criteria.and("countTime").gte(countTimeStart).lte(countTimeEnd);
//		}
//		if (null != countTimeStart && !"".equals(countTimeStart) && (null == countTimeEnd || "".equals(countTimeEnd))) {
//			criteria.and("countTime").gte(countTimeStart);
//		}
//		if (null != countTimeEnd && !"".equals(countTimeEnd) && (null == countTimeStart || "".equals(countTimeStart))) {
//			criteria.and("countTime").lte(countTimeEnd);
//		}
//		if (null != storeName && !"".equals(storeName)) {
//			criteria.and("storeName").regex(storeName);
//		}
//		if (null != ids && !"".equals(ids)) {
//			List<String> idsList = new ArrayList<>();
//			if (ids.contains(",")) {
//				String[] idarr = ids.split(",");
//				for (int i = 0; i < idarr.length; i++) {
//					idsList.add(idarr[i]);
//				}
//			} else {
//				idsList.add(ids);
//			}
//			criteria.and("_id").in(idsList);
//		}
//		Query query = new Query();
//		query.addCriteria(criteria);
//		query.with(new Sort(new Order(Direction.DESC, "countTime")));
//		List<TransactionDataStoreReportForm> list = mongoTemplate.find(query, TransactionDataStoreReportForm.class);
//		String regex = "*****";
//		if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_REPORTFORMS_TRANSECON_COL_STORENAME)){
//			if(list != null && list.size() > 0){
//				for (TransactionDataStoreReportForm report:list){
//					report.setStoreName(CommonUtil.setColRegexParams(regex,report.getStoreName()));
//				}
//			}
//		}
//		return list;
//	}

	@Override
	public List<IndustryProductSpecTopRF> searchIndustryProductSpecList(String year, int month) {

		Criteria criteria = new Criteria();
		criteria = Criteria.where("_id").nin("");
		criteria.and("month").is(month).and("year").is(year);
		Query query = new Query();
		query.addCriteria(criteria);
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "numbers")));
		List<IndustryProductSpecTopRF> list = mongoTemplate.find(query, IndustryProductSpecTopRF.class);

		if (list != null && list.size() < 10) {
			for (int i = list.size() + 1; i < 11; i++) {
				setProductSpec(month, year, list, i);
			}
		}
		if (list == null || list.size() == 0) {
			for (int n = 1; n < 11; n++) {
				setProductSpec(month, year, list, n);
			}
		}
		return list;
	}

	private void setProductSpec(int month, String year, List<IndustryProductSpecTopRF> list, int n) {
		IndustryProductSpecTopRF specTopRF = new IndustryProductSpecTopRF();
		specTopRF.setMonth(month);
		specTopRF.setYear(year);
		specTopRF.setNumbers(n);
		specTopRF.setProductName(Constants.INDUSTRYDATA_NULL_MSG);
		specTopRF.setSpecName(Constants.INDUSTRYDATA_NULL_MSG);
		list.add(specTopRF);
	}

	@Override
	public List<IndustryStoreTopRF> searchIndustryStoreList(String year, int month) {
		Criteria criteria = new Criteria();
		criteria = Criteria.where("_id").nin("");
		criteria.and("month").is(month).and("year").is(year);
		Query query = new Query();
		query.addCriteria(criteria);

		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "numbers")));
		List<IndustryStoreTopRF> list = mongoTemplate.find(query, IndustryStoreTopRF.class);
		if (list != null && list.size() < 10) {
			for (int i = list.size() + 1; i < 11; i++) {
				setStore(month, year, list, i);
			}
		}
		if (list == null || list.size() == 0) {
			for (int n = 1; n < 11; n++) {
				setStore(month, year, list, n);
			}
		}
		return list;
	}

	private void setStore(int month, String year, List<IndustryStoreTopRF> list, int n) {
		IndustryStoreTopRF storeTopRF = new IndustryStoreTopRF();
		storeTopRF.setMonth(month);
		storeTopRF.setYear(year);
		storeTopRF.setNumbers(n);
		storeTopRF.setStoreName(Constants.INDUSTRYDATA_NULL_MSG);
		list.add(storeTopRF);
	}

	@Override
	public List<IndustryPurchaserTopRF> searchIndustryPurchaserList(String year, int month) {
		Criteria criteria = new Criteria();
		criteria = Criteria.where("_id").nin("");
		criteria.and("month").is(month).and("year").is(year);
		Query query = new Query();
		query.addCriteria(criteria);
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "numbers")));
		List<IndustryPurchaserTopRF> list = mongoTemplate.find(query, IndustryPurchaserTopRF.class);
		if (list != null && list.size() < 10) {
			for (int i = list.size() + 1; i < 11; i++) {
				setPurchaser(month, year, list, i);
			}
		}
		if (list == null || list.size() == 0) {
			for (int n = 1; n < 11; n++) {
				setPurchaser(month, year, list, n);
			}
		}
		return list;
	}

	private void setPurchaser(int month, String year, List<IndustryPurchaserTopRF> list, int n) {
		IndustryPurchaserTopRF purchaserTopRF = new IndustryPurchaserTopRF();
		purchaserTopRF.setMonth(month);
		purchaserTopRF.setYear(year);
		purchaserTopRF.setNumbers(n);
		purchaserTopRF.setPurchaserName(Constants.INDUSTRYDATA_NULL_MSG);
		list.add(purchaserTopRF);
	}
}
