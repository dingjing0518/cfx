package cn.cf.service;

import cn.cf.PageModel;
import cn.cf.dto.B2bSxCertificateDto;
import cn.cf.dto.B2bSxCertificateDtoEx;

import java.util.List;
import java.util.Map;

public interface B2bSxCertificateService {


    String addCertificate(String companyPk,String companyName,String storePk,String certificateName);
    String updateCertificate(String pk,String companyName,String certificateName,String companyPk);
    String deleteCertificate(String pk);
    PageModel<B2bSxCertificateDtoEx> searchCertificate(Map<String, Object> map);
    List<B2bSxCertificateDto> getbyMap(Map<String, Object> map);
    List<B2bSxCertificateDto> searchbyMap(Map<String, Object> map);
    B2bSxCertificateDto getByPk(String pk);
}
