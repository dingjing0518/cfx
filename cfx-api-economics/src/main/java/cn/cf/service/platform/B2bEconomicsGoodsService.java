package cn.cf.service.platform;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsGoodsDto;

public interface B2bEconomicsGoodsService {

	/**
	 * 查询所有金融产品
	 * @return
	 */
	List<B2bEconomicsGoodsDto> searchList();
	
	/**
	 * 查询询单个金融产品
	 * @param pk
	 * @return
	 */
	B2bEconomicsGoodsDto getByPk(String pk);
	
	/**
	 * 查询单个金融产品
	 * @return
	 */
	B2bEconomicsGoodsDto searchOne(Map<String,Object> map);
}
