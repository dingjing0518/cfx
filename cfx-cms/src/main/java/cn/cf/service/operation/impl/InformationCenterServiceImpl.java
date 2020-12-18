package cn.cf.service.operation.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import cn.cf.dao.SysCategoryDao;
import cn.cf.dao.SysNewsCategoryDao;
import cn.cf.dao.SysNewsDao;
import cn.cf.dao.SysRegionsDao;
import cn.cf.dto.SysCategoryDto;
import cn.cf.dto.SysNewsCategoryDto;
import cn.cf.dto.SysNewsDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.InformationCenterService;
import cn.cf.util.KeyUtils;

@Service
public class InformationCenterServiceImpl implements InformationCenterService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SysNewsDao sysNewsDao;

	@Autowired
	private SysNewsCategoryDao sysNewsCategoryDao;

	@Autowired
	private SysCategoryDao sysCategoryDao;
	
	@Autowired
	private SysRegionsDao sysRegionsDao;
	

	@Override
	public String importNewsToMongo() {
		int result = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isDelete", 1);
			List<SysNewsDto> sysNewsList = sysNewsDao.searchList(map);

			result = updateSysNewsStorage(sysNewsList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}

		return msg;
	}

	private int updateSysNewsStorage(List<SysNewsDto> sysNewsList) {
		int result = 0;
		if (sysNewsList != null && sysNewsList.size() > 0) {
			for (SysNewsDto sysNewsDto : sysNewsList) {
				try {
					Query query = Query.query(Criteria.where("newsPk").is(sysNewsDto.getPk()));
					mongoTemplate.findAllAndRemove(query, SysNewsStorageEntity.class);
					List<SysNewsCategoryDto> newsCategoryList = (List<SysNewsCategoryDto>) sysNewsCategoryDao
							.getByNewsPk(sysNewsDto.getPk());
					if (newsCategoryList != null && newsCategoryList.size() > 0) {
						for (SysNewsCategoryDto sysNewsCategoryDto : newsCategoryList) {
							SysNewsStorageEntity newsStorage = setNewsParams(sysNewsDto, sysNewsCategoryDto);
							mongoTemplate.save(newsStorage);
							result = 1;
						}
					} else {
						SysNewsStorageEntity newsStorage = setNewsParamsCtgoryIsNull(sysNewsDto);
						mongoTemplate.save(newsStorage);
						result = 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 资讯分类不为空设置参数
	 * 
	 * @param sysNewsDto
	 * @return
	 */
	private SysNewsStorageEntity setNewsParamsCtgoryIsNull(SysNewsDto sysNewsDto) {
		SysNewsStorageEntity newsStorage = new SysNewsStorageEntity();
		newsStorage.setId(KeyUtils.getUUID());
		newsStorage.setNewsPk(sysNewsDto.getPk());
		newsStorage.setTitle(sysNewsDto.getTitle());
		if (sysNewsDto.getInsertTime() != null) {
			newsStorage.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysNewsDto.getInsertTime()));
		} else {
			newsStorage.setInsertTime("");
		}
		newsStorage.setIsDelete(sysNewsDto.getIsDelete());
		newsStorage.setIsVisable(sysNewsDto.getIsVisable());
		newsStorage.setContent(sysNewsDto.getContent());
		if (sysNewsDto.getRecommend() != null) {
			newsStorage.setRecommend(sysNewsDto.getRecommend());
		} else {
			newsStorage.setRecommend(0);
		}
		if (sysNewsDto.getTop() != null) {
			newsStorage.setTop(sysNewsDto.getTop());
		} else {
			newsStorage.setTop(2);
		}
		newsStorage.setKeyword(sysNewsDto.getKeyword());
		newsStorage.setNewAbstrsct(sysNewsDto.getNewAbstrsct());
		newsStorage.setUrl(sysNewsDto.getUrl());
		if (sysNewsDto.getEstimatedTime() != null) {
			newsStorage.setEstimatedTime(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysNewsDto.getEstimatedTime()));
		} else {
			newsStorage.setEstimatedTime("");
		}
		newsStorage.setStatus(sysNewsDto.getStatus());
		newsStorage.setNewSource(sysNewsDto.getNewSource());
		return newsStorage;
	}

	/**
	 * 资讯分类为空设置参数
	 * 
	 * @param sysNewsDto
	 * @param sysNewsCategoryDto
	 * @return
	 */
	private SysNewsStorageEntity setNewsParams(SysNewsDto sysNewsDto, SysNewsCategoryDto sysNewsCategoryDto) {
		SysNewsStorageEntity newsStorage = new SysNewsStorageEntity();
		SysCategoryDto categoryDto = sysCategoryDao.getByPk(sysNewsCategoryDto.getCategoryPk());
		newsStorage.setId(KeyUtils.getUUID());
		newsStorage.setNewsPk(sysNewsDto.getPk());
		if (categoryDto != null) {
			newsStorage.setCategoryPk(categoryDto.getPk());
			newsStorage.setCategoryName(categoryDto.getName());
			newsStorage.setParentId(categoryDto.getParentId());
		}
		newsStorage.setTitle(sysNewsDto.getTitle());
		if (sysNewsDto.getInsertTime() != null) {
			newsStorage.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysNewsDto.getInsertTime()));
		}
		newsStorage.setIsDelete(sysNewsDto.getIsDelete());
		newsStorage.setIsVisable(sysNewsDto.getIsVisable());
		newsStorage.setContent(sysNewsDto.getContent());
		if (sysNewsDto.getRecommend() != null) {
			newsStorage.setRecommend(sysNewsDto.getRecommend());
		} else {
			newsStorage.setRecommend(0);
		}
		if (sysNewsDto.getTop() != null) {
			newsStorage.setTop(sysNewsDto.getTop());
		} else {
			newsStorage.setTop(2);
		}
		newsStorage.setKeyword(sysNewsDto.getKeyword());
		newsStorage.setNewAbstrsct(sysNewsDto.getNewAbstrsct());
		newsStorage.setUrl(sysNewsDto.getUrl());
		if (sysNewsDto.getEstimatedTime() != null) {
			newsStorage.setEstimatedTime(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysNewsDto.getEstimatedTime()));
		} else {
			newsStorage.setEstimatedTime("");
		}
		newsStorage.setStatus(sysNewsDto.getStatus());
		newsStorage.setNewSource(sysNewsDto.getNewSource());
		newsStorage.setPvCount(sysNewsDto.getPvCount() == null ? 0L : sysNewsDto.getPvCount());
		return newsStorage;
	}

	

	
	

	@Override
	public List<SysRegionsDto> searchSysRegionsList(String parentPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentPk", parentPk);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		return sysRegionsDao.searchList(map);
	}

}
