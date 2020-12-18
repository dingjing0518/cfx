package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bLiveBroadCastCategoryExDto;


public interface B2bBroadCastCategoryService {
	/**
	 * 搜索直播分类列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bLiveBroadCastCategoryExDto> searchLiveBroadCastCategorydata(QueryModel<B2bLiveBroadCastCategoryExDto> qm);
	/**
	 * 修改直播分类
	 * @param b2bLiveBraodCastCategoryExDto
	 * @return
	 * @throws Exception
	 */
	int updateLiveBroadCastCategory(B2bLiveBroadCastCategoryExDto b2bLiveBraodCastCategoryExDto) throws Exception;
	/**
	 * 根据条件获取直播分类集合
	 * @return
	 */
	List<B2bLiveBroadCastCategoryExDto> getAllBroadCastCategory();

}
