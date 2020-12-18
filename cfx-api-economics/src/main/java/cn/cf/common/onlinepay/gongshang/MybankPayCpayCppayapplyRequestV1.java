package cn.cf.common.onlinepay.gongshang;

import com.icbc.api.AbstractIcbcRequest;
import com.icbc.api.BizContent;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;

import java.util.List;

public class MybankPayCpayCppayapplyRequestV1 extends AbstractIcbcRequest<MybankPayCpayCppayapplyResponseV1> {
	
	public Class<MybankPayCpayCppayapplyResponseV1> getResponseClass() {
		return MybankPayCpayCppayapplyResponseV1.class;
	}
	
	public MybankPayCpayCppayapplyRequestV1() {
		this.setServiceUrl("https://gw.open.icbc.com.cn/api/mybank/pay/cpay/cppayapply/V1");
	}
	
	public boolean isNeedEncrypt() {
		return false;
	}
	
	public String getMethod() {
		return "POST";
	}
	
	public Class<? extends BizContent> getBizContentClass() {
		return MybankPayCpayCppayapplyRequestV1Biz.class;
	}
	
	public static class MybankPayCpayCppayapplyRequestV1Biz implements BizContent {

		@JSONField(
				name = "payMode"
		)
		private String payMode;

		@JSONField(
				name = "payEntitys"
		)
		private String payEntitys;

		@JSONField(
				name = "partnerSeq"
		)
		private String partnerSeq;
		
		@JSONField(
				name = "internationalFlag"
		)
		private String internationalFlag;

		@JSONField(
				name = "reservDirect"
		)
		private String reservDirect;

		@JSONField(
				name = "payChannel"
		)
		private String payChannel;
		
		
		@JSONField(
				name = "agreeCode"
		)
		private String agreeCode;
		
		@JSONField(
				name = "returnUrl"
		)
		private String returnUrl;

		@JSONField(
				name = "callbackUrl"
		)
		private String callbackUrl;

		@JSONField(
				name = "payerAccname"
		)
		private String payerAccname;
		
		@JSONField(
				name = "payerFeeAccno"
		)
		private String payerFeeAccno;
		
		@JSONField(
				name = "payerFeeAccName"
		)
		private String payerFeeAccName;
		
		@JSONField(
				name = "payerFeeCurr"
		)
		private String payerFeeCurr;
		
		@JSONField(
				name = "payMemno"
		)
		private String payMemno;
		
		@JSONField(
				name = "orgcode"
		)
		private String orgcode;

		@JSONField(
				name = "payerAccno"
		)
		private String payerAccno;

		@JSONField(
				name = "orderCode"
		)
		private String orderCode;

		@JSONField(
				name = "orderAmount"
		)
		private String orderAmount;
		
		@JSONField(
				name = "orderCurr"
		)
		private String orderCurr;
		
		@JSONField(
				name = "sumPayamt"
		)
		private String sumPayamt;
		
		@JSONField(
				name = "submitTime"
		)
		private String submitTime;
		
		@JSONField(
				name = "orderRemark"
		)
		private String orderRemark;

		@JSONField(
				name = "rceiptRemark"
		)
		private String rceiptRemark;
		
		@JSONField(
				name = "asynFlag"
		)
		private String asynFlag;
		
		@JSONField(
				name = "logonId"
		)
		private String logonId;
		
		
		@JSONField(
				name = "agreementId"
		)
		private String agreementId; 
		
		@JSONField(
				name = "invoiceId"
		)
		private String invoiceId;
		
		@JSONField(
				name = "manifestId"
		)
		private String manifestId; 
		
		@JSONField(
				name = "agreementImageId"
		)
		private String agreementImageId;

		@JSONField(
				name = "payeeList"
		)
		private List<BeanRecvMallInfo> payeeList;

		public String getPayMode() {
			return payMode;
		}

		public void setPayMode(String payMode) {
			this.payMode = payMode;
		}

		public String getPayEntitys() {
			return payEntitys;
		}

		public void setPayEntitys(String payEntitys) {
			this.payEntitys = payEntitys;
		}

		public String getPartnerSeq() {
			return partnerSeq;
		}

		public void setPartnerSeq(String partnerSeq) {
			this.partnerSeq = partnerSeq;
		}

		public String getAsynFlag() {
			return asynFlag;
		}
		
		public void setAsynFlag(String asynFlag) {
			this.asynFlag = asynFlag;
		}

		public String getReservDirect() {
			return reservDirect;
		}

		public void setReservDirect(String reservDirect) {
			this.reservDirect = reservDirect;
		}
		
		public String getPayChannel() {
			return payChannel;
		}

		public void setPayChannel(String payChannel) {
			this.payChannel = payChannel;
		}
		
		public String getAgreeCode() {
			return agreeCode;
		}
		
		public void setAgreeCode(String agreeCode) {
			this.agreeCode = agreeCode;
		}
		
		public String getReturnUrl() {
			return returnUrl;
		}
		
		public void setReturnUrl(String returnUrl) {
			this.returnUrl = returnUrl;
		}

		public String getCallbackUrl() {
			return callbackUrl;
		}

		public void setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
		}

		public String getPayerAccname() {
			return payerAccname;
		}

		public void setPayerAccname(String payerAccname) {
			this.payerAccname = payerAccname;
		}
		
		public String getPayMemno() {
			return payMemno;
		}
		
		public void setPayMemno(String payMemno) {
			this.payMemno = payMemno;
		}

		public String getPayerAccno() {
			return payerAccno;
		}

		public void setPayerAccno(String payerAccno) {
			this.payerAccno = payerAccno;
		}

		public String getOrderCode() {
			return orderCode;
		}

		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}
		
		public String getOrderAmount() {
			return orderAmount;
		}

		public void setOrderAmount(String orderAmount) {
			this.orderAmount = orderAmount;
		}
		
		public String getOrderCurr() {
			return orderCurr;
		}
		
		public void setOrderCurr(String orderCurr) {
			this.orderCurr = orderCurr;
		}
		
		public String getSumPayamt() {
			return sumPayamt;
		}
		
		public void setSumPayamt(String sumPayamt) {
			this.sumPayamt = sumPayamt;
		}
		
		public String getSubmitTime() {
			return submitTime;
		}
		
		public void setSubmitTime(String submitTime) {
			this.submitTime = submitTime;
		}
		
		public String getOrderRemark() {
			return orderRemark;
		}
		
		public void setOrderRemark(String orderRemark) {
			this.orderRemark = orderRemark;
		}
		
		public String getRceiptRemark() {
			return rceiptRemark;
		}

		public void setRceiptRemark(String rceiptRemark) {
			this.rceiptRemark = rceiptRemark;
		}

		public String getAgreementId() {
			return agreementId;
		}
		
		public void setAgreementId(String agreementId) {
			this.agreementId = agreementId;
		}
		
		public String getInvoiceId() {
			return invoiceId;
		}
		
		public void setInvoiceId(String invoiceId) {
			this.invoiceId = invoiceId;
		}
		
		public String getManifestId() {
			return manifestId;
		}
		
		public void setManifestId(String manifestId) {
			this.manifestId = manifestId;
		}
		
		
		
		public String getPayerFeeAccno() {
			return payerFeeAccno;
		}

		public void setPayerFeeAccno(String payerFeeAccno) {
			this.payerFeeAccno = payerFeeAccno;
		}

		public String getPayerFeeAccName() {
			return payerFeeAccName;
		}

		public void setPayerFeeAccName(String payerFeeAccName) {
			this.payerFeeAccName = payerFeeAccName;
		}

		public String getPayerFeeCurr() {
			return payerFeeCurr;
		}

		public void setPayerFeeCurr(String payerFeeCurr) {
			this.payerFeeCurr = payerFeeCurr;
		}

		public String getAgreementImageId() {
			return agreementImageId;
		}
		
		public void setAgreementImageId(String agreementImageId) {
			this.agreementImageId = agreementImageId;
		}
		
		public String getInternationalFlag() {
			return internationalFlag;
		}

		public void setInternationalFlag(String internationalFlag) {
			this.internationalFlag = internationalFlag;
		}

		public String getOrgcode() {
			return orgcode;
		}

		public void setOrgcode(String orgcode) {
			this.orgcode = orgcode;
		}

		public String getLogonId() {
			return logonId;
		}

		public void setLogonId(String logonId) {
			this.logonId = logonId;
		}
		
		public List<BeanRecvMallInfo> getPayeeList() {
			return payeeList;
		}

		public void setPayeeList(List<BeanRecvMallInfo> payeeList) {
			this.payeeList = payeeList;
		}

		public List<BeanGoodsInfo> getGoodsList() {
			return goodsList;
		}
		
		public void setGoodsList(List<BeanGoodsInfo> goodsList) {
			this.goodsList = goodsList;
		}
		
		@JSONField(
				name = "goodsList"
		)
		private List<BeanGoodsInfo> goodsList;
		
	}
	
	public static class BeanGoodsInfo {
		
		@JSONField(
				name = "goodsSubId"
		)
		private String goodsSubId;
		
		
		@JSONField(
				name = "goodsName"
		)
		private String goodsName;
		
		
		@JSONField(
				name = "payeeCompanyName"
		)
		private String payeeCompanyName;
		
		
		@JSONField(
				name = "goodsNumber"
		)
		private String goodsNumber;
		
		@JSONField(
				name = "goodsUnit"
		)
		private String goodsUnit; //add 
		
		@JSONField(
				name = "goodsAmt"
		)
		
		private String goodsAmt;
		
		public String getGoodsSubId() {
			return goodsSubId;
		}
		
		public void setGoodsSubId(String goodsSubId) {
			this.goodsSubId = goodsSubId;
		}
		
		public String getGoodsName() {
			return goodsName;
		}
		
		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}
		
		public String getPayeeCompanyName() {
			return payeeCompanyName;
		}
		
		public void setPayeeCompanyName(String payeeCompanyName) {
			this.payeeCompanyName = payeeCompanyName;
		}
		
		public String getGoodsNumber() {
			return goodsNumber;
		}
		
		public void setGoodsNumber(String goodsNumber) {
			this.goodsNumber = goodsNumber;
		}
		
		public String getGoodsUnit() {
			return goodsUnit;
		}

		public void setGoodsUnit(String goodsUnit) {
			this.goodsUnit = goodsUnit;
		}

		public String getGoodsAmt() {
			return goodsAmt;
		}
		
		public void setGoodsAmt(String goodsAmt) {
			this.goodsAmt = goodsAmt;
		}
	}
	
	public static class BeanRecvMallInfo {
		
		@JSONField(
				name = "mallCode"
		)
		private String mallCode; 
		
		@JSONField(
				name = "mccCode"
		)
		private String mccCode;
		
		@JSONField(
				name = "mccName"
		)
		private String mccName; 
		
		@JSONField(
				name = "businessLicense"
		)
		private String businessLicense; 
		
		@JSONField(
				name = "businessLicenseType"
		)
		private String businessLicenseType; 
		
		@JSONField(
				name = "mallName"
		)
		private String mallName;



		@JSONField(
				name = "payeeCompanyName"
		)
		private String payeeCompanyName;
		
		@JSONField(
				name = "payeeSysflag"
		)
		private String payeeSysflag; 
		
		@JSONField(
				name = "payeeBankCode"
		)
		private String payeeBankCode; 
		
		@JSONField(
				name = "payeeAccno"
		)
		private String payeeAccno;
		
		@JSONField(
				name = "payAmount"
		)
		private String payAmount; 
		
		@JSONField(
				name = "payeeBankCountry"
		)
		private String payeeBankCountry; 
		
		@JSONField(
				name = "rbankname"
		)
		private String rbankname; 
		
		@JSONField(
				name = "payeeBankSign"
		)
		private String payeeBankSign; 
		
		@JSONField(
				name = "payeeCountry"
		)
		private String payeeCountry; 
		
		@JSONField(
				name = "payeeAddress"
		)
		private String payeeAddress;
		
		@JSONField(
				name = "racaddress1"
		)
		private String racaddress1;
		
		@JSONField(
				name = "racaddress2"
		)
		private String racaddress2; 
		
		@JSONField(
				name = "racaddress3"
		)
		private String racaddress3; 
		
		@JSONField(
				name = "racaddress4"
		)
		private String racaddress4;
		
		@JSONField(
				name = "racpostcode"
		)
		private String racpostcode; 
		
		@JSONField(
				name = "agentbic"
		)
		private String agentbic; 
		
		public String getMallCode() {
			return mallCode;
		}
		
		public void setMallCode(String mallCode) {
			this.mallCode = mallCode;
		}
		
		public String getMccCode() {
			return mccCode;
		}
		
		public void setMccCode(String mccCode) {
			this.mccCode = mccCode;
		}
		
		public String getMccName() {
			return mccName;
		}
		
		public void setMccName(String mccName) {
			this.mccName = mccName;
		}
		
		public String getBusinessLicense() {
			return businessLicense;
		}
		
		public void setBusinessLicense(String businessLicense) {
			this.businessLicense = businessLicense;
		}
		
		public String getBusinessLicenseType() {
			return businessLicenseType;
		}
		
		public void setBusinessLicenseType(String businessLicenseType) {
			this.businessLicenseType = businessLicenseType;
		}
		
		
		public String getMallName() {
			return mallName;
		}

		public void setMallName(String mallName) {

			this.mallName = mallName;
		}
		public String getPayeeCompanyName() {
			return payeeCompanyName;
		}

		public void setPayeeCompanyName(String payeeCompanyName) {
			this.payeeCompanyName = payeeCompanyName;
		}

		public String getPayeeSysflag() {
			return payeeSysflag;
		}
		
		public void setPayeeSysflag(String payeeSysflag) {
			this.payeeSysflag = payeeSysflag;
		}
		
		public String getPayeeBankCode() {
			return payeeBankCode;
		}
		
		public void setPayeeBankCode(String payeeBankCode) {
			this.payeeBankCode = payeeBankCode;
		}
		
		public String getPayeeAccno() {
			return payeeAccno;
		}
		
		public void setPayeeAccno(String payeeAccno) {
			this.payeeAccno = payeeAccno;
		}
		
		public String getPayAmount() {
			return payAmount;
		}
		
		public void setPayAmount(String payAmount) {
			this.payAmount = payAmount;
		}
		
		public String getPayeeBankCountry() {
			return payeeBankCountry;
		}
		
		public void setPayeeBankCountry(String payeeBankCountry) {
			this.payeeBankCountry = payeeBankCountry;
		}
		
		public String getRbankname() {
			return rbankname;
		}
		
		public void setRbankname(String rbankname) {
			this.rbankname = rbankname;
		}
		
		public String getPayeeBankSign() {
			return payeeBankSign;
		}
		
		public void setPayeeBankSign(String payeeBankSign) {
			this.payeeBankSign = payeeBankSign;
		}
		
		public String getPayeeCountry() {
			return payeeCountry;
		}
		
		public void setPayeeCountry(String payeeCountry) {
			this.payeeCountry = payeeCountry;
		}
		
		public String getPayeeAddress() {
			return payeeAddress;
		}
		
		public void setPayeeAddress(String payeeAddress) {
			this.payeeAddress = payeeAddress;
		}
		
		public String getRacaddress1() {
			return racaddress1;
		}
		
		public void setRacaddress1(String racaddress1) {
			this.racaddress1 = racaddress1;
		}
		
		public String getRacaddress2() {
			return racaddress2;
		}
		
		public void setRacaddress2(String racaddress2) {
			this.racaddress2 = racaddress2;
		}
		
		public String getRacaddress3() {
			return racaddress3;
		}
		
		public void setRacaddress3(String racaddress3) {
			this.racaddress3 = racaddress3;
		}
		
		public String getRacaddress4() {
			return racaddress4;
		}
		
		public void setRacaddress4(String racaddress4) {
			this.racaddress4 = racaddress4;
		}
		
		public String getRacpostcode() {
			return racpostcode;
		}
		
		public void setRacpostcode(String racpostcode) {
			this.racpostcode = racpostcode;
		}
		
		public String getAgentbic() {
			return agentbic;
		}
		
		public void setAgentbic(String agentbic) {
			this.agentbic = agentbic;
		}
		
	}
	
}
