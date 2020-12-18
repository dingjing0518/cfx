package cn.cf.task;

import cn.cf.common.OrderRecordType;
import cn.cf.common.RestCode;
import cn.cf.constant.BillType;
import cn.cf.constant.MemberSys;
import cn.cf.constant.SmsCode;
import cn.cf.dao.B2bCreditDaoEx;
import cn.cf.dao.ManageAccountDao;
import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bFinanceRecordsDtoEx;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bLoanNumberDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.OrderRecord;
import cn.cf.entry.BankInfo;
import cn.cf.entry.BillInfo;
import cn.cf.model.B2bLoanNumber;
import cn.cf.model.B2bOrder;
import cn.cf.service.CommonService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.bill.BillCusGoodsService;
import cn.cf.service.bill.BillOrderService;
import cn.cf.service.bill.BillSingingService;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.common.HuaxianhuiBillService;
import cn.cf.service.common.HuaxianhuiReportService;
import cn.cf.service.creditpay.BankGuangdaService;
import cn.cf.service.foreign.B2bMemberService;
import cn.cf.service.foreign.ForeignBankService;
import cn.cf.service.foreign.ForeignOrderService;
import cn.cf.service.onlinepay.OnlinepayService;
import cn.cf.service.platform.B2bCreditService;
import cn.cf.service.platform.B2bEconomicsBankCompanyService;
import cn.cf.service.platform.B2bLoanNumberService;
import cn.cf.service.platform.FinanceRecordsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.InterestUtil;
import cn.cf.util.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:FJM
 * @describe:定时任务类
 * @time:2017-12-15 上午12:48:54
 */
@Component
public class Scheduler {

	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;
	
	@Autowired
	private BankGuangdaService bankGuangdaService;

	@Autowired
	private B2bCreditDaoEx b2bCreditDaoEx;
	
	@Autowired
	private ManageAccountDao accountDao;
	
	@Autowired
	private ForeignOrderService foreignOrderService;
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	
	@Autowired
	private ForeignBankService foreignBankService;
	
	@Autowired
	private FinanceRecordsService financeRecordsService;
	
	@Autowired
	private B2bCreditService creditService;
	
	
	@Autowired
	private B2bEconomicsBankCompanyService economicsBankCompanyService;
	
	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private OnlinepayService onlinepayService;
	@Autowired
	private  B2bEconomicsBankCompanyService b2bEconomicsBankCompanyService;
	
	@Autowired
	private HuaxianhuiReportService huaxianhuiReportService;
	
	@Autowired
	private BillOrderService billOrderService;
	
	@Autowired
	private HuaxianhuiBillService huaxianhuiBillService;
	
	@Autowired
	private BillCusGoodsService cusGoodsService;
	
	@Autowired
	private BillSingingService billSingingService;
	@Autowired
	private CuccSmsService  commonSmsService;
	 
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 执行时间:每天凌晨0:00执行一次 
	 * 定时任务:关闭所有申请中的订单
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void cancelOrder() {
		List<B2bLoanNumberDto> list = b2bLoanNumberService.searchB2bLoanNumberDtoList();
		B2bOrder order = new B2bOrder();
		if(null != list && list.size()>0){
			for(B2bLoanNumberDto o : list){
				if(null == o.getBankName() || "".equals(o.getBankName())){
					continue;
				}
				try {
					Map<String,Object> map =  foreignBankService.delLoanOrder(o.getOrderNumber());
					//如果取消成功
					if(RestCode.CODE_0000.getCode().equals(map.get("code").toString())){
						logger.error("task delLoanOrder success:bankName is {},orderNumber is {}", o.getBankName(), o.getOrderNumber());
						cancelOrder(order, o.getOrderNumber());
					}
				} catch (Exception e) {
					logger.error("task delLoanOrder success:bankName is {},orderNumber is {}", o.getBankName(), o.getOrderNumber());
					logger.error("closeUnLoanOrders:"+o.getOrderNumber(),e);
				}
			}
		}
		List<B2bBillOrderDto> billOrderList = billOrderService.searchCancelBillOrder();
		if(null != billOrderList && billOrderList.size()>0){
			for(B2bBillOrderDto o : billOrderList){
				//取消票付通见证方式未绑票的以及票付通非见证方式和贴现方式
				if((BillType.PFT.equals(o.getGoodsShotName()) && 
						(null == o.getSerialNumber() || "".equals(o.getSerialNumber())))
						|| BillType.PFT_1.equals(o.getGoodsShotName()) || BillType.TX.equals(o.getGoodsShotName())){
					try {
						Map<String,Object> map = huaxianhuiBillService.billPayCancel(o);
						//如果取消成功
						if(RestCode.CODE_0000.getCode().equals(map.get("code").toString())){
							cancelOrder(order, o.getOrderNumber());
						}
					} catch (Exception e) {
						logger.error("closeUnBillOrders:"+o.getOrderNumber(),e);
					}
				}
			}
		}
	}
	private void cancelOrder(B2bOrder order, String orderNumber) {
		B2bOrderDtoMa odto = foreignOrderService.getOrder(orderNumber);
		//如果订单还未确认收款  取消订单
		if(null != odto && null != odto.getOrderStatus() && 2 == odto.getOrderStatus()){
			order.UpdateDTO(odto);
			order.setOrderStatus(-1);
			foreignOrderService.updateOrderStatus(order,null);
			//更新订单日志
			OrderRecord or = new OrderRecord();
			or.setId(KeyUtils.getUUID());
			or.setOrderNumber(orderNumber);
			or.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			String content = OrderRecordType.UPDATE.toString();
			content = content.replace("${orderNumber}",
					odto.getOrderNumber());
			content += "订单号:" + orderNumber + ", 自动取消";
			or.setContent(content);
			or.setInsertTime(DateUtil.getDateFormat(new Date()));
			or.setStatus(-1);//订单取消
			mongoTemplate.save(or);
		}
	}
	/**
	 * 执行时间:每天凌晨0:10执行一次 
	 * 定时任务:更新光大银行密钥
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	public void appKeySecret() {
		
		bankGuangdaService.appMacKeyValue();
	}
	
	/**
	 * 执行时间:凌晨0点20分执行一次
	 * 定时任务:删除3个月以前银行日志数据
	 */
	@Scheduled(cron = "0 20 * * * ?")
	public void deleteBankInfo() {

		b2bEconomicsBankCompanyService.deleteBankInfo();
		//删除票据日志
		this.removeBillDate();
	}

	/**
	 * 执行时间:每天凌晨1点15分执行一次 
	 * 定时任务:更新见证支付票付通锁定状态的订单
	 */
	@Scheduled(cron = "0 15 1 * * ?")
	public void billPftPaySearch() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 4);
		List<B2bBillOrderDto> list = billOrderService.searchListByMap(map);
		if(null != list && list.size()>0){
			for(B2bBillOrderDto dto : list){
				B2bOrderDtoMa om = null;
				if(dto.getType()==1){
					 om = foreignOrderService.getOrder(dto.getOrderNumber());
				}else{
					 om = foreignOrderService.getContract(dto.getOrderNumber());
				}
				//已完成的执行
				if(null != om && om.getOrderStatus() == 6){
					huaxianhuiBillService.searchBillInfo(dto.getOrderNumber());
				}
			}
		}
	}

	/**
	 * 执行时间:凌晨2点执行一次
	 * 定时任务:更新mongoDb还款数据
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void updateRepaymentData() {

		List<B2bRepaymentRecord> list = mongoTemplate.findAll(B2bRepaymentRecord.class);
		if(null != list && list.size()>0){
			Update update = new Update();
			for(B2bRepaymentRecord r : list){
				B2bLoanNumberDto o = b2bLoanNumberService.getB2bLoanNumberDto(r.getOrderNumber());
				if(null != o){
					update.set("goodsType",o.getEconomicsGoodsType());
					update.set("goodsName",o.getEconomicsGoodsName());
				}
				update.set("creditTime", r.getCreateTime().replaceAll("-|/", ""));
				mongoTemplate.upsert(new Query(Criteria.where("id").is(r.getId())),
						update, B2bRepaymentRecord.class);
			}
		}
	}
	
	/**
	 * 执行时间:每天凌晨3:00执行一次 
	 * 定时任务:查询银行是否授额给会员加积分
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	// 每天凌晨3点执行一次
	public void updateCreditBySuzhouBank() {
		List<B2bCreditDto> list = creditService.searchCreditList();
		if(null != list && list.size()>0){
			for(B2bCreditDto dto : list){
				List<B2bEconomicsBankCompanyDto> bankList = economicsBankCompanyService.searchList(dto.getCompanyPk());
				if(null != bankList && bankList.size()>0){
					for(B2bEconomicsBankCompanyDto bank : bankList){
						if(null == bank.getType()){
							continue;
						}
						//化纤白条
						if(bank.getType() == 1){
							//发起http请求
//							Map<String,String> paraMap=new HashMap<>();
//							paraMap.put("memberPk", dto.getMemberPk());
//							paraMap.put("companyPk", dto.getCompanyPk());
//							paraMap.put("pointType", "1");
//							paraMap.put("active", MemberSys.ACCOUNT_DIMEN_BLNOTE.getValue());
//							paraMap.put("flag", "true");
//							HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/addPoint2", paraMap);
							//memberPointService.addPoint2(dto.getMemberPk(), dto.getCompanyPk(), 1, MemberSys.ACCOUNT_DIMEN_BLNOTE.getValue(), true);
							commonService.addPointForMember(dto.getMemberPk(), dto.getCompanyPk(), 1, MemberSys.ACCOUNT_DIMEN_BLNOTE.getValue(), true);
						}
						//化纤贷
						if(bank.getType() == 2){
							//发起http请求
//							Map<String,String> paraMap=new HashMap<>();
//							paraMap.put("memberPk", dto.getMemberPk());
//							paraMap.put("companyPk", dto.getCompanyPk());
//							paraMap.put("pointType", "1");
//							paraMap.put("active", MemberSys.ACCOUNT_DIMEN_FIBER.getValue());
//							paraMap.put("flag", "true");
//							HttpHelper.doPost(PropertyConfig.getProperty("api_member_url")+"member/addPoint2", paraMap);
							commonService.addPointForMember(dto.getMemberPk(), dto.getCompanyPk(), 1, MemberSys.ACCOUNT_DIMEN_FIBER.getValue(), true);
							//memberPointService.addPoint2(dto.getMemberPk(), dto.getCompanyPk(), 1, MemberSys.ACCOUNT_DIMEN_FIBER.getValue(), true);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 执行时间:每1小时执行一次 
	 * 定时任务:更新苏州银行还款记录
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void updateRepayment() {
		// 查询所有是否有未还款的订单
		List<B2bLoanNumberDtoEx> list = b2bLoanNumberService.searchB2bRepaymentDtoList(null);
		if(null != list && list.size()>0){
			for(B2bLoanNumberDtoEx o : list){
				try {
					huaxianhuiBankService.updateRepaymentDetails(o);
				}catch (Exception e){
					logger.error("errorOrder"+o.getOrderNumber(),e);
				}
			}
		}
	}

	/**
	 * 执行时间:每天早晨9:00执行一次 
	 * 定时任务:通知授信快到期的客户
	 */
	@Scheduled(cron = "0 0 9 * * ?")
	// 每天早晨9点执行一次
	public void creditExpirationReminder() {
		//查询授信金融产品小于两天的用户
		List<B2bCreditDtoEx> list = b2bCreditDaoEx.getCreditExpirationReminder();
		if (list.size() > 0) {
			for (B2bCreditDtoEx c : list) {
				B2bMemberDto mdto = b2bMemberService.getMember(c.getCreditContactsTel());
				if(null != mdto){
					commonSmsService.sendMSM(mdto, mdto.getMobile(), SmsCode.CREDIT_EXP.getValue(), null);
				}
			}
		}
	}

	/**
	 * 执行时间:每天早晨10:00执行一次 
	 * 定时任务:提前2天提醒客户还款(限授信成功与部分还款的订单(数据库对应字段 loanStatus 对应值3:申请成功 、 6:部分还款))
	 */
	@Scheduled(cron = "0 0 10 * * ?")
	// 每天早晨9点执行一次
	public void repaymentReminder() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", "-1");
		List<B2bLoanNumberDtoEx> list = b2bLoanNumberService.searchB2bRepaymentDtoList(map);
		if (list.size() > 0) {
			B2bCreditDto credit = null;
			String accountMobile= null;
			for(B2bLoanNumberDtoEx o : list){
				credit = creditService.getCredit(o.getPurchaserPk(), null);
				if(null == credit){
					continue;
				}
				ManageAccountDto account = accountDao.getByPk(credit.getFinancePk());
				if(null == account){
					accountMobile = "";
				}else if(null==account.getMobile() || "".equals(account.getMobile())){
					accountMobile = "";
				}else{
					accountMobile = account.getMobile();
				}
				String[] date = DateUtil.getDateYearMonthDay(o.getLoanEndTime());
				Double coverCharges = ArithUtil.round(InterestUtil.getCoverCharges(DateUtil.formatYearMonthDay(o.getLoanStartTime()), DateUtil.formatYearMonthDay(new Date()), ArithUtil.round(o.getOtherAmount(), 2), o.getTotalRate(), 0d, o.getSevenRate(), o.getEconomicsGoodsType()), 2);
//				msg = "尊敬的"+o.getPurchaserName()+"在化纤汇平台有"+ArithUtil.round(o.getOtherAmount(), 2)+"元的化纤白条/化纤贷产品（合同号："+o.getLoanNumber()+"）将在"+date[0]+"年"+date[1]+"月"+date[2]+"日到期，利息服务费"+coverCharges+"元，请做好资金的合理安排。（如已提前还款，请忽略此信息）如有疑问联系"+credit.getFinanceContacts()+accountMobile+"回复TD拒收。";
				if(null != credit && null != credit.getCreditContactsTel() && !"".equals(credit.getCreditContactsTel())){
					Map<String,String> smsMap = new HashMap<String,String>();
					smsMap.put("pname", o.getPurchaserName() );
					smsMap.put("money", String.valueOf(ArithUtil.round(o.getOtherAmount(), 2)) );
					smsMap.put("order_id",o.getLoanNumber()  );
					smsMap.put("endtime",date[0]+"年"+date[1]+"月"+date[2]+"日"  );
					smsMap.put("interest",String.valueOf(coverCharges)   );
					smsMap.put("accountMobile", credit.getFinanceContacts()+accountMobile);
					commonSmsService.sendMSM(null, credit.getCreditContactsTel(),  SmsCode.REM_REAPY_NEW.getValue(), smsMap);
				}
			}
		}
	}

	/**
	 * 执行时间:每10分钟执行一次
	 * 定时任务:查询所有授信订单是否有做过放款处理
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void creditResultSearch() {
		//查询所有当天未收到放款通知的授信订单
		List<B2bLoanNumberDto> list  = b2bLoanNumberService.searchB2bLoanNumberDtoList();
		if(null != list && list.size()>0){
			for(B2bLoanNumberDto o : list){
				huaxianhuiBankService.updateLoanDetails(o);
			}
		}
	}
	
	/**
	 * 执行时间:每10分钟执行一次 
	 * 定时任务:根据提款记录更新交易状态
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void updateFinanceRecords() {
		//查询交易记录在处理中的数据
		List<B2bFinanceRecordsDtoEx> list  = financeRecordsService.searchUnsuccessRecords();
		if(null != list && list.size()>0){
			for(B2bFinanceRecordsDtoEx dto : list){
				try {
					huaxianhuiBankService.updateFinanceRecords(dto.getOrderNumber());
				} catch (Exception e) {
					logger.error("errorUpdateFinanceRecords",e);
				}
			}
		}
	}
	
	/**
	 * 执行时间:每1小时执行一次 
	 * 定时任务:同步代扣支付成功状态
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void updateAgentPayStatus() {
		Query qu = new Query(Criteria.where("agentPayStatus").is(
				1));
		List<B2bRepaymentRecord> list = mongoTemplate.find(qu,
				B2bRepaymentRecord.class);
		if(null != list && list.size()>0){
			for(B2bRepaymentRecord dto : list){
				huaxianhuiBankService.updateAgentQry(null, dto.getId());
			}
		}
	}
	
	/**
	 * 执行时间:每1分钟执行一次 
	 * 定时任务:微众下载
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void downLoadWz() {
		huaxianhuiReportService.downLoadRayxFile();
		huaxianhuiReportService.downLoadWzFile();
	}
	
	/**
	 * 执行时间:每10分钟执行一次 
	 * 定时任务:线上支付记录
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void onlinePay() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		List<B2bOnlinepayRecordDto> list = onlinepayService.searchList(map);
		if(null != list && list.size()>0){
			for(B2bOnlinepayRecordDto dto : list){
				huaxianhuiBankService.onlinePaySearch(dto);
			}
		}
	}

	/**
	 * 执行时间:每10分钟执行一次
	 * 定时任务:更新票据支付记录
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void billPaySearch() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		List<B2bBillOrderDto> list = billOrderService.searchListByMap(map);
		if(null != list && list.size()>0){
			for(B2bBillOrderDto dto : list){
				try {
					huaxianhuiBillService.searchBillInfo(dto.getOrderNumber());
				} catch (Exception e) {
					logger.error("searchBillInfo",e);
				}
			}
		}
	}
	
	/**
	 * 执行时间:每10分钟执行一次
	 * 定时任务:更新票付通最终结果记录
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void billFinishSearch() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 5);
		List<B2bBillOrderDto> list = billOrderService.searchListByMap(map);
		if(null != list && list.size()>0){
			for(B2bBillOrderDto dto : list){
				huaxianhuiBillService.searchPayInfoPft(dto.getOrderNumber());
			}
		}
		map.put("status", -1);
		List<B2bBillOrderDto> listtwo = billOrderService.searchListByMap(map);
		if(null != listtwo && listtwo.size()>0){
			for(B2bBillOrderDto dto : listtwo){
				huaxianhuiBillService.searchPayInfoPft(dto.getOrderNumber());
			}
		}
		map.put("status", 4);
		List<B2bBillOrderDto> listthree = billOrderService.searchListByMap(map);
		if(null != listthree && listthree.size()>0){
			for(B2bBillOrderDto dto : listthree){
				huaxianhuiBillService.searchPayInfoPft(dto.getOrderNumber());
			}
		}
	}
	
	/**
	 * 执行时间:每10分钟执行一次
	 * 定时任务:更新票付通客户产品
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void updateBillCusGoods() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bindStatus", 1);
		map.put("isVisable", 1);
		try {
			List<B2bBillCusgoodsDtoEx> list = cusGoodsService.searchBillCusGoodsList(map);
			if(null != list && list.size()>0){
				for(B2bBillCusgoodsDtoEx dto : list){
					huaxianhuiBillService.searchBillPft(dto);
				}
			}
		} catch (Exception e) {
			logger.error("errorbillcusgoods:",e);
		}
		try {
			map.put("status", 1);
			map.put("isDelete", 1);
			List<B2bBillSigningDto> signlist = billSingingService.getByMap(map);
			if(null != signlist && signlist.size()>0){
				for(B2bBillSigningDto sign:signlist){
					huaxianhuiBillService.searchBillSignPft(sign);
				}
			}
		} catch (Exception e) {
			logger.error("errorbillcusgoods:",e);
		}
	}
	/**
	 * 执行时间:每月1号执行一次
	 * 定时任务:更新贴现额度
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
	public void searchAmountTx() {
		huaxianhuiBillService.searchAmountTx();
	}


	private void removeBillDate(){
		Date dNow = new Date();   //当前时间
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, -3);  //设置为前3月
		Date	dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		Query query = new Query(
				Criteria.where("insertTime").lte(defaultStartDate));
		List<BillInfo> list=	mongoTemplate.find(query, BillInfo.class);
		if (list.size()>0) {
			for(BillInfo info : list){
				mongoTemplate.remove(info);
			}
		}

	}
}
