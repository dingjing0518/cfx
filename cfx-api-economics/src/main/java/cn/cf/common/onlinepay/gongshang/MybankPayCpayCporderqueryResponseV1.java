package cn.cf.common.onlinepay.gongshang;

import java.util.HashMap;
import java.util.List;

import com.icbc.api.IcbcResponse;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;



public class MybankPayCpayCporderqueryResponseV1 extends IcbcResponse {

	@JSONField(name = "response_biz_content")
	private String response_biz_content;
	
	@JSONField(name = "sign")
	private String sign;

	@JSONField(name = "serialNo")
	private String serialNo;

	@JSONField(name = "agreeCode")
	private String agreeCode;

	@JSONField(name = "partnerSeq")
	private String partnerSeq;

	@JSONField(name = "payMode")
	private String payMode;

	@JSONField(name = "orderAmount")
	private String orderAmount;

	@JSONField(name = "orderCurr")
	private String orderCurr;

	@JSONField(name = "sumPayamt")
	private String sumPayamt;

	@JSONField(name = "payerAccno")
	private String payerAccno;

	@JSONField(name = "payerAccname")
	private String payerAccname;
	
	@JSONField(name = "agreeName")
	private String agreeName;
	
	@JSONField(name = "source")
	private String source;
	
	@JSONField(name = "accrualCny")
	private String accrualCny;
	
	@JSONField(name = "accrualForeign")
	private String accrualForeign;
	
	@JSONField(name = "payerAccnoForeign")
	private String payerAccnoForeign;
	
	@JSONField(name = "payerAccnameForeign")
	private String payerAccnameForeign;
	
	@JSONField(name = "payType")
	private String payType;
	
	@JSONField(name = "payStatus")
	private String payStatus;

	@JSONField(name = "payPlanList")
	private List<HashMap<String, Object>> payPlanList;

	@JSONField(name = "payeeList")
	private List<HashMap<String, Object>> payeeList;

	@JSONField(name = "goodsList")
	private List<HashMap<String, Object>> goodsList;



	public String getResponse_biz_content() {
		return response_biz_content;
	}

	public void setResponse_biz_content(String response_biz_content) {
		this.response_biz_content = response_biz_content;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

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

	public String getPayerAccno() {
		return payerAccno;
	}

	public void setPayerAccno(String payerAccno) {
		this.payerAccno = payerAccno;
	}

	public String getPayerAccname() {
		return payerAccname;
	}

	public void setPayerAccname(String payerAccname) {
		this.payerAccname = payerAccname;
	}

	public List<HashMap<String, Object>> getPayPlanList() {
		return payPlanList;
	}

	public void setPayPlanList(List<HashMap<String, Object>> payPlanList) {
		this.payPlanList = payPlanList;
	}

	public List<HashMap<String, Object>> getPayeeList() {
		return payeeList;
	}

	public void setPayeeList(List<HashMap<String, Object>> payeeList) {
		this.payeeList = payeeList;
	}

	public List<HashMap<String, Object>> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<HashMap<String, Object>> goodsList) {
		this.goodsList = goodsList;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAccrualCny() {
		return accrualCny;
	}

	public void setAccrualCny(String accrualCny) {
		this.accrualCny = accrualCny;
	}

	public String getAccrualForeign() {
		return accrualForeign;
	}

	public void setAccrualForeign(String accrualForeign) {
		this.accrualForeign = accrualForeign;
	}

	public String getPayerAccnoForeign() {
		return payerAccnoForeign;
	}

	public void setPayerAccnoForeign(String payerAccnoForeign) {
		this.payerAccnoForeign = payerAccnoForeign;
	}

	public String getPayerAccnameForeign() {
		return payerAccnameForeign;
	}

	public void setPayerAccnameForeign(String payerAccnameForeign) {
		this.payerAccnameForeign = payerAccnameForeign;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	

}
