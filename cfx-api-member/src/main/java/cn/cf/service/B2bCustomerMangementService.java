package cn.cf.service;

import java.io.File;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.entry.CustomerImport;
import cn.cf.entry.RespCustomer;
import cn.cf.model.B2bCustomerManagement;

public interface B2bCustomerMangementService  {

	RestCode addCustomerManagement(B2bCustomerManagementDto dto);
	
	String insertOrUpdate(B2bCustomerManagement b2bCustomerManagement);

    RespCustomer customerList(File file);

    @Transactional
     int b2bCompany(List<CustomerImport> list,String storePk);



}
