package cn.cf.service.yarn.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dto.SxMaterialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.SxMaterialExtDao;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxMaterialExtDto;

import cn.cf.model.SxMaterial;
import cn.cf.service.yarn.SxMaterialService;
import cn.cf.util.KeyUtils;

@Service
public class SxMaterialServiceImpl implements SxMaterialService {

    @Autowired
    private  SxMaterialExtDao sxMaterialExtDao;
    
    @Override
    public PageModel<SxMaterialExtDto> searchRawMaterialList(QueryModel<SxMaterialExtDto> qm) {
     
        PageModel<SxMaterialExtDto> pm = new PageModel<SxMaterialExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
        map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
        map.put("parentPk", "-1");
        int totalCount = sxMaterialExtDao.searchGridExtCount(map);
        List<SxMaterialExtDto> list = sxMaterialExtDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
  
        return pm;
    }

    @Override
    public PageModel<SxMaterialExtDto> searchRawMaterialListTwo(QueryModel<SxMaterialExtDto> qm) {

        PageModel<SxMaterialExtDto> pm = new PageModel<SxMaterialExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
        map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
        map.put("parentPk", qm.getEntity().getParentPk());
        int totalCount = sxMaterialExtDao.searchGridExtTwoCount(map);
        List<SxMaterialExtDto> list = sxMaterialExtDao.searchGridTwoExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);

        return pm;
    }

    @Override
    public int updateMaterail(SxMaterialExtDto sxMaterialExtDto) {
       
        int retVal = 0;
        Map<String,Object>m=new HashMap<String,Object>();
        m.put("name", sxMaterialExtDto.getName());
        m.put("pk", sxMaterialExtDto.getPk());
        m.put("parentPk", sxMaterialExtDto.getParentPk());
        int t=sxMaterialExtDao.check(m);
        if(t>0){
            retVal=-3;
            return retVal;
        }
        if (sxMaterialExtDto.getPk() != null && !"".equals(sxMaterialExtDto.getPk())) {
            retVal = sxMaterialExtDao.updateMaterial(sxMaterialExtDto);
        } else {
            // 新增
            retVal = insertMaterial(sxMaterialExtDto);
        }
        return retVal;
    }

    private int insertMaterial(SxMaterialExtDto sxMaterialExtDto) {
        SxMaterial sxMaterial = new SxMaterial();
        sxMaterial.setPk(KeyUtils.getUUID());

        sxMaterial.setName(sxMaterialExtDto.getName());
        sxMaterial.setInsertTime(new Date());
        sxMaterial.setSort(sxMaterialExtDto.getSort());
        sxMaterial.setParentPk(sxMaterialExtDto.getParentPk());
        if (!sxMaterialExtDto.getParentPk().equals("-1")) {
			SxMaterialDto     dto  =   sxMaterialExtDao.getByPk(sxMaterialExtDto.getParentPk());
			 sxMaterial.setIsBlend(dto.getIsBlend());
		}else {
			 sxMaterial.setIsBlend(sxMaterialExtDto.getIsBlend());
		}
        if (sxMaterialExtDto.getIsVisable() == null || "".equals(sxMaterialExtDto.getIsVisable())) {
            sxMaterial.setIsVisable(Constants.ONE);
        } else {
            sxMaterial.setIsVisable(sxMaterialExtDto.getIsVisable());
        }
        sxMaterial.setIsDelete(Constants.ONE);
        return sxMaterialExtDao.insert(sxMaterial);
    }

    @Override
    public int updateMaterailEx(SxMaterialExtDto sxMaterialExtDto) {
        int retVal=0;
        if (sxMaterialExtDto.getPk() != null && !"".equals(sxMaterialExtDto.getPk())) {
            retVal = sxMaterialExtDao.updateMaterial(sxMaterialExtDto);
        } 
        return retVal;
    }

    
    @Override
    public List<SxMaterialDto> searchParentRawMaterial(SxMaterialDto dto) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentPk",dto.getParentPk());
        map.put("isVisable",dto.getIsVisable());
        return sxMaterialExtDao.searchList(map);
    }
    
	@Override
	public List<SxMaterialDto> getchangeSecondMaterial(String rawMaterialPk) {
        Map<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        map.put("parentPk", rawMaterialPk);
        return sxMaterialExtDao.searchList(map);
    }
    

}
