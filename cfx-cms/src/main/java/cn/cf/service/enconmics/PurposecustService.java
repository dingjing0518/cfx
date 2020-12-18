package cn.cf.service.enconmics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bEconomicsPurposecustExDto;
import cn.cf.model.B2bEconomicsPurposecust;

public interface PurposecustService {
	/**
	 * 意向客户列表
	 * @param qm
	 * @param i
	 * @return
	 */
	PageModel<B2bEconomicsPurposecustExDto> searchEcPurposecustList(QueryModel<B2bEconomicsPurposecustExDto> qm);
	/**
	 * 
	 * 更新意向客户
	 * @param ecPurposecust
	 * @return
	 */
	Integer update(B2bEconomicsPurposecust ecPurposecust);

}
