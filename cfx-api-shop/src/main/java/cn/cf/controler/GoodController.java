package cn.cf.controler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bGoodsEx;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.FacadeService;
import cn.cf.util.ServletUtils;
import cn.cf.util.StringUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("/shop")
public class GoodController   extends BaseController {
	
	 private final static Logger logger = LoggerFactory.getLogger(GoodController.class);

    @Autowired
    private FacadeService facadeService;
    
    @Autowired
    private B2bGoodsService goodsService;
    /**
     * 供应商：添加/编辑商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    public String addGoods(HttpServletRequest request) {
        RestCode restCode = RestCode.CODE_0000;
         Sessions session=this.getSessions(request);
        if (null == session.getCompanyDto()) {
            restCode = RestCode.CODE_M008;
        } else {
            B2bGoodsEx good = new B2bGoodsEx();
            good.bind(request);
            if(null==good.getGoodsInfo()||"".equals(good.getGoodsInfo())){
            	 restCode = RestCode.CODE_A001;
            }else{
            	  restCode = facadeService.addGood(good,session);
            }
          
        }
        return restCode.toJson();
    }
    /**
     * 商品详情 
     *
     * @param request
     * 
     * @return
     */
    @RequestMapping(value = "getGoodDetails", method = RequestMethod.POST)
    public String getGoodDetails(HttpServletRequest request) {
        String result = null;
        String pk = ServletUtils.getStringParameter(request, "pk");
        if (pk == null || "".equals(pk)) {
            result = RestCode.CODE_A001.toJson();
        } else {
            try {
                Sessions session = this.getSessions(request);
                String memberPk = "";
                if (session != null) {
                    memberPk = session.getMemberPk();
                }
                B2bGoodsDtoEx goods = facadeService.getB2bGoodsByPk(pk, memberPk);
                result = RestCode.CODE_0000.toJson(goods);
            } catch (Exception e) {
                e.printStackTrace();
                result = RestCode.CODE_S999.toJson();
            }
        }

        return result;
    }
    
    
    /**
     * 商品列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "searchGoodsList", method = RequestMethod.POST)
    public String searchGoodsList(HttpServletRequest request) {
        String result = null;
        try {
            Map<String, Object> map = this.paramsToMap(request);
            map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
            map.put("orderName", request.getParameter("orderName"));
            map.put("orderType", request.getParameter("orderType"));
            map.put("productPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "productPk", "")));
            map.put("specPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "specPk", "")));
            map.put("seriesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seriesPk", "")));
            map.put("varietiesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "varietiesPk", "")));
            map.put("seedvarietyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seedvarietyPk", "")));
            map.put("brandPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "brandPk", "")));
            map.put("brandPks", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "brandPks", "")));//关联查b2b_goods_brand 的brandPk
            map.put("gradePk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "gradePk", "")));
            map.put("searchKey", ServletUtils.getStringParameter(request, "searchKey", ""));
            map.put("technologyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "technologyPk", "")));
            map.put("rawMaterialPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialPk", "")));
            map.put("rawMaterialParentPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialParentPk", "")));
            map.put("isDelete", 1);
            map.put("isUpdown", 2);// 上架
            map.put("isNewProduct", 0);// 商城不展示新品
            map.put("companyStatus", 1);// 商城列表的商品不显示公司未审核通过以及删除了的
            map.put("auction", "auction");//查询非竞拍的商品
            map.put("binding", "binding");//查询非拼团的商品
            map.put("searchType", 1);
            PageModel<B2bGoodsDtoEx> pm = goodsService.searchGoodsList(map);
            result = RestCode.CODE_0000.toJson(pm);
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }
    
    
    @RequestMapping(value = "getGoodDetailsByPks", method = RequestMethod.POST)
    public String getGoodDetailsByPks(HttpServletRequest request) {
        String result = null;
        String pk = ServletUtils.getStringParameter(request, "pks");
        if (pk == null || "".equals(pk)) {
            result = RestCode.CODE_A001.toJson();
        } else {
            try {
            	List<B2bGoodsDto> goods=goodsService.getGoodDetailsByPks(pk);
                result = RestCode.CODE_0000.toJson(goods);
            } catch (Exception e) {
                e.printStackTrace();
                result = RestCode.CODE_S999.toJson();
            }
        }

        return result;
    }
    
    /**
     * 供应商 商品列表
     */
    @RequestMapping(value = "searchGoodsListByMember", method = RequestMethod.POST)
    public String searchGoodsListByMember(HttpServletRequest request) {
        String result = null;
        Sessions session =this.getSessions(request);
        if (null == session.getCompanyDto()) {
            result = RestCode.CODE_M008.toJson();
        } else {
            Map<String, Object> map = this.paramsToMap(request);
            try {
                map.put("storePk", session.getStoreDto() == null||session.getStoreDto().getPk()==null ? "" : session.getStoreDto().getPk());
                if (!"-1".equals(session.getCompanyDto().getParentPk())) {
                    map.put("companyPk", session.getCompanyDto().getPk());
                }
                map.put("isDelete", 1);
                map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
                map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
                map.put("orderName",request.getParameter("orderName") != null ? request.getParameter("orderName") : "updateTime");
                map.put("orderType", request.getParameter("orderType") != null ? request.getParameter("orderType") : "desc");
                map.put("productPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "productPk", "")));
                map.put("specPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "specPk", "")));
                map.put("seriesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seriesPk", "")));
                map.put("varietiesPk",StringUtils.splitStrs(ServletUtils.getStringParameter(request, "varietiesPk", "")));
                map.put("seedvarietyPk",StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seedvarietyPk", "")));
                map.put("brandPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "brandPk", "")));
                map.put("gradePk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "gradePk", "")));
                map.put("batchNumber", ServletUtils.getStringParameter(request, "batchNumber", ""));
                map.put("type", ServletUtils.getStringParameter(request, "type", ""));
                map.put("isUpdown", ServletUtils.getStringParameter(request, "isUpdown", ""));
                map.put("technologyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "technologyPk", "")));
                map.put("rawMaterialPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialPk", "")));
                map.put("rawMaterialParentPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialParentPk", "")));
                map.put("auction", "auction");//查询非竞拍的商品
                map.put("binding", "binding");//查询非拼团的商品
                PageModel<B2bGoodsDtoEx> pm = goodsService.searchGoodsList(map);
                result = RestCode.CODE_0000.toJson(pm);
            } catch (Exception e) {
                e.printStackTrace();
                result = RestCode.CODE_S999.toJson();
            }
        }
        return result;
    }
    

    /***
     * 上下架数量 1未上架，2上架，3 下架
     */
    @RequestMapping(value = "searchUpdownCounts", method = RequestMethod.POST)
    public String searchUpdownCounts(HttpServletRequest request) {
        String result = null;
        Sessions session =this.getSessions(request);
        if (null == session.getCompanyDto()) {
            result = RestCode.CODE_M008.toJson();
        } else {
            Map<String, Object> map = this.paramsToMap(request);
            String type = ServletUtils.getStringParameter(request, "type", "");
            map = this.paramsToMap(request);
            map.put("storePk", session.getStoreDto() == null ||session.getStoreDto().getPk()==null? "" : session.getStoreDto().getPk());
            if (!"-1".equals(session.getCompanyDto().getParentPk())) {
                map.put("companyPk", session.getCompanyDto().getPk());
            }
            map.put("isDelete", 1);

            map.put("type", ServletUtils.getStringParameter(request, "type", ""));
            map.put("productPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "productPk", "")));
            map.put("specPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "specPk", "")));
            map.put("seriesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seriesPk", "")));
            map.put("varietiesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "varietiesPk", "")));
            map.put("seedvarietyPk",StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seedvarietyPk", "")));
            map.put("brandPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "brandPk", "")));
            map.put("gradePk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "gradePk", "")));
            map.put("auction", "auction");//查询非竞拍的商品
            map.put("binding", "binding");//查询非拼团的商品
            map.put("technologyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "technologyPk", "")));
            map.put("rawMaterialPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialPk", "")));
            map.put("rawMaterialParentPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialParentPk", "")));
            
            if(!"".equals(type)){
            	map.remove("auction");
            	map.remove("binding");
            }
            List<B2bGoodsDtoEx> list = goodsService.searchUpdownCounts(map);
            result = RestCode.CODE_0000.toJson(list);
        }
        return result;
    }
    
    /**
     * 商品导入(暂时只支持更新)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importGoods", method = RequestMethod.POST)
    public String importGoods(HttpServletRequest request, HttpServletResponse response) {
        RestCode restCode = RestCode.CODE_0000;
        String strs = ServletUtils.getStringParameter(request, "json", "");
       Sessions session =this.getSessions(request);
        if (null == session.getCompanyDto()) {
            restCode = RestCode.CODE_M008;
        } else {
            if (!"".equals(strs)) {
                restCode = facadeService.importGoods(strs, session);
            } else {
                restCode = RestCode.CODE_A001;
            }
        }
        return restCode.toJson();
    }
    
    /**
     * 代采业务员可见商品列表
     */
    @RequestMapping(value = "b2bGoodsListBySalesman", method = RequestMethod.POST)
    public String b2bGoodsListBySalesman(HttpServletRequest request) {
        Sessions session = this.getSessions(request);
        Map<String, Object> map = this.paramsToMap(request);
        try {
            map.put("isDelete", 1);
            map.put("isUpdown", 2);
            map.put("storePk", session.getStoreDto().getPk());
            map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
            map.put("orderName",request.getParameter("orderName") != null ? request.getParameter("orderName") : "updateTime");
            map.put("orderType",request.getParameter("orderType") != null ? request.getParameter("orderType") : "desc");
           
            map.put("productPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "productPk", "")));
            map.put("specPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "specPk", "")));
            map.put("seriesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seriesPk", "")));
            map.put("varietiesPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "varietiesPk", "")));
            map.put("seedvarietyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "seedvarietyPk", "")));
            map.put("brandPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "brandPk", "")));
            map.put("gradePk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "gradePk", "")));
            map.put("technologyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "technologyPk", "")));
            map.put("rawMaterialPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialPk", "")));
            map.put("rawMaterialParentPk", StringUtils.splitStrs(ServletUtils.getStringParameter(request, "rawMaterialParentPk", "")));
            
            PageModel<B2bGoodsDtoEx> pm = facadeService.searchGoodsListBySalesMan(map, session.getIsAdmin(), session.getMemberDto(), session.getCompanyDto());
            return RestCode.CODE_0000.toJson(pm);
        } catch (Exception e) {
        	logger.error("b2bGoodsListBySalesman",e);
            return RestCode.CODE_S999.toJson();
        }
    }
    
    

}
