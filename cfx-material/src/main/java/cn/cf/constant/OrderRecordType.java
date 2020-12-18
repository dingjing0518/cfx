/**
 * 
 */
package cn.cf.constant;


import com.alibaba.fastjson.JSONObject;

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
	 * 订单支付
	 */
	PAYMENT(2),
	/**
	 * 订单修改
	 */
	UPDATE(3),
	/**
	 * 订单还款
	 */
	REPAYMENT(4),
	/**
	 * 部分发货
	 */
	PARTIALSHIPMENT(5),
	/**
	 * 全部发货
	 */
	ALLSHIPMENTS(6),
	/**
	 * 合同创建
	 */
	CINSERT(7),
	/**
	 * 合同支付
	 */
	CPAYMENT(8),
	/**
	 * 合同修改
	 */
	CUPDATE(9),
	/**
	 * 合同还款
	 */
	CREPAYMENT(10),
	/**
	 * 合同部分发货
	 */
	CPARTIALSHIPMENT(11),
	/**
	 * 合同全部发货
	 */
	CALLSHIPMENTS(12);
	
	
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
	/*public static RestCode fromString(String str) {
		try {
			return RestCode.valueOf(str);
		} catch (RuntimeException e) {
			return null;
		}
	}*/

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
		case 7:
			return "创建合同成功,";
		case 8:
			return "合同支付成功,";
		case 9:
			return "合同更新成功,";
		case 10:
			return "合同还款记录,";
		case 11:
			return "合同部分发货,";
		case 12:
			return "合同商品均已全部发货,请查收  ";
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

	public String toJson() {
		JSONObject js = new JSONObject();
		js.put("msg", getMsg());
		js.put("code", getCode());
		return js.toString();
	}

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
