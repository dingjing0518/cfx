package cn.cf.controler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bSxCertificateDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bSxCertificateService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 *
 * @description:证书增删改查
 * @author WYZ
 * @date 2018-4-17 下午3:13:34
 */
@RestController
@RequestMapping("shop")
public class B2bSxCertificateController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(B2bCompanyController.class);

    @Autowired
    private B2bSxCertificateService b2bSxCertificateService;


    /**
     * 添加证书
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addCertificate", method = RequestMethod.POST)
    public String addCertificate(HttpServletRequest request) {
        String restCode = RestCode.CODE_0000.toJson();
        Sessions session = this.getSessions(request);
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        String companyName = ServletUtils.getStringParameter(request, "companyName", "");
        String certificateName = ServletUtils.getStringParameter(request, "certificateName", "");
        String storePk = "";
        if (null != session.getCompanyDto() && null != session.getStoreDto() || null != session.getStoreDto().getPk() && !"".equals(session.getStoreDto().getPk())) {
            storePk = session.getStoreDto().getPk();
        }
        if ("".equals(companyPk) || "".equals(certificateName)) {
            restCode = RestCode.CODE_A001.toJson();
        }else{
        	restCode = b2bSxCertificateService.addCertificate(companyPk, companyName, storePk, certificateName);
        }
        return restCode;


    }


    /***
     *   删除证书
     */
    @RequestMapping(value = "deleteCertificate", method = RequestMethod.POST)
    public String deleteCertificate (HttpServletRequest request) {
        String restCode = RestCode.CODE_0000.toJson();
        String pk = ServletUtils.getStringParameter(request, "pk", "");

        if(null!=pk&&!"".equals(pk)){
            restCode = b2bSxCertificateService.deleteCertificate(pk);
        }else{
            restCode=RestCode.CODE_A001.toJson();
        }

        return restCode;
    }

    /***
     *   查询证书列表
     */
    @RequestMapping(value = "searchCertificate", method = RequestMethod.POST)
    public String searchCertificate(HttpServletRequest req) {
        String result = RestCode.CODE_0000.toJson();
        Sessions session = this.getSessions(req);
        try {
            String storePk = "";
            if (null != session.getCompanyDto() && null != session.getStoreDto() || null != session.getStoreDto().getPk() && !"".equals(session.getStoreDto().getPk())) {
                storePk = session.getStoreDto().getPk();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyPk",ServletUtils.getStringParameter(req, "companyPk", ""));
            map.put("start", ServletUtils.getIntParameter(req, "start", 0));
            map.put("limit", ServletUtils.getIntParameter(req, "limit", 15));
            map.put("orderName", ServletUtils.getStringParameter (req, "orderName", "insertTime"));
            map.put("orderType", ServletUtils.getStringParameter(req, "orderType", "desc"));
            map.put("storePk", storePk);

            PageModel<B2bSxCertificateDtoEx> pm = b2bSxCertificateService.searchCertificate(map);
            result = RestCode.CODE_0000.toJson(pm);
        } catch (Exception e) {
            logger.error("searchCertificate Error", e);
            e.printStackTrace();
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

    /**
     * 编辑证书
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateCertificate", method = RequestMethod.POST)
    public String updateCertificate(HttpServletRequest request) {
        String restCode = RestCode.CODE_0000.toJson();
        String pk = ServletUtils.getStringParameter(request, "pk", "");
        String companyName = ServletUtils.getStringParameter(request, "companyName", "");
        String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
        String certificateName = ServletUtils.getStringParameter(request, "certificateName", "");
        if ("".equals(companyPk) || "".equals(certificateName)) {
            restCode = RestCode.CODE_A001.toJson();
        }
        restCode = b2bSxCertificateService.updateCertificate(pk,companyName,certificateName,companyPk);
        return restCode;
    }

    @RequestMapping(value = "getCertificateByPk", method = RequestMethod.POST)
    public  String getCertificateByPk(HttpServletRequest request) {
        String result = null;
        String pk = ServletUtils.getStringParameter(request, "pk", "");
        if (pk.equals("")) {
            result = RestCode.CODE_A004.toJson();
        }else{
            result = RestCode.CODE_0000.toJson(b2bSxCertificateService.getByPk(pk));
        }
        return result;
    }

}