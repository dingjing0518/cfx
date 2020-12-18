package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.jianshe.BeanUtils;
import cn.cf.common.creditpay.jianshe.IFSPOrderResultBean;
import cn.cf.common.creditpay.jianshe.IFSPQRCodeInfoGetBean;
import cn.cf.common.creditpay.jianshe.PostUtils;
import cn.cf.common.creditpay.jianshe.XmlUtils;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.entry.BankJiansheResp;
import cn.cf.json.JsonUtils;
import cn.cf.service.creditpay.BankJiansheService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.XmlTools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class BankJiansheServiceImpl implements BankJiansheService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,
			B2bCreditGoodsDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		String id = null;
		try {
			BeanUtils bean = new BeanUtils();
			IFSPOrderResultBean ibean = bean.getOrderResult(loanNumber.getOrderNumber());
			id = this.saveBankInfo(null, null,
					ibean.ReqToJsonString(), null,
					"active","order");
			String rest =  PostUtils.post(ibean);
			this.saveBankInfo(null, id, ibean.ReqToJsonString(),rest, "active","order");
			JSONObject o = null;
			if(null != rest){
				o = JsonUtils.toJSONObject(rest);;
				loanNumber.setLoanNumber(null!=o.get("loanaccno")?o.getString("loanaccno"):null);
			}
			if(null!=loanNumber.getLoanNumber()){
				String xml = XmlUtils.searchCustomerAmount(loanNumber.getLoanNumber());
				id = this.saveBankInfo(null, null,
						xml, null,
						"active","loan");
				rest =  PostUtils.postUrl(xml, "A0831BR90");
				this.saveBankInfo(null, id, xml,rest, "active","loan");
				if(null != rest){
					JSONObject json = XmlTools.xmltoJson(rest);
					json = null!=json.getJSONObject("Response")?json.getJSONObject("Response"):json;
					if(null !=json.get("rsp_code") && "000000000000".equals(json.getString("rsp_code"))){
						resp = new BankBaseResp();
						//放款结束日期
						String loanEndTime = null != json.get("Loan_ExDat")?json.get("Loan_ExDat").toString():null;
						//贷款利率
						String inteRate = null != json.get("Loan_Yr_IntRt")?json.get("Loan_Yr_IntRt").toString():null;
						BankCreditDto cdto = new BankCreditDto();
						cdto.setLoanEndDate(DateUtil.parseDateFormatYMD(loanEndTime));
						cdto.setIouNumber(loanNumber.getLoanNumber());
						cdto.setPayStatus(1);
						cdto.setLoanRate(null !=inteRate?Double.parseDouble(inteRate):null);
						resp.setBankCreditDto(cdto);	
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorJsLoan:",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = new BankBaseResp();
		String id = null;
		try {
			String xml = XmlUtils.searchCustomerRepayment(loanNumber);
			System.out.println("xml:========="+xml);
			id = this.saveBankInfo(null, null,
					xml, null,
					"active","repayment");
			String rest =  PostUtils.postUrl(xml, "A0831BR92");
			System.out.println("rest:========="+rest);
			this.saveBankInfo(null, id, xml,rest, "active","repayment");
			if(null != rest){
				JSONObject json = XmlTools.xmltoJson(rest);
				json = null!=json.getJSONObject("Response")?json.getJSONObject("Response"):json;
				if(null !=json.get("rsp_code") && "000000000000".equals(json.getString("rsp_code"))
						&& null != json.get("Cur_Rcrd_Num")){
					JSONArray array = new JSONArray();
					if(json.getInteger("Cur_Rcrd_Num") == 1){//总笔数
						array.add(json.getJSONObject("TR_SCRN_INFO"));
					}
					if(json.getInteger("Cur_Rcrd_Num") > 1){//总笔数
						array = json.getJSONArray("TR_SCRN_INFO");
					}
					if(null != array && array.size()>0){
						List<B2bRepaymentRecord> brpdList = new ArrayList<B2bRepaymentRecord>();
						Double repaymentAmount = 0d;
						resp = new BankBaseResp();
						for (int i = 0; i < array.size(); i++) {
							json = array.getJSONObject(i);
							B2bRepaymentRecord brpr = new B2bRepaymentRecord();
							brpr.setOrderNumber(loanNumber.getOrderNumber());
							brpr.setIouNumber(loanNumber.getLoanNumber());
							brpr.setStatus(1);
							brpr.setCompanyPk(loanNumber.getPurchaserPk());
							brpr.setCompanyName(loanNumber.getPurchaserName());
							brpr.setBankPk(loanNumber.getBankPk());
							brpr.setBankName(loanNumber.getBankName());
							brpr.setAmount(json.getDouble("Hist_Txn_1_Amt"));//本金
							brpr.setInterest(json.getDouble("Hist_Txn_2_Amt"));//利息
							brpr.setCreateTime(json.getString("Txn_AccEntr_Dt"));//交易日期
							brpr.setOrganizationCode(loanNumber.getOrganizationCode());
							brpdList.add(brpr);
							repaymentAmount = ArithUtil.add(repaymentAmount, brpr.getAmount());
							if(i == array.size()-1){
								resp.setRepaymentDate(DateUtil.numberToString(brpr.getCreateTime()));
							}
						}
						if(loanNumber.getLoanAmount().toString().equals(ArithUtil.round(repaymentAmount, 2)+"")){
							resp.setLoanStatus(5);//5全部还款 
						}else{
							resp.setLoanStatus(6);//6部分还款
						}
						resp.setB2bRepaymentRecordList(brpdList);
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorJsRepayment:",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = new BankBaseResp();
		String id = null;
		try {
			BeanUtils bean = new BeanUtils();
			IFSPOrderResultBean ibean = bean.getOrderResult(loanNumber.getOrderNumber());
			id = this.saveBankInfo(null, null,
					ibean.ReqToJsonString(), null,
					"active","cancel");
			String rest =  PostUtils.post(ibean);
			System.out.println("cancel:"+rest);
			this.saveBankInfo(null, id, ibean.ReqToJsonString(),rest, "active","cancel");
			if(null != rest){
				JSONObject o = JsonUtils.toJSONObject(rest);
//				00支付成功（终点）
//				01未/待支付（初始）
//				02支付失败（终点）
//				03支付失效（终点）
//				04未知（异常：中间）
//				55处理中（中间过渡）
				if(null !=o.get("ori_Txn_Ind") && ("02".equals(o.getString("ori_Txn_Ind")) || 
						"03".equals(o.getString("ori_Txn_Ind")) || "01".equals(o.getString("ori_Txn_Ind")))){
					resp.setCode(RestCode.CODE_0000.getCode());
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg("订单暂时无法关闭");
				}
			}else{
				resp.setCode(RestCode.CODE_Z000.getCode());
			}
		} catch (Exception e) {
			logger.error("errorJsLoan:",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp customerApply(B2bCreditDtoMa creditDto,B2bCompanyDto company) {
		BankBaseResp resp = new BankBaseResp();
		String id = null;
		try {
			if(null != creditDto){
				String xml = XmlUtils.customerApply(company, creditDto);
				id = this.saveBankInfo(null, null,
						xml, null,
						"active","apply");
				String rest =  PostUtils.postUrl(xml, "A0831BR89");
				this.saveBankInfo(null, id, xml,rest, "active","apply");
				System.out.println("apply:"+rest);
				if(null !=rest){
					JSONObject json = XmlTools.xmltoJson(rest);
					json = null!=json?json.getJSONObject("Response"):new JSONObject();
					if(null !=json.get("rsp_code") && "000000000000".equals(json.get("rsp_code"))){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null!=json.get("Ret_Inf")?json.getString("Ret_Inf"):"银行返回信息异常");
					}
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg("银行返回信息异常");
				}
			}
		} catch (Exception e) {
			resp.setCode(RestCode.CODE_S999.getCode());
			logger.error("erororapply:",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp payOrderUnique(B2bCreditDtoMa creditDto,
			B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto) {
		String id = null;
		BankBaseResp resp = null;
		try {
			String bankCode = null;
			String bankNo = null;
			if(null !=card.getBankclscode() && "105".equals(card.getBankclscode())){
				bankCode = "1";//收款人账号行别代码 1：建行 2：他行
			}else{
				bankCode = "2";//收款人账号行别代码 1：建行 2：他行
				bankNo = card.getBankNo();//收款人开户行支付联行号(收款行为他行时必填)
			}
			BeanUtils bu = new BeanUtils();
			IFSPQRCodeInfoGetBean ibean = bu.getQrCode(order.getOrderNumber(), creditDto.getInfo().getUserId(), creditDto.getInfo().getRcDocMobile(), 
					creditDto.getInfo().getRcDocType(), creditDto.getInfo().getRcDocNumber(), creditDto.getInfo().getRcName(),
					creditDto.getInfo().getCustomerNumber(), DateUtil.formatYearMonthDay(new Date())+" 23:59:59", order.getActualAmount().toString(), 
					order.getPurchaser().getPurchaserName(), creditDto.getInfo().getCreditNumber(), order.getSupplier().getSupplierName(), 
					card.getBankAccount(), bankCode, bankNo, company.getOrganizationCode());
			id = this.saveBankInfo(null, null,
					ibean.ReqToJsonString(), null,
					"active","pay");
			String rest =  PostUtils.post(ibean);
			this.saveBankInfo(null, id, ibean.ReqToJsonString(),rest, "active","pay");
			if(null != rest && !"".equals(rest)){
				JSONObject json = JsonUtils.toJSONObject(rest);
				resp = new BankBaseResp();
				if(null !=json.get("txn_Rsp_Cd_Dsc") && "000000000000".equals(json.getString("txn_Rsp_Cd_Dsc"))){
					resp.setCode(RestCode.CODE_0000.getCode());
					BankJiansheResp br = new BankJiansheResp();
					br.setBaseCode(json.getString("qr_Code"));
					br.setReturnUrl(json.getString("url"));
					resp.setBankJiansheResp(br);
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg(null!=json.get("txn_Ret_Inf")?json.getString("txn_Ret_Inf"):"银行返回数据异常");
				}
			}
		} catch (Exception e) {
			logger.error("errorPay:",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchBankCreditAmountUnique(B2bCreditDtoMa credit,B2bCreditGoodsDto dto) {
		BankBaseResp resp = null;
		String id =null;
		try {
			String xml = XmlUtils.searchCustomerAmount(credit.getInfo().getCustomerNumber());
			id = this.saveBankInfo(null, null,
					xml, null,
					"active","customer");
			String rest =  PostUtils.postUrl(xml, "A0831BR90");
			System.out.println("rest:"+rest);
			this.saveBankInfo(null, id, xml,rest, "active","customer");
			if(null !=rest){
				JSONObject json = XmlTools.xmltoJson(rest);
				json = null!=json.getJSONObject("Response")?json.getJSONObject("Response"):json;
				if(null !=json.get("rsp_code") && "000000000000".equals(json.getString("rsp_code"))){
					List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
					B2bEconomicsBankCompanyDto model = new B2bEconomicsBankCompanyDto();
					model.setPk(KeyUtils.getUUID());
					model.setCustomerNumber(credit.getInfo().getCustomerNumber());
					model.setContractNumber(credit.getInfo().getCreditNumber());
					model.setCreditAmount(null!=credit.getInfo().getTotalAmount()?credit.getInfo().getTotalAmount():0d);
					model.setContractAmount(model.getCreditAmount());
					model.setAvailableAmount(null !=json.get("Avl_Lmt")?json.getDouble("Avl_Lmt"):0d);
					model.setCreditStartTime(null!=json.get("AR_EfDt")?DateUtil
							.numberToString(json.getString("AR_EfDt")):null);
					model.setCreditEndTime(null!=json.get("Loan_ExDat")?DateUtil
							.numberToString(json.getString("Loan_ExDat")):null);
					model.setCompanyPk(credit.getCompanyPk());
					model.setBankPk(dto.getBankPk());
					model.setBankName(dto.getBank());
					//对应金融产品类型化纤白条
					model.setType(1);
					ebcList.add(model);
					resp = new BankBaseResp();
					resp.setEbcList(ebcList);
				}
			}
		} catch (Exception e) {
			logger.error("errorJiansheCustomer",e);
		}
		
		return resp;
	}

	@Override
	public BankBaseResp integrationCustomer(String rest) {
		BankBaseResp resp = new BankBaseResp();
		if(null != rest && !"".equals(rest)){
			JSONObject json = JsonUtils.toJSONObject(rest);
			if(null ==json.get("EntNm") || "".equals(json.getString("EntNm")) || 
					null ==json.get("Unn_Soc_Cr_Cd") || "".equals(json.getString("Unn_Soc_Cr_Cd")) ||
					null ==json.get("Cst_Nm") || "".equals(json.getString("Cst_Nm")) ||
					null ==json.get("Crdt_TpCd") || "".equals(json.getString("Crdt_TpCd")) ||
					null ==json.get("Crdt_No") ||
					null ==json.get("crgtlmtid") || "".equals(json.getString("crgtlmtid")) ||
					null ==json.get("CrGLn") || "".equals(json.getString("CrGLn")) ||
					null ==json.get("Cur_Lmt_EfDt") || "".equals(json.getString("Cur_Lmt_EfDt")) ||
					null ==json.get("Cur_Lmt_ExfDt") || "".equals(json.getString("Cur_Lmt_ExfDt")) ||
					null ==json.get("loanaccno") || "".equals(json.getString("loanaccno")) ||
					null ==json.get("usrid") || "".equals(json.getString("usrid")) ||
					null ==json.get("CrGLn")){
				resp.setCode(RestCode.CODE_A001.getCode());
			}else{
				BankJiansheResp br = new BankJiansheResp();
				br.setCompanyName(json.getString("EntNm"));
				br.setOrganizationCode(json.getString("Unn_Soc_Cr_Cd"));
				br.setCustomerName(json.getString("Cst_Nm"));
				br.setCertificatesType(json.getString("Crdt_TpCd"));
				br.setCertificatesNumber(json.getString("Crdt_No"));
				br.setCreditNumber(json.getString("crgtlmtid"));
				br.setCreditAmount(json.getDouble("CrGLn"));
				br.setStartTime(DateUtil.numberToString(json.getString("Cur_Lmt_EfDt")));
				br.setEndTime(DateUtil.numberToString(json.getString("Cur_Lmt_ExfDt")));
				br.setCustomerNumber(json.getString("loanaccno"));
				br.setUserId(json.getString("usrid"));
				br.setCreditAmount(json.getDouble("CrGLn"));
				resp.setCode(RestCode.CODE_0000.getCode());
				resp.setBankJiansheResp(br);
			}
		}else{
			resp.setCode(RestCode.CODE_A001.getCode());
		}
		return resp;
	}

	@Override
	public BankBaseResp orderPayresult(String rest) {
		BankBaseResp resp = new BankBaseResp();
		if(null != rest && !"".equals(rest)){
			JSONObject json = JsonUtils.toJSONObject(rest);
			if(null ==json.get("ordercode") || "".equals(json.getString("ordercode"))){
				resp.setCode(RestCode.CODE_A001.getCode());
			}else{
				BankJiansheResp br = new BankJiansheResp();
				br.setOrderNumber(json.getString("ordercode"));
				resp.setBankJiansheResp(br);
				resp.setCode(RestCode.CODE_0000.getCode());
			}
		}else{
			resp.setCode(RestCode.CODE_A001.getCode());
		}
		return resp;
	}
	
	private String saveBankInfo(String code, String id, String requestValue,
			 String responseValue,
		 String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("jiansheBank");
			bank.setRequestValue(requestValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setRequestMode(requestMode);
			bank.setHxhCode(hxhCode);
			mongoTemplate.save(bank);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, BankInfo.class);
			return id;
		}
	}
}
