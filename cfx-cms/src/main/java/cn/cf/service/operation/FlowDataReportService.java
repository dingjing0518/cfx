package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.FlowDataReport;
import cn.cf.entity.FlowDataReportExt;

public interface FlowDataReportService {
	/**
	 * 流量数据列表
	 * @param qm
	 * @return
	 */
	PageModel<FlowDataReport> searchflowDataList(QueryModel<FlowDataReportExt> qm);
	/**
	 * 导出流量数据
	 * @param jsonData
	 * @param flow
	 * @return
	 */
	//List<FlowDataReport> exportFlowData(String jsonData, FlowDataReportExt flow);

}
