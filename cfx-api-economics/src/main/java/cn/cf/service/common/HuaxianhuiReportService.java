package cn.cf.service.common;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.CreditReportInfo;

/**
 * 化纤汇风控业务桥接层
 * @description:
 * @author FJM
 * @date 2019-8-5 下午4:18:37
 */
public interface HuaxianhuiReportService {

	/**
	 * 微众税银查询
	 * @param company
	 * @return
	 */
	String searchWzFile(B2bCompanyDto company);
	
	
	/**
	 * 下载微众文件
	 */
	void downLoadWzFile();
	
	/**
	 * 融安易信查询
	 * @param company
	 * @return
	 */
	String searchRayx(B2bCompanyDto company);

	@Transactional
	int downLoadRayxFile() ;

	CreditReportInfo searchCreditReport(String companyPk);
}
