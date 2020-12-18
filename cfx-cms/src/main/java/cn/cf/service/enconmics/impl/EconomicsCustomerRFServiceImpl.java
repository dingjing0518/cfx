package cn.cf.service.enconmics.impl;

import java.util.*;

import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.entity.*;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.SysExcelStore;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.service.enconmics.EconomicsCustomerRFService;

@Service
public class EconomicsCustomerRFServiceImpl implements EconomicsCustomerRFService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;

	@Autowired
	private B2bEconomicsBankExtDao b2bEconomicsBankExtDao;

	@Override
	public Map<String, Object> getCustomerCounts(String years) {

		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(years));
		query.with(new Sort(new Order(Direction.ASC, "month")));
		List<EconomicsCustomerReportForms> list = mongoTemplate.find(query, EconomicsCustomerReportForms.class);
		EconCustMonthSumRF monthSumRF = new EconCustMonthSumRF();
		
		String[] timeList = Constants.ECONOMICS_CUSTOMER_TIME_LIST;
		Map<String, Object> map = new HashMap<String, Object>();
		List<EconomicsCustomerReportForms> tempList = new ArrayList<EconomicsCustomerReportForms>();
		if (list != null && list.size() > 0) {
			
			for (int i = 0; i < timeList.length; i++) {
				EconomicsCustomerReportForms rf = list.get(i);
				rf.setMonthName(timeList[i]);
				tempList.add(rf);
			}
			// 算出月份数量合计
			setSelfMonthSum(monthSumRF, list,years);
		}else{
			for (int i = 0; i < timeList.length; i++) {
				EconomicsCustomerReportForms nrf = new EconomicsCustomerReportForms();
				nrf.setMonthName(timeList[i]);
				tempList.add(nrf);
			}
		}
		monthSumRF.setName("合计");
		map.put("tempList", tempList);
		map.put("monthSumRF", monthSumRF);
		return map;
	}
	
	private void setSelfMonthSum(EconCustMonthSumRF monthSumRF, List<EconomicsCustomerReportForms> list,String years) {

		// 本月的月份统计数量(做每种类型每种来源月份的合计计算)
		// 化纤白条
		int blankMonthSH = 0;
		int blankMonthPT = 0;
		int blankMonthXFM = 0;
		int blankMonthYX = 0;
		int blankMonthOther = 0;
		int blankMonthAll = 0;
		// 化纤贷
		int loanMonthSH = 0;
		int loanMonthPT = 0;
		int loanMonthXFM = 0;
		int loanMonthYX = 0;
		int loanMonthOther = 0;
		int loanMonthAll = 0;

		// 化纤白条+化纤贷
		int blaLoMonthSH = 0;
		int blaLoMonthPT = 0;
		int blaLoMonthXFM = 0;
		int blaLoMonthYX = 0;
		int blaLoMonthOther = 0;
		int blaLoMonthAll = 0;
		
		int addUpMonthSH = 0;
		int addUpMonthPT = 0;
		int addUpMonthXFM = 0;
		int addUpMonthYX = 0;
		int addUpMonthOther = 0;
		int addUpMonthCount = 0;

		if (list != null && list.size() > 0) {
			for (EconomicsCustomerReportForms ecoCustRF : list) {
				if (ecoCustRF.getTimes() != null && ecoCustRF.getTimes() == 4) {
					// 每种类型每个来源每月合计
					// 白条产品类型每月合计
					blankMonthSH += ecoCustRF.getBlankSH() == null?0:ecoCustRF.getBlankSH();
					blankMonthPT += ecoCustRF.getBlankPT() == null?0:ecoCustRF.getBlankPT();
					blankMonthXFM += ecoCustRF.getBlankXFM() == null?0:ecoCustRF.getBlankXFM();
					blankMonthYX += ecoCustRF.getBlankYX() == null?0:ecoCustRF.getBlankYX();
					blankMonthOther += ecoCustRF.getBlankOther() == null?0:ecoCustRF.getBlankOther();
					blankMonthAll += ecoCustRF.getBlankGoodsTypeCount() == null?0:ecoCustRF.getBlankGoodsTypeCount();

					// 化纤贷产品类型每月合计
					loanMonthSH += ecoCustRF.getLoanSH() == null?0:ecoCustRF.getLoanSH();
					loanMonthPT += ecoCustRF.getLoanPT() == null?0:ecoCustRF.getLoanPT();
					loanMonthXFM += ecoCustRF.getLoanXFM() == null?0:ecoCustRF.getLoanXFM();
					loanMonthYX += ecoCustRF.getLoanYX() == null?0:ecoCustRF.getLoanYX();
					loanMonthOther += ecoCustRF.getLoanOther() == null?0:ecoCustRF.getLoanOther();
					loanMonthAll += ecoCustRF.getLoanGoodsTypeCount() == null?0:ecoCustRF.getLoanGoodsTypeCount();

					// 化纤白条+化纤贷产品类型每月合计
					blaLoMonthSH += ecoCustRF.getBlaLoSH() == null?0:ecoCustRF.getBlaLoSH();
					blaLoMonthPT += ecoCustRF.getBlaLoPT() == null?0:ecoCustRF.getBlaLoPT();
					blaLoMonthXFM += ecoCustRF.getBlaLoXFM() == null?0:ecoCustRF.getBlaLoXFM();
					blaLoMonthYX += ecoCustRF.getBlaLoYX() == null?0:ecoCustRF.getBlaLoYX();
					blaLoMonthOther += ecoCustRF.getBlaLoOther() == null?0:ecoCustRF.getBlaLoOther();
					blaLoMonthAll += ecoCustRF.getBlaLoGoodsTypeCount() == null?0:ecoCustRF.getBlaLoGoodsTypeCount();
					
					addUpMonthSH += ecoCustRF.getAddUpSH() == null?0:ecoCustRF.getAddUpSH();
					addUpMonthXFM += ecoCustRF.getAddUpXFM() == null?0:ecoCustRF.getAddUpXFM();
					addUpMonthYX += ecoCustRF.getAddUpYX() == null?0:ecoCustRF.getAddUpYX();
					addUpMonthPT += ecoCustRF.getAddUpPT() == null?0:ecoCustRF.getAddUpPT();
					addUpMonthOther += ecoCustRF.getAddUpOther() == null?0:ecoCustRF.getAddUpOther();
					addUpMonthCount += ecoCustRF.getAddUpCount() == null?0:ecoCustRF.getAddUpCount();
					
				}
			}
		}
		
		monthSumRF.setBlankSHMonthCount(blankMonthSH);
		monthSumRF.setBlankPTMonthCount(blankMonthPT);
		monthSumRF.setBlankXFMMonthCount(blankMonthXFM);
		monthSumRF.setBlankYXMonthCount(blankMonthYX);
		monthSumRF.setBlankOtherMonthCount(blankMonthOther);
		monthSumRF.setBlankMonthAll(blankMonthAll);

		monthSumRF.setLoanSHMonthCount(loanMonthSH);
		monthSumRF.setLoanPTMonthCount(loanMonthPT);
		monthSumRF.setLoanXFMMonthCount(loanMonthXFM);
		monthSumRF.setLoanYXMonthCount(loanMonthYX);
		monthSumRF.setLoanOtherMonthCount(loanMonthOther);
		monthSumRF.setLoanMonthAll(loanMonthAll);

		monthSumRF.setBlaLoSHMonthCount(blaLoMonthSH);
		monthSumRF.setBlaLoPTMonthCount(blaLoMonthPT);
		monthSumRF.setBlaLoXFMMonthCount(blaLoMonthXFM);
		monthSumRF.setBlaLoYXMonthCount(blaLoMonthYX);
		monthSumRF.setBlaLoOtherMonthCount(blaLoMonthOther);
		monthSumRF.setBlaLoMonthAll(blaLoMonthAll);

		monthSumRF.setAddUpSHMonthCount(addUpMonthSH);
		monthSumRF.setAddUpPTMonthCount(addUpMonthPT);
		monthSumRF.setAddUpXFMMonthCount(addUpMonthXFM);
		monthSumRF.setAddUpYXMonthCount(addUpMonthYX);
		monthSumRF.setAddUpOtherMonthCount(addUpMonthOther);
		monthSumRF.setAddUpMonthAll(addUpMonthCount);
	}


	@Override
	public int saveCustomApproveToOss(ReportFormsDataTypeParams params, ManageAccount account) {
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportCustomerRF_"+params.getUuid());
		if (CommonUtil.isNotEmpty(params.getYears())){
			store.setParamsName("年份:"+params.getYears());
		}
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(new Date());
		store.setName("金融中心-数据管理-客户申请表");
		store.setType(Constants.FOUR);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public Map<String, Object> getBankCreditCustomerCounts(String years, String bankPk) {
		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(years).and("bankPk").is(bankPk));
		query.with(new Sort(new Order(Direction.ASC, "month")));
		List<EconomicsCustomerBankReportForms> list = mongoTemplate.find(query, EconomicsCustomerBankReportForms.class);
		EconCustBankMonthSumRF monthSumRF = new EconCustBankMonthSumRF();
		String[] timeList = Constants.ECONOMICS_CUSTOMER_TIME_LIST;
		Map<String, Object> map = new HashMap<String, Object>();
		List<EconomicsCustomerBankReportForms> tempList = new ArrayList<EconomicsCustomerBankReportForms>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < timeList.length; i++) {
				EconomicsCustomerBankReportForms rf = list.get(i);
				rf.setMonthName(timeList[i]);
				tempList.add(rf);
			}
			// 算出月份数量合计
			setSelfBankMonthSum(monthSumRF, tempList,years,bankPk);
		}else{
			for (int i = 0; i < timeList.length; i++) {
				EconomicsCustomerBankReportForms nrf = new EconomicsCustomerBankReportForms();
				nrf.setMonthName(timeList[i]);
				tempList.add(nrf);
			}
		}
		monthSumRF.setName("合计");
		map.put("tempList", tempList);
		map.put("monthSumRF", monthSumRF);
		return map;
	}

	@Override
	public int saveBankApproveCustomerToOss(ReportFormsDataTypeParams params, ManageAccount account) {
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportBankApproveCustomer_"+params.getUuid());
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
		store.setName("金融中心-数据管理-银行审批客户数");
		store.setType(Constants.FOUR);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	private void setSelfBankMonthSum(EconCustBankMonthSumRF monthSumRF, List<EconomicsCustomerBankReportForms> list, String years, String bankPk) {

		// 本月的月份统计数量(做每种类型每种来源月份的合计计算)
		// 化纤白条
		int blankMonthSH = 0;
		int blankMonthPT = 0;
		int blankMonthXFM = 0;
		int blankMonthYX = 0;
		int blankMonthOther = 0;
		// 化纤贷
		int loanMonthSH = 0;
		int loanMonthPT = 0;
		int loanMonthXFM = 0;
		int loanMonthYX = 0;
		int loanMonthOther = 0;

		// 化纤白条+化纤贷
		int blaLoMonthSH = 0;
		int blaLoMonthPT = 0;
		int blaLoMonthXFM = 0;
		int blaLoMonthYX = 0;
		int blaLoMonthOther = 0;
		
		
		//每月累计相加，得出合计数据（数据不准）
		int blankAddUpMonthSH = 0;
		int blankAddUpMonthPT = 0;
		int blankAddUpMonthXFM = 0;
		int blankAddUpMonthYX = 0;
		int blankAddUpMonthOther = 0;
		// 化纤贷
		int loanAddUpMonthSH = 0;
		int loanAddUpMonthPT = 0;
		int loanAddUpMonthXFM = 0;
		int loanAddUpMonthYX = 0;
		int loanAddUpMonthOther = 0;

		// 化纤白条+化纤贷
		int blaLoAddUpMonthSH = 0;
		int blaLoAddUpMonthPT = 0;
		int blaLoAddUpMonthXFM = 0;
		int blaLoAddUpMonthYX = 0;
		int blaLoAddUpMonthOther = 0;

		if (list != null && list.size() > 0) {
			for (EconomicsCustomerBankReportForms ecoCustRF : list) {
				if (ecoCustRF.getTimes() != null && ecoCustRF.getTimes() == 4) {
					// 每种类型每个来源每月合计
					// 白条产品类型每月合计
					blankMonthSH += ecoCustRF.getBlankSH() == null ? 0 : ecoCustRF.getBlankSH();
					blankMonthPT += ecoCustRF.getBlankPT() == null ? 0 : ecoCustRF.getBlankPT();
					blankMonthXFM += ecoCustRF.getBlankXFM() == null ? 0 : ecoCustRF.getBlankXFM();
					blankMonthYX += ecoCustRF.getBlankYX() == null ? 0 : ecoCustRF.getBlankYX();
					blankMonthOther += ecoCustRF.getBlankOther() == null ? 0 : ecoCustRF.getBlankOther();

					// 化纤贷产品类型每月合计
					loanMonthSH += ecoCustRF.getLoanSH() == null ? 0 : ecoCustRF.getLoanSH();
					loanMonthPT += ecoCustRF.getLoanPT() == null ? 0 : ecoCustRF.getLoanPT();
					loanMonthXFM += ecoCustRF.getLoanXFM() == null ? 0 : ecoCustRF.getLoanXFM();
					loanMonthYX += ecoCustRF.getLoanYX() == null ? 0 : ecoCustRF.getLoanYX();
					loanMonthOther += ecoCustRF.getLoanOther() == null ? 0 : ecoCustRF.getLoanOther();

					// 化纤白条+化纤贷产品类型每月合计
					blaLoMonthSH += ecoCustRF.getBlaLoSH() == null ? 0 : ecoCustRF.getBlaLoSH();
					blaLoMonthPT += ecoCustRF.getBlaLoPT() == null ? 0 : ecoCustRF.getBlaLoPT();
					blaLoMonthXFM += ecoCustRF.getBlaLoXFM() == null ? 0 : ecoCustRF.getBlaLoXFM();
					blaLoMonthYX += ecoCustRF.getBlaLoYX() == null ? 0 : ecoCustRF.getBlaLoYX();
					blaLoMonthOther += ecoCustRF.getBlaLoOther() == null ? 0 : ecoCustRF.getBlaLoOther();
					
					
					
					//每月累计相加，得出合计数据（数据不准）
					// 白条产品类型每月合计
					blankAddUpMonthSH += ecoCustRF.getBlankAddUpSH() == null ? 0 : ecoCustRF.getBlankAddUpSH();
					blankAddUpMonthPT += ecoCustRF.getBlankPT() == null ? 0 : ecoCustRF.getBlankAddUpPT();
					blankAddUpMonthXFM += ecoCustRF.getBlankXFM() == null ? 0 : ecoCustRF.getBlankAddUpXFM();
					blankAddUpMonthYX += ecoCustRF.getBlankAddUpYX() == null ? 0 : ecoCustRF.getBlankAddUpYX();
					blankAddUpMonthOther += ecoCustRF.getBlankAddUpOther() == null ? 0 : ecoCustRF.getBlankAddUpOther();

					// 化纤贷产品类型每月合计
					loanAddUpMonthSH += ecoCustRF.getLoanAddUpSH() == null ? 0 : ecoCustRF.getLoanAddUpSH();
					loanAddUpMonthPT += ecoCustRF.getLoanAddUpPT() == null ? 0 : ecoCustRF.getLoanAddUpPT();
					loanAddUpMonthXFM += ecoCustRF.getLoanAddUpXFM() == null ? 0 : ecoCustRF.getLoanAddUpXFM();
					loanAddUpMonthYX += ecoCustRF.getLoanAddUpYX() == null ? 0 : ecoCustRF.getLoanAddUpYX();
					loanAddUpMonthOther += ecoCustRF.getLoanAddUpOther() == null ? 0 : ecoCustRF.getLoanAddUpOther();

					// 化纤白条+化纤贷产品类型每月合计
					blaLoAddUpMonthSH += ecoCustRF.getBlaLoAddUpSH() == null ? 0 : ecoCustRF.getBlaLoAddUpSH();
					blaLoAddUpMonthPT += ecoCustRF.getBlaLoAddUpPT() == null ? 0 : ecoCustRF.getBlaLoAddUpPT();
					blaLoAddUpMonthXFM += ecoCustRF.getBlaLoAddUpXFM() == null ? 0 : ecoCustRF.getBlaLoAddUpXFM();
					blaLoAddUpMonthYX += ecoCustRF.getBlaLoAddUpYX() == null ? 0 : ecoCustRF.getBlaLoAddUpYX();
					blaLoAddUpMonthOther += ecoCustRF.getBlaLoAddUpOther() == null ? 0 : ecoCustRF.getBlaLoAddUpOther();
				}
			}
			
				monthSumRF.setBlankAddUpMonthSH(blankAddUpMonthSH);
				monthSumRF.setBlankAddUpMonthXFM(blankAddUpMonthXFM);
				monthSumRF.setBlankAddUpMonthYX(blankAddUpMonthYX);
				monthSumRF.setBlankAddUpMonthPT(blankAddUpMonthPT);
				monthSumRF.setBlankAddUpMonthOther(blankAddUpMonthOther);
				monthSumRF.setBlankAddUpMonthCount(blankAddUpMonthSH+blankAddUpMonthXFM+blankAddUpMonthYX+blankAddUpMonthPT+blankAddUpMonthOther);

				monthSumRF.setLoanAddUpMonthSH(loanAddUpMonthSH);
				monthSumRF.setLoanAddUpMonthXFM(loanAddUpMonthXFM);
				monthSumRF.setLoanAddUpMonthYX(loanAddUpMonthYX);
				monthSumRF.setLoanAddUpMonthPT(loanAddUpMonthPT);
				monthSumRF.setLoanAddUpMonthOther(loanAddUpMonthOther);
				monthSumRF.setLoanAddUpMonthCount(loanAddUpMonthSH+loanAddUpMonthXFM+loanAddUpMonthYX+loanAddUpMonthPT+loanAddUpMonthOther);

				monthSumRF.setBlaLoAddUpMonthSH(blaLoAddUpMonthSH);
				monthSumRF.setBlaLoAddUpMonthXFM(blaLoAddUpMonthXFM);
				monthSumRF.setBlaLoAddUpMonthYX(blaLoAddUpMonthYX);
				monthSumRF.setBlaLoAddUpMonthPT(blaLoAddUpMonthPT);
				monthSumRF.setBlaLoAddUMonthpOther(blaLoAddUpMonthOther);
				monthSumRF.setBlaLoAddUpMonthCount(blaLoAddUpMonthSH+blaLoAddUpMonthXFM+blaLoAddUpMonthYX+blaLoAddUpMonthPT+blaLoAddUpMonthOther);
		}
		monthSumRF.setBlankSHMonthCount(blankMonthSH);
		monthSumRF.setBlankPTMonthCount(blankMonthPT);
		monthSumRF.setBlankXFMMonthCount(blankMonthXFM);
		monthSumRF.setBlankYXMonthCount(blankMonthYX);
		monthSumRF.setBlankOtherMonthCount(blankMonthOther);
		monthSumRF.setBlankMonthAllCount(blankMonthSH+blankMonthXFM+blankMonthYX+blankMonthPT+blankMonthOther);

		monthSumRF.setLoanSHMonthCount(loanMonthSH);
		monthSumRF.setLoanPTMonthCount(loanMonthPT);
		monthSumRF.setLoanXFMMonthCount(loanMonthXFM);
		monthSumRF.setLoanYXMonthCount(loanMonthYX);
		monthSumRF.setLoanOtherMonthCount(loanMonthOther);
		monthSumRF.setLoanMonthAllCount(loanMonthSH+loanMonthXFM+loanMonthYX+loanMonthPT+loanMonthOther);

		monthSumRF.setBlaLoSHMonthCount(blaLoMonthSH);
		monthSumRF.setBlaLoPTMonthCount(blaLoMonthPT);
		monthSumRF.setBlaLoXFMMonthCount(blaLoMonthXFM);
		monthSumRF.setBlaLoYXMonthCount(blaLoMonthYX);
		monthSumRF.setBlaLoOtherMonthCount(blaLoMonthOther);
		monthSumRF.setBlaLoMonthAllCount(blaLoMonthSH+blaLoMonthXFM+blaLoMonthYX+blaLoMonthPT+blaLoMonthOther);
	}

	@Override
	public PageModel<SupplierEconomicsOrderReportForms> searchEconProdBussCountsList(
			QueryModel<SupplierEconomicsOrderReportForms> qm,String accountPk) {

		PageModel<SupplierEconomicsOrderReportForms> pm = new PageModel<SupplierEconomicsOrderReportForms>();
		Query query = new Query();
		query.addCriteria(
				Criteria.where("year").is(qm.getEntity().getYear()).and("bankPk").is(qm.getEntity().getBankPk()));

		long count = mongoTemplate.count(query, SupplierEconomicsOrderReportForms.class);

		query.with(new Sort(new Order(Direction.ASC, qm.getFirstOrderName())));
		query.skip(qm.getStart());
		query.limit(qm.getLimit());
		List<SupplierEconomicsOrderReportForms> list = mongoTemplate.find(query,
				SupplierEconomicsOrderReportForms.class);
		//隐藏店铺名称
		if (list.size()>0) {
            for (SupplierEconomicsOrderReportForms s : list) {
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_REPORT_PROBUSS_COL_STORENAME)) {
                    s.setStoreName(CommonUtil.hideCompanyName(s.getStoreName()));
                }
            }
        }
		pm.setTotalCount((int) count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public List<SupplierEconomicsOrderReportForms> exportEconProdBussCountsList(String years, String bankPk,String accountPk) {
	
		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(years).and("bankPk").is(bankPk));
		List<SupplierEconomicsOrderReportForms> list = mongoTemplate.find(query,
				SupplierEconomicsOrderReportForms.class);
		//隐藏店铺名称
		if (list.size()>0) {
            for (SupplierEconomicsOrderReportForms s : list) {
                if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_REPORT_PROBUSS_COL_STORENAME)) {
                    s.setStoreName(CommonUtil.hideCompanyName(s.getStoreName()));
                }
            }
        }
		return list;
	}
}
