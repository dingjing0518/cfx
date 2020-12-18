package cn.cf.task.marketing;

import java.math.BigDecimal;
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

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.B2bOrderGoodsExtDao;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.entity.CfGoods;
import cn.cf.entity.SupplierSaleDataReport;
import cn.cf.util.ArithUtil;

/**
 * 营销报表：供应商销售数据
 * 
 * @author xht
 *
 *         2018年11月13日
 */
@Component
@EnableScheduling
public class SupplierSaleDataFormsSchedule {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bOrderGoodsExtDao b2bOrderGoodsExtDao;

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;

	@Scheduled(cron = "0 30 0 * * ?")

	public void supplierSaleData() {
		// 处理昨天的数据
		String yesterDay = CommonUtil.yesterDay();
		if (isExsit(yesterDay) == 0) {// 昨日数据是否统计过
			getOneInfo(yesterDay);
		}
		// 初始化以前数据
		if (countDataBeforeYesterDay(yesterDay) == 0) {
			String originalDate = b2bOrderExtDao.searchOriginalDate();
			List<String> dateList = CommonUtil.getBetweenDate(originalDate, yesterDay);
			for (String date : dateList) {
				getOneInfo(date);
			}
		}
	}

	private void getOneInfo(String date) {
		// 产生订单的店铺
		List<SupplierSaleDataReport> list = b2bOrderGoodsExtDao.searchStoreByOrder(date);
		

		if (null != list && list.size() > 0) {
			for (SupplierSaleDataReport report : list) {
				report.setDate(date);
				// 查询店铺一天的订单情况
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("storePk", report.getPk());
				map.put("date", date);
				map.put("block", Constants.BLOCK_CF);
				List<B2bOrderGoodsDto> orderList = b2bOrderGoodsExtDao.getByStorePk(map);
				for (B2bOrderGoodsDto orderGoodsDto : orderList) {
					CfGoods cfGoods = JSON.parseObject(orderGoodsDto.getGoodsInfo(), CfGoods.class);
					if (cfGoods != null && !cfGoods.equals("")) {
						List<Double> priceList = new ArrayList<Double>();
						priceList.add(cfGoods.getAdminFee() == null ? 0 : cfGoods.getAdminFee());
						priceList.add(cfGoods.getPackageFee() == null ? 0 : cfGoods.getPackageFee());
						priceList.add(cfGoods.getLoadFee() == null ? 0 : cfGoods.getLoadFee());
						priceList.add(orderGoodsDto.getPresentPrice() == null ? 0 : orderGoodsDto.getPresentPrice());
						Double price = ArithUtil.add(ArithUtil.mul(ArithUtil.addList(priceList), orderGoodsDto.getWeight()),
								orderGoodsDto.getPresentTotalFreight());
						price = new   BigDecimal(price).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						if (cfGoods.getProductName() != null && cfGoods.getProductName().equals("POY")) {
							report.setPOYnum(report.getPOYnum() + 1);
							report.setPOYprice(ArithUtil.add(report.getPOYprice(), price));
							report.setPOYweight(ArithUtil.add(orderGoodsDto.getWeight(), report.getPOYweight()));
						} else if (cfGoods.getProductName() != null && cfGoods.getProductName().equals("DTY")) {
							report.setDTYnum(report.getDTYnum() + 1);
							report.setDTYprice(ArithUtil.add(report.getDTYprice(), price));
							report.setDTYweight(ArithUtil.add(orderGoodsDto.getWeight(), report.getDTYweight()));
						} else if (cfGoods.getProductName() != null && cfGoods.getProductName().equals("FDY")) {
							report.setFDYnum(report.getFDYnum() + 1);
							report.setFDYprice(ArithUtil.add(report.getFDYprice(), price));
							report.setFDYweight(ArithUtil.add(orderGoodsDto.getWeight(), report.getFDYweight()));
						} else if (cfGoods.getProductName() != null && cfGoods.getProductName().equals("切片")) {
							report.setCUTnum(report.getCUTnum() + 1);
							report.setCUTprice(ArithUtil.add(report.getCUTprice(), price));
							report.setCUTweight(ArithUtil.add(orderGoodsDto.getWeight(), report.getCUTweight()));
						} else {
							report.setOthernum(report.getOthernum() + 1);
							report.setOtherprice(ArithUtil.add(report.getOtherprice(), price));
							report.setOtherweight(ArithUtil.add(orderGoodsDto.getWeight(), report.getOtherweight()));
						}
					}
				}
				
			}
			mongoTemplate.insertAll(list);
		}
	}

	private int countDataBeforeYesterDay(String date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(date));
		return (int) mongoTemplate.count(query, SupplierSaleDataReport.class);
	}

	private int isExsit(String date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").is(date));
		return (int) mongoTemplate.count(query, SupplierSaleDataReport.class);
	}

}
