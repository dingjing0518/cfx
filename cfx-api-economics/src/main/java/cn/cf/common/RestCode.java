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
     * 手机验证码无效 
     */
    , CODE_S004("S004")
    /**
     * 时间范围在半年之内
     */
    , CODE_S005("S005")
    /**
     * 返回某个字段值为空
     */
    , CODE_A001("A001")
    /**
     * 返回某个字段值不正确
     */
    , CODE_A002("A002")
    /**
     * 不能邀请自己公司
     */
    , CODE_A003("A003")
    /**
     * 公司不存在
     */
    , CODE_A004("A004")
    /**
     * 公司组织机构代码不存在
     */
    , CODE_A005("A005")
    /**
     * 证书申请中
     */
    , CODE_A006("A006")
    /**
     * 申请证书异常
     */
    , CODE_A007("A007")
    /**
     * 证书申请失败
     */
    , CODE_A008("A008")
    /**
     * 证书查询异常
     */
    , CODE_A009("A009")
    /**
     * 证书委托文件不存在
     */
    , CODE_A010("A010")
    /**
     * 请维护纳税人性质
     */
    , CODE_A011("A011")
    /**
     * 请维护主管税务机关代码
     */
    , CODE_A012("A012")
    /**
     * 请维护主管税务机关名称
     */
    , CODE_A013("A013")
    /**
     * 订单号不存在
     */
    , CODE_O001("O001")
    /**
     * 订单号不存在
     */
    , CODE_O002("O002")
     /**
     * 订单号已取消
     */
    , CODE_O010("O010")
    /**
     * 订单号已结清
     */
    , CODE_O011("O011")
    /**
     * 订单号已放款
     */
    , CODE_O012("O012")
    /**
     * 还款金额不能大于放款总金额
     */
    , CODE_O013("O013")
    /**
     * 供应商未绑定实体账户
     */
    , CODE_C001("C001")
     /**
     * 用户授信未审核通过
     */
    , CODE_C002("C002")
    /**
     * 白条额度不足
     */
    , CODE_C003("C003")
     /**
     * 支付密码错误
     */
    , CODE_C006("C006")
     /**
     * 原支付密码错误
     */
    , CODE_C007("C007")
     /**
     * 用户已注册
     */
    , CODE_C008("C008")
     /**
     * 意向客户已申请
     */
    , CODE_C009("C009")
     /**
     * 未查询到银行客户号
     */
    , CODE_C0010("C0010")
     /**
     * 客户无银行额度
     */
    , CODE_C0011("C0011")
    /**
     * 金融产品未匹配授信银行
     */
    , CODE_C0012("C0012")
    /**
     * 用户未授信
     */
    , CODE_C0013("C0013")
    /**
     * 审核中,无需重复提交
     */
    , CODE_C0014("C0014")
    /**
     * 票据产品未启用
     */
    , CODE_B001("B001")
    /**
     * 电票账户未绑定成功
     */
    , CODE_B002("B002")
    /**
     * 客户未审核通过
     */
    , CODE_B003("B003")
    /**
     * 授信银行不存在
     */
    , CODE_B004("B004")
    /**
     * 自定义返回内容
     */
    , CODE_Z000("Z000")
    ;
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
     * @param str 被转换的String对象
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
     * @param val 枚举对象整型值
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
            case "S004":
                return RestCode.CODE_S004;
            case "S005":
                return RestCode.CODE_S005;
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
            case "A010":
                return RestCode.CODE_A010;
            case "A011":
                return RestCode.CODE_A011;
            case "A012":
                return RestCode.CODE_A012;
            case "A013":
                return RestCode.CODE_A013;
            case "O001":
                return RestCode.CODE_O001;
            case "O002":
                return RestCode.CODE_O002;
            case "O010":
                return RestCode.CODE_O010;
            case "O011":
                return RestCode.CODE_O011;
            case "O012":
                return RestCode.CODE_O012;
            case "O013":
                return RestCode.CODE_O013;
            case "C001":
                return RestCode.CODE_C001;
            case "C002":
                return RestCode.CODE_C002;
            case "C003":
                return RestCode.CODE_C003;
            case "C006":
                return RestCode.CODE_C006;
            case "C007":
                return RestCode.CODE_C007;
            case "C008":
                return RestCode.CODE_C008;
            case "C009":
                return RestCode.CODE_C009;
            case "C0010":
                return RestCode.CODE_C0010;
            case "C0011":
                return RestCode.CODE_C0011;
            case "C0012":
                return RestCode.CODE_C0012;
            case "C0013":
                return RestCode.CODE_C0013;
            case "C0014":
                return RestCode.CODE_C0014;
            case "B001":
                return RestCode.CODE_B001;
            case "B002":
                return RestCode.CODE_B002;
            case "B003":
                return RestCode.CODE_B003;
            case "B004":
                return RestCode.CODE_B004;
            case "Z000":
                return RestCode.CODE_Z000;
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
            case "S004":
                return "手机验证码无效";
            case "S005":
                return "日期范围在半年之内";
            case "A001":
                return "参数不能为空";
            case "A002":
                return "返回某字段不正确";
            case "A003":
                return "不能邀请自己公司";
            case "A004":
                return "公司不存在";
            case "A005":
                return "公司组织机构代码不存在";
            case "A006":
                return "证书申请中";
            case "A007":
                return "申请证书异常";
            case "A008":
                return "证书申请失败";
            case "A009":
                return "证书查询异常";
            case "A010":
                return "证书委托文件不存在";
            case "A011":
                return "请维护纳税人性质";
            case "A012":
                return "请维护主管税务机关代码";
            case "A013":
                return "请维护主管税务机关名称";
            case "O001":
                return "订单不存在";
            case "O002":
                return "订单已支付";
            case "O010":
                return "订单已取消,无法操作";
            case "O011":
                return "订单已结清,无法操作";
            case "O012":
                return "订单已放款,无法操作";
            case "O013":
                return "还款金额不能大于放款总金额";
            case "C001":
                return "供应商未绑定实体账户";
            case "C002":
                return "用户授信未审核通过";
            case "C003":
                return "额度不足";
            case "C006":
                return "支付密码错误";
            case "C007":
                return "原支付密码错误";
            case "C008":
                return "客户已注册,请登录";
            case "C009":
                return "意向客户已存在,无需重复提交";
            case "C0010":
                return "未找到对应的银行客户号";
            case "C0011":
                return "金融产品提交银行失败,请稍后重试";
            case "C0012":
                return "金融产品未匹配授信银行";
            case "C0013":
                return "白条未申请,无法设置";
            case "C0014":
                return "审核中,无需重复提交";
            case "B001":
                return "票据产品未启用";
            case "B002":
                return "电票账户未绑定成功";
            case "B003":
                return "客户未审核通过";
            case "B004":
                return "授信银行不存在";
            case "Z000":
                return getMsg();
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
