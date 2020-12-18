package cn.cf.common.creditpay.shanghai;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.property.PropertyConfig;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.cf.http.SingletonHttpclient;


public class PostUtils {

	public String post(String method, String params) {
		String result = null;
		// 登录
		String loginResult = logon();
		System.out.println("帐号登陆结果：=" + loginResult + "=");
		//登录成功后做业务处理
		if (null != loginResult && !"".equals(loginResult) && loginResult.contains("<retCode>0</retCode>")) {
			//截取前40位sessionId
			String sessionId = loginResult.substring(0, 40);
			try {
				String url = "http://" + PropertyConfig.getProperty("sh_api_ip") + ":" + PropertyConfig.getProperty("sh_pre_port") + "/CM/APIReqServlet";
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("dse_sessionId", sessionId);
				paraMap.put("opName", method);
				paraMap.put("reqData", params);
				result = sendHttp(url, paraMap);
				System.out.println("接口返回结果：="+result);
			} catch (Exception e) {
				System.out.println("银行返回信息异常:" + e);
			}
		} else {
			System.out.println("没有登陆正确:" + loginResult);
			result = "loginFail"+loginResult;
		}
		return result;
	}


	/**
	 * 登陆
	 */
	public String logon() {
		XmlUtils xmlUtils = new XmlUtils();
		String reqData = SignOpStep.getNodeValue((new SignOpStep(xmlUtils.login(
				PropertyConfig.getProperty("sh_user_id"),PropertyConfig.getProperty("sh_user_pwd"))))
				.sign(), "signed_data");
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("opName", "CebankUserLogon1_1Op");
		paraMap.put("reqData", reqData);
		String responseUrl = "http://" + PropertyConfig.getProperty("sh_api_ip") + ":" + PropertyConfig.getProperty("sh_pre_port")+ "/CM/APISessionReqServlet";// 登陆
		return sendHttp(responseUrl, paraMap);
	}

	/**
	 * 数据发送
	 * @param url
	 * @param paraMap
	 * @return
	 */
	private String sendHttp(String url,Map<String,String> paraMap){
		String result="";
		HttpResponse response = null;
		URI uri = URI.create(url);
		url = uri.toASCIIString();
		if (paraMap == null || paraMap.size() == 0) {
		} else {
			List<NameValuePair> paraList = new ArrayList<>();
			for (Map.Entry<String, String> m : paraMap.entrySet()) {
				paraList.add(new BasicNameValuePair(m.getKey(), m
						.getValue()));
			}
			HttpPost httpMethod = null;
			try {
				httpMethod = new HttpPost(url);
				RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(60*1000).build();
				httpMethod.setConfig(requestConfig);
				httpMethod.setHeader("User-Agent", "compatible; MSIE 5.0;");
				httpMethod.setEntity(new UrlEncodedFormEntity(paraList,
						"GBK"));
				response = SingletonHttpclient.getHttpClient().execute(
						httpMethod);
				//网站转换为byte[]
				byte[] bytes = EntityUtils.toByteArray(response.getEntity());
				//根据字符集重新编码成正确的
				String Ori_Entity = new String(bytes, "GBK");
				//转换为统一的utf-8
				result = new String(Ori_Entity.getBytes(), "utf-8");
			} catch (Exception e) {
				System.out.println("sendError==="+e);
				if (httpMethod != null) {
					httpMethod.abort();
				}
			}finally {
				if (httpMethod != null) {
					httpMethod.abort();
				}
			}
		}
		return result;
	}

//	public static void main(String[] arg) {
//		PostUtils p = new PostUtils();
//		String rest =p.post("PusReqExInfo1_1Op", "<?xml version='1.0' encoding = 'GBK'?><BOSEBankData><opReq><serialNo>20200225150152817291</serialNo><reqTime>20200225150152</reqTime><ReqParam><AgreementNo>TBE00510062820022402061</AgreementNo><T24Id>117996677</T24Id><OperaType>1</OperaType><opReqSet><opRequest><PoId>HXO202002251052486511665</PoId><PoRef>HXO202002251052486511665</PoRef><PoType>8</PoType><PoMode>0</PoMode><Ccy>CNY</Ccy><PoSumAmt>4465.44</PoSumAmt><TotalNum></TotalNum><PoStart>2020-02-25</PoStart><PoEnd>2020-12-26</PoEnd><PoLoanRt></PoLoanRt><BuyId>C00002226</BuyId><BuyName></BuyName><SupplyNm>江苏盛虹科贸有限公司</SupplyNm><CustNm>苏州萧然新材料有限公司</CustNm></opRequest></opReqSet></ReqParam></opReq></BOSEBankData>");
//		System.out.println(rest);
//	}

}
