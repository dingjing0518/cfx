/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillCustomerExtDto;
import cn.cf.model.B2bBillCustomer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCustomerExtDao  extends B2bBillCustomerDao{

	List<B2bBillCustomerExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);
	int updateObj(B2bBillCustomerExtDto extDto);
	int updateStatusByCompanyPk(B2bBillCustomerExtDto extDto);
	void updateExt(B2bBillCustomer model);
}
