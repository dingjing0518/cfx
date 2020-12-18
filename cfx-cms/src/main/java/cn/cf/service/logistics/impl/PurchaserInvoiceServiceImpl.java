package cn.cf.service.logistics.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.LgDeliveryOrderExtDao;
import cn.cf.dao.LgMemberInvoicesDao;
import cn.cf.dto.LgOrderDetailDto;
import cn.cf.dto.PurchaserInvoiceDto;
import cn.cf.model.LgMemberInvoices;
import cn.cf.model.SearchInvoice;
import cn.cf.service.logistics.PurchaserInvoiceService;
import cn.cf.util.KeyUtils;

@Service
public class PurchaserInvoiceServiceImpl implements PurchaserInvoiceService {

    @Autowired
    private LgDeliveryOrderExtDao lgOrderExtDao;

    @Autowired
    private LgMemberInvoicesDao lgMemberInvoicesDao;

    @Override
    public PageModel<PurchaserInvoiceDto> searchPurchaserInvoiceList(QueryModel<SearchInvoice> qm, int i) {
        PageModel<PurchaserInvoiceDto> pm = new PageModel<PurchaserInvoiceDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 是否显示分页字段
        if (i == 1) {
            map.put("start", qm.getStart());
            map.put("limit", qm.getLimit());
        }
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("invoiceName", qm.getEntity().getInvoiceName());
        map.put("applicateStartTime", qm.getEntity().getApplicateStartTime());
        map.put("applicateEndTime", qm.getEntity().getApplicateEndTime());
        map.put("invoiceRegPhone", qm.getEntity().getInvoiceRegPhone());
        map.put("invoiceStartTime", qm.getEntity().getInvoiceStartTime());
        map.put("invoiceEndTime", qm.getEntity().getInvoiceEndTime());
        map.put("memberInvoiceStatus", qm.getEntity().getMemberInvoiceStatus());
        
        map.put("colInvoiceName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICENAME));
        map.put("colInvoiceTaxidNumber", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICETAXIDNUMBER));
        map.put("colInvoiceAddress", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEREGADDRESS));
        map.put("colInvoiceRegPhone", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEREGPHONE));
        map.put("colInvoiceBankName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEBANKNAME));
        map.put("colInvoiceAccountName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEBANKACCOUNT));
        map.put("colInvoiceContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_CONTACTS));
        map.put("colInvoiceContactsTel", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_CONTACTSTEL));
        map.put("colAddress", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_PURINVOICE_COL_ADDRESS));

        int totalCount = lgOrderExtDao.searchPurchaserInvoiceCount(map);
        List<PurchaserInvoiceDto> list = lgOrderExtDao.searchPurchaserInvoice(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    @Transactional
    public int updateInvoice(Map<String, Object> par) {
        String pks = par.get("pk").toString();
        String tempPk = KeyUtils.getUUID();
        par.put("memberInvoicePk", tempPk);
        Date tempDate = new Date();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        LgOrderDetailDto dto = lgOrderExtDao.selectDetailByPk(pks);
        LgMemberInvoices lgMemberInvoices = new LgMemberInvoices();
        lgMemberInvoices.setPk(tempPk);
        lgMemberInvoices.setMonth(String.valueOf(month));
        lgMemberInvoices.setBillingAmount(dto.getPresentTotalFreight());
        lgMemberInvoices.setInsertTime(tempDate);
        lgMemberInvoices.setUpdateTime(tempDate);
        lgMemberInvoices.setApplyTime(tempDate);
        lgMemberInvoices.setStatus(2);
        lgMemberInvoices.setInvoiceTime(tempDate);
        int vert = lgOrderExtDao.updateInvoice(par) + lgMemberInvoicesDao.insert(lgMemberInvoices);
        return vert;
    }

}
