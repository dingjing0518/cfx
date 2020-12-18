package cn.cf.task.marketing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.entity.SupplierSaleData;
import cn.cf.entity.SupplierSaleDataReportForms;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class PlatformSaleDataReportFormsSchedule {

	private final static Logger logger = LoggerFactory.getLogger(PlatformSaleDataReportFormsSchedule.class);

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 平台销售数据报表 每天0:30处理
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void platformSale() {

		Date date = new Date();
		logger.error("**********************SupplierSaleReportFormsSchedule_START************************" + date);
		// 上上月21号
		String lasterMonth = CommonUtil.specifyMonth(-2, 21, date);
		// 上月20号
		String lasteMonth = CommonUtil.specifyMonth(-1, 20, date);
		// 上月21号
		String lastMonth = CommonUtil.specifyMonth(-1, 21, date);
		// 本月20号
		String selfMonth = CommonUtil.specifyMonth(0, 20, date);

		Calendar calendar = Calendar.getInstance();// 日历对象
		calendar.setTime(date);// 设置当前日期
		int month = calendar.get(Calendar.MONTH) + 1;// 当前月

		String year = new SimpleDateFormat("yyyy").format(date);

		String[] supllierList = Constants.MARKETING_SUPPLIER_STOREPK_LIST;

		//补充当年以前月份数据
		addBeforeData(date, month, year, supllierList);



		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.set(Calendar.DAY_OF_YEAR, 1);
		String dd =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		for (int y = 1; y < 4; y++){
			int beforeYear = Integer.valueOf(year) -y;
			for (int m = 1; m < 13; m++){
				addBeforeData(date, month, year, supllierList);
			}
		}

		//统计当前月份数据
		countReportForms(date, lasterMonth, lasteMonth, lastMonth, selfMonth, month, year, supllierList);
		// 设置累计
		for (int i = 1; i < month+1; i++) {
			setAddUp(i, year);
		}
		//补全后面数据
		setSupplierSaleRF(date, year,month);
	}

	/**
	 * 补充以前数据
	 * @param date
	 * @param month
	 * @param year
	 * @param supllierList
	 */
	private void addBeforeData(Date date, int month, String year, String[] supllierList) {
		for (int j = 1; j < month; j++) {
			Query query = Query.query(Criteria.where("year").is(year).and("month").is(month-j));
			List<SupplierSaleDataReportForms> rfList = mongoTemplate.find(query, SupplierSaleDataReportForms.class);
			if(rfList != null && rfList.size() > 0){
				continue;
			}else{
				// 上上月21号
				String month2Start = CommonUtil.specifyMonth(-(j+2), 21, date);
				// 上月20号
				String month2End = CommonUtil.specifyMonth(-(j+1), 20, date);

				// 上月21号
				String month1Start = CommonUtil.specifyMonth(-(j+1), 21, date);
				// 本月20号
				String month1End = CommonUtil.specifyMonth(-j, 20, date);

				countReportForms(date, month2Start, month2End, month1Start, month1End, month-j, year, supllierList);
				for (int i = 1; i < month+1; i++) {
					setAddUp(i, year);
				}
			}
		}
	}

	private void countReportForms(Date date, String lasterMonth, String lasteMonth, String lastMonth, String selfMonth,
								  int month, String year, String[] supllierList) {

		SupplierSaleDataReportForms saleDataReportForms = new SupplierSaleDataReportForms();
		saleDataReportForms.setMonth(month);
		saleDataReportForms.setYear(year);
		saleDataReportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		// 设置盛虹
		setSupplierSaleSH(lasterMonth, lasteMonth, lastMonth, selfMonth, month, year, supllierList,
				saleDataReportForms);
		// 设置新凤鸣
		setSupplierSaleXFM(lasterMonth, lasteMonth, lastMonth, selfMonth, month, year, supllierList,
				saleDataReportForms);
		// 设置其他
		setSupplierSaleOther(lasterMonth, lasteMonth, lastMonth, selfMonth, month, year, supllierList,
				saleDataReportForms);
		// 保存到mongo报表
		saveToMongo(month, year, saleDataReportForms);
	}

	private void setSupplierSaleRF(Date date, String year,int month) {
		for (int i = month+1; i < 13; i++) {
			Query query = Query.query(Criteria.where("year").is(year).and("month").is(i));
			List<SupplierSaleDataReportForms> rfList = mongoTemplate.find(query, SupplierSaleDataReportForms.class);
			if(rfList != null && rfList.size() > 0){
				continue;
			}else{
				SupplierSaleDataReportForms saleDataReportForms = new SupplierSaleDataReportForms();
				saleDataReportForms.setMonth(i);
				saleDataReportForms.setYear(year);
				saleDataReportForms.setId(KeyUtils.getUUID());
				saleDataReportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
				mongoTemplate.save(saleDataReportForms);
			}

		}
	}

	private void saveToMongo(int month, String year, SupplierSaleDataReportForms saleDataReportForms) {
		Query query = Query.query(Criteria.where("year").is(year).and("month").is(month));
		List<SupplierSaleDataReportForms> rfList = mongoTemplate.find(query, SupplierSaleDataReportForms.class);

		if (rfList != null && rfList.size() > 0) {
			saleDataReportForms.setId(rfList.get(0).getId());
		} else {
			saleDataReportForms.setId(KeyUtils.getUUID());
		}
		mongoTemplate.save(saleDataReportForms);
	}

	private void setAddUp(int month, String year) {
		BigDecimal addUpAmount = new BigDecimal(0.0);
		int addUpCounts = 0;
		double addUpWeight = 0d;
		//查询本月及之前的数据做累计
		Query query = Query.query(Criteria.where("year").is(year).and("month").is(month));
		query.with(new Sort(new Order(Direction.DESC, "month")));
		List<SupplierSaleDataReportForms> rfList = mongoTemplate.find(query, SupplierSaleDataReportForms.class);
		if (rfList != null && rfList.size() > 0) {
			//累积到本月
			SupplierSaleDataReportForms saleDataRF = rfList.get(0);
			addUpAmount = addUpAmount.add(
					saleDataRF.getAmountSH().add(saleDataRF.getAmountXFM()).add(saleDataRF.getAmountOther()));
			addUpCounts += saleDataRF.getCountsSH() + saleDataRF.getCountsXFM() + saleDataRF.getCountsOther();
			addUpWeight += saleDataRF.getWeightSH() + saleDataRF.getWeightXFM() + saleDataRF.getWeightOther();

			saleDataRF.setAmountAddUp(addUpAmount);
			saleDataRF.setCountsAddUp(addUpCounts);
			saleDataRF.setWeightAddUp(CommonUtil.formatDoubleFour(addUpWeight));
			mongoTemplate.save(saleDataRF);
		}
	}

	private void setSupplierSaleSH(String lasterMonth, String lasteMonth, String lastMonth, String selfMonth, int month,
								   String year, String[] supllierList, SupplierSaleDataReportForms saleDataReportForms) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询盛虹本月
		map.put("startTime", lastMonth);
		map.put("endTime", selfMonth);
		// 查询盛虹上月
		map.put("lastStartTime", lasterMonth);
		map.put("lastEndTime", lasteMonth);
		map.put("supplierPk", supllierList[0]);
		map.put("supplierPkSH", "");
		map.put("supplierPkXFM", "");
		List<SupplierSaleData> saleDataList = b2bOrderExtDao.searchSupplierSaleDataRF(map);
		if (saleDataList != null && saleDataList.size() == 3) {
			SupplierSaleData saleDataSelf = saleDataList.get(0);
			SupplierSaleData saleDataLast = saleDataList.get(1);
			SupplierSaleData saleDataAll = saleDataList.get(2);

			if (saleDataSelf != null) {
				saleDataReportForms.setAmountSH(saleDataSelf.getAmount());
				saleDataReportForms.setWeightSH(CommonUtil.formatDoubleFour(saleDataSelf.getWeight()));
				saleDataReportForms.setCountsSH(saleDataSelf.getCounts());
				if (saleDataLast != null && saleDataAll != null) {
					Double selfAmount = saleDataSelf.getAmount().doubleValue();
					Double lastAmount = saleDataLast.getAmount().doubleValue();
					Double allAmount = saleDataAll.getAmount().doubleValue();

					// 占比
					if (allAmount > 0) {
						Double precentBig = (selfAmount/allAmount)*100;
						saleDataReportForms.setPrecentSH(CommonUtil.formatDoubleTwo(precentBig));
					}
					// 增长率
					if(selfAmount > 0){
						Double addUpprcentBy = ((selfAmount-lastAmount)/selfAmount)*100;
						saleDataReportForms.setAddUpPrecentSH(CommonUtil.formatDoubleTwo(addUpprcentBy));
					}
				}
			}
		}
	}

	private void setSupplierSaleXFM(String lasterMonth, String lasteMonth, String lastMonth, String selfMonth,
									int month, String year, String[] supllierList, SupplierSaleDataReportForms saleDataReportForms) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询盛虹本月
		map.put("startTime", lastMonth);
		map.put("endTime", selfMonth);
		// 查询新凤鸣上月
		map.put("lastStartTime", lasterMonth);
		map.put("lastEndTime", lasteMonth);
		map.put("supplierPk", supllierList[1]);
		map.put("supplierPkSH", "");
		map.put("supplierPkXFM", "");
		List<SupplierSaleData> saleDataList = b2bOrderExtDao.searchSupplierSaleDataRF(map);
		if (saleDataList != null && saleDataList.size() == 3) {
			SupplierSaleData saleDataSelf = saleDataList.get(0);
			SupplierSaleData saleDataLast = saleDataList.get(1);
			SupplierSaleData saleDataAll = saleDataList.get(2);

			if (saleDataSelf != null) {
				saleDataReportForms.setAmountXFM(saleDataSelf.getAmount());
				saleDataReportForms.setWeightXFM(CommonUtil.formatDoubleFour(saleDataSelf.getWeight()));
				saleDataReportForms.setCountsXFM(saleDataSelf.getCounts());
				if (saleDataLast != null && saleDataAll != null) {
					Double selfAmount = saleDataSelf.getAmount().doubleValue();
					Double lastAmount = saleDataLast.getAmount().doubleValue();
					Double allAmount = saleDataAll.getAmount().doubleValue();
					// 占比

					if (allAmount > 0) {
						Double precentBig = (selfAmount/allAmount)*100;
						saleDataReportForms.setPrecentXFM(CommonUtil.formatDoubleTwo(precentBig));
					}
					// 增长率
					if(selfAmount > 0){
						Double addUpprcentBy = ((selfAmount-lastAmount)/selfAmount);
						saleDataReportForms.setAddUpPrecentXFM(CommonUtil.formatDoubleTwo(addUpprcentBy*100));
					}
				}
			}
		}
	}

	private void setSupplierSaleOther(String lasterMonth, String lasteMonth, String lastMonth, String selfMonth,
									  int month, String year, String[] supllierList, SupplierSaleDataReportForms saleDataReportForms) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询盛虹本月
		map.put("startTime", lastMonth);
		map.put("endTime", selfMonth);
		// 查询盛虹上月
		map.put("lastStartTime", lasterMonth);
		map.put("lastEndTime", lasteMonth);
		map.put("supplierPk", "");
		map.put("supplierPkSH", supllierList[0]);
		map.put("supplierPkXFM", supllierList[1]);
		List<SupplierSaleData> saleDataList = b2bOrderExtDao.searchSupplierSaleDataRF(map);
		if (saleDataList != null && saleDataList.size() == 3) {
			SupplierSaleData saleDataSelf = saleDataList.get(0);
			SupplierSaleData saleDataLast = saleDataList.get(1);
			SupplierSaleData saleDataAll = saleDataList.get(2);

			if (saleDataSelf != null) {
				saleDataReportForms.setAmountOther(saleDataSelf.getAmount());
				saleDataReportForms.setWeightOther(CommonUtil.formatDoubleFour(saleDataSelf.getWeight()));
				saleDataReportForms.setCountsOther(saleDataSelf.getCounts());
				if (saleDataLast != null && saleDataAll != null) {
					Double selfAmount = saleDataSelf.getAmount().doubleValue();
					Double lastAmount = saleDataLast.getAmount().doubleValue();
					Double allAmount = saleDataAll.getAmount().doubleValue();

					// 占比
					if (allAmount > 0) {
						Double precent = CommonUtil.formatDoubleTwo((selfAmount.doubleValue()/allAmount.doubleValue()) * 100);
						saleDataReportForms.setPrecentOther(precent);
					}
					// 增长率
					if(selfAmount > 0){
						Double addUpprcentBy = ((selfAmount-lastAmount)/selfAmount);
						saleDataReportForms.setAddUpPrecentOther(CommonUtil.formatDoubleTwo(addUpprcentBy*100));
					}
				}
			}
		}
	}
}
