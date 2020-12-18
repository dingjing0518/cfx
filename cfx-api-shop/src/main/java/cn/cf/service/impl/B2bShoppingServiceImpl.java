package cn.cf.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.SxHomeSecondnavigationDao;
import cn.cf.dao.SxHomeThirdnavigationDao;
import cn.cf.dao.SysHomeBannerMassageDao;
import cn.cf.dao.SysHomeBannerProductnameDao;
import cn.cf.dao.SysHomeBannerSeriesDao;
import cn.cf.dao.SysHomeBannerSpecDao;
import cn.cf.dao.SysHomeBannerVarietiesDao;
import cn.cf.dao.SysSupplierRecommedDao;
import cn.cf.dto.SxHomeSecondnavigationDto;
import cn.cf.dto.SxHomeSecondnavigationDtoEx;
import cn.cf.dto.SysHomeBannerMassageDto;
import cn.cf.dto.SysHomeBannerProductnameDto;
import cn.cf.dto.SysHomeBannerProductnameDtoEx;
import cn.cf.dto.SysHomeBannerSeriesDto;
import cn.cf.dto.SysHomeBannerSpecDto;
import cn.cf.dto.SysHomeBannerVarietiesDto;
import cn.cf.dto.SysHomeBannerVarietiesDtoEx;
import cn.cf.dto.SysSupplierRecommedDto;
import cn.cf.entity.LeftNavigation;
import cn.cf.service.B2bShoppingService;

@Service
public class B2bShoppingServiceImpl implements B2bShoppingService {

	@Autowired
	private SysHomeBannerVarietiesDao homeBannerVarietiesDao;

	@Autowired
	private SysHomeBannerMassageDao homeBannerMassageDao;

	@Autowired
	private SysHomeBannerProductnameDao homeBannerProductnameDao;

	@Autowired
	private SysHomeBannerSeriesDao homeBannerSeriesDao;

	@Autowired
	private SysHomeBannerSpecDao homeBannerSpecDao;

	@Autowired
	private SysSupplierRecommedDao sysSupplierRecommedDao;

	@Autowired
	private SxHomeSecondnavigationDao sxHomeSecondnavigationDao;

	@Autowired
	private SxHomeThirdnavigationDao sxHomeThirdnavigationDao;

	@Override
	public List<SysHomeBannerProductnameDtoEx> homeProductList(
			Map<String, Object> map) {
		List<SysHomeBannerProductnameDto> list = homeBannerProductnameDao
				.searchGrid(map);
		List<SysHomeBannerProductnameDtoEx> newList = new ArrayList<SysHomeBannerProductnameDtoEx>();
		if (null != list && list.size() > 0) {
			List<SysHomeBannerSpecDto> specList = null;
			List<SysHomeBannerSeriesDto> seiesList = null;
			List<SysHomeBannerVarietiesDto> varietiesList = null;
			List<SysHomeBannerMassageDto> smallUrls = null;
			List<SysHomeBannerMassageDto> bigUrls = null;

			for (SysHomeBannerProductnameDto shbp : list) {
				SysHomeBannerProductnameDtoEx sysHomeBannerProductnameDtoEx = new SysHomeBannerProductnameDtoEx();
				try {
					BeanUtils.copyProperties(sysHomeBannerProductnameDtoEx,
							shbp);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				map.put("productnamePk", shbp.getPk());
				specList = homeBannerSpecDao.searchGrid(map);
				seiesList = homeBannerSeriesDao.searchGrid(map);
				varietiesList = homeBannerVarietiesDao.searchGrid(map);
				map.put("type", 1);// 图片消息展示类型(1上部小图 下部大图)
				smallUrls = homeBannerMassageDao.searchGrid(map);
				map.put("type", 2);
				bigUrls = homeBannerMassageDao.searchGrid(map);
				sysHomeBannerProductnameDtoEx.setSpecList(specList);// 规格大类
				sysHomeBannerProductnameDtoEx.setSeiesList(seiesList);// 规格系列
				sysHomeBannerProductnameDtoEx.setVarietiesList(varietiesList);// 品种
				sysHomeBannerProductnameDtoEx.setSmallUrls(smallUrls);// 小图展示
				sysHomeBannerProductnameDtoEx.setBigUrls(bigUrls);// 大图展示
				newList.add(sysHomeBannerProductnameDtoEx);
			}
		}
		return newList;
	}

	@Override
	public List<SysHomeBannerProductnameDtoEx> nagigationProductList(
			Map<String, Object> map) {
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		// 品名
		List<SysHomeBannerProductnameDto> list = homeBannerProductnameDao
				.searchGrid(map);
		List<SysHomeBannerProductnameDtoEx> newList = new ArrayList<SysHomeBannerProductnameDtoEx>();
		if (null != list && list.size() > 0) {
			List<SysHomeBannerVarietiesDto> varietiesList = null;
			for (SysHomeBannerProductnameDto shbp : list) {
				SysHomeBannerProductnameDtoEx sysHomeBannerProductnameDtoEx = new SysHomeBannerProductnameDtoEx();
				try {
					BeanUtils.copyProperties(sysHomeBannerProductnameDtoEx,
							shbp);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				map.put("productnamePk", shbp.getPk());
				// 品种
				varietiesList = homeBannerVarietiesDao.searchGrid(map);
				sysHomeBannerProductnameDtoEx.setVarietiesList(varietiesList);
				newList.add(sysHomeBannerProductnameDtoEx);
			}
		}
		return newList;
	}

	@Override
	public List<SysHomeBannerVarietiesDtoEx> nagigationVarietiesList(
			Map<String, Object> map) {
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		SysHomeBannerProductnameDto product = homeBannerProductnameDao
				.getByPk(map.get("productnamePk").toString());
		// 品种
		List<SysHomeBannerVarietiesDto> varietiesList = homeBannerVarietiesDao
				.searchGrid(map);
		List<SysHomeBannerVarietiesDtoEx> newList = new ArrayList<SysHomeBannerVarietiesDtoEx>();
		if (null != varietiesList && varietiesList.size() > 0) {
			for (SysHomeBannerVarietiesDto shbp : varietiesList) {
				SysHomeBannerVarietiesDtoEx varietiesDtoEx = new SysHomeBannerVarietiesDtoEx();
				try {
					BeanUtils.copyProperties(varietiesDtoEx, shbp);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				map.put("productnamePk", shbp.getProductnamePk());
				map.put("varietiesPk", shbp.getVarietiesPk());
				List<SysHomeBannerSeriesDto> seriesList = homeBannerSeriesDao
						.searchGrid(map);
				if (null != product) {
					varietiesDtoEx.setProductPk(product.getProductPk());
				}
				varietiesDtoEx.setSeriesList(seriesList);
				newList.add(varietiesDtoEx);
			}
		}
		return newList;
	}

	@Override
	public List<SysSupplierRecommedDto> nagigationSupplierRecommedList(
			Map<String, Object> map) {
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		map.put("isRecommed", 1);
		map.put("isOnline",1);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		return sysSupplierRecommedDao.searchList(map);
	}

	@Override
	public LinkedHashMap<Integer, Object> getLeftAllList() {
		LinkedHashMap<Integer, Object> all = new LinkedHashMap<Integer, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("orderName", "sort");
		map.put("orderType", "desc");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("orderName", "sort");
		map1.put("orderType", "desc");
		map1.put("isDelete", 1);
		map1.put("platform",1);
		map1.put("isOnline",1);
		LeftNavigation ln = new LeftNavigation();
		List<SxHomeSecondnavigationDto> snList = null;
		SxHomeSecondnavigationDtoEx hsdtoEx = new SxHomeSecondnavigationDtoEx();
		for (int i = 1; i < 4; i++) {
			ln = new LeftNavigation();
			map.put("parentNavigation", i);
			map.remove("secondNavigationPk");
			snList = sxHomeSecondnavigationDao.searchList(map);
			for (SxHomeSecondnavigationDto hsdto : snList) {
				hsdtoEx = new SxHomeSecondnavigationDtoEx();
				hsdtoEx.UpdateMine(hsdto);
				map.put("secondNavigationPk", hsdto.getPk());
				hsdtoEx.setThList(sxHomeThirdnavigationDao.searchList(map));
				ln.getList().add(hsdtoEx);
			}
			if (i==1){
				map1.put("block","cf");
			}else if (i==2){
				map1.put("block","sx");
			}
			map1.put("position", 1);
			ln.setReList(sysSupplierRecommedDao.searchList(map1));

			all.put(i, ln);
		}
		return all;
	}

}
