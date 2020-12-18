package cn.cf.service;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.dto.B2bSpecDto;

public interface B2bSpecService {
	/**
	 * 查询规格 ：
	 * @param parentPk 规格上级
	 * @return
	 */
	List<B2bSpecDto> searchSpecsList(String parentPk);
	
	/**
	 * 分页查询规格 ：
	 * @param parentPk 规格上级
	 * @return
	 */
	PageModel<B2bSpecDto> searchSpecsPage(String parentPk,String name,Integer start,Integer limit);

	List<B2bSpecDto>  searchSeriesNameBySeriesPks(String seriesPks);

}
