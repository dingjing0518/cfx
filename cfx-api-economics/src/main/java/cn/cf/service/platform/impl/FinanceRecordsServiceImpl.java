package cn.cf.service.platform.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bFinanceRecordsDaoEx;
import cn.cf.dto.B2bFinanceRecordsDtoEx;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.model.B2bFinanceRecords;
import cn.cf.service.platform.FinanceRecordsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
public class FinanceRecordsServiceImpl implements FinanceRecordsService {
	
	@Autowired
	private B2bFinanceRecordsDaoEx recordsDaoEx;
	
	@Override
	public PageModel<B2bFinanceRecordsDtoEx> searchFinanceRecordsDtoEx(
			Map<String, Object> map) {
	    PageModel<B2bFinanceRecordsDtoEx> pm = new PageModel<B2bFinanceRecordsDtoEx>();
		List<B2bFinanceRecordsDtoEx> dataList = recordsDaoEx.searchFinanceRecordsList(map);
	    int counts =  recordsDaoEx.searchFinanceRecordsCounts(map);
	    pm.setDataList(dataList);
	    pm.setTotalCount(counts);
	    if(null != map.get("start")){
	    	pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
	    	pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
	    }
		return pm;
	}

	@Override
	public void updateFinancerecords(String orderNumber,Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNumber", orderNumber);
		map.put("status", status);
		recordsDaoEx.updateStatus(map);
		
	}

	@Override
	public List<B2bFinanceRecordsDtoEx> searchUnsuccessRecords() {
		return recordsDaoEx.searchUnsuccessRecords();
	}

	@Override
	public void insertFinanceRecords(B2bOrderDtoMa odto, String bankName,
			String bankAccount,Integer status) {

		// 增加一笔采购商支付订单交易记录
		B2bFinanceRecords records = new B2bFinanceRecords();
		records.setPk(KeyUtils.getUUID());
		// 采购商信息
		records.setCompanyName(odto.getPurchaser().getPurchaserName());
		records.setCompanyPk(odto.getPurchaserPk());
		//查询供应商实体账户
		records.setSupplierPk(odto.getSupplierPk());
		records.setSupplierName(odto.getSupplier().getSupplierName());
		records.setReceivablesAccount(bankAccount);//供应商收款实体卡号
		records.setReceivablesAccountName(bankName);//供应商收款实体银行名
		records.setDescription("订单号:" + odto.getOrderNumber()
				+ "<br>供应商账户:"
				+ bankAccount
				+ "<br>供应商名称:" + odto.getSupplier().getSupplierName());
		records.setTransactionAmount(ArithUtil.round(odto.getActualAmount(), 2));
		records.setInsertTime(new Date());
		records.setStatus(status);
		records.setTransactionType(1);// 付款
		records.setOrderNumber(odto.getOrderNumber());
		records.setEmployeePk(odto.getEmployeePk());
		records.setEmployeeName(odto.getEmployeeName());
		records.setEmployeeNumber(odto.getEmployeeNumber());
		recordsDaoEx.insert(records);
		
		B2bFinanceRecords recordNex = new B2bFinanceRecords();
		recordNex.setPk(KeyUtils.getUUID());
		recordNex.setCompanyName(odto.getSupplier().getSupplierName());
		recordNex.setCompanyPk(odto.getSupplierPk());
		recordNex.setSupplierPk(odto.getPurchaserPk());
		recordNex.setSupplierName(odto.getPurchaser().getPurchaserName());
		recordNex.setReceivablesAccount(bankAccount);//供应商收款实体卡号
		recordNex.setReceivablesAccountName(bankName);//供应商收款实体银行名
		recordNex.setDescription("订单号:" + odto.getOrderNumber()
				+ "<br>采购商名称:" + odto.getPurchaser().getPurchaserName()
				+ "<br>收款账户:" + bankAccount
				+ "<br>收款银行:" + bankName);	
		recordNex.setTransactionAmount((ArithUtil.round(odto.getActualAmount(), 2)));
		recordNex.setInsertTime(new Date());
		recordNex.setStatus(status);
		recordNex.setTransactionType(5);// 收款
		recordNex.setOrderNumber(odto.getOrderNumber());
		recordNex.setEmployeePk(odto.getEmployeePk());
		recordNex.setEmployeeName(odto.getEmployeeName());
		recordNex.setEmployeeNumber(odto.getEmployeeNumber());
		recordsDaoEx.insert(recordNex);
	}

	@Override
	public void insertFinanceRecords(String orderNumber,Double amount,String purchaserPk,String purchaserName,String supplierPk,String supplierName,
			Integer status,Integer type,String details,String serialNumber,String iouNumber,String employeePk,String employeeName,String employeeNumber) {
		B2bFinanceRecords f = new B2bFinanceRecords();
		f.setPk(KeyUtils.getUUID());
		f.setStatus(status);
		f.setInsertTime(new Date());
		f.setCompanyPk(purchaserPk);
		f.setCompanyName(purchaserName);
		f.setSupplierPk(supplierPk);
		f.setSupplierName(supplierName);
		f.setTransactionAmount(amount);
		f.setTransactionType(type);
		f.setOrderNumber(orderNumber);
		f.setTransactionAccount(null==amount?"":amount.toString());
		f.setSerialNumber(serialNumber);
		f.setIouNumber(iouNumber);
		f.setDescription(details);
		f.setEmployeePk(employeePk);
		f.setEmployeeName(employeeName);
		f.setEmployeeNumber(employeeNumber);
		recordsDaoEx.insert(f);
		
	}

}
