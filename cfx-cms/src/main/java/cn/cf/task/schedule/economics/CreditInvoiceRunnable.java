package cn.cf.task.schedule.economics;

import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.B2bCreditInvoiceExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bCreditInvoiceExtDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CreditInvoiceDataTypeParams;
import cn.cf.util.DateUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditInvoiceRunnable implements Runnable {

	private SysExcelStoreExtDto storeDtoTemp;
	private SysExcelStoreExtDao storeDao;
	private B2bCreditInvoiceExtDao b2bCreditInvoiceExtDao;
	private String uuid;
	@Override
	public void run() {
        //上传数据
        try {
            upLoadFile();
        } catch (Exception e) {
			ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao,this.storeDtoTemp,e.getMessage());
        } finally {
        }
    }

	private void upLoadFile() {

		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		this.b2bCreditInvoiceExtDao = (B2bCreditInvoiceExtDao) BeanUtils.getBean("b2bCreditInvoiceExtDao");

		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "creditInvoice_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> storelist = storeDao.getFirstTimeExcelStore(map);
			if (storelist != null && storelist.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : storelist) {
					this.storeDtoTemp = storeDto;
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						CreditInvoiceDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								CreditInvoiceDataTypeParams.class);
						if (b2bCreditInvoiceExtDao != null) {
							String ossPath = searchSupplierSaleData(params,storeDto);
							File file = new File(ossPath);
							// 上传到OSS
							ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}
	}
	private String searchSupplierSaleData(CreditInvoiceDataTypeParams params,
			SysExcelStoreExtDto storeDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyPk", params.getCompanyPk());
		map.put("dataType", params.getDataType());
		map.put("billingDateStart", params.getBillingDateStart());
		map.put("billingDateEnd", params.getBillingDateEnd());
		List<B2bCreditInvoiceExtDto> list = b2bCreditInvoiceExtDao.searchExportList(map);

		String excelName = "金融中心-授信客户管理-发票查询-" + storeDto.getAccountName() + "-"
				+ DateUtil.formatYearMonthDayHMS(new Date());
		ExportUtil<B2bCreditInvoiceExtDto> exportUtil = new ExportUtil<>();
		return exportUtil.exportDynamicUtil("creditInvoice", excelName, list);
	}


}
