package cn.cf.service.enconmics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bBillInventoryExtDto;
import cn.cf.dto.B2bBillOrderExtDto;

public interface BillOrderService {
	/**
	 * 票据订单
	 * @param qm
	 * @return
	 */
	PageModel<B2bBillOrderExtDto> getBillOrderList(QueryModel<B2bBillOrderExtDto> qm);

	PageModel<B2bBillInventoryExtDto> getBillInventory(QueryModel<B2bBillInventoryExtDto> qm);

}
