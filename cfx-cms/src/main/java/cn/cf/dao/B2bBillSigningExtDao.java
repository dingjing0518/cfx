package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillSigningExtDto;
import cn.cf.model.B2bBillSigning;

public interface B2bBillSigningExtDao extends B2bBillSigningDao {

	int searchGridExtCount(Map<String, Object> map);

	List<B2bBillSigningExtDto> searchGridExt(Map<String, Object> map);

	int isExtCompany(String companyPk);

	int updateExt(B2bBillSigning model);

}
