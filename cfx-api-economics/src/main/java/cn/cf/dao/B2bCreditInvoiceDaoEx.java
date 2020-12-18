package cn.cf.dao;

import org.apache.ibatis.annotations.Param;

public interface B2bCreditInvoiceDaoEx extends B2bCreditInvoiceDao{
	
	
	/**
	 * 删除记录
	 * @param companyPk  公司pk
	 * @param startDate  开始日期
	 * @param endDate  结束日期
	 * @return
	 */
	Integer deleteEx(@Param("companyPk")String companyPk,@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("dataType")Integer dataType);
	
	
	
}
