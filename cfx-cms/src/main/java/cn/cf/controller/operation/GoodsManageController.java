
/**
 *
 */
package cn.cf.controller.operation;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.chemifiber.GoodsRunnable;
import cn.cf.task.schedule.chemifiber.OrderRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品管理
 * @author why 
 */
@Controller
@RequestMapping("/")
public class GoodsManageController extends BaseController {

    @SuppressWarnings("unused")
    private final static Logger logger = Logger.getLogger(GoodsManageController.class);

    @Autowired
    private GoodsManageService goodsManageService;
    @Autowired
    private DynamicTask dynamicTask;

    
    /**
     * 商品管理显示页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("goodsUpper")
    public ModelAndView goodsUpper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/goodsUpper");
        mav.addObject("goodsBrandList", goodsManageService.getB2bGoodsBrandList(null));// 厂家品牌
        mav.addObject("productList", goodsManageService.getB2bProductList());// 品名
        mav.addObject("varietiesList", goodsManageService.getB2bVarietiesPidList());// 品种
        mav.addObject("specList", goodsManageService.getb2bSpecPid());
        mav.addObject("gradeList", goodsManageService.getB2bGradeList());
        return mav;
    }



    /**
     * 修改商品
     * @param request
     * @param b2bGoodsExtDto
     * @return
     */
    @RequestMapping("updateGoods")
    @ResponseBody
    public String updateGoods(HttpServletRequest request, B2bGoodsExtDto b2bGoodsExtDto) {
        int result = goodsManageService.updateGoods(b2bGoodsExtDto);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }


    /**
     *商品管理
     * @param request
     * @param response
     * @param goods
     * @return
     * @throws Exception
     */
    @RequestMapping("searchGoodsUpAndDownList")
    @ResponseBody
    public String searchGoodsUpAndDownList(HttpServletRequest request, HttpServletResponse response, B2bGoodsExtDto goods)
            throws Exception {
        ManageAccount account = this.getAccount(request);
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
        goods.setBlock("cf");
        QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods, start, limit, sort, dir);
        PageModel<B2bGoodsExtDto> pm = goodsManageService.searchGoodsUpAndDownList(qm,account,2);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 品种搜索
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("searchVarietiesList")
    @ResponseBody
    public String searchVarietiesList(HttpServletRequest request, HttpServletResponse response){

      return JsonUtils.convertToString(goodsManageService.getB2bVarietiesList());// 品种子品种
    }



    /**
     * 商品管理导出
     *
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("exportGoodsList")
    @ResponseBody
    public String exportGoodsList(HttpServletRequest request, HttpServletResponse response, GoodsDataTypeParams params)
            throws Exception {
        ManageAccount account = this.getAccount(request);
//
//        goods.setBlock("cf");
//        QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods, 0, 0, "insertTime", "desc");
//        List<B2bGoodsExtDto> list = goodsManageService.exportGoodsList(qm,account,2);
//        ExportUtil<B2bGoodsExtDto> export = new ExportUtil<B2bGoodsExtDto>();
//        export.exportUtil("goodsIsUpDown", list, response, request);

        params.setBlock("cf");
        String uuid  = KeyUtils.getUUID();
        params.setUuid(uuid);
        goodsManageService.saveGoodsExcelToOss(params,account,1);
        String name = Thread.currentThread().getName();//获取当前执行线程
        dynamicTask.startCron(new GoodsRunnable(name,uuid),name);
        return Constants.EXPORT_MSG;
    }
    
    
	@ResponseBody
	@RequestMapping("getExportGoodsNumbers")
	public String getExportGoodsNumbers(HttpServletRequest request, HttpServletResponse response, B2bGoodsExtDto goods) {
        List<B2bGoodsExtDto> list = goodsManageService.getExportGoodsNumbers(goods);
		if (list.size() > 5000) {
			return "fail";
		} else {
			return "success";
		}
	}

    /**
     * 查询采购商供应商公司集合列表
     *
     * @param request
     * @param response
     * @param b2bcompay
     * @return
     */
    @ResponseBody
    @RequestMapping("getChildCompanyList")
    public String getChildCompanyList(HttpServletRequest request, HttpServletResponse response,
                                      B2bCompanyExtDto b2bcompay) {
        List<B2bCompanyDto> list = goodsManageService.getB2bChildCompay(b2bcompay.getParentPk());

        String json = "";
        if (list != null && list.size() > 0) {
            json = JsonUtils.convertToString(list);
        }
        return json;
    }

    /**
     * 查询采购商供应商公司集合列表
     *
     * @param request
     * @param response
     * @param companyPk
     * @return
     */
    @ResponseBody
    @RequestMapping("getStoreByCompanyPk")
    public String getStoreByCompanyPk(HttpServletRequest request, HttpServletResponse response, String companyPk) {
        List<B2bStoreDto> list = goodsManageService.getB2bStoreByCompayPk(companyPk);

        String json = "";
        if (list != null && list.size() > 0) {
            json = JsonUtils.convertToString(list);
        }
        return json;
    }

    /***
     * 厂区
     *
     * @param storePk
     * @return
     */
    @ResponseBody
    @RequestMapping("getPlant")
    public String getPlant(String storePk) {
        List<B2bPlantDto> list = goodsManageService.getB2bPlantByStorePk(storePk);
        String json = JsonUtils.convertToString(list);
        return json;
    }

    /***
     * 仓库
     */
    @ResponseBody
    @RequestMapping("getWare")
    public String changeWare(String storePk) {
        List<B2bWareDto> list = goodsManageService.getB2bWareByPlant(storePk);
        String json = JsonUtils.convertToString(list);
        return json;
    }

    /***
     * 厂家品牌
     *
     * @param storePk
     * @return
     */
    @ResponseBody
    @RequestMapping("getCompanyBrand")
    public String changeCompanyBrand(String storePk) {
        List<B2bGoodsBrandDto> list = goodsManageService.getB2bGoodsBrandByStorePk(storePk);
        String json = JsonUtils.convertToString(list);
        return json;
    }

    /**
     * 查询过个系列
     *
     * @param parentPk
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getspecByPid")
    public String getspecByPid(String parentPk) {
        List<B2bSpecDto> list = goodsManageService.getb2bSpecByPid(parentPk);

        return JsonUtils.convertToString(list);
    }

    /**
     * 查询子品种
     *
     * @param parentPk
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getvarietiesByPid")
    public String getvarietiesByPid(String parentPk) {
        List<B2bVarietiesDto> list = goodsManageService.getB2bVarietiesByPidList(parentPk);

        return JsonUtils.convertToString(list);
    }

    /**
     * 商品品名页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("product")
    public ModelAndView product(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/product");
        return mav;
    }

    /**
     * 品名查询集合列表
     *
     * @param request
     * @param response
     * @param b2bProduct
     * @return
     */
    @RequestMapping("searchProduct")
    @ResponseBody
    public String searchProduct(HttpServletRequest request, HttpServletResponse response, B2bProductExtDto b2bProduct) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bProductExtDto> qm = new QueryModel<B2bProductExtDto>(b2bProduct, start, limit, orderName,
                orderType);
        PageModel<B2bProductExtDto> pm = goodsManageService.searchProductList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 修改商品品名
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateProduct")
    @ResponseBody
    public String updateProduct(HttpServletRequest request, HttpServletResponse response, B2bProductExtDto product) {
        int retVal = goodsManageService.updateProduct(product);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -1) {
            msg = Constants.PRODUCT_NAME_REPEAT;
        }
        return msg;
    }

    /**
     * 品种
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("varieties")
    public ModelAndView varieties(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/varieties");
        return mav;
    }

    /**
     * 子品种
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("seedvariety")
    public ModelAndView seedvariety(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/seedvariety");
        mav.addObject("varietiesList", goodsManageService.getB2bVarietiesByPidList("-1"));// 品种
        return mav;
    }

    /**
     * 等級
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("grade")
    public ModelAndView grade(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/grade");
        return mav;
    }

    /**
     * 等级搜索
     * @param request
     * @param response
     * @param b2bGrade
     * @return
     * @throws Exception
     */
    @RequestMapping("searchGrade")
    @ResponseBody
    public String searchGrade(HttpServletRequest request, HttpServletResponse response, B2bGradeExtDto b2bGrade)
            throws Exception {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
        QueryModel<B2bGradeExtDto> qm = new QueryModel<B2bGradeExtDto>(b2bGrade, start, limit, orderName, orderType);
        PageModel<B2bGradeExtDto> pm = goodsManageService.searchGrade(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 等级更新
     * @param b2bGrade
     * @return
     */
    @RequestMapping("updateGrade")
    @ResponseBody
    public String updateGrade(B2bGradeExtDto b2bGrade) {

        int retVal = goodsManageService.updateGrade(b2bGrade);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -1) {
            msg = "{\"success\":false,\"msg\":\"名称已存在,请重新定义名称!\"}";
        } else if (retVal == -2) {
            msg = "{\"success\":false,\"msg\":\"中文名称已存在,请重新定义!\"}";
        }
        return msg;
    }

    /**
     * 根据等级名称查询等级
     * @param name
     * @return
     */
    @RequestMapping("getGradeByName")
    @ResponseBody
    public String getGradeByName(String name) {
        B2bGradeDto dto = goodsManageService.getGradeByName(name);
        if (dto != null) {
            return JsonUtils.convertToString(dto);
        } else {
            return "";
        }
    }

    /**
     * 商品规格大类页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("spec")
    public ModelAndView spec(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/spec");
        return mav;
    }

    /**
     *  商品规格系列
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("series")
    public ModelAndView series(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/series");
        mav.addObject("specList", goodsManageService.getb2bSpecByPid("-1"));
        return mav;
    }

    /**
     * 商品规格大类查询集合列表
     *
     * @param request
     * @param response
     * @param b2bSpec
     * @return
     */
    @RequestMapping("searchSpec")
    @ResponseBody
    public String searchSpec(HttpServletRequest request, HttpServletResponse response, B2bSpecExtDto b2bSpec) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String parentPk = ServletUtils.getStringParameter(request, "parentPk", null);
        String noparentPk = ServletUtils.getStringParameter(request, "noparentPk", null);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bSpecExtDto> qm = new QueryModel<B2bSpecExtDto>(b2bSpec, start, limit, orderName, orderType);
        qm.putParams("parentPk", parentPk);
        qm.putParams("noparentPk", noparentPk);
        PageModel<B2bSpecExtDto> pm = goodsManageService.searchSpecList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 修改规格大类
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateSpec")
    @ResponseBody
    public String updateSpec(HttpServletRequest request, HttpServletResponse response, B2bSpecExtDto specExtDto) {
        int retVal = goodsManageService.updateSpec(specExtDto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -1) {
            msg = Constants.SPEC_NAME_REPEAT;
        }
        return msg;
    }

    /**
     * 商品品牌查询集合列表
     *
     * @param request
     * @param response
     * @param b2bBrand
     * @return
     */
    @RequestMapping("searchBrand")
    @ResponseBody
    public String searchBrand(HttpServletRequest request, HttpServletResponse response, B2bBrandExtDto b2bBrand) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
        QueryModel<B2bBrandExtDto> qm = new QueryModel<B2bBrandExtDto>(b2bBrand, start, limit, orderName, orderType);
        PageModel<B2bBrandExtDto> pm = goodsManageService.searchBrandList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 商品品牌页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("brand")
    public ModelAndView brand(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("operation/goodsmg/brand");
        return mav;
    }

    /**
     * 修改商品品牌
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateBrand")
    @ResponseBody
    public String updateBrand(HttpServletRequest request, HttpServletResponse response, B2bBrandExtDto extDto) {
        int retVal = goodsManageService.updateB2bBarnd(extDto);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (retVal == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (retVal == -1) {
            msg = Constants.PRODUCT_NAME_REPEAT;
        } else if (retVal == -10) {
            msg = "{\"success\":false,\"msg\":\"先在运营管理~厂家品牌列表解除关联关系!\"}";
        }
        return msg;
    }

    /**
     * 商品品种查询集合列表
     *
     * @param request
     * @param respons
     * @param b2bVarieties
     * @return
     */
    @RequestMapping("searchVarieties")
    @ResponseBody
    public String searchVarieties(HttpServletRequest request, HttpServletResponse respons,
                                  B2bVarietiesExtDto b2bVarieties) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
        String parentPk = ServletUtils.getStringParameter(request, "parentPk", null);
        String noparentPk = ServletUtils.getStringParameter(request, "noparentPk", null);
        QueryModel<B2bVarietiesExtDto> qm = new QueryModel<B2bVarietiesExtDto>(b2bVarieties, start, limit, orderName,
                orderType);
        qm.putParams("parentPk", parentPk);
        qm.putParams("noparentPk", noparentPk);
        PageModel<B2bVarietiesExtDto> pm = goodsManageService.searchVarietiesList(qm);
        String json = JsonUtils.convertToString(pm);

        return json;
    }
    /**
     * 更新商品品种
     * @param request
     * @param respons
     * @param b2bVarieties
     * @return
     */
    @RequestMapping("updateVarieties")
    @ResponseBody
    public String updateVarieties(HttpServletRequest request, HttpServletResponse respons,
                                  B2bVarietiesExtDto b2bVarieties) {

        int result = goodsManageService.updateVarieties(b2bVarieties);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } 
        else if (result == 0) {
            msg = Constants.RESULT_FAIL_MSG;
        } else if (result == -1) {
            msg = Constants.VARIETIES_NAME_REPEAT;
        } else if (result == -2) {
            msg = Constants.VARIETIES_NAME_REPEAT;
        }else if (result == -3) {
            msg = Constants.SEEDVARIETIES_NAME_REPEAT;
        }
        return msg;
    }

}
