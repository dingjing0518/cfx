package cn.cf.service.operation;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bSpecHotDto;
import cn.cf.dto.B2bSpecHotDtoEx;
import cn.cf.model.B2bSpecHot;

public interface B2bSpecHotService {
	/**
	 * 查询热门规格列表
	 * @param query
	 * @return
	 */
	PageModel<B2bSpecHotDto> searchList(QueryModel<B2bSpecHotDtoEx> query);
	/**
	 * 更新热门规格
	 * @param specHot
	 * @return
	 */
	String update(B2bSpecHot specHot);


	/**
	 * 删除热门规格
	 * @param pk
	 * @return
	 */
	String delete(String pk);
	/**
	 * 修改热门规格的状态
	 * @param specHot
	 * @return
	 */
	String updateSpecHotStatus(B2bSpecHot specHot);
}
