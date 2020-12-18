package cn.cf.common.bill;

import java.util.HashMap;
import java.util.Map;

import cn.cf.property.PropertyConfig;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSONObject;
import com.nbcb.pluto.pay.sdk.PayUtil;

public class PostUtils {

	public static String post(String method,JSONObject json){
		String refundRsp = null;
		try {
			Map<String,String> header=new HashMap<String,String>();
			// 添加自定义请求头信息
			header.put("X-GW-APP-ID", PropertyConfig.getProperty("nb_appid"));
			header.put("X-GW-SDK-VERSION", "1.1.0");
			header.put("MESGID", KeyUtils.getUUID());
			//加签名
			String sign = PayUtil.sign(PropertyConfig.getProperty("nb_pfx"), PropertyConfig.getProperty("nb_pwd"), json.toJSONString());
			if(null != sign && !"".equals(sign)){
				JSONObject j = new JSONObject();
				j.put("body", json.toJSONString());
				j.put("signMsg", sign);
				refundRsp = PayUtil.post(PropertyConfig.getProperty("nb_appid"), PropertyConfig.getProperty("nb_pfturl")+method, header, j.toJSONString());
			}
		} catch (Exception e) {
			System.out.println("errorPft:"+e);
		}
		return refundRsp;
	}
	
	
	public static String pay(JSONObject json,String mesgid){
		String refundRsp = null;
		try {
			Map<String,String> header=new HashMap<String,String>();
			// 添加自定义请求头信息
			header.put("X-GW-APP-ID", PropertyConfig.getProperty("nb_appid"));
			header.put("X-GW-SDK-VERSION", "1.1.0");
			header.put("mesgid", mesgid);
			//加签名
			String sign = PayUtil.sign(PropertyConfig.getProperty("nb_pfx"), PropertyConfig.getProperty("nb_pwd"), json.toJSONString());
			if(null != sign && !"".equals(sign)){
				refundRsp = PayUtil.createAutoFormSubmitHtml(PropertyConfig.getProperty("nb_appid"), PropertyConfig.getProperty("nb_payurl"), header, json.toJSONString(), sign);
			}
		} catch (Exception e) {
			System.out.println("errorPft:"+e);
		}
		return refundRsp;
	}
	
	public static String postTx(String method,JSONObject json){
		String refundRsp = null;
		try {
			Map<String,String> header=new HashMap<String,String>();
			// 添加自定义请求头信息
			header.put("X-GW-APP-ID", PropertyConfig.getProperty("nb_appid"));
			header.put("X-GW-SDK-VERSION", "1.1.0");
			header.put("MESGID", KeyUtils.getUUID());
			//加签名
			String sign = PayUtil.sign(PropertyConfig.getProperty("nb_pfx"), PropertyConfig.getProperty("nb_pwd"), json.toJSONString());
			if(null != sign && !"".equals(sign)){
				JSONObject j = new JSONObject();
				j.put("body", json.toJSONString());
				j.put("signMsg", sign);
				refundRsp = PayUtil.post(PropertyConfig.getProperty("nb_appid"), PropertyConfig.getProperty("nb_txurl")+method, header, j.toJSONString());
			}
		} catch (Exception e) {
			System.out.println("errorPft:"+e);
		}
		return refundRsp;
	}
}
