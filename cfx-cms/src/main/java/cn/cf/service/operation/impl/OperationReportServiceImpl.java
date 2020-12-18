package cn.cf.service.operation.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cf.entity.*;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.impl.EconReportProductUseServiceImpl;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.B2bOrderGoodsExtDao;
import cn.cf.service.operation.OperationReportService;
import cn.cf.util.ArithUtil;

@Service
public class OperationReportServiceImpl implements OperationReportService {

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;
	@Autowired
	private B2bOrderGoodsExtDao b2bOrderGoodsExtDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	private final static Logger logger = LoggerFactory.getLogger(OperationReportServiceImpl.class);

	@Override
	public PageModel<OperationSupplierSaleRF> searchSupplierSaleList(QueryModel<OperationSupplierSaleRF> qm, String accountPk) {

		PageModel<OperationSupplierSaleRF> pm = new PageModel<OperationSupplierSaleRF>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", qm.getEntity().getStartTime());
		map.put("endTime", qm.getEntity().getEndTime());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		List<OperationSupplierSaleRF> list = b2bOrderExtDao.searchSupplierSaleRF(map);
		if (list != null && list.size() > 0) {// 拼接数据，根据总订单号统计所有子订单的重量和金额
			for (OperationSupplierSaleRF operationSupplierSaleRF : list) {

				operationSupplierSaleRF.setAmount(new BigDecimal(ArithUtil.roundBigDecimal(operationSupplierSaleRF.getAmount(),2)));

			    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALE_COL_STORENAME)) {
			        operationSupplierSaleRF.setStoreName(CommonUtil.hideCompanyName(operationSupplierSaleRF.getStoreName()));
                }
			    
			    if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALE_COL_ACCOUNTNAME)){
			        operationSupplierSaleRF.setAccountName(CommonUtil.hideContacts(operationSupplierSaleRF.getAccountName()));
			    }
			    
				List<String> orderNumberList = new ArrayList<String>();
				String orderNumbers = operationSupplierSaleRF.getOrderNumbers();
				if (orderNumbers != null && !"".equals(orderNumbers) && orderNumbers.contains(",")) {
					String[] array = operationSupplierSaleRF.getOrderNumbers().split(",");
					for (int i = 0; i < array.length; i++) {
						orderNumberList.add(array[i]);
					}
				}
				if (orderNumbers != null && !"".equals(orderNumbers) && !orderNumbers.contains(",")) {
					orderNumberList.add(orderNumbers);
				}

				if (orderNumberList != null && orderNumberList.size() > 0) {
					List<OrderGoodsWeightAmount> weightAmountList = b2bOrderGoodsExtDao.getB2bOrderGoodsWA(orderNumberList);
					if (weightAmountList != null && weightAmountList.size() > 0) {
//						Double amounts = 0d;
//						amounts = getaAmount(weightAmountList, amounts);
						Double weights = 0d;
						weights = getWeights(weightAmountList, weights);
						operationSupplierSaleRF.setWeight(CommonUtil.formatDoubleFive(weights));
					}
				}
			}
		}
		int counts = b2bOrderExtDao.countSupplierSaleRF(map);
		pm.setDataList(list);
		pm.setTotalCount(counts);
		return pm;
	}

	private Double getWeights(List<OrderGoodsWeightAmount> weightAmountList, Double weights) {
		for(OrderGoodsWeightAmount amount:weightAmountList){
					Double weight = amount.getWeight() ==null?0d:amount.getWeight();
					weights +=weight;
				}
		return weights;
	}

	private Double getaAmount(List<OrderGoodsWeightAmount> weightAmountList, Double amounts) {
		for(OrderGoodsWeightAmount amount:weightAmountList){
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
					amounts += (adminFee + packageFee + loadFee + presentPrice) * weight + presentTotalFreight;
				}
			}
		}
		return amounts;
	}

	@Override
	public List<OperationSupplierSaleRF> exportSupplierSaleList(Map<String, Object> map, String accountPk) {
		map.put("startTime", map.get("startTime"));
		map.put("endTime", map.get("endTime"));
		List<OperationSupplierSaleRF> list = b2bOrderExtDao.searchSupplierSaleRF(map);
		if (list != null && list.size() > 0) {// 拼接数据，根据总订单号统计所有子订单的重量和金额
			for (OperationSupplierSaleRF operationSupplierSaleRF : list) {
				operationSupplierSaleRF.setAmount(new BigDecimal(ArithUtil.roundBigDecimal(operationSupplierSaleRF.getAmount(),2)));
			    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALE_COL_STORENAME)) {
                    operationSupplierSaleRF.setStoreName(CommonUtil.hideCompanyName(operationSupplierSaleRF.getStoreName()));
                }
                
                if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALE_COL_ACCOUNTNAME)){
                    operationSupplierSaleRF.setAccountName(CommonUtil.hideContacts(operationSupplierSaleRF.getAccountName()));
                }
				List<String> orderNumberList = new ArrayList<String>();
				String orderNumbers = operationSupplierSaleRF.getOrderNumbers();
				if (orderNumbers != null && !"".equals(orderNumbers) && orderNumbers.contains(",")) {
					String[] array = operationSupplierSaleRF.getOrderNumbers().split(",");
					for (int i = 0; i < array.length; i++) {
						orderNumberList.add(array[i]);
					}
				}
				if (orderNumbers != null && !"".equals(orderNumbers) && !orderNumbers.contains(",")) {
					orderNumberList.add(orderNumbers);
				}

				if (orderNumberList != null && orderNumberList.size() > 0) {
				    
					List<OrderGoodsWeightAmount> weightAmountList = b2bOrderGoodsExtDao.getB2bOrderGoodsWA(orderNumberList);
					if (weightAmountList != null && weightAmountList.size() > 0 ) {
						//Double amounts = 0d;
						//amounts = getaAmount(weightAmountList, amounts);
						Double weights = 0d;
						weights = getWeights(weightAmountList, weights);
						operationSupplierSaleRF.setWeight(CommonUtil.formatDoubleFive(weights));
						//operationSupplierSaleRF.setAmount(new BigDecimal(ArithUtil.roundBigDecimal(new BigDecimal(amounts),2)));
					}
				}
			}
		}
		return list;
	}

	@Override
	public PageModel<PurchaserSaleReportForms> searchPuchaserSaleList(QueryModel<PurchaserSaleReportForms> qm,String accountPk) {
		PageModel<PurchaserSaleReportForms> pm = new PageModel<PurchaserSaleReportForms>();
		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(qm.getEntity().getYear()));

		long count = mongoTemplate.count(query, PurchaserSaleReportForms.class);

		query.with(new Sort(new Order(Direction.ASC, qm.getFirstOrderName())));
		query.skip(qm.getStart());
		query.limit(qm.getLimit());
		List<PurchaserSaleReportForms> list = mongoTemplate.find(query, PurchaserSaleReportForms.class);
		for (PurchaserSaleReportForms p : list) {
		    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_PURCHASERSALE_COL_PURNAME)) {
                p.setPurchaserName(CommonUtil.hideCompanyName(p.getPurchaserName()));
            }
		    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_PURCHASERSALE_COL_AUCCOUNTNAME)) {
                p.setAccountName(CommonUtil.hideContacts(p.getAccountName()));
            }
        }
		pm.setTotalCount((int) count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public List<PurchaserSaleReportForms> exporthPuchaserSaleList(String year, String accountPk) {
		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(year));
		List<PurchaserSaleReportForms> list = mongoTemplate.find(query, PurchaserSaleReportForms.class);

		for (PurchaserSaleReportForms p : list) {
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_PURCHASERSALE_COL_PURNAME)) {
                p.setPurchaserName(CommonUtil.hideCompanyName(p.getPurchaserName()));
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_PURCHASERSALE_COL_AUCCOUNTNAME)) {
                p.setAccountName(CommonUtil.hideContacts(p.getAccountName()));
            }
        }
		return list;
	}

	@Override
	public Map<String, Object> searchSupplierSaleData(Map<String, Object> map) {

		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(map.get("year")));
		query.with(new Sort(new Order(Direction.ASC, "month")));
		List<SupplierSaleDataReportForms> list = mongoTemplate.find(query, SupplierSaleDataReportForms.class);

		if (list != null && list.size() > 0) {
			// 拼接合计
			SupplierSaleTotalData totalData = new SupplierSaleTotalData();
			setTotalData(list, totalData);
			map.put("list", list);
			map.put("totalData", totalData);
			return map;
		} else {
			// 如果没有查到选择的年份数据，则默认为零
			String[] nameArr = Constants.ECONOMICS_CUSTOMER_TIME_LIST;
			List<SupplierSaleDataReportForms> listEmpty = new ArrayList<>();
			for (int i = 3; i < nameArr.length; i++) {
				SupplierSaleDataReportForms dataReportForms = new SupplierSaleDataReportForms();
				String name = nameArr[i];
				dataReportForms.setMonthName(name);
				listEmpty.add(dataReportForms);
			}
			SupplierSaleTotalData emptyData = new SupplierSaleTotalData();
			map.put("list", listEmpty);
			map.put("totalData", emptyData);
			return map;
		}

	}

	private void setTotalData(List<SupplierSaleDataReportForms> list, SupplierSaleTotalData totalData) {

		int countsAddup = 0;
		BigDecimal amountAddup = new BigDecimal(0.0);
		double weightAddup = 0d;

		int countsSH = 0;
		BigDecimal amountSH = new BigDecimal(0.0);
		double weightSH = 0d;

		int countsXFM = 0;
		BigDecimal amountXFM = new BigDecimal(0.0);
		double weightXFM = 0d;

		int countsOther = 0;
		BigDecimal amountOther = new BigDecimal(0.0);
		double weightOther = 0d;
		int monthIndex = 3;
		for (SupplierSaleDataReportForms data : list) {

			data.setMonthName(Constants.ECONOMICS_CUSTOMER_TIME_LIST[monthIndex]);
			monthIndex++;

			countsAddup += data.getCountsAddUp();
			amountAddup = amountAddup.add(data.getAmountAddUp());
			weightAddup += data.getWeightAddUp();

			countsSH += data.getCountsSH();
			amountSH = amountSH.add(data.getAmountSH());
			weightSH += data.getWeightSH();

			countsXFM += data.getCountsXFM();
			amountXFM = amountXFM.add(data.getAmountXFM());
			weightXFM += data.getWeightXFM();

			countsOther += data.getCountsOther();
			amountOther = amountOther.add(data.getAmountOther());
			weightOther += data.getWeightOther();

			data.setAmountSH(data.getAmountSH().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setAmountXFM(data.getAmountXFM().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setAmountAddUp(data.getAmountAddUp().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setAmountOther(data.getAmountOther().setScale(2, BigDecimal.ROUND_HALF_UP));
			//list.add(data);
		}

		totalData.setAmountAddup(amountAddup.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsAddup(countsAddup);
		totalData.setWeightAddup(CommonUtil.formatDoubleFour(weightAddup));

		totalData.setAmountSH(amountSH.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsSH(countsSH);
		totalData.setWeightSH(CommonUtil.formatDoubleFour(weightSH));

		totalData.setAmountXFM(amountXFM.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsXFM(countsXFM);
		totalData.setWeightXFM(CommonUtil.formatDoubleFour(weightXFM));

		totalData.setAmountOther(amountOther.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsOther(countsOther);
		totalData.setWeightOther(CommonUtil.formatDoubleFour(weightOther));
	}

	@Override
	public PageModel<GoodsUpdateReport> searchGoodsUpdateReportList(QueryModel<GoodsUpdateReportExt> qm, int type,String accountPk) {
		PageModel<GoodsUpdateReport> pm = new PageModel<GoodsUpdateReport>();
		Query query = new Query();
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

		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		if (type != 2) {
			int counts = (int) mongoTemplate.count(query, GoodsUpdateReport.class);
			query.skip(qm.getStart()).limit(qm.getLimit());
			pm.setTotalCount(counts);
		}
		List<GoodsUpdateReport> list  = mongoTemplate.find(query, GoodsUpdateReport.class);
		if (list.size()>0) {
		    for (GoodsUpdateReport g : list) {
		        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_GOODSUPDATE_COL_SUPSTORENAME)) {
		            g.setName(CommonUtil.hideCompanyName( g.getName()));
                }
		        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_GOODSUPDATE_COL_ACCOUNTNAME)) {
                    g.setAccountName(CommonUtil.hideContacts( g.getAccountName()));
                }
            }
        }
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<SalemanSaleDetailReport> searchSalemanSaleList(QueryModel<SalemanSaleDetailReportExt> qm,
			int type,String accountPk) {
		PageModel<SalemanSaleDetailReport> pm = new PageModel<SalemanSaleDetailReport>();
		Criteria criteria = new Criteria();
		if (qm.getEntity().getStartTime() != null && !qm.getEntity().getStartTime().equals("")) {
			if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
				criteria = Criteria.where("date").gte(qm.getEntity().getStartTime()).lte(qm.getEntity().getEndTime());
			} else {
				criteria = Criteria.where("date").gte(qm.getEntity().getStartTime());
			}
		} else if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
			criteria = Criteria.where("date").lte(qm.getEntity().getEndTime());
		}
		TypedAggregation<SalemanSaleDetailReport> aggregation = null;
		if (type == 1) {
			aggregation = Aggregation.newAggregation(SalemanSaleDetailReport.class, Aggregation.match(criteria),
					Aggregation.group("accountPk").first("accountPk").as("accountPk").first("accountName")
							.as("accountName").sum("supcompanyNum").as("supcompanyNum").sum("purcompanyNum")
							.as("purcompanyNum").sum("pPOYWeight").as("pPOYWeight").sum("pDTYWeight").as("pDTYWeight")
							.sum("pFDYWeight").as("pFDYWeight").sum("pCUTWeight").as("pCUTWeight").sum("pOtherWeight")
							.as("pOtherWeight").sum("sPOYWeight").as("sPOYWeight").sum("sDTYWeight").as("sDTYWeight")
							.sum("sFDYWeight").as("sFDYWeight").sum("sCUTWeight").as("sCUTWeight").sum("sOtherWeight")
							.as("sOtherWeight"),
					Aggregation.skip(qm.getStart()), Aggregation.limit(qm.getLimit()));
		} else if (type == 2) {
			aggregation = Aggregation.newAggregation(SalemanSaleDetailReport.class, Aggregation.match(criteria),
					Aggregation.group("accountPk").first("accountPk").as("accountPk").first("accountName")
							.as("accountName").sum("supcompanyNum").as("supcompanyNum").sum("purcompanyNum")
							.as("purcompanyNum").sum("pPOYWeight").as("pPOYWeight").sum("pDTYWeight").as("pDTYWeight")
							.sum("pFDYWeight").as("pFDYWeight").sum("pCUTWeight").as("pCUTWeight").sum("pOtherWeight")
							.as("pOtherWeight").sum("sPOYWeight").as("sPOYWeight").sum("sDTYWeight").as("sDTYWeight")
							.sum("sFDYWeight").as("sFDYWeight").sum("sCUTWeight").as("sCUTWeight").sum("sOtherWeight")
							.as("sOtherWeight"));
		}
		AggregationResults<SalemanSaleDetailReport> ar = mongoTemplate.aggregate(aggregation,
				SalemanSaleDetailReport.class);
		List<SalemanSaleDetailReport> list = ar.getMappedResults();
		if (list.size() > 0) {
			for (SalemanSaleDetailReport s : list) {
			    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SALEMANSALEDETAIL_COL_ACCOUNTNAME)) {
                    s.setAccountName(CommonUtil.hideContacts(s.getAccountName()));
                }
				s.setpPOYWeightb(new BigDecimal(s.getpPOYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setpDTYWeightb(new BigDecimal(s.getpDTYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setpFDYWeightb(new BigDecimal(s.getpFDYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setpCUTWeightb(new BigDecimal(s.getpCUTWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setpOtherWeightb(new BigDecimal(s.getpOtherWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setsPOYWeightb(new BigDecimal(s.getsPOYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setsDTYWeightb(new BigDecimal(s.getsDTYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setsFDYWeightb(new BigDecimal(s.getsFDYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setsCUTWeightb(new BigDecimal(s.getsCUTWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				s.setsOtherWeightb(new BigDecimal(s.getsOtherWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			}
		}

		if (type != 2) {
			TypedAggregation<SalemanSaleDetailReport> aggregationCount = Aggregation.newAggregation(
					SalemanSaleDetailReport.class, Aggregation.match(criteria), Aggregation.group("accountPk")
							.first("accountPk").as("accountPk").first("accountName").as("accountName"));
			AggregationResults<SalemanSaleDetailReport> arCount = mongoTemplate.aggregate(aggregationCount,
					SalemanSaleDetailReport.class);
			int counts = arCount.getMappedResults().size();
			pm.setTotalCount(counts);
		}
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<SupplierSaleDataReport> supplierSaleDataReportList(QueryModel<SupplierSaleDataReportExt> qm,
			int type,String accountPk) {
		PageModel<SupplierSaleDataReport> pm = new PageModel<SupplierSaleDataReport>();
		Criteria criteria = new Criteria();
		if (qm.getEntity().getStartTime() != null && !qm.getEntity().getStartTime().equals("")) {
			if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
				criteria = Criteria.where("date").gte(qm.getEntity().getStartTime()).lte(qm.getEntity().getEndTime());
			} else {
				criteria = Criteria.where("date").gte(qm.getEntity().getStartTime());
			}
		} else if (qm.getEntity().getEndTime() != null && !qm.getEntity().getEndTime().equals("")) {
			criteria = Criteria.where("date").lte(qm.getEntity().getEndTime());
		}
		TypedAggregation<SupplierSaleDataReport> aggregation = null;
		if (type == 1) {
			aggregation = Aggregation.newAggregation(SupplierSaleDataReport.class, Aggregation.match(criteria),
					Aggregation.sort(Sort.Direction.ASC, "pk"),
					Aggregation.group("pk").first("pk").as("pk").first("name").as("name").addToSet("accountName")
							.as("accountArry").sum("POYnum").as("POYnum").sum("POYprice").as("POYprice")
							.sum("POYweight").as("POYweight").sum("FDYnum").as("FDYnum").sum("FDYprice").as("FDYprice")
							.sum("FDYweight").as("FDYweight").sum("DTYnum").as("DTYnum").sum("DTYprice").as("DTYprice")
							.sum("DTYweight").as("DTYweight").sum("CUTnum").as("CUTnum").sum("CUTprice").as("CUTprice")
							.sum("CUTweight").as("CUTweight").sum("othernum").as("othernum").sum("otherprice")
							.as("otherprice").sum("otherweight").as("otherweight"),
					Aggregation.skip(qm.getStart()), Aggregation.limit(qm.getLimit()));
		} else if (type == 2) {
			aggregation = Aggregation.newAggregation(SupplierSaleDataReport.class, Aggregation.match(criteria),
					Aggregation.sort(Sort.Direction.ASC, "pk"),
					Aggregation.group("pk").first("pk").as("pk").first("name").as("name").addToSet("accountName")
							.as("accountArry").sum("POYnum").as("POYnum").sum("POYprice").as("POYprice")
							.sum("POYweight").as("POYweight").sum("FDYnum").as("FDYnum").sum("FDYprice").as("FDYprice")
							.sum("FDYweight").as("FDYweight").sum("DTYnum").as("DTYnum").sum("DTYprice").as("DTYprice")
							.sum("DTYweight").as("DTYweight").sum("CUTnum").as("CUTnum").sum("CUTprice").as("CUTprice")
							.sum("CUTweight").as("CUTweight").sum("othernum").as("othernum").sum("otherprice")
							.as("otherprice").sum("otherweight").as("otherweight"));
		}
		AggregationResults<SupplierSaleDataReport> ar = mongoTemplate.aggregate(aggregation,
				SupplierSaleDataReport.class);
		List<SupplierSaleDataReport> list = ar.getMappedResults();
		if (list != null && list.size() > 0) {
			TypedAggregation<SupplierSaleDataReport> aggregationTotal = Aggregation.newAggregation(
					SupplierSaleDataReport.class, Aggregation.match(criteria),
					Aggregation.group("pk").first("pk").as("pk").sum("$POYweight").as("POYweight").sum("$FDYweight")
							.as("FDYweight").sum("$DTYweight").as("DTYweight").sum("$CUTweight").as("CUTweight")
							.sum("$otherweight").as("otherweight"));
			AggregationResults<SupplierSaleDataReport> arTotel = mongoTemplate.aggregate(aggregationTotal,
					SupplierSaleDataReport.class);
			if (arTotel.getMappedResults() != null && arTotel.getMappedResults().size() > 0) {
				double POYweight = 0d;
				double FDYweight = 0d;
				double DTYweight = 0d;
				double CUTweight = 0d;
				double Otherweight = 0d;
				for (int i = 0; i < arTotel.getMappedResults().size(); i++) {
					Otherweight = Otherweight + new BigDecimal(arTotel.getMappedResults().get(i).getOtherweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue() ;
					POYweight = POYweight + new BigDecimal(arTotel.getMappedResults().get(i).getPOYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
					FDYweight = FDYweight +new BigDecimal(arTotel.getMappedResults().get(i).getFDYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue() ;
					DTYweight = DTYweight +new BigDecimal(arTotel.getMappedResults().get(i).getDTYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
					CUTweight = CUTweight +new BigDecimal(arTotel.getMappedResults().get(i).getCUTweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue() ;
				}
				for (SupplierSaleDataReport report : list) {
				  
				    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALEDATA_COL_STORENAME)) {
                        report.setName(CommonUtil.hideCompanyName(report.getName()));
                    }
				    
					report.setPOYpriceb(new BigDecimal(report.getPOYprice().toString()).setScale(2, RoundingMode.HALF_UP));
					report.setPOYweightb(new BigDecimal(report.getPOYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
					report.setFDYpriceb(new BigDecimal(report.getFDYprice().toString()).setScale(2, RoundingMode.HALF_UP));
					report.setFDYweightb(new BigDecimal(report.getFDYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
					report.setDTYpriceb(new BigDecimal(report.getDTYprice().toString()).setScale(2, RoundingMode.HALF_UP));
					report.setDTYweightb(new BigDecimal(report.getDTYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
					report.setCUTpriceb(new BigDecimal(report.getCUTprice().toString()).setScale(2, RoundingMode.HALF_UP));
					report.setCUTweightb(new BigDecimal(report.getCUTweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
					report.setOtherpriceb(new BigDecimal(report.getOtherprice().toString()).setScale(2, RoundingMode.HALF_UP));
					report.setOtherweightb(new BigDecimal(report.getOtherweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
					report.setPOYRatio(countRatio(new BigDecimal(report.getPOYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), POYweight));
					report.setFDYRatio(countRatio(new BigDecimal(report.getFDYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), FDYweight));
					report.setDTYRatio(countRatio(new BigDecimal(report.getDTYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), DTYweight));
					report.setCUTRatio(countRatio(new BigDecimal(report.getCUTweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), CUTweight));
					report.setOtherRatio(countRatio(report.getOtherweight(), Otherweight));
					
					if (report.getAccountArry().length > 0) {
					
						  if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALEDATA_COL_ACCOUNTNAME)) {
						      String str = "";
						      for (int i = 0; i < report.getAccountArry().length; i++) {
						          str = str + CommonUtil.hideContacts(report.getAccountArry()[i])+",";
						      }
		                        report.setAccountName(str.substring(0, str.length()-1));
		                    }else{
		                        String str = StringUtils.join(report.getAccountArry(), ","); // 数组转字符串(逗号分隔)(推荐)
	                              report.setAccountName(str);
		                    }
					}
				}
			}
		}
		if (type != 2) {
			TypedAggregation<SupplierSaleDataReport> aggregationCount = Aggregation.newAggregation(
					SupplierSaleDataReport.class, Aggregation.match(criteria),
					Aggregation.group("pk").first("pk").as("pk"));
			AggregationResults<SalemanSaleDetailReport> arCount = mongoTemplate.aggregate(aggregationCount,
					SalemanSaleDetailReport.class);
			int counts = arCount.getMappedResults().size();
			pm.setTotalCount(counts);
		}
		pm.setDataList(list);
		return pm;
	}

	
	/**
	 * 计算销售两占比
	 * 
	 * @param double1
	 * @param double2
	 * @return
	 */
	private String countRatio(Double double1, Double double2) {
		if (double2 == 0) {
			return "0%";
		} else {
			Double result = ArithUtil.mul(ArithUtil.div(double1, double2, 4), 100);
			return result.toString() + "%";
		}

	}

}
