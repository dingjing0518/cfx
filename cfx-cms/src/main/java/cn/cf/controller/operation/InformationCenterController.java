/**
 * 
 */
package cn.cf.controller.operation;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cf.common.BaseController;
import cn.cf.service.operation.InformationCenterService;

/**
 * @author hxh 资讯和帮助中心
 */
@Controller
@RequestMapping("/")
public class InformationCenterController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(InformationCenterController.class);
	
	
	@Autowired
	private InformationCenterService informationCenterService; 
	/**
	 * 资讯信息导入mongo数据库
	 * 
	 * @param request
	 * @param memberPk
	 * @param rolePks
	 * @return
	 */
	@RequestMapping("importNewsToMongo")
	@ResponseBody
	public String importStorage(HttpServletRequest request) {
		logger.error("---------importNewsToMongo----------------");
		return informationCenterService.importNewsToMongo();
	}
	
	
	
}
