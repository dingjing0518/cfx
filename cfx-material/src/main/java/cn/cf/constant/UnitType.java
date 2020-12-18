/**
 * 
 */
package cn.cf.constant;


/**
 * @author bin
 *
 */
public enum UnitType {
	UNIT_1("1"),
	UNIT_2("2"),
	UNIT_3("3"),
	UNIT_4("4"),
	UNIT_5("5");
	/**
	 * 枚举对象整型值
	 */
	private String code;
	private String defaultValue;
	


	/**
	 * 构造方法
	 *
	 *  枚举对象整型值
	 */
	private UnitType(String code) {
		this.code = code;
	}

	/**
	 * 获取枚举对象对应的整型值
	 * 
	 * @author 赵旺 @2010-8-29
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * 根据String值构造枚举对象
	 * 
	 * @author 赵旺 @2010-8-29
	 * @param str
	 *            被转换的String对象
	 * @return 若构造失败，返回null
	 */
	public static UnitType fromString(String str) {
		try {
			return UnitType.valueOf(str);
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
	public static UnitType fromInt(String val) {
		if (null == val || "".equals(val)) {
			return UnitType.UNIT_1;
		}
		/**
		 * TOKEN失效
		 */
		switch (val) {
		case "1":
			return UnitType.UNIT_1;
		case "2":
			return UnitType.UNIT_2;
		case "3":
			return UnitType.UNIT_3;
		case "4":
			return UnitType.UNIT_4;
		case "5":
			return UnitType.UNIT_5;
		default:
			return UnitType.UNIT_1;
		}
	}

	/**
	 * 获取枚举对象描述信息，对应页面上映射的text 超级管理员不可修改
	 */
	@Override
	public String toString() {
		//单位(1:箱 2:锭 3:件 4:粒)
		switch (this.code) {
		case "1":
			return "箱 ";
		case "2":
			return "锭 ";
		case "3":
			return "件";
		case "4":
			return "粒 ";
		case "5":
			return "包 ";
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


	public void setCode(String code) {
		this.code = code;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public static void main(String[] args) {
		System.out.println(UnitType.fromInt("5").toString());
	}
}
