package cn.cf.common.onlinepay.gongshang;

import com.icbc.api.IcbcResponse;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;

public class MybankPayCpayCpordercloseResponseV1 extends IcbcResponse {
	
	
	//响应参数集合,包含公共和业务参数
	@JSONField(name ="response_biz_content")
	private String response_biz_content;
	//针对返回参数集合的签名
	@JSONField(name ="sign")
	private String sign;
	//transOk  返回码，交易成功返回0，正表示业务报错，负表示系统报错，负值时须考虑疑帐 
//	@JSONField(name ="transOk")
//	private int transOk;
	
	@JSONField(name ="errorNo")
	private String errorNo;
	
	@JSONField(name ="status")
	private String status;
	
	@JSONField(name ="agreeCode")
	private String agreeCode;
	
	@JSONField(name ="partnerSeq")
	private String partnerSeq;
	
	public String getAgreeCode() {
		return agreeCode;
	}
	public void setAgreeCode(String agreeCode) {
		this.agreeCode = agreeCode;
	}
	public String getPartnerSeq() {
		return partnerSeq;
	}
	public void setPartnerSeq(String partnerSeq) {
		this.partnerSeq = partnerSeq;
	}
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
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
//	public int getTransOk() {
//		return transOk;
//	}
//	public void setTransOk(int transOk) {
//		this.transOk = transOk;
//	}
	
}
