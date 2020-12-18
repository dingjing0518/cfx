package cn.cf.task.operationReportFormsSchedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.cf.entity.TransactionDataStoreEntity;
import cn.cf.entity.TransactionDataStoreReportForm;
import cn.cf.json.JsonUtils;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class TransactionDataStoreReportFormsSchedule {
	
	private final static Logger logger = LoggerFactory.getLogger(TransactionDataStoreReportFormsSchedule.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;
	/**
	 * 店铺日交易金融数据每天0:30处理
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void transactionDataStore() {
		logger.error("++++++++++++++++++++++++++++++++TransactionDataStoreReportFormsSchedule");
		
		String yesterDay = CommonUtil.yesterDay();
		String toDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		
		//统计昨天数据
		setYesterday(yesterDay, toDay, map);
		//补全历史数据
		List<String> receTimeList = b2bOrderExtDao.searchReceivablesTimeList();
		if(receTimeList != null && receTimeList.size() > 0){
			for (String receTime : receTimeList) {
				setYesterday(receTime, toDay, map);
			}
		}
	}

	private void setYesterday(String yesterDay, String toDay, Map<String, Object> map) {
		map.put("dayTime", yesterDay);
		//化纤白条
		map.put("goodsType", Constants.ONE);
		List<TransactionDataStoreReportForm> storeReportFormList = new ArrayList<>();

		List<TransactionDataStoreEntity> listBlank = b2bOrderExtDao.searchStoreTransDataCounts(map);
		if(listBlank != null && listBlank.size() > 0){
			setBlank(yesterDay, toDay, listBlank, storeReportFormList);
		}

		//化纤贷
		map.put("goodsType", Constants.TWO);
		List<TransactionDataStoreEntity> listLoan = b2bOrderExtDao.searchStoreTransDataCounts(map);
		if(listLoan != null && listLoan.size() > 0){
			setLoan(yesterDay, toDay, storeReportFormList, listLoan);
		}

		//保存数据到mongo
		if(storeReportFormList != null && storeReportFormList.size() > 0){
			saveDataToMongo(yesterDay, storeReportFormList);
		}
	}

	private void saveDataToMongo(String yesterDay, List<TransactionDataStoreReportForm> storeReportFormList) {
		for (TransactionDataStoreReportForm transactionDataStoreReportForm : storeReportFormList) {
			Query query = Query
					.query(Criteria.where("countTime").is(yesterDay).and("storePk").is(transactionDataStoreReportForm.getStorePk()));
			List<TransactionDataStoreReportForm> rfList = mongoTemplate.find(query, TransactionDataStoreReportForm.class);
			if(rfList != null && rfList.size() > 0){
			}else{
				mongoTemplate.save(transactionDataStoreReportForm);	
			}
			
		}
	}
	private void setBlank(String yesterDay, String toDay, List<TransactionDataStoreEntity> listBlank,
			List<TransactionDataStoreReportForm> storeReportFormList) {
		for (TransactionDataStoreEntity dataStoreBlank : listBlank) {
			List<String> orderNumberList = new ArrayList<>();
			TransactionDataStoreReportForm storeReportForm = new TransactionDataStoreReportForm();
			storeReportForm.setId(KeyUtils.getUUID());

			storeReportForm.setCountTime(yesterDay);
			storeReportForm.setInsertTime(toDay);
			storeReportForm.setStorePk(dataStoreBlank.getStorePk());
			storeReportForm.setStoreName(dataStoreBlank.getStoreName());
			String orderNumbers = dataStoreBlank.getOrderNumbers();
			if(orderNumbers != null && !"".equals(orderNumbers)&& orderNumbers.contains(",")){
				String[] orderAddr = orderNumbers.split(",");
				for (int i = 0; i < orderAddr.length; i++) {
					orderNumberList.add(orderAddr[i]);
				}
			}
			if(orderNumbers != null && !"".equals(orderNumbers) && !orderNumbers.contains(",")){
				orderNumberList.add(orderNumbers);	
			}
			logger.info("+++++++++++++b2bOrderExtDao.getStoreTransWeightCounts_setBlank:"+JsonUtils.convertToString(orderNumberList));
			Double weightBlank = b2bOrderExtDao.getStoreTransWeightCounts(orderNumberList);
			Double weightBlankContract = b2bOrderExtDao.getStoreContractTransWeightCounts(orderNumberList);
			Double weight = (weightBlank==null?0d:weightBlank)+(weightBlankContract==null?0d:weightBlankContract);
			storeReportForm.setAmountBlank(new BigDecimal(dataStoreBlank.getAmount()));
			storeReportForm.setCountBlank(dataStoreBlank.getCount());
			storeReportForm.setWeightBlank(new BigDecimal(weight));
			storeReportFormList.add(storeReportForm);
		}
	}
	private void setLoan(String yesterDay, String toDay, List<TransactionDataStoreReportForm> storeReportFormList,
			List<TransactionDataStoreEntity> listLoan) {
		for (TransactionDataStoreEntity dataStoreLoan : listLoan) {
			List<String> orderNumberList = new ArrayList<>();
			String orderNumbers = dataStoreLoan.getOrderNumbers();
			if(orderNumbers != null && !"".equals(orderNumbers)&& orderNumbers.contains(",")){
				String[] orderAddr = orderNumbers.split(",");
				for (int i = 0; i < orderAddr.length; i++) {
					orderNumberList.add(orderAddr[i]);
				}
			}
			if(orderNumbers != null && !"".equals(orderNumbers) && !orderNumbers.contains(",")){
				orderNumberList.add(orderNumbers);	
			}
			Double weightLoan = b2bOrderExtDao.getStoreTransWeightCounts(orderNumberList);
			Double weightBlankContract = b2bOrderExtDao.getStoreContractTransWeightCounts(orderNumberList);
			Double weight = (weightLoan==null?0d:weightLoan)+(weightBlankContract==null?0d:weightBlankContract);
			boolean isExist = false;
			//判断是否和化纤白条存在相同的店铺
			if(storeReportFormList != null && storeReportFormList.size() > 0){
				for (TransactionDataStoreReportForm entity : storeReportFormList) {
					if(dataStoreLoan.getStorePk().equals(entity.getStorePk())){
						entity.setAmountLoan(new BigDecimal(dataStoreLoan.getAmount()));
						entity.setCountLoan(dataStoreLoan.getCount());
						entity.setWeightLoan(new BigDecimal(weight));
						isExist = true;
						break;
					}
				}
				if(!isExist){
					TransactionDataStoreReportForm rf = setLoanRF(yesterDay, toDay, dataStoreLoan, weightLoan);
					storeReportFormList.add(rf);
				}
			}else{
				TransactionDataStoreReportForm rf = setLoanRF(yesterDay, toDay, dataStoreLoan, weightLoan);
				storeReportFormList.add(rf);
			}
			
		}
	}
	
	private TransactionDataStoreReportForm setLoanRF(String yesterDay, String toDay, TransactionDataStoreEntity dataStoreLoan,
			Double weightLoan) {
		TransactionDataStoreReportForm storeReportForm = new TransactionDataStoreReportForm();
		storeReportForm.setId(KeyUtils.getUUID());
		storeReportForm.setStorePk(dataStoreLoan.getStorePk());
		storeReportForm.setStoreName(dataStoreLoan.getStoreName());
		storeReportForm.setAmountLoan(new BigDecimal(dataStoreLoan.getAmount()));
		storeReportForm.setCountLoan(dataStoreLoan.getCount());
		storeReportForm.setWeightLoan(weightLoan == null?new BigDecimal(0.0):new BigDecimal(weightLoan));
		storeReportForm.setCountTime(yesterDay);
		storeReportForm.setInsertTime(toDay);
		return storeReportForm;
	}
	
}
