package cn.cf.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.util.MapRemoveNullUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.DoubleSerializer;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.util.TypeUtils;

public class JsonUtils {
	private static final SerializeConfig config;  
	private static final String YEAR_MONTH_DAY_TIME = "yyyy-MM-dd HH:mm:ss";

	static {
		config = new SerializeConfig();
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(Double.class, new DoubleSerializer("#.#####"));  
	}

	private static final SerializerFeature[] features = {

	SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteDateUseDateFormat, };

	public static String convertObjectToJSON(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}

	public static Object toBean(String text) {
		return JSON.parse(text);
	}

	public static <T> T toBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	/**
	 * 转换为数组
	 * 
	 * @param text
	 * @return
	 */
	public static <T> Object[] toArray(String text) {
		return toArray(text, null);
	}

	/**
	 * 转换为数组
	 * 
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> Object[] toArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	/**
	 * 转换为List
	 * 
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	public static <T> List<T> getStringToList(String jsonStr, Class<T> model) {
		List<T> ts = (List<T>) JSONArray.parseArray(jsonStr, model);
		return ts;
	}

	/**
	 * 将string转化为序列化的json字符串
	 * 
	 * @param keyvalue
	 * @return
	 */
	public static Object textToJson(String text) {
		Object objectJson = JSON.parse(text);
		return objectJson;
	}

	/**
	 * json字符串转化为map
	 * 
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> stringToCollect(String s) {
		Map<K, V> m = (Map<K, V>) JSONObject.parseObject(s);
		return m;
	}

	public static <K, V> Map<K, V> toHashMap(Object object) {
		if (null != object && !"".equals(object)) {
			String jsonObject = JSON.toJSONStringWithDateFormat(object, YEAR_MONTH_DAY_TIME);
			return JsonUtils.stringToCollect(jsonObject);
		}
		return null;
	}

	/**
	 * 转换JSON字符串为对象
	 * 
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	public static Object convertJsonToObject(String jsonData, Class<?> clazz) {
		return JSONObject.parseObject(jsonData, clazz);
	}

	/**
	 * 将map转化为string
	 * 
	 * @param m
	 * @return
	 */
	public static <K, V> String collectToString(Map<K, V> m) {
		String s = JSONObject.toJSONString(m);
		return s;
	}

	/**
	 * 
	 * json字符串转化为map 且过滤空值
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, Object> getStringToMap(String str) {
		Map<String, Object> m = (Map<String, Object>) JSONObject
				.parseObject(str);
		m = MapRemoveNullUtil.removeNullEntry(m);
		return m;
	}

	public static String convertToString(Object data) {
		return JSON.toJSONString(data);

	}
	
	public static Object formatJsonObject(Object data) {
		String jsonObject = JSON.toJSONStringWithDateFormat(data, YEAR_MONTH_DAY_TIME);
		return JSON.parse(jsonObject);

	}

	public static JSONObject toJSONObject(String data) {
		return JSON.parseObject(data);
	}

	public static String convertToString(PageModel<?> pm) {

		int size = 0;
		if (null == pm.getDataList()) {
			size = 0;
		} else {
			size = pm.getDataList().size();
		}
		TypeUtils.compatibleWithJavaBean = true;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total",
				pm.getTotalCount() == 0 ? size : pm.getTotalCount());
		resultMap.put("page", pm.getPageNo());
		resultMap.put("rows", JSONObject.parseArray(JSON.toJSONString(
				pm.getDataList(), features)));
		return JSON.toJSONString(resultMap);

	}

	public static String convertToStringEx(PageModel<?> pm, String[] excludes) {

		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		if (excludes != null && excludes.length > 0) {
			for (String arg : excludes) {
				filter.getExcludes().add(arg);
			}
		}
		int size = 0;
		if (null == pm.getDataList()) {
			size = 0;
		} else {
			size = pm.getDataList().size();
		}
		JSONObject jo = new JSONObject();
		jo.put("total", pm.getTotalCount() == 0 ? size : pm.getTotalCount());
		jo.put("page", pm.getPageNo());
		jo.put("rows", JSONObject.parseArray(JSONObject.toJSONString(
				pm.getDataList(), filter)));
		return jo.toJSONString();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T toJSONMapBean(String value, Class<T> clazz,
			Map<String, Class> map) {
		T t = JSON.parseObject(value, clazz);
		JSONObject js = JSON.parseObject(value);
		for (String key : map.keySet()) {
			try {
				JSONArray array = js.getJSONArray(key);
				if (null != array && array.size() > 0) {
					Class<T> avalue = map.get(key);
					List<T> list = new ArrayList<T>();
					for (int i = 0; i < array.size(); i++) {
						list.add(JSON.parseObject(array.getString(i), avalue));
					}
					t.getClass()
							.getMethod(
									"set" + key.substring(0, 1).toUpperCase()
											+ key.substring(1),
									t.getClass().getDeclaredField(key)
											.getType()).invoke(t, list);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}
}
