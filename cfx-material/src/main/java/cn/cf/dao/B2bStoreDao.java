/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bStore;
import cn.cf.dto.B2bStoreDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bStoreDao {
	int insert(B2bStore model);
	int update(B2bStore model);
	int delete(String id);
	List<B2bStoreDto> searchGrid(Map<String, Object> map);
	List<B2bStoreDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bStoreDto getByPk(java.lang.String pk); 
	 B2bStoreDto getByCompanyPk(java.lang.String companyPk); 
	 B2bStoreDto getByStartTime(java.lang.String startTime); 
	 B2bStoreDto getByEndTime(java.lang.String endTime); 
	 B2bStoreDto getByCustomerTel(java.lang.String customerTel); 
	 B2bStoreDto getByName(java.lang.String name); 
	 B2bStoreDto getByQq(java.lang.String qq); 
	 B2bStoreDto getByIntroduce(java.lang.String introduce); 
	 B2bStoreDto getByShopNotices(java.lang.String shopNotices); 
	 B2bStoreDto getByLogoSettings(java.lang.String logoSettings); 
	 B2bStoreDto getByContacts(java.lang.String contacts); 
	 B2bStoreDto getByContactsTel(java.lang.String contactsTel); 

}
