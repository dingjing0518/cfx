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
import cn.cf.dto.B2bGradeDto;
import cn.cf.service.B2bGradeService;
import cn.cf.utils.BaseController;

/**
 * @author:XHT
 * @describe:产品等级接口汇总
 * @time:2017-5-23 下午3:32:11
 */
@RestController
@RequestMapping("/shop")
public class B2bGradeController  extends BaseController{
	
	@Autowired
	private B2bGradeService gradeService;
	
	/**
	 * 产品等级
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchGradeList", method = RequestMethod.POST)
	public String searchGradeList(HttpServletRequest request) {
		String result = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isDelete", 1);
			map.put("isVisable", 1);
			List<B2bGradeDto> list = gradeService.searchGradeList(map);
			result = RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	

}
