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
import cn.cf.dto.EconomicsLimitDto;
import cn.cf.entity.EconomicsLimit;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionLimitService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionLimitServiceImpl implements EconomicsDimensionLimitService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsLimit> getLimitList() {
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            return JSON.parseArray(limitContent, EconomicsLimit.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsLimit> getOpenLimitList() {
        List<EconomicsLimit> list = new ArrayList<>();
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            List<EconomicsLimit> economicsLimitList = JSON.parseArray(limitContent, EconomicsLimit.class);
            for (EconomicsLimit capa : economicsLimitList) {
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
    public PageModel<EconomicsLimitDto> getLimitData(QueryModel<EconomicsLimitDto> qm) {
        PageModel<EconomicsLimitDto> pm = new PageModel<EconomicsLimitDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);

        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());

        int totalCount = searchLimitCount(map);
        List<EconomicsLimitDto> list = searchLimitGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateLimitStatus(EconomicsLimit economicsLimit) {
        List<EconomicsLimit> list = new ArrayList<>();
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            List<EconomicsLimit> economicsLimitList = JSON.parseArray(limitContent, EconomicsLimit.class);
            for (EconomicsLimit capa : economicsLimitList) {
                if (!Objects.equals(capa.getPk(), economicsLimit.getPk())) {
                    list.add(capa);
                } else {
                    capa.setOpen(economicsLimit.getOpen());
                    list.add(capa);
                }
            }
            limit.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(limit);
        } else
            return 0;
    }

    @Override
    public int updateLimit(EconomicsLimit economicsLimit) {
        List<EconomicsLimit> list = new ArrayList<>();
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            List<EconomicsLimit> economicsLimitList = JSON.parseArray(limitContent, EconomicsLimit.class);
            for (EconomicsLimit lim : economicsLimitList) {
                if (!Objects.equals(lim.getPk(), economicsLimit.getPk())) {
                    list.add(lim);
                } else {
                    lim.setOpen(1);
                    lim.setCategory(economicsLimit.getCategory());
                    lim.setInsertTime(new Date());
                    list.add(lim);
                }
            }
            limit.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(limit);
        } else
            return 0;
    }

    @Override
    public int insertLimit(EconomicsLimit economicsLimit) {
        List<EconomicsLimit> list = new ArrayList<>();
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");

        EconomicsLimit lim = new EconomicsLimit();
        lim.setPk(KeyUtils.getUUID());
        lim.setInsertTime(new Date());
        lim.setOpen(1);
        lim.setCategory(economicsLimit.getCategory());
        lim.setLimit(economicsLimit.getLimit());

        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            List<EconomicsLimit> economicsLimitList = JSON.parseArray(limitContent, EconomicsLimit.class);
            list.addAll(economicsLimitList);
            list.add(lim);
            limit.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(limit);
        } else if (limit != null) {
            list.add(lim);
            limit.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(limit);
        } else
            return 0;
    }

    @Override
    public EconomicsLimit getLimitByPk(String pk) {
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            List<EconomicsLimit> economicsLimitList = JSON.parseArray(limit.getContent(), EconomicsLimit.class);
            for (EconomicsLimit lim : economicsLimitList) {
                if (Objects.equals(lim.getPk(), pk)) {
                    return lim;
                }
            }
        } else {
            return null;
        }
        return null;
    }



    private int searchLimitCount(Map map) {
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        int size = 0;
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            List<EconomicsLimit> list = JSON.parseArray(limitContent, EconomicsLimit.class);
            for (EconomicsLimit economicsLimit : list) {
                boolean checked = true;
                if (map.get("open") != null)
                    if (!economicsLimit.getOpen().equals(map.get("open")))
                        if (!map.get("open").equals("")) {
                            checked = false;
                        }
                if (map.get("insertStartTime") != null)
                    if (!map.get("insertStartTime").equals(""))
                        if ((java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsLimit.getInsertTime()))) {
                            checked = false;
                        }
                if (map.get("insertEndTime") != null)
                    if (!map.get("insertEndTime").equals(""))
                        if ((java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsLimit.getInsertTime()))) {
                            checked = false;
                        }
                if (checked)
                    size++;
            }
            return size;
        } else
            return 0;
    }

    private List<EconomicsLimitDto> searchLimitGridExt(Map map) {
        B2bEconomicsDimensionModel limit = b2bEconomicsDimensionDao.getByItem("limit");
        List<EconomicsLimitDto> list = new ArrayList<>();
        if (limit != null && limit.getContent() != null && !Objects.equals(limit.getContent(), "")) {
            String limitContent = limit.getContent();
            List<EconomicsLimitDto> temp = JSON.parseArray(limitContent, EconomicsLimitDto.class);
            for (EconomicsLimitDto economicsLimitDto : temp) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsLimitDto.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsLimitDto.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsLimitDto.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(economicsLimitDto);
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



