/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgArayacakMandate;
import cn.cf.dto.LgArayacakMandateDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgArayacakMandateDao {
	int insert(LgArayacakMandate model);
	int update(LgArayacakMandate model);
	int delete(String id);
	List<LgArayacakMandateDto> searchGrid(Map<String, Object> map);
	List<LgArayacakMandateDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgArayacakMandateDto getByPk(java.lang.String pk); 
	 LgArayacakMandateDto getByUserPk(java.lang.String userPk); 
	 LgArayacakMandateDto getByUserMobile(java.lang.String userMobile); 
	 LgArayacakMandateDto getByCompanyPk(java.lang.String companyPk); 
	 LgArayacakMandateDto getByCompanyName(java.lang.String companyName); 
	 LgArayacakMandateDto getByMandateName(java.lang.String mandateName); 
	 LgArayacakMandateDto getByMandateUrl(java.lang.String mandateUrl); 

}
