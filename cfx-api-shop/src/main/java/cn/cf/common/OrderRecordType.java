/**
 * 
 */
package cn.cf.common;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author bin
 * 
 */
public enum OrderRecordType {
	/**
	 * 订单创建
	 */
	INSERT(1),
	
	/**
	 * 支付
	 */
	PAYMENT(2),
	/**
	 * 修改
	 */
	UPDATE(3),
	/**
	 * 还款
	 */
	REPAYMENT(4),
	/**
	 * 部分发货
	 */
	PARTIALSHIPMENT(5),
	/**
 * 全部发货
 */
	ALLSHIPMENTS(6);
	/**
	 * 枚举对象整型值
	 */
	private int code;
	private String msg;

	/**
	 * 构造方法
	 * 
	 * @param value
	 *            枚举对象整型值
	 */
	private OrderRecordType(int code) {
		this.code = code;
		this.msg = this.toString();
	}

	/**
	 * 获取枚举对象对应的整型值
	 * 
	 * @author 赵旺 @2010-8-29
	 * @return
	 */
	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 根据String值构造枚举对象
	 * 
	 * @author 赵旺 @2010-8-29
	 * @param str
	 *            被转换的String对象
	 * @return 若构造失败，返回null
	 */
	public static RestCode fromString(String str) {
		try {
			return RestCode.valueOf(str);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 根据整型值构造枚举对象
	 * 
	 * @author 赵旺 @2010-8-29
	 * @param val
	 *            枚举对象整型值
	 * @return 若没有匹配的整型值，则返回null
	 */
	public static RestCode fromInt(int val) {
		/**
		 * TOKEN失效
		 */
		switch (val) {
		case 1:
			return RestCode.CODE_0000;
		case 2:
			return RestCode.CODE_S001;
		default:
			return null;
		}
	}

	/**
	 * 获取枚举对象描述信息，对应页面上映射的text
	 */
	@Override
	public String toString() {
		switch (this.code) {
		case 1:
			return "创建订单成功,";
		case 2:
			return "订单支付成功,";
		case 3:
			return "订单更新成功,";
		case 4:
			return "订单还款记录,";
		case 5:
			return "订单部分发货,";
		case 6:
			return "订单商品均已全部发货,请查收  ";
		default:
			return "";
		}
	}

	/**
	 * 获取代码字符串
	 * 
	 * @author 赵旺 @2010-8-29
	 * @return 代码字符串， 即"INACTIVE"等，对应页面上映射的codeText
	 */
	public String toCodeString() {
		return super.toString();
	}

	public String getValue() {
		return super.toString();
	}

	@JsonValue
	public String toJson() {
		JSONObject js = new JSONObject();
		js.put("msg", getMsg());
		js.put("code", getCode());
		return js.toString();
	}

	@JsonValue
	public String toJson(Object val) {
		JSONObject js = new JSONObject();
		js.put("msg", getMsg());
		js.put("code", getCode());
		if (null != val) {
			js.put("data", val);
		}
		return js.toString();
	}
}
