package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bAddressDao;
import cn.cf.dao.SysRegionsDaoEx;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.model.B2bAddress;
import cn.cf.service.B2bAddressService;
import cn.cf.util.KeyUtils;

@Service
public class B2bAddressServiceImpl implements B2bAddressService {
	
	@Autowired
	private B2bAddressDao addressDao;
	
	@Autowired
	private SysRegionsDaoEx sysRegionsDaoEx;
	
	
	
	@Override
	public B2bAddress getAddressByDetails(B2bAddress address) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", address.getCompanyPk());
		if(null != address.getProvinceName() && !"".equals(address.getProvinceName())){
			map.put("parentPk", "-1");
			map.put("name", address.getProvinceName());
			SysRegionsDto regions = getRegionsPk(map);
			address.setProvince(null != regions?regions.getPk():"");
			address.setProvinceName(null != regions?regions.getName():"");
			map.put("province", address.getProvince());
		}
		if(null != address.getCityName() && !"".equals(address.getCityName())){
			map.put("parentPk", address.getProvince());	
			map.put("name", address.getCityName());
			SysRegionsDto regions = getRegionsPk(map);
			address.setCity(null != regions?regions.getPk():"");
			address.setCityName(null != regions?regions.getName():"");
			map.put("city", address.getCity());
		}
		if(null != address.getAreaName() && !"".equals(address.getAreaName())){
			map.put("parentPk", address.getCity());
			map.put("name",address.getAreaName());
			SysRegionsDto regions = getRegionsPk(map);
			address.setArea(null != regions?regions.getPk():"");
			address.setAreaName(null != regions?regions.getName():"");
			map.put("area", address.getArea());
		}
		if(null != address.getTownName() && !"".equals(address.getTownName())){
			map.put("parentPk", address.getArea());
			map.put("name",address.getTownName());
			SysRegionsDto regions = getRegionsPk(map);
			address.setTown(null != regions?regions.getPk():"");
			address.setTownName(null != regions?regions.getName():"");
			map.put("town", address.getTown());
		}
		map.put("address", address.getAddress());
		map.put("contacts", address.getContacts());
		map.put("contactsTel", address.getContactsTel());
		map.put("signingCompany", address.getSigningCompany());
		map.put("isDelete", 1);
		address.setIsDelete(1);
		List<B2bAddressDto> addressList = addressDao.searchList(map);
		if(null !=addressList && addressList.size()>0){
			address.setPk(addressList.get(0).getPk());
			address.setInsertTime(addressList.get(0).getInsertTime());
			address.setIsDefault(addressList.get(0).getIsDefault());
		}
		return address;
	}

	@Override
	public void insertOrUpdate(B2bAddress address) {
		if(null != address){
			if(null == address.getPk() || "".equals(address.getPk())){
				address.setPk(KeyUtils.getUUID());
				address.setInsertTime(new Date());
				address.setIsDefault(2);
				addressDao.insert(address);
			}else{
				addressDao.update(address);
			}
		}
	}
	
	private SysRegionsDto getRegionsPk(Map<String,Object> map){
		map.put("nameOne", map.get("name")+"省");
		map.put("nameTwo", map.get("name")+"市");
		map.put("nameThree", map.get("name")+"区");
		map.put("nameFour", map.get("name")+"县");
		map.put("nameFive", map.get("name")+"镇");
		return sysRegionsDaoEx.getRegionByNames(map);
	}

}
