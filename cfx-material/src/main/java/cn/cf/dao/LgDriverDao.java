/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgDriver;
import cn.cf.dto.LgDriverDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgDriverDao {
	int insert(LgDriver model);
	int update(LgDriver model);
	int delete(String id);
	List<LgDriverDto> searchGrid(Map<String, Object> map);
	List<LgDriverDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgDriverDto getByPk(java.lang.String pk); 
	 LgDriverDto getByName(java.lang.String name); 
	 LgDriverDto getByCompanyPk(java.lang.String companyPk); 
	 LgDriverDto getByMobile(java.lang.String mobile); 
	 LgDriverDto getByLicenseUrl(java.lang.String licenseUrl); 

}
