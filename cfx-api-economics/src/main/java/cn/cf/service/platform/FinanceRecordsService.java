package cn.cf.service.platform;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bFinanceRecordsDtoEx;
import cn.cf.entity.B2bOrderDtoMa;

public interface FinanceRecordsService {
	
	/**
	 * 交易记录查询
	 * @param map
	 * @return
	 */
	PageModel<B2bFinanceRecordsDtoEx> searchFinanceRecordsDtoEx(Map<String,Object> map);
	
	/**
	 * 更新交易记录
	 * @param orderNumber
	 */
	void updateFinancerecords(String orderNumber,Integer status);
	
	/**
	 * 查询交易记录(状态在处理中的)
	 * @return
	 */
	List<B2bFinanceRecordsDtoEx> searchUnsuccessRecords();
	/**
	 * 新增交易记录
	 * @param odto
	 * @param bankName
	 * @param bankAccount

	 */
	void insertFinanceRecords(B2bOrderDtoMa odto, String bankName,
			String bankAccount,Integer status);
	/**
	 * 新增交易记录(全)
	 * @param orderNumber
	 * @param amount
	 * @param purchaserPk
	 * @param purchaserName
	 * @param supplierPk
	 * @param supplierName
	 * @param status
	 * @param type
	 * @param details
	 * @param serialNumber
	 * @param iouNumber
	 * @param employeePk
	 * @param employeeName
	 * @param employeeNumber
	 */
	void insertFinanceRecords(String orderNumber,Double amount,String purchaserPk,String purchaserName,String supplierPk,String supplierName,
			Integer status,Integer type,String details,String serialNumber,String iouNumber,String employeePk,String employeeName,String employeeNumber);
}
