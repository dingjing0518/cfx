/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bTrancsationContract;
import cn.cf.dto.B2bTrancsationContractDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bTrancsationContractDao {
	int insert(B2bTrancsationContract model);
	int update(B2bTrancsationContract model);
	int delete(String id);
	List<B2bTrancsationContractDto> searchGrid(Map<String, Object> map);
	List<B2bTrancsationContractDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bTrancsationContractDto getByPk(java.lang.String pk); 
	 B2bTrancsationContractDto getByStorePk(java.lang.String storePk); 
	 B2bTrancsationContractDto getByCompanyPk(java.lang.String companyPk); 

}
