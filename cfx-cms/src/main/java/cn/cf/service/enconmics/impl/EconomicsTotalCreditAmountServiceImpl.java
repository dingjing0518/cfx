package cn.cf.service.enconmics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bEconomicsDimensionExtDao;
import cn.cf.entity.EconTotalCreditAmount;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsTotalCreditAmountService;
import cn.cf.util.KeyUtils;
@Service
public class EconomicsTotalCreditAmountServiceImpl  implements EconomicsTotalCreditAmountService{
	@Autowired
	private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

	@Override
	public List<EconTotalCreditAmount> getTotalCreditAmountList() {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			return JSON.parseArray(content, EconTotalCreditAmount.class);
		} else
			return null;
	}

	@Override
	public EconTotalCreditAmount getByPk(String pk) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			List<EconTotalCreditAmount> economicsList = JSON.parseArray(model.getContent(), EconTotalCreditAmount.class);
			for (EconTotalCreditAmount capa : economicsList) {
				if (Objects.equals(capa.getPk(), pk)) {
					return capa;
				}
			}
		} else {
			return null;
		}
		return null;
	}

	@Override
	public PageModel<EconTotalCreditAmount> getTotalCreditAmount(QueryModel<EconTotalCreditAmount> qm) {
		PageModel<EconTotalCreditAmount> pm = new PageModel<EconTotalCreditAmount>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("open", qm.getEntity().getOpen());
		int totalCount = searchCount(map);
		List<EconTotalCreditAmount> list = searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private int searchCount(Map<String, Object> map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		int size = 0;
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconTotalCreditAmount> list = JSON.parseArray(content, EconTotalCreditAmount.class);
			for (EconTotalCreditAmount econTotalCreditAmount : list) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !econTotalCreditAmount.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(econTotalCreditAmount.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(econTotalCreditAmount.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    size++;
            }
			return size;
		} else
			return 0;
	}

	private List<EconTotalCreditAmount> searchGridExt(Map<String, Object> map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		List<EconTotalCreditAmount> list = new ArrayList<>();
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			 List<EconTotalCreditAmount> temp = JSON.parseArray(content, EconTotalCreditAmount.class);
	            for (EconTotalCreditAmount econTotalCreditAmount : temp) {
	                boolean checked = true;
	                if (map.get("open") != null && !map.get("open").equals("") && !econTotalCreditAmount.getOpen().equals(map.get("open"))) {
	                    checked = false;
	                }
	                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(econTotalCreditAmount.getInsertTime()))) {
	                    checked = false;
	                }
	                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(econTotalCreditAmount.getInsertTime()))) {
	                    checked = false;
	                }
	                if (checked)
	                    list.add(econTotalCreditAmount);
	            }
	            
	            Integer start = (Integer) map.get("start");
	            Integer lim = (Integer) map.get("limit");
	            if (list.size() > start + lim)
	                return list.subList(start, start + lim);
	            else if (list.size() > start)
	                return list.subList(start, list.size());
	            else
	                return list;
		} else
			return null;
	}

	@Override
	public int updateStatus(EconTotalCreditAmount econTotalCreditAmount) {
		List<EconTotalCreditAmount> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconTotalCreditAmount> economicsBuildProperties = JSON.parseArray(content, EconTotalCreditAmount.class);
			for (EconTotalCreditAmount capa : economicsBuildProperties) {
				if (!Objects.equals(capa.getPk(), econTotalCreditAmount.getPk())) {
					list.add(capa);
				} else {
					capa.setOpen(econTotalCreditAmount.getOpen());
					list.add(capa);
				}
			}
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else
			return 0;
	}

	@Override
	public int update(EconTotalCreditAmount econTotalCreditAmount) {
		List<EconTotalCreditAmount> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String capacityContent = model.getContent();
			List<EconTotalCreditAmount> EconomicsCommericalBankList = JSON.parseArray(capacityContent, EconTotalCreditAmount.class);
			for (EconTotalCreditAmount capa : EconomicsCommericalBankList) {
				if (!Objects.equals(capa.getPk(), econTotalCreditAmount.getPk())) {
					list.add(capa);
				} else {
					capa.setScore(econTotalCreditAmount.getScore());
					capa.setMinAmount(econTotalCreditAmount.getMinAmount());
					capa.setMaxAmount(econTotalCreditAmount.getMaxAmount());
					capa.setYear(econTotalCreditAmount.getYear());
					capa.setOpen(1);
					capa.setName(econTotalCreditAmount.getMinAmount() + "w <授信总金额<=" + econTotalCreditAmount.getMaxAmount()+"w");
					capa.setInsertTime(new Date());
					list.add(capa);
				}
			}
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else
			return 0;
	}

	@Override
	public int insert(EconTotalCreditAmount econTotalCreditAmount) {

		List<EconTotalCreditAmount> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("totalCreditAmount");
		EconTotalCreditAmount capa = new EconTotalCreditAmount();
		capa.setPk(KeyUtils.getUUID());
		capa.setScore(econTotalCreditAmount.getScore());
		capa.setMinAmount(econTotalCreditAmount.getMinAmount());
		capa.setMaxAmount(econTotalCreditAmount.getMaxAmount());
		capa.setYear(econTotalCreditAmount.getYear());
		capa.setOpen(1);
		capa.setName(econTotalCreditAmount.getMinAmount() + "w <授信总金额<=" + econTotalCreditAmount.getMaxAmount()+"w");
		capa.setInsertTime(new Date());
		
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconTotalCreditAmount> EconomicsCommericalBankList = JSON.parseArray(content, EconTotalCreditAmount.class);
			list.addAll(EconomicsCommericalBankList);
			list.add(capa);
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else if (model != null) {
			list.add(capa);
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else
			return 0;
	}

}
