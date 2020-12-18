package cn.cf.property;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cn.cf.json.JsonUtils;

import com.alibaba.fastjson.JSONObject;

public class PropertyConfig {
	private static Properties mConfig;
	static {
		mConfig = new Properties();
	}
	/**
	 * 初始化单个数据
	 * @param content
	 */
	public static void init(String content){
		if(null != content){
			JSONObject o = JsonUtils.toJSONObject(content);
			for(Map.Entry<String, Object> entry : o.entrySet()){
				mConfig.setProperty(entry.getKey(), entry.getValue().toString());
			}
		}
		
	}
	
	public static void initMsProperties(String path) {
		InputStream is = new BufferedInputStream(
				PropertyConfig.class.getClassLoader().getResourceAsStream(path));
		try {
			mConfig.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		String value = null;
		if(null != key && !"".equals(key)){
			value = mConfig.getProperty(key);
		}
		return null == value?"":value;
	}
	
	
	public static String getPropertyValue(String path,String arg){  
		String resourcePath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();  
		         Properties prop = new Properties();  
		         try {  
		             InputStream in = new FileInputStream(resourcePath);  
		             prop.load(in);  
		         }catch (FileNotFoundException e) {  
		                e.printStackTrace();  
		         }catch (IOException e) {  
		         }  
		           
		         return prop.getProperty(arg);  
		    }

	public static String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		if (value == null)
			return defaultValue;

		return value;
	}

	public static boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = PropertyConfig.getProperty(name);

		if (value == null)
			return defaultValue;

		return (new Boolean(value)).booleanValue();
	}

	public static int getIntProperty(String name) {
		return getIntProperty(name, 0);
	}

	public static int getIntProperty(String name, int defaultValue) {
		String value = getProperty(name);

		if (value == null)
			return defaultValue;

		return (new Integer(value)).intValue();
	}

	/**
	 * @author xianglinhai
	 * @version 2.0 通过键获得int类型的value
	 */
	public static int getIntValueByKey(String key) {
		
		return Integer.parseInt(getProperty(key));
	}

	/**
	 * @author xianglinhai
	 * @version 2.0 通过键获得String类型的value
	 */
	public static String getStrValueByKey(String key) {
		String value = null;
		try {
			value = new String(getProperty(key).getBytes("ISO8859-1"),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;

	}
	
	public static void search(){
		
		 Set<Object> keys = mConfig.keySet();//返回属性key的集合
	        for (Object key : keys) {
	            System.out.println(key.toString() + "=" + mConfig.get(key));
	        }
	}

}
