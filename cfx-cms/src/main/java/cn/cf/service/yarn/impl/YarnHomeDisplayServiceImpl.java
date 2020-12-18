package cn.cf.service.yarn.impl;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.service.yarn.YarnHomeDisplayService;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class YarnHomeDisplayServiceImpl implements YarnHomeDisplayService {

    @Autowired
    private SxMaterialExtDao sxMaterialDao;
    
    @Autowired
    private SxTechnologyExtDao SxTechnologyDao;

    @Autowired
    private SxSpecExtDao  sxSpecExtDao;

    @Override
    public List<SxTechnologyDto> getAllTechnologyList() {
        Map<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        return SxTechnologyDao.searchList(map);
    }

    @Override
    public List<SxTechnologyDto> getNoIsVisTechnologyList() {
        Map<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        return SxTechnologyDao.searchList(map);
    }

    @Override
    public List<SxMaterialDto> getAllMaterialList() {
        Map<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        return sxMaterialDao.searchList(map);
    }

    @Override
    public List<SxMaterialDto> getNoIsVisMaterialList(String  parentPk) {
        Map<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        map.put("parentPk", parentPk);
        return sxMaterialDao.searchList(map);
    }

    @Override
    public List<SxMaterialDto> getMaterialListByGrade(Map<String, Object> map) {
        return sxMaterialDao.searchList(map);
    }

    @Override
    public List<SxSpecDto> getSxSpecList() {
        Map<String, Object> map = new HashMap<>();
        map.put("isDelete", Constants.ONE);
        map.put("isVisable", Constants.ONE);
        return sxSpecExtDao.searchList(map);
    }
}
