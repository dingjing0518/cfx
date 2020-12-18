package cn.cf.controller.economics;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bBillGoodsExtDto;
import cn.cf.dto.B2bCreditreportGoodsExtDto;
import cn.cf.dto.B2bEconomicsGoodsExDto;
import cn.cf.dto.B2bMemberExtDto;
import cn.cf.dto.B2bOnlinepayGoodsExtDto;
import cn.cf.dto.B2bStoreExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bBillGoods;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.B2bEconomicsGoodsService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.service.operation.B2bStoreService;
import cn.cf.util.ServletUtils;

/**
 * 金融产品
 * 
 * @author xht 2018年5月23日
 */
@Controller
@RequestMapping("/")
public class EcGoodsController extends BaseController {

    @Autowired
    private B2bEconomicsGoodsService economicsGoodsService;
    @Autowired
    private EconomicsBankService economicsBankService;

    @Autowired
    private B2bStoreService b2bStoreService;
    /**
     * 金融产品管理
     * 
     * @return
     */
    @RequestMapping("ecGoodsManager")
    public ModelAndView ecGoodsManager() {
        ModelAndView mav = new ModelAndView("economics/b2bEconomicsGoods");
        return mav;
    }

    /**
     * 列表展示
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("ecGoods_data")
    @ResponseBody
    public String ecGoods_data(HttpServletRequest request, HttpServletResponse response, B2bEconomicsGoodsExDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bEconomicsGoodsExDto> qm = new QueryModel<B2bEconomicsGoodsExDto>(dto, start, limit, orderName, orderType);
        PageModel<B2bEconomicsGoodsExDto> pm = economicsGoodsService.searchEcGoodsList(qm);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 更新
     * 
     * @param request
     * @param response
     * @param economicsGoods
     * @return
     * @throws ParseException
     */
    @RequestMapping("updateEcGoods")
    @ResponseBody
    public String updateEcGoods(HttpServletRequest request, HttpServletResponse response, B2bEconomicsGoodsExDto economicsGoods)
            throws ParseException {

        return economicsGoodsService.update(economicsGoods);
    }

    /**
     * 金融产品详情
     * 
     * @param request
     * @return
     */
    @RequestMapping("ecGoodsDetail")
    @ResponseBody
    public String ecGoodsDetail(HttpServletRequest request) {

        return JsonUtils.convertToString(economicsGoodsService.getByPk(ServletUtils.getStringParameter(request, "pk", "")));
    }

    /**
     * 金融产品管理
     *
     * @return
     */
    @RequestMapping("billGoodsManager")
    public ModelAndView billGoodsManager() {
        ModelAndView mav = new ModelAndView("economics/b2bBillGoods");
        mav.addObject("economicsBankList", economicsBankService.searchBankList());
        return mav;
    }

    /**
     * 列表展示
     *
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("searchBillGoodsList")
    @ResponseBody
    public String searchBillGoodsList(HttpServletRequest request, HttpServletResponse response, B2bBillGoodsExtDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bBillGoodsExtDto> qm = new QueryModel<>(dto, start, limit, orderName, orderType);
        PageModel<B2bBillGoodsExtDto> pm = economicsGoodsService.searchBillGoodsList(qm);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 新增票据
     *
     * @param request
     * @param response
     * @param dto
     * @return
     * @throws ParseException
     */
    @RequestMapping("insertBillGoods")
    @ResponseBody
    public String insertBillGoods(HttpServletRequest request, HttpServletResponse response, B2bBillGoods dto)
            throws ParseException {

        return economicsGoodsService.insertBillGoods(dto);
    }

    /**
     * 更新和修改票据状态
     *
     * @param request
     * @param response
     * @param dto
     * @return
     * @throws ParseException
     */
    @RequestMapping("updateBillGoods")
    @ResponseBody
    public String updateBillGoods(HttpServletRequest request, HttpServletResponse response, B2bBillGoodsExtDto dto)
            throws ParseException {

        return economicsGoodsService.updateBillGoods(dto);
    }



    /**
     * 在线支付商品管理
     *
     * @return
     */
    @RequestMapping("onlinePayGoods")
    public ModelAndView onlinePayGoods() {
        ModelAndView mav = new ModelAndView("economics/onlinePayGoods");
        mav.addObject("economicsBankList", economicsBankService.searchBankList());
        return mav;
    }

    /**
     * 在征信商品管理
     *
     * @return
     */
    @RequestMapping("creditReportGoods")
    public ModelAndView creditReportGoods() {
        ModelAndView mav = new ModelAndView("economics/creditReportGoods");
        mav.addObject("economicsBankList", economicsBankService.searchBankList());
        return mav;
    }

    /**
     * 列表展示
     *
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("searchOnlinePayGoodsList")
    @ResponseBody
    public String searchOnlinePayGoodsList(HttpServletRequest request, HttpServletResponse response, B2bOnlinepayGoodsExtDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "name");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bOnlinepayGoodsExtDto> qm = new QueryModel<>(dto, start, limit, orderName, orderType);
        PageModel<B2bOnlinepayGoodsExtDto> pm = economicsGoodsService.searchOnlinePayGoodsList(qm);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 列表展示
     *
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("searchCreditReportGoodsList")
    @ResponseBody
    public String searchCreditReportGoodsList(HttpServletRequest request, HttpServletResponse response, B2bCreditreportGoodsExtDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "name");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bCreditreportGoodsExtDto> qm = new QueryModel<>(dto, start, limit, orderName, orderType);
        PageModel<B2bCreditreportGoodsExtDto> pm = economicsGoodsService.searchCreditReportGoodsList(qm);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 更新
     *
     * @param request
     * @param response
     * @param dto
     * @return
     * @throws ParseException
     */
    @RequestMapping("updateOnlinePayGoods")
    @ResponseBody
    public String updateOnlinePayGoods(HttpServletRequest request, HttpServletResponse response, B2bOnlinepayGoodsExtDto dto)
            throws ParseException {

        return economicsGoodsService.updateOnlinePayGoods(dto);
    }

    /**
     * 更新在线支付商品状态
     *
     * @param request
     * @param response
     * @param dto
     * @return
     * @throws ParseException
     */
    @RequestMapping("updateStatusOnlinePayGoods")
    @ResponseBody
    public String updateStatusOnlinePayGoods(HttpServletRequest request, HttpServletResponse response, B2bOnlinepayGoodsExtDto dto)
            throws ParseException {

        return economicsGoodsService.updateStatusOnlinePayGoods(dto);
    }

    /**
     * 更新
     *
     * @param request
     * @param response
     * @param dto
     * @return
     * @throws ParseException
     */
    @RequestMapping("updateCreditReportGoods")
    @ResponseBody
    public String updateCreditReportGoods(HttpServletRequest request, HttpServletResponse response, B2bCreditreportGoodsExtDto dto)
            throws ParseException {

        return economicsGoodsService.updateCreditReportGoods(dto);
    }

    /**
     * 更新征信管理商品状态
     *
     * @param request
     * @param response
     * @param dto
     * @return
     * @throws ParseException
     */
    @RequestMapping("updateStatusCreditReportGoods")
    @ResponseBody
    public String updateStatusCreditReportGoods(HttpServletRequest request, HttpServletResponse response, B2bCreditreportGoodsExtDto dto)
            throws ParseException {

        return economicsGoodsService.updateStatusCreditReportGoods(dto);
    }
    
    
    /**
     * 绑定店铺功能-店铺列表
     * @param request
     * @param response
     * @param store
     * @return
     */
	@ResponseBody
	@RequestMapping("storeBind")
	public String storeBind(HttpServletRequest request, HttpServletResponse response, B2bStoreExtDto store) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bStoreExtDto> qm = new QueryModel<B2bStoreExtDto>(store, start, limit, orderName, orderType);
		PageModel<B2bStoreExtDto> pm = b2bStoreService.searchStoreBindList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}
}
