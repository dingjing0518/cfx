package cn.cf.common.creditpay.sunong;

import java.util.Map;

import cn.cf.property.PropertyConfig;

import com.szrcb.openbank.bean.main.OpenBank;


public class PostUtils {

	@SuppressWarnings("unchecked")
	public static Map<String,Object> post(String url, Map<String,Object> params) throws Exception {
		OpenBank openBank = new OpenBank();
		//https接入,需要双向认证  开始
		openBank.setKeyStorePath(PropertyConfig.getProperty("sn_key"));
		openBank.setKeyStorePassword(PropertyConfig.getProperty("sn_password"));
		openBank.setTrustStorePath(PropertyConfig.getProperty("sn_trust"));
		openBank.setTrustStorePassword(PropertyConfig.getProperty("sn_password"));
		//https接入,需要双向认证   结束
		return openBank.requestService(url, params, "xml");

	}
}
