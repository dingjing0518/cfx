package cn.cf.service.yarn.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.SxTechnologyExtDao;
import cn.cf.dto.SxTechnologyDto;
import cn.cf.dto.SxTechnologyExtDto;
import cn.cf.model.SxTechnology;
import cn.cf.service.yarn.SxTechnologyService;
import cn.cf.util.KeyUtils;

@Service
public class SxTechnologyServiceImpl implements SxTechnologyService{

    @Autowired
    private SxTechnologyExtDao  sxTechnologyExtDao;
    @Override
    public PageModel<SxTechnologyExtDto> searchTechnology(QueryModel<SxTechnologyExtDto> qm) {
      
        PageModel<SxTechnologyExtDto> pm = new PageModel<SxTechnologyExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
        map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
        
        int totalCount = sxTechnologyExtDao.searchGridExtCount(map);
        List<SxTechnologyExtDto> list = sxTechnologyExtDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
  
        return pm;
    }

    
    
    @Override
    public int updateTechnology(SxTechnologyExtDto sxTechnologyExtDto) {
       
        int retVal = 0;
        
        
        Map<String,Object>m=new HashMap<String,Object>();
        m.put("name", sxTechnologyExtDto.getName());
        m.put("pk", sxTechnologyExtDto.getPk());
        int t=sxTechnologyExtDao.check(m);
        if(t>0){
            retVal=-3;
            return retVal;
        }
        
        if (sxTechnologyExtDto.getPk() != null && !"".equals(sxTechnologyExtDto.getPk())) {
            retVal = sxTechnologyExtDao.updateTechnology(sxTechnologyExtDto);
        } else {
            // 新增
            retVal = insertTechnology(sxTechnologyExtDto);
        }
        return retVal;
    }
    
    private int insertTechnology(SxTechnologyExtDto sxTechnologyExtDto) {
        SxTechnology sxTechnology = new SxTechnology();
        sxTechnology.setPk(KeyUtils.getUUID());

        sxTechnology.setName(sxTechnologyExtDto.getName());
        sxTechnology.setInsertTime(new Date());
        sxTechnology.setSort(sxTechnologyExtDto.getSort());
        if (sxTechnologyExtDto.getIsVisable() == null || "".equals(sxTechnologyExtDto.getIsVisable())) {
            sxTechnology.setIsVisable(Constants.ONE);
        } else {
            sxTechnology.setIsVisable(sxTechnologyExtDto.getIsVisable());
        }
        sxTechnology.setIsDelete(Constants.ONE);
        return sxTechnologyExtDao.insert(sxTechnology);
    }



    @Override
    public int updateTechnologyExt(SxTechnologyExtDto sxTechnologyExtDto) {
        int retVal = 0;
        if (sxTechnologyExtDto.getPk() != null && !"".equals(sxTechnologyExtDto.getPk())) {

            retVal = sxTechnologyExtDao.updateTechnology(sxTechnologyExtDto);
        }
        return retVal;
    }

  
    

    
    
}
