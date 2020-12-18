package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.LgUserAddressDaoEx;
import cn.cf.dto.LgUserAddressDto;
import cn.cf.model.LgUserAddress;
import cn.cf.service.LgUserAddressService;
import cn.cf.util.KeyUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LgUserAddressServiceImpl implements LgUserAddressService {

    @Autowired
    private LgUserAddressDaoEx lgUserAddressDaoExt;
    
    //地址列表查询
    @Override
    public PageModel<LgUserAddressDto> searchAddress(Map<String, Object> map) {
        PageModel<LgUserAddressDto> pm = new PageModel<LgUserAddressDto>();
        map.put("isDelete", 1);
        map.put("orderName", "insertTime");
        map.put("orderType", "desc");
        List<LgUserAddressDto> list=lgUserAddressDaoExt.searchList(map);
        int counts=lgUserAddressDaoExt.searchGridCount(map);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        if(map.get("start")!=null){
            pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
            pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
        }
        return pm;
    }

    //根据pk查询地址信息
    @Override
    public LgUserAddressDto searchAddressByPk(String pk) {
        return lgUserAddressDaoExt.getByPk(pk);
    }
    
    //验证地址是否已经存在
    @Override
    public Integer searchEntity(LgUserAddressDto dto) {
        return lgUserAddressDaoExt.searchEntity(dto);
    }

	// 添加地址
	@Override
	public String addAddress(LgUserAddressDto dto) {
		Integer count = lgUserAddressDaoExt.searchEntity(dto);
		if (count > 0) {
			return RestCode.CODE_Q001.toJson();
		}
		dto.setPk(KeyUtils.getUUID());
		dto.setInsertTime(new Date());
		dto.setUpdateTime(new Date());
		dto.setIsDelete(1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", dto.getCompanyPk());
		if (null == dto.getIsDefault()) {
			dto.setIsDefault(2);
		}
		LgUserAddress address = new LgUserAddress();
		address.UpdateDTO(dto);
		// 如果设置为默认地址，则将其他地址设置为非默认
		if (address.getIsDefault() == 1) {
			lgUserAddressDaoExt.updateIsDefault(dto.getUserPk(), dto.getCompanyPk(), dto.getType());
		}
		lgUserAddressDaoExt.insert(address);
		return RestCode.CODE_0000.toJson(dto);
	}

    //修改地址
    @Override
    @Transactional
    public String updateAddress(LgUserAddressDto dto) {
    	String result=RestCode.CODE_0000.toJson();
    	if (null == dto.getIsDefault() || dto.getIsDefault() != 1) {
            int count = lgUserAddressDaoExt.searchEntity(dto);
            if (count > 0) {
               return RestCode.CODE_Q001.toJson();
            }
        }
        // 如果是设为默认地址
        if (null!=dto.getIsDefault()&&dto.getIsDefault() == 1) {
        	//将另外一个默认地址设为非默认
        	lgUserAddressDaoExt.updateIsDefault(dto.getUserPk(),dto.getCompanyPk(),dto.getType());
        }else {
        	dto.setIsDefault(2);
		}
        if (null!=dto.getProvince()&&"".equals(dto.getProvince())) {
        	dto.setProvince("");
		}
        if (null!=dto.getProvinceName()&&"".equals(dto.getProvinceName())) {
        	dto.setProvinceName("");
		}
        if (null!=dto.getCity()&&"".equals(dto.getCity())) {
        	dto.setCity("");
		}
        if (null!=dto.getCityName()&&"".equals(dto.getCityName())) {
        	dto.setCityName("");
		}
        if (null!=dto.getArea()&&"".equals(dto.getArea())) {
        	dto.setArea("");
		}
        if (null!=dto.getAreaName()&&"".equals(dto.getAreaName())) {
        	dto.setAreaName("");
		}
        if (null!=dto.getTown()&&"".equals(dto.getTown())) {
        	dto.setTown("");
		}
        if (null!=dto.getTownName()&&"".equals(dto.getTownName())) {
        	dto.setTownName("");
		}
        if (null!=dto.getAddress()&&"".equals(dto.getAddress())) {
			dto.setAddress("");
		}
        lgUserAddressDaoExt.updatePartField(dto);
        return result;
    }
    
    //删除地址
    @Override
    public void delAddress(String pk) {
        LgUserAddressDto address = new LgUserAddressDto();
        address.setPk(pk);
        address.setIsDelete(2);
        lgUserAddressDaoExt.updatePartField(address);
    }

}
