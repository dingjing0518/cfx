package cn.cf.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.GoodsType;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bGoodsEx;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bGoodsService;
import cn.cf.service.FileOperationService;
import cn.cf.util.ServletUtils;
import cn.cf.util.StringUtils;
import cn.cf.utils.BaseController;

/**
 * @author:XHT
 * @describe:商品接口汇总
 * @time:2017-5-23 下午3:32:11
 */
@RestController
@RequestMapping("/shop")
public class B2bGoodsController extends BaseController {

    @Autowired
    private B2bGoodsService goodsService;

    @Autowired
    private B2bFacadeService facadeService;

    @Autowired
    private FileOperationService fileOperationService;

   // private final static Logger logger = LoggerFactory.getLogger(B2bCompanyController.class);

 

      
    /**
     * 更新一个或多个商品
     *
     * @param request
     * @param isRecommend 是否推荐(0否 1是)
     * @param isUpdown    1待上架2上架3下架
     * @return
     */
    @RequestMapping(value = "updateGoods", method = RequestMethod.POST)
    public String updateGoods(HttpServletRequest request) {
        RestCode restCode = RestCode.CODE_0000;
        String pk = ServletUtils.getStringParameter(request, "pk", "");
        Integer isUpdown = ServletUtils.getIntParameter(request, "isUpdown", -1);
        if (null == pk || pk.equals("")) {
            restCode = RestCode.CODE_A001;
        } else {
            B2bGoodsEx good = new B2bGoodsEx();
            good.setPk(pk);
            good.setIsUpdown(isUpdown);
            restCode = goodsService.updateMore(good);
        }
        return restCode.toJson();
    }

    

    /**
     * 商品导出Excel
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportGoods", method = RequestMethod.POST)
    public void exportGoods(HttpServletRequest request, HttpServletResponse response) {
        Sessions sessions=this.getSessions(request);
        Map<String, Object> map = this.paramsToMap(request);
        map.put("searchKey", ServletUtils.getStringParameter(request, "searchKey", ""));
        System.out.println("searchKey:"+map.get("searchKey"));
        map.put("searchType", 1);
        //商品导出显示正常 预售 特卖的数据
        map.put("goodsType", new String[]{GoodsType.NORMAL.getValue(),GoodsType.PRESALE.getValue(),GoodsType.SALE.getValue()});
        fileOperationService.exportGoods(goodsService.searchGoodsList(map, sessions), request, response,sessions);
    }

    

    /**
     * 商品类型
     */
    @RequestMapping(value = "b2bgoodTypes", method = RequestMethod.POST)
    public String b2bgoodTypes(HttpServletRequest req) {
        List<B2bGoodsDto> list = new ArrayList<B2bGoodsDto>();
        Map<String, String> map = new HashMap<String, String>();
        // map.put("rare", "紧货");
        map.put("presale", "预售");
        map.put("auction", "竞拍");
        map.put("binding", "绑定");
        map.put("normal", "正常");
        map.put("sale", "特卖");
        for (String key : map.keySet()) {
            B2bGoodsDto good = new B2bGoodsDto();
            good.setPk(key);
            list.add(good);
        }
        return RestCode.CODE_0000.toJson(list);
    }

    /***
     * 查询某个公司下的所有销售中商品
     */
    @RequestMapping(value = "goodsListByCompany", method = RequestMethod.POST)
    public String goodsListByCompany(HttpServletRequest req) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("storePk", ServletUtils.getStringParameter(req, "storePk", ""));
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            map.put("productPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "productPk", "")));
            map.put("specPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "specPk", "")));
            map.put("seriesPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "seriesPk", "")));
            map.put("varietiesPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "varietiesPk", "")));
            map.put("seedvarietyPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "seedvarietyPk", "")));
            map.put("brandPk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "brandPk", "")));
            map.put("gradePk", StringUtils.splitStrs(ServletUtils
                    .getStringParameter(req, "gradePk", "")));
            map.put("orderName", req.getParameter("orderName"));
            map.put("orderType", req.getParameter("orderType"));
            map.put("searchKey",
                    ServletUtils.getStringParameter(req, "searchKey", ""));
            map.put("isNewProduct", 0);
            map.put("auction", "auction");//查询非竞拍的商品
            map.put("binding", "binding");//查询非拼团的商品
            map.put("technologyPk", StringUtils.splitStrs(ServletUtils.getStringParameter(req, "technologyPk", "")));
            map.put("rawMaterialPk", StringUtils.splitStrs(ServletUtils.getStringParameter(req, "rawMaterialPk", "")));
            map.put("rawMaterialParentPk", StringUtils.splitStrs(ServletUtils.getStringParameter(req, "rawMaterialParentPk", "")));
            PageModel<B2bGoodsDtoEx> pm = facadeService.getB2bGoodsList(map);
            return RestCode.CODE_0000.toJson(pm);
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toString();
        }
    }
    
    
    
    
    /***
     * 查询某个公司下的所有销售中商品
     */
    @RequestMapping(value = "searchRecomendGoodsList", method = RequestMethod.POST)
    public String searchRecomendGoodsList(HttpServletRequest req) {
    	 Map<String, Object> map = paramsToMap(req);
         return RestCode.CODE_0000.toJson(goodsService.searchRecomendGoodsList(map));   
    }
}
