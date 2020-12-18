package cn.cf.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	private static final String REGEX_SPECIAL_TEXT = "^[A-Za-z0-9\u4e00-\u9fa5()（）]+$";
	
	 /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    /**
     * 是否是子母和数据
     */
    public static final String REGEX_NUMBERKEY = "^[a-zA-Z0-9_]+$";
    /**
     * 验证2-4汉字
     */
    public static final String REGEX_TWOFOURCHINESE = "^[\u4E00-\u9FA5]{2,4}$";
    
    /**
     * 纯数字
     */
    public static final String REGEX_NUMBER = "^[0-9]*[1-9][0-9]*$";
    
    /**
     * 纯中文
     */
    public static final String REGEX_TEXT = "^[\u4E00-\u9FFF]+$";
    /**
     *数字带橫杠 
     */
    public static final String REGEX_NUMBER_PLUS = "[0-9-]+";
    /**
     * 特殊符号 
     */
    	private static final String REGEX_SPECIAL = "[`~!@#$%^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——|{}【】‘；：”“’。，、\\？_]";
/***************************该区域请勿改动********************************************************/
    	/***
    	 * 包含 .*(\\d+)((?i)D|/|(?i)d/)(\\d+)((?i)f).*
    	 */
    	public static final String REGEX_CONTSIN_NUMBER_DF=".*(\\d+)((?i)D|/|(?i)d/)(\\d+)((?i)f).*";
    	
    	/***
    	 * 只含(\\d+)(((?i)d|/|(?i)d/)(\\d+)((?i)f))
    	 */
    	public static final String REGEX_NUMBER_DF="(\\d+)(((?i)d|/|(?i)d/)(\\d+)((?i)f))";
    	
    	/***
    	 * 只含(((?i)d|/|(?i)d/)(\\d+)((?i)f))
    	 */
    	public static final String REGEX_DF="(((?i)d|/|(?i)d/)(\\d+)((?i)f))";
    	
    	
    	/***
    	 * 包含".*(\\d+)((?i)D|/|(?i)d/)(\\d+).*"
    	 */
    	public static final String REGEX_CONTSIN_NUMBER_D_NUMBER=".*(\\d+)((?i)D|/|(?i)d/)(\\d+).*";
    	
    	/***
    	 * 只含(\\d+)(((?i)d|/|(?i)d/)(\\d+))
    	 */
    	public static final String REGEX_NUMBER_D_NUMBER="(\\d+)(((?i)d|/|(?i)d/)(\\d+))";
    	
    	/***
    	 * 只含"(((?i)d|/|(?i)d/)(\\d+))"
    	 */
    	public static final String REGEX_D_NUMBER="(((?i)d|/|(?i)d/)(\\d+))";
    	
    	
    	/***
    	 * 包含.*(\\d+)((?i)d).*
    	 */
    	public static final String REGEX_CONTSIN_NUMBER_D=".*(\\d+)((?i)d).*";
    	
       	/***
    	 * 包含.*(\\d+)((?i)f).*
    	 */
    	public static final String REGEX_CONTSIN_NUMBER_F=".*(\\d+)((?i)f).*";
     	/***
    	 * 只含(\\d+)((?i)f)
    	 */
    	public static final String REGEX_NUMBER_F="(\\d+)((?i)f)";
    	/***
    	 * 包含.*\\d+.*
    	 */
    	public static final String REGEX_CONTSIN_NUMBER=".*\\d+.*";
    	
    	/***
    	 * 只含[(\\d+)((?i)f)[
    	 */
    	public static final String REGEX_BRACKETS_NUMBER_F="\\[(\\d+)((?i)f)]";
    	
    	/***
    	 * 只含(\\d+)((?i)d)
    	 */
    	public static final String REGEX_NUMBER_D="(\\d+)((?i)d)";
    	
    	/***
    	 * 只含(\\++|\\-+)
    	 */
    	public static final String REGEX_PLUS_OR_MINUD="(\\++|\\-+|\\++\\-+|\\-+\\++)";
    	/***
    	 * 只含([0-9]+F)
    	 */
    	public static final String REGEX_NUMBER_ANDF="([0-9]+F)";
    	
    	/***
    	 * 包含特殊符号
    	 *  sql 正则的时候需转义
    	 */
    	public static final String REGEX_CONTSIN_SPECIAL=".*(\\+|\\\\|(|)|$|\\*|\\.|\\[|\\]|\\?|\\^|\\{|\\}).*";
    	 
    	/***
    	 * 英文，英文-英文[a-zA-Z-]+[a-zA-Z]|[a-zA-Z]
    	 */
    	public static final String REGEX_LETTER_LETTER="[a-zA-Z-]+[a-zA-Z]|[a-zA-Z]";
    	/***************************该区域请勿改动********************************************************/
    	
    	/**
	 * 验证包含特殊字符
	 * @param str
	 * @return
	 */
	public static boolean isSpecialChar(String str) {
        Pattern p = Pattern.compile(REGEX_SPECIAL_TEXT);
        Matcher m = p.matcher(str);
        return m.find();
    }
	
	public static String getNumber(String str){
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(str);   
		return m.replaceAll("").trim();
	}
	
	   /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String str) {
    	if(null == str){
    		return false;
    	}
        return Pattern.matches(REGEX_MOBILE, str);
    }
	
	/**
	  * 校验数字子母
	  * 
	  * @param mobile
	  * @return 校验通过返回true，否则返回false
	  */
	 public static boolean isNumberAndKey(String str) {
		 if(null == str){
	    		return false;
	     }
	     return Pattern.matches(REGEX_NUMBERKEY, str);
	 }
	 
	 
		/**
	  * 校验2-4汉字
	  * 
	  * @param mobile
	  * @return 校验通过返回true，否则返回false
	  */
	 public static boolean isTwoToFourChinese(String str) {
		 if(null == str){
	    		return false;
	     }
	     return Pattern.matches(REGEX_TWOFOURCHINESE, str);
	 }
	 
	/**
	  * 校验数字
	  * 
	  * @param mobile
	  * @return 校验通过返回true，否则返回false
	  */
	 public static boolean isNumber(String str) {
		 if(null == str){
	    		return false;
	     }
	     return Pattern.matches(REGEX_NUMBER, str);
	 }
		/**
	  * 校验数字加横杠
	  * 
	  * @param mobile
	  * @return 校验通过返回true，否则返回false
	  */
	 public static boolean isNumberPlus(String str) {
		 if(null == str){
	    		return false;
	     }
	     return Pattern.matches(REGEX_NUMBER_PLUS, str);
	 }
	 /**
	  * 过滤出中英文和数字
	  * @param str
	  * @return
	  */
	 public static String getNospecialText(String str){
		if(null != str && !"".equals(str)){
			str =str.replaceAll(REGEX_SPECIAL, "");
		}
		return str;

	 }

		public static String getSpecialText(String str) {
			Pattern p = Pattern.compile(REGEX_SPECIAL);
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim();
		}
		
		public static boolean isEnglishPlus(String str) {
			Pattern p = Pattern.compile("^[A-Za-z]+\\+$");
	        Matcher m = p.matcher(str);
	        return m.find();
	    }
		public static boolean isEnglishReduce(String str) {
			Pattern p = Pattern.compile("^[A-Za-z]+\\-$");
	        Matcher m = p.matcher(str);
	        return m.find();
	    }
		/***
		 * 
		 * @param input
		 * @param regex
		 * @return
		 */
		public static Set<String> getlist(String input, String regex) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(input);
			Set<String> a1=new HashSet<String>();
			while (m.find()) { 
				a1.add(m.group(0));
					        }
			return a1;
			
		}
}
