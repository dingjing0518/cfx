package cn.cf.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

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

//	public static void main(String[] args) throws Exception {
////		String src = "{\"token\":\"A325C571C8027E9DEC3CFC6B296A19D0\",\"purchaserPk\":\"8feae138c62a4a439568763aa95cf019\",\"organizationCode\":\"987546727828282992\"}";
//		String src = "{\"appId\":\"3dfsdfssdfsdfsdfsdfsdfdfdfd23423\",\"appSecret\":\"3dfsdfssdfsdfsdfsdfsdfdfdfd23423\",\"source\":\"android\"}";
//		//内容加密编码
//		String encode = des3Encrypt3Base64(src);
//		//加密后解码
//		System.out.println(encode);
//		//String endo="ZWhnbGxvam5hamtrZGJsZ2prb3BjZWFpdGEzaXpTYk9iaXRTeE1FbjN2cUNtSmVWS2lUSUU5Q050TUU5SnV6TGs0ZkRYbXJJdWc2Q2tFZE1TUGRQb3J4cENSZUdEbkYzUkpEdnlHeERWS1EybDVyWjM5aW83a29WS3VqTHRDZmx4TWlrUWtPbFhnczhKbjBraHVPTzlaYzc=";
//		String endo = "ZnBnYmtiYWdkbGhsbGZjaXBpZ29wamJmYlJaYkw0SkU4bDJzbjZ3NlIyd0VTbDI4R3VkRjNUM29rbzlrSDZJeDByTjZlcEVxeXZDc2psbHZtWmcwTE8ycjZlRllHVFYydE8xajkwWXNYSDkyd3d4d2k3TCtzZERQd0tPR2RVVDlONHZTdTNVaG8rSFEvd0k4V3hWTVQ4dmRyZWdZWXFZNXQrMU5OdE50UG9rZWxEem8ybmtTSkxTVC9VWGV5R3plMnZGdFo4SElZYUQ1UXdBSWRhRmdlTzg2bVBCWWk0UHhRMzhvaHQ0dnRMcDBKWXhJMnQzdTBzT25aTXoyNERWUjFpakZLUXhHTURpeVpXL2dHUWYvR2tYK1l6L0hKNkw2cXQrWEZZVWh2THplZndyT1IzTEJRREprcjV0MTl6SDdob2lXcVdwdUZDOGNHSDhOUjJ1eTB2NjZXYVZGR1dKL1dCR1hUYncrTUNJU2hXSndjbzloWi9KUC9GWnhjaXk5QStnZnNUcHRxVDhwQlQrYkV3ejFrZUVxZ3N0UFBVRllib3Y5Umx0c0RyeEJZMExXMlNmZ0l6c1BmakxtcTBDT041MUJXcVVPQldrdnFkSUdtSHhtMXdMQkpJeUprSzRZZlVtdS9IQmVnUHV1ZHd1NnVNWjRrNmRhOUZnMnQxcStJTVlHVDJzbUpxbGxHSHlEMTA2M1lGTVB2blN5SnM0dmd6MHdhaEJJU2tjaVJ6OGoxamh5RWhoYjRRU1dLNVN2YnVyYmdTR21UMys5MGlXR1BicUgrc2NJbFlKYTE2bERXOVAzT01WQ0JQQW56K0loZm9hd2psVXJEWFNhSW1oYVhMYzhIblBRVFlwbDIwL0dhUHJEWDVweno4aEwyK0RYSVRnVEpXN04zWjJUQ1NjSmk4RXBwT2xzbGRIbVFwcGVqY1dRek5FbUtlMXNrZWNzK1NaUWZWWUc3aFg0UGZMT1BiSGorOWRSV0lFZFBNNDNvd2FYdXlRUG53TnlIb3NkWFJKU04xemlSWEZiRitBV3JRK253dlhONEpLcm9DUU5GRTBmNVBLa1NRM3djOUdvRGVUMUZCbzd2anRSUXYycW0wbU1uQlM1K3dKczFuenZwQXY5VVBjR1RCT1pYL1Zxdnl0Rm4ydW1FNVBkTFVseXVNeVpWU09qYzRWWkFIRVdBK3k3YitHMHZqQjhaTTZRNXBOMk81NzlDbjJKV1Nqc3F0WE03MFQ1a1hlWTBGUnFWY0lEeHgxaVd6b25XWXZXS1Vnejd5M0dUNkF5WWJVYkpOTWNYS1p0cjdTc1F5aUFGQmRLbmNEY01sdzhybnBZOHN3S1dOaGd5NU9RSjY5M3czb0kzVFpaZkh5b0UzZTJUcXJGVzlEc1Z6czI4R1RSMC9GeWhScnJjZzNOZGo2aFpZMFhQRmxSS09lcE1DVzNIWWtFd2loTWFYVGMrRU1EMXlRWkRrMEdVZHZGTVpJaVJ3SDdQY2hyeE9EOHJxVVMzUFUyM2l1MEpBWG9WMjd2TGg5bzJ0RlRvU3J5ZCtGRTRvTDVFT3BRNjBGeXNveDNaN0VJTHY3TWFsV1p5RnlNckg5NmdBN1dya0ZFM0J5WTl3NUpVbHdtQi9JZ0VVQUFkUjEySFdaTlNROFB1VE9HaWVESFl1OHBjem4zaWFSbmpPVXREWjFlUVVZTHA0b25kRmhlVitjaENweTltS2ZidFBwRkhrNVdNMllHakIvWFNrZ0REY0JidElHSWljeEdZOWJOY2ttSmFmNXJmcXRRUEZDNTdOM2U0U1EwbStrMktIeVZhM1lJWmExVkJpNVhIVGRLektXSnJqK1pOK1p4UFYvSEdrZnNMQURUNHZDd1pmeWJVYXVOSi9vbjBPTXExRmt4aERZTGUvaDZTRWFHWXdUZmg0ZEs1UVkzVXVHU3Y2anJxUTFLaG1ySStvRVhPYlpDUWZGNjllOU8vQ3U3cVYwbElaMFZQQnpXVXEwWkN1ZURkbXlsb2ZZenpFOFlIdTZ5ZmVYdngzVE5Fd0krcGIvRlByclFVNUJ1eWF4TUxPTmZPUWczWWNMVExrV2NIT2xEck5hR2NWQmJVM2hXSXFVMEN3cVlycWJhSzhBY2dDZGFCUVRRS3lNMEprQWRyaWJuY2I2SlcySStzd2ZXVE9CWDZsbExrNG9jZGZNL29HVXFwZjh6MG00Y3VuWEZzcEt4TEZBc3VBL2Qrbk1LQ3B5MDlMU0dITENaSzhKV2xTWE5VUlZFem9pZXJ3aGlYQUpaYkM4MDF0U2pnUExEZlBUaCtuOWc5MTFyMzd5eVV0K28yWS9STWxBbjFlOXRUWmxnTW9HR0NXUkYwOGpDck1vYVVsc0lHWTcrUm9Qakg5SXNqL0tDWXJ1SVJnZ0ptUTBpVlo1NGVhUGVYbVpXTy82bUhnVEdHY202U2JaN1VlRGZaRE1BSlNyNFRIUEZSVHlSdWdwOHNMRE5uQkdjN2ZtWWpvSnNIZndqejVGLzlSaUFlMDQ4U2VZTHNPMll3dzNmRmdhU2FYUUlvOHhqSGRjcVVVOTJwaVhKckdIaHp4ay91QWFrblVjTDhVUndWYTNraWZjb1M2VXNlVU5QM21LK3VBNHlVZ042T2Jra1lBZnJOblFTSlJ1M09ySFdUZjJ4U0Q5Rzc5MDI0QklPWUZ4YWdEUGQxeERwelNFL0hhc2w0RTE2eFA1VU5xR2g5N0Z6Q3FqWVc4NnlhaXp5MXo1S3l4cFJ6SXRGb0wzbTBINXdNV0ZRdVEwZk5vVnFzR0dnNUR5U2UxLzBybURGcW5BYko5V0MrTFgxbmxpSGpWRVNheGp1S0xiNXRaM2lkclhDRWwycVZUUVpraFY5NDl1Yzgwb2haUDBHTDlwOVIxb2Z4aEx0V2hXTlh3RU1PL1FZUWR4OGVod0dyYmJMcS9VSk9sQnlCVTBNR0xwRk9FRUlHamlHM0FieWN5ZFFETzB3Z0wvRTJGclJOZ2RYd1hBMHJWMzliSkREMGVnUmFhMkFOQVVDSDEveStOVHhCaEZnd0xtRE5KeDZNLzZvWVJUVnZFNkp5akZvQWp1czc1TkpyclZ1NEJGWnk0d0lzRHhmclRUdHIxQ1J3T2hYa1dEYmpUTVBTYVBxdHJRNjhjamNrZHBidzRFR2xpR0FhV2Z6cER0YkFrdzdWZnRYcEJ2bm96QUIwQ1I0OXZZdit3M1pLcjBQWHdkUlFuRGhBTWE5NGVUcDBjZzZQVVlZOGNBOXNmc00zZnJjUnp0dE82WHRzcHd1UThLNU1oZWtyMVFxT1hRbjJJWWZuZm1HMEFHOEJVbGhJaFF4ZlVETUYyVEx3L040ZUpoaGdGeDBlNThDYzl3Y2x1Q3d3NjF3SitScTBVOHkxMmwxc0RqVnZUK2NpQ0o3aUN0WTJFSmZPc2lFY1pkRmw5aWNtbVdEUEpnQ1hURUc3c1QzZE5GcitFRjRZZk5NRTYzNStDZGthdEVKQkJVV3hiU0RZdm14QnVzemxhc0kvZHU3cUs3akcyWEhRU0ZvVm14d1B2MmtlRS9GblprUGZiOE5URkdOSmFDaTk2TkJjekpITzUrQW53R3RuZVpXS0dGNWFEcWg4T1NVM3JYdkg5RWttd1doc3dHTjRqTVN4RHhva0RiOWIzNmRkVXN6WWRUZ0s3VnFveU5WaDc0K20rTDVwaVltUGV4Zk14cy9FK1ZXTUVMbDZKc0xObTFFdzErNjNGK1k3ZjcxWmo2cWttNmFVeVQvWFgrOUJxQS9kRlFieXNHWFJ4YmY5L3pYTncyWHJabmZxcmk1YjBOTmo1Q3pUWnV0Y01Fb1hvZGZwR2pWbXdIc3ZqNDFjZDBHcXFqRmZnWnhwRlhyb0tvOGpxYzJGQXozZGR6YjFkSUs1RVBkemZQbzhsZGVONmhZOGdYRDRLRjNTMG4yNHp5K1BCcmsyMTlEcDJNS21iaGFGb0kvU0tpb3JxTm0vSHhUVm5jYjJoaGZxN0J1bzErVUNQMkZVV2JVK0pGb0hhZ21idFJ3OFRUeVc2ZkhuMFRNaXNIZjZmb3poMmdPUTV1KzdBTWoydDZCcUtwZ3ZjN2FCcjBaSndPVmhRZEVXTVcxYVdWeWlJL0xJTGVEc3g2TDVFWlg4enk5OVNJbVNTZ2FnTEcxdHhDb0dqZmlPWEJodW1MNVZwZy82VWhzcGlyMmpoY3ZmbFJUTTJUQ0JwUmU1T3dPcTBNMGN2RXB3RndJVmNuWVRETGlJZjlyUlV0UTFTNXVudGIzL1praG41cm16d1VlK3I1b0xHOGwweWVlOWYwZ01lbEpUZytxRVlITXpobk5tcmwrdHFzdHRVMk1jOW9IWW9oL0hxQjBZMStNWHdMU3o5TWdGdHB2OU1PeDZwRHluU3pVY3JweHo5RjRQVXYwVWl2WEtFb3ltdXVWTXpuQzR2VDREWHJmZnZFemtRWlIvblhCajQyMmJ5MGlNUmRzWnpNVDdIYUp3Qm1jUExFcDVxaDFNYTR1ZGYyUldRckJzemt4SFJSVnkrejRZQ0VHVjdDNmNYVGFNMWxCc1UzYVpic3lORmRibkVZYkpFMmZMVXRBMkltbmVGUUx1d2lCdytGbVhtcTI1UWNjMkZteVRrK1pXZk9EMTFQT2Y2SUNmNEVTZXFyQ3NQbHYrK2hMZnd6YnBSS0xVY1hSZCtYUDVwNGhIdk9FT3RYSGFxL09XUDFndUw3QmM5SjdHYlR1OHJaNk14Z01SSHpEbXV5VmRoLzVHS0FzT2JlanFYdUdKZzZZMk5wMHZETHg3TFdtRHppb1JkcC9ObGlOMmtmcklzZ3ZkajlCOHNSZG94RFg2T2NRMWRUTFhaZkFFY1ZzM2lwbVBXSWh1VHVHNm5kdmZ0V1lLbHpCZ2tjWXRwMFh2dnNNQUZUa0x0eFEvUVd1V3NFZk5WMmtudzhIcE5qSzdIYmhOQlk1cExWczhuQjVrTzB0VzFvNFpGKzBGS2Nia1ZDb1BtODd0WTkzSjhlT0JiOXloWElwRjFHMERLV05Dd0I5cEc3Z3ZnMktGNVZrN1JmYUhYR0xYTVpuYk9LQzE4VEc4Rm5sMGkrcnBvN2oyTU54c0tvME9wbG9FcGpnWGFUeDhleXg3MzE2dlJOZ2xBVmNwR3JqdGJxa1U4ckpSeVFSWWxKenE2cDBWSG5KM05NcEJjMVJpVjBHNTg1ckEzcTl2dEFkK0l3T29EZ3RBUHlWcEZhd1RUNlprS3FQV2taWm1hZ2g4ZFB4a3ZrdTlZc1JHOUZvcmZuNlkvQmNjcGJqWU9BOStVdnVncWxOd1hobXg3T3hrOTdzOVAwY1c2TUVIZDFJMTd4R0NjZzV6NlhrMlNkSE5td0FmKzNmT3p5dnorK3QwTDcvZEtoTk8vWjVGSGZzRW5UQVBZSDl3S2lRZXNjSFZReWhDWnpGengwK2xyTEJ3L2Y1Vng2dWVpc2pxNjByNmZiTmg1R1dhUmJxVGlXQm1OUUNiblowaWRLN3Y1WUZyUXgxdmtBV3o5V1VOY1pzUTBvcktCUHduU0JtWmdtMk9NWC9Rbll1dmxaazk1RTlvWXpYbjk0d1Q0RDAvaUVZL2daanVWb2tyak9UUGJXdXVZWVRpVTJvSWxsMzV6OXEvbWRRaERBRWM2bjlTTlhTVSthQ2YyNTJISHh1djBiQlVNVVZqV0d0OWJGRktQcFMxR0kxckJneG5tUE5KV1hyVURqeGJudExhOG5zU2RvbytZSEx0S2dBWGd3TkNqSlJpdGRJaGFiQU5leGFxd1YxcktnUkNybVBKdXdqS0EvVjJJNjg2emN1akF5Mml1SjNzK2RKOGo3K1FTdWczdndQZngxYw==";
//		String[] result = des3Decrypt3Base64(endo);
//		System.out.println(result[1]);
//		
//		System.out.println(createKey());
//	}
}
