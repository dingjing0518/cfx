package cn.cf.service.common.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.OrderRecordType;
import cn.cf.common.RestCode;
import cn.cf.common.RiskControlType;
import cn.cf.constant.BillType;
import cn.cf.dao.B2bFinanceRecordsDaoEx;
import cn.cf.dao.B2bLoanNumberDaoEx;
import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.OrderPaymentLimitDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.CreditReportInfo;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.SupplierInfo;
import cn.cf.entity.WzDownLoad;
import cn.cf.entry.BankBaseResp;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bBillGoods;
import cn.cf.model.B2bBillInventory;
import cn.cf.model.B2bBillOrder;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bFinanceRecords;
import cn.cf.model.B2bLoanNumber;
import cn.cf.model.B2bOnlinepayRecord;
import cn.cf.model.B2bOrder;
import cn.cf.property.PropertyConfig;
import cn.cf.service.bill.BillCusGoodsService;
import cn.cf.service.bill.BillGoodsService;
import cn.cf.service.bill.BillInventoryService;
import cn.cf.service.bill.BillOrderService;
import cn.cf.service.bill.BillSigncardService;
import cn.cf.service.bill.BillSingingService;
import cn.cf.service.common.HuaxianhuiService;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.foreign.ForeignOrderService;
import cn.cf.service.onlinepay.OnlinepayService;
import cn.cf.service.platform.B2bCreditGoodsService;
import cn.cf.service.platform.B2bEconomicsCustomerService;
import cn.cf.service.platform.B2bLoanNumberService;
import cn.cf.service.platform.FinanceRecordsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.Constants;
import cn.cf.util.DateUtil;
import cn.cf.util.FileUtil;
import cn.cf.util.InterestUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.OSSUtils;

@Service
public class HuaxianhuiServiceImpl implements HuaxianhuiService {
	
	@Autowired
	private ForeignOrderService foreignOrderService;
	
	@Autowired
	private B2bFinanceRecordsDaoEx financeRecordsDaoEx;
	
	@Autowired
	private FinanceRecordsService financeRecordsService;
	
	@Autowired
	private B2bLoanNumberDaoEx b2bLoanNumberDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bEconomicsCustomerService b2bEconomicsCustomerService;
	
	@Autowired
	private OnlinepayService onlinepayService;
	
	@Autowired
	private B2bCreditGoodsService b2bCreditGoodsService;
	
	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;
	
	@Autowired
	private BillOrderService billOrderService;
	@Autowired
	private BillCusGoodsService cusGoodsService;
	
	@Autowired
	private BillGoodsService billGoodsService;
	
	@Autowired
	private BillCusGoodsService billCusGoodsService;
	
	@Autowired
	private BillSigncardService billSigncardService;
	
	@Autowired
	private BillSingingService billSingingService;
	
	@Autowired
	private BillInventoryService billInventoryService;
	
	@Autowired
	private ForeignCompanyService foreignCompanyService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void updateBackPyOrder(B2bOrderDtoMa odto,String contractNo,Integer paymentType,String paymentName,B2bCompanyDto company,B2bCreditGoodsDtoMa creditGoods,SysCompanyBankcardDto bdto,BankBaseResp resp) {
		B2bLoanNumber number = new B2bLoanNumber();
			number.setOrderNumber(odto.getOrderNumber());
			number.setBankPk(creditGoods.getBankPk());
			number.setBankName(creditGoods.getBank());
			number.setContractNumber(creditGoods.getAgreementNumber());
			number.setLoanStatus(2);
			if(null == creditGoods.getProportion() || creditGoods.getProportion() == 0d){
				creditGoods.setProportion(1d);
			}
			number.setLoanAmount(ArithUtil.round(ArithUtil.mul(odto.getActualAmount(), creditGoods.getProportion()),2));
			number.setEconomicsGoodsName(creditGoods.getEconomicsGoodsName());
			number.setEconomicsGoodsType(creditGoods.getGoodsType());
			number.setPurchaserPk(odto.getPurchaserPk());
			number.setPurchaserName(odto.getPurchaser().getPurchaserName());
			number.setSupplierPk(odto.getSupplierPk());
			number.setSupplierName(odto.getSupplier().getSupplierName());
			number.setCustomerNumber(creditGoods.getBankAccountNumber());
			number.setOrganizationCode(company.getOrganizationCode());
			number.setInsertTime(new Date());
			number.setPrincipal(0d);
			number.setInterest(0d);
			number.setPenalty(0d);
			number.setCompound(0d);
			number.setRepaidInterest(0d);
			number.setRepaidSerCharge(0d);
			number.setTotalRate(creditGoods.getTotalRate());
			number.setBankRate(creditGoods.getBankRate());
			number.setSevenRate(creditGoods.getSevenRate());
			number.setLoanStartTime(new Date());
			number.setIsOverdue(1);
			if(null != creditGoods.getTerm()){
				number.setLoanEndTime(DateUtil.getDaysForDate(new Date(),creditGoods.getTerm()));
			}
			if(null != resp.getLoanAccount()){
				number.setLoanAccount(resp.getLoanAccount());
			}
			if(null !=resp.getBankJiansheResp()){
				number.setQrCode(resp.getBankJiansheResp().getBaseCode());
				number.setReturnUrl(resp.getBankJiansheResp().getReturnUrl());
			}
			b2bLoanNumberDao.insert(number);
			//更新订单表信息
			if("".equals(contractNo)){
				B2bOrder order =  new B2bOrder();
				order.setOrderNumber(odto.getOrderNumber());
				order.setPaymentType(paymentType);
				order.setPaymentName(paymentName);
				order.setEconomicsGoodsType(creditGoods.getGoodsType() );
				order.setEconomicsGoodsName(creditGoods.getEconomicsGoodsName());
				order.setOrderStatus(2);
				order.setPaymentTime(new Date());
				order.setOwnAmount(ArithUtil.sub(odto.getOrderAmount(), 
						number.getLoanAmount()));
				foreignOrderService.updateOrderStatus(order,bdto);
			}else{
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				contract.setPaymentType(paymentType);
				contract.setPaymentName(paymentName);
				contract.setEconomicsGoodsName(creditGoods.getEconomicsGoodsName());
				contract.setEconomicsGoodsType(creditGoods.getGoodsType());
				contract.setContractStatus(2);
				contract.setPaymentTime(new Date());
				contract.setOwnAmount(ArithUtil.sub(odto.getOrderAmount(), 
						number.getLoanAmount()));
				foreignOrderService.updateContractStatus(contract,bdto);
			}
		// 增加一笔采购商支付订单交易记录
		B2bFinanceRecords records = new B2bFinanceRecords();
		records.setPk(KeyUtils.getUUID());
		// 采购商信息
		records.setCompanyName(odto.getPurchaser().getPurchaserName());
		records.setCompanyPk(odto.getPurchaserPk());
		//查询供应商实体账户
		records.setSupplierPk(odto.getSupplierPk());
		records.setSupplierName(odto.getSupplier().getSupplierName());
		records.setReceivablesAccount(bdto.getBankAccount());//供应商收款实体卡号
		records.setReceivablesAccountName(bdto.getBankName());//供应商收款实体银行名
		records.setDescription("订单号:" + odto.getOrderNumber()
				+ "<br>供应商账户:"
				+ bdto.getBankAccount()
				+ "<br>供应商名称:" + odto.getSupplier().getSupplierName());	
		records.setTransactionAmount(ArithUtil.round(number.getLoanAmount(), 2));
		records.setInsertTime(new Date());
		records.setStatus(3);// 状态为处理中
		records.setTransactionType(1);// 付款
		records.setOrderNumber(odto.getOrderNumber());
		records.setEmployeePk(odto.getEmployeePk());
		records.setEmployeeName(odto.getEmployeeName());
		records.setEmployeeNumber(odto.getEmployeeNumber());
		financeRecordsDaoEx.insert(records);
		if(null == resp.getOrderPaymentList() || resp.getOrderPaymentList().size() == 0){
			B2bFinanceRecords recordNex = new B2bFinanceRecords();
			recordNex.setPk(KeyUtils.getUUID());
			recordNex.setCompanyName(odto.getSupplier().getSupplierName());
			recordNex.setCompanyPk(odto.getSupplierPk());
			recordNex.setSupplierPk(odto.getPurchaserPk());
			recordNex.setSupplierName(odto.getPurchaser().getPurchaserName());
			recordNex.setReceivablesAccount(bdto.getBankAccount());//供应商收款实体卡号
			recordNex.setReceivablesAccountName(bdto.getBankName());//供应商收款实体银行名
			recordNex.setDescription("订单号:" + odto.getOrderNumber()
					+ "<br>采购商名称:" + odto.getPurchaser().getPurchaserName()
					+ "<br>收款账户:" + bdto.getBankAccount()
					+ "<br>收款银行:" + bdto.getBankName());	
			recordNex.setTransactionAmount((ArithUtil.round(number.getLoanAmount(), 2)));
			recordNex.setInsertTime(new Date());
			recordNex.setStatus(3);// 状态为处理中
			recordNex.setTransactionType(5);// 收款
			recordNex.setOrderNumber(odto.getOrderNumber());
			recordNex.setEmployeePk(odto.getEmployeePk());
			recordNex.setEmployeeName(odto.getEmployeeName());
			recordNex.setEmployeeNumber(odto.getEmployeeNumber());
			financeRecordsDaoEx.insert(recordNex);
		//增加多个供应商收款记录	
		}else{
			for (OrderPaymentLimitDto dto : resp.getOrderPaymentList()) {
				B2bFinanceRecords recordNex = new B2bFinanceRecords();
				recordNex.setPk(KeyUtils.getUUID());
				recordNex.setCompanyName(dto.getPayeePlatName());
				recordNex.setCompanyPk(dto.getPayeePlatformUser());
				recordNex.setSupplierPk(odto.getPurchaserPk());
				recordNex.setSupplierName(odto.getPurchaser().getPurchaserName());
				recordNex.setReceivablesAccount(dto.getPayeeVirtualAccount());//供应商收款实体卡号
				recordNex.setReceivablesAccountName(dto.getPayeeBankName());//供应商收款实体银行名
				recordNex.setDescription("订单号:" + odto.getOrderNumber()
						+ "<br>采购商名称:" + odto.getPurchaser().getPurchaserName()
						+ "<br>收款账户:" + dto.getPayeeVirtualAccount()
						+ "<br>收款银行:" + dto.getPayeeBankName());	
				recordNex.setTransactionAmount((ArithUtil.round(Double.parseDouble(dto.getOrderAmount()), 2)));
				recordNex.setInsertTime(new Date());
				recordNex.setStatus(3);// 状态为处理中
				recordNex.setTransactionType(5);// 收款
				recordNex.setOrderNumber(odto.getOrderNumber());
				recordNex.setEmployeePk(odto.getEmployeePk());
				recordNex.setEmployeeName(odto.getEmployeeName());
				recordNex.setEmployeeNumber(odto.getEmployeeNumber());
				financeRecordsDaoEx.insert(recordNex);
			}
		}
		//更新订单日志
				OrderRecord or = new OrderRecord();
				or.setId(KeyUtils.getUUID());
				or.setOrderNumber(odto.getOrderNumber());
				or.setInsertTime(DateUtil.formatDateAndTime(new Date()));
				String content = OrderRecordType.PAYMENT.toString();
				content = content.replace("${orderNumber}",
						odto.getOrderNumber());
				content += "支付方式:"+paymentName+creditGoods.getEconomicsGoodsName()
						+ ",支付金额:"
						+ ArithUtil.roundBigDecimal(new BigDecimal(
								number.getLoanAmount()), 2);
				or.setContent(content);
				or.setInsertTime(DateUtil.getDateFormat(new Date()));
				or.setStatus(2);//确认收款
				mongoTemplate.save(or);
	}
	@Override
	public void updateBackLoanOrder(B2bLoanNumberDto odto,BankCreditDto creditDto) {
		//更新订单表信息
		B2bLoanNumber loanNumber =  new B2bLoanNumber();
		loanNumber.setOrderNumber(odto.getOrderNumber());
		loanNumber.setLoanStatus(3);
		loanNumber.setContractNumber(creditDto.getContractNumber());
		loanNumber.setLoanNumber(creditDto.getIouNumber());
		loanNumber.setLoanStartTime(creditDto.getLoanStartDate());
		loanNumber.setLoanEndTime(creditDto.getLoanEndDate());
		loanNumber.setLoanAccount(creditDto.getLoanAccount());
		if(null != creditDto.getLoanRate() && creditDto.getLoanRate() >0d){
			loanNumber.setBankRate(creditDto.getLoanRate());
		}
		try {
			b2bLoanNumberDao.updateByOrderNumber(loanNumber);
		} catch (Exception e) {
			logger.error("callBackLoanError",e);
		}
		// 存订单日志(放款成功)
		OrderRecord orone = new OrderRecord();
		orone.setId(KeyUtils.getUUID());
		orone.setOrderNumber(odto.getOrderNumber());
		String content = OrderRecordType.UPDATE.toString();
		content = content.replace("${orderNumber}", odto.getOrderNumber());
		content += odto.getBankName()+"放款成功,放款金额:" + new BigDecimal(odto.getLoanAmount().toString()).toPlainString();
		orone.setContent(content);
		orone.setInsertTime(DateUtil.getDateFormat(new Date()));
		mongoTemplate.save(orone);
		//支付成功之后
		if(null != creditDto.getPayStatus() && creditDto.getPayStatus() == 1){
			//更新订单交易记录
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderNumber", odto.getOrderNumber());
			map.put("status", 1);
			financeRecordsDaoEx.updateStatus(map);
		}
	}

	@Override
	public void updatezhLoanOrder(B2bLoanNumberDto odto, BankCreditDto creditDto) {
		//更新订单表信息
		B2bLoanNumber loanNumber = new B2bLoanNumber();
		loanNumber.setOrderNumber(odto.getOrderNumber());
		loanNumber.setLoanStatus(creditDto.getLoanStatus());
		loanNumber.setContractNumber(creditDto.getContractNumber());
		loanNumber.setLoanNumber(creditDto.getIouNumber());
		loanNumber.setLoanStartTime(creditDto.getLoanStartDate());
		loanNumber.setLoanEndTime(creditDto.getLoanEndDate());
		loanNumber.setLoanAmount(creditDto.getLoanAmount());
		if (null != creditDto.getLoanRate() && creditDto.getLoanRate() > 0d) {
			loanNumber.setBankRate(creditDto.getLoanRate());
		}
		try {
			b2bLoanNumberDao.updateByOrderNumber(loanNumber);
		} catch (Exception e) {
			logger.error("callBackLoanError", e);
		}
		if (loanNumber.getLoanStatus() == 3) {
			// 存订单日志(放款成功)
			OrderRecord orone = new OrderRecord();
			orone.setId(KeyUtils.getUUID());
			orone.setOrderNumber(odto.getOrderNumber());
			String content = OrderRecordType.UPDATE.toString();
			content = content.replace("${orderNumber}", odto.getOrderNumber());
			content += odto.getBankName() + "放款成功,放款金额:" + new BigDecimal(odto.getLoanAmount().toString()).toPlainString();
			orone.setContent(content);
			orone.setInsertTime(DateUtil.getDateFormat(new Date()));
			mongoTemplate.save(orone);
			//支付成功之后
			if (null != creditDto.getPayStatus() && creditDto.getPayStatus() == 1) {
				//更新订单交易记录
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNumber", odto.getOrderNumber());
				map.put("status", 1);
				financeRecordsDaoEx.updateStatus(map);
			}
		}
		// 存订单日志(未放款成功)
		OrderRecord orone = new OrderRecord();
		orone.setId(KeyUtils.getUUID());
		orone.setOrderNumber(odto.getOrderNumber());
		String content = OrderRecordType.UPDATE.toString();
		content = content.replace("${orderNumber}", odto.getOrderNumber());
		if (loanNumber.getLoanStatus() == 1){
			content += odto.getBankName() + "放款待申请";
		}else if (loanNumber.getLoanStatus() == 2){
			content += odto.getBankName() + "放款申请中";
		}
		content += odto.getBankName() + "放款失败";
		orone.setContent(content);
		orone.setInsertTime(DateUtil.getDateFormat(new Date()));
		mongoTemplate.save(orone);
	}

	@Override
	public void updateBackRepayment(B2bOrderDtoMa om, B2bLoanNumberDto odto,
			List<B2bRepaymentRecord> records,Integer loanStatus,Date repaymentDate) {
		/**
		 * 删除原有的订单的还款记录
		 */
		Query qu = new Query(Criteria.where("orderNumber").is(
				odto.getOrderNumber()));
		List<B2bRepaymentRecord> list = mongoTemplate.find(qu,
				B2bRepaymentRecord.class);
		B2bLoanNumber loanNumber = new B2bLoanNumber();
		if (list.size() < records.size()) {
			if (list != null) {
				mongoTemplate.remove(qu, B2bRepaymentRecord.class);
			}
			Double amount = 0d;// 实还本金
			Double interest = 0d;// 实还利息
			Double penalty = 0d;// 实还罚息
			Double compound = 0d;// 实还复利
			Double repaidInterest = 0d;//银行已还利息
			/**
			 * 查询所有的还款记录
			 */
			for (int i = 0; i < records.size(); i++) {
				B2bRepaymentRecord o = records.get(i);
				if(null != o.getAmount()){
					amount += o.getAmount();
				}
				if(null != o.getInterest()){
					interest += o.getInterest();
				}
				if(null != o.getPenalty()){
					penalty += o.getPenalty();
				}
				if(null != o.getCompound()){
					compound += o.getCompound();
				}
				o.setInterestReceivable(ArithUtil.round(InterestUtil.getInterest(DateUtil.formatYearMonthDay(odto.getLoanStartTime()), numberToString(o.getCreateTime()), o.getAmount(), odto.getBankRate()), 2));
				o.setServiceCharge(ArithUtil.round(InterestUtil.getCoverCharges(DateUtil.formatYearMonthDay(odto.getLoanStartTime()), numberToString(o.getCreateTime()), o.getAmount(), odto.getTotalRate(), odto.getBankRate(), odto.getSevenRate(), odto.getEconomicsGoodsType()), 2));
				if(null != odto.getEconomicsGoodsType() && (odto.getEconomicsGoodsType() ==2 || odto.getEconomicsGoodsType() ==4)){
					o.setSevenCharge(ArithUtil.round(InterestUtil.getSevenDayServiceCharges(DateUtil.formatYearMonthDay(odto.getLoanStartTime()), numberToString(o.getCreateTime()), o.getAmount(),odto.getSevenRate()), 2));
				}else{
					o.setSevenCharge(0d);
				}
				o.setGoodsName(odto.getEconomicsGoodsName());
				o.setGoodsType(odto.getEconomicsGoodsType());
				repaidInterest = ArithUtil.add(repaidInterest, o.getInterestReceivable());
				// 还款记录
				mongoTemplate.insert(o);
			}
			double orderNewAmount = ArithUtil.sub(amount,
					odto.getPrincipal());
			double orderNewInterest = ArithUtil.sub(interest,
					odto.getInterest());
			double orderNewPenalty = ArithUtil.sub(penalty,
					odto.getPenalty());
			double orderNewCompound = ArithUtil.sub(compound,
					odto.getCompound());
			/**
			 * 如还款有变化则在订单记录中增加还款记录
			 */
			if (orderNewAmount > 0 || orderNewInterest > 0
					|| orderNewPenalty > 0 || orderNewCompound > 0) {
				/**
				 * 交易记录
				 */
				B2bFinanceRecords f = new B2bFinanceRecords();
				f.setPk(KeyUtils.getUUID());
				f.setStatus(1);
				f.setInsertTime(new Date());
				f.setCompanyPk(odto.getPurchaserPk());
				f.setCompanyName(odto.getPurchaserName());
				f.setTransactionAmount(orderNewAmount);
				f.setTransactionType(4);
				f.setOrderNumber(odto.getOrderNumber());
				f.setIouNumber(odto.getLoanNumber());
				if(interest > 0 || penalty > 0 || compound > 0){
					f.setDescription("订单号:"
							+ odto.getOrderNumber()
							+ "<br>实还金额:"
							+ ArithUtil.add(ArithUtil.add(
									orderNewAmount, orderNewInterest),
									ArithUtil.add(orderNewPenalty,
											orderNewCompound))
											+ "=实还本金:"
											+ ArithUtil.roundStr(orderNewAmount, 2)
											+ "+实还利息:"
											+ ArithUtil.roundStr(orderNewInterest, 2)
											+ "+实还罚息:"
											+ ArithUtil.roundStr(orderNewPenalty, 2)
											+ "+实还复利:"
											+ ArithUtil.roundStr(orderNewCompound, 2));
				//如果没有返回利息的情况下
				}else{
					f.setDescription("订单号:"+ odto.getOrderNumber()+"<br>"+"实还本金:"+ ArithUtil.roundStr(orderNewAmount, 2));
				}
				f.setEmployeePk(om.getEmployeePk());
				f.setEmployeeName(om.getEmployeeName());
				f.setEmployeeNumber(om.getEmployeeNumber());
				financeRecordsDaoEx.insert(f);
			}
			loanNumber.setPrincipal(amount);//已还本金
			loanNumber.setInterest(interest);//已还利息
			loanNumber.setPenalty(penalty);//已还罚息
			loanNumber.setCompound(compound);//已还复利
			loanNumber.setRepaidInterest(repaidInterest);//已还银行利息
			/**
			 * 更新订单
			 */
			loanNumber.setOrderNumber(odto.getOrderNumber());
			loanNumber.setRepaymentTime(null==repaymentDate?new Date():repaymentDate);//最后一天还款日期
//		loanNumber.setLoanStatus(loanStatus);
			if(null !=loanNumber.getPrincipal() && (ArithUtil.roundStr(loanNumber.getPrincipal(), 2).equals(ArithUtil.roundStr(odto.getLoanAmount(),2)) ||
					loanNumber.getPrincipal() > odto.getLoanAmount())){
				loanNumber.setLoanStatus(5);//已还款
			}else{
				loanNumber.setLoanStatus(6);//部分还款
			}
			b2bLoanNumberDao.updateByOrderNumber(loanNumber);
		}
	}
	@Override
	public List<B2bRepaymentRecord> updateBackRepaymentAnother(B2bOrderDtoMa om,B2bLoanNumberDto odto,
			List<B2bRepaymentRecord> list, Integer loanStatus,
			Date repaymentDate,Boolean flag) {
			B2bLoanNumber loanNumber = new B2bLoanNumber();
			Double amount = 0d;// 实还本金
			Double repaidInterest = 0d;//应收利息
			Double interest = 0d;//银行已还利息
			//新增还款记录
			List<B2bRepaymentRecord> newRepaymentList = new ArrayList<B2bRepaymentRecord>();
			for (int i = 0; i < list.size(); i++) {
				// 还款记录
				B2bRepaymentRecord o = list.get(i);
				amount = o.getAmount();
				//应收利息如果不传入则自行计算
				if(null ==o.getInterestReceivable()){
					o.setInterestReceivable(ArithUtil.round( InterestUtil.getInterest(DateUtil.formatYearMonthDay(odto.getLoanStartTime()), DateUtil.formatYearMonthDay(repaymentDate), amount, odto.getBankRate()), 2));
				}
				o.setServiceCharge(ArithUtil.round(InterestUtil.getCoverCharges(DateUtil.formatYearMonthDay(odto.getLoanStartTime()), numberToString(o.getCreateTime()), o.getAmount(), odto.getTotalRate(), odto.getBankRate(), odto.getSevenRate(), odto.getEconomicsGoodsType()), 2));
				//化纤贷与化纤贷委托放款需要算七日费率
				if(null != odto.getEconomicsGoodsType() && (odto.getEconomicsGoodsType() ==2 || odto.getEconomicsGoodsType() == 4)){
					o.setSevenCharge(ArithUtil.round(InterestUtil.getSevenDayServiceCharges(DateUtil.formatYearMonthDay(odto.getLoanStartTime()), DateUtil.formatYearMonthDay(repaymentDate), amount,odto.getSevenRate()), 2));
				}else{
					o.setSevenCharge(0d);
				}
				repaidInterest = ArithUtil.add(repaidInterest, o.getInterestReceivable()); 
				interest = ArithUtil.add(interest, null==o.getInterest()?0d:o.getInterest());
				B2bFinanceRecords f = new B2bFinanceRecords();
				f.setPk(KeyUtils.getUUID());
				f.setStatus(1);
				f.setInsertTime(new Date());
				f.setCompanyPk(odto.getPurchaserPk());
				f.setCompanyName(odto.getPurchaserName());
				f.setTransactionAmount(amount);
				f.setTransactionType(4);
				f.setOrderNumber(odto.getOrderNumber());
				f.setIouNumber(odto.getLoanNumber());
				f.setDescription("订单号:"+ odto.getOrderNumber()+"<br>"+"实还本金:"+ ArithUtil.roundStr(amount, 2));
				f.setEmployeePk(om.getEmployeePk());
				f.setEmployeeName(om.getEmployeeName());
				f.setEmployeeNumber(om.getEmployeeNumber());
				financeRecordsDaoEx.insert(f);
				mongoTemplate.insert(o);
				newRepaymentList.add(o);
			}
			loanNumber.setPrincipal(ArithUtil.add(odto.getPrincipal(), amount));//已还本金
			loanNumber.setRepaidInterest(ArithUtil.add(odto.getRepaidInterest(), repaidInterest));//应收利息
			loanNumber.setInterest(ArithUtil.add(odto.getRepaidInterest(), interest));//银行还款利息
		/**
		 * 更新订单
		 */
		loanNumber.setOrderNumber(odto.getOrderNumber());
		loanNumber.setLoanStatus(loanStatus);
		loanNumber.setRepaymentTime(null==repaymentDate?new Date():repaymentDate);//最后一天还款日期
		b2bLoanNumberDao.updateByOrderNumber(loanNumber);
		//修改已使用额度
		if(flag){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("companyPk", odto.getPurchaserPk());
			map.put("bankPk",odto.getBankPk());
			map.put("goodsType",odto.getEconomicsGoodsType());
			List<B2bCreditGoodsDto> gdtoList =b2bCreditGoodsService.searchList(map);
			if(null != gdtoList && gdtoList.size()>0){
				B2bCreditGoodsDto gdto = gdtoList.get(0);
//				gdto.setPledgeUsedAmount(ArithUtil.round(ArithUtil.sub(null==gdto.getPledgeUsedAmount()?0d:gdto.getPledgeUsedAmount(), amount), 2));
//				if(gdto.getPledgeUsedAmount()<0d){
//					gdto.setPledgeUsedAmount(0d);
//				}
				gdto.setPledgeUsedAmount(b2bLoanNumberService.searchSumLoan(odto.getPurchaserPk(), gdto.getGoodsType()));
				b2bCreditGoodsService.updateByCreditGoods(gdto);
			}
		}
		return newRepaymentList;
	}
	
	public static String numberToString(String starttime) {
		starttime =  starttime.replaceAll("/", "").replaceAll("-", "");
		if (starttime.length() == 8) {
			starttime = starttime.substring(0, 4) + "-" + starttime.substring(4, 6) + "-" + starttime.substring(6, 8);
		}
		return starttime;
	}
	@Override
	public void updateAngetPay(B2bLoanNumberDto odto, B2bRepaymentRecord record,BankBaseResp resp) {
		Update update = new Update();
		update.set("agentPayStatus", resp.getAgentPayStatus());
		mongoTemplate.upsert(new Query(Criteria.where("id").is(record.getId())),
				update, B2bRepaymentRecord.class);
		if(null != resp && resp.getAgentPayStatus() ==2){
			B2bLoanNumber loanNumber = new B2bLoanNumber();
			loanNumber.setOrderNumber(odto.getOrderNumber());
			loanNumber.setRepaidSerCharge(ArithUtil.add(null==odto.getRepaidSerCharge()?0d:odto.getRepaidSerCharge(), record.getServiceCharge()));
			b2bLoanNumberDao.updateByOrderNumber(loanNumber);
		}
	}
	@Override
	public void updateAngetQry(B2bRepaymentRecord record,BankBaseResp resp) {
		Update update = new Update();
		update.set("agentPayStatus", resp.getAgentPayStatus());
		mongoTemplate.upsert(new Query(Criteria.where("id").is(record.getId())),
				update, B2bRepaymentRecord.class);
		
	}
	@Override
	public void upLoadWz(WzDownLoad downLoad) {
		if(null != downLoad && null!= downLoad.getDownLoadStatus() && downLoad.getDownLoadStatus() == 2){
			try {
				File file = new File(PropertyConfig.getStrValueByKey("wzsy_wz_path")+downLoad.getFileName());
				if(file.exists()){
					String fileDetails = FileUtil.readFile(PropertyConfig.getStrValueByKey("wzsy_wz_path")+downLoad.getFileName());
					if(null != fileDetails){
						//判断文件格式是否正确
						if(!fileDetails.contains("errorCode")){
//							String xmlFilePath = PropertyConfig.getStrValueByKey("wzsy_wz_path")+downLoad.getFileName().replace("txt", "xml");
//							File xmlFile = new File(xmlFilePath);
//							if(!xmlFile.exists()){
//								try {
//									xmlFile.createNewFile();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}
//							FileUtil.writeStringFile(fileDetails, xmlFilePath);
//							String uploadpath = OSSUtils.ossMangerUpload(xmlFile, Constants.wz);
							String uploadpath = OSSUtils.ossMangerUpload(file, Constants.REPORT);
							//更新申请客户管理地址
							B2bEconomicsCustomerDto customerDto = b2bEconomicsCustomerService.getEconomicsCustomer(downLoad.getCompanyPk());
							if(null != customerDto){
								customerDto.setCreditUrl(PropertyConfig.getStrValueByKey("oss_url")+uploadpath);
								b2bEconomicsCustomerService.updateEconomicsCustomer(customerDto);
								Update update = new Update();
								update.set("downLoadStatus", 3);//已上传
								update.set("creditUrl", PropertyConfig.getStrValueByKey("oss_url")+uploadpath);
								mongoTemplate.upsert(new Query(Criteria.where("id").is(downLoad.getId())),
										update, WzDownLoad.class);
								
								this.updateReport(customerDto.getCompanyPk(), customerDto.getCompanyName(), 
										RiskControlType.WZSY.getValue(), fileDetails, PropertyConfig.getStrValueByKey("oss_url")+uploadpath);
							
							}
							//删除文件
							file.delete();
//							xmlFile.delete();
						}else{
							Update update = new Update();
							update.set("downLoadStatus", 1);//未下载
							mongoTemplate.upsert(new Query(Criteria.where("id").is(downLoad.getId())),
									update, WzDownLoad.class);
						}
					}
				}
			} catch (Exception e) {
				logger.error("errorupLoadWz: downLoadId:"+downLoad.getId(),e);
			}
		}
	}
	@Override
	public String updateWz(String companyPk, String fileName) {
		String rest = RestCode.CODE_0000.toJson();
		try {
				WzDownLoad downLoad = new WzDownLoad();
				downLoad.setCompanyPk(companyPk);
				downLoad.setFileName(fileName);
				downLoad.setInsertTime(DateUtil.formatDateAndTime(new Date()));
				downLoad.setDownLoadStatus(1);
				mongoTemplate.save(downLoad);
		} catch (Exception e) {
			logger.error("updateWz:"+e);
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}
	
	@Override
	public void successBackPyOnline(B2bOrderDtoMa odto,String bankAccount,String bankAccountName) {
		if(null != odto){
			//修改线上支付申请表
			B2bOnlinepayRecordDto onlineRecord =  onlinepayService.getByOrderNumer(odto.getOrderNumber());
			if(null != onlineRecord){
				onlineRecord.setStatus(2);
				B2bOnlinepayRecord record = new B2bOnlinepayRecord();
				record.UpdateDTO(onlineRecord);
				onlinepayService.updateOnlineRecord(record);
				//交易记录
				financeRecordsService.insertFinanceRecords(odto,bankAccountName,bankAccount,1);
			}
		}
	}

	@Override
	public void errorBackPyOnline(String orderNumber) {
		//修改线上支付申请表
		B2bOnlinepayRecordDto onlineRecord =  onlinepayService.getByOrderNumer(orderNumber);
		if(null != onlineRecord){
			onlineRecord.setStatus(3);
			B2bOnlinepayRecord record = new B2bOnlinepayRecord();
			record.UpdateDTO(onlineRecord);
			onlinepayService.updateOnlineRecord(record);
		}
		
		B2bOrderDtoMa odto = foreignOrderService.getOrder(orderNumber);
		String contractNo = null;
		String supplierInfo = null;
		if(null == odto){
			odto = foreignOrderService.getContract(orderNumber);
			contractNo = null!=odto?odto.getOrderNumber():null;
		}
		if(null != odto && odto.getOrderStatus() ==2){
			//修改订单支付状态
			if(null != odto.getSupplierInfo() && !"".equals(supplierInfo)){
				SupplierInfo s= odto.getSupplier();
				s.setBankName(null);
				s.setBankAccount(null);
				supplierInfo = JsonUtils.convertToString(s);
			}
			if(null==contractNo || "".equals(contractNo)){
				B2bOrder order =  new B2bOrder();
				order.setOrderNumber(orderNumber);
				order.setPaymentType(null);
				order.setPaymentName(null);
				order.setEconomicsGoodsType(null);
				order.setEconomicsGoodsName(null);
				order.setOrderStatus(1);
				order.setPaymentTime(null);
				order.setOwnAmount(0d);
				order.setSupplierInfo(supplierInfo);
				foreignOrderService.updateOrderStatus(order,new SysCompanyBankcardDto());
			}else{
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				contract.setPaymentType(null);
				contract.setPaymentName(null);
				contract.setEconomicsGoodsName(null);
				contract.setEconomicsGoodsType(null);
				contract.setContractStatus(1);
				contract.setPaymentTime(null);
				contract.setOwnAmount(0d);
				contract.setSupplierInfo(supplierInfo);
				foreignOrderService.updateContractStatus(contract,new SysCompanyBankcardDto());
			}
			
			//更新订单日志
			OrderRecord or = new OrderRecord();
			or.setId(KeyUtils.getUUID());
			or.setOrderNumber(odto.getOrderNumber());
			or.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			String content = OrderRecordType.UPDATE.toString();
			content = content.replace("${orderNumber}",
					odto.getOrderNumber());
			content += "线上支付失败";
			or.setContent(content);
			or.setInsertTime(DateUtil.getDateFormat(new Date()));
			or.setStatus(1);//待付款
			mongoTemplate.save(or);	
		}
	}
	@Override
	public void applyBackOnline(B2bOrderDtoMa odto, String contractNo,String paymentName,Integer paymentType,String serialNumber,
			SysCompanyBankcardDto card, B2bOnlinepayGoodsDto onlinePayGoods,String returnUrl) {
		String orderNumber = null;
		try {
			//更新订单表信息
			if("".equals(contractNo)){
				orderNumber = odto.getOrderNumber();
				B2bOrder order =  new B2bOrder();
				order.setOrderNumber(orderNumber);
				order.setPaymentType(paymentType);
				order.setPaymentName(paymentName);
				order.setEconomicsGoodsType(paymentType);
				order.setEconomicsGoodsName(onlinePayGoods.getName());
				order.setOrderStatus(2);
				order.setPaymentTime(new Date());
				order.setOwnAmount(odto.getActualAmount());
				foreignOrderService.updateOrderStatus(order,card);
			}else{
				orderNumber = contractNo;
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				contract.setPaymentType(paymentType);
				contract.setPaymentName(paymentName);
				contract.setEconomicsGoodsName(onlinePayGoods.getName());
				contract.setEconomicsGoodsType(paymentType);
				contract.setContractStatus(2);
				contract.setPaymentTime(new Date());
				contract.setOwnAmount(odto.getActualAmount());
				foreignOrderService.updateContractStatus(contract,card);
			}
		} catch (Exception e) {
			logger.error("callBackOrderError",e);
		}
		//修改线上支付申请表
		B2bOnlinepayRecordDto onlineRecord =  onlinepayService.getByOrderNumer(odto.getOrderNumber());
		boolean flag = true;
		if(null == onlineRecord){
			onlineRecord = new B2bOnlinepayRecordDto();
			flag = false;
		}
		onlineRecord.setOrderNumber(odto.getOrderNumber());
		onlineRecord.setStatus(1);
		onlineRecord.setSerialNumber(serialNumber);
		onlineRecord.setPurchaserPk(odto.getPurchaser().getPurchaserPk());
		onlineRecord.setPurchaserName(odto.getPurchaser().getPurchaserName());
		onlineRecord.setSupplierName(odto.getSupplier().getSupplierName());
		onlineRecord.setSupplierPk(odto.getSupplierPk());
		onlineRecord.setOrderAmount(odto.getActualAmount());
		onlineRecord.setInsertTime(new Date());
		onlineRecord.setReceivablesAccount(card.getBankAccount());
		onlineRecord.setReceivablesAccountName(odto.getSupplier().getSupplierName());
		onlineRecord.setOnlinePayGoodsPk(onlinePayGoods.getPk());
		onlineRecord.setOnlinePayGoodsName(onlinePayGoods.getName());
		onlineRecord.setShotName(onlinePayGoods.getShotName());
		onlineRecord.setReturnUrl(returnUrl);
		B2bOnlinepayRecord record = new B2bOnlinepayRecord();
		record.UpdateDTO(onlineRecord);
		if(flag){
			onlinepayService.updateOnlineRecord(record);
		}else{
			onlinepayService.insertOnlineRecord(record);
		}
		//更新订单日志
		OrderRecord or = new OrderRecord();
		or.setId(KeyUtils.getUUID());
		or.setOrderNumber(odto.getOrderNumber());
		or.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		String content = OrderRecordType.PAYMENT.toString();
		content = content.replace("${orderNumber}",
				odto.getOrderNumber());
		content += "支付方式:"+paymentName+onlinePayGoods.getName()
				+ ",支付金额:"
				+ ArithUtil.roundBigDecimal(new BigDecimal(
						odto.getActualAmount()), 2);
		or.setContent(content);
		or.setInsertTime(DateUtil.getDateFormat(new Date()));
		or.setStatus(2);//确认收款
		mongoTemplate.save(or);		
	}
	@Override
	public void sureLoan(B2bLoanNumberDto o,
			B2bCreditGoodsDto gdto) {
		if(null != o){
			B2bLoanNumber number = new B2bLoanNumber();
			number.setOrderNumber(o.getOrderNumber());
			number.setLoanStatus(3);
			b2bLoanNumberDao.updateByOrderNumber(number);
		}
		if(null != gdto){
//			gdto.setPledgeUsedAmount(ArithUtil.round(ArithUtil.add(null==gdto.getPledgeUsedAmount()?0d:gdto.getPledgeUsedAmount(), o.getLoanAmount()), 2));
			gdto.setPledgeUsedAmount(b2bLoanNumberService.searchSumLoan(o.getPurchaserPk(), gdto.getGoodsType()));
			b2bCreditGoodsService.updateByCreditGoods(gdto);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNumber", o.getOrderNumber());
		map.put("status", 1);
		financeRecordsDaoEx.updateStatus(map);
		// 存订单日志(放款成功)
		OrderRecord orone = new OrderRecord();
		orone.setId(KeyUtils.getUUID());
		orone.setOrderNumber(o.getOrderNumber());
		String content = OrderRecordType.UPDATE.toString();
		content = content.replace("${orderNumber}", o.getOrderNumber());
		content += o.getBankName()+"放款成功,放款金额:" + new BigDecimal(o.getLoanAmount().toString()).toPlainString();
		orone.setContent(content);
		orone.setInsertTime(DateUtil.getDateFormat(new Date()));
		mongoTemplate.save(orone);	
	}
	@Override
	public void updateBackBillOrder(B2bOrderDtoMa om, String contractNo,
			String paymentName, Integer paymentType, String serialNumber,String returnUrl,
			SysCompanyBankcardDto card, B2bBillCusgoodsDto gdto,String mesgid,Integer paytype) {
		String orderNumber = null;
		Integer type = 1;
		try {
			//更新订单表信息
			if("".equals(contractNo)){
				orderNumber = om.getOrderNumber();
				B2bOrder order =  new B2bOrder();
				order.setOrderNumber(orderNumber);
				order.setPaymentType(paymentType);
				order.setPaymentName(paymentName);
				order.setEconomicsGoodsType(paymentType);
				order.setEconomicsGoodsName(gdto.getGoodsName());
				order.setOrderStatus(2);
				order.setPaymentTime(new Date());
				order.setOwnAmount(om.getActualAmount());
				foreignOrderService.updateOrderStatus(order,card);
			}else{
				orderNumber = contractNo;
				B2bContract contract = new B2bContract();
				contract.setContractNo(contractNo);
				contract.setPaymentType(paymentType);
				contract.setPaymentName(paymentName);
				contract.setEconomicsGoodsName(gdto.getGoodsName());
				contract.setEconomicsGoodsType(paymentType);
				contract.setContractStatus(2);
				contract.setPaymentTime(new Date());
				contract.setOwnAmount(om.getActualAmount());
				foreignOrderService.updateContractStatus(contract,card);
				type = 2;
			}
		} catch (Exception e) {
			logger.error("callBackOrderError",e);
		}
		B2bBillOrder billOrder = new B2bBillOrder();
		billOrder.setOrderNumber(om.getOrderNumber());
		billOrder.setSerialNumber(serialNumber);
		if(BillType.PFT.equals(gdto.getGoodsShotName())){
			billOrder.setStatus(paytype==1?4:1);//票付通见证支付为4(锁定状态) 、非见证支付为1(处理中状态)
			billOrder.setGoodsShotName(paytype==0?BillType.PFT_1:gdto.getGoodsShotName());
		}else{
			billOrder.setStatus(1);
			billOrder.setGoodsShotName(gdto.getGoodsShotName());
		}
		billOrder.setAmount(om.getActualAmount());
		billOrder.setBillAmount(0d);
		billOrder.setPurchaserPk(om.getPurchaserPk());
		billOrder.setPurchaserName(om.getPurchaser().getPurchaserName());
		billOrder.setSupplierPk(om.getSupplierPk());
		billOrder.setSupplierName(om.getSupplier().getSupplierName());
		billOrder.setStorePk(om.getStorePk());
		billOrder.setStoreName(om.getStoreName());
		billOrder.setGoodsPk(gdto.getGoodsPk());
		billOrder.setGoodsName(gdto.getGoodsName());
		billOrder.setReturnUrl(null!=returnUrl?returnUrl.replace(mesgid, "${hxh_mesgid}"):null);
		billOrder.setPayeeOrzaCode(om.getPurchaser().getOrganizationCode());
		billOrder.setPayerOrzaCode(om.getSupplier().getOrganizationCode());
		billOrder.setPayerAccount(gdto.getAccount());
		billOrder.setPayerBankNo(gdto.getBankNo());
		billOrder.setPayeeAccount(card.getBankAccount());
		billOrder.setPayeeBankNo(card.getBankNo());
		billOrder.setType(type);
		billOrderService.insertBillOrder(billOrder);
		//贴现更新客户额度
		if(BillType.TX.equals(gdto.getGoodsShotName())){
			gdto.setUseAmount(ArithUtil.add(null==gdto.getUseAmount()?0d:gdto.getUseAmount(), om.getActualAmount()));
			billCusGoodsService.updateUseAmount(gdto);
		}
		//更新订单日志
		OrderRecord or = new OrderRecord();
		or.setId(KeyUtils.getUUID());
		or.setOrderNumber(om.getOrderNumber());
		or.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		String content = OrderRecordType.PAYMENT.toString();
		content = content.replace("${orderNumber}",
				om.getOrderNumber());
		content += "支付方式:"+paymentName+(billOrder.getGoodsShotName().equals(BillType.PFT_1)?BillType.PFT_1_NAME:gdto.getGoodsName())
				+ ",支付金额:"
				+ ArithUtil.roundBigDecimal(new BigDecimal(
						om.getActualAmount()), 2);
		or.setContent(content);
		or.setInsertTime(DateUtil.getDateFormat(new Date()));
		or.setStatus(2);//确认收款
		mongoTemplate.save(or);			
	}
	@Override
	public void updateBackBillOrderInventory(B2bBillOrderDto order,
			List<B2bBillInventoryDto> list) {
		B2bBillOrder billOrder = new B2bBillOrder();
		billOrder.UpdateDTO(order);
		Double billAmount =0d;
		//1删除所有票据
		billInventoryService.deleteInventory(order.getOrderNumber());
		//2取消状态修改票据金额为'0'
		if(null != billOrder.getStatus() && billOrder.getStatus() == 3){
			billOrder.setBillAmount(billAmount);
		}else{
			//2.1其余状态更新票据信息
			if(null != list && list.size() >0){
				for(B2bBillInventoryDto dto : list){
					B2bBillInventory inventory = new B2bBillInventory();
					inventory.UpdateDTO(dto);
					inventory.setPk(KeyUtils.getUUID());
					inventory.setOrderNumber(order.getOrderNumber());
					billInventoryService.insertInventory(inventory);
					if(null != dto.getStatus() && (dto.getStatus()==1 || dto.getStatus() == 2 || dto.getStatus() == 3)){
						billAmount +=null==inventory.getAmount()?0d:inventory.getAmount();
					}
				}
				//2.2计算票据总金额
				billOrder.setBillAmount(ArithUtil.round(billAmount, 2));
			}
		}
		//3 票付通产品（处理中或者已关闭的状态下）且没有票据信息的将流水号置空
		if((BillType.PFT.equals(order.getGoodsShotName()) || BillType.PFT_1.equals(order.getGoodsShotName()))
				&& (null == list || list.size() ==0) && (-1 !=order.getStatus())){
			billOrder.setSerialNumber(null);
		}
		billOrderService.updateBillOrder(billOrder);
		//4 贴现取消或者完成修改已使用额度
		if(BillType.TX.equals(order.getGoodsShotName()) && (billOrder.getStatus() == 2 || billOrder.getStatus() == 3)){
			finishTx(order, billOrder);
		}
		//5.1成功存交易记录(含贴现订单)
		if(order.getStatus() == 2 && null !=order.getType() && 3!=order.getType()){
			successAll(order);
		}
		//5.2成功存交易记录(只限贴现)
		if(order.getStatus() == 2 && null !=order.getType() && 3==order.getType()){
			successTx(order);
		}
	}
	private void finishTx(B2bBillOrderDto order, B2bBillOrder billOrder) {
		B2bBillCusgoodsDto cgdto = billCusGoodsService.getByCompanyPk(order.getGoodsPk(), order.getPurchaserPk());
		if(null != cgdto && null != cgdto.getUseAmount()){
			//已使用额度=客户剩余额度+订单已扣额度-实际使用额度
			cgdto.setUseAmount(ArithUtil.add(ArithUtil.sub(cgdto.getUseAmount(), order.getAmount()),
					null ==billOrder.getBillAmount()?0d:billOrder.getBillAmount())<0d?0d:
						ArithUtil.add(ArithUtil.sub(cgdto.getUseAmount(), order.getAmount()),
								null ==billOrder.getBillAmount()?0d:billOrder.getBillAmount()));
			billCusGoodsService.updateUseAmount(cgdto);
		}
	}
	private void successAll(B2bBillOrderDto order) {
		List<B2bBillInventoryDto> inventoryList = billInventoryService.searchByMap(order.getOrderNumber(), 3);
		if(null != inventoryList && inventoryList.size()>0){
			B2bOrderDtoMa om = null;
			if(order.getType() == 1){
				om = foreignOrderService.getOrder(order.getOrderNumber());
			}else{
				om = foreignOrderService.getContract(order.getOrderNumber());
			}
			if(null != om){
				
				String purDes = "订单号:" + om.getOrderNumber()
						+ "<br>供应商账户:"
						+ om.getSupplier().getBankAccount()
						+ "<br>供应商名称:" + om.getSupplier().getSupplierName()+"<br>";
				String supDes = "订单号:" + om.getOrderNumber()
						+ "<br>采购商名称:" + om.getPurchaser().getPurchaserName()
						+ "<br>收款账户:" + om.getSupplier().getBankAccount()
						+ "<br>收款银行:" + om.getSupplier().getBankName()+"<br>";
				//存采购商、供应商成功记录
				String details = "票据信息:<br>";
				for (int i = 0; i < inventoryList.size(); i++) {
					if(i==0){
						details+=inventoryList.get(i).getBillNumber();
					}else{
						details+="<br>"+inventoryList.get(i).getBillNumber();
					}
				}
				financeRecordsService.insertFinanceRecords(order.getOrderNumber(), order.getBillAmount(), order.getPurchaserPk(), order.getPurchaserName(), order.getSupplierPk(), order.getSupplierName(), 1, 1, purDes+details, order.getSerialNumber(), null, om.getEmployeePk(), om.getEmployeeName(), om.getEmployeeNumber());
				financeRecordsService.insertFinanceRecords(order.getOrderNumber(), order.getBillAmount(), order.getSupplierPk(), order.getSupplierName(), order.getPurchaserPk(), order.getPurchaserName(), 1, 5, supDes+details, order.getSerialNumber(), null, om.getEmployeePk(), om.getEmployeeName(), om.getEmployeeNumber());
			}
		}
	}
	
	private void successTx(B2bBillOrderDto order) {
		List<B2bBillInventoryDto> inventoryList = billInventoryService.searchByMap(order.getOrderNumber(), 3);
		if(null != inventoryList && inventoryList.size()>0){
				
				String purDes = "贴现流水号:" + order.getOrderNumber();
				//存采购商、供应商成功记录
				String details = "<br>票据信息:<br>";
				for (int i = 0; i < inventoryList.size(); i++) {
					if(i==0){
						details+=inventoryList.get(i).getBillNumber();
					}else{
						details+="<br>"+inventoryList.get(i).getBillNumber();
					}
				}
				financeRecordsService.insertFinanceRecords(order.getOrderNumber(), order.getBillAmount(), order.getPurchaserPk(), order.getPurchaserName(), order.getSupplierPk(), order.getSupplierName(), 1, 6, purDes+details, order.getSerialNumber(), null, null, null, null);
		}
	}
	@Override
	public void updateReport(String companyPk,String companyName,String shotName, String data, String fileUrl) {
		Criteria c = new Criteria();
		c.and("shotName").is(shotName);
		c.and("companyPk").is(companyPk);
		CreditReportInfo info = mongoTemplate.findOne(new Query(c), CreditReportInfo.class);
		if(null ==info){
			info = new CreditReportInfo();
			info.setId(KeyUtils.getUUID());
		}
		info.setCompanyPk(companyPk);
		info.setCompanyName(companyName);
		info.setDetail(data);
		info.setFileUrl(fileUrl);
		info.setShotName(shotName);
		mongoTemplate.save(info);
	}
	@Override
	public void customerBindPftAccount(String companyPk, List<B2bBillSigncardDto> list) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("goodsShotName",BillType.PFT);
		cusGoodsService.updateBindStatus( companyPk,BillType.PFT,1);
		if(null != list && list.size()>0){
			for(B2bBillSigncardDto dto : list){
				List<B2bBillSigncardDto> oldList = billSigncardService.getByCompanyAndCard(companyPk,null, dto.getBankAccount(), dto.getBankName());
				if(null != oldList && oldList.size()>0){
					continue;
				}
				billSigncardService.insert(dto);
			}
		}
		
	}
	@Override
	public void updateAmount(B2bBillGoodsDto dto) {
		B2bBillGoodsDto gdto = billGoodsService.getByShotName(BillType.TX);
		if(null != gdto){
			B2bBillGoods goods = new B2bBillGoods();
			goods.UpdateDTO(gdto);
			goods.setPlatformAmount(dto.getPlatformAmount());
			goods.setPlatformUseAmount(dto.getPlatformUseAmount());
			billGoodsService.update(goods);
			//更新全部已使用额度
			cusGoodsService.cleanUseAmount();
		}
	}
	@Override
	public void customerSearchPftAccount(B2bBillCusgoodsDtoEx cus,
			List<B2bBillSigncardDto> list) {
		if(null !=cus && null !=cus.getBankNo() &&
				null != cus.getAccount() && null != list && list.size()>0 ){
			for(B2bBillSigncardDto dto : list){
				if(null == dto.getBankNo() || null == dto.getBankAccount()
						|| "".equals(dto.getBankNo()) || "".equals(dto.getBankAccount())){
					continue;
				}
				if(dto.getBankNo().equals(cus.getBankNo()) && dto.getBankAccount().equals(cus.getAccount())){
					B2bBillSigncardDto gdto = new B2bBillSigncardDto();
					gdto.setCompanyPk(cus.getCompanyPk());
					gdto.setBankAccount(cus.getAccount());
					gdto.setBankName(cus.getBankName());
					gdto.setBankNo(cus.getBankNo());
					gdto.setStatus(dto.getStatus());
					billSigncardService.insertOrUpdate(gdto);
					billCusGoodsService.updateBindStatus(cus.getCompanyPk(), BillType.PFT, dto.getStatus());
				}
			}
		}
		
	}
	@Override
	public void supplierSearchPftAccount(B2bBillSigningDto sign,
			List<B2bBillSigncardDto> list) {
		if(null !=sign && null != list && list.size()>0 ){
			List<B2bBillSigncardDto> oldCards = billSigncardService.getByCompanyAndCard(sign.getCompanyPk(),1, null, null);
			if(null != oldCards && oldCards.size()>0){
				billSigncardService.update(list, 2);
				Boolean flag = true;
				ko:for(B2bBillSigncardDto odto : oldCards){
					for (int i = 0; i < list.size(); i++) {
						if(odto.getBankAccount().equals(list.get(i).getBankAccount()) 
								&& odto.getBankNo().equals(list.get(i).getBankNo())){
							break;
						}
						//如果一个都没有匹配上
						if(i == list.size()-1){
							flag = false;
							break ko;
						}
					}
				}
				//更新供应商签约状态
				if(flag){
					sign.setStatus(2);
					billSingingService.updateByDto(sign);
				}
			}
		}
	}
	@Override
	public void supplierBindPftAccount(B2bBillSigningDto sign,
			List<B2bBillSigncardDto> list) {
		if(null != list && list.size()>0){
			billSigncardService.update(list, 1);
			sign.setStatus(1);
			billSingingService.updateByDto(sign);
		}
		
	}
	@Override
	public void unBindPftAccount(String companyPk, List<B2bBillSigncardDto> list) {
		billCusGoodsService.updateBindStatus(companyPk, BillType.PFT, 0);
		
	}
	@Override
	public void updateBackTx(B2bOrderDtoMa om,B2bBillCusgoodsDto gdto,String serialNumber) {
		B2bBillOrder billOrder = new B2bBillOrder();
		billOrder.setOrderNumber(om.getOrderNumber());
		billOrder.setSerialNumber(serialNumber);
		billOrder.setStatus(1);
		billOrder.setGoodsShotName(gdto.getGoodsShotName());
		billOrder.setAmount(om.getActualAmount());
		billOrder.setBillAmount(0d);
		billOrder.setPurchaserPk(om.getPurchaserPk());
		billOrder.setPurchaserName(om.getPurchaser().getPurchaserName());
		billOrder.setGoodsPk(gdto.getGoodsPk());
		billOrder.setGoodsName(gdto.getGoodsName());
		billOrder.setPayeeOrzaCode(om.getPurchaser().getOrganizationCode());
		billOrder.setPayerAccount(gdto.getAccount());
		billOrder.setPayerBankNo(gdto.getBankNo());
		billOrder.setType(3);
		billOrderService.insertBillOrder(billOrder);
		//贴现更新客户额度
		if(BillType.TX.equals(gdto.getGoodsShotName())){
			gdto.setUseAmount(ArithUtil.add(null==gdto.getUseAmount()?0d:gdto.getUseAmount(), om.getActualAmount()));
			billCusGoodsService.updateUseAmount(gdto);
		}
		
	}
}
