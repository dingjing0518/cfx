package cn.cf.common.onlinepay.gongshang;

import com.icbc.api.IcbcResponse;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;

public class MybankPayCpayCppayapplyResponseV1 extends IcbcResponse {
	@JSONField(name ="response_biz_content")
	private String response_biz_content;
	@JSONField(name ="sign")
	private String sign;
	
	@JSONField(
			name = "serialNo"
	)
	private String serialNo;
	
	@JSONField(
			name = "agreeCode"
	)
	private String agreeCode;

	@JSONField(
			name = "agreeName"
	)
	private String agreeName;

	@JSONField(
			name = "partnerSeq"
	)
	private String partnerSeq;

	@JSONField(
			name = "payMode"
	)
	private String payMode;

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
			name = "status"
	)
	private String status;
	
	@JSONField(
			name = "redirectParam"
	)
	private String redirectParam;
	
	public String getSerialNo() {
		return serialNo;
	}
	
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public String getAgreeCode() {
		return agreeCode;
	}
	
	public void setAgreeCode(String agreeCode) {
		this.agreeCode = agreeCode;
	}
	
	public String getAgreeName() {
		return agreeName;
	}
	
	public void setAgreeName(String agreeName) {
		this.agreeName = agreeName;
	}

	public String getPartnerSeq() {
		return partnerSeq;
	}

	public void setPartnerSeq(String partnerSeq) {
		this.partnerSeq = partnerSeq;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRedirectParam() {
		return redirectParam;
	}
	
	public void setRedirectParam(String redirectParam) {
		this.redirectParam = redirectParam;
	}
}
