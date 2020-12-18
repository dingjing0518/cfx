package cn.cf.common.zhonghang;

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

    /**
     * 订单状态更新  fcl_orders
     *
     * @param orderNumber
     * @param orderStatus
     * @return
     */
    public String zhStatusUpdate(String orderNumber, Integer orderStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        map.put("orderStatus", orderStatus);
        String credit = PostUtils.getJsonString(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getHead("fcl_orders"));
        sb.append("<DATA_AREA>");
        sb.append(credit);
        sb.append("</DATA_AREA></UTILITY_PAYMENT>");
        return sb.toString();
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
}
