package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bMemubarManageDto;

public interface B2bMemubarManageService {

	/**
	 * 查询list
	 * @param map 参数
	 * @return
	 */
	List<B2bMemubarManageDto> searchList(Map<String,Object> map);
	
	
}
