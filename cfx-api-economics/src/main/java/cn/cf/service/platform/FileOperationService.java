package cn.cf.service.platform;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.dto.B2bFinanceRecordsDtoEx;

public interface FileOperationService {
	
	/**
	 * 导出交易记录
	 * @param list
	 * @param request
	 * @param response
	 */
	void exportFinanceRecords(List<B2bFinanceRecordsDtoEx> list,HttpServletRequest request,HttpServletResponse response);
}
