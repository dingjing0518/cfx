package cn.cf.entity;

import java.util.Date;

import cn.cf.dto.B2bLotteryRecordDtoEx;
import cn.cf.model.B2bAddress;

public class B2bAddressEntity extends B2bAddress{
	public B2bAddressEntity(String addressPk,B2bLotteryRecordDtoEx dto,String companyPk) {
		this.setPk(addressPk);
		this.setProvince(dto.getProvince());
		this.setProvinceName(dto.getProvinceName());
		this.setCity(dto.getCity());
		this.setCityName(dto.getCityName());
		this.setArea(dto.getArea());
		this.setAreaName(dto.getAreaName());
		this.setTown(dto.getTown());
		this.setTownName(dto.getTownName());
		this.setContacts(dto.getContacts());
		this.setContactsTel(dto.getContactsTel());
		this.setAddress(dto.getAddress());
		if(null!=companyPk&&!"".equals(companyPk)){
			this.setCompanyPk(companyPk);
			this.setInsertTime(new Date());
			this.setIsDelete(1);
			this.setIsDefault(2);
		}
	}

	private static final long serialVersionUID = 1L;
 
}
