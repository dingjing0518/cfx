/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.dto.SysCompanyBankcardDtoEx;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysCompanyBankcardExtDao extends SysCompanyBankcardDao{

	int updateSysCompanyBankcard(SysCompanyBankcardDto dto);

	SysCompanyBankcardDto getByCompanyPk(String companyPk);

	List<SysCompanyBankcardDtoEx> searchGridEx(Map<String, Object> map);

	int checkCompanyAndBank(Map<String, Object> map);
}
