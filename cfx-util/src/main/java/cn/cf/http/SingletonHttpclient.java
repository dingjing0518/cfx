package cn.cf.http;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * 保证HttpClient的单例使用
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("deprecation")
public class SingletonHttpclient {
	private static final String CHARSET = "UTF-8";
	private static HttpClient singleHttpClient;
	/** 连接超时时长 */
	private static final int CONNECTION_TIMEOUT = 20000;
	/** 请求超时时长 */
	private static final int METHOD__TIMEOUT = 40000;
	/** http协议端口号 */
	private static final int HTTP_PORT = 80;
	/** https协议端口号 */
	private static final int HTTPS_PORT = 443;

	private SingletonHttpclient() {
	}

	public static synchronized HttpClient getHttpClient() {
		if (null == singleHttpClient) {
			// 在最新版本的httpclient中ThreadSafeClientConnManager已被@Deprecated掉，这里使用PoolingClientConnectionManager。
			ClientConnectionManager conMgr = new PoolingClientConnectionManager(
					getSchemeRegistry());
			singleHttpClient = new DefaultHttpClient(conMgr,
					prepareHttpParams());
		}
		return singleHttpClient;
	}

	/**
	 * 设置Http协议信息
	 * 
	 * @return Http协议对象
	 * @throws Exception
	 */
	private static SchemeRegistry getSchemeRegistry() {
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("HTTP", HTTP_PORT, PlainSocketFactory
				.getSocketFactory()));
		registry.register(new Scheme("HTTPS", HTTPS_PORT, SSLSocketFactory
				.getSocketFactory()));
		return registry;
	}

	/**
	 * 设置Http请求参数
	 * 
	 * @return Http请求参数
	 */
	private static HttpParams prepareHttpParams() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpProtocolParams
				.setUserAgent(params,
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:19.0) Gecko/20100101 Firefox/19.0");
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, METHOD__TIMEOUT);
		params.setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.BROWSER_COMPATIBILITY);
		return params;
	}
}