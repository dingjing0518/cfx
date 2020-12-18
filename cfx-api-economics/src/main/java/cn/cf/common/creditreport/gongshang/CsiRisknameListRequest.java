package cn.cf.common.creditreport.gongshang;

import java.util.Map;

import com.icbc.api.BizContent;
import com.icbc.api.IcbcRequest;
import com.icbc.api.IcbcResponse;
/**
 * 征信查询
 * @author Chan
 *
 */
public class CsiRisknameListRequest implements IcbcRequest<IcbcResponse>{
	private String serviceUrl = "";
	@Override
	public BizContent getBizContent() {
		return bizContent;
	}
	@Override
	public Class<? extends BizContent> getBizContentClass() {
		return (Class<? extends BizContent>) CsiRisknameListInfoRequestBiz.class;
	}

	@Override
	public Map<String, String> getExtraParameters() {
		return extraParameters;
	}
	@Override
	public String getMethod() {
		return "POST";
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<IcbcResponse> getResponseClass() {
		return (Class<IcbcResponse>) response.getClass();
	}
	private IcbcResponse response;
	public void setResponse(IcbcResponse response){
		this.response = response;
	}
	@Override
	public String getServiceUrl() {
		return serviceUrl;
	}

	@Override
	public boolean isNeedEncrypt() {
		return false;
	}
	private BizContent bizContent;
	@Override
	public void setBizContent(BizContent bizContent) {
		this.bizContent = bizContent;
	}
	@Override
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	private Map<String, String> extraParameters;
	public void setExtraParameters(Map<String, String> extraParameters) {
		this.extraParameters = extraParameters;
	}
	
	public static class CsiRisknameListInfoRequestBiz implements BizContent{
		private String ver;
		private String bankId;
		private String appId;
		private final String source = "3";
		private String type;
		private String ip;
		private String time;
		private Object reqData;
		private String userId;
		private final String language = "zh_CN";
		public String getVer() {
			return ver;
		}
		public String getBankId() {
			return bankId;
		}
		public void setBankId(String bankId) {
			this.bankId = bankId;
		}
		public String getType() {
			return type;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		
		public Object getReqData() {
			return reqData;
		}
		public void setReqData(Object reqData) {
			this.reqData = reqData;
		}
		public String getSource() {
			return source;
		}
		public void setVer(String ver) {
			this.ver = ver;
		}
		public String getLanguage() {
			return language;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		
	}
}
