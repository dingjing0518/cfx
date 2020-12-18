package cn.cf.constant;

public enum SmsCode {
	/**
	 * 发送验证码
	 */
	SEND_CODE("verification"),
	/**
	 * 通知采购商已到款(采购商)
	 */
	CONF_PAY("confirm_paymen"),
	/**
	 * 解卡成功（供应商）
	 */
	DEL_CARD_SP("unbundling_card_ToSup"),
	/**
	 * 更改开户手机（供应商）
	 */
	ED_MOB_SP("change_mobile _ToSup"),
	/**
	 * 虚拟账号申请成功（供应商）
	 */
	AD_VIR_SP("virtualaccount_suc_ToSup"),
	/**
	 * 新-授信不通过
	 */
	NO_CREDIT("credit_apply_n"),
	/**
	 * 绑卡成功（供应商）
	 */
	AD_CARD_SP("binding_card_ToSup"),
	/**
	 * 提现成功
	 */
	CASH_SUCCESS("cash_dividends_ToSup"),
	/**
	 * 关注活动-活动开始前
	 */
	AU_ACTIVITY("auction_activities"),
	/**
	 * 竞拍淘汰
	 */
	EL_AUCTION("auction_eliminate"),
	/**
	 * 开店时间提醒
	 */
	REM_OPEN_STORE("store_reminding"),
	/**
	 * 竞拍商品中标
	 */
	BID_AUCTION("auction_bid"),
	/**
	 * 邀请好友开通白条
	 */
	INV_FRD_CREDIT("inviting_friends_open_credit"),
	/**
	 * 抽奖活动通知
	 */
	NOT_ACTIVITY("activity_notify"),
	/**
	 * 订单发货时(采购商)
	 */
	ORDER_DEL_PU("order_delivery_topu"),
	/**
	 * 新-会员找回密码
	 */
	FIND_PWD("find_password"),
	/**
	 * 入驻
	 */
	SETTLED("settled"),
	/**
	 * 新-订单创建时(供应商)
	 */
	ORDER_AD_SP("order_creation_tosu"),
	/**
	 * 新-订单作废(供应商)
	 */
	ORDER_CAL_SP("order_cancellation_tosu"),
	/**
	 * 新-订单创建(采购商)
	 */
	ORDER_AD_PU("order_creation_topu"),
	/**
	 * 新-供应商入驻审核失败
	 */
	FAIL_SATTLE("supplier_audit_failure"),
	/**
	 * 新-上传付款凭证(线下支付)(供应商)
	 */
	UP_VOUCHER("upload_payment_voucher"),
	/**
	 * 会员认证通过
	 */
	PASS_MEMBER("certification_passed"),
	/**
	 * 新-订单作废(采购商)
	 */
	ORDER_CAL_PU("order_cancellation_topu"),
	/**
	 * 审核不通过(采购商)
	 */
	NO_PASS_PU("audit_not_through"),
	/**
	 * 付款成功（通知采购商）
	 */
	PAY_SUCCESS_PU("payment_topurchaser"),
	/**
	 * 确定收货(提醒供应商)
	 */
	ORDER_RECE_SP("confirm_receipt"),
	/**
	 * 修改价格（提醒采购商）
	 */
	ORDER_ED_PRICE_PU("modify_price"),
	/**
	 * 申请授信中
	 */
	AD_CREDIT("credit_apply"),
	/**
	 * 平台采(提醒采购商)
	 */
	ORDER_PLAT_PU("platform_Mining"),
	/**
	 * 还款成功
	 */
	REPAY_SUCCESS("repayment_success"),
	/**
	 * 用户删除，提醒用户和公司解绑
	 */
	UN_COMPANY("hire_unbundling"),
	/**
	 * 提醒用户审核中
	 */
	AUD_MEMBER("user_audit"),
	/**
	 * 授信额度通过
	 */
	CREDIT_PASS("credit_apply_p"),
	/**
	 * 金融产品额度启用通知
	 */
	CREDIT_Notification("enable_notification"),
	/**
	 * 付款成功(通知供应商入账一笔)
	 */
	PAY_SUCCESS_SP("payment_tosupplier"),
	/**
	 * 还款提醒
	 */
	REM_REAPY("repayment_reminder"),
	
	/**
	 * 还款提醒
	 */
	REM_REAPY_NEW("repayment_reminder_new"),
	/**
	 * 代采
	 */
	MINING("mining"),
	/**
	 * 绑卡成功
	 */
	AD_CARD_PU("binding_card"),
	/**
	 * 会员注册完成
	 */
	REGISTER("register"),
	/**
	 * 订单部分发货时
	 */
	SOME_DEL_PU("someitems_delivery_topu"),
	/**
	 * 新-会员更改密码时
	 */
	ED_PWD("change_password"),
	/**
	 * 虚拟账号申请成功
	 */
	AD_VIR_SUCCESS("virtualaccount_suc"),
	/**
	 * 解卡成功
	 */
	DEL_CARD_PU("unbundling_card"),
	/**
	 * 提现成功
	 */
	CASH_SUCCESS_PU("cash_dividends"),
	/**
	 * 部分商品确定收货(提醒供应商)
	 */
	ORDER_CONF_SP("someitems_confirm_receipt"),
	/**
	 * 支付密码修改成功
	 */
	ED_PWD_SUCCESS("change_payment_password"),
	/**
	 * 议价通知（供应商）
	 */
	BARG_SP("bargaining_apply"),
	/**
	 * 更改开户手机
	 */
	ED_MOBILE("change_mobile "),
	/**
	 * 添加用户
	 */
	AD_MEMBER("addUser"),
	/**
	 * 授信通过
	 */
	CREDIT_SUCCESS("credit_audit_pass"),
	/**
	 * 设置管理员
	 */
	ED_MANAGER("setup_manager"),
	/**
	 * 授信即将过期
	 */
	CREDIT_EXP("credit_expiring"),
	/**
	 * C2B求购审核未通过
	 */
	MARR_NO_PASS("audit_failed_wanted"),
	/**
	 * C2B未中标
	 */
	MARR_FAIL_BID("bid_failed"),
	/**
	 * C2B求购审核通过
	 */
	MARR_PASS("audit_wanted"),
	/**
	 * C2B报价（供应商自己报价）
	 */
	MARR_QUO("quotation"),
	/**
	 * C2B中标
	 */
	MARR_BID("bid"),
	/**
	 * C2B收到报价
	 */
	MARR_RE_QUO("receive_quotation"),
	/**
	 *C2B报价 (交易专员帮客户报价) 
	 */
	MARR_PLAT_QUO("platform_quote"),
	/**
	 * 需求匹配推送
	 */
	MARR_DEM_MATCH("demand_matching"),
	/**
	 * 供应商对采购商进行报价
	 */
	MARR_QUO_PRICE("quoted_price"),
	/**
	 * 订单派车
	 */
	LG_ASSIGN("assignVehicle"),
	/**
	 * 配送中
	 */
	LG_DELIVERY("comfirmDelivery"),
	/**
	 *接收订单
	 */
	LG_RECIVE("reavice_delivery"),
	/**
	 * 手机获取验证码前缀 如："code18829394444"根据此标识获取验证码
	 */
	SMS_CODE("code"),
	/**
	 * 金融产品额度启用通知
	 */
 ECON_NOTIFICATION("enable_notification");
	

	private String value;
	
	private SmsCode(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
