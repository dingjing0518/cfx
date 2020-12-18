/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import org.apache.ibatis.annotations.Param;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCusgoodsApplyDaoEx extends B2bBillCusgoodsApplyDao{
	 
	int deleteByCompanyPk(@Param("companyPk") String companyPk);
}
