package cn.cf.common;

/**
 * 鐢悂鍣洪崐锟� * @author sea
 * 
 */
public class Constants {

	/**
	 * 项目名
	 */

	/**
	 * 用户session有效时间（ 默认7天7*24*60*60,单位为FNE）
	 */
	public static final int USER_SESSION_TIME_OUT = 3 * 24 * 60;

	public static final int ONE = 1;

	public static final int TWO = 2;

	public static final int THREE = 3;

	public static final int FOUR = 4;

	public static final int FIVE = 5;

	public static final int SIX = 6;

	public static final int SEVEN = 7;

	public static final int EIGHT = 8;

	public static final int TEN = 10;
	
	public static final int MINUS_ONE = -1;
	
	public static final int MINUS_TWO = -2;
	
	public static final int ZERO = 0;

	// 跳转地址
	/********************************* 上传图片常用变量 begin ***************************************************/

	public static final int IMG = 1; // 图片
	public static final int TXT = 2; // 文本
	public static final int VIDEO = 3; // 视频
	public static final int AUDIO = 4; // 音频
	public static final int XLS = 5; // 表格
	public static final int FILE=6;
	public static final int JSON=7;
	public static final int CSV=8;
	public static final int TEMP = 999; // 上传图片测试

	public static final int PICTURE_SIZE = 2 * 1024 * 1024; // 图片限制大小(2m左右)
	public static final int TXT_SIZE = 5 * 1024 * 1024; // 文本限制大小(5M左右)
	public static final int VIDEO_SIZE = 10 * 1024 * 1024; // 视频限制大小(10M左右)
	public static final int AUDIO_SIZE = 10 * 1024 * 1024; // 音频限制大小(10M左右)
	public static final int XLS_SIZE = 5 * 1024 * 1024; // 表格限制大小(5M左右)
	public static final int FILE_SIZE = 10 * 1024 * 1024; // 表格限制大小(10M左右)
	public static final int CSV_SIZE = 5 * 1024 * 1024; // CSV限制大小(5M左右)


	// 这个目录主要存用户头像
	public static String FILE_IMG = "img/";
	// 这个目录主要存用户视频
	public static String FILE_VIDEO = "video/";
	// 这个目录主要存用户TXT
	public static String FILE_TXT = "txt/";
	// 这个目录主要存用户AUDIO
	public static String FILE_AUDIO = "audio/";
	// 这个目录主要存用户xls
	public static String FILE_XLS = "xls/";
	// 这个目录主要存用户csv
	public static String FILE_CSV = "csv/";
	// 这个目录主要存用户file
		public static String FILE_FILE = "file/";
	// 上传文件test目录
	public static String FILE_TEMP = "temp/";
	// 上传文件到根目录
	public static String ROOT_FILE = "";


	public static String IS_ONLINE_OSS_PATH = "yes";
	/********************************* 上传图片常用变量 end ***************************************************/
    public static String picURL="http://huaxianhui.oss-cn-shanghai.aliyuncs.com/";
    
    /***********************订单提示信息*********************/
    public static final Integer ORDER_STATUS_002 = 2;//已取消
    public static final Integer ORDER_STATUS_003 = 3;//已签收
    public static final Integer ORDER_STATUS_004 = 4;//配送中
    public static final Integer ORDER_STATUS_005 = 5;//提货中
    public static final Integer ORDER_STATUS_006 = 6;//已匹配
    public static final Integer ORDER_STATUS_007 = 7;//未匹配
    public static final Integer ORDER_STATUS_008 = 8;//待确认
    public static final Integer ORDER_STATUS_009 = 9;//待付款
    public static final Integer ORDER_STATUS_010 = 10;//已关闭
    public static final Integer ORDER_ISABNORMAL_001 = 1;//订单正常状态
    public static final Integer ORDER_ISABNORMAL_002 = 2;//订单异常状态
    public static final String ORDER_LG_MSG_0076="订单已匹配 待指派车辆";
    public static final String ORDER_LG_MSG_0067="已取消匹配，待分派物流公司";
	public static final String ORDER_LG_MSG_0087 = "财务已确认  待分派物流公司";

	/***********************线程数*********************/
	public static final int THREAD_NUMBERS_TWRNTY = 20;
	public static final int THREAD_NUMBERS_FIVE = 5;
	public static final int THREAD_NUMBERS_TEN = 10;

	public static final int EXCEL_NUMBER_TENHOUSAND = 10000;

	public static final String RESULT_SUCCESS_MSG = "{\"success\":true,\"msg\":\"操作成功!\"}";
	
	public static final String RESULT_FAIL_MSG = "{\"success\":false,\"msg\":\"操作失败!\"}";

	public static final String RESULT_SCOREIMPORT_MSG = "{\"success\":false,\"msg\":\"分值填写有误！\"}";

	public static final String RESULT_ALLSCOREIMPORT_MSG = "{\"success\":false,\"msg\":\"总得分计算有误！\"}";

	public static final String RESULT_SCOREIMPORT_ERROR_MSG = "{\"success\":false,\"msg\":\"导入数据有问题！\"}";
	
	public static final String RESULT_FAIL_MSG_TOOLONG = "{\"success\":false,\"msg\":\"名称太长!\"}";
	
	public static final String RESULT_EXCEPTION_MSG = "{\"success\":false,\"msg\":\"操作异常!\"}";
	
	public static final String OrganizationCodeMsg = "{\"success\":false,\"msg\":\"统一社会信用代码已被其他公司注册!\"}";
	
	public static final String PRODUCT_NAME_REPEAT = "{\"success\":false,\"msg\":\"商品品名已存在,请重新定义名称!\"}";
	
	public static final String VARIETIES_NAME_REPEAT = "{\"success\":false,\"msg\":\"商品品种已存在,请重新定义名称!\"}";
	public static final String SEEDVARIETIES_NAME_REPEAT = "{\"success\":false,\"msg\":\"商品子品种已存在,请重新定义名称!\"}";
	
	
	public static final String MATERIAL_NAME_REPEAT = "{\"success\":false,\"msg\":\"原料名称已存在!\"}";
	public static final String TECHNOLOGY_NAMESHORTNAME_REPEAT = "{\"success\":false,\"msg\":\"工艺名称已存在!\"}";
	
	public static final String ISEXTIST_COMPANY_NAME = "{\"success\":false,\"msg\":\"公司名称已被注册!\"}";

	public static final String ISEXTIST_YARN_SPEC_NAME = "{\"success\":false,\"msg\":\"存在相同名称的规格!\"}";

	public static final String ISEXTIST_HOT_SPEC_NAME = "{\"success\":false,\"msg\":\"存在相同的热门规格!\"}";
	
	public static final String MINUS_STR_ONE = "-1";
	
	public static final String IS_EMPTY_ECURL= "{\"success\":false,\"msg\":\"营业执照和企业认证授权书不能为空!!\"}";
	
	public static final String IS_EMPTY_BLURL_ECURL= "{\"success\":false,\"msg\":\"营业执照和企业认证授权书不能为空!!\"}";
	
	
	public static final String GOODS_BRANDS_IS_USED = "{\"success\":false,\"msg\":\"该厂家品牌已被使用，不能删除!\"}";
	public static final String GOODS_BRANDS_IS_EXTIST_NAME = "{\"success\":false,\"msg\":\"此店铺已存在相同名称的厂家品牌!\"}";
	
	
	public static final String MOBILE_REPEAT = "{\"success\":false,\"msg\":\"手机号已被注册!\"}";
	
	public static final String EMPLOYEE_NUMBER_EXIST = "{\"success\":false,\"msg\":\"该公司下已经存在该员工号!\"}";
	
	public static final String CATEGORY_NAME_EXIST = "{\"success\":false,\"msg\":\"分类名称在对应的系统中已存在!\"}";
	
	public static final String CATEGORY_IS_CONTAINS = "{\"success\":false,\"msg\":\"当前所选位置与分类位置不一致!\"}";
	
	public static final String ORDER_UPDATE_MSG = "订单更新成功,";
	
	public static final String SUPPLIER_RECOMMED_MSG = "{\"success\":false,\"msg\":\"该供应商已经在对应平台做过推荐!\"}";
	
	public static final String ISEXIST_NAME_MSG = "{\"success\":false,\"msg\":\"已存在相同的维度!\"}";
	
	public static final String ISEXIST_NAME_DIMENTYPE_MSG = "{\"success\":false,\"msg\":\"已存在相同的维度类型!\"}";
	
	public static final String ISEXIST_NAME_REWARD_MSG = "{\"success\":false,\"msg\":\"已存在相同奖励!\"}";
	
	public static final String ISEXIST_NAME_EXTREWARD_MSG = "{\"success\":false,\"msg\":\"已存在相同额外奖励!\"}";
	
	public static final String ISEXIST_NAME_MEMBERGRADE_MSG = "{\"success\":false,\"msg\":\"已存在相同会员等级!\"}";
	
	public static final String ISEXIST_NAME_ECGOODS = "{\"success\":true,\"msg\":\"金融商品名称已存在!\"}";
	
	public static final String ACCEPTANCE_MSG = "{\"success\":false,\"msg\":\"客户已在受理中,暂时无法受理!\"}";
	public static final String ADD_ACCEPTANCE_MSG = "{\"success\":false,\"msg\":\"客户已在受理中,暂时无法提交申请!\"}";

	public static final String ONLINEPAYGOODS_MSG = "{\"success\":false,\"msg\":\"线上支付产品已经存在!\"}";

	public static final String CREDITREPORTGOODS_MSG = "{\"success\":false,\"msg\":\"征信产品已经存在!\"}";

	public static final String BILLGOODS_MSG = "{\"success\":false,\"msg\":\"存在相同名称的票据产品!\"}";


	public static final String EXPORT_MSG = "{\"success\":true,\"msg\":\"<b>导出当前数据</b><br/><br/>导出数据已形成定时任务，请在管理中心~任务管理中查看<br/>预计1个小时后处理完成。<br/><br/>\"}";
	
	
	public static final String GATEWAY_MSG = "{\"msg\":\"服务器内部错误\",\"code\":\"500\"}";
	
	public static final String IMPORT_FILE_ISEMPTY = "导入的文件为空!";
	
	public final static String bannerKey="sysbanner";
	public final static String categoryKey="syscategorys";
	public final static String recommedKey="sysrecommeds";

	
	public static final String BANK_NAME = "苏州银行股份有限公司";
	/**************完善资料类型**************/
	public static final int DATUMTYPE_01 = 1;//企业经营资料完善
	public static final int DATUMTYPE_02 = 2;//线下交易管理
	public static final int DATUMTYPE_03 = 3;//授信管理
	public static final int DATUMTYPE_04 = 4;//担保管理
	public static final int DATUMTYPE_05 = 5;//补充资料
	
	public static final String DIMENSION_DATUM_OPER = "operation";//企业经营资料完善
	
	public static final String DIMENSION_DATUM_OPER_CAP = "capacity";//产能
	public static final String DIMENSION_DATUM_OPER_EQU = "equipment";//设备状况
	public static final String DIMENSION_DATUM_OPER_OWNER = "ownership";//设备状况
	
	
	public static final String DIMENSION_DATUM_ONLINE = "online";//线上交易管理
	public static final String DIMENSION_DATUM_LIMIT = "limit";//最高限额
	
	
	public static final String DIMENSION_DATUM_CATEGORY_ORGINZE = "credit";
	public static final String DIMENSION_DATUM_ITEM_ORGINZE = "creditOrginze";
	public static final String DIMENSION_DATUM_ITEM_TOTALAMOUNT = "totalCreditAmount";
	public static final String DIMENSION_DATUM_ITEM_BANK = "commericalBank";
	public static final String DIMENSION_DATUM_ITEM_BUILD = "buildProperty";
	public static final String DIMENSION_DATUM_ITEM_NATIONBANK = "nationBank";
	
	
	
	
	public static final String DIMENSION_DATUM_CATEGORY_OFFLINE = "offline";
	
	public static final String DIMENSION_DATUM_ITEM_OFFLINE_SALES = "offlineSales";
	
	public static final String DIMENSION_DATUM_ITEM_OFFLINE_INVOICE = "offlineInvoice";
	
	public static final String DIMENSION_DATUM_ITEM_OFFLINE_SCALE = "offlineScale";
	
	
	public static final String DIMENSION_DATUM_CATEGORY_GUARNT = "guarantee";
	
	public static final String DIMENSION_DATUM_ITEM_GUARNT_TYPE = "guaranteeType";
	public static final String DIMENSION_DATUM_ITEM_GUARNT_TARGET = "guaranteeTarget";
	public static final String DIMENSION_DATUM_ITEM_GUARNT_COUNT = "guaranteeCount";
	
	/*************************************/
/**********************金融统计报表START********************************/
	
	public static final String[] ECONOMICS_REPORT_FORMS_TYPE = {"1","2","3","4","5"};
	public static final String[] ECONOMICS_REPORT_FORMS_GOODSTYPE = {"1","2","1,2","2,1"};
	
	public static final String[] ECONOMICS_REPORT_FORMS_MONTHS = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	public static final String[] ECONOMICS_CUSTOMER_TIME_LIST = {"昨日","当周","上周","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
	
	//本地测试
	public static final String[] MARKETING_SUPPLIER_STOREPK_LIST ={"1","103"};//盛虹，新凤鸣店铺Pk
	//public static final String[] MARKETING_SUPPLIER_LIST ={"ec2cb337cf211e7b8737cd30abda4fa1","8cff7781b11e7b8737cd30abda4fa103"};
	
	public static final String[] GOODS_INFO_LIST ={"varietiesName","specName","seriesName","productName","brandName","batchNumber","gradeName"};

	public static final String[] SXGOODS_INFO_LIST ={"technologyName","technologyTypeName","rawMaterialName","specifications","batchNumber","brandName"};
	
	/**********************金融统计报表END********************************/

	public static final String INDUSTRYDATA_NULL_MSG = "暂无数据";
	//采购商pk为康海

	//f9dce1e200264a44a665b9c4e5c6ad31 本地数据
	//6df544ffdb0843db8d70d112504facaa 康海数据
	public static final String TRANSACTION_PURCHASER_KH = "6df544ffdb0843db8d70d112504facaa";
	
	/*********************/
	public static final String BTN_ORDER_SEARCH = "MARKET_ORDER_BTN_ORDER_SEARCH";
	
	public static final String BTN_DISTRIBUTE  =  "MARKET_CUST_SUP_BTN_DISTRIBUTE";
	
	public static final String RESULT_FAIL_GROUP_MSG = "{\"success\":false,\"msg\":\"人员角色已绑定账户，不可删除!\"}";

	//化纤板块
	public static final String BLOCK_CF = "cf";
	public static final String BLOCK_CF_ONE = "1";
	//纱线板块
	public static final String BLOCK_SX = "sx";
	public static final String BLOCK_SX_TWO = "2";

    public static final String RESULT_SUCCESS_VERSIONUM = "{\"success\":false,\"msg\": \"该终端版本号已存在!\"}";
    public static final String ISEXIT_NAVIGATION_MSG = "{\"success\":false,\"msg\":\"一级导航下已经存在该名称!\"}";
    public static final String ISEXIT_SECONDNAVIGATION_MSG = "{\"success\":false,\"msg\":\"二级导航下已经存在该名称!\"}";
	public static final String SPEC_NAME_REPEAT = "{\"success\":false,\"msg\":\"名称已存在,请重新定义名称!\"}";
	public static final String SUPPSIGNING_NAME = "{\"success\":false,\"msg\":\"公司已添加!\"}";
	public static final String ORIGINALTIME = "2016-12-01";

}
