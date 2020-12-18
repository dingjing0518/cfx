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
	 * 已操作
	 */
	, CODE_C000("C000")
	/**
	 * 超级管理员不可修改
	 */
	, CODE_0001("0001")
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
	 *店铺不存在,供应商未审核通过
	 */
		, CODE_A007("A007")	
	/**
	 * 商品不存在
	 */
	,CODE_G001("G001")
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
	 * 未启用
	 */
	, CODE_AS002("AS002")
	/**
	 * 用户不存在
	 */
	, CODE_M001("M001")
	
	/**
	 * 用户不存在
	 */
	, CODE_M000("M000")
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
	 * 未审核通过
	 */
	, CODE_M006("M006")
	/**
	 * 请选择采购商
	 */
	, CODE_M007("M007")
		/**
	 * 会员未绑定公司
	 */
	, CODE_M008("M008")
		/**
	 * 未添加此成员
	 */
	, CODE_M0011("M0011")
			/**
	 * 发票不存在
	 */
	, CODE_M009("M009")
				/**
	 * 发票名称不存在
	 */
	, CODE_M0010("M0010")
		/**
	 * 已存在,不可重复提交
	 */
	, CODE_M0012("M0012")
			/**
	 * 组长不能为自己
	 */
	, CODE_M0013("M0013")
		/**
	 * 工号不能重复
	 */
	, CODE_M0014("M0014")
		/**
	 *竞拍中，不可重复提交
	 */
	, CODE_M0015("M0015")
			/**
	 *竞拍日期不可早于当日
	 */
	, CODE_M0016("M0016")
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
	 * 额度不足
	 */
	, CODE_L006("L006")
	/**
	 * 白条未申请
	 */
	, CODE_L007("L007")
	/**
	 * 供应商未绑定实体账户
	 */
	, CODE_L008("L008")
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
	 *品名不存在
	 */
	,CODE_O009("O009")
	/**
	 *采购商与品名已匹配
	 */
	,CODE_O0010("O0010")
	/**
	 *不可匹配该类型
	 */
	,CODE_O0011("O0011")
	/**
	 *订单已取消
	 */
	,CODE_O0012("O0012")
	/**
	 *订单已审核
	 */
	,CODE_O0013("O0013")
	/**
	 *分货数量不能为0
	 */
	,CODE_O0014("O0014")
	/**
	 *子订单号不存在
	 */
	,CODE_O0015("O0015")
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
	/**
	 * 公司已审核通过
	 */
	,CODE_C004("C004")
	/**
	 * 公司不存在
	 */
	,CODE_C005("C005")
	/***
	 * 短信模板不存在
	 */
	,CODE_SMS001("SMS001")
	/**
	 * 未选择收货地址
	 */
	,CODE_LO001("LO001")/**
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
	 * 自定义返回内容
	 */
	,CODE_B005("B005")
	/**
	 * 银行返回数据异常
	 */
	,CODE_B006("B006")
		/**
	 * 自定义返回内容
	 */
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
	 * 竞拍活动已结束
	 */
	,CODE_U000("U000")
	/**
	 * 接口更新中
	 */
	,CODE_I001("I001")
	/**
	 * 接口更新失败
	 */
	,CODE_I002("I002")
	/**
	 * 优惠券不存在
	 */
	,CODE_C011("C011")
	/**
	 * 请输入其他号码
	 */
	,CODE_C012("C012")
	/**
	 * 该公司已被邀请
	 */
	,CODE_C013("C013")
	/**
	 * 做任务可赢得抽奖机会
	 */
	,CODE_C014("C014")
	/**
	 * 接口更新失败
	 */
	,CODE_D001("D001")
	/**
	 * 该账户不支持此操作
	 */
	,CODE_W001("W001")
	/**
	 * 请求CRM失败
	 */
	,CODE_W002("W002");

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
		case "C000":
			return RestCode.CODE_C000;	
		case "0001":
			return RestCode.CODE_0001;
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
		case "M000":
			return RestCode.CODE_M000;	
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
		case "M0011":
			return RestCode.CODE_M0011;
		case "M0012":
			return RestCode.CODE_M0012;
		case "M0013":
			return RestCode.CODE_M0013;
		case "M0014":
			return RestCode.CODE_M0014;
		case "M0015":
			return RestCode.CODE_M0015;
		case "M0016":
			return RestCode.CODE_M0016;
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
		case "L008":
			return RestCode.CODE_L008;		
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
		case "O0010":
			return RestCode.CODE_O0010;
		case "O0011":
			return RestCode.CODE_O0011;
		case "O0012":
			return RestCode.CODE_O0012;
		case "O0013":
			return RestCode.CODE_O0013;
		case "O0014":
			return RestCode.CODE_O0014;
		case "O0015":
			return RestCode.CODE_O0015;
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
		case "B006":
			return RestCode.CODE_B006;	
		case "L000":
			return RestCode.CODE_L000;
		case "C002":
			return RestCode.CODE_C002;
		case "C003":
			return RestCode.CODE_C003;
		case "C004":
			return RestCode.CODE_C004;	
		case "C005":
			return RestCode.CODE_C005;
		case "Y001":
			return RestCode.CODE_Y001;		
		case "G001":
			return RestCode.CODE_G001;
		case "G002":
			return RestCode.CODE_G002;
		case "G003":
			return RestCode.CODE_G003;
		case "G000":
			return RestCode.CODE_G000;	
		case "I001":
			return RestCode.CODE_I001;
		case "U000":
			return RestCode.CODE_U000;	
		case "I002":
			return RestCode.CODE_I002;
		case "D001":
			return RestCode.CODE_D001;
		case "W001":
			return RestCode.CODE_W001;
		case "W002":
			return RestCode.CODE_W002;
		case "C011":
			return RestCode.CODE_C011;	
		case "C012":
			return RestCode.CODE_C012;	
		case "C013":
			return RestCode.CODE_C013;	
		case "C014":
			return RestCode.CODE_C014;	
			
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
		case "C000":
			return "已操作成功,请刷新查看";	
		case "0001":
			return "超级管理员不可修改";
		case "A000":
			return "返回字段属性为空";
		case "A001":
			return "返回字段值为空";
		case "A002":
			return "返回字段值不正确";
		case "A003":
			return "店铺营业中无法请求";
		case "A004":
			return "返回请求失败";
		case "A005":
			return "库存异常";
		case "A006":
			return "店铺暂未营业";
		case "A007":
			return "店铺不存在,供应商未审核通过";
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
			return "用户未启用";
		case "S999":
			return "请求错误";
		case "M001":
			return "用户不存在";
		case "M000":
			return "用户已被锁定,无法出价";	
		case "M002":
			return "登录密码错误";
		case "M003":
			return "用户已被登录";
		case "M004":
			return "用户支付密码错误";
		case "M005":
			return "名称已存在";
		case "M006":
			return "用户审核未通过";
		case "M007":
			return "请选择采购商";
		case "M008":
			return "会员未绑定公司";
		case "M009":
			return "发票不存在";
		case "M0010":
			return "发票名称不存在";
		case "M0011":
			return "未添加该成员";
		case "M0012":
			return "已存在,不可重复提交";
		case "M0013":
			return "组长不能为自己";
		case "M0014":
			return "工号不能重复";
		case "M0015":
			return "竞拍中，不可重复提交";
		case "M0016":
			return "竞拍日期不可早于当日";
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
			return "额度不足";
		case "L007":
			return "采购商白条未申请";
		case "L008":
			return "供应商未绑定实体账户";	
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
			return "品名不存在";
		case "O0010":
			return "该采购商已匹配";
		case "O0011":
			return "业务员已有其他类型匹配,不可匹配该类型";
		case "O0012":
			return "订单已被取消";
		case "O0013":
			return "订单已审核";
		case "O0014":
			return "订单分货数量不能为0";
		case "O0015":
			return "子订单号不存在";
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
		case "B006":
			return "银行返回数据异常";		
		case "L000":
			return "物流模板已存在";
		case "C002":
			return "公司名称不可重复";
		case "C003":
			return "公司营业执照号已被注册";
		case "C004":
			return "公司已审核通过";
		case "C005":
			return "公司不存在";
		case "Y001":
			return "已添加";	
		case "G001":
			return "商品不存在";
		case "G002":
			return "商品价格为0";
		case "G003":
			return "商品箱重为0";
		case "G000":
			return "商品价格不能为0";
		case "U000":
			return "竞拍活动已结束";	
		case "I001":
			return "接口更新中";
		case "I002":
			return "接口更新失败";
		case "D001":
			return "最大重量必须大于起始重量";
		case "W001":
			return "该账户不支持此操作";
		case "W002":
			return "请求CRM失败";
		case "C011":
			return "优惠券不存在";	
		case "C012":
			return "请输入其他手机号码";	
		case "C013":
			return "该公司已被邀请";	
		case "C014":
			return "您的抽奖次数已用完";	
			
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

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
