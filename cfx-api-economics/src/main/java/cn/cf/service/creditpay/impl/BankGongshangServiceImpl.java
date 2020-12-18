package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.gongshang.BaseCodeUtils;
import cn.cf.common.creditpay.gongshang.XmlUtils;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.entry.OrderGoodsDtoEx;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.creditpay.BankGongshangService;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.foreign.ForeignOrderService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.icbc.api.core.ApiClient;
import com.icbc.api.core.ApiException;
import com.icbc.api.core.ApiRequest;
import com.icbc.api.core.ApiResponse;

@Service
public class BankGongshangServiceImpl implements BankGongshangService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	private  final String version = "1.0.0.0";
	private  final String version2 = "2.0.0.0";
	private  final String charset = "UTF-8";
	
	@Autowired
	private ForeignOrderService foreignOrderService;
	
	@Autowired
	private ForeignCompanyService foreignCompanyService;
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,B2bCreditGoodsDto dto) {
		BankBaseResp resp = null;
		XmlUtils utils = new XmlUtils();
		try {
			String reqXml = utils.usersxQuery(company);
			ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
			ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), "com.icbc.gyj.eloansplus.usersxquery", PropertyConfig.getProperty("gs_appid"));
			req.setRequestField("version", version);//接口版本，1.0.0.0
			req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
			req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
			req.setRequestField("trancode", "USERSXQUERY");//交易代码，详见接口文档
			req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
			JSONObject json = utils.getJsonByXml(execute(ac, req,reqXml,"customer"));
			if(!json.isNull("SxAmount") && !json.isNull("KyAmount")){
				resp = new BankBaseResp();
				List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
				B2bEconomicsBankCompanyDto ebc = new B2bEconomicsBankCompanyDto();
				Double contractAmount = json.getDouble("SxAmount");//贷款额度
				Double availableAmount = json.getDouble("KyAmount");//可用额度
				ebc.setPk(KeyUtils.getUUID());
				ebc.setCreditAmount(contractAmount);
				ebc.setCompanyPk(company.getPk());
				ebc.setBankPk(dto.getBankPk());
				ebc.setBankName(dto.getBank());
				ebc.setContractAmount(contractAmount);
				ebc.setAvailableAmount(availableAmount);
				ebc.setAmountType("1");//目前写死
				ebc.setType(1);//化纤白条类型
				ebcList.add(ebc);
				resp.setEbcList(ebcList);
			}
		} catch (Exception e) {
			logger.error("searchBankCreditAmount", e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		XmlUtils utils = new XmlUtils();
		BankBaseResp resp = null;
		String reqXml = utils.loanList(loanNumber);
		ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
		ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), "com.icbc.gyj.eloansplus.loanlist", PropertyConfig.getProperty("gs_appid"));
		req.setRequestField("version", version);//接口版本，1.0.0.0
		req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
		req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
		req.setRequestField("trancode", "LOANLIST");//交易代码，详见接口文档
		req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
		try {
			JSONObject json = utils.getJsonByXml(execute(ac, req,reqXml,"loan"));
			if(!json.isNull("TotalCount") && json.getInt("TotalCount")>0){
				if(json.getInt("TotalCount") == 1){
					json =  json.getJSONObject("rd");
				}else{
					JSONArray array = json.getJSONArray("rd");
					json = array.getJSONObject(0);
				}
				//BillStatus:融资状态：0-未发起 1-待确认 2-已融资 3-部分还款 4-已还款 5-已逾期 6-失败 7-已取消 8-放款成功定向支付失败 9-指令已提交
				if(null != json && !json.isNull("BillStatus") && 0!=json.getInt("BillStatus") && 
						1!=json.getInt("BillStatus") && 6!=json.getInt("BillStatus") && 
						7!=json.getInt("BillStatus") && 9!=json.getInt("BillStatus")){
					resp = new BankBaseResp();
					//放款开始日期
					String loanStartTime = json.isNull("LoanDate")?"":json.get("LoanDate").toString();
					//放款结束日期
					String loanEndTime = json.isNull("ExpiredDate")?"":json.get("ExpiredDate").toString();
					//借据编号
					String iouNumber = json.isNull("BillNo")?"":json.get("BillNo").toString();
					//合同号
					String contractNumber = json.isNull("ContractNo")?"":json.get("ContractNo").toString();
					BankCreditDto cdto = new BankCreditDto();
					cdto.setLoanStartDate("".equals(loanStartTime)?null:DateUtil.parseDateFormatYMD(loanStartTime));
					cdto.setLoanEndDate("".equals(loanEndTime)?null:DateUtil.parseDateFormatYMD(loanEndTime));
					cdto.setIouNumber(iouNumber);
					cdto.setPayStatus(1);
					cdto.setContractNumber(contractNumber);
					if(!json.isNull("LoanRate")){
						cdto.setLoanRate(ArithUtil.mul(json.getDouble("LoanRate"), 100));
					}
					resp.setBankCreditDto(cdto);
				}
			}
		} catch (Exception e) {
			logger.error("searchloan", e);
		}
		return resp;
	}
	
	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		XmlUtils utils = new XmlUtils();
		BankBaseResp resp = null;
		String reqXml = utils.loanList(loanNumber);
		ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
		ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), "com.icbc.gyj.eloansplus.loanlist", PropertyConfig.getProperty("gs_appid"));
		req.setRequestField("version", version);//接口版本，1.0.0.0
		req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
		req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
		req.setRequestField("trancode", "LOANLIST");//交易代码，详见接口文档
		req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
		try {
			JSONObject json = utils.getJsonByXml(execute(ac, req,reqXml,"repayment"));
			if(!json.isNull("TotalCount") && json.getInt("TotalCount")>0){
				if(json.getInt("TotalCount") == 1){
					json =  json.getJSONObject("rd");
				}else{
					JSONArray array = json.getJSONArray("rd");
					json = array.getJSONObject(0);
				}
				//BillStatus:融资状态：0-未发起 1-待确认 2-已融资 3-部分还款 4-已还款 5-已逾期 6-失败 7-已取消 8-放款成功定向支付失败 9-指令已提交
				if(null != json && !json.isNull("BillStatus") && (3==json.getInt("BillStatus") || 
						4==json.getInt("BillStatus") || 5==json.getInt("BillStatus"))){
					resp = new BankBaseResp();
					resp.setRepaymentType(1);//无还款记录格式(每次还款金额累加)
					Double principal = loanNumber.getPrincipal();//b2b已还本金
					Double amount = ArithUtil.sub(json.getDouble("LoanAmount"), json.getDouble("BillBalance")); //银行已还本金=接口金额-借据余额
					//存还款记录
					if(amount>principal){
						List<B2bRepaymentRecord> brList = new ArrayList<B2bRepaymentRecord>();
						B2bRepaymentRecord record = new B2bRepaymentRecord();
						record.setOrderNumber(loanNumber.getOrderNumber());
						record.setIouNumber(loanNumber.getLoanNumber());
						record.setAmount(ArithUtil.sub(amount, principal));//实还本金记录 = 已还本金-已还本金记录
						record.setStatus(1);
						record.setBankPk(loanNumber.getBankPk());
						record.setBankName(loanNumber.getBankName());
						record.setCompanyPk(loanNumber.getPurchaserPk());
						record.setCompanyName(loanNumber.getPurchaserName());
						record.setCreateTime(DateUtil.formatYearMonthDay(new Date()));
						record.setOrganizationCode(loanNumber.getOrganizationCode());
						brList.add(record);
						resp.setB2bRepaymentRecordList(brList);
						resp.setRepaymentDate(new Date());
						if(3==json.getInt("BillStatus") || 5==json.getInt("BillStatus")){
							resp.setLoanStatus(6);//6部分还款
						}else{
							resp.setLoanStatus(5);//5全部还款 
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("searchRepayment", e);
		}
		return resp;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card,B2bCreditGoodsDtoMa cgDto) {
		BankBaseResp bankBaseResp = new BankBaseResp();
		XmlUtils utils = new XmlUtils();
		List<OrderGoodsDtoEx> goodsList = foreignOrderService.getOrderGoods(order.getOrderNumber());
		if(null == goodsList || goodsList.size() == 0){
			goodsList = foreignOrderService.getContractGoods(order.getOrderNumber());
		}
		String reqXml = utils.orderUpload(order, goodsList, company, card,0,DateUtil.getDaysForDate(new Date(),cgDto.getTerm()));
		ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
		ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), "com.icbc.gyj.eloansplus.orderupload", PropertyConfig.getProperty("gs_appid"));
		req.setRequestField("version", version);//接口版本，1.0.0.0
		req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
		req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
		req.setRequestField("trancode", "RESULTQUERY");//交易代码，详见接口文档
		req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
		try {
			bankBaseResp = executeAnother(ac, req,reqXml,"pay");
		} catch (Exception e) {
			bankBaseResp.setCode(RestCode.CODE_S999.getCode());
			logger.error("payOrder", e);
		}
		return bankBaseResp;
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		XmlUtils utils = new XmlUtils();
		BankBaseResp bankBaseResp = new BankBaseResp();
		List<OrderGoodsDtoEx> goodsList = null;
		B2bOrderDtoMa order = foreignOrderService.getOrder(loanNumber.getOrderNumber());
		if(null != order){
			goodsList = foreignOrderService.getOrderGoods(loanNumber.getOrderNumber());
		}else{
			order = foreignOrderService.getContract(loanNumber.getOrderNumber());
			goodsList = foreignOrderService.getContractGoods(loanNumber.getOrderNumber());
		}
		B2bCompanyDto company = foreignCompanyService.getCompany(order.getPurchaserPk());
		//先调删除
		bankBaseResp = cancelOperate(loanNumber, utils, bankBaseResp,
				goodsList, order, company,1);
		//删除未成功调用释放
		if(null != bankBaseResp && !RestCode.CODE_0000.getCode().equals(bankBaseResp.getCode())){
			bankBaseResp = cancelOperate(loanNumber, utils, bankBaseResp,
					goodsList, order, company,2);
			//释放调成功 再调删除
			if(null != bankBaseResp && RestCode.CODE_0000.getCode().equals(bankBaseResp.getCode())){
				bankBaseResp = cancelOperate(loanNumber, utils, bankBaseResp,
						goodsList, order, company,1);
			}
		}
		return bankBaseResp;
	}
	/**
	 * 删除/释放订单
	 * @param loanNumber
	 * @param utils
	 * @param bankBaseResp
	 * @param goodsList
	 * @param order
	 * @param company
	 * @param operateType 1 删除  2释放
	 * @return
	 */
	private BankBaseResp cancelOperate(B2bLoanNumberDto loanNumber,
			XmlUtils utils, BankBaseResp bankBaseResp,
			List<OrderGoodsDtoEx> goodsList, B2bOrderDtoMa order,
			B2bCompanyDto company,Integer operateType) {
		String reqXml = utils.orderUpload(order, goodsList, company, null,operateType,loanNumber.getLoanEndTime());
		ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
		ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), "com.icbc.gyj.eloansplus.orderupload", PropertyConfig.getProperty("gs_appid"));
		req.setRequestField("version", version);//接口版本，1.0.0.0
		req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
		req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
		req.setRequestField("trancode", "RESULTQUERY");//交易代码，详见接口文档
		req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
		try {
			bankBaseResp =	executeAnother(ac, req,reqXml,"cancel");
		} catch (Exception e) {
			bankBaseResp.setCode(RestCode.CODE_S999.getCode());
			logger.error("cancelOrder", e);
		}
		return bankBaseResp;
	}

	@Override
	public BankBaseResp skblOrderPay(B2bRepaymentRecord record) {
		XmlUtils utils = new XmlUtils();
		BankBaseResp resp = new BankBaseResp();
		try {
			String reqXml = utils.skblOrderPay(record);
			ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
			ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), PropertyConfig.getProperty("gs_skbl_pay"), PropertyConfig.getProperty("gs_appid"));
			req.setRequestField("version", version2);//接口版本，1.0.0.0
			req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
			req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
			req.setRequestField("trancode", "SKBLORDERPAY");//交易代码，详见接口文档
			req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
			JSONObject json = utils.getJsonByXmlAnother(executeNoDecode(ac, req, reqXml, "agentPay"));
			//orderstatus: 0-待支付 1-订单支付中 2-支付成功 3-支付失败
			if(null != json && !json.isNull("orderno") && !json.isNull("orderstatus")){
				//成功
				if(json.getInt("orderstatus")==2){
					resp.setAgentPayStatus(2);
				//失败
				}else if(json.getInt("orderstatus")==3){
					resp.setAgentPayStatus(3);
				//支付未结束
				}else{
					resp.setAgentPayStatus(1);
				}
			}else{
				resp.setAgentPayStatus(3);
			}
		} catch (ApiException e) {
			resp.setAgentPayStatus(3);
			logger.error("skblOrderPay", e);
		}
		return resp;
	}

	@Override
	public BankBaseResp skblOrderQry(B2bRepaymentRecord record) {
		XmlUtils utils = new XmlUtils();
		BankBaseResp resp = null;
		String reqXml = utils.skblOrderQry(record);
		ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
		ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), PropertyConfig.getProperty("gs_skbl_qry"), PropertyConfig.getProperty("gs_appid"));
		req.setRequestField("version", version2);//接口版本，1.0.0.0
		req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
		req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
		req.setRequestField("trancode", "SKBLORDERQRY");//交易代码，详见接口文档
		req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
		try {
			JSONObject json = utils.getJsonByXmlAnother(executeNoDecode(ac, req, reqXml, "agentQry"));
			resp = new BankBaseResp();
			if(!json.isNull("total") && json.getInt("total") == 1){
				//orderstatus: 0-待支付 1-订单支付中 2-支付成功 3-支付失败
				int status = json.getJSONObject("rds").getJSONObject("rd").getInt("orderstatus");
				if(status==2){
					resp.setAgentPayStatus(2);
				}else{
					resp.setAgentPayStatus(1);
				}
			}
		} catch (ApiException e) {
			logger.error("skblOrderQry", e);
		}
		return resp;
	}
	
	private String execute(ApiClient ac, ApiRequest req,String xml,String code) throws ApiException {
		String id = this.saveBankInfo(null, null,xml, null, "active",code);
		ApiResponse ar = ac.execute(req);
		String resp = null;
		if(ar.isCheckValid() && ar.getLong("ICBC_API_RETCODE")==0){
			Map<Object, Object> res = ar.getMap("response");
			if(null != res && null !=res.get("status") && "000".equals(res.get("status").toString())){
				Map<String, Object> data = JsonUtils.toHashMap(res.get("data"));
				if(null != data.get("retcode") && "200".equals(data.get("retcode").toString())){
					resp= BaseCodeUtils.base64Decode(data.get("ansxml").toString(), charset);
				}else{
					resp = ar.getSignBlock();
				}
			}else{
				resp = ar.getSignBlock();
			}
		}else{
			resp = ar.getSignBlock();
		}
		this.saveBankInfo(null, id, xml, resp, "active",code);
		return resp;
	}
	
	private String executeNoDecode(ApiClient ac, ApiRequest req,String xml,String code) throws ApiException {
		String id = this.saveBankInfo(null, null,xml, null, "active",code);
		ApiResponse ar = ac.execute(req);
		String resp = null;
		if(ar.isCheckValid() && ar.getLong("ICBC_API_RETCODE")==0){
			Map<Object, Object> res = ar.getMap("response");
			if(null != res.get("status") && "000".equals(res.get("status").toString())){
				Map<String, Object> data = JsonUtils.toHashMap(res.get("data"));
				if(null !=data.get("retcode") && "0000".equals(data.get("retcode").toString())){
					resp= data.get("ansxml").toString();
				}else{
					resp = ar.getSignBlock();
				}
			}else{
				resp = ar.getSignBlock();
			}
		}else{
			resp = ar.getSignBlock();
		}
		this.saveBankInfo(null, id, xml, resp, "active",code);
		return resp;
	}
	
	private BankBaseResp executeAnother(ApiClient ac, ApiRequest req,String xml,String code) throws ApiException {
		String id = this.saveBankInfo(null, null,xml, null, "active",code);
		ApiResponse ar = ac.execute(req);
		BankBaseResp baseResp = new BankBaseResp();
		String resp = null;
		if(ar.isCheckValid() && ar.getLong("ICBC_API_RETCODE")==0){
			Map<Object, Object> res = ar.getMap("response");
			if(null !=res.get("status") &&"000".equals(res.get("status").toString())){
				Map<String, Object> data = JsonUtils.toHashMap(res.get("data"));
				if(null !=data.get("retcode") && "200".equals(data.get("retcode").toString())){
					resp= BaseCodeUtils.base64Decode(data.get("ansxml").toString(), charset);
					XmlUtils xu = new XmlUtils();
					JSONObject json = new JSONObject();
					try {
						json = xu.getJsonByXml(resp).getJSONObject("rd");
					} catch (Exception e) {
						JSONArray array = xu.getJsonByXml(resp).getJSONArray("rd");
						json = array.getJSONObject(0);
					} 
					if(!json.isNull("UploadStatus") && "1".equals(json.get("UploadStatus").toString())){
						baseResp.setCode(RestCode.CODE_0000.getCode());
					}else{
						baseResp.setCode(RestCode.CODE_Z000.getCode());
						baseResp.setMsg(json.get("UploadMessage").toString());
					}
				}else{
					resp = ar.getSignBlock();
					baseResp.setCode(RestCode.CODE_Z000.getCode());
					baseResp.setMsg(data.get("retmsg").toString());
				}
			}else{
				resp = ar.getSignBlock();
				baseResp.setCode(RestCode.CODE_Z000.getCode());
				baseResp.setMsg("银行接口异常");
			}
		}else{
			resp = ar.getSignBlock();
			baseResp.setCode(RestCode.CODE_Z000.getCode());
			baseResp.setMsg("银行接口异常");
		}
		this.saveBankInfo(null, id, xml, resp, "active",code);
		return baseResp;
	}
	
	

	@Override
	public void jumpLoanPage(Integer source, B2bLoanNumberDto b2bLoanNumberDto,HttpServletResponse resp) {
		XmlUtils utils = new XmlUtils();
		String method = "com.icbc.gyj.mobile.pagejump";//手机端
		String backUrl = PropertyConfig.getProperty("gs_return_wap_url");
		if(source ==1){//pc端
			method = "com.icbc.gyj.pc.pagejump";
			backUrl = PropertyConfig.getProperty("gs_return_pc_url");
		}
		try {
			   B2bCompanyDto company = foreignCompanyService.getCompany(b2bLoanNumberDto.getPurchaserPk());
					B2bOrderDtoMa odto = foreignOrderService.getOrder(b2bLoanNumberDto.getOrderNumber());
				if(null == odto){
					odto = foreignOrderService.getContract(b2bLoanNumberDto.getOrderNumber());
				}
			   String reqXml = utils.loanPage(b2bLoanNumberDto,odto.getSupplier().getBankAccount());
			   logger.info("reqXml-------------------"+reqXml);
			   ApiClient ac = new ApiClient(PropertyConfig.getProperty("gs_pri_key"), PropertyConfig.getProperty("gs_pub_key"));
			   ApiRequest req = new ApiRequest(PropertyConfig.getProperty("gs_bank_url"), method, PropertyConfig.getProperty("gs_appid"));
			   req.setRequestField("version", version);//接口版本，1.0.0.0
			   req.setRequestField("charset", charset);//编码字符集，base64编码中使用的字符集，目前仅支持utf-8
			   req.setRequestField("merid", PropertyConfig.getProperty("gs_merid"));//商户编号
			   req.setRequestField("trancode", "LOANPAGE");//交易代码，详见接口文档
			   req.setRequestField("customerid", b2bLoanNumberDto.getOrganizationCode());
			   req.setRequestField("customername", b2bLoanNumberDto.getPurchaserName());
			   req.setRequestField("role", 1);//客户角色，下游：1，上游：11
			   if(null != company){
				   req.setRequestField("mobile", company.getContactsTel());//客户手机号
			   }
			   req.setRequestField("url", backUrl);//url：企业地址，用于跳转返回
			   req.setRequestField("reqdata", BaseCodeUtils.base64Encode(reqXml, charset));//交易对应的报文，采用xml格式，详见接口文档发送包
			   ac.post2Site(req, resp);
		} catch (Exception e) {
			logger.error("jumpLoanPage",e);
		}
	}
	
	public String saveBankInfo(String code, String id, String requestValue,
			 String responseValue,String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("gongshangBank");
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
