package cn.cf.service.creditreport;


public interface ZhongWangFinancialService {

	
	/**
	 * 授信客户发票同步
	 * @param creditPk 记录pk
	 * @param companyPk 客户公司pk
	 * @param dataType  数据类型：1进项 2销项
	 * @param startDate 开始日期，如：2019-01-01
	 * @param endDate 结束日期，如：2019-01-01
	 * @return
	 */
	String syncCreditInvoice(String creditPk, String companyPk,int dataType,String startDate,String endDate);
	
}
