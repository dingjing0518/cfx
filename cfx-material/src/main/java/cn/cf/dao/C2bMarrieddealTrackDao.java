/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.C2bMarrieddealTrack;
import cn.cf.dto.C2bMarrieddealTrackDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface C2bMarrieddealTrackDao {
	int insert(C2bMarrieddealTrack model);
	int update(C2bMarrieddealTrack model);
	int delete(String id);
	List<C2bMarrieddealTrackDto> searchGrid(Map<String, Object> map);
	List<C2bMarrieddealTrackDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 C2bMarrieddealTrackDto getByPk(java.lang.String pk); 
	 C2bMarrieddealTrackDto getByMarrieddealId(java.lang.String marrieddealId); 
	 C2bMarrieddealTrackDto getBySupplierPk(java.lang.String supplierPk); 
	 C2bMarrieddealTrackDto getBySupplierName(java.lang.String supplierName); 
	 C2bMarrieddealTrackDto getByPurchaserPk(java.lang.String purchaserPk); 
	 C2bMarrieddealTrackDto getByPurchaserName(java.lang.String purchaserName); 
	 C2bMarrieddealTrackDto getBySupplierContacts(java.lang.String supplierContacts); 
	 C2bMarrieddealTrackDto getByPurchaserContacts(java.lang.String purchaserContacts); 
	 C2bMarrieddealTrackDto getBySupplierTel(java.lang.String supplierTel); 
	 C2bMarrieddealTrackDto getByPurchaserTel(java.lang.String purchaserTel); 
	 C2bMarrieddealTrackDto getByBidPk(java.lang.String bidPk); 
	 C2bMarrieddealTrackDto getByRemark(java.lang.String remark); 
	 C2bMarrieddealTrackDto getByMemberPk(java.lang.String memberPk); 
	 C2bMarrieddealTrackDto getByMemberName(java.lang.String memberName); 

}
