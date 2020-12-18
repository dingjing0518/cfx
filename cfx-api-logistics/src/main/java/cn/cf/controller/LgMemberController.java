package cn.cf.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.LgArayacakMandateDto;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgLogisticsTrackingDtoEx;
import cn.cf.dto.LgUserAddressDto;
import cn.cf.dto.LgUserInvoiceDto;
import cn.cf.dto.OrderDeliveryDto;
import cn.cf.dto.PageModelOrderVo;
import cn.cf.entity.Sessions;
import cn.cf.model.LgArayacakMandate;
import cn.cf.model.LgPayType;
import cn.cf.model.LgUserInvoice;
import cn.cf.property.PropertyConfig;
import cn.cf.service.LgArayacakMandateService;
import cn.cf.service.LgDeliveryOrderService;
import cn.cf.service.LgLogisticsTrackingService;
import cn.cf.service.LgPayTypeService;
import cn.cf.service.LgUserAddressService;
import cn.cf.service.LgUserInvoiceService;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;


/**
 * @author wangc
 *         物流系统 “个人中心”所有接口
 */
@RestController
@RequestMapping("/logistics/member")
public class LgMemberController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LgMemberController.class);

    @Autowired
    private LgUserAddressService lgUserAddressService;

    @Autowired
    private LgUserInvoiceService lgUserInvoiceService;

    @Autowired
    private LgArayacakMandateService lgArayacakMandateService;

    @Autowired
    private LgDeliveryOrderService lgDeliveryOrderService;

    @Autowired
    private LgPayTypeService lgPayTypeService;
    
    @Autowired
    private LgLogisticsTrackingService TrackingService;
    
    /**
     * 收货/提货地址列表信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/searchAddressList", method = RequestMethod.POST)
    public String address(HttpServletRequest req) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(req);
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            Sessions session = this.getSessions(req);
            String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
            map.put("userPk", session.getMemberPk());
            if (companyPk == null || "".equals(companyPk)) {
                map.put("companyPk", session.getCompanyPk() == null || session.getCompanyPk().equals("") ? "-1" : session.getCompanyPk());
            }
            PageModel<LgUserAddressDto> address = lgUserAddressService.searchAddress(map);
            result = RestCode.CODE_0000.toJson(address);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("searchAddressList exception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 收货/提货地址详情信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "getAddressInfo", method = RequestMethod.POST)
    public String getAddressInfo(HttpServletRequest req) {
        String result = "";
        try {
            String pk = ServletUtils.getStringParameter(req, "pk", "");
            if (null == pk || "".equals(pk)) {
                result = RestCode.CODE_A007.toJson();
                return result;
            }
            LgUserAddressDto dto = lgUserAddressService.searchAddressByPk(pk);
            result = RestCode.CODE_0000.toJson(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getAddressInfo exeception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 新增收货/提货地址
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "addAddress", method = RequestMethod.POST)
    public String addAddress(HttpServletRequest req) {
        String result = "";
        try {
            LgUserAddressDto dto = new LgUserAddressDto();
            dto.bind(req);
            Sessions session = this.getSessions(req);
            dto.setUserPk(session.getMemberPk());
            if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
                dto.setCompanyPk(session.getCompanyPk() == null ? "" : session.getCompanyPk());
                dto.setCompanyName(session.getCompanyName() == null ? "" : session.getCompanyName());
            }
            result = lgUserAddressService.addAddress(dto);
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 修改收货/提货地址
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public String updateAddrss(HttpServletRequest req) {
        String result = "";
        try {
            LgUserAddressDto dto = new LgUserAddressDto();
            dto.bind(req);
            if (dto == null || dto.getPk() == null || "".equals(dto.getPk())) {
                return RestCode.CODE_A001.toJson();
            }
            Sessions session = this.getSessions(req);
            dto.setUserPk(session.getMemberPk() == null ? "" : session.getMemberPk());
            dto.setCompanyPk(session.getCompanyPk() == null || session.getCompanyPk().equals("") ? "-1" : session.getCompanyPk());
            result = lgUserAddressService.updateAddress(dto);
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 删除收货/提货 地址
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "delAddress", method = RequestMethod.POST)
    public String delAddrss(HttpServletRequest req) {
        String result = "";
        try {
            String pk = ServletUtils.getStringParameter(req, "pk", "");
            if (pk == null || "".equals(pk)) {
                return RestCode.CODE_A007.toJson();
            }
            lgUserAddressService.delAddress(pk);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delAddress exception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }


    /**
     * 平台用户发票信息查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "getInvoiceInfo", method = RequestMethod.POST)
    public String invoiceInfo(HttpServletRequest req) {
        String result = "";
        try {
            Sessions session = this.getSessions(req);
            String companyPk = session.getCompanyPk() == null || session.getCompanyPk().equals("") ? "-1" : session.getCompanyPk();
            String userPk = session.getMemberPk();
            LgUserInvoice invoiceInfo = lgUserInvoiceService.getInvoiceInfo(companyPk, userPk);
            result = RestCode.CODE_0000.toJson(invoiceInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getInvoiceInfo exeception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 新增发票信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "addInvoiceInfo", method = RequestMethod.POST)
    public String addInvoiceInfo(HttpServletRequest req) {
        String result = "";
        try {
            LgUserInvoiceDto dto = new LgUserInvoiceDto();
            dto.bind(req);
            if (null == dto.getTaxidNumber() || "".equals(dto.getTaxidNumber())
                    || null == dto.getProvince() || "".equals(dto.getProvince()) || null == dto.getProvinceName() || "".equals(dto.getProvinceName())
                    || null == dto.getCity() || "".equals(dto.getCity()) || null == dto.getCityName() || "".equals(dto.getCityName())
                    || null == dto.getRegAddress() || "".equals(dto.getRegAddress()) || null == dto.getRegPhone() || "".equals(dto.getRegPhone())
                    || null == dto.getRecipt() || "".equals(dto.getRecipt())) {
                return RestCode.CODE_A007.toJson();
            }
            Sessions session = this.getSessions(req);
            dto.setUserPk(session.getMemberPk());
            if (dto.getCompanyPk() == null || "".equals(dto.getCompanyPk())) {
                dto.setCompanyPk(session.getCompanyPk() == null ? "" : session.getCompanyPk());
                dto.setCompanyName(session.getCompanyName() == null ? "" : session.getCompanyName());
            }
            result = lgUserInvoiceService.addInvoiceInfo(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("addInvoiceInfo exeception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }


    /**
     * 修改发票信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "updateInvoiceInfo", method = RequestMethod.POST)
    public String updateInvoiceInfo(HttpServletRequest req) {
        String result = "";
        try {
            LgUserInvoiceDto dto = new LgUserInvoiceDto();
            dto.bind(req);
            if (null == dto.getPk() || "".equals(dto.getPk())) {
                return RestCode.CODE_A007.toJson();
            }
            Sessions session = this.getSessions(req);
            dto.setCompanyPk(session.getCompanyPk() == null ? "" : session.getCompanyPk());
            dto.setCompanyName(session.getCompanyName() == null ? "" : session.getCompanyName());
            lgUserInvoiceService.updateInvoiceInfo(dto);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
            logger.error("updateInvoiceInfo exception:" + e);
        }
        return result;
    }

    /**
     * 自提委托书查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "searchMandateList", method = RequestMethod.POST)
    public String searchMandate(HttpServletRequest request) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            map.put("userPk", session.getMemberPk() == null ? "" : session.getMemberPk());
            map.put("companyPk", session.getCompanyPk() == null || session.getCompanyPk().equals("") ? "-1" : session.getCompanyPk());
            map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
            PageModel<LgArayacakMandate> dataList = lgArayacakMandateService.queryMandateByLimit(map);
            result = RestCode.CODE_0000.toJson(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
            logger.error("searchMandate exception:" + e);
        }
        return result;
    }

    /**
     * 新增自提委托书
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "addMandate", method = RequestMethod.POST)
    public String addMandate(HttpServletRequest request) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            if (map.get("mandateUrl") == null || "".equals(map.get("mandateUrl"))) {
                return RestCode.CODE_A007.toJson();
            }
            LgArayacakMandate model = new LgArayacakMandate();
            String pk = KeyUtils.getUUID();
            String userPk = session.getMemberPk() == null ? "" : session.getMemberPk();
            String userMobile = session.getMobile() == null ? "" : session.getMobile();
            String companyPk = session.getCompanyPk() == null ? "" : session.getCompanyPk();
            String companyName = session.getCompanyName() == null ? "" : session.getCompanyName();
            String mandateName = map.get("mandateName") == null ? "" : map.get("mandateName").toString();
            String mandateUrl = map.get("mandateUrl").toString();
            Date tempDate = new Date();
            model.setPk(pk);
            model.setUserPk(userPk);
            model.setUserMobile(userMobile);
            model.setCompanyPk(companyPk);
            model.setCompanyName(companyName);
            model.setMandateName(mandateName);
            model.setMandateUrl(mandateUrl);
            model.setInsertTime(tempDate);
            model.setUpdateTime(tempDate);
            model.setDelStatus(1);
            if (lgArayacakMandateService.insert(model) > 0) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("pk", pk);
                result = RestCode.CODE_0000.toJson(data);
            } else {
                result = RestCode.CODE_S999.toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("addMandate exception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 根据Pk查询自提委托书
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "getMandateByPk", method = RequestMethod.POST)
    public String getMandateByPk(HttpServletRequest request) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(request);
            if (null == map.get("mandatePk") || "".equals(map.get("mandatePk").toString())) {
                return RestCode.CODE_A007.toJson();
            }
            String mandatePk = map.get("mandatePk").toString();
            LgArayacakMandateDto lgArayacakMandate = lgArayacakMandateService.getMandateByPK(mandatePk);
            if (null == lgArayacakMandate) {
                result = RestCode.CODE_S999.toJson();
            } else {
                result = RestCode.CODE_0000.toJson(lgArayacakMandate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getMandateByPk exception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 修改自提委托书
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "updateMandate", method = RequestMethod.POST)
    public String updateMandate(HttpServletRequest request) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(request);
            if (null == map.get("mandatePk") || "".equals(map.get("mandatePk").toString())
                    || null == map.get("mandateUrl") || "".equals(map.get("mandateUrl").toString())) {
                return RestCode.CODE_A007.toJson();
            }
            LgArayacakMandateDto dto = lgArayacakMandateService.getMandateByPK(map.get("mandatePk").toString());
            Date tempDate = new Date();
            dto.setMandateUrl(map.get("mandateUrl").toString());
            dto.setUpdateTime(tempDate);
            if (lgArayacakMandateService.updateMandate(dto) > 0) {
                result = RestCode.CODE_0000.toJson();
            } else {
                result = RestCode.CODE_S999.toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateMandate exception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 删除自提委托书
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "deleteMandate", method = RequestMethod.POST)
    public String deleteMandate(HttpServletRequest request) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(request);
            if (null == map.get("mandatePk") || "".equals(map.get("mandatePk").toString())) {
                return RestCode.CODE_A007.toJson();
            }
            String mandatePk = map.get("mandatePk").toString();
            int delStatus = lgArayacakMandateService.checkDelStatusByPk(mandatePk);
            if (delStatus == 2) {
                return RestCode.CODE_S999.toJson();
            }
            if (lgArayacakMandateService.deleteLgArayacakMandate(mandatePk) > 0) {
                result = RestCode.CODE_0000.toJson();
            } else {
                result = RestCode.CODE_S999.toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
            logger.error("deleteMandate exception:" + e);
        }
        return result;
    }


    /**
     * 平台用户_订单管理_我的订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "deliveryOrderList", method = RequestMethod.POST)
    public String deliveryOrderList(HttpServletRequest req) {
        String result = "";
        try {
            Map<String, Object> par = this.paramsToMap(req);
            par.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            par.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            par.put("orderStatus", ServletUtils.getIntParameterr(req, "orderStatus", 0));//0:全部
			if (null != par.get("orderStatus") && par.get("orderStatus").toString().equals("0")) {
				par.remove("orderStatus");
				par.put("orderStatuses", "10,9,8,6,5,4,3,2");
			}
            Sessions session = this.getSessions(req);
            par.put("purAndSup", session.getCompanyPk() == null || session.getCompanyPk().equals("") ? "-1" : session.getCompanyPk());
            par.put("memberPk", session.getMemberPk() == null ? "" : session.getMemberPk());
            //par.put("isAbnormal", 1);
            PageModelOrderVo<OrderDeliveryDto> dataList = lgDeliveryOrderService.selectMemberDeliveryList(par);
            result = RestCode.CODE_0000.toJson(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }


    /**
     * 导出订单
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "exportOrder", method = RequestMethod.POST)
    public void exportOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sessions session = this.getSessions(request);
        Map<String, Object> par = this.paramsToMap(request);
//        if (session == null || session.getCompanyPk() == null || session.getCompanyPk().equals("")) {
//            par.put("purAndSup", "-1");
//        } else {
//            par.put("purAndSup", session.getCompanyPk()==null?"":session.getCompanyPk());
//            par.put("memberPk", session.getMemberPk()==null?"":session.getMemberPk());
//        }
        par.put("purAndSup", session.getCompanyPk() == null || session.getCompanyPk().equals("") ? "-1" : session.getCompanyPk());
        par.put("memberPk", session.getMemberPk() == null ? "" : session.getMemberPk());
        //par.put("isAbnormal", 1);
        if (null != par.get("orderStatus") && par.get("orderStatus").toString().equals("0")) {
            par.remove("orderStatus");
        }
        List<OrderDeliveryDto> list = lgDeliveryOrderService.exportDeliveryOrder(par);
        //String templateFileName = CommonUtil.getPath() + "MemberDeliveryOrder.xls";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("MemberDeliveryOrder.xls");
        String path = PropertyConfig.getProperty("excel_url");
        File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
        String fileName = "/order" + KeyUtils.getUUID() + ".xls";
        String destFileName = path + fileName;
        File file = new File(destFileName);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> beans = new HashMap<>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName));
        workbook.write(os); 
        os.flush();
        os.close();
        download(response, request, destFileName);
    }

    
    /**
     * 平台用户_订单管理_异常订单
     * @param req
     * @return
     */
    @RequestMapping(value = "abDeliveryOrderList", method = RequestMethod.POST)
    public String abDeliveryOrderList(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> par = this.paramsToMap(req);
            par.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            par.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            Sessions session = this.getSessions(req);
            par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            par.put("memberPk", session.getMemberPk()==null?"":session.getMemberPk());
            par.put("isAbnormal", 2);
            par.put("isMember", 2);
            PageModelOrderVo<OrderDeliveryDto> dataList = lgDeliveryOrderService.selectAbDeliveryList(par);
            result = RestCode.CODE_0000.toJson(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("abDeliveryOrderList exception============="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    
    
    /**
     * 异常订单导出
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "exportAbOrder", method = RequestMethod.POST)
    public void exportAbOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sessions session = this.getSessions(request);
        Map<String, Object> par = this.paramsToMap(request);
//        if (session == null || session.getCompanyPk() == null || session.getCompanyPk().equals("")) {
//            par.put("purAndSup", "-1");
//        } else {
//            par.put("purAndSup", session.getCompanyPk()==null?"":session.getCompanyPk());
//            par.put("memberPk", session.getMemberPk()==null?"":session.getMemberPk());
//        }
        par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
        par.put("memberPk", session.getMemberPk()==null?"":session.getMemberPk());
        par.put("isAbnormal", 2);
        par.put("isMember", 2);
        List<OrderDeliveryDto> list = lgDeliveryOrderService.exportDeliveryOrder(par);
        //String templateFileName = CommonUtil.getPath() + "SupplierDeliveryAbOrder.xls";
        String path = PropertyConfig.getProperty("excel_url");
        File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("MemberDeliveryOrder.xls");
        String fileName = "/order" + KeyUtils.getUUID() + ".xls";
        String destFileName = path + fileName;
        File file = new File(destFileName);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> beans = new HashMap<>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        Workbook workbook = transformer.transformXLS(resourceAsStream, beans);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileName));
        workbook.write(os); 
        os.flush();
        os.close();
        download(response, request, destFileName);
    }
    
    
    /**
     * 订单详细
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deliveryOrderDetail", method = RequestMethod.POST)
    public String deliveryOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        String result="";
    	try {
            Map<String, Object> par = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            if (null==par.get("deliveryPk")||"".equals(par.get("deliveryPk").toString())) {
				return RestCode.CODE_S999.toJson();
			}
            par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            HashMap<String, Object> data = lgDeliveryOrderService.selectDetailByDeliveryPkOnMemeber(par);
            result = RestCode.CODE_0000.toJson(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deliveryOrderDetail exception=========="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;

    }
    
    /**
     * 平台用户取消订单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cancelDelivery", method = RequestMethod.POST)
    public String cancelDelivery(HttpServletRequest request) {
        String result="";
    	try {
            Map<String, Object> par = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            if (null==par.get("deliveryPk")||"".equals(par.get("deliveryPk").toString())) {
				return RestCode.CODE_S999.toJson();
			}
            par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
            int orderStatus=lgDeliveryOrderService.checkOrderStatusByPk(deliveryPk);
            //只有订单状态为9待付款9的时候才可以取消订单
            if (orderStatus!=9) {
            	return RestCode.CODE_S999.toJson();
			}
            lgDeliveryOrderService.cancelDelivery(deliveryPk);
            result = RestCode.CODE_0000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("cancelDelivery exception:"+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    
    /**
     * 异常反馈_订单详情
     * @param request
     * @return
     */
/*    @RequestMapping(value = "/getDeliveryOrderByPk", method = RequestMethod.POST)
    public String getDeliveryOrderByPk(HttpServletRequest request) {
        try {
            Map<String, Object> par = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            String deliveryPk = ServletUtils.getStringParameter(request, "deliveryPk", "");
            LgDeliveryOrderDto dto = deOrService.getBypk(deliveryPk);
            return RestCode.CODE_0000.toJson(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }*/
    
    
    /**
     * 配送中-异常反馈
     * @param request
     * @return
     */
/*    @RequestMapping(value = "/abnormalFeedback", method = RequestMethod.POST)
    public String abnormalFeedback(HttpServletRequest request) {
        try {
            Map<String, Object> par = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            deOrService.abnormalFeedback(par);
            return RestCode.CODE_0000.toJson(null);
        } catch (ErrorParameterException par) {
            return RestCode.CODE_P000.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }*/
    
    
    
    /**
     * 平台用户提交订单
     * @return
     */
    @RequestMapping(value = "commitOrder", method = RequestMethod.POST)
    public String commitOrder(HttpServletRequest req) {
        try {
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            map.put("memberPk", session.getMemberPk());
            map.put("companyName", session.getCompanyName()==null?"":session.getCompanyName());
            map.put("companyPK",  session.getCompanyPk()==null?"":session.getCompanyPk());
            map.put("member", session.getMobile());//member  下单用户
    		map.get("fromAddressPk").toString();
    		map.get("toAddressPk").toString();
    		map.get("member").toString();
    		map.get("memberPk").toString();
    		map.get("remark").toString(); 
    		map.get("companyName").toString();
    		map.get("companyPK").toString();
    		map.get("invoicePk").toString();//发票PK
    		if (null == map.get("fromAddressPk")||"".equals(map.get("fromAddressPk").toString())) {
    			return RestCode.CODE_LO004.toJson();
			}
    		if (null == map.get("toAddressPk") || "".equals(map.get("toAddressPk").toString())) {
    			return RestCode.CODE_LO003.toJson();
			}
    		if (null == map.get("invoicePk")||"".equals(map.get("invoicePk").toString())) {
    			return RestCode.CODE_A008.toJson();
			}
    		try {
    			Double.parseDouble(map.get("weight").toString());//重量
    			Integer.parseInt(map.get("unit").toString());//单位(1:箱 2:锭 3:件 4:粒)
    			Integer.parseInt(map.get("boxes").toString());//箱数
			} catch (Exception e) {
				e.printStackTrace();
				return RestCode.CODE_P000.toJson();
			}
    		if (Integer.parseInt(map.get("boxes").toString())<=0) {
    			return RestCode.CODE_P000.toJson();
			}
    		RestCode restCode = lgDeliveryOrderService.commitOrder(map);
    		return restCode.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }
    
    
    /**
     * 付款前获取付款信息
     * @param req
     * @return
     */
    @RequestMapping(value = "getInfoBeforePay", method = RequestMethod.POST)
    public String getInfoBeforePay(HttpServletRequest req) {
        String result="";
    	try {
        	Map<String, Object> resultMap=new HashMap<>();
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            map.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            String deliveryPk=map.get("deliveryPk").toString();
            //订单的支付信息
            LgDeliveryOrderDto lgDeliveryOrderDto=lgDeliveryOrderService.getInfoBeforePay(deliveryPk);
            if (lgDeliveryOrderDto==null) {
				return RestCode.CODE_S999.toJson();
			}
            if (null==lgDeliveryOrderDto.getOrderStatus()||lgDeliveryOrderDto.getOrderStatus()!=9) {
            	return RestCode.CODE_L006.toJson();
			}
            resultMap.put("lgDeliveryOrderDto", lgDeliveryOrderDto);
            //支付方式的信息
            List<LgPayType> payTypes =lgPayTypeService.getPayTypes();
            resultMap.put("payTypes", payTypes);
            result = RestCode.CODE_0000.toJson(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getInfoBeforePay exception============"+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    
    
    /**
     * 物流订单支付，1：线下支付
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "payForLogistics1", method = RequestMethod.POST)
    public String payForLogistics1(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            //1:修改订单状态，2:新增支付记录
            String deliveryPk=map.get("deliveryPk").toString();
            String paymentPk=map.get("payTypePk").toString();
            if ("".equals(deliveryPk)||"".equals(paymentPk)) {
            	return RestCode.CODE_S999.toJson();
			}
            String paymentName=lgPayTypeService.getPayTypeNameByPK(paymentPk);
            Date tempDate=new Date();
            //检查订单是否已经支付过
            int orderStatus=lgDeliveryOrderService.checkOrderStatusByPk(deliveryPk);
            if (orderStatus!=9) {
            	return RestCode.CODE_S999.toJson();
			}
            int a=lgDeliveryOrderService.payForLogistics(deliveryPk,paymentPk,paymentName,tempDate);
            if (a>0) {
            	result = RestCode.CODE_0000.toJson();
			}else {
				result = RestCode.CODE_S999.toJson();
			}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("payForLogistics1 exception========"+e);
            result= RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    /**
     * 物流订单支付，2：月结
     *payType=2
     * @param req
     * @return
     */
    @RequestMapping(value = "payForLogistics2", method = RequestMethod.POST)
    public String payForLogistics2(HttpServletRequest req) {
    	String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            //1:修改订单状态，2:新增支付记录
            String deliveryPk=map.get("deliveryPk").toString();
            String paymentPk=map.get("payTypePk").toString();
            if ("".equals(deliveryPk)||"".equals(paymentPk)) {
            	return RestCode.CODE_S999.toJson();
			}
            String paymentName=lgPayTypeService.getPayTypeNameByPK(paymentPk);
            Date tempDate=new Date();
            //检查订单是否已经支付过
            int orderStatus=lgDeliveryOrderService.checkOrderStatusByPk(deliveryPk);
            if (orderStatus!=9) {
            	return RestCode.CODE_S999.toJson();
			}
            int a=lgDeliveryOrderService.payForLogistics(deliveryPk,paymentPk,paymentName,tempDate);
            if (a>0) {
            	result = RestCode.CODE_0000.toJson();
			}else {
				result = RestCode.CODE_S999.toJson();
			}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("payForLogistics2（月结） exception========"+e);
            return RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    /**
     * 再来一单获取数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getInfo4MoreOrder", method = RequestMethod.POST)
    public String getInfo4MoreOrder(HttpServletRequest request, HttpServletResponse response) {
        String result="";
    	try {
            Map<String, Object> par = this.paramsToMap(request);
            Sessions session = this.getSessions(request);
            par.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            HashMap<String, Object> data = lgDeliveryOrderService.getInfo4MoreOrder(par);
            result = RestCode.CODE_0000.toJson(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getInfo4MoreOrder exception========="+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    
    /**
     * 平台用户开票订单管理
     * @param req
     * @return
     */
    @RequestMapping(value = "memberBillOrderList", method = RequestMethod.POST)
    public String memberOrdersList(HttpServletRequest req) {
        String result="";
    	try {
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            map.put("purAndSup", session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk());
            map.put("memberPk", session.getMemberPk()==null?"":session.getMemberPk());
            map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
            map.put("orderName",ServletUtils.getStringParameter(req, "orderName", "updateTime"));
            map.put("orderType",ServletUtils.getStringParameter(req, "orderType", "desc"));
            String orderNumber = ServletUtils.getStringParameter(req, "orderNumber", "");
            if (!orderNumber.equals("")){
            	map.put("orderNumber", orderNumber);
            }
            PageModelOrderVo<OrderDeliveryDto> orderDeliveryDtoPageModelOrderVo = lgDeliveryOrderService.memberDeliveryOrder(map);
            result = RestCode.CODE_0000.toJson(orderDeliveryDtoPageModelOrderVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("memberBillOrderList esception============"+e);
            result = RestCode.CODE_S999.toJson();
        }
    	return result;
    }
    
    
    /**
     * 平台用户开票操作
     * @param req
     * @return
     */
    @RequestMapping(value = "memberBillOrders", method = RequestMethod.POST)
    public String billOrders(HttpServletRequest req) {
        try {
        	RestCode restCode=RestCode.CODE_0000;
            Map<String, Object> map = this.paramsToMap(req);
            Sessions session = this.getSessions(req);
            String companyPk = session.getCompanyPk()==null||session.getCompanyPk().equals("")?"-1":session.getCompanyPk();
            String userPk=session.getMemberPk();
            String mobile=session.getMobile();
            LgUserInvoice invoiceInfo = lgUserInvoiceService.getInvoiceInfo(companyPk,userPk);
            if (null==invoiceInfo ||null== invoiceInfo.getPk()||"".equals(invoiceInfo.getPk())) {
            	return RestCode.CODE_A008.toJson();
            }
            map.put("invoicePk", invoiceInfo.getPk()==null?"":invoiceInfo.getPk());
            map.put("contactTel", mobile);
            map.put("contactName", mobile);
            map.put("invoiceProvince", invoiceInfo.getProvince()==null?"":invoiceInfo.getProvince());
            map.put("invoiceProvinceName", invoiceInfo.getProvinceName()==null?"":invoiceInfo.getProvinceName());
            map.put("invoiceCity", invoiceInfo.getCity()==null?"":invoiceInfo.getCity());
            map.put("invoiceCityName", invoiceInfo.getCityName()==null?"":invoiceInfo.getCityName());
            map.put("invoiceArea", invoiceInfo.getArea()==null?"":invoiceInfo.getArea());
            map.put("invoiceAreaName", invoiceInfo.getAreaName()==null?"":invoiceInfo.getAreaName());
            map.put("contactAddress", invoiceInfo.getProvinceName()+invoiceInfo.getCityName()+invoiceInfo.getAreaName());
            map.put("bankAccount", invoiceInfo.getBankAccount()==null?"":invoiceInfo.getBankAccount());
            map.put("bankName", invoiceInfo.getBankName()==null?"":invoiceInfo.getBankName());
            map.put("regTel", invoiceInfo.getRegPhone()==null?"":invoiceInfo.getRegPhone());
            map.put("regAddress", invoiceInfo.getProvinceName()+invoiceInfo.getCityName()+invoiceInfo.getAreaName()+invoiceInfo.getRegAddress());
            map.put("shortRegAddress", invoiceInfo.getRegAddress()==null?"":invoiceInfo.getRegAddress());
            map.put("taxID", invoiceInfo.getTaxidNumber()==null?"":invoiceInfo.getTaxidNumber());
            map.put("name", invoiceInfo.getRecipt()==null?"":invoiceInfo.getRecipt());
            restCode = lgDeliveryOrderService.bill4member(map);
            return restCode.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }
    
    

    
    //下载文件
    public void download(HttpServletResponse response, HttpServletRequest request, String filePath) {
        File file = new File(filePath);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] b = new byte[1024];
        int len = 0;
        try {
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            response.setContentType("application/force-download");
            String filename = "订单报表.xls";
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.setContentLength((int) file.length());
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (file.exists() && file.isFile()) {
            file.delete();
        }

    }


	/**
	 * 货物所在地--
	 */
	@RequestMapping(value = "updateLgTracking", method = RequestMethod.POST)
	public String updateLgTracking(HttpServletRequest req) {
		RestCode code = RestCode.CODE_0000;
		LgLogisticsTrackingDtoEx dto = new LgLogisticsTrackingDtoEx();
		dto.bind(req);
		try {
			code = TrackingService.updateTracking(dto);
		} catch (Exception e) {
			logger.error("updateLgTracking", e);
			code = RestCode.CODE_S999;
		}

		return code.toJson();
	}
	
	/***
	 * 货物所在地列表
	 */
	@RequestMapping(value = "getLgTrackingsByPk", method = RequestMethod.POST)
	public String getLgTrackingsByPk(HttpServletRequest req) {
		String code =null;
		String orderPk=ServletUtils.getStringParameter(req, "orderPk");
	
		try {
			if("".equals(orderPk)){
				code = RestCode.CODE_P000.toJson();
			}else{
				code = RestCode.CODE_0000.toJson(TrackingService.getLgTrackingsByPk(orderPk));
			}
			
		} catch (Exception e) {
			logger.error("getLgTrackingsByPk", e);
			code = RestCode.CODE_S999.toJson();
		}

		return code;
	}
	/**
	 * 根据货物所在地pk 查看详情
	 */
	@RequestMapping(value = "searchLgTrackingsByPk", method = RequestMethod.POST)
	public String searchLgTrackingsByPk(HttpServletRequest req) {
		String code =null;
		String pk=ServletUtils.getStringParameter(req, "pk");
	
		try {
			if("".equals(pk)){
				code = RestCode.CODE_P000.toJson();
			}else{
				code = RestCode.CODE_0000.toJson(TrackingService.searchLgTrackingsByPk(pk));
			}
			
		} catch (Exception e) {
			logger.error("searchLgTrackingsByPk", e);
			code = RestCode.CODE_S999.toJson();
		}

		return code;
	}
}
