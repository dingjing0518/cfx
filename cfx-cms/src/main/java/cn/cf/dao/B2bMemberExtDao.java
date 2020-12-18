/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberExtDao extends  B2bMemberDao{
	
	/**
	 * 根据条件查询会员集合
	 * @param map
	 * @return
	 */
	List<B2bMemberExtDto> searchB2bMemberList(Map<String, Object> map);
	/**
	 * 根据条件统计会员数量
	 * @param map
	 * @return
	 */
	int searchB2bMemberCounts(Map<String, Object> map);

	/**
	 * 根据公司pk更新member表
	 * @param map
	 * @return
	 */
	int updateMemberByCompanyPk(Map<String, Object> map);
	
	B2bMemberDto searchMemberByEmployeeNumber(Map<String, Object> map);
	
	/**
	 * 解除与公司的关联关系
	 * @param memberPk
	 * @return
	 */
	int delB2bMemberComponyByPk(String memberPk);
	
	/**
	 * 公司设置超级管理员列表查询
	 * @param map
	 * @return
	 */
	List<B2bMemberExtDto> getMemberByAdminList(Map<String,Object> map);
	
	/**
	 * 公司设置超级管理员数量统计
	 * @param map
	 * @return
	 */
	int getMemberByAdminCount(Map<String,Object> map);


	/**
	 * 查询是否符合优惠券赠送条件的会员
	 * @param map
	 * @return
	 */
	B2bMemberDto searchMemberByCompanyPkAndTelAndName(Map<String,Object> map);
	B2bMemberDto searchAdminByCompanyPk(String companyPk);

	List<B2bMemberDto> getByRoleAndCompanyPk(Map<String, String> map);
	List<B2bMemberDto> searchAdminsByCompanyPk(String companyPk);


	List<B2bMemberDto> searchAdminsByStorePK(String storePk);
	B2bMemberDto searchAdminByStorePK(String storePk);


	int memberCountByInvitationCode(String invitationCode);
	/**
	 * 查询最早注册的日期
	 * @return
	 */
	String searchOriginalDate();
	String searchOriginalEnterTime();
	String searchOriginalAuditTime();
	String searchOriginalAuditSpTime();
	String searchOriginalInsertTime();
}
