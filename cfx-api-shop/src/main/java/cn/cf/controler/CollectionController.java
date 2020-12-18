package cn.cf.controler;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.cf.dto.B2bGoodsDtoEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.entity.CollectionCompany;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.Sessions;
import cn.cf.service.CollectionService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * @author:ZJ
 * @describe:收藏接口汇总
 * @time:
 */
@RestController
@RequestMapping("/shop")
public class CollectionController extends BaseController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 收藏商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/collectGoods", method = RequestMethod.POST)
    public String collectGoods(HttpServletRequest request) {
        RestCode restCode = RestCode.CODE_0000;
        Sessions session = this.getSessions(request);
        String goodsPk = ServletUtils.getStringParameter(request, "goodsPk", "");
        if (goodsPk == null || "".equals(goodsPk)||session ==null) {
            restCode = RestCode.CODE_A001;
        } else {
            try {
                restCode = collectionService.collectGoods(goodsPk, session);
            } catch (Exception e) {
                e.printStackTrace();
                restCode = RestCode.CODE_S999;
            }
        }
        return restCode.toJson();
    }

    /**
     * 收藏公司
     *前端暂未用2019-07-08 zlb
     * @param request
     * @return
     */
    @RequestMapping(value = "/collectCompany", method = RequestMethod.POST)
    public String collectCompany(HttpServletRequest request) {
        RestCode restCode = RestCode.CODE_0000;
        Sessions session = this.getSessions(request);
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        if (companyPk == null || "".equals(companyPk)) {
            restCode = RestCode.CODE_A001;
        } else {
            try {
                restCode = collectionService.collectCompany(companyPk, session);
            } catch (Exception e) {
                e.printStackTrace();
                restCode = RestCode.CODE_S999;
            }
        }
        return restCode.toJson();
    }


    /**
     * 收藏店铺
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/collectStore", method = RequestMethod.POST)
    public String collectStore(HttpServletRequest request) {
        RestCode restCode = RestCode.CODE_0000;
        Sessions session = this.getSessions(request);
        String storePk = ServletUtils.getStringParameter(request, "storePk", "");
        if (storePk == null || "".equals(storePk)) {
            restCode = RestCode.CODE_A001;
        } else {
            try {
                restCode = collectionService.collectStore(storePk, session);
            } catch (Exception e) {
                e.printStackTrace();
                restCode = RestCode.CODE_S999;
            }
        }
        return restCode.toJson();
    }

    /*
     * 取消公司收藏
     */
    @RequestMapping(value = "/removeCompanys", method = RequestMethod.POST)
    public String removeCompanys(HttpServletRequest request) {
        Sessions session = this.getSessions(request);
        CollectionCompany dto = new CollectionCompany();
        dto.bind(request);
        RestCode res = collectionService.removeByCompanyIds(dto, session);
        return res.toJson();
    }

    /*
     * 取消商品收藏
     */
    @RequestMapping(value = "/removeGoodsIds", method = RequestMethod.POST)
    public String removeGoodsId(HttpServletRequest request) {
        Sessions session = this.getSessions(request);
        CollectionGoods dto = new CollectionGoods();
        dto.bind(request);
        RestCode res = collectionService.removeByGoodsId(session, dto);
        return res.toJson();
    }

    /*
       * 取消所有商品收藏
       * 前端暂未用2019-07-08 zlb
       */
    @RequestMapping(value = "/removeGoods", method = RequestMethod.POST)
    public void removeGOods(HttpServletRequest request) {
        String sessionId = ServletUtils
                .getStringParameter(request, "sessionId");
        try {
            collectionService.removeByUserId(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收藏公司列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/collectionCompanyList", method = RequestMethod.POST)
    public String collectionCompanyList(HttpServletRequest request) {
        Sessions session = this.getSessions(request);
        Map<String, Object> map = this.paramsToMap(request);
        map.put("memberPk", session.getMemberPk());
        PageModel<CollectionCompany> pm = collectionService.searchCompanyList(map);
        return RestCode.CODE_0000.toJson(pm);
    }

    /**
     * 收藏商品列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/collectionGoodsList", method = RequestMethod.POST)
    public String collectionGoodsList(HttpServletRequest request) {
        Sessions session = this.getSessions(request);
        Map<String, Object> map = this.paramsToMap(request);
        map.put("memberPk", session.getMemberPk());
        map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
        map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
        PageModel<B2bGoodsDtoEx> pm = collectionService.searchGoodList(map);
        return RestCode.CODE_0000.toJson(pm);
    }


}
