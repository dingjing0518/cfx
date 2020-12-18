package cn.cf.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
	
	/**
	 * 文件转化成base64字符串
	 * @param urlStr  文件HttpURL
	 * @return
	 */
	public static String fileToBase64(String urlStr) {
		byte[] data = null;
		String encode = null;
		InputStream inputStream = null;
		ByteArrayOutputStream bos =null;
		// 对字节数组Base64编码
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
			}
			bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = inputStream.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			inputStream.close();
			data = bos.toByteArray();
			encode = Base64.encodeBase64String(data);
		} catch (IOException e) {
			System.out.println("获取文件出现异常，路径为：" + urlStr);
			e.printStackTrace();
		} finally {
				try {
					inputStream.close();
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return encode;
	}
	

	/**
	 * base64字符串转化为文件
	 * 
	 * @param encode base64字符串
	 * @param filePath 保存的文件路径（包含文件的后缀名，如：E:\\usr\\test.jpg）
	 */
	public static void base64ToFile(String encode, final String filePath) {
		byte[] bytes = Base64.decodeBase64(encode);
		File file = new File(filePath);
		OutputStream output = null;
		BufferedOutputStream bufferedOutput = null;
		try {
			output = new FileOutputStream(file);
			bufferedOutput = new BufferedOutputStream(output);
			bufferedOutput.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedOutput) {
				try {
					bufferedOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != output) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
//		String reString = fileToBase64("http://huaxianhui.oss-cn-shanghai.aliyuncs.com/img/jmmddqkurynktmoumgcivyws1560214092.jpg");
//		System.out.println(reString);
//		base64ToFile(reString, "E:\\usr\\test01.jpg");
//		System.out.println("true");
		
		String rest = "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";
		base64ToFile(rest, "d://pdf文件//123.jpg");
	}

}