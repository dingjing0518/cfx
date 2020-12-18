package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;

public interface B2bMemberDaoEx extends B2bMemberDao{

	List<B2bMemberDto> getAdmin(String companyPk);
	
	List<B2bMemberDto> getAdminByParent(String companyPk);
	
	List<B2bMemberDto> searchMemberByCompany(Map<String, Object> map);


	void updateParentPk(String parantPk);

	List<B2bMemberDtoEx> searchMemberGrid(Map<String, Object> map);

	B2bMemberDto isRepeat(Map<String, Object> map);

	
	List<B2bMemberDto> searchMemberBySmsRole(Map<String, String> map);

	int memberGridCount(Map<String, Object> map);

	B2bMemberDtoEx getMemberByPk(String memberPk);
	

	void upgradeMember(@Param("numberStart")int numberStart,@Param("numberEnd")int numberEnd, @Param("gradeNumber") int gradeNumber,@Param("gradeName") String gradeName) ;

	void upgradeMember2(@Param("numberStart")int numberStart,@Param("numberEnd")int numberEnd, @Param("gradeNumber") int gradeNumber,@Param("gradeName") String gradeName) ;

	int searchAccumulativeMember(Map<String, Object> map);
	
	List<B2bMemberDto> searchMemberByEmployeeNumber(Map<String, Object> map);

	void updateLoginTime(String pk);

}
