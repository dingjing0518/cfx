package cn.cf.service.enconmics.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.entity.BankApproveAmountExport;
import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.service.enconmics.EconReportBankApproveAmountService;
import cn.cf.service.enconmics.EconReportFacadeService;
import cn.cf.service.enconmics.EconomicsBankCompanyService;
import cn.cf.util.ArithUtil;

@Service
public class EconReportBankApproveAmountServiceImpl implements EconReportBankApproveAmountService {
	
	@Autowired
	private EconomicsBankCompanyService economicsBankCompanyService;
	
	@Autowired 
	private EconReportFacadeService econReportFacadeService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	

  
	@Override
	public List<B2bEconomicsBankCompanyExtDto> searchBankApproveAmount() {
		// 取昨天的开始授信维额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creditStartTime",CommonUtil.yesterDay());// 昨天
		return economicsBankCompanyService.searchBankApproveAmount(map);
	}

	@Override
	public List<BankApproveAmountExport> getYesterdayBankApproveAmount(List<B2bEconomicsBankCompanyExtDto> list) {
		List<BankApproveAmountExport> bamList = new ArrayList<BankApproveAmountExport>();
		// 添加新增额度
		if (list != null && list.size() > 0) {
			getApproveAmount(list, bamList, Constants.ONE);
		}
		// 计算昨日的有效额度
		econReportFacadeService.countApproveAmountYesterDay(bamList);
		return bamList;
	}
	
	
	private void getApproveAmount(List<B2bEconomicsBankCompanyExtDto> list, List<BankApproveAmountExport> bamList,
			int flag) {
		BankApproveAmountExport nowBam = new BankApproveAmountExport();
		String bank = list.get(0).getBankPk();
		for (int i = 0; i < list.size(); i++) {
			if (bank.equals(list.get(i).getBankPk())) {
				nowBam.setFlag(flag);
				nowBam.setYear(CommonUtil.yesterday(1).toString());
				nowBam.setMonth(CommonUtil.yesterday(2));
				nowBam.setBankPk(list.get(i).getBankPk());
				econReportFacadeService.setBankApproveAmount(list.get(i), nowBam);
			} else {
				bamList.add(nowBam);
				nowBam = new BankApproveAmountExport();
				nowBam.setFlag(flag);
				nowBam.setYear(CommonUtil.yesterday(1).toString());
				nowBam.setMonth(CommonUtil.yesterday(2));
				nowBam.setBankPk(list.get(i).getBankPk());
				econReportFacadeService.setBankApproveAmount(list.get(i), nowBam);
			}
			bank = list.get(i).getBankPk();
		}
		bamList.add(nowBam);
	}
	
	
	/**
	 * 更新本周数据:当前日期是周一，删除本周数据，更新上周数据；非周一，更新本周数据
	 */
	@Override
	public void UpdateWeekData(List<BankApproveAmountExport> bamList) {
		BankApproveAmountExport nowWeek = new BankApproveAmountExport();
		nowWeek.setYear(CommonUtil.yesterday(1).toString());
		nowWeek.setMonth(CommonUtil.yesterday(2));
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (bamList != null && bamList.size() > 0) {
			for (BankApproveAmountExport bam : bamList) {
				nowWeek.setBankPk(bam.getBankPk());
				
				// 某银行当周数据
				map.put("bankPk", bam.getBankPk());
				map.put("flag", Constants.TWO);
				Query thisquery = getQueryByMap(map);
				BankApproveAmountExport nowBam = mongoTemplate.findOne(thisquery,BankApproveAmountExport.class);
				
				// 某银行上周数据
				map.put("flag", Constants.THREE);
				Query lastquery = getQueryByMap(map);
				BankApproveAmountExport lastBam = mongoTemplate.findOne(lastquery,BankApproveAmountExport.class);
				
				// 叠加审批数量将昨日的数据加到当周
				if (nowBam == null || nowBam.equals("")) {
					nowBam= new BankApproveAmountExport();
					nowBam.setYear(CommonUtil.yesterday(1).toString());
					nowBam.setMonth(CommonUtil.yesterday(2));
					nowBam.setBankPk(bam.getBankPk());
					nowBam.setFlag(Constants.TWO);
				}else{
					nowBam.setYear(CommonUtil.yesterday(1).toString());
					nowBam.setMonth(CommonUtil.yesterday(2));
				}
				econReportFacadeService.accumulativeBankApproveAmount(nowBam, bam);
				mongoTemplate.remove(thisquery, BankApproveAmountExport.class);
				
				if (CommonUtil.getWeekOfDate(new Date()).equals("星期一")) {
					// 查询本周数据，更新到上周数据，清空上周数据 
					if (lastBam!=null&&!lastBam.equals("")) {
						mongoTemplate.remove(lastquery, BankApproveAmountExport.class);
					}
					nowBam.setFlag(Constants.THREE);
					mongoTemplate.insert(nowBam);
					
					//本周数据初始化
					nowWeek.setFlag(Constants.TWO);
					mongoTemplate.insert(nowWeek);
					
				} else {// 更新某银行当周的数据
					if (lastBam==null||lastBam.equals("")) {
						nowWeek.setFlag(Constants.THREE);
						mongoTemplate.insert(nowWeek);
					}else{//更新上周数据所属的年月
						Update update = Update.update("month", CommonUtil.yesterday(2)).set("year",CommonUtil.yesterday(1).toString());
						mongoTemplate.updateFirst(lastquery, update,BankApproveAmountExport.class);
					}
					
					nowBam.setFlag(Constants.TWO);
					mongoTemplate.insert(nowBam);
				}
			}
		} 
	}


	/**
	 * 按月更新银行的授信金额 每月的21号更新上个月的授信金额 （上月21至当月20号）
	 */
	@Override
	public void UpdateMonthData() {
		if (CommonUtil.yesterday(Constants.THREE) == 20) {
			List<BankApproveAmountExport> bamList = new ArrayList<BankApproveAmountExport>();
			// 获取上月的新增授信额度，按银行排序
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startTime", CommonUtil.lastMonthDay());// 上个月21号// 上个月21号
			map.put("endTime", CommonUtil.yesterDay());// 昨天（当月的20号）
			List<B2bEconomicsBankCompanyExtDto> list = economicsBankCompanyService.searchAmountByMonth(map);
			if (list != null && list.size() > 0) {
				getApproveAmount(list, bamList, Constants.FOUR);
			}
			econReportFacadeService.countApproveAmountMonth(bamList);
			mongoTemplate.insertAll(bamList);
		}
	}
	
	private Query getQueryByMap(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		return query;
	}

	@Override
	public List<BankApproveAmountExport> bankApproveAmount_data(String bankPk, String year) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("bankPk", bankPk);
		map.put("year", year);
		return  searchEconCustomerApproveList(map);
	}

	private List<BankApproveAmountExport> searchEconCustomerApproveList(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		query.with(new Sort(new Order(Direction.ASC, "flag"),new Order(Direction.ASC, "month")));
		List<BankApproveAmountExport>  list =	mongoTemplate.find(query, BankApproveAmountExport.class);
		BankApproveAmountExport  export = new BankApproveAmountExport();
		if (list!=null&&list.size()>0) {
			//补全缺少的月份
			setBankApproveAmountMonth(list,map.get("year").toString());
				for (BankApproveAmountExport e : list) {
					e.setBTAmount(new BigDecimal(Double.parseDouble(e.getsBTAmount())+Double.parseDouble(e.getxBTAmount())+Double.parseDouble(e.getyBTAmount())+Double.parseDouble(e.getqBTAmount())+Double.parseDouble(e.getpBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
					e.setDAmount(new BigDecimal(Double.parseDouble(e.getsDAmount())+Double.parseDouble(e.getxDAmount())+Double.parseDouble(e.getyDAmount())+Double.parseDouble(e.getqDAmount())+Double.parseDouble(e.getpDAmount())+Double.parseDouble(e.getqDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
					e.setBTTotalAmount(new BigDecimal(Double.parseDouble(e.getsBTTotalAmount())+Double.parseDouble(e.getxBTTotalAmount())+Double.parseDouble(e.getyBTTotalAmount())+Double.parseDouble(e.getpBTTotalAmount())+Double.parseDouble(e.getqBTTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
					e.setDTotalAmount(new BigDecimal(Double.parseDouble(e.getsDTotalAmount())+Double.parseDouble(e.getxDTotalAmount())+Double.parseDouble(e.getyDTotalAmount())+Double.parseDouble(e.getpDTotalAmount())+Double.parseDouble(e.getqDTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
					if (e.getFlag()==1) {
						e.setTitle("昨日");
					}
					if (e.getFlag()==2) {
						e.setTitle("当周");
					}
					if (e.getFlag()==3) {
						e.setTitle("上周");
					}
					if (e.getFlag()==4) {
						e.setTitle(e.getMonth()+"月");
						setBankApproveAmountExportTotal(export,e);//月的合计
					}
				}
				list.add(export);
		}else {
			setBankApproveAmountMonth(list,map.get("year").toString());
		}
		//先按flag排序，再按月份排序
		Collections.sort(list);
		return  list;
	}

	private void setBankApproveAmountExportTotal(BankApproveAmountExport export, BankApproveAmountExport e) {
		export.setTitle("合计");
		export.setFlag(5);
		export.setsBTAmount(ArithUtil.addString(export.getsBTAmount(),e.getsBTAmount()));
		export.setxBTAmount(ArithUtil.addString(export.getxBTAmount(),e.getxBTAmount()));
		export.setyBTAmount(ArithUtil.addString(export.getyBTAmount(),e.getyBTAmount()));
		export.setqBTAmount(ArithUtil.addString(export.getqBTAmount(),e.getqBTAmount()));
		export.setpBTAmount(ArithUtil.addString(export.getpBTAmount(),e.getpBTAmount()));
		export.setBTAmount(ArithUtil.addString(export.getBTAmount(),e.getBTAmount()));
		
		export.setsDAmount(ArithUtil.addString(export.getsDAmount(),e.getsDAmount()));
		export.setxDAmount(ArithUtil.addString(export.getxDAmount(),e.getxDAmount()));
		export.setyDAmount(ArithUtil.addString(export.getyDAmount(),e.getyDAmount()));
		export.setqDAmount(ArithUtil.addString(export.getqDAmount(),e.getqDAmount()));
		export.setpDAmount(ArithUtil.addString(export.getpDAmount(),e.getpDAmount()));
		export.setDAmount(ArithUtil.addString(export.getDAmount(),e.getDAmount()));
		
		export.setsBTTotalAmount(ArithUtil.addString(export.getsBTTotalAmount(),e.getsBTTotalAmount()));
		export.setxBTTotalAmount(ArithUtil.addString(export.getxBTTotalAmount(),e.getxBTTotalAmount()));
		export.setyBTTotalAmount(ArithUtil.addString(export.getyBTTotalAmount(),e.getyBTTotalAmount()));
		export.setqBTTotalAmount(ArithUtil.addString(export.getqBTTotalAmount(),e.getqBTTotalAmount()));
		export.setpBTTotalAmount(ArithUtil.addString(export.getpBTTotalAmount(),e.getpBTTotalAmount()));
		export.setBTTotalAmount(ArithUtil.addString(export.getBTTotalAmount(),e.getBTTotalAmount()));
		
		export.setsDTotalAmount(ArithUtil.addString(export.getsDTotalAmount(),e.getsDTotalAmount()));
		export.setxDTotalAmount(ArithUtil.addString(export.getxDTotalAmount(),e.getxDTotalAmount()));
		export.setyDTotalAmount(ArithUtil.addString(export.getyDTotalAmount(),e.getyDTotalAmount()));
		export.setqDTotalAmount(ArithUtil.addString(export.getqDTotalAmount(),e.getqDTotalAmount()));
		export.setpDTotalAmount(ArithUtil.addString(export.getpDTotalAmount(),e.getpDTotalAmount()));
		export.setDTotalAmount(ArithUtil.addString(export.getDTotalAmount(),e.getDTotalAmount()));
	}

	private void setBankApproveAmountMonth(List<BankApproveAmountExport> list, String year) {
		
		BankApproveAmountExport  newe = new BankApproveAmountExport();
		if (list!=null && list.size()>0) {
			int lastMonth = list.get(list.size()-1).getMonth();
			if(list.get(0).getFlag()==4){//非当前年数据
				if (list.get(0).getMonth()>1) {
					for (int i = 1; i < list.get(0).getMonth(); i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new BankApproveAmountExport();
					}
				}
				if (lastMonth<12) {
					for (int i = lastMonth+1; i <=12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new BankApproveAmountExport();
					}
				}
			}else{
				//该年份有数：默认有昨日，上周，当周，本月4条排列循序的数据
				if (list.size()==3) {//还未执行21的月
					for (int i = 1; i <= 12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new BankApproveAmountExport();
					}
				}else{
					if (list.get(3).getMonth()>1) {//从年中旬开始统计，补全之前月
						for (int i = 1; i < list.get(3).getMonth(); i++) {
							newe.setFlag(4);
							newe.setMonth(i);
							list.add(newe);
							newe = new BankApproveAmountExport();
						}
					}
					if (lastMonth<12) {//统计到年终，补全之月
						for (int i = lastMonth+1; i <= 12; i++) {
							newe.setFlag(4);
							newe.setMonth(i);
							list.add(newe);
							newe = new BankApproveAmountExport();
						}
					}
				}
			}
		}else{//该年份无数据
			if (CommonUtil.yesterday(1).toString().equals(year)) {
				newe = new BankApproveAmountExport();
				newe.setFlag(1);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("昨日");
				list.add(newe);
				newe = new BankApproveAmountExport();
				newe.setFlag(2);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("当周");
				list.add(newe);
				newe = new BankApproveAmountExport();
				newe.setFlag(3);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("上周");
				list.add(newe);
			}
			for (int i = 1; i <= 12; i++) {
				newe = new BankApproveAmountExport();
				newe.setFlag(4);
				newe.setMonth(i);
				newe.setTitle(i+"月");
				list.add(newe);
			}
			newe = new BankApproveAmountExport();
			newe.setTitle("合计");
			newe.setFlag(5);
			list.add(newe);
		}
	}


}
