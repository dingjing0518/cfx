package cn.cf.dao;

import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgCompanyExtDto;
import cn.cf.model.LgCompany;

import java.util.List;
import java.util.Map;

public interface LgCompanyExtDao {

    List<LgCompanyDto> searchList (Map<String, Object> map);

    LgCompanyExtDto getCompanyByPk(String logisticsCompanyPk);

    LgCompanyExtDto searchPkByName(String companyName);

    int searchVaildName(Map<String, Object> map);

    int searchVaildMobile(Map<String, Object> map);

    int insertContacts(LgCompany model);

    /**
     * 更新承运商联系人
     * @param model
     * @return
     */
    int updateContacts(LgCompany model);

    //根据pk查询物流承运商
    LgCompanyDto getLgCompanyByPk(String pk);

    int searchGridCount(Map<String, Object> map);

    List<LgCompanyExtDto> searchGrid(Map<String, Object> map);

    int insert(LgCompany model);

    /**
     * 更新承运商
     * @param model
     * @return
     */
	int updateEx(LgCompany model);
}
