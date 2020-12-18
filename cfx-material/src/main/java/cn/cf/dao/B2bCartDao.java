/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bCart;
import cn.cf.dto.B2bCartDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCartDao {
	int insert(B2bCart model);
	int update(B2bCart model);
	int delete(String id);
	List<B2bCartDto> searchGrid(Map<String, Object> map);
	List<B2bCartDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCartDto getByPk(java.lang.String pk); 
	 B2bCartDto getByGoodsPk(java.lang.String goodsPk); 
	 B2bCartDto getByMemberPk(java.lang.String memberPk); 
	 B2bCartDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bCartDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bCartDto getBySupplierName(java.lang.String supplierName); 
	 B2bCartDto getBySupplierPk(java.lang.String supplierPk); 

}
