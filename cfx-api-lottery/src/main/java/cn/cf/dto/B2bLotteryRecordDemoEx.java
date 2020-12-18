package cn.cf.dto;

import java.util.Date;

import cn.cf.util.KeyUtils;

public class B2bLotteryRecordDemoEx extends B2bLotteryRecordDto{
	private static final long serialVersionUID = 1L;
	private Integer awardSort;
	public B2bLotteryRecordDemoEx(B2bLoanNumberDtoEx loan,
			B2bLotteryActivityDtoEx activityDto, String awardName,	B2bAddressDto addressA) {
		this.setPk(KeyUtils.getUUID());
		this.setActivityPk(activityDto.getPk());
		this.setStatus(2);
		this.setName(awardName);
		this.setAwardName("京东E卡"+loan.getCardAmount()+"元");
		this.setAwardStatus(1);
		this.setAwardType(2);
		this.setInsertTime(new Date());
		this.setMemberPk("");
		this.setCompanyPk(loan.getPurchaserPk());
		this.setCompanyName(loan.getPurchaserName());
		if(null!=addressA){
			this.setProvince(addressA.getProvince());
	    	this.setProvinceName(addressA.getProvinceName());
	    	this.setCity(addressA.getCity());
	    	this.setCityName(addressA.getCityName());
	    	this.setArea(addressA.getArea());
	    	this.setAreaName(addressA.getAreaName());
	    	this.setTown(addressA.getTown());
	    	this.setTownName(addressA.getTownName());
	    	this.setAddress(addressA.getAddress());
	    	this.setContacts(addressA.getContacts());
	    	this.setContactsTel(addressA.getContactsTel());
	    	this.setAddressPk(addressA.getPk());
		}
	}

	public B2bLotteryRecordDemoEx( B2bMemberDto member,B2bLotteryAwardDto award,Integer awardStatus,	B2bAddressDto addressA) {
		this.setAwardStatus(awardStatus);// '奖品发放状态 1未发放，2已发放', 3 无,
		this.setPk(KeyUtils.getUUID());
		this.setActivityPk(award.getActivityPk());
		this.setAwardPk(award.getPk()==null?"":award.getPk());
		this.setStatus(award.getStatus()==null?1:award.getStatus());
		this.setName(award.getName()==null?"再接再厉":award.getName());
		this.setAwardName(award.getAwardName()==null?"无":award.getAwardName());
		this.setAwardType(award.getAwardType()==null?3:award.getAwardType());
		this.setMemberPk(member.getPk());
		this.setMemberName(member.getEmployeeName() == null
				|| "".equals(member.getEmployeeName()) ? member.getMobile()
				: member.getEmployeeName());
		this.setCompanyPk(member.getCompanyPk());
		this.setCompanyName(member.getCompanyName());
		this.setMobile(member.getMobile());
		this.setInsertTime(new Date());
		if(null!=addressA){
			this.setProvince(addressA.getProvince());
	    	this.setProvinceName(addressA.getProvinceName());
	    	this.setCity(addressA.getCity());
	    	this.setCityName(addressA.getCityName());
	    	this.setArea(addressA.getArea());
	    	this.setAreaName(addressA.getAreaName());
	    	this.setTown(addressA.getTown());
	    	this.setTownName(addressA.getTownName());
	    	this.setAddress(addressA.getAddress());
	    	this.setContacts(addressA.getContacts());
	    	this.setContactsTel(addressA.getContactsTel());
	    	this.setAddressPk(addressA.getPk());
		}
	}

 

	public B2bLotteryRecordDemoEx( B2bAddressDto address) {
		this.setProvince(address.getProvince());
    	this.setProvinceName(address.getProvinceName());
    	this.setCity(address.getCity());
    	this.setCityName(address.getCityName());
    	this.setArea(address.getArea());
    	this.setAreaName(address.getAreaName());
    	this.setTown(address.getTown());
    	this.setTownName(address.getTownName());
    	this.setAddress(address.getAddress());
    	this.setContacts(address.getContacts());
    	this.setContactsTel(address.getContactsTel());
    	this.setAddressPk(address.getPk());
	}

	public Integer getAwardSort() {
		return awardSort;
	}

	public void setAwardSort(Integer awardSort) {
		this.awardSort = awardSort;
	}

	 
 
	
}
