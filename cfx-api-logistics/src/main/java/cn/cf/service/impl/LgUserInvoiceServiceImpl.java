package cn.cf.service.impl;

import cn.cf.common.RestCode;
import cn.cf.dao.LgUserInvoiceDaoEx;
import cn.cf.dto.LgUserInvoiceDto;
import cn.cf.model.LgUserInvoice;
import cn.cf.service.LgUserInvoiceService;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class LgUserInvoiceServiceImpl implements LgUserInvoiceService {

    @Autowired
    LgUserInvoiceDaoEx lgUserInvoiceDaoEx;

    //添加发票信息
    @Override
    public String addInvoiceInfo(LgUserInvoiceDto dto) {
    	String result = RestCode.CODE_0000.toJson();
    	int count = lgUserInvoiceDaoEx.searchEntity(dto);
        if (count > 0) {
            return RestCode.CODE_Q002.toJson();
        }
    	dto.setPk(KeyUtils.getUUID());
        dto.setInsertTime(new Date());
        dto.setUpdateTime(new Date());
        LgUserInvoice invoice = new LgUserInvoice();
        invoice.UpdateDTO(dto);
        try {
        	lgUserInvoiceDaoEx.insert(invoice);
        	result = RestCode.CODE_0000.toJson(dto);
		} catch (Exception e) {
			result = RestCode.CODE_S999.toJson();
		}
        return result;
    }

    //根据companyPk或者userPk查询发票信息
    @Override
    public LgUserInvoice getInvoiceInfo(String companyPk,String userPk) {
       return lgUserInvoiceDaoEx.getByCompanyPkOrUserPk(companyPk,userPk);
    }

    //更新发票信息
    @Override
    public void updateInvoiceInfo(LgUserInvoiceDto dto) {
        LgUserInvoice invoice = new LgUserInvoice();
        invoice.UpdateDTO(dto);
        lgUserInvoiceDaoEx.updatePartField(invoice);
    }

    //对发票信息做存在性验证
    @Override
    public int searchEntity(LgUserInvoiceDto dto) {
        return lgUserInvoiceDaoEx.searchEntity(dto);
    }

	@Override
	public LgUserInvoiceDto getByPk(String pk) {
		return lgUserInvoiceDaoEx.getByPk(pk);
	}
}
