package cn.cf.task.operationReportFormsSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bContractExtDao;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.BussEconPurchaserDataReport;
import cn.cf.entity.OrderNumEntity;
import cn.cf.util.ArithUtil;

/**
 * 采购商日交易金融数据
 * 
 * @author xht
 *
 *         2018年10月16日
 */
@Component
@EnableScheduling
public class BussEconPurchaserDataFormSchedule {

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;
	
	@Autowired
	private B2bContractExtDao contractExtDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 采购商日交易金融数据 每天0:30处理
	 */
	 @Scheduled(cron = "0 30 0 * * ?")
	// @Scheduled(cron = "0 15 10 * * ?")
	public void BussEconPurchaserDataReport() {

		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();// 昨日
		if (isExsit(yesterDay) == 0) {
			getOneData(yesterDay);
		}
		
		// 初始化以前数据
		if (countDataBeforeYesterDay(yesterDay) == 0) {
			// 查询支付时间最早的订单的支付时间
			String originalDate = b2bOrderExtDao.searchOriginalDate();
			List<String> dateList = CommonUtil.getBetweenDate(originalDate, yesterDay);
			for (String date : dateList) {
				getOneData(date);
			}
		}
	}

	private void getOneData(String date) {
		//获取所有有订单的采购商
		List<B2bCompanyDto> purchaserPkList = b2bOrderExtDao.getPurByOrder(date);
		if (purchaserPkList!=null&&purchaserPkList.size()>0) {
			List<BussEconPurchaserDataReport> list = new ArrayList<BussEconPurchaserDataReport>();
			for (B2bCompanyDto companyDto : purchaserPkList) {
				BussEconPurchaserDataReport  report = new BussEconPurchaserDataReport();
				report.setPurchaserName(companyDto.getName());
				report.setPurchaserPk(companyDto.getPk());
				report.setDate(date);
				Map<String, Object> map = new HashMap<>();
				map.put("date", date);
				map.put("economicsGoodsType", 1);
				map.put("purchaserPk", companyDto.getPk());
				OrderNumEntity purBt = b2bOrderExtDao.getOrderByPurchaserPk(map);
				OrderNumEntity purContBt =contractExtDao.getContractByPurchaserPk(map);
				sumOrder(report,purBt,purContBt);
				
				map.put("economicsGoodsType", 2);
				OrderNumEntity purD = b2bOrderExtDao.getOrderByPurchaserPk(map);
				OrderNumEntity purContD =contractExtDao.getContractByPurchaserPk(map);
				sumContract(report,purD,purContD);
				list.add(report);
			}
			mongoTemplate.insertAll(list);
		}
	}

	private void sumContract(BussEconPurchaserDataReport report, OrderNumEntity purD,
			OrderNumEntity purContD) {
		report.setdAmount(ArithUtil.addBigDecimal(purD.getAmount(), purContD.getAmount()));
		report.setdNumber(purD.getNumber()+purContD.getNumber());
		report.setdWeight(ArithUtil.addBigDecimal(purD.getWeight(), purContD.getWeight()));
	}

	private void sumOrder(BussEconPurchaserDataReport report, OrderNumEntity purBt,
			OrderNumEntity purContBt) {
		report.setBtAmount(ArithUtil.addBigDecimal(purBt.getAmount(), purContBt.getAmount()));
		report.setBtNumber(purBt.getNumber()+purContBt.getNumber());
		report.setBtWeight(ArithUtil.addBigDecimal(purBt.getWeight(), purContBt.getWeight()));
	}

	private Integer isExsit(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		return (int) mongoTemplate.count(query, BussEconPurchaserDataReport.class);
	}

	private int countDataBeforeYesterDay(String yesterDay) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(yesterDay));
		return (int) mongoTemplate.count(query, BussEconPurchaserDataReport.class);
	}

}
