package cn.cf.service.onlinepay.impl;

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
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCpordercloseRequestV1;
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCpordercloseRequestV1.MybankPayCpayCpordercloseV1RequestV1Biz;
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCpordercloseResponseV1;
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCporderqueryRequestV1;
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCporderqueryResponseV1;
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCppayapplyRequestV1;
import cn.cf.common.onlinepay.gongshang.MybankPayCpayCppayapplyResponseV1;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.entry.OrderGoodsDtoEx;
import cn.cf.property.PropertyConfig;
import cn.cf.service.onlinepay.OnlineGongshangService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcApiException;
import com.icbc.api.internal.util.internal.util.fastjson.JSONObject;

@Service
public class OnlineGongshangServiceImpl implements OnlineGongshangService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public BankBaseResp onlinePay(B2bOrderDtoMa odto,List<OrderGoodsDtoEx> goodsList,SysCompanyBankcardDto card) {
		DefaultIcbcClient client = new DefaultIcbcClient(PropertyConfig.getProperty("eqf_appid"), PropertyConfig.getProperty("eqf_pri_key"), PropertyConfig.getProperty("eqf_pub_key"));
		MybankPayCpayCppayapplyRequestV1 request = new MybankPayCpayCppayapplyRequestV1();
		request.setServiceUrl(PropertyConfig.getProperty("eqf_url")+"/api/mybank/pay/cpay/cppayapply/V1");
		
		
		MybankPayCpayCppayapplyRequestV1.MybankPayCpayCppayapplyRequestV1Biz bizContent = new MybankPayCpayCppayapplyRequestV1.MybankPayCpayCppayapplyRequestV1Biz();
		MybankPayCpayCppayapplyRequestV1.BeanRecvMallInfo beanRecvMallInfo = new MybankPayCpayCppayapplyRequestV1.BeanRecvMallInfo();
		
		List<MybankPayCpayCppayapplyRequestV1.BeanGoodsInfo> beanGoodsInfoList = new ArrayList<MybankPayCpayCppayapplyRequestV1.BeanGoodsInfo>();
		List<MybankPayCpayCppayapplyRequestV1.BeanRecvMallInfo> beanRecvMallInfoList = new ArrayList<MybankPayCpayCppayapplyRequestV1.BeanRecvMallInfo>();
		
		bizContent.setAgreeCode(PropertyConfig.getProperty("eqf_agreecode"));//协议号
		bizContent.setPartnerSeq(KeyUtils.getFlowNumber());//流水号
		bizContent.setPayChannel("1");//1 PC
		bizContent.setPayMode("1");//1 直接支付
		bizContent.setPayEntitys("10000000000000000000");//支付工具
		bizContent.setAsynFlag("0");//0 同步
		bizContent.setPayMemno(odto.getMemberName());//会员编号
		bizContent.setOrderCode(odto.getOrderNumber());//订单编号
		Double amount= ArithUtil.mul(odto.getActualAmount(), 100);
		bizContent.setOrderAmount(amount.intValue()+"");//订单金额
		bizContent.setOrderCurr("1");//币种
		bizContent.setSumPayamt(bizContent.getOrderAmount());//本次支付金额
		if(null == PropertyConfig.getProperty("eqf_subtime") || "".equals(PropertyConfig.getProperty("eqf_subtime"))){
			bizContent.setSubmitTime(DateUtil.formatYearMonthDayHms(new Date()));//提交时间
		}else{
			bizContent.setSubmitTime(PropertyConfig.getProperty("eqf_subtime"));//提交时间
		}
		bizContent.setCallbackUrl(PropertyConfig.getProperty("eqf_backurl"));
		//账户信息
		bizContent.setInternationalFlag("1");//1境内
		beanRecvMallInfo.setMallCode(odto.getSupplierPk());//收方商户号
		beanRecvMallInfo.setMallName(odto.getSupplier().getSupplierName());//商户名称
		beanRecvMallInfo.setPayeeCompanyName(odto.getSupplier().getSupplierName());//收款人户名
		beanRecvMallInfo.setPayeeAccno(card.getBankAccount());//收款人账号
		//工商银行bankclscode为102
		if(null !=card.getBankclscode() && "102".equals(card.getBankclscode())){
			beanRecvMallInfo.setPayeeSysflag("1");//1境内工行2境内他行
		}else{
			beanRecvMallInfo.setPayeeSysflag("2");//1境内工行2境内他行
			beanRecvMallInfo.setPayeeBankCode(card.getBankNo());//行号 境内他行时必输 
		}
		beanRecvMallInfo.setPayAmount(bizContent.getOrderAmount());//收款金额 单位:分
//		beanRecvMallInfo.setMccCode("1");
//		beanRecvMallInfo.setMccName("1");
//		beanRecvMallInfo.setBusinessLicense("1");
//		beanRecvMallInfo.setBusinessLicenseType("0");
//		beanRecvMallInfo.setPayeeBankCountry("1");
//		beanRecvMallInfo.setRbankname("1");
//		beanRecvMallInfo.setPayeeBankSign("1");
//		beanRecvMallInfo.setPayeeCountry("1");
//		beanRecvMallInfo.setPayeeAddress("1");
//		beanRecvMallInfo.setRacaddress1("1");
//		beanRecvMallInfo.setRacaddress2("1");
//		beanRecvMallInfo.setRacaddress3("1");
//		beanRecvMallInfo.setRacaddress4("1");
//		beanRecvMallInfo.setRacpostcode("1");
//		beanRecvMallInfo.setAgentbic("1");
		beanRecvMallInfoList.add(beanRecvMallInfo);
		//商品信息
		if(null != goodsList && goodsList.size()>0){
			Integer totalBoxes = 0;
			for(OrderGoodsDtoEx o : goodsList){
				totalBoxes += o.getAfterBoxes();
			}
				OrderGoodsDtoEx o = goodsList.get(0);
					MybankPayCpayCppayapplyRequestV1.BeanGoodsInfo beanGoodsInfo = new MybankPayCpayCppayapplyRequestV1.BeanGoodsInfo();
					beanGoodsInfo.setGoodsSubId("1");//商品序号
					if(null !=o.getProductName() && !"".equals(o.getProductName())){
						beanGoodsInfo.setGoodsName(o.getProductName()+(goodsList.size()>1?"等":""));//商品名称
					}else{
						beanGoodsInfo.setGoodsName(o.getRawMaterialName()+(goodsList.size()>1?"等":""));//商品名称
					}
					beanGoodsInfo.setGoodsAmt(bizContent.getOrderAmount());//商品金额
					beanGoodsInfo.setPayeeCompanyName(odto.getSupplier().getSupplierName());//收款人户名
					beanGoodsInfo.setGoodsNumber(totalBoxes+"");//商品数量
					beanGoodsInfo.setGoodsUnit(o.getUnit());
					beanGoodsInfoList.add(beanGoodsInfo);
		}
		
		bizContent.setPayeeList(beanRecvMallInfoList);
		bizContent.setGoodsList(beanGoodsInfoList);
//		bizContent.setReservDirect("1");
//		bizContent.setReturnUrl("1");
//		bizContent.setPayerAccname("1");
//		bizContent.setPayerAccno("0200062009213057712");
//		bizContent.setPayerFeeAccno("1");
//		bizContent.setPayerFeeAccName("1");
//		bizContent.setPayerFeeCurr("1");
//		bizContent.setRceiptRemark("1");
		
//		bizContent.setAgreementId("1");
//		bizContent.setInvoiceId("1");
//		bizContent.setManifestId("1");
//		bizContent.setAgreementImageId("1");
		
//		request.setBizContent(bizContent);
		
		String msgId = KeyUtils.getFlowNumber();
		
		request.setBizContent(bizContent);
		
		MybankPayCpayCppayapplyResponseV1 response;
		BankBaseResp resp = new BankBaseResp();
		try {
			String rest = JSONObject.toJSONString(request);
			String id = this.saveBankInfo(null, null,rest, null, "active","onlinePay");
			response = client.execute(request,msgId);
			this.saveBankInfo(null, id, rest, JSONObject.toJSONString(response), "active","onlinePay");
			if (response.isSuccess()) {
				resp.setCode(RestCode.CODE_0000.getCode());
				resp.setReturnUrl(response.getRedirectParam());
				resp.setSerialNumber(bizContent.getPartnerSeq());
			} else {
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg(response.getReturnMsg());
			}
		} catch (IcbcApiException e) {
			logger.error("errorOnlinePay",e);
			
		}
		return resp;
	}

	@Override
	public BankBaseResp onlinePaySearch(B2bOnlinepayRecordDto recordDto) {
		DefaultIcbcClient client = new DefaultIcbcClient(PropertyConfig.getProperty("eqf_appid"), PropertyConfig.getProperty("eqf_pri_key"), PropertyConfig.getProperty("eqf_pub_key"));
		
		MybankPayCpayCporderqueryRequestV1 request = new MybankPayCpayCporderqueryRequestV1();
		request.setServiceUrl(PropertyConfig.getProperty("eqf_url")+"/api/mybank/pay/cpay/cporderquery/V1");
		
		MybankPayCpayCporderqueryRequestV1.QueryPayApplyRequestV1Biz bizContent = new MybankPayCpayCporderqueryRequestV1.QueryPayApplyRequestV1Biz();
		
		String msgId = KeyUtils.getFlowNumber();

		bizContent.setAgreeCode(PropertyConfig.getProperty("eqf_agreecode"));
		bizContent.setPartnerSeq(recordDto.getSerialNumber());
		bizContent.setOrderCode(recordDto.getOrderNumber());

		request.setBizContent(bizContent);
		
		MybankPayCpayCporderqueryResponseV1 response;
		BankBaseResp resp = new BankBaseResp();
		try {
			String rest = JSONObject.toJSONString(request);
			String id = this.saveBankInfo(null, null,rest, null, "active","onlineSearch");
			response = client.execute(request,msgId);
			this.saveBankInfo(null, id, rest, JSONObject.toJSONString(response), "active","onlineSearch");
			if (response.isSuccess()) {
				resp.setCode(RestCode.CODE_0000.getCode());
				Integer payStatus = null;
				if(null != response.getPayPlanList().get(0).get("status")){
					String status = response.getPayPlanList().get(0).get("status").toString();
					//成功
					if("1".equals(status)){
						payStatus = 1;
					}
					//失败
					if("2".equals(status) || "6".equals(status) || "7".equals(status)){
						payStatus = 2;
					}
				}
				resp.setPayStatus(payStatus);
			} else {
				resp.setCode(RestCode.CODE_S999.getCode());
			}
		} catch (IcbcApiException e) {
			// TODO Auto-generated catch block
			resp.setCode(RestCode.CODE_S999.getCode());
			logger.info("onlinePaySearch",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp onlinePayCancel(B2bOnlinepayRecordDto recordDto) {
		
		DefaultIcbcClient client = new DefaultIcbcClient(PropertyConfig.getProperty("eqf_appid"), PropertyConfig.getProperty("eqf_pri_key"), PropertyConfig.getProperty("eqf_pub_key"));
        
		MybankPayCpayCpordercloseRequestV1 req = new MybankPayCpayCpordercloseRequestV1();
		
		req.setServiceUrl(PropertyConfig.getProperty("eqf_url")+"/api/mybank/pay/cpay/cporderclose/V1");

		MybankPayCpayCpordercloseV1RequestV1Biz biz = new MybankPayCpayCpordercloseV1RequestV1Biz();
		biz.setAgreeCode(PropertyConfig.getProperty("eqf_agreecode"));
		biz.setPartnerSeq(recordDto.getSerialNumber());
		biz.setOrderCode(recordDto.getOrderNumber());
		req.setBizContent(biz);
		MybankPayCpayCpordercloseResponseV1 response;

		String msgId = KeyUtils.getFlowNumber();
		BankBaseResp resp = new BankBaseResp();
		try {
			String rest = JSONObject.toJSONString(req);
			String id = this.saveBankInfo(null, null,rest, null, "active","onlineCancel");
			response = client.execute(req, msgId);
			this.saveBankInfo(null, id, rest, JSONObject.toJSONString(response), "active","onlineCancel");
			if (response.isSuccess()) {
				resp.setCode(RestCode.CODE_0000.getCode());
				resp.setPayStatus(Integer.parseInt(response.getStatus()));
				resp.setMsg(RestCode.CODE_0000.getMsg());
			} else {
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg(response.getReturnMsg());
			}
		} catch (IcbcApiException e) {
			resp.setCode(RestCode.CODE_S999.getCode());
			resp.setMsg(RestCode.CODE_S999.getMsg());
			logger.error("errorCancel",e);
		}
		return resp;
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
