package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bBillInventoryExtDao;
import cn.cf.dao.B2bBillOrderExtDao;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillInventoryExtDto;
import cn.cf.dto.B2bBillOrderExtDto;
import cn.cf.service.enconmics.BillOrderService;

@Service
public class BillOrderServiceImpl  implements BillOrderService{

	@Autowired 
	private B2bBillOrderExtDao  b2bBillOrderExtDao;
	
	
	@Autowired
	private B2bBillInventoryExtDao b2bBillInventoryExtDao;
	
	
	
	
	@Override
	public PageModel<B2bBillOrderExtDto> getBillOrderList(QueryModel<B2bBillOrderExtDto> qm) {
        PageModel<B2bBillOrderExtDto> pm = new PageModel<B2bBillOrderExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
   
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        map.put("serialNumber", qm.getEntity().getSerialNumber());
        map.put("purchaserName", qm.getEntity().getPurchaserName());
        map.put("insertTimeBegin", qm.getEntity().getInsertTimeBegin());
        map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
        map.put("goodsName", qm.getEntity().getGoodsName());
        map.put("status", qm.getEntity().getStatus());
        
        if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_SUPPLIER)) {
			map.put("supplierCol", ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_SUPPLIER);
		}
        
        if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_PURCHESER)) {
			map.put("purcheserCol", ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_PURCHESER);
		}
        if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_STORENAME)) {
			map.put("storeNameCol", ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_STORENAME);
		}
        int counts = b2bBillOrderExtDao.searchGridCountExt(map);
        List<B2bBillOrderExtDto> list = b2bBillOrderExtDao.searchGridExt(map);
        pm.setDataList(list);
        pm.setTotalCount(counts);
        return pm;
    }

	@Override
	public PageModel<B2bBillInventoryExtDto> getBillInventory(QueryModel<B2bBillInventoryExtDto> qm) {
        PageModel<B2bBillInventoryExtDto> pm = new PageModel<B2bBillInventoryExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        int counts = b2bBillInventoryExtDao.searchGridCountExt(map);
        List<B2bBillInventoryExtDto> list = b2bBillInventoryExtDao.searchGridExt(map);
        pm.setDataList(list);
        pm.setTotalCount(counts);
        return pm;
    }

}
