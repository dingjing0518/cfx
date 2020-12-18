package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bInvitationRecordExtDto;

public interface B2bInvitationRecordExtDao extends B2bInvitationRecordDao{
	
	int searchGridExtCount(Map<String,Object> map);
	List<B2bInvitationRecordExtDto> searchGridExt(Map<String,Object> map);
	
	B2bInvitationRecordExtDto getByPkExt(String pk);
	
	int updateInvitation(B2bInvitationRecordExtDto extDto);
	
	int updatePkByCompanyName(Map<String,Object> map);
}
