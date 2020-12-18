package cn.cf.common.onlinepay.gongshang;

import com.icbc.api.AbstractIcbcRequest;
import com.icbc.api.BizContent;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;


public class MybankPayCpayCporderqueryRequestV1 extends AbstractIcbcRequest<MybankPayCpayCporderqueryResponseV1> {

	public Class<MybankPayCpayCporderqueryResponseV1> getResponseClass() {
		return MybankPayCpayCporderqueryResponseV1.class;
	}

	public MybankPayCpayCporderqueryRequestV1() {
		this.setServiceUrl("https://gw.open.icbc.com.cn/api/mybank/pay/cpay/cporderquery/V1");
	}

	public boolean isNeedEncrypt() {
		return false;
	}

	public String getMethod() {
		return "POST";
	}

	public Class<? extends BizContent> getBizContentClass() {
		return MybankPayCpayCporderqueryRequestV1.QueryPayApplyRequestV1Biz.class;
	}

	public static class QueryPayApplyRequestV1Biz implements BizContent {
		
		@JSONField(name = "api_name")
		private String api_name;
		
		@JSONField(name = "api_version")
		private String api_version;
		
		@JSONField(name = "app_id")
		private String app_id;
		
		@JSONField(name = "msg_id")
		private String msg_id;
		
		@JSONField(name = "format")
		private String format;
		
		@JSONField(name = "charset")
		private String charset;
		
		@JSONField(name = "encrypt_type")
		private String encrypt_type;
		
		@JSONField(name = "sign_type")
		private String sign_type;
		
		@JSONField(name = "sign")
		private String sign;
		
		@JSONField(name = "timestamp")
		private String timestamp;
		
		@JSONField(name = "ca")
		private String ca;
		
		@JSONField(name = "biz_content")
		private String biz_content;

		@JSONField(name = "agreeCode")
		private String agreeCode;

		@JSONField(name = "partnerSeq")
		private String partnerSeq;

		@JSONField(name = "orderCode")
		private String orderCode;

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

		public String getOrderCode() {
			return orderCode;
		}

		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}

		public String getApi_name() {
			return api_name;
		}

		public void setApi_name(String api_name) {
			this.api_name = api_name;
		}

		public String getApi_version() {
			return api_version;
		}

		public void setApi_version(String api_version) {
			this.api_version = api_version;
		}

		public String getApp_id() {
			return app_id;
		}

		public void setApp_id(String app_id) {
			this.app_id = app_id;
		}

		public String getMsg_id() {
			return msg_id;
		}

		public void setMsg_id(String msg_id) {
			this.msg_id = msg_id;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		public String getCharset() {
			return charset;
		}

		public void setCharset(String charset) {
			this.charset = charset;
		}

		public String getEncrypt_type() {
			return encrypt_type;
		}

		public void setEncrypt_type(String encrypt_type) {
			this.encrypt_type = encrypt_type;
		}

		public String getSign_type() {
			return sign_type;
		}

		public void setSign_type(String sign_type) {
			this.sign_type = sign_type;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getCa() {
			return ca;
		}

		public void setCa(String ca) {
			this.ca = ca;
		}

		public String getBiz_content() {
			return biz_content;
		}

		public void setBiz_content(String biz_content) {
			this.biz_content = biz_content;
		}

	}

}
