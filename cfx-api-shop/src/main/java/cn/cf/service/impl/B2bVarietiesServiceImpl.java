package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bVarietiesDaoEx;
import cn.cf.dto.B2bVarietiesDto;
import cn.cf.service.B2bVarietiesService;
import cn.cf.util.StringUtils;

@Service
public class B2bVarietiesServiceImpl  implements B2bVarietiesService{
	
	@Autowired
	private B2bVarietiesDaoEx varietiesDao;

	/**
	 * 查询商品品种
	 * @param parentPk
	 * @return
	 */
	@Override
	public List<B2bVarietiesDto> searchVarietiesList(String parentPk) {
		List<B2bVarietiesDto> list = null ; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);//1:显示,2:不显示
		map.put("isVisable", 1);
		if(parentPk.equals("-1")){//大类
			map.put("parentPk",parentPk);
			list =  varietiesDao.searchList(map);
		}else{//规格系列
			if (null != parentPk && !"".equals(parentPk)) {
				map.put("parentPks", StringUtils.splitStrs(parentPk));
			}
			list = varietiesDao.searchChList(map);	
		}
		return list;
	}

	@Override
	public PageModel<B2bVarietiesDto> searchVarietiesPage(String parentPk,String name,Integer start,Integer limit) {
		PageModel<B2bVarietiesDto> pm = new PageModel<B2bVarietiesDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<B2bVarietiesDto> list = null ;
		int counts = 0;
		map.put("isDelete", 1);//1:显示,2:不显示
		map.put("isVisable", 1);
		map.put("name", name);
		map.put("start", start);
		map.put("limit", limit);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		if(parentPk.equals("-1")){//大类
			map.put("parentPk",parentPk);
			list =  varietiesDao.searchGrid(map);
			counts = varietiesDao.searchGridCount(map);
		}else{//规格系列
			if (parentPk.equals(" ")) {
				map.put("parentPks", parentPk);
			}else {
				map.put("parentPks", StringUtils.splitStrs(parentPk));
			}
			list = varietiesDao.searchChList(map);
			counts = varietiesDao.searchChCount(map);
		}
		pm.setDataList(list);
		pm.setTotalCount(counts);
		pm.setStartIndex(start);
		pm.setPageSize(limit);
		return pm;
	}

	@Override
	public List<B2bVarietiesDto> searchVarietiesNameByVarietiesPks(
			String varietiesPks) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(!"-1".equals(varietiesPks)){
			String [] varietiesPk=varietiesPks.split(",");
			if(varietiesPk.length>0){
				map.put("varietiesPks", varietiesPk);
			} 
		} 
		return varietiesDao.searchVarietiesNameByVarietiesPks(map);
		
	}

}
