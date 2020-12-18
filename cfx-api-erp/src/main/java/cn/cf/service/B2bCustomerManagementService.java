package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.B2bCorrespondenceInfoEx;
import cn.cf.model.B2bCustomerManagement;

public interface B2bCustomerManagementService {

	B2bCustomerManagementDto getByStorePkAndPurchaserPk(Map<String, Object> map);

	int insert(B2bCustomerManagement management);


	void insertCustomerSale(List<B2bCorrespondenceInfoEx> list, B2bTokenDto b2btoken);

	void editCustomerSale(List<B2bCorrespondenceInfoEx> list, B2bTokenDto b2btoken);

}
