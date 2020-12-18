package cn.cf.service.creditreport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.cf.dto.B2bCompanyDto;
/**
 * 融安易信
 * @description:
 * @author FJM
 * @date 2019-8-5 下午1:51:55
 */
public interface ReportGongshangService {
	/**
	 * 征信记录查询
	 * @param company
	 * @return
	 */
	JSONObject search(B2bCompanyDto company);
	/**
	 * 查询征信报告文件
	 * @param companyPk
	 * @param companyName
	 * @return
	 */
	JSONArray searchReport(String companyName);
}
