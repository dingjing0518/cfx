package cn.cf.dao;

import cn.cf.dto.B2bSxCertificateDto;

import java.util.List;
import java.util.Map;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 * */
public interface B2bSxCertificateDao {

    int insert(B2bSxCertificateDto model);
    int update(B2bSxCertificateDto model);
    int delete(String id);
    B2bSxCertificateDto getCertificate(String pk);
    List<B2bSxCertificateDto> searchGrid(Map<String, Object> map);
    List<B2bSxCertificateDto> searchList(Map<String, Object> map);
    List<B2bSxCertificateDto> searchByMap(Map<String, Object> map);
    int searchGridCount(Map<String, Object> map);
}
