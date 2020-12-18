package cn.cf.controler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.SysNewsDto;
import cn.cf.service.SysNewsService;
import cn.cf.util.ServletUtils;

@RestController
@RequestMapping("/shop")
public class NewsController {
	private final static Logger logger = LoggerFactory
			.getLogger(NewsController.class);
	@Autowired
	private SysNewsService sysNewsService;
	
	/**
	 * 行业资讯详细
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "informationDetails",method = RequestMethod.POST)
	public String informationDetails(HttpServletRequest req){
		String result=RestCode.CODE_0000.toJson();
		try {
			String pk = ServletUtils.getStringParameter(req, "pk", "");
			String categoryPk = ServletUtils.getStringParameter(req, "categoryPk", "");
			if("".equals(pk)){
				return RestCode.CODE_A001.toJson();
			}
			SysNewsDto news= sysNewsService.getSysNewsByPk(pk,categoryPk);
			if(null != news){
				result=RestCode.CODE_0000.toJson(news);
			}else{
				result=RestCode.CODE_A001.toJson();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sysnewsError:", e);
			result= RestCode.CODE_S999.toJson();
		}
		return result;
	}
}
