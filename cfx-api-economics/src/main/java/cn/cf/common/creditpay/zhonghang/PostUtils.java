package cn.cf.common.creditpay.zhonghang;

import cn.cf.property.PropertyConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.*;
import java.util.*;

public class PostUtils {
    static CloseableHttpClient httpClient;
    static CloseableHttpResponse httpResponse;

    public static String getJsonString(Map<String, Object> map) {
        if (null == map) {
            map = new HashMap<String, Object>();
        }
        Set keys = map.keySet();
        Object[] arr = keys.toArray();
        Arrays.sort(arr);
        String prestr = "";
        for (Object key : arr) {
            String value = null;
            Object object = map.get(key);
            if (object instanceof Double || object instanceof Integer) {
                value = String.valueOf(object);
            } else {
                value = (String) map.get(key);
            }
            prestr = prestr + key + "=" + value + "&";
        }
        prestr += "key=" + PropertyConfig.getProperty("zh_key");
        map.put("signature", MD5Utils.getMD5(prestr, false, 32));
        return JSON.toJSONString(map);
    }

    /**
     * 发送https请求
     *
     * @param url
     * @param xmlString
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, String xmlString) throws UnsupportedEncodingException {
        HttpRequest request = HttpRequest.post(url).method(Method.POST).header("clentid", "378")
                .header("type", "03").body(xmlString, "text/plain");
        HttpResponse httpResponse = request.execute();
        return new String(httpResponse.bodyBytes(), "utf-8");
    }
}
