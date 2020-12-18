package cn.cf.service;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.dto.B2bVarietiesDto;

public interface B2bVarietiesService {
	/**
	 * /**
	 * 查询商品品种：
	 * @param type ：1品种  2子品种
	 * @param parentPk 品种上级
	 * @return
	 */
	List<B2bVarietiesDto> searchVarietiesList(String parentPk);
	
	/**
	 * 
	 * 分页查询商品品种：
	 * @param type ：1品种  2子品种
	 * @param parentPk 品种上级
	 * @return
	 */
	PageModel<B2bVarietiesDto> searchVarietiesPage(String parentPk,String name,Integer start,Integer limit);

	List<B2bVarietiesDto> searchVarietiesNameByVarietiesPks(String varietiesPks);

}
