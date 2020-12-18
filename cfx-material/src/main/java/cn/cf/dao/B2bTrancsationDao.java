/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bTrancsation;
import cn.cf.dto.B2bTrancsationDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bTrancsationDao {
	int insert(B2bTrancsation model);
	int update(B2bTrancsation model);
	int delete(String id);
	List<B2bTrancsationDto> searchGrid(Map<String, Object> map);
	List<B2bTrancsationDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bTrancsationDto getByPk(java.lang.String pk); 
	 B2bTrancsationDto getByStorePk(java.lang.String storePk); 
	 B2bTrancsationDto getByCompanyPk(java.lang.String companyPk); 

}
