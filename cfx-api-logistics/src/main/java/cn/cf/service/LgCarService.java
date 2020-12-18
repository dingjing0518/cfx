package cn.cf.service;


import java.util.List;
import java.util.Map;
import cn.cf.PageModel;
import cn.cf.dto.LgCarDto;

public interface LgCarService {

	/**
	 * 车辆列表查询
	 * @param map  参数
	 * @return
	 */
    PageModel<LgCarDto> searchCarList(Map<String, Object> map);
    
    /**
     * 查询车辆信息
     * @param pk pk
     * @return
     */
    LgCarDto searchCarByPk(String pk);
    
    /**
     * 查询车辆信息是否重复
     * @param dto  参数
     * @return
     */
    Integer searchEntity(LgCarDto dto);

    /**
     * 新增车辆
     * @param dto 参数
     * @return
     */
    LgCarDto addCar(LgCarDto dto);
    
    /**
     * 修改车辆
     * @param dto  参数
     */
    void updateCar(LgCarDto dto);
    
    //删除车辆
    void delCar(String pk);
    
    //查询承运商指派车辆时，可选的车辆
    List<LgCarDto> getCars4Assign(Map<String,Object> map);
    

}
