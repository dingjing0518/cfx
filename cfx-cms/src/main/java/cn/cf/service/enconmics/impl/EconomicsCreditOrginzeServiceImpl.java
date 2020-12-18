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
import cn.cf.entity.EconCreditOrginze;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsCreditOrginzeService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsCreditOrginzeServiceImpl implements EconomicsCreditOrginzeService{
	@Autowired
	private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;
	
	@Override
	public List<EconCreditOrginze> getCreditOrginzeList() {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			return JSON.parseArray(content, EconCreditOrginze.class);
		} else
			return null;
	}

	@Override
	public EconCreditOrginze getByPk(String pk) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			List<EconCreditOrginze> economicsList = JSON.parseArray(model.getContent(), EconCreditOrginze.class);
			for (EconCreditOrginze capa : economicsList) {
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
	public PageModel<EconCreditOrginze> getCreditOrginze(QueryModel<EconCreditOrginze> qm) {
		PageModel<EconCreditOrginze> pm = new PageModel<EconCreditOrginze>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
		map.put("open", qm.getEntity().getOpen());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		int totalCount = searchCount(map);
		List<EconCreditOrginze> list = searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	private int searchCount(Map<String, Object> map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		int size = 0;
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconCreditOrginze> list = JSON.parseArray(content, EconCreditOrginze.class);
			for (EconCreditOrginze econCreditOrginze : list) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !econCreditOrginze.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(econCreditOrginze.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(econCreditOrginze.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    size++;
            }
			
			return size;
		} else
			return 0;
	}

	private List<EconCreditOrginze> searchGridExt(Map<String, Object> map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		 List<EconCreditOrginze> list = new ArrayList<>();
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconCreditOrginze> temp = JSON.parseArray(content, EconCreditOrginze.class);
			for (EconCreditOrginze econCreditOrginze : temp) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !econCreditOrginze.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(econCreditOrginze.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(econCreditOrginze.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(econCreditOrginze);
            }
			Integer start = (Integer) map.get("start");
			Integer lim = (Integer) map.get("limit");
			if (temp.size() > start + lim)
				return temp.subList(start, start + lim);
			else if (temp.size() > start)
				return temp.subList(start, temp.size());
			else
				return temp;
		} else
			return null;
	}
	@Override
	public int updateStatus(EconCreditOrginze econCreditOrginze) {
		List<EconCreditOrginze> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconCreditOrginze> economicsBuildProperties = JSON.parseArray(content, EconCreditOrginze.class);
			for (EconCreditOrginze capa : economicsBuildProperties) {
				if (!Objects.equals(capa.getPk(), econCreditOrginze.getPk())) {
					list.add(capa);
				} else {
					capa.setOpen(econCreditOrginze.getOpen());
					list.add(capa);
				}
			}
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else
			return 0;
	}

	@Override
	public int update(EconCreditOrginze econCreditOrginze) {
		List<EconCreditOrginze> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String capacityContent = model.getContent();
			List<EconCreditOrginze> EconomicsCommericalBankList = JSON.parseArray(capacityContent, EconCreditOrginze.class);
			for (EconCreditOrginze capa : EconomicsCommericalBankList) {
				if (!Objects.equals(capa.getPk(), econCreditOrginze.getPk())) {
					list.add(capa);
				} else {
					capa.setScore(econCreditOrginze.getScore());
					capa.setMinCount(econCreditOrginze.getMinCount());
					capa.setMaxCount(econCreditOrginze.getMaxCount());
					capa.setOpen(1);
					capa.setCountName(econCreditOrginze.getMinCount() + "<授信机构数<=" + econCreditOrginze.getMaxCount());
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
	public int insert(EconCreditOrginze econCreditOrginze) {

		List<EconCreditOrginze> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("creditOrginze");
		EconCreditOrginze capa = new EconCreditOrginze();
		capa.setPk(KeyUtils.getUUID());
		capa.setScore(econCreditOrginze.getScore());
		capa.setMinCount(econCreditOrginze.getMinCount());
		capa.setMaxCount(econCreditOrginze.getMaxCount());
		capa.setOpen(1);
		capa.setCountName(econCreditOrginze.getMinCount() + "<授信机构数<=" + econCreditOrginze.getMaxCount());
		capa.setInsertTime(new Date());
		
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconCreditOrginze> EconomicsCommericalBankList = JSON.parseArray(content, EconCreditOrginze.class);
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
