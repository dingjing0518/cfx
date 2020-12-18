package cn.cf.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bBrandService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * @author:XHT
 * @describe:品牌接口汇总
 * @time:
 */
@RestController
@RequestMapping("/shop")
public class B2bBrandController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(B2bBrandController.class);

	@Autowired
	private B2bBrandService brandService;

	 
	/**
	 * 我的厂家品牌
	 */
	@RequestMapping(value = "myGoodsBrandList", method = RequestMethod.POST)
	public String myGoodsBrandList(HttpServletRequest request) {
	    String storePk = ServletUtils.getStringParameter(request, "storePk", "");
	    if(null!=storePk&&!"".equals(storePk)){
	    	Map<String, Object> map = this.paramsToMap(request);
			try {
				map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
				PageModel<B2bGoodsBrandDto> pm = brandService.searchMyGoodsBrandList(map);
				return RestCode.CODE_0000.toJson(pm);
			} catch (Exception e) {
				e.printStackTrace();
				return RestCode.CODE_S999.toJson();
			}
	    }
	    return RestCode.CODE_0000.toJson();
	}
	
	
	/**
	 * 厂家品牌
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchBrandsList", method = RequestMethod.POST)
	public String searchBrandsList(HttpServletRequest req) {
		String result = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isDelete", 1);
			map.put("isVisable", 1);
			List<B2bBrandDto> list = brandService.searchBrand(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "searchBrandsListPage", method = RequestMethod.POST)
	public String searchBrandsListPage(HttpServletRequest req) {
		String result = null;
		try {
			Map<String, Object> map =  this.paramsToMap(req);
			map.put("isDelete", 1);
			map.put("isVisable", 1);
			map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
			map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
			PageModel<B2bBrandDto> pm = brandService.searchBrandPage(map );
			result = RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	/**
	 * 添加品牌
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addBrand", method = RequestMethod.POST)
	public String addBrand(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			String brandName = ServletUtils.getStringParameter(request, "brandName");
			Sessions sessions = this.getSessions(request);
			if (null == sessions.getCompanyDto()) {
				restCode = RestCode.CODE_M008;
			} else {
				restCode = brandService.addBrand(brandName, sessions.getStoreDto().getPk(),sessions.getCompanyDto().getPk(),
						sessions.getStoreDto().getName(),sessions.getMemberPk());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addBrand exception:", e);
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	/**
	 * 编辑品牌
	 * 
	 * @param req
	 * @param isDelete
	 * @param pk
	 * @return
	 */
	@RequestMapping(value = "updateGoodsBrand", method = RequestMethod.POST)
	public String updateGoodsBrand(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			B2bGoodsBrandDto dto = new B2bGoodsBrandDto();
			dto.bind(req);
			brandService.updateGoodsBrand(dto);
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
			e.printStackTrace();
		}
		return restCode.toJson();
	}
}
