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
import cn.cf.entity.EconomicsCapacity;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionCapacityService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionCapacityServiceImpl implements EconomicsDimensionCapacityService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsCapacity> getCapacityList() {
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            return JSON.parseArray(capacityContent, EconomicsCapacity.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsCapacity> getOpenCapacityList() {
        List<EconomicsCapacity> list = new ArrayList<>();
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            List<EconomicsCapacity> economicsCapacityList = JSON.parseArray(capacityContent, EconomicsCapacity.class);
            for (EconomicsCapacity capa : economicsCapacityList) {
                if (!Objects.equals(capa.getOpen(), 1)) {
                    list.add(capa);
                }
            }
        }
        return list;
    }

    @Override
    public int insert(B2bEconomicsDimensionModel b2bEconomicsDimensionModel) {
        return b2bEconomicsDimensionDao.insert(b2bEconomicsDimensionModel);
    }

    @Override
    public int updateCapacityStatus(EconomicsCapacity economicsCapacity) {

        List<EconomicsCapacity> list = new ArrayList<>();
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            List<EconomicsCapacity> economicsCapacityList = JSON.parseArray(capacityContent, EconomicsCapacity.class);
            for (EconomicsCapacity capa : economicsCapacityList) {
                if (!Objects.equals(capa.getPk(), economicsCapacity.getPk())) {
                    list.add(capa);
                } else {
                    capa.setOpen(economicsCapacity.getOpen());
                    list.add(capa);
                }
            }
            capacity.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(capacity);
        } else
            return 0;
    }

    @Override
    public int updateCapacity(EconomicsCapacity economicsCapacity) {

        List<EconomicsCapacity> list = new ArrayList<>();
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            List<EconomicsCapacity> economicsCapacityList = JSON.parseArray(capacityContent, EconomicsCapacity.class);
            for (EconomicsCapacity capa : economicsCapacityList) {
                if (!Objects.equals(capa.getPk(), economicsCapacity.getPk())) {
                    list.add(capa);
                } else {
                    capa.setScore(economicsCapacity.getScore());
                    capa.setMinCapacity(economicsCapacity.getMinCapacity());
                    capa.setMaxCapacity(economicsCapacity.getMaxCapacity());
                    capa.setOpen(1);
                    capa.setName(economicsCapacity.getMinCapacity() + "<产能范围<=" + economicsCapacity.getMaxCapacity());
                    capa.setInsertTime(new Date());
                    list.add(capa);
                }
            }
            capacity.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(capacity);
        } else
            return 0;
    }

    @Override
    public int insertCapacity(EconomicsCapacity economicsCapacity) {

        List<EconomicsCapacity> list = new ArrayList<>();
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");

        EconomicsCapacity capa = new EconomicsCapacity();
        capa.setPk(KeyUtils.getUUID());
        capa.setInsertTime(new Date());
        capa.setOpen(1);
        capa.setScore(economicsCapacity.getScore());
        capa.setMinCapacity(economicsCapacity.getMinCapacity());
        capa.setMaxCapacity(economicsCapacity.getMaxCapacity());
        capa.setName(economicsCapacity.getMinCapacity() + "<产能范围<=" + economicsCapacity.getMaxCapacity());

        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            List<EconomicsCapacity> economicsCapacityList = JSON.parseArray(capacityContent, EconomicsCapacity.class);
            list.addAll(economicsCapacityList);
            list.add(capa);
            capacity.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(capacity);
        } else if (capacity != null) {
            list.add(capa);
            capacity.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(capacity);
        } else
            return 0;
    }

    @Override
    public EconomicsCapacity getCapacityByPk(String pk) {

        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            List<EconomicsCapacity> economicsCapacityList = JSON.parseArray(capacity.getContent(), EconomicsCapacity.class);
            for (EconomicsCapacity capa : economicsCapacityList) {
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
    public PageModel<EconomicsCapacity> getCapacityData(QueryModel<EconomicsCapacity> qm) {

        PageModel<EconomicsCapacity> pm = new PageModel<EconomicsCapacity>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);
        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        int totalCount = searchCapacityCount(map);
        List<EconomicsCapacity> list = searchCapacityGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }


    private int searchCapacityCount(Map map) {
    	int size = 0;
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            List<EconomicsCapacity> list = JSON.parseArray(capacityContent, EconomicsCapacity.class);
            for (EconomicsCapacity economicsCapacity : list) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsCapacity.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsCapacity.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsCapacity.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    size++;
            }
            return size;
        } else
            return 0;
    }

    private List<EconomicsCapacity> searchCapacityGridExt(Map map) {
        B2bEconomicsDimensionModel capacity = b2bEconomicsDimensionDao.getByItem("capacity");
        List<EconomicsCapacity> list = new ArrayList<>();
        if (capacity != null && capacity.getContent() != null && !Objects.equals(capacity.getContent(), "")) {
            String capacityContent = capacity.getContent();
            List<EconomicsCapacity> temp = JSON.parseArray(capacityContent, EconomicsCapacity.class);
            for (EconomicsCapacity economicsCapacity : temp) {
            	 boolean checked = true;
                 if (map.get("open") != null && !map.get("open").equals("") && !economicsCapacity.getOpen().equals(map.get("open"))) {
                     checked = false;
                 }
                 if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsCapacity.getInsertTime()))) {
                     checked = false;
                 }
                 if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsCapacity.getInsertTime()))) {
                     checked = false;
                 }
                 if (checked)
                     list.add(economicsCapacity);
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
}
