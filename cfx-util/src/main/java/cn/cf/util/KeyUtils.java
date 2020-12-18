/**
 * 
 */
package cn.cf.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author bin
 * 
 */
public class KeyUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	public static String getOrderNumber() {
		Random random = new Random();
		return DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmssSSS")+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
	}
	public static String getchildOrderNumber(String OrderNumber,
			Integer count) {
		 int childNumber=count*10;
		 if(childNumber<100){
			 return OrderNumber+"0"+childNumber;
		 }else{
			 return OrderNumber +childNumber;
		 }
	}
	public static String getFlowNumber() {
		Random random = new Random();
		return DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmssSSS")+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
	}
	
	public static String getFlow(Integer index) {
		Random random = new Random();
		String str = "";
		if(null != index && index>0){
			for (int i = 0; i < index; i++) {
				str+=random.nextInt(10);
			}
		}
		return DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss")+str;
	}
	
	public static String getRandom(int length){
		Random random = new Random();
		String str = "";
		for (int i = 0; i < length; i++) {
			str += random.nextInt(10);
		}
		return str;
	}
	
	/**
	 * 获取随机数
	 * 参数n 大于1小于20位整数
	 * @param n
	 * @return
	 */
	public static String getNRandom(int n) {
		
		if(n > 1 && n < 20){
			long m = 1L;
			for (int i = 0; i < n-1; i++) {
				m*=10;
			}
			long random = (long)((Math.random()*9+1)*m);
				return String.valueOf(random); 
		}
			return "";
	}
}
