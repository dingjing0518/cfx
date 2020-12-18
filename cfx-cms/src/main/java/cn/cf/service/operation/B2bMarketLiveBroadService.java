package cn.cf.service.operation;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bMarketLiveBroadExtDto;
import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.dto.SysMarketLivebroadDto;
import cn.cf.model.SysLivebroadCategory;

public interface B2bMarketLiveBroadService {
	/**
	 * 搜索行情直播列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bMarketLiveBroadExtDto> searchMarketLiveBroaddata(QueryModel<B2bMarketLiveBroadExtDto> qm);
	/**
	 * 更新行情直播
	 * @param b2bMarketLiveBroadExtDto
	 * @return
	 */
	int updateMarketLiveBroad(B2bMarketLiveBroadExtDto b2bMarketLiveBroadExtDto);

	/**
	 * 根据pk查询直播
	 * @param pk
	 * @return
	 */
	SysMarketLivebroadDto getMarketLiveBroadByPk(String pk);

	/**
	 * 根据Pk查询直播分类
	 * @param pk
	 * @return
	 */
	SysLivebroadCategoryDto getLiveCategoryByPk(String pk);

}
