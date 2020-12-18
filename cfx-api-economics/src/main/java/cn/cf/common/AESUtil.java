package cn.cf.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * AES加解密工具类
 * @author zhangjp
 * created on 2019年7月2日 下午4:44:54
 */
public class AESUtil {

	/**
	 * 加密
	 * @author zhangjp
	 * created on 2019年7月2日 下午5:01:16
	 * @param content	加密数据
	 * @param password	加密秘钥
	 * @return			加密数据密文
	 * @throws Exception
	 */
	public static String encrypt(String content, String password) throws Exception {
		String result = null;
		if (StringUtils.isBlank(password)) {
			return result;
		}
		byte[] raw = password.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
		result = new Base64().encodeToString(encrypted);
		return result;

	}
	
	/**
	 * 解密
	 * @author zhangjp
	 * created on 2019年7月2日 下午5:01:52
	 * @param content	待解密密文
	 * @param password	解密秘钥
	 * @return			解密后明文
	 * @throws Exception
	 */
	public static String decrypt(String content, String password) throws Exception {
		String result = null;
		if (StringUtils.isBlank(password)) {
			return result;
		}
		byte[] raw = password.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted1 = new Base64().decode(content);// 先用base64解密
		byte[] original = cipher.doFinal(encrypted1);
		result = new String(original, "utf-8");
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String key = "6d644cd33e58dfc5";
		JSONObject jobj = new JSONObject();
		jobj.put("endDate", "2019-07-31");
		jobj.put("taxNumber", "91110108351315258N");
		jobj.put("syncType", "1");
		jobj.put("startDate", "2019-03-01");
		jobj.put("page", 1);
		/*jobj.put("delegateCert", "KmQMrHEieih6U2hnLU2UX8jpHoYNK1kRrGVXAL6");
		jobj.put("fileType", "1");
		jobj.put("taxAuthorityCode", "11101080000");
		jobj.put("taxAuthorityName", "国家税务总局北京市海淀区税务局");
		List<ApplyForTaxList> listTax =new ArrayList<>();
		ApplyForTaxList apply=new ApplyForTaxList();
		apply.setAreaName("北京");
		apply.setTaxName("北京数联四方信息技术有限公司");
		apply.setTaxNo("91110108351315258N");
		listTax.add(apply);
		jobj.put("certificate", listTax);*/
		/*InputStream inputStream = null;
		byte[] data1 = null;
		try {
			inputStream = new FileInputStream("D://e.jpg");
			data1 = new byte[inputStream.available()];
			inputStream.read(data1);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		//String s = new String(data);
		/*jobj.put("content", Base64.encodeBase64String(data1));
		jobj.put("fileName", "zoomwant1");*/
		/*jobj.put("startDate", "2019-04-01");
		jobj.put("endDate", "2019-06-30");
		jobj.put("token", "3~0~0~~1~0~0~0~0~0~1c52fa73-d6d4-4d2d-8a3c-c1b561899286");*/
		/*jobj.put("invoiceCode", "033001800204");
		jobj.put("invoiceNumber", "11225448");
		jobj.put("billingDate", "2019-07-05");
		jobj.put("totalAmount", "40.78");
		jobj.put("checkCode", "154070");*/
		//String desjson="{\"InvoiceCode\":\"88\",\"InvoiceNumber\":\"78\",\"TotalAmount\":\"99\"}";
		//System.err.println("json>>" + JSON.toJSONString(desjson));
		String json = jobj.toJSONString();
		String encryptedData = encrypt(json, key);
		System.err.println("encryptedData>>" + encryptedData);
		String decryptedData = decrypt(encryptedData, key);
		System.err.println("decryptedData>>" + decryptedData);
		//String data = "pWam8nUP8K9MzhYV9axdqiKyIlv/94VbmlY7ceqA/0EFJwiMmwuju1TyvIy1Oe5j7h7WmdwoBnpMPgY88SDKFc/T0pidaF3kYUqT/2MqElAPM2sOIfU4d5sDcZSBZCVrjj5e9yu5/5xL3grWgU1jjuzzMHHDwWESyjqzS1CK6AHsTINxZPOPnwveIufArepMfKoqmJCt8TxjEyvGKgKw3G0TRFE2jeVrbi0wVVurVB/5cnm52l7HUmN1BTcwPun7+gUAYL8LLzzv+QeHlabcBw6j79GXuupxkFBgea8CqJ7eKpQzDS0APQVM7Z+F1IixZmh5Afvp99/Euh8xnw3rgoP8GyZZpZGAyznebMl8I1t3Tv0x6dH/dcGxpyXL2VfDh0kCQ5R0USx9/wDItJeefQcVurHvkb9JBwBL5tVbGKLJRCu2TEEDNh+VmBFQEv3uZ23A7m04jEdt/FJpf2avGTRhP2JxQX0zTp/XX/yA+JHx8x865rstHkjgCyYbwtbN5r4kfc12BFV427o7OQClKfn+cAjhU4KM4RvOM/KMWAZqcgkRVftgmJQ5aVB7mf+JjXaK9zuailm0vlCsB2kZ5cDHiQ8p5lxD5oqwTbic2keFCcAScptW30zOnPj97/mcaipAIRShOGE+Tnqe1Eai7fCZiG7sDxYflGc94PLC2iQzBoLBekSC3z7RWYNlQ+Q5oEY85QeCVuqLnocgTzk/buZzj6sDkk3GzyUgBYsz/YU/Pt9voh5us7N0zQB/bOA/drdgzdvi1xd63fycU4vZvk3OzgZiGI9WptIkix5ysmEvU08Ql5gNLMte4CohpkApQ0ipXo6HGVlkBoJH+/J6aPgZEMs1tiC1ZKxUCcetzHKF86pgd9TOqImY4u00jU/9BSqhRxrqQoQPJnWleArPZZXdaYu890FHLfhKpTSYJkRFqN6YOorr1TPXv8Lns/xcB8QOfILHsPdTJHn6DIFcvQi9Dqbdyy+h3fwENjPYT7ejEXUepT4LromsAtsQ7NNT6II0Nim6R+OSxvGdYUVCAV228yfI9y/jfYHTHtcPq71MwI4ty1zWiyro4Lr91EvO7DetRyKyT/VBhQpa+PJ0XMZkfyZOj7HMJNZvZiABZmKHsX61lplIMCTxrAs2bhCWIhfRjw62LtP7I9Jm8w9QpoIn7VQ3i1uH0JRhV1Z+8r52DgDCEBDC8q0Dtg5LPuMF2Xn5BNX1jbX8Z+aD6KB4uZaE3fP6gHPXE3Q8/nHpHX83Z9HGCqarLvJrUvLipF3JF42z98v6ycs6hvAcYNsR7vXyRgErb5blf9WYtStsLhvfrk7xop6ddmrbqO28WKZc/xZYPlSzSefo4kmOY7HlYxZm03ZnWfTnKzg7XjFk5/r9MXWKWYpGPHHlXR9yF4yDeGp2L3pWMGAlQTApQO8qAeQLweBlvbyf/LPCoX8pIx4+d/y4Boe2Bw0wcMtPiswcRGROG05nn188CurhfatLqqkemwAXx4W1GUDfcA7pl4pJEPVILZcg2W1k/57B9BXi";
		String data="u8qO/ne5J01epCeEk9Q7LNw/VOniyuVzprjdwBTPNyWuXSAAtUInvJbXz0veQwx4sSFigWAIiA0Z/b6AcuM1AEPKzApkRQqWx/J6W9DZhbAKDOsRsvj4z3TSCHjxr4/X";
		String decrypt = decrypt(data, key);
		System.err.println(decrypt);

	/*	Date date = new Date();//获取当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
		//calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间
		calendar.getTime();//获取一年前的时间，或者一个月前的时间
		System.err.println(calendar.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取String类型的时间
		String createdate = sdf.format(calendar.getTime());
		String creatdate = sdf.format(date);
		System.err.println(createdate);
		System.err.println(creatdate);*/


	}
}
