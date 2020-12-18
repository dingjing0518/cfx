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
import cn.cf.dto.EconomicsOnlineDto;
import cn.cf.entity.EconomicsOnline;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionOnlineService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionOnlineServiceImpl implements EconomicsDimensionOnlineService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsOnline> getOnlineList() {
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            return JSON.parseArray(onlineContent, EconomicsOnline.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsOnline> getOpenOnlineList() {
        List<EconomicsOnline> list = new ArrayList<>();
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            List<EconomicsOnline> economicsOnlineList = JSON.parseArray(onlineContent, EconomicsOnline.class);
            for (EconomicsOnline on : economicsOnlineList) {
                if (!Objects.equals(on.getOpen(), 1)) {
                    list.add(on);
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
    public PageModel<EconomicsOnlineDto> getOnlineData(QueryModel<EconomicsOnlineDto> qm) {
        PageModel<EconomicsOnlineDto> pm = new PageModel<EconomicsOnlineDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);
        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        int totalCount = searchOnlineCount(map);
        List<EconomicsOnlineDto> list = searchOnlineGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateOnlineStatus(EconomicsOnline economicsOnline) {
        List<EconomicsOnline> list = new ArrayList<>();
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            List<EconomicsOnline> economicsOnlineList = JSON.parseArray(onlineContent, EconomicsOnline.class);
            for (EconomicsOnline on : economicsOnlineList) {
                if (!Objects.equals(on.getPk(), economicsOnline.getPk())) {
                    list.add(on);
                } else {
                    on.setOpen(economicsOnline.getOpen());
                    list.add(on);
                }
            }
            online.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(online);
        } else
            return 0;
    }

    @Override
    public int updateOnline(EconomicsOnline economicsOnline) {
        List<EconomicsOnline> list = new ArrayList<>();
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            List<EconomicsOnline> economicsOnlineList = JSON.parseArray(onlineContent, EconomicsOnline.class);
            for (EconomicsOnline on : economicsOnlineList) {
                if (!Objects.equals(on.getPk(), economicsOnline.getPk())) {
                    list.add(on);
                } else {
                    on.setScore(economicsOnline.getScore());
                    on.setMonth(economicsOnline.getMonth());
                    on.setMinNumber(economicsOnline.getMinNumber());
                    on.setMaxNumber(economicsOnline.getMaxNumber());
                    on.setOpen(1);
                    on.setName(economicsOnline.getMinNumber() + "w <线上交易数据<=" + economicsOnline.getMaxNumber()+"w");
                    on.setInsertTime(new Date());
                    list.add(on);
                }
            }
            online.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(online);
        } else
            return 0;
    }

    @Override
    public int insertOnline(EconomicsOnline economicsOnline) {
        List<EconomicsOnline> list = new ArrayList<>();
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        EconomicsOnline on = new EconomicsOnline();
        on.setPk(KeyUtils.getUUID());
        on.setInsertTime(new Date());
        on.setOpen(1);
        on.setMonth(economicsOnline.getMonth());
        on.setScore(economicsOnline.getScore());
        on.setMinNumber(economicsOnline.getMinNumber());
        on.setMaxNumber(economicsOnline.getMaxNumber());
        on.setName(economicsOnline.getMinNumber() + "w <线上交易数据<=" + economicsOnline.getMaxNumber()+"w");

        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            List<EconomicsOnline> economicsOnlineList = JSON.parseArray(onlineContent, EconomicsOnline.class);
            list.addAll(economicsOnlineList);
            list.add(on);
            online.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(online);
        } else if (online != null) {
            list.add(on);
            online.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(online);
        } else
            return 0;
    }

    @Override
    public EconomicsOnline getOnlineByPk(String pk) {
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            List<EconomicsOnline> economicsOnlineList = JSON.parseArray(online.getContent(), EconomicsOnline.class);
            for (EconomicsOnline on : economicsOnlineList) {
                if (Objects.equals(on.getPk(), pk)) {
                    return on;
                }
            }
        } else {
            return null;
        }
        return null;
    }


    private int searchOnlineCount(Map map) {
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        int size = 0;
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            List<EconomicsOnline> list = JSON.parseArray(onlineContent, EconomicsOnline.class);
            for (EconomicsOnline economicsOnline : list) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsOnline.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOnline.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOnline.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    size++;
            }
            return size;
        } else
            return 0;
    }

    private List<EconomicsOnlineDto> searchOnlineGridExt(Map map) {
        B2bEconomicsDimensionModel online = b2bEconomicsDimensionDao.getByItem("online");
        List<EconomicsOnlineDto> list = new ArrayList<>();
        if (online != null && online.getContent() != null && !Objects.equals(online.getContent(), "")) {
            String onlineContent = online.getContent();
            List<EconomicsOnlineDto> temp = JSON.parseArray(onlineContent, EconomicsOnlineDto.class);
            for (EconomicsOnlineDto economicsOnline : temp) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsOnline.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOnline.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOnline.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(economicsOnline);
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
