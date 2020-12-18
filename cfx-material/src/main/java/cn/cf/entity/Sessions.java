package cn.cf.entity;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgMemberDto;


public class Sessions {

	private String sessionId;
	private String memberPk;// 用户id
	private String mobile;//手机号码
	private String companyPk;//承运商pk
	private String companyName;//承运商名称
	private String menus;//所有模块
	private String purMenus;//采购商模块
	private String purMenusSx;//纱线采购商模块
	private String supMenus;//供应商模块
	private String supMenusSx;//纱线供应商模块
	private Integer companyType;
	private Integer isPerfect =1;//是否完善  1否2完善
	private String lastLoginTime;//最后登录时间
	private Integer isAdmin;
	private String invitationCode;
	private Integer source;
	private B2bCompanyDto companyDto;//公司管理
	private B2bStoreDto storeDto;//店铺信息
	private B2bMemberDto memberDto;//会员
	private LgCompanyDto lgCompanyDto;//公司表
	private LgMemberDto lgMemberDto;//物流公司业务员表
	private String block;//模块：化纤cf，纱线sx
	private Integer stampDuty;//印花税 1否2是
	
	
	public Sessions(){
		
	}
	
	public Sessions(String sessionId, Integer companyType,Integer isPerfect,String memberPk,String mobile,String companyPk,String companyName,String invitationCode,Integer source,String block) {
		this.sessionId = sessionId;
		this.companyType = companyType;
		this.isPerfect=isPerfect;	
		this.memberPk = memberPk;
		this.mobile = mobile;
		this.companyPk = companyPk;
		this.companyName = companyName;
		this.invitationCode=invitationCode;
		this.source=source;
		this.block = block;
	}
	
	public Sessions(String sessionId, LgMemberDto lgMemberDto, LgCompanyDto lgCompanyDto) {
		this.sessionId = sessionId;
		this.memberPk = lgMemberDto.getPk();
		this.mobile = lgMemberDto.getMobile();
		this.companyPk = lgMemberDto.getCompanyPk();
		this.companyName = lgMemberDto.getCompanyName();
		this.lgMemberDto = lgMemberDto;
		this.lgCompanyDto = lgCompanyDto;
	}


	public B2bCompanyDto getCompanyDto() {
		return companyDto;
	}
	public void setCompanyDto(B2bCompanyDto companyDto) {
		this.companyDto = companyDto;
	}
	public B2bStoreDto getStoreDto() {
		return storeDto;
	}
	public void setStoreDto(B2bStoreDto storeDto) {
		this.storeDto = storeDto;
	}
	public B2bMemberDto getMemberDto() {
		return memberDto;
	}
	public void setMemberDto(B2bMemberDto memberDto) {
		this.memberDto = memberDto;
	}
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public Integer getIsPerfect() {
		return isPerfect;
	}
	public void setIsPerfect(Integer isPerfect) {
		this.isPerfect = isPerfect;
	}
	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}


	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPurMenus() {
		return purMenus;
	}
	public void setPurMenus(String purMenus) {
		this.purMenus = purMenus;
	}
	public String getSupMenus() {
		return supMenus;
	}
	public void setSupMenus(String supMenus) {
		this.supMenus = supMenus;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public LgCompanyDto getLgCompanyDto() {
		return lgCompanyDto;
	}

	public void setLgCompanyDto(LgCompanyDto lgCompanyDto) {
		this.lgCompanyDto = lgCompanyDto;
	}

	public LgMemberDto getLgMemberDto() {
		return lgMemberDto;
	}

	public void setLgMemberDto(LgMemberDto lgMemberDto) {
		this.lgMemberDto = lgMemberDto;
	}

	public String getPurMenusSx() {
		return purMenusSx;
	}

	public void setPurMenusSx(String purMenusSx) {
		this.purMenusSx = purMenusSx;
	}

	public String getSupMenusSx() {
		return supMenusSx;
	}

	public void setSupMenusSx(String supMenusSx) {
		this.supMenusSx = supMenusSx;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public Integer getStampDuty() {
		return stampDuty;
	}

	public void setStampDuty(Integer stampDuty) {
		this.stampDuty = stampDuty;
	}
	
	
}
