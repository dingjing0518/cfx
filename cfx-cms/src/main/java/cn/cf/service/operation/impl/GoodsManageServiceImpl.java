package cn.cf.service.operation.impl;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxTechnologyDto;
import cn.cf.entity.CfGoods;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.SxGoods;
import cn.cf.json.JsonUtils;
import cn.cf.model.*;
import cn.cf.service.operation.GoodsManageService;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsManageServiceImpl implements GoodsManageService {

	@Autowired
	private B2bProductExtDao b2bProductExtDao;

	@Autowired
	private B2bSpecExtDao b2bSpecExtDao;

	@Autowired
	private B2bVarietiesExtDao b2bVarietiesExtDao;

	@Autowired
	private B2bGradeExtDao b2bGradeExtDao;

	@Autowired
	private B2bBrandExtDao b2bBrandExtDao;

	@Autowired
	private B2bCompanyExtDao b2bCompanyExtDao;

	@Autowired
	private B2bGoodsExtDao b2bGoodsExtDao;

	@Autowired
	private B2bGoodsBrandExtDao b2bGoodsBrandExtDao;

	@Autowired
	private B2bStoreExtDao b2bStoreExtDao;

	@Autowired
	private B2bPlantDao b2bPlantDao;

	@Autowired
	private B2bWareDao b2bWareDao;

	@Autowired
	private SxTechnologyDao sxTechnologyDao;

	@Autowired
	private SxMaterialExtDao sxMaterialExtDao;

	@Autowired
	private ManageAuthorityExtDao manageAuthorityExtDao;
	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;

	private void setGoodsAuthParams(ManageAccount account, Map<String, Object> map,
			String marketGoodsstatisticColStorename, String marketGoodsstatisticColCompanyname,
			String marketGoodsstatisticColBrandname) {
		if (!CommonUtil.isExistAuthName((account).getPk(), marketGoodsstatisticColStorename)) {
			map.put("storeNameCol", marketGoodsstatisticColStorename);
		}
		if (!CommonUtil.isExistAuthName((account).getPk(), marketGoodsstatisticColCompanyname)) {
			map.put("companyNameCol", marketGoodsstatisticColCompanyname);
		}
		if (!CommonUtil.isExistAuthName((account).getPk(), marketGoodsstatisticColBrandname)) {
			map.put("brandNameCol", marketGoodsstatisticColBrandname);
		}
	}

	@Override
	public PageModel<B2bGoodsExtDto> searchGoodsUpAndDownList(QueryModel<B2bGoodsExtDto> qm, ManageAccount account,
			int type) {
		PageModel<B2bGoodsExtDto> pm = new PageModel<B2bGoodsExtDto>();
		Map<String, Object> map = getStringObjectMap(qm);
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		int totalCount = b2bGoodsExtDao.searchGridExtCount(map);
		if (type == 2) {// 化纤的商品管理
			setGoodsAuthParams(account, map, ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_STORENAME,
					ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_COMPANYNAME,
					ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_BRANDNAME);
		} else if (type == 1) {// 营销的商品统计
			setGoodsAuthParams(account, map, ColAuthConstants.MARKET_GOODSSTATISTIC_COL_STORENAME,
					ColAuthConstants.MARKET_GOODSSTATISTIC_COL_COMPANYNAME,
					ColAuthConstants.MARKET_GOODSSTATISTIC_COL_BRANDNAME);
		}
		List<B2bGoodsExtDto> list = b2bGoodsExtDao.searchGridExt(map);
		if (totalCount > 0) {
			for (B2bGoodsExtDto dto : list) {
				CfGoods cfGoods = JSON.parseObject(dto.getGoodsInfo(), CfGoods.class);
				if (type == 2) {// 化纤的商品管理
					if (!CommonUtil.isExistAuthName(account.getPk(),
							ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_PLANTNAME)) {
						cfGoods.setPlantName(CommonUtil.hideCompanyName(cfGoods.getPlantName()));
					}
				} else if (type == 1) {// 营销的商品统计
					if (!CommonUtil.isExistAuthName(account.getPk(),
							ColAuthConstants.MARKET_GOODSSTATISTIC_COL_PLANTNAME)) {
						cfGoods.setPlantName(CommonUtil.hideCompanyName(cfGoods.getPlantName()));
					}
				}
				dto.setCfgoods(cfGoods);
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private Map<String, Object> getStringObjectMap(QueryModel<B2bGoodsExtDto> qm) {
		Map<String, Object> map = goodsListParams(qm.getEntity());
		// 查询总公司时通手机查询子公司的商品
		if (qm.getEntity().getCompanyPk() != null) {
			B2bCompanyDto coms = b2bCompanyExtDao.getByPk(qm.getEntity().getCompanyPk());
			String[] comPks = new String[1];
			if (coms != null && "-1".equals(coms.getParentPk())) {
				List<B2bCompanyDto> l = b2bCompanyExtDao.searchCompanyByParentAndChild(coms.getPk());
				comPks = new String[l.size()];
				for (int i = 0; i < l.size(); i++) {
					comPks[i] = l.get(i).getPk();
				}
			} else {
				comPks[0] = qm.getEntity().getCompanyPk();
			}
			map.put("companyPk", comPks);
		}
		return map;
	}

	private Map<String, Object> goodsListParams(B2bGoodsExtDto qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isUpdown", qm.getIsUpdown());
		map.put("storeName", qm.getStoreName());
		map.put("brandName", qm.getBrandName());
		map.put("insertStratTime", qm.getInsertStartTime());
		map.put("brandName", qm.getBrandName());// 厂家品牌
		map.put("productPk", qm.getProductPk());// 品名
		map.put("varietiesPk", qm.getVarietiesPk());// 品种
		map.put("seedvarietyPk", qm.getSeedvarietyPk());// 子品种
		map.put("insertStratTime", qm.getInsertStartTime());// 创建时间
		map.put("insertEndTime", qm.getInsertEndTime());
		map.put("updateStartTime", qm.getUpdateStartTime());
		map.put("updateEndTime", qm.getUpdateEndTime());
		map.put("specPk", qm.getSpecPk());// 规格大类
		map.put("seriesPk", qm.getSeriesPk());// 规格系列
		map.put("type", qm.getType());// 商品类型
		map.put("isNewProduct", qm.getIsNewProduct());// 是否显示
		map.put("companyName", qm.getCompanyName());
		map.put("upStartTime", qm.getUpStartTime());// 上架时间
		map.put("upEndTime", qm.getUpEndTime());
		map.put("batchNumber", qm.getBatchNumber());
		map.put("type", qm.getType());
		map.put("block", qm.getBlock());
		return map;
	}

	@Override
	public List<B2bGoodsExtDto> exportGoodsList(QueryModel<B2bGoodsExtDto> qm, ManageAccount account, Integer type) {
		Map<String, Object> map = getStringObjectMap(qm);
		if (type == 2) {// 化纤的商品管理
			setGoodsAuthParams(account, map, ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_STORENAME,
					ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_COMPANYNAME,
					ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_BRANDNAME);
		} else if (type == 1) {// 营销的商品统计
			setGoodsAuthParams(account, map, ColAuthConstants.MARKET_GOODSSTATISTIC_COL_STORENAME,
					ColAuthConstants.MARKET_GOODSSTATISTIC_COL_COMPANYNAME,
					ColAuthConstants.MARKET_GOODSSTATISTIC_COL_BRANDNAME);
		}
		List<B2bGoodsExtDto> list = b2bGoodsExtDao.searchGridExt(map);
		if (list.size() > 0) {
			List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao
					.getColManageAuthorityByRolePk(account.getRolePk());
			Map<String, String> checkMap = CommonUtil.checkColAuth(account, setDtoList);
			for (B2bGoodsExtDto dto : list) {
				CfGoods cfGoods = JSON.parseObject(dto.getGoodsInfo(), CfGoods.class);
				if (type == 2) {// 化纤的商品管理
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_PLANTNAME))) {
						cfGoods.setPlantName(CommonUtil.hideCompanyName(cfGoods.getPlantName()));
					}
				} else if (type == 1) {// 营销的商品统计
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_GOODSSTATISTIC_COL_PLANTNAME))) {
						cfGoods.setPlantName(CommonUtil.hideCompanyName(cfGoods.getPlantName()));
					}
				}
				dto.setCfgoods(cfGoods);
			}
		}
		return list;
	}

	@Override
	public int saveGoodsExcelToOss(GoodsDataTypeParams params, ManageAccount account,int type) {
		Date time = new Date();
		try {
			Map<String,String> insertMap = CommonUtil.checkExportTime(params.getInsertStartTime(), params.getInsertEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
			Map<String,String> updateMap = CommonUtil.checkExportTime(params.getUpdateStartTime(), params.getUpdateEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
			Map<String,String> upMap = CommonUtil.checkExportTime(params.getUpStartTime(), params.getUpEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
			params.setInsertStartTime(insertMap.get("startTime"));
			params.setInsertEndTime(insertMap.get("endTime"));
			params.setUpdateStartTime(updateMap.get("startTime"));
			params.setUpdateEndTime(updateMap.get("endTime"));
			params.setUpStartTime(upMap.get("startTime"));
			params.setUpEndTime(upMap.get("endTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportGoodsList_"+params.getUuid());
		store.setParams(json);
		store.setIsDeal(Constants.TWO);

		store.setInsertTime(time);
		if (type == Constants.ONE){
			store.setName("化纤中心-商品管理");
		}else{
			store.setName("营销中心-商品管理");
		}
		store.setParamsName(ExportDoJsonParams.doGoodsRunnableParams(params,time));
		store.setType(Constants.THREE);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public List<B2bCompanyDto> getB2bChildCompay(String parentPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		map.put("isDelete", Constants.ONE);
		B2bCompanyDto dto = b2bCompanyExtDao.getByPk(parentPk);
		if (dto != null) {
			if (dto.getAuditSpStatus() == 2 && dto.getAuditStatus() == 2) {// 采购商供应商
				map.put("auditSpStatus", Constants.TWO);
			} else if (dto.getCompanyType() == 2) {// 供应商
				map.put("auditSpStatus", Constants.TWO);
			} else if (dto.getCompanyType() == 1) {// 采购商
				map.put("auditStatus", Constants.TWO);
			}
		}
		map.put("parentPk", parentPk);
		return b2bCompanyExtDao.searchList(map);
	}

	@Override
	public List<B2bStoreDto> getB2bStoreByCompayPk(String companyPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		return b2bStoreExtDao.searchList(map);
	}

	@Override
	public List<B2bPlantDto> getB2bPlantByStorePk(String storePk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storePk", storePk);
		map.put("isDelete", Constants.ONE);
		return b2bPlantDao.searchList(map);
	}

	@Override
	public List<B2bGoodsBrandDto> getB2bGoodsBrandByStorePk(String storePk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storePk", storePk);
		map.put("isDelete", Constants.ONE);
		map.put("auditStatus", Constants.TWO);
		return b2bGoodsBrandExtDao.searchList(map);
	}

	@Override
	public List<B2bSpecDto> getb2bSpecByPid(String parentPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("parentPk", parentPk);
		return b2bSpecExtDao.searchGrid(map);
	}

	@Override
	public List<B2bVarietiesDto> getB2bVarietiesByPidList(String parentPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("parentPk", parentPk);
		return b2bVarietiesExtDao.searchList(map);
	}

	@Override
	public int updateGoods(B2bGoodsExtDto dto) {
		dto.setUpdateTime(new Date());
		if (dto.getIsUpdown() != null && dto.getIsUpdown() == 2) {
			dto.setUpTime(new Date());
		}
		return b2bGoodsExtDao.updateGoods(dto);
	}

	// @Override
	// public void insertGoodsIndex(Map<String, Object> map) throws IOException
	// {
	// String path = "/usr/local/goodsindex";
	// delAllFile(path);
	// List<B2bGoodsExtDto> list = b2bGoodsExtDao.getAllGoodsForLucene();
	// luceneDao.deleteAll();
	// luceneDao.forceDelete();
	// if (list != null && list.size() > 0) {
	// for (B2bGoodsDto b2bGoodsDto : list) {
	// luceneDao.addIndex(b2bGoodsDto);
	// }
	// }
	// }

	private static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				flag = true;
			}
		}
		return flag;
	}

	// @Override
	// public String insertGoods(B2bGoods goods) {
	// int retVal = 0;
	// String msg = "";
	// goods.setPk(KeyUtils.getUUID());
	// goods.setInsertTime(new Date());
	// goods.setIsUpdown(Constants.ONE);// 默认待上架
	// goods.setIsNewProduct(Constants.ZERO);// 默认是新品(前台取反了)
	// //goods.setUnit(Constants.ONE);// 单位箱
	// if (goods.getPrice() != null && !"".equals(goods.getPrice())) {
	// goods.setTonPrice(goods.getPrice() * 1000);
	// goods.setSalePrice(goods.getPrice() * 1000);
	// }
	// retVal = b2bGoodsExtDao.insert(goods);
	// try {// 索引先注释掉
	// if (retVal == 1) {
	// B2bGoodsDto dto = getGoodsDto(goods);
	// luceneDao.addIndex(dto);
	// }
	// } catch (IOException e) {
	// retVal = 2;
	// e.printStackTrace();
	// }
	//
	// if (retVal == 1) {
	// msg = Constants.RESULT_SUCCESS_MSG;
	// } else {
	// msg = Constants.RESULT_FAIL_MSG;
	// }
	//
	// return JsonUtils.convertToString(msg);
	// }
	//
	// private B2bGoodsDto getGoodsDto(B2bGoods entity) {
	// B2bGoodsDto dto = new B2bGoodsDto();
	// dto.setPk(entity.getPk());
	// //TODO
	//// dto.setName(entity.getName());
	// dto.setPrice(entity.getPrice());
	// dto.setSalePrice(entity.getSalePrice());
	// dto.setTonPrice(entity.getTonPrice());
	// dto.setStorePk(entity.getStorePk());
	// dto.setStoreName(entity.getStoreName());
	// dto.setCompanyPk(entity.getCompanyPk());
	// dto.setCompanyName(entity.getCompanyName());
	// dto.setBrandPk(entity.getBrandPk());
	// dto.setBrandName(entity.getBrandName());
	// dto.setInsertTime(entity.getInsertTime());
	// dto.setUpdateTime(entity.getUpdateTime());
	// dto.setIsUpdown(entity.getIsUpdown());
	// dto.setUpTime(entity.getUpTime());
	// dto.setTareWeight(entity.getTareWeight());
	// dto.setTotalBoxes(entity.getTotalBoxes());
	// dto.setTotalWeight(entity.getTotalWeight());
	// //TODO
	//// dto.setFlag(entity.getFlag());
	//// dto.setIsEstimate(entity.getIsEstimate());
	//// dto.setEstimateTime(entity.getEstimateTime());
	// dto.setType(entity.getType());
	// return dto;
	// }

	@Override
	public PageModel<B2bProductExtDto> searchProductList(QueryModel<B2bProductExtDto> qm) {
		PageModel<B2bProductExtDto> pm = new PageModel<B2bProductExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("name", qm.getEntity().getName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bProductExtDao.searchGridExtCount(map);
		List<B2bProductExtDto> list = b2bProductExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		pm.setPageName("product");
		return pm;
	}

	@Override
	public int updateProduct(B2bProductExtDto extDto) {
		int retVal = 0;
		if (extDto.getPk() != null && !"".equals(extDto.getPk())) {
			retVal = b2bProductExtDao.updateProduct(extDto);
		} else {
			// 新增
		    Map<String,Object>m=new HashMap<String,Object>();
		    m.put("name", extDto.getName());
		    Integer  c=b2bProductExtDao.searchGridCount(m);
		    if(c==0){
		        retVal = insertProduct(extDto);
		    }else{
		        retVal=-1;
		    }
			
		}
		return retVal;
	}

	private int insertProduct(B2bProductExtDto extDto) {
		B2bProduct product = new B2bProduct();
		product.setPk(KeyUtils.getUUID());
		// TODO
		// product.setProductType(extDto.getProductType());
		product.setName(extDto.getName());
		product.setInsertTime(new Date());
		product.setSort(extDto.getSort());
		if (extDto.getIsVisable() == null || "".equals(extDto.getIsVisable())) {
			product.setIsVisable(Constants.ONE);
		} else {
			product.setIsVisable(extDto.getIsVisable());
		}
		product.setIsDelete(Constants.ONE);
		return b2bProductExtDao.insert(product);
	}

	@Override
	public PageModel<B2bSpecExtDto> searchSpecList(QueryModel<B2bSpecExtDto> qm) {
		PageModel<B2bSpecExtDto> pm = new PageModel<B2bSpecExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("parentPk", qm.getParamsString("parentPk"));
		map.put("noparentPk", qm.getParamsString("noparentPk"));
		map.put("name", qm.getEntity().getName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bSpecExtDao.searchGridExtCount(map);
		List<B2bSpecExtDto> list = b2bSpecExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		if ("-1".equals(qm.getParamsString("noparentPk"))) {
			pm.setPageName("seedSpec");
		} else {
			pm.setPageName("spec");
		}

		return pm;
	}

	@Override
	public int updateSpec(B2bSpecExtDto dto) {
		int retVal = 0;

		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			retVal = b2bSpecExtDao.updateSpec(dto);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", dto.getName());
			map.put("isDelete", dto.getIsDelete());
			if (dto.getParentPk() == null || "".equals(dto.getParentPk())) {
				map.put("parentPk", "-1");
			} else {
				map.put("parentPk", dto.getParentPk());
			}
			map.put("pk", dto.getPk());
			int count = b2bSpecExtDao.isExtSpec(map);
			if (count == 0) {
				// 新增
				retVal = insertSpec(dto);
			} else {
				retVal = -1;
			}
		}

		return retVal;
	}

	private int insertSpec(B2bSpecExtDto dto) {
		int retVal;
		B2bSpec spec = new B2bSpec();
		spec.setInsertTime(new Date());
		spec.setName(dto.getName());
		spec.setPk(KeyUtils.getUUID());
		if (dto.getIsVisable() == null || "".equals(dto.getIsVisable())) {
			spec.setIsVisable(Constants.ONE);
		} else {
			spec.setIsVisable(dto.getIsVisable());
		}

		if (dto.getParentPk() == null || "".equals(dto.getParentPk())) {
			spec.setParentPk("-1");
		} else {
			spec.setParentPk(dto.getParentPk());
		}
		spec.setSort(dto.getSort());
		spec.setIsDelete(Constants.ONE);
		retVal = b2bSpecExtDao.insert(spec);
		return retVal;
	}

	@Override
	public PageModel<B2bVarietiesExtDto> searchVarietiesList(QueryModel<B2bVarietiesExtDto> qm) {
		PageModel<B2bVarietiesExtDto> pm = new PageModel<B2bVarietiesExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("parentPk", qm.getParamsString("parentPk"));
		map.put("noparentPk", qm.getParamsString("noparentPk"));
		map.put("name", qm.getEntity().getName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bVarietiesExtDao.searchGridExtCount(map);
		List<B2bVarietiesExtDto> list = b2bVarietiesExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		pm.setPageName("goodsVarieties");
		return pm;
	}

	@Override
	public int updateVarieties(B2bVarietiesExtDto dto) {

		int retVal = 0;
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			retVal = b2bVarietiesExtDao.updateVarieties(dto);
		} else {
			// 新增
		   Map<String,Object>m=new HashMap<String,Object>();
		   if (dto.getParentPk() == null || "".equals(dto.getParentPk())) {
	            m.put("parentPk", -1);
	            m.put("name", dto.getName());
	            
	            Integer c=b2bVarietiesExtDao.getByCondition(m);
	            if(c==0){
	                retVal = insertVarieties(dto);
	            }else{
	                retVal=-1;
	            }
	            
	        } else {
	            if("-1".equals(dto.getParentPk())){
	                
	                m.put("parentPk", dto.getParentPk());
	                m.put("name", dto.getName());
	                Integer c=b2bVarietiesExtDao.getByCondition(m);
	                if(c==0){
	                    retVal = insertVarieties(dto);
	                }else{
	                    retVal=-2;
	                }
	                
	            }else{
	                
	                m.put("parentPk", dto.getParentPk());
	                m.put("name", dto.getName());
	                Integer c=b2bVarietiesExtDao.getByCondition(m);
	                if(c==0){
	                    retVal = insertVarieties(dto);
	                }else{
	                    retVal=-3;
	                }
	            }
	           
	        }
		}
		return retVal;
	}

	private int insertVarieties(B2bVarietiesExtDto dto) {
		B2bVarieties varieties = new B2bVarieties();
		varieties.setInsertTime(new Date());
		varieties.setPk(KeyUtils.getUUID());
		varieties.setName(dto.getName());
		varieties.setSort(dto.getSort());
		if (dto.getParentPk() == null || "".equals(dto.getParentPk())) {
			varieties.setParentPk("-1");
		} else {
			varieties.setParentPk(dto.getParentPk());
		}
		if (dto.getIsVisable() == null || "".equals(dto.getIsVisable())) {
			varieties.setIsVisable(Constants.ONE);
		} else {
			varieties.setIsVisable(dto.getIsVisable());
		}
		varieties.setIsDelete(Constants.ONE);
		return b2bVarietiesExtDao.insert(varieties);
	}

	@Override
	public PageModel<B2bBrandExtDto> searchBrandList(QueryModel<B2bBrandExtDto> qm) {
		PageModel<B2bBrandExtDto> pm = new PageModel<B2bBrandExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("name", qm.getEntity().getName());
		map.put("shortName", qm.getEntity().getShortName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("isDelete", 1);
		if ("1".equals(qm.getEntity().getModelType())) {
			map.put("block", "cf");
		} else {
			map.put("block", "sx");
		}

		int totalCount = b2bBrandExtDao.searchGridExtCount(map);
		List<B2bBrandExtDto> list = b2bBrandExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		pm.setPageName("goodsBrand");
		return pm;
	}

	@Override
	public int updateB2bBarnd(B2bBrandExtDto dto) {
		int retVal = 0;
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("brandPk", dto.getPk());
			map.put("isDelete", Constants.ONE);
			List<B2bGoodsBrandDto> goodsDtoList = b2bGoodsBrandExtDao.searchList(map);
			// 判断相同名称的厂家品牌
			if (goodsDtoList != null && goodsDtoList.size() > 0) {
				retVal = -10;
			} else
				retVal = b2bBrandExtDao.updateBrand(dto);
		} else {
			// 新增
			retVal = insertBrand(dto);
		}
		return retVal;
	}

	private int insertBrand(B2bBrandExtDto extDto) {
		B2bBrand model = new B2bBrand();
		model.setPk(KeyUtils.getUUID());
		model.setName(extDto.getName());
		model.setShortName(extDto.getShortName());
		model.setInsertTime(new Date());
		model.setSort(extDto.getSort());
		if (extDto.getIsVisable() == null || "".equals(extDto.getIsVisable())) {
			model.setIsVisable(Constants.ONE);
		} else {
			model.setIsVisable(extDto.getIsVisable());
		}
		model.setIsDelete(Constants.ONE);
		return b2bBrandExtDao.insert(model);
	}

	@Override
	public List<B2bGoodsBrandDto> getB2bGoodsBrandList(String block) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("auditStatus", Constants.TWO);
		if (block != null && !"".equals(block)) {
			map.put("block", block);
		}
		return b2bGoodsBrandExtDao.searchList(map);
	}

	@Override
	public List<B2bProductDto> getB2bProductList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return b2bProductExtDao.searchList(map);
	}

	@Override
	public List<B2bVarietiesDto> getB2bVarietiesPidList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("parentPk", -1);
		return b2bVarietiesExtDao.searchList(map);
	}

	@Override
	public List<B2bVarietiesDto> getB2bVarietiesList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return b2bVarietiesExtDao.searchList(map);
	}

	@Override
	public List<B2bSpecDto> getb2bSpecPid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("parentPk", -1);
		return b2bSpecExtDao.searchList(map);
	}

	@Override
	public List<B2bGradeDto> getB2bGradeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		map.put("isDelete", Constants.ONE);
		return b2bGradeExtDao.searchList(map);
	}

	@Override
	public List<B2bWareDto> getB2bWareByPlant(String storePk) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("storePk", storePk);

		return b2bWareDao.searchList(map);
	}

	@Override
	public List<B2bCompanyDto> getB2bCompayDtoByType(Integer companyType, String parentPk, String block) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (companyType == Constants.ONE) {
			map.put("auditStatus", Constants.TWO);
		}
		if (companyType == Constants.TWO) {
			map.put("auditSpStatus", Constants.TWO);
		}
		map.put("isVisable", Constants.ONE);
		map.put("isDelete", Constants.ONE);
		map.put("parentPk", parentPk);
		if (block != null && !block.equals("")) {
			map.put("block", block);
		}
		return b2bCompanyExtDao.getByBlock(map);
	}

	@Override
	public int updateGrade(B2bGradeExtDto extDto) {
		int retVal = 0;
		if (extDto.getPk() != null && !"".equals(extDto.getPk())) {
			retVal = b2bGradeExtDao.updateGrade(extDto);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isDelete", Constants.ONE);
			map.put("name", extDto.getName());
			int count = b2bGradeExtDao.isExitGrade(map);
			if (count == 0) {
				// 新增
				retVal = insertGrade(extDto);
			} else {
				retVal = -1;
			}

		}
		return retVal;
	}

	private int insertGrade(B2bGradeExtDto extDto) {
		B2bGrade model = new B2bGrade();
		model.setPk(KeyUtils.getUUID());
		model.setName(extDto.getName());
		model.setChineseName(extDto.getChineseName());
		model.setInsertTime(new Date());
		model.setSort(extDto.getSort());
		if (extDto.getIsVisable() == null || "".equals(extDto.getIsVisable())) {
			model.setIsVisable(Constants.ONE);
		} else {
			model.setIsVisable(extDto.getIsVisable());
		}
		model.setIsDelete(Constants.ONE);
		return b2bGradeExtDao.insert(model);
	}

	@Override
	public PageModel<B2bGradeExtDto> searchGrade(QueryModel<B2bGradeExtDto> qm) {
		PageModel<B2bGradeExtDto> pm = new PageModel<B2bGradeExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bGradeExtDao.searchGridExtCount(map);
		List<B2bGradeExtDto> list = b2bGradeExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		pm.setPageName("goodsGrade");
		return pm;
	}

	@Override
	public B2bGradeDto getGradeByName(String name) {
		// TODO Auto-generated method stub
		return b2bGradeExtDao.getByName(name);
	}

	// @Override
	// public List<B2bGoodsExtDto> searchisUpDownGoodsList(B2bGoodsExtDto
	// goods,ManageAccount account) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("isUpdown", goods.getIsUpdown());
	// //TODO
	//// map.put("name", goods.getName());
	// map.put("storeName", goods.getStoreName());
	// map.put("brandName", goods.getBrandName());
	//// map.put("specName", goods.getSpecName());
	//// map.put("productName", goods.getProductName());
	//// map.put("productPk", goods.getProductPk());
	//// map.put("varietiesPk", goods.getVarietiesPk());
	//// map.put("seedvarietyPk", goods.getSeedvarietyPk());
	//// map.put("specPk", goods.getSpecPk());
	//// map.put("seriesPk", goods.getSeriesPk());
	//// map.put("batchNumber", goods.getBatchNumber());
	// map.put("insertStratTime", goods.getInsertStartTime());
	// map.put("insertEndTime", goods.getInsertEndTime());
	//
	// map.put("type", goods.getType());
	// map.put("isNewProduct", goods.getIsNewProduct());
	// map.put("companyName", goods.getCompanyName());
	// map.put("updateStartTime", goods.getUpdateStartTime());
	// map.put("updateEndTime", goods.getUpdateEndTime());
	// map.put("upStartTime", goods.getUpStartTime());
	// map.put("upEndTime", goods.getUpEndTime());
	//
	// // 查询总公司时通手机查询子公司的商品
	// if (goods.getCompanyPk() != null) {
	// B2bCompanyDto coms = this.b2bCompanyExtDao.getByPk(goods.getCompanyPk());
	// String[] comPks = new String[1];
	// if (coms != null && "-1".equals(coms.getParentPk())) {
	// List<B2bCompanyDto> l =
	// this.b2bCompanyExtDao.searchCompanyByParentAndChild(coms.getPk());
	// comPks = new String[l.size()];
	// for (int i = 0; i < l.size(); i++) {
	// comPks[i] = l.get(i).getPk();
	// }
	// } else {
	// comPks[0] = goods.getCompanyPk();
	// }
	// map.put("companyPk", comPks);
	// }
	// setGoodsAuthParams(account, map,
	// ColAuthConstants.MARKET_GOODSSTATISTIC_COL_STORENAME,
	// ColAuthConstants.MARKET_GOODSSTATISTIC_COL_COMPANYNAME,
	// ColAuthConstants.MARKET_GOODSSTATISTIC_COL_PLANTNAME,
	// ColAuthConstants.MARKET_GOODSSTATISTIC_COL_BRANDNAME);
	// List<B2bGoodsExtDto> list =
	// this.b2bGoodsExtDao.getExportIsUpDownGoods(map);
	// }

	@Override
	public PageModel<B2bGoodsExtDto> searchGoodsToPriceTrendList(QueryModel<B2bGoodsExtDto> qm) {
		PageModel<B2bGoodsExtDto> pm = new PageModel<B2bGoodsExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", Constants.ONE);
		map.put("isUpdown", Constants.TWO);
		map.put("type", "normal");
		String block = qm.getEntity().getBlock();
		map.put("block", block);

		map.put("goodsInfo", qm.getEntity().getB2bGoodsInfo());

		List<B2bGoodsExtDto> list = b2bGoodsExtDao.searchGridPriceTrendExt(map);
		int totalCount = b2bGoodsExtDao.searchGridPriceTrendExtCount(map);

		if (list != null && list.size() > 0) {
			for (B2bGoodsExtDto dto : list) {
				String info = "";
				if (CommonUtil.isNotEmpty(dto.getGoodsInfo())){
					if (Constants.BLOCK_CF.equals(block)) {
						CfGoods cfGoods = JSON.parseObject(dto.getGoodsInfo(),CfGoods.class);
						String productName = CommonUtil.isNullReturnString(cfGoods.getProductName());
						String varietiesName = CommonUtil.isNullReturnString(cfGoods.getVarietiesName());
						String specName = CommonUtil.isNullReturnString(cfGoods.getSpecName());
						String specifications = CommonUtil.isNullReturnString(cfGoods.getProductName());
						String batchNumber =  CommonUtil.isNullReturnString(cfGoods.getBatchNumber());
						String gradeName = CommonUtil.isNullReturnString(cfGoods.getGradeName());
						info = productName + " " + varietiesName + " " + dto.getBrandName() + " " + specName + " "
								+ specifications + " " + batchNumber + " " + gradeName;
					}
					if (Constants.BLOCK_SX.equals(block)){
						SxGoods sxGoods = JSON.parseObject(dto.getGoodsInfo(), SxGoods.class);
						String technologyName = CommonUtil.isNullReturnString(sxGoods.getTechnologyName());
						String rawMaterialParentName = CommonUtil.isNullReturnString(sxGoods.getRawMaterialParentName());
						String rawMaterialName = CommonUtil.isNullReturnString(sxGoods.getRawMaterialName());
						String specName = CommonUtil.isNullReturnString(sxGoods.getSpecName());
						info = technologyName + " "+ rawMaterialParentName +" "+ " " + rawMaterialName+" "+ specName;
					}
				}
				dto.setB2bGoodsInfo(info);
			}
		}
		pm.setDataList(list);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<SxTechnologyDto> searchSxTechnologyList() {
		Map<String, Object> map = new HashMap<>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return sxTechnologyDao.searchList(map);
	}

	@Override
	public List<SxMaterialDto> searchSxMaterialList() {
		Map<String, Object> map = new HashMap<>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("parentPk","-1");
		return sxMaterialExtDao.searchList(map);
	}

	@Override
	public List<SxMaterialDto> searchSxSecondMaterialList(String parentPk) {
	    Map<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        map.put("parentPk", parentPk);
        return sxMaterialExtDao.searchList(map);
	}

	@Override
	public List<B2bGoodsExtDto> getExportGoodsNumbers(B2bGoodsExtDto qm) {
		Map<String, Object> map = goodsListParams(qm);
		List<B2bGoodsExtDto> list = b2bGoodsExtDao.searchGridExt(map);
		return list;
	}

}
