package cn.cf.controller.economics;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.property.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.http.HttpHelper;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bOnlinepayRecordExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.OnlinePayService;
import cn.cf.util.ServletUtils;
import net.sf.json.JSONObject;

/**
 * 线上支付管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/")
public class OnlinePayController extends BaseController {
	
	@Autowired
	private   OnlinePayService onlinePayService;

	private static final Logger logger = LoggerFactory.getLogger(OnlinePayController.class);
	/**
	 *线上支付记录页面
	 *
	 * @return
	 */
	@RequestMapping("onlinePayRecord")
	public ModelAndView onlinePayRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("economics/onlinePayRecord");
		mav.addObject("goodsList", onlinePayService.searchNoIsDelAndIsvOnPayGoodsList());
		return mav;
	}
	
	
	
	@ResponseBody
	@RequestMapping("searchOnlinePayRecord")
	public String searchOnlinePayRecord(HttpServletRequest request, HttpServletResponse response, B2bOnlinepayRecordExtDto dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bOnlinepayRecordExtDto> qm = new QueryModel<B2bOnlinepayRecordExtDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bOnlinepayRecordExtDto> pm = onlinePayService.searchOnlinePayRecord(qm);
		return JsonUtils.convertToString(pm);
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "checkOnlineRecord", method = RequestMethod.POST)
	public String checkOnlineRecord(HttpServletRequest request, HttpServletResponse response, String orderNumber)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNumber", orderNumber);
		String msg;
		try {
			sb.append("checkOnlineRecord_HttpHelper.param:" + map + "\r\n");
			String data = HttpHelper.sendPostRequest(PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/updateOnlineRecord", map, null, null);
			sb.append("checkOnlineRecord_HttpHelper.data:" + data + "\r\n");
			msg = Constants.RESULT_SUCCESS_MSG;
			data = CommonUtil.deciphData(data);// 解密
			sb.append("checkOnlineRecord_HttpHelper.deciphData:" + data + "\r\n");
			logger.error("returnValue:"+sb);
			if (data != null) {
				JSONObject obj = JSONObject.fromObject(data);
					msg = obj.toString();
			} else {
				logger.error(sb.append("银行未返回结果\r\n").toString());
				msg = Constants.RESULT_FAIL_MSG;
			}
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}
}
