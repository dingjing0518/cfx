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
import cn.cf.dto.EconomicsGuaranteeCountDto;
import cn.cf.dto.EconomicsGuaranteeTargetDto;
import cn.cf.dto.EconomicsGuaranteeTypeDto;
import cn.cf.entity.EconomicsGuaranteeCount;
import cn.cf.entity.EconomicsGuaranteeTarget;
import cn.cf.entity.EconomicsGuaranteeType;
import cn.cf.model.B2bEconomicsDimensionModel;
import cn.cf.service.enconmics.EconomicsDimensionGuaranteeService;
import cn.cf.util.KeyUtils;

@Service
public class EconomicsDimensionGuaranteeServiceImpl implements EconomicsDimensionGuaranteeService {

    @Autowired
    private B2bEconomicsDimensionExtDao b2bEconomicsDimensionDao;

    @Override
    public List<EconomicsGuaranteeCount> getGuaranteeCountList() {
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String guaranteeCountModelContent = guaranteeCountModel.getContent();
            return JSON.parseArray(guaranteeCountModelContent, EconomicsGuaranteeCount.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsGuaranteeCount> getOpenGuaranteeCountList() {
        List<EconomicsGuaranteeCount> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String guaranteeCountModelContent = guaranteeCountModel.getContent();
            List<EconomicsGuaranteeCount> guaranteeCountList = JSON.parseArray(guaranteeCountModelContent, EconomicsGuaranteeCount.class);
            for (EconomicsGuaranteeCount guarantee : guaranteeCountList) {
                if (!Objects.equals(guarantee.getOpen(), 1)) {
                    list.add(guarantee);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsGuaranteeCountDto> getGuaranteeCountData(QueryModel<EconomicsGuaranteeCountDto> qm) {
        PageModel<EconomicsGuaranteeCountDto> pm = new PageModel<EconomicsGuaranteeCountDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);

        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());


        int totalCount = searchGuaranteeCountCount(map);
        List<EconomicsGuaranteeCountDto> list = searchGuaranteeCountGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateGuaranteeCountStatus(EconomicsGuaranteeCount economicsGuaranteeCount) {
        List<EconomicsGuaranteeCount> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String guaranteeCountContent = guaranteeCountModel.getContent();
            List<EconomicsGuaranteeCount> economicsGuaranteeCountList = JSON.parseArray(guaranteeCountContent, EconomicsGuaranteeCount.class);
            for (EconomicsGuaranteeCount guaranteeCount : economicsGuaranteeCountList) {
                if (!Objects.equals(guaranteeCount.getPk(), economicsGuaranteeCount.getPk())) {
                    list.add(guaranteeCount);
                } else {
                    guaranteeCount.setOpen(economicsGuaranteeCount.getOpen());
                    list.add(guaranteeCount);
                }
            }
            guaranteeCountModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeCountModel);
        } else
            return 0;
    }

    @Override
    public int updateGuaranteeCount(EconomicsGuaranteeCount economicsGuaranteeCount) {
        List<EconomicsGuaranteeCount> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String onlineContent = guaranteeCountModel.getContent();
            List<EconomicsGuaranteeCount> economicsGuaranteeCountList = JSON.parseArray(onlineContent, EconomicsGuaranteeCount.class);
            for (EconomicsGuaranteeCount guaranteeCount : economicsGuaranteeCountList) {
                if (!Objects.equals(guaranteeCount.getPk(), economicsGuaranteeCount.getPk())) {
                    list.add(guaranteeCount);
                } else {
                    guaranteeCount.setScore(economicsGuaranteeCount.getScore());
                    guaranteeCount.setMinNumber(economicsGuaranteeCount.getMinNumber());
                    guaranteeCount.setMaxNumber(economicsGuaranteeCount.getMaxNumber());
                    guaranteeCount.setOpen(1);
                    guaranteeCount.setName(economicsGuaranteeCount.getMinNumber() + "<担保户数<=" + economicsGuaranteeCount.getMaxNumber());
                    guaranteeCount.setInsertTime(new Date());
                    list.add(guaranteeCount);
                }
            }
            guaranteeCountModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeCountModel);
        } else
            return 0;
    }

    @Override
    public int insertGuaranteeCount(EconomicsGuaranteeCount economicsGuaranteeCount) {
        List<EconomicsGuaranteeCount> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");

        EconomicsGuaranteeCount guaranteeCount = new EconomicsGuaranteeCount();
        guaranteeCount.setPk(KeyUtils.getUUID());
        guaranteeCount.setInsertTime(new Date());
        guaranteeCount.setOpen(1);
        guaranteeCount.setScore(economicsGuaranteeCount.getScore());
        guaranteeCount.setMinNumber(economicsGuaranteeCount.getMinNumber());
        guaranteeCount.setMaxNumber(economicsGuaranteeCount.getMaxNumber());
        guaranteeCount.setName(economicsGuaranteeCount.getMinNumber() + "<担保户数<=" + economicsGuaranteeCount.getMaxNumber());

        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String guaranteeCountContent = guaranteeCountModel.getContent();
            List<EconomicsGuaranteeCount> economicsGuaranteeCountList = JSON.parseArray(guaranteeCountContent, EconomicsGuaranteeCount.class);
            list.addAll(economicsGuaranteeCountList);
            list.add(guaranteeCount);
            guaranteeCountModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeCountModel);
        } else if (guaranteeCountModel != null) {
            list.add(guaranteeCount);
            guaranteeCountModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeCountModel);
        } else
            return 0;
    }

    @Override
    public EconomicsGuaranteeCount getGuaranteeCountByPk(String pk) {
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            List<EconomicsGuaranteeCount> guaranteeCountList = JSON.parseArray(guaranteeCountModel.getContent(), EconomicsGuaranteeCount.class);
            for (EconomicsGuaranteeCount guaranteeCount : guaranteeCountList) {
                if (Objects.equals(guaranteeCount.getPk(), pk)) {
                    return guaranteeCount;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public List<EconomicsGuaranteeTarget> getGuaranteeTargetList() {
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String guaranteeTypeModelContent = guaranteeTargetModel.getContent();
            return JSON.parseArray(guaranteeTypeModelContent, EconomicsGuaranteeTarget.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsGuaranteeTarget> getOpenGuaranteeTargetList() {
        List<EconomicsGuaranteeTarget> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String guaranteeTypeModelContent = guaranteeTargetModel.getContent();
            List<EconomicsGuaranteeTarget> guaranteeTargetList = JSON.parseArray(guaranteeTypeModelContent, EconomicsGuaranteeTarget.class);
            for (EconomicsGuaranteeTarget guarantee : guaranteeTargetList) {
                if (!Objects.equals(guarantee.getOpen(), 1)) {
                    list.add(guarantee);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsGuaranteeTargetDto> getGuaranteeTargetData(QueryModel<EconomicsGuaranteeTargetDto> qm) {
        PageModel<EconomicsGuaranteeTargetDto> pm = new PageModel<EconomicsGuaranteeTargetDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);

        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());

        int totalCount = searchGuaranteeTargetCount(map);
        List<EconomicsGuaranteeTargetDto> list = searchGuaranteeTargetGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateGuaranteeTargetStatus(EconomicsGuaranteeTarget economicsGuaranteeTarget) {
        List<EconomicsGuaranteeTarget> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String guaranteeTargetContent = guaranteeTargetModel.getContent();
            List<EconomicsGuaranteeTarget> economicsGuaranteeTargetList = JSON.parseArray(guaranteeTargetContent, EconomicsGuaranteeTarget.class);
            for (EconomicsGuaranteeTarget guaranteeTarget : economicsGuaranteeTargetList) {
                if (!Objects.equals(guaranteeTarget.getPk(), economicsGuaranteeTarget.getPk())) {
                    list.add(guaranteeTarget);
                } else {
                    guaranteeTarget.setOpen(economicsGuaranteeTarget.getOpen());
                    list.add(guaranteeTarget);
                }
            }
            guaranteeTargetModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTargetModel);
        } else
            return 0;
    }

    @Override
    public int updateGuaranteeTarget(EconomicsGuaranteeTarget economicsGuaranteeTarget) {
        List<EconomicsGuaranteeTarget> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String onlineContent = guaranteeTargetModel.getContent();
            List<EconomicsGuaranteeTarget> economicsGuaranteeTargetList = JSON.parseArray(onlineContent, EconomicsGuaranteeTarget.class);
            for (EconomicsGuaranteeTarget guaranteeTarget : economicsGuaranteeTargetList) {
                if (!Objects.equals(guaranteeTarget.getPk(), economicsGuaranteeTarget.getPk())) {
                    list.add(guaranteeTarget);
                } else {
                    guaranteeTarget.setScore(economicsGuaranteeTarget.getScore());
                    guaranteeTarget.setTarget(economicsGuaranteeTarget.getTarget());
                    guaranteeTarget.setMinAccount(economicsGuaranteeTarget.getMinAccount());
                    guaranteeTarget.setMaxAccount(economicsGuaranteeTarget.getMaxAccount());
                    guaranteeTarget.setMinRate(economicsGuaranteeTarget.getMinRate());
                    guaranteeTarget.setMaxRate(economicsGuaranteeTarget.getMaxRate());
                    guaranteeTarget.setOpen(1);
                    guaranteeTarget.setYear(economicsGuaranteeTarget.getYear());
                    guaranteeTarget.setAccountName(economicsGuaranteeTarget.getMinAccount() + "w <担保金额<=" + economicsGuaranteeTarget.getMaxAccount()+"w");
                    guaranteeTarget.setRateName(economicsGuaranteeTarget.getMinRate() + "<担保总金额/纳税销售收入比<=" + economicsGuaranteeTarget.getMaxRate());
                    guaranteeTarget.setInsertTime(new Date());
                    list.add(guaranteeTarget);
                }
            }
            guaranteeTargetModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTargetModel);
        } else
            return 0;
    }

    @Override
    public int insertGuaranteeTarget(EconomicsGuaranteeTarget economicsGuaranteeTarget) {
        List<EconomicsGuaranteeTarget> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");

        EconomicsGuaranteeTarget guaranteeTarget = new EconomicsGuaranteeTarget();
        guaranteeTarget.setPk(KeyUtils.getUUID());
        guaranteeTarget.setScore(economicsGuaranteeTarget.getScore());
        guaranteeTarget.setTarget(economicsGuaranteeTarget.getTarget());
        guaranteeTarget.setMinAccount(economicsGuaranteeTarget.getMinAccount());
        guaranteeTarget.setMaxAccount(economicsGuaranteeTarget.getMaxAccount());
        guaranteeTarget.setMinRate(economicsGuaranteeTarget.getMinRate());
        guaranteeTarget.setMaxRate(economicsGuaranteeTarget.getMaxRate());
        guaranteeTarget.setYear(economicsGuaranteeTarget.getYear());
        guaranteeTarget.setOpen(1);
        guaranteeTarget.setAccountName(economicsGuaranteeTarget.getMinAccount() + "w <担保金额<=" + economicsGuaranteeTarget.getMaxAccount()+"w");
        guaranteeTarget.setRateName(economicsGuaranteeTarget.getMinRate() + "<担保总金额/纳税销售收入比<=" + economicsGuaranteeTarget.getMaxRate());
        guaranteeTarget.setInsertTime(new Date());


        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String guaranteeTargetContent = guaranteeTargetModel.getContent();
            List<EconomicsGuaranteeTarget> economicsGuaranteeTargetList = JSON.parseArray(guaranteeTargetContent, EconomicsGuaranteeTarget.class);
            list.addAll(economicsGuaranteeTargetList);
            list.add(guaranteeTarget);
            guaranteeTargetModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTargetModel);
        } else if (guaranteeTargetModel != null) {
            list.add(guaranteeTarget);
            guaranteeTargetModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTargetModel);
        } else
            return 0;
    }

    @Override
    public EconomicsGuaranteeTarget getGuaranteeTargetByPk(String pk) {
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            List<EconomicsGuaranteeTarget> guaranteeTargetList = JSON.parseArray(guaranteeTargetModel.getContent(), EconomicsGuaranteeTarget.class);
            for (EconomicsGuaranteeTarget guaranteeTarget : guaranteeTargetList) {
                if (Objects.equals(guaranteeTarget.getPk(), pk)) {
                    return guaranteeTarget;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public List<EconomicsGuaranteeType> getGuaranteeTypeList() {
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String guaranteeTypeModelContent = guaranteeTypeModel.getContent();
            return JSON.parseArray(guaranteeTypeModelContent, EconomicsGuaranteeType.class);
        } else
            return null;
    }

    @Override
    public List<EconomicsGuaranteeType> getOpenGuaranteeTypeList() {
        List<EconomicsGuaranteeType> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String guaranteeTypeModelContent = guaranteeTypeModel.getContent();
            List<EconomicsGuaranteeType> guaranteeTypeList = JSON.parseArray(guaranteeTypeModelContent, EconomicsGuaranteeType.class);
            for (EconomicsGuaranteeType guarantee : guaranteeTypeList) {
                if (!Objects.equals(guarantee.getOpen(), 1)) {
                    list.add(guarantee);
                }
            }
        }
        return list;
    }

    @Override
    public PageModel<EconomicsGuaranteeTypeDto> getGuaranteeTypeData(QueryModel<EconomicsGuaranteeTypeDto> qm) {
        PageModel<EconomicsGuaranteeTypeDto> pm = new PageModel<EconomicsGuaranteeTypeDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isDelete", 1);

        map.put("open", qm.getEntity().getOpen());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        int totalCount = searchGuaranteeTypeCount(map);
        List<EconomicsGuaranteeTypeDto> list = searchGuaranteeTypeGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int updateGuaranteeTypeStatus(EconomicsGuaranteeType economicsGuaranteeType) {
        List<EconomicsGuaranteeType> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String guaranteeTypeContent = guaranteeTypeModel.getContent();
            List<EconomicsGuaranteeType> economicsGuaranteeTypeList = JSON.parseArray(guaranteeTypeContent, EconomicsGuaranteeType.class);
            for (EconomicsGuaranteeType guaranteeType : economicsGuaranteeTypeList) {
                if (!Objects.equals(guaranteeType.getPk(), economicsGuaranteeType.getPk())) {
                    list.add(guaranteeType);
                } else {
                    guaranteeType.setOpen(economicsGuaranteeType.getOpen());
                    list.add(guaranteeType);
                }
            }
            guaranteeTypeModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTypeModel);
        } else
            return 0;
    }

    @Override
    public int updateGuaranteeType(EconomicsGuaranteeType economicsGuaranteeType) {
        List<EconomicsGuaranteeType> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String onlineContent = guaranteeTypeModel.getContent();
            List<EconomicsGuaranteeType> economicsGuaranteeTypeList = JSON.parseArray(onlineContent, EconomicsGuaranteeType.class);
            for (EconomicsGuaranteeType guaranteeType : economicsGuaranteeTypeList) {
                if (!Objects.equals(guaranteeType.getPk(), economicsGuaranteeType.getPk())) {
                    list.add(guaranteeType);
                } else {
                    guaranteeType.setScore(economicsGuaranteeType.getScore());
                    guaranteeType.setType(economicsGuaranteeType.getType());
                    guaranteeType.setMinNumber(economicsGuaranteeType.getMinNumber());
                    guaranteeType.setMaxNumber(economicsGuaranteeType.getMaxNumber());
                    guaranteeType.setName(economicsGuaranteeType.getMinNumber() + "w <担保金额<=" + economicsGuaranteeType.getMaxNumber()+"w");
                    guaranteeType.setOpen(1);
                    guaranteeType.setInsertTime(new Date());
                    list.add(guaranteeType);
                }
            }
            guaranteeTypeModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTypeModel);
        } else
            return 0;
    }

    @Override
    public int insertGuaranteeType(EconomicsGuaranteeType economicsGuaranteeType) {
        List<EconomicsGuaranteeType> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");

        EconomicsGuaranteeType guaranteeType = new EconomicsGuaranteeType();
        guaranteeType.setPk(KeyUtils.getUUID());
        guaranteeType.setScore(economicsGuaranteeType.getScore());
        guaranteeType.setType(economicsGuaranteeType.getType());
        guaranteeType.setMinNumber(economicsGuaranteeType.getMinNumber());
        guaranteeType.setMaxNumber(economicsGuaranteeType.getMaxNumber());
        guaranteeType.setName(economicsGuaranteeType.getMinNumber() + "w <担保金额<=" + economicsGuaranteeType.getMaxNumber()+"w");
        guaranteeType.setOpen(1);
        guaranteeType.setInsertTime(new Date());


        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String guaranteeTypeContent = guaranteeTypeModel.getContent();
            List<EconomicsGuaranteeType> economicsGuaranteeTypeList = JSON.parseArray(guaranteeTypeContent, EconomicsGuaranteeType.class);
            list.addAll(economicsGuaranteeTypeList);
            list.add(guaranteeType);
            guaranteeTypeModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTypeModel);
        } else if (guaranteeTypeModel != null) {
            list.add(guaranteeType);
            guaranteeTypeModel.setContent(JSON.toJSONString(list));
            return b2bEconomicsDimensionDao.update(guaranteeTypeModel);
        } else
            return 0;
    }

    @Override
    public EconomicsGuaranteeType getGuaranteeTypeByPk(String pk) {
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            List<EconomicsGuaranteeType> guaranteeTypeList = JSON.parseArray(guaranteeTypeModel.getContent(), EconomicsGuaranteeType.class);
            for (EconomicsGuaranteeType guaranteeType : guaranteeTypeList) {
                if (Objects.equals(guaranteeType.getPk(), pk)) {
                    return guaranteeType;
                }
            }
        } else {
            return null;
        }
        return null;
    }


    private int searchGuaranteeTargetCount(Map map) {
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        int size =0;
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String onlineContent = guaranteeTargetModel.getContent();
            List<EconomicsGuaranteeTarget> list = JSON.parseArray(onlineContent, EconomicsGuaranteeTarget.class);
            for (EconomicsGuaranteeTarget economicsGuareanteeTarget : list) {
                boolean checked = true;
                if (map.get("open") != null)
                    if (!economicsGuareanteeTarget.getOpen().equals(map.get("open")))
                        if (!map.get("open").equals("")) {
                            checked = false;
                        }
                if (map.get("insertStartTime") != null)
                    if (!map.get("insertStartTime").equals(""))
                        if ((java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsGuareanteeTarget.getInsertTime()))) {
                            checked = false;
                        }
                if (map.get("insertEndTime") != null)
                    if (!map.get("insertEndTime").equals(""))
                        if ((java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsGuareanteeTarget.getInsertTime()))) {
                            checked = false;
                        }
                if (checked)
                    size++;
            }
            return size;
        } else
            return 0;
    }

    private List<EconomicsGuaranteeTargetDto> searchGuaranteeTargetGridExt(Map map) {
        B2bEconomicsDimensionModel guaranteeTargetModel = b2bEconomicsDimensionDao.getByItem("guaranteeTarget");
        List<EconomicsGuaranteeTargetDto> list = new ArrayList<>();
        if (guaranteeTargetModel != null && guaranteeTargetModel.getContent() != null && !Objects.equals(guaranteeTargetModel.getContent(), "")) {
            String onlineContent = guaranteeTargetModel.getContent();
            List<EconomicsGuaranteeTargetDto> guaranteeTargetDto = JSON.parseArray(onlineContent, EconomicsGuaranteeTargetDto.class);
            for (EconomicsGuaranteeTargetDto economicsGuaranteeTargetDto : guaranteeTargetDto) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsGuaranteeTargetDto.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsGuaranteeTargetDto.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsGuaranteeTargetDto.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(economicsGuaranteeTargetDto);
            }
            Integer start = (Integer) map.get("start");
            Integer limit = (Integer) map.get("limit");
            if (list.size() > start + limit)
                return list.subList(start, start + limit);
            else if (list.size() > start)
                return list.subList(start, list.size());
            else
                return list;
        } else
            return null;
    }


    private int searchGuaranteeTypeCount(Map map) {
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        int size = 0;
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String guaranteeTypeContent = guaranteeTypeModel.getContent();
            List<EconomicsGuaranteeType> list = JSON.parseArray(guaranteeTypeContent, EconomicsGuaranteeType.class);
            for (EconomicsGuaranteeType economicsGuaranteeType : list) {
                boolean checked = true;
                if (map.get("open") != null)
                    if (!economicsGuaranteeType.getOpen().equals(map.get("open")))
                        if (!map.get("open").equals("")) {
                            checked = false;
                        }
                if (map.get("insertStartTime") != null)
                    if (!map.get("insertStartTime").equals(""))
                        if ((java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsGuaranteeType.getInsertTime()))) {
                            checked = false;
                        }
                if (map.get("insertEndTime") != null)
                    if (!map.get("insertEndTime").equals(""))
                        if ((java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsGuaranteeType.getInsertTime()))) {
                            checked = false;
                        }
                if (checked)
                    size++;
            }
            return size;
        } else
            return 0;
    }

    private List<EconomicsGuaranteeTypeDto> searchGuaranteeTypeGridExt(Map map) {
        List<EconomicsGuaranteeTypeDto> list = new ArrayList<>();
        B2bEconomicsDimensionModel guaranteeTypeModel = b2bEconomicsDimensionDao.getByItem("guaranteeType");
        if (guaranteeTypeModel != null && guaranteeTypeModel.getContent() != null && !Objects.equals(guaranteeTypeModel.getContent(), "")) {
            String guaranteeTypeModelContent = guaranteeTypeModel.getContent();
            List<EconomicsGuaranteeTypeDto> guaranteeTypeDtos = JSON.parseArray(guaranteeTypeModelContent, EconomicsGuaranteeTypeDto.class);
            for (EconomicsGuaranteeTypeDto economicsGuaranteeTypeDto : guaranteeTypeDtos) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsGuaranteeTypeDto.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsGuaranteeTypeDto.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsGuaranteeTypeDto.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(economicsGuaranteeTypeDto);
            }
            Integer start = (Integer) map.get("start");
            Integer limit = (Integer) map.get("limit");
            if (list.size() > start + limit)
                return list.subList(start, start + limit);
            else if (list.size() > start)
                return list.subList(start, list.size());
            else
                return list;
        } else
            return null;
    }

    private int searchGuaranteeCountCount(Map map) {
        int size = 0;
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String guaranteeCountModelContent = guaranteeCountModel.getContent();
            List<EconomicsGuaranteeCount> list = JSON.parseArray(guaranteeCountModelContent, EconomicsGuaranteeCount.class);
            for (EconomicsGuaranteeCount economicsLimit : list) {
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

    private List<EconomicsGuaranteeCountDto> searchGuaranteeCountGridExt(Map map) {
        B2bEconomicsDimensionModel guaranteeCountModel = b2bEconomicsDimensionDao.getByItem("guaranteeCount");
        List<EconomicsGuaranteeCountDto> list = new ArrayList<>();
        if (guaranteeCountModel != null && guaranteeCountModel.getContent() != null && !Objects.equals(guaranteeCountModel.getContent(), "")) {
            String guaranteeCountModelContent = guaranteeCountModel.getContent();
            List<EconomicsGuaranteeCountDto> temp = JSON.parseArray(guaranteeCountModelContent, EconomicsGuaranteeCountDto.class);

            for (EconomicsGuaranteeCountDto economicsGuaranteeCountDto : temp) {
                boolean checked = true;
                if (map.get("open") != null && !map.get("open").equals("") && !economicsGuaranteeCountDto.getOpen().equals(map.get("open"))) {
                    checked = false;
                }
                if (map.get("insertStartTime") != null && !map.get("insertStartTime").equals("") && (java.sql.Date.valueOf((String) map.get("insertStartTime")).after(economicsGuaranteeCountDto.getInsertTime()))) {
                    checked = false;
                }
                if (map.get("insertEndTime") != null && !map.get("insertEndTime").equals("") && (java.sql.Timestamp.valueOf( map.get("insertEndTime")+" 23:59:59").before(economicsGuaranteeCountDto.getInsertTime()))) {
                    checked = false;
                }
                if (checked)
                    list.add(economicsGuaranteeCountDto);
            }
            Integer start = (Integer) map.get("start");
            Integer limit = (Integer) map.get("limit");
            if (list.size() > start + limit)
                return list.subList(start, start + limit);
            else if (list.size() > start)
                return list.subList(start, list.size());
            else
                return list;
        } else
            return null;
    }

}
