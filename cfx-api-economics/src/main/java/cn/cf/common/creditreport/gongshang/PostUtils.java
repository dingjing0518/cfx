package cn.cf.common.creditreport.gongshang;

import java.util.Date;

import cn.cf.common.creditreport.gongshang.CsiRisknameListRequest.CsiRisknameListInfoRequestBiz;
import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;
import cn.cf.utils.SftpUtils;

import com.alibaba.fastjson.JSONObject;
import com.icbc.api.DefaultIcbcClient;

public class PostUtils {
	/**
	 * 融安易信提交文件
	 * @param method
	 * @param type
	 * @param json
	 * @return
	 */
	public static CsiRisknameResponse post(String method,String type,JSONObject json){
		CsiRisknameResponse response = new CsiRisknameResponse();
		CsiRisknameListRequest request = new CsiRisknameListRequest();
		try {
			CsiRisknameListInfoRequestBiz biz = new CsiRisknameListInfoRequestBiz();
			biz.setBankId(PropertyConfig.getProperty("rayx_bankid"));
			biz.setType(type);
			biz.setTime(DateUtil.formatYearMonthDayHMS(new Date()));
			biz.setReqData(json);
			request.setServiceUrl(PropertyConfig.getProperty("rayx_url")+method);
			request.setBizContent(biz);
			request.setResponse(response);
			DefaultIcbcClient client = new DefaultIcbcClient(PropertyConfig.getProperty("rayx_appid"), PropertyConfig.getProperty("rayx_pri_key"), PropertyConfig.getProperty("rayx_pub_key"));
			response = (CsiRisknameResponse) client.execute(request);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getLocalizedMessage());
		}
		return response;
	}
	
	/**
	 * 融安易信文件服务下载
	 * @param fileNames
	 */
	public static void downLoad(String[] fileNames){
		SftpUtils sftp = new SftpUtils(PropertyConfig.getProperty("rayx_fsname"),PropertyConfig.getProperty("rayx_fsip"),Integer.parseInt(PropertyConfig.getProperty("rayx_fsport")), PropertyConfig.getProperty("rayx_fsrsa"));
        sftp.login();
        if(null != fileNames){
        	for (int i = 0; i < fileNames.length; i++) {
        		sftp.download(PropertyConfig.getProperty("rayx_fsdownload"), fileNames[i], PropertyConfig.getProperty("rayx_upload")+fileNames[i]);
			}
        }
        sftp.logout();
	}
}
