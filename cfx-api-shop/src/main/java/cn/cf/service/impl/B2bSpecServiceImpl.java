package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bSpecDaoEx;
import cn.cf.dto.B2bSpecDto;
import cn.cf.service.B2bSpecService;
import cn.cf.util.StringUtils;

@Service
public class B2bSpecServiceImpl implements B2bSpecService{
	
	@Autowired
	private B2bSpecDaoEx specDao;
	
	
	/**
	 * 查询规格 ：type(1大类 2系列)
	 */
	@Override
	public List<B2bSpecDto> searchSpecsList(String parentPk) {
		List<B2bSpecDto> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);//1:显示,2:不显示
		map.put("isDelete", 1);
		if(parentPk.equals("-1")){//大类
			map.put("parentPk", parentPk);
			list =  specDao.searchList(map);
		}else{//规格系列
			if (parentPk.equals("")) {//全部系列
				map.put("parentPks", parentPk);
			} else {
				map.put("parentPks", StringUtils.splitStrs(parentPk));
			}
			list = specDao.searchChList(map);	
		}
		return list;
	}


	@Override
	public PageModel<B2bSpecDto> searchSpecsPage(String parentPk,String name,
			Integer start, Integer limit) {
		PageModel<B2bSpecDto> pm = new PageModel<B2bSpecDto>();
		List<B2bSpecDto> list = null;
		int counts = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);//1:显示,2:不显示
		map.put("isDelete", 1);
		map.put("name", name);
		map.put("start", start);
		map.put("limit", limit);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		if(parentPk.equals("-1")){//大类
			map.put("parentPk", parentPk);
			list =  specDao.searchGrid(map);
			counts = specDao.searchGridCount(map);
		}else{//规格系列
			if (parentPk.equals("")) {//全部系列
				map.put("parentPks", parentPk);
			} else {
				map.put("parentPks", StringUtils.splitStrs(parentPk));
			}
			list = specDao.searchChList(map);
			counts = specDao.searchChCount(map);
		}
		pm.setDataList(list);
		pm.setTotalCount(counts);
		pm.setStartIndex(start);
		pm.setPageSize(limit);
		return pm;
	}


	@Override
	public List<B2bSpecDto> searchSeriesNameBySeriesPks(String seriesPks) {
		Map<String, Object> map=new HashMap<String, Object>();
		if( !"-1".equals(seriesPks)){
			String [] seriesPk=seriesPks.split(",");
			if(seriesPk.length>0){
				map.put("seriesPks", seriesPk);
			}
		}
		return specDao.searchSeriesNameBySeriesPks(map);
		
	}
	
	
	
	
	
	

}
