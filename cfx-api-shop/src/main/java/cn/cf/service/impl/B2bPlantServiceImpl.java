package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bPlantDaoEx;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bPlantDtoEx;
import cn.cf.model.B2bPlant;
import cn.cf.service.B2bPlantService;
import cn.cf.service.CommonService;

@Service
public class B2bPlantServiceImpl implements B2bPlantService {
	@Autowired
	private B2bPlantDaoEx b2bPlantDao;
 
	
	@Autowired 
	private CommonService commonService;

	@Override
	public List<B2bPlantDto> searchList(Map<String, Object> map) {
		return b2bPlantDao.searchList(map);
	}

	@Override
	public B2bPlantDto isPlantRepeated(Map<String, Object> map) {
		return b2bPlantDao.isPlantRepeated(map);
	}

	@Override
	public int insert(B2bPlant plantModel) {
		return b2bPlantDao.insert(plantModel);
	}

	@Override
	public int update(B2bPlant plantModel) {
		return b2bPlantDao.update(plantModel);
	}

	@Override
	public B2bPlantDto getByPk(String pk) {
		return b2bPlantDao.getByPk(pk);
	}

	@Override
	public PageModel<B2bPlantDtoEx> searchPlantList(Map<String, Object> map) {
		PageModel<B2bPlantDtoEx> pm = new PageModel<B2bPlantDtoEx>();
		List<B2bPlantDtoEx> list = b2bPlantDao.searchPlantGrid(map);
		int count = b2bPlantDao.searchGridCount(map);
	if (Integer.parseInt(map.get("limit").toString())>0) {
			for (B2bPlantDtoEx b : list) {
				b.setType(commonService.isDeletePlant(b.getPk()));
			}
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		pm.setTotalCount(count);
		pm.setDataList(list);
		return pm;
	}

//	private Integer isDeletePlant(String plantPk) {
//		Integer type = null;
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("isDelete", 1);
//		m.put("plantPk",plantPk);
//		int result = b2bGoodsDaoEx.searchGridCount(m);
//		if (result > 0) {
//			type =1;// 1商品关联,2商品未关联
//		} else {
//			type =2;
//		}
//		return type;
//	}

	@Override
	public List<B2bPlantDto> searchAllPlantList(Map<String, Object> map) {
		return b2bPlantDao.searchList(map);
	}

}
