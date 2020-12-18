package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bOnlinepayGoodsExtDao;
import cn.cf.dao.B2bOnlinepayRecordExtDao;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.B2bOnlinepayRecordExtDto;
import cn.cf.service.enconmics.OnlinePayService;

@Service
public class OnlinePayServiceImpl  implements OnlinePayService{

	@Autowired
	private B2bOnlinepayGoodsExtDao  b2bOnlinepayGoodsDao;
	
	@Autowired
	private B2bOnlinepayRecordExtDao b2bOnlinepayRecordExtDao;
	@Override
	public List<B2bOnlinepayGoodsDto> searchNoIsDelAndIsvOnPayGoodsList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return b2bOnlinepayGoodsDao.searchList(map);
	}
	
	
	@Override
	public PageModel<B2bOnlinepayRecordExtDto> searchOnlinePayRecord(QueryModel<B2bOnlinepayRecordExtDto> qm) {
		PageModel<B2bOnlinepayRecordExtDto> pm = new PageModel<B2bOnlinepayRecordExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete",  Constants.ONE);
		map.put("orderNumber", qm.getEntity().getOrderNumber());
		map.put("status", qm.getEntity().getStatus());
	    map.put("purchaserName", qm.getEntity().getPurchaserName());
	    map.put("supplierName", qm.getEntity().getSupplierName());
	    map.put("receivablesAccount", qm.getEntity().getReceivablesAccount());
	    map.put("onlinePayGoodsPk", qm.getEntity().getOnlinePayGoodsPk());
	    map.put("shotName", qm.getEntity().getShotName());
		int totalCount = b2bOnlinepayRecordExtDao.searchGridExtCount(map);
		List<B2bOnlinepayRecordExtDto> list = b2bOnlinepayRecordExtDao.searchExtList(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	
	
	

}
