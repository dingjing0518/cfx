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
import cn.cf.dto.B2bMemubarManageDto;
import cn.cf.service.B2bMemubarManageService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("shop")
public class B2bMemubarManageController extends BaseController{
	
	@Autowired
	private B2bMemubarManageService memubarManageService;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchMemubarManageList", method = RequestMethod.POST)
	public String searchGradeList(HttpServletRequest request) {
		String result = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			Integer source = this.getSource(request);
			String block = ServletUtils.getStringParameter(request, "block", "cf");
			map.put("source", source);
			map.put("block", block);
			List<B2bMemubarManageDto> list = memubarManageService.searchList(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	

}
