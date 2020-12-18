package cn.cf.dao;


import cn.cf.dto.LgMemberDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LgMemberExtDao {

    //根据memberPk查询
    LgMemberDto getMemberByPk(@Param("memberPk") String memberPk);

    List<LgMemberDto> getMemberByCompanyPk(@Param("logisticsCompanyPk") String logisticsCompanyPk);

	void updateByCompanyPk(Map<String, Object> map);
}
