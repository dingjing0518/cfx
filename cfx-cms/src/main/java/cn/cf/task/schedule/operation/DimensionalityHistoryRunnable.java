package cn.cf.task.schedule.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.common.ExportDoJsonParams;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.B2bDimensionalityPresentExDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bDimensionalityPresentExDto;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class DimensionalityHistoryRunnable  implements Runnable  {
	
	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;

	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;

	public DimensionalityHistoryRunnable() {

	}

	
	
	public DimensionalityHistoryRunnable(String name,String uuid) {

		this.name = name;
		
		this.uuid = uuid;
	}

	
	
	@Override
	public void run() {
        //上传数据
        ScheduledFuture future = null;
        if (CommonUtil.isNotEmpty(this.name)) {
        	future = ScheduledFutureMap.map.get(this.name);
		}
        try {
            upLoadFile();
        } catch (Exception e) {
			ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao,this.storeDtoTemp,e.getMessage());
        } finally {
            ScheduledFutureMap.stopFuture(future,this.name);
        }
    }



	private void upLoadFile() {

		B2bDimensionalityPresentExDao b2bDimensionalityPresentExDao = (B2bDimensionalityPresentExDao) BeanUtils.getBean("b2bDimensionalityPresentExDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportDimensionalityHistoryList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "运营中心-会员体系-奖励赠送记录-" + storeDto.getAccountName() + "-"
							+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						CustomerDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								CustomerDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (b2bDimensionalityPresentExDao != null) {
							String ossPath = "";

							 Map<String, Object> parmsMap=new HashMap<String, Object>();
							 parmsMap.put("pks", params.getIds().split(","));
								// 设置权限
								setColAwardParams(parmsMap,accountPk);
								List<B2bDimensionalityPresentExDto>  presentExDtos =  b2bDimensionalityPresentExDao.searchGridExt(parmsMap);
								 if (presentExDtos != null && presentExDtos.size() > Constants.ZERO) {
										ExportUtil<B2bDimensionalityPresentExDto> exportUtil = new ExportUtil<B2bDimensionalityPresentExDto>();
										String path = exportUtil.exportDynamicUtil("dimensionalityPresentHistory", this.fileName, presentExDtos);
										File file = new File(path);
										// 上传到OSS
										ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
									}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						
					}
				}
			}
		}
		}
	}
	private void setColAwardParams(Map<String, Object> map, String accountPk) {
			if (!cn.cf.common.utils.CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_MEMBER_DIMEMS_COL_COMPANYNAME)){
				map.put("companyNameCol",ColAuthConstants.OPER_MEMBER_DIMEMS_COL_COMPANYNAME);
			}
			if (!cn.cf.common.utils.CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_MEMBER_DIMEMS_COL_MOBILE)){
				map.put("contactTelCol",ColAuthConstants.OPER_MEMBER_DIMEMS_COL_MOBILE);
			}
	}



	public static String dimensionalityHistoryParam(CustomerDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getCompanyName())) {
			paramStr = paramStr +"公司名称：" + params.getCompanyName()+";";
		}
		if (CommonUtil.isNotEmpty(params.getContactsTel())) {
			paramStr = paramStr +"电话：" + params.getContactsTel()+";";
		}
		if (CommonUtil.isNotEmpty(params.getDimenTypeName())) {
			paramStr = paramStr +"维度：" + params.getDimenTypeName()+";";
		}
		if (CommonUtil.isNotEmpty(params.getDimenCategoryName())) {
			paramStr = paramStr +"类型：" + params.getDimenCategoryName()+";";
		}
		if (CommonUtil.isNotEmpty(params.getPeriodTypeName())) {
			paramStr = paramStr +"赠送周期：" + params.getPeriodTypeName()+";";
		}
		if (CommonUtil.isNotEmpty(params.getIsVisableName())) {
			paramStr = paramStr +"是否启用：" + params.getIsVisableName()+";";
		}
		paramStr = CommonUtil.getDateShow(paramStr,  params.getUpdateStartTime(), params.getUpdateEndTime(),  "更新时间：");

		
		if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
			paramStr = paramStr +"订单号：" + params.getOrderNumber()+";";
		}
		if (null != params.getIds() && !"".equals( params.getIds())) {
			List<String> idsList = getIds(params.getIds());
			paramStr = paramStr + "勾选" + idsList.size()+"条数据;";
		}
		return paramStr;
	}

	private static List<String> getIds( String ids) {
		List<String> idsList = new ArrayList<>();
		if (ids.contains(",")) {
			String[] idarr = ids.split(",");
			for (int i = 0; i < idarr.length; i++) {
				idsList.add(idarr[i]);
			}
		} else {
			idsList.add(ids);
		}
		return idsList;
	}

}
