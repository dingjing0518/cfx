package cn.cf.common.creditpay.suzhou;

import java.util.Date;
import java.util.Map;

import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.bsz.becp.core.client.BSZBECPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostUtils {
	private static final Logger logger = LoggerFactory.getLogger(PostUtils.class);
	public static String post(Map<String,Object> map){
		logger.error("sz_bank_url",PropertyConfig.getProperty("sz_bank_url"));
		BSZBECPClient becpClient = new BSZBECPClient();
		becpClient.initialize(PropertyConfig.getProperty("sz_bank_url"));
		map.put("clientFlowNo", DateUtil.formatYearMonthDayHms(new Date())+KeyUtils.getRandom(18));
		return becpClient.sendAndReceive(map);
	}
}
