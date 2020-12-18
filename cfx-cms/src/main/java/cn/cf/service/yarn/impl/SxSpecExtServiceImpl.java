package cn.cf.service.yarn.impl;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.SxSpecExtDao;
import cn.cf.dto.SxSpecDto;
import cn.cf.dto.SxSpecExtDto;
import cn.cf.model.SxSpec;
import cn.cf.service.yarn.SxSpecExtService;
import cn.cf.util.KeyUtils;
import org.apache.tools.ant.taskdefs.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SxSpecExtServiceImpl implements SxSpecExtService {
    @Autowired
    private SxSpecExtDao sxSpecExtDao;
    @Override
    public PageModel<SxSpecExtDto> searchSxSpecList(QueryModel<SxSpecExtDto> qm) {
        PageModel<SxSpecExtDto> pm = new PageModel<SxSpecExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        int totalCount = sxSpecExtDao.searchGridExtCount(map);
        List<SxSpecExtDto> list = sxSpecExtDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }
    @Override
    public int updateSxSpec(SxSpecExtDto dto) {
        int retVal = 0;
        if (dto.getPk() != null && !"".equals(dto.getPk())) {
            dto.setUpdateTime(new Date());
            retVal = sxSpecExtDao.updateObj(dto);
        } else {
            // 新增
            retVal = insertSpec(dto);
        }
        return retVal;
    }
    private int insertSpec (SxSpecExtDto dto) {
        SxSpec model = new SxSpec();
        model.setInsertTime(new Date());
        model.setPk(KeyUtils.getUUID());
        model.setName(dto.getName());
        model.setSort(dto.getSort());
        model.setIsVisable(Constants.ONE);
        return sxSpecExtDao.insert(model);
    }


    @Override
    public int del(String pk) {
        return sxSpecExtDao.delete(pk);
    }


    @Override
    public int updateSxSpecStatus(SxSpecExtDto dto) {
        return sxSpecExtDao.updateObj(dto);
    }

    @Override
    public List<SxSpecDto> searchAllSxSpecList() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isVisable", Constants.ONE);
        return sxSpecExtDao.searchList(map);
    }

    @Override
    public List<SxSpecDto> searchSxSpecList(Map<String, Object> map) {

        return sxSpecExtDao.searchList(map);
    }
}
