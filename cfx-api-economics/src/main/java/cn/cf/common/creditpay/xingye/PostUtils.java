package cn.cf.common.creditpay.xingye;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import cn.cf.http.HttpsRequest;
import cn.cf.property.PropertyConfig;

public class PostUtils {
	
	static CloseableHttpClient httpClient;
	static CloseableHttpResponse httpResponse;
	
	public static Map<String,String> getPostParams(LinkedHashMap<String,String> map){
		if(null == map){
			map = new LinkedHashMap<String,String>();
		}
		String str = "";
		 for (String key: map.keySet()) {
			 str += key+map.get(key);
		}
		 str +=PropertyConfig.getProperty("xy_secret_key");
		 //做签名
		 map.put("mac", DesUtils.md5(str));
		return map;
	}
	
	
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
 
	}
 
	/**
	 * 发送https请求
	 * 
	 * @param params
	 * @throws Exception
	 */
	public static String post(Map<String, String> params) {
		try {
			HttpPost httpPost = new HttpPost(PropertyConfig.getProperty("xy_search_url"));
			RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(60*1000).build();
			httpPost.setConfig(requestConfig);
			List<NameValuePair> listNVP = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String key : params.keySet()) {
					listNVP.add(new BasicNameValuePair(key, params.get(key).toString()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(listNVP, "UTF-8");
			httpPost.setEntity(entity);
			httpClient = HttpsRequest.createSSLClientDefault();
			httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				String jsObject = EntityUtils.toString(httpEntity, "UTF-8");
				return jsObject;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
