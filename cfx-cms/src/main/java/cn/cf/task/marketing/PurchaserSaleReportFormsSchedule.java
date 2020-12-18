package cn.cf.task.marketing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.entity.CfGoods;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bCompanyDao;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.B2bOrderGoodsExtDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.OperationPurchaserSaleRF;
import cn.cf.entity.OrderGoodsWeightAmount;
import cn.cf.entity.PurchaserSaleReportForms;
import cn.cf.util.KeyUtils;

@Component
@EnableScheduling
public class PurchaserSaleReportFormsSchedule {

	private final static Logger logger = LoggerFactory.getLogger(PurchaserSaleReportFormsSchedule.class);

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;

	@Autowired
	private B2bOrderGoodsExtDao b2bOrderGoodsExtDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bCompanyExtDao b2bCompanyDao;

	/**
	 * 采购商交易统计 每天0:30处理
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void purchaserSale() {

		Date date = new Date();
		logger.error("**********************SupplierSaleReportFormsSchedule_START************************" + date);
		// 上月21号
		String lastMonth = CommonUtil.specifyMonth(-1, 21, date);
		// 本月20号
		String selfMonth = CommonUtil.specifyMonth(0, 20, date);

		Calendar calendar = Calendar.getInstance();// 日历对象
		calendar.setTime(date);// 设置当前日期
		int month = calendar.get(Calendar.MONTH) + 1;// 当前月
		// 补充当年以前月份数据
		for (int j = 1; j < month+1; j++) {
				// 上月21号
				String month1Start = CommonUtil.specifyMonth(-(j), 21, date);
				// 本月20号
				String month1End = CommonUtil.specifyMonth(-(j-1), 20, date);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("startTime", month1Start);
				map.put("endTime", month1End);
				setSelfDay(date,(month+1)-j, map);
		}
	}

	private void setSelfDay(Date date, int month, Map<String, Object> map) {
		List<OperationPurchaserSaleRF> list = b2bOrderExtDao.searchPurchaserSaleRF(map);

		if (list != null && list.size() > 0) {
			// 拼接数据
			setOperationPurchaserSale(list);
			// 添加到mongo
			for (OperationPurchaserSaleRF saleRF : list) {
				String year = new SimpleDateFormat("yyyy").format(date);
				Query query = Query
						.query(Criteria.where("year").is(year).and("purchaserName").is(saleRF.getPurchaserName()));
				List<PurchaserSaleReportForms> rfList = mongoTemplate.find(query, PurchaserSaleReportForms.class);
				PurchaserSaleReportForms saleReportForms = null;
				if (rfList != null && rfList.size() > 0) {
					saleReportForms = rfList.get(0);
					saleReportForms.setAccountName(saleRF.getAccountName());
				} else {
					saleReportForms = new PurchaserSaleReportForms();
					saleReportForms.setId(KeyUtils.getUUID());
					saleReportForms.setAccountName(saleRF.getAccountName());
					saleReportForms.setPurchaserName(saleRF.getPurchaserName());
					saleReportForms.setYear(year);
				}
				saleReportForms.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
				// 设置对应月份值
				setPurchaserSaleRF(month, saleRF, saleReportForms);
				// 设置总和
				setPurchaserSaleAll(saleReportForms);
				mongoTemplate.save(saleReportForms);
			}
		}
	}

	private void setPurchaserSaleAll(PurchaserSaleReportForms saleReportForms) {
		int countsAll = 0;
		double weightAll = 0d;
		BigDecimal amountAll = new BigDecimal(0.0);
		countsAll = saleReportForms.getCounts1() + saleReportForms.getCounts2() + saleReportForms.getCounts3()
				+ saleReportForms.getCounts4() + saleReportForms.getCounts5() + saleReportForms.getCounts6()
				+ saleReportForms.getCounts7() + saleReportForms.getCounts8() + saleReportForms.getCounts9()
				+ saleReportForms.getCounts10() + saleReportForms.getCounts11() + saleReportForms.getCounts12();

		weightAll = saleReportForms.getWeight1() + saleReportForms.getWeight2() + saleReportForms.getWeight3()
				+ saleReportForms.getWeight4() + saleReportForms.getWeight5() + saleReportForms.getWeight6()
				+ saleReportForms.getWeight7() + saleReportForms.getWeight8() + saleReportForms.getWeight9()
				+ saleReportForms.getWeight10() + saleReportForms.getWeight11() + saleReportForms.getWeight12();

		amountAll = amountAll.add(saleReportForms.getAmount1()).add(saleReportForms.getAmount2())
				.add(saleReportForms.getAmount3()).add(saleReportForms.getAmount4()).add(saleReportForms.getAmount5())
				.add(saleReportForms.getAmount6()).add(saleReportForms.getAmount7()).add(saleReportForms.getAmount8())
				.add(saleReportForms.getAmount9()).add(saleReportForms.getAmount10()).add(saleReportForms.getAmount11())
				.add(saleReportForms.getAmount12());

		saleReportForms.setCountsAll(countsAll);
		saleReportForms.setWeightAll(CommonUtil.formatDoubleFour(weightAll));
		saleReportForms.setAmountAll(amountAll);
	}

	private void setPurchaserSaleRF(int month, OperationPurchaserSaleRF saleRF,
			PurchaserSaleReportForms saleReportForms) {
		BigDecimal amount = saleRF.getActualAmount() == null ? new BigDecimal(0.0)
				: saleRF.getActualAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
		int counts = saleRF.getCounts() == null ? 0 : saleRF.getCounts();
		double weight = saleRF.getWeight() == null ? 0d : saleRF.getWeight();
		if (month == 1) {
			saleReportForms.setAmount1(amount);
			saleReportForms.setCounts1(counts);
			saleReportForms.setWeight1(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 2) {
			saleReportForms.setAmount2(amount);
			saleReportForms.setCounts2(counts);
			saleReportForms.setWeight2(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 3) {
			saleReportForms.setAmount3(amount);
			saleReportForms.setCounts3(counts);
			saleReportForms.setWeight3(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 4) {
			saleReportForms.setAmount4(amount);
			saleReportForms.setCounts4(counts);
			saleReportForms.setWeight4(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 5) {
			saleReportForms.setAmount5(amount);
			saleReportForms.setCounts5(counts);
			saleReportForms.setWeight5(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 6) {
			saleReportForms.setAmount6(amount);
			saleReportForms.setCounts6(counts);
			saleReportForms.setWeight6(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 7) {
			saleReportForms.setAmount7(amount);
			saleReportForms.setCounts7(counts);
			saleReportForms.setWeight7(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 8) {
			saleReportForms.setAmount8(amount);
			saleReportForms.setCounts8(counts);
			saleReportForms.setWeight8(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 9) {
			saleReportForms.setAmount9(amount);
			saleReportForms.setCounts9(counts);
			saleReportForms.setWeight9(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 10) {
			saleReportForms.setAmount10(amount);
			saleReportForms.setCounts10(counts);
			saleReportForms.setWeight10(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 11) {
			saleReportForms.setAmount11(amount);
			saleReportForms.setCounts11(counts);
			saleReportForms.setWeight11(CommonUtil.formatDoubleFour(weight));
		}
		if (month == 12) {
			saleReportForms.setAmount12(amount);
			saleReportForms.setCounts12(counts);
			saleReportForms.setWeight12(CommonUtil.formatDoubleFour(weight));
		}
	}

	private void setOperationPurchaserSale(List<OperationPurchaserSaleRF> list) {
		for (OperationPurchaserSaleRF operationPurchaserSaleRF : list) {
			String orderNumbers = operationPurchaserSaleRF.getOrderNumbers();
			List<String> arrayList = new ArrayList<String>();
			if (orderNumbers != null && !"".equals(orderNumbers) && orderNumbers.contains(",")) {
				String[] numbers = orderNumbers.split(",");
				for (int i = 0; i < numbers.length; i++) {
					arrayList.add(numbers[i]);
				}
			}
			if (orderNumbers != null && !"".equals(orderNumbers) && !orderNumbers.contains(",")) {

				arrayList.add(orderNumbers);
			}
			List<OrderGoodsWeightAmount> listAmount = b2bOrderGoodsExtDao.getB2bOrderGoodsWA(arrayList);
			if (listAmount != null && listAmount.size() > 0 ) {
				Double amounts = 0d;
				Double weights = 0d;
				for(OrderGoodsWeightAmount amount:listAmount){
					String goodsInfo = amount.getGoodsInfo();
					if (goodsInfo != null && !"".equals(goodsInfo)){
						CfGoods cf = JSON.parseObject(goodsInfo,CfGoods.class);
						if (cf != null) {
							Double adminFee = cf.getAdminFee() == null ? 0d : cf.getAdminFee();
							Double packageFee = cf.getPackageFee() == null ? 0d : cf.getPackageFee();
							Double loadFee = cf.getLoadFee() == null ? 0d : cf.getLoadFee();
							Double presentTotalFreight = amount.getPresentTotalFreight() == null ? 0d : amount.getPresentTotalFreight();
							Double presentPrice = amount.getPresentPrice() == null ? 0d : amount.getPresentPrice();
							Double weight = amount.getWeight() ==null?0d:amount.getWeight();
							weights += weight;
							amounts += (adminFee + packageFee + loadFee + presentPrice) * weight + presentTotalFreight;
						}
					}
				}
				operationPurchaserSaleRF.setWeight(CommonUtil.formatDoubleFour(weights));
				B2bCompanyDto companyDto = b2bCompanyDao.getByPk(operationPurchaserSaleRF.getPurchaserName());
				operationPurchaserSaleRF.setPurchaserName(companyDto.getName());
			}
		}
	}
}
