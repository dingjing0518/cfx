package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class ManageAccountExtDto extends ManageAccountDto implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5973243389909279700L;
	
	private String roleName;
	
	
	
	//金融系统
	private MemberShip  memberShip;
	private String userId;
	private String groupId;
	
	private String area;
	//columns END

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MemberShip getMemberShip() {
		return memberShip;
	}

	public void setMemberShip(MemberShip memberShip) {
		this.memberShip = memberShip;
	}

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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}