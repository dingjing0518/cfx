package cn.cf.common;

import cn.cf.json.JsonUtils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RestCode {
	/**
	 * 成功返回
	 */
	CODE_0000("0000")
	/**
	 * 异常返回
	 */
	, CODE_S999("S999")
	/**
	 * token失效
	 */
	, CODE_S001("S001")
	/**
	 * 签名错误
	 */
	, CODE_S002("S002")
	/**
	 * session失效
	 */
	, CODE_S003("S003")
	/**
	 * 必填参数不能为空
	 */
	, CODE_A001("A001")
	/**
	 * 返回某个字段值不正确
	 */
	, CODE_A002("A002")
	/**
	 * 返回某个字段值为空
	 */
	, CODE_A004("A004")
	/**
	 * 店铺不存在,供应商未审核通过
	 */
	, CODE_A007("A007")
	/**
	 * 超级管理员不可修改
	 */
	, CODE_M000("M000")
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
	 * 用户已登录
	 */
	, CODE_M006("M006")
	/**
	 * 用户审核未通过
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
	 * 公司不存在
	 */
	, CODE_C001("C001")
	/**
	 * 公司未启用
	 */
	, CODE_C002("C002")
	/**
	 * 组织机构代码重复
	 */
	, CODE_C003("C003")
	/**
	 * 店铺未营业
	 */
	, CODE_C000("C000")
	/**
	 * 店铺不存在
	 */
	, CODE_C005("C005")
	/**
	 * 公司名称已存在
	 */
	, CODE_C006("C006")
	/**
	 * 公司已添加
	 */
	, CODE_C010("C010")
	/**
	 * 做任务可赢得抽奖机会
	 */
	, CODE_C014("C014")
	/**
	 * 已抽奖
	 */
	, CODE_C019("C019")
	/**
	 * 不可申请返款
	 */
	, CODE_C015("C015")
	/**
	 * 已申请， 不可重复提交
	 */
	, CODE_C016("C016")
	/**
	 * 已返款， 不可重复提交
	 */
	, CODE_C017("C017")
	/**
	 * 自定义输出内容
	 */
	, CODE_Z000("Z000")
	/**
	 * 商品价格不为0
	 */
	, CODE_G000("G000")
	/**
	 * 商品已存在
	 */
	, CODE_G001("G001")
	/**
	 * 竞拍中，不可重复提交
	 */
	, CODE_G002("G002")
	/**
	 * 商品已收藏
	 */
	, CODE_G005("G005")
	/**
	 * 自定义返回内容
	 */
	, CODE_B005("B005")
	/**
	 * 品牌已存在
	 */
	, CODE_B001("B001")
	/**
	 * 商品已下架
	 */
	, CODE_G003("G003")
	/**
	 * 商品库存不足
	 */
	, CODE_G004("G004")
	/**
	 * 收货地址已存在
	 */
	, CODE_D001("D001")
	/**
	 * 收货地址不存在
	 */
	, CODE_D002("D002")
	/**
	 * 竞拍中，不可重复提交
	 */
	, CODE_G006("G006")
	/**
	 * 商品箱重不能为'0'
	 */
	, CODE_G007("G007")
	/**
	 * 库存异常
	 */
	, CODE_G008("G008")
	/**
	 * 库存异常
	 */
	, CODE_G009("G009")
	/**
	 * 竞拍活动进行中的商品无法操作
	 */
	, CODE_G010("G010")
	/**
	 * 厂区已存在
	 */
	, CODE_P001("P001"),
	/**
	 * 仓库已存在
	 */
	CODE_W001("W001"),
	/**
	 * 发票不存在
	 */
	CODE_I001("I001"),
	/**
	 * 物流方式不存在
	 */
	CODE_L001("L001"),
	/**
	 * 物流方式不存在
	 */
	CODE_L002("L002"),
	CODE_L003("L003"),
	/**
	 * 不能发布重复需求
	 */
	CODE_O008("O008"),
		/**
	 * 库位号，产品类型已存在
	 */
	CODE_O009("O009")
	/**
	 * 不满足业务要求
	 */
	, CODE_O005("O005")
	/**
	 * 活动已结束
	 */
	, CODE_C018("C018")
	/**
	 * 订单号不存在
	 */
	, CODE_O001("O001")
	/**
	 * 订单已取消
	 */
	, CODE_O010("O010")
	/**
	 * 请输入其他号码
	 */
	, CODE_C012("C012")
	/**
	 * 该公司已被邀请
	 */
	, CODE_C013("C013")
	/**
	 * 该公司已经申请白条，不能进行提交
	 */
	, CODE_C020("C020")
	/**
	 * 不可以邀请自己的公司
	 */
	, CODE_C021("C021")
	/**
	 * 地址已失效
	 */
	, CODE_C022("C022")
	/**
	 * 请维护地址
	 */
	, CODE_C023("C023")
	/**
	 * 订单已操作
	 */
	, CODE_O000("O000")
	/**
	 * 分货总量必须大于0
	 */
	, CODE_O003("O003")
	/**
	 * 订单状态限制此操作
	 */
	, CODE_O002("O002")
	/**
	 * 竞拍活动已结束
	 */
	, CODE_U000("U000")
	/**
	 * 承运商已存在
	 */
	, CODE_LC001("LC001")
	/**
	 * 承运商不存在
	 */
	, CODE_LC002("LC002")
	/**
	 * 承运商已被禁用
	 */
	, CODE_LC004("LC004")
	/**
	 * 线路名称已存在
	 */
	, CODE_LC003("LC003")
	/**
	 * 承运商同步失败
	 */
	, CODE_I002("I002")
	/**
	 * 排序必须大于0
	 */
	, CODE_P002("P002")
	/**
	 * 场次已存在
	 */
	, CODE_P003("P003")
	/**
	 * 日期格式错误
	 */
	, CODE_P004("P004")
	/**
	 * 竞拍量不能低于最低起拍量
	 */
	, CODE_P005("P005")
	/**
	 * 参数类型异常
	 */
	, CODE_P006("P006")
	/**
	 * 出价不能低于当前商品的起拍价
	 */
	, CODE_P007("P007")
	/**
	 * 竞拍开始时间不能小于当前时间
	 */
	, CODE_P008("P008")
	/**
	 * 采购量不能大于库存量
	 */
	, CODE_P009("P009")
	/**
	 * 正在进行中商品不可以设置成正常商品
	 */
	, CODE_P010("P010")
	/**
	 * 最低起拍量不能高于库存量
	 */
	, CODE_P011("P011")
	/**
	 * 拼团活动不存在
	 */
	, CODE_P012("P012")
	/**
	 * 拼团活动已下架
	 */
	, CODE_P013("P013")
	/**
	 * 商品不存在
	 */
	, CODE_P014("P014")
	/**
	 * 竞拍商品不可参加团购
	 */
	, CODE_P015("P015")
	/**
	 * 商品已参加 团购
	 */
	, CODE_P016("P016")
	/**
	 * 参数异常
	 */
	, CODE_P017("P017")
	/**
	 * 正在参加拼团的商品不可以设置为正常商品
	 */
	, CODE_P018("P018")
	/**
	 * 上架的拼团活动中的商品不可以更换拼团活动
	 */
	, CODE_P019("P019")
	/**
	 * 该活动没有商品
	 */
	, CODE_P020("P020")
	/**
	 * 该商品正处于上架的拼团活动中，不可以修改
	 */
	, CODE_P021("P021")
	/**
	 * 该拼团活动正在上架中，不可添加商品
	 */
	, CODE_P022("P022")
	/**
	 * 团购活动正在上架中
	 */
	, CODE_P023("P023")
	/**
	 * 活动内有商品没有库存
	 */
	, CODE_P024("P024")
	/**
	 * 团购活动不存在
	 */
	, CODE_P025("P025")
	/**
	 * 竞拍订单已提交
	 */
	, CODE_P026("P026")
	/**
	 * 该商品已经在竞拍中
	 */
	, CODE_G013("G013")
	/**
	 * 竞拍场次正在进行中
	 */
	, CODE_J001("J001")
	/**
	 * 竞拍商品还未设置场次
	 */
	, CODE_J002("J002")
	/**
	 * 竞拍场次已结束
	 */
	, CODE_J003("J003")
	/**
	 * 竞拍开始时间不能小于当前时间
	 */
	, CODE_G011("G011")
	/**
	 * 该商品正在竞拍中，不能修改
	 */
	, CODE_G012("G012")
	/**
	 * 拼团商品中商品无法操作上下架 
	 */
	, CODE_G014("G014")
	
	/**
	 * 商品价格必须大于0 
	 */
	, CODE_G015("G015")
	/**
	 * 运费价格不能小于0 
	 */
	, CODE_G016("G016")
	/**
	 * 请输入正整数
	 */
	, CODE_G017("G017")
	/**
	 * 箱重异常
	 */
	, CODE_G018("G018")
	/**
	 * 竞拍价格异常
	 */
	, CODE_G019("G019");
	/**
	 * 枚举对象整型值
	 */
	private String code;
	private String msg;
	private String defaultValue;

	/**
	 * 构造方法
	 * <p>
	 * 枚举对象整型值
	 */
	private RestCode(String code) {
		this.code = code;
		this.msg = this.toString();
	}

	/**
	 * 获取枚举对象对应的整型值
	 * 
	 * @return
	 * @author 赵旺 @2010-8-29
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
	 * @param str
	 *            被转换的String对象
	 * @return 若构造失败，返回null
	 * @author 赵旺 @2010-8-29
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
	 * @param val
	 *            枚举对象整型值
	 * @return 若没有匹配的整型值，则返回null
	 * @author 赵旺 @2010-8-29
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
		case "S001":
			return RestCode.CODE_S001;
		case "S002":
			return RestCode.CODE_S002;
		case "S003":
			return RestCode.CODE_S003;
		case "A001":
			return RestCode.CODE_A001;
		case "A002":
			return RestCode.CODE_A002;
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
		case "C001":
			return RestCode.CODE_C001;
		case "C002":
			return RestCode.CODE_C002;
		case "C003":
			return RestCode.CODE_C003;
		case "C000":
			return RestCode.CODE_C000;
		case "C005":
			return RestCode.CODE_C005;
		case "C006":
			return RestCode.CODE_C006;
		case "C010":
			return RestCode.CODE_C010;
		case "C014":
			return RestCode.CODE_C014;
		case "C019":
			return RestCode.CODE_C019;
		case "C015":
			return RestCode.CODE_C015;
		case "C016":
			return RestCode.CODE_C016;
		case "C017":
			return RestCode.CODE_C017;
		case "Z000":
			return RestCode.CODE_Z000;
		case "G000":
			return RestCode.CODE_G000;
		case "G001":
			return RestCode.CODE_G001;
		case "G002":
			return RestCode.CODE_G002;
		case "G005":
			return RestCode.CODE_G005;
		case "B005":
			return RestCode.CODE_B005;
		case "B001":
			return RestCode.CODE_B001;
		case "G003":
			return RestCode.CODE_G003;
		case "G004":
			return RestCode.CODE_G004;
		case "G006":
			return RestCode.CODE_G006;
		case "G007":
			return RestCode.CODE_G007;
		case "G008":
			return RestCode.CODE_G008;
		case "G009":
			return RestCode.CODE_G009;
		case "G010":
			return RestCode.CODE_G010;
		case "D001":
			return RestCode.CODE_D001;
		case "D002":
			return RestCode.CODE_D002;
		case "P001":
			return RestCode.CODE_P001;
		case "W001":
			return RestCode.CODE_W001;
		case "I001":
			return RestCode.CODE_I001;
		case "L001":
			return RestCode.CODE_L001;
		case "L002":
			return RestCode.CODE_L002;
		case "L003":
			return RestCode.CODE_L003;
		case "O008": 
			return RestCode.CODE_O008;
		case "O009": 
			return RestCode.CODE_O009;
		case "O010":
			return RestCode.CODE_O010;
		case "O005":
			return RestCode.CODE_O005;
		case "C018":
			return RestCode.CODE_C018;
		case "C012":
			return RestCode.CODE_C012;
		case "C013":
			return RestCode.CODE_C013;
		case "C020":
			return RestCode.CODE_C020;
		case "C021":
			return RestCode.CODE_C021;
		case "C022":
			return RestCode.CODE_C022;
		case "C023":
			return RestCode.CODE_C023;
		case "O000":
			return RestCode.CODE_O000;
		case "U001":
			return RestCode.CODE_U000;
		case "O001":
			return RestCode.CODE_O001;
		case "O002":
			return RestCode.CODE_O002;
		case "O003":
			return RestCode.CODE_O003;
		case "LC001":
			return RestCode.CODE_LC001;
		case "LC002":
			return RestCode.CODE_LC002;
		case "LC004":
			return RestCode.CODE_LC004;
		case "LC003":
			return RestCode.CODE_LC003;
		case "I002":
			return RestCode.CODE_I002;
		case "P002":
			return RestCode.CODE_P002;
		case "P003":
			return RestCode.CODE_P003;
		case "P004":
			return RestCode.CODE_P004;
		case "P005":
			return RestCode.CODE_P005;
		case "P006":
			return RestCode.CODE_P006;
		case "P007":
			return RestCode.CODE_P007;
		case "P008":
			return RestCode.CODE_P008;
		case "P009":
			return RestCode.CODE_P009;
		case "P010":
			return RestCode.CODE_P010;
		case "P011":
			return RestCode.CODE_P011;
		case "P012":
			return RestCode.CODE_P012;
		case "P013":
			return RestCode.CODE_P013;
		case "P014":
			return RestCode.CODE_P014;
		case "P015":
			return RestCode.CODE_P015;
		case "P016":
			return RestCode.CODE_P016;
		case "P017":
			return RestCode.CODE_P017;
		case "P018":
			return RestCode.CODE_P018;
		case "P019":
			return RestCode.CODE_P019;
		case "P020":
			return RestCode.CODE_P020;
		case "P021":
			return RestCode.CODE_P021;
		case "P022":
			return RestCode.CODE_P022;
		case "P023":
			return RestCode.CODE_P023;
		case "P024":
			return RestCode.CODE_P024;
		case "P025":
			return RestCode.CODE_P025;
		case "P026":
			return RestCode.CODE_P026;
		case "G013":
			return RestCode.CODE_G013;
		case "J001":
			return RestCode.CODE_J001;
		case "J002":
			return RestCode.CODE_J002;
		case "J003":
			return RestCode.CODE_J003;
		case "G011":
			return RestCode.CODE_G011;
		case "G012":
			return RestCode.CODE_G012;
		case "G014":
			return RestCode.CODE_G014;
		case "G015":
			return RestCode.CODE_G015;
		case "G016":
			return RestCode.CODE_G016;
		case "G017":
			return RestCode.CODE_G017;
		case "G018":
			return RestCode.CODE_G018;
		case "G019":
			return RestCode.CODE_G019;
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
		case "S001":
			return "TOKEN失效";
		case "S002":
			return "签名错误";
		case "S003":
			return "SESSION失效";
		case "A001":
			return "必填参数不能为空";
		case "A002":
			return "返回某字段不正确";
		case "A003":
			return "返回店铺营业中无法请求";
		case "A004":
			return "返回请求失败";
		case "A005":
			return "库存异常";
		case "A006":
			return "店铺暂未营业";
		case "A007":
			return "店铺不存在,供应商未审核通过";
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
		case "C001":
			return "公司不存在";
		case "C002":
			return "公司未启用";
		case "C003":
			return "营业执照号重复";
		case "C000":
			return "店铺未营业";
		case "C005":
			return "店铺不存在";
		case "C010":
			return "客户已添加";
		case "C014":
			return "做任务可赢得抽奖机会";
		case "C019":
			return "已抽奖";
		case "C015":
			return "不可申请返款";
		case "C016":
			return "已申请，不可重复提交";
		case "C017":
			return "已返款，不可重复提交";
		case "Z000":
			return getMsg();
		case "B005":
			return getMsg();
		case "B001":
			return "品牌已存在";
		case "G000":
			return "商品价格不能为零";
		case "G001":
			return "商品已存在";
		case "G002":
			return "竞拍中，不可重复提交";
		case "G005":
			return "商品已收藏";
		case "G003":
			return "商品已下架";
		case "G004":
			return "商品库存不足";
		case "G006":
			return "竞拍中，不可重复提交";
		case "G007":
			return "商品箱重不能为0";
		case "G008":
			return "库存异常,请稍后重试";
		case "G009":
			return "商品数量必须大于0";
		case "G010":
			return "竞拍活动进行中的商品无法操作";
		case "D001":
			return "收货地址已存在";
		case "D002":
			return "收货地址不存在";
		case "P001":
			return "厂区已存在";
		case "W001":
			return "仓库已存在";
		case "I001":
			return "发票不存在";
		case "L001":
			return "物流方式不存在";
		case "L002":
			return "物流模板已存在";
		case "L003":
			return "该泡货已存在";
		case "O008":
			return "不能发布重复需求";
		case "O009":
			return "库位号，产品类型已存在";
		case "O005":
			return "不满足业务要求";
		case "C018":
			return "活动已结束";
		case "C012":
			return "请输入其他手机号码";
		case "C013":
			return "该公司已被邀请";
		case "C020":
			return "该公司已经申请白条，不能进行提交";
		case "C021":
			return "不可以邀请自己的公司";
		case "C022":
			return "地址已失效";
		case "C023":
			return "请维护地址";
		case "O000":
			return "订单已操作";
		case "U001":
			return "竞拍活动已结束";
		case "O001":
			return "订单不存在";
		case "O010":
			return "订单已取消,无法操作";
		case "O002":
			return "该订单状态下无法进行此操作,请刷新重试";
		case "O003":
			return "分货总量必须大于0";
		case "C006":
			return "公司名称已存在";
		case "LC001":
			return "承运商已存在";
		case "LC002":
			return "承运商不存在";
		case "LC004":
			return "承运商已被禁用";
		case "LC003":
			return "线路名称已存在";
		case "I002":
			return "承运商同步失败";
		case "P002":
			return "排序必须大于0";
		case "P003":
			return "场次已存在";
		case "P004":
			return "日期格式错误";
		case "P005":
			return "竞拍量不能低于最低起拍量";
		case "P006":
			return "参数类型异常";
		case "P007":
			return "出价不能低于当前商品的起拍价";
		case "P008":
			return "竞拍开始时间不能小于当前时间";
		case "P009":
			return "采购量不能大于库存量";
		case "P010":
			return "正在进行中商品不可以设置成正常商品";
		case "P011":
			return "最低起拍量不能高于库存量";
		case "P012":
			return "拼团活动不存在";
		case "P013":
			return "拼团活动已下架";
		case "P014":
			return "商品不存在";
		case "P015":
			return "竞拍商品不可以参加团购";
		case "P016":
			return "商品已参加团购";
		case "P017":
			return "参数异常";
		case "P018":
			return "正在参加拼团的商品不可以设置为正常商品";
		case "P019":
			return "上架的拼团活动下的商品不可以更换拼团活动";
		case "P020":
			return "该活动没有商品";
		case "P021":
			return "该商品正处于上架的拼团活动中，不可以修改";
		case "P022":
			return "该拼团活动正在上架中，不可添加商品";
		case "P023":
			return "团购活动正在上架中";
		case "P024":
			return "活动内有商品没有库存";
		case "P025":
			return "团购活动不存在";
		case "P026":
			return "竞拍订单已提交";
		case "U000":
			return "竞拍活动已结束";
		case "G013":
			return "该商品已经在竞拍中";
		case "J001":
			return "竞拍场次正在进行中";
		case "J002":
			return "竞拍商品还未设置场次";
		case "J003":
			return "竞拍场次已结束";
		case "G011":
			return "竞拍开始时间不能小于当前时间";
		case "G012":
			return "该商品正在竞拍中，不能修改";
		case "G014":
			return "拼团中的商品,无法操作";
		case "G015":
			return "商品价格必须大于0";
		case "G016":
			return "运费价格不能小于0";
		case "G017":
			return "请输入正整数";
		case "G018":
			return "箱重异常";
		case "G019":
			return "竞拍价格异常";
		default:
			return "";
		}
	}

	/**
	 * 获取代码字符串
	 * 
	 * @return 代码字符串， 即"INACTIVE"等，对应页面上映射的codeText
	 * @author 赵旺 @2010-8-29
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
