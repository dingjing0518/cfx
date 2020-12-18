package cn.cf.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncodeUtil {
	private final static String DEFAULT_CHARSET = "UTF-8";
	private final static Logger LOGGER = Logger.getLogger(cn.cf.util.EncodeUtil.class);
	private final static char hexChars[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private final static String[] hexDigits = { "g", "h", "i", "j", "k", "l",
			"m", "n", "p", "o", "a", "b", "c", "d", "e", "f" };

	/**
	 * 对指定字符串进行urlEncode编码为默认编码utf8
	 * 
	 * @param rs
	 * @return
	 */
	public static String URLEncode(String rs) {
		return URLEncode(rs, DEFAULT_CHARSET);
	}

	/**
	 * 对指定字符串进行urlEncode编码为默认编码utf8
	 * 
	 * @param rs
	 * @return
	 */
	public static String URLEncode(String rs, String charset) {
		String result = "";
		if (rs != null && !rs.equals("")) {
			try {
				result = URLEncoder.encode(rs, charset);
				result = result.replaceAll("\\+", "%20");
			} catch (Exception e) {
				LOGGER.warn("url encode error", e);
			}
		}
		return result;
	}

	/**
	 * 对指定字符串进行urlDecode编码为默认编码utf8
	 * 
	 * @param rs
	 * @return
	 */
	public static String URLDecode(String rs) {
		return URLDecode(rs, DEFAULT_CHARSET);
	}

	/**
	 * 对指定字符串进行urlDecode编码为默认编码utf8
	 * 
	 * @param rs
	 * @return
	 */
	public static String URLDecode(String rs, String charset) {
		String result = "";
		if (rs != null && !rs.equals("")) {
			try {
				result = result.replaceAll("%20", "+");
				result = URLDecoder.decode(rs, charset);
			} catch (Exception e) {
				LOGGER.warn("url decode error", e);
			}
		}
		return result;
	}

	/**
	 * 对指定字符串进行MD5Encode
	 * 
	 * @param msg
	 * @return
	 */
	public static String MD5Encode(String msg) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(msg.getBytes());
			return byteToHexString(b);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.warn("md5 encode error", e);
			return null;
		}

	}

	/** */
	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexChars[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexChars[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	/**
	 * 生成3des key
	 * 
	 * @return
	 */
	public static String createKey() {
		String key = "";
		for (int i = 0; i < 24; i++) {
			int j = ((Double) (Math.random() * 16)).intValue();
			key = key + hexDigits[j];
		}
		return key;
	}

	/**
	 * 生成des key
	 * 
	 * @return
	 */
	public static String createDesKey() {
		String key = "";
		for (int i = 0; i < 8; i++) {
			int j = ((Double) (Math.random() * 16)).intValue();
			key = key + hexDigits[j];
		}
		return key;
	}

	public static byte[] desEncrypt(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(DEFAULT_CHARSET));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes(DEFAULT_CHARSET));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes(DEFAULT_CHARSET));
	}

	public static String desEncrypt2Base64(String message, String key)
			throws Exception {
		return base64Encode(desEncrypt(message, key));
	}

	public static byte[] desDecrypt(byte[] message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(DEFAULT_CHARSET));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes(DEFAULT_CHARSET));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message);
	}

	public static String desDecrypt4Base64(String message, String key)
			throws Exception {
		byte[] temp = base64DecodeToBytes(message);
		if (temp != null) {
			return new String(desDecrypt(temp, key), DEFAULT_CHARSET);
		} else {
			return null;
		}
	}

	public static byte[] des3Encrypt(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

		DESedeKeySpec desKeySpec = new DESedeKeySpec(
				key.getBytes(DEFAULT_CHARSET));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.substring(0, 8).getBytes(
				DEFAULT_CHARSET));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes(DEFAULT_CHARSET));
	}

	public static String des3Encrypt2Base64(String message, String key)
			throws Exception {
		return base64Encode(des3Encrypt(message, key));
	}

	public static String des3Encrypt3Base64(String message)
			throws Exception {
		//创建key
		String key = createKey();
		String data = base64Encode(des3Encrypt(message, key)).toString();
		return base64Encode(key + data);
	}

	public static String des3Encrypt2URLEncode(String message, String key)
			throws Exception {
		return URLEncode(des3Encrypt(message, key).toString());
	}

	public static byte[] des3Decrypt(byte[] message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

		DESedeKeySpec desKeySpec = new DESedeKeySpec(
				key.getBytes(DEFAULT_CHARSET));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.substring(0, 8).getBytes(
				DEFAULT_CHARSET));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message);
	}

	public static String[] des3Decrypt3Base64(String message) throws Exception {
		String[] result = new String[2];
		String temp = base64Decode(message);
		if (temp != null) {
			result[0] = temp.substring(0, 24);
			String data = temp.substring(24, temp.length());
			byte[] temp1 = base64DecodeToBytes(data);
			result[1] = new String(des3Decrypt(temp1, result[0]),
					DEFAULT_CHARSET);
			return result;
		} else {
			return null;
		}
	}

	public static String des3Decrypt4Base64(String message, String key)
			throws Exception {
		byte[] temp = base64DecodeToBytes(message);
		if (temp != null) {
			return new String(des3Decrypt(temp, key), DEFAULT_CHARSET);
		} else {
			return null;
		}
	}

	public static String des3Decrypt4URLDecode(String message, String key)
			throws Exception {
		byte[] temp = URLEncode(message).getBytes();
		if (temp != null) {
			return new String(des3Decrypt(temp, key), DEFAULT_CHARSET);
		} else {
			return null;
		}
	}

	/**
	 * BASE64 编码.
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static String base64Encode(String src) {
		Base64 base64 = new Base64();
		return new String(base64.encode(src.getBytes()));
	}

	/**
	 * BASE64 编码(byte[]).
	 * 
	 * @param src
	 *            byte[] inputed string
	 * @return String returned string
	 */
	public static String base64Encode(byte[] src) {
		Base64 base64 = new Base64();
		return new String(base64.encode(src));
	}

	/**
	 * BASE64 解码.
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static String base64Decode(String src) {
		Base64 base64 = new Base64();
		return new String(base64.decode(src.getBytes()));
	}

	/**
	 * BASE64 解码(to byte[]).
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static byte[] base64DecodeToBytes(String src) {
		Base64 base64 = new Base64();
		return base64.decode(src.getBytes());

	}

	public static String VerificationCode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;

	}

	public static void main(String[] args) throws Exception {
//		String src = "{\"token\":\"A325C571C8027E9DEC3CFC6B296A19D0\",\"purchaserPk\":\"8feae138c62a4a439568763aa95cf019\",\"organizationCode\":\"987546727828282992\"}";
//		String src = "{\"appId\":\"23hjjdksla989dixjkds09sd43584dsa\",\"appSecret\":\"0bkf1545erf5dc8io4lk4m1jhg4865vf\"}";
//		//内容加密编码
//		String encode = des3Encrypt3Base64(src);
//		//加密后解码
//
		String endo="ZGhkaGtva29rZ2xjZ2hkZGdvbmJrY2ZpSHBLS2RMOW0rSzRHM2lQa0cvNFhzNFRQK0NjOERKV1p2dnk4ZnRMOVd4SCtPTThjemgwbEVBPT0=";
		String[] result = des3Decrypt3Base64(endo);
		System.out.println(result[1]);
	}
}
