package cn.cf.common.creditpay.jianshe;


import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import cn.cf.http.HttpHelper;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.util.EncodeUtil;
import cn.cf.util.XmlTools;

import com.alibaba.fastjson.JSONObject;
import com.ccb.sdk.CCBSDK;
import com.ccb.sdk.bean.AbstractBussinessBean;

public class PostUtils {

	private static boolean isInit = false;
	
	private static String accessToken ="<rsp_code>YDCA08310121</rsp_code>";
	
	private static void init(){
//		String priKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC5e5RawSmsoWHVn8Vg4dvleaQEAQMfCOiDAi999csInBNhu5WR3fJDNQi4BmWooczouDkM3YP2YxLbF3DgvQ2aZ0VaVFW53X42nWqtWW9Z6E7GTflKYxg3RUwLGDXUpK4ZRdVC6YGMPny/mLNJRYkP8vHVeMzEeQUAImLJiBW7nhFjtTz/TvSTTN1itTgyEbSYGEwdOTmiC4/D0OI3qJwIC7FOlATj0zynMb5KuhHwdP6/JKMXILHj2i2HdkW1Uvmp/ZE5Wd8XhI+bJLHhtcWlt/il5I2c10z8/B0Uft0VeYjnYiz8/JQwVVTfe+HZliTp20qWg3Ip11KrWJvTT3JRAgMBAAECggEAZIgPm7TwYonB3ihPsbgZGGJ6vFTUHUEVzNQla48KMatucd1bLwlt4nFfPWbyOJkh18zp1whFGwkN0kP9QUIWmmZ414G4SRo861sc08ISsVdRQvhGMsRM/sAHfEft/UgYuMf5099+AMZplOst2Xvf0w4pdzuXOLIjPLRmeLbSmLIhiPu7yw9p/YxTWy5Idk2n+LPmxRB+Bi+DjnG9FnwiSuMsM320Maoe+YmDbRk932h7j3NQ8a82GuoHlaL42VRA6GvZZDYTqraSaiO1fd1upbpJmz/vmJWsiuPXUnTFZnOx+UDQTW+AWq7eYit6Wqij3K8ymjNkOJk6gBn1XG+WAQKBgQDv8StIa/DFSSRMKHwHLZ4YaNp0166+5qlEyQGiuZ3/AZvy6jtyspUNdc29neEsYMACml23arUKoqkIuG9yKHJF/0elHLuk+Z9fDCmOUKcbhNkZIdV8U6fss5H8xZsXx4tdZ/UFDfBv4vb+r8HVdOz59sDsQtTRwpJFkjpSOjqDMQKBgQDF5WFsvynTUkVySpQlfTnJTDx7iogdaSmebg/VVrnETZwJjmZH80oK30GPhVORPe8JnwmcgwPhllWkx1cJbX2RWXQSwVwPdcTAAkGTXWBcBmKkwNa9GrSEdGT1DomnxpUF/i1xK2hSJt/RNbAdBqcWoXgFe7eTG0adR3EOfZDZIQKBgBTiIKpE+sew/f/C5kiZp9DoD1tsCkGDQehCtmm7Tg0Lzs7tpA7vjBmHz0hZnWeHZC7V0KJOH0O1tJCVvN1qt8dv2IR+Y7aQI1bacCN60ZCLl3mggDw567umRWaCrGT9cRvIGINAG1Uho+22zmANtTJsT45AotU+4gw8FediOgWBAoGBAMAVUWc4deSEKpww/3MUBY75zIstf8jsVlVNB1lQAKglnJs5+NVFtEyxPvWLt/12XBq+n5mnIZFJVn/GbRjdidAXP68kt7LZ4MtuZTAgpnMsT3sTIlwTG7ylLYkfP0X2Xpeei4UZWPzEduKedf72giBZQiIwX5MOGpB206n4/yuBAoGBAKAkxxVwu8diDBp/IUiIeb349FyeyWPAw8qVVAQj3Yu7mjahzuWYvYxAzEAU+A/cJ+ziD4GqCbY4YJ4RW7CWAMibycBtdjJFMbVfNwdW/AphckObvCj/jhwBWCI6ZCgDIUhjedk/fdVymgFqNZzOkaNV/BbyW4x/vMCD1iqMkgtd";
//		CCBSDK.init("D://js//jsyh.properties",priKey);
		CCBSDK.init(PropertyConfig.getProperty("js_properties_path"),PropertyConfig.getProperty("js_pri_key"));
	}
	
	public static String post(AbstractBussinessBean bean){
		String rest = null;
		if(!isInit){
			init();
		}
		try {
			CCBSDK.send("CCBInclsvFnSvc", bean);
			rest = null!=bean.getResp()?JsonUtils.formatJsonObject(bean.getResp()).toString():null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rest;
	}
	
	public static void getToken(){
		String rest = null;
		try {
			Header[] headers = new Header[3];
			headers[0] = new BasicHeader("AuthorizedID", PropertyConfig.getProperty("js_auth_id"));//授权用户id
			headers[1] = new BasicHeader("AuthorizedCode", PropertyConfig.getProperty("js_auth_code"));//授权码
			headers[2] = new BasicHeader("TRANS_CODE","A0831BN02");//交易码
			rest = HttpHelper.customizePost(PropertyConfig.getProperty("js_bank_url"), EncodeUtil.encrypt(XmlUtils.getToken(), PropertyConfig.getProperty("js_key")), headers, "utf-8");
			rest = EncodeUtil.decrypt(rest, PropertyConfig.getProperty("js_key"));
			System.out.println("token-rest:"+rest);
			JSONObject json = XmlTools.xmltoJson(rest);
			json = null!=json?json.getJSONObject("Response"):json;
			if(null !=json && "000000000000".equals(json.get("rsp_code")) && 
					null!=json.get("ext_acs_tokn") && !"".equals(json.get("ext_acs_tokn").toString())){
				JedisUtils.set("js_token",json,3600);
			}else{
				System.out.println("token未获取到");
			}
		} catch (Exception e) {
			System.out.println("errorgetToken"+e);
		}
	}
	
	public static  Header[] getHeaders(String token,String transCode){
		Header[] headers = new Header[4];
		headers[0] = new BasicHeader("Token", token);
		headers[1] = new BasicHeader("AuthorizedID", PropertyConfig.getProperty("js_auth_id"));//授权用户id
		headers[2] = new BasicHeader("AuthorizedCode", PropertyConfig.getProperty("js_auth_code"));//授权码
		headers[3] = new BasicHeader("TRANS_CODE",transCode);//交易码
		return headers;
	}
	
	public static String postUrl(String rest,String transCode){
		String resp = null;
		try {
			JSONObject token = JedisUtils.get("js_token",JSONObject.class);
			if(null ==token){
				getToken();
				token = JedisUtils.get("js_token",JSONObject.class);
			}
			resp = HttpHelper.customizePost(PropertyConfig.getProperty("js_bank_url"), EncodeUtil.encrypt(rest, PropertyConfig.getProperty("js_key")),getHeaders(null==token?"":token.get("ext_acs_tokn").toString(), transCode), "utf-8");
			resp = EncodeUtil.decrypt(resp, PropertyConfig.getProperty("js_key"));
			//token失效验证
			if(null !=resp && resp.contains(accessToken)){
				getToken();
				token = JedisUtils.get("js_token",JSONObject.class);
				resp = HttpHelper.customizePost(PropertyConfig.getProperty("js_bank_url"), EncodeUtil.encrypt(rest, PropertyConfig.getProperty("js_key")),getHeaders(null==token?"":token.get("ext_acs_tokn").toString(), transCode), "utf-8");
				resp = EncodeUtil.decrypt(resp, PropertyConfig.getProperty("js_key"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("postUrl:"+e);
		}
		return resp;
	}
	
	public static void main(String[] args) {
		init();
		BeanUtils bean = new BeanUtils();
//		IFSPQRCodeInfoGetBean demo = bean.getQrCode("HXO201911180954560473529", "ZF0010066461d", "15061981657", "1010", "320298198809227374", "薛三三", "83001099763600000000968456",
//				"2019-11-19 23:59:59", "10000.0", "吴江市顺凯纺织有限公司", "322961015600450610", "品牌采购", "3205840021120151000430", "2", "313305460028", 
//				"913205096933120306");
//		try {
//			// 发送请求，第一个参数为产品ID
//			System.out.println("==============="+demo.ReqToJsonString());
//			CCBSDK.send("CCBInclsvFnSvc", demo);
//			// 读取响应信息
//			System.out.println(JsonUtils.formatJsonObject(demo.getResp()).toString());
//			IFSPQRCodeInfoGetBean.Response response = (Response) demo.getResp();
//			// 公共字段：返回码和返回信息
//			String code = response.getTxn_Rsp_Cd_Dsc();
//			String msg = response.getTxn_Rsp_Inf();
//			String rcode = response.getQr_Code();
//		  	String url = response.getUrl();
//			System.out.println("返回码=["+code+"]");
//			System.out.println("返回信息=["+msg+"]");
//			System.out.println("返回code=["+rcode+"]");
//			System.out.println("返回url=["+url+"]");
//		} catch (Exception e) {
//			
//		}
		IFSPOrderResultBean demo =	bean.getOrderResult("HXO201911221109566309921");
		try {
			// 发送请求，第一个参数为产品ID
			System.out.println("==============="+demo.ReqToJsonString());
			CCBSDK.send("CCBInclsvFnSvc", demo);
			System.out.println(JsonUtils.formatJsonObject(demo.getResp()));
//			IFSPOrderResultBean.Response response = (cn.cf.common.creditpay.jianshe.IFSPOrderResultBean.Response) demo.getResp();
//			// 公共字段：返回码和返回信息
//			String code = response.getTxn_Rsp_Cd_Dsc();
//			String msg = response.getTxn_Rsp_Inf();
//		  	String loanNumber = response.getLoanaccno();
//			System.out.println("返回码=["+code+"]");
//			System.out.println("返回信息=["+msg+"]");
//			System.out.println("返回loanaccno=["+loanNumber+"]");
		} catch (Exception e) {
			
		}
	}
}
