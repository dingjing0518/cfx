package cn.cf.task.schedule.marketing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bContractExtDao;
import cn.cf.dao.B2bManageRegionDao;
import cn.cf.dao.MarketingOrderMemberExtDao;
import cn.cf.dao.MarketingPersonnelExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bManageRegionDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.RegionJson;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class MContractRunnable implements Runnable {
	private String name;

	private String fileName;
	private String accountPk;
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;
	private SysExcelStoreExtDao storeDao;
	private MarketingOrderMemberExtDao orderMemberDao;
	private MarketingPersonnelExtDao marketingPersonnelDao;
	private B2bContractExtDao b2bContractExtDao;
	private B2bManageRegionDao b2bManageRegionDao;
	private MongoTemplate mongoTemplate;

	public MContractRunnable() {

	}

	public MContractRunnable(String name, String uuid) {
		this.name = name;

		this.uuid = uuid;
	}

	@Override
	public void run() {
		// 上传数据
		ScheduledFuture future = null;
		if (CommonUtil.isNotEmpty(this.name)) {
			future = ScheduledFutureMap.map.get(this.name);
		}
		try {
			upLoadFile();
		} catch (Exception e) {
			ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao, this.storeDtoTemp, e.getMessage());
		} finally {
			ScheduledFutureMap.stopFuture(future, this.name);
		}
	}

	 public void upLoadFile() throws Exception {
		 
		this.mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		this.marketingPersonnelDao = (MarketingPersonnelExtDao) BeanUtils.getBean("marketingPersonnelExtDao");
		this.orderMemberDao = (MarketingOrderMemberExtDao) BeanUtils.getBean("marketingOrderMemberExtDao");
		this.b2bContractExtDao = (B2bContractExtDao) BeanUtils.getBean("b2bContractExtDao");
		this.b2bManageRegionDao = (B2bManageRegionDao) BeanUtils.getBean("b2bManageRegionDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportMContact_" + StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "营销中心-合同管理-" + storeDto.getAccountName() + "-"+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						OrderDataTypeParams params = JSON.parseObject(storeDto.getParams(), OrderDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (b2bContractExtDao != null && orderMemberDao != null && marketingPersonnelDao != null) {
							// 设置查询参数
							Map<String, Object> paramMap = getStringObjectMap(params,storeDto);
							String block = params.getBlock();
							// 设置权限
							if (Constants.BLOCK_CF.equals(block)) {
								setCFOperColContractParams(paramMap, accountPk);
							}
							Map<String, Object> mapAccount = new HashMap<>();
							mapAccount.put("accountPk", accountPk);
							mapAccount.put("isDelete", 1);
							mapAccount.put("isVisable", 1);
							MarketingPersonnelDto dto = marketingPersonnelDao.getByMap(mapAccount);
							int totalCount = 0;
							if (null != dto) {
								if (dto.getType() == 1) {// 业务员账号登录
									paramMap.put("businessPk", accountPk);
									totalCount = orderMemberDao.searchContactCount(paramMap);
								} else if (dto.getType() == 2) { // 平台交易员登录,看所有的订单
									totalCount = b2bContractExtDao.searchGridCountExt(paramMap);
								} else if (dto.getType() == 3) {// 区域经理
									B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
									if (regionDto != null && regionDto.getArea() != null
											&& !regionDto.getArea().equals("")) {
										List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(),
												RegionJson.class);
										paramMap.put("regionList", regionJsons);
										paramMap.put("loginPk", accountPk);
										totalCount = orderMemberDao.searchContactCount(paramMap);
									} else {// 区域总监看见所有
										totalCount = b2bContractExtDao.searchGridCountExt(paramMap);
									}
								}
							}
							String ossPath = "";

							if (totalCount > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(dto, totalCount, paramMap, storeDto);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(dto, totalCount, paramMap, storeDto);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto, storeDao, ossPath);
						}
					}
				}
			}
		}

	}

	private String setNotLimitPages(MarketingPersonnelDto dto, int totalCount, Map<String, Object> orderMap,
			SysExcelStoreExtDto storeDto) {
        String ossPath = "";
        List<B2bContractExtDto> list = new ArrayList<B2bContractExtDto>();
        list = getContactList(dto, orderMap, list);
        getCol(list);
        if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<B2bContractExtDto> exportUtil = new ExportUtil<B2bContractExtDto>();
            String path = exportUtil.exportDynamicUtil("contact",this.fileName, list);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

	private String setLimitPages(MarketingPersonnelDto dto, int counts, Map<String, Object> orderMap,
			SysExcelStoreExtDto storeDto) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
		List<File> fileList = new ArrayList<>();
		List<B2bContractExtDto> list = new ArrayList<B2bContractExtDto>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			orderMap.put("start", start);
			list = getContactList(dto, orderMap, list);
			orderMap.remove("start");
			getCol(list);
			ExportUtil<B2bContractExtDto> exportUtil = new ExportUtil<B2bContractExtDto>();
			String excelName = "营销中心-合同管理-" + storeDto.getAccountName() + "-"
					+ DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
			String path = exportUtil.exportDynamicUtil("contact", excelName, list);
			File file = new File(path);
			fileList.add(file);
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void getCol(List<B2bContractExtDto> list) {
		if (list != null && list.size() > Constants.ZERO) {
			if (CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_VOUCHER)) {
				setPayVoucher(list);
			}
			if (list != null && list.size() > 0) {
				for (B2bContractExtDto extDto : list) {
					setOperContractParams(accountPk, extDto);
				}
			}
		}
	}

	private List<B2bContractExtDto> getContactList(MarketingPersonnelDto dto, Map<String, Object> orderMap,
			List<B2bContractExtDto> list) {
		if (null != dto) {
			if (dto.getType() == 1) {// 业务员账号登录
				orderMap.put("businessPk", accountPk);
				list = orderMemberDao.searchContactList(orderMap);
			} else if (dto.getType() == 2) { // 平台交易员登录,看所有的订单
				list = b2bContractExtDao.searchGridExt(orderMap);
			} else if (dto.getType() == 3) {// 区域经理
				B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
				if (regionDto != null && regionDto.getArea() != null && !regionDto.getArea().equals("")) {
					List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
					orderMap.put("regionList", regionJsons);
					orderMap.put("loginPk", accountPk);
					list = orderMemberDao.searchContactList(orderMap);
				} else {// 区域总监看见所有
					list = b2bContractExtDao.searchGridExt(orderMap);
				}
			}
		}
		return list;
	}

	private void setOperContractParams(String accountPk, B2bContractExtDto extDto) {
		String storeName = "";
		if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_STORENAME)) {
			storeName = extDto.getStoreName()== null ? ""
					: CommonUtil.hideCompanyName( extDto.getStoreName());
		} else {
			storeName = extDto.getStoreName();
		}
		extDto.setStoreName(storeName);
		String purchaserInfo = extDto.getPurchaserInfo();
		if (purchaserInfo != null && !"".equals(purchaserInfo)) {
			PurchaserInfo obj = JSON.parseObject(purchaserInfo, PurchaserInfo.class);
			String telephone = "";
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_PURCHTEL)) {
				telephone = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				telephone = obj.getContactsTel();
			}
			extDto.setTelephone(telephone);

			String purchaserName = "";
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_PURCHNAME)) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}
			extDto.setPurchaserName(purchaserName);
		}
		String addressInfo = extDto.getAddressInfo();
		if (addressInfo != null && !"".equals(addressInfo)) {
			AddressInfo obj = JSON.parseObject(addressInfo, AddressInfo.class);
			String signingCompany = "";
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_SIGNCOMPANY)) {
				signingCompany = obj.getSigningCompany() == null ? ""
						: CommonUtil.hideCompanyName(obj.getSigningCompany());
			} else {
				signingCompany = obj.getSigningCompany() == null ? "" : obj.getSigningCompany();
			}
			extDto.setSigningCompany(signingCompany);

			String contacts = "";
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_SIGNACC)) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			extDto.setSigningAcc(contacts);

			String contactsTel = "";
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_SIGNTEL)) {
				contactsTel = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			extDto.setSigningTel(contactsTel);

			String toAddress = "";
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String townName = obj.getTownName() == null ? "" : obj.getTownName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_TOADDRESS)) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " "+ townName+" " + address;
			}
			extDto.setToAddress(toAddress);
		}
	}

	private void setPayVoucher(List<B2bContractExtDto> list) {
		if (list != null && list.size() > 0) {
			for (B2bContractExtDto extDto : list) {
				Query query = Query.query(Criteria.where("orderNumber").is(extDto.getContractNo()));
				List<B2bPayVoucher> rfList = mongoTemplate.find(query, B2bPayVoucher.class);
				if (rfList != null && rfList.size() > 0) {
					List<String> vList = new ArrayList<>();
					for (B2bPayVoucher payv : rfList) {
						vList.add(payv.getUrl());
					}
					extDto.setPayVoucherUrl(vList);
				}
			}
		}
	}

	private void setCFOperColContractParams(Map<String, Object> map, String accountPk) {
		if (accountPk != null) {
			if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_STORENAME)) {
				map.put("storeNameCol", ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_STORENAME);
			}
		}
	}

	private Map<String, Object> getStringObjectMap(OrderDataTypeParams params, SysExcelStoreExtDto dto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		if (CommonUtil.isNotEmpty(params.getContractNo())) {
		map.put("contractNo", params.getContractNo());
		}
		if (CommonUtil.isNotEmpty(params.getInsertTimeStart())) {
		map.put("insertTimeStart", params.getInsertTimeStart());
		}
		if (CommonUtil.isNotEmpty(params.getInsertTimeEnd())) {
		map.put("insertTimeEnd", params.getInsertTimeEnd());
		}
		if (CommonUtil.isNotEmpty(params.getPayTimeStart())) {
		map.put("payTimeStart", params.getPayTimeStart());
		}
		if (CommonUtil.isNotEmpty(params.getPayTimeEnd())) {
		map.put("payTimeEnd", params.getPayTimeEnd());
		}
		if (CommonUtil.isNotEmpty(params.getStoreName())) {
		map.put("storeName", params.getStoreName());
		}
		if (CommonUtil.isNotEmpty(params.getContractStatus())) {
		map.put("contractStatus", params.getContractStatus());
		}
		if (CommonUtil.isNotEmpty(params.getPaymentType())) {
		map.put("paymentType", params.getPaymentType());
		}
		if (CommonUtil.isNotEmpty(params.getBlock())) {
		map.put("block", params.getBlock());
		}
		if (CommonUtil.isNotEmpty(params.getEconomicsGoodsType())) {
		map.put("economicsGoodsType", params.getEconomicsGoodsType());
		}
		if (CommonUtil.isNotEmpty(params.getEconomicsGoodsName())) {
		map.put("economicsGoodsName", params.getEconomicsGoodsName());
		}
		if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
			map.put("purchaserName", params.getPurchaserName());
		}
		return map;
	}

	public static String ContactParam(OrderDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getContractNo())) {
			paramStr = "合同编号：" + params.getContractNo() + ";";
		}
		paramStr = CommonUtil.getDateShow(paramStr,  params.getInsertTimeStart(), params.getInsertTimeEnd(),  "订单时间：");

		if (CommonUtil.isNotEmpty(params.getStoreName())) {
			paramStr = paramStr + "店铺：" + params.getStoreName() + ";";
		}
		paramStr = CommonUtil.getDateShow(paramStr,  params.getPayTimeStart(), params.getPayTimeEnd(),  "支付时间：");
		
		if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
			paramStr = paramStr + "采购商：" + params.getPurchaserName() + ";";
		}
		if (CommonUtil.isNotEmpty(params.getPaymentName())) {
			paramStr = paramStr + "支付方式：" + params.getPaymentName() + ";";
		}
		if (CommonUtil.isNotEmpty(params.getEconomicsGoodsName())) {
			paramStr = paramStr + "金融产品：" + params.getEconomicsGoodsName() + ";";
		}
		// 是否匹配
		if (params.getContractStatus().equals("1")) {
			paramStr =paramStr +  "合同状态：待付款" + ";";
		}else if (params.getContractStatus().equals("2")) {
			paramStr = paramStr + "合同状态：待审批" + ";";
		}else if (params.getContractStatus().equals("3")) {
			paramStr = paramStr + "合同状态：待发货" + ";";
		}else if (params.getContractStatus().equals("4")) {
			paramStr = paramStr + "合同状态：部分发货" + ";";
		}else if (params.getContractStatus().equals("5")) {
			paramStr = paramStr + "合同状态：待收货" + ";";
		}else if (params.getContractStatus().equals("6")) {
			paramStr = paramStr + "合同状态：已完成" + ";";
		}else if (params.getContractStatus().equals("-1")) {
			paramStr = paramStr + "合同状态：已关闭" + ";";
		}
		return paramStr;
	}

}
