package cn.cf.service.operation.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cn.cf.PageModel;
import cn.cf.common.AddMemberSysPoint;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.OrderExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.common.utils.PdfTableUtil;
import cn.cf.common.utils.PingYinUtil;
import cn.cf.constant.MemberSys;
import cn.cf.dao.B2bBrandExtDao;
import cn.cf.dao.B2bCompanyDao;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bEconomicsGoodsExDao;
import cn.cf.dao.B2bGoodsBrandExtDao;
import cn.cf.dao.B2bGoodsExtDao;
import cn.cf.dao.B2bLoanNumberExtDao;
import cn.cf.dao.B2bMemubarManageExtDao;
import cn.cf.dao.B2bMenuDao;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.B2bOrderGoodsExtDao;
import cn.cf.dao.B2bPaymentDao;
import cn.cf.dao.B2bRoleDao;
import cn.cf.dao.B2bRoleMenuDao;
import cn.cf.dao.B2bRoleMenuExDao;
import cn.cf.dao.B2bStoreExtDao;
import cn.cf.dao.LogisticsModelDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dao.SysBannerDao;
import cn.cf.dao.SysBannerExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dao.SysSupplierRecommedExtDao;
import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.dto.B2bGoodsBrandExtDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemubarManageExtDto;
import cn.cf.dto.B2bMenuDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.B2bOrderGoodsExtDto;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.B2bRoleDto;
import cn.cf.dto.B2bRoleMenuDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.SysBannerDto;
import cn.cf.dto.SysBannerExtDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.dto.SysSupplierRecommedDto;
import cn.cf.dto.SysSupplierRecommedExtDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.CfGoods;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.DeliveryOrder;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.entity.SupplierInfo;
import cn.cf.entity.SxGoods;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bBrand;
import cn.cf.model.B2bMemubarManage;
import cn.cf.model.B2bPayment;
import cn.cf.model.B2bRole;
import cn.cf.model.B2bRoleMenu;
import cn.cf.model.LogisticsModel;
import cn.cf.model.ManageAccount;
import cn.cf.model.OrderRecord;
import cn.cf.model.SysBanner;
import cn.cf.model.SysExcelStore;
import cn.cf.model.SysSupplierRecommed;
import cn.cf.property.PropertyConfig;
import cn.cf.service.operation.B2bMemberService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.economics.BankApproveAmountRunnable;
import cn.cf.task.schedule.economics.BillOrderRunnable;
import cn.cf.task.schedule.economics.EconProdBussRunnable;
import cn.cf.task.schedule.economics.EconProductUseRunnable;
import cn.cf.task.schedule.economics.ImprovingdataRunnable;
import cn.cf.task.schedule.economics.ProductBalanceRunnable;
import cn.cf.task.schedule.logistics.CustomerReportRunnable;
import cn.cf.task.schedule.logistics.GrossProfitReportRunnable;
import cn.cf.task.schedule.logistics.LogisticsOrderRunnable;
import cn.cf.task.schedule.logistics.LogisticsRouteRunnable;
import cn.cf.task.schedule.logistics.PurchaserInvoiceFormRunnable;
import cn.cf.task.schedule.logistics.SupplierDataReportRunnable;
import cn.cf.task.schedule.logistics.SupplierInvoiceFormRunnable;
import cn.cf.task.schedule.logistics.SupplierSettleFormRunnable;
import cn.cf.task.schedule.marketing.GoodsUpdateReportRunnable;
import cn.cf.task.schedule.marketing.MContractRunnable;
import cn.cf.task.schedule.marketing.PurchaserSaleReportRunnable;
import cn.cf.task.schedule.marketing.SalemanSaleDetailReportRunnable;
import cn.cf.task.schedule.marketing.ShopSaleDataReportRunnable;
import cn.cf.task.schedule.marketing.SupplierSaleDataReportRunnable;
import cn.cf.task.schedule.marketing.SupplierSaleReportRunnable;
import cn.cf.task.schedule.marketing.SysNewsStorageRunnable;
import cn.cf.task.schedule.operation.BussEconPurchaserDataRunnable;
import cn.cf.task.schedule.operation.BussEconStoreRunnable;
import cn.cf.task.schedule.operation.BussOverviewRunnable;
import cn.cf.task.schedule.operation.BussStoreDataRunnable;
import cn.cf.task.schedule.operation.DimensionalityExtPresentRunnable;
import cn.cf.task.schedule.operation.DimensionalityHistoryRunnable;
import cn.cf.task.schedule.operation.FlowDataRunnable;
import cn.cf.task.schedule.operation.MemberDataReportRunnable;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class OperationManageServiceImpl implements OperationManageService {
	private static Logger logger = LoggerFactory.getLogger(OperationManageServiceImpl.class);
	@Autowired
	private B2bGoodsBrandExtDao b2bGoodsBrandExtDao;

	@Autowired
	private B2bStoreExtDao b2bStoreExtDao;

	@Autowired
	private B2bBrandExtDao b2bBrandExtDao;

	@Autowired
	private B2bGoodsExtDao b2bGoodsExtDao;

	@Autowired
	private B2bPaymentDao b2bPaymentDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bOrderExtDao b2bOrderExtDao;

	@Autowired
	private B2bCompanyExtDao b2bCompanyExtDao;

	@Autowired
	private B2bOrderGoodsExtDao b2bOrderGoodsExtDao;

	@Autowired
	private SysBannerExtDao sysBannerExtDao;

	@Autowired
	private SysSupplierRecommedExtDao sysSupplierRecommedExtDao;

	@Autowired
	private LogisticsModelDao logisticsModelDao;

	@Autowired
	private B2bRoleDao b2bRoleDao;

	@Autowired
	private B2bMenuDao b2bMenuDao;

	@Autowired
	private B2bRoleMenuExDao b2bRoleMenuExDao;

	@Autowired
	private B2bRoleMenuDao b2bRoleMenuDao;

	@Autowired
	private SysBannerDao sysBannerDao;

	@Autowired
	private B2bCompanyDao b2bCompanyDao;

	@Autowired
	private SysSupplierRecommedExtDao supplierRecommedExtDao;

	@Autowired
	private AddMemberSysPoint addMemberSysPoint;

	@Autowired
	private B2bEconomicsGoodsExDao b2bEconomicsGoodsExDao;

	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private B2bMemubarManageExtDao b2bMemubarManageExtDao;

	@Autowired
	private ManageAuthorityExtDao manageAuthorityExtDao;

	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;

	@Autowired
	private B2bLoanNumberExtDao b2bLoanNumberExtDao;

	@Override
	public PageModel<B2bGoodsBrandExtDto> searchGoodsBrandList(QueryModel<B2bGoodsBrandExtDto> qm,
			ManageAccount account) {
		PageModel<B2bGoodsBrandExtDto> pm = new PageModel<B2bGoodsBrandExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("auditStatus", qm.getEntity().getAuditStatus());
		map.put("storePk", qm.getEntity().getStorePk());
		map.put("brandName", qm.getEntity().getBrandName());
		map.put("isDelete", 1);
		int totalCount = b2bGoodsBrandExtDao.searchGridExtCount(map);

		if (account != null) {
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_OPERMG_BRAND_COL_COMPANYNAME)) {
				map.put("companyNameCol", ColAuthConstants.OPER_OPERMG_BRAND_COL_COMPANYNAME);
			}
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_OPERMG_BRAND_COL_BRANDNAME)) {
				map.put("goodsBrandCol", ColAuthConstants.OPER_OPERMG_BRAND_COL_BRANDNAME);
			}
		}
		List<B2bGoodsBrandExtDto> list = b2bGoodsBrandExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public String updateGoodsBrand(B2bGoodsBrandExtDto extDto) {
		String msg = "";
		int result = 0;
		if (extDto.getIsDelete() == 2) {
			// 删除前判断是否存在产品或未下架产品
			int count = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("brandPk", extDto.getPk());
			map.put("isDelete", Constants.ONE);
			count = b2bGoodsExtDao.searchGridCount(map);
			if (count != 0) {
				msg = Constants.GOODS_BRANDS_IS_USED;
				return msg;
			} else {
				result = b2bGoodsBrandExtDao.updateGoodsBrand(extDto);
			}
		} else {// 编辑厂家品牌
			result = b2bGoodsBrandExtDao.updateGoodsBrand(extDto);
		}

		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;

			if (null != extDto && null != extDto.getAuditStatus()) {
				// 如果是通过审核操作，会员体系加积分
				if (extDto.getAuditStatus() == 2) {
					try {
						B2bGoodsBrandDto tempGoodsBrand = b2bGoodsBrandExtDao.getByPk(extDto.getPk());
						B2bStoreDto tempStore = getStoreByPk(tempGoodsBrand.getStorePk());
						B2bMemberDto b2bMemberDto = b2bMemberService.getMemberByPk(tempGoodsBrand.getAddMemberPk());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("dimenType", MemberSys.ACCOUNT_DIMEN_GOODBRAND.getValue());
						map.put("parentCompanyPk", tempStore.getCompanyPk() == null ? "" : tempStore.getCompanyPk());
						List<MangoMemberPoint> list = addMemberSysPoint.searchPointList(map);
						if (list == null || list.size() == 0) {
							addMemberSysPoint.addPoint(tempGoodsBrand.getAddMemberPk(),
									b2bMemberDto.getCompanyPk() == null ? "" : b2bMemberDto.getCompanyPk(),
									MemberSys.ACCOUNT_DIMEN_GOODBRAND.getValue(), null);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		} else if ("".equals(msg)) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 根据pk查询store
	 * 
	 * @param storePk
	 * @return
	 */
	public B2bStoreDto getStoreByPk(String storePk) {
		return b2bStoreExtDao.getByPk(storePk);
	}

	@Override
	public String insertGoodsBrand(B2bGoodsBrandExtDto goodsBrand) {
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandName", goodsBrand.getBrandName());
		map.put("storePk", goodsBrand.getStorePk());
		map.put("isDelete", Constants.ONE);
		List<B2bGoodsBrandDto> brandList = b2bGoodsBrandExtDao.searchList(map);
		// 判断相同名称的厂家品牌
		if (brandList != null && brandList.size() > 0) {
			msg = Constants.GOODS_BRANDS_IS_EXTIST_NAME;
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("name", goodsBrand.getBrandName());
			param.put("isDelete", 1);
			B2bBrandDto dto = b2bBrandExtDao.getByMap(param);
			if (dto != null) {
				goodsBrand.setBrandPk(dto.getPk());
			} else {
				// 如果品牌为空增加品牌
				insertBrand(goodsBrand);
			}
			goodsBrand.setPk(KeyUtils.getUUID());
			goodsBrand.setIsDelete(Constants.ONE);
			goodsBrand.setInsertTime(new Date());
			goodsBrand.setAuditStatus(Constants.TWO);// 后台新增厂家品牌不需要审核
			goodsBrand.setAuditTime(new Date());
			goodsBrand.setShortName(goodsBrand.getBrandShortName());
			if (b2bGoodsBrandExtDao.insertExt(goodsBrand) > 0) {
				msg = Constants.RESULT_SUCCESS_MSG;
			} else {
				msg = Constants.RESULT_FAIL_MSG;
			}
		}
		return msg;
	}

	private void insertBrand(B2bGoodsBrandExtDto goodsBrand) {
		B2bBrand brands = new B2bBrand();
		brands.setPk(KeyUtils.getUUID());
		brands.setInsertTime(new Date());
		brands.setIsDelete(Constants.ONE);
		brands.setName(goodsBrand.getBrandName());
		brands.setIsVisable(Constants.ONE);
		brands.setSort(Constants.ONE);
		brands.setShortName(goodsBrand.getBrandShortName());
		b2bBrandExtDao.insert(brands);
		B2bBrandDto brandDto = b2bBrandExtDao.getByName(goodsBrand.getBrandName());
		goodsBrand.setBrandPk(brandDto.getPk());
	}

	@Override
	public List<B2bStoreDto> searchStoreList() {
		Map<String, Object> map = new HashMap<String, Object>();
		return b2bStoreExtDao.getByCompanyBlock(map);
	}

	@Override
	public List<B2bStoreDto> searchAllStoreList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return b2bStoreExtDao.searchList(map);
	}

	@Override
	public List<B2bGoodsBrandDto> searchBrandList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("auditStatus", Constants.TWO);
		return b2bGoodsBrandExtDao.searchList(map);
	}

	@Override
	public List<B2bGoodsBrandDto> searchDistinctGoodsBrandList() {

		return b2bGoodsBrandExtDao.searchDisGoodsBrand();
	}

	@Override
	public List<B2bGoodsBrandDto> searchGoodsBrandListByBrandPk(String brandPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("auditStatus", Constants.TWO);
		map.put("brandPk", brandPk);
		return b2bGoodsBrandExtDao.searchList(map);
	}

	@Override
	public List<B2bPaymentDto> getPaymentList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		return b2bPaymentDao.searchList(map);
	}

	@Override
	public PageModel<B2bOrderExtDto> searchOrderList(QueryModel<B2bOrderExtDto> qm, ManageAccount account) {
		PageModel<B2bOrderExtDto> pm = new PageModel<B2bOrderExtDto>();
		Map<String, Object> map = orderParams(qm);
		int totalCount = b2bOrderExtDao.searchGridExtCount(map);
		String block = qm.getEntity().getBlock();
		String economicsGoodsType = qm.getEntity().getEconomicsGoodsType() == null ? null
				: qm.getEntity().getEconomicsGoodsType().toString();
		setColOrderParams(map, account, block, economicsGoodsType);
		List<B2bOrderExtDto> list = b2bOrderExtDao.searchGridExt(map);
		// 判断如果选择了付款凭证权限，则不显示
		for (B2bOrderExtDto d : list) {
			// 解析json数据
			OrderExportUtil.setOrderJsonInfo(d, account, block, economicsGoodsType);
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)
						&& CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_VOUCHER)) {
					// 查询付款凭证
					getMongoVoucher(d);
				}
				if (Constants.BLOCK_SX.equals(block)
						&& CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_VOUCHER)) {
					getMongoVoucher(d);
				}
			} else if (Constants.BLOCK_CF.equals(block)
					&& CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_VOUCHER)
					&& economicsGoodsType.equals("2")) {
				getMongoVoucher(d);
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private void getMongoVoucher(B2bOrderExtDto d) {
		// 查询付款凭证
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("orderNumber").is(d.getOrderNumber()), Criteria.where("type").is(1));
		List<B2bPayVoucher> imgUrls = mongoTemplate.find(new Query(criatira), B2bPayVoucher.class);
		if (null != imgUrls && imgUrls.size() != 0) {
			d.setVoucherList(imgUrls);
		}
	}

	private void setColOrderParams(Map<String, Object> map, ManageAccount account, String block,
			String economicsGoodsType) {
		if (account != null) {
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_STORENAME)) {
						map.put("storeNameCol", ColAuthConstants.OPER_ORDERMG_COL_STORENAME);
					}
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_MEMBERNAME)) {
						map.put("memberNameCol", ColAuthConstants.OPER_ORDERMG_COL_MEMBERNAME);
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_STORENAME)) {
						map.put("storeNameCol", ColAuthConstants.YARN_ORDERMG_COL_STORENAME);
					}
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_MEMBERNAME)) {
						map.put("memberNameCol", ColAuthConstants.YARN_ORDERMG_COL_MEMBERNAME);
					}
				}
			} else if (Constants.BLOCK_CF.equals(block) && economicsGoodsType.equals("2")) {
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_STORENAME)) {
					map.put("storeNameCol", ColAuthConstants.FC_ODER_MGR_COL_STORENAME);
				}
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_MEMBERNAME)) {
					map.put("memberNameCol", ColAuthConstants.FC_ODER_MGR_COL_MEMBERNAME);
				}
			}
		}
	}

	private Map<String, Object> orderParams(QueryModel<B2bOrderExtDto> qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("orderNumber", qm.getEntity().getOrderNumber());
		map.put("purchaserName", qm.getEntity().getPurchaserName());
		map.put("block", qm.getEntity().getBlock());
		map.put("purchaseType", qm.getEntity().getPurchaseType());

		map.put("supplierName", qm.getEntity().getSupplierName());
		map.put("storeName", qm.getEntity().getStoreName());
		map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
		// TODO
		map.put("paymentType", qm.getEntity().getPaymentType());
		map.put("paymentName", qm.getEntity().getPaymentName());
		map.put("orderStatus", qm.getEntity().getOrderStatus());
		map.put("memberPk", qm.getEntity().getMemberPk());
		map.put("source", qm.getEntity().getSource());
		map.put("paymentStartTime", qm.getEntity().getPaymentStartTime());
		map.put("paymentEndTime", qm.getEntity().getPaymentEndTime());
		map.put("economicsGoodsType", qm.getEntity().getEconomicsGoodsType());
		return map;
	}

	@Override
	public B2bOrderExtDto exportOrderPDF(String orderNumber, HttpServletResponse resp, HttpServletRequest req)
			throws Exception {
		// 告诉浏览器用什么软件可以打开此文件
		resp.setHeader("content-Type", "application/pdf");
		// 下载文件的默认名称
		String fileName = orderNumber + ".pdf";
		resp.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
		resp.setCharacterEncoding("UTF-8");
		Document document = new Document();

		B2bOrderExtDto order = searchOrderDetails(orderNumber);
		if (order == null) {
			return null;
		}
		// 已付款状态的存临时文件
		if (order.getOrderStatus() != 1 && order.getOrderStatus() != -1) {
			// 如果已有则下载
			if (PdfTableUtil.downloadLocal(resp, req, orderNumber)) {
				return null;
			}
			// 临时文件路径
			String tempPath = PropertyConfig.getProperty("FILE_PATH");
			// String path =
			// req.getSession().getServletContext().getRealPath("/");
			// String tempPath = path + "temp";
			File file = new File(tempPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			PdfWriter.getInstance(document, new FileOutputStream(tempPath + orderNumber + ".pdf"));
		}
		PdfWriter.getInstance(document, resp.getOutputStream());
		B2bCompanyDto purchase = b2bCompanyExtDao.getByPk(order.getPurchaserPk());
		B2bCompanyDto supplier = b2bCompanyExtDao.getByPk(order.getSupplierPk());
		if (purchase == null || supplier == null) {
			return null;
		}
		// 使用itext-asian.jar中的字体
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		Font font = new Font(baseFont);
		document.open();
		Paragraph text1 = new Paragraph("苏州纤联电子商务有限公司", new Font(baseFont, 20, Font.BOLD));
		Paragraph text2 = new Paragraph("全国统一免费电话：" + PropertyConfig.getStrValueByKey("official_tel"),
				new Font(baseFont, 10));
		Paragraph text3 = new Paragraph("化纤汇官网：" + PropertyConfig.getStrValueByKey("official_website"),
				new Font(baseFont, 10));
		text1.setAlignment(Element.ALIGN_CENTER);
		text2.setAlignment(Element.ALIGN_CENTER);
		text3.setAlignment(Element.ALIGN_CENTER);
		document.add(text1);
		document.add(text2);
		document.add(text3);
		PdfPTable table = new PdfPTable(13);
		createPDFTable(order, purchase, supplier, baseFont, font, table);
		// ------------------订单商品详情-------------------------------

		Double freight = searchFreight(orderNumber);
		String paymentName = order.getPaymentName();
		if (null != order.getEconomicsGoodsName() && !"".equals(order.getEconomicsGoodsName())) {
			paymentName = order.getEconomicsGoodsName();
		}
		if (order.getOrderGoods() != null && order.getOrderGoods().size() > 0) {
			setOrderGoodsToTable(order, font, table, freight);
		}
		table.addCell(PdfTableUtil.getCell("实付金额", font, 2, true, null));
		table.addCell(PdfTableUtil.getCell(cn.cf.util.Tool.change(order.getActualAmount()), font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("小写", font, 2, true, null));
		table.addCell(PdfTableUtil.getCell(order.getActualAmount() + "", font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("物流方式", font, 2, true, null));
		table.addCell(PdfTableUtil.getCell(order.getLogisticsModelName(), font, 11, true, null));
		table.addCell(PdfTableUtil.getCell("付款方式", font, 2, true, null));

		table.addCell(PdfTableUtil.getCell(paymentName, font, 11, true, null));
		String qualityValue = null;
		if (null != order.getEconomicsGoodsType() && order.getEconomicsGoodsType() > 0) {
			// 调金融产品配置的描述
			// B2bEconomicsGoodsDto eGoods =
			// b2bEconomicsGoodsExDao.getByPk(order
			// .getEconomicsGoodsType());
			Map<String, Object> eMap = new HashMap<>();
			eMap.put("economicsGoodsName", order.getEconomicsGoodsName());
			eMap.put("economicsGoodsType", order.getEconomicsGoodsType());
			B2bEconomicsGoodsDto eGoods = b2bEconomicsGoodsExDao.getEconomicsGoodsByPk(eMap);
			if (eGoods != null) {
				qualityValue = eGoods.getQualityValue();
			}
		}
		if (null == qualityValue || "".equals(qualityValue)) {

			qualityValue = "一   产品质量标准\n\n"
					+ "1.1  产品标准按照甲方企业定等标准执行。\n\n1.2若产品不符合约定的质量标准，乙方应在收货后7个工作日内以书面方式提出，否则产品视作符合约定标准。\n\n"
					+ "二   产品包装\n\n" + "采用行业惯用包装，能够满足搬运需要。\n\n" + "三   付款期限及付款方式\n\n"
					+ "3.1  本合同签订之日乙方向甲方支付合同价款，本合同自动生效。\n\n" + "四   提货期限\n\n" + "4.1  合同载明货物自签订之日起，规定工作日内提完。\n\n"
					+ "五   其他约定\n\n" + "5.1  合同履行过程中，甲、乙双方发生纠纷应友好协商解决。协商不成则提交供方所在地人民法院解决。\n\n"
					+ "5.2  本合同文本不得涂改，若需修改，甲、乙双方应协商达成一致修改意见，并签订书面文本。\n\n" + "5.3  本合同一式二份，甲、乙双方各执一份，具同等法律效力。";
		}

		table.addCell(PdfTableUtil.getCell(qualityValue, font, 13, false, null));
		table.addCell(PdfTableUtil.getCell("供应商(签章)\n\n代理人(签章)", font, 2, true, null));
		table.addCell(PdfTableUtil.getCell("", font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("采购商(签章)\n\n代理人(签章)", font, 2, true, null));
		table.addCell(PdfTableUtil.getCell("", font, 5, true, null));
		document.add(table);
		document.close();
		return null;
	}

	private void setOrderGoodsToTable(B2bOrderExtDto order, Font font, PdfPTable table, Double freight) {
		for (int i = 0; i < order.getOrderGoods().size(); i++) {
			B2bOrderGoodsDto goods = order.getOrderGoods().get(i);
			String goodsInfo = goods.getGoodsInfo();
			String productName = "";
			String varietiesName = "";
			String seedvarietyName = "";
			String specName = "";
			String seriesName = "";
			String batchNumber = "";
			Double loadFee = 0d;
			Double packageFee = 0d;
			Double adminFee = 0d;
			if (goodsInfo != null && !"".equals(goodsInfo)) {
				CfGoods obj = JSON.parseObject(goodsInfo, CfGoods.class);
				productName = obj.getProductName() == null ? "" : obj.getProductName();
				varietiesName = obj.getVarietiesName() == null ? "" : obj.getVarietiesName();
				seedvarietyName = obj.getSeedvarietyName() == null ? "" : obj.getSeedvarietyName();
				specName = obj.getSpecName() == null ? "" : obj.getSpecName();
				seriesName = obj.getSeriesName() == null ? "" : obj.getSeriesName();
				batchNumber = obj.getBatchNumber() == null ? "" : obj.getBatchNumber();
				loadFee = obj.getLoadFee() == null ? 0d : obj.getLoadFee();
				packageFee = obj.getPackageFee() == null ? 0d : obj.getPackageFee();
				adminFee = obj.getAdminFee() == null ? 0d : obj.getAdminFee();
			}
			table.addCell(PdfTableUtil.getCell(i + 1 + "", font, 1, true, null));
			table.addCell(PdfTableUtil.getCell(productName + "\n\n" + varietiesName + "(" + seedvarietyName + ")"
					+ "\n\n" + specName + "/" + seriesName, font, 1, true, null));
			table.addCell(PdfTableUtil.getCell(batchNumber, font, 1, true, null));
			table.addCell(PdfTableUtil.getCell(goods.getAfterBoxes() + "", font, 1, true, null));
			table.addCell(PdfTableUtil.getCell(new BigDecimal(goods.getAfterWeight().toString()).toPlainString() + "",
					font, 1, true, null));
			table.addCell(PdfTableUtil.getCell(goods.getPresentPrice() + "", font, 1, true, null));
			Double presentTotalPrice = (goods.getPresentPrice() + Double.valueOf(loadFee) + Double.valueOf(packageFee)
					+ Double.valueOf(adminFee)) * goods.getWeight();
			//
			table.addCell(PdfTableUtil.getCell(ArithUtil.roundBigDecimal(new BigDecimal(presentTotalPrice), 2) + "",
					font, 1, true, null));

			table.addCell(PdfTableUtil.getCell(
					new BigDecimal(goods.getPresentFreight().toString()).toPlainString() + "", font, 1, true, null));

			// Double presentTotalFreight =
			// goods.getPresentFreight()*goods.getWeight();
			table.addCell(
					PdfTableUtil.getCell(new BigDecimal(goods.getPresentTotalFreight().toString()).toPlainString() + "",
							font, 1, true, null));

			table.addCell(
					PdfTableUtil.getCell(new BigDecimal(loadFee.toString()).toPlainString(), font, 1, true, null));
			table.addCell(
					PdfTableUtil.getCell(new BigDecimal(packageFee.toString()).toPlainString(), font, 1, true, null));
			table.addCell(
					PdfTableUtil.getCell(new BigDecimal(adminFee.toString()).toPlainString(), font, 1, true, null));
			if (i == 0) {
				table.addCell(PdfTableUtil.getCell(
						freight > 0 ? order.getActualAmount() + "(含运费)" : order.getActualAmount() + "", font, 1, true,
						order.getOrderGoods().size()));
			}
		}
	}

	private void createPDFTable(B2bOrderDto order, B2bCompanyDto purchase, B2bCompanyDto supplier, BaseFont baseFont,
			Font font, PdfPTable table) throws DocumentException {
		table.setSpacingBefore(10);// 设置表头间距
		table.setTotalWidth(500);// 设置表格的总宽度
		// 19,74,34,20,30,49,45,49,40,42,42,42,38
		// 20, 80, 35, 35, 35, 40, 40, 40, 35, 30, 30, 30,74
		table.setTotalWidth(new float[] { 19, 74, 34, 20, 30, 49, 45, 49, 40, 42, 42, 42, 38 });// 设置表格的各列宽度
		// 使用以上两个函数，必须使用以下函数，将宽度锁定。
		table.setLockedWidth(true);

		String purchaserInfo = order.getPurchaserInfo();
		String purchaserName = "";
		if (purchaserInfo != null && !"".equals(purchaserInfo)) {
			PurchaserInfo obj = JSON.parseObject(purchaserInfo, PurchaserInfo.class);
			purchaserName = obj.getPurchaserName();
		}
		String supplierInfo = order.getSupplierInfo();
		String supplierName = "";
		if (supplierInfo != null && !"".equals(supplierInfo)) {
			SupplierInfo obj = JSON.parseObject(supplierInfo, SupplierInfo.class);
			supplierName = obj.getSupplierName();
		}
		table.addCell(PdfTableUtil.getCell("销售合同", new Font(baseFont, 20, Font.BOLD), 13, true, null));
		table.addCell(PdfTableUtil.getCell("订单编号", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(order.getOrderNumber(), font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("时间", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(DateUtil.formatDateAndTime(order.getInsertTime()), font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("甲方(供应商)", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(supplierName, font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("乙方(采购商)", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(purchaserName, font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("地址", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell((supplier.getProvinceName() == null ? "" : supplier.getProvinceName())
				+ (supplier.getCityName() == null ? "" : supplier.getCityName())
				+ (supplier.getAreaName() == null ? "" : supplier.getAreaName()), font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("地址", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell((purchase.getProvinceName() == null ? "" : purchase.getProvinceName())
				+ (purchase.getCityName() == null ? "" : purchase.getCityName())
				+ (purchase.getAreaName() == null ? "" : purchase.getAreaName()), font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("联系人", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(supplier.getContacts(), font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("联系人", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(purchase.getContacts(), font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("手机", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(supplier.getContactsTel(), font, 4, true, null));
		table.addCell(PdfTableUtil.getCell("手机", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell(purchase.getContactsTel(), font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("座机", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell("", font, 4, false, null));
		table.addCell(PdfTableUtil.getCell("座机", font, 2, false, null));
		table.addCell(PdfTableUtil.getCell("", font, 5, true, null));
		table.addCell(PdfTableUtil.getCell("根据《中华人民共和国合同法》的有关规定，经买卖双方协商一致，签订本合同并遵照执行：", font, 13, false, null));
		table.addCell(PdfTableUtil.getCell("序号", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("商品信息", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("批号", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("箱数", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("重量(吨)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("成交价格(元/吨)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("商品结算(元)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("实际运费(元/吨)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("运费结算(元)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("装车费(元/吨)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("包装费(元/吨)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("管理费(元/吨)", font, 1, true, null));
		table.addCell(PdfTableUtil.getCell("合计(元)", font, 1, true, null));
	}

	private B2bOrderExtDto searchOrderDetails(String orderNumber) {
		B2bOrderExtDto dto = b2bOrderExtDao.getByOrderNumberExt(orderNumber);
		// TODO
		List<B2bOrderGoodsExtDto> og = b2bOrderExtDao.getByOrderNumberListExt(orderNumber);
		dto.setOrderGoods(og);
		return dto;
	}

	private Double searchFreight(String orderNumber) {
		Map<String, Object> map = b2bOrderExtDao.getTotalPriceAndFreight(orderNumber);
		if (null != map.get("totalFreight")) {
			return Double.parseDouble(map.get("totalFreight").toString());
		}
		return 0d;
	}

	@Override
	public B2bOrderExtDto exportYarnOrderPDF(String orderNumber, HttpServletResponse resp, HttpServletRequest req)
			throws Exception {
		// 告诉浏览器用什么软件可以打开此文件
		resp.setHeader("content-Type", "application/pdf");
		// 下载文件的默认名称
		resp.setHeader("Content-Disposition", "attachment;filename=" + orderNumber + ".pdf");
		B2bOrderExtDto order = b2bOrderExtDao.getByOrderNumberExt(orderNumber);
		List<B2bOrderGoodsExtDto> orderGoodsList = b2bOrderExtDao.getByOrderNumberListExt(orderNumber);
		String supplierInfo = order.getSupplierInfo();
		String purchaserInfo = order.getPurchaserInfo();
		String addressInfo = order.getAddressInfo();

		String supplierName = "";
		String purchaserName = "";
		SupplierInfo objSup = null;
		if (CommonUtil.isNotEmpty(supplierInfo)) {
			objSup = JSON.parseObject(supplierInfo, SupplierInfo.class);
			supplierName = objSup.getSupplierName();
		}
		PurchaserInfo objPur = null;
		if (CommonUtil.isNotEmpty(purchaserInfo)) {
			objPur = JSON.parseObject(purchaserInfo, PurchaserInfo.class);
			purchaserName = objPur.getPurchaserName();
		}
		AddressInfo addr = null;
		if (CommonUtil.isNotEmpty(addressInfo)) {
			addr = JSON.parseObject(addressInfo, AddressInfo.class);
		}

		try {
			Document document = new Document();
			Boolean flag = true;
			// 已付款状态的存临时文件
			if (order.getOrderStatus() > 1) {
				// 如果已有则下载
				// if (PdfTableUtil.downloadLocal(resp, req, orderNumber)) {
				// return null;
				// }
				// 临时文件路径
				String tempPath = PropertyConfig.getProperty("FILE_PATH");
				File file = new File(tempPath);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdir();
				}
				PdfWriter.getInstance(document, new FileOutputStream(tempPath + orderNumber + ".pdf"));
			}
			PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());

			B2bCompanyDto supplier = b2bCompanyExtDao.getByPkExt(order.getSupplierPk());
			// 使用itext-asian.jar中的字体
			BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font font = new Font(baseFont);
			document.open();
			Paragraph text1 = new Paragraph("需方传真：", new Font(baseFont, 7, Font.NORMAL));
			document.add(text1);
			Paragraph text2 = new Paragraph("产品购销合同", new Font(baseFont, 20, Font.BOLD));
			text2.setAlignment(Element.ALIGN_CENTER);
			document.add(text2);
			PdfPTable table1 = new PdfPTable(2);
			table1.setSpacingBefore(10);// 设置表头间距
			table1.setTotalWidth(500);// 设置表格的总宽度
			table1.setTotalWidth(new float[] { 250, 250 });// 设置表格的各列宽度
															// 使用以上两个函数，必须使用以下函数，将宽度锁定。
			table1.setLockedWidth(true);
			table1.addCell(getCell("合同编号：" + orderNumber, font, 1, 2, null));
			table1.addCell(getCell("签订日期：" + DateUtil.formatYearMonthAndDay(order.getInsertTime()), font, 1, 2, null));
			table1.addCell(getCell("供方：" + supplierName, font, 1, 2, null));
			if (supplier != null) {
				table1.addCell(
						getCell("签订地点：" + supplier.getProvinceName() + supplier.getCityName() + supplier.getAreaName(),
								font, 1, 2, null));
			} else {
				table1.addCell(getCell("签订地点：", font, 1, 2, null));
			}
			table1.addCell(getCell("需方：" + purchaserName, font, 1, 2, null));
			table1.addCell(getCell("业务员：" + CommonUtil.isNullReturnString(order.getEmployeeName()), font, 1, 2, null));
			document.add(table1);
			Paragraph text3 = new Paragraph("一、产品名称、规格、数量、金额", font);
			document.add(text3);
			PdfPTable table2 = new PdfPTable(7);
			table2.setSpacingBefore(10);// 设置表头间距
			table2.setTotalWidth(500);// 设置表格的总宽度
			table2.setTotalWidth(new float[] { 65, 65, 65, 65, 65, 65, 65 });// 设置表格的各列宽度
																				// 使用以上两个函数，必须使用以下函数，将宽度锁定。
			table2.setLockedWidth(true);
			table2.addCell(getCell("产品名称", font, 1, 1, null));
			table2.addCell(getCell("证书名称", font, 1, 1, null));
			table2.addCell(getCell("规格", font, 1, 1, null));
			table2.addCell(getCell("单位", font, 1, 1, null));
			table2.addCell(getCell("数量", font, 1, 1, null));
			table2.addCell(getCell("单价(元)", font, 1, 1, null));
			table2.addCell(getCell("总金额(元)", font, 1, 1, null));
			// ------------------订单商品详情-------------------------------
			Double totalWeight = 0d;
			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				for (B2bOrderGoodsExtDto orderGoods : orderGoodsList) {

					String rawMaterialName = "";
					String specifications = "";
					String certificateName = "";
					if (CommonUtil.isNotEmpty(orderGoods.getGoodsInfo())) {
						SxGoods sxGoods = JSON.parseObject(orderGoods.getGoodsInfo(), SxGoods.class);
						rawMaterialName = CommonUtil.isNullReturnString(sxGoods.getRawMaterialParentName()) == "" ? ""
								: CommonUtil.isNullReturnString(sxGoods.getRawMaterialParentName()) + "纱";
						specifications = sxGoods.getSpecName();

						if (CommonUtil.isNotEmpty(sxGoods.getCertificateName())) {
							certificateName = sxGoods.getCertificateName();
						} else {
							certificateName = "无";
						}
					}
					table2.addCell(getCell(rawMaterialName, font, 1, 1, null));
					table2.addCell(getCell(certificateName, font, 1, 1, null));
					table2.addCell(getCell(specifications, font, 1, 1, null));
					table2.addCell(getCell("公斤", font, 1, 1, null));
					table2.addCell(getCell(new BigDecimal(orderGoods.getAfterWeight().toString()).toPlainString(), font,
							1, 1, null));

					table2.addCell(getCell(
							ArithUtil.roundBigDecimal(new BigDecimal(orderGoods.getPresentPrice().toString()), 2), font,
							1, 1, null));
					table2.addCell(getCell(
							ArithUtil.roundBigDecimal(new BigDecimal(orderGoods.getPresentTotalPrice().toString()), 2),
							font, 1, 1, null));
					totalWeight += orderGoods.getAfterWeight();
				}
				table2.addCell(getCell("合计：", font, 4, 4, null));
				table2.addCell(getCell(new BigDecimal(totalWeight.toString()).toPlainString(), font, 1, 1, null));
				table2.addCell(getCell("", font, 1, 1, null));
				table2.addCell(getCell(ArithUtil.roundBigDecimal(new BigDecimal(order.getActualAmount().toString()), 2),
						font, 1, 1, null));
			}
			document.add(table2);

			String provinceName = "";
			String cityName = "";
			String areaName = "";
			String townName = "";
			String address = "";
			if (addr != null) {
				provinceName = CommonUtil.isNullReturnString(addr.getProvinceName());
				cityName = CommonUtil.isNullReturnString(addr.getCityName());
				areaName = CommonUtil.isNullReturnString(addr.getAreaName());
				townName = CommonUtil.isNullReturnString(addr.getTownName());
				address = CommonUtil.isNullReturnString(addr.getAddress());
			}
			Paragraph text4 = new Paragraph("二、要求及技术标准：达到国家一等品标准。\n\n" + "三、交货地点：" + provinceName + cityName + areaName
					+ townName + address + "  " + (null == order.getContacts() ? "" : order.getContacts()) + " "
					+ (null == order.getContactsTel() ? "" : order.getContactsTel()) + "\n\n" + "四、运输方式及费用负担："
					+ order.getLogisticsModelName() + "。\n\n" + "五、交货时间：全款到后当天发完。\n\n" + "六、交货数量：按订货数量。\n\n"
					+ "七、结算方式及期限：现汇，签订后当日款到，合同有效，逾期作废。\n\n" + "八、纺织袋、纸箱、（集装箱）。\n\n"
					+ "九、违约责任：本合同是经双方友好协商签定的，若发生纠纷，可经双方友好协商解决，如协商不成，向供方所在地人民法院提起诉讼。\n\n"
					+ "十、本合同签字或盖章有效，传真件有效，一式二份，双方各执一份。\n\n" + "十一、备注：请客户按照生产日期顺序使用。\n\n" + "十二、收款信息：", font);
			document.add(text4);

			PdfPTable table3 = new PdfPTable(2);
			table3.setSpacingBefore(10);// 设置表头间距
			table3.setTotalWidth(500);// 设置表格的总宽度
			table3.setTotalWidth(new float[] { 250, 250 });// 设置表格的各列宽度
															// 使用以上两个函数，必须使用以下函数，将宽度锁定。
			if (null != objSup.getBankName() && null != objSup.getBankAccount() && !"".equals(objSup.getBankName())
					&& !"".equals(objSup.getBankAccount())) {
				table3.addCell(getCell("户名：" + supplierName, font, 2, 2, null));
				table3.addCell(getCell("开户行：" + objSup.getBankName(), font, 2, 2, null));
				table3.addCell(getCell("账号：" + objSup.getBankAccount(), font, 2, 2, null));
			} else {
				table3.addCell(getCell("户名：", font, 2, 2, null));
				table3.addCell(getCell("开户行：", font, 2, 2, null));
				table3.addCell(getCell("账号：", font, 2, 2, null));
			}
			table3.addCell(getCell("供方签章：\n\n", font, 1, 2, null));
			table3.addCell(getCell("需方签章：\n\n", font, 1, 2, null));
			document.add(table3);
			Paragraph text5 = new Paragraph("请签字盖章回传：" + (null != supplier ? supplier.getContactsTel() : ""), font);
			text5.setAlignment(Element.ALIGN_RIGHT);
			document.add(text5);
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	/**
	 *
	 * @param value
	 *            值
	 * @param font
	 *            字体
	 * @param colspan
	 *            合并宽
	 * @param rowspan
	 *            合并高
	 * @return
	 */
	private PdfPCell getCell(String value, Font font, Integer colspan, Integer size, Integer rowspan) {
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph(value, font);
		cell.setPhrase(p);
		cell.setColspan(colspan);
		font.setSize(6);
		if (rowspan != null) {
			cell.setRowspan(rowspan);
		}
		switch (size) {
		case 1:
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			break;
		case 2:
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			break;
		case 4:
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			break;
		default:
			break;
		}
		return cell;
	}

	@Override
	public PageModel<B2bOrderGoodsExtDto> searchOrderGoodsList(QueryModel<B2bOrderGoodsExtDto> qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<B2bOrderGoodsExtDto> pm = new PageModel<B2bOrderGoodsExtDto>();
		map.put("orderNumber", qm.getEntity().getOrderNumber());
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		int counts = b2bOrderGoodsExtDao.getB2bOrderGoodsCount(map);
		List<B2bOrderGoodsExtDto> list = new ArrayList<B2bOrderGoodsExtDto>();
		if (qm.getEntity().getBlock().equals(Constants.BLOCK_CF)) {// 化纤
			list = b2bOrderGoodsExtDao.getB2bOrderGoods(map);
			if (counts > 0) {
				for (B2bOrderGoodsExtDto dto : list) {
					// 计算商品的实际价格
					CfGoods cfGoods = JSON.parseObject(dto.getGoodsInfo(), CfGoods.class);
					countPresentTotalPrice(cfGoods, dto);
					dto.setCfGoods(cfGoods);
					B2bGoodsDto goodsDto = b2bGoodsExtDao.getByPk(cfGoods.getPk());
					if (qm.getEntity().getEconomicsGoodsType() == null) {// 化纤中心的订单商品
						if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(),
								ColAuthConstants.MARKET_ORDER_GOODS_COL_STORENAME)) {
							dto.setStoreName(CommonUtil.hideCompanyName(goodsDto.getStoreName()));
						} else {
							dto.setStoreName(goodsDto.getStoreName());
						}
					} else if (qm.getEntity().getEconomicsGoodsType() == Constants.TWO) {// 财务中心的订单商品
						if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(),
								ColAuthConstants.FC_ODER_MGR_COL_STORENAME)) {
							dto.setStoreName(CommonUtil.hideCompanyName(goodsDto.getStoreName()));
						} else {
							dto.setStoreName(goodsDto.getStoreName());
						}
					}

				}
			}
		} else {// 纱线
			list = b2bOrderGoodsExtDao.getSxOrderGoods(map);
			if (counts > 0) {
				for (B2bOrderGoodsExtDto dto : list) {
					// 计算商品的实际价格
					SxGoods sxGoods = JSON.parseObject(dto.getGoodsInfo(), SxGoods.class);
					dto.setSxGoods(sxGoods);
				}
			}
		}

		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	private void countPresentTotalPrice(CfGoods cfGoods, B2bOrderGoodsExtDto dto) {
		double presentTotalPrice = 0.0d;
		List<Double> strList = new ArrayList<Double>();
		strList.add(dto.getPresentPrice() != null ? dto.getPresentPrice() : 0.0);
		strList.add(cfGoods.getPackageFee() != null ? cfGoods.getPackageFee() : 0.0);
		strList.add(cfGoods.getLoadFee() != null ? cfGoods.getLoadFee() : 0.0);
		strList.add(cfGoods.getAdminFee() != null ? cfGoods.getAdminFee() : 0.0);
		presentTotalPrice = ArithUtil.addList(strList);

		double originalTotalPrice = 0.0d;
		if (dto.getAfterWeight() > 0) {
			originalTotalPrice = ArithUtil.round(ArithUtil.mul(dto.getOriginalPrice(), dto.getAfterWeight()), 2);
		} else {
			originalTotalPrice = ArithUtil.round(ArithUtil.mul(dto.getOriginalPrice(), dto.getWeight()), 2);
		}
		dto.setOriginalTotalPrice(originalTotalPrice);

		if (dto.getAfterWeight() > 0) {
			presentTotalPrice = ArithUtil.round(ArithUtil.mul(presentTotalPrice, dto.getAfterWeight()), 2);
		} else {
			presentTotalPrice = ArithUtil.round(ArithUtil.mul(presentTotalPrice, dto.getWeight()), 2);
		}
		dto.setPresentTotalPrice(presentTotalPrice);

	}

	@Override
	public void exportPaymentVoucher(B2bOrderExtDto b2bOrder) {
		OrderRecord or = new OrderRecord();
		B2bPayVoucher voucher = new B2bPayVoucher();
		String content = null;
		String mongoId = KeyUtils.getUUID();
		or.setId(mongoId);
		or.setOrderNumber(b2bOrder.getOrderNumber());
		or.setInsertTime(DateUtil.getDateFormat(new Date()));
		or.setType(1);
		voucher.setId(KeyUtils.getUUID());
		voucher.setOrderNumber(b2bOrder.getOrderNumber());
		voucher.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		voucher.setType(1);
		String[] payUrls = b2bOrder.getPayUrl();
		if (payUrls != null && payUrls.length > 0) {
			for (int i = 0; i < payUrls.length; i++) {
				String imgUrl = payUrls[i];
				if (imgUrl != null && !"".equals(imgUrl)) {
					content = Constants.ORDER_UPDATE_MSG + "订单号:" + b2bOrder.getOrderNumber() + "上传凭证:<img src='"
							+ imgUrl + "' style='display:block;'/>";
					or.setImgUrl(imgUrl);
					or.setContent(content);
					mongoTemplate.insert(or);
					// 存付款凭证表
					voucher.setUrl(imgUrl);
					mongoTemplate.insert(voucher);
				}
			}
		}
	}

	@Override
	public int updateBanner(SysBanner banner) {
		int result = 0;

		if (banner.getPk() != null && !"".equals(banner)) {
			result = sysBannerExtDao.updateModel(banner);
			if (result > 0 && banner.getType() != null) {
				freshToRedis(banner);
			}
		}
		return result;
	}

	private void freshToRedis(SysBanner banner) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("type", banner.getType());
		List<SysBannerDto> list = sysBannerDao.searchList(map);
		JedisMaterialUtils.del(cn.cf.common.Constants.bannerKey + banner.getType());
		if (list != null && list.size() > 0) {
			JedisMaterialUtils.set(cn.cf.common.Constants.bannerKey + banner.getType(), list);
		}
	}

	@Override
	public int insertBanner(SysBanner banner) {

		int result = 0;
		banner.setPk(KeyUtils.getUUID());
		banner.setIsDelete(Constants.ONE);
		banner.setInsertTime(new Date());
		result = sysBannerExtDao.insert(banner);
		if (result > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isDelete", 1);
			map.put("isVisable", 1);
			map.put("type", banner.getType());
			List<SysBannerDto> list = sysBannerDao.searchList(map);
			JedisMaterialUtils.del(cn.cf.common.Constants.bannerKey + banner.getType());
			if (list != null && list.size() > 0) {
				JedisMaterialUtils.set(cn.cf.common.Constants.bannerKey + banner.getType(), list);
			}
		}
		return result;
	}

	@Override
	public PageModel<SysBannerExtDto> searchBannerList(QueryModel<SysBannerExtDto> qm) {
		PageModel<SysBannerExtDto> pm = new PageModel<SysBannerExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("type", qm.getEntity().getType());
		map.put("startStime", qm.getEntity().getStartStime());
		map.put("startEtime", qm.getEntity().getStartEtime());
		map.put("endStime", qm.getEntity().getEndStime());
		map.put("endEtime", qm.getEntity().getEndEtime());
		map.put("name", qm.getEntity().getName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("onlineStime", qm.getEntity().getOnlineStime());
		map.put("onlineEtime", qm.getEntity().getOnlineEtime());
		map.put("position", qm.getEntity().getPosition());
		map.put("platform", qm.getEntity().getPlatform());
		map.put("isDelete", Constants.ONE);
		int totalCount = sysBannerExtDao.searchGridExtCount(map);
		List<SysBannerExtDto> list = sysBannerExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<SysSupplierRecommedExtDto> searchSupplierRecommedList(QueryModel<SysSupplierRecommedExtDto> qm,
			ManageAccount account) {
		PageModel<SysSupplierRecommedExtDto> pm = new PageModel<SysSupplierRecommedExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());

		map.put("isDelete", 1);
		map.put("position", qm.getEntity().getPosition());
		map.put("platform", qm.getEntity().getPlatform());
		map.put("region", qm.getEntity().getRegion());
		map.put("brandPk", qm.getEntity().getBrandPk());
		map.put("storePk", qm.getEntity().getStorePk());
		map.put("block", qm.getEntity().getBlock());
		map.put("isOnline", qm.getEntity().getIsOnline());
		map.put("insertTimeBegin", qm.getEntity().getInsertTimeBegin());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		int totalCount = sysSupplierRecommedExtDao.searchGridExtCount(map);
		if (account != null) {
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_OPERMG_RECOMMEND_COL_SUPPNAME)) {
				map.put("supplierNameCol", ColAuthConstants.OPER_OPERMG_RECOMMEND_COL_SUPPNAME);
			}
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_OPERMG_RECOMMEND_COL_PREPIC)) {
				map.put("prePicCol", ColAuthConstants.OPER_OPERMG_RECOMMEND_COL_PREPIC);
			}

		}
		List<SysSupplierRecommedExtDto> list = sysSupplierRecommedExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateSupplierRecommed(SysSupplierRecommedExtDto dto) {
		int result = 0;
		// 检验是该供应商是否有在对应平台推荐过
		Map<String, Object> map = new HashMap<String, Object>();
		dto.setUpdateTime(new Date());
		map.put("positioned", dto.getPosition());
		map.put("brandPk", dto.getBrandPk());
		map.put("storePk", dto.getStorePk());
		map.put("platformed", dto.getPlatform());
		map.put("isDelete", Constants.ONE);
		List<SysSupplierRecommedExtDto> list = sysSupplierRecommedExtDao.searchGridExt(map);
		boolean isExist = false;
		// 为了排除自身，当编辑时不修改任何信息是可以保存成功
		if (list != null && list.size() > 0) {
			for (SysSupplierRecommedDto listDto : list) {
				if (!listDto.getPk().equals(dto.getPk())) {
					result = Constants.TWO;
					isExist = true;
					break;
				}
			}
		} else {
			result = sysSupplierRecommedExtDao.updateSupplierRecommed(dto);
		}
		if (!isExist) {
			result = sysSupplierRecommedExtDao.updateSupplierRecommed(dto);
		}
		return result;
	}

	@Override
	public int updateSupplierRecommedStatus(SysSupplierRecommedExtDto dto) {

		return sysSupplierRecommedExtDao.updateSupplierRecommed(dto);
	}

	@Override
	public int insertSupplierRecommed(SysSupplierRecommed recommed) {
		int result = 0;
		// 检验是该供应商是否有在对应平台推荐过
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("position", recommed.getPosition());
		map.put("brandPk", recommed.getBrandPk());
		map.put("storePk", recommed.getStorePk());
		map.put("platform", recommed.getPlatform());
		int counts = sysSupplierRecommedExtDao.searchRecommendCounts(map);
		if (counts > 0) {
			result = Constants.TWO;
		} else {
			recommed.setPk(KeyUtils.getUUID());
			recommed.setInsertTime(new Date());
			recommed.setUpdateTime(new Date());
			recommed.setIsDelete(Constants.ONE);
			recommed.setIsOnline(Constants.ONE);
			String brandPy = PingYinUtil.getFirstSpell(recommed.getBrandName());
			recommed.setAbbreviated(brandPy.substring(0, 1).toUpperCase());
			result = sysSupplierRecommedExtDao.insert(recommed);
		}
		return result;
	}

	@Override
	public PageModel<LogisticsModelDto> searchLogisticsModelList(QueryModel<LogisticsModelDto> qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<LogisticsModelDto> pm = new PageModel<LogisticsModelDto>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		List<LogisticsModelDto> list = logisticsModelDao.searchGrid(map);
		int counts = logisticsModelDao.searchGridCount(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<B2bPaymentDto> searchb2bPaymentList(QueryModel<B2bPaymentDto> qm) {
		PageModel<B2bPaymentDto> pm = new PageModel<B2bPaymentDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bPaymentDao.searchGridCount(map);
		List<B2bPaymentDto> list = b2bPaymentDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<B2bRoleDto> searchB2bRoleGrid(QueryModel<B2bRoleDto> qm) {
		PageModel<B2bRoleDto> pm = new PageModel<B2bRoleDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
		map.put("companyType", qm.getEntity().getCompanyType());
		int totalCount = b2bRoleDao.searchGridCount(map);
		List<B2bRoleDto> list = b2bRoleDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public List<B2bMenuDto> getB2bMenuByParentPk(String parentPk, Integer companyType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentPk", parentPk);
		map.put("companyType", companyType);
		map.put("isDelete", 1);
		return b2bMenuDao.searchGrid(map);
	}

	@Override
	public List<B2bRoleMenuDto> getb2broleMenuByRolepk(String rolePk) {
		return b2bRoleMenuExDao.getRoleMenuByRolePk(rolePk);
	}

	@Override
	public String saveB2bRole(B2bRole role, String node) {
		String pk = null;
		if (role.getPk() != null && !"".equals(role.getPk())) {
			role.setIsVisable(1);
			role.setInsertTime(new Date());
			b2bRoleDao.update(role);
			pk = role.getPk();
		} else {
			pk = KeyUtils.getUUID();
			role.setPk(pk);
			role.setIsVisable(1);
			role.setIsDelete(1);
			role.setInsertTime(new Date());
			b2bRoleDao.insert(role);
		}
		if (null != pk && !"".equals(pk)) {
			B2bRoleMenu mra = new B2bRoleMenu();
			mra.setRolePk(pk);
			if (pk != null && !"".equals(pk)) {
				b2bRoleMenuExDao.deleteByB2bRoleMenuPk(mra);
			}
			if (node != null && !"".equals(node)) {
				node = node.substring(1, node.length() - 1);
				String[] id = node.split(",");
				for (int i = 0; i < id.length; i++) {
					pk = KeyUtils.getUUID();
					mra.setPk(pk);
					mra.setMenuPk(id[i].replace("\"", ""));
					b2bRoleMenuDao.insert(mra);
				}
			}
		}
		return pk;

	}

	@Override
	public PageModel<SysBannerExtDto> searchBannerdata(QueryModel<SysBannerExtDto> qm) {
		PageModel<SysBannerExtDto> pm = new PageModel<SysBannerExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("type", qm.getEntity().getType());
		map.put("startStime", qm.getEntity().getStartStime());
		map.put("startEtime", qm.getEntity().getStartEtime());
		map.put("endStime", qm.getEntity().getEndStime());
		map.put("endEtime", qm.getEntity().getEndEtime());
		map.put("name", qm.getEntity().getName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("onlineStime", qm.getEntity().getOnlineStime());
		map.put("onlineEtime", qm.getEntity().getOnlineEtime());
		map.put("isDelete", 1);
		int totalCount = sysBannerExtDao.searchGridExtCount(map);
		List<SysBannerExtDto> list = sysBannerExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int addSysBanner(SysBanner banner) {
		banner.setPk(KeyUtils.getUUID());
		banner.setIsDelete(1);
		banner.setInsertTime(new Date());
		return sysBannerDao.insert(banner);
	}

	/**
	 * 根据不同类型查询公司 companyType: 1采购商 2供应商 parentPk:总公司pk
	 */
	@Override
	public List<B2bCompanyDto> getB2bCompanyDtoByType(Integer companyType, String parentPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (companyType == 1) {
			map.put("auditStatus", 2);
		}
		if (companyType == 2) {
			map.put("auditSpStatus", 2);
			// map.put("companyType", companyType);
		}
		map.put("isVisable", 1);
		map.put("parentPk", parentPk);
		return b2bCompanyDao.searchGrid(map);
	}

	@Override
	public PageModel<SysSupplierRecommedExtDto> searchSuppREdata(QueryModel<SysSupplierRecommedExtDto> qm) {
		PageModel<SysSupplierRecommedExtDto> pm = new PageModel<SysSupplierRecommedExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", qm.getEntity().getIsDelete());
		int totalCount = supplierRecommedExtDao.searchGridExtCount(map);
		List<SysSupplierRecommedExtDto> list = supplierRecommedExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<LogisticsModelDto> logisticsModelList(QueryModel<LogisticsModelDto> qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<LogisticsModelDto> pm = new PageModel<LogisticsModelDto>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		List<LogisticsModelDto> list = logisticsModelDao.searchGrid(map);
		int counts = logisticsModelDao.searchGridCount(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateLogisticModel(LogisticsModel logisticsModel) {
		return logisticsModelDao.update(logisticsModel);
	}

	@Override
	public PageModel<B2bPaymentDto> searchb2bPaymentdata(QueryModel<B2bPaymentDto> qm) {
		PageModel<B2bPaymentDto> pm = new PageModel<B2bPaymentDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bPaymentDao.searchGridCount(map);
		List<B2bPaymentDto> list = b2bPaymentDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updatePayment(B2bPayment b2bPayment) {
		int result = 0;
		if (b2bPayment.getPk() != null && !"".equals(b2bPayment.getPk())) {
			result = b2bPaymentDao.update(b2bPayment);
		}
		return result;
	}

	@Override
	public int exportOrderListCount(QueryModel<B2bOrderExtDto> qm) {
		Map<String, Object> map = orderParams(qm);
		return b2bOrderExtDao.searchGridExtCount(map);
	}

	@Override
	public List<B2bOrderExtDto> exportOrderGoodsList(QueryModel<B2bOrderExtDto> qm, ManageAccount account) {
		Map<String, Object> map = orderParams(qm);
		String block = qm.getEntity().getBlock();

		setColOrderParams(map, account, block, null);
		List<B2bOrderExtDto> list = b2bOrderExtDao.searchOrderGoodsList(map);
		List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(account.getRolePk());
		if (list != null && list.size() > 0) {
			Map<String, String> chckMap = CommonUtil.checkColAuth(account, setDtoList);
			for (B2bOrderExtDto order : list) {
				OrderExportUtil.setOrderJsonInfoCheckMap(order, chckMap, block, null);
				if (Constants.BLOCK_CF.equals(block)) {
					OrderExportUtil.setExportParams(order);
				}
				if (Constants.BLOCK_SX.equals(block)) {
					OrderExportUtil.setExportSxParams(order);
				}
			}
			chckMap.clear();

		}
		return list;
	}

	@Override
	public int saveExcelToOss(OrderDataTypeParams params, ManageAccount account) {
		Date time = new Date();
		setStartAndEndTime(params, time);
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportOrderGoods_" + params.getUuid());
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(time);
		if (Constants.BLOCK_CF.equals(params.getBlock())) {
			store.setName("化纤中心-订单管理-导出");
		} else {
			store.setName("纱线中心-订单管理-导出");
		}
		store.setParamsName(ExportDoJsonParams.doOrderRunnableParams(params, time));
		store.setType(Constants.TWO);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	private void setStartAndEndTime(OrderDataTypeParams params, Date time) {
		try {
			Map<String, String> insertMap = CommonUtil.checkExportTime(params.getInsertStartTime(),
					params.getInsertEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
			Map<String, String> payMap = CommonUtil.checkExportTime(params.getPaymentStartTime(),
					params.getPaymentEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
			Map<String, String> receMap = CommonUtil.checkExportTime(params.getReceivablesStartTime(),
					params.getReceivablesEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
			params.setInsertStartTime(insertMap.get("startTime"));
			params.setInsertEndTime(insertMap.get("endTime"));
			params.setPaymentStartTime(payMap.get("startTime"));
			params.setPaymentEndTime(payMap.get("endTime"));
			params.setReceivablesStartTime(receMap.get("startTime"));
			params.setReceivablesEndTime(receMap.get("endTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int saveOrderGoodsExcelToOss(OrderDataTypeParams params, ManageAccount account) {
		Date time = new Date();
		setStartAndEndTime(params, time);
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportOrder_" + params.getUuid());
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(time);
		store.setParamsName(ExportDoJsonParams.doOrderRunnableParams(params, time));
		if (Constants.BLOCK_CF.equals(params.getBlock()) && params.getEconomicsGoodsType() == null) {
			store.setName("化纤中心-订单管理-订单导出");
		} else if (Constants.BLOCK_SX.equals(params.getBlock()) && params.getEconomicsGoodsType() == null) {
			store.setName("纱线中心-订单管理-订单导出");
		} else if (Constants.BLOCK_CF.equals(params.getBlock()) && params.getEconomicsGoodsType().equals("2")) {
			store.setName("财务中心-订单管理-订单导出");
		}
		store.setType(Constants.TWO);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}
	// @Override
	// public List<B2bOrderExtDto> exportOrderList(QueryModel<B2bOrderExtDto>
	// qm,ManageAccount account,int type) {
	// Map<String, Object> map = orderParams(qm);
	// String block = qm.getEntity().getBlock();
	// setColOrderParams(map, account, block,Constants.ONE);
	// LinkedList<B2bOrderExtDto> list = b2bOrderExtDao.exportOrderList(map);
	// if (type == 2) {
	// if (list != null && list.size() > 0) {
	// List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao
	// .getColManageAuthorityByRolePk(account.getRolePk());
	// Map<String, String> chckMap = CommonUtil.checkColAuth(account,
	// setDtoList);
	// for (B2bOrderExtDto order : list) {
	// OrderExportUtil.setOrderJsonInfoCheckMap(order, chckMap, block);
	// }
	// chckMap.clear();
	// }
	// }
	// return list;
	// }

	@Override
	public B2bCompanyDto getByCompanyPk(String companyPk) {
		return b2bCompanyExtDao.getByPk(companyPk);
	}

	@Override
	public PageModel<B2bMemubarManageExtDto> searchMemuManageList(QueryModel<B2bMemubarManageExtDto> qm) {
		PageModel<B2bMemubarManageExtDto> pm = new PageModel<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());

		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("source", qm.getEntity().getSource());
		map.put("name", qm.getEntity().getName());

		List<B2bMemubarManageExtDto> list = b2bMemubarManageExtDao.searchGridExt(map);
		int count = b2bMemubarManageExtDao.searchGridExtCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int delMemuManageList(String pk) {
		return b2bMemubarManageExtDao.delete(pk);
	}

	@Override
	public int editMemuManageList(B2bMemubarManageExtDto extDto) {

		int result = 0;
		if (extDto.getPk() != null && !"".equals(extDto.getPk())) {
			extDto.setUpdateTime(new Date());
			result = b2bMemubarManageExtDao.updateObj(extDto);
		} else {
			B2bMemubarManage memu = new B2bMemubarManage();
			memu.setPk(KeyUtils.getUUID());
			memu.setInsertTime(new Date());
			memu.setName(extDto.getName());
			memu.setSort(extDto.getSort());
			memu.setUrl(extDto.getUrl() == null ? "" : extDto.getUrl());
			memu.setUrlLink(extDto.getUrlLink() == null ? "" : extDto.getUrlLink());
			memu.setSource(extDto.getSource());
			result = b2bMemubarManageExtDao.insert(memu);
		}
		return result;
	}

	/**
	 * 关闭订单
	 */
	@Override
	public Integer closeOrder(String orderNumber, String closeReason) {
		int temp1 = b2bOrderExtDao.closeOrder(orderNumber);
		int temp2 = b2bOrderGoodsExtDao.closeOrder(orderNumber);
		if (temp1 + temp2 >= 2) {
			cn.cf.entity.OrderRecord orderRecord = new cn.cf.entity.OrderRecord();
			orderRecord.setId(KeyUtils.getUUID());
			orderRecord.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			String content = "订单已取消,订单号：" + orderNumber + ",关闭原因：" + closeReason;
			orderRecord.setContent(content);
			orderRecord.setOrderNumber(orderNumber);
			orderRecord.setStatus(Constants.MINUS_ONE);
			mongoTemplate.insert(orderRecord);
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public PageModel<SysExcelStoreExtDto> searchExcelStoreList(QueryModel<SysExcelStoreExtDto> qm) {

		PageModel<SysExcelStoreExtDto> pm = new PageModel<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("name", qm.getEntity().getName());
		map.put("isDeal", qm.getEntity().getIsDeal());
		map.put("type", qm.getEntity().getType());
		map.put("accountName", qm.getEntity().getAccountName());
		map.put("accountPk", qm.getEntity().getAccountPk());
		List<SysExcelStoreExtDto> list = sysExcelStoreExtDao.searchGridExt(map);
		int count = sysExcelStoreExtDao.searchGridExtCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int saveReportExcelToOss(ReportFormsDataTypeParams params, ManageAccount account, String methodName,
			String name, int flag) {
		String paramsName = "";
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName(methodName);
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(new Date());
		store.setName(name);
		store.setAccountPk(account.getPk());
		store.setType(Constants.FOUR);
		if (flag == 1) {// 物流中心--物流供应商报表
			paramsName = SupplierDataReportRunnable.supplierDataReportParam(params, store);
		} else if (flag == 2) {// 金融中心--银行审批额度
			paramsName = BankApproveAmountRunnable.bankApproveAmountParam(params);
		} else if (flag == 3) {// 金融中心--金融产品使用情况
			paramsName = EconProductUseRunnable.econProductUseParam(params);
		} else if (flag == 4) {// 金融中心--供应商金融产品交易
			paramsName = EconProdBussRunnable.econProdBussParam(params);
		} else if (flag == 5) {// 金融中心--金融产品使用余额统计表
			paramsName = ProductBalanceRunnable.productBalanceParam(params);
		} else if (flag == 6) {// 物流中心--毛利报表
			paramsName = GrossProfitReportRunnable.grossProfitReportParam(params, store);
		} else if (flag == 7) {// 物流中心--客户报表
			paramsName = CustomerReportRunnable.customerReportParam(params, store);
		} else if (flag == 8) {// 营销中心--产品更新表
			paramsName = GoodsUpdateReportRunnable.goodsUpdateReportParam(params, store);
		} else if (flag == 9) {// 营销中心--供应商销售报表
			paramsName = SupplierSaleReportRunnable.supplierSaleReportParam(params, store);
		} else if (flag == 10) {// 营销中心--业务员交易明细
			paramsName = SalemanSaleDetailReportRunnable.salemanSaleDetailReportParam(params, store);
		} else if (flag == 11) {// 营销中心--采购商交易统计
			paramsName = PurchaserSaleReportRunnable.purchaserSaleReportParam(params, store);
		} else if (flag == 12) {// 营销中心--供应商销售数据
			paramsName = SupplierSaleDataReportRunnable.supplierSaleDataReportParam(params, store);
		} else if (flag == 13) {// 营销中心--平台销售数据
			paramsName = ShopSaleDataReportRunnable.shopSaleDataReportParam(params, store);
		} else if (flag == 14) {// 运营中心--会员数据
			paramsName = MemberDataReportRunnable.memberDataReportParam(params, store);
		} else if (flag == 15) {// 运营中心--交易总览
			paramsName = BussOverviewRunnable.bussOverviewParam(params, store);
		} else if (flag == 16) {// 运营中心--店铺日交易数据
			paramsName = BussStoreDataRunnable.bussStoreDataParam(params, store);
		} else if (flag == 17) {// 运营中心--店铺日交易金融数据
			paramsName = BussEconStoreRunnable.bussEconStoreParam(params, store);
		} else if (flag == 18) {// 运营中心-采购商日交易金融数据
			paramsName = BussEconPurchaserDataRunnable.BussEconPurchaserParam(params, store);
		} else if (flag == 19) {// 运营中心-流量数据
			paramsName = FlowDataRunnable.flowDataParam(params, store);
		}
		store.setParamsName(paramsName);
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public int saveCustomerExcelToOss(CustomerDataTypeParams params, ManageAccount account, String methodName,
			String name, int flag) {
		String paramsName = "";
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName(methodName);
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(new Date());
		store.setName(name);
		store.setAccountPk(account.getPk());
		store.setType(Constants.ONE);
		if (flag == 1) {// 物流中心--发票管理（供应商）
			paramsName = SupplierInvoiceFormRunnable.supplierInvoiceParam(params, store);
		} else if (flag == 2) {// 物流中心--物流供应商结算
			paramsName = SupplierSettleFormRunnable.supplierSettleParam(params, store);
		} else if (flag == 3) {// 物流中心--发票管理（采购商）
			paramsName = PurchaserInvoiceFormRunnable.purchaserInvoiceParam(params, store);
		} else if (flag == 4) {// 物流中心--线路管理
			paramsName = LogisticsRouteRunnable.logisticsRouteParam(params, store);
		} else if (flag == 5) {// 会员体系--奖励赠送记录
			paramsName = DimensionalityHistoryRunnable.dimensionalityHistoryParam(params, store);
		} else if (flag == 6) {// 会员体系--额外奖励赠送
			paramsName = DimensionalityExtPresentRunnable.dimensionalityExtPresentParam(params, store);
		} else if (flag == 7) {
			paramsName = SysNewsStorageRunnable.sysNewsStorageParam(params, store);
		} else if (flag == 8) {
			paramsName = ImprovingdataRunnable.ImprovingdataParam(params, store);
		}

		store.setParamsName(paramsName);
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public int saveOrderExcelToOss(OrderDataTypeParams params, ManageAccount account, String methodName, String name,
			int flag) {
		String paramsName = "";
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName(methodName);
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(new Date());
		store.setName(name);
		store.setAccountPk(account.getPk());
		store.setType(Constants.TWO);
		if (flag == 1) {// 物流中心--正常订单
			paramsName = LogisticsOrderRunnable.logisticsOrderParam(params, store);
		} else if (flag == 2) {
			paramsName = LogisticsOrderRunnable.logisticsAbOrderParam(params, store);
		} else if (flag == 3) {
			paramsName = BillOrderRunnable.billOrderParam(params, store);
		} else if (flag == 4) {
			if (params.getPaymentType() != null && !params.getPaymentType().equals("")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", params.getPaymentType());
				List<B2bPaymentDto> list = b2bPaymentDao.searchList(map);
				if (list != null && list.size() > 0) {
					params.setPaymentName(list.get(0).getName());
				}
			}

			paramsName = MContractRunnable.ContactParam(params, store);
		}
		store.setParamsName(paramsName);
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public List<B2bOrderGoodsExtDto> searchOrderGoods(String orderNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNumber", orderNumber);
		map.put("noOrderStatus", -1);
		List<B2bOrderGoodsExtDto> list = b2bOrderGoodsExtDao.getB2bOrderGoods(map);
		if (list != null && list.size() > 0) {
			for (B2bOrderGoodsExtDto dto : list) {
				CfGoods cfGoods = JSONObject.parseObject(dto.getGoodsInfo(), CfGoods.class);
				dto.setCfGoods(cfGoods);
			}
		}
		return list;
	}

	@Override
	public void sureDeliveryOrderStatus(String deliveryNumber, Integer status) {
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("deliveryNumber").is(deliveryNumber));
		Query query = new Query(c);
		Update update = Update.update("status", status);
		mongoTemplate.updateFirst(query, update, DeliveryOrder.class);
	}

}
