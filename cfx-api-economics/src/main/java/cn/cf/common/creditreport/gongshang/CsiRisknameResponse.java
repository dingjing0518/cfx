package cn.cf.common.creditreport.gongshang;

import com.icbc.api.IcbcResponse;

/**
 * 风险查询
 * @author Chan
 *
 */
public class CsiRisknameResponse extends IcbcResponse{
	public CsiRisknameResponse(){
		this.setMsg("调用接口失败");
		this.setRespCd("-1");
	}
	private String msg;
	private String respCd;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRespCd() {
		return respCd;
	}
	public void setRespCd(String respCd) {
		this.respCd = respCd;
	}
	private Object data;
	public String getData() {
		return data == null ? "" : data.toString();
	}
	public void setData(Object data) {
		this.data = data;
	}
}
