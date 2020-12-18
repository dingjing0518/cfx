package cn.cf.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.constant.BillType;
import cn.cf.constant.Block;
import cn.cf.constant.Constants;
import cn.cf.constant.GoodsType;
import cn.cf.constant.MemberSys;
import cn.cf.constant.OrderRecordType;
import cn.cf.constant.RestCode;
import cn.cf.constant.SmsCode;
import cn.cf.constant.UnitType;
import cn.cf.dao.CommonDao;
import cn.cf.dao.TradeDao;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.dto.B2bDimensionalityRewardDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bGoodsBrandDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.dto.B2bMemberGroupDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgLineDto;
import cn.cf.dto.LgLinePriceDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.dto.SysRegionsDto;
import cn.cf.entity.B2bBillCusgoodsDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.CfGoods;
import cn.cf.entity.DeliveryBasic;
import cn.cf.entity.DeliveryGoods;
import cn.cf.entity.DeliveryOrder;
import cn.cf.entity.ForB2BLgPriceDto;
import cn.cf.entity.LgDeliveryOrderForB2BDto;
import cn.cf.entity.LgDeliveryOrderForB2BPTDto;
import cn.cf.entity.LgMemberDeliveryOrderDtoMa;
import cn.cf.entity.LgSearchPriceForB2BDto;
import cn.cf.entity.LogisticsLinePriceDto;
import cn.cf.entity.LogisticsRouteDto;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.MemberPointErrorInfo;
import cn.cf.entity.MemberPointPeriod;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.SearchLgLine;
import cn.cf.entity.SupplierInfo;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.model.B2bDimensionalityPresent;
import cn.cf.model.B2bGoodsBrand;
import cn.cf.model.B2bMember;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bOrderGoods;
import cn.cf.model.B2bPlant;
import cn.cf.model.B2bWare;
import cn.cf.model.B2bWarehouseNumber;
import cn.cf.model.LgDeliveryOrder;
import cn.cf.model.LgDeliveryOrderGoods;
import cn.cf.model.LgTrackDetail;
import cn.cf.property.PropertyConfig;
import cn.cf.service.CommonService;
import cn.cf.service.CuccSmsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.InterestUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.PdfFooterEvent;
import cn.cf.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

@Service
public class CommonServiceImpl implements CommonService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonDao commonDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TradeDao tradeDao;
    @Autowired
    private CuccSmsService cuccSmsService;
 
 
 
 
    @Override
    public List<MangoMemberPoint> searchPointList(Map<String, String> map) {
        Query query = new Query();
        for (String key : map.keySet()) {
            query.addCriteria(Criteria.where(key).is(map.get(key)));
        }
        // 1查询用户积分记录
        List<MangoMemberPoint> list = mongoTemplate.find(query, MangoMemberPoint.class);
        // 2查询用户插入失败的积分记录
        List<MemberPointErrorInfo> errorList = mongoTemplate.find(query, MemberPointErrorInfo.class);
        // 3合并两次的记录
        for (MemberPointErrorInfo memberPointErrorInfo : errorList) {
            MangoMemberPoint point = new MangoMemberPoint();
            try {
                BeanUtils.copyProperties(point, memberPointErrorInfo);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(point);
        }
        return list;
    }

    /**
     * 会员体系埋点方法（正常外奖励）
     *
     * @param memberPk
     * @param companyPk
     * @param pointType
     * @return
     */
    @Override
    @Transactional
    public RestCode addPoint(String memberPk, String companyPk, Integer pointType, String dimenType) {
        RestCode restCode = RestCode.CODE_0000;
        MangoMemberPoint point = new MangoMemberPoint();
        try {
            point.setDimenType(dimenType);
            // 1.查询交易相关具体的经验值
            B2bDimensionalityRewardDto dto = searchDimension(dimenType);
            // 2 插入记录
            if (null != dto) {
                insertMember(memberPk, companyPk, point, dto, pointType, null);
            } else {
            }
        } catch (Exception e) {
            restCode = RestCode.CODE_S999;
        } finally {
            // 添加插入失败的记录存mango
            // addErrorRecord(restCode, point);
        }
        return restCode;
    }


    /**
     * 查找对应的维度值
     *
     * @param dimenType
     * @return
     */
    private B2bDimensionalityRewardDto searchDimension(String dimenType) {
        B2bDimensionalityRewardDto dto = null;
//        String json = "";
//        if (JedisUtils.get(dimenType).equals(false)) {// 先从缓存取
            String[] strArr = dimenType.split("_");
            Map<String, Object> map = new HashMap<>();
            map.put("dimenCategory", strArr[0]);
            map.put("dimenType", strArr[1]);
            map.put("isDelete", 1);
            map.put("isVisable", 1);
            List<B2bDimensionalityRewardDto> list = commonDao.searchDimensionalityRewardList(map);
            if (list != null && list.size() > 0) {
                dto = list.get(0);
            }
//        } else {
//            json = JsonUtils.convertToString(JedisUtils.get(dimenType));
//            if (json != null) {
//                dto = JSONObject.parseObject(json, B2bDimensionalityRewardDto.class);
//            }
//        }
        return dto;
    }



    /**
     * @param memberPk
     * @param companyPk
     * @param point
     * @param dto
     * @param pointType
     */
    private void insertMember(String memberPk, String companyPk, MangoMemberPoint point, B2bDimensionalityRewardDto dto,
                              Integer pointType, String thing) {
        B2bMemberDto memberDto = commonDao.getMemberByPk(memberPk);
        B2bCompanyDto companyDto = commonDao.getCompanyByPk(companyPk);
        if(null != memberDto && null != companyDto){
        	Double memberfbGradeRatio = 0.00;
        	Double memberemGradeRatio = 0.00;
        	Integer periodType = dto.getPeriodType();
        	String category = dto.getDimenCategory().toString();
        	String dimenType = point.getDimenType();
        	Date currentDate = new Date();
        	String today = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("dimenType", point.getDimenType());
        	map.put("memberPk", memberDto.getPk());
        	if (periodType == 2) map.put("insertTime", today);
        	List<MangoMemberPoint> list = this.searchPointList(map);
        	if (list != null && list.size() > 0) {
        		return;
        	}
        	String parentCompanyPk = "-1";
        	if (category.equals(MemberSys.GOODS_DIMEN.getValue()) || dimenType.equals(MemberSys.ACCOUNT_DIMEN_WARE.getValue()) ||
        			dimenType.equals(MemberSys.ACCOUNT_DIMEN_PLANT.getValue()) || dimenType.equals(MemberSys.ACCOUNT_DIMEN_LGTEP.getValue()) ||
        			dimenType.equals(MemberSys.ACCOUNT_DIMEN_INV.getValue())) {
        		B2bCompanyDto company = commonDao.getCompanyByPk(companyPk);
        		if (company != null && company.getParentPk() != null && !company.getParentPk().equals("-1")) {
        			parentCompanyPk = company.getParentPk();
        			map = new HashMap<String, String>();
        			map.put("dimenType", point.getDimenType());
        			map.put("companyPk", companyPk);
        			if (periodType == 2){
        				map.put("insertTime", today);
        			}
        			list = this.searchPointList(map);
        			map = new HashMap<String, String>();
        			map.put("dimenType", point.getDimenType());
        			map.put("companyPk", parentCompanyPk);
        			if (periodType == 2){
        				map.put("insertTime", today);
        			}
        			List<MangoMemberPoint> list2 = this.searchPointList(map);
        			list.addAll(list2);
        			map = new HashMap<String, String>();
        			map.put("dimenType", point.getDimenType());
        			map.put("parentCompanyPk", parentCompanyPk);
        			if (periodType == 2){
        				map.put("insertTime", today);
        			}
        			List<MangoMemberPoint> list3 = this.searchPointList(map);
        			list.addAll(list3);
        		} else if (company != null && company.getParentPk() != null && company.getParentPk().equals("-1")) {
        			map = new HashMap<String, String>();
        			map.put("dimenType", point.getDimenType());
        			map.put("companyPk", companyPk);
        			if (periodType == 2){
        				map.put("insertTime", today);
        			}
        			list = this.searchPointList(map);
        			map = new HashMap<String, String>();
        			map.put("dimenType", point.getDimenType());
        			map.put("parentCompanyPk", companyPk);
        			if (periodType == 2){
        				map.put("insertTime", today);
        			}
        			List<MangoMemberPoint> list2 = this.searchPointList(map);
        			list.addAll(list2);
        		}
        		if ((list != null && list.size() > 0)) {
        			return;
        		}
        	}
        	// 1.加分所需的系数比例
        	// 1.1等级系数
        	if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
        		B2bMemberGradeDto gradeDto = commonDao.getDtoByGradeNumber(
        				(memberDto.getLevel() == null || memberDto.getLevel().equals("")) ? 1 : memberDto.getLevel());
        		if (gradeDto != null) {
        			memberfbGradeRatio = gradeDto.getFbGradeRatio();
        			memberemGradeRatio = gradeDto.getEmGradeRatio();
        		}
        	}
        	// 1.2加权后系数
        	Double totalemRatio = ArithUtil.mul(dto.getEmpiricalValue(), dto.getEmpiricalRatio());
        	Double totalfbRatio = ArithUtil.mul(dto.getFibreShellNumber(), dto.getFibreShellRatio());
        	// 2.插入mongo总分
        	Double totalEmpiricalValue = ArithUtil.mul(totalemRatio, memberemGradeRatio);
        	Double totalfbGradeValue = ArithUtil.mul(totalfbRatio, memberfbGradeRatio);
        	point.setCompanyPk(companyPk);
        	if (category.equals(MemberSys.GOODS_DIMEN.getValue()) || dimenType.equals(MemberSys.ACCOUNT_DIMEN_WARE.getValue()) ||
        			dimenType.equals(MemberSys.ACCOUNT_DIMEN_PLANT.getValue()) || dimenType.equals(MemberSys.ACCOUNT_DIMEN_LGTEP.getValue()) ||
        			dimenType.equals(MemberSys.ACCOUNT_DIMEN_INV.getValue()))  point.setParentCompanyPk(parentCompanyPk);
        	point.setMemberPk(memberPk);
        	point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
        	point.setExpValue(ArithUtil.round((pointType == 2 ? (-totalEmpiricalValue) : totalEmpiricalValue), 2));
        	point.setFiberValue(ArithUtil.round((pointType == 2 ? (-totalfbGradeValue) : totalfbGradeValue), 2));
        	point.setOrderNumber(thing);
        	mongoTemplate.insert(point);
        	// 3. 更新用户总积分
        	updateMemberValue(memberPk,totalEmpiricalValue,totalfbGradeValue);
        	// 4.插入加分明细
        	addB2bDimensionalityPresent(dto, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio,ArithUtil.round(totalfbRatio,2),
        			ArithUtil.round(totalemRatio,2));
        }
    }

    private void updateMemberValue(String memberPk, Double expValue, Double fiberValue) {
        B2bMemberDto memberDto = this.commonDao.getMemberByPk(memberPk);
        B2bMember member = new B2bMember();
        member.setPk(memberPk);
        member.setShell((memberDto.getShell() == null ? 0 : memberDto.getShell()) + ArithUtil.round(fiberValue, 0));
        member.setExperience((memberDto.getExperience() == null ? 0 : memberDto.getExperience()) + ArithUtil.round(expValue, 0));
        member.setFeedTime(new Date());
        commonDao.updateMember(member);
    }



	private void addB2bDimensionalityPresent(B2bDimensionalityRewardDto dto, B2bMemberDto memberDto,
			B2bCompanyDto companyDto, Double memberfbGradeRatio, Double memberemGradeRatio, Double totalfbRatio,
			Double totalemRatio) {
		B2bDimensionalityPresent d = new B2bDimensionalityPresent();
		d.setPk(KeyUtils.getUUID());
		d.setRewardPk(dto.getPk());
		d.setCompanyPk(companyDto.getPk());
		d.setCompanyName(companyDto.getName());
		d.setMemberPk(memberDto.getPk());
		d.setMemberName(memberDto.getEmployeeName());
		d.setContactsTel(companyDto.getContactsTel());
		d.setDimenCategory(dto.getDimenCategory());
		d.setDimenName(dto.getDimenName());
		d.setDimenType(dto.getDimenType());
		d.setDimenTypeName(dto.getDimenTypeName());
		d.setPeriodType(dto.getPeriodType());
		d.setFibreShellNumber(dto.getFibreShellNumber());
		d.setFibreShellRatio(dto.getFibreShellRatio());
		d.setEmpiricalValue(dto.getEmpiricalValue());
		d.setEmpiricalRatio(dto.getEmpiricalRatio());
		d.setFbGradeRatio(memberfbGradeRatio);
		d.setEmGradeRatio(memberemGradeRatio);
		d.setFbShellNumberWeighting(totalfbRatio);
		d.setEmpiricalValueWeighting(totalemRatio);
		d.setType(1);
		this.commonDao.insertDimensionalityPresent(d);
	}



	@Override
	public Map<String, Object> getGoodsByMember(Map<String, Object> map) {
		List<B2bCustomerSalesmanDto> cus = this.commonDao.searchGoodsBySaleMan(map);
		String sqlStr = "";
		if(null !=cus){
			for (B2bCustomerSalesmanDto cdto : cus) {
				if(!"".equals(sqlStr)){
					sqlStr +=" or";
				}
				if(map.containsKey("sxFlag")){
					if( null !=cdto.getFiliale() && !"".equals(cdto.getFiliale())){
						sqlStr +=" g.companyPk = '"+cdto.getFiliale()+"' ";
						continue;
					}
				}else{
					if(null!=cdto.getProductPk() && !"".equals(cdto.getProductPk()) &&  null !=cdto.getFiliale() && !"".equals(cdto.getFiliale())){
					//	sqlStr +="( productPk = '"+cdto.getProductPk()+"' and companyPk = '"+cdto.getFiliale()+"' )";
						sqlStr +="FIND_IN_SET('\"productPk\":\"" + cdto.getProductPk() + "\"',goodsInfo) and companyPk = '"+cdto.getFiliale()+"' ";;
						
						continue;
					}
					if(null!=cdto.getProductPk() && !"".equals(cdto.getProductPk()) &&  (null ==cdto.getFiliale() || "".equals(cdto.getFiliale()))){
						//sqlStr +="( productPk = '"+cdto.getProductPk()+"' )";
						sqlStr +="FIND_IN_SET('\"productPk\":\"" + cdto.getProductPk() + "\"',goodsInfo)";;
						
						continue;
					}
					if((null==cdto.getProductPk() || "".equals(cdto.getProductPk())) &&  null !=cdto.getFiliale() && !"".equals(cdto.getFiliale())){
						sqlStr +="( companyPk = '"+cdto.getFiliale()+"' )";
						continue;
					}
				}
			}
			if(!"".equals(sqlStr)){
				map.put("sqlStr", sqlStr);
			}
		}else{
			map.put("sType", 1);
		}
		return map;
	}
 
 
	@Override
	public List<B2bMemberDto> getByMember(Map<String, Object> map) {
		return this.commonDao.searchMemberList(map);
	}


    /**
     * 收藏商品/店铺
     */
    @Override
    public RestCode addPointForThing(String memberPk, String companyPk, String thing, String dimenType) {
        RestCode restCode = RestCode.CODE_0000;
        MangoMemberPoint point = new MangoMemberPoint();
        Criteria criteria = new Criteria();
        criteria.and("memberPk").is(memberPk);
        criteria.and("dimenType").is(dimenType);
        Query query = new Query(criteria);
        boolean exists = mongoTemplate.exists(query, MangoMemberPoint.class);
        if (exists)
            return restCode;
        try {
            point.setDimenType(dimenType);
            // 1.查询交易相关具体的经验值
            B2bDimensionalityRewardDto dto = searchDimension(dimenType);
            // 2 插入记录
            if (dto != null) {
                insertMember(memberPk, companyPk, point, dto, 1, thing);
            }
        } catch (Exception e) {
            restCode = RestCode.CODE_S999;
        } finally {
            // 添加插入失败的记录存mango
            // addErrorRecord(restCode, point);
        }
        return restCode;
    }

    /**
     * 取消收藏**
     */
    @Override
    public void cancelThing(String tingPk, String memberPk) {
        Criteria c = new Criteria();
        if (tingPk != null && !tingPk.equals("")) {
            c = Criteria.where("tingPk").is(tingPk);
        } else {
            //取消全部的商品收藏
            c = Criteria.where("dimenType").is(MemberSys.STORE_DIMEN_GOODS.getValue());
        }
        c.andOperator(Criteria.where("memberPk").is(memberPk));
        Query query = new Query(c);
        Update update = Update.update("isDelete", 2);
        mongoTemplate.updateMulti(query, update, MemberPointPeriod.class);
    }


	/**
	 * 商城确认发货(商家承运)
	 */
	@Override
	public boolean confirmFaHuoForB2BSJ(LgDeliveryOrderForB2BDto dto) {
		// lg_delivery_order
		LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
		String deliveryPk = KeyUtils.getUUID();
		Date tempDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lgDeliveryOrder.setPk(deliveryPk);
		lgDeliveryOrder.setOrderPk(dto.getOrderPk() == null ? "" : dto.getOrderPk());
		lgDeliveryOrder.setParentPk("-1");
		lgDeliveryOrder.setOrderStatus(6);
		lgDeliveryOrder.setIsMatched(1);
		lgDeliveryOrder.setOriginalTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对外总价
		lgDeliveryOrder.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对内总价
		lgDeliveryOrder.setInsertTime(tempDate);
		lgDeliveryOrder.setUpdateTime(tempDate);
		Date orderTimeTemp;
		if (dto.getOrderTime() == null || dto.getOrderTime().equals("")) {
			orderTimeTemp = null;
		} else {
			try {
				orderTimeTemp = sdf.parse(dto.getOrderTime());
			} catch (ParseException e) {
				e.printStackTrace();
				orderTimeTemp = null;
			}
		}
		lgDeliveryOrder.setOrderTime(orderTimeTemp);
		lgDeliveryOrder.setLogisticsCompanyPk(dto.getLogisticsCompanyPk() == null ? "" : dto.getLogisticsCompanyPk());

		// System.out.println("===============================LogisticsCompanyPk:"+lgDeliveryOrderForB2BDto.getLogisticsCompanyPk());
		LgCompanyDto tempCompany = this.commonDao
				.getLgCompanyByPk(dto.getLogisticsCompanyPk() == null ? "" : dto.getLogisticsCompanyPk());
		if (null != tempCompany && null != tempCompany.getPk()) {
			lgDeliveryOrder.setLogisticsCompanyName(tempCompany.getName() == null ? "" : tempCompany.getName());
		}
		// System.out.println("================================:LogisticsCompanyName"+tempCompany.getName());
		lgDeliveryOrder.setFromCompanyName(dto.getFromCompanyName() == null ? "" : dto.getFromCompanyName());
		lgDeliveryOrder.setFromAddress(dto.getFromAddress() == null ? "" : dto.getFromAddress());
		lgDeliveryOrder.setFromContacts(dto.getFromContacts() == null ? "" : dto.getFromContacts());
		lgDeliveryOrder.setFromContactsTel(dto.getFromContactsTel() == null ? "" : dto.getFromContactsTel());
		lgDeliveryOrder.setFromProvincePk(dto.getFromProvincePk() == null ? "" : dto.getFromProvincePk());
		lgDeliveryOrder.setFromProvinceName(dto.getFromProvinceName() == null ? "" : dto.getFromProvinceName());
		lgDeliveryOrder.setFromCityPk(dto.getFromCityPk() == null ? "" : dto.getFromCityPk());
		lgDeliveryOrder.setFromCityName(dto.getFromCityName() == null ? "" : dto.getFromCityName());
		lgDeliveryOrder.setFromAreaPk(dto.getFromAreaPk() == null ? "" : dto.getFromAreaPk());
		lgDeliveryOrder.setFromAreaName(dto.getFromAreaName() == null ? "" : dto.getFromAreaName());
		lgDeliveryOrder.setFromTownPk(dto.getFromTownPk() == null ? "" : dto.getFromTownPk());
		lgDeliveryOrder.setFromTownName(dto.getFromTownName() == null ? "" : dto.getFromTownName());
		lgDeliveryOrder.setToCompanyName(dto.getToCompanyName() == null ? "" : dto.getToCompanyName());
		lgDeliveryOrder.setToAddress(dto.getToAddress() == null ? "" : dto.getToAddress());
		lgDeliveryOrder.setToContacts(dto.getToContacts() == null ? "" : dto.getToContacts());
		lgDeliveryOrder.setToContactsTel(dto.getToContactsTel() == null ? "" : dto.getToContactsTel());
		lgDeliveryOrder.setToProvinceName(dto.getToProvinceName() == null ? "" : dto.getToProvinceName());
		Map<String, Object> map=new HashMap<>();
		map.put("name", dto.getToProvinceName());
		map.put("parentPk", -1);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		SysRegionsDto provinceDto = this.commonDao.getRegionsByNameExM(map);
		if (null!=provinceDto && null!=provinceDto.getPk()) {
			lgDeliveryOrder.setToProvincePk(provinceDto.getPk()==null?"":provinceDto.getPk());
		}

		lgDeliveryOrder.setToCityName(dto.getToCityName() == null ? "" : dto.getToCityName());
		map.put("name", dto.getToCityName());
		map.put("parentPk", provinceDto.getPk()==null?"":provinceDto.getPk());
		SysRegionsDto cityDto = this.commonDao.getRegionsByNameExM(map);
		if (null!=cityDto && null!=cityDto.getPk()) {
			lgDeliveryOrder.setToCityPk(cityDto.getPk()==null?"":cityDto.getPk());
		}

		lgDeliveryOrder.setToAreaName(dto.getToAreaName() == null ? "" : dto.getToAreaName());
		map.put("name", dto.getToAreaName());
		map.put("parentPk", cityDto.getPk()==null?"":cityDto.getPk());
		SysRegionsDto areaDto = commonDao.getRegionsByNameExM(map);
		if (null!=areaDto && null!=areaDto.getPk()) {
			lgDeliveryOrder.setToAreaPk(areaDto.getPk()==null?"":areaDto.getPk());
		}

		lgDeliveryOrder.setToTownName(dto.getToTownName() == null ? "" : dto.getToTownName());
		map.put("name",dto.getToTownName());
		map.put("parentPk", areaDto.getPk()==null?"":areaDto.getPk());
		SysRegionsDto townDto = commonDao.getRegionsByNameExM(map);
		if (null!=townDto && null!=townDto.getPk()) {
			lgDeliveryOrder.setToTownPk(null==townDto.getPk()?"":townDto.getPk());
		}

		lgDeliveryOrder.setSupplierInvoiceStatus(0);// 物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认
		lgDeliveryOrder.setMemberInvoiceStatus(1);// 开票状态：1：未开发票，2：已开发票，3：已寄送
		lgDeliveryOrder.setSource(1);
		lgDeliveryOrder.setMember(dto.getMember() == null ? "" : dto.getMember());
		lgDeliveryOrder.setMemberPk(dto.getMemberPk() == null ? "" : dto.getMemberPk());
		lgDeliveryOrder.setRemark(dto.getRemark() == null ? "" : dto.getRemark());
		// 设置订单的提货单号
		int temp = this.commonDao.getDeliveryCountByOrderPkM(dto.getOrderPk());
		if (temp == 0) {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk());
		} else if (temp == 1) {
			// 把第一个部分发货的发货单的deliveryNumber改成 abc-1
			String tempPk = this.commonDao.getPkByDeliveryNumberM(dto.getOrderPk());
			this.commonDao.updateDeliveryNumberByPkM(tempPk, dto.getOrderPk() + "-1");
			// 第二个部分发货的deliveryNumber设置为abc-2
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-2");
		} else {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-" + (temp + 1));
		}
		lgDeliveryOrder.setIsAbnormal(1);
		lgDeliveryOrder.setPaymentName(dto.getPaymentName() == null ? "" : dto.getPaymentName());
		Date paymentTimeTemp;
		if (dto.getPaymentTime() == null || dto.getPaymentTime().equals("")) {
			paymentTimeTemp = null;
		} else {
			try {
				paymentTimeTemp = sdf.parse(dto.getPaymentTime());
			} catch (ParseException e) {
				e.printStackTrace();
				paymentTimeTemp = null;
			}
		}
		lgDeliveryOrder.setPaymentTime(paymentTimeTemp);
		lgDeliveryOrder.setPurchaserName(dto.getPurchaserName() == null ? "" : dto.getPurchaserName());
		lgDeliveryOrder.setPurchaserPk(dto.getPurchaserPk() == null ? "" : dto.getPurchaserPk());
		lgDeliveryOrder.setSupplierName(dto.getSupplierName() == null ? "" : dto.getSupplierName());
		lgDeliveryOrder.setSupplierPk(dto.getSupplierPk() == null ? "" : dto.getSupplierPk());
		lgDeliveryOrder.setInvoiceName(dto.getInvoiceName() == null ? "" : dto.getInvoiceName());
		lgDeliveryOrder.setInvoiceTaxidNumber(dto.getInvoiceTaxidNumber() == null ? "" : dto.getInvoiceTaxidNumber());
		lgDeliveryOrder.setInvoiceRegPhone(dto.getInvoiceRegPhone() == null ? "" : dto.getInvoiceRegPhone());
		lgDeliveryOrder.setInvoiceBankAccount(dto.getInvoiceBankAccount() == null ? "" : dto.getInvoiceBankAccount());
		lgDeliveryOrder.setInvoiceBankName(dto.getInvoiceBankName() == null ? "" : dto.getInvoiceBankName());
		lgDeliveryOrder
				.setInvoiceProvinceName(dto.getInvoiceProvinceName() == null ? "" : dto.getInvoiceProvinceName());
		lgDeliveryOrder.setInvoiceCityName(dto.getInvoiceCityName() == null ? "" : dto.getInvoiceCityName());
		lgDeliveryOrder.setInvoiceAreaName(dto.getInvoiceAreaName() == null ? "" : dto.getInvoiceAreaName());
		lgDeliveryOrder.setInvoiceRegAddress(dto.getInvoiceRegAddress() == null ? "" : dto.getInvoiceRegAddress());
		lgDeliveryOrder.setBasisLinePrice(dto.getBasisLinePrice() == null ? 0.0 : dto.getBasisLinePrice());
		lgDeliveryOrder.setSettleWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgDeliveryOrder.setIsSettleFreight(1);
		// lg_order_goods
		LgDeliveryOrderGoods lgOrderGoods = new LgDeliveryOrderGoods();
		lgOrderGoods.setPk(KeyUtils.getUUID());
		lgOrderGoods.setDeliveryPk(deliveryPk);
		lgOrderGoods.setProductName(dto.getProductName() == null ? "" : dto.getProductName());
		lgOrderGoods.setProductPk(dto.getProductPk() == null ? "" : dto.getProductPk());
		lgOrderGoods.setVarietiesName(dto.getVarietiesName() == null ? "" : dto.getVarietiesName());
		lgOrderGoods.setSeedvarietyName(dto.getSeedvarietyName() == null ? "" : dto.getSeedvarietyName());
		lgOrderGoods.setSpecName(dto.getSpecName() == null ? "" : dto.getSpecName());
		lgOrderGoods.setSeriesName(dto.getSeriesName() == null ? "" : dto.getSeriesName());
		lgOrderGoods.setGradeName(dto.getGradeName() == null ? "" : dto.getGradeName());
		lgOrderGoods.setGradePk(dto.getGradePk() == null ? "" : dto.getGradePk());
		lgOrderGoods.setBatchNumber(dto.getBatchNumber() == null ? "" : dto.getBatchNumber());
		lgOrderGoods.setWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgOrderGoods.setBoxes(dto.getBoxes() == null ? 0 : dto.getBoxes());
		// lgOrderGoods.setOrderNumber(lgDeliveryOrderForB2BDto.getOrderPk()==null?"":lgDeliveryOrderForB2BDto.getOrderPk());
		lgOrderGoods.setTareWeight(dto.getTareWeight() == null ? 0.0 : dto.getTareWeight());
		lgOrderGoods.setOriginalFreight(dto.getPresentFreight() == null ? 0.0 : dto.getPresentFreight());
		lgOrderGoods.setPresentFreight(dto.getPresentFreight() == null ? 0.0 : dto.getPresentFreight());
		lgOrderGoods.setGoodsName(dto.getGoodsName() == null ? "" : dto.getGoodsName());
		lgOrderGoods.setGoodsOriginalFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对外总价
		lgOrderGoods.setGoodsPresentFreight(ArithUtil.round(ArithUtil.mul(dto.getPresentFreight(), dto.getWeight()),2));//对内总价
		lgOrderGoods.setUnit(dto.getUnit() == null ? null : dto.getUnit());
		if (this.commonDao.insertLgDeliveryOrder(lgDeliveryOrder) > 0) {
			if (this.commonDao.insertLgDeliveryOrderGoods(lgOrderGoods) > 0) {
				// 插入一条物流轨迹，“商城确认发货”
				LgTrackDetail lgTrackDetail = new LgTrackDetail();
				lgTrackDetail.setPk(KeyUtils.getUUID());
				lgTrackDetail.setDeliveryPk(lgDeliveryOrder.getPk());
				lgTrackDetail.setStepDetail("商城确认发货，推送发货单成功");
				lgTrackDetail.setOrderStatus(6);
				lgTrackDetail.setFinishedTime(tempDate);
				lgTrackDetail.setInsertTime(tempDate);
				lgTrackDetail.setUpdateTime(tempDate);
				this.commonDao.insertLgTrackDetail(lgTrackDetail);
				//??匹配该运货单属于哪些业务员
				//logisticsCompanyPk,deliveryPk,fromProvincePk,fromCityPk,fromAreaPk,fromTownPk
				Map<String, Object> par=new HashMap<>();
				par.put("companyPk", dto.getLogisticsCompanyPk()==null?"":dto.getLogisticsCompanyPk());
				par.put("province", dto.getFromProvincePk()==null?"":dto.getFromProvincePk());
				par.put("city", dto.getFromCityPk()==null?"":dto.getFromCityPk());
				par.put("area", dto.getFromAreaPk()==null?"":dto.getFromAreaPk());
				par.put("town", dto.getFromTownPk()==null?"":dto.getFromTownPk());
				par.put("isDelete", 1);
				par.put("isVisable", 1);
				List<String> members= this.commonDao.lgRolematchMembersM(par);
				LgMemberDeliveryOrderDtoMa memberDeliveryOrderDtoEx=new LgMemberDeliveryOrderDtoMa();
				for (String member : members) {
					memberDeliveryOrderDtoEx.setPk(KeyUtils.getUUID());
					memberDeliveryOrderDtoEx.setMemberPk(member);
					memberDeliveryOrderDtoEx.setDeliveryOrderPk(lgDeliveryOrder.getPk());
					memberDeliveryOrderDtoEx.setIsDelete(1);
					commonDao.insertLgMemberDeliveryOrder(memberDeliveryOrderDtoEx);
				}
				return true;
			} else {
				commonDao.deleteLgDeliveryOrder(deliveryPk);
				return false;
			}
		} else {
			return false;
		}
	}
	/**
	 * 提供给商城查询线路价格
	 * @param searchLgLine
	 * @param weight
	 * @return
	 */
	@Override
	public ForB2BLgPriceDto searchLgPriceForB2B(LgSearchPriceForB2BDto lgSearchPriceForB2BDto) {
		ForB2BLgPriceDto forB2BLgPriceDto=new ForB2BLgPriceDto();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromProvicePk", lgSearchPriceForB2BDto.getFromProvicePk()==null?"":lgSearchPriceForB2BDto.getFromProvicePk());
		map.put("fromCityPk", lgSearchPriceForB2BDto.getFromCityPk()==null?"":lgSearchPriceForB2BDto.getFromCityPk());
		map.put("fromAreaPk", lgSearchPriceForB2BDto.getFromAreaPk()==null?"":lgSearchPriceForB2BDto.getFromAreaPk());
		map.put("fromTownPk", lgSearchPriceForB2BDto.getFromTownPk()==null?"":lgSearchPriceForB2BDto.getFromTownPk());
		map.put("toProvicePk", lgSearchPriceForB2BDto.getToProvicePk()==null?"":lgSearchPriceForB2BDto.getToProvicePk());
		map.put("toCityPk", lgSearchPriceForB2BDto.getToCityPk()==null?"":lgSearchPriceForB2BDto.getToCityPk());
		map.put("toAreaPk", lgSearchPriceForB2BDto.getToAreaPk()==null?"":lgSearchPriceForB2BDto.getToAreaPk());
		map.put("toTownPk", lgSearchPriceForB2BDto.getToTownPk()==null?"":lgSearchPriceForB2BDto.getToTownPk());
		map.put("productPk", lgSearchPriceForB2BDto.getProductPk()==null?"":lgSearchPriceForB2BDto.getProductPk());
		map.put("gradePk", lgSearchPriceForB2BDto.getGradePk()==null?"":lgSearchPriceForB2BDto.getGradePk());
		//map.put("block", lgSearchPriceForB2BDto.getBlock()==null?"":lgSearchPriceForB2BDto.getBlock());
		map.put("status", 1);// 线路启用
		map.put("isDelete", 1);
		map.put("companyIsDelete", 1);// 物流公司启用
		map.put("companyIsVisable", 1);
		map.put("companyAuditStatus", 1);

		SearchLgLine searchLgLine=new SearchLgLine();
		searchLgLine.setFromProvicePk(lgSearchPriceForB2BDto.getFromProvicePk()==null?"":lgSearchPriceForB2BDto.getFromProvicePk());
		searchLgLine.setFromCityPk(lgSearchPriceForB2BDto.getFromCityPk()==null?"":lgSearchPriceForB2BDto.getFromCityPk());
		searchLgLine.setFromAreaPk(lgSearchPriceForB2BDto.getFromAreaPk()==null?"":lgSearchPriceForB2BDto.getFromAreaPk());
		searchLgLine.setFromTownPk(lgSearchPriceForB2BDto.getFromTownPk()==null?"":lgSearchPriceForB2BDto.getFromTownPk());
		searchLgLine.setToProvicePk(lgSearchPriceForB2BDto.getToProvicePk()==null?"":lgSearchPriceForB2BDto.getToProvicePk());
		searchLgLine.setToCityPk(lgSearchPriceForB2BDto.getToCityPk()==null?"":lgSearchPriceForB2BDto.getToCityPk());
		searchLgLine.setToAreaPk(lgSearchPriceForB2BDto.getToAreaPk()==null?"":lgSearchPriceForB2BDto.getToAreaPk());
		searchLgLine.setToTownPk(lgSearchPriceForB2BDto.getToTownPk()==null?"":lgSearchPriceForB2BDto.getToTownPk());
		searchLgLine.setProductPk(lgSearchPriceForB2BDto.getProductPk()==null?"":lgSearchPriceForB2BDto.getProductPk());
		searchLgLine.setGradePk(lgSearchPriceForB2BDto.getGradePk()==null?"":lgSearchPriceForB2BDto.getGradePk());
		searchLgLine.setWeight(lgSearchPriceForB2BDto.getWeight()==null?0.0:lgSearchPriceForB2BDto.getWeight());

		List<LogisticsRouteDto> list = getMatcheRoute(map, searchLgLine);
		//匹配到线路
		if (list.size() > 0) {
			LogisticsRouteDto logisticsRouteDto = list.get(0);
			forB2BLgPriceDto.setLinePk(logisticsRouteDto.getPk());// 线路pk
			//合同下单，如果匹配的是最低运费，直接返回
			if (lgSearchPriceForB2BDto.getType() == 1 && null != logisticsRouteDto.getLeastWeight()&& logisticsRouteDto.getLeastWeight() > lgSearchPriceForB2BDto.getWeight() ) {
				forB2BLgPriceDto.setLinePk("");
				forB2BLgPriceDto.setLogisticsStepInfoPk("");
				forB2BLgPriceDto.setPrice(0.0);
				forB2BLgPriceDto.setLowPrice(0.0);
				return forB2BLgPriceDto;
			}
			//最低运价
			if (null != logisticsRouteDto.getLeastWeight()&& logisticsRouteDto.getLeastWeight() > lgSearchPriceForB2BDto.getWeight()) {
				forB2BLgPriceDto.setLogisticsStepInfoPk("");
				forB2BLgPriceDto.setPrice(0.0);
				forB2BLgPriceDto.setLowPrice(logisticsRouteDto.getFreight());
			} else {
				//最低运价（合同除外）
				if (ArithUtil.sub(ArithUtil.mul(logisticsRouteDto.getList().get(0).getSalePrice(),lgSearchPriceForB2BDto.getWeight()), logisticsRouteDto.getFreight()) < 0
						&& lgSearchPriceForB2BDto.getType() != 1) {
					forB2BLgPriceDto.setLogisticsStepInfoPk("");
					forB2BLgPriceDto.setPrice(0.0);
					forB2BLgPriceDto.setLowPrice(logisticsRouteDto.getFreight());
				} else {
					//阶梯价
					forB2BLgPriceDto.setLogisticsStepInfoPk(logisticsRouteDto.getList().get(0).getPk());
					forB2BLgPriceDto.setPrice(logisticsRouteDto.getList().get(0).getSalePrice());
					forB2BLgPriceDto.setLowPrice(0.0);
				}
			}
		} else {
			// 未匹配到线路
			forB2BLgPriceDto.setLinePk("");
			forB2BLgPriceDto.setLogisticsStepInfoPk("");
			forB2BLgPriceDto.setPrice(0.0);
			forB2BLgPriceDto.setLowPrice(0.0);
		}
		return forB2BLgPriceDto;
	}


	/**
	 * 查询线路
	 */
	private List<LogisticsRouteDto> getMatcheRoute(Map<String, Object> map,SearchLgLine searchLgLine) {
		List<LogisticsRouteDto> list = new ArrayList<LogisticsRouteDto>();
		list = queryMongo(map);
		if (null == list || list.size() == 0) {
			map.put("toTownPk", "");
			list = queryMongo(map);
			System.out.println("========================"+queryMongo(map));
			if (null == list || list.size() == 0) {
				map.put("toAreaPk", "");
				list = queryMongo(map);
				System.out.println("========================"+queryMongo(map));
				if (null == list || list.size() == 0) {
					map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
					map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
					map.put("gradePk", "");
					list = queryMongo(map);
					if (null == list || list.size() == 0) {
						map.put("toTownPk", "");
						list = queryMongo(map);
						if (null == list || list.size() == 0) {
							map.put("toAreaPk", "");
							list = queryMongo(map);
							if (null == list || list.size() == 0) {
								map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
								map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
								map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
								map.put("productPk", "");
								list = queryMongo(map);
								if (null == list || list.size() == 0) {
									map.put("toTownPk", "");
									list = queryMongo(map);
									if (null == list || list.size() == 0) {
										map.put("toAreaPk", "");
										list = queryMongo(map);
										if (null == list || list.size() == 0) {
											map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
											map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
											map.put("gradePk", "");
											list = queryMongo(map);
											if (null == list || list.size() == 0) {
												map.put("toTownPk", "");
												list = queryMongo(map);
												if (null == list || list.size() == 0) {
													map.put("toAreaPk", "");
													list = queryMongo(map);
													if (null == list || list.size() == 0) {
														map.put("toAreaPk", searchLgLine.getToAreaPk()==null?"":searchLgLine.getToAreaPk());
														map.put("toTownPk", searchLgLine.getToTownPk()==null?"":searchLgLine.getToTownPk());
														map.put("productPk", searchLgLine.getProductPk()==null?"":searchLgLine.getProductPk());
														map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
														map.put("fromTownPk", "");
														list = queryMongo(map);
														if (null == list || list.size() == 0) {
															map.put("fromAreaPk", "");
															list = queryMongo(map);
															if (null == list || list.size() == 0) {
																map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																map.put("gradePk", "");
																list = queryMongo(map);
																if (null == list || list.size() == 0) {
																	map.put("fromTownPk", "");
																	list = queryMongo(map);
																	if (null == list || list.size() == 0) {
																		map.put("fromAreaPk", "");
																		list = queryMongo(map);
																		if (null == list || list.size() == 0) {
																			map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																			map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																			map.put("gradePk", searchLgLine.getGradePk()==null?"":searchLgLine.getGradePk());
																			map.put("productPk", "");
																			list = queryMongo(map);
																			if (null == list || list.size() == 0) {
																				map.put("fromTownPk", "");
																				list = queryMongo(map);
																				if (null == list || list.size() == 0) {
																					map.put("fromAreaPk", "");
																					list = queryMongo(map);
																					if (null == list || list.size() == 0) {
																						map.put("fromAreaPk", searchLgLine.getFromAreaPk()==null?"":searchLgLine.getFromAreaPk());
																						map.put("fromTownPk", searchLgLine.getFromTownPk()==null?"":searchLgLine.getFromTownPk());
																						map.put("gradePk", "");
																						list = queryMongo(map);
																						if (null == list || list.size() == 0) {
																							map.put("fromTownPk", "");
																							list = queryMongo(map);
																							if (null == list || list.size() == 0) {
																								map.put("fromAreaPk", "");
																								list = queryMongo(map);
																							}

																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (null!=list.get(i).getLeastWeight() && searchLgLine.getWeight() < list.get(i).getLeastWeight()) {
					List<LogisticsLinePriceDto> dtoList = new ArrayList<LogisticsLinePriceDto>();
					LogisticsLinePriceDto dto = new LogisticsLinePriceDto();
					dto.setFromWeight(0.0);
					dto.setToWeight(searchLgLine.getWeight());
					dto.setBasisPrice(list.get(i).getBasicPrice()); // 结算价
					dto.setSalePrice(list.get(i).getFreight()); // 对外价
					dtoList.add(dto);
					list.get(i).setList(dtoList);
				} else {
					Query query = new Query();
					query.addCriteria(Criteria.where("linePk").is(list.get(i).getPk()));
					query.addCriteria(Criteria.where("fromWeight").lte(searchLgLine.getWeight()));
					query.addCriteria(Criteria.where("toWeight").gt(searchLgLine.getWeight()));
					Criteria stepInfo = new Criteria();
					stepInfo.andOperator(Criteria.where("linePk").is(list.get(i).getPk()),
							Criteria.where("fromWeight").lte(searchLgLine.getWeight()), Criteria.where("toWeight").gt(searchLgLine.getWeight()));
					Query query1 = new Query(stepInfo);
					List<LogisticsLinePriceDto> logisticsPrice = mongoTemplate.find(query1,
							LogisticsLinePriceDto.class);
					if (null != logisticsPrice && logisticsPrice.size() > 0) {
						list.get(i).setList(logisticsPrice);
					} else {
						list.remove(i);
					}
				}
			}
		}
		return list;
	}



	/**
	 * 查询mongo数据库
	 *
	 * @param map
	 * @return
	 */
	private List<LogisticsRouteDto> queryMongo(Map<String, Object> map) {
		Query query = new Query();
		for (String key : map.keySet()) {
			query.addCriteria(Criteria.where(key).is(map.get(key)));
		}
		System.out.println("============="+query);
		List<LogisticsRouteDto> logisticsLine = mongoTemplate.find(query, LogisticsRouteDto.class);
		return logisticsLine;
	}
	/**
	 * 商城确认发货（平台承运）
	 *
	 */
	@Override
	public boolean confirmFaHuoForB2BPT(LgDeliveryOrderForB2BPTDto dto) {
		// System.out.println("===========================平台承运，确认发货=========================");
		// lg_delivery_order
		LgDeliveryOrder lgDeliveryOrder = new LgDeliveryOrder();
		LgDeliveryOrderGoods lgOrderGoods = new LgDeliveryOrderGoods();
		LgLineDto lgLineDto = this.commonDao.getLgLineByPk(dto.getLgLinePk());
		LgLinePriceDto lgLinePriceDto = this.commonDao.getLgLinePriceByPk(dto.getLgLinePricePk()==null?"-1":dto.getLgLinePricePk());
		String deliveryPk = KeyUtils.getUUID();
		Date tempDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lgDeliveryOrder.setPk(deliveryPk);
		lgDeliveryOrder.setOrderPk(dto.getOrderPk() == null ? "" : dto.getOrderPk());
		lgDeliveryOrder.setParentPk("-1");
		lgDeliveryOrder.setOrderStatus(6);
		lgDeliveryOrder.setIsMatched(1);
		//1：匹配的是最低起运价
		if (null == lgLinePriceDto || null == lgLinePriceDto.getPk()) {
			lgDeliveryOrder.setPresentTotalFreight(null==lgLineDto.getBasicPrice()?0.0:lgLineDto.getBasicPrice());//对内总价
			//商城供应商修改过运费单价
			if (null!=dto.getBasisLinePrice()&& 0.0!=dto.getBasisLinePrice()) {
				lgDeliveryOrder.setOriginalTotalFreight(ArithUtil.mul(dto.getBasisLinePrice(),dto.getWeight()));//对外总价
			}else{
				lgDeliveryOrder.setOriginalTotalFreight(null==lgLineDto.getFreight()?0.0:lgLineDto.getFreight());//对外总价
			}
			lgOrderGoods.setGoodsPresentFreight(null==lgLineDto.getBasicPrice()?0.0:lgLineDto.getBasicPrice());//对内总价
			lgOrderGoods.setGoodsOriginalFreight(null==lgLineDto.getFreight()?0.0:lgLineDto.getFreight());//对外总价
			lgOrderGoods.setPresentFreight(null);
			lgOrderGoods.setOriginalFreight(dto.getBasisLinePrice());//对外单价
		//2:匹配到阶梯运价
		}else {
			lgDeliveryOrder.setPresentTotalFreight(ArithUtil.round(ArithUtil.mul(lgLinePriceDto.getBasisPrice(), dto.getWeight()),2));//对内总价
			lgDeliveryOrder.setOriginalTotalFreight(ArithUtil.round(ArithUtil.mul(dto.getBasisLinePrice(), dto.getWeight()),2));//对外总价
			lgDeliveryOrder.setBasisLinePrice(lgLinePriceDto.getBasisPrice());
			lgOrderGoods.setGoodsPresentFreight(ArithUtil.round(ArithUtil.mul(lgLinePriceDto.getBasisPrice(), dto.getWeight()),2));//对内总价
			lgOrderGoods.setGoodsOriginalFreight(ArithUtil.round(ArithUtil.mul(dto.getBasisLinePrice(), dto.getWeight()),2));//对外总价
			lgOrderGoods.setPresentFreight(lgLinePriceDto.getBasisPrice());//对内单价
			lgOrderGoods.setOriginalFreight(dto.getBasisLinePrice());//对外单价
		}
		lgDeliveryOrder.setInsertTime(tempDate);
		lgDeliveryOrder.setUpdateTime(tempDate);
		Date orderTimeTemp;
		if (dto.getOrderTime() == null || dto.getOrderTime().equals("")) {
			orderTimeTemp = null;
		} else {
			try {
				orderTimeTemp = sdf.parse(dto.getOrderTime());
			} catch (ParseException e) {
				e.printStackTrace();
				orderTimeTemp = null;
			}
		}
		lgDeliveryOrder.setOrderTime(orderTimeTemp);

		LgCompanyDto lgCompanyDto = new  LgCompanyDto();
		if (null!=lgLineDto && null!=lgLineDto.getPk() ) {
			lgCompanyDto = this.commonDao.getLgCompanyByPk(lgLineDto.getCompanyPk());
			lgDeliveryOrder.setLogisticsCompanyPk(lgCompanyDto.getPk() == null ? "" : lgCompanyDto.getPk());
			lgDeliveryOrder.setLogisticsCompanyName(lgCompanyDto.getName() == null ? "" : lgCompanyDto.getName());
		}
		lgDeliveryOrder.setFromCompanyName(dto.getFromCompanyName() == null ? "" : dto.getFromCompanyName());
		lgDeliveryOrder.setFromAddress(dto.getFromAddress() == null ? "" : dto.getFromAddress());
		lgDeliveryOrder.setFromContacts(dto.getFromContacts() == null ? "" : dto.getFromContacts());
		lgDeliveryOrder.setFromContactsTel(dto.getFromContactsTel() == null ? "" : dto.getFromContactsTel());
		lgDeliveryOrder.setFromProvincePk(dto.getFromProvincePk() == null ? "" : dto.getFromProvincePk());
		lgDeliveryOrder.setFromProvinceName(dto.getFromProvinceName() == null ? "" : dto.getFromProvinceName());
		lgDeliveryOrder.setFromCityPk(dto.getFromCityPk() == null ? "" : dto.getFromCityPk());
		lgDeliveryOrder.setFromCityName(dto.getFromCityName() == null ? "" : dto.getFromCityName());
		lgDeliveryOrder.setFromAreaPk(dto.getFromAreaPk() == null ? "" : dto.getFromAreaPk());
		lgDeliveryOrder.setFromAreaName(dto.getFromAreaName() == null ? "" : dto.getFromAreaName());
		lgDeliveryOrder.setFromTownPk(dto.getFromTownPk() == null ? "" : dto.getFromTownPk());
		lgDeliveryOrder.setFromTownName(dto.getFromTownName() == null ? "" : dto.getFromTownName());
		lgDeliveryOrder.setToCompanyName(dto.getToCompanyName() == null ? "" : dto.getToCompanyName());
		lgDeliveryOrder.setToAddress(dto.getToAddress() == null ? "" : dto.getToAddress());
		lgDeliveryOrder.setToContacts(dto.getToContacts() == null ? "" : dto.getToContacts());
		lgDeliveryOrder.setToContactsTel(dto.getToContactsTel() == null ? "" : dto.getToContactsTel());
		lgDeliveryOrder.setToProvinceName(dto.getToProvinceName() == null ? "" : dto.getToProvinceName());
		Map<String, Object> map=new HashMap<>();
		map.put("name", dto.getToProvinceName());
		map.put("parentPk", -1);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		SysRegionsDto provinceDto = this.commonDao.getRegionsByNameExM(map);
		lgDeliveryOrder.setToProvincePk(provinceDto.getPk()==null?"":provinceDto.getPk());
		lgDeliveryOrder.setToCityName(dto.getToCityName() == null ? "" : dto.getToCityName());
		map.put("name", dto.getToCityName());
		map.put("parentPk", provinceDto.getPk()==null?"":provinceDto.getPk());
		SysRegionsDto cityDto = this.commonDao.getRegionsByNameExM(map);
		if (null!=cityDto && null!=cityDto.getPk()) {
			lgDeliveryOrder.setToCityPk(cityDto.getPk()==null?"":cityDto.getPk());
		}
		lgDeliveryOrder.setToAreaName(dto.getToAreaName() == null ? "" : dto.getToAreaName());
		map.put("name", dto.getToAreaName());
		map.put("parentPk", cityDto.getPk()==null?"":cityDto.getPk());
		SysRegionsDto areaDto =this.commonDao.getRegionsByNameExM(map);
		if (null!=areaDto && null!=areaDto.getPk()) {
			lgDeliveryOrder.setToAreaPk(areaDto.getPk()==null?"":areaDto.getPk());
		}
		lgDeliveryOrder.setToTownName(dto.getToTownName() == null ? "" : dto.getToTownName());
		map.put("name",dto.getToTownName());
		map.put("parentPk", areaDto.getPk()==null?"":areaDto.getPk());
		SysRegionsDto townDto = this.commonDao.getRegionsByNameExM(map);
		if (null!=townDto && null!=townDto.getPk()) {
			lgDeliveryOrder.setToTownPk(null==townDto.getPk()?"":townDto.getPk());
		}

		lgDeliveryOrder.setSupplierInvoiceStatus(0);// 物流供应商发票状态:0:订单进行中，1：未开票，2：已开票待确认，3：已开票已确认
		lgDeliveryOrder.setMemberInvoiceStatus(1);// 开票状态:1：未开发票，2：已开发票，3：已寄送
		lgDeliveryOrder.setSource(2);
		lgDeliveryOrder.setMember(dto.getMember() == null ? "" : dto.getMember());
		lgDeliveryOrder.setMemberPk(dto.getMemberPk() == null ? "" : dto.getMemberPk());
		lgDeliveryOrder.setRemark(dto.getRemark() == null ? "" : dto.getRemark());
		// 设置订单的提货单号
		int temp = this.commonDao.getDeliveryCountByOrderPkM(dto.getOrderPk());
		if (temp == 0) {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk());
		} else if (temp == 1) {
			// 把第一个部分发货的发货单的deliveryNumber改成 abc-1
			String tempPk = this.commonDao.getPkByDeliveryNumberM(dto.getOrderPk());
			 this.commonDao.updateDeliveryNumberByPkM(tempPk, dto.getOrderPk() + "-1");
			// 第二个部分发货的deliveryNumber设置为abc-2
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-2");
		} else {
			lgDeliveryOrder.setDeliveryNumber(dto.getOrderPk() + "-" + (temp + 1));
		}
		lgDeliveryOrder.setIsAbnormal(1);
		lgDeliveryOrder.setPaymentName(dto.getPaymentName() == null ? "" : dto.getPaymentName());
		Date paymentTimeTemp;
		if (dto.getPaymentTime() == null || dto.getPaymentTime().equals("")) {
			paymentTimeTemp = null;
		} else {
			try {
				paymentTimeTemp = sdf.parse(dto.getPaymentTime());
			} catch (ParseException e) {
				e.printStackTrace();
				paymentTimeTemp = null;
			}
		}
		lgDeliveryOrder.setPaymentTime(paymentTimeTemp);
		lgDeliveryOrder.setPurchaserName(dto.getPurchaserName() == null ? "" : dto.getPurchaserName());
		lgDeliveryOrder.setPurchaserPk(dto.getPurchaserPk() == null ? "" : dto.getPurchaserPk());
		lgDeliveryOrder.setSupplierName(dto.getSupplierName() == null ? "" : dto.getSupplierName());
		lgDeliveryOrder.setSupplierPk(dto.getSupplierPk() == null ? "" : dto.getSupplierPk());
		lgDeliveryOrder.setInvoiceName(dto.getInvoiceName() == null ? "" : dto.getInvoiceName());
		lgDeliveryOrder.setInvoiceTaxidNumber(dto.getInvoiceTaxidNumber() == null ? "" : dto.getInvoiceTaxidNumber());
		lgDeliveryOrder.setInvoiceRegPhone(dto.getInvoiceRegPhone() == null ? "" : dto.getInvoiceRegPhone());
		lgDeliveryOrder.setInvoiceBankAccount(dto.getInvoiceBankAccount() == null ? "" : dto.getInvoiceBankAccount());
		lgDeliveryOrder.setInvoiceBankName(dto.getInvoiceBankName() == null ? "" : dto.getInvoiceBankName());
		lgDeliveryOrder.setInvoiceProvinceName(dto.getInvoiceProvinceName() == null ? "" : dto.getInvoiceProvinceName());
		lgDeliveryOrder.setInvoiceCityName(dto.getInvoiceCityName() == null ? "" : dto.getInvoiceCityName());
		lgDeliveryOrder.setInvoiceAreaName(dto.getInvoiceAreaName() == null ? "" : dto.getInvoiceAreaName());
		lgDeliveryOrder.setInvoiceRegAddress(dto.getInvoiceRegAddress() == null ? "" : dto.getInvoiceRegAddress());
		//lgDeliveryOrder.setBasisLinePrice(dto.getBasisLinePrice() == null ? 0.0 : dto.getBasisLinePrice());
		lgDeliveryOrder.setSettleWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgDeliveryOrder.setIsSettleFreight(1);
		// lg_order_goods
		lgOrderGoods.setPk(KeyUtils.getUUID());
		lgOrderGoods.setDeliveryPk(deliveryPk);
		lgOrderGoods.setProductName(dto.getProductName() == null ? "" : dto.getProductName());
		lgOrderGoods.setProductPk(dto.getProductPk() == null ? "" : dto.getProductPk());
		lgOrderGoods.setVarietiesName(dto.getVarietiesName() == null ? "" : dto.getVarietiesName());
		lgOrderGoods.setSeedvarietyName(dto.getSeedvarietyName() == null ? "" : dto.getSeedvarietyName());
		lgOrderGoods.setSpecName(dto.getSpecName() == null ? "" : dto.getSpecName());
		lgOrderGoods.setSeriesName(dto.getSeriesName() == null ? "" : dto.getSeriesName());
		lgOrderGoods.setGradeName(dto.getGradeName() == null ? "" : dto.getGradeName());
		lgOrderGoods.setGradePk(dto.getGradePk() == null ? "" : dto.getGradePk());
		lgOrderGoods.setBatchNumber(dto.getBatchNumber() == null ? "" : dto.getBatchNumber());
		lgOrderGoods.setWeight(dto.getWeight() == null ? 0.0 : dto.getWeight());
		lgOrderGoods.setBoxes(dto.getBoxes() == null ? 0 : dto.getBoxes());
		// lgOrderGoods.setOrderNumber(lgDeliveryOrderForB2BDto.getOrderPk()==null?"":lgDeliveryOrderForB2BDto.getOrderPk());
		lgOrderGoods.setTareWeight(dto.getTareWeight() == null ? 0.0 : dto.getTareWeight());
		lgOrderGoods.setGoodsName(dto.getGoodsName() == null ? "" : dto.getGoodsName());
		lgOrderGoods.setUnit(dto.getUnit() == null ? null : dto.getUnit());
		if ( this.commonDao.insertLgDeliveryOrder(lgDeliveryOrder) > 0) {
			if (this.commonDao.insertLgDeliveryOrderGoods(lgOrderGoods) > 0) {
				// 插入一条物流轨迹，“商城确认发货”
				LgTrackDetail lgTrackDetail = new LgTrackDetail();
				lgTrackDetail.setPk(KeyUtils.getUUID());
				lgTrackDetail.setDeliveryPk(lgDeliveryOrder.getPk());
				lgTrackDetail.setStepDetail("商城确认发货，推送发货单成功");
				lgTrackDetail.setOrderStatus(6);
				lgTrackDetail.setFinishedTime(tempDate);
				lgTrackDetail.setInsertTime(tempDate);
				lgTrackDetail.setUpdateTime(tempDate);
				this.commonDao.insertLgTrackDetail(lgTrackDetail);
				// ??匹配该运货单属于哪些业务员
				// logisticsCompanyPk,deliveryPk,fromProvincePk,fromCityPk,fromAreaPk,fromTownPk
				Map<String, Object> par = new HashMap<>();
				par.put("companyPk", lgCompanyDto.getPk() == null ? "" : lgCompanyDto.getPk());
				par.put("province", dto.getFromProvincePk() == null ? "" : dto.getFromProvincePk());
				par.put("city", dto.getFromCityPk() == null ? "" : dto.getFromCityPk());
				par.put("area", dto.getFromAreaPk() == null ? "" : dto.getFromAreaPk());
				par.put("town", dto.getFromTownPk() == null ? "" : dto.getFromTownPk());
				par.put("isDelete", 1);
				par.put("isVisable", 1);
				List<String> members = this.commonDao.lgRolematchMembersM(par);
				LgMemberDeliveryOrderDtoMa memberDeliveryOrderDtoEx = new LgMemberDeliveryOrderDtoMa();
				for (String member : members) {
					memberDeliveryOrderDtoEx.setPk(KeyUtils.getUUID());
					memberDeliveryOrderDtoEx.setMemberPk(member);
					memberDeliveryOrderDtoEx.setDeliveryOrderPk(lgDeliveryOrder.getPk());
					memberDeliveryOrderDtoEx.setIsDelete(1);
					this.commonDao.insertLgMemberDeliveryOrder(memberDeliveryOrderDtoEx);
				}
				return true;
			} else {
				this.commonDao.deleteLgDeliveryOrder(deliveryPk);
				return false;
			}
		} else {
			return false;
		}
	}
	/**
	 *  商城下单时根据线路pk,weight查询运费对外总价
	 */
	@Override
	public ForB2BLgPriceDto searchLgPriceForB2BByLinePk(String lgLinePk, String lgLineStepPk) {
		ForB2BLgPriceDto dto=new ForB2BLgPriceDto();
		LgLineDto lineDto =this.commonDao. getLgLineByPk(lgLinePk);
		LgCompanyDto companyDto= this.commonDao.getLgCompanyByPk(lineDto.getCompanyPk());
		dto.setLinePk(lgLinePk);
		dto.setLogisticsCarrierPk(companyDto.getPk());
		dto.setLogisticsCarrierName(companyDto.getName());
		//没有阶梯价
		if ("".equals(lgLineStepPk)) {
			dto.setLowPrice(lineDto.getFreight());
			dto.setPrice(0d);
			dto.setTotalFreight(lineDto.getFreight());
		} else {
			//有阶梯价
			LgLinePriceDto priceDto = this.commonDao.getLgLinePriceByPk(lgLineStepPk);
			dto.setPrice(priceDto.getSalePrice());
			dto.setLowPrice(0d);
			dto.setTotalFreight(0d);
		}
		return dto;
	}

	@Override
	public SysCompanyBankcardDto getCompanyBankCard(String companyPk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		List<SysCompanyBankcardDto> list = this.commonDao.searchCompanyBankcardList(map);
		return (null !=list&&list.size()>0)?list.get(0):null;
	}
	
	@Override
	public List<B2bCreditGoodsDtoMa> searchCreditGoodsAndBankCard(String purchaserPk,String supplierPk,String storePk) {
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("companyPk", purchaserPk);
		map.put("status", 2);
		map.put("endTime", "true");
		map.put("startTime", "true");
		List<B2bCreditGoodsDtoMa> dtoList = this.commonDao.getCreditGoods(map);
		List<B2bCreditGoodsDtoMa> newList = new ArrayList<B2bCreditGoodsDtoMa>();
		
		if(null != dtoList && dtoList.size()>0){
			for(B2bCreditGoodsDtoMa g : dtoList){
//				if((g.getGoodsType() == 2 || g.getGoodsType() == 4) && !storePk.equals(PropertyConfig.getProperty("storePk"))){
//					continue;
//				}
				if(null != g.getStoreInfo() && !"".equals(g.getStoreInfo())){
					try {
						List<B2bStoreDto> storeDtos = JsonUtils.toList(g.getStoreInfo(),B2bStoreDto.class);
						for (B2bStoreDto s : storeDtos) {
							if(storePk.equals(s.getPk())){
								newList.add(g);
								break;
							}
						}
					}catch (Exception e){
						continue;
					}
				}
			}
		}
		
		if(null != newList && newList.size() >0 && null !=supplierPk && !"".equals(supplierPk)){
			for(B2bCreditGoodsDtoMa dto : newList){
				SysCompanyBankcardDto card = this.getCompanyBankCard(supplierPk, dto.getBankPk());
				if(null != card){
					dto.setBankAccount(card.getBankAccount());
					dto.setBankName(card.getBankName());
				}
			}
		}
		return newList;
	}

	@Override
	public SysCompanyBankcardDto getCompanyBankCard(String supplierPk,
			String bankPk) {
		SysCompanyBankcardDto dto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", supplierPk);
		List<SysCompanyBankcardDto> list = this.commonDao.searchCompanyBankcardList(map);
		if(null != list && list.size()>0){
				if(null != bankPk){
					for (int i = 0; i < list.size(); i++) {
						//根据采购商的授信银行匹配供应商收款账户
						if( null != list.get(i).getEcbankPk() 
								&& !"".equals(bankPk) && bankPk.equals(list.get(i).getEcbankPk())){
							dto = list.get(i);
							break;
						}else if(null == list.get(i).getEcbankPk() || "".equals(list.get(i).getEcbankPk())){
							dto = list.get(i);
						}
					}
				}
				//如果未匹配到对应的则随机取一个收款账户
				if(null == dto){
					dto = list.get(0);
				}
		}
		return dto;
	
	}

	@Override
	public B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber) {
		// TODO Auto-generated method stub
		return this.commonDao.getLoanNumberByOrderNumber(orderNumber);
	}

	@Override
	public B2bEconomicsGoodsDto getEconomicsGoodsByPk(String name, Integer type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("goodsType", type.toString());
		map.put("isDelete", 1);
		map.put("status", 1);
		List<B2bEconomicsGoodsDto> list= this.commonDao.searchEconomicsGoodsList(map);
		if (null!=list && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public B2bMemberDto getMemberByMobile(String mobile) {
		// TODO Auto-generated method stub
		return this.commonDao.getMemberByMobile(mobile);
	}


	@Override
	public B2bMemberDto getSaleMan(String companyPk, String purchaserPk, String productPk, String storePk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaserPk", purchaserPk);// 采购商Pk
		map.put("filiale", companyPk);// 供应商pk
		map.put("productPk", productPk);// 品名
		map.put("storePk", storePk);// 店铺
		B2bMemberDto member = null;
		List<B2bCustomerSalesmanDto> cusSales = commonDao.searchMemberBySaleMan(map);
		// 排除品名查询
		if (null == cusSales || cusSales.size() == 0) {
			map.put("productPk", "");
			cusSales = commonDao.searchMemberBySaleMan(map);
		}
		// 排除分公司查询
		if (null == cusSales || cusSales.size() == 0) {
			map.put("productPk", productPk);
			map.put("filiale", "");
			cusSales = commonDao.searchMemberBySaleMan(map);
		}
		// 排除品名和分公司查询
		if (null == cusSales || cusSales.size() == 0) {
			map.put("productPk", "");
			cusSales = commonDao.searchMemberBySaleMan(map);
		}
		// 如果没有查到业务员则 查询供应商子公司/总公司的超级管理员
		if (null != cusSales && cusSales.size()> 0){
			if(null !=cusSales.get(0).getMemberPk() && !"".equals(cusSales.get(0).getMemberPk())){
				member = commonDao.getMemberByPk(cusSales.get(0).getMemberPk());
			}else{
				member = new B2bMemberDto();
				member.setEmployeeName(cusSales.get(0).getEmployeeName());
				member.setEmployeeNumber(cusSales.get(0).getEmployeeNumber());
			}
		}
        if(null==member||"".equals(member)){
			// 子公司
			List<B2bMemberDto> members = commonDao.getAdminM(companyPk);
			if (null == members || members.size()==0) {
				// 总公司
				members = commonDao.getAdminByParent(companyPk);
			}
			if(null !=members && members.size()>0){
				member = members.get(0);
			}
		}
		return member;
	}
	
	
	@Override
	public RestCode addCustomerManagement(B2bCustomerManagementDto dto) {
		RestCode restCode = RestCode.CODE_0000;
		if (dto.getPurchaserPk()==null||dto.getPurchaserPk().equals("")) {
			restCode = RestCode.CODE_C001;
		}else {
			Map<String, Object> mapC = new HashMap<String, Object>();
			mapC.put("purchaserPk", dto.getPurchaserPk());
			mapC.put("storePk", dto.getStorePk());
			mapC.put("pk", dto.getPk());
				int count = commonDao.isCustomerReaped(mapC);
				if (count <= 0) {
					int result = 0;
					// 编辑
					B2bCustomerManagement model = new B2bCustomerManagement();
					model.UpdateDTO(dto);
					if (null != dto.getPk() && !dto.getPk().equals("")) {
						result = commonDao.updateB2bCustomerManagement(model);
					} else {// 新增
						model.setPk(KeyUtils.getUUID());
						result = commonDao.insertB2bCustomerManagement(model);
					}
					if (result == 0) {
						restCode = RestCode.CODE_A001;
					}
				}else{
					restCode = RestCode.CODE_C010;
				}
		}
		return restCode;
	}
	
	
	/**
	 * 取消订单
	 */
	@Override
	public void cancelOrder(String orderNumber) {
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("orderNumber").is(orderNumber));
		Query query = new Query(c);
		Update update = Update.update("isDelete", 2);
		mongoTemplate.updateMulti(query, update, MemberPointPeriod.class);
	}
	
	
    /**
     * 插入订单类分值记录
     */
    @Override
    @Transactional
    public RestCode addPointForOrder(String orderNumber, int pointType) {
        RestCode restCode = RestCode.CODE_0000;
        List<B2bDimensionalityRewardDto> list = new ArrayList<B2bDimensionalityRewardDto>();
        // 查询总订单
        B2bOrderDtoMa orderDto = commonDao.getByOrderNumber(orderNumber);
        B2bDimensionalityRewardDto sourceDimen = null;
        B2bDimensionalityRewardDto payDimen = null;
        B2bDimensionalityRewardDto productDimen = null;
        try {
            // 1来源和支付方式
            getSourceAndPay(list, orderDto, sourceDimen, payDimen);
            //2：POY或非POY订单
            Map<String, Object> parm = new HashMap<String, Object>();
            parm.put("orderNumber", orderNumber);
            List<B2bOrderGoodsDtoMa> gList = commonDao.searchOrderGoodsList(parm);
            /**
			 * 对gList按照订单价格升序进行排序
			 */
			Collections.sort(gList, new Comparator<B2bOrderGoodsDtoMa>() {
				@Override
				public int compare(B2bOrderGoodsDtoMa o1, B2bOrderGoodsDtoMa o2) {
					if (getTotalPrice(o1)+o1.getPresentTotalFreight() >getTotalPrice(o2) +o2.getPresentTotalFreight()) {
						return 1;
					}
					if (getTotalPrice(o1)+o1.getPresentTotalFreight() == getTotalPrice(o2)+o2.getPresentTotalFreight()) {
						return 0;
					}
					return -1;
				}
			});
			//订单金额最大的子订单(订单金额=商品实际总价+运费实际总价)
			B2bOrderGoodsDtoMa tempOrderGoodsDto = gList.get(gList.size() - 1);
			System.out.println("=============================:"+gList.size());
			if ( null != tempOrderGoodsDto && null == tempOrderGoodsDto.getSxGoods() && tempOrderGoodsDto.getCfGoods().getProductName().equals("POY")) {
				productDimen = searchDimension(MemberSys.TRADE_DIMEN_POYORDER.getValue());
			} else {
				productDimen = searchDimension(MemberSys.TRADE_DIMEN_NOTPOY_ORDER.getValue());
			}
			if (null!=productDimen) {
				list.add(productDimen);
			}
			System.out.println("===============list.size======================"+list.size());
			for (B2bDimensionalityRewardDto b2bOrderGoodsDto : list) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+b2bOrderGoodsDto.getPk());
			}
			System.out.println("=================isCanAddPoint(list)==================="+isCanAddPoint(list));
            // 满足赠送周期，经验值，纤贝值相等
            if (isCanAddPoint(list)) {
                // 订单总金额（商品总金额+运费总金额）
            	
                Double total = ArithUtil.add(getTotalPrice(tempOrderGoodsDto),
                		tempOrderGoodsDto.getPresentTotalFreight());
                // 加分
                MangoMemberPoint point = new MangoMemberPoint();
                point.setCompanyPk(orderDto.getPurchaserPk());
                point.setMemberPk(orderDto.getMemberPk());
                point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
                point.setOrderNumber(orderNumber);
                insertOrderMemberPoint(point, total, list, orderDto, pointType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            restCode = RestCode.CODE_S999;
        }
        return restCode;
    }
	
    
    

	private void getSourceAndPay(List<B2bDimensionalityRewardDto> list, B2bOrderDto orderDto,
			B2bDimensionalityRewardDto sourceDimen, B2bDimensionalityRewardDto payDimen) {
		// wap订单
		if (orderDto.getSource() == 2) {
			sourceDimen = searchDimension(MemberSys.TRADE_DIMEN_WAPORDER.getValue());
		}
		// APP订单(无)
		/*
		 * if (orderDto.getSource() == 1) { sourceDimen =
		 * searchDimension(MemberSys.TRADE_DIMEN_WAPORDER.getValue()); }
		 */
		if (sourceDimen != null) {
			list.add(sourceDimen);
		}
		// 2支付方式
		if (null != orderDto.getPaymentType() && orderDto.getPaymentType() > 0) {
			// 线下支付
			if (orderDto.getPaymentType() == 1) {
				payDimen = searchDimension(MemberSys.TRADE_DIMEN_DOWLINE.getValue());
			} else {
				// 金融产品支付
				if (orderDto.getPaymentType() == 3) {
					B2bEconomicsGoodsDto goodsDto = searchOne(orderDto);
					if (goodsDto != null) {// 1化纤白条、2化纤贷
						if (goodsDto.getGoodsType() == 1) {
							payDimen = searchDimension(MemberSys.TRADE_DIMEN_BLNOTE.getValue());
							System.out.println(
									"===========orderDto.orderNumber==================" + orderDto.getOrderNumber());
							System.out.println("===========payDimen======================" + payDimen.getDimenName());
						}
						if (goodsDto.getGoodsType() == 2) {
							payDimen = searchDimension(MemberSys.TRADE_DIMEN_FIBER_PAY.getValue());
						}
					}
				}
			}
			if (payDimen != null) {
				list.add(payDimen);
			}
		}
	}
	
	private B2bEconomicsGoodsDto searchOne(B2bOrderDto orderDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsType", orderDto.getEconomicsGoodsType());
		map.put("name", orderDto.getEconomicsGoodsName());
		map.put("isDelete", 1);
		map.put("status", 1);
		List<B2bEconomicsGoodsDto> list = commonDao.searchEconomicsGoodsList(map);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	private Double getTotalPrice(B2bOrderGoodsDtoMa orderGood) {
		if(null == orderGood.getSxGoods()){
			return ArithUtil.round(ArithUtil.mul(orderGood.getPresentPrice() + 
					(null==orderGood.getCfGoods().getAdminFee()?0d:orderGood.getCfGoods().getAdminFee())
					+ (null==orderGood.getCfGoods().getLoadFee()?0d:orderGood.getCfGoods().getLoadFee())
					+ (null==orderGood.getCfGoods().getPackageFee()?0d:orderGood.getCfGoods().getPackageFee()), orderGood.getWeight()), 2);
		}else{
			return ArithUtil.round(ArithUtil.mul(orderGood.getPresentPrice(), orderGood.getWeight()), 2);
		}
	}
	
	private boolean isCanAddPoint(List<B2bDimensionalityRewardDto> list) {
		Boolean flag = true;
		if (list.size() > 0) {
			if (list.size() > 1) {
				for (int i = 1; i < list.size(); i++) {
					if (list.get(i).getPeriodType().intValue() == list.get(i - 1).getPeriodType().intValue()
							&& list.get(i).getEmpiricalValue().intValue() == list.get(i - 1).getEmpiricalValue()
									.intValue()
							&& list.get(i).getFibreShellNumber().intValue() == list.get(i - 1).getFibreShellNumber()
									.intValue()
							&& list.get(i).getEmpiricalRatio().doubleValue() == list.get(i - 1).getEmpiricalRatio()
									.doubleValue()
							&& list.get(i).getFibreShellRatio().doubleValue() == list.get(i - 1).getFibreShellRatio()
									.doubleValue()) {

					} else {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}
	
	
    /**
     * 交易维度加积分
     *
     * @param point     最终的赠送列表记录
     * @param total
     * @param list      满足奖励维度的规则
     * @param orderDto  订单详情
     * @param pointType 加分还是减分， 1：加分，2：减分
     */
    private void insertOrderMemberPoint(MangoMemberPoint point, Double total, List<B2bDimensionalityRewardDto> list,
    		B2bOrderDtoMa orderDto, int pointType) {
        List<MemberPointPeriod> periodList = new ArrayList<>();
        B2bMemberDto memberDto = commonDao.getMemberByPk(orderDto.getMemberPk());
        Double memberfbGradeRatio = 1d;
        Double memberemGradeRatio = 1d;

        // 1.1等级系数
        if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
            B2bMemberGradeDto gradeDto = commonDao.getDtoByGradeNumber(
                    (memberDto.getLevel() == null || memberDto.getLevel().equals("")) ? 1 : memberDto.getLevel());
            if (gradeDto != null) {
                memberfbGradeRatio = gradeDto.getFbGradeRatio();
                memberemGradeRatio = gradeDto.getEmGradeRatio();
            }
        }
        // 加权后
        Double fbShellNumberWeighting = 1.000000;
        Double empiricalValueWeighting = 1.0000000;
        String dimen = "";
        Integer fbBase = list.get(0).getFibreShellNumber();
        Integer emBase = list.get(0).getEmpiricalValue();
        Integer period = list.get(0).getPeriodType();
        for (int i = 0; i < list.size(); i++) {
            if (period != 1) {
                diffPointPeriod(list, periodList, i);
            }
            dimen = dimen + list.get(i).getDimenCategory() + "_" + list.get(i).getDimenType() + ",";
            fbShellNumberWeighting = ArithUtil.mul(fbShellNumberWeighting, list.get(i).getFibreShellRatio());
            empiricalValueWeighting = ArithUtil.mul(empiricalValueWeighting, list.get(i).getEmpiricalRatio());
        }
        fbShellNumberWeighting = ArithUtil.div(ArithUtil.mul(fbShellNumberWeighting, total), fbBase);
        empiricalValueWeighting = ArithUtil.div(ArithUtil.mul(empiricalValueWeighting, total), emBase);
        System.out.print("fbShellNumberWeighting" + fbShellNumberWeighting + "====empiricalValueWeighting====" + empiricalValueWeighting);

        // 1.2总系数
        Double totalEmpiricalValue = ArithUtil.mul(memberemGradeRatio,empiricalValueWeighting);
        Double totalfbGradeValue = ArithUtil.mul(memberfbGradeRatio,fbShellNumberWeighting);
        point.setExpValue(ArithUtil.round((pointType == 2 ? (-totalEmpiricalValue) : totalEmpiricalValue), 2));
        point.setFiberValue(ArithUtil.round((pointType == 2 ? (-totalfbGradeValue) : totalfbGradeValue), 2));
        point.setDimenType(StringUtils.subStrs(dimen));
        // 插入芒果
        mongoTemplate.insert(point);
        // 更新用户总分
        updateMemberValue(memberDto.getPk(),totalEmpiricalValue,totalfbGradeValue);

        // 插入赠送列表
        List<B2bDimensionalityPresent> presentList = getPresentList(list, orderDto, memberfbGradeRatio,
                memberemGradeRatio, ArithUtil.round(fbShellNumberWeighting,2),ArithUtil.round(empiricalValueWeighting,2));
        commonDao.insertB2bDimensionalityPresentList(presentList);
    }
    
    private void diffPointPeriod(List<B2bDimensionalityRewardDto> list, List<MemberPointPeriod> periodList, int i) {
        MemberPointPeriod p = new MemberPointPeriod();
        p.setDimenType(list.get(i).getDimenType());
        p.setDimenCategory(list.get(i).getDimenCategory());
        p.setDimenTypeName(list.get(i).getDimenTypeName());
        p.setDimenName(list.get(i).getDimenName());
        p.setFibreShellRatio(list.get(i).getFibreShellRatio());
        p.setFibreShellNumber(list.get(i).getFibreShellNumber());
        p.setEmpiricalValue(list.get(i).getEmpiricalValue());
        p.setEmpiricalRatio(list.get(i).getEmpiricalRatio());
        periodList.add(p);
    }
    
	private List<B2bDimensionalityPresent> getPresentList(List<B2bDimensionalityRewardDto> list, B2bOrderDtoMa orderDto,
			Double memberfbGradeRatio, Double memberemGradeRatio, Double fbShellNumberWeighting,
			Double empiricalValueWeighting) {
		List<B2bDimensionalityPresent> presentList = new ArrayList<>();
		for (B2bDimensionalityRewardDto b : list) {
			B2bDimensionalityPresent d = new B2bDimensionalityPresent();
			d.setPk(KeyUtils.getUUID());
			d.setCompanyPk(orderDto.getPurchaserPk());
			d.setCompanyName(orderDto.getPurchaser().getPurchaserName());
			d.setMemberPk(orderDto.getMemberPk());
			d.setMemberName(orderDto.getMemberName());

			String contactTel = "";
			String purchaserPk = orderDto.getPurchaserPk();
			if (purchaserPk != null) {
				B2bCompanyDto company = commonDao.getCompanyByPk(purchaserPk);
				if (company != null && company.getContactsTel() != null)
					contactTel = company.getContactsTel();
			}
			d.setContactsTel(contactTel);
			d.setDimenCategory(b.getDimenCategory());
			d.setDimenName(b.getDimenName());
			d.setDimenType(b.getDimenType());
			d.setDimenTypeName(b.getDimenTypeName());
			d.setPeriodType(b.getPeriodType());
			d.setFibreShellNumber(b.getFibreShellNumber());
			d.setFibreShellRatio(b.getFibreShellRatio());
			d.setEmpiricalValue(b.getEmpiricalValue());
			d.setEmpiricalRatio(b.getEmpiricalRatio());
			d.setFbGradeRatio(memberfbGradeRatio);
			d.setEmGradeRatio(memberemGradeRatio);
			d.setEmpiricalValueWeighting(empiricalValueWeighting);
			d.setFbShellNumberWeighting(fbShellNumberWeighting);
			d.setOrderNumber(orderDto.getOrderNumber());
			d.setType(1);
			presentList.add(d);
		}
		return presentList;
	}
	
	
    /**
     * 交易维度加额外奖励
     *
     * @param orderNumber 订单编号
     * @param pointType   增减分类型： 1增加分值；2减少分值
     * @return
     */
    @Override
    public RestCode addExtPointForOrder(String orderNumber, int pointType) {
        RestCode restCode = RestCode.CODE_0000;
        List<B2bDimensionalityExtrewardDto> list = new ArrayList<B2bDimensionalityExtrewardDto>();
        // 查询总订单
        B2bOrderDto orderDto = commonDao.getByOrderNumber(orderNumber);
        if (null == orderDto || null == orderDto.getOrderNumber()) {
            restCode = RestCode.CODE_A003;
            return restCode;
        }
        //1;查询所有的交易维度额外奖励规则
        String[] strArr = MemberSys.TRADE_DIMEN_BLNOTE.getValue().split("_");
        // 交易维度-白条订单
        //TRADE_DIMEN_BLNOTE("2_1"),
        String[] BLNOTE = MemberSys.TRADE_DIMEN_BLNOTE.getValue().split("_");
        // 交易维度-线下支付
        //TRADE_DIMEN_DOWLINE("2_2"),
        String[] DOWLINE = MemberSys.TRADE_DIMEN_DOWLINE.getValue().split("_");
        // 交易维度-求购订单
        //TRADE_DIMEN_MARRORDER("2_3"),
        String[] MARRORDER = MemberSys.TRADE_DIMEN_MARRORDER.getValue().split("_");
        // 交易维度-竞拍订单
        //TRADE_DIMEN_ACTAORDER("2_4"),
        String[] ACTAORDER = MemberSys.TRADE_DIMEN_ACTAORDER.getValue().split("_");
        // 交易维度-除POY之外其他品名订单
        //TRADE_DIMEN_NOTPOY_ORDER("2_5"),
        String[] NOTPOY_ORDER = MemberSys.TRADE_DIMEN_NOTPOY_ORDER.getValue().split("_");
        // 交易维度-POY订单
        //TRADE_DIMEN_POYORDER("2_6"),
        String[] POYORDER = MemberSys.TRADE_DIMEN_POYORDER.getValue().split("_");
        // 交易维度-WAP订单
        //TRADE_DIMEN_WAPORDER("2_7"),
        String[] WAPORDER = MemberSys.TRADE_DIMEN_WAPORDER.getValue().split("_");
        // 交易维度-APP订单
        //TRADE_DIMEN_APPORDER("2_8"),
        String[] APPORDER = MemberSys.TRADE_DIMEN_APPORDER.getValue().split("_");
        // 交易维度-在线支付
        //TRADE_DIMEN_ONLNORDER("2_9"),
        String[] ONLNORDER = MemberSys.TRADE_DIMEN_ONLNORDER.getValue().split("_");
        // 交易维度-化纤贷支付
        //TRADE_DIMEN_FIBER_PAY("2_10"),
        String[] FIBER_PAY = MemberSys.TRADE_DIMEN_FIBER_PAY.getValue().split("_");

        //查询额外奖励下的所有交易维度的规则
        list = commonDao.getExtrewardByDimenCategory(strArr[0]);
        List<B2bDimensionalityExtrewardDto> effectiveList = new ArrayList<B2bDimensionalityExtrewardDto>();
        if (null != list && list.size() > 0) {
            for (B2bDimensionalityExtrewardDto b2bDimensionalityExtrewardDto : list) {
                //交易维度-白条订单 2_1
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(BLNOTE[1])) {
                    if (null != orderDto.getEconomicsGoodsType() && orderDto.getEconomicsGoodsType()>0 ) {
                        if (orderDto.getEconomicsGoodsType() == 1) {//产品类型：1:化纤白条,2:化纤贷
                            if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                                effectiveList.add(b2bDimensionalityExtrewardDto);
                            }
                        }
                    }
                }
                //交易维度-线下支付 2_2
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(DOWLINE[1])) {
                    if (null!=orderDto.getPaymentType()&&orderDto.getPaymentType() == 1) {
                        if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                            effectiveList.add(b2bDimensionalityExtrewardDto);
                        }
                    }
                }
                //交易维度-求购订单 2_3
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(MARRORDER[1])) {

                }
                //交易维度-竞拍订单 2_4
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(ACTAORDER[1])) {

                }
                //交易维度-除POY之外其他品名订单 2_5
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(NOTPOY_ORDER[1])) {
                    Map<String, Object> parm = new HashMap<String, Object>();
                    parm.put("orderNumber", orderNumber);
                    List<B2bOrderGoodsDtoMa> orderGoodsList = commonDao.searchOrderGoodsList(parm);
                    if (null != orderGoodsList && orderGoodsList.size() > 0) {
                    	 /**
            			 * 对gList按照订单价格升序进行排序
            			 */
            			Collections.sort(orderGoodsList, new Comparator<B2bOrderGoodsDtoMa>() {
            				@Override
            				public int compare(B2bOrderGoodsDtoMa o1, B2bOrderGoodsDtoMa o2) {
            					if (getTotalPrice(o1)+o1.getPresentTotalFreight() > getTotalPrice(o2)+o2.getPresentTotalFreight()) {
            						return 1;
            					}
            					if (getTotalPrice(o1)+o1.getPresentTotalFreight() == getTotalPrice(o2)+o2.getPresentTotalFreight()) {
            						return 0;
            					}
            					return -1;
            				}
            			});
            			//订单金额最大的子订单(订单金额=商品实际总价+运费实际总价)
            			B2bOrderGoodsDtoMa tempOrderGoodsDto = orderGoodsList.get(orderGoodsList.size() - 1);
            			if (null != tempOrderGoodsDto && null == tempOrderGoodsDto.getSxGoods() && !tempOrderGoodsDto.getCfGoods().getProductName().equals("POY")) {
            				if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
            					effectiveList.add(b2bDimensionalityExtrewardDto);
            				}
            			}
                    }
                }
                //交易维度-POY订单 2_6
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(POYORDER[1])) {
                    Map<String, Object> parm = new HashMap<String, Object>();
                    parm.put("orderNumber", orderNumber);
                    List<B2bOrderGoodsDtoMa> orderGoodsList = commonDao.searchOrderGoodsList(parm);
                    if (null != orderGoodsList && orderGoodsList.size() > 0) {
                    	 /**
            			 * 对gList按照订单价格升序进行排序
            			 */
            			Collections.sort(orderGoodsList, new Comparator<B2bOrderGoodsDtoMa>() {
            				@Override
            				public int compare(B2bOrderGoodsDtoMa o1, B2bOrderGoodsDtoMa o2) {
            					if (getTotalPrice(o1)+o1.getPresentTotalFreight() >getTotalPrice(o2)+o2.getPresentTotalFreight()) {
            						return 1;
            					}
            					if (getTotalPrice(o1)+o1.getPresentTotalFreight() == getTotalPrice(o2)+o2.getPresentTotalFreight()) {
            						return 0;
            					}
            					return -1;
            				}
            			});
            			//订单金额最大的子订单(订单金额=商品实际总价+运费实际总价)
            			B2bOrderGoodsDtoMa tempOrderGoodsDto = orderGoodsList.get(orderGoodsList.size() - 1);
                    	if (null !=tempOrderGoodsDto && null == tempOrderGoodsDto.getSxGoods() && null!=tempOrderGoodsDto.getCfGoods().getProductName()&&tempOrderGoodsDto.getCfGoods().getProductName().equals("POY")) {
                    		if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                                effectiveList.add(b2bDimensionalityExtrewardDto);
                            }
						}
                    }
                }
                //交易维度-WAP订单 2_7
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(WAPORDER[1])) {
                    if (orderDto.getSource() == 2) {
                        if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                            effectiveList.add(b2bDimensionalityExtrewardDto);
                        }
                    }
                }
                //交易维度-APP订单  2_8
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(APPORDER[1])) {
                    if (orderDto.getSource() == 3) {
                        if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                            effectiveList.add(b2bDimensionalityExtrewardDto);
                        }
                    }
                }

                //交易维度-在线支付 2_9
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(ONLNORDER[1])) {
                    if (null!=orderDto.getPaymentType()&&orderDto.getPaymentType() != 1) {//1线下支付2余额支付3白条支付
                        if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                            effectiveList.add(b2bDimensionalityExtrewardDto);
                        }
                    }
                }
                //交易维度-化纤贷支付  2_10
                if (b2bDimensionalityExtrewardDto.getDimenType() == Integer.parseInt(FIBER_PAY[1])) {
                    if (null != orderDto.getEconomicsGoodsType() && orderDto.getEconomicsGoodsType()>0) {
                        if (orderDto.getEconomicsGoodsType() == 2) {//产品类型：1:化纤白条,2:化纤贷
                            if (checkExtDimenValidity(orderDto, b2bDimensionalityExtrewardDto)) {
                                effectiveList.add(b2bDimensionalityExtrewardDto);
                            }
                        }
                    }
                }
            }
        }
        //对所有符合额外奖励维度的规则取纤贝最大值
        if (null != effectiveList && effectiveList.size() > 0) {
			/**
			 * 对list按照纤贝值升序进行排序
			 */
			Collections.sort(effectiveList, new Comparator<B2bDimensionalityExtrewardDto>() {
				@Override
				public int compare(B2bDimensionalityExtrewardDto o1, B2bDimensionalityExtrewardDto o2) {
					if (o1.getFibreShellNumber() > o2.getFibreShellNumber()) {
						return 1;
					}
					if (o1.getFibreShellNumber() == o2.getFibreShellNumber()) {
						return 0;
					}
					return -1;
				}
			});
			
			B2bDimensionalityExtrewardDto tempDto = effectiveList.get(effectiveList.size() - 1);
			// 给对应的采购商业务员加积分
			updateMemberValue(orderDto.getMemberPk(),Double.parseDouble(tempDto.getEmpiricalValue()+""),Double.parseDouble(tempDto.getFibreShellNumber()+""));
			// 插入额外加分明细
			B2bMemberDto memberDto = commonDao.getMemberByPk(orderDto.getMemberPk());
			B2bCompanyDto companyDto = commonDao.getCompanyByPk(memberDto.getCompanyPk());
			addB2bDimensionalityExtPresent(tempDto, memberDto, companyDto, orderNumber);
        }
        return restCode;
    }
	
    
	/**
	 * 验证额外奖励维度的有效性
	 *
	 * @param orderDto
	 *            订单信息
	 * @param extrewardDto
	 *            额外奖励维度
	 * @return
	 */
	private boolean checkExtDimenValidity(B2bOrderDto orderDto, B2bDimensionalityExtrewardDto extrewardDto) {
		boolean result = false;
		int conditionType = extrewardDto.getConditionType();// 满足条件类型,1:累计成交采购商家数,2:累计成交供应商家数,3:累计发布求购数量,4:累计求购报价数量,5:累计添加人员数
		int periodTime = extrewardDto.getPeriodTime();// 多长时间范围内
		int numberTimes = extrewardDto.getNumberTimes();// 累计成交次数
		// 1:累计成交采购商家数
		// 供应商累计成交几家采购商暂时不做
		/*
		 * if (conditionType == 1) { Map<String, Object> map = new HashMap<>();
		 * map.put("supplierPk", orderDto.getSupplierPk());// 供应商pk
		 * map.put("periodTime", periodTime);// 多长时间范围内 Integer totalPurCount =
		 * orderDaoEx.selectTotalPurCountInPeriodTime(map);// 累计成交采购商家数量 if
		 * (totalPurCount < numberTimes) { result = false; }else{ result=true; }
		 * }
		 */
		// 2:累计成交供应商家数
		if (conditionType == 2) {
			Map<String, Object> map = new HashMap<>();
			map.put("memberPk", orderDto.getMemberPk());// 采购商业务员pk
			map.put("periodTime", periodTime+1);// 多长时间范围内
			map.put("periodTimeStart", extrewardDto.getUpdateTime());//规则的更新时间
			Integer totalSupCount = commonDao.selectTotalSupCountInPeriodTime(map);// 累计成交供应商家数量
			if (totalSupCount == numberTimes) {
				Integer lastTotalSupCount = commonDao.checkLastTotalSupCountInPeriodTime(orderDto.getMemberPk(),
						periodTime+1, orderDto.getOrderNumber(),extrewardDto.getUpdateTime());// 本次订单之前累计成交供应商家数
				if (lastTotalSupCount == totalSupCount) {
					result = false;
				} else {
					result = true;
				}
			} else {
				result = false;
			}
		}
		// 3:累计发布求购数量
		// 4:累计求购报价数量
		return result;
	}
	
	/**
     * 插入额外奖励记录
     *
     * @param dto        额外奖励维度
     * @param memberDto  会员
     * @param companyDto 会员所属公司
     */
    private void addB2bDimensionalityExtPresent(B2bDimensionalityExtrewardDto dto, B2bMemberDto memberDto,
                                                B2bCompanyDto companyDto, String orderNumber) {
        B2bDimensionalityPresent d = new B2bDimensionalityPresent();
        d.setPk(KeyUtils.getUUID());
        d.setRewardPk(dto.getPk());
        d.setCompanyPk(companyDto.getPk());
        d.setCompanyName(companyDto.getName());
        d.setMemberPk(memberDto.getPk());
        d.setMemberName(memberDto.getEmployeeName());
        d.setContactsTel(companyDto.getContactsTel());
        d.setDimenCategory(dto.getDimenCategory());
        d.setDimenName(dto.getDimenName());
        d.setDimenType(dto.getDimenType());
        d.setDimenTypeName(dto.getDimenTypeName());
        d.setPeriodType(dto.getPeriodType());
        d.setFibreShellNumber(dto.getFibreShellNumber());
        d.setEmpiricalValue(dto.getEmpiricalValue());
        d.setType(2);//赠送类型，1:正常赠送2:额外赠送
        d.setOrderNumber(orderNumber);
        commonDao.insertDimensionalityPresent(d);
    }
    
    
    @Override
    public B2bCompanyDto getCompanyByPk(String pk) {
        return commonDao.getCompanyByPk(pk);
    }
	
    @Override
    public List<B2bCompanyDto> searchCompanyList(Integer companyType, String companyPk, Integer returnType,
                                                   Map<String, Object> map) {
        if (null == map) {
            map = new HashMap<String, Object>();
        }
        map.put("isVisable", 1);
        map.put("isDelete", 1);
        map.put("auditStatus", 2);
        // 供应商
        if (companyType == 2) {
            map.put("auditSpStatus", 2);
        }
        // 查询自己包含子公司
        if (returnType == 1) {
            map.put("parentChildPk", companyPk);
            // 只查询子公司
        } else {
            map.put("parentPk", companyPk);
        }
        return commonDao.searchCompanyList(map);
    }
    
    @Override
    public List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map) {
        return commonDao.searchCompanyList(map);
    }
    
	@Override
	public B2bCompanyDto getCompanyByName(String companyName) {
		B2bCompanyDto company = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", companyName);
		List<B2bCompanyDto> list = commonDao.searchCompanyList(map);
		if(null != list && list.size()>0){
			company = list.get(0);
		}
		return company;
	}
	
	
	@Override
	public RestCode addPointForMember(String memberPk, String companyPk, Integer pointType, String active, Boolean flag) {
		RestCode restCode = RestCode.CODE_0000;
		Map<String, String> map = new HashMap<String, String>();
		if (flag) {
			map.put("memberPk", memberPk);
		}
		map.put("companyPk", companyPk);
		map.put("dimenType", active);
		List<MangoMemberPoint> list = this.searchPointList(map);
		if (null == list || list.size() == 0) {
			restCode = this.addPoint(memberPk, companyPk, pointType, active);
		}
		return restCode;
	}
	@Override
	public Integer isDeletePlant(String plantPk) {
		Integer type = 2;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("plantPk",plantPk);
		int result = commonDao.isDeletePlantByB(m);
		if (result > 0) {
			type =1;// 1商品关联,2商品未关联
		}
		return type;
	}
	
	@Override
	public Integer isDeleteWare(String warePk) {
		Integer type = 2;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("warePk",warePk);
		int result = commonDao.isDeleteWareByB(m);
		if (result > 0) {
			type =1;// 1商品关联,2商品未关联
		}
		return type;
	}

	@Override
	public B2bMemberDto getGroupMan(String pk) {
		B2bMemberDto member=commonDao.getMemberByPk(pk);
		if("-1".equals(member.getParentPk())){
			return member;
		}else{
			B2bMemberDto group=commonDao.getGroupMan(pk);
			return group;
		}
	}

	@Override
	public B2bGoodsDtoMa getB2bGoodsDtoMa(String pk) {
		// TODO Auto-generated method stub
		return commonDao.getB2bGoodsDtoMa(pk);
	}

	@Override
	public String[] getEmployeePks(String memberPk) {
			List<B2bMemberGroupDto> list = commonDao.getMemberGroupList(memberPk);
			String[] employeePks = new String[list.size()];
			for (int i = 0; i < employeePks.length; i++) {
				employeePks[i] = list.get(i).getParentPk();
			}
			return employeePks;
	}

	@Override
	public B2bOnlinepayRecordDto getB2bOnlinepayRecordDto(String orderNumber) {
		// TODO Auto-generated method stub
		return commonDao.getB2bOnlinepayRecordDto(orderNumber);
	}

	@Override
	public List<B2bBillCusgoodsDtoMa> searchBillGoodsAndBankCard(
			String purchaserPk, String supplierPk) {
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("companyPk", purchaserPk);
		List<B2bBillCusgoodsDtoMa> dtoList = this.commonDao.getBillCusgoods(map);
		if(null != dtoList && dtoList.size() >0 && null !=supplierPk && !"".equals(supplierPk)){
			for(B2bBillCusgoodsDtoMa dto : dtoList){
				B2bBillGoodsDto billGoods =  commonDao.getBillGoods(dto.getGoodsPk());
				dto.setImgUrl(null!=billGoods?billGoods.getImgUrl():"");
				switch (dto.getGoodsShotName()) {
				case BillType.PFT:
					B2bBillSigncardDto signcard = this.getBillCompanyBankCard(supplierPk, null==billGoods?null:billGoods.getBankPk());
					if(null != signcard){
						dto.setBankAccount(signcard.getBankAccount());
						dto.setBankName(signcard.getBankName());
					}
					break;
				case BillType.TX:
					SysCompanyBankcardDto card = this.getCompanyBankCard(supplierPk, null==billGoods?null:billGoods.getBankPk());
					if(null != card){
						dto.setBankAccount(card.getBankAccount());
						dto.setBankName(card.getBankName());
					}
					break;
				default:
					break;
				}
			}
		}
		return dtoList;
	}

	@Override
	public B2bBillOrderDto getBillOrder(String orderNumber) {
		// TODO Auto-generated method stub
		return commonDao.getBillOrder(orderNumber);
	}

	@Override
	public B2bBillSigncardDto getBillCompanyBankCard(String companyPk,
			String bankPk) {
		B2bBillSigncardDto dto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		map.put("status", 2);
		List<B2bBillSigncardDto> list = this.commonDao.searchBillSigncardList(map);
		if(null != list && list.size()>0){
				if(null != bankPk){
					for (int i = 0; i < list.size(); i++) {
						//根据采购商的授信银行匹配供应商收款账户
						if( null != list.get(i).getEcbankPk() 
								&& !"".equals(bankPk) && bankPk.equals(list.get(i).getEcbankPk())){
							dto = list.get(i);
							break;
						}else if(null == list.get(i).getEcbankPk() || "".equals(list.get(i).getEcbankPk())){
							dto = list.get(i);
						}
					}
				}
				//如果未匹配到对应的则随机取一个收款账户
				if(null == dto){
					dto = list.get(0);
				}
		}
		return dto;
	}

	@Override
	public List<B2bBillInventoryDto> searchBillInventoryList(String orderNumber) {
		// TODO Auto-generated method stub
		return commonDao.searchBillInventoryList(orderNumber);
	}

	@Override
	public RestCode DuplicatedGoods(List<B2bOrder> olist) {
		if(null!=olist&&olist.size()>0){
		for (B2bOrder o:olist) {
			B2bStoreDto store=commonDao.getStoreByCompanyPk(o.getPurchaserPk());
			if(null!=store&&store.getPk().equals(PropertyConfig.getProperty("storePk"))){
				B2bOrderDtoMa order = tradeDao.getByOrderNumber(o.getOrderNumber());
				if(null!=order){
					Map<String, Object> map = new HashMap<String, Object>();
				    map.put("orderNumber", o.getOrderNumber());
				    List<B2bOrderGoodsDtoMa> orderGoods=tradeDao.searchOrderGoodsList(map);
				    if(null!=orderGoods&&orderGoods.size()>0){
				    	for (B2bOrderGoodsDtoMa ma:orderGoods) {
					    	addGood(ma.getGoodsPk(),store,order.getPurchaser().getPurchaserPk(),order.getPurchaser().getPurchaserName());
						}
				    }
				}
			}
			
		}
		}
		return RestCode.CODE_0000;
	}
 

	private void addGood(String goodsPk, B2bStoreDto store,String purchaserPk,String purchaserName) {
		B2bGoodsDto good=commonDao.getB2bGoodsDtoMa(goodsPk);
		if(null!=good){
		//判断唯一性
		int repeated=isGoodsRepeated(good,store.getPk());
		if(repeated==0){
			String gpk=KeyUtils.getUUID();
	    	good.setPk(gpk);
	    	good.setStorePk(store.getPk());
	    	good.setStoreName(store.getName());
	    	good.setCompanyPk(purchaserPk);
	    	good.setCompanyName(purchaserName);
	    	good.setType(GoodsType.PRESALE.getValue());
	    	good.setIsUpdown(2);
	    	//品牌
	    	String brandPk=addBrand(good.getBrandPk(), store);
	    	good.setBrandPk(brandPk);
	    	Map<String, Object> goodInfo = JsonUtils.getStringToMap(good.getGoodsInfo());
	    	goodInfo.put("brandPk",brandPk);
	    	if("".equals(checkMap(goodInfo, "stampDuty"))||"1".equals(checkMap(goodInfo, "stampDuty"))){
	    		goodInfo.put("stampDuty",2);//印花税 1否2是
	    		Double  saleP= ArithUtil.mulBigDecimal(good.getSalePrice(),Double.valueOf(PropertyConfig.getProperty("duty"))).setScale(2, RoundingMode.CEILING).doubleValue();
	    		good.setSalePrice(saleP);
	    	}
	    	//厂区
	    	addPlant(goodInfo, store);
	    	//仓库
	        addWare(goodInfo,store);
	    	//库位号
	    	addWarehouseNumber(goodInfo,good.getStorePk(),store);
	    	goodInfo.put("pk", gpk);
	    	good.setGoodsInfo(JsonUtils.collectToString(goodInfo));
	    	commonDao.insertB2bGood(good);
		}
		}
    	 
	}
 

	private int isGoodsRepeated(B2bGoodsDto good, String storePk) {
		Map<String, Object> goodInfo = JsonUtils.getStringToMap(good.getGoodsInfo());
		goodInfo.put("storePk", storePk); 
		int repeated=commonDao.isGoodsRepeated(goodInfo);
		return repeated;
	}

	private String checkMap(Map<String, Object> goodInfo, String key) {
		if(goodInfo.containsKey(key)&&!"".equals(goodInfo.get(key).toString())){
			 return goodInfo.get(key).toString();
		}
		return "";
	}
	private String  addBrand(String brandPk,  B2bStoreDto store) {
		String key="";
		B2bGoodsBrandDto before=commonDao.getGoodsBrandByPk(brandPk);
		if(null!=before&&!"".equals(before)){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandName", before.getBrandName());
		map.put("storePk", store.getPk());
		map.put("isDelete", 1);
		List<B2bGoodsBrandDto> dto = commonDao.searchGoodsBrandList(map);
		if (dto == null || dto.size() == 0) {
			B2bGoodsBrand goodsbrand = new B2bGoodsBrand();
			goodsbrand.UpdateDTO(before);
			key=KeyUtils.getUUID();
			goodsbrand.setPk(key);
			goodsbrand.setStorePk(store.getPk());
			goodsbrand.setStoreName(store.getName());
			goodsbrand.setAddMemberPk(null);
			goodsbrand.setAuditStatus(2);
			commonDao.insertB2bGoodsBrand(goodsbrand);
		 
		}else{
			key=dto.get(0).getPk();
			 
		}
		}
		return key;
	}
	private void addPlant(Map<String, Object> goodInfo, B2bStoreDto store) {
		String key="";
		String plantPk=checkMap(goodInfo, "plantPk");
		List<B2bPlantDto> list=new ArrayList<B2bPlantDto>();
		B2bPlantDto before=commonDao.getPlantByPk(plantPk);
		if(null!=before&&!"".equals(before)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", before.getName());
			map.put("storePk", store.getPk());
			map.put("isDelete", 1);
			list=commonDao.searchPlantList(map);
			if (list == null || list.size() == 0) {
				B2bPlant plant=new B2bPlant();
				plant.UpdateDTO(before);
				key=KeyUtils.getUUID();
				plant.setPk(key);
				plant.setStorePk(store.getPk());
				plant.setStoreName(store.getName());
				commonDao.insertPlant(plant);
			} else{
				key=list.get(0).getPk();
				 
			}
		}
		goodInfo.put("plantPk", key);
	}
	private void addWare(Map<String, Object> goodInfo, B2bStoreDto store) {
		String key="";
		String warePk=checkMap(goodInfo, "warePk");
		List<B2bWareDto> list=new ArrayList<B2bWareDto>();
		B2bWareDto before=commonDao.getWareByPk(warePk);
		if(null!=before&&!"".equals(before)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", before.getName());
			map.put("storePk", store.getPk());
			map.put("isDelete", 1);
			list=commonDao.searchWareList(map);
			if (list == null || list.size() == 0) {
				B2bWare model=new B2bWare();
				model.UpdateDTO(before);
				key=KeyUtils.getUUID();
				model.setPk(key);
				model.setStorePk(store.getPk());
				commonDao.insertB2bWare(model);
			}else{
				key=list.get(0).getPk();
			}
		}
		goodInfo.put("warePk", key);
	}
	private void addWarehouseNumber(Map<String, Object> goodInfo,String beforeStorePk, B2bStoreDto store) {
		String wnumber=checkMap(goodInfo, "warehouseNumber");
    	String wnumberType=checkMap(goodInfo, "warehouseType");
    	if(!"".equals(wnumber)&&!"".equals(wnumberType)){
    		Map<String, Object> numMap = new HashMap<String, Object>();
    		numMap.put("number", wnumber);
    		numMap.put("type", wnumberType);
    		numMap.put("storePk", store.getPk());
    		numMap.put("isDelete", 1);
    		List<B2bWarehouseNumberDto> list=commonDao.searchB2bWarehouseNumberList(numMap);
    		if(null==list||list.size()==0){
    			numMap.put("storePk", beforeStorePk);
    			List<B2bWarehouseNumberDto> beforeList=commonDao.searchB2bWarehouseNumberList(numMap);
    			if(null!=beforeList&&beforeList.size()>0){
    				B2bWarehouseNumber number=new B2bWarehouseNumber();
    				number.UpdateDTO(beforeList.get(0));
    				number.setPk(KeyUtils.getUUID());
    				number.setStorePk(store.getPk());
    				commonDao.insertB2bWarehouseNumber(number);
    			}
    		} 
    	}
		
	}

	
	
	@Override
	public List<B2bCreditGoodsDto> searchCreditGoods(Map<String, Object> map) {
		return commonDao.searchCreditGoods(map);
	}

	
	
	@Override
	public B2bEconomicsGoodsDto getEconomicsGoods(String pk) {
		Map<String, Object> map = new HashMap<>();
		map.put("pk", pk);
		B2bEconomicsGoodsDto b2bEconomicsGoodsDto=null;
		List<B2bEconomicsGoodsDto> list = commonDao.searchEconomicsGoodsList(map);
		if (null!=list && list.size()>0) {
			b2bEconomicsGoodsDto = list.get(0);
		}
		return b2bEconomicsGoodsDto;
	}

	@Override
	public DeliveryBasic getDeliveryBasic(String orderNumber) {
		B2bOrderDtoMa om = commonDao.getByOrderNumber(orderNumber);
		B2bLoanNumberDto loan = commonDao.getLoanNumberByOrderNumber(orderNumber);
		DeliveryBasic db = null;
		Double deamount = 0d;//已用金额
		if(null !=om && null !=loan){
			db = new DeliveryBasic();
			db.setOrder(om);
			db.setLoan(loan);
			Criteria c = new Criteria();
			c.andOperator(Criteria.where("status").is(3),
					Criteria.where("orderNumber").is(orderNumber));
			Query query = new Query(c);
			List<DeliveryOrder> dolist = mongoTemplate.find(query,
					DeliveryOrder.class);
			if(null != dolist && dolist.size()>0){
				for(DeliveryOrder o : dolist){
					deamount = ArithUtil.add(deamount, o.getDeliveryAmount());
				}
			}
			//部分还款剩余提货金额=订单金额-已提货金额	
			if(loan.getLoanStatus() == 5){
				db.setSurplusAmount(ArithUtil.sub(om.getActualAmount(), deamount)<0d?
						0d:ArithUtil.sub(om.getActualAmount(), deamount));
			//已还款 剩余提货金额=贷款金额-已提货金额
			}else{
				db.setSurplusAmount(ArithUtil.sub(loan.getLoanAmount(), deamount)<0d?
						0d:ArithUtil.sub(loan.getLoanAmount(), deamount));
			}
			//首付金额
			db.setDownPaymentAmount(ArithUtil.round(ArithUtil.sub(om.getActualAmount(), loan.getLoanAmount()), 2));
		}
		return db;
	}

	@Override
	public List<DeliveryOrder> searchDeliveryOrderList(String orderNumber,List<Integer> status) {
		
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("orderNumber").is(orderNumber),
				Criteria.where("status").in(status));
		Query query = new Query(c);
		query.with(new Sort(new Order(Direction.DESC,"date")));   
		List<DeliveryOrder> dolist = mongoTemplate.find(query,
				DeliveryOrder.class);
		if(null != dolist && dolist.size() > 0){
			Map<String,Object> map = new HashMap<String,Object>();
			for(DeliveryOrder o : dolist){
				List<DeliveryGoods> dglist = JsonUtils.toList(o.getDeliveryGoodsInfo(), DeliveryGoods.class);
				for(DeliveryGoods dg : dglist){
					map.put("childOrderNumber", dg.getChildOrderNumber());
					List<B2bOrderGoodsDtoMa> oglist = commonDao.searchOrderGoodsList(map);
					if(null != oglist && oglist.size() > 0){
						B2bOrderGoodsDtoMa ogm = oglist.get(0);
						dg.setBoxes(ogm.getBoxes());
						dg.setWeight(ogm.getWeight());
						dg.setBoxesShipped(ogm.getBoxesShipped());
						dg.setWeightShipped(ogm.getWeightShipped());
						dg.setNoBoxesShipped((ogm.getBoxes()-ogm.getBoxesShipped())<0?0:
							ogm.getBoxes()-ogm.getBoxesShipped());
						dg.setNoWeightShipped((ArithUtil.sub(ogm.getWeight(), ogm.getWeightShipped()))<0?0d:
							ArithUtil.sub(ogm.getWeight(), ogm.getWeightShipped()));
					}
				}
				o.setDeliveryGoods(dglist);
				o.setDeliveryGoodsInfo(null);
				//提货凭证
				o.setPayVoucherList(searchPayVoucherList(o.getDeliveryNumber(), 2));
			}
		}
		return dolist;
	}

	@Override
	public String creditDeliveryOrder(String orderNumber,
			List<DeliveryGoods> dgList) {
		String rest = RestCode.CODE_0000.toJson();
		if(null == orderNumber || "".equals(orderNumber) || null == dgList || dgList.size() == 0){
			rest = RestCode.CODE_A001.toJson();
		}else{
			boolean flag = true;
			B2bLoanNumberDto loan = null;
			Double deliveryAmount = 0d;//提货单总金额
			List<DeliveryGoods> newlist = null;
			for(DeliveryGoods o : dgList){
				if((null ==o.getDeliverBoxes() || 0 == o.getDeliverBoxes())
						&& (null == o.getDeliverWeight() || 0d == o.getDeliverWeight())){
					continue;
				}
				if(null ==o.getDeliverBoxes() || 0 == o.getDeliverBoxes()
						|| null == o.getDeliverWeight() || 0d == o.getDeliverWeight()){
					RestCode.CODE_Z000.setMsg("提货重量或提货数量不能为0");
					rest = RestCode.CODE_Z000.toJson();
					flag = false;
					break;
				}
				if(null == newlist){
					newlist = new ArrayList<DeliveryGoods>();
				}
				newlist.add(o);
			}
			if(null == newlist){
				RestCode.CODE_Z000.setMsg("提货重量或提货数量不能为0");
				rest = RestCode.CODE_Z000.toJson();
				flag = false;
			}
			if(flag){
				 loan = commonDao.getLoanNumberByOrderNumber(orderNumber);
				 if(null == loan){
					 rest = RestCode.CODE_A001.toJson();
					 flag = false;
				 }
			}
			if(flag){
				Map<String,Object> map = new HashMap<String,Object>();
				for(DeliveryGoods dg : newlist){
					map.put("childOrderNumber", dg.getChildOrderNumber());
					List<B2bOrderGoodsDtoMa> oglist = commonDao.searchOrderGoodsList(map);
					if(null == oglist || oglist.size() == 0){
						rest = RestCode.CODE_A001.toJson();
						flag = false;
						break;
					}
					B2bOrderGoodsDtoMa om = oglist.get(0);
					dg.setOrderNumber(orderNumber);
					dg.setPresentPrice(om.getPresentPrice());
					dg.setGoodsInfo(om.getGoodsInfo());
					dg.setBlock(om.getBlock());
					dg.setPrincipal(ArithUtil.round(ArithUtil.mul(dg.getPresentPrice(), dg.getDeliverWeight()), 2));//本金
					dg.setInterest(InterestUtil.getInterest(DateUtil.formatYearMonthDay(loan.getLoanStartTime()), 
							DateUtil.formatYearMonthDay(new Date()), dg.getPrincipal(), loan.getBankRate()));//利息
					dg.setServiceCharge(InterestUtil.getCoverCharges(DateUtil.formatYearMonthDay(loan.getLoanStartTime()), 
							DateUtil.formatYearMonthDay(new Date()), dg.getPrincipal(), loan.getTotalRate(), loan.getBankRate(),
							loan.getSevenRate(), 2));//服务费
					dg.setSevenCharge(InterestUtil.getSevenDayServiceCharges(DateUtil.formatYearMonthDay(loan.getLoanStartTime()),
							DateUtil.formatYearMonthDay(new Date()), dg.getPrincipal(), loan.getSevenRate()));
					deliveryAmount = ArithUtil.add(deliveryAmount, dg.getPrincipal());//提货单总金额
				}
			}
			if(flag){
				//生成提货单
				String deliveryNumber = creditDeliverNumber(DateUtil.formatYearMonthDayTwo(new Date())+KeyUtils.getNRandom(5));
				DeliveryOrder d = new DeliveryOrder();
				d.setOrderNumber(orderNumber);
				d.setDeliveryNumber(deliveryNumber);
				d.setStatus(1);
				d.setDate(new Date());
				d.setDeliveryGoodsInfo(JsonUtils.convertToString(newlist));
				d.setDeliveryAmount(deliveryAmount);
				mongoTemplate.save(d);
				//更新凭证
				Criteria cone = new Criteria();
				cone.andOperator(Criteria.where("type").is(2),
						Criteria.where("orderNumber").is(orderNumber));
				List<B2bPayVoucher> vlist = mongoTemplate.find(new Query(cone), B2bPayVoucher.class);
				if(null != vlist && vlist.size() > 0){
					for (B2bPayVoucher o : vlist) {
						Update update = new Update();
						update.set("orderNumber", deliveryNumber);
						mongoTemplate.upsert(new Query(Criteria.where("id").is(o.getId())),
								update, B2bPayVoucher.class);
					}
				}
			}
		}
		return rest;
	}
	/**
	 * 提货流水号防重复验证
	 * @param number
	 * @return
	 */
	private String creditDeliverNumber(String number){
		Query q = new Query(Criteria.where("deliveryNumber").is(number));
		DeliveryOrder d = mongoTemplate.findOne(q, DeliveryOrder.class);
		if(null != d){
			number = DateUtil.formatYearMonthDayTwo(new Date())+KeyUtils.getNRandom(5);
			creditDeliverNumber(number);
		}
		return number;
	}

	@Override
	public String savePayvoucher(String orderNumber, String imgUrl, Integer type) {
		String mongoId = "";
		OrderRecord or = new OrderRecord();
		String content = OrderRecordType.UPDATE.toString() + "订单号:";
		B2bOrderDtoMa om = tradeDao.getByOrderNumber(orderNumber);
		if(null == om){
			content = OrderRecordType.CUPDATE.toString() + "合同号:";
			om = tradeDao.getContractToOrder(orderNumber);
		}
		if(null != om){
			mongoId = KeyUtils.getUUID();
			or.setId(mongoId);
			or.setOrderNumber(orderNumber);
			or.setInsertTime(DateUtil.getDateFormat(new Date()));
			content += orderNumber + "上传凭证:<img src='" + imgUrl
					+ "' style='display:block;'/>";
			or.setContent(content);
			or.setType(1);
			or.setImgUrl(imgUrl);
			mongoTemplate.insert(or);
			// 存付款凭证表
			B2bPayVoucher voucher = new B2bPayVoucher();
			voucher.setId(KeyUtils.getUUID());
			voucher.setUrl(imgUrl);
			voucher.setOrderNumber(orderNumber);
			voucher.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			voucher.setType(type);
			voucher.setStatus(Constants.ONE);
			PurchaserInfo pinfo = new PurchaserInfo(om.getPurchaserInfo());
			voucher.setPurchaserName(pinfo.getPurchaserName());
			SupplierInfo sinfo = new SupplierInfo(om.getSupplierInfo());
			voucher.setSupplierName(sinfo.getSupplierName());
			voucher.setPurchaserPk(om.getPurchaserPk());
			voucher.setSupplierPk(om.getSupplierPk());
			voucher.setStorePk(om.getStorePk());
			voucher.setEmployeePk(om.getEmployeePk());
			voucher.setEmployeeName(om.getEmployeeName());
			voucher.setEmployeeNumber(om.getEmployeeNumber());
			mongoTemplate.insert(voucher);
		}
		return mongoId ;
	}

	@Override
	public void delPayvoucher(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		B2bPayVoucher b2bVoucher = mongoTemplate.findOne(query,B2bPayVoucher.class);
		if (null != b2bVoucher) {
			B2bOrderDtoMa om = tradeDao.getByOrderNumber(b2bVoucher.getOrderNumber());
			String content = OrderRecordType.UPDATE.toString();
			if(null == om){
				content = OrderRecordType.CUPDATE.toString();
				om = tradeDao.getContractToOrder(b2bVoucher.getOrderNumber());
			}
			// 增加订单删除凭证日志
			content += (b2bVoucher.getType() == 1 ? "付款" : "提货")+ "凭证:<img src='" + b2bVoucher.getUrl()
					+ "' style='display:block;'/>已被删除作废";
			B2bOrder order = new B2bOrder();
			order.setOrderNumber(b2bVoucher.getOrderNumber());
			OrderRecord or = new OrderRecord();
			String mongoId = KeyUtils.getUUID();
			or.setId(mongoId);
			or.setOrderNumber(b2bVoucher.getOrderNumber());
			or.setInsertTime(DateUtil.getDateFormat(new Date()));
			or.setContent(content);
			mongoTemplate.insert(or);
			// 删除对应记录
			mongoTemplate.remove(b2bVoucher);
		
		}
	}

	@Override
	public List<B2bPayVoucher> searchPayVoucherList(String orderNumber,
			Integer type) {
		//凭证
		Criteria cone = new Criteria();
		cone.andOperator(Criteria.where("type").is(type),
				Criteria.where("orderNumber").is(orderNumber));
		Query q = new Query(cone);
		return mongoTemplate.find(q,B2bPayVoucher.class);
				
	}

	@Override
	public String sendDeliverOrder(String deliveryNumber) {
		String rest = RestCode.CODE_0000.toJson();
		Query q = new Query(
				Criteria.where("deliveryNumber").is(deliveryNumber));
		DeliveryOrder d = mongoTemplate.findOne(q, DeliveryOrder.class);
		if(null != d && null != d.getDeliveryGoodsInfo() && !"".equals(d.getDeliveryGoodsInfo())
				&& null != d.getOrderNumber()){
			int orderStatus = 5;
			List<B2bOrderGoods> goodsList = new ArrayList<B2bOrderGoods>();
			B2bOrderDtoMa om = commonDao.getByOrderNumber(d.getOrderNumber());
			Boolean flag = true;
			if (null == om) {
				rest = RestCode.CODE_A001.toJson();
				flag = false;
			}
			// 已取消的不能发货
			if (flag && -1 == om.getOrderStatus()) {
				rest = RestCode.CODE_A001.toJson();
				flag = false;
			}
			// 已发货和已完成的订单不能发货
			if (flag && (4 == om.getOrderStatus() || 6 == om
					.getOrderStatus())) {
				rest = RestCode.CODE_A001.toJson();
				flag = false;
			}
			if (flag) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("orderNumber", om.getOrderNumber());
				List<B2bOrderGoodsDtoMa> orderGoods = commonDao.searchOrderGoodsList(map);
					List<DeliveryGoods> ogList = JsonUtils.toList(d.getDeliveryGoodsInfo(),
							DeliveryGoods.class);
					for (B2bOrderGoodsDtoMa o : orderGoods) {
						// 订单已经取消则不做处理
						if (-1 == o.getOrderStatus()) {
							continue;
						}
						for (DeliveryGoods g : ogList) {
							if (o.getChildOrderNumber().equals(
									g.getChildOrderNumber())) {
								// 已发货重量
								B2bOrderGoods og = new B2bOrderGoods();
								og.setChildOrderNumber(o.getChildOrderNumber());
								og.setBoxesShipped(o.getBoxesShipped()
										+ g.getDeliverBoxes());
								og.setWeightShipped(ArithUtil.add(
										o.getWeightShipped(), g.getDeliverWeight()));
								og.setOrderStatus(orderStatus);
								goodsList.add(og);
								// 发货单数据同步接口(物流系统提供)
								try {
									// 当前发货重量
									o.setBoxesShipped(og.getBoxesShipped()
											- o.getBoxesShipped());
									o.setWeightShipped(og.getWeightShipped()
											- o.getWeightShipped());
									// 发货/部分发货将订单同步给物流系统
									this.confirmFaHuoForB2B(o);
								} catch (Exception e) {
									logger.error("confirmFaHuoForB2B", e);
								}
							}
						}
					}
				B2bOrder order = new B2bOrder();
				order.UpdateDTO(om);
				order.setOrderStatus(orderStatus);
				tradeDao.updateOrder(order);
				for(B2bOrderGoods og : goodsList){
					tradeDao.updateOrderGoods(og);
				}
					Map<String, String> smsMap = new HashMap<String, String>();
					smsMap.put("order_id", d.getOrderNumber());
						try {
							String content = "";
							for (B2bOrderGoods og : goodsList) {
								content += "子订单:"
										+ og.getChildOrderNumber()
										+ "已发货数量:"
										+ og.getBoxesShipped()
										+ ",已发货重量:"
										+ new BigDecimal(og.getWeightShipped()
												.toString()).toPlainString() + " ";
							}
							this.insertOrderRecord(order,
									OrderRecordType.UPDATE.toString(), "订单号:"
											+ om.getOrderNumber() + "部分发货;" + content);
						} catch (Exception e) {
							logger.error("errorOrderLog", e);
						}
						try {
							cuccSmsService.sendMessage(null,
									om.getPurchaserPk(), true,
									SmsCode.SOME_DEL_PU.getValue(), smsMap);
						} catch (Exception e) {
							logger.error("errorPurchaserSms", e);
						}
						// 检验订单是否有未完成发货的商品
						try {
							map.put("flag", true);
							map.put("cancel", true);
							List<B2bOrderGoodsDtoMa> nsOdto = tradeDao.searchOrderGoodsList(map);
							if (null == nsOdto || nsOdto.size() == 0) {
								B2bOrder o = new B2bOrder();
								o.UpdateDTO(om);
								o.setOrderStatus(4);
								tradeDao.updateOrder(o);
								Map<String,Object> cmap = new HashMap<String,Object>();
								cmap.put("orderNumber", om.getOrderNumber());
								cmap.put("orderStatus", 4);
								tradeDao.updateChildOrderStatus(cmap);
								this.insertOrderRecord(o,OrderRecordType.UPDATE.toString(),"订单号:" 
																			+ om.getOrderNumber() + "已全部发货,状态为已发货");
							}
						} catch (Exception e) {
							logger.error("shipment", e);
						}
			}
		}else{
			rest = RestCode.CODE_A001.toJson();
		}
		
		return rest;
	}
	
	private void insertOrderRecord(B2bOrder order, String type,
			String content) {
		OrderRecord or = new OrderRecord();
		or.setId(KeyUtils.getUUID());
		or.setOrderNumber(order.getOrderNumber());
		or.setContent(type+content);
		or.setInsertTime(DateUtil.getDateFormat(new Date()));
		if(null != order.getOrderStatus()){
			or.setStatus(order.getOrderStatus());
		}
		mongoTemplate.save(or);
		
	}

	@Override
	public void confirmFaHuoForB2B(B2bOrderGoodsDtoMa goods) {

		if(Block.CF.getValue().equals(goods.getBlock())){
			B2bOrderDtoMa order = commonDao.getByOrderNumber(goods.getOrderNumber());
			LogisticsModelDto modelDto = commonDao.getLogisticsModel(order.getLogisticsModelPk());
			// 商家承运
			if (modelDto.getType() == 2) {
				shopCarryToLg(order,goods,modelDto);
			}
			
			// 平台承运
			if (modelDto.getType() == 1) {
				lgCarryToLg(order,goods,modelDto);
			}
		}
	}
	
	//平台承运-同步到物流
	private void lgCarryToLg(B2bOrderDtoMa om,B2bOrderGoodsDtoMa ogm, LogisticsModelDto modelDto) {
		LgDeliveryOrderForB2BPTDto b = new LgDeliveryOrderForB2BPTDto();
		b.setOrderPk(om.getOrderNumber());
		// 运费原总价
		b.setOrderTime(DateUtil.DATE_AND_TIME_FORMATER.format(om.getInsertTime()));
		b.setOriginalFreight(ogm.getOriginalFreight());// 运费(对外单价)
		b.setPresentFreight(ogm.getPresentFreight());// 实际运费(对外单价)
		// 插入始发地
		b = addPlantLg(b, null==ogm.getSxGoods()?ogm.getCfGoods().getPlantPk():ogm.getSxGoods().getPlantPk(),
				null==ogm.getSxGoods()?ogm.getCfGoods().getWarePk():ogm.getSxGoods().getWarePk(),
						om.getStorePk());
		b.setToCompanyName(om.getPurchaser().getPurchaserName());
		b.setToProvinceName(om.getAddress().getProvinceName());
		b.setToCityName(om.getAddress().getCityName());
		b.setToAreaName(om.getAddress().getAreaName());
		b.setToTownName(om.getAddress().getTownName());
		b.setToAddress(om.getAddress().getAddress());
		b.setToContacts(om.getAddress().getContacts());
		b.setToContactsTel(om.getAddress().getContactsTel());
		b.setMember(om.getMemberName());
		b.setMemberPk(om.getMemberPk());
		b.setPaymentName(om.getPaymentName());
		b.setPaymentTime(om.getPaymentTime() != null
				&& om.getPaymentTime().equals("") ? DateUtil.DATE_AND_TIME_FORMATER
				.format(om.getPaymentTime()) : null);
		b.setPurchaserName(om.getPurchaser().getInvoiceName());
		b.setPurchaserPk(om.getPurchaserPk());
		b.setSupplierName(om.getSupplier().getSupplierName());
		b.setSupplierPk(om.getSupplierPk());
		b.setInvoiceName(om.getPurchaser().getInvoiceName());
		b.setInvoiceTaxidNumber(om.getPurchaser().getInvoiceTaxidNumber());
		b.setInvoiceRegPhone(om.getPurchaser().getInvoiceRegPhone());
		b.setInvoiceBankAccount(om.getPurchaser().getInvoiceBankAccount());
		b.setInvoiceBankName(om.getPurchaser().getInvoiceBankName());
		// 插入发票
		if(null!=om.getPurchaser().getInvoiceName()&&!"".equals(om.getPurchaser().getInvoiceName())){
			 b = addInvoiceLg(b, om.getPurchaser().getInvoiceName());	
		}
		B2bCompanyDto b2bCompanyDto = commonDao.getCompanyByPk(om.getPurchaserPk());
		b.setInvoiceProvinceName(b2bCompanyDto.getProvinceName());
		b.setInvoiceCityName(b2bCompanyDto.getCityName());
		b.setInvoiceAreaName(b2bCompanyDto.getAreaName());
		b.setInvoiceRegAddress(b2bCompanyDto.getRegAddress());
		// 线路的实际结算单价
		b.setBasisLinePrice(ogm.getPresentFreight());//实际运费单价（供应商可以修改运费单价）
		b.setOriginalFreight(ogm.getOriginalFreight());//原运费单价
		b.setProductName(ogm.getCfGoods().getProductName());
		b.setProductPk(ogm.getCfGoods().getProductPk());
		b.setVarietiesName(ogm.getCfGoods().getVarietiesName());
		b.setSeedvarietyName(ogm.getCfGoods().getSeedvarietyName());
		b.setSeriesName(ogm.getCfGoods().getSeriesName());
		b.setSpecName(ogm.getCfGoods().getSpecifications());
		b.setGradeName(ogm.getCfGoods().getGradeName());
		b.setGradePk(ogm.getCfGoods().getGradePk());
		b.setBatchNumber(ogm.getCfGoods().getBatchNumber());
		b.setUnit(ogm.getCfGoods().getUnit());
		b.setWeight(ogm.getWeightShipped());// 发货重量
		b.setBoxes(ogm.getBoxesShipped());// 发货箱数
		b.setTareWeight(ArithUtil.round(
				ArithUtil.mul(
						ArithUtil.div(ogm.getWeightShipped(),
								ogm.getBoxesShipped()), 1000), 2));// 箱重
		b.setRemark(om.getMeno());
		b.setLgLinePk(ogm.getLogisticsPk());
		b.setLgLinePricePk(ogm.getLogisticsStepInfoPk());
		try {
			boolean flag = this.confirmFaHuoForB2BPT(b);
			// 如果插入失败，插入芒果
			if (!flag) {
				mongoTemplate.insert(b);
			}
		} catch (Exception e) {
			logger.error("b2b_confirmFaHuoForB2BPT", e);
			logger.error("b2b_confirmFaHuoForB2BPT_param", b);
		}
	}

 	private LgDeliveryOrderForB2BPTDto addInvoiceLg(LgDeliveryOrderForB2BPTDto b, String invoiceName) {
 		Map<String, Object> map=new HashMap<String, Object>();
 		map.put("name",invoiceName  );
 		map.put("isDelete",  1);
 		map.put("auditStatus", 2 );
 		map.put("isVisable", 1 );
 		List<B2bCompanyDto> list=commonDao.searchCompanyList(map);
 		if(null!=list&&list.size()>0){
 			B2bCompanyDto company=list.get(0);
 			if (company != null && !company.equals("")) {
 				b.setInvoiceProvinceName(company.getProvinceName());
 				b.setInvoiceCityName(company.getCityName());
 				b.setInvoiceAreaName(company.getAreaName());
 				b.setInvoiceRegAddress(company.getRegAddress());
 			}
 		}
		return b;
	} 

	private LgDeliveryOrderForB2BPTDto addPlantLg(LgDeliveryOrderForB2BPTDto b,
			String plantPk, String warePk, String storePk) {
		B2bPlantDto plantDto = commonDao.getPlantByPk(plantPk);
		if (plantDto != null && !plantDto.equals("")) {
			b.setFromCompanyName(plantDto.getName());
			b.setFromProvinceName(plantDto.getProvinceName());
			b.setFromCityName(plantDto.getCityName());
			b.setFromAreaName(plantDto.getAreaName());
			b.setFromTownName(plantDto.getTownName());
			b.setFromProvincePk(plantDto.getProvince());
			b.setFromCityPk(plantDto.getCity());
			b.setFromAreaPk(plantDto.getArea());
			b.setFromTownPk(plantDto.getTown());
			// B2bWareDto wareDto = b2bWareDaoEx.getByPk(warePk);
			b.setFromAddress(plantDto.getAddress());
			// 如果生产厂区的联系人，电话未填写的时候，取店铺的联系人跟电话
			if (plantDto.getContacts() == null
					|| plantDto.getContactsTel().equals("")) {
				B2bStoreDto storeDto = commonDao.getStoreByPk(storePk);
				b.setFromContacts(storeDto.getContacts());
				b.setFromContactsTel(storeDto.getContactsTel());
			} else {
				b.setFromContacts(plantDto.getContacts());
				b.setFromContactsTel(plantDto.getContactsTel());
			}

		}
		return b;
	}

	private void shopCarryToLg(B2bOrderDtoMa om,B2bOrderGoodsDtoMa ogm,
			LogisticsModelDto modelDto) {
		LgDeliveryOrderForB2BDto b = new LgDeliveryOrderForB2BDto();
		if (ogm.getLogisticsCarrierPk() != null
				&& !ogm.getLogisticsCarrierPk().equals("")) {
			b.setOrderPk(ogm.getOrderNumber());
			b.setOrderTime(DateUtil.DATE_AND_TIME_FORMATER.format(om.getInsertTime()));
			b.setOriginalFreight(ogm.getOriginalFreight());// 运费(对外单价)
			b.setPresentFreight(ogm.getPresentFreight());// 实际运费(对外单价)
			if (modelDto.getType() == 2) {
				b.setLogisticsCompanyPk(ogm.getLogisticsCarrierPk());
				b.setLogisticsCompanyName(ogm.getLogisticsCarrierName());
			}
			// 插入始发地
			b = addPlant(b, ogm.getCfGoods().getPlantPk(), ogm.getCfGoods().getWarePk(),
					om.getStorePk());
			b.setToCompanyName(om.getPurchaser().getPurchaserName());
			b.setToProvinceName(om.getAddress().getProvinceName());
			b.setToCityName(om.getAddress().getCityName());
			b.setToAreaName(om.getAddress().getAreaName());
			b.setToTownName(om.getAddress().getTownName());
			b.setToAddress(om.getAddress().getAddress());
			b.setToContacts(om.getAddress().getContacts());
			b.setToContactsTel(om.getAddress().getContactsTel());
			b.setMember(om.getMemberName());
			b.setMemberPk(om.getMemberPk());
			b.setPaymentName(om.getPaymentName());
			b.setPaymentTime(om.getPaymentTime() != null
					&& om.getPaymentTime().equals("") ? DateUtil.DATE_AND_TIME_FORMATER
					.format(om.getPaymentTime()) : null);
			b.setPurchaserName(om.getPurchaser().getInvoiceName());
			b.setPurchaserPk(om.getPurchaserPk());
			b.setSupplierName(om.getSupplier().getSupplierName());
			b.setSupplierPk(om.getSupplierPk());
			b.setInvoiceName(om.getPurchaser().getInvoiceName());
			b.setInvoiceTaxidNumber(om.getPurchaser().getInvoiceTaxidNumber());
			b.setInvoiceRegPhone(om.getPurchaser().getInvoiceRegPhone());
			b.setInvoiceBankAccount(om.getPurchaser().getInvoiceBankAccount());
			b.setInvoiceBankName(om.getPurchaser().getInvoiceBankName());
			// 插入发票
		//	b = addInvoice(b, order.getInvoicePk());
			B2bCompanyDto b2bCompanyDto = commonDao.getCompanyByPk(om.getPurchaserPk());
			b.setInvoiceProvinceName(b2bCompanyDto.getProvinceName());
			b.setInvoiceCityName(b2bCompanyDto.getCityName());
			b.setInvoiceAreaName(b2bCompanyDto.getAreaName());
			b.setInvoiceRegAddress(b2bCompanyDto.getRegAddress());
			b.setBasisLinePrice(ogm.getPresentFreight());
			b.setSettleWeight(ogm.getWeight());
			b.setProductName(ogm.getCfGoods().getProductName());
			b.setProductPk(ogm.getCfGoods().getProductPk());
			b.setVarietiesName(ogm.getCfGoods().getVarietiesName());
			b.setSeedvarietyName(ogm.getCfGoods().getSeedvarietyName());
			b.setSeriesName(ogm.getCfGoods().getSeriesName());
			b.setSpecName(ogm.getCfGoods().getSpecifications());
			b.setGradeName(ogm.getCfGoods().getGradeName());
			b.setGradePk(ogm.getCfGoods().getGradePk());
			b.setBatchNumber(ogm.getCfGoods().getBatchNumber());
			b.setUnit(ogm.getCfGoods().getUnit());
			b.setWeight(ogm.getWeightShipped());// 发货重量
			b.setBoxes(ogm.getBoxesShipped());// 发货箱数
			b.setTareWeight(ArithUtil.round(
					ArithUtil.mul(
							ArithUtil.div(ogm.getWeightShipped(),
									ogm.getBoxesShipped()), 1000), 2));// 箱重
			b.setRemark(om.getMeno());
			try {
				boolean flag = this.confirmFaHuoForB2BSJ(b);
				// 如果插入失败，插入芒果
				if (!flag) {
					mongoTemplate.insert(b);
				}
			} catch (Exception e) {
				logger.error("b2b_confirmFaHuoForB2B", e);
				logger.error("b2b_confirmFaHuoForB2B_param", b);
			}
		}
	}


	private LgDeliveryOrderForB2BDto addPlant(LgDeliveryOrderForB2BDto b,
			String plantPk, String warePk, String storePk) {
		B2bPlantDto plantDto = commonDao.getPlantByPk(plantPk);
		if (plantDto != null && !plantDto.equals("")) {
			b.setFromCompanyName(plantDto.getName());
			b.setFromProvinceName(plantDto.getProvinceName());
			b.setFromCityName(plantDto.getCityName());
			b.setFromAreaName(plantDto.getAreaName());
			b.setFromTownName(plantDto.getTownName());
			b.setFromProvincePk(plantDto.getProvince());
			b.setFromCityPk(plantDto.getCity());
			b.setFromAreaPk(plantDto.getArea());
			b.setFromTownPk(plantDto.getTown());
			b.setFromAddress(plantDto.getAddress());
			if (plantDto.getContacts() == null
					|| plantDto.getContactsTel().equals("")) {
				B2bStoreDto storeDto = commonDao.getStoreByPk(storePk);
				b.setFromContacts(storeDto.getContacts());
				b.setFromContactsTel(storeDto.getContactsTel());
			} else {
				b.setFromContacts(plantDto.getContacts());
				b.setFromContactsTel(plantDto.getContactsTel());
			}
		}
		return b;
	}
	
	@Override
	public void downloadDeliveryEntrust(String deliveryNumber,String filePath, HttpServletResponse resp){
		DeliveryOrder order = mongoTemplate.findOne(new Query(Criteria.
				where("deliveryNumber").is(deliveryNumber)), DeliveryOrder.class);
		if(null != order && null != order.getDeliveryGoodsInfo() && 
				!"".equals(order.getDeliveryGoodsInfo())){
			// 临时文件路径
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			try {
				Double totalweight = 0d;
				String totalweightStr = totalweight.toString();
				Integer index = 1;
				B2bOrderDtoMa om = tradeDao.getByOrderNumber(order.getOrderNumber());
				if(null == om){
					om = tradeDao.getContractToOrder(order.getOrderNumber());
				}
				List<DeliveryGoods> dglist = JsonUtils.toList(order.getDeliveryGoodsInfo(), DeliveryGoods.class);
				for(DeliveryGoods g : dglist){
					totalweight = ArithUtil.add(totalweight, g.getDeliverWeight());
					totalweightStr =	new BigDecimal(totalweight).setScale(5,   BigDecimal.ROUND_HALF_UP).toString();
				}	
				//使用itext-asian.jar中的字体
				BaseFont baseFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
				Document document = new Document();
				PdfWriter writer1 = PdfWriter.getInstance(document, new FileOutputStream(filePath+deliveryNumber+"_2.pdf"));
				PdfWriter writer2 = PdfWriter.getInstance(document, resp.getOutputStream());
				writer1.setPageEvent(new PdfFooterEvent(baseFont,10,""," / ","")); 
				writer2.setPageEvent(new PdfFooterEvent(baseFont,10,""," / ","")); 
				document.open();
				Paragraph text = new Paragraph("");  
				text.add(new Chunk(new LineSeparator()));
				document.add(text);  
				Paragraph text1 = new Paragraph("委托书(2，提货委托书)",new Font(baseFont, 15, Font.BOLD));
				text1.setAlignment(Element.ALIGN_CENTER);
				document.add(text1);
				Chunk c1 = new Chunk(om.getStoreName()+"    ", new Font(baseFont, 10, Font.NORMAL));
				c1.setUnderline(0.2f, -2f);
			    document.add(c1);
				Chunk c2 = new Chunk("：\n", new Font(baseFont, 10, Font.NORMAL));
			    document.add(c2);
			    String year =DateUtil.getDateYearMonthDay(order.getDate())[0];
			    String month =DateUtil.getDateYearMonthDay(order.getDate())[1];
			    String day =DateUtil.getDateYearMonthDay(order.getDate())[2];
			    Chunk chkweight = new Chunk("   "+totalweightStr+"   ", new Font(baseFont, 10, Font.NORMAL));
			    chkweight.setUnderline(0.2f, -2f);
			    Chunk chkyear =new Chunk("   "+year+"   ",new Font(baseFont, 10, Font.NORMAL)); 
			    chkyear.setUnderline(0.2f, -2f);
			    Chunk chkmonth =new Chunk("   "+month+"   ",new Font(baseFont, 10, Font.NORMAL)); 
			    chkmonth.setUnderline(0.2f, -2f);
			    Chunk chkday =new Chunk("   "+day+"   ",new Font(baseFont, 10, Font.NORMAL)); 
			    chkday.setUnderline(0.2f, -2f);
			    Phrase text2 = new Phrase();  
			    text2.add(new Chunk("        我方与贵公司签订的购销合同（编号："+order.getOrderNumber()+
			    		"）项下的", new Font(baseFont, 10, Font.NORMAL)));
			    text2.add(chkweight);
			    text2.add(new Chunk("吨绦纶丝，请安排", new Font(baseFont, 10, Font.NORMAL)));
			    text2.add(chkyear);
			    text2.add(new Chunk("年", new Font(baseFont, 10, Font.NORMAL)));
			    text2.add(chkmonth);
			    text2.add(new Chunk("月", new Font(baseFont, 10, Font.NORMAL)));
			    text2.add(chkday);
			    text2.add(new Chunk("日发货，发货明细如下：\n", new Font(baseFont, 10, Font.NORMAL)));
			    text2.setLeading(20);  
			    document.add(text2);  
				for(DeliveryGoods g : dglist){
					Phrase text3 = new Phrase();
					Chunk chkvar = new Chunk("   "+(null != g.getCfGoods().getVarietiesName()?g.getCfGoods()
							.getVarietiesName():"")+"   ；\n", new Font(baseFont, 10, Font.NORMAL));
					chkvar.setUnderline(0.2f, -2f);
					Chunk chkspec = new Chunk("   "+(null != g.getCfGoods().getSpecifications()?g.getCfGoods()
							.getSpecifications():"")+"   ；\n", new Font(baseFont, 10, Font.NORMAL));
					chkspec.setUnderline(0.2f, -2f);
					Chunk chkcount = new Chunk("   "+g.getDeliverBoxes()+ (null != g.getCfGoods().getUnit()?
							(UnitType.fromInt(g.getCfGoods().getUnit().toString()).toString()):"")+"，"
							+new BigDecimal(g.getDeliverWeight()).setScale(5,   BigDecimal.ROUND_HALF_UP).toString()+"吨   ；\n",new Font(baseFont, 10, Font.NORMAL));
					chkcount.setUnderline(0.2f, -2f);
					Chunk chkbatch = new Chunk("   "+g.getCfGoods().getBatchNumber()+"   ；\n", 
							new Font(baseFont, 10, Font.NORMAL));
					chkbatch.setUnderline(0.2f, -2f);
					text3.add(new Chunk("       （"+(index++)+"）品种", new Font(baseFont, 10, Font.NORMAL)));
					text3.add(chkvar);
					text3.add(new Chunk("                   规格", new Font(baseFont, 10, Font.NORMAL)));
					text3.add(chkspec);
					text3.add(new Chunk("                   数量", new Font(baseFont, 10, Font.NORMAL)));
					text3.add(chkcount);
					text3.add(new Chunk("                   批号", new Font(baseFont, 10, Font.NORMAL)));
					text3.add(chkbatch);
					text3.setLeading(20);
					document.add(text3);
				}
				Paragraph text4 = new Paragraph("      我方委托________________________车辆，代办到贵公司提货业务，"
						+"请予办理。",new Font(baseFont, 10, Font.NORMAL));
				text4.setLeading(20);
				document.add(text4);
				Paragraph text5 = new Paragraph("附件:              司机驾驶证、行驶证复印件，并签字。",
						new Font(baseFont, 10, Font.NORMAL));
				text5.setLeading(20);
				document.add(text5);
				Paragraph text6 = new Paragraph("委托单位：                                                ",new Font(baseFont, 10, Font.NORMAL));
				text6.setAlignment(Element.ALIGN_RIGHT);
				text6.setLeading(20);
				document.add(text6);
				Paragraph text7 = new Paragraph("电话：                                                   ",new Font(baseFont, 10, Font.NORMAL));
				text7.setAlignment(Element.ALIGN_RIGHT);
				text7.setLeading(20);
				document.add(text7);
				Paragraph text8 = new Paragraph("日期：   "+year+"   年   "+month+"   月   "+day+"   日",
						new Font(baseFont, 10, Font.NORMAL));
				text8.setLeading(20);
				text8.setAlignment(Element.ALIGN_RIGHT);
				document.add(text8);
				document.close();
			} catch (Exception e) {
				logger.error("downloadDevlieryNumber",e);
			}
		}
	}

	@Override
	public void exportDeliveryReq(String deliveryNumber,String fliePath, HttpServletResponse resp){
		Document document = new Document();
		// 获取数据
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("deliveryNumber").is(deliveryNumber));
		Query query = new Query(c);
		DeliveryOrder deliveryOrder = mongoTemplate.findOne(query, DeliveryOrder.class);
		try {
			if (deliveryOrder != null) {
				PdfWriter.getInstance(document, new FileOutputStream(fliePath));
				PdfWriter.getInstance(document, resp.getOutputStream());

				BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
				BaseFont baseFont1 = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-V", false);

				Font font = new Font(baseFont);
				document.open();
				Paragraph text1 = new Paragraph();
				Chunk chunk1 = new Chunk("提货申请单（第一步）", new Font(baseFont, 16, Font.BOLD));
				Chunk chunk2 = new Chunk("（编号：                                         ）", new Font(baseFont, 12));
				text1.add(chunk1);
				text1.add(chunk2);
				text1.setAlignment(Element.ALIGN_CENTER);
				document.add(text1);
				PdfPTable headerTable = new PdfPTable(4);
				headerTable.setSpacingBefore(10);// 设置表头间距
				headerTable.setWidthPercentage(70);
				headerTable.setTotalWidth(500);// 设置表格的总宽度
				headerTable.setTotalWidth(new float[] { 70, 180, 70, 180 });// 设置表格的各列宽度
				headerTable.setLockedWidth(true);
				// 使用以上两个函数，必须使用以下函数，将宽度锁定。

				headerTable.addCell(getCell("申请单位", font, 1, false, null));
				B2bOrderDto orderExtDto = commonDao.getByOrderNumber(deliveryOrder.getOrderNumber());
				PurchaserInfo purchaserInfo = JSONObject.parseObject(orderExtDto.getPurchaserInfo(),
						PurchaserInfo.class);
				headerTable.addCell(getCell(purchaserInfo.getPurchaserName(), font, 1, false, null));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
				SimpleDateFormat sdfChina = new SimpleDateFormat("yyyy年MM月dd日");
				headerTable.addCell(getCell("申请日期", font, 1, false, null));
				headerTable.addCell(getCell(sdf.format(deliveryOrder.getDate()), font, 1, false, null));
				PdfPCell cell1 = getCell("申请理由", new Font(baseFont1), 1, false, null);
                cell1.setPaddingTop(Float.valueOf((int)Math.ceil(cell1.getEffectivePaddingTop()/2))*100);
                cell1.setPaddingBottom(Float.valueOf((int)Math.ceil(cell1.getEffectivePaddingBottom()/2)*150));
                cell1.setPaddingLeft(Float.valueOf((int)Math.ceil(cell1.getEffectivePaddingLeft()/2)*30));
                
				headerTable.addCell(cell1);
				PdfPTable table = setGoodsTable(deliveryOrder, font, sdfChina);
				PdfPCell cell = new PdfPCell(table);
				cell.setPadding(0);
				cell.setColspan(3);
				headerTable.addCell(cell);
				document.add(headerTable);
				document.close();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * @param deliveryOrder
	 * @param font
	 * @param sdfChina
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable setGoodsTable(DeliveryOrder deliveryOrder, Font font, SimpleDateFormat sdfChina)
			throws DocumentException {
		PdfPTable table = new PdfPTable(7);
		table.setTotalWidth(420);// 设置表格的总宽度
		table.setTotalWidth(new float[] { 40, 110, 60, 40, 60, 60, 60 });// 设置表格的各列宽度
		table.setLockedWidth(true);
		// 借款单号
		String qualityValue = "       本公司因销售需要，根据                                                                        号“化纤贷”质押监管业务协议以及本公司与苏州康海供应链管理有限公司签定的\n"
				+ deliveryOrder.getOrderNumber() + "       号《购销合同》的有关约定，特向" + "苏州康海供应链管理有限公司申请提取以下监管货物，货物相对应的银行贷款本息\n及服务费和运费等已全部付清。\n\n"
				+ "    本公司此次申请提取的货物清单如下：";
		table.addCell(getCell(qualityValue, font, 7, false, null));
		table.addCell(getCell("序号", font, 1, true, null));
		table.addCell(getCell("商品品名，规格", font, 1, true, null));
		table.addCell(getCell("商品批号", font, 1, true, null));
		table.addCell(getCell("箱数", font, 1, true, null));
		table.addCell(getCell("重量（吨）", font, 1, true, null));
		table.addCell(getCell("合同单价（元/吨）", font, 1, true, null));
		table.addCell(getCell("商品金额（元）", font, 1, true, null));
		if (deliveryOrder.getDeliveryGoodsInfo() != null ) {
			List<DeliveryGoods> goods = JsonUtils.toList(deliveryOrder.getDeliveryGoodsInfo(), DeliveryGoods.class);
			for (int i = 0; i < goods.size(); i++) {
				CfGoods goodsinfo = goods.get(i).getCfGoods();
				table.addCell(getCell(Integer.toString(i + 1), font, 1, true, null));
				String goodInfo = 	getGoodsInfo(goodsinfo);
 				table.addCell(getCell(goodInfo,font, 1, true, null));
				table.addCell(getCell(goodsinfo.getPackNumber(), font, 1, true, null));
				table.addCell(getCell(String.valueOf(goods.get(i).getDeliverBoxes()), font, 1,
						true, null));
				table.addCell(getCell(new BigDecimal(goods.get(i).getDeliverWeight()).setScale(5,   BigDecimal.ROUND_HALF_UP).toString(), font, 1,
						true, null));
				table.addCell(getCell(String.valueOf(goods.get(i).getPresentPrice()), font, 1,
						true, null));
				table.addCell(getCell(
								ArithUtil
										.mulBigDecimal(goods.get(i).getDeliverWeight(),
												goods.get(i).getPresentPrice())
										.setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
								font, 1, true, null));
			}
		}
	       String year =DateUtil.getDateYearMonthDay(deliveryOrder.getDate())[0];
			    String month =DateUtil.getDateYearMonthDay(deliveryOrder.getDate())[1];
			    String day =DateUtil.getDateYearMonthDay(deliveryOrder.getDate())[2];
		table.addCell(getCell( "         特此申请\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
			    +"                                                                                                                                                                                                                                            （单位公章）                                      \n\n"
				+"                                                                                                                                                                                                                                           法定代表人或有权人：（签章）                                              \n\n\n "+
			    "                                                                                                                                                                                                                                            "+year+"   年   "+month+"   月   "+day+"   日", font, 7, false, null));
		return table;
	}

	/**
	 * @param goodsinfo
	 * @param goodInfo
	 */
	private String getGoodsInfo(CfGoods goodsinfo) {

		StringBuilder sb = new StringBuilder();
		if (goodsinfo.getProductName()!=null&&!goodsinfo.getProductName().equals("")) {
			sb.append(goodsinfo.getProductName());
		}
		if( goodsinfo.getVarietiesName()!=null &&!goodsinfo.getVarietiesName().equals("")) {
			sb.append(" ");
			sb.append(goodsinfo.getVarietiesName());
		}
		if (goodsinfo.getSpecifications()!=null &&!goodsinfo.getSpecifications().equals("")) {
			sb.append(" ");
			sb.append(goodsinfo.getSpecifications());
		}
		return sb.toString();
	}
	
	
	/**
    *
    * @param value
    *            值
    * @param font
    *            字体
    * @param colspan
    *            合并宽
    * @param alginCenter
    *            是否居中
    * @param rowspan
    *            合并高
    * @return
    */
   public static PdfPCell getCell(String value, Font font, Integer colspan,
                            boolean alginCenter, Integer rowspan) {
       PdfPCell cell = new PdfPCell();
  
       Paragraph p = new Paragraph(value, font);
       cell.setPhrase(p);
       cell.setColspan(colspan);
       font.setSize(6);
       if (rowspan != null) {
           cell.setRowspan(rowspan);
       }
       if (alginCenter) {
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
       }
       return cell;
   }
}
