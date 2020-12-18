package cn.cf.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.LgDriverDaoEx;
import cn.cf.dto.LgDriverDto;
import cn.cf.model.LgDriver;
import cn.cf.service.LgDriverService;
import cn.cf.util.KeyUtils;

@Service
public class LgDriverServiceImpl implements LgDriverService {

    @Autowired
    private LgDriverDaoEx driverDaoEx;
    
    
    //司机列表查询
	@Override
	public PageModel<LgDriverDto> searchLgDriver(Map<String, Object> map) {
		PageModel<LgDriverDto> pm = new PageModel<LgDriverDto>();
		map.put("isDelete", 1);
        map.put("orderName", "updateTime");
        map.put("orderType", "desc");
        List<LgDriverDto> list =  driverDaoEx.searchGrid(map);
        int counts = driverDaoEx.searchGridCount(map);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        if(map.get("start")!=null){
            pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
            pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
        }
        return pm;
	}
	
	//根据pk查询司机信息
	@Override
	public LgDriverDto getByPk(String pk) {
		return driverDaoEx.getByPk(pk);
	}
	
	//查询司机信息是否已存在
	@Override
	public int searchEntity(LgDriverDto dto) {
		return driverDaoEx.searchEntity(dto);
	}
	
	//添加司机信息
	@Override
	public LgDriverDto add(LgDriverDto dto) {
		dto.setPk(KeyUtils.getUUID());
		dto.setIsDelete(1);
		dto.setInsertTime(new Date());
		dto.setUpdateTime(new Date());
	    Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyPk", dto.getCompanyPk());
        LgDriver lgDriver = new LgDriver();
        lgDriver.UpdateDTO(dto);
        driverDaoEx.insert(lgDriver);
		return dto;
	}
	
	//修改司机信息
	@Override
	public void updateDriver(LgDriverDto dto) {
		LgDriver driver = new LgDriver();
		driver.UpdateDTO(dto);
		driver.setUpdateTime(new Date());
		driverDaoEx.updatePartField(driver);
	}
	
	//删除司机
	@Override
	public void delDriver(String pk) {
		LgDriver driver = new LgDriver();
		driver.setPk(pk);
		driver.setIsDelete(2);
		driverDaoEx.updatePartField(driver);
	}
	
	//指派车辆时，查询可选司机
	@Override
	public List<LgDriverDto> getDrivers4Assign(Map<String, Object> map) {
		map.put("isDelete", 1);
        map.put("orderName", "updateTime");
        map.put("orderType", "desc");
        List<LgDriverDto> list = driverDaoEx.searchList(map);
		return list;
	}
	
	/*
	@Override
	public void delete(String pk) {
		driverDao.delete(pk);
	}


*/


}
