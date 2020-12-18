package cn.cf.controller;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.*;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.service.B2bBindService;
import cn.cf.service.B2bFacadeService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:拼团
 * @date 2018-8-17 下午3:13:34
 */
@RestController
@RequestMapping("trade")
public class B2bBindController  extends BaseController {


    @Autowired
    private B2bBindService b2bBindService;
    
    @Autowired
    private B2bBindGoodsService b2bBindGoodsService;
    
    @Autowired
    private B2bFacadeService facadeService;
    
	/**
	 * 添加   拼团活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addBind", method = RequestMethod.POST)
	public String addAuction(HttpServletRequest request) {
		B2bStoreDto store = this.getStoreBysessionsId(request);
		String goodsPks = ServletUtils.getStringParameter(request, "goodsPks","");//商品pk,多个时逗号隔开
        /*if (null==goodsPks||"".equals(goodsPks)) {
            return RestCode.CODE_A001.toJson();
        }*/
		/*bindName//拼团活动名称
		bindProductCount//拼团件数
		bindReason//拼团原因
		bindProductDetails//拼团产品描述
		bindProductPrice//拼团单价
*/		
		B2bBindDto bindDto=new B2bBindDto();
		bindDto.bind(request);
		bindDto.setStorePk(store.getPk());
		bindDto.setStoreName(store.getName());
		RestCode restCode = facadeService.addBind(bindDto,goodsPks);//添加拼团活动
		return restCode.toJson();
	}
    
	
	/**
	 * 编辑 拼团活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "editBind", method = RequestMethod.POST)
	public String editBind(HttpServletRequest request) {
		String pk = ServletUtils.getStringParameter(request, "pk","");//活动pk
		String goodsPks = ServletUtils.getStringParameter(request, "goodsPks","");//商品pk,多个时逗号隔开
        if (null==pk||"".equals(pk)) {
            return RestCode.CODE_P025.toJson();
        }
		B2bBindDto bindDto=new B2bBindDto();
		bindDto.bind(request);
		RestCode restCode = facadeService.editBind(bindDto,goodsPks);//编辑拼团活动
		return restCode.toJson();
	}
	
	
    /**
     * 拼团活动列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getBindList", method = RequestMethod.POST)
    public String getBindList(HttpServletRequest request) {
    	Map<String,Object> map = new HashMap<String,Object>();
		B2bStoreDto store = this.getStoreBysessionsId(request);
		map.put("bindName", ServletUtils.getStringParameter(request, "bindName"));//团购名称
        map.put("bindProductID", ServletUtils.getStringParameter(request, "bindProductID"));//产品编码
        map.put("status", ServletUtils.getStringParameter(request, "status"));//状态 0未成团 1已成团 2失效
        map.put("storePk", store.getPk() == null ? "" : store.getPk());
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
        map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
        int isUpDown=ServletUtils.getIntParameter(request, "isUpDown", 0);//上架状态，0：全部，1：上架，2：下架
        map.put("isUpDown", isUpDown);
        PageModelBind<B2bBindDtoEx> pm = b2bBindService.searchBindList(map);
        return RestCode.CODE_0000.toJson(pm);
    }
    
	/**
	 * 删除拼团活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delBind", method = RequestMethod.POST)
	public String delBind(HttpServletRequest request) {
		String pk=ServletUtils.getStringParameter(request, "pk");//拼团活动pk
		if (null==pk || "".equals(pk)) {
			return RestCode.CODE_P025.toJson();
		}
		RestCode restCode = facadeService.delBind(pk);
		return restCode.toJson();
	}
	
	
	
    /**
     * 拼团活动详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getBind", method = RequestMethod.POST)
    public String getBind(HttpServletRequest request) {
        String pk = ServletUtils.getStringParameter(request, "pk", "");
        if ("".equals(pk)) {
        	return RestCode.CODE_P025.toJson();
        } 
        B2bBindDto bind = b2bBindService.getBind(pk);
        if (null== bind||null==bind.getPk()) {
        	return RestCode.CODE_S999.toJson();
		}else {
			return RestCode.CODE_0000.toJson(bind);
		}
    }
    

    /**
     * 某个拼团活动下的商品列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getBindGoodsListByBindPk", method = RequestMethod.POST)
    public String getBindGoodsListByBindPk(HttpServletRequest request) {
        String bindPk = ServletUtils.getStringParameter(request, "bindPk","");
        if (null==bindPk || "".equals(bindPk)) {
            return RestCode.CODE_P025.toJson();
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("bindPk", bindPk);
        map.put("keyword", ServletUtils.getStringParameter(request, "keyword"));//商品信息
        map.put("batchNumber", ServletUtils.getStringParameter(request, "batchNumber"));//主批号
        map.put("distinctNumber", ServletUtils.getStringParameter(request, "distinctNumber"));//区分号
        map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
        map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
        PageModel<B2bBindGoodsDtoEx> pm = b2bBindGoodsService.searchBindGoodList(map);
        return RestCode.CODE_0000.toJson(pm);
    }
    
    
	/**
	 * 查询供拼团选择的商品
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchBindGoodsList", method = RequestMethod.POST)
	public String searchBindGoodsList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		B2bStoreDto store = this.getStoreBysessionsId(request);
		map.put("storePk", store.getPk()==null?"":store.getPk());
		map.put("searchKey", ServletUtils.getStringParameter(request, "searchKey"));//搜索关键词
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		try {
			PageModel<B2bGoodsDtoEx> pm = facadeService.searchBindGoodsList(map);
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
    
	
	/**
	 * 拼团中商品
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchGoodsListOnBind", method = RequestMethod.POST)
	public String searchGoodsListOnBind(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		B2bStoreDto store = this.getStoreBysessionsId(request);
		map = this.paramsToMap(request);
		map.put("storePk", store.getPk()==null?"":store.getPk());
		map.put("searchKey", ServletUtils.getStringParameter(request, "searchKey"));//搜索关键词
		map.put("isUpdown", ServletUtils.getStringParameter(request, "isUpdown"));//搜索关键词
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		try {
			PageModel<B2bBindGoodsDtoEx> pm = facadeService.searchGoodsListOnBind(map);
			return RestCode.CODE_0000.toJson(pm);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	 /**
     * 拼团商品修改商品类型
     * @param request
     * @return
     */
	@RequestMapping(value = "backToNormalGoods", method = RequestMethod.POST)
    public String backToNormalGoods(HttpServletRequest request) {
        String goodsPks = ServletUtils.getStringParameter(request, "goodsPks","");//商品pk,多个时逗号隔开
        String type = ServletUtils.getStringParameter(request, "type","");//商品类型
        if (null==goodsPks||"".equals(goodsPks)) {
            return RestCode.CODE_P014.toJson();
        }
        RestCode restCode = facadeService.backToNormalGoods(goodsPks,type);
        return restCode.toJson();
    }
	
	
	
	/**
	 * 团购活动List
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getBindLists", method = RequestMethod.POST)
	public String getBindLists(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		B2bStoreDto store = this.getStoreBysessionsId(request);
		map.put("storePk", store.getPk()==null?"":store.getPk());
		try {
			List<B2bBindDto> list = b2bBindService.getBindLists(map);
			return RestCode.CODE_0000.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
    
	
	 /**
     * 设置拼团活动
     * @param request
     * @return
     */
	@RequestMapping(value = "setBindingActivity", method = RequestMethod.POST)
    public String setBindingActivity(HttpServletRequest request) {
        String goodsPks = ServletUtils.getStringParameter(request, "goodsPks","");//商品pk,多个时逗号隔开
        String bindPk = ServletUtils.getStringParameter(request, "bindPk","");//拼团pk
        if (null==goodsPks||"".equals(goodsPks)) {
            return RestCode.CODE_P014.toJson();
        }
        if (null==bindPk || "".equals(bindPk)) {
        	return RestCode.CODE_P025.toJson();
		}
        
        RestCode restCode = facadeService.setBindingActivity(goodsPks,bindPk);
        return restCode.toJson();
    }

	
	 /**
     * 上架/下架   拼团活动
     * @param request
     * @return
     */
	@RequestMapping(value = "upDownBind", method = RequestMethod.POST)
    public String upDownBind(HttpServletRequest request) {
        String pk = ServletUtils.getStringParameter(request, "pk","");//拼团pk
        int isUpDown = ServletUtils.getIntParameter(request, "isUpDown", 0);//上下架 1:上架， 2:下架
        if (null==pk || "".equals(pk)  ) {
            return RestCode.CODE_P025.toJson();
        }
        if (isUpDown==0) {
        	return RestCode.CODE_P006.toJson();
		}
        RestCode restCode = facadeService.upDownBinding(pk,isUpDown);
        return restCode.toJson();
    }
	
	 /**
     * 删除某个团下的商品
     * @param request
     * @return
     */
	@RequestMapping(value = "delGoodsInBind", method = RequestMethod.POST)
    public String delGoodsInBind(HttpServletRequest request) {
		String bindPk = ServletUtils.getStringParameter(request, "bindPk","");//拼团pk
        String goodsPks = ServletUtils.getStringParameter(request, "goodsPks","");//商品pk
        if (null==bindPk||"".equals(bindPk)) {
            return RestCode.CODE_P025.toJson();
        }
        if ( null==goodsPks || "".equals(goodsPks)) {
        	return RestCode.CODE_P014.toJson();
		}
        RestCode restCode = facadeService.delGoodsInBind(bindPk,goodsPks);
        return restCode.toJson();
    }
	

}
