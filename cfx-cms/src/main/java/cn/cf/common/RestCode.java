package cn.cf.common;

import net.sf.json.JSONObject;

public enum RestCode {
	/**
	 * 返回成功
	 */
	CODE_0000("0000")
	/**
	 * 返回某个字段属性为空
	 */
	, CODE_A000("A000")
	/**
	 * 返回某个字段值为空
	 */
	, CODE_A001("A001")
	/**
	 * 返回某个字段值不正确
	 */
	, CODE_A002("A002")
	/**
	 * 返回店铺营业中无法请求
	 */
	, CODE_A003("A003")
/**
	 * 商品已下架
	 */
	,CODE_O007("O007")
		/**
	 * 库存不足
	 */
	,CODE_O004("O004")
	/**
	 * 返回某个字段值为空
	 */
	, CODE_A004("A004")
		/**
	 * 库存异常
	 */
		, CODE_A005("A005")
		
	/**
	 *店铺暂未营业
	 */
		, CODE_A006("A006")
			/**
	 *商品价格为0
	 */
	,CODE_G002("G002")
		/**
	 * 商品箱重为0
	 */
	,CODE_G003("G003")
	/**
	 * TOKEN失效
	 */
	, CODE_S001("S001")
	/**
	 * 签名错误
	 */
	, CODE_S002("S002")
	/**
	 * SEESION失效
	 */
	, CODE_S003("S003")
	/**
	 * 验证码失效
	 */
	, CODE_S004("S004")
	/**
	 * 请求错误
	 */
	, CODE_S999("S999")
	/**
	 * 待审核
	 */
	, CODE_AS001("AS001")
	/**
	 * 审核不通过
	 */
	, CODE_AS002("AS002")
	/**
	 * 未选择收货地址
	 */
	,CODE_LO001("LO001")
	/** * 发票不存在
	 */
	, CODE_M009("M009")
				/**
	 * 发票名称不存在
	 */
	, CODE_M0010("M0010")
	/**
	 * 用户不存在
	 */
	, CODE_M001("M001")
	/**
	 * 用戶密碼錯誤
	 */
	, CODE_M002("M002")
	/**
	 * 用户已登陆
	 */
	, CODE_M003("M003")
	/**
	 * 用户已登陆
	 */
	, CODE_M004("M004")
		/**
	 * 已存在
	 */
	, CODE_M005("M005")
	
	/**
	 * 用户未注册
	 */
	, CODE_R001("R001")
	/**
	 * 用户已注册
	 */
	, CODE_R002("R002")
	/**
	 * 白条已申请
	 */
	, CODE_L001("L001")
	/**
	 * 格式有误
	 */
	, CODE_U001("U001")
	
	,CODE_U002("U002")
	/**
	 * 订单不存在
	 */
	,CODE_O001("O001")
	/**
	 * 订单信息有误
	 */
	,CODE_O002("O002")
	/**
	 * 会员未绑定公司
	 */
	 ,CODE_M008("M008")
	 /**
	  * 商品不存在
	  */
	,CODE_G001("G001");
	/**
	 * 枚举对象整型值
	 */
	private String code;
	private String msg;

	/**
	 * 构造方法
	 * 
	 * @param value
	 *            枚举对象整型值
	 */
	private RestCode(String code) {
		this.code = code;
		this.msg = this.toString();
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
	public static RestCode fromInt(String val) {
		if (null == val) {
			return null;
		}
		/**
		 * TOKEN失效
		 */
		switch (val) {
		case "0000":
			return RestCode.CODE_0000;
		case "A000":
			return RestCode.CODE_A000;
		case "A001":
			return RestCode.CODE_A001;
		case "A002":
			return RestCode.CODE_A002;
		case "A003":
			return RestCode.CODE_A003;
		case "O007":
			return RestCode.CODE_O007;
		case "O004":
			return RestCode.CODE_O004;
		case "A004":
			return RestCode.CODE_A004;
		case "A005":
			return RestCode.CODE_A005;
		case "A006":
			return RestCode.CODE_A006;
		case "G002":
			return RestCode.CODE_G002;
		case "G003":
			return RestCode.CODE_G003;
		case "S001":
			return RestCode.CODE_S001;
		case "S002":
			return RestCode.CODE_S002;
		case "S003":
			return RestCode.CODE_S003;
		case "S004":
			return RestCode.CODE_S004;
		case "S999":
			return RestCode.CODE_S999;
		case "AS001":
			return RestCode.CODE_AS001;
		case "AS002":
			return RestCode.CODE_AS002;
		case "M001":
			return RestCode.CODE_M001;
		case "M002":
			return RestCode.CODE_M002;
		case "R001":
			return RestCode.CODE_R001;
		case "R002":
			return RestCode.CODE_R002;
		case "M003":
			return RestCode.CODE_M003;
		case "M004":
			return RestCode.CODE_M004;
		case "M005":
			return RestCode.CODE_M005;
		case "L001":
			return RestCode.CODE_L001;
		case "U001":
			return RestCode.CODE_U001;
		case "U002":
			return RestCode.CODE_U002;
		case "O001":
			return RestCode.CODE_O001;
		case "O002":
			return RestCode.CODE_O002;
		case "M008":
			return RestCode.CODE_M008;
		case "G001":
			return RestCode.CODE_G001;
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
		case "0000":
			return "操作成功";
		case "A000":
			return "返回字段属性为空";
		case "A001":
			return "返回字段值为空";
		case "A002":
			return "返回字段值不正确";
		case "A003":
			return "返回店铺营业中无法请求";
		case "A004":
			return "返回请求失败";
		case "A005":
			return "库存异常";
		case "A006":
			return "店铺暂未营业";
		case "G002":
			return "商品价格为0";
		case "G003":
			return "商品箱重为0";
		case "S001":
			return "TOKEN失效";
		case "S002":
			return "签名错误";
		case "S003":
			return "SEESION失效";
		case "S004":
			return "验证码失效";
		case "S005":
			return "用户已注册";
		case "AS001":
			return "待审核";
		case "AS002":
			return "审核不通过";
		case "S999":
			return "请求错误";
		case "M001":
			return "用户不存在";
		case "M002":
			return "用户密码错误";
		case "M003":
			return "用户已登录";
		case "M004":
			return "用户支付密码错误";
		case "M005":
			return "name已存在";
		case "R001":
			return "用户未注册";
		case "R002":
			return "用户已注册";
		case "L001":
			return "公司已申请白条";
		case "U001":
			return "文件格式有误";
		case "O007":
			return "商品已下架";
		case "O004":
			return "商品库存不足";
		case "U002":
			return "您上传的文件已超出最大限制2M，请重新上传";
		case "O001":
			return "订单号不存在";
		case "O002":
			return "订单信息有误";	
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
