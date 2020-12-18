package cn.cf.service.impl;

import cn.cf.PageModel;
import cn.cf.dao.LgCarDaoEx;
import cn.cf.dto.LgCarDto;
import cn.cf.model.LgCar;
import cn.cf.service.LgCarService;
import cn.cf.util.KeyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LgCarServiceImpl implements LgCarService {

	@Autowired
    private LgCarDaoEx lgCarDaoEx;

    @Override
    public PageModel<LgCarDto> searchCarList(Map<String, Object> map) {
        PageModel<LgCarDto> pm = new PageModel<LgCarDto>();
        map.put("isDeleted", 1);
        map.put("orderName", "updateTime");
        map.put("orderType", "desc");
        List<LgCarDto> list=lgCarDaoEx.searchList(map);
        int counts=lgCarDaoEx.searchGridCount(map);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        if(map.get("start")!=null){
            pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
            pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
        }
        return pm;
    }
    
    //查询车辆信息
    @Override
    public LgCarDto searchCarByPk(String pk) {
        return lgCarDaoEx.getByPk(pk);
    }
    
    //查询车辆信息是否重复
    @Override
    public Integer searchEntity(LgCarDto dto) {
        return lgCarDaoEx.searchEntity(dto);
    }
    
    //新增车辆
    @Override
    public LgCarDto addCar(LgCarDto dto) {
        dto.setPk(KeyUtils.getUUID());
        dto.setInsertTime(new Date());
        dto.setUpdateTime(new Date());
        dto.setIsDeleted(1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyPk", dto.getCompanyPk());
        LgCar car = new LgCar();
        car.UpdateDTO(dto);
        lgCarDaoEx.insert(car);
        return  dto;
    }
    
    //修改车辆
    @Override
    public void updateCar(LgCarDto dto) {
        dto.setUpdateTime(new Date());
        LgCar car = new LgCar();
        car.UpdateDTO(dto);
        lgCarDaoEx.updatePartField(car);
    }
    
    //删除车辆
    @Override
    public void delCar(String pk) {
    	LgCar car = new LgCar();
        car.setPk(pk);
        car.setIsDeleted(2);
        lgCarDaoEx.updatePartField(car);
    }
    
    //查询承运商指派车辆时，可以选择的所有车辆
	@Override
	public List<LgCarDto> getCars4Assign(Map<String, Object> map) {
		map.put("isDeleted", 1);
        map.put("orderName", "updateTime");
        map.put("orderType", "desc");
        List<LgCarDto> list=lgCarDaoEx.searchList(map);
		return list;
	}



}
