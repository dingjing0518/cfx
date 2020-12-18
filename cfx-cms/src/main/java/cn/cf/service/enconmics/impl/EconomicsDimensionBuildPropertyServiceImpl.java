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
import cn.cf.entity.EconomicsBuildProperty;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionBuildPropertyService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionBuildPropertyServiceImpl implements EconomicsDimensionBuildPropertyService {

	@Autowired
	private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

	@Override
	public PageModel<EconomicsBuildProperty> getbuildProperty(QueryModel<EconomicsBuildProperty> qm) {
		PageModel<EconomicsBuildProperty> pm = new PageModel<EconomicsBuildProperty>();
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
		List<EconomicsBuildProperty> list = searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private int searchCount(Map map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("buildProperty");
		int size = 0;
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBuildProperty> list = JSON.parseArray(content, EconomicsBuildProperty.class);
			for (EconomicsBuildProperty economicsBuildProperty : list) {
				boolean checked = true;
				if (map.get("open") != null && !map.get("open").equals("")
						&& !economicsBuildProperty.getOpen().equals(map.get("open"))) {
					checked = false;
				}
				if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertStartTime")).after(economicsBuildProperty.getInsertTime()))) {
					checked = false;
				}
				if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertEndTime")).before(economicsBuildProperty.getInsertTime()))) {
					checked = false;
				}
				if (checked)
					size++;
			}
			return size;
		} else
			return 0;
	}

	private List<EconomicsBuildProperty> searchGridExt(Map map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("buildProperty");
		List<EconomicsBuildProperty> list = new ArrayList<>();
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String capacityContent = model.getContent();
			List<EconomicsBuildProperty> temp = JSON.parseArray(capacityContent, EconomicsBuildProperty.class);
			for (EconomicsBuildProperty economicsBuildProperty : temp) {
				boolean checked = true;
				if (map.get("open") != null && !map.get("open").equals("")
						&& !economicsBuildProperty.getOpen().equals(map.get("open"))) {
					checked = false;
				}
				if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertStartTime")).after(economicsBuildProperty.getInsertTime()))) {
					checked = false;
				}
				if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertEndTime")).before(economicsBuildProperty.getInsertTime()))) {
					checked = false;
				}
				if (checked)
					list.add(economicsBuildProperty);
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
	public EconomicsBuildProperty getByPk(String pk) {
		B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("buildProperty");
		if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
			List<EconomicsBuildProperty> economicsCapacityList = JSON.parseArray(capacity.getContent(),
					EconomicsBuildProperty.class);
			for (EconomicsBuildProperty capa : economicsCapacityList) {
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
	public int update(EconomicsBuildProperty economicsBuildProperty) {
		List<EconomicsBuildProperty> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("buildProperty");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String capacityContent = model.getContent();
			List<EconomicsBuildProperty> EconomicsBuildPropertyList = JSON.parseArray(capacityContent,
					EconomicsBuildProperty.class);
			for (EconomicsBuildProperty capa : EconomicsBuildPropertyList) {
				if (!Objects.equals(capa.getPk(), economicsBuildProperty.getPk())) {
					list.add(capa);
				} else {
					capa.setScore(economicsBuildProperty.getScore());
					capa.setMinMortgage(economicsBuildProperty.getMinMortgage());
					capa.setMaxMortgage(economicsBuildProperty.getMaxMortgage());
					capa.setMinTotalMortgage(economicsBuildProperty.getMinTotalMortgage());
					capa.setMaxTotalMortgage(economicsBuildProperty.getMaxTotalMortgage());
					capa.setOpen(1);
					capa.setMortgageName(economicsBuildProperty.getMinMortgage() + "-"
							+ economicsBuildProperty.getMaxMortgage() + "w");
					capa.setTotalMortgageName(economicsBuildProperty.getMinTotalMortgage() + "-"
							+ economicsBuildProperty.getMaxTotalMortgage() + "%");
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
	public int insert(EconomicsBuildProperty economicsBuildProperty) {

		List<EconomicsBuildProperty> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("buildProperty");

		EconomicsBuildProperty capa = new EconomicsBuildProperty();
		capa.setPk(KeyUtils.getUUID());
		capa.setScore(economicsBuildProperty.getScore());
		capa.setMinMortgage(economicsBuildProperty.getMinMortgage());
		capa.setMaxMortgage(economicsBuildProperty.getMaxMortgage());
		capa.setMinTotalMortgage(economicsBuildProperty.getMinTotalMortgage());
		capa.setMaxTotalMortgage(economicsBuildProperty.getMaxTotalMortgage());
		capa.setOpen(1);
		capa.setMortgageName(
				economicsBuildProperty.getMinMortgage() + "-" + economicsBuildProperty.getMaxMortgage() + "w");
		capa.setTotalMortgageName(economicsBuildProperty.getMinTotalMortgage() + "-"
				+ economicsBuildProperty.getMaxTotalMortgage() + "%");
		capa.setInsertTime(new Date());
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBuildProperty> EconomicsBuildPropertyList = JSON.parseArray(content,
					EconomicsBuildProperty.class);
			list.addAll(EconomicsBuildPropertyList);
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

	@Override
	public List<EconomicsBuildProperty> getBuildPropertyList() {
		B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("buildProperty");
		if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
			String capacityContent = capacity.getContent();
			return JSON.parseArray(capacityContent, EconomicsBuildProperty.class);
		} else
			return null;
	}

	@Override
	public int updateStatus(EconomicsBuildProperty economicsBuildProperty) {

		List<EconomicsBuildProperty> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("buildProperty");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBuildProperty> economicsBuildProperties = JSON.parseArray(content,
					EconomicsBuildProperty.class);
			for (EconomicsBuildProperty capa : economicsBuildProperties) {
				if (!Objects.equals(capa.getPk(), economicsBuildProperty.getPk())) {
					list.add(capa);
				} else {
					capa.setOpen(economicsBuildProperty.getOpen());
					list.add(capa);
				}
			}
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else
			return 0;
	}
}
