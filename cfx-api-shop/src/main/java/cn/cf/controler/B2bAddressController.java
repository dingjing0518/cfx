package cn.cf.controler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bAddressDto;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bAddressService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:收货地址管理
 * @author FJM
 * @date 2018-4-17 下午3:13:34
 */
@RestController
@RequestMapping("shop")
public class B2bAddressController extends BaseController{
	
	@Autowired
	private B2bAddressService addressService;
	
	/**
	 * 收货地址列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchAddressList", method = RequestMethod.POST)
	public String searchAddressList(HttpServletRequest request) {
		Sessions session = this.getSessions(request);
		String companyPk = ServletUtils.getStringParameter(request, "companyPk","");
		if("".equals(companyPk)){
			companyPk = session.getCompanyPk();
		}
		Map<String,Object> map = this.paramsToMap(request);
		map.put("companyPk", companyPk);
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		PageModel<B2bAddressDto> pm =  addressService.searchAddressList(map);
		return RestCode.CODE_0000.toJson(pm);
	}
	
	/**
	 * 收货地址详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAddress", method = RequestMethod.POST)
	public String getAddress(HttpServletRequest request) {
		String rest = null;
		String pk = ServletUtils.getStringParameter(request, "pk","");
		if("".equals(pk)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			rest = RestCode.CODE_0000.toJson(addressService.getAddress(pk));
		}
		return rest;
	}
	
	/**
	 * 新增收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addAddress", method = RequestMethod.POST)
	public String addAddress(HttpServletRequest request) {
		Sessions session = this.getSessions(request);
		B2bAddressDto address = new B2bAddressDto();
		address.bind(request);
		String companyPk = ServletUtils.getStringParameter(request, "companyPk","");
		if("".equals(companyPk)){
			address.setCompanyPk(session.getCompanyPk());
		}else{
			address.setCompanyPk(companyPk);
		}
		return addressService.addAddress(address,session.getMemberPk());
	}
	
	/**
	 * 更新收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateAddress", method = RequestMethod.POST)
	public String updateAddress(HttpServletRequest request) {
		String result="";
		String pk = ServletUtils.getStringParameter(request, "pk","");
		if (null == pk || "".equals(pk)) {
			result = RestCode.CODE_A001.toJson();
		}else {
			Sessions session = this.getSessions(request);
			B2bAddressDto address = new B2bAddressDto();
			address.bind(request);
			String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
			if ("".equals(companyPk)) {
				address.setCompanyPk(session.getCompanyPk());
			} else {
				address.setCompanyPk(companyPk);
			}
			result =addressService.updateAddress(address);
		}
		return result;
	}
	
	/**
	 * 删除收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delAddress", method = RequestMethod.POST)
	public String delAddress(HttpServletRequest request) {
		String rest = null;
		String pk = ServletUtils.getStringParameter(request, "pk","");
		if("".equals(pk)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			rest = addressService.delAddress(pk);
		}
		return rest;
	}
}
