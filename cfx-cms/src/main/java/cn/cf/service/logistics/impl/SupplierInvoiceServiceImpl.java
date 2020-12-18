package cn.cf.service.logistics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.LgDeliveryOrderExtDao;
import cn.cf.dto.SupplierInvoiceDto;
import cn.cf.model.SearchInvoice;
import cn.cf.service.logistics.SupplierInvoiceService;

@Service
public class SupplierInvoiceServiceImpl implements SupplierInvoiceService {

    @Autowired
    private LgDeliveryOrderExtDao lgOrderExtDao;

    @Override
    public PageModel<SupplierInvoiceDto> searchSupplierInvoiceList(QueryModel<SearchInvoice> qm, int i) {
        PageModel<SupplierInvoiceDto> pm = new PageModel<SupplierInvoiceDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (i == 1) {
            map.put("start", qm.getStart());
            map.put("limit", qm.getLimit());
        }
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("logisticsCompanyName", qm.getEntity().getLogisticsCompanyName());
        map.put("applicationStartTime", qm.getEntity().getApplicationStartTime());
        map.put("applicationEndTime", qm.getEntity().getApplicationEndTime());
        map.put("orderPk", qm.getEntity().getOrderPk());
        map.put("supplierInvoiceStatus", qm.getEntity().getSupplierInvoiceStatus());
        
        map.put("colComName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_SUPPINVOICE_COL_COMNAME));
        map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_SUPPINVOICE_COL_CONTACTS));
        map.put("colContactsTel",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_SUPPINVOICE_COL_CONTACTSTEL));

        int totalCount = lgOrderExtDao.searchInvoiceCount(map);
        List<SupplierInvoiceDto> list = lgOrderExtDao.searchInvoice(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateSupplierInvoice(Map<String, Object> map) {
        return lgOrderExtDao.updateSupplierInvoice(map);
    }

    public Double checkDouble(Double num) {
        if (num == null || num.equals("")) {
            return 0.0;
        } else {
            return num;
        }
    }

}
