/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SysCompanyBankcard;
import cn.cf.dto.SysCompanyBankcardDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysCompanyBankcardDao {
	int insert(SysCompanyBankcard model);
	int update(SysCompanyBankcard model);
	int delete(String id);
	List<SysCompanyBankcardDto> searchGrid(Map<String, Object> map);
	List<SysCompanyBankcardDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysCompanyBankcardDto getByPk(java.lang.String pk); 
	 SysCompanyBankcardDto getByCompanyPk(java.lang.String companyPk); 
	 SysCompanyBankcardDto getByBankAccount(java.lang.String bankAccount); 
	 SysCompanyBankcardDto getByBankName(java.lang.String bankName); 
	 SysCompanyBankcardDto getByBankNo(java.lang.String bankNo); 
	 SysCompanyBankcardDto getByEcbankPk(java.lang.String ecbankPk); 
	 SysCompanyBankcardDto getByEcbankName(java.lang.String ecbankName); 

}
