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
import cn.cf.entity.EconomicsOwnership;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionOwnershipService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionOwnershipServiceImpl implements EconomicsDimensionOwnershipService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsOwnership> getOwnershipList() {
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            return JSON.parseArray(ownershipContent, EconomicsOwnership.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsOwnership> getOpenOwnershipList() {
        List<EconomicsOwnership> list = new ArrayList<>();
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            List<EconomicsOwnership> economicsOwnershipList = JSON.parseArray(ownershipContent, EconomicsOwnership.class);
            for (EconomicsOwnership owner : economicsOwnershipList) {
                if (!Objects.equals(owner.getOpen(), 1)) {
                    list.add(owner);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsOwnership> getOwnershipData(QueryModel<EconomicsOwnership> qm) {
        PageModel<EconomicsOwnership> pm = new PageModel<EconomicsOwnership>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);
        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        int totalCount = searchOwnershipCount(map);
        List<EconomicsOwnership> list = searchOwnershipGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateOwnershipStatus(EconomicsOwnership economicsOwnership) {

        List<EconomicsOwnership> list = new ArrayList<>();
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            List<EconomicsOwnership> economicsOwnershipList = JSON.parseArray(ownershipContent, EconomicsOwnership.class);
            for (EconomicsOwnership owner : economicsOwnershipList) {
                if (!Objects.equals(owner.getPk(), economicsOwnership.getPk())) {
                    list.add(owner);
                } else {
                    owner.setOpen(economicsOwnership.getOpen());
                    list.add(owner);
                }
            }
            ownership.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(ownership);
        } else
            return 0;
    }

    @Override
    public int updateOwnership(EconomicsOwnership economicsOwnership) {
        List<EconomicsOwnership> list = new ArrayList<>();
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            List<EconomicsOwnership> economicsOwnershipList = JSON.parseArray(ownershipContent, EconomicsOwnership.class);
            for (EconomicsOwnership owner : economicsOwnershipList) {
                if (!Objects.equals(owner.getPk(), economicsOwnership.getPk())) {
                    list.add(owner);
                } else {
                    owner.setScore(economicsOwnership.getScore());
                    owner.setOwnership(economicsOwnership.getOwnership());
                    owner.setOpen(1);
                    owner.setInsertTime(new Date());
                    list.add(owner);
                }
            }
            ownership.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(ownership);
        } else
            return 0;
    }

    @Override
    public int insertOwnership(EconomicsOwnership economicsOwnership) {
        List<EconomicsOwnership> list = new ArrayList<>();
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");

        EconomicsOwnership owner = new EconomicsOwnership();
        owner.setPk(KeyUtils.getUUID());
        owner.setInsertTime(new Date());
        owner.setOpen(1);
        owner.setScore(economicsOwnership.getScore());
        owner.setOwnership(economicsOwnership.getOwnership());

        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            List<EconomicsOwnership> economicsOwnershipList = JSON.parseArray(ownershipContent, EconomicsOwnership.class);
            list.addAll(economicsOwnershipList);
            list.add(owner);
            ownership.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(ownership);
        } else if (ownership != null) {
            list.add(owner);
            ownership.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(ownership);
        } else
            return 0;
    }

    @Override
    public EconomicsOwnership getOwnershipByPk(String pk) {
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            List<EconomicsOwnership> economicsOwnershipList = JSON.parseArray(ownership.getContent(), EconomicsOwnership.class);
            for (EconomicsOwnership owner : economicsOwnershipList) {
                if (Objects.equals(owner.getPk(), pk)) {
                    return owner;
                }
            }
        } else {
            return null;
        }
        return null;
    }



    private int searchOwnershipCount(Map map) {
    	int size = 0;
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            List<EconomicsOwnership> list = JSON.parseArray(ownershipContent, EconomicsOwnership.class);
            for (EconomicsOwnership economicsOwnership : list) {
            	 boolean checked = true;
                 if (map.get("open") != null && !map.get("open").equals("") && !economicsOwnership.getOpen().equals(map.get("open"))) {
                     checked = false;
                 }
                 if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOwnership.getInsertTime()))) {
                     checked = false;
                 }
                 if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOwnership.getInsertTime()))) {
                     checked = false;
                 }
                 if (checked)
                     size++;
				
			}
            return size;
        } else
            return 0;
    }

    private List<EconomicsOwnership> searchOwnershipGridExt(Map map) {
        B2bEconomicsDimensionModel ownership = b2bEconomicsDimensionDao.getByItem("ownership");
        List<EconomicsOwnership> list = new ArrayList<EconomicsOwnership>();
        if (ownership != null && ownership.getContent() != null && !Objects.equals(ownership.getContent(), "")) {
            String ownershipContent = ownership.getContent();
            List<EconomicsOwnership> temp = JSON.parseArray(ownershipContent, EconomicsOwnership.class);
            for (EconomicsOwnership economicsOwnership : temp) {
           	 boolean checked = true;
             if (map.get("open") != null && !map.get("open").equals("") && !economicsOwnership.getOpen().equals(map.get("open"))) {
                 checked = false;
             }
             if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOwnership.getInsertTime()))) {
                 checked = false;
             }
             if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOwnership.getInsertTime()))) {
                 checked = false;
             }
             if (checked)
                 list.add(economicsOwnership);
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
