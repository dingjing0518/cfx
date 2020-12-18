package cn.cf.common.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import cn.cf.property.PropertyConfig;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http请求访问公共类
 * 
 * @since
 */
public class HttpHelper {
	private final static Logger logger = LoggerFactory.getLogger(HttpHelper.class);
	private static final int SUCCESS_RESPONSE_STATUS = 200;
	private static final String CHARSET = "UTF-8";

	/**
	 * 发送Http请求，返回文本形式的请求响应
	 * 
	 * @param url
	 *            url地址
	 * @return
	 * @throws ServiceException
	 */
	public static String doGet(String url) {
		
		return doGet(url, null);
	}

	/**
	 * 发送Http请求，返回文本形式的请求响应
	 * 
	 * @param url
	 *            url地址
	 * @return
	 * @throws ServiceException
	 */
	public static String doGet(String url, Header[] headers) {
		String result = null;
		if (url == null || url.length() <= 0) {
		} else {
			String temp = url.toLowerCase();
			if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
				url = "http://" + url;
			}
			URI uri = URI.create(url);
			url = uri.toASCIIString();
			String host = uri.getHost();
			if (headers == null) {
				headers = prepareHeaders(host);
			}
			headers[7] = new BasicHeader("", "");
			HttpGet httpMethod = null;
			try {
				// 初始化Http请求方法和参数
				httpMethod = new HttpGet(url);
				httpMethod.setHeaders(headers);
				// 进行Http请求
				HttpResponse response = SingletonHttpclient.getHttpClient()
						.execute(httpMethod);
				// logger.error("response------------"
				// + response.getStatusLine().getStatusCode());
				// 判断请求是否成功
				if (!isResponseSuccessStatus(response)) {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println("Http Satus:" + status + ",Url:" + url);
					if (status >= 500 && status < 600)
						throw new IOException("Remote service<" + url
								+ "> respose a error, status:" + status);
					return null;
				}
				result = parseHttpResponse2String(response);
			} catch (Exception e) {
				logger.error(e.getMessage());
				if (httpMethod != null) {
					httpMethod.abort();
				}
			}
		}
		return result;

	}

	public static void doGetFile(String url, Header[] headers, String fileName) {
		if (url == null || url.length() <= 0) {
		} else {
			String temp = url.toLowerCase();
			if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
				url = "http://" + url;
			}
			URI uri = URI.create(url);
			url = uri.toASCIIString();
			String host = uri.getHost();
			if (headers == null) {
				headers = prepareHeaders(host);
			}
			HttpGet httpMethod = null;
			FileOutputStream outputStream = null;
			InputStream inputStream = null;
			try {
				// 初始化Http请求方法和参数
				httpMethod = new HttpGet(url);
				httpMethod.setHeaders(headers);
				// 进行Http请求
				HttpResponse response = SingletonHttpclient.getHttpClient()
						.execute(httpMethod);
				// 判断请求是否成功
				if (!isResponseSuccessStatus(response)) {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println("Http Satus:" + status + ",Url:" + url);
					if (status >= 500 && status < 600)
						throw new IOException("Remote service<" + url
								+ "> respose a error, status:" + status);
					return;
				}
				String filePath = PropertyConfig.getProperty("FILE_PATH",
						"/opt/tomcat/webapps/");
				File file = new File(filePath + fileName);
				if (!file.exists()) {
					file.getParentFile().mkdirs();
				}
				outputStream = new FileOutputStream(file);
				inputStream = response.getEntity().getContent();
				byte b[] = new byte[1024];
				int j = 0;
				while ((j = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, j);
				}
				//System.out.println("存储了文件: " + file.getAbsolutePath());
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if (httpMethod != null) {
					httpMethod.abort();
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}

	public static String doGetFile(String url, int sceneid) {
		String filePath = "";
		if (url == null || url.length() <= 0) {
		} else {
			String temp = url.toLowerCase();
			if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
				url = "http://" + url;
			}
			URI uri = URI.create(url);
			url = uri.toASCIIString();
			HttpGet httpMethod = null;
			FileOutputStream outputStream = null;
			InputStream inputStream = null;
			try {
				httpMethod = new HttpGet(url);
				HttpResponse response = SingletonHttpclient.getHttpClient()
						.execute(httpMethod);
				if (!isResponseSuccessStatus(response)) {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println("Http Satus:" + status + ",Url:" + url);
					if (status >= 500 && status < 600)
						throw new IOException("Remote service<" + url
								+ "> respose a error, status:" + status);
				} else {
					String path = PropertyConfig.getProperty("FILE_PATH",
							"/opt/tomcat/webapps/");
					filePath += "scene/" + sceneid + ".jpg";
					File file = new File(path + filePath);
					if (!file.exists()) {
						file.getParentFile().mkdirs();
					}
					outputStream = new FileOutputStream(file);
					inputStream = response.getEntity().getContent();
					byte b[] = new byte[1024];
					int j = 0;
					while ((j = inputStream.read(b)) != -1) {
						outputStream.write(b, 0, j);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if (httpMethod != null) {
					httpMethod.abort();
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		return filePath;
	}

	/**
	 * 判断Http请求是否成功
	 * 
	 * @param response
	 * @return 成功标识
	 */
	private static boolean isResponseSuccessStatus(HttpResponse response) {
		int status = response.getStatusLine().getStatusCode();
		return SUCCESS_RESPONSE_STATUS == status;
	}

	/**
	 * 解析Http响应结果，读出Stream流将结果转换为文本
	 * 
	 * @param response
	 * @return InputStream
	 * @throws Exception
	 * @throws IOException
	 */
	public static String parseHttpResponse2String(HttpResponse response)
			throws Exception {
		HttpEntity entity = response.getEntity();
		checkEntity(entity);
		String result = EntityUtils.toString(entity, "utf-8");
		EntityUtils.consume(entity);
		return result;
	}

	/**
	 * 检查HttpEntity是否正确
	 * 
	 * @param entity
	 * @throws Exception
	 */
	private static void checkEntity(HttpEntity entity) throws Exception {
		if (null == entity || entity.getContentLength() > Integer.MAX_VALUE) {
			throw new Exception(
					"entity is null or the max length exceed Integer.MAX_VALUE");
		}
	}

	private static Header[] prepareHeaders(String host) {
		Header[] headers = new Header[10];
		headers[0] = new BasicHeader("Host", host);
		headers[1] = new BasicHeader("Connection", "keep-alive");
		headers[2] = new BasicHeader("X-Requested-With", "XMLHttpRequest");
		headers[3] = new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:19.0) Gecko/20100101 Firefox/19.0");
		headers[4] = new BasicHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		headers[5] = new BasicHeader("Accept", "*/*");
		headers[6] = new BasicHeader("Referer", "");
		headers[7] = new BasicHeader("Accept-Encoding", "gzip, deflate");
		headers[8] = new BasicHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		headers[9] = new BasicHeader("Accept-Charset",
				"GBK,utf-8;q=0.7,*;q=0.3");
		return headers;
	}

	public static String doPost(String url, Map<String, String> paraMap) {
		return doPost(url, null, paraMap, CHARSET);
	}
	
	
	public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(reqURL);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
        if(params != null){
        	for(Map.Entry<String,String> entry : params.entrySet()){
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try{
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
             
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        }catch(Exception e){
        	logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", "sendPostRequest", e);
        }finally{
        	httpPost.releaseConnection();
        }
        
        logger.info("sendPostRequest responseContent="+responseContent, "sendPostRequest");
        return responseContent;
    }

	public static String doPost(String url, Header[] headers,
			Map<String, String> paraMap, String charset) {
		String result = null;
		try {
			HttpResponse response = doPostAndGetHttpResponse(url, headers,
					paraMap, charset);
			if (response != null) {
				// 判断请求是否成功
				if (!isResponseSuccessStatus(response)) {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println("Http Satus:" + status + ",Url:" + url);
					if (status >= 500 && status < 600)
						throw new IOException("Remote service<" + url
								+ "> respose a error, status:" + status);

					logger.error("ResponseSuccessStatus", url +" "+ status);
					return null;
				}
				result = parseHttpResponse2String(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static HttpResponse doPostAndGetHttpResponse(String url,
			Header[] headers, Map<String, String> paraMap, String charset) {
		HttpResponse response = null;
		if (url == null || url.length() <= 0) {
		} else {
			String temp = url.toLowerCase();
			if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
				url = "http://" + url;
			}
			URI uri = URI.create(url);
			url = uri.toASCIIString();
			String host = uri.getHost();
			if (headers == null) {
				headers = prepareHeaders(host);
			}
			if (paraMap == null || paraMap.size() == 0) {
			} else {
				List<NameValuePair> paraList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> m : paraMap.entrySet()) {
					paraList.add(new BasicNameValuePair(m.getKey(), m
							.getValue()));
				}
				HttpPost httpMethod = null;
				try {
					httpMethod = new HttpPost(url);
					httpMethod.setHeaders(headers);
					httpMethod.setEntity(new UrlEncodedFormEntity(paraList,
							charset));
					response = SingletonHttpclient.getHttpClient().execute(
							httpMethod);
				} catch (Exception e) {
					if (httpMethod != null) {
						httpMethod.abort();
					}
				}
			}
		}
		return response;
	}

	public static String doJsonPost(String url, String str) throws Exception {
		String rep = null;
		if (url == null || url.length() <= 0) {
		} else {
			HttpResponse response = null;
			String temp = url.toLowerCase();
			if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
				url = "http://" + url;
			}
			URI uri = URI.create(url);
			url = uri.toASCIIString();
			HttpPost httpMethod = null;
			try {
				httpMethod = new HttpPost(url);
				if (null != str && !"".equals(str)) {
					StringEntity entity = new StringEntity(str, CHARSET);
					httpMethod.setEntity(entity);
				}
				httpMethod.addHeader("content-type", "application/json");
				response = SingletonHttpclient.getHttpClient().execute(
						httpMethod);
			} catch (Exception e) {
				if (httpMethod != null) {
					httpMethod.abort();
				}
			} finally {
				if (httpMethod != null) {
					httpMethod.abort();
				}
			}
			rep = parseHttpResponseString(response);
		}
		return rep;

	}

	public static HttpResponse doPostJson(String url, String str) {
		HttpResponse response = null;
		if (url == null || url.length() <= 0) {
		} else {
			String temp = url.toLowerCase();
			if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
				url = "http://" + url;
			}
			URI uri = URI.create(url);
			url = uri.toASCIIString();
			HttpPost httpMethod = null;
			try {
				httpMethod = new HttpPost(url);
				StringEntity entity = new StringEntity(str, CHARSET);
				httpMethod.setEntity(entity);
				httpMethod.addHeader("content-type", "application/json");
				response = SingletonHttpclient.getHttpClient().execute(
						httpMethod);
			} catch (Exception e) {
				if (httpMethod != null) {
					httpMethod.abort();
				}
			}
		}
		return response;
	}

	public static String doGetForWx(String url) {
		String result = null;
		String temp = url.toLowerCase();
		if (!temp.startsWith("http://") && !temp.startsWith("https://")) {
			url = "http://" + url;
		}
		URI uri = URI.create(url);
		url = uri.toASCIIString();
		HttpGet httpMethod = null;
		try {
			httpMethod = new HttpGet(url);
			httpMethod.addHeader("content-type", "text/html;charset=UTF-8");
			HttpResponse response = SingletonHttpclient.getHttpClient()
					.execute(httpMethod);
			// 判断请求是否成功
			if (!isResponseSuccessStatus(response)) {
				int status = response.getStatusLine().getStatusCode();
				//System.out.println("Http Satus:" + status + ",Url:" + url);
				if (status >= 500 && status < 600) {
					if (httpMethod != null) {
						httpMethod.abort();
					}
					throw new IOException("Remote service<" + url
							+ "> respose a error, status:" + status);
				}
				return null;
			}
			result = parseHttpResponseString(response);
			// result = parseHttpResponse2String(response);
		} catch (Exception e) {
			if (httpMethod != null) {
				httpMethod.abort();
			}
			logger.error("doGetForWx", e);
		} finally {
			if (httpMethod != null) {
				httpMethod.abort();
			}
		}
		return result;
	}

	public static String parseHttpResponseString(HttpResponse response)
			throws Exception {
		HttpEntity entity = response.getEntity();
		InputStream in = null;
		StringBuffer result = new StringBuffer();
		// new String(response.getResponseBodyAsString().getBytes("gb2312"));
		try {

			if (entity != null) {
				in = entity.getContent();
			}
			byte[] b = new byte[215];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				result.append(new String(b, 0, i));
			}
		} catch (Exception e) {
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return result.toString();
	}

	public static void main(String[] args) {
		HttpHelper
				.doGetForWx("https://api.weixin.qq.com/sns/oauth2/access_token?"
						+ "grant_type=authorization_code&code=02a32244f1ac899f0dfb67e9e85928db&appid=wx4c4d87bf0b5769e8&secret=5f5193ad888a1aab44ca77026df8527d");
	}
}
