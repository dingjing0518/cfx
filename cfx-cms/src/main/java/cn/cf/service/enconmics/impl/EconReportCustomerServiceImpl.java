package cn.cf.service.enconmics.impl;

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
import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.B2bEconomicsCustomerGoodsExtDto;
import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.SysExcelStore;
import cn.cf.service.enconmics.EconReportCustomerService;
import cn.cf.service.enconmics.EconReportFacadeService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import net.sf.json.JSONArray;

@Service
public class EconReportCustomerServiceImpl implements EconReportCustomerService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private EconReportFacadeService econReportFacadeService;

	@Autowired
	private EconomicsBankService economicsBankService;

	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;

	@Autowired
	private B2bEconomicsBankExtDao b2bEconomicsBankExtDao;

	@Override
	public List<EconCustomerMongo> getApproveBeforeDay() {
		Date Yesterday = DateUtil.dateBeforeNowDay(1, 0, 0, 0);// 昨天
		Date now = DateUtil.dateBeforeNowDay(0, 23, 0, 0);// 今天
		Query query = new Query();
		query.addCriteria(Criteria.where("approveTime").gte(Yesterday).lt(now));

		return mongoTemplate.find(query, EconCustomerMongo.class);
	}

	@Override
	public List<EconCustomerApproveExport> getYesterdayCustomerApprove(List<EconCustomerMongo> list) {
	
		String bankALL = "";
		List<B2bEconomicsCustomerExtDto> customerExtDtos = new ArrayList<B2bEconomicsCustomerExtDto>();
		if (list.size() > 0) {
			for (EconCustomerMongo mongo : list) {
				if (mongo.getContent() != null || !"".equals(mongo.getContent())) {
					JSONArray array = JSONArray.fromObject(mongo.getContent());// 解析申请产品.相同银行，相同来源的
					List<B2bEconomicsCustomerGoodsExtDto> goodsDtos = new ArrayList<B2bEconomicsCustomerGoodsExtDto>();
					B2bEconomicsCustomerGoodsExtDto jsonObj = null;
					for (int i = 0; i < array.size(); i++) {
						jsonObj = JsonUtils.toBean(array.getJSONObject(i).toString(),
								B2bEconomicsCustomerGoodsExtDto.class); 
						goodsDtos.add(jsonObj);
					}
					Collections.sort(goodsDtos);
					B2bEconomicsCustomerExtDto customerDto = new B2bEconomicsCustomerExtDto();
					String bankPk = "";
					for (int i = 0; i < goodsDtos.size(); i++) {
						if(goodsDtos.get(i).getBankPk()!=null&&!goodsDtos.get(i).getBankPk().equals("")){
							if (bankPk.equals("")) {
								bankPk = goodsDtos.get(i).getBankPk();
								bankALL = bankALL + bankPk + ",";
								customerDto.setSource(mongo.getSource());
								customerDto.setAuditStatus(mongo.getAuditStatus());
								customerDto.setBankPk(goodsDtos.get(i).getBankPk());
								customerDto.setProductType(goodsDtos.get(i).getGoodsType().toString());
							} else {
								if (!bankPk.equals("") && !bankPk .equals(goodsDtos.get(i).getBankPk()) ) {// 不同同一家银行
									customerExtDtos.add(customerDto);
									customerDto = new B2bEconomicsCustomerExtDto();
									bankPk = goodsDtos.get(i).getBankPk();
									bankALL = bankALL + bankPk + ",";
									customerDto.setSource(mongo.getSource());
									customerDto.setAuditStatus(mongo.getAuditStatus());
									customerDto.setBankPk(goodsDtos.get(i).getBankPk());
									customerDto.setProductType(goodsDtos.get(i).getGoodsType().toString());
									
								} else {// 同一家银行
									customerDto.setProductType(customerDto.getProductType() + ","
											+ goodsDtos.get(i).getGoodsType().toString());
								}
							}
						}
					}
					customerExtDtos.add(customerDto);
					
				}
			}
		}
		Collections.sort(customerExtDtos);
		List<EconCustomerApproveExport> exports = econReportFacadeService.setCustomerApproveStatus(customerExtDtos);
		List<B2bEconomicsBankDto> banksList = economicsBankService.searchAllList();// 所有银行
		for (B2bEconomicsBankDto b : banksList) {
			if (bankALL.equals("") || !bankALL.contains(b.getPk())) {
				EconCustomerApproveExport ece = new EconCustomerApproveExport();
				ece.setFlag(Constants.ONE);
				ece.setYear(CommonUtil.yesterday(1).toString());
				ece.setMonth(CommonUtil.yesterday(2));
				ece.setBankPk(b.getPk());
				exports.add(ece);
			}
		}

		return exports;
	}

	/**
	 * 更新本周数据:当前日期是周一，删除本周数据，更新上周数据；非周一，更新本周数据
	 * 
	 * @param eceList
	 */
	@Override
	public void UpdateWeekData(List<EconCustomerApproveExport> eceList) {
		Map<String, Object> map = new HashMap<String, Object>();
		EconCustomerApproveExport newWeek = new EconCustomerApproveExport();
		newWeek.setYear(CommonUtil.yesterday(1).toString());
		newWeek.setMonth(CommonUtil.yesterday(2));
		if (eceList != null && eceList.size() > 0) {
			for (EconCustomerApproveExport ece : eceList) {
				newWeek.setBankPk(ece.getBankPk());

				// 某银行当周数据
				map.put("bankPk", ece.getBankPk());
				map.put("flag", Constants.TWO);
				Query thisquery = getQueryByMap(map);
				EconCustomerApproveExport nowece = mongoTemplate.findOne(thisquery, EconCustomerApproveExport.class);

				// 某银行上周数据
				map.put("flag", Constants.THREE);
				Query lastquery = getQueryByMap(map);
				EconCustomerApproveExport lastece = mongoTemplate.findOne(lastquery, EconCustomerApproveExport.class);

				// 叠加审批数量将昨日的数据加到当周
				if (nowece == null || nowece.equals("")) {
					nowece = new EconCustomerApproveExport();
					nowece.setBankPk(ece.getBankPk());
					nowece.setFlag(Constants.TWO);
					nowece.setMonth(CommonUtil.yesterday(2));
					nowece.setYear(CommonUtil.yesterday(1).toString());
				}else{
					nowece.setFlag(Constants.TWO);
					nowece.setMonth(CommonUtil.yesterday(2));
					nowece.setYear(CommonUtil.yesterday(1).toString());
				} 
				econReportFacadeService.accumulativeCustomerApprove(nowece, ece);
				
				// 删除当周数据
				mongoTemplate.remove(thisquery, EconCustomerApproveExport.class);
				// 星期一
				if (CommonUtil.getWeekOfDate(new Date()).equals("星期一")) {
					//先删除上周数据在更新上周
					if (lastece != null && !lastece.equals("")) {
						mongoTemplate.remove(lastquery, EconCustomerApproveExport.class);
					} 
					nowece.setFlag(Constants.THREE);
					mongoTemplate.insert(nowece);
					
					// 更新当周数据
					newWeek.setFlag(Constants.TWO);
					mongoTemplate.insert(newWeek);
				} else {
					// 插入上周数据
					if (lastece == null || lastece.equals("")) {
						newWeek.setFlag(Constants.THREE);
						mongoTemplate.insert(newWeek);
					}else{//更新上周数据所属的年月
						Update update = Update.update("month", CommonUtil.yesterday(2)).set("year",CommonUtil.yesterday(1).toString());
						mongoTemplate.updateFirst(lastquery, update,EconCustomerApproveExport.class);

					}
					// 更新当周数据
					nowece.setFlag(Constants.TWO);
					mongoTemplate.insert(nowece);
				}
			}
		}

	}

	/**
	 * 某银行，每月的数据
	 */
	@Override
	public void UpdateMonthData(List<EconCustomerApproveExport> eceList) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (eceList != null && eceList.size() > 0) {
			for (EconCustomerApproveExport ece : eceList) {
				map.put("bankPk", ece.getBankPk());
				map.put("year", ece.getYear());
				map.put("month", CommonUtil.yesterday(2));
				map.put("flag", Constants.FOUR);
				Query query = getQueryByMap(map);
				EconCustomerApproveExport nowMonth = mongoTemplate.findOne(query, EconCustomerApproveExport.class);
				// 是不是新增
				if (nowMonth == null || nowMonth.equals("")) {
					ece.setFlag(Constants.FOUR);
					mongoTemplate.insert(ece);
				} else {
					econReportFacadeService.accumulativeCustomerApprove(nowMonth, ece);
					mongoTemplate.remove(query, EconCustomerApproveExport.class);
					mongoTemplate.insert(nowMonth);
				}
			}
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
	public List<EconCustomerApproveExport> approvalManage_data(String bankPk, String year) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankPk", bankPk);
		map.put("year", year);
		return searchApprovalManageList(map);
	}

	@Override
	public int saveApproveManageToOss(ReportFormsDataTypeParams params, ManageAccount account) {

		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportApprovalManageList_"+params.getUuid());

		String paramsName = "";
		if (CommonUtil.isNotEmpty(params.getYears())){
			 paramsName = "年份:"+params.getYears()+";";
		}
		if (CommonUtil.isNotEmpty(params.getBankPk())){
			B2bEconomicsBankDto dto = b2bEconomicsBankExtDao.getByPk(params.getBankPk());
			if (dto != null){
				paramsName +="银行名称:"+dto.getBankName();
			}
		}
		store.setParamsName(paramsName);
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(new Date());
		store.setName("金融中心-数据管理-审批表");
		store.setType(Constants.FOUR);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	public List<EconCustomerApproveExport> searchApprovalManageList(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		query.with(new Sort(new Order(Direction.ASC, "flag"), new Order(Direction.ASC, "month")));
		List<EconCustomerApproveExport> list = mongoTemplate.find(query, EconCustomerApproveExport.class);
		EconCustomerApproveExport export = new EconCustomerApproveExport();
		if (list != null && list.size() > 0) {
			// 补全缺少的月份
			setMonth(list);
			for (EconCustomerApproveExport e : list) {
				e.setyApproving(e.getyBTApproving() + e.getyDApproving() + e.getyBTDApproving());
				e.setyPass(e.getyBTPass() + e.getyDPass() + e.getyBTDPass());
				e.setyUnpass(e.getyBTUnpass() + e.getyDUnpass() + e.getyBTDUnpass());

				e.setpApproving(e.getpBTApproving() + e.getpDApproving() + e.getpBTDApproving());
				e.setpPass(e.getpBTPass() + e.getpDPass() + e.getpBTDPass());
				e.setpUnpass(e.getpBTUnpass() + e.getpDUnpass() + e.getpBTDUnpass());

				e.setsApproving(e.getsBTApproving() + e.getsDApproving() + e.getsBTDApproving());
				e.setsPass(e.getsBTPass() + e.getsDPass() + e.getsBTDPass());
				e.setsUnpass(e.getsBTUnpass() + e.getsDUnpass() + e.getsBTDUnpass());

				e.setxApproving(e.getxBTApproving() + e.getxDApproving() + e.getxBTDApproving());
				e.setxPass(e.getxBTPass() + e.getxDPass() + e.getxBTDPass());
				e.setxUnpass(e.getxBTUnpass() + e.getxDUnpass() + e.getxBTDUnpass());
				e.setqApproving(e.getqBTApproving() + e.getqDApproving() + e.getqBTDApproving());
				e.setqPass(e.getqBTPass() + e.getqDPass() + e.getqBTDPass());
				e.setqUnpass(e.getqBTUnpass() + e.getqDUnpass() + e.getqBTDUnpass());
				if (e.getFlag() == 1) {
					e.setTitle("昨日");
				}
				if (e.getFlag() == 2) {
					e.setTitle("当周");
				}
				if (e.getFlag() == 3) {
					e.setTitle("上周");
				}
				if (e.getFlag() == 4) {
					e.setTitle(e.getMonth() + "月");
					setExportTotal(export, e);// 月的合计
				}
			}
			list.add(export);
		} else {
			setMonth(list);
		}
		// 先按flag排序，再按月份排序
		Collections.sort(list);
		return list;
	}

	private void setMonth(List<EconCustomerApproveExport> list) {

		EconCustomerApproveExport newe = new EconCustomerApproveExport();
		if (list != null && list.size() > 0) {
			int lastMonth = list.get(list.size() - 1).getMonth();
			if (list.get(0).getFlag() == 4) {// 非当前年数据// 当前年份有数：默认有昨日，上周，当周，当月4条排列循序的数据
				if (list.get(0).getMonth() > 1) {
					for (int i = 1; i < list.get(0).getMonth(); i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i + "月");
						list.add(newe);
						newe = new EconCustomerApproveExport();
					}
				}
				if (lastMonth < 12) {
					for (int i = lastMonth + 1; i <= 12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i + "月");
						list.add(newe);
						newe = new EconCustomerApproveExport();
					}
				}
			} else {
				// 当前年份有数：默认有昨日，上周，当周，3条排列循序的数据
				if (list.get(3).getMonth()   > 1) {// 从年中旬开始统计，补全之前月
					for (int i = 1; i < list.get(3).getMonth() ; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						list.add(newe);
						newe = new EconCustomerApproveExport();
					}
				}
				if (lastMonth < 12) {// 统计到年终，补全之月
					for (int i = lastMonth + 1; i <= 12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						list.add(newe);
						newe = new EconCustomerApproveExport();
					}
				}
			}
		} else {// 该年份无数据
			for (int i = 1; i <= 12; i++) {
				newe.setFlag(4);
				newe.setMonth(i);
				newe.setTitle(i + "月");
				list.add(newe);
				newe = new EconCustomerApproveExport();
			}
			newe.setTitle("合计");
			newe.setFlag(5);
			list.add(newe);
		}
	}

	// 合计
	private void setExportTotal(EconCustomerApproveExport export, EconCustomerApproveExport e) {
		export.setTitle("合计");
		export.setFlag(5);
		export.setyBTApproving(export.getyBTApproving() + e.getyBTApproving());
		export.setyBTPass(export.getyBTPass() + e.getyBTPass());
		export.setyBTUnpass(export.getyBTUnpass() + e.getyBTUnpass());
		export.setyDApproving(export.getyDApproving() + e.getyDApproving());
		export.setyDPass(export.getyDPass() + e.getyDPass());
		export.setyDUnpass(export.getyDUnpass() + e.getyDUnpass());
		export.setyBTDApproving(export.getyBTDApproving() + e.getyBTDApproving());
		export.setyBTDPass(export.getyBTDPass() + e.getyBTDPass());
		export.setyBTDUnpass(export.getyBTDUnpass() + e.getyBTDUnpass());
		export.setyApproving(export.getyApproving() + e.getyApproving());
		export.setyPass(export.getyPass() + e.getyPass());
		export.setyUnpass(export.getyUnpass() + e.getyUnpass());

		export.setpBTApproving(export.getpBTApproving() + e.getpBTApproving());
		export.setpBTPass(export.getpBTPass() + e.getpBTPass());
		export.setpBTUnpass(export.getpBTUnpass() + e.getpBTUnpass());
		export.setpDApproving(export.getpDApproving() + e.getpDApproving());
		export.setpDPass(export.getpDPass() + e.getpDPass());
		export.setpDUnpass(export.getpDUnpass() + e.getpDUnpass());
		export.setpBTDApproving(export.getpBTDApproving() + e.getpBTDApproving());
		export.setpBTDPass(export.getpBTDPass() + e.getpBTDPass());
		export.setpBTDUnpass(export.getpBTDUnpass() + e.getpBTDUnpass());
		export.setpApproving(export.getpApproving() + e.getpApproving());
		export.setpPass(export.getpPass() + e.getpPass());
		export.setpUnpass(export.getpUnpass() + e.getpUnpass());

		export.setsBTApproving(export.getsBTApproving() + e.getsBTApproving());
		export.setsBTPass(export.getsBTPass() + e.getsBTPass());
		export.setsBTUnpass(export.getsBTUnpass() + e.getsBTUnpass());
		export.setsDApproving(export.getsDApproving() + e.getsDApproving());
		export.setsDPass(export.getsDPass() + e.getsDPass());
		export.setsDUnpass(export.getsDUnpass() + e.getsDUnpass());
		export.setsBTDApproving(export.getsBTDApproving() + e.getsBTDApproving());
		export.setsBTDPass(export.getsBTDPass() + e.getsBTDPass());
		export.setsBTDUnpass(export.getsBTDUnpass() + e.getsBTDUnpass());
		export.setsApproving(export.getsApproving() + e.getsApproving());
		export.setsPass(export.getsPass() + e.getsPass());
		export.setsUnpass(export.getsUnpass() + e.getsUnpass());

		export.setxBTApproving(export.getxBTApproving() + e.getxBTApproving());
		export.setxBTPass(export.getxBTPass() + e.getxBTPass());
		export.setxBTUnpass(export.getxBTUnpass() + e.getxBTUnpass());
		export.setxDApproving(export.getxDApproving() + e.getxDApproving());
		export.setxDPass(export.getxDPass() + e.getxDPass());
		export.setxDUnpass(export.getxDUnpass() + e.getxDUnpass());
		export.setxBTDApproving(export.getxBTDApproving() + e.getxBTDApproving());
		export.setxBTDPass(export.getxBTDPass() + e.getxBTDPass());
		export.setxBTDUnpass(export.getxBTDUnpass() + e.getxBTDUnpass());
		export.setxApproving(export.getxApproving() + e.getxApproving());
		export.setxPass(export.getxPass() + e.getxPass());
		export.setxUnpass(export.getxUnpass() + e.getxUnpass());

		export.setqBTApproving(export.getqBTApproving() + e.getqBTApproving());
		export.setqBTPass(export.getqBTPass() + e.getqBTPass());
		export.setqBTUnpass(export.getqBTUnpass() + e.getqBTUnpass());
		export.setqDApproving(export.getqDApproving() + e.getqDApproving());
		export.setqDPass(export.getqDPass() + e.getqDPass());
		export.setqDUnpass(export.getqDUnpass() + e.getqDUnpass());
		export.setqBTDApproving(export.getqBTDApproving() + e.getqBTDApproving());
		export.setqBTDPass(export.getqBTDPass() + e.getqBTDPass());
		export.setqBTDUnpass(export.getqBTDUnpass() + e.getqBTDUnpass());
		export.setqApproving(export.getqApproving() + e.getqApproving());
		export.setqPass(export.getqPass() + e.getqPass());
		export.setqUnpass(export.getqUnpass() + e.getqUnpass());
	}
}
