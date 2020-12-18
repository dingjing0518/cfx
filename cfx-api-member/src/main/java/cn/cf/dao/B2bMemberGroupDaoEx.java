/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberGroupDaoEx  extends B2bMemberGroupDao{
 
	int deleteByParentPk(String parentPk);

	String searchParents(String memberPk);

	int deleteByMemberPk(String memberPk);

	int deleteByParentPkAndMemberPk(String memberPk); 

}
