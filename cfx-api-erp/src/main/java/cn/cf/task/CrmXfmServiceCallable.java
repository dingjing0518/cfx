/**
 * 
 */
package cn.cf.task;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.cf.common.EncodeUtil;
import cn.cf.http.HttpHelper;
import cn.cf.property.PropertyConfig;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author bin
 *
 */
public class CrmXfmServiceCallable implements Callable<Object>{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String jsonData;

	public CrmXfmServiceCallable(String jsonData) {
		 this.jsonData = jsonData;
	}

	@Override
	public Object call() throws Exception {
		String data = null;
		try {
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("encodeData", EncodeUtil.des3Encrypt3Base64(jsonData));
			String rest =HttpHelper.doPost(PropertyConfig.getProperty("xfm_url")+"aip/component/http/JSON/aip_default/HXH005", getHeaders(), paraMap, "utf-8");
			logger.info("xfm-orderResult:---------"+rest);
			if(null != rest && !"".equals(rest)){
				JSONObject json = JSONObject.parseObject(rest);
				if(json.containsKey("rows")){
					json = (JSONObject) JSONArray.parseArray(json.getString("rows")).get(0);
					//成功
					if(null != json && json.containsKey("success") && "true".equals(json.getString("success"))){
						data="{\"status\":\"S\",\"message\":\"操作成功\"}";
					}else if(null != json && json.containsKey("success") && "false".equals(json.getString("success")) && json.containsKey("message")){
						data="{\"status\":\"F\",\"message\":\"" + json.getString("message") + " \"}";
					}else{
						data="{\"status\":\"F\",\"message\":\"" + rest + " \"}";
					}
				}else{
					data="{\"status\":\"F\",\"message\":\""+rest+"\"}";
				}
			}else{
				data="{\"status\":\"F\",\"message\":\"crm无返回数据\"}";
			}
		} catch (Exception e) {
			logger.error("syncOrderByXinfengming", e);
			data="{\"status\":\"N\",\"message\":\""+e+"\"}";
		}
		return data;
	}

	private Header[] getHeaders(){
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		headers[1] = new BasicHeader("Authorization",getXFMAuthorization());
		return headers;
	}
	private String getXFMAuthorization() {
	    String auth = PropertyConfig.getProperty("xfm_username") + ":" + PropertyConfig.getProperty("xfm_password");
	    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	    String authHeader = "Basic " + new String(encodedAuth);
	    return authHeader;
	  }
}
