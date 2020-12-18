/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgCar;
import cn.cf.dto.LgCarDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgCarDao {
	int insert(LgCar model);
	int update(LgCar model);
	int delete(String id);
	List<LgCarDto> searchGrid(Map<String, Object> map);
	List<LgCarDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgCarDto getByPk(java.lang.String pk); 
	 LgCarDto getByCompanyPk(java.lang.String companyPk); 
	 LgCarDto getByPlateNumber(java.lang.String plateNumber); 
	 LgCarDto getByCarLength(java.lang.String carLength); 
	 LgCarDto getByCarType(java.lang.String carType); 
	 LgCarDto getByLicenseUrl(java.lang.String licenseUrl); 

}
