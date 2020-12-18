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
import cn.cf.entity.EconomicsEquipment;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionEquipmentService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionEquipmentServiceImpl implements EconomicsDimensionEquipmentService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsEquipment> getEquipmentList() {
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            return JSON.parseArray(equipmentContent, EconomicsEquipment.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsEquipment> getOpenEquipmentList() {
        List<EconomicsEquipment> list = new ArrayList<>();
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            List<EconomicsEquipment> economicsEquipmentList = JSON.parseArray(equipmentContent, EconomicsEquipment.class);
            for (EconomicsEquipment equip : economicsEquipmentList) {
                if (!Objects.equals(equip.getOpen(), 1)) {
                    list.add(equip);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsEquipment> getEquipmentData(QueryModel<EconomicsEquipment> qm) {
        PageModel<EconomicsEquipment> pm = new PageModel<EconomicsEquipment>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("open", qm.getEntity().getOpen());
        map.put("isDelete", 1);
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        int totalCount = searchEquipmentCount(map);
        List<EconomicsEquipment> list = searchEquipmentGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateEquipmentStatus(EconomicsEquipment economicsEquipment) {
        List<EconomicsEquipment> list = new ArrayList<>();
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            List<EconomicsEquipment> economicsEquipmentList = JSON.parseArray(equipmentContent, EconomicsEquipment.class);
            for (EconomicsEquipment equip : economicsEquipmentList) {
                if (!Objects.equals(equip.getPk(), economicsEquipment.getPk())) {
                    list.add(equip);
                } else {
                    equip.setOpen(economicsEquipment.getOpen());
                    list.add(equip);
                }
            }
            equipment.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(equipment);
        } else
            return 0;
    }

    @Override
    public int updateEquipment(EconomicsEquipment economicsEquipment) {
        List<EconomicsEquipment> list = new ArrayList<>();
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            List<EconomicsEquipment> economicsEquipmentList = JSON.parseArray(equipmentContent, EconomicsEquipment.class);
            for (EconomicsEquipment equip : economicsEquipmentList) {
                if (!Objects.equals(equip.getPk(), economicsEquipment.getPk())) {
                    list.add(equip);
                } else {
                    equip.setScore(economicsEquipment.getScore());
                    equip.setMinNumber(economicsEquipment.getMinNumber());
                    equip.setMaxNumber(economicsEquipment.getMaxNumber());
                    equip.setOpen(1);
                    equip.setCategory(economicsEquipment.getCategory());
                    equip.setMaxAges(economicsEquipment.getMaxAges());
                    equip.setMinAges(economicsEquipment.getMinAges());
                    equip.setAges(economicsEquipment.getMinAges() + "<年限<=" + economicsEquipment.getMaxAges());
                    equip.setNumber(economicsEquipment.getMinNumber() + "<设备台数<=" + economicsEquipment.getMaxNumber());
                    equip.setOwnership(economicsEquipment.getOwnership());
                    equip.setInsertTime(new Date());
                    list.add(equip);
                }
            }
            equipment.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(equipment);
        } else
            return 0;
    }

    @Override
    public int insertEquipment(EconomicsEquipment economicsEquipment) {
        List<EconomicsEquipment> list = new ArrayList<>();
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");

        EconomicsEquipment equip = new EconomicsEquipment();
        equip.setPk(KeyUtils.getUUID());
        equip.setScore(economicsEquipment.getScore());
        equip.setMinNumber(economicsEquipment.getMinNumber());
        equip.setMaxNumber(economicsEquipment.getMaxNumber());
        equip.setOpen(1);
        equip.setCategory(economicsEquipment.getCategory());
        equip.setMaxAges(economicsEquipment.getMaxAges());
        equip.setMinAges(economicsEquipment.getMinAges());
        equip.setOwnership(economicsEquipment.getOwnership());
        equip.setInsertTime(new Date());
        equip.setAges(economicsEquipment.getMinAges() + "<年限<=" + economicsEquipment.getMaxAges());
        equip.setNumber(economicsEquipment.getMinNumber() + "<设备台数<=" + economicsEquipment.getMaxNumber());

        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            List<EconomicsEquipment> economicsEquipmentList = JSON.parseArray(equipmentContent, EconomicsEquipment.class);
            list.addAll(economicsEquipmentList);
            list.add(equip);
            equipment.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(equipment);
        } else if (equipment != null) {
            list.add(equip);
            equipment.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(equipment);
        } else
            return 0;
    }

    @Override
    public EconomicsEquipment getEquipmentByPk(String pk) {
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            List<EconomicsEquipment> economicsEquipmentList = JSON.parseArray(equipment.getContent(), EconomicsEquipment.class);
            for (EconomicsEquipment equip : economicsEquipmentList) {
                if (Objects.equals(equip.getPk(), pk)) {
                    return equip;
                }
            }
        } else {
            return null;
        }
        return null;
    }


    private int searchEquipmentCount(Map map) {
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        int size = 0;
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            List<EconomicsEquipment> list = JSON.parseArray(equipmentContent, EconomicsEquipment.class);
            for (EconomicsEquipment economicsEquipment : list) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsEquipment.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsEquipment.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsEquipment.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    size++;
            }
            return size;
        } else
            return 0;
    }

    private List<EconomicsEquipment> searchEquipmentGridExt(Map map) {
        B2bEconomicsDimensionModel equipment = b2bEconomicsDimensionDao.getByItem("equipment");
        List<EconomicsEquipment> list = new ArrayList<>();
        if (equipment != null && equipment.getContent() != null && !Objects.equals(equipment.getContent(), "")) {
            String equipmentContent = equipment.getContent();
            List<EconomicsEquipment> temp = JSON.parseArray(equipmentContent, EconomicsEquipment.class);
            for (EconomicsEquipment economicsEquipment : temp) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsEquipment.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsEquipment.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsEquipment.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(economicsEquipment);
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
