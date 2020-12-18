package cn.cf.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.model.LgMember;

/**
 * Created by Thinkpad on 2017/10/16.
 */
public interface LgMemberDaoEx extends  LgMemberDao{

	LgMemberDtoEx getByMobile(java.lang.String mobile);

    int update(LgMember dto);
    
    //根据memberPk查询
    LgMemberDtoEx getMemberByPk(@Param("memberPk") String memberPk);

	int isReapetMobile(Map<String, Object> map);

	List<LgMemberDtoEx> searchRoleMember(Map<String, Object> map);

	int countRoleMember(Map<String, Object> map);

	
    
}
