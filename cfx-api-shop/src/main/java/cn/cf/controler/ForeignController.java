package cn.cf.controler;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.json.JsonUtils;
import cn.cf.service.B2bCompanyService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("shop")
public class ForeignController extends BaseController{
	
	@Autowired
	private B2bCompanyService b2bCompanyService;

	
    /**
     * 获取公司信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByCompany", method = RequestMethod.POST)
    public String getByCompany(HttpServletRequest request) {
    	String pk =  ServletUtils.getStringParameter(request, "pk","");
      	B2bCompanyDto company =  b2bCompanyService.getCompany(pk);
      	if(null != company){
      		return RestCode.CODE_0000.toJson(company);
      	}else{
      		return RestCode.CODE_0000.toJson();
      	}
    }
    
    /**
     * 获取公司信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByCompanyList", method = RequestMethod.POST)
    public String getByCompanyList(HttpServletRequest request) {
    	String companyType =  ServletUtils.getStringParameter(request, "companyType","");
    	String companyPk =  ServletUtils.getStringParameter(request, "companyPk","");
    	String returnType =  ServletUtils.getStringParameter(request, "returnType","");
    	Map<String,Object> map = paramsToMap(request);
    	String rest = RestCode.CODE_0000.toJson();
    	if(!"".equals(returnType)){
    		List<B2bCompanyDtoEx> list = b2bCompanyService.searchCompanyList(Integer.parseInt(companyType), companyPk, Integer.parseInt(returnType), null);
    		if(null != list && list.size()>0){
    			rest = RestCode.CODE_0000.toJson(list);
    		}
    	}else{
    		List<B2bCompanyDto> list = b2bCompanyService.getCompanyDtoByMap(map);
    		if(null != list && list.size()>0){
    			rest = RestCode.CODE_0000.toJson(list);
    		}
    	}
    	return rest;
    }
    
    /**
     * 根据公司名称查询公司
     * @return
     */
	@RequestMapping(value = "/getCompanyByName", method = RequestMethod.POST)
	public String getCompanyByName(HttpServletRequest request) {
		String jsonData = ServletUtils.getStringParameter(request, "jsonData");
		B2bCompanyDto dto = JsonUtils.toBean(jsonData,B2bCompanyDto.class);
		dto = b2bCompanyService.getCompanyByName(dto.getName());
		return RestCode.CODE_0000.toJson(dto);
	}
    
    
}
