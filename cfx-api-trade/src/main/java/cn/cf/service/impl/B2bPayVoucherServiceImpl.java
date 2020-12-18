package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.OrderRecordType;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.Sessions;
import cn.cf.entity.SupplierInfo;
import cn.cf.service.B2bPayVoucherService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class B2bPayVoucherServiceImpl implements B2bPayVoucherService {

	@Autowired
	private MongoTemplate mongoTemplate;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addPayVoucher(B2bOrderDtoEx orderEx, String imgUrl,int type) {
		OrderRecord or = new OrderRecord();
		String content = null;
		String mongoId = KeyUtils.getUUID();
		or.setId(mongoId);
		or.setOrderNumber(orderEx.getOrderNumber());
		or.setInsertTime(DateUtil.getDateFormat(new Date()));
		if (type==1) {
			content = OrderRecordType.UPDATE.toString() + "订单号:"
					+ orderEx.getOrderNumber() + "上传凭证:<img src='" + imgUrl
					+ "' style='display:block;'/>";
		}
		if (type==2) {
			content = OrderRecordType.CUPDATE.toString() + "合同号:"
					+ orderEx.getOrderNumber() + "上传凭证:<img src='" + imgUrl
					+ "' style='display:block;'/>";
		}
		or.setContent(content);
		or.setType(1);
		or.setImgUrl(imgUrl);
		mongoTemplate.insert(or);
		// 存付款凭证表
		B2bPayVoucher voucher = new B2bPayVoucher();
		voucher.setId(KeyUtils.getUUID());
		voucher.setUrl(imgUrl);
		voucher.setOrderNumber(orderEx.getOrderNumber());
		voucher.setInsertTime(DateUtil.formatDateAndTime(new Date()));
		voucher.setType(1);
		voucher.setStatus(1);
		PurchaserInfo pinfo = new PurchaserInfo(orderEx.getPurchaserInfo());
		voucher.setPurchaserName(pinfo.getPurchaserName());
		SupplierInfo sinfo = new SupplierInfo(orderEx.getSupplierInfo());
		voucher.setSupplierName(sinfo.getSupplierName());
		voucher.setPurchaserPk(orderEx.getPurchaserPk());
		voucher.setSupplierPk(orderEx.getSupplierPk());
		voucher.setStorePk(orderEx.getStorePk());
		voucher.setEmployeePk(orderEx.getEmployeePk());
		voucher.setEmployeeName(orderEx.getEmployeeName());
		voucher.setEmployeeNumber(orderEx.getEmployeeNumber());
		mongoTemplate.insert(voucher);
	}

	@Override
	public List<B2bPayVoucher> searchB2bPayVoucherList(String orderNumber) {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("orderNumber").is(orderNumber),
				Criteria.where("type").is(1));
		return mongoTemplate.find(new Query(criatira), B2bPayVoucher.class);

	}

	@Override
	public PageModel<B2bPayVoucher> searchB2bPayVoucherList(
			Map<String, Object> map, Sessions session) {
		PageModel<B2bPayVoucher> pm = new PageModel<B2bPayVoucher>();
		if (null != map.get("searchType")
				&& "3".equals(map.get("searchType").toString())) {
			if (session.getIsAdmin() != 1) {
				if (null == session.getMemberDto().getParentPk()
						|| "".equals(session.getMemberDto().getParentPk())
						|| "-1".equals(session.getMemberDto().getParentPk())) {
					map.put("employeePk", session.getMemberDto().getPk());
				} else {
					map.put("employeePk", session.getMemberDto().getParentPk());
				}
			}
		}
		Query query = queryVoucher(map, session.getCompanyDto(),
				session.getStoreDto());
		query.with(new Sort(Direction.DESC, "insertTime"));
		Integer start = Integer.parseInt(map.get("start").toString());
		Integer limit = Integer.parseInt(map.get("limit").toString());
		query.skip(start).limit(limit);
		List<B2bPayVoucher> list = mongoTemplate.find(query,
				B2bPayVoucher.class);
		int counts = (int) mongoTemplate.count(query, B2bPayVoucher.class);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		pm.setStartIndex(start);
		pm.setPageSize(limit);
		return pm;
	}

	@Override
	public String updatePayVoucher(String id, Integer status) {
		RestCode code = RestCode.CODE_0000;
		try {

			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				Update update = new Update();
				update.set("status", status);
				update.set("insertTime", DateUtil.formatDateAndTime(new Date()));
				mongoTemplate.upsert(
						new Query(Criteria.where("id").is(ids[i])), update,
						B2bPayVoucher.class);
			}
		} catch (Exception e) {
			logger.error("errorUpdatePayVoucher", e);
			code = RestCode.CODE_S999;
		}
		return code.toJson();
	}

	@Override
	public Map<String, Object> searchB2bPayVoucherCounts(
			Map<String, Object> map, Sessions session) {
		if (null != map.get("searchType")
				&& "3".equals(map.get("searchType").toString())) {
			if (session.getIsAdmin() != 1) {
				if (null == session.getMemberDto().getParentPk()
						|| "".equals(session.getMemberDto().getParentPk())
						|| "-1".equals(session.getMemberDto().getParentPk())) {
					map.put("employeePk", session.getMemberDto().getPk());
				} else {
					map.put("employeePk", session.getMemberDto().getParentPk());
				}
			}
		}
		map.put("status", 1);// 未入账
		Query ndmission = queryVoucher(map, session.getCompanyDto(),
				session.getStoreDto());
		int ncounts = (int) mongoTemplate.count(ndmission, B2bPayVoucher.class);
		map.put("status", 2);// 已入账
		Query ydmission = queryVoucher(map, session.getCompanyDto(),
				session.getStoreDto());
		int ycounts = (int) mongoTemplate.count(ydmission, B2bPayVoucher.class);
		map.put("status", 3);// 失效
		Query fail = queryVoucher(map, session.getCompanyDto(),
				session.getStoreDto());
		int fcounts = (int) mongoTemplate.count(fail, B2bPayVoucher.class);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ncounts", ncounts);
		result.put("ycounts", ycounts);
		result.put("fcounts", fcounts);
		result.put("allcounts", fcounts + ycounts + ncounts);
		return result;
	}

	private Query queryVoucher(Map<String, Object> map, B2bCompanyDto company,
			B2bStoreDto store) {
		Criteria c = new Criteria();
		// c = Criteria.where("_id").nin("");
		if (null != map.get("purchaserName")) {
			c.and("purchaserName").regex(escapeExprSpecialWord(map.get("purchaserName").toString()));
		}
		if (null != map.get("accountedTimeBegin")
				&& null == map.get("accountedTimeEnd")) {
			c.and("insertTime").gte(
					map.get("accountedTimeBegin").toString() + " 00:00:00");
		}
		if (null != map.get("accountedTimeEnd")
				&& null == map.get("accountedTimeBegin")) {
			c.and("insertTime").lte(
					map.get("accountedTimeEnd").toString() + " 23:59:59");
		}
		if (null != map.get("accountedTimeEnd")
				&& null != map.get("accountedTimeBegin")) {
			c.andOperator(
					Criteria.where("insertTime").gte(
							map.get("accountedTimeBegin").toString()
									+ " 00:00:00"),
					Criteria.where("insertTime").lte(
							map.get("accountedTimeEnd").toString()
									+ " 23:59:59"));
		}
		if (null != map.get("employeePk")) {
			List<String> list = new ArrayList<String>();
			String[] strs = map.get("employeePk").toString().split(",");
			for (int i = 0; i < strs.length; i++) {
				list.add(strs[i]);
			}
			c.and("employeePk").in(list);
		}
		if (null != map.get("status")) {
			c.and("status").is(Integer.parseInt(map.get("status").toString()));
		}
		if(null != map.get("type")){
			c.and("type").is(Integer.parseInt(map.get("type").toString()));
		}
		if ("-1".equals(company.getParentPk())) {
			c.and("storePk").is(store.getPk());
		} else {
			c.and("supplierPk").is(company.getPk());
		}
		return new Query(c);
	}
	
	
	 /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
   public static String escapeExprSpecialWord(String keyword) {
       String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
       for (String key : fbsArr) {
           if (keyword.contains(key)) {
               keyword = keyword.replace(key, "\\" + key);
           }
       }
       return keyword;
   }

	

}
