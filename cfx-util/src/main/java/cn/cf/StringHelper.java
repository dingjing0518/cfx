package cn.cf;



import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 继承自 spring 的字符串处理工具。
 * 
 * @date 
 * @author 
 */
public class StringHelper extends StringUtils {

	/**
	 * 绝对比较两个字符串是否一样，可以比较两个 NULL 值。
	 * 
	 * @author 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isSameWithNull(String str1, String str2) {
		return String.valueOf(str1).equals(String.valueOf(str2));
	}

	/**
	 * 比较两个字符串的文本是否完全一样，排除空格。
	 * 
	 * @author 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isTextSame(String str1, String str2) {
		return String.valueOf(str1).trim().equals(String.valueOf(str2).trim());
	}

	/**
	 * 根据固定长度补齐字符串，如果超长则返回本身。
	 * 
	 * @author 
	 * @param str
	 * @param fillChar
	 * @param totalLength
	 * @return
	 */
	public static String fillWithChar(String str, char fillChar, int totalLength) {
		for (int i = (totalLength - str.length()); i > 0; i--) {
			str = str + fillChar;
		}
		return str;
	}

	/**
	 * 移除HTML标签
	 */
	public static String removeHtmlTag(String html) {
		return html.replaceAll("<.*?>", "").replaceAll("&", "&amp;")
				.replaceAll("\"", "&quot;");
	}

	/**
	 * 转义 HTML 标签到页面可显示标签。
	 */
	public static String conveyHtmlToPage(String html) {
		return html.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(
				"&", "&amp;").replaceAll("\"", "&quot;");
	}

	/**
	 * 获取某个最后的字符之后的所有字符。
	 * 
	 * @author 王帅 @2010-8-24
	 * @param str
	 * @param sp
	 * @param withSp
	 *            是否带 sp 本身
	 * @return
	 */
	public static String getLastFromSp(String str, String sp, boolean withSp) {
		if (str.contains(sp)) {
			str = str.substring(str.lastIndexOf(sp)
					+ (withSp ? 0 : sp.length()), str.length());
		}
		return str;
	}

	/**
	 * 获取某个字符之前的所有字符。
	 * 
	 * @author 王帅 @2010-8-24
	 * @modifier zw 2011-02-28获取某个字符之前的字符，substring第二个参数的结束下标值不应该减一 str =
	 *           str.substring(0, str.lastIndexOf(sp) - 1);
	 * @param str
	 * @param sp
	 * @return
	 */
	public static String getFrontBeforeSp(String str, String sp) {
		if (str.contains(sp)) {
			// str = str.substring(0, str.lastIndexOf(sp) - 1);
			str = str.substring(0, str.lastIndexOf(sp));
		}
		return str;
	}

	/**
	 * 将max值乘于1.5并将除第一位的数字补0 如参数max值为2002 则返回值 3000 主要适用于柱状图的修改
	 * 
	 * @param max
	 * @return
	 */
	public static String convertMaxValue(double max) {
		max = max * 1.5;
		String temp = String.valueOf(max);
		if (temp.contains(".")) {
			temp = temp.substring(0, temp.indexOf("."));
		}
		int m = 0;
		m = Integer.parseInt(temp.substring(0, 1));
		if (temp.length() >= 2) {
			int z = Integer.parseInt(temp.substring(1, 2));
			if (z >= 5) {
				m = m + 1;
			}
		}
		for (int i = 1; i < temp.length(); i++) {
			m = m * 10;
		}
		return String.valueOf(m);
	}

	/**
	 * 如果orderName或者orderType为空则不做操作，直接返回。
	 * 
	 * @param orderName
	 *            排序名
	 * @param orderType
	 *            排序类型：升序（ASC）、降序（DESC）
	 * @param mapping
	 *            添加排序名的映射，例如{{"name","C.CHANNEL_NAME"}{"areaName","A.AREA_NAME"}
	 *            }。 如果为空则或者没有找到映射则直接用orderName参数的值作为数据库的排序字段。
	 * @return StringBuilder对象
	 */
	public static String getOrderName(String orderName, String[][] mapping) {
		if (orderName == null) {
			return null;
		} else {
			if (mapping != null) {
				for (int i = 0; i < mapping.length; i++) {
					if (mapping[i][0].equals(orderName)) {
						orderName = mapping[i][1];
						return orderName;
					}
				}
			}

		}
		return orderName;
	}

	/**
	 * int 数组转换为以某个字符分隔的字符串
	 * 
	 * @author 王帅 @2010-9-18
	 * @param arr
	 * @param dou
	 * @return
	 */
	public static String intArrayToString(int[] arr, String dou) {
		String str = "";
		for (int i : arr) {
			str += i + dou;
		}
		return str.substring(0, str.lastIndexOf(dou));
	}

	/**
	 * 获取给定字符串的值，若字符串非空则返回自身，若字符串为null或""则返回默认值
	 * 
	 * @param value
	 *            ：给定字符串
	 * @param defaultValue
	 *            ：默认值
	 * @return
	 */
	public static String defaultIfEmpty(String value, String defaultValue) {
		return checkNull(value) ? defaultValue : value;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return boolean 为空(null或""),返回true;不为空,返回false
	 */
	public static boolean checkNull(String str) {
		boolean check = true;
		if ((null != str) && (!"".equals(str.trim()))) {
			check = false;
		}
		return check;
	}
	
	public static boolean checkNotNull(Object obj){
		boolean flag = false;
		if(obj != null){
			if(obj instanceof String){
				String str = (String) obj;
				if(str.length()>0){
					flag = true;
				}
			}else if(obj instanceof Date){
				flag = true;
			}else if(obj instanceof Collection<?>){
				Collection<?> collection = (Collection<?>)obj;
				if(!collection.isEmpty()){
					flag = true;
				}
			}else if(obj instanceof Object[]){
				Object[] arr = (Object[]) obj;
				if(arr.length > 0){
					flag = true;
				}
			}
		}
		return flag;
	}
	/**
	 * 验证中英文符号
	 */
	public static String verifySign(String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("（", "(");
		map.put("）", ")");
		String revalue = value;
		for (String key : map.keySet()) {
			revalue = revalue.replaceAll(key, map.get(key));
		}
		return revalue;

	}
}
