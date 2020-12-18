package cn.cf.controler;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bSpecDto;
import cn.cf.service.B2bSpecService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * @author:XHT
 * @describe:规格接口汇总
 * @time:
 */
@RestController
@RequestMapping("/shop")
public class B2bSpecController extends BaseController {
	
	@Autowired
	private B2bSpecService  specService;
	/**
	 * 查询规格
	 * @param request: parentPk (-1:规格大类  空为全部的规格系列)
	 * @describe ：规格大类 ，规格系列
	 * @return
	 */
	@RequestMapping(value = "searchSpecList", method = RequestMethod.POST)
	public String searchSpecList(HttpServletRequest request) {
		String result = null;
		try {
			String parentPk = ServletUtils.getStringParameter(request, "parentPk", "");
			List<B2bSpecDto> list = specService.searchSpecsList(parentPk);
			result =  RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 分页查询规格
	 * @param request: parentPk (-1:规格大类  空为全部的规格系列)
	 * @describe ：规格大类 ，规格系列
	 * @return
	 */
	@RequestMapping(value = "searchSpecPage", method = RequestMethod.POST)
	public String searchSpecPage(HttpServletRequest request) {
		String result = null;
		try {
			String parentPk = ServletUtils.getStringParameter(request, "parentPk", "");
			String name = ServletUtils.getStringParameter(request, "name", "");
			Integer start = ServletUtils.getIntParameterr(request, "start", 0);
			Integer limit = ServletUtils.getIntParameterr(request, "limit", 10);
			PageModel<B2bSpecDto> pm = specService.searchSpecsPage(parentPk,name, start, limit);
			result =  RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	

}
