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
import cn.cf.entity.EconomicsBank;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsCommericalBankService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsCommericalBankServiceImpl implements EconomicsCommericalBankService {

	@Autowired
	private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

	@Override
	public List<EconomicsBank> getCommericalBankList() {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			return JSON.parseArray(content, EconomicsBank.class);
		} else
			return null;
	}

	@Override
	public EconomicsBank getByPk(String pk) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			List<EconomicsBank> economicsList = JSON.parseArray(model.getContent(), EconomicsBank.class);
			for (EconomicsBank capa : economicsList) {
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
	public PageModel<EconomicsBank> getCommericalBank(QueryModel<EconomicsBank> qm) {
		PageModel<EconomicsBank> pm = new PageModel<EconomicsBank>();
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
		List<EconomicsBank> list = searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private int searchCount(Map<String, Object> map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		int size = 0;
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBank> list = JSON.parseArray(content, EconomicsBank.class);
			for (EconomicsBank economicsBank : list) {
				boolean checked = true;
				if (map.get("open") != null && !map.get("open").equals("")
						&& !economicsBank.getOpen().equals(map.get("open"))) {
					checked = false;
				}
				if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertStartTime")).after(economicsBank.getInsertTime()))) {
					checked = false;
				}
				if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertEndTime")).before(economicsBank.getInsertTime()))) {
					checked = false;
				}
				if (checked)
					size++;
			}
			return size;
		} else
			return 0;
	}

	private List<EconomicsBank> searchGridExt(Map<String, Object> map) {
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		List<EconomicsBank> list = new ArrayList<>();
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBank> temp = JSON.parseArray(content, EconomicsBank.class);
			for (EconomicsBank economicsBank : temp) {
				boolean checked = true;
				if (map.get("open") != null && !map.get("open").equals("")
						&& !economicsBank.getOpen().equals(map.get("open"))) {
					checked = false;
				}
				if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertStartTime")).after(economicsBank.getInsertTime()))) {
					checked = false;
				}
				if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Date
						.valueOf((String) map.get("insertEndTime")).before(economicsBank.getInsertTime()))) {
					checked = false;
				}
				if (checked)
					list.add(economicsBank);
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
	public int updateStatus(EconomicsBank economicsCommericalBank) {
		List<EconomicsBank> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBank> economicsBuildProperties = JSON.parseArray(content, EconomicsBank.class);
			for (EconomicsBank capa : economicsBuildProperties) {
				if (!Objects.equals(capa.getPk(), economicsCommericalBank.getPk())) {
					list.add(capa);
				} else {
					capa.setOpen(economicsCommericalBank.getOpen());
					list.add(capa);
				}
			}
			model.setContent(JSON.toJSONString(list));
			return b2bEconomicsDimensionDao.update(model);
		} else
			return 0;
	}

	@Override
	public int update(EconomicsBank economicsCommericalBank) {
		List<EconomicsBank> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String capacityContent = model.getContent();
			List<EconomicsBank> EconomicsCommericalBankList = JSON.parseArray(capacityContent, EconomicsBank.class);
			for (EconomicsBank capa : EconomicsCommericalBankList) {
				if (!Objects.equals(capa.getPk(), economicsCommericalBank.getPk())) {
					list.add(capa);
				} else {
					capa.setScore(economicsCommericalBank.getScore());
					capa.setMinCount(economicsCommericalBank.getMinCount());
					capa.setMaxCount(economicsCommericalBank.getMaxCount());
					capa.setOpen(1);
					capa.setName(economicsCommericalBank.getMinCount() + "<国有商业银行授信数<="
							+ economicsCommericalBank.getMaxCount());
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
	public int insert(EconomicsBank economicsCommericalBank) {

		List<EconomicsBank> list = new ArrayList<>();
		B2bEconomicsDimensionModel model = b2bEconomicsDimensionDao.getByItem("commericalBank");
		EconomicsBank capa = new EconomicsBank();
		capa.setPk(KeyUtils.getUUID());
		capa.setScore(economicsCommericalBank.getScore());
		capa.setMinCount(economicsCommericalBank.getMinCount());
		capa.setMaxCount(economicsCommericalBank.getMaxCount());
		capa.setOpen(1);
		capa.setName(economicsCommericalBank.getMinCount() + "<国有商业银行授信数<=" + economicsCommericalBank.getMaxCount());
		capa.setInsertTime(new Date());
		if (model != null && model.getContent() != null && !Objects.equals(model.getContent(), "")) {
			String content = model.getContent();
			List<EconomicsBank> EconomicsCommericalBankList = JSON.parseArray(content, EconomicsBank.class);
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
