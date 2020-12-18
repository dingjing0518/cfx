package cn.cf.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @description
 *
 
 */
public class HttpRequest {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection
					.setRequestProperty(
							"user-agent",
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");

			// 建立实际的连接
			connection.connect();
			/*
			 * // 获取所有响应头字段 Map<String, List<String>> map =
			 * connection.getHeaderFields(); // 遍历所有的响应头字段 for (String key :
			 * map.keySet()) { System.out.println(key + "--->" + map.get(key));
			 * }
			 */
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);

			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("第三方运费查询接口POST报错：" + e.getMessage());
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * post请求 发送xml格式
	 * @param postUrl
	 * @param methond
	 * @param xmlString
	 * @return
	 */
	public static String sendPostByXml(String postUrl,String methond,String xmlString){
		byte[] xmlData = xmlString.getBytes();   
		DataInputStream input = null;   
		java.io.ByteArrayOutputStream out = null; 
		String urlStr =  postUrl + methond;   
		
		try{   
			
			//获得到位置服务的链接   
			
			URL url = new URL(urlStr);   
			
			URLConnection urlCon = url.openConnection();   
			
			urlCon.setDoOutput(true);   
			
			urlCon.setDoInput(true);   
			
			urlCon.setUseCaches(false);   
			
			//将xml数据发送到位置服务   
			
			urlCon.setRequestProperty("Content-Type", "text/xml");   
			
			urlCon.setRequestProperty("Content-length",String.valueOf(xmlData.length));   
			
			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());   
			
			printout.write(xmlData);   
			
			printout.flush();   
			
			printout.close();   
			
			input = new DataInputStream(urlCon.getInputStream());   
			
			byte[] rResult;   
			
			out = new java.io.ByteArrayOutputStream();   
			
			byte[] bufferByte = new byte[256];   
			
			int l = -1;   
			
			int downloadSize = 0;   
			
			while ((l = input.read(bufferByte)) > -1) {   
			
			  downloadSize += l;   
			
			  out.write(bufferByte, 0, l);   
			
			  out.flush();   
			
			}   
			rResult = out.toByteArray();   
			
			return new String(rResult);
			
			}catch(Exception e){    
				e.printStackTrace();
				return null;
			}finally {   
			
				try {   
			
			     out.close();   
			
			     input.close();   
			
				}catch (Exception ex) {   
					
				}    
			}   
		}
}
