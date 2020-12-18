package cn.cf.common.creditpay.xingye;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * DES加密 解密算法
 * 
 */
public class DesUtils {

	private final static String DES = "DES";
	private final static String ENCODE = "utf-8";
	private final static char[] hexChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	public static String encryptByDES(String key,String content){
		if(null == key || null == content){
			return null;
		}
		byte[] tmpBytes = key.getBytes();
		byte[] keyBytes = new byte[8];
		for (int i = 0; i < tmpBytes.length && i <keyBytes.length; i++) {
			keyBytes[i] = tmpBytes[i];
		}
		try {
			Key k = new SecretKeySpec(keyBytes, DES);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] output = cipher.doFinal(content.getBytes());
			return bytesToHexStr(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String bytesToHexStr(byte[] bytes){
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
			sb.append(hexChar[(bytes[i] & 0x0f)]);
		}
		return sb.toString();
	}
	
	
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes(ENCODE));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}