package cn.cf.dao;

import org.apache.ibatis.annotations.Param;

import cn.cf.model.B2bBillCustomerApply;

public interface B2bBillCustomerApplyDaoEx extends B2bBillCustomerApplyDao{
/**
 * 查询是否在处理中
 * @param companyPk
 * @param flag 
 * @return
 */
	int searchCountByStatus(@Param("companyPk") String companyPk );
/**
 * 更新票据客户申请表
 * @param cus
 * @return
 */
int updateCustomer(B2bBillCustomerApply cus);

}
