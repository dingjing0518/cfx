package cn.cf.controller.economics;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bProductPriceIndexDto;
import cn.cf.dto.B2bProductPriceIndexExtDto;
import cn.cf.dto.EconomicsLimitDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.PricProductIndexParams;
import cn.cf.entity.ProductPriceIndexEntry;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bProductPriceIndex;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.ProductPriceIndexService;
import cn.cf.task.schedule.economics.BillOrderRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProductPriceIndexController extends BaseController {


    @Autowired
    private ProductPriceIndexService productPriceIndexService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("priceIndex")
    public ModelAndView priceIndex(){
        ModelAndView mv = new ModelAndView("economics/report/priceIndex");
        mv.addObject("productList",productPriceIndexService.getProductList());
        return mv;
    }

    @ResponseBody
    @RequestMapping("searchProductPriceList")
    public String searchProductPriceList(HttpServletRequest request, HttpServletResponse response, B2bProductPriceIndexExtDto priceIndexDto) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<B2bProductPriceIndexExtDto> qm = new QueryModel<>(priceIndexDto, start, limit, orderName, orderType);
        PageModel<B2bProductPriceIndexExtDto> pm = productPriceIndexService.searchProductPriceIndexLis(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    @ResponseBody
    @RequestMapping("getProductPriceByPk")
    public String getProductPriceByPk(HttpServletRequest request, HttpServletResponse response, String pk) {
        return JsonUtils.convertToString(productPriceIndexService.getProductIndexByPk(pk));
    }

    @ResponseBody
    @RequestMapping("updateProductPrice")
    public String updateProductPrice(HttpServletRequest request, HttpServletResponse response, B2bProductPriceIndex priceIndex) {


        int result = productPriceIndexService.updateProductIndex(priceIndex);

        String msg = "";
        if (result > 0){
            msg = Constants.RESULT_SUCCESS_MSG;
        }else if(result == -1){
            msg = "{\"success\":false,\"msg\":\"存在相同品名的数据!\"}";
        } else{
            msg = Constants.RESULT_FAIL_MSG;
        }

        return msg;
    }

    @RequestMapping("transactionPriceIndex")
    public ModelAndView transactionPriceIndex(){
        ModelAndView mv = new ModelAndView("economics/report/transactionPriceIndex");
        return mv;
    }

    @ResponseBody
    @RequestMapping("searchTransactionProductPriceList")
    public String searchTransactionProductPriceList(HttpServletRequest request, HttpServletResponse response, ProductPriceIndexEntry priceIndexDto) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<ProductPriceIndexEntry> qm = new QueryModel<>(priceIndexDto, start, limit, orderName, orderType);
        PageModel<ProductPriceIndexEntry> pm = productPriceIndexService.searchTransactionProductPriceIndexLis(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    @ResponseBody
    @RequestMapping("updateTransactionProductPrice")
    public String updateTransactionProductPrice(HttpServletRequest request, HttpServletResponse response, Double dueofpayMount, String id) {
        ProductPriceIndexEntry indexEntry = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),ProductPriceIndexEntry.class);
        if (indexEntry != null){
            indexEntry.setDepositMount(new BigDecimal(dueofpayMount));
            indexEntry.setIsConfirm(Constants.TWO);
            mongoTemplate.save(indexEntry);
        }
        return Constants.RESULT_SUCCESS_MSG;
    }

    @RequestMapping("exportProductPriceIndex")
    @ResponseBody
    public String exportBillOrder(HttpServletRequest request, HttpServletResponse response,
                                  PricProductIndexParams params) throws Exception {
        ManageAccount account = getAccount(request);
        productPriceIndexService.saveProductPriceIndexExcelToOss(params,account);
        return Constants.EXPORT_MSG;
    }
}
