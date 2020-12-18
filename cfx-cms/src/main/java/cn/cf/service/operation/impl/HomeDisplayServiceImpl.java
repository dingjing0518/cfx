package cn.cf.service.operation.impl;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxTechnologyDto;
import cn.cf.entity.*;
import cn.cf.json.JsonUtils;
import cn.cf.model.*;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.HomeDisplayService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeDisplayServiceImpl implements HomeDisplayService {

	@Autowired
	private B2bProductDao b2bProductDao;

	@Autowired
	private B2bVarietiesDao b2bVarietiesDao;

	@Autowired
	private B2bSpecDao b2bSpecDao;

	@Autowired
	private B2bProductExtDao b2bProductExtDao;

	@Autowired
	private SysHomeBannerProductnameExtDao sysHomeBannerProductnameDao;

	@Autowired
	private SysHomeBannerProductnameExtDao sysHomeBannerProductnameExtDao;

	@Autowired
	private SysHomeBannerMassageExtDao sysHomeBannerMassageExtDao;

	@Autowired
	private SysHomeBannerMassageDao sysHomeBannerMassageDao;

	@Autowired
	private SysHomeBannerSeriesDao sysHomeBannerSeriesDao;

	@Autowired
	private SysHomeBannerSeriesExtDao sysHomeBannerSeriesExtDao;

	@Autowired
	private SysHomeBannerSpecExtDao sysHomeBannerSpecExtDao;

	@Autowired
	private SysHomeBannerSpecDao sysHomeBannerSpecDao;

	@Autowired
	private SysHomeBannerVarietiesExtDao sysHomeBannerVarietiesExtDao;

	@Autowired
	private SysHomeBannerVarietiesDao sysHomeBannerVarietiesDao;

	@Autowired
	private B2bKeywordHotDao b2bKeywordHotDao;

	@Autowired
	private B2bFriendLinkDao b2bFriendLinkDao;

	@Autowired
	private B2bPriceMovementExtDao b2bPriceMovementExtDao;
	
	@Autowired
	private B2bGoodsExtDao b2bGoodsExtDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SxHomeSecondnavigationExtDao   sxHomeSecondnavigationExtDao;
	
	@Autowired
	private  SxHomeThirdnavigationExtDao sxHomeThirdnavigationExtDao;
	
	@Autowired
	private SxTechnologyDao  sxTechnologyDao;
	
	@Autowired
	private B2bBrandDao  b2bBrandDao;
	
	@Autowired
	private SxMaterialDao  sxMaterialDao;

	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;

	@Override
	public List<B2bProductDto> getB2bProductList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		return b2bProductDao.searchList(map);
	}

	@Override
	public List<B2bProductDto> getAllProductList() {
		return b2bProductExtDao.getAllProductList();
	}

	@Override
	public PageModel<SysHomeBannerProductnameDto> searchHomeBannerProductnameList(
			QueryModel<SysHomeBannerProductnameExtDto> qm) {
		PageModel<SysHomeBannerProductnameDto> pm = new PageModel<SysHomeBannerProductnameDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("productName", qm.getEntity().getProductName());
		map.put("productPk", qm.getEntity().getProductPk());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = sysHomeBannerProductnameDao.searchGridCount(map);
		List<SysHomeBannerProductnameDto> list = sysHomeBannerProductnameDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateHomeBannerProductname(SysHomeBannerProductnameExtDto dto) {
		int result = 0;
		if (result == 1 && dto.getIsDelete() != null && dto.getIsDelete() == 2) {
			result = sysHomeBannerProductnameDao.updateObj(dto);
			sysHomeBannerMassageExtDao.updateBannerByProductnamePk(dto.getPk());
			sysHomeBannerSeriesExtDao.updateBannerByProductnamePk(dto.getPk());
			sysHomeBannerSpecExtDao.updateBannerByProductnamePk(dto.getPk());
			sysHomeBannerVarietiesExtDao.updateBannerByProductnamePk(dto.getPk());
		}
		dto.setUpdateTime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productName", dto.getProductName());
		map.put("detail", dto.getDetail());
		map.put("url", dto.getUrl());
		List<SysHomeBannerProductnameDto> list = sysHomeBannerProductnameDao.searchExtList(map);
		if (null != list && list.size() > 0) {
			result = 2;
		} else {
			result = sysHomeBannerProductnameDao.updateObj(dto);
		}
		return result;
	}

	@Override
	public int updateIsVisAndDelHomePgProNameBanner(SysHomeBannerProductnameExtDto dto) {
		// TODO Auto-generated method stub
		return sysHomeBannerProductnameDao.updateObj(dto);
	}

	@Override
	public int insertHomeBannerProductname(SysHomeBannerProductname dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productPk", dto.getProductPk());
		map.put("isDelete", Constants.ONE);
		List<SysHomeBannerProductnameDto> sysDtoList = sysHomeBannerProductnameDao.searchExtList(map);
		if (sysDtoList != null && sysDtoList.size() > 0) {
			result = 2;
		} else {
			dto.setPk(KeyUtils.getUUID());
			dto.setIsDelete(1);
			dto.setInsertTime(new Date());
			dto.setIsVisable(1);
			result = sysHomeBannerProductnameDao.insert(dto);
		}
		return result;
	}

	@Override
	public int updateHomeBannerProductnameSort(SysHomeBannerProductnameDto dto) {
		return sysHomeBannerProductnameDao.updateObj(dto);
	}

	@Override
	public int updateHomeBannerVarietiesnameSort(SysHomeBannerVarietiesDto dto) {
		return sysHomeBannerVarietiesExtDao.updateObj(dto);
	}

	@Override
	public int updateHomeBannerSpecnameSort(SysHomeBannerSpecDto dto) {
		return sysHomeBannerSpecExtDao.updateObj(dto);
	}

	@Override
	public int updateHomeBannerSeriesnameSort(SysHomeBannerSeriesDto dto) {
		return sysHomeBannerSeriesExtDao.updateObj(dto);
	}

	@Override
	public PageModel<SysHomeBannerMassageExtDto> searchHomeBannerMessageList(
			QueryModel<SysHomeBannerMassageExtDto> qm) {
		PageModel<SysHomeBannerMassageExtDto> pm = new PageModel<SysHomeBannerMassageExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("productnamePk", qm.getEntity().getProductnamePk());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = sysHomeBannerMassageExtDao.searchGridCount(map);
		List<SysHomeBannerMassageExtDto> list = sysHomeBannerMassageExtDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateHomeBannerMessage(SysHomeBannerMassageExtDto dto) {
		int result = 0;
		dto.setUpdateTime(new Date());
		List<SysHomeBannerMassageDto> list = isExtistBannerMassage(dto);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			result = sysHomeBannerMassageExtDao.updateObj(dto);
		}
		return result;
	}

	private List<SysHomeBannerMassageDto> isExtistBannerMassage(SysHomeBannerMassageExtDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("url", dto.getUrl());
		map.put("type", dto.getType());
		map.put("linkUrl", dto.getLinkUrl());
		List<SysHomeBannerMassageDto> list = sysHomeBannerMassageDao.searchList(map);
		return list;
	}

	@Override
	public int updateIsVisAndDelMessageBanner(SysHomeBannerMassageExtDto dto) {
		// TODO Auto-generated method stub
		return sysHomeBannerMassageExtDao.updateObj(dto);
	}

	@Override
	public int insertHomeBannerMessage(SysHomeBannerMassage dto) {
		int result = 0;
		SysHomeBannerMassageExtDto extDto = new SysHomeBannerMassageExtDto();
		extDto.setProductnamePk(dto.getProductnamePk());
		extDto.setUrl(dto.getUrl());
		extDto.setType(dto.getType());
		extDto.setLinkUrl(dto.getLinkUrl());
		List<SysHomeBannerMassageDto> list = isExtistBannerMassage(extDto);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			dto.setPk(KeyUtils.getUUID());
			dto.setIsDelete(1);
			dto.setInsertTime(new Date());
			result = sysHomeBannerMassageDao.insert(dto);
		}
		return result;
	}

	@Override
	public List<SysHomeBannerProductnameDto> getAllSysBannerProductNameList() {
		return sysHomeBannerProductnameExtDao.getAllSysBannerProductNameList();
	}

	@Override
	public List<SysHomeBannerProductnameDto> getSysBannerProductNameToOtherList() {
		return sysHomeBannerProductnameExtDao.getSysBannerProductNameToOtherList();
	}

	@Override
	public int updateHomeBannerMassagenameSort(SysHomeBannerMassageDto dto) {
		return sysHomeBannerMassageExtDao.updateObj(dto);
	}

	@Override
	public List<B2bVarietiesDto> getB2bVarietiesPidList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("parentPk", -1);
		return b2bVarietiesDao.searchList(map);
	}

	@Override
	public List<B2bSpecDto> getb2bSpecByPid(String parentPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("parentPk", parentPk);
		return b2bSpecDao.searchGrid(map);
	}

	@Override
	public List<B2bSpecDto> getb2bSpecs() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("parentPk", -1);
		map.put("start", null);
		map.put("limit", null);
		return b2bSpecDao.searchGrid(map);
	}

	@Override
	public PageModel<SysHomeBannerVarietiesExtDto> searchHomeBannerVarietiesList(
			QueryModel<SysHomeBannerVarietiesExtDto> qm) {
		PageModel<SysHomeBannerVarietiesExtDto> pm = new PageModel<SysHomeBannerVarietiesExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("productnamePk", qm.getEntity().getProductnamePk());
		map.put("varietiesPk", qm.getEntity().getVarietiesPk());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("isDelete", Constants.ONE);
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = sysHomeBannerVarietiesDao.searchGridCount(map);
		List<SysHomeBannerVarietiesExtDto> list = sysHomeBannerVarietiesExtDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<SysHomeBannerSpecExtDto> searchHomeBannerSpecList(QueryModel<SysHomeBannerSpecExtDto> qm) {
		PageModel<SysHomeBannerSpecExtDto> pm = new PageModel<SysHomeBannerSpecExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("productnamePk", qm.getEntity().getProductnamePk());
		map.put("specPk", qm.getEntity().getSpecPk());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = sysHomeBannerSpecExtDao.searchGridExtCount(map);
		List<SysHomeBannerSpecExtDto> list = sysHomeBannerSpecExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<SysHomeBannerSeriesExtDto> searchHomeBannerSeriesList(QueryModel<SysHomeBannerSeriesExtDto> qm) {
		PageModel<SysHomeBannerSeriesExtDto> pm = new PageModel<SysHomeBannerSeriesExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("productnamePk", qm.getEntity().getProductnamePk());
		map.put("isDelete", Constants.ONE);
		map.put("specPk", qm.getEntity().getSpecPk());
		map.put("seriesPk", qm.getEntity().getSeriesPk());
		map.put("varietiesPk", qm.getEntity().getVarietiesPk());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = sysHomeBannerSeriesExtDao.searchCount(map);
		List<SysHomeBannerSeriesExtDto> list = sysHomeBannerSeriesExtDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateHomeBannerVarieties(SysHomeBannerVarietiesExtDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("varietiesPk", dto.getVarietiesPk());
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		List<SysHomeBannerVarietiesDto> list = sysHomeBannerVarietiesDao.searchList(map);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			dto.setUpdateTime(new Date());
			result = sysHomeBannerVarietiesExtDao.updateObj(dto);
		}
		return result;
	}

	@Override
	public int updateIsVisAndDelPgVarietiesBanner(SysHomeBannerVarietiesExtDto dto) {
		// TODO Auto-generated method stub
		return sysHomeBannerVarietiesExtDao.updateObj(dto);
	}

	@Override
	public int insertHomeBannerVarieties(SysHomeBannerVarieties dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("varietiesPk", dto.getVarietiesPk());
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		List<SysHomeBannerVarietiesDto> list = sysHomeBannerVarietiesDao.searchList(map);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			dto.setPk(KeyUtils.getUUID());
			dto.setIsDelete(1);
			dto.setInsertTime(new Date());
			result = sysHomeBannerVarietiesDao.insert(dto);
		}
		return result;
	}

	@Override
	public int updateHomeBannerSpec(SysHomeBannerSpecExtDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("specPk", dto.getSpecPk());
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		List<SysHomeBannerSpecDto> list = sysHomeBannerSpecDao.searchList(map);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			dto.setUpdateTime(new Date());
			result = sysHomeBannerSpecExtDao.updateObj(dto);
		}
		return result;
	}

	@Override
	public int updateIsVisAndDelHomePgSpecBanner(SysHomeBannerSpecExtDto dto) {
		// TODO Auto-generated method stub
		return sysHomeBannerSpecExtDao.updateObj(dto);
	}

	@Override
	public int insertHomeBannerSpec(SysHomeBannerSpec dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("specPk", dto.getSpecPk());
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		List<SysHomeBannerSpecDto> list = sysHomeBannerSpecDao.searchList(map);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			dto.setPk(KeyUtils.getUUID());
			dto.setIsDelete(1);
			dto.setInsertTime(new Date());
			result = sysHomeBannerSpecDao.insert(dto);
		}
		return result;
	}

	@Override
	public int updateHomeBannerSeries(SysHomeBannerSeriesExtDto dto) {
		int result = 0;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("specPk", dto.getSpecPk());
		map.put("varietiesPk", dto.getVarietiesPk());
		map.put("seriesPk", dto.getSeriesPk());
		map.put("isDelete", Constants.ONE);
		map.put("pk", dto.getPk());
		int count = sysHomeBannerSeriesExtDao.searchCount(map);
		if (count > 0) {
			result = 2;
		} else {
			dto.setUpdateTime(new Date());
			result = sysHomeBannerSeriesExtDao.updateObj(dto);
		}
		return result;
	}

	@Override
	public int updateIsVisAndDelHomePgSeriesBanner(SysHomeBannerSeriesExtDto dto) {
		// TODO Auto-generated method stub
		return sysHomeBannerSeriesExtDao.updateObj(dto);
	}

	@Override
	public int insertHomeBannerSeries(SysHomeBannerSeries dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", dto.getProductnamePk());
		map.put("specPk", dto.getSpecPk());
		map.put("varietiesPk", dto.getVarietiesPk());
		map.put("seriesPk", dto.getSeriesPk());
		map.put("isDelete", Constants.ONE);
		int count = sysHomeBannerSeriesExtDao.searchCount(map);
		if (count > 0) {
			result = 2;
		} else {
			dto.setPk(KeyUtils.getUUID());
			dto.setIsDelete(1);
			dto.setInsertTime(new Date());
			result = sysHomeBannerSeriesDao.insert(dto);
		}
		return result;
	}

	@Override
	public List<SysHomeBannerSpecExtDto> getSysHomeBannerSpecByProductNamePk(String productnamePk) {
		return sysHomeBannerSpecExtDao.getByProductnamePkExt(productnamePk);
	}

	@Override
	public SysHomeBannerSpecDto getSysHomeBannerSpecPk(String pk) {
		return sysHomeBannerSpecDao.getByPk(pk);
	}

	@Override
	public List<SysHomeBannerVarietiesDto> searchSysHomeBannerVarieties(String productnamePk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productnamePk", productnamePk);
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return sysHomeBannerVarietiesDao.searchList(map);
	}

	@Override
	public PageModel<B2bKeywordHotDto> searchB2bKeywordHot(QueryModel<B2bKeywordHotExtDto> qm) {
		PageModel<B2bKeywordHotDto> pm = new PageModel<B2bKeywordHotDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		map.put("status", qm.getEntity().getStatus());
		map.put("name", qm.getEntity().getName());
		map.put("insertTimeBegin", qm.getEntity().getInsertTimeBegin());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = b2bKeywordHotDao.searchGridCount(map);
		List<B2bKeywordHotDto> list = b2bKeywordHotDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateB2bKeywordHot(B2bKeywordHot keywordHot) {
		int result = 0;
		if (null == keywordHot.getPk() || "".equals(keywordHot.getPk())) {
			keywordHot.setPk(KeyUtils.getUUID());
			keywordHot.setIsDelete(1);
			keywordHot.setInsertTime(new Date());
			result = b2bKeywordHotDao.insert(keywordHot);
		} else {
			result = b2bKeywordHotDao.update(keywordHot);
		}
		return result;
	}

	@Override
	public PageModel<B2bFriendLinkDto> searchB2bFriendLink(QueryModel<B2bFriendLinkExtDto> qm) {
		PageModel<B2bFriendLinkDto> pm = new PageModel<B2bFriendLinkDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		map.put("status", qm.getEntity().getStatus());
		map.put("name", qm.getEntity().getName());
		map.put("insertTimeBegin", qm.getEntity().getInsertTimeBegin());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = b2bFriendLinkDao.searchGridCount(map);
		List<B2bFriendLinkDto> list = b2bFriendLinkDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateB2bFriendLink(B2bFriendLink link) {
		int result = 0;
		if (null == link.getPk() || "".equals(link.getPk())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isDelete", 1);
			map.put("name", link.getName());
			List<B2bFriendLinkDto> list = b2bFriendLinkDao.searchList(map);
			if (null != list && list.size() > 0) {
				result = 2;
			} else {
				link.setPk(KeyUtils.getUUID());
				link.setIsDelete(1);
				link.setInsertTime(new Date());
				result = b2bFriendLinkDao.insert(link);
			}
		} else {
			result = b2bFriendLinkDao.update(link);
		}
		return result;
	}

	@Override
	public PageModel<B2bPriceMovementExtDto> searchPriceTrendList(QueryModel<B2bPriceMovementExtDto> qm) {
		PageModel<B2bPriceMovementExtDto> pm = new PageModel<B2bPriceMovementExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		map.put("productName", qm.getEntity().getProductName());
		map.put("varietiesName", qm.getEntity().getVarietiesName());
		map.put("specName", qm.getEntity().getSpecName());
		map.put("seriesName", qm.getEntity().getSeriesName());
		map.put("specifications", qm.getEntity().getSpecifications());
		map.put("batchNumber", qm.getEntity().getBatchNumber());
		map.put("brandName", qm.getEntity().getBrandName());
		map.put("technologyPk", qm.getEntity().getTechnologyName());
		map.put("rawMaterialPk", qm.getEntity().getSecondMaterialName());
		map.put("rawMaterialParentPk", qm.getEntity().getFirstMaterialName());
		map.put("platformUpdateTimeStart", qm.getEntity().getPlatformUpdateTimeStart());
		map.put("platformUpdateTimeEnd", qm.getEntity().getPlatformUpdateTimeEnd());
		map.put("updateTimeStart", qm.getEntity().getUpdateTimeStart());
		map.put("updateTimeEnd", qm.getEntity().getUpdateTimeEnd());
		map.put("dateStart", qm.getEntity().getDateStart());
		map.put("dateEnd", qm.getEntity().getDateEnd());
		map.put("isHome", qm.getEntity().getIsHome());
		String block = qm.getEntity().getBlock();
		map.put("block", block);
		int totalCount = b2bPriceMovementExtDao.searchGridCountExt(map);
		List<B2bPriceMovementExtDto> list = b2bPriceMovementExtDao.searchGridExt(map);
		if (list != null && list.size() > 0){
			for (B2bPriceMovementExtDto extDto:list) {
				String goodsInfo = extDto.getGoodsInfo();
					if (goodsInfo != null && !"".equals(goodsInfo)){
						if (Constants.BLOCK_CF.equals(block)){
							CfGoods cf = JSON.parseObject(goodsInfo,CfGoods.class);
							if (cf != null) {
								extDto.setProductName(cf.getProductName());
								extDto.setVarietiesName(cf.getVarietiesName());
								extDto.setSpecName(cf.getSpecName());
								extDto.setSpecifications(cf.getSpecifications());
								extDto.setBatchNumber(cf.getBatchNumber());
								extDto.setGradeName(cf.getGradeName());
								extDto.setSeriesName(cf.getSeriesName());
							}
					}
						if (Constants.BLOCK_SX.equals(block)){
							SxGoods sx = JSON.parseObject(goodsInfo, SxGoods.class);
							if (sx != null) {
								extDto.setTechnologyName(sx.getTechnologyName());
								extDto.setSecondMaterialName(sx.getRawMaterialName());//二级原料
								extDto.setFirstMaterialName(sx.getRawMaterialParentName());//一级原料
								extDto.setSpecifications(sx.getSpecName());
							}
						}
				}
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateB2bPriceMovement(B2bPriceMovementExtDto dto) {
			int result = 0;
			dto.setUpdateTime(new Date());
			result = b2bPriceMovementExtDao.updatePriceMovement(dto);
			//同时删除mongo数据
			if(dto.getIsDelete() != null && dto.getIsDelete() == 2){
				Query query = new Query();
				query.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("movementPk").is(dto.getPk()));
				mongoTemplate.remove(query, B2bPriceMovementEntity.class);
			}
		return result;
	}

	@Override
	public int updateB2bPriceMovementEntity(B2bPriceMovementEntity dto) {
		int result = 0;
		Query query = new Query();
		query.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("date").is(dto.getDate()));
		query.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> list = mongoTemplate.find(query, B2bPriceMovementEntity.class);
		if (list != null && list.size() > 0) {
			//编辑历史数据
			result = setCfToMongo(dto, result, list);
		}
		return result;
	}

	@Override
	public int updateSxPriceMovementEntity(SxPriceMovementEntity dto) {
		int result = 0;
		Query query = new Query();
		query.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("date").is(dto.getDate()));
		query.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> list = mongoTemplate.find(query, SxPriceMovementEntity.class);
		if (list != null && list.size() > 0) {
			result = setSxToMongo(dto, result, list);
		}
		return result;
	}

	@Override
	public int isShowB2bPriceMovementEntity(B2bPriceMovementEntity dto) {
		int result = 0;
		try {
			Query querys = Query
					.query(Criteria.where("_id").is(dto.getId()));
			Update update = new Update();
			update.set("isShow", dto.getIsShow());
			mongoTemplate.updateMulti(querys, update, B2bPriceMovementEntity.class);
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}

		//如果修改显示不显示的数据时间前面有一条数据，
		// 判断价格下降的时候要把昨日价格更新为当前之前一条数据，以便折线图查看直观
		//判断较上次价格起伏，用于前端显示
		Query queryPrice = Query
				.query(Criteria.where("movementPk").is(dto.getMovementPk()).and("date")
						.gt(dto.getDate()));
		queryPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> listPrice = mongoTemplate.find(queryPrice, B2bPriceMovementEntity.class);

		Query afterPrice = Query
				.query(Criteria.where("movementPk").is(dto.getMovementPk()).and("date")
						.lt(dto.getDate()));
		afterPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> afterList = mongoTemplate.find(afterPrice, B2bPriceMovementEntity.class);
		if (dto.getIsShow() != null){
			if (dto.getIsShow() == Constants.TWO){
				isUnShowPrice(dto, listPrice, afterList);
			}else{
				isShowPrice(dto, listPrice, afterList);
			}
		}
		return result;
	}

	private void isUnShowPrice(B2bPriceMovementEntity dto, List<B2bPriceMovementEntity> listPrice, List<B2bPriceMovementEntity> afterList) {
//		if (listPrice != null && listPrice.size() == 1){
//			//更新为本条数据隐藏之后上一条数据的价格
//			if (afterList != null && afterList.size() > 0){
//				B2bPriceMovementEntity afterPri = afterList.get(0);
//				B2bPriceMovementExtDto movement = new B2bPriceMovementExtDto();
//				movement.setPk(dto.getMovementPk());
//				movement.setYesterdayPrice(afterPri.getPrice());
//				b2bPriceMovementExtDao.updatePriceMovement(movement);
//			}
//		}
		//判断当最新的一条被隐藏之后，要更新当前价格，取最新价格之前最近的一条价格更新
		if (listPrice != null && listPrice.size() < 1){//当前数据为最新一条
			if (afterList != null && afterList.size() > 0){
				B2bPriceMovementEntity afterPri = afterList.get(0);
				B2bPriceMovementExtDto movement = new B2bPriceMovementExtDto();
				movement.setPk(dto.getMovementPk());
				movement.setPrice(afterPri.getPrice());
				b2bPriceMovementExtDao.updatePriceMovement(movement);
			}
		}
	}

	private void isShowPrice(B2bPriceMovementEntity dto, List<B2bPriceMovementEntity> listPrice, List<B2bPriceMovementEntity> afterList) {
//		if (listPrice != null && listPrice.size() == 1){
//				B2bPriceMovementExtDto movement = new B2bPriceMovementExtDto();
//				movement.setPk(dto.getMovementPk());
//				movement.setYesterdayPrice(dto.getPrice());
//				b2bPriceMovementExtDao.updatePriceMovement(movement);
//		}

		if (listPrice != null && listPrice.size() < 1){//当前数据为最新一条
				B2bPriceMovementExtDto movement = new B2bPriceMovementExtDto();
				movement.setPk(dto.getMovementPk());
				movement.setPrice(dto.getPrice());
				b2bPriceMovementExtDao.updatePriceMovement(movement);
		}
	}

	@Override
	public int isShowSxPriceMovementEntity(SxPriceMovementEntity dto) {
		int result = 0;

		try {
			Query querys = Query
					.query(Criteria.where("_id").is(dto.getId()));
			Update update = new Update();
			update.set("isShow", dto.getIsShow());
			mongoTemplate.updateMulti(querys, update, SxPriceMovementEntity.class);
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}

		//如果修改显示不显示的数据时间前面有一条数据，
		// 判断价格下降的时候要把昨日价格更新为当前之前一条数据，以便折线图查看直观
		Query queryPrice = Query
				.query(Criteria.where("movementPk").is(dto.getMovementPk()).and("date")
						.gt(dto.getDate()));
		queryPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> listPrice = mongoTemplate.find(queryPrice, SxPriceMovementEntity.class);

		Query afterPrice = Query
				.query(Criteria.where("movementPk").is(dto.getMovementPk()).and("date")
						.lt(dto.getDate()));
		afterPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> afterList = mongoTemplate.find(afterPrice, SxPriceMovementEntity.class);
		if(dto.getIsShow() != null){
			if (dto.getIsShow() == Constants.TWO){
				isUnSxShowPrice(dto, listPrice, afterList);
			}else{
				isSxShowPrice(dto, listPrice, afterList);
			}
		}
		return result;
	}

	private void isUnSxShowPrice(SxPriceMovementEntity dto, List<SxPriceMovementEntity> listPrice, List<SxPriceMovementEntity> afterList) {

		//判断当最新的一条被隐藏之后，要更新当前价格，取最新价格之前最近的一条价格更新
		if (listPrice != null && listPrice.size() < 1){
			if (afterList != null && afterList.size() > 0){
				SxPriceMovementEntity afterPri = afterList.get(0);
				B2bPriceMovementExtDto movement = new B2bPriceMovementExtDto();
				movement.setPk(dto.getMovementPk());
				movement.setPrice(afterPri.getPrice());
				b2bPriceMovementExtDao.updatePriceMovement(movement);
			}
		}
	}

	private void isSxShowPrice(SxPriceMovementEntity dto, List<SxPriceMovementEntity> listPrice, List<SxPriceMovementEntity> afterList) {

		//如果展示本条数据的时候，判断前面没有数据price修改成本条数据的price，及最新的数据当前数据
		if (listPrice != null && listPrice.size() < 1){
				B2bPriceMovementExtDto movement = new B2bPriceMovementExtDto();
				movement.setPk(dto.getMovementPk());
				movement.setPrice(dto.getPrice());
				b2bPriceMovementExtDao.updatePriceMovement(movement);
		}
	}

	private int setCfToMongo(B2bPriceMovementEntity dto, int result, List<B2bPriceMovementEntity> list) {
		B2bPriceMovementEntity movementEntity = list.get(0);
		movementEntity.setPrice(dto.getPrice());
		B2bPriceMovementDto movementDto = b2bPriceMovementExtDao.getByPk(dto.getMovementPk());
		if (movementDto != null) {// yesterdayPrice取最新的价格趋势时间的上一条数据（第二新数据）保存到yesterdayPrice
			Query queryPrice = new Query();
			queryPrice.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("date")
					.lt(movementDto.getDate().toString()));
			queryPrice.with(new Sort(new Order(Direction.DESC, "date")));
			List<B2bPriceMovementEntity> listPrice = mongoTemplate.find(queryPrice, B2bPriceMovementEntity.class);
			if (listPrice != null && listPrice.size() > 0) {
				B2bPriceMovementEntity entityPrice = listPrice.get(0);
				movementEntity.setYesterdayPrice(entityPrice.getPrice());
			} else {
				movementEntity.setYesterdayPrice(0d);
			}
			//修改最新成最新数据
			updatePriceMovement(movementDto, dto.getDate(), dto.getPrice());
		}
		try {
			mongoTemplate.save(movementEntity);
			result = 1;
		} catch (Exception e) {
		}
		return result;
	}

	private void updatePriceMovement(B2bPriceMovementDto movementDto, String date, Double price) {
		Date movementDate = movementDto.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date mongoDate;
		try {
			mongoDate = sdf.parse(date);
			if (mongoDate.compareTo(movementDate) > 0 || mongoDate.compareTo(movementDate) == 0) {
				B2bPriceMovementExtDto extDto = new B2bPriceMovementExtDto();
				extDto.setPk(movementDto.getPk());
				extDto.setPrice(price);
				extDto.setDate(mongoDate);
				b2bPriceMovementExtDao.updatePriceMovement(extDto);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private int setSxToMongo(SxPriceMovementEntity dto, int result, List<SxPriceMovementEntity> list) {
		SxPriceMovementEntity movementEntity = list.get(0);
		movementEntity.setPrice(dto.getPrice());
		B2bPriceMovementDto movementDto = b2bPriceMovementExtDao.getByPk(dto.getMovementPk());
		if (movementDto != null) {// yesterdayPrice取最新的价格趋势时间的上一条数据（第二新数据）保存到yesterdayPrice
			Query queryPrice = new Query();
			queryPrice.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("date")
					.lt(movementDto.getDate().toString()));
			queryPrice.with(new Sort(new Order(Direction.DESC, "date")));
			List<SxPriceMovementEntity> listPrice = mongoTemplate.find(queryPrice, SxPriceMovementEntity.class);
			if (listPrice != null && listPrice.size() > 0) {
				SxPriceMovementEntity entityPrice = listPrice.get(0);
				movementEntity.setYesterdayPrice(entityPrice.getPrice());
			} else {
				movementEntity.setYesterdayPrice(0d);
			}
			//修改最新成最新数据
			updatePriceMovement(movementDto, dto.getDate(), dto.getPrice());
		}
		try {
			mongoTemplate.save(movementEntity);
			result = 1;
		} catch (Exception e) {
		}
		return result;
	}

	
	@Override
	public int deleteB2bPriceMovementEntity(B2bPriceMovementEntity dto) {
		int result = 0;
		
		Query queryCount = new Query();
		queryCount.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("movementPk").is(dto.getMovementPk()));
		long count = mongoTemplate.count(queryCount, B2bPriceMovementEntity.class);
		//如果只有一条历史记录删除的时候连同数据库中的数据删除
		if(count == 1){
			b2bPriceMovementExtDao.delete(dto.getMovementPk());
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(dto.getId()));
		try {
			mongoTemplate.remove(query, B2bPriceMovementEntity.class);
			result = 1;
		} catch (Exception e) {
			
		}
		return result;
	}

	@Override
	public int deleteSxPriceMovementEntity(SxPriceMovementEntity dto) {
		int result = 0;
		Query queryCount = new Query();
		queryCount.addCriteria(Criteria.where("goodsPk").is(dto.getGoodsPk()).and("movementPk").is(dto.getMovementPk()));
		long count = mongoTemplate.count(queryCount, SxPriceMovementEntity.class);
		//如果只有一条历史记录删除的时候连同数据库中的数据删除
		if(count == 1){
			b2bPriceMovementExtDao.delete(dto.getMovementPk());
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(dto.getId()));
		try {
			mongoTemplate.remove(query, SxPriceMovementEntity.class);
			result = 1;
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public int insertB2bPriceMovementEntity(B2bPriceMovementEntity entity) {
		int result = 0;
		B2bPriceMovementDto movementDto = b2bPriceMovementExtDao.getByPk(entity.getMovementPk());
		//首先判断数据库中最新的数据日期是否小于或等于今天。
		if(movementDto != null){
			Date movementDate = movementDto.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date mongoDate = sdf.parse(entity.getDate());
				if(mongoDate.compareTo(movementDate) > 0 || mongoDate.compareTo(movementDate) == 0){
					//修改原来最新的数据
					result = saveToDB(entity, movementDto, sdf);
					//保存到mongo
					saveToMongo(entity,entity.getDate());
				} else{
					//保存到mongo
					saveToMongo(entity,entity.getDate());
					result = 1;
				}
				//更新 当前记录(entity)的 yesterdayPrice
				updateCurrentYesterDayPrice(entity);
				updatePreYesterDayPrice(entity);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int insertSxPriceMovementEntity(SxPriceMovementEntity dto) {
		int result = 0;
		B2bPriceMovementDto movementDto = b2bPriceMovementExtDao.getByPk(dto.getMovementPk());
		//首先判断数据库中最新的数据日期是否小于或等于今天。
		if(movementDto != null){
			Date movementDate = movementDto.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date mongoDate = sdf.parse(dto.getDate());
				if(mongoDate.compareTo(movementDate) > 0 || mongoDate.compareTo(movementDate) == 0){
					//修改原来最新的数据
					result = saveSxToDB(dto, movementDto, sdf);
					//保存到mongo
					saveSxToMongo(dto,dto.getDate());
				} else{
					//保存到mongo
					saveSxToMongo(dto,dto.getDate());
					result = 1;
				}
				//更新 当前记录(entity)的 yesterdayPrice
				updateSxCurrentYesterDayPrice(dto);
				updateSxPreYesterDayPrice(dto);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private B2bPriceMovementEntity queryCurrentPre(B2bPriceMovementEntity entity){
		
		
		B2bPriceMovementEntity pre=null;
		Query queryPrePrice = new Query();
		queryPrePrice.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.gt(entity.getDate()));
		queryPrePrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> listPrePrice = mongoTemplate.find(queryPrePrice, B2bPriceMovementEntity.class);
		if(null!=listPrePrice && listPrePrice.size()>0){
			pre=listPrePrice.get(listPrePrice.size()-1);
		}
		return pre;
	}

	private SxPriceMovementEntity querySxCurrentPre(SxPriceMovementEntity entity){

		SxPriceMovementEntity pre=null;
		Query queryPrePrice = new Query();
		queryPrePrice.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.gt(entity.getDate()));
		queryPrePrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> listPrePrice = mongoTemplate.find(queryPrePrice, SxPriceMovementEntity.class);
		if(null!=listPrePrice && listPrePrice.size()>0){
			pre=listPrePrice.get(listPrePrice.size()-1);
		}
		return pre;
	}
	
	private B2bPriceMovementEntity queryCurrentNext(B2bPriceMovementEntity entity){
		B2bPriceMovementEntity next=null;
		
		Query queryNextPrice = new Query();
		queryNextPrice.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.lt(entity.getDate()));
		queryNextPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> listNextPrice = mongoTemplate.find(queryNextPrice, B2bPriceMovementEntity.class);
		
		if(null!=listNextPrice && listNextPrice.size()>0){
			next=listNextPrice.get(0);
		}
		return next;
	}

	private SxPriceMovementEntity querySxCurrentNext(SxPriceMovementEntity entity){
		SxPriceMovementEntity next=null;

		Query queryNextPrice = new Query();
		queryNextPrice.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.lt(entity.getDate()));
		queryNextPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> listNextPrice = mongoTemplate.find(queryNextPrice, SxPriceMovementEntity.class);

		if(null!=listNextPrice && listNextPrice.size()>0){
			next=listNextPrice.get(0);
		}
		return next;
	}
	
	private void updatePreYesterDayPrice(B2bPriceMovementEntity entity){
		
        B2bPriceMovementEntity pre=queryCurrentPre(entity);
        B2bPriceMovementDto b2bPriceMovementDto=null;
		
		B2bPriceMovementExtDto extDto = new B2bPriceMovementExtDto();
		
		if(null!=pre){
			 b2bPriceMovementDto=b2bPriceMovementExtDao.getByPk(pre.getMovementPk());
		}


		//前一条是否等于数据库里最新的那一条
		if(null!=b2bPriceMovementDto){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String last=sdf.format(b2bPriceMovementDto.getDate());
			if(null!=pre && pre.getDate().equals(last)){
				extDto.setPk(pre.getMovementPk());
				extDto.setYesterdayPrice(entity.getPrice());
				b2bPriceMovementExtDao.updatePriceMovement(extDto);
			}
		}

	}

	private void updateSxPreYesterDayPrice(SxPriceMovementEntity entity){

		SxPriceMovementEntity pre=querySxCurrentPre(entity);
		B2bPriceMovementDto b2bPriceMovementDto=null;

		B2bPriceMovementExtDto extDto = new B2bPriceMovementExtDto();

		if(null!=pre){
			b2bPriceMovementDto=b2bPriceMovementExtDao.getByPk(pre.getMovementPk());
		}

		//前一条是否等于数据库里最新的那一条
		if(null!=b2bPriceMovementDto){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String last=sdf.format(b2bPriceMovementDto.getDate());
			if(null!=pre && pre.getDate().equals(last)){
				extDto.setPk(pre.getMovementPk());
				extDto.setYesterdayPrice(entity.getPrice());
				b2bPriceMovementExtDao.updatePriceMovement(extDto);
			}
		}

	}

	private void updateCurrentYesterDayPrice(B2bPriceMovementEntity entity) {
		B2bPriceMovementEntity next=queryCurrentNext(entity);

		B2bPriceMovementExtDto extDto = new B2bPriceMovementExtDto();
		extDto.setPk(entity.getMovementPk());
		if(null!=next){
			extDto.setYesterdayPrice(next.getPrice());
			b2bPriceMovementExtDao.updatePriceMovement(extDto);
		}
	}

	private void updateSxCurrentYesterDayPrice(SxPriceMovementEntity entity) {
		SxPriceMovementEntity next=querySxCurrentNext(entity);

		B2bPriceMovementExtDto extDto = new B2bPriceMovementExtDto();
		extDto.setPk(entity.getMovementPk());
		if(null!=next){

			if (next.getPrice() == null){
				extDto.setYesterdayPrice(0d);
			}else{
				extDto.setYesterdayPrice(next.getPrice());
			}
			b2bPriceMovementExtDao.updatePriceMovement(extDto);
		}
	}

	private void saveToMongo(B2bPriceMovementEntity entity,String date) {
		Query queryPrice = new Query();
		queryPrice.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.lt(date));
		queryPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> listPrice = mongoTemplate.find(queryPrice, B2bPriceMovementEntity.class);
		B2bGoodsDto goodsDto = b2bGoodsExtDao.getByPk(entity.getGoodsPk());

		if(goodsDto != null){
			CfGoods cf = JSON.parseObject(goodsDto.getGoodsInfo(),CfGoods.class);
			if (cf != null){
				entity.setVarietiesName(cf.getVarietiesName());
				entity.setSpecName(cf.getSpecName());
				entity.setSeriesName(cf.getSeriesName());
				entity.setSpecifications(cf.getSpecifications());
				entity.setBatchNumber(cf.getBatchNumber());
				entity.setProductName(cf.getProductName());
				entity.setGradeName(cf.getGradeName());
			}
		}
		entity.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		entity.setDate(entity.getDate());
		entity.setBrandName(goodsDto.getBrandName());
		entity.setGoodsPk(goodsDto.getPk());
		entity.setTonPrice(goodsDto.getTonPrice());
		
		if (listPrice != null && listPrice.size() > 0) {
			B2bPriceMovementEntity entityPrice = listPrice.get(0);
			entity.setYesterdayPrice(entityPrice.getPrice());
		} else {
			entity.setYesterdayPrice(0d);
		}
		
		//查询是否是存在当前时间的数据
		Query query = new Query();
		query.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.is(date));
		query.with(new Sort(new Order(Direction.DESC, "date")));
		List<B2bPriceMovementEntity> list = mongoTemplate.find(query, B2bPriceMovementEntity.class);
		if (list != null && list.size() > 0) {
			B2bPriceMovementEntity entityObj = list.get(0);
			if(entityObj.getDate().equals(entity.getDate())){
				entity.setId(entityObj.getId());
			}else{
				entity.setId(KeyUtils.getUUID());
			}
		}
		mongoTemplate.save(entity);
	}

	private void saveSxToMongo(SxPriceMovementEntity entity,String date) {
		Query queryPrice = new Query();
		queryPrice.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.lt(date));
		queryPrice.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> listPrice = mongoTemplate.find(queryPrice, SxPriceMovementEntity.class);
		entity.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		entity.setDate(entity.getDate());

		B2bGoodsDto goodsDto = b2bGoodsExtDao.getByPk(entity.getGoodsPk());

		if (goodsDto != null){
			SxGoods sx = JSON.parseObject(goodsDto.getGoodsInfo(),SxGoods.class);
			if (sx != null){
				entity.setTechnologyName(sx.getTechnologyName());
				entity.setBrandName(goodsDto.getBrandName());
				entity.setFirstMaterialName(sx.getRawMaterialParentName());
				entity.setSecondMaterialName(sx.getRawMaterialName());
				entity.setSpecifications(sx.getSpecName());
				entity.setGoodsPk(goodsDto.getPk());
				entity.setYarnPrice(goodsDto.getPrice());
			}
		}
		if (listPrice != null && listPrice.size() > 0) {
			SxPriceMovementEntity entityPrice = listPrice.get(0);
			entity.setYesterdayPrice(entityPrice.getPrice());
		} else {
			entity.setYesterdayPrice(0d);
		}

		//查询是否是存在当前时间的数据
		Query query = new Query();
		query.addCriteria(Criteria.where("goodsPk").is(entity.getGoodsPk()).and("date")
				.is(date));
		query.with(new Sort(new Order(Direction.DESC, "date")));
		List<SxPriceMovementEntity> list = mongoTemplate.find(query, SxPriceMovementEntity.class);
		if (list != null && list.size() > 0) {
			SxPriceMovementEntity entityObj = list.get(0);
			if(entityObj.getDate().equals(entity.getDate())){
				entity.setId(entityObj.getId());
			}else{
				entity.setId(KeyUtils.getUUID());
			}
		}
		mongoTemplate.save(entity);
	}

	private int saveToDB(B2bPriceMovementEntity entity, B2bPriceMovementDto movementDto, SimpleDateFormat sdf)
			throws ParseException {
		int result = getToDBResult(movementDto, sdf, entity.getPrice(), entity.getDate());
		return result;
	}

	private int saveSxToDB(SxPriceMovementEntity entity, B2bPriceMovementDto movementDto, SimpleDateFormat sdf)
			throws ParseException {
		int result = getToDBResult(movementDto, sdf, entity.getPrice(), entity.getDate());
		return result;
	}
	private int getToDBResult(B2bPriceMovementDto movementDto, SimpleDateFormat sdf, Double price, String date) throws ParseException {
		int result;
		B2bPriceMovementExtDto extDto = new B2bPriceMovementExtDto();
		extDto.setPk(movementDto.getPk());
		extDto.setPrice(price);
		extDto.setDate(sdf.parse(date));
		result = b2bPriceMovementExtDao.updatePriceMovement(extDto);
		return result;
	}

	@Override
	public List<B2bPriceMovementExtDto> exportPriceTrendList(B2bPriceMovementExtDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		setExportParams(dto, map);
		List<B2bPriceMovementExtDto>  list = b2bPriceMovementExtDao.searchGridExt(map);
		if (list.size()>0) {
				for (B2bPriceMovementExtDto extDto : list) {
					String goodsInfo = extDto.getGoodsInfo();
					if (goodsInfo != null && !"".equals(goodsInfo)){
						if (Constants.BLOCK_CF.equals(dto.getBlock())){
							CfGoods cf = JSON.parseObject(goodsInfo,CfGoods.class);
							if (cf != null) {
								extDto.setProductName(cf.getProductName());
								extDto.setVarietiesName(cf.getVarietiesName());
								extDto.setSpecName(cf.getSpecName());
								extDto.setSpecifications(cf.getSpecifications());
								extDto.setBatchNumber(cf.getBatchNumber());
								extDto.setGradeName(cf.getGradeName());
								extDto.setSeriesName(cf.getSeriesName());
							}
						}
						if (Constants.BLOCK_SX.equals(dto.getBlock())){
							SxGoods sx = JSON.parseObject(goodsInfo, SxGoods.class);
							if (sx != null) {
								extDto.setTechnologyName(sx.getTechnologyName());
								extDto.setFirstMaterialName(sx.getRawMaterialParentName());
                                extDto.setSecondMaterialName(sx.getRawMaterialName());
								extDto.setSpecifications(sx.getSpecName());
							}
						}
				}
			}
				
		}
		return  list;
	}

	@Override
	public int savePriceTrendExcelToOss(GoodsDataTypeParams params,ManageAccount account) {
		Date time = new Date();
		String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
		try {
			Map<String, String> map = CommonUtil.checkExportTime(params.getPlatformUpdateTimeStart(), params.getPlatformUpdateTimeEnd(), timeStr);
			params.setPlatformUpdateTimeStart(map.get("startTime"));
			params.setPlatformUpdateTimeEnd(map.get("endTime"));

			Map<String, String> updateMap = CommonUtil.checkExportTime(params.getUpdateTimeStart(), params.getUpdateTimeEnd(), timeStr);
			params.setUpdateTimeStart(updateMap.get("startTime"));
			params.setUpdateTimeEnd(updateMap.get("endTime"));

			Map<String, String> dateMap = CommonUtil.checkExportTime(params.getDateStart(), params.getDateEnd(), timeStr);
			params.setDateStart(dateMap.get("startTime"));
			params.setDateEnd(dateMap.get("endTime"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportPriceTrend_"+params.getUuid());
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(time);
		store.setParamsName(ExportDoJsonParams.doPriceTreandRunnableParams(params,time));
		if (Constants.BLOCK_CF.equals(params.getBlock())){
			store.setName("化纤中心-运营管理-首页显示管理-价格趋势");
		}else{
			store.setName("纱线中心-运营管理-首页显示管理-价格趋势");
		}
		store.setType(Constants.THREE);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	private void setExportParams(B2bPriceMovementExtDto dto, Map<String, Object> map) {
		map.put("isDelete", Constants.ONE);
		map.put("productName", dto.getProductName());
		map.put("varietiesName", dto.getVarietiesName());
		map.put("specName", dto.getSpecName());
		map.put("seriesName", dto.getSeriesName());
		map.put("specifications", dto.getSpecifications());
		map.put("batchNumber", dto.getBatchNumber());
		map.put("brandName", dto.getBrandName());
		map.put("block", dto.getBlock());
		map.put("platformUpdateTimeStart", dto.getPlatformUpdateTimeStart());
		map.put("platformUpdateTimeEnd", dto.getPlatformUpdateTimeEnd());
		map.put("updateTimeStart", dto.getUpdateTimeStart());
		map.put("updateTimeEnd", dto.getUpdateTimeEnd());
		map.put("dateStart", dto.getDateStart());
		map.put("dateEnd", dto.getDateEnd());
		map.put("isHome", dto.getIsHome());
	}


	@Override
	public PageModel<B2bPriceMovementExtDto> searchEditPriceTrendList(QueryModel<B2bPriceMovementExtDto> qm) {
		PageModel<B2bPriceMovementExtDto> pm = new PageModel<B2bPriceMovementExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		String block = qm.getEntity().getBlock();
		map.put("block", block);
		map.put("goodsInfo", qm.getEntity().getGoodsInfo());
		List<B2bPriceMovementExtDto> list = b2bPriceMovementExtDao.searchGridExt(map);
		int totalCount = b2bPriceMovementExtDao.searchGridCountExt(map);
		if (list != null && list.size() > 0) {
			for (B2bPriceMovementExtDto dto : list) {

				String goodsInfo = dto.getGoodsInfo();
				String info = "";
				if (Constants.BLOCK_CF.equals(block)){
					if (goodsInfo != null && !"".equals(goodsInfo)){
						CfGoods cfGoods = JSON.parseObject(goodsInfo, CfGoods.class);
						String productName = cfGoods.getProductName() == null?"":cfGoods.getProductName();
						String varietiesName = cfGoods.getVarietiesName() == null?"":cfGoods.getVarietiesName();
						String brandName = dto.getBrandName() == null?"":dto.getBrandName();
						String specName = cfGoods.getSpecName() == null?"":cfGoods.getSpecName();
						String specifications = cfGoods.getSpecifications() == null?"":cfGoods.getSpecifications();
						String batchNumber = cfGoods.getBatchNumber() == null?"":cfGoods.getBatchNumber();
						String gradeName = cfGoods.getGradeName() == null?"":cfGoods.getGradeName();
						info = productName + " " + varietiesName + " " + brandName + " "
								+ specName + " " + specifications + " " + batchNumber + " "
								+ gradeName;
					}
				}

				if (Constants.BLOCK_SX.equals(block)){
					if (goodsInfo != null && !"".equals(goodsInfo)){
						SxGoods sxGoods = JSON.parseObject(goodsInfo, SxGoods.class);
						String technologyName = CommonUtil.isNullReturnString(sxGoods.getTechnologyName());
						String rawMaterialName = CommonUtil.isNullReturnString(sxGoods.getRawMaterialName());
						String rawMaterialParentName = CommonUtil.isNullReturnString(sxGoods.getRawMaterialParentName());
						String specName = CommonUtil.isNullReturnString(sxGoods.getSpecName());
						info = technologyName + " "+ rawMaterialParentName +" "+ " " + rawMaterialName+" "+ specName;
					}
				}
				dto.setGoodsInfo(info);
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public String insertGoodsToPriceTrendList(String pk) {

		return insertPriceTrend(pk,Constants.BLOCK_CF);
	}

	@Override
	public String insertSxGoodsToPriceTrendList(String pk) {
		return insertPriceTrend(pk,Constants.BLOCK_SX);
	}

	private String insertPriceTrend(String pk, String block) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsPk", pk);
		map.put("isDelete", Constants.ONE);
		map.put("block", block);
		String msg = Constants.RESULT_FAIL_MSG;
		List<B2bPriceMovementDto> list = b2bPriceMovementExtDao.searchList(map);
		if (list != null && list.size() > 0) {
			msg = "{\"success\":false,\"msg\":\"当前添加的商品已存在!\"}";
		} else {
			B2bPriceMovement movement = new B2bPriceMovement();
			String movementPk =  KeyUtils.getUUID();
			movement.setPk(movementPk);
			movement.setGoodsPk(pk);
			movement.setIsDelete(Constants.ONE);
			movement.setDate(new Date());
			movement.setIsHome(Constants.ONE);
			movement.setSort(Constants.ONE);
			movement.setInsertTime(new Date());
			movement.setBlock(block);
			int result = b2bPriceMovementExtDao.insert(movement);
			if (result > 0) {//保存mongo
				Date date = new Date();
				//保存到化纤对应mongo对象
				if (Constants.BLOCK_CF.equals(block)) {
					B2bGoodsDto goodsDto = b2bGoodsExtDao.getByPk(pk);
					if(goodsDto != null){
						B2bPriceMovementEntity movementEntity = setInsertCfMongo(movementPk, goodsDto,date);
						mongoTemplate.save(movementEntity);
					}
				}
				//保存到化纤对应mongo对象
				if (Constants.BLOCK_SX.equals(block)) {
					B2bGoodsDto sxGoods = b2bGoodsExtDao.getByPk(pk);
					if (sxGoods != null){
						SxPriceMovementEntity sxMongo = setInsertSxMongo(movementPk,sxGoods,date);
						mongoTemplate.save(sxMongo);
					}
				}
				msg = Constants.RESULT_SUCCESS_MSG;
			}
		}
		return msg;
	}

	private B2bPriceMovementEntity setInsertCfMongo(String movementPk, B2bGoodsDto goodsDto, Date date) {
		B2bPriceMovementEntity movementEntity = new B2bPriceMovementEntity();
		movementEntity.setId(KeyUtils.getUUID());
		movementEntity.setInsertTime(DateUtil.formatDateAndTime(date));
		movementEntity.setDate(DateUtil.formatYearMonthDay(date));
		movementEntity.setBrandName(goodsDto.getBrandName());
		CfGoods cfGoods = JSON.parseObject(goodsDto.getGoodsInfo(), CfGoods.class);
		if(cfGoods != null) {
			movementEntity.setProductName(cfGoods.getProductName());
			movementEntity.setBatchNumber(cfGoods.getBatchNumber());
			movementEntity.setSeriesName(cfGoods.getSeriesName());
			movementEntity.setSpecName(cfGoods.getSpecName());
			movementEntity.setSpecifications(cfGoods.getSpecifications());
			movementEntity.setVarietiesName(cfGoods.getVarietiesName());
			movementEntity.setGradeName(cfGoods.getGradeName());
		}
		movementEntity.setPrice(0d);
		movementEntity.setYesterdayPrice(0d);
		movementEntity.setGoodsPk(goodsDto.getPk());
		movementEntity.setMovementPk(movementPk);
		movementEntity.setTonPrice(goodsDto.getTonPrice());

		return movementEntity;
	}

	private SxPriceMovementEntity setInsertSxMongo(String movementPk, B2bGoodsDto goodsDto, Date date) {
		SxPriceMovementEntity movementEntity = new SxPriceMovementEntity();
		movementEntity.setId(KeyUtils.getUUID());
		movementEntity.setInsertTime(DateUtil.formatDateAndTime(date));
		movementEntity.setDate(DateUtil.formatYearMonthDay(date));
		movementEntity.setBrandName(goodsDto.getBrandName());
		SxGoods sxGoods = JSON.parseObject(goodsDto.getGoodsInfo(), SxGoods.class);
		if(sxGoods != null) {
			movementEntity.setTechnologyName(sxGoods.getTechnologyName());
			movementEntity.setFirstMaterialName(sxGoods.getRawMaterialParentName());
			movementEntity.setSecondMaterialName(sxGoods.getRawMaterialName());
			movementEntity.setSpecifications(sxGoods.getSpecName());
		}
		movementEntity.setPrice(0d);
		movementEntity.setYesterdayPrice(0d);
		movementEntity.setGoodsPk(goodsDto.getPk());
		movementEntity.setMovementPk(movementPk);
		movementEntity.setYarnPrice(goodsDto.getTonPrice());

		return movementEntity;
	}

	@Override
	public PageModel<B2bPriceMovementEntity> searchPriceTrendHistoryList(QueryModel<B2bPriceMovementExtDto> qm) {
		PageModel<B2bPriceMovementEntity> pm = new PageModel<B2bPriceMovementEntity>();
		Query query = getQueryParams(qm);
		List<B2bPriceMovementEntity> list = mongoTemplate.find(query, B2bPriceMovementEntity.class);
		long count = mongoTemplate.count(query, B2bPriceMovementEntity.class);

		pm.setDataList(list);
		pm.setTotalCount((int) count);
		return pm;
	}

	@Override
	public PageModel<SxPriceMovementEntity> searchSxPriceTrendHistoryList(QueryModel<B2bPriceMovementExtDto> qm) {
		PageModel<SxPriceMovementEntity> pm = new PageModel<SxPriceMovementEntity>();
		Query query = getQueryParams(qm);
		List<SxPriceMovementEntity> list = mongoTemplate.find(query, SxPriceMovementEntity.class);
		long count = mongoTemplate.count(query, SxPriceMovementEntity.class);
		pm.setDataList(list);
		pm.setTotalCount((int) count);
		return pm;
	}
	private Query getQueryParams(QueryModel<B2bPriceMovementExtDto> qm) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria = Criteria.where("_id").nin("");
		String start = qm.getEntity().getDateHistoryStart();
		String end = qm.getEntity().getDateHistoryEnd();
		if (start != null && !"".equals(start) && end != null && !"".equals(end)) {
			criteria.and("date").gte(start).lte(end);
		}
		if (start != null && !"".equals(start) && (end == null || "".equals(end))) {
			criteria.and("date").gte(start);
		}
		if (end != null && !"".equals(end) && (start == null || "".equals(start))) {
			criteria.and("date").lte(end);
		}
		if (qm.getEntity().getMovementPk() != null && !"".equals(qm.getEntity().getMovementPk())) {
			criteria.and("movementPk").is(qm.getEntity().getMovementPk());
		}
		if (qm.getEntity().getIsHome()!=null&&qm.getEntity().getIsHome() >0) {
			criteria.and("isShow").is(qm.getEntity().getIsHome());
		}
		query.addCriteria(criteria);
		Sort sorts = null;
		String orderType = qm.getFirstOrderType();
		if (orderType != null && "desc".equals(orderType)) {
			sorts = new Sort(Direction.DESC, qm.getFirstOrderName());
		} else {
			sorts = new Sort(Direction.ASC, qm.getFirstOrderName());
		}
		query.with(sorts);
		query.skip(qm.getStart());
		query.limit(qm.getLimit());
		return query;
	}

	@Override
	public List<B2bPriceMovementEntity> exportPriceTrendHistoryList(B2bPriceMovementExtDto dto) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria = Criteria.where("goodsPk").nin("");

		String start = dto.getDateHistoryStart();
		String end = dto.getDateHistoryEnd();

		if (start != null && !"".equals(start) && end != null && !"".equals(end)) {
			criteria.and("date").gte(start).lte(end);
		}
		if (start != null && !"".equals(start) && (end == null || "".equals(end))) {
			criteria.and("date").gte(start);
		}
		if (end != null && !"".equals(end) && (start == null || "".equals(start))) {
			criteria.and("date").lte(end);
		}
		if (dto.getIsHome()!=null&&dto.getIsHome() >0) {
			criteria.and("isShow").is(dto.getIsHome());
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, B2bPriceMovementEntity.class);
	}

	@Override
	public List<SxPriceMovementEntity> exportSxPriceTrendHistoryList(B2bPriceMovementExtDto dto) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria = Criteria.where("goodsPk").nin("");

		String start = dto.getDateHistoryStart();
		String end = dto.getDateHistoryEnd();

		if (start != null && !"".equals(start) && end != null && !"".equals(end)) {
			criteria.and("date").gte(start).lte(end);
		}
		if (start != null && !"".equals(start) && (end == null || "".equals(end))) {
			criteria.and("date").gte(start);
		}
		if (end != null && !"".equals(end) && (start == null || "".equals(start))) {
			criteria.and("date").lte(end);
		}
		if (dto.getIsHome()!=null&&dto.getIsHome() >0) {
			criteria.and("isShow").is(dto.getIsHome());
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, SxPriceMovementEntity.class);
	}


	@Override
	public int savePriceTrendHistoryToOss(GoodsDataTypeParams params,ManageAccount account) {
		Date time = new Date();
			String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
		try {
			Map<String, String> dateMap = CommonUtil.checkExportTime(params.getDateStart(), params.getDateEnd(), timeStr);
			params.setDateStart(dateMap.get("startTime"));
			params.setDateEnd(dateMap.get("endTime"));

			Map<String, String> dateHisMap = CommonUtil.checkExportTime(params.getDateHistoryStart(), params.getDateHistoryEnd(), timeStr);
			params.setDateHistoryStart(dateHisMap.get("startTime"));
			params.setDateHistoryEnd(dateHisMap.get("endTime"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());

		if (Constants.BLOCK_CF.equals(params.getBlock())){
			store.setMethodName("exportPriceTrendHistoryList_"+params.getUuid());
			store.setName("化纤中心-运营管理-首页显示管理-价格趋势历史");
		}else{
			store.setMethodName("exportSxPriceTrendHistoryList_"+params.getUuid());
			store.setName("纱线中心-运营管理-首页显示管理-价格趋势历史");
		}
		store.setParams(json);
		store.setParamsName(ExportDoJsonParams.doPriceTreandRunnableParams(params,time));
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(time);
		store.setType(Constants.THREE);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public PageModel<SxHomeSecondnavigationExtDto> searchHomeSecondNavigationList(
			QueryModel<SxHomeSecondnavigationExtDto> qm) {
		PageModel<SxHomeSecondnavigationExtDto> pm = new PageModel<SxHomeSecondnavigationExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("isDelete",Constants.ONE);
		map.put("navigation", qm.getEntity().getNavigation());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("parentNavigation", qm.getEntity().getParentNavigation());
		int totalCount = sxHomeSecondnavigationExtDao.searchExtGridCount(map);
		List<SxHomeSecondnavigationExtDto> list = sxHomeSecondnavigationExtDao.searchExtGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<SxHomeThirdnavigationExtDto> searchHomeThirdNavigationList(
			QueryModel<SxHomeThirdnavigationExtDto> qm) {
		PageModel<SxHomeThirdnavigationExtDto> pm = new PageModel<SxHomeThirdnavigationExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("isDelete", Constants.ONE);
		map.put("navigation", qm.getEntity().getNavigation());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("secondNavigationPk", qm.getEntity().getSecondNavigationPk());
		int totalCount = sxHomeThirdnavigationExtDao.searchExtGridCount(map);
		List<SxHomeThirdnavigationExtDto> list = sxHomeThirdnavigationExtDao.searchExtGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public String changeSecondNavigation(Integer type) {
		String reString = "";
		Map<String, Object> map = new HashMap<>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		if (type == 1) {//化纤的品名 
			List<B2bProductDto>	 productDtos = 	b2bProductDao.searchList(map);
			reString = JsonUtils.convertToString(productDtos);
		}else if(type == 2) {//纱线工艺
			List<SxTechnologyDto>  technologyDtos = 	sxTechnologyDao.searchList(map);
			reString = JsonUtils.convertToString(technologyDtos);
		}else if (type == 3) {//热销品牌的品牌
			List<B2bBrandDto> b2bBrandDtos = b2bBrandDao.searchList(map);
			reString = JsonUtils.convertToString(b2bBrandDtos);
		}
		return reString;
	}

	@Override
	public Integer updateSecondNavigation(SxHomeSecondnavigationExtDto dto) {
		Integer  reasult = 0;
		//校验同一一级导航下是否已存在该二级导航
		if (isExtSecondNavigation(dto)) {
			reasult =2;
		}else {
			if (null!=dto.getPk()&&!dto.getPk().equals("")) {
				dto.setUpdateTime(new Date());
				reasult = sxHomeSecondnavigationExtDao.updateExt(dto);
			}else{
				SxHomeSecondnavigation  model = new SxHomeSecondnavigation();
				model.setPk(KeyUtils.getUUID());
				model.setNavigation(dto.getNavigation());
				model.setNavigationPk(dto.getNavigationPk());
				model.setParentNavigation(dto.getParentNavigation());
				model.setSort(dto.getSort());
				model.setUrl(dto.getUrl());
				model.setIsVisable(dto.getIsVisable());
				model.setInsertTime(new Date());
				reasult =sxHomeSecondnavigationExtDao.insert(model);
			}
		}
		return reasult;
	}

	private boolean isExtSecondNavigation(SxHomeSecondnavigationExtDto dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("pk", dto.getPk());
		if (dto.getParentNavigation().equals("3")) {
			map.put("navigation", dto.getNavigation());
		}else{
			map.put("navigationPk", dto.getNavigationPk());
		}
		map.put("parentNavigation", dto.getParentNavigation());
		Integer  count = sxHomeSecondnavigationExtDao.isExtSecondNavigation(map);
		if (count>0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Integer updateSecondNavigationSort(SxHomeSecondnavigationExtDto dto) {
		dto.setUpdateTime(new Date());
		return sxHomeSecondnavigationExtDao.updateExt(dto);
	}

	@Override
	public List<SxHomeSecondnavigationDto> getAllSecondNavigationList() {
		return sxHomeSecondnavigationExtDao.getAllSecondNavigationList();
	}

	@Override
	public String changeThirdNavigation(String pk) {
		String reString = "";
		SxHomeSecondnavigationDto  dto = sxHomeSecondnavigationExtDao.getByPk(pk);
		Map<String, Object> map = new HashMap<>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("parentPk","-1");
		if (dto!=null) {
			if (dto.getParentNavigation().equals("1")) {//化纤的品种 
				List<B2bVarietiesDto>	 varietiesDtos = 	b2bVarietiesDao.searchList(map);
				reString = JsonUtils.convertToString(varietiesDtos);
			}else if(dto.getParentNavigation().equals("2")) {//纱线原料
				List<SxMaterialDto>  sxMaterialDtos = 	sxMaterialDao.searchList(map);
				reString = JsonUtils.convertToString(sxMaterialDtos);
			}
		}
		return reString;
	}

	@Override
	public Integer updateThirdNavigation(SxHomeThirdnavigationExtDto dto) {
		Integer  reasult = 0;
		//校验同一一级导航下是否已存在该二级导航
		if (isExtThirdNavigation(dto)) {
			reasult =2;
		}else {
			if (null!=dto.getPk()&&!dto.getPk().equals("")) {
				dto.setUpdateTime(new Date());
				reasult = sxHomeThirdnavigationExtDao.updateExt(dto);
			}else{
				SxHomeThirdnavigation  model = new SxHomeThirdnavigation();
				model.setPk(KeyUtils.getUUID());
				model.setNavigation(dto.getNavigation());
				model.setNavigationPk(dto.getNavigationPk());
				model.setSecondNavigationPk(dto.getSecondNavigationPk());
				model.setSort(dto.getSort());
				model.setIsVisable(dto.getIsVisable());
				model.setInsertTime(new Date());
				reasult =sxHomeThirdnavigationExtDao.insert(model);
			}
		}
		return reasult;
	}

	private boolean isExtThirdNavigation(SxHomeThirdnavigationExtDto dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("pk", dto.getPk());
		map.put("navigationPk", dto.getNavigationPk());
		map.put("secondNavigationPk", dto.getSecondNavigationPk());
		Integer  count = sxHomeThirdnavigationExtDao.isExtThirdNavigation(map);
		if (count>0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Integer updateThirdNavigationSort(SxHomeThirdnavigationExtDto dto) {
		dto.setUpdateTime(new Date());
		return sxHomeThirdnavigationExtDao.updateExt(dto);
	}
}
