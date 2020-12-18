//用于保存系统中出现的类别显现


//优惠券操作类型
var lottery_coupon_status = [{"key":0,"value":"新增数据"},{"key":1,"value":"未使用"},{"key":2,"value":"已使用"},{"key":3,"value":"赠送中"},{"key":4,"value":"已赠送"},{"key":5,"value":"已过期"}];
	
var order_status = [{"key":-1,"value":"订单取消"},{"key":0,"value":"待审核"},{"key":1,"value":"待付款"},{"key":2,"value":"确认付款"},{"key":3,"value":"待发货"},{"key":4,"value":"已发货"},{"key":5,"value":"部分发货"},{"key":6,"value":"已完成"}];
	
var order_loan_status = [{"key":1,"value":"待申请"},{"key":2,"value":"申请中"},{"key":3,"value":"申请成功"},{"key":4,"value":"申请失败"},{"key":5,"value":"已还款"},{"key":6,"value":"部分还款"}];
	
var lottery_apply_Status = [{"key":1,"value":"未申请"},{"key":2,"value":"已申请"},{"key":3,"value":"已确认"}];
	
var lottery_back_type = [{"key":1,"value":"支付宝"},{"key":2,"value":"银行卡"}];

var lottery_back_status = [{"key":1,"value":"未返款"},{"key":2,"value":"已返款"}];

var is_apply_status = [{"key":"1","value":"是"},{"key":"2","value":"否"}];

var coupon_type= [{"key":1,"value":"免息券"}];

var use_type = [{"key":1,"value":"金融白条支付"}];

var activity_type = [{"key":1,"value":"抽奖"},{"key":2,"value":"白条新客户见面礼"},{"key":3,"value":"白条老客户尊享礼"}];

var award_type = [{"key":"2","value":"实物"},{"key":"3","value":"再接再厉,明日再来"}];

var award_variety = [{"key":"2","value":"实物"},{"key":"3","value":"无"}];

var grant_type = [{"key":"1","value":"业务员代送"},{"key":"2","value":"邮寄"},{"key":"3","value":"无"}];

var award_status = [{"key":"1","value":"未发放"},{"key":"2","value":"已发放"},{"key":"3","value":"无"}];

/*会员维度*/
var member_dimansionality = [{"key":"1","value":"账户维度"},{"key":"2","value":"交易维度"},
 /* {"key":"3","value":"求购维度"},{"key":"4","value":"竞拍维度"},*/
{"key":"5","value":"收藏维度"},{"key":"6","value":"流量维度"},{"key":"7","value":"商品维度"}];

/*维度对应的类型*/
var dimansionality_account = [{"type":"1","list":[{"key":"1","value":"成功注册采购商"},{"key":"2","value":"完善发票信息"},{"key":"3","value":"完善收货地址"}
,{"key":"4","value":"完善公司logo"},{"key":"5","value":"开通白条(已经开启授信额度)"},{"key":"6","value":"添加人员"},{"key":"7","value":"成功入驻供应商"}
,{"key":"8","value":"添加物流模板"},{"key":"9","value":"完善生产厂区"},{"key":"10","value":"完善仓库信息"},{"key":"11","value":"添加子公司"},{"key":"12","value":"开通化纤贷(已经开启授信额度)"},{"key":"13","value":"商品品牌"}]},

 {"type":"2","list":[{"key":"1","value":"白条订单"},{"key":"2","value":"线下支付"}
,{"key":"5","value":"除POY之外其他品名订单"},{"key":"6","value":"POY订单"},{"key":"7","value":"WAP订单"}
,{"key":"8","value":"APP订单"},{"key":"10","value":"化纤贷支付"}]},

{"type":"3","list":[{"key":"1","value":"发布求购"},{"key":"2","value":"确定中标"},{"key":"3","value":"报价"}]},

{"type":"4","list":[{"key":"1","value":"竞拍出价"},{"key":"2","value":"上架竞拍商品(首次)"},{"key":"3","value":"确认中标"}]},

{"type":"5","list":[{"key":"1","value":"收藏商品"},{"key":"2","value":"收藏店铺"}]},

{"type":"6","list":[{"key":"1","value":"每日登录"}]},

{"type":"7","list":[{"key":"1","value":"更新商品"}]}
];
/*会员维度赠送周期类型*/
var presentPeriodType = [{"key":"1","value":"一次赠送"},{"key":"2","value":"每天"},{"key":"3","value":"每周"}];

/*满足条件类型*/
var conditionTypes = [{"key":"2","value":"累计成交供应商家数"},{"key":"5","value":"累计添加人员数"}];

/*满足条件单位类型*/
var utilTypes = [{"key":"1","value":"条"},{"key":"2","value":"笔"},{"key":"3","value":"家"},{"key":"4","value":"人"}];

/*时间范围*/
var periodTimes = [{"key":"3","value":"最近3天"},{"key":"7","value":"最近7天"},{"key":"15","value":"最近15天"},{"key":"30","value":"最近30天"},{"key":"90","value":"最近90天"}];


/**************************金融风控START***************************/
var useResource = [{"key":"1","value":"盛虹推荐"},{"key":"2","value":"自主申请"},{"key":"3","value":"新凤鸣推荐"},{"key":"4","value":"营销推荐"},{"key":"5","value":"其他供应商推荐"},{"key":"6","value":"CMS后台申请"}];
//设备类型
var deviceType = [{"key":"经编","value":"经编"},{"key":"喷水织机","value":"喷水织机"},{"key":"圆机","value":"圆机"}]; 
//使用类型
var useType = [{"key":"自有","value":"自有"},{"key":"租赁","value":"租赁"},{"key":"自有+租赁","value":"自有+租赁"}];
//企业经营场所
var companyPlace = [{"key":"自有","value":"自有"},{"key":"租赁","value":"租赁"},{"key":"自有+租赁","value":"自有+租赁"}];

//合作对象
var cooperativePartner = [{"key":"个人","value":"个人"},{"key":"公司","value":"公司"}];

/**************************金融风控END***************************/
var token_type = [{"key":"/erp/","value":"erp"},{"key":"/b2b/pc","value":"pc"},{"key":"/b2b/wap","value":"wap"},{"key":"/b2b/app","value":"app"}];


var memubar_type = [{"key":1,"value":"PC"},{"key":2,"value":"WAP"},{"key":3,"value":"APP"}];

/**********************次导航类型************************/
var block_Type = [{"key":"cf","value":"化纤"},{"key":"sx","value":"纱线"}];

var position_Type = [{"key":1,"value":"次导航"},{"key":2,"value":"品牌专区"},{"key":3,"value":"优质供应商"}];

var platform_Type = [{"key":1,"value":"PC端"},{"key":2,"value":"WAP端"},{"key":3,"value":"APP端"}];

var region_Type = [{"key":1,"value":"太仓"},{"key":2,"value":"常熟"},{"key":3,"value":"张家港"},{"key":4,"value":"萧山"},{"key":5,"value":"绍兴"},{"key":6,"value":"桐乡"},{"key":7,"value":"吴江"},{"key":8,"value":"其他"}];

//票据类型
var bill_Type = [{"key":0,"value":"无限制"},{"key":1,"value":"商承票"},{"key":2,"value":"银承票"}];


//完善资料，设备类型
var device_Type = [{"key":1,"value":"圆机"},{"key":2,"value":"经编机"},{"key":3,"value":"加弹机"},{"key":4,"value":"梭织机"}];

//完善资料，设备品牌
var device_Brand = {1:["卡尔迈耶","卜硕","凹凸","力可茂","佰源","其他"],
                    2:["卡尔迈耶","常州三纺","常德","五洋","其他"],
                    3:["日本TMT","德国巴马格","宏源","苏州巴马格","经纬","越剑","荣腾","精功","其他"],
                    4:["津田驹","丰田","青岛引春","海佳","日佳","日发","其他"]};

var config_Type = [{"key":1,"value":"系统"},{"key":2,"value":"银行"},{"key":3,"value":"线上支付"},{"key":4,"value":"风控"},{"key":5,"value":"票据"},{"key":6,"value":"erp"}];
