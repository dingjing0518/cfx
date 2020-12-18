package cn.cf.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestBindingException;

import cn.cf.StringHelper;
import cn.cf.util.ServletUtils;

/**
 * @date 2010-9-25
 * @author
 */
public class QueryModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 缺省构造
	 */
	public QueryModel() {
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param t
	 */
	public QueryModel(T entity) {
		this.entity = entity;
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param inputMap
	 */
	public QueryModel(Map<String, Object> inputMap) {
		this.inputMap = inputMap;
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param entity
	 *            实体类
	 * @param start
	 *            开始索引
	 * @param limit
	 *            限制
	 */
	public QueryModel(T entity, int start, int limit) {
		this.entity = entity;
		this.start = start;
		this.limit = limit;
	}

	/**
	 * 传递参数构造。
	 * 
	 * @param entity
	 * @param start
	 * @param limit
	 * @param field
	 * @param dir
	 */
	public QueryModel(T entity, int start, int limit, int type, String field, String dir) {
		this.entity = entity;
		this.start = start;
		this.limit = limit;
		this.type = type;
		this.putOrder(field, dir);
	}

	/**
	 * 传递参数构造。
	 *
	 * @param entity
	 * @param start
	 * @param limit
	 * @param field
	 * @param dir
	 */
	public QueryModel(T entity, int start, int limit,  String field, String dir) {
		this.entity = entity;
		this.start = start;
		this.limit = limit;
		this.putOrder(field, dir);
	}

	/**
	 * 开始
	 */
	private int start = 0;
	/**
	 * 类型
	 */
	private int type= -1;
	/**
	 * 数量限制
	 */
	private int limit = Integer.MAX_VALUE;
	/**
	 * 由 start ,limit 算出 pageNo，以适应更多的查询方式。
	 */
	private int pageNo;
	/**
	 * 排序字段名,使用map来应对多字段排序, key=字段名，value=字段排序规则。
	 */
	private Map<String, String> orderMap;
	/**
	 * 需要查询的实体类，有限从此处取参数。<br />
	 * 优先级 1
	 */
	private T entity;
	/**
	 * 参数map，实体类没有的，从map取参数。<br />
	 * 优先级 2
	 */
	private Map<String, Object> inputMap;
	/**
	 * 用于应急传递参数的 拼串传递。 <br />
	 * 优先级3
	 */
	private String sql = "";

	/**
	 * putMap 方法，提供便捷的管理。默认使用 HashMap
	 * 
	 * @author
	 * @2010-9-25
	 * @param key
	 *            参数key,
	 * @param value
	 *            参数 value
	 * @return
	 */
	public QueryModel<T> putParams(String key, Object value) { // <泛型>
		this.getInputMap().put(key, value);
		return this;
	}

	/**
	 * 将排序 map 放入
	 * 
	 * @author
	 * @2010-9-25
	 * @param field
	 * @param type
	 * @return
	 */
	public QueryModel<T> putOrder(String field, String type) {
		this.getOrderMap().put(field, type);
		return this;
	}

	/**
	 * 获取默认排序，即 orderMap 大小为 1 时的排序。
	 * 
	 * @author
	 * @2010-9-25
	 * @return
	 */
	public String getFirstOrderName() {
		if (this.getOrderMap().isEmpty()) {
			return "";
		}
		return this.getOrderMap().keySet().iterator().next();
	}

	/**
	 * 获取默认排序顺序，即 orderMap 大小为 1 时的排序。
	 * 
	 * @author
	 * @2010-9-25
	 * @return
	 */
	public String getFirstOrderType() {
		if (this.getOrderMap().isEmpty()) {
			return "";
		}
		return this.orderMap.values().iterator().next();
	}

	/**
	 * 根据 参数 key 获取字符串参数值，为空返回 Null。
	 * 
	 * @author @2010-10-14
	 * @param key
	 * @return
	 */
	public String getParamsString(String key) {
		Object value = this.getInputMap().get(key);
		return value == null ? null : value.toString();
	}

	/**
	 * 根据 参数 key 获取整形参数值，为空返回 Null。
	 * 
	 * @author @2010-10-14
	 * @param key
	 * @return
	 */
	public Integer getParamsInteger(String key) {
		Object value = this.getInputMap().get(key);
		return (value == null ? null : (Integer) value);
	}

	/**
	 * 根据 参数 key 获取整形参数值，为空返回 Null。
	 * 
	 * @author @2010-10-14
	 * @param key
	 * @return
	 */
	public Long getParamsLong(String key) {
		Object value = this.getInputMap().get(key);
		return (value == null ? null : (Long) value);
	}

	/**
	 * 根据 参数 key 获取整形参数值，为空返回 Null。
	 * 
	 * @author @2010-10-14
	 * @param key
	 * @return
	 */
	public Boolean getParamsBoolean(String key) {
		Object value = this.getInputMap().get(key);
		return (value == null ? null : (Boolean) value);
	}

	/**
	 * 绑定常规排序，开始结束参数。
	 * 
	 * @author @2010-12-12
	 * @param request
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public QueryModel<T> bindParams(HttpServletRequest request)
			throws ServletRequestBindingException {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int typetype = ServletUtils.getIntParameter(request, "type", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 50);
		String field = ServletUtils.getStringParameter(request, "sort", null);
		String type = ServletUtils.getStringParameter(request, "dir", null);
		this.setLimit(limit);
		this.setType(typetype);
		this.setStart(start);
		if (StringHelper.hasText(field) && StringHelper.hasText(type)) {
			this.putOrder(field, type);
		}
		return this;
	}

	// -------------

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Map<String, String> getOrderMap() {
		if (this.orderMap == null) {
			this.orderMap = new LinkedHashMap<String, String>();
		}
		return this.orderMap;
	}

	public void setOrderMap(Map<String, String> orderMap) {
		this.orderMap = orderMap;
	}

	public T getEntity() {
		return this.entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Map<String, Object> getInputMap() {
		if (this.inputMap == null) {
			this.inputMap = new LinkedHashMap<String, Object>();
		}
		return this.inputMap;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public static String toString(Object value) {
		if (null != value) {
			return value.toString();
		}
		return null;
	}
	/* ----------------- get set ----------------- */

}
