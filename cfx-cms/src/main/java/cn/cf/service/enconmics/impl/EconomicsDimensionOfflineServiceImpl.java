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
import cn.cf.entity.EconomicsOfflineInvoice;
import cn.cf.entity.EconomicsOfflineSales;
import cn.cf.entity.EconomicsOfflineScale;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionOfflineService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionOfflineServiceImpl implements EconomicsDimensionOfflineService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsOfflineSales> getOfflineSalesList() {
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            String offlineSalesContent = offlineSales.getContent();
            return JSON.parseArray(offlineSalesContent, EconomicsOfflineSales.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsOfflineSales> getOpenOfflineSalesList() {
        List<EconomicsOfflineSales> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            String onlineContent = offlineSales.getContent();
            List<EconomicsOfflineSales> offlineSalesList = JSON.parseArray(onlineContent, EconomicsOfflineSales.class);
            for (EconomicsOfflineSales off : offlineSalesList) {
                if (!Objects.equals(off.getOpen(), 1)) {
                    list.add(off);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsOfflineSales> getOfflineSalesData(QueryModel<EconomicsOfflineSales> qm) {
        PageModel<EconomicsOfflineSales> pm = new PageModel<EconomicsOfflineSales>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("isDelete", 1);
        int totalCount = searchOfflineSalesCount(map);
        List<EconomicsOfflineSales> list = searchOfflineSalesGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateOfflineSalesStatus(EconomicsOfflineSales economicsOfflineSales) {
        List<EconomicsOfflineSales> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            String offlineSalesContent = offlineSales.getContent();
            List<EconomicsOfflineSales> economicsOfflineSalesList = JSON.parseArray(offlineSalesContent, EconomicsOfflineSales.class);
            for (EconomicsOfflineSales offlineSale : economicsOfflineSalesList) {
                if (!Objects.equals(offlineSale.getPk(), economicsOfflineSales.getPk())) {
                    list.add(offlineSale);
                } else {
                    offlineSale.setOpen(economicsOfflineSales.getOpen());
                    list.add(offlineSale);
                }
            }
            offlineSales.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineSales);
        } else
            return 0;
    }

    @Override
    public int updateOfflineSales(EconomicsOfflineSales economicsOfflineSales) {
        List<EconomicsOfflineSales> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            String onlineContent = offlineSales.getContent();
            List<EconomicsOfflineSales> economicsOfflineSalesList = JSON.parseArray(onlineContent, EconomicsOfflineSales.class);
            for (EconomicsOfflineSales offlineSale : economicsOfflineSalesList) {
                if (!Objects.equals(offlineSale.getPk(), economicsOfflineSales.getPk())) {
                    list.add(offlineSale);
                } else {
                    offlineSale.setScore(economicsOfflineSales.getScore());
                    offlineSale.setMonth(economicsOfflineSales.getMonth());
                    offlineSale.setMinNumber(economicsOfflineSales.getMinNumber());
                    offlineSale.setMaxNumber(economicsOfflineSales.getMaxNumber());
                    offlineSale.setOpen(1);
                    offlineSale.setName(economicsOfflineSales.getMinNumber() + "w <销售总金额<=" + economicsOfflineSales.getMaxNumber()+"w");
                    offlineSale.setInsertTime(new Date());
                    list.add(offlineSale);
                }
            }
            offlineSales.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineSales);
        } else
            return 0;
    }

    @Override
    public int insertOfflineSales(EconomicsOfflineSales economicsOfflineSales) {
        List<EconomicsOfflineSales> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineSale = b2bEconomicsDimensionDao.getByItem("offlineSales");

        EconomicsOfflineSales offlineSales= new EconomicsOfflineSales();
        offlineSales.setPk(KeyUtils.getUUID());
        offlineSales.setInsertTime(new Date());
        offlineSales.setOpen(1);
        offlineSales.setMonth(economicsOfflineSales.getMonth());
        offlineSales.setScore(economicsOfflineSales.getScore());
        offlineSales.setMinNumber(economicsOfflineSales.getMinNumber());
        offlineSales.setMaxNumber(economicsOfflineSales.getMaxNumber());
        offlineSales.setName(economicsOfflineSales.getMinNumber() + "w <销售总金额<=" + economicsOfflineSales.getMaxNumber()+"w");

        if (offlineSale != null && offlineSale.getContent() != null && !Objects.equals(offlineSale.getContent(), "")) {
            String offlineSalesContent = offlineSale.getContent();
            List<EconomicsOfflineSales> economicsOfflineSalesList = JSON.parseArray(offlineSalesContent, EconomicsOfflineSales.class);
            list.addAll(economicsOfflineSalesList);
            list.add(offlineSales);
            offlineSale.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineSale);
        } else if (offlineSale != null) {
            list.add(offlineSales);
            offlineSale.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineSale);
        } else
            return 0;
    }

    @Override
    public EconomicsOfflineSales getOfflineSalesByPk(String pk) {
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            List<EconomicsOfflineSales> economicsOfflineSalesList = JSON.parseArray(offlineSales.getContent(), EconomicsOfflineSales.class);
            for (EconomicsOfflineSales on : economicsOfflineSalesList) {
                if (Objects.equals(on.getPk(), pk)) {
                    return on;
                }
            }
        } else {
            return null;
        }
        return null;
    }




    @Override
    public List<EconomicsOfflineScale> getOfflineScaleList() {
        B2bEconomicsDimensionModel offlineScale = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScale != null && offlineScale.getContent() != null && !Objects.equals(offlineScale.getContent(), "")) {
            String offlineSalesContent = offlineScale.getContent();
            return JSON.parseArray(offlineSalesContent, EconomicsOfflineScale.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsOfflineScale> getOpenOfflineScaleList() {
        List<EconomicsOfflineScale> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineScale = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScale != null && offlineScale.getContent() != null && !Objects.equals(offlineScale.getContent(), "")) {
            String offlineSalesContent = offlineScale.getContent();
            List<EconomicsOfflineScale> economicsOfflineScaleList = JSON.parseArray(offlineSalesContent, EconomicsOfflineScale.class);
            for (EconomicsOfflineScale scale : economicsOfflineScaleList) {
                if (!Objects.equals(scale.getOpen(), 1)) {
                    list.add(scale);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsOfflineScale> getOfflineScaleData(QueryModel<EconomicsOfflineScale> qm) {
        PageModel<EconomicsOfflineScale> pm = new PageModel<EconomicsOfflineScale>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);
        int totalCount = searchOfflineScaleCount(map);
        List<EconomicsOfflineScale> list = searchOfflineScaleGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateOfflineScaleStatus(EconomicsOfflineScale economicsOfflineScale) {
        List<EconomicsOfflineScale> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineScaleModel = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScaleModel != null && offlineScaleModel.getContent() != null && !Objects.equals(offlineScaleModel.getContent(), "")) {
            String offlineScaleContent = offlineScaleModel.getContent();
            List<EconomicsOfflineScale> economicsOfflineScaleList = JSON.parseArray(offlineScaleContent, EconomicsOfflineScale.class);
            for (EconomicsOfflineScale offlineScale : economicsOfflineScaleList) {
                if (!Objects.equals(offlineScale.getPk(), economicsOfflineScale.getPk())) {
                    list.add(offlineScale);
                } else {
                    offlineScale.setOpen(offlineScale.getOpen());
                    list.add(offlineScale);
                }
            }
            offlineScaleModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineScaleModel);
        } else
            return 0;
    }

    @Override
    public int updateOfflineScale(EconomicsOfflineScale economicsOfflineScale) {
        List<EconomicsOfflineScale> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineScaleModel = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScaleModel != null && offlineScaleModel.getContent() != null && !Objects.equals(offlineScaleModel.getContent(), "")) {
            String offlineScaleContent = offlineScaleModel.getContent();
            List<EconomicsOfflineScale> economicsOfflineScaleList = JSON.parseArray(offlineScaleContent, EconomicsOfflineScale.class);
            for (EconomicsOfflineScale scale : economicsOfflineScaleList) {
                if (!Objects.equals(scale.getPk(), economicsOfflineScale.getPk())) {
                    list.add(scale);
                } else {
                    scale.setScore(economicsOfflineScale.getScore());
                    scale.setMonth(economicsOfflineScale.getMonth());
                    scale.setMinNumber(economicsOfflineScale.getMinNumber());
                    scale.setMaxNumber(economicsOfflineScale.getMaxNumber());
                    scale.setOpen(1);
                    scale.setName(economicsOfflineScale.getMinNumber() + "<销售规模<=" + economicsOfflineScale.getMaxNumber());
                    scale.setInsertTime(new Date());
                    list.add(scale);
                }
            }
            offlineScaleModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineScaleModel);
        } else
            return 0;
    }

    @Override
    public int insertOfflineScale(EconomicsOfflineScale economicsOfflineScale) {
        List<EconomicsOfflineScale> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineScale = b2bEconomicsDimensionDao.getByItem("offlineScale");

        EconomicsOfflineScale scale = new EconomicsOfflineScale();
        scale.setPk(KeyUtils.getUUID());
        scale.setInsertTime(new Date());
        scale.setOpen(1);
        scale.setMonth(economicsOfflineScale.getMonth());
        scale.setScore(economicsOfflineScale.getScore());
        scale.setMinNumber(economicsOfflineScale.getMinNumber());
        scale.setMaxNumber(economicsOfflineScale.getMaxNumber());
        scale.setName(economicsOfflineScale.getMinNumber() + "<销售规模<=" + economicsOfflineScale.getMaxNumber());

        if (offlineScale != null && offlineScale.getContent() != null && !Objects.equals(offlineScale.getContent(), "")) {
            String offlineScaleContent = offlineScale.getContent();
            List<EconomicsOfflineScale> economicsOfflineScaleList = JSON.parseArray(offlineScaleContent, EconomicsOfflineScale.class);
            list.addAll(economicsOfflineScaleList);
            list.add(scale);
            offlineScale.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineScale);
        } else if (offlineScale != null) {
            list.add(scale);
            offlineScale.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineScale);
        } else
            return 0;
    }

    @Override
    public EconomicsOfflineScale getOfflineScaleByPk(String pk) {
        B2bEconomicsDimensionModel offlineScaleModel = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScaleModel != null && offlineScaleModel.getContent() != null && !Objects.equals(offlineScaleModel.getContent(), "")) {
            List<EconomicsOfflineScale> economicsOfflineScaleList = JSON.parseArray(offlineScaleModel.getContent(), EconomicsOfflineScale.class);
            for (EconomicsOfflineScale offlineScale : economicsOfflineScaleList) {
                if (Objects.equals(offlineScale.getPk(), pk)) {
                    return offlineScale;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public List<EconomicsOfflineInvoice> getOfflineInvoiceList() {
        B2bEconomicsDimensionModel offlineInvoice = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        if (offlineInvoice != null && offlineInvoice.getContent() != null && !Objects.equals(offlineInvoice.getContent(), "")) {
            String onlineContent = offlineInvoice.getContent();
            return JSON.parseArray(onlineContent, EconomicsOfflineInvoice.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsOfflineInvoice> getOpenOfflineInvoiceList() {
        List<EconomicsOfflineInvoice> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineInvoiceModel = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        if (offlineInvoiceModel != null && offlineInvoiceModel.getContent() != null && !Objects.equals(offlineInvoiceModel.getContent(), "")) {
            String offlineInvoiceContent = offlineInvoiceModel.getContent();
            List<EconomicsOfflineInvoice> economicsOfflineInvoiceList = JSON.parseArray(offlineInvoiceContent, EconomicsOfflineInvoice.class);
            for (EconomicsOfflineInvoice on : economicsOfflineInvoiceList) {
                if (!Objects.equals(on.getOpen(), 1)) {
                    list.add(on);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsOfflineInvoice> getOfflineInvoiceData(QueryModel<EconomicsOfflineInvoice> qm) {
        PageModel<EconomicsOfflineInvoice> pm = new PageModel<EconomicsOfflineInvoice>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);
        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        int totalCount = searchOfflineInvoiceCount(map);
        List<EconomicsOfflineInvoice> list = searchOfflineInvoiceGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateOfflineInvoiceStatus(EconomicsOfflineInvoice economicsOfflineInvoice) {
        List<EconomicsOfflineInvoice> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineInvoiceModel = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        if (offlineInvoiceModel != null && offlineInvoiceModel.getContent() != null && !Objects.equals(offlineInvoiceModel.getContent(), "")) {
            String onlineContent = offlineInvoiceModel.getContent();
            List<EconomicsOfflineInvoice> economicsOfflineInvoiceList = JSON.parseArray(onlineContent, EconomicsOfflineInvoice.class);
            for (EconomicsOfflineInvoice on : economicsOfflineInvoiceList) {
                if (!Objects.equals(on.getPk(), economicsOfflineInvoice.getPk())) {
                    list.add(on);
                } else {
                    on.setOpen(economicsOfflineInvoice.getOpen());
                    list.add(on);
                }
            }
            offlineInvoiceModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineInvoiceModel);
        } else
            return 0;
    }

    @Override
    public int updateOfflineInvoice(EconomicsOfflineInvoice economicsOfflineInvoice) {
        List<EconomicsOfflineInvoice> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineInvoiceModel = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        if (offlineInvoiceModel != null && offlineInvoiceModel.getContent() != null && !Objects.equals(offlineInvoiceModel.getContent(), "")) {
            String offlineInvoiceContent = offlineInvoiceModel.getContent();
            List<EconomicsOfflineInvoice> economicsOfflineInvoiceList = JSON.parseArray(offlineInvoiceContent, EconomicsOfflineInvoice.class);
            for (EconomicsOfflineInvoice offlineInvoice : economicsOfflineInvoiceList) {
                if (!Objects.equals(offlineInvoice.getPk(), economicsOfflineInvoice.getPk())) {
                    list.add(offlineInvoice);
                } else {
                    offlineInvoice.setScore(economicsOfflineInvoice.getScore());
                    offlineInvoice.setMinNumber(economicsOfflineInvoice.getMinNumber());
                    offlineInvoice.setMaxNumber(economicsOfflineInvoice.getMaxNumber());
                    offlineInvoice.setOpen(1);
                    offlineInvoice.setName(economicsOfflineInvoice.getMinNumber() + "w <开票总金额<=" + economicsOfflineInvoice.getMaxNumber()+"w");
                    offlineInvoice.setInsertTime(new Date());
                    list.add(offlineInvoice);
                }
            }
            offlineInvoiceModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineInvoiceModel);
        } else
            return 0;
    }

    @Override
    public int insertOfflineInvoice(EconomicsOfflineInvoice economicsOfflineInvoice) {
        List<EconomicsOfflineInvoice> list = new ArrayList<>();
        B2bEconomicsDimensionModel offlineInvoiceModel = b2bEconomicsDimensionDao.getByItem("offlineInvoice");

        EconomicsOfflineInvoice offlineInvoice = new EconomicsOfflineInvoice();
        offlineInvoice.setPk(KeyUtils.getUUID());
        offlineInvoice.setInsertTime(new Date());
        offlineInvoice.setOpen(1);
        offlineInvoice.setScore(economicsOfflineInvoice.getScore());
        offlineInvoice.setMinNumber(economicsOfflineInvoice.getMinNumber());
        offlineInvoice.setMaxNumber(economicsOfflineInvoice.getMaxNumber());
        offlineInvoice.setName(economicsOfflineInvoice.getMinNumber() + "w <开票总金额<=" + economicsOfflineInvoice.getMaxNumber()+"w");

        if (offlineInvoiceModel != null && offlineInvoiceModel.getContent() != null && !Objects.equals(offlineInvoiceModel.getContent(), "")) {
            String offlineInvoiceContent = offlineInvoiceModel.getContent();
            List<EconomicsOfflineInvoice> economicsOfflineInvoiceList = JSON.parseArray(offlineInvoiceContent, EconomicsOfflineInvoice.class);
            list.addAll(economicsOfflineInvoiceList);
            list.add(offlineInvoice);
            offlineInvoiceModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineInvoiceModel);
        } else if (offlineInvoiceModel != null) {
            list.add(offlineInvoice);
            offlineInvoiceModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(offlineInvoiceModel);
        } else
            return 0;
    }

    @Override
    public EconomicsOfflineInvoice getOfflineInvoiceByPk(String pk) {
        B2bEconomicsDimensionModel offlineInvoiceModel = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        if (offlineInvoiceModel != null && offlineInvoiceModel.getContent() != null && !Objects.equals(offlineInvoiceModel.getContent(), "")) {
            List<EconomicsOfflineInvoice> economicsOfflineInvoiceList = JSON.parseArray(offlineInvoiceModel.getContent(), EconomicsOfflineInvoice.class);
            for (EconomicsOfflineInvoice offlineInvoice : economicsOfflineInvoiceList) {
                if (Objects.equals(offlineInvoice.getPk(), pk)) {
                    return offlineInvoice;
                }
            }
        } else {
            return null;
        }
        return null;
    }





    private int searchOfflineSalesCount(Map map) {
    	int size = 0;
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            String offlineSalesContent = offlineSales.getContent();
            List<EconomicsOfflineSales> list = JSON.parseArray(offlineSalesContent, EconomicsOfflineSales.class);
          for (EconomicsOfflineSales economicsOfflineSales : list) {
              boolean checked = true;
              if (map.get("open") != null && !map.get("open").equals("") && !economicsOfflineSales.getOpen().equals(map.get("open"))) {
                  checked = false;
              }
              if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOfflineSales.getInsertTime()))) {
                  checked = false;
              }
              if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOfflineSales.getInsertTime()))) {
                  checked = false;
              }
              if (checked)
            	  size++;
          }  
            return size;
        } else
            return 0;
    }

    private List<EconomicsOfflineSales> searchOfflineSalesGridExt(Map map) {
        B2bEconomicsDimensionModel offlineSales = b2bEconomicsDimensionDao.getByItem("offlineSales");
        List<EconomicsOfflineSales> list = new ArrayList<>();
        if (offlineSales != null && offlineSales.getContent() != null && !Objects.equals(offlineSales.getContent(), "")) {
            String offlineSalesContent = offlineSales.getContent();
            List<EconomicsOfflineSales> temp =  JSON.parseArray(offlineSalesContent, EconomicsOfflineSales.class);
         for (EconomicsOfflineSales economicsOfflineSales : temp) {
             boolean checked = true;
             if (map.get("open") != null && !map.get("open").equals("") && !economicsOfflineSales.getOpen().equals(map.get("open"))) {
                 checked = false;
             }
             if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOfflineSales.getInsertTime()))) {
                 checked = false;
             }
             if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOfflineSales.getInsertTime()))) {
                 checked = false;
             }
             if (checked)
                 list.add(economicsOfflineSales);
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

    private int searchOfflineScaleCount(Map map) {
        B2bEconomicsDimensionModel offlineScale = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScale != null && offlineScale.getContent() != null && !Objects.equals(offlineScale.getContent(), "")) {
            String offlineScaleContent = offlineScale.getContent();
            List<EconomicsOfflineScale> list = JSON.parseArray(offlineScaleContent, EconomicsOfflineScale.class);
            return list.size();
        } else
            return 0;
    }

    private List<EconomicsOfflineScale> searchOfflineScaleGridExt(Map map) {
        B2bEconomicsDimensionModel offlineScale = b2bEconomicsDimensionDao.getByItem("offlineScale");
        if (offlineScale != null && offlineScale.getContent() != null && !Objects.equals(offlineScale.getContent(), "")) {
            String offlineScaleContent = offlineScale.getContent();
            return JSON.parseArray(offlineScaleContent, EconomicsOfflineScale.class);

        } else
            return null;
    }

    private int searchOfflineInvoiceCount(Map map) {
        B2bEconomicsDimensionModel offlineInvoice = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        int size = 0;
        if (offlineInvoice != null && offlineInvoice.getContent() != null && !Objects.equals(offlineInvoice.getContent(), "")) {
            String offlineInvoiceContent = offlineInvoice.getContent();
            List<EconomicsOfflineInvoice> list = JSON.parseArray(offlineInvoiceContent, EconomicsOfflineInvoice.class);
           for (EconomicsOfflineInvoice economicsOfflineInvoice : list) {
               boolean checked = true;
               if (map.get("open") != null && !map.get("open").equals("") && !economicsOfflineInvoice.getOpen().equals(map.get("open"))) {
                   checked = false;
               }
               if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOfflineInvoice.getInsertTime()))) {
                   checked = false;
               }
               if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOfflineInvoice.getInsertTime()))) {
                   checked = false;
               }
               if (checked)
                   size++;
           }
            return size;
        } else
            return 0;
    }

    private List<EconomicsOfflineInvoice> searchOfflineInvoiceGridExt(Map map) {
        B2bEconomicsDimensionModel offlineInvoice = b2bEconomicsDimensionDao.getByItem("offlineInvoice");
        List<EconomicsOfflineInvoice> list = new ArrayList<>();
        if (offlineInvoice != null && offlineInvoice.getContent() != null && !Objects.equals(offlineInvoice.getContent(), "")) {
            String offlineInvoiceContent = offlineInvoice.getContent();
            List<EconomicsOfflineInvoice> temp = JSON.parseArray(offlineInvoiceContent, EconomicsOfflineInvoice.class);
         for (EconomicsOfflineInvoice economicsOfflineInvoice : temp) {
             boolean checked = true;
             if (map.get("open") != null && !map.get("open").equals("") && !economicsOfflineInvoice.getOpen().equals(map.get("open"))) {
                 checked = false;
             }
             if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsOfflineInvoice.getInsertTime()))) {
                 checked = false;
             }
             if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsOfflineInvoice.getInsertTime()))) {
                 checked = false;
             }
             if (checked)
                 list.add(economicsOfflineInvoice);
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
