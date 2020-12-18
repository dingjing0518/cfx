package cn.cf.task.schedule.economics;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bBillOrderExtDao;
import cn.cf.dao.B2bProductPriceIndexDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bBillOrderExtDto;
import cn.cf.dto.B2bProductPriceIndexDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.ProductPriceIndexEntry;
import cn.cf.model.SysExcelStore;
import cn.cf.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductPriceIndexRunnable implements Runnable {
	private String fileName;
	private String uuid;

	private MongoTemplate mongoTemplate;
	private B2bProductPriceIndexDao b2bProductPriceIndexDao;

	public ProductPriceIndexRunnable() {

	}

	public ProductPriceIndexRunnable(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public void run() {

		this.mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		SysExcelStoreExtDao storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		b2bProductPriceIndexDao = (B2bProductPriceIndexDao) BeanUtils.getBean("b2bProductPriceIndexDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportPriceIndex_" + StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.mongoTemplate = mongoTemplate;
					this.fileName = "金融中心-数据管理-成交价格指数-" + storeDto.getAccountName() + "-"
							+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						if (this.mongoTemplate != null) {
							// 设置查询参数
							Query query = params(storeDto.getParams());
							// 设置权限
							String ossPath = "";
							List<ProductPriceIndexEntry> nowList = this.mongoTemplate.find(query,
									ProductPriceIndexEntry.class);
							List<ProductPriceIndexEntry> resList = setPrice(nowList);
							long counts = resList.size();
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(query, counts, resList);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(resList);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto, storeDao, ossPath);
						}
					}
				}
			}
		}
	}

	private String setNotLimitPages(List<ProductPriceIndexEntry> list) {
		String ossPath = "";
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<ProductPriceIndexEntry> exportUtil = new ExportUtil<>();
			String path = exportUtil.exportDynamicUtil("priceProductIndex", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}

	private String setLimitPages(Query query, long counts, List<ProductPriceIndexEntry> list) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			int pageNum = start / Constants.EXCEL_NUMBER_TENHOUSAND + 1;
			List<ProductPriceIndexEntry> reslist = startPage(list, pageNum, Constants.EXCEL_NUMBER_TENHOUSAND);
			query.skip(start).limit(Constants.EXCEL_NUMBER_TENHOUSAND);
			if (reslist != null && reslist.size() > Constants.ZERO) {
				ExportUtil<ProductPriceIndexEntry> exportUtil = new ExportUtil<>();
				String excelName = this.fileName + "-" + (i + 1);
				String path = exportUtil.exportDynamicUtil("priceProductIndex", excelName, reslist);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	/**
	 * 开始分页
	 * 
	 * @param tempList
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页多少条数据
	 * @return
	 */
	public List<ProductPriceIndexEntry> startPage(List<ProductPriceIndexEntry> tempList, Integer pageNum,
			Integer pageSize) {
		if (tempList == null) {
			return null;
		}
		if (tempList.size() == 0) {
			return null;
		}

		Integer count = tempList.size(); // 记录总数
		Integer pageCount = 0; // 页数
		if (count % pageSize == 0) {
			pageCount = count / pageSize;
		} else {
			pageCount = count / pageSize + 1;
		}

		int fromIndex = 0; // 开始索引
		int toIndex = 0; // 结束索引

		if (pageNum != pageCount) {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = fromIndex + pageSize;
		} else {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = count;
		}

		List<ProductPriceIndexEntry> pageList = tempList.subList(fromIndex, toIndex);

		return pageList;
	}

	private List<ProductPriceIndexEntry> setPrice(List<ProductPriceIndexEntry> list) {

		List<ProductPriceIndexEntry> tempList = new ArrayList<>();
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("isVisable", Constants.ONE);
		List<B2bProductPriceIndexDto> priceIndexDtoList = this.b2bProductPriceIndexDao.searchList(searchMap);
		Map<String, Integer> keyMap = new HashMap<>();
		if (priceIndexDtoList != null && priceIndexDtoList.size() > 0) {
			for (B2bProductPriceIndexDto dto : priceIndexDtoList) {
				keyMap.put(dto.getProductPk(), dto.getPriceIndex());
			}
		}

		for (ProductPriceIndexEntry entry : list) {
			if (keyMap != null) {
				Integer price = keyMap.get(entry.getProductPk());
				entry.setNowPriceIndex(price == null ? 0 : price);
				// 计算涨跌幅
				if (price != null && entry.getPriceIndex() != null) {
					// 涨跌幅=(当前价格指数-成交价格指数)/成交价格指数
					DecimalFormat dF = new DecimalFormat("0.00");
					Integer dividePrice = price - entry.getPriceIndex();
					float i = (float) dividePrice / entry.getPriceIndex();
					double percent = Double.parseDouble(dF.format((i < 0.1 && i > -0.1) ? 0d : i));
					entry.setRiseAndFall(percent * 100);
					double floorPer = Math.ceil(percent * 10);
					// 需缴纳保证金
					// 需补交保证金XXX元=该商品的未发吨数*商品单价*涨跌幅-已缴纳保证金（用户在确认窗口填写的本次缴纳保证金金额累加
					if (floorPer < 0d) {
						entry.setRiseAndFall(floorPer * 10);
						if (entry.getWeightShipped() != null) {
							double weightShipped = entry.getWeightShipped().doubleValue();
							double presentPrice = entry.getPresentPrice() == null ? 0d
									: entry.getPresentPrice().doubleValue();
							double depositMount = entry.getDepositMount() == null ? 0d
									: entry.getDepositMount().doubleValue();
							// 应缴金额
							double dueofpayMount = weightShipped * presentPrice * Math.abs(floorPer/10) - depositMount;
							if (dueofpayMount > 0d) {
								entry.setDueofpayMount(dueofpayMount);
							}
						}

						if (entry.getLoanStatus() == Constants.SIX) {
							entry.setLoanStatusName("部分还款");
						}
						if (entry.getLoanStatus() == Constants.THREE) {
							entry.setLoanStatusName("放款成功");
						}
						if (entry.getIsConfirm() == Constants.ONE) {
							entry.setIsConfirmName("未确认");
						}
						if (entry.getIsConfirm() == Constants.TWO) {
							entry.setIsConfirmName("已确认");
						}
						tempList.add(entry);
					}
				}
			}

		}
		return tempList;
	}

	private Query params(String params) {

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("_id").nin(""));
		criteria.and("priceIndex").exists(true);
		Query query;
		if (CommonUtil.isNotEmpty(params)) {
			JSONObject json = JSONObject.parseObject(params);
			if (json.containsKey("goodsInfo") && CommonUtil.isNotEmpty(json.getString("goodsInfo"))) {
				criteria.and("goodsInfo").regex(json.getString("goodsInfo"));
			}
			if (json.containsKey("purchaserName") && CommonUtil.isNotEmpty(json.getString("purchaserName"))) {
				criteria.and("purchaserName").is(json.getString("purchaserName"));
			}
			if (json.containsKey("loanNumber") && CommonUtil.isNotEmpty(json.getString("loanNumber"))) {
				criteria.and("loanNumber").is(json.getString("loanNumber"));
			}
			if (json.containsKey("isConfirm") && json.getInteger("isConfirm") != null) {
				criteria.and("isConfirm").is(json.getInteger("isConfirm"));
			}
			String start = "";
			if (json.containsKey("repaymentTimeStart") && CommonUtil.isNotEmpty(json.getString("repaymentTimeStart"))) {
				start = json.getString("repaymentTimeStart");
			}
			String end = "";
			if (json.containsKey("repaymentTimeEnd") && CommonUtil.isNotEmpty(json.getString("repaymentTimeEnd"))) {
				end = json.getString("repaymentTimeEnd");
			}
			if (CommonUtil.isNotEmpty(start) && CommonUtil.isNotEmpty(end)) {
				criteria.and("repaymentTime").gte(start).lte(end);
			}
			if (!CommonUtil.isNotEmpty(start) && CommonUtil.isNotEmpty(end)) {
				criteria.and("repaymentTime").lte(end);
			}
			if (CommonUtil.isNotEmpty(start) && !CommonUtil.isNotEmpty(end)) {
				criteria.and("repaymentTime").gte(start);
			}

			query = new Query(criteria);
		} else {
			query = new Query(criteria);
		}
		return query;
	}
}
