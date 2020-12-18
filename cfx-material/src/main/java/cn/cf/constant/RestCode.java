package cn.cf.constant;

import com.alibaba.fastjson.JSONObject;

public enum RestCode {
	/**
	 * 成功返回
	 */
	CODE_0000("0000")
	/**
	 * 异常返回
	 */
	,CODE_S999("S999")
	/**
	 * 请勿频繁操作
	 */
	,CODE_S998("S998")
	/**
	 * token失效
	 */
	,CODE_S001("S001")
	/**
	 * 签名错误
	 */
	,CODE_S002("S002")
	/**
	 * session失效
	 */
	,CODE_S003("S003")
	/**
	 * 短信验证码不正确，请重新输入
	 */
	,CODE_S004("S004")
	/**
	 * 非法字符
	 */
	,CODE_S005("S005")
	/**
	 * 字符串长度超过限制
	 */
	,CODE_S006("S006")
	/**
	 * 公司名称长度不能超过40位
	 */
	,CODE_S007("S007")
	/**
	 * 统一征信代码长度不能超过80位
	 */
	,CODE_S008("S008")
	/**
	 * 联系人长度不能超过80位
	 */
	,CODE_S009("S009")
	/**
	 * 联系电话长度不能超过20位
	 */
	,CODE_S010("S010")
	/**
	 * 参数不能为空
	 */
	,CODE_A001("A001")
	/**
	 * 返回某字段为空
	 */
	,CODE_A002("A002")
	/**
	 * 订单异常
	 */
	,CODE_A003("A003")
	/**
	 * 超级管理员不可修改
	 */
	,CODE_M000("M000")
	/**
	 * 用户已注册
	 */
	, CODE_M001("M001")
	/**
	 * 用户未注册
	 */
	, CODE_M002("M002")
	/**
	 * 用户已登陆
	 */
	, CODE_M003("M003")
		/**
	 * 用户未启用
	 */
	, CODE_M004("M004")
	/**
	 * 登录密码错误
	 */
	, CODE_M005("M005")
	/**
	 * 用户审核未通过
	 */
	, CODE_M006("M006")
		/**
	 * 会员未绑定公司
	 */
	, CODE_M007("M007")
	/**
	 * 会员未绑定公司
	 */
	, CODE_M008("M008")
	/**
	 * 用户不存在
	 */
	, CODE_M009("M009")
	/**
	 * 组长不能为自己
	 */
	, CODE_M0010("M0010")
	/**
	 * 用户今天已经签到
	 */
	, CODE_M011("M011")
	
	/**
	 * 公司不存在
	 */
	, CODE_C001("C001")
	/**
	 * 公司已添加
	 */
	, CODE_C010("C010")
	/**
	 * 公司未启用
	 */
	, CODE_C002("C002")
	/**
	 * 公司已审核
	 */
	, CODE_C003("C003")
	/**
	 * 统一社会信息代码已存在
	 */
	, CODE_C004("C004")
	/**
	 * 自定义输出内容
	 */
	, CODE_Z000("Z000")	
	/**
	 *当前公司已分配业务员，无需再次分配!
	 */
	,CODE_O0010("O0010")
	/**
	 *
	 *不可匹配该类型
	 */
	,CODE_O0011("O0011")
	/**
	 *
	 *不可匹配该类型
	 */
	,CODE_O0012("O0012");
	

	/**
	 * 枚举对象整型值
	 */
	private String code;
	private String msg;
	private String defaultValue;

	/**
	 * 构造方法
	 *
	 *  枚举对象整型值
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
		case "S999":
			return RestCode.CODE_S999;
		case "S998":
			return RestCode.CODE_S998;
		case "S001":
			return RestCode.CODE_S001;
		case "S002":
			return RestCode.CODE_S002;
		case "S003":
			return RestCode.CODE_S003;
		case "S004":
			return RestCode.CODE_S004;
		case "S005":
			return RestCode.CODE_S005;
		case "S006":
			return RestCode.CODE_S006;
		case "S007":
			return RestCode.CODE_S007;
		case "S008":
			return RestCode.CODE_S008;
		case "S009":
			return RestCode.CODE_S009;
		case "S010":
			return RestCode.CODE_S010;
		case "A001":
			return RestCode.CODE_A001;	
		case "A002":
			return RestCode.CODE_A002;
		case "A003":
			return RestCode.CODE_A003;
		case "M000":
			return RestCode.CODE_M000;
		case "M001":
			return RestCode.CODE_M001;
		case "M002":
			return RestCode.CODE_M002;
		case "M003":
			return RestCode.CODE_M003;
		case "M004":
			return RestCode.CODE_M004;
		case "M005":
			return RestCode.CODE_M005;
		case "M006":
			return RestCode.CODE_M006;
		case "M007":
			return RestCode.CODE_M007;
		case "M008":
			return RestCode.CODE_M008;
		case "M009":
			return RestCode.CODE_M009;
		case "M0010":
			return RestCode.CODE_M0010;
		case "M011":
			return RestCode.CODE_M011;
		case "C001":
			return RestCode.CODE_C001;
		case "C002":
			return RestCode.CODE_C002;
		case "C003":
			return RestCode.CODE_C003;
		case "C004":
			return RestCode.CODE_C004;
		case "C010":
			return RestCode.CODE_C010;
		case "Z000":
			return RestCode.CODE_Z000;	
		case "O0010":
			return RestCode.CODE_O0010;
		case "O0011":
			return RestCode.CODE_O0011;
		case "O0012":
			return RestCode.CODE_O0012;
		default:
			return null;
		}
	}

	/**
	 * 获取枚举对象描述信息，对应页面上映射的text 超级管理员不可修改
	 */
	@Override
	public String toString() {
		switch (this.code) {
		case "0000":
			return "操作成功";
		case "S999":
			return "系统异常";
		case "S998":
			return "请勿频繁操作";
		case "S001":
			return "TOKEN失效";
		case "S002":
			return "签名错误";
		case "S003":
			return "SESSION失效";
		case "S004":
			return "短信验证码不正确，请重新输入";
		case "S005":
			return "公司名称含有非法字符";
		case "S006":
			return "字符串长度超过最长限制";
		case "S007":
			return "公司名称长度不能超过40位";
		case "S008":
			return "统一征信代码长度不能超过80位";
		case "S009":
			return "联系人长度不能超过80位";
		case "S010":
			return "联系电话长度不能超过20位";
		case "A001":
			return "参数不能为空";
		case "A002":
			return "返回某字段为空";
		case "A003":
			return "订单异常";	
		case "M000":
			return "超级管理员不可修改";
		case "M001":
			return "用户已注册";
		case "M002":
			return "用户未注册";
		case "M003":
			return "用户审核中";
		case "M004":
			return "用户未启用";
		case "M005":
			return "登录密码错误";
		case "M006":
			return "用户已被登录";
		case "M007":
			return "用户审核未通过";
		case "M008":
			return "会员未绑定公司";
		case "M009":
			return "用户不存在";
		case "M0010":
			return "组长不能为自己";
		case "M011":
			return "每天不能重复签到";
		case "C001":
			return "公司不存在";
		case "C002":
			return "公司未启用";
		case "C003":
			return "公司已审核";
		case "C004":
			return "统一社会信息代码已存在";
		case "C010":
			return "公司已添加";
		case "O0010":
			return "当前公司已分配业务员，无需再次分配!";	
		case "O0011":
			return "业务员已有其他类型匹配,不可匹配该类型";
		case "O0012":
			return "内容不能为空";
		case "Z000":
			return getMsg();
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

	public void setCode(String code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
