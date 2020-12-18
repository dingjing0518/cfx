package cn.cf.common.creditpay.zheshang;

import java.util.Date;
import java.util.Map;

import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.ruimin.intfin.sdk.inf.ApiHeader;
import com.ruimin.intfin.sdk.inf.ApiRspData;

public class PostUtils {

	public static ApiRspData post(String method,Map<String,Object> params){
		//实例化 api服务调用入口类
				ZsServiceBean bean = new ZsServiceBean();
				/**/// 1. 通用openapi服务调用实例
				// 设置公共头
				ApiHeader header = new ApiHeader();
				// 设置服务访问地址 api文档上获取具体服务地址
				header.setUrl(PropertyConfig.getProperty("zs_baseurl")+method);
				// 设置请求时间  时间格式 yyyy-MM-dd HH:mm:ss.SSS
				header.setSendtime(DateUtil.formatDateAndTimeS(new Date()));
				// 设置流水号 建议用标准的流水号生成器
				header.setTraceno(KeyUtils.getFlowNumber());
				// 设置业务体 根据具体业务接口设置上传参数
				// api服务调用
				return bean.getService().invoke(header, params);
	}
}
