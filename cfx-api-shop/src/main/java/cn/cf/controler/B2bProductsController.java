package cn.cf.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bProductDto;
import cn.cf.service.B2bProductsService;
import cn.cf.utils.BaseController;

/**
 * @author:XHT
 * @describe:品名接口汇总
 * @time:
 */
@RestController
@RequestMapping("/shop")
public class B2bProductsController extends BaseController {

	@Autowired
	private B2bProductsService productsService;

	/**
	 * 品名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchProductsList", method = RequestMethod.POST)
	public String searchProductsList(HttpServletRequest request) {
		String result= null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("productType", request.getParameter("productType"));//1默认2切片
			map.put("isDelete", 1);
			map.put("isVisable", 1);
			List<B2bProductDto> list = productsService.searchProductList(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	
	

}
