package cn.cf.common.creditpay.gongshang;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class BaseCodeUtils {
	
	
	public static String base64Encode(String data, String charset) {
		BASE64Encoder b64enc = new BASE64Encoder();
		try {
			return b64enc.encode(data.getBytes(charset)).replaceAll("\r\n", "").replaceAll("\n", "");
		} catch (Exception e) {
			return null;
		}
	}

	public static String base64Decode(String data, String charset) {
		BASE64Decoder b64dec = new BASE64Decoder();
		try {
			return new String(b64dec.decodeBuffer(data), charset);
		} catch (Exception e) {
			return null;
		}
	}
}
