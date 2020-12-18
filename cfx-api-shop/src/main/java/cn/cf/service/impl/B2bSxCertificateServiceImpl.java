package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bGoodsDao;
import cn.cf.dao.B2bSxCertificateDao;
import cn.cf.dao.B2bSxCertificateDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bSxCertificateDto;
import cn.cf.dto.B2bSxCertificateDtoEx;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bSxCertificateService;
import cn.cf.util.KeyUtils;

@Service
public class B2bSxCertificateServiceImpl implements B2bSxCertificateService {
    @Autowired
    private    B2bSxCertificateService b2bSxCertificateService;
    @Autowired
    private B2bSxCertificateDao b2bSxCertificateDao;
    @Autowired
    private B2bGoodsDao b2bGoodsDao;
    @Autowired
    private B2bSxCertificateDaoEx b2bSxCertificateDaoEx;
    @Autowired
    private B2bCompanyService b2bCompanyService;


    @Override
    public String addCertificate(String companyPk, String companyName, String storePk, String certificateName) {
        String restCode = RestCode.CODE_0000.toJson();
        Map<String, Object> map = new HashMap<String, Object>();
        B2bSxCertificateDto model=new B2bSxCertificateDto();
        map.put("companyPk", companyPk);
        map.put("name", certificateName);
        List<B2bSxCertificateDto> b = b2bSxCertificateService.getbyMap(map);
        if (b != null && b.size() > 0) {
            restCode = RestCode.CODE_G022.toJson();
        }else{
            String pk = KeyUtils.getUUID();
            model.setPk(pk);
            model.setCompanyName(companyName);
            model.setCompanyPk(companyPk);
            model.setName(certificateName);
            model.setInsertTime(new Date());
            model.setStorePk(storePk);
            model.setIsDelete(1);
            b2bSxCertificateDao.insert(model);
        }
        return restCode;
    }

    @Override
    public String updateCertificate(String pk,String companyName ,String certificateName,String companyPk) {
        String restCode =RestCode.CODE_0000.toJson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyPk", companyPk);
        map.put("name", certificateName);
        map.put("pk",pk);
        List<B2bSxCertificateDto> b = b2bSxCertificateService.searchbyMap(map);
        if (b != null && b.size() > 0) {
            restCode = RestCode.CODE_G022.toJson();
        }else {
            B2bSxCertificateDto dto = b2bSxCertificateDao.getCertificate(pk);
            if (null != dto) {
                dto.setName(certificateName);
                dto.setCompanyName(companyName);
                dto.setCompanyPk(companyPk);
                b2bSxCertificateDao.update(dto);
            }
        }
        return restCode;
    }

    @Override
    public String deleteCertificate(String pk) {
        int result=b2bSxCertificateDao.delete(pk);
        if(result==1){
            return  RestCode.CODE_0000.toJson();
        }else{
            return  RestCode.CODE_S999.toJson();
        }
    }

    @Override
    public PageModel<B2bSxCertificateDtoEx> searchCertificate(Map<String, Object> map) {
        PageModel<B2bSxCertificateDtoEx> pm = new PageModel<B2bSxCertificateDtoEx>();
        Map<String, Object> m = new HashMap<String, Object>();
        String companyPk=map.get("companyPk").toString();
        B2bCompanyDto companyDto=b2bCompanyService.getCompanyDto(companyPk);
        String parentPk=companyDto.getParentPk();
        if (null!=parentPk&&parentPk.equals("-1")){
            map.put("companyPk","");
        }
        List<B2bSxCertificateDtoEx> list = b2bSxCertificateDaoEx.searchList(map);
        for (B2bSxCertificateDtoEx b : list ){
             String pk=b.getPk();
             m.put("certificatePk",pk);
             int count=b2bGoodsDao.searchGridCount(m);
             if (count>0){
               b.setIsRelevance(1);
             }else {
                 b.setIsRelevance(2);
             }

        }
        int count = b2bSxCertificateDao.searchGridCount(map);
        pm.setTotalCount(count);
        pm.setDataList(list);
        pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
        pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
        return pm;
    }


    @Override
    public List<B2bSxCertificateDto> getbyMap(Map<String, Object> map) {
        return b2bSxCertificateDao.searchGrid(map);
    }

    @Override
    public List<B2bSxCertificateDto> searchbyMap(Map<String, Object> map) {
        return b2bSxCertificateDao.searchByMap(map);
    }

    @Override
    public B2bSxCertificateDto getByPk(String pk) {
        return b2bSxCertificateDao.getCertificate(pk);
    }
}
