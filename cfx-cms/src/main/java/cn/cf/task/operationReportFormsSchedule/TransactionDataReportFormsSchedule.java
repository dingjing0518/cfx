package cn.cf.task.operationReportFormsSchedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import cn.cf.entity.TransactionDataReportForm;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class TransactionDataReportFormsSchedule {
	
	private final static Logger logger = LoggerFactory.getLogger(TransactionDataReportFormsSchedule.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;
	/**
	 * 交易数据，交易总览，每天0:30处理
	 *
	 */
	 @Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void transactionData() {
		logger.error("++++++++++++++++++++++++++++++++TransactionDataReportFormsSchedule_START");
		
		String yesterDay = CommonUtil.yesterDay();
		Map<String,Object> map = new HashMap<String,Object>();
		String insertTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//统计昨日数据
		yesterdayCounts(yesterDay, map, insertTime);
		//统计历史数据
		List<String> receTimeList = b2bOrderExtDao.searchReceivablesTimeList();
		if(receTimeList != null &&receTimeList.size() > 0){
			for (String historyTime : receTimeList) {

				yesterdayCounts(historyTime,map,insertTime);
			}
		}
	}
	private void yesterdayCounts(String yesterDay, Map<String, Object> map, String insertTime) {
		TransactionDataReportForm reportForm = new TransactionDataReportForm();
		map.put("time", yesterDay);
		Query query = Query
				.query(Criteria.where("countTime").is(yesterDay));
		List<TransactionDataReportForm> rfList = mongoTemplate.find(query, TransactionDataReportForm.class);
		if(rfList != null && rfList.size() > 0){
		}else{
			reportForm.setId(KeyUtils.getUUID());
			reportForm.setCountTime(yesterDay);
			reportForm.setInsertTime(insertTime);
			saveDataToMongo(yesterDay, map, reportForm);
		}
	}
	
	private void saveDataToMongo(String yesterDay, Map<String, Object> map, TransactionDataReportForm reportForm) {
		//交易数据，店铺数量
		map.put("khPurchaser", Constants.TRANSACTION_PURCHASER_KH);
		List<Integer> storeList = b2bOrderExtDao.searchStoreCounts(map);
 		if(storeList != null &&storeList.size() > 0){
 			int allStore = storeList.get(0) == null?0:storeList.get(0);
 			reportForm.setStoreAllCount(allStore);
 			int blankStore = storeList.get(1) == null?0:storeList.get(1);
 			reportForm.setStoreBlankCount(blankStore);
 			int loanStore = storeList.get(2) == null?0:storeList.get(2);
 			reportForm.setStoreLoanCount(loanStore);
 		}
 		//交易采购商数
		List<Integer> purchaserList = b2bOrderExtDao.searchPurchaserCounts(map);
		if(purchaserList != null && purchaserList.size() > 0){
			int purchaserAll = purchaserList.get(0) == null?0:purchaserList.get(0);
			reportForm.setPurchaserCount(purchaserAll);
			int purchaserBlank = purchaserList.get(1) == null?0:purchaserList.get(1);
			reportForm.setPurchBlankCount(purchaserBlank);
			int purchaserLoan = purchaserList.get(2) == null?0:purchaserList.get(2);
			reportForm.setPurchLoanCount(purchaserLoan);
		}
		//订单数
		List<Integer> orderList = b2bOrderExtDao.searchOrderCounts(map);
		if(orderList != null && orderList.size() > 0){
			int orderAll = orderList.get(0) == null?0:orderList.get(0);
			reportForm.setOrderCount(orderAll);
			int orderBlank = orderList.get(1) == null?0:orderList.get(1);
			reportForm.setOrderBlankCount(orderBlank);
			int orderLoan = orderList.get(2) == null?0:orderList.get(2);
			reportForm.setOrderLoanCount(orderLoan);
			int orderSelf = orderList.get(3) == null?0:orderList.get(3);
			reportForm.setOrderSelfCount(orderSelf);
			int orderWap = orderList.get(4) == null?0:orderList.get(4);
			reportForm.setOrderWapCount(orderWap);
		}
		
		//成交量（吨）
		List<Double> weightList = b2bOrderExtDao.searchWeightCounts(map);
		if(weightList != null && weightList.size() > 0){
			double weightAll = weightList.get(0) == null?0d:weightList.get(0);
			reportForm.setTransWeight(new BigDecimal(weightAll));
			double weightBlank = weightList.get(1) == null?0d:weightList.get(1);
			reportForm.setTransBlankWeight(new BigDecimal(weightBlank));
			double weightLoan = weightList.get(2) == null?0d:weightList.get(2);
			reportForm.setTransLoanWeight(new BigDecimal(weightLoan));
		}
		
		//交易额（元）
		List<Double> amountList = b2bOrderExtDao.searchAmountCounts(map);
		if(amountList != null && amountList.size() > 0){
			double amountAll = amountList.get(0) == null?0d:amountList.get(0);
			reportForm.setTransAmount(new BigDecimal(amountAll));
			double amountBlank = amountList.get(1) == null?0d:amountList.get(1);
			reportForm.setTransBlankAmount(new BigDecimal(amountBlank));
			double amountLoan = amountList.get(2) == null?0d:amountList.get(2);
			reportForm.setTransLoanAmount(new BigDecimal(amountLoan));
		}
		
		map.put("time",null);
		map.put("addUpTime",yesterDay);
		List<Integer> addUpOrderList = b2bOrderExtDao.searchOrderCounts(map);
		//累计订单数
		if(addUpOrderList != null && addUpOrderList.size() > 0){
			int addUpOrderAll = addUpOrderList.get(0) == null?0:addUpOrderList.get(0);
			reportForm.setAddUpOrderCount(addUpOrderAll);
			int addUpOrderBlank = addUpOrderList.get(1) == null?0:addUpOrderList.get(1);
			reportForm.setAddUpOrderBlankCount(addUpOrderBlank);
			int addUpOrderLoan = addUpOrderList.get(2) == null?0:addUpOrderList.get(2);
			reportForm.setAddUpOrderLoanCount(addUpOrderLoan);
		}
		
		//累计成交量（吨）
		List<Double> addUpWeightList = b2bOrderExtDao.searchWeightCounts(map);
		if(addUpWeightList != null && addUpWeightList.size() > 0){
			double addUpWeightAll = addUpWeightList.get(0) == null?0d:addUpWeightList.get(0);
			reportForm.setAddUpTransWeight(new BigDecimal(addUpWeightAll));
			double addUpWeightBlank = addUpWeightList.get(1) == null?0d:addUpWeightList.get(1);
			reportForm.setAddUpTransBlankWeight(new BigDecimal(addUpWeightBlank));
			double addUpWeightLoan = addUpWeightList.get(2) == null?0d:addUpWeightList.get(2);
			reportForm.setAddUpTransLoanWeight(new BigDecimal(addUpWeightLoan));
		}
		
		//累计交易额（元）
		List<Double> addUpAmountList = b2bOrderExtDao.searchAmountCounts(map);
		if(addUpAmountList != null && addUpAmountList.size() > 0){
			double addUpAmountAll = addUpAmountList.get(0) == null?0d:addUpAmountList.get(0);
			reportForm.setAddUpTransAmount(new BigDecimal(addUpAmountAll));
			double addUpAmountBlank = addUpAmountList.get(1) == null?0d:addUpAmountList.get(1);
			reportForm.setAddUpTransBlankAmount(new BigDecimal(addUpAmountBlank));
			double addUpAmountLoan = addUpAmountList.get(2) == null?0d:addUpAmountList.get(2);
			reportForm.setAddUpTransLoanAmount(new BigDecimal(addUpAmountLoan));
		}
		mongoTemplate.save(reportForm);
	}
	
}
