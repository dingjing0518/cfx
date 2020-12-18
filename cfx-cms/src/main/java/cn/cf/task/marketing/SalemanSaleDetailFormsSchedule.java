package cn.cf.task.marketing;

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

import com.alibaba.fastjson.JSON;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.MarketingPersonnelExtDao;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.entity.CfGoods;
import cn.cf.entity.SalemanSaleDetailReport;
import cn.cf.util.ArithUtil;

/**
 * 营销报表：业务员交易明细
 * 
 * @author xht
 *
 *         2018年11月13日
 */
@Component
@EnableScheduling
public class SalemanSaleDetailFormsSchedule {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;

	@Autowired
	private MarketingPersonnelExtDao marketingPersonnelExtDao;

	@Scheduled(cron = "0 30 0 * * ?")
	
	public void salemanSaleDetail() {
		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();// 昨日
		if (isExsit(yesterDay) == 0) {
			// 昨日完成订单相关的业务员
			insertOneDay(yesterDay);

		}
		// 初始化以前数据
		if (countDateBeforeYesterDay(yesterDay) == 0) {
			// 查询支付时间最早的订单的支付时间
			String originalDate = b2bOrderExtDao.searchOriginalDate();
			List<String> dateList = CommonUtil.getBetweenDate(originalDate, yesterDay);
			for (String date : dateList) {
				insertOneDay(date);
			}
		}

	}

	private void insertOneDay(String day) {
		List<MarketingPersonnelDto> accountList = marketingPersonnelExtDao.getAccountByOrder(day);
		if (accountList.size() > 0) {
			List<SalemanSaleDetailReport> reports = new ArrayList<SalemanSaleDetailReport>();
			for (MarketingPersonnelDto dto : accountList) {
				SalemanSaleDetailReport report = new SalemanSaleDetailReport();
				report.setAccountName(dto.getAccountName());
				report.setAccountPk(dto.getAccountPk());
				report.setDate(day);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("accountPk",dto.getAccountPk());
				map.put("date", day);
				// 采购商客户数
				Integer purNum = marketingPersonnelExtDao.getPurCustomNum(map);
				report.setPurcompanyNum(purNum!=null?purNum:0);
				// 供应商客户数
				Integer supNum = marketingPersonnelExtDao.getSupCustomNum(map);
				report.setSupcompanyNum(supNum!=null?supNum:0);
				// 采购商订单
				List<B2bOrderGoodsDto> purOrdrer = marketingPersonnelExtDao.getPurOrderByAccount(map);
				if (null != purOrdrer && purOrdrer.size() > 0) {
					for (B2bOrderGoodsDto dto1 : purOrdrer) {
						CfGoods cfGoods = JSON.parseObject(dto1.getGoodsInfo(), CfGoods.class);
						
						if (cfGoods.getProductName()!=null&&cfGoods.getProductName().equals("POY")) {
							report.setpPOYWeight(ArithUtil.add(report.getpPOYWeight() !=null? report.getpPOYWeight() : 0,
									dto1.getWeight()));

						} else if (cfGoods.getProductName()!=null&&cfGoods.getProductName().equals("DTY")) {
							report.setpDTYWeight(ArithUtil.add(report.getpDTYWeight() !=null ? report.getpDTYWeight() : 0,
									dto1.getWeight()));

						} else if (cfGoods.getProductName()!=null&&cfGoods.getProductName().equals("FDY")) {
							report.setpFDYWeight(ArithUtil.add(report.getpFDYWeight() !=null ? report.getpFDYWeight() : 0,
									dto1.getWeight()));

						} else if (cfGoods.getProductName()!=null&&cfGoods.getProductName().equals("切片")) {
							report.setpCUTWeight(ArithUtil.add(report.getpCUTWeight() !=null ? report.getpCUTWeight() : 0,
									dto1.getWeight()));
						} else {
							report.setpOtherWeight(ArithUtil.add(
									report.getpOtherWeight() !=null ? report.getpOtherWeight() : 0, dto1.getWeight()));
						}
					}
				}
				// 供应商订单
				List<B2bOrderGoodsDto> supOrdrer = marketingPersonnelExtDao.getSupOrderByAccount(map);
				if (null != supOrdrer && supOrdrer.size() > 0) {
					for (B2bOrderGoodsDto dto2 : supOrdrer) {
						CfGoods cfGoods = JSON.parseObject(dto2.getGoodsInfo(), CfGoods.class);
						if (cfGoods.getProductName().equals("POY")) {
							report.setsPOYWeight(ArithUtil.add(report.getsPOYWeight() !=null  ? report.getsPOYWeight() : 0,
									dto2.getWeight()));
						} else if (cfGoods.getProductName().equals("DTY")) {
							report.setsDTYWeight(ArithUtil.add(report.getsDTYWeight() !=null  ? report.getsDTYWeight() : 0,
									dto2.getWeight()));

						} else if (cfGoods.getProductName().equals("FDY")) {
							report.setsFDYWeight(ArithUtil.add(report.getsFDYWeight() !=null  ? report.getsFDYWeight() : 0,
									dto2.getWeight()));

						} else if (cfGoods.getProductName().equals("切片")) {
							report.setsCUTWeight(ArithUtil.add(report.getsCUTWeight() !=null  ? report.getsCUTWeight() : 0,
									dto2.getWeight()));

						} else {
							report.setsOtherWeight(ArithUtil.add(
									report.getsOtherWeight() !=null  ? report.getsOtherWeight() : 0, dto2.getWeight()));
						}
					}

				}
				reports.add(report);
			}
			if (null != reports && reports.size() > 0) {
				mongoTemplate.insertAll(reports);
			}
		}
	}

	private int countDateBeforeYesterDay(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(day));
		return (int) mongoTemplate.count(query, SalemanSaleDetailReport.class);
	}

	private int isExsit(String day) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(day));
		return (int) mongoTemplate.count(query, SalemanSaleDetailReport.class);
	}

}
