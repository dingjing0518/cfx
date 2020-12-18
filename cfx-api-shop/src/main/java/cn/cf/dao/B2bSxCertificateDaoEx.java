package cn.cf.dao;

import cn.cf.dto.B2bSxCertificateDtoEx;

import java.util.List;
import java.util.Map;

public interface B2bSxCertificateDaoEx {
    List<B2bSxCertificateDtoEx> searchList(Map<String, Object> map);

}
