package cn.cf.service;


import java.util.List;
import java.util.Map;
import cn.cf.PageModel;
import cn.cf.dto.LgDriverDto;

public interface LgDriverService {
	
	/**
	 * 查询司机列表
	 * @param map 参数
	 * @return
	 */
	PageModel<LgDriverDto> searchLgDriver(Map<String, Object> map);
	
	/**
	 * 根据pk查询司机信息
	 * @param pk  pk
	 * @return
	 */
	LgDriverDto getByPk(String pk);
	
	/**
	 * 查询司机信息是否已经存在
	 * @param dto  参数
	 * @return
	 */
	int searchEntity(LgDriverDto dto);
	
	/**
	 * 添加司机信息
	 * @param dto  司机信息
	 * @return
	 */
	LgDriverDto add(LgDriverDto dto);
	
	/**
	 * 修改司机信息
	 * @param dto  司机信息
	 */
	void updateDriver(LgDriverDto dto);

	/**
	 * 删除司机信息
	 * @param pk  司机pk
	 */
    void delDriver(String pk);
	
    /**
     * 指派车辆时，查询可选司机
     * @param map  参数
     * @return
     */
    List<LgDriverDto> getDrivers4Assign(Map<String,Object> map);
    
}
