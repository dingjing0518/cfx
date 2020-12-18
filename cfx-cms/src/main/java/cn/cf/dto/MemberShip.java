package cn.cf.dto;

public class MemberShip {
	
	private String userId;
	private String groupId;
	private ActGroupDto actGroupDto;
	private ActUserDto actUserDto;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public ActGroupDto getActGroupDto() {
		return actGroupDto;
	}
	public void setActGroupDto(ActGroupDto actGroupDto) {
		this.actGroupDto = actGroupDto;
	}
	public ActUserDto getActUserDto() {
		return actUserDto;
	}
	public void setActUserDto(ActUserDto actUserDto) {
		this.actUserDto = actUserDto;
	}
	
	

}
