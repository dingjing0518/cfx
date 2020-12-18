package cn.cf.controler;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bVarietiesDto;
import cn.cf.service.B2bVarietiesService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * @author:XHT
 * @describe:品种接口汇总
 * @time:
 */
@RestController
@RequestMapping("/shop")
public class B2bVarietiesController extends BaseController {
	
	@Autowired
	private B2bVarietiesService  varietiesService;
	
	/**
	 * 查询商品品种
	 * @param request:parentPk -1：品种  空未全部子品种
	 * @describe ：商品品种 ，商品子品种
	 * @return
	 */
	@RequestMapping(value = "searchVarietiesList", method = RequestMethod.POST)
	public String searchVarietiesList(HttpServletRequest request) {
		String result = null ;
		try {
			String parentPk = ServletUtils.getStringParameter(request, "parentPk", "");
			List<B2bVarietiesDto> list = varietiesService.searchVarietiesList(parentPk);
			result =  RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	/**
	 * 分页查询商品品种
	 * @param request:parentPk -1：品种  空未全部子品种
	 * @describe ：商品品种 ，商品子品种
	 * @return
	 */
	@RequestMapping(value = "searchVarietiesPage", method = RequestMethod.POST)
	public String searchVarietiesPage(HttpServletRequest request) {
		String result = null ;
		try {
			String parentPk = ServletUtils.getStringParameter(request, "parentPk", " ");
			String name = ServletUtils.getStringParameter(request, "name", "");
			Integer start = ServletUtils.getIntParameterr(request, "start", 0);
			Integer limit = ServletUtils.getIntParameterr(request, "limit", 10);
			PageModel<B2bVarietiesDto> pm = varietiesService.searchVarietiesPage(parentPk,name, start, limit);
			result =  RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	

}
