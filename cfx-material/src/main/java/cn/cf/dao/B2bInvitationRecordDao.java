/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bInvitationRecord;
import cn.cf.dto.B2bInvitationRecordDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bInvitationRecordDao {
	int insert(B2bInvitationRecord model);
	int update(B2bInvitationRecord model);
	int delete(String id);
	List<B2bInvitationRecordDto> searchGrid(Map<String, Object> map);
	List<B2bInvitationRecordDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bInvitationRecordDto getByPk(java.lang.String pk); 
	 B2bInvitationRecordDto getByMember(java.lang.String member); 
	 B2bInvitationRecordDto getByMname(java.lang.String mname); 
	 B2bInvitationRecordDto getByMphone(java.lang.String mphone); 
	 B2bInvitationRecordDto getByMcompanyPk(java.lang.String mcompanyPk); 
	 B2bInvitationRecordDto getByMcompanyName(java.lang.String mcompanyName); 
	 B2bInvitationRecordDto getByBeInvitedCode(java.lang.String beInvitedCode); 
	 B2bInvitationRecordDto getByTname(java.lang.String tname); 
	 B2bInvitationRecordDto getByTphone(java.lang.String tphone); 
	 B2bInvitationRecordDto getByTcompanyPk(java.lang.String tcompanyPk); 
	 B2bInvitationRecordDto getByTcompanyName(java.lang.String tcompanyName); 
	 B2bInvitationRecordDto getByAddress(java.lang.String address); 
	 B2bInvitationRecordDto getByProvinceName(java.lang.String provinceName); 
	 B2bInvitationRecordDto getByProvince(java.lang.String province); 
	 B2bInvitationRecordDto getByCityName(java.lang.String cityName); 
	 B2bInvitationRecordDto getByCity(java.lang.String city); 
	 B2bInvitationRecordDto getByAreaName(java.lang.String areaName); 
	 B2bInvitationRecordDto getByArea(java.lang.String area); 
	 B2bInvitationRecordDto getByTown(java.lang.String town); 
	 B2bInvitationRecordDto getByTownName(java.lang.String townName); 
	 B2bInvitationRecordDto getByAddressPk(java.lang.String addressPk); 
	 B2bInvitationRecordDto getByContacts(java.lang.String contacts); 
	 B2bInvitationRecordDto getByContactsTel(java.lang.String contactsTel); 
	 B2bInvitationRecordDto getByActivityPk(java.lang.String activityPk); 
	 B2bInvitationRecordDto getByAwardName(java.lang.String awardName); 
	 B2bInvitationRecordDto getByNote(java.lang.String note); 

}
