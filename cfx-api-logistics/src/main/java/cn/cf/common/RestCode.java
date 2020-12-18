package cn.cf.common;



import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonValue;

import cn.cf.json.JsonUtils;

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
	 *必填参数不能为空
	 */
	, CODE_A007("A007")
	
	/**
	 *请维护发票信息
	 */
	, CODE_A008("A008")
	
	/**
	 *手机号码已存在
	 */
	, CODE_A009("A009")
	
	/**
	 * 商品不存在
	 */
	,CODE_G001("G001")
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
	 * 短信验证码失效
	 */
	, CODE_S006("S006")
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
	 * 用户不存在
	 */
	, CODE_M001("M001")
	/**
	 * 物流承运商不存在
	 */
	, CODE_M011("M011")
	/**
	 * 虚拟账号已存在
	 */
	,CODE_V001("V001")
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
	 * 用户未启用
	 */
	, CODE_M006("M006")
	/**
	 * 请选择采购商
	 */
	, CODE_M007("M007")
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
	 * 虚拟账户未申请
	 */
	, CODE_L002("L002")
	/**
	 * 授信额度未启用
	 */
	, CODE_L003("L003")
	/**
	 * 额度支付申请失败
	 */
	, CODE_L004("L004")
	/**
	 * 支付错误
	 */
	, CODE_L005("L005")
	/**
	 * 该订单已不可支付
	 */
	, CODE_L006("L006")
	/**
	 * 支付成功
	 */
	, CODE_L007("L007")
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
	 * 订单已确认付款，无法取消订单
	 */
	,CODE_O003("O003")
	/**
	 * 库存不足
	 */
	,CODE_O004("O004")
	/**
	 * 不满足业务要求
	 */
	,CODE_O005("O005")
	/**
	 * 商品已存在
	 */
	,CODE_O006("O006")
	/**
	 * 商品已下架
	 */
	,CODE_O007("O007")
	/**
	 * 不能发布重复需求
	 */
	,CODE_O008("O008")
	/**
	 * 订单状态异常
	 */
	,CODE_O009("O009")
	 /**
     * 订单已存在
     */
	,CODE_O010("O010")
	/**
	 * 传入的某个参数值为空
	 */

	,CODE_T000("T000")
	/**
	 * 公司未注册
	 */
	,CODE_C001("C001")
	/**
	 * 公司名称不可重复
	 */
	,CODE_C002("C002")
	/**
	 * 组织机构代码重复
	 */
	,CODE_C003("C003")
	/***
	 * 短信模板不存在
	 */
	,CODE_SMS001("SMS001")
	/**
	 * 未选择收货地址
	 */
	,CODE_LO001("LO001")
	/**
	 * 未选择提货地址
	 */
	,CODE_LO002("LO002")
	/**
	 * 收货地址不存在
	 */
	,CODE_LO003("LO003")
	/**
	 * 提货地址不存在
	 */
	,CODE_LO004("LO004")
	/**
	 * 支付密码错误
	 */
	,CODE_P001("P001")
	/**
	 * 还款失败
	 */
	,CODE_Z001("Z001")
	/**
	 * 银行卡号或密码错误
	 */
	,CODE_B001("B001")
	/**
	 * 提现失败
	 */
	,CODE_B002("B002")
	/**
	 * 已经绑定过银行卡
	 */
	,CODE_B003("B003")
	/**
	 * 绑卡失败
	 */
	,CODE_B004("B004")
	/**
	 * 银行返回内容
	 */
	,CODE_B005("B005")
	/**
	 * 采购商已添加
	 */
	,CODE_Y001("Y001")
	/**
	 * 物流模板已存在
	 */
	,CODE_L000("L000")
	/**
	 * 商品价格不为0
	 */
	,CODE_G000("G000")
	/**
	 * 请求参数错误
	 */
	,CODE_P000("P000")
	/**
	 * 参数类型错误
	 */
	,CODE_P002("P002")
	/**
	 * 地址已存在
	 */
	,CODE_Q001("Q001")
	/**
	 * 发票信息已存在
	 */
	,CODE_Q002("Q002")
	/**
	 * 已开完票
	 */
	,CODE_Q003("Q003")
	/**
	 * 手机号已存在
	 */
	,CODE_M008("M008");
	
	/**
	 * 枚举对象整型值
	 */
	private String code;
	private String msg;

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
		case "A004":
			return RestCode.CODE_A004;
		case "A005":
			return RestCode.CODE_A005;
		case "A006":
			return RestCode.CODE_A006;
		case "A007":
			return RestCode.CODE_A007;
		case "A008":
			return RestCode.CODE_A008;
		case "A009":
			return RestCode.CODE_A009;
		case "S001":
			return RestCode.CODE_S001;
		case "S002":
			return RestCode.CODE_S002;
		case "S003":
			return RestCode.CODE_S003;
		case "S004":
			return RestCode.CODE_S004;
		case "S006":
			return RestCode.CODE_S006;
		case "S999":
			return RestCode.CODE_S999;
		case "AS001":
			return RestCode.CODE_AS001;
		case "AS002":
			return RestCode.CODE_AS002;
		case "M001":
			return RestCode.CODE_M001;
		case "M011":
			return RestCode.CODE_M011;
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
		case "M006":
			return RestCode.CODE_M006;
		case "M007":
			return RestCode.CODE_M007;
		case "L001":
			return RestCode.CODE_L001;
		case "L002":
			return RestCode.CODE_L002;
		case "L003":
			return RestCode.CODE_L003;	
		case "L004":
			return RestCode.CODE_L004;
		case "L005":
			return RestCode.CODE_L005;
		case "L006":
			return RestCode.CODE_L006;
		case "L007":
			return RestCode.CODE_L007;
		case "U001":
			return RestCode.CODE_U001;
		case "U002":
			return RestCode.CODE_U002;
		case "O001":
			return RestCode.CODE_O001;	
		case "O002":
			return RestCode.CODE_O002;
		case "O003":
			return RestCode.CODE_O003;
		case "O004":
			return RestCode.CODE_O004;
		case "O005":
			return RestCode.CODE_O005;
		case "O006":
			return RestCode.CODE_O006;
		case "O007":
			return RestCode.CODE_O007;
		case "O008":
			return RestCode.CODE_O008;
		case "O009":
			return RestCode.CODE_O009;
		case "O010":
			return RestCode.CODE_O010;	
		case "T000":
			return RestCode.CODE_T000;
		case "C001":
			return RestCode.CODE_C001;	
		case "SMS001":
			return RestCode.CODE_SMS001;
		case "V001":
			return RestCode.CODE_V001;
		case "LO001":
			return RestCode.CODE_LO001;
		case "LO002":
			return RestCode.CODE_LO002;
		case "LO003":
			return RestCode.CODE_LO003;
		case "LO004":
			return RestCode.CODE_LO004;
		case "P001":
			return RestCode.CODE_P001;
		case "Z001":
			return RestCode.CODE_Z001;	
		case "B001":
			return RestCode.CODE_B001;
		case "B002":
			return RestCode.CODE_B002;
		case "B003":
			return RestCode.CODE_B003;
		case "B004":
			return RestCode.CODE_B004;
		case "B005":
			return RestCode.CODE_B005;
		case "L000":
			return RestCode.CODE_L000;
		case "C002":
			return RestCode.CODE_C002;
		case "C003":
			return RestCode.CODE_C003;		
		case "Y001":
			return RestCode.CODE_Y001;		
		case "G001":
			return RestCode.CODE_G001;
		case "G000":
			return RestCode.CODE_G000;
		case "P002":
			return RestCode.CODE_P002;
		case "Q001":
			return RestCode.CODE_Q001;
		case "Q002":
			return RestCode.CODE_Q002;
		case "Q003":
			return RestCode.CODE_Q003;
		case "M008":
			return RestCode.CODE_M008;
			
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
		case "A007":
			return "必填参数不能为空";
		case "A008":
			return "请维护发票信息";
		case "A009":
			return "手机号码已存在";
		case "S001":
			return "TOKEN失效";
		case "S002":
			return "签名错误";
		case "S003":
			return "SEESION失效";
		case "S004":
			return "验证码失效";
		case "S006":
			return "短信验证码失效";
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
		case "M011":
			return "承运商不存在";
		case "M002":
			return "用户密码错误";
		case "M003":
			return "用户已被登录";
		case "M004":
			return "用户支付密码错误";
		case "M005":
			return "名称已存在";
		case "M006":
			return "用户未启用";
		case "M007":
			return "请选择采购商";
		case "R001":
			return "用户未注册";
		case "R002":
			return "用户已注册";
		case "L001":
			return "公司已申请白条";
		case "L002":
			return "虚拟账户未申请";	
		case "L003":
			return "授信额度未启用";
		case "L004":
			return "额度支付申请失败";
		case "L005":
			return "虚拟账户支付失败";	
		case "L006":
			return "该订单已不可支付";	
		case "L007":
			return "支付成功";
		case "U001":
			return "文件格式有误";
		case "U002":
			return "您上传的文件已超出最大限制2M，请重新上传";
		case "O001":
			return "订单号不存在";
		case "O002":
			return "订单信息有误";
		case "O003":
			return "订单已付款，无法关闭订单";
		case "O004":
			return "商品库存不足";
		case "O005":
			return "不满足业务要求";
		case "O006":
			return "商品已存在";
		case "O007":
			return "商品已下架";
		case "O008":
			return "不能发布重复需求";
		case "O009":
			return "订单状态异常";
		case "O010":
			return "订单已存在";	
		case "T000":
			return "传入的参数值为空";
		case "C001":
			return "公司抬头未匹配到对应公司";	
		case "SMS001":
			return "短信模板不存在";
		case "V001":
			return "虚拟子账户已申请";
		case "LO001":
			return "未选择收货地址";	
		case "LO002":
			return "未选择提货地址";
		case "LO003":
			return "收货地址不存在";	
		case "LO004":
			return "提货地址不存在";
		case "P001":
			return "支付密码错误";
		case "Z001":
			return "还款失败";	
		case "B001":
			return "银行卡号或密码错误";
		case "B002":
			return "提现失败";
		case "B003":
			return "银行卡已经绑定";
		case "B004":
			return "银行卡绑定失败";
		case "B005":
			return getMsg();		
		case "L000":
			return "物流模板已存在";
		case "C002":
			return "公司名称不可重复";
		case "C003":
			return "公司营业执照号已被注册";	
		case "Y001":
			return "已添加";	
		case "G001":
			return "商品不存在";
		case "G000":
			return "商品价格不能为0";
		case "P000":
			return "请求参数有误";
		case "P002":
			return "参数类型错误";
		case "Q001":
			return "地址已存在";
		case "Q002":
			return "发票信息已存在";
		case "Q003":
			return "已开完票";
		case "M008":
			return "手机号已存在";
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
			js.put("data",JsonUtils.formatJsonObject(val));
		}
		return js.toString();
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
