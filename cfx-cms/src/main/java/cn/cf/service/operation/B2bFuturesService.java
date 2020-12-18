package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bFuturesTypeDto;
import cn.cf.dto.B2bFuturesTypeDtoEx;
import cn.cf.entity.B2bFutures;
import cn.cf.entity.B2bFuturesEx;
import cn.cf.model.B2bFuturesType;

public interface B2bFuturesService {
	/**
	 * 搜索期货列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bFutures> searchFuturesList(QueryModel<B2bFuturesEx> qm);

	/**
	 * 更新期货
	 * @param future
	 * @return
	 */
	String updateFutures(B2bFutures future);
	/**
	 * 删除期货
	 * @param pk
	 * @return
	 */
	String delFutures(String pk);
	/**
	 * 查询所有期货信息
	 * @return
	 */
	List<B2bFuturesTypeDto> searchFuturesTypeList(String flag);
	/**
	 * 更新期货种类
	 * @param futuresType
	 * @return
	 */
	String updateFuturesType(B2bFuturesType futuresType);
	/**
	 * 搜索取货种类列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bFuturesTypeDtoEx> searchFuturesTypeListGrid(QueryModel<B2bFuturesTypeDtoEx> qm);


}
