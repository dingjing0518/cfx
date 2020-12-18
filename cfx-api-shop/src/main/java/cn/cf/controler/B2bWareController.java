package cn.cf.controler;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWareDtoEx;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bWare;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bWareService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:仓库接口管理
 * @author XHT
 * @date 2018-4-13 下午5:20:27
 */
@RestController
@RequestMapping("shop")
public class B2bWareController extends BaseController {

	@Autowired
	private B2bFacadeService facadeService;

	@Autowired
	private B2bWareService wareService;

	/**
	 * 编辑/添加仓库
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addWare", method = RequestMethod.POST)
	public String addWare(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			Sessions sessions = this.getSessions(req);
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			B2bStoreDto storeDto = sessions.getStoreDto();
			if (null == company) {
				restCode = RestCode.CODE_M008;
			} else {
				B2bWareDto dto = new B2bWareDto();
				dto.bind(req);
				dto.setStorePk(storeDto.getPk());
				restCode = facadeService.addWare(dto,sessions.getMemberPk());
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}

	/**
	 * 删除仓库
	 * @param req
	 * 
	 */
	@RequestMapping(value = "updateWare", method = RequestMethod.POST)
	public String updateWare(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		B2bWare model = new B2bWare();
		model.bind(req);
		Integer result = wareService.update(model);
		if (result == 0) {
			restCode = RestCode.CODE_A001;
		}
		return restCode.toJson();
	}

	/**
	 * 仓库列表 limit 
	 * @param request -1 :全部
	 * @return
	 */
	@RequestMapping(value = "searchWareList", method = RequestMethod.POST)
	public String searchWareList(HttpServletRequest request) {
		String result = null;
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		Sessions sessions = this.getSessions(request);
		B2bStoreDto storeDto =sessions.getStoreDto();
		if (null == company) {
			result = RestCode.CODE_M008.toJson();
		} else {
			Map<String, Object> map = this.paramsToMap(request);
			try {
				map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
				map.put("storePk", null==storeDto.getPk()?"":storeDto.getPk());
				map.put("isDelete", 1);
				PageModel<B2bWareDtoEx> pm = wareService.searchWareList(map);
				result = RestCode.CODE_0000.toJson(pm);
			} catch (Exception e) {
				e.printStackTrace();
				result = RestCode.CODE_S999.toJson();
			}
		}
		return result;
	}
	
	/**********************************以下库位号*************************************************/
	
	/**
	 * 增删改 库位号
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "updateWareNumber", method = RequestMethod.POST)
	public String updateWareNumber(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			Sessions sessions = this.getSessions(req);
			B2bStoreDto storeDto =sessions.getStoreDto();
			if (null == company) {
				restCode = RestCode.CODE_M008;
			} else {
				B2bWarehouseNumberDto dto = new B2bWarehouseNumberDto();
				dto.bind(req);
				dto.setStorePk(storeDto.getPk());
				restCode = facadeService.addWareNumber(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}

	/**
	 * 库位号列表 limit 
	 *  
	 */
	@RequestMapping(value = "searchWareNumberList", method = RequestMethod.POST)
	public String searchWareNumberList(HttpServletRequest request) {
		String result = null;
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		Sessions sessions = this.getSessions(request);
		B2bStoreDto storeDto =sessions.getStoreDto();
		if (null == company) {
			result = RestCode.CODE_M008.toJson();
		} else {
			Map<String, Object> map = this.paramsToMap(request);
			try {
				map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
				map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
				map.put("orderName", request.getParameter("orderName")!=null?request.getParameter("orderName"):"updateTime");
				map.put("orderType", request.getParameter("orderType")!=null?request.getParameter("orderType"):"desc");
				map.put("storePk", null==storeDto.getPk()?"":storeDto.getPk());
				PageModel<B2bWarehouseNumberDto> pm = wareService.searchWareNumberList(map);
				result = RestCode.CODE_0000.toJson(pm);
			} catch (Exception e) {
				e.printStackTrace();
				result = RestCode.CODE_S999.toJson();
			}
		}
		return result;
	}
	
	@RequestMapping(value = "wareNumberDetail", method = RequestMethod.POST)
	public String wareNumberDetail(HttpServletRequest request) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		B2bWarehouseNumberDto dto = wareService.searchWareNumberByPk(pk);
		return RestCode.CODE_0000.toJson(dto);
	}

}
