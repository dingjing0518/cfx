package cn.cf.controller.yarn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.common.Constants;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.service.yarn.SxSpecExtService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.chemifiber.OrderGoodsRunnable;
import cn.cf.task.schedule.yarn.YarnGoodsRunnable;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.service.yarn.SxGoodsManageService;
import cn.cf.service.yarn.YarnHomeDisplayService;
import cn.cf.util.ServletUtils;

/**
 * 纱线商品
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class SxGoodsManageController  extends BaseController {

    @Autowired
    private GoodsManageService goodsManageService;
    
    @Autowired
    private YarnHomeDisplayService  yarnHomeDisplayService;
    
    
    @Autowired
    private SxGoodsManageService  sxGoodsManageService;

    @Autowired
    private SxSpecExtService sxSpecExtService;

    @Autowired
    private DynamicTask dynamicTask;
    
    /**
     * 商品管理页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("yarnGoods")
    public ModelAndView yarnGoods (HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("yarn/goodsmgr/goodsManage");
        mav.addObject("goodsBrandList",  goodsManageService.getB2bGoodsBrandList(null));// 厂家品牌
        mav.addObject("materialList", yarnHomeDisplayService.getNoIsVisMaterialList("-1"));// 原料一级
        mav.addObject("technologyList", yarnHomeDisplayService.getNoIsVisTechnologyList());// 工艺
        mav.addObject("sxSpecList", sxSpecExtService.searchAllSxSpecList());// 规格
        return mav;
    }
    
    
    /**
     * 纱线商品
     * @param request
     * @param response
     * @param goods
     * @return
     * @throws Exception
     */
    @RequestMapping("searchGoodsManageList")
    @ResponseBody
    public String searchGoodsManageList(HttpServletRequest request, HttpServletResponse response, B2bGoodsExtDto goods)
            throws Exception {
        ManageAccount account = this.getAccount(request);
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
        QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods, start, limit, sort, dir);
        PageModel<B2bGoodsExtDto> pm = sxGoodsManageService.searchGoodsManageList(qm,account,1);
        return JsonUtils.convertToString(pm);
    }

    
    
    /**
     * 纱线商品导出
     *
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("exportSxGoodsList")
    @ResponseBody
    public String exportGoodsList(HttpServletRequest request, HttpServletResponse response, GoodsDataTypeParams params)
            throws Exception {
        ManageAccount account = this.getAccount(request);

//        String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//        String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
//        QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods, 0, 0, sort, dir);
//        List<B2bGoodsExtDto> pm = sxGoodsManageService.getExportGoodsNumbers(qm,account,1);
//        ExportUtil<B2bGoodsExtDto> export = new ExportUtil<B2bGoodsExtDto>();
//        export.exportUtil("goodsManage", pm, response, request);

        String uuid = KeyUtils.getUUID();
        params.setUuid(uuid);
        sxGoodsManageService.saveYarnGoodsToOss(params,account);
        String name = Thread.currentThread().getName();//获取当前执行线程
        dynamicTask.startCron(new YarnGoodsRunnable(name,uuid),name);
        return Constants.EXPORT_MSG;
    }
    
    
	@ResponseBody
	@RequestMapping("getExportSxGoodsNumbers")
	public String getExportSxGoodsNumbers(HttpServletRequest request, HttpServletResponse response, B2bGoodsExtDto goods) {
		ManageAccount account = this.getAccount(request);
		QueryModel<B2bGoodsExtDto> qm = new QueryModel<B2bGoodsExtDto>(goods, 0, 0, null, null);
        List<B2bGoodsExtDto> list = sxGoodsManageService.getExportGoodsNumbers(qm,account,2);
		if (list.size() > 5000) {
			return "fail";
		} else {
			return "success";
		}
	}

    
    
}
