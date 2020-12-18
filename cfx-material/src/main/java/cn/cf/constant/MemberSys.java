package cn.cf.constant;

public enum MemberSys {

	// 账户维度
	ACCOUNT_DIMEN("1"),
	// 账户维度-成功注册采购商
	ACCOUNT_DIMEN_PURCHER("1_1"),
	// 账户维度-完善发票信息
	ACCOUNT_DIMEN_INV("1_2"),
	// 账户维度-完善收货地址
	ACCOUNT_DIMEN_ADDR("1_3"),
	// 账户维度-完善公司logo
	ACCOUNT_DIMEN_LOG("1_4"),
	// 账户维度-开通白条 （已经开启授信额度）
	ACCOUNT_DIMEN_BLNOTE("1_5"),
	// 账户维度-首次添加人员
	ACCOUNT_DIMEN_ADDME("1_6"),
	// 账户维度-成功入驻供应商
	ACCOUNT_DIMEN_SUPPLIER("1_7"),
	// 账户维度-添加物流模板
	ACCOUNT_DIMEN_LGTEP("1_8"),
	// 账户维度-完善生产厂区
	ACCOUNT_DIMEN_PLANT("1_9"),
	// 账户维度-完善仓库信息
	ACCOUNT_DIMEN_WARE("1_10"),
	// 账户维度-添加子公司
	ACCOUNT_DIMEN_CHICOM("1_11"),
	// 账户维度-开通化纤贷（已经开启授信额度）
	ACCOUNT_DIMEN_FIBER("1_12"),
	// 账户维度-首次添加商家品牌
	ACCOUNT_DIMEN_GOODBRAND("1_13"),
	ACCOUNT_DIMEN_CUMADDME("1_14"),
	

	// 交易维度-白条订单
	TRADE_DIMEN_BLNOTE("2_1"),
	// 交易维度-线下支付
	TRADE_DIMEN_DOWLINE("2_2"),
	// 交易维度-求购订单
	TRADE_DIMEN_MARRORDER("2_3"),
	// 交易维度-竞拍订单
	TRADE_DIMEN_ACTAORDER("2_4"),
	// 交易维度-除POY之外其他品名订单
	TRADE_DIMEN_NOTPOY_ORDER("2_5"),
	// 交易维度-POY订单
	TRADE_DIMEN_POYORDER("2_6"),
	// 交易维度-WAP订单
	TRADE_DIMEN_WAPORDER("2_7"),
	// 交易维度-APP订单
	TRADE_DIMEN_APPORDER("2_8"),
	// 交易维度-在线支付
	TRADE_DIMEN_ONLNORDER("2_9"),
	// 交易维度-化纤贷支付
	TRADE_DIMEN_FIBER_PAY("2_10"),

	// 求购维度-发布求购
	MARRI_DIMEN_PUBLISH_MARR("3_1"),
	// 求购维度-确定中标
	MARRI_DIMEN_SURE_BIDDING("3_2"),
	// 求购维度-报价
	MARRI_DIMEN_QUOTE("3_3"),

	// 竞拍维度-竞拍出价
	ACTION_DIMEN_ACTQUOTE("4_1"),
	// 竞拍维度-上架竞拍商品（首次）
	ACTION_DIMEN_FIRST_UPGOODS("4_2"),
	// 竞拍维度-确认中标
	ACTION_DIMEN_SURE_BIDDING("4_3"),

	// 收藏维度-收藏商品
	STORE_DIMEN_GOODS("5_1"),
	// 收藏维度-收藏店铺
	STORE_DIMEN_STORE("5_2"),
	// 流量维度-每日登录
	FLOW_DIMEN_PER_LOGIN("6_1"),
	// 商品维度
	GOODS_DIMEN("7"),
	// 商品维度-更新商品
	GOODS_DIMEN_UPDATE_GOODS("7_1");
	
	
	
	

	private String value;

	private MemberSys(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
