package cn.cf.service.enconmics.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import cn.cf.dao.B2bLoanNumberExtDao;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.BankApproveAmountExport;
import cn.cf.entity.EcnoProductUseExport;
import cn.cf.entity.EconomicsProductOrder;
import cn.cf.entity.RepayInfo;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconReportFacadeService;
import cn.cf.service.enconmics.EconReportProductUseService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.service.enconmics.EconomicsCreditService;
import cn.cf.service.enconmics.EconomicsOrdersService;
import cn.cf.util.ArithUtil;

@Service
public class EconReportProductUseServiceImpl implements EconReportProductUseService {
	
	private final static Logger logger = LoggerFactory.getLogger(EconReportProductUseServiceImpl.class);

	@Autowired
	private EconReportFacadeService econReportFacadeService;

	@Autowired
	private EconomicsBankService economicsBankService;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private EconomicsCreditService economicsCreditService;
	
	@Autowired
	private EconomicsOrdersService  economicsOrdersService;
	
	@Autowired
	private B2bLoanNumberExtDao  loanNumberExtDao;
	

	/**
	 * 查询昨日新增的提款
	 */
	@Override
	public List<EcnoProductUseExport> searchEcnoProductUse() {
		List<EcnoProductUseExport> list = new ArrayList<EcnoProductUseExport>();
		List<B2bEconomicsBankDto> banksList = economicsBankService.searchAllList();// 所有银行
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanStartTime", CommonUtil.yesterDay());//放款时间
		List<EconomicsProductOrder> orderList =  economicsOrdersService.searchEconomicsOrders(map);// 昨日提款情况按银行排序

		List<RepayInfo> repayList = getRepayRecord();// 昨日还款情况
		map = new HashMap<String, Object>();
		map.put("flag", Constants.ONE);
		// 昨日提款
		if (banksList != null && banksList.size() > 0) {
			for (B2bEconomicsBankDto banks : banksList) {

				EcnoProductUseExport dto = new EcnoProductUseExport();
				dto.setFlag(Constants.ONE);
				dto.setYear(CommonUtil.yesterday(1).toString());
				dto.setMonth(CommonUtil.yesterday(2));
				dto.setBankPk(banks.getPk());
				// 新增提款
				if (orderList != null && orderList.size() > 0) {
					for (EconomicsProductOrder epo : orderList) {
						if (epo.getBankPk().equals(banks.getPk())) {		
							
							logger.error("新增提款=================="+JsonUtils.convertToString(epo));
							econReportFacadeService.setNewEconomicsProduct(epo, dto);
						}
					}
				}
				
				map.put("bankPk", banks.getPk());
				EcnoProductUseExport pUseExport = new  EcnoProductUseExport();
				
				pUseExport = getEcnoProductUseExport(map);// 芒果昨日记录
			
				
				// 累计提款 
				if (pUseExport != null && !pUseExport.equals("")) {
					econReportFacadeService.setAccumteEconomicsProduct(pUseExport, dto);
				}
				// 新增还款
				if (repayList != null && repayList.size() > 0) {
					for (RepayInfo repayInfo : repayList) {
						if (repayInfo.getBankPk().equals(banks.getPk())) {
							econReportFacadeService.setRepayEconomicsProduct(repayInfo, dto);
						}
					}
				}
				
				// 累计还款
				if (pUseExport != null && !pUseExport.equals("")) {
					econReportFacadeService.setAccumteRepayEconomicsProduct(pUseExport, dto);
				}
				// 当前余额
				econReportFacadeService.setNowAvailableAmount(banks.getPk(), dto);// 根据银行划分可用额度
				list.add(dto);
			}
		}
		return list;

	}

	private List<RepayInfo> getRepayRecord() {
		List<RepayInfo> repayList = new ArrayList<RepayInfo>();
		Criteria criteria = new Criteria();
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyyMMdd");
		criteria.orOperator(Criteria.where("createTime").is(sd1.format(DateUtils.addDays(date, -1))),Criteria.where("createTime").is(sd.format(DateUtils.addDays(date, -1))));
		criteria.andOperator(Criteria.where("status").is(1));
		List<B2bRepaymentRecord> repaymentRecordsList = mongoTemplate.find(new Query(criteria).with(new Sort(new Order(Direction.DESC, "companyPk"))), B2bRepaymentRecord.class);
		if (repaymentRecordsList != null && repaymentRecordsList.size() > 0) {
			String companyPk = repaymentRecordsList.get(0).getCompanyPk();
			B2bCreditDto creditDto = economicsCreditService.getByCompanyPk(companyPk);
			for (B2bRepaymentRecord rr : repaymentRecordsList) {
				//获取化纤的订单
				B2bLoanNumberDto loanDto =	loanNumberExtDao.getByOrderNumber(rr.getOrderNumber());
				if (loanDto!=null&&loanDto.getBankPk()!=null&&!loanDto.getBankPk().equals("")) {
					RepayInfo repayInfo = new RepayInfo();
					if (!companyPk.equals(rr.getCompanyPk())) {
						creditDto = economicsCreditService.getByCompanyPk(rr.getCompanyPk());
					}
					if (creditDto!=null&& creditDto.getSource()!=null &&!creditDto.getSource().equals("")) {
						repayInfo.setCompanyPk(rr.getCompanyPk());
						repayInfo.setBankPk(loanDto.getBankPk());
						repayInfo.setSource(Integer.parseInt(creditDto.getSource()));
						repayInfo.setAmount(rr.getAmount());
						repayInfo.setProductType(loanDto.getEconomicsGoodsType());
						repayList.add(repayInfo);
					}
				}
			}
		}
		return repayList;
	}

	private EcnoProductUseExport getEcnoProductUseExport(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		return mongoTemplate.findOne(query, EcnoProductUseExport.class);
	}

	@Override
	public void UpdateWeekData(List<EcnoProductUseExport> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", Constants.TWO);
		
		EcnoProductUseExport nowWeek = new EcnoProductUseExport();
		nowWeek.setFlag(Constants.TWO);
		nowWeek.setYear(CommonUtil.yesterday(1).toString());
		nowWeek.setMonth(CommonUtil.yesterday(2));
		if (list.size()>0) {
			for (EcnoProductUseExport ep : list) {
				nowWeek.setBankPk(ep.getBankPk());
				Query query =searchEcProductMongo(ep.getBankPk(),Constants.TWO);//查询某银行当周数据
				EcnoProductUseExport nowece = mongoTemplate.findOne(query,EcnoProductUseExport.class);
				
				Query query1 =searchEcProductMongo(ep.getBankPk(),Constants.THREE);// 某银行上周数据
				EcnoProductUseExport lastWeek = mongoTemplate.findOne(query1,EcnoProductUseExport.class);

				// 叠加累计数量
				if (nowece != null && !nowece.equals("")) {
					nowece.setYear(CommonUtil.yesterday(1).toString());
					nowece.setMonth(CommonUtil.yesterday(2));
					econReportFacadeService.accumulativeEcnoProductUse(nowece, ep,1);//1：全部 ；2累计
				}
				
				mongoTemplate.remove(query, EcnoProductUseExport.class);
				//是否为周一
				if (CommonUtil.getWeekOfDate(new Date()).equals("星期一")) {
					// 查询本周数据，更新到上周数据，清空本周数据1:昨日 ;2:上周;3:当周;4:年
					if (lastWeek!=null&&!lastWeek.equals("")) {
						mongoTemplate.remove(query1, EcnoProductUseExport.class);
					}
					
					ep.setFlag(Constants.THREE);
					if (nowece != null && !nowece.equals("") ) {
						nowece.setFlag(Constants.THREE);
					}
					mongoTemplate.insert(nowece != null && !nowece.equals("") ? nowece : ep);
					
					//插入本周余额
					econReportFacadeService.accumulativeEcnoProductUse(nowWeek, nowece != null && !nowece.equals("") ? nowece : ep ,2);//1：全部 ；2当前余额
					mongoTemplate.insert(nowWeek);
				} else {
					if (lastWeek==null||lastWeek.equals("")) {
						nowWeek.setFlag(Constants.THREE);
						mongoTemplate.insert(nowWeek);
					}else{
						Update update = Update.update("month", CommonUtil.yesterday(2)).set("year",CommonUtil.yesterday(1).toString());
						mongoTemplate.updateFirst(query1, update,EcnoProductUseExport.class);
					}
					//如果当周数据为空
					ep.setFlag(Constants.TWO);
					if (nowece != null && !nowece.equals("") ) {
						nowece.setFlag(Constants.TWO);
					}
					
					// 更新某银行当周的数据
					mongoTemplate.insert(nowece != null && !nowece.equals("") ? nowece : ep);
				}
			}
		}
	}

	private Query searchEcProductMongo(String bankPk,Integer flag ) {
		Query query = new Query();
		query.addCriteria(Criteria.where("flag").is(flag));
		query.addCriteria(Criteria.where("bankPk").is(bankPk));
		return query;
	}

	@Override
	public void UpdateMonthData(List<EcnoProductUseExport> list) {
		if (list!=null && list.size()>0) {
			for (EcnoProductUseExport epd : list) {
				epd.setFlag(Constants.FOUR);
				Criteria criteria = searchMonthEcnoProductUse(epd, Constants.FOUR);
				EcnoProductUseExport nowMonth = mongoTemplate.findOne(new Query(criteria),EcnoProductUseExport.class);
				// 是不是新增
				if (nowMonth == null || nowMonth.equals("")) {
					mongoTemplate.insert(epd);
				} else {
					econReportFacadeService.accumulativeEcnoProductUse(nowMonth, epd,1);
					mongoTemplate.remove(new Query(criteria), EcnoProductUseExport.class);
					mongoTemplate.insert(nowMonth);
				}
			}
		}
	}

	private Criteria searchMonthEcnoProductUse(EcnoProductUseExport epd, int flag) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("year").is(epd.getYear()), Criteria.where("bankPk").is(epd.getBankPk()),
				Criteria.where("month").is(CommonUtil.yesterday(2)), Criteria.where("flag").is(flag));
		return criteria;
	}



	
	@Override
	public List<EcnoProductUseExport> econProductUse_data(String bankPk, String year) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("bankPk", bankPk);
		map.put("year", year);
		return  searchEcnoProductUseExportList(map);
	}

	private List<EcnoProductUseExport> searchEcnoProductUseExportList(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		query.with(new Sort(new Order(Direction.ASC, "flag"),new Order(Direction.ASC, "month")));
		List<EcnoProductUseExport>  list =	mongoTemplate.find(query, EcnoProductUseExport.class);
		EcnoProductUseExport  export = new EcnoProductUseExport();
		if (list!=null&&list.size()>0) {
			//补全缺少的月份
			setEcnoProductUseMonth(list,map.get("year").toString());
			for (EcnoProductUseExport e : list) {
				e.setbTAmount(new BigDecimal(Double.parseDouble(e.getsBTAmount())+Double.parseDouble(e.getxBTAmount())+
						Double.parseDouble(e.getyBTAmount())+Double.parseDouble(e.getqBTAmount())+Double.parseDouble(e.getpBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setdAmount(new BigDecimal(Double.parseDouble(e.getsDAmount())+Double.parseDouble(e.getxDAmount())+
						Double.parseDouble(e.getyDAmount())+Double.parseDouble(e.getqDAmount())+Double.parseDouble(e.getpDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setbTTotalAmount(new BigDecimal(Double.parseDouble(e.getsBTTotalAmount())+Double.parseDouble(e.getxBTTotalAmount())+
						Double.parseDouble(e.getyBTTotalAmount())+Double.parseDouble(e.getqBTTotalAmount())+Double.parseDouble(e.getpBTTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setdTotalAmount(new BigDecimal(Double.parseDouble(e.getsDTotalAmount())+Double.parseDouble(e.getxDTotalAmount())+
						Double.parseDouble(e.getyDTotalAmount())+Double.parseDouble(e.getqDTotalAmount())+Double.parseDouble(e.getpDTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setPayBTAmount(new BigDecimal(Double.parseDouble(e.getsPayBTAmount())+Double.parseDouble(e.getxPayBTAmount())+
						Double.parseDouble(e.getyPayBTAmount())+Double.parseDouble(e.getqPayBTAmount())+Double.parseDouble(e.getpPayBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setPayDAmount(new BigDecimal(Double.parseDouble(e.getsPayDAmount())+Double.parseDouble(e.getxPayDAmount())+
						Double.parseDouble(e.getyPayDAmount())+Double.parseDouble(e.getqPayDAmount())+Double.parseDouble(e.getpPayDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setPayBTTotalAmount(new BigDecimal(Double.parseDouble(e.getsPayBTTotalAmount())+Double.parseDouble(e.getxPayBTTotalAmount())+
						Double.parseDouble(e.getyPayBTTotalAmount())+Double.parseDouble(e.getqPayBTTotalAmount())+Double.parseDouble(e.getpPayBTTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setPayDTotalAmount(new BigDecimal(Double.parseDouble(e.getsPayDTotalAmount())+Double.parseDouble(e.getxPayDTotalAmount())+
						Double.parseDouble(e.getyPayDTotalAmount())+Double.parseDouble(e.getqPayDTotalAmount())+Double.parseDouble(e.getpPayDTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setNowBTAmount(new BigDecimal(Double.parseDouble(e.getsNowBTAmount())+Double.parseDouble(e.getxNowBTAmount())+
						Double.parseDouble(e.getyNowBTAmount())+Double.parseDouble(e.getqNowBTAmount())+Double.parseDouble(e.getpNowBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
				e.setNowDAmount(new BigDecimal(Double.parseDouble(e.getsNowDAmount())
						+ Double.parseDouble(e.getxNowDAmount())+Double.parseDouble(e.getyNowDAmount())+
						Double.parseDouble(e.getqNowDAmount())+Double.parseDouble(e.getpNowDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
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
					setEcnoProductUseExportTotal(export,e);//月的合计
				}
			}
				list.add(export);
		}else {
			setEcnoProductUseMonth(list,map.get("year").toString());
		}
		//先按flag排序，再按月份排序
		Collections.sort(list);
		return  list;
	}

	private void setEcnoProductUseExportTotal(EcnoProductUseExport export, EcnoProductUseExport e) {
		export.setTitle("合计");
		export.setFlag(5);
		export.setsBTAmount(ArithUtil.addString(export.getsBTAmount(),e.getsBTAmount()));
		export.setxBTAmount(ArithUtil.addString(export.getxBTAmount(),e.getxBTAmount()));
		export.setyBTAmount(ArithUtil.addString(export.getyBTAmount(),e.getyBTAmount()));
		export.setqBTAmount(ArithUtil.addString(export.getqBTAmount(),e.getqBTAmount()));
		export.setpBTAmount(ArithUtil.addString(export.getpBTAmount(),e.getpBTAmount()));
		export.setbTAmount(ArithUtil.addString(export.getbTAmount(),e.getbTAmount()));
		
		export.setsDAmount(ArithUtil.addString(export.getsDAmount(),e.getsDAmount()));
		export.setxDAmount(ArithUtil.addString(export.getxDAmount(),e.getxDAmount()));
		export.setyDAmount(ArithUtil.addString(export.getyDAmount(),e.getyDAmount()));
		export.setqDAmount(ArithUtil.addString(export.getqDAmount(),e.getqDAmount()));
		export.setpDAmount(ArithUtil.addString(export.getpDAmount(),e.getpDAmount()));
		export.setdAmount(ArithUtil.addString(export.getdAmount(),e.getdAmount()));
		
		export.setsBTTotalAmount(ArithUtil.addString(export.getsBTTotalAmount(),e.getsBTTotalAmount()));
		export.setxBTTotalAmount(ArithUtil.addString(export.getxBTTotalAmount(),e.getxBTTotalAmount()));
		export.setyBTTotalAmount(ArithUtil.addString(export.getyBTTotalAmount(),e.getyBTTotalAmount()));
		export.setqBTTotalAmount(ArithUtil.addString(export.getqBTTotalAmount(),e.getqBTTotalAmount()));
		export.setpBTTotalAmount(ArithUtil.addString(export.getpBTTotalAmount(),e.getpBTTotalAmount()));
		export.setbTTotalAmount(ArithUtil.addString(export.getbTTotalAmount(),e.getbTTotalAmount()));

		export.setsDTotalAmount(ArithUtil.addString(export.getsDTotalAmount(),e.getsDTotalAmount()));
		export.setxDTotalAmount(ArithUtil.addString(export.getxDTotalAmount(),e.getxDTotalAmount()));
		export.setyDTotalAmount(ArithUtil.addString(export.getyDTotalAmount(),e.getyDTotalAmount()));
		export.setqDTotalAmount(ArithUtil.addString(export.getqDTotalAmount(),e.getqDTotalAmount()));
		export.setpDTotalAmount(ArithUtil.addString(export.getpDTotalAmount(),e.getpDTotalAmount()));
		export.setdTotalAmount(ArithUtil.addString(export.getdTotalAmount(),e.getdTotalAmount()));
		
		export.setsPayBTAmount(ArithUtil.addString(export.getsPayBTAmount(),e.getsPayBTAmount()));
		export.setxPayBTAmount(ArithUtil.addString(export.getxPayBTAmount(),e.getxPayBTAmount()));
		export.setyPayBTAmount(ArithUtil.addString(export.getyPayBTAmount(),e.getyPayBTAmount()));
		export.setqPayBTAmount(ArithUtil.addString(export.getqPayBTAmount(),e.getqPayBTAmount()));
		export.setpPayBTAmount(ArithUtil.addString(export.getpPayBTAmount(),e.getpPayBTAmount()));
		export.setPayBTAmount(ArithUtil.addString(export.getPayBTAmount(),e.getPayBTAmount()));
		
		export.setsPayDAmount(ArithUtil.addString(export.getsPayDAmount(),e.getsPayDAmount()));
		export.setxPayDAmount(ArithUtil.addString(export.getxPayDAmount(),e.getxPayDAmount()));
		export.setyPayDAmount(ArithUtil.addString(export.getyPayDAmount(),e.getyPayDAmount()));
		export.setqPayDAmount(ArithUtil.addString(export.getqPayDAmount(),e.getqPayDAmount()));
		export.setpPayDAmount(ArithUtil.addString(export.getpPayDAmount(),e.getpPayDAmount()));
		export.setPayDAmount(ArithUtil.addString(export.getPayDAmount(),e.getPayDAmount()));

		export.setsPayBTTotalAmount(ArithUtil.addString(export.getsPayBTTotalAmount(),e.getsPayBTTotalAmount()));
		export.setxPayBTTotalAmount(ArithUtil.addString(export.getxPayBTTotalAmount(),e.getxPayBTTotalAmount()));
		export.setyPayBTTotalAmount(ArithUtil.addString(export.getyPayBTTotalAmount(),e.getyPayBTTotalAmount()));
		export.setqPayBTTotalAmount(ArithUtil.addString(export.getqPayBTTotalAmount(),e.getqPayBTTotalAmount()));
		export.setpPayBTTotalAmount(ArithUtil.addString(export.getpPayBTTotalAmount(),e.getpPayBTTotalAmount()));
		export.setPayBTTotalAmount(ArithUtil.addString(export.getPayBTTotalAmount(),e.getPayBTTotalAmount()));
		
		export.setsPayDTotalAmount(ArithUtil.addString(export.getsPayDTotalAmount(),e.getsPayDTotalAmount()));
		export.setxPayDTotalAmount(ArithUtil.addString(export.getxPayDTotalAmount(),e.getxPayDTotalAmount()));
		export.setyPayDTotalAmount(ArithUtil.addString(export.getyPayDTotalAmount(),e.getyPayDTotalAmount()));
		export.setqPayDTotalAmount(ArithUtil.addString(export.getqPayDTotalAmount(),e.getqPayDTotalAmount()));
		export.setpPayDTotalAmount(ArithUtil.addString(export.getpPayDTotalAmount(),e.getpPayDTotalAmount()));
		export.setPayDTotalAmount(ArithUtil.addString(export.getPayDTotalAmount(),e.getPayDTotalAmount()));
		
		export.setsNowBTAmount(ArithUtil.addString(export.getsNowBTAmount(),e.getsNowBTAmount()));
		export.setxNowBTAmount(ArithUtil.addString(export.getxNowBTAmount(),e.getxNowBTAmount()));
		export.setyNowBTAmount(ArithUtil.addString(export.getyNowBTAmount(),e.getyNowBTAmount()));
		export.setqNowBTAmount(ArithUtil.addString(export.getqNowBTAmount(),e.getqNowBTAmount()));
		export.setpNowBTAmount(ArithUtil.addString(export.getpNowBTAmount(),e.getpNowBTAmount()));
		export.setNowBTAmount(ArithUtil.addString(export.getNowBTAmount(),e.getNowBTAmount()));
		
		export.setsNowDAmount(ArithUtil.addString(export.getsNowDAmount(),e.getsNowDAmount()));
		export.setxNowDAmount(ArithUtil.addString(export.getxNowDAmount(),e.getxNowDAmount()));
		export.setyNowDAmount(ArithUtil.addString(export.getyNowDAmount(),e.getyNowDAmount()));
		export.setqNowDAmount(ArithUtil.addString(export.getqNowDAmount(),e.getqNowDAmount()));
		export.setpNowDAmount(ArithUtil.addString(export.getpNowDAmount(),e.getpNowDAmount()));
		export.setNowDAmount(ArithUtil.addString(export.getNowDAmount(),e.getNowDAmount()));
	}

	private void setEcnoProductUseMonth(List<EcnoProductUseExport> list, String year) {
		
		EcnoProductUseExport  newe = new EcnoProductUseExport();
		if (list!=null && list.size()>0) {//该年份有数：默认有昨日，上周，当周，当月4条排列循序的数据
			int lastMonth = list.get(list.size()-1).getMonth();
			if(list.get(0).getFlag()==4){//非当前年数据
				if (list.get(0).getMonth()>1) {
					for (int i = 1; i < list.get(0).getMonth(); i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
				if (lastMonth<12) {
					for (int i = lastMonth+1; i <=12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
			}else{
				if (list.get(3).getMonth()>1) {//从年中旬开始统计，补全之前月
					for (int i = 1; i < list.get(3).getMonth(); i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
				if (lastMonth<12) {//统计到年终，补全之月
					for (int i = lastMonth+1; i <= 12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
			}
		}else{//该年份无数据
			if (CommonUtil.yesterday(1).toString().equals(year)) {
				newe = new EcnoProductUseExport();
				newe.setFlag(1);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("昨日");
				list.add(newe);
				newe = new EcnoProductUseExport();
				newe.setFlag(2);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("当周");
				list.add(newe);
				newe = new EcnoProductUseExport();
				newe.setFlag(3);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("上周");
				list.add(newe);
			}
			for (int i = 1; i <= 12; i++) {
				newe = new EcnoProductUseExport();
				newe.setFlag(4);
				newe.setMonth(i);
				newe.setTitle(i+"月");
				list.add(newe);
			}
			newe = new EcnoProductUseExport();
			newe.setTitle("合计");
			newe.setFlag(5);
			list.add(newe);
		}
	}

}
