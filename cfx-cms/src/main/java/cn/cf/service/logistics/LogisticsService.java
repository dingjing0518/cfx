package cn.cf.service.logistics;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgCompanyExtDto;
import cn.cf.model.LgCompanyEx;

public interface LogisticsService {

    /***
     * 物流公司名称
     * 
     * @return
     */
    List<LgCompanyDto> getLgCompanyList();

    /**
     * 全部物流公司
     * 
     * @return
     */
    List<LgCompanyDto> getAllLgCompanyList();

    /**
     * 根据名称查找物流公司
     * 
     * @param companyName
     * @return
     */
    LgCompanyDto searchPkByName(String companyName);

    /**
     * 物流承运商列表
     * 
     * @param qm
     * @param accountPk 
     * @return
     */
    PageModel<LgCompanyExtDto> searchCompanyList(QueryModel<LgCompanyExtDto> qm, String accountPk);

    /**
     * 新增/编辑物流供应商
     * 
     * @param compay
     * @return
     */
    @Transactional
    Integer LgCompany(LgCompanyEx compay);

    /**
     * 公司是否已注册
     * 
     * @param map
     * @return
     */
    int getLgCompanyByVaildCompany(Map<String, Object> map);

    /**
     * 手机号是否已注册
     * 
     * @param map
     * @return
     */
    int getLgCompanyByVaildMobile(Map<String, Object> map);

    /**
     * 禁用/启用物流供应商
     * 
     * @param isVisable
     * @param pk
     * @return
     */
    int editLogisticsCompanyState(Integer isVisable, String pk);

    /**
     * 根据主键查找公司
     * 
     * @param pk
     * @return
     */
    LgCompanyDto getLgCompanyByPk(String pk);

}
