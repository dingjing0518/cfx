package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.entity.MemberDataReport;
import cn.cf.entity.MemberDataReportExt;

public interface MemberDataService {
	/**
	 * 查询某一天会员报表数据
	 * @param date
	 * @return
	 */
	MemberDataReport searchOnedayData(String date);
	/**
	 * 会员数据列表
	 * @param qm
	 * @return
	 */
	PageModel<MemberDataReport> searchMemberDataList(QueryModel<MemberDataReportExt> qm);
	/**
	 * 导出会员数据列表
	 * @param jsonData
	 * @param reportForm
	 * @return
	 */
//	List<MemberDataReport> exportMemberData(String jsonData, MemberDataReportExt reportForm);
	
	/**
	 * 查询某天申请金融产品的公司
	 * @param day
	 */
	List<B2bEconomicsCreditcustomerDtoEx> searchOneDayApplyCompany(String day);

	List<B2bEconomicsCreditcustomerDtoEx> searchAccDayApplyCompany(String day);

}
