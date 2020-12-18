package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bInvitationRecordDtoEx;
import cn.cf.model.B2bInvitationRecord;

public interface B2bInvitationRecordDaoEx extends B2bInvitationRecordDao{
	
	B2bInvitationRecordDtoEx getbyTCompanyName(B2bInvitationRecordDtoEx invitation);
	
	//邀请管理pageModel
	List<B2bInvitationRecordDtoEx> searchGridEx(Map<String, Object> map);
	
	//邀请管理查询count
	int searchGridCountEx(Map<String, Object> map);
	
	/**
	 * 邀请管理页签数字
	 * @param map
	 * @return
	 */
	List<B2bInvitationRecordDtoEx> searchinvitationStatusCounts(Map<String, Object> map);
	
	int updateEx(B2bInvitationRecord model);
	
	B2bInvitationRecordDtoEx getDtoExByPk(String pk);
	
 
	
	B2bAddressDto selectRecordAddress(String beInvitedCode);
	
}
