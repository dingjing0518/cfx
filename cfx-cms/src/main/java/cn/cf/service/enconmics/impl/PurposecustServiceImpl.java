package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bEconomicsPurposecustExDao;
import cn.cf.dto.B2bEconomicsPurposecustExDto;
import cn.cf.model.B2bEconomicsPurposecust;
import cn.cf.service.enconmics.PurposecustService;

@Service
public class PurposecustServiceImpl implements PurposecustService {

	@Autowired
	private B2bEconomicsPurposecustExDao purchaserInvoiceDao;

	@Override
	public PageModel<B2bEconomicsPurposecustExDto> searchEcPurposecustList(
			QueryModel<B2bEconomicsPurposecustExDto> qm) {
		PageModel<B2bEconomicsPurposecustExDto> pm = new PageModel<B2bEconomicsPurposecustExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("contactsTel", qm.getEntity().getContactsTel());
		map.put("upStartTime", qm.getEntity().getUpdateStartTime());
		map.put("upEndTime", qm.getEntity().getUpdateEndTime());
		map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
		map.put("status", qm.getEntity().getStatus());
		map.put("type", qm.getEntity().getType());
		
		map.put("colCompanyName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_PURPOSE_COL_COM_NAME));
		map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_PURPOSE_COL_CONTACTS));
		map.put("colContactsTel",  CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CUST_PURPOSE_COL_CONTACTSTEL));
		
		int totalCount = purchaserInvoiceDao.countPurposecustGrid(map);
		List<B2bEconomicsPurposecustExDto> list = purchaserInvoiceDao.searchPurposecustGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public Integer update(B2bEconomicsPurposecust ecPurposecust) {
		return purchaserInvoiceDao.updateExt(ecPurposecust);
	}

}
