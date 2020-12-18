package cn.cf.common.creditpay.zhonghang;

import cn.cf.json.JsonUtils;
import cn.cf.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;

import java.util.*;


public class XmlUtils {
    /**
     * 报文头部
     *
     * @param fcl
     * @return
     */
    private String getHead(String fcl) {
        String xml = "00E1091500010400011               3820200818101215                                                                                 00860SZOPEN" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><UTILITY_PAYMENT><CONST_HEAD> " +
                "<REQUEST_TYPE>0240</REQUEST_TYPE>" +
                "<REQUEST_MERCH>BOCOP000</REQUEST_MERCH>" +
                "<AGENT_CODE>32860001</AGENT_CODE>" +
                "<TRN_CODE>SZOPEN</TRN_CODE>" +
                "<TRN_FLAG>" + fcl + "</TRN_FLAG>" +
                "<FRONT_TRACENO>202006242981309198</FRONT_TRACENO>" +
                "<FRONT_DATE>" + DateUtil.formatYearMonthDayTwo(new Date()) + "</FRONT_DATE>" +
                "<FRONT_TIME>" + DateUtil.formatYearMonthDayHms(new Date()) + "</FRONT_TIME>" +
                "<TERM_ID></TERM_ID>" +
                "<CHANNEL_FLAG>38</CHANNEL_FLAG>" +
                "</CONST_HEAD> ";
        return xml;
    }

    public String getXml(String xml) {
        if (null == xml || "".equals(xml)) {
            return "000000";
        }
        String xmlLength = xml.length() + "";
        int length = xmlLength.length();
        for (int i = 0; i < 6 - length; i++) {
            xmlLength = "0" + xmlLength;
        }
        return xmlLength + xml;
    }

    public JSONObject getJsonByXml(String xml) throws JSONException {
        JSONObject xmlJSONObj = null;
        try {
            xmlJSONObj = JsonUtils.toJSONObject(xml.substring(xml.indexOf("<DATA_AREA>") + "<DATA_AREA>".length(), xml.indexOf("</DATA_AREA>")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return xmlJSONObj;
    }

    /**
     * 申请工作密钥 9000
     *
     * @return
     */
    public String guangda9000() {
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("9000"));
        sb.append("<Body>");
        sb.append("<operationDate>" + DateUtil.formatYearMonthDayTwo(new Date()) + "</operationDate>");
        sb.append("</Body></In>");
        return getXml(sb.toString());
    }

    /**
     * 额度查询 fcl_credit
     *
     * @param customer
     * @param organizationCode
     * @return
     */
    public String zhCreditAmount(String customer, String organizationCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("customer", customer);
        map.put("organizationCode", organizationCode);
        String credit = PostUtils.getJsonString(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("fcl_credit"));
        sb.append("<DATA_AREA>");
        sb.append(credit);
        sb.append("</DATA_AREA></UTILITY_PAYMENT>");
        return sb.toString();
    }

    /**
     * 订单支付 fcl_order
     *
     * @param customer         客户名称
     * @param orderNumber      订单支付编号
     * @param startTime        订单申请日期
     * @param endTime          还款时间（还款时间以银行协议为准）
     * @param organizationCode 统一社会信用代码
     * @param orderAmount      订单总金额
     * @param amount1          放款金额
     * @param payName1         收款人名称
     * @param payUserName1     收款人户名
     * @param payAccount1      收款人账户
     * @param payBankNo1       收款人开户行行号
     * @param payBank1         收款人开户行行名
     * @return
     */
    public String zhPayOrder(String customer, String orderNumber, Date startTime, Date endTime, String organizationCode,
                             Double orderAmount, Double amount1, String payName1, String payUserName1, String payAccount1,
                             String payBankNo1, String payBank1) {
        Map<String, Object> map = new HashMap<>();
        map.put("customer", customer);
        map.put("orderNumber", orderNumber);
        map.put("startTime", DateUtil.formatYearMonthDayHms(startTime));
        map.put("endTime", endTime == null ? "null" : DateUtil.formatYearMonthDayHms(endTime));
        map.put("organizationCode", organizationCode);
        map.put("orderAmount", orderAmount);
        map.put("amount1", amount1);
        map.put("payName1", payName1);
        map.put("payUserName1", payUserName1);
        map.put("payAccount1", payAccount1);
        map.put("payBankNo1", payBankNo1);
        map.put("payBank1", payBank1);
        String credit = PostUtils.getJsonString(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("fcl_order"));
        sb.append("<DATA_AREA>");
        sb.append(credit);
        sb.append("</DATA_AREA></UTILITY_PAYMENT>");
        return sb.toString();
    }

    /**
     * 放款进度查询 fcl_fin
     *
     * @param customer
     * @param organizationCode
     * @param orderNumber
     * @return
     */
    public String zhSearchloan(String customer, String organizationCode, String orderNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("customer", customer == null ? "null" : customer);
        map.put("organizationCode", organizationCode == null ? "null" : organizationCode);
        map.put("orderNumber", orderNumber);
        String credit = PostUtils.getJsonString(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("fcl_fin"));
        sb.append("<DATA_AREA>");
        sb.append(credit);
        sb.append("</DATA_AREA></UTILITY_PAYMENT>");
        return sb.toString();
    }

    /**
     * 还款明细查询  fcl_repay
     *
     * @param customer
     * @param organizationCode
     * @param lousNumber
     * @return
     */
    public String zhRepayment(String customer, String organizationCode, String lousNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("customer", customer == null ? "null" : customer);
        map.put("organizationCode", organizationCode == null ? "null" : organizationCode);
        map.put("lousNumber", lousNumber);
        String credit = PostUtils.getJsonString(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("fcl_repay"));
        sb.append("<DATA_AREA>");
        sb.append(credit);
        sb.append("</DATA_AREA></UTILITY_PAYMENT>");
        return sb.toString();
    }

    /**
     * 取消订单  fcl_cancel
     *
     * @param orderNumber
     * @return
     */
    public String zhCancelOrder(String orderNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        String credit = PostUtils.getJsonString(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("fcl_cancel"));
        sb.append("<DATA_AREA>");
        sb.append(credit);
        sb.append("</DATA_AREA></UTILITY_PAYMENT>");
        return sb.toString();
    }
}
