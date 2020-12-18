package cn.cf.service.yarn.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.common.ExportDoJsonParams;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.SysExcelStore;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bGoodsExtDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.entity.SxGoodsExt;
import cn.cf.model.ManageAccount;
import cn.cf.service.yarn.SxGoodsManageService;

@Service
public class SxGoodsManageServiceImpl implements SxGoodsManageService {
    
    @Autowired
    private  B2bGoodsExtDao  b2bGoodsExtDao;
    
    @Autowired
    private ManageAuthorityExtDao  manageAuthorityExtDao;

    @Autowired
    private SysExcelStoreExtDao sysExcelStoreExtDao;
  
    
    @Override
    public PageModel<B2bGoodsExtDto> searchGoodsManageList(QueryModel<B2bGoodsExtDto> qm, ManageAccount account,int type ) {
        PageModel<B2bGoodsExtDto> pm = new PageModel<B2bGoodsExtDto>();
        Map<String, Object> map = goodsListParams(qm,type);
        int totalCount = 0;
        if (account!=null) {
        	
            if (!CommonUtil.isExistAuthName((account).getPk(), ColAuthConstants.YARN_GOODS_MG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.YARN_GOODS_MG_COL_STORENAME);
            }
            if (!CommonUtil.isExistAuthName((account).getPk(),  ColAuthConstants.YARN_GOODS_MG_COL_COMPANYNAME)) {
                map.put("companyNameCol",  ColAuthConstants.YARN_GOODS_MG_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName((account).getPk(),   ColAuthConstants.YARN_GOODS_MG_COL_BRANDNAME)) {
                map.put("brandNameCol",   ColAuthConstants.YARN_GOODS_MG_COL_BRANDNAME);
            }
        }
        if (type==1) {
        	totalCount  = b2bGoodsExtDao.searchSxGridExtCount(map);
            pm.setTotalCount(totalCount);                                                                                                                                                                  
        }
        List<B2bGoodsExtDto> list = b2bGoodsExtDao.searchSxGridExt(map);
        if (list.size()>0) {
        	for (B2bGoodsExtDto dto : list) {
        		SxGoodsExt sxGoods = JSON.parseObject(dto.getGoodsInfo(), SxGoodsExt.class);
        	 if (!CommonUtil.isExistAuthName((account).getPk(), ColAuthConstants.YARN_GOODS_MG_COL_PLANTNAME)) {
        		 sxGoods.setPlantName(CommonUtil.hideCompanyName(sxGoods.getPlantName()));
             }
             if (!CommonUtil.isNotEmpty(sxGoods.getCertificateName())){
                 sxGoods.setCertificateName("无");
             }
        	 sxGoods.setIsBlendName(getIsblend(sxGoods.getIsBlend()));
        	 dto.setSxGoodsExt(sxGoods);
        	}
		}
        pm.setDataList(list);
        return pm;
    }

    
    private String getIsblend(Integer isBlend) {
		String str = "" ;
		if (isBlend!=null) {
			if (isBlend==1) {
				str = "是";
			}else if (isBlend==2) {
				str = "否";
			}
		}
		
		return str;
	}


	private Map<String, Object> goodsListParams(QueryModel<B2bGoodsExtDto> qm,int type) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (type==1) {
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        }
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("isUpdown", qm.getEntity().getIsUpdown());
        map.put("storeName", qm.getEntity().getStoreName());
        map.put("brandPk", qm.getEntity().getBrandPk());
        map.put("technologyPk", qm.getEntity().getTechnologyPk());
        map.put("rawMaterialPk", qm.getEntity().getRawMaterialPk());
        map.put("rawMaterialParentPk", qm.getEntity().getRawMaterialParentPk());
        map.put("insertStratTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
        map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
        map.put("type", qm.getEntity().getType());
        map.put("companyName", qm.getEntity().getCompanyName());
        map.put("upStartTime", qm.getEntity().getUpStartTime());
        map.put("upEndTime", qm.getEntity().getUpEndTime());
        map.put("batchNumber", qm.getEntity().getBatchNumber());
        map.put("isDelete", Constants.ONE);
        map.put("materialName", qm.getEntity().getMaterialName());
        map.put("type", qm.getEntity().getType());
        map.put("isNewProduct", qm.getEntity().getIsNewProduct());
        map.put("block", Constants.BLOCK_SX);
        map.put("isBlend", qm.getEntity().getIsBlend());
        map.put("specPk", qm.getEntity().getSpecPk());
        return map;
    }


	@Override
	public List<B2bGoodsExtDto> getExportGoodsNumbers(QueryModel<B2bGoodsExtDto> qm, ManageAccount account,int type) {
        Map<String, Object> map = goodsListParams(qm,2);
        if (account!=null) {
            if (!CommonUtil.isExistAuthName((account).getPk(), ColAuthConstants.YARN_GOODS_MG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.YARN_GOODS_MG_COL_STORENAME);
            }
            if (!CommonUtil.isExistAuthName((account).getPk(),  ColAuthConstants.YARN_GOODS_MG_COL_COMPANYNAME)) {
                map.put("companyNameCol",  ColAuthConstants.YARN_GOODS_MG_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName((account).getPk(),   ColAuthConstants.YARN_GOODS_MG_COL_BRANDNAME)) {
                map.put("brandNameCol",   ColAuthConstants.YARN_GOODS_MG_COL_BRANDNAME);
            }
        }
       
        List<B2bGoodsExtDto> list = b2bGoodsExtDao.searchSxGridExt(map);
        if (type ==1 ) {
        	if (list.size()>0) {
            	List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(account.getRolePk());
    			Map<String,String> checkMap = CommonUtil.checkColAuth(account,setDtoList);
            	for (B2bGoodsExtDto dto : list) {
            		SxGoodsExt sxGoods = JSON.parseObject(dto.getGoodsInfo(), SxGoodsExt.class);
            	 if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_GOODS_MG_COL_PLANTNAME))) {
            		 sxGoods.setPlantName(CommonUtil.hideCompanyName(sxGoods.getPlantName()));
                 }
            	 sxGoods.setIsBlendName(getIsblend(sxGoods.getIsBlend()));
            	 dto.setSxGoodsExt(sxGoods);
            	}
            	checkMap.clear();
    		}
		}
        return list;
    }

    @Override
    public int saveYarnGoodsToOss(GoodsDataTypeParams params, ManageAccount account) {
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
        store.setMethodName("exportSxGoodsList_"+params.getUuid());
        store.setParams(json);
        store.setIsDeal(Constants.TWO);
        store.setInsertTime(time);
        store.setParamsName(ExportDoJsonParams.doGoodsRunnableParams(params,time));
        store.setName("纱线中心-商品管理");
        store.setType(Constants.THREE);
        store.setAccountPk(account.getPk());
        return sysExcelStoreExtDao.insert(store);
    }
}
