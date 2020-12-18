package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.B2bOnlinepayRecordExtDto;

public interface OnlinePayService  {
	/**
	 * 查询未被删除为禁用的线上支付商品
	 * @return
	 */
	List<B2bOnlinepayGoodsDto> searchNoIsDelAndIsvOnPayGoodsList();

	/**
	 * 线上支付记录
	 * @param qm
	 * @return
	 */
	PageModel<B2bOnlinepayRecordExtDto> searchOnlinePayRecord(QueryModel<B2bOnlinepayRecordExtDto> qm);

}
