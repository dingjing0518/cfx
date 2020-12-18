package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillSigncardExtDto;

public interface B2bBillSigncardExtDao extends B2bBillSigncardDao {

	List<B2bBillSigncardExtDto> searchGridEx(Map<String, Object> map);

	int checkCompanyAndBank(Map<String, Object> map);

}
