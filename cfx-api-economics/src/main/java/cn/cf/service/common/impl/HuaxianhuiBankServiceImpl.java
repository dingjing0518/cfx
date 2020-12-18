package cn.cf.service.common.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.cf.service.creditpay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.cf.common.BanksType;
import cn.cf.common.OnlineType;
import cn.cf.common.RestCode;
import cn.cf.constant.BillType;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.entity.CreditInfo;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.OrderGoodsDtoEx;
import cn.cf.entry.UnsynErpOrder;
import cn.cf.model.B2bOnlinepayRecord;
import cn.cf.property.PropertyConfig;
import cn.cf.service.bill.BillGoodsService;
import cn.cf.service.bill.BillOrderService;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.common.HuaxianhuiService;
import cn.cf.service.foreign.B2bTokenService;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.foreign.ForeignOrderService;
import cn.cf.service.onlinepay.OnlineGongshangService;
import cn.cf.service.onlinepay.OnlinepayService;
import cn.cf.service.platform.B2bCompanyBankcardService;
import cn.cf.service.platform.B2bCreditGoodsService;
import cn.cf.service.platform.B2bCreditService;
import cn.cf.service.platform.B2bEconomicsBankCompanyService;
import cn.cf.service.platform.B2bEconomicsBankService;
import cn.cf.service.platform.B2bEconomicsGoodsService;
import cn.cf.service.platform.B2bLoanNumberService;
import cn.cf.service.platform.FinanceRecordsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSONObject;

@Service
public class HuaxianhuiBankServiceImpl implements HuaxianhuiBankService {
	
	@Autowired
	private B2bEconomicsBankCompanyService economicsBankCompanyService;
	
	@Autowired
	private BankSuzhouService bankSuzhouService;
	
	@Autowired
	private BankGuangdaService bankGuangdaService;
	
	@Autowired
	private BankXingyeService bankXingyeService;
	
	@Autowired
	private BankGongshangService bankGongshangService;
	
	@Autowired
	private BankSunongService bankSunongService;
	
	@Autowired
	private BankZheshangService bankZheshangService;
	
	@Autowired
	private ForeignOrderService foreignOrderService;
	
	@Autowired
	private B2bCompanyBankcardService companyBankcardService;
	
	@Autowired
	private B2bCreditService creditService;
	
	@Autowired
	private HuaxianhuiService huaxianhuiService;
	
	@Autowired
	private B2bEconomicsBankService economicsBankService;
	
	@Autowired
	private B2bCreditGoodsService b2bCreditGoodsService;
	
	@Autowired
	private B2bEconomicsGoodsService economicsGoodsService;
	
	@Autowired
	private FinanceRecordsService financeRecordsService;
	
	@Autowired
	private B2bTokenService b2bTokenService;
	
	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ForeignCompanyService companyService;
	
	@Autowired
	private OnlinepayService onlinepayService;
	
	@Autowired
	private OnlineGongshangService onlineGongshangService;
	
	@Autowired
	private BankShanghaiService bankShanghaiService;
	
	@Autowired
	private BillOrderService billOrderService;
	
	@Autowired
	private BillGoodsService billGoodsService;
	
	@Autowired
	private BankJiansheService bankJiansheService;

	@Autowired
	private BankZhonghangService bankZhonghangService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Async
	@Override
	public void updateCreditAmount(B2bCompanyDto b2bCompanyDtoEx) {
		//先根据公司查询授信的金融产品
		List<B2bCreditGoodsDto> list = b2bCreditGoodsService.searchListGroupBank(b2bCompanyDtoEx.getPk());
		if(null != list && list.size()>0){
			for(B2bCreditGoodsDto dto : list){
				//调用不同的银行返回结果
				BankBaseResp resp = null;
				if(null == dto.getBank() || "".equals(dto.getBank())){
					logger.error("dto.getBank is {}", dto.getBank());
					continue;
				}
					switch (dto.getBank()) {
					case BanksType.bank_guangda:
						resp = 	bankGuangdaService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						break;
					case BanksType.bank_suzhou:
							resp = 	bankSuzhouService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						break;
					case BanksType.bank_xingye:
						resp = 	bankXingyeService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						break;
					case BanksType.bank_gongshang:
						resp = 	bankGongshangService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						break;
					case BanksType.bank_sunong:
						resp = 	bankSunongService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						break;
					case BanksType.bank_shanghai:
						resp = 	bankShanghaiService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						break;
					case BanksType.bank_jianshe:
						B2bCreditDto credit = creditService.getCredit(b2bCompanyDtoEx.getPk(), null);
						B2bCreditDtoMa cm = new B2bCreditDtoMa();
						cm.UpdateMine(credit);
						resp = 	bankJiansheService.searchBankCreditAmountUnique(cm,dto);
						break;
					case BanksType.bank_zhonghang:
						resp = 	bankZhonghangService.searchBankCreditAmount(b2bCompanyDtoEx,dto);
						logger.error("bank zhonghang: resp is {}", resp);
						break;
					default:
						break;
					}
				if(null != resp){
					//如果返回结果成功进行回调
					economicsBankCompanyService.updateCredit(b2bCompanyDtoEx.getPk(),dto.getBankPk(),resp.getEbcList());
				}
			}
		}
		
	}

	@Override
	public Map<String,Object> updateOrder(String orderNumber,String contractNo,String password,
			B2bCompanyDto dto, Integer paymentType, String paymentName,String creditGoodsPk,B2bMemberDto member) {
		String rest = null;
		Map<String, Object> map = new HashMap<String,Object>();
		boolean flag = true;
		B2bOrderDtoMa odto = null;
		SysCompanyBankcardDto card = null;
		B2bCreditDto cdto  = null;
		B2bCreditGoodsDtoMa cgDto = null;
		B2bEconomicsBankCompanyDto edto = null;
		Double proportion = 1d;
		try {
			if(!"".equals(orderNumber)){
				odto = foreignOrderService.getOrder(orderNumber);
			}
			if(!"".equals(contractNo)){
				odto = foreignOrderService.getContract(contractNo);
			}
		} catch (Exception e) {
			logger.error("errorOrder",e);
		}
		//验证订单
		if(null == odto){
			flag =false;
			rest = RestCode.CODE_O001.toJson();
		}
		if(flag && odto.getOrderStatus() >=2){
			flag =false;
			rest = RestCode.CODE_O002.toJson();
		}
		//订单已取消
		if(flag && -1 == odto.getOrderStatus()){
			flag =false;
			rest = RestCode.CODE_O010.toJson();
		}
		//验证授信客户与密码
		if(flag){
			cdto = creditService.getCredit(dto.getPk(),2);
			if(null == cdto){
				flag =false;
				rest = RestCode.CODE_C002.toJson();
			}else{
				//判断支付密码
				if(!password.equals(cdto.getVirtualPayPassword())){
					flag =false;
					rest = RestCode.CODE_C006.toJson();
				}
			}
		}
		if(flag){
			//判断金融产品额度
			cgDto =  b2bCreditGoodsService.searchCreditGoodsByPk(creditGoodsPk);
			if(null != cgDto && null != cgDto.getPlatformAmount() && null != cgDto.getIsVisable() && 1== cgDto.getIsVisable()
					&& null != cgDto.getCompanyPk() && cgDto.getCompanyPk().equals(dto.getPk())){
				Double avaiableAmount = ArithUtil.sub(cgDto.getPlatformAmount(), null==cgDto.getPledgeUsedAmount()?0d:cgDto.getPledgeUsedAmount());
				//订单总金额*支付比例=订单所需支付的额度
				if(null != cgDto.getProportion() && cgDto.getProportion() >0d){
					proportion = cgDto.getProportion();
				}
				if(ArithUtil.round(ArithUtil.mul(odto.getActualAmount(), proportion), 2)>avaiableAmount){
					flag = false;
					rest = RestCode.CODE_C003.toJson();
				}
				//限制支付
//				if((cgDto.getGoodsType() == 2 || cgDto.getGoodsType() == 4) && !PropertyConfig.getProperty("storePk").equals(odto.getStorePk())){
//					flag = false;
//					rest = RestCode.CODE_C0011.toJson();
//				}
			}else{
				flag = false;
				rest = RestCode.CODE_C003.toJson();
			}
		}
		//化纤白条和化纤贷需要判断银行额度
		if(flag && (cgDto.getGoodsType() == 1 || cgDto.getGoodsType() == 2)){
			//判断银行额度
			edto = economicsBankCompanyService.searchGoods(cgDto.getCompanyPk(), cgDto.getBankPk(), cgDto.getGoodsType());
			if(null == edto){
				flag = false;
				rest = RestCode.CODE_C0011.toJson();
			}
		}
		if(flag){
			//验证供应商银行卡号
			 card = companyBankcardService.getCompanyBankCard(odto.getSupplierPk(),cgDto.getBankPk());
			if(null == card){
				flag =false;
				rest = RestCode.CODE_C001.toJson();
			}
		}
		if(flag){
			//根据授信金融产品的银行判断
			if(null != cgDto.getBank() && !"".equals(cgDto.getBank())){
//				dto.setCustomerNumber(cgDto.getBankAccountNumber());
				//调用不同的银行返回结果
				BankBaseResp resp = null;
				switch (cgDto.getBank()) {
				case BanksType.bank_guangda:
					resp = bankGuangdaService.payOrder(odto, dto, card,cgDto);
					break;
				case BanksType.bank_suzhou:
					resp = bankSuzhouService.payOrder(odto, dto, card, cgDto);
					break;
				case BanksType.bank_xingye:
					resp = bankXingyeService.payOrder(odto, dto, card,cgDto);
					break;
				case BanksType.bank_gongshang:
					resp = bankGongshangService.payOrder(odto, dto, card,cgDto);
					break;
				case BanksType.bank_sunong:
					cgDto.setContractNumber(edto.getContractNumber());
					resp = bankSunongService.payOrder(odto, dto, card,cgDto);
					break;
				case BanksType.bank_zheshang:
					resp = bankZheshangService.payOrder(odto, dto, card,cgDto);
					break;
				case BanksType.bank_shanghai:
					cgDto.setContractNumber(edto.getContractNumber());
					cgDto.setAgreementNumber(edto.getAgreementNumber());
					resp = bankShanghaiService.payOrder(odto, dto, card,cgDto);
					break;
				case BanksType.bank_jianshe:
					B2bCreditDtoMa cm = new B2bCreditDtoMa();
					cm.UpdateMine(cdto);
					resp = bankJiansheService.payOrderUnique(cm, odto, dto, card, cgDto);
					break;
				case BanksType.bank_zhonghang:
					resp = bankZhonghangService.payOrder(odto, dto, card,cgDto);
					break;
				default:
					//其他无对接的银行
					if(cgDto.getGoodsType() == 3 || cgDto.getGoodsType() == 4){
						resp = new BankBaseResp();
						resp.setCode(RestCode.CODE_0000.getCode());
					}
					break;
				}
				//成功回调hxh业务
				if(null != resp && RestCode.CODE_0000.getCode().equals(resp.getCode())){
					//更新订单 交易记录等信息
					huaxianhuiService.updateBackPyOrder(odto,contractNo,paymentType,paymentName,dto,cgDto,card,resp);
					B2bEconomicsBankDto bankDto = economicsBankService.getEconomicsBank(cgDto.getBankPk());
					Map<String,Object> resMap = new HashMap<String,Object>();
					if(null != bankDto){
						resMap.put("gateway", bankDto.getGateway());
					}
					if(null != resp.getBankJiansheResp()){
						resMap.put("qrCode", resp.getBankJiansheResp().getBaseCode());
						resMap.put("returnUrl", resp.getBankJiansheResp().getReturnUrl());
					}
					rest = RestCode.CODE_0000.toJson(resMap);
					map.put("type", "success");
					// 订单推送给erp
					sendToCrm(odto,contractNo);
					//失败 返回银行信息
				}else{
					if(null ==resp || null == resp.getMsg()){
						rest = RestCode.CODE_C0011.toJson();
					}else{
						RestCode code = RestCode.CODE_Z000;
						code.setMsg(resp.getMsg());
						rest = code.toJson();
					}
				}
			}else{
				rest = RestCode.CODE_C0012.toJson();
			}
		}
		map.put("rest", rest);
		return map;
	}

	private void sendToCrm(B2bOrderDtoMa odto,String contractNo) {
		B2bTokenDto token =  b2bTokenService.getByCompanyPk(odto.getStorePk());
		if(null != token){
			//合同订单推送给erp 合同来源为4的不推送给erp
			if(null != contractNo && !"".equals(contractNo) && 4 != odto.getSource()){
				try {	
					ContractSyncToMongo syncToMongo = new ContractSyncToMongo();
					syncToMongo.setId(contractNo);
					syncToMongo.setIsPush(1);//
					syncToMongo.setStorePk(odto.getStorePk());
					syncToMongo.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
					syncToMongo.setContractNumber(contractNo);
					syncToMongo.setDetail("");
					mongoTemplate.save(syncToMongo);
				} catch (Exception e) {
					// 如果失败存数据做定时任务推送
					logger.error("errorErp--------orderNumber:" + contractNo, e);
					UnsynErpOrder erpOrder = new UnsynErpOrder();
					erpOrder.setOrderNumber(contractNo);
					erpOrder.setInsertTime(DateUtil.formatDateAndTime(new Date()));
					erpOrder.setStorePk(odto.getStorePk());
					erpOrder.setType(2);
					mongoTemplate.save(erpOrder);
				}
			}
		}
	}

	@Override
	public void updateLoanDetails(B2bLoanNumberDto odto) {
		if(null != odto){
			//调用不同的银行返回结果
			BankBaseResp resp = null;
			switch (odto.getBankName()) {
			case BanksType.bank_guangda:
				resp = 	bankGuangdaService.searchloan(odto);
				break;
			case BanksType.bank_suzhou:
				resp = 	bankSuzhouService.searchloan(odto);
				break;
			case BanksType.bank_xingye:
				resp = 	bankXingyeService.searchloan(odto);
				break;
			case BanksType.bank_gongshang:
				resp = 	bankGongshangService.searchloan(odto);
				break;	
			case BanksType.bank_sunong:
				resp = 	bankSunongService.searchloan(odto);
				break;
			case BanksType.bank_shanghai:
				resp = 	bankShanghaiService.searchloan(odto);
				break;
			case BanksType.bank_jianshe:
				resp = 	bankJiansheService.searchloan(odto);
				break;
			case BanksType.bank_zhonghang:
				resp = 	bankZhonghangService.searchloan(odto);
				break;
			default:
				break;
			}
			//业务回调
			if (null != resp && null != resp.getBankCreditDto()) {
				if (null != resp.getBankCreditDto().getLoanStatus()
						&& resp.getBankCreditDto().getLoanStatus() == 4) {
					b2bLoanNumberService.updateBackCancalOrder(odto.getOrderNumber());
				} else {
					if (!odto.getBankName().equals("中国银行")) {
						huaxianhuiService.updateBackLoanOrder(odto, resp.getBankCreditDto());
					} else {
						huaxianhuiService.updatezhLoanOrder(odto, resp.getBankCreditDto());
					}
				}
			}
			//调用额度查询
//			try {
//				B2bCompanyDto company = companyService.getCompany(odto.getPurchaserPk());
//				this.updateCreditAmount(company);
//			} catch (Exception e) {
//				logger.error("errorUpdateCreditAmount",e);
//			}
		}
	}

	@Override
	public void updateRepaymentDetails(B2bLoanNumberDto odto) {
		if(null != odto ){
			//调用不同的银行返回结果
			BankBaseResp resp = null;
			List<B2bRepaymentRecord> withholdlist = null;//代扣费用列表
			switch (odto.getBankName()) {
			case BanksType.bank_guangda:
				resp = 	bankGuangdaService.searchRepayment(odto);
				break;
			case BanksType.bank_suzhou:
				resp = 	bankSuzhouService.searchRepayment(odto);
				break;
			case BanksType.bank_xingye:
				resp = 	bankXingyeService.searchRepayment(odto);
				break;
			case BanksType.bank_gongshang:
				resp = 	bankGongshangService.searchRepayment(odto);
				break;
			case BanksType.bank_sunong:
				resp = 	bankSunongService.searchRepayment(odto);
				break;
			case BanksType.bank_shanghai:
				resp = 	bankShanghaiService.searchRepayment(odto);
				break;
			case BanksType.bank_jianshe:
				resp = 	bankJiansheService.searchRepayment(odto);
				break;
			case BanksType.bank_zhonghang:
				resp = 	bankZhonghangService.searchRepayment(odto);
				break;
			default:
				break;
			}
			//业务回调
			if(null != resp && null != resp.getB2bRepaymentRecordList() && resp.getB2bRepaymentRecordList().size()>0){
				B2bOrderDtoMa om = foreignOrderService.getOrder(odto.getOrderNumber());
				if(null == om){
					om = foreignOrderService.getContract(odto.getOrderNumber());
				}
				//第一套还款业务回调
				if(null == resp.getRepaymentType()){
					logger.error("null == resp.getRepaymentType()");
					huaxianhuiService.updateBackRepayment(om,odto, resp.getB2bRepaymentRecordList(), resp.getLoanStatus(), resp.getRepaymentDate());
				//第二套还款业务回调
				}else{
					//类型为2是排除已存在的还款记录
//					if(resp.getRepaymentType() ==2){
//						this.setRepaymentList(odto,resp);
//					}
					withholdlist = huaxianhuiService.updateBackRepaymentAnother(om,odto, resp.getB2bRepaymentRecordList(), resp.getLoanStatus(), resp.getRepaymentDate(),false);
				}
				//代扣申请
				try {
					if(null != withholdlist && withholdlist.size()>0){
						this.updateAgentPay(odto, withholdlist);
					}
				} catch (Exception e) {
					logger.error("updateAgentPay",e);
				}
			}
			//调用额度查询
			try {
				B2bCompanyDto company = companyService.getCompany(odto.getPurchaserPk());
				this.updateCreditAmount(company);
			} catch (Exception e) {
				logger.error("errorUpdateCreditAmount",e);
			}
		}
	}

	@Override
	public void updateFinanceRecords(String orderNumber) {
		
		B2bLoanNumberDto order =  b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		//查询放款成功的订单
		if(null != order && null !=order.getLoanStatus() && order.getLoanStatus() == 3){
//			B2bEconomicsGoodsDto goods = economicsGoodsService.getByPk(order.getEconomicsGoodsPk());
			BankBaseResp resp = null;
			switch (order.getBankName()) {
			case BanksType.bank_suzhou:
				//调取苏州银行提款结果查询接口
				 resp = bankSuzhouService.searchWithdrawalsByHttp(orderNumber, order.getCustomerNumber(),order.getEconomicsGoodsType());
				break;
			case BanksType.bank_xingye:
				//调取兴业银行订单查询接口
				 resp = bankXingyeService.searchOrder(order);
				break;
			default:
				break;
			}	
			//支付成功进行回调
			if(null != resp && resp.getPayStatus() == 1){
				financeRecordsService.updateFinancerecords(orderNumber,1);
			}		
		}
	}

	@Override
	public void updateAgentPay(B2bLoanNumberDto odto, List<B2bRepaymentRecord> recordList) {
		//调用不同的银行返回结果
		if(null != recordList && recordList.size()>0){
			for(B2bRepaymentRecord record : recordList){
				//服务费为0不提交
				if(null==record.getServiceCharge() || record.getServiceCharge() == 0d){
					continue;
				}
				BankBaseResp resp = null;
				switch (odto.getBankName()) {
				case BanksType.bank_gongshang:
					resp = 	bankGongshangService.skblOrderPay(record);
					break;
//				case BanksType.bank_shanghai:
//					resp = 	bankShanghaiService.agentPay(odto, record);
//					break;
				default:
					break;
				}
				if(null !=resp){
					huaxianhuiService.updateAngetPay(odto, record,resp);
				}
			}
		}
	}

	@Override
	public void updateAgentQry(B2bLoanNumberDto odto,String id) {
		Query qu =null;
		if(null != id){
			qu = new Query(Criteria.where("id").is(
					id));
		}else{
			qu = new Query(Criteria.where("orderNumber").is(
					odto.getOrderNumber()));
		}
		List<B2bRepaymentRecord> list = mongoTemplate.find(qu,
				B2bRepaymentRecord.class);
		if(null != list && list.size()>0){
			for(B2bRepaymentRecord record : list){
				//服务费为0不提交
				if(null==record.getServiceCharge() || record.getServiceCharge() == 0d){
					continue;
				}
				BankBaseResp resp = null;
				switch (record.getBankName()) {
				case BanksType.bank_gongshang:
					resp = 	bankGongshangService.skblOrderQry(record);
					break;
				default:
					break;
				}
				if(null !=resp){
					huaxianhuiService.updateAngetQry(record,resp);
				}
			}
		}
	}


	@Override
	public void jumpLoanPage(Integer source, String orderNumber,
			HttpServletResponse resp) {
		B2bLoanNumberDto b2bLoanNumberDto  = b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		if(null != b2bLoanNumberDto){
			B2bEconomicsBankDto bank = economicsBankService.getEconomicsBank(b2bLoanNumberDto.getBankPk());
				JSONObject json = new JSONObject();
				json.put("gateway", bank.getGateway());
				if(null != b2bLoanNumberDto.getQrCode()){
					json.put("qrCode", b2bLoanNumberDto.getQrCode());
				}
				if(null !=b2bLoanNumberDto.getReturnUrl()){
					json.put("returnUrl", b2bLoanNumberDto.getReturnUrl());
				}
				try {
					resp.getWriter().print(json.toJSONString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else{
			B2bOnlinepayRecordDto order = onlinepayService.getByOrderNumer(orderNumber);
			if(null != order){
				if( order.getStatus() == 1){
					try {
						resp.getWriter().print("{\"gateway\":\""+order.getReturnUrl()+"\"}");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				B2bBillOrderDto billOrder = billOrderService.getBillOrder(orderNumber);
				JSONObject json = new JSONObject();
				if(null != billOrder && null != billOrder.getGoodsShotName() && 
						(BillType.PFT.equals(billOrder.getGoodsShotName()) ||
								BillType.PFT_1.equals(billOrder.getGoodsShotName()))){
					try {
						json.put("html", null!=billOrder.getReturnUrl()?billOrder.getReturnUrl().replace("${hxh_mesgid}", KeyUtils.getUUID()):"");
						resp.getWriter().print(json.toJSONString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					B2bBillGoodsDto bgdto = billGoodsService.getByPk(billOrder.getGoodsPk());
					json.put("gateway", bgdto.getGateway());
					try {
						resp.getWriter().print(json.toJSONString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public String onlinePay(String orderNumber, String contractNo,
			String goodsPk,String paymentName,Integer paymentType) {
		String rest = null;
		Map<String, Object> map = new HashMap<String,Object>();
		boolean flag = true;
		B2bOrderDtoMa odto = null;
		SysCompanyBankcardDto card = null;
		B2bOnlinepayGoodsDto ogdto = null;
		List<OrderGoodsDtoEx> goodsList = null;
		try {
			if(!"".equals(orderNumber)){
				odto = foreignOrderService.getOrder(orderNumber);
			}
			if(!"".equals(contractNo)){
				odto = foreignOrderService.getContract(contractNo);
			}
		} catch (Exception e) {
			logger.error("errorOrder",e);
		}
		//验证订单
		if(null == odto){
			flag =false;
			rest = RestCode.CODE_O001.toJson();
		}
		//验证商品
		if(flag){
			if(null == contractNo || "".equals(contractNo)){
				goodsList = foreignOrderService.getOrderGoods(orderNumber);
			}else{
				goodsList = foreignOrderService.getContractGoods(contractNo);
			}
			if(null == goodsList || goodsList.size() == 0){
				flag =false;
				rest = RestCode.CODE_O001.toJson();
			}
		}
		if(flag && odto.getOrderStatus() >=2){
			flag =false;
			rest = RestCode.CODE_O002.toJson();
		}
		//订单已取消
		if(flag && -1 == odto.getOrderStatus()){
			flag =false;
			rest = RestCode.CODE_O010.toJson();
		}
		//支付产品不存在
		if(flag){
			ogdto = onlinepayService.getByPk(goodsPk);
			if(null == ogdto){
				flag =false;
				rest = RestCode.CODE_O010.toJson();
			}
		}
		if(flag){
			//验证供应商银行卡号
			card = companyBankcardService.getCompanyBankCard(odto.getSupplierPk(),ogdto.getBankPk());
			if(null == card){
				flag =false;
				rest = RestCode.CODE_C001.toJson();
			}
		}
		if(flag){
				//调用不同的银行返回结果
				BankBaseResp resp = null;
				switch (ogdto.getShotName()) {
				case OnlineType.EQF:
					resp = onlineGongshangService.onlinePay(odto, goodsList, card);
					break;
				default:
					break;
				}
				//成功回调hxh业务
				if(null != resp && resp.getCode().equals(RestCode.CODE_0000.getCode())){
					//更新订单 交易记录等信息
					huaxianhuiService.applyBackOnline(odto,contractNo,paymentName,paymentType,resp.getSerialNumber(), card, ogdto, resp.getReturnUrl());
					//失败 返回银行信息
					map.put("gateway", resp.getReturnUrl());
					rest = RestCode.CODE_0000.toJson(map);
					// 订单推送给erp
					sendToCrm(odto,contractNo);
				}else{
					if(null ==resp || null == resp.getMsg()){
						rest = RestCode.CODE_C0011.toJson();
					}else{
						RestCode code = RestCode.CODE_Z000;
						code.setMsg(resp.getMsg());
						rest = code.toJson();
					}
				}
		}
		return rest;
	}

	@Override
	public void onlinePaySearch(B2bOnlinepayRecordDto record) {
			BankBaseResp resp = null;
			switch (record.getShotName()) {
			case OnlineType.EQF:
				resp = onlineGongshangService.onlinePaySearch(record);
				break;
			default:
				break;
			}
			if(null != resp && RestCode.CODE_0000.getCode().equals(resp.getCode())){
				B2bOrderDtoMa odto = null;
				//1支付成功
				if(null != resp.getPayStatus() && resp.getPayStatus() == 1){
					odto = foreignOrderService.getOrder(record.getOrderNumber());
					if(null == odto){
						odto = foreignOrderService.getContract(record.getOrderNumber());
					}
					huaxianhuiService.successBackPyOnline(odto,record.getReceivablesAccount(), record.getReceivablesAccountName());
				}
				//支付失败
				if(null != resp.getPayStatus() && resp.getPayStatus() == 2 ){
					huaxianhuiService.errorBackPyOnline(record.getOrderNumber());
				}
			}
	}

	@Override
	public Map<String,Object> onlinePayCancel(B2bOnlinepayRecordDto record) {
		BankBaseResp resp = null;
		Map<String,Object> map = new HashMap<String, Object>();
		switch (record.getShotName()) {
		case OnlineType.EQF:
			resp = onlineGongshangService.onlinePayCancel(record);
			break;
		default:
			break;
		}
		if(!RestCode.CODE_0000.getCode().equals(resp.getCode())){
			map.put("code", "z000");
			if (null==resp.getMsg() || "".equals(resp.getMsg())) {
				map.put("msg", "银行处理失败");
			}else {
				map.put("msg", resp.getMsg());
			}
		//订单取消成功 修改交易记录状态
		}else{
			map.put("code", "0000");
			try {
				B2bOnlinepayRecord r = new B2bOnlinepayRecord();
				r.UpdateDTO(record);
				r.setStatus(3);
				onlinepayService.updateOnlineRecord(r);
			} catch (Exception e) {
				logger.error("errorOnlinePayCancel",e);
			}
		}
		return map;
	}

	@Override
	public String updateEntrustLoanDetails(B2bLoanNumberDto o,Integer status) {
		String rest = RestCode.CODE_0000.toJson();
		//化纤白条委贷 化纤贷委贷支持此条件
		if(null != o && (o.getEconomicsGoodsType() == 3 || o.getEconomicsGoodsType() == 4)){
			if(status == 1){
				if(o.getLoanStatus() == 2){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("companyPk", o.getPurchaserPk());
					map.put("bankPk",o.getBankPk());
					map.put("goodsType",o.getEconomicsGoodsType());
					List<B2bCreditGoodsDto> gdtoList =b2bCreditGoodsService.searchList(map);
					if(null != gdtoList &&gdtoList.size()>0){
						B2bCreditGoodsDto g = gdtoList.get(0);
						//判断可用额度
						if(ArithUtil.sub(null==g.getPlatformAmount()?0d:g.getPlatformAmount(), 
								null==g.getPledgeUsedAmount()?0d:g.getPledgeUsedAmount()) < o.getLoanAmount()){ 
							rest = RestCode.CODE_C003.toJson();
						}else{
							huaxianhuiService.sureLoan(o,g);
						}
					}else{
						rest = RestCode.CODE_C003.toJson();
					}
				}
			}
			if(status == 2){
				if(o.getLoanStatus() != 4){
					b2bLoanNumberService.updateBackCancalOrder(o.getOrderNumber());
				}
			}
		}
		return rest;
	}

	@Override
	public String updateEntrusRepayment(B2bLoanNumberDto o, Double principal,
			Double interest) {
		String rest = RestCode.CODE_0000.toJson();
		B2bOrderDtoMa om = foreignOrderService.getOrder(o.getOrderNumber());
		if(null == om){
			om = foreignOrderService.getContract(o.getOrderNumber());
		}
		//已还款的无法操作
		if(null != o && null != om && (o.getLoanStatus() == 3 ||  o.getLoanStatus() == 6)){
			//
			if(ArithUtil.add(null==o.getPrincipal()?0d:o.getPrincipal(), principal)
					>o.getLoanAmount()){
				rest = RestCode.CODE_O013.toJson();
			}else{
				Integer loanStatus = 5;
				//部分还款
				if(ArithUtil.add(null==o.getPrincipal()?0d:o.getPrincipal(), principal)
						<o.getLoanAmount()){
					loanStatus = 6;
				}
				List<B2bRepaymentRecord> list = new ArrayList<B2bRepaymentRecord>();
				B2bRepaymentRecord r = new B2bRepaymentRecord();
				r.setOrderNumber(o.getOrderNumber());
				r.setCreateTime(DateUtil.formatYearMonthDay(new Date()));
				r.setAmount(principal);
				r.setInterest(interest);
				r.setStatus(1);
				r.setCompanyPk(o.getPurchaserPk());
				r.setCompanyName(o.getPurchaserName());
				r.setBankPk(o.getBankPk());
				r.setBankName(o.getBankName());
				r.setOrganizationCode(o.getOrganizationCode());
				r.setInterestReceivable(interest);
				list.add(r);
				huaxianhuiService.updateBackRepaymentAnother(om, o, list, loanStatus, new Date(),true);
			}
		}else{
			rest = RestCode.CODE_O011.toJson();
		}
		return rest;
	}

	@Override
	public String integrationCreditInfo(String rest,String bankName) {
		String code = null;
		try {
			if(null != rest && !"".equals(rest) && null != bankName && !"".equals(bankName)){
				BankBaseResp resp = null;
				switch (bankName) {
				case BanksType.bank_jianshe:
					resp = bankJiansheService.integrationCustomer(rest);
					break;
				default:
					break;
				}
				if(null !=resp.getCode() && RestCode.CODE_0000.getCode().equals(resp.getCode())){
					B2bCompanyDto company = companyService.getCompanyByName(resp.getBankJiansheResp().getCompanyName());
					if(null == company){
						code = RestCode.CODE_A004.toJson();
					}else{
						CreditInfo info = new CreditInfo();
						info.setCreditNumber(resp.getBankJiansheResp().getCreditNumber());
						info.setCustomerNumber(resp.getBankJiansheResp().getCustomerNumber());
						info.setUserId(resp.getBankJiansheResp().getUserId());
						info.setTotalAmount(resp.getBankJiansheResp().getCreditAmount());
						creditService.updateCreditAudit(company.getPk(), info);
						code = RestCode.CODE_0000.toJson(company);
						//调用额度查询
//						this.updateCreditAmount(company);
					}
				}else{
					code = RestCode.CODE_A001.toJson();
				}
			}else{
				code = RestCode.CODE_A001.toJson();
			}
		} catch (Exception e) {
			code = RestCode.CODE_S999.toJson();
			logger.error("integrationCreditInfo",e);
		}
		return code;
	}

	@Override
	public RestCode orderPayResult(String rest, String bankName) {

		RestCode code = RestCode.CODE_0000;
		try {
			if(null != rest && !"".equals(rest) && null != bankName && !"".equals(bankName)){
				BankBaseResp resp = null;
				switch (bankName) {
				case BanksType.bank_jianshe:
					resp = bankJiansheService.orderPayresult(rest);
					break;
				default:
					break;
				}
				if(null !=resp.getCode() && RestCode.CODE_0000.getCode().equals(resp.getCode())){
					B2bLoanNumberDto loanNumber = b2bLoanNumberService.getB2bLoanNumberDto(resp.getBankJiansheResp().getOrderNumber());
					if(null != loanNumber){
						this.updateLoanDetails(loanNumber);
					}
				}else{
					code = RestCode.CODE_A001;
				}
			}else{
				code = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			code = RestCode.CODE_S999;
			logger.error("integrationCreditInfo",e);
		}
		return code;
	
	}

	@Override
	public String creditCustomerApply(String companyPk) {
		RestCode code = RestCode.CODE_0000;
		B2bCreditDto credit = creditService.getCredit(companyPk, null);
		B2bCompanyDto company = companyService.getCompany(companyPk);
		BankBaseResp resp = null;
		B2bCreditDtoMa cm = new B2bCreditDtoMa();
		cm.UpdateMine(credit);
		//目前只有建设银行
		resp =bankJiansheService.customerApply(cm, company);		
		if(null == resp || !RestCode.CODE_0000.getCode().equals(resp.getCode())){
			RestCode.CODE_Z000.setMsg(null != resp.getMsg()?resp.getMsg():"银行返回信息异常");
			code = RestCode.CODE_Z000;
		}	
		return code.toJson();
	}
	
//	private void setRepaymentList(B2bLoanNumberDto odto, BankBaseResp resp) {
//		Query qu = new Query(Criteria.where("orderNumber").is(
//				odto.getOrderNumber()));
//		List<B2bRepaymentRecord> list = mongoTemplate.find(qu, B2bRepaymentRecord.class);
//		if(null != list && list.size()>0 && null !=  resp.getB2bRepaymentRecordList() 
//				&& resp.getB2bRepaymentRecordList().size()>0){
//			List<B2bRepaymentRecord> nlist =new ArrayList<B2bRepaymentRecord>();
//			for (B2bRepaymentRecord nl : resp.getB2bRepaymentRecordList()) {
//				for (int i = 0; i < list.size(); i++) {
//					if(nl.getRepaymentNumber().equals(list.get(i).getRepaymentNumber())){
//						break;
//					}
//					if(i == list.size()-1){
//						nlist.add(nl);
//					}
//				}
//			}
//			resp.setB2bRepaymentRecordList(nlist);
//		}
//	}
}
