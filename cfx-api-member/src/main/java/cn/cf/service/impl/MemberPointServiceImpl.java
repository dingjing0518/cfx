package cn.cf.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.common.RestCode;
import cn.cf.constant.MemberSys;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bDimensionalityExtrewardDaoEx;
import cn.cf.dao.B2bDimensionalityPresentExDao;
import cn.cf.dao.B2bDimensionalityRewardDao;
import cn.cf.dao.B2bEconomicsGoodsDao;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bMemberGradeDao;
import cn.cf.dao.B2bOrderDaoEx;
import cn.cf.dao.B2bOrderGoodsDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.dto.B2bDimensionalityRewardDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.MemberPointPeriod;
import cn.cf.entry.MemberPointErrorInfo;
import cn.cf.model.B2bDimensionalityPresent;
import cn.cf.model.B2bMember;
import cn.cf.service.MemberPointService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;


@Service
public class MemberPointServiceImpl implements MemberPointService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private B2bMemberDaoEx memberDaoEx;

    @Autowired
    private B2bDimensionalityRewardDao b2bDimensionalityRewardDao;

    @Autowired
    private MemberPointService memberPointService;

    @Autowired
    private B2bDimensionalityExtrewardDaoEx b2bDimensionalityExtrewardDaoEx;

    @Autowired
    private B2bMemberGradeDao b2bMemberGradeDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private B2bCompanyDaoEx b2bCompanyExtDao;


    @Autowired
    private B2bDimensionalityPresentExDao dimensionalityPresentDao;

    @Autowired
    private B2bOrderDaoEx orderDaoEx;

    @Autowired
    private B2bEconomicsGoodsDao economicsGoodsDao;

    @Autowired
    private B2bOrderGoodsDao orderGoodsDao;

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
            logger.error("非额外奖励", e);
            restCode = RestCode.CODE_S999;
        } finally {
            // 添加插入失败的记录存mango
            // addErrorRecord(restCode, point);
        }
        return restCode;
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
    public RestCode addPoint3(String memberPk, String companyPk, Integer pointType, String dimenType, String companyType) {
        RestCode restCode = RestCode.CODE_0000;
        MangoMemberPoint point = new MangoMemberPoint();
        try {
            point.setDimenType(dimenType);
            // 1.查询交易相关具体的经验值
            B2bDimensionalityRewardDto dto = searchDimension(dimenType);
            // 2 插入记录
            if (null != dto) {
                insertMember2(memberPk, companyPk, point, dto, pointType, companyType);
            } else {
            }
        } catch (Exception e) {
            logger.error("非额外奖励", e);
            restCode = RestCode.CODE_S999;
        } finally {
            // 添加插入失败的记录存mango
            // addErrorRecord(restCode, point);
        }
        return restCode;
    }


    /**
     * 会员体系埋点方法（额外奖励）
     *
     * @param memberPk
     * @param companyPk
     * @param pointType
     * @return
     */
    @Override
    @Transactional
    public RestCode addPointForExtReward(String memberPk, String companyPk, Integer pointType, String dimenType) {
        RestCode restCode = RestCode.CODE_0000;
        MangoMemberPoint point = new MangoMemberPoint();
        point.setDimenType(dimenType);
        try {
            // 1.查询交易相关具体的经验值
            List<B2bDimensionalityExtrewardDto> list = getDimensionalityExtReward(dimenType);
            if (list.size() > 0) {
                // 2 插入记录
                insertMemberForExtReward(memberPk, companyPk, point, list.get(0), pointType);
            }

        } catch (Exception e) {
            logger.error("额外奖励", e);
            restCode = RestCode.CODE_S999;
        } finally {
            // 添加插入失败的记录存mango
            // addErrorRecord(restCode, point);
        }
        return restCode;
    }

    private void insertMemberForExtReward(String memberPk, String companyPk, MangoMemberPoint point,
                                          B2bDimensionalityExtrewardDto dto, Integer pointType) {
        B2bMemberDto memberDto = memberDaoEx.getByPk(memberPk);
        B2bCompanyDto companyDto = b2bCompanyExtDao.getByPk(companyPk);
        Double memberfbGradeRatio = null;
        Double memberemGradeRatio = null;

        // 1.1等级系数
        if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
            B2bMemberGradeDto gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(
                    (memberDto.getLevel() == null || memberDto.getLevel().equals("")) ? 1 : memberDto.getLevel());
            if (gradeDto != null) {
                memberfbGradeRatio = gradeDto.getFbGradeRatio();
                memberemGradeRatio = gradeDto.getEmGradeRatio();
            }
        }

        // 1.2总系数
        Double totalEmpiricalValue = ArithUtil.mul(memberemGradeRatio, dto.getEmpiricalValue());
        Double totalfbGradeValue = ArithUtil.mul(memberfbGradeRatio, dto.getFibreShellNumber());
        point.setCompanyPk(companyPk);
        point.setMemberPk(memberPk);
        point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
        point.setExpValue(ArithUtil.round((pointType == 2 ? (-totalEmpiricalValue) : totalEmpiricalValue), 2));
        point.setFiberValue(ArithUtil.round((pointType == 2 ? (-totalfbGradeValue) : totalfbGradeValue), 2));
        mongoTemplate.insert(point);

        // 3. 更新用户总积分
        updateMemberValue(memberPk,totalEmpiricalValue,totalfbGradeValue);

        // 4.插入加分明细
        addB2bExtDimensionalityPresent(dto, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio);

        // 5.添加赠送方式:赠送周期类型1.一次赠送，2每天，3每周
        /*if (dto.getPeriodType() != 1) {
			insertMemberPointPeriodExtReward(point, dto, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio);
		}*/

    }

//    private void insertMemberPointPeriodExtReward(MangoMemberPoint point, B2bDimensionalityExtrewardDto dto,
//                                                  B2bMemberDto memberDto, B2bCompanyDto companyDto, Double memberfbGradeRatio, Double memberemGradeRatio) {
//        MemberPointPeriod p = new MemberPointPeriod();
//        p.setRewardPk(dto.getPk());
//        p.setDimenCategory(dto.getDimenCategory());
//        p.setDimenType(dto.getDimenType());
//        p.setPeriodType(dto.getPeriodType());
//        p.setFibreShellNumber(dto.getFibreShellNumber());
//        p.setEmpiricalValue(dto.getEmpiricalValue());
//        p.setCompanyPk(companyDto.getPk());
//        p.setMemberPk(memberDto.getPk());
//        p.setFbGradeRatio(memberfbGradeRatio);
//        p.setEmGradeRatio(memberemGradeRatio);
//        p.setInsertTime(new Date());
//        p.setExpValue(point.getExpValue());
//        p.setFiberValue(point.getFiberValue());
//        p.setCompanyName(companyDto.getName());
//        p.setDimenName(dto.getDimenName());
//        p.setDimenTypeName(dto.getDimenTypeName());
//        p.setContactsTel(companyDto.getContactsTel());
//        p.setType(2);
//        p.setIsDelete(1);
//        mongoTemplate.insert(p);
//    }

    private void addB2bExtDimensionalityPresent(B2bDimensionalityExtrewardDto dto, B2bMemberDto memberDto,
                                                B2bCompanyDto companyDto, Double memberfbGradeRatio, Double memberemGradeRatio) {
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
        d.setFibreShellRatio(1.0);
        d.setEmpiricalValue(dto.getEmpiricalValue());
        d.setEmpiricalRatio(1.0);
        d.setFbGradeRatio(memberfbGradeRatio);
        d.setEmGradeRatio(memberemGradeRatio);
        d.setType(2);
        dimensionalityPresentDao.insert(d);
    }
	private Double getTotalPrice(B2bOrderGoodsDtoMa orderGood) {
		return ArithUtil.round(ArithUtil.mul(orderGood.getPresentPrice()+orderGood.getCfGoods().getAdminFee()+orderGood.getCfGoods().getLoadFee()+orderGood.getCfGoods().getPackageFee(), orderGood.getWeight()), 2);
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
        B2bOrderDto orderDto = orderDaoEx.getByOrderNumber(orderNumber);
        B2bDimensionalityRewardDto sourceDimen = null;
        B2bDimensionalityRewardDto payDimen = null;
        B2bDimensionalityRewardDto productDimen = null;
        try {
            // 1来源和支付方式
            getSourceAndPay(list, orderDto, sourceDimen, payDimen);
            //2：POY或非POY订单
            Map<String, Object> parm = new HashMap<String, Object>();
            parm.put("orderNumber", orderNumber);
            List<B2bOrderGoodsDto> gList = orderGoodsDao.searchList(parm);
        	
            /**
			 * 对gList按照订单价格升序进行排序
			 */
			Collections.sort(gList, new Comparator<B2bOrderGoodsDto>() {
				@Override
				public int compare(B2bOrderGoodsDto o1, B2bOrderGoodsDto o2) {
					if (getTotalPrice((B2bOrderGoodsDtoMa) o1)+o1.getPresentTotalFreight() >getTotalPrice((B2bOrderGoodsDtoMa) o2) +o2.getPresentTotalFreight()) {
						return 1;
					}
					if (getTotalPrice((B2bOrderGoodsDtoMa) o1)+o1.getPresentTotalFreight() == getTotalPrice((B2bOrderGoodsDtoMa) o2)+o2.getPresentTotalFreight()) {
						return 0;
					}
					return -1;
				}
			});
			//订单金额最大的子订单(订单金额=商品实际总价+运费实际总价)
			B2bOrderGoodsDtoMa tempOrderGoodsDto = (B2bOrderGoodsDtoMa) gList.get(gList.size() - 1);
			if (null != tempOrderGoodsDto && tempOrderGoodsDto.getCfGoods().getProductName().equals("POY")) {
				productDimen = searchDimension(MemberSys.TRADE_DIMEN_POYORDER.getValue());
			} else {
				productDimen = searchDimension(MemberSys.TRADE_DIMEN_NOTPOY_ORDER.getValue());
			}
			if (null!=productDimen) {
				list.add(productDimen);
			}
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
            logger.error("订单奖励", e);
            e.printStackTrace();
            restCode = RestCode.CODE_S999;
        }
        return restCode;
    }

    
    
    
 /*   *//**
     * 插入订单类分值记录
     *//*
    @Override
    @Transactional
    public RestCode addPointForOrder(String orderNumber, int pointType) {
        RestCode restCode = RestCode.CODE_0000;
        List<B2bDimensionalityRewardDto> list = new ArrayList<B2bDimensionalityRewardDto>();
        // 查询总订单
        B2bOrderDto orderDto = orderDaoEx.getByOrderNumber(orderNumber);
        B2bDimensionalityRewardDto sourceDimen = null;
        B2bDimensionalityRewardDto payDimen = null;
        B2bDimensionalityRewardDto productDimen = null;
        try {
            // 1来源和支付方式
            getSourceAndPay(list, orderDto, sourceDimen, payDimen);
            Map<String, Object> parm = new HashMap<String, Object>();
            parm.put("orderNumber", orderNumber);
            List<B2bOrderGoodsDto> gList = orderGoodsDao.searchList(parm);
            if (gList.size() > 0) {
                for (B2bOrderGoodsDto b2bOrderGoodsDto : gList) {
                    if (list.contains(productDimen)) {
                        list.remove(productDimen);
                    }
                    // 3.品名
                    if (b2bOrderGoodsDto.getProductName().equals("POY")) {
                        productDimen = searchDimension(MemberSys.TRADE_DIMEN_POYORDER.getValue());
                    } else {
                        productDimen = searchDimension(MemberSys.TRADE_DIMEN_NOTPOY_ORDER.getValue());
                    }
                    if (productDimen != null) {
                        list.add(productDimen);
                    }
                    // 满足赠送周期，经验值，纤贝值相等
                    if (isCanAddPoint(list)) {
                        // 订单总金额（商品总金额+运费总金额）
                        Double total = ArithUtil.add(b2bOrderGoodsDto.getPresentTotalPrice(),
                                b2bOrderGoodsDto.getPresentTotalFreight());
                        // 加分
                        MangoMemberPoint point = new MangoMemberPoint();
                        point.setCompanyPk(orderDto.getPurchaserPk());
                        point.setMemberPk(orderDto.getMemberPk());
                        point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
                        point.setOrderNumber(orderNumber);
                        insertOrderMemberPoint(point, total, list, orderDto, pointType);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("订单奖励", e);
            e.printStackTrace();
            restCode = RestCode.CODE_S999;
        }
        return restCode;
    }*/
    
    

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
        B2bOrderDto orderDto = orderDaoEx.getByOrderNumber(orderNumber);
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
        list = b2bDimensionalityExtrewardDaoEx.getExtrewardByDimenCategory(strArr[0]);
        System.out.println("11111111111111111111111111111111:"+list.size());
        
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
                    List<B2bOrderGoodsDto> orderGoodsList = orderGoodsDao.searchList(parm);
                    if (null != orderGoodsList && orderGoodsList.size() > 0) {
                    	 /**
            			 * 对gList按照订单价格升序进行排序
            			 */
            			Collections.sort(orderGoodsList, new Comparator<B2bOrderGoodsDto>() {
            				@Override
            				public int compare(B2bOrderGoodsDto o1, B2bOrderGoodsDto o2) {
            					if (getTotalPrice((B2bOrderGoodsDtoMa) o1)+o1.getPresentTotalFreight() > getTotalPrice((B2bOrderGoodsDtoMa) o2)+o2.getPresentTotalFreight()) {
            						return 1;
            					}
            					if (getTotalPrice((B2bOrderGoodsDtoMa) o1)+o1.getPresentTotalFreight() == getTotalPrice((B2bOrderGoodsDtoMa) o2)+o2.getPresentTotalFreight()) {
            						return 0;
            					}
            					return -1;
            				}
            			});
            			//订单金额最大的子订单(订单金额=商品实际总价+运费实际总价)
            			B2bOrderGoodsDtoMa tempOrderGoodsDto = (B2bOrderGoodsDtoMa) orderGoodsList.get(orderGoodsList.size() - 1);
            			if (null != tempOrderGoodsDto && !tempOrderGoodsDto.getCfGoods().getProductName().equals("POY")) {
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
                    List<B2bOrderGoodsDto> orderGoodsList = orderGoodsDao.searchList(parm);
                    if (null != orderGoodsList && orderGoodsList.size() > 0) {
                    	 /**
            			 * 对gList按照订单价格升序进行排序
            			 */
            			Collections.sort(orderGoodsList, new Comparator<B2bOrderGoodsDto>() {
            				@Override
            				public int compare(B2bOrderGoodsDto o1, B2bOrderGoodsDto o2) {
            					if (getTotalPrice((B2bOrderGoodsDtoMa) o1)+o1.getPresentTotalFreight() >getTotalPrice((B2bOrderGoodsDtoMa) o2)+o2.getPresentTotalFreight()) {
            						return 1;
            					}
            					if (getTotalPrice((B2bOrderGoodsDtoMa) o1)+o1.getPresentTotalFreight() == getTotalPrice((B2bOrderGoodsDtoMa) o2)+o2.getPresentTotalFreight()) {
            						return 0;
            					}
            					return -1;
            				}
            			});
            			//订单金额最大的子订单(订单金额=商品实际总价+运费实际总价)
            			B2bOrderGoodsDtoMa tempOrderGoodsDto = (B2bOrderGoodsDtoMa) orderGoodsList.get(orderGoodsList.size() - 1);
                    	if (null!=tempOrderGoodsDto.getCfGoods().getProductName()&&tempOrderGoodsDto.getCfGoods().getProductName().equals("POY")) {
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
        System.out.println("22222222222222222222222222222222222:"+effectiveList.size());
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
			B2bMemberDto memberDto = memberDaoEx.getByPk(orderDto.getMemberPk());
			B2bCompanyDto companyDto = b2bCompanyExtDao.getByPk(memberDto.getCompanyPk());
			addB2bDimensionalityExtPresent(tempDto, memberDto, companyDto, orderNumber);
        }
        return restCode;
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
        dimensionalityPresentDao.insert(d);
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
			Integer totalSupCount = orderDaoEx.selectTotalSupCountInPeriodTime(map);// 累计成交供应商家数量
			if (totalSupCount == numberTimes) {
				Integer lastTotalSupCount = orderDaoEx.checkLastTotalSupCountInPeriodTime(orderDto.getMemberPk(),
						periodTime+1, orderDto.getOrderNumber(),extrewardDto.getUpdateTime());// 本次订单之前累计成交供应商家数
				//System.out.println("11111111111111111111111111:" + lastTotalSupCount);
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
	 * 验证额外奖励交易维度的有效性（带类型参数,暂时没用到）
	 * @param type 交易维度下的类型：1：白条订单，2：线下支付，3：求购订单，4：竞拍订单，5：除POY之外其他品名订单，6：POY订单，7：WAP订单，8：APP订单，9：在线支付，10：化纤贷支付
	 * @param orderDto 订单信息
	 * @return
	 */
//	private boolean checkExtDimenValidity2(int type,B2bOrderDto orderDto, B2bDimensionalityExtrewardDto extrewardDto) {
//		boolean result = false;
//		int conditionType = extrewardDto.getConditionType();// 满足条件类型,1:累计成交采购商家数,2:累计成交供应商家数,3:累计发布求购数量,4:累计求购报价数量,5:累计添加人员数
//		int periodTime = extrewardDto.getPeriodTime();// 多长时间范围内
//		int numberTimes = extrewardDto.getNumberTimes();// 累计成交次数
//		// 1:累计成交采购商家数
//		// 供应商累计成交几家采购商暂时不做
//		/*
//		 * if (conditionType == 1) { Map<String, Object> map = new HashMap<>();
//		 * map.put("supplierPk", orderDto.getSupplierPk());// 供应商pk
//		 * map.put("periodTime", periodTime);// 多长时间范围内 Integer totalPurCount =
//		 * orderDaoEx.selectTotalPurCountInPeriodTime(map);// 累计成交采购商家数量 if
//		 * (totalPurCount < numberTimes) { result = false; }else{ result=true; }
//		 * }
//		 */
//		// 2:累计成交供应商家数
//		if (conditionType == 2) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("memberPk", orderDto.getMemberPk());// 采购商业务员pk
//			map.put("periodTime", periodTime);// 多长时间范围内
//			Integer totalSupCount=0;//一个业务员累计成交的供应商家数
//			Integer lastTotalSupCount=0;//一个业务员，本次订单之前累计成交的供应商家数
//			//1：白条订单
//			if (type==1) {
//				//totalSupCount = orderDaoEx.selectTotalSupCountInPeriodTimeFor1(map);//累计成交供应商家数量
//				map.put("orderNumber", orderDto.getOrderNumber());
//				//lastTotalSupCount = orderDaoEx.selectBeforeTotalSupCountInPeriodTimeFor1(map);//本次订单之前累计成交供应商家数
//			}
//			//2：线下支付
//			if (type==2) {
//				//totalSupCount = orderDaoEx.selectTotalSupCountInPeriodTimeFor2(map);//累计成交供应商家数量
//				map.put("orderNumber", orderDto.getOrderNumber());
//				//lastTotalSupCount = orderDaoEx.selectBeforeTotalSupCountInPeriodTimeFor2(map);//本次订单之前累计成交供应商家数
//			}
//			//3：求购订单
//			if (type==3) {
//				
//			}
//			//4：竞拍订单
//			if (type==4) {
//				
//			}
//			//5:除POY之外其他品名订单
//			if (type==5) {
//				
//			}
//			
//			if (totalSupCount == numberTimes) {
//				if (lastTotalSupCount == totalSupCount) {
//					result = false;
//				} else {
//					result = true;
//				}
//			} else {
//				result = false;
//			}
//		}
//		// 3:累计发布求购数量
//		// 4:累计求购报价数量
//		return result;
//	}

    private void getSourceAndPay(List<B2bDimensionalityRewardDto> list, B2bOrderDto orderDto,
                                 B2bDimensionalityRewardDto sourceDimen, B2bDimensionalityRewardDto payDimen) {
        //wap订单
        if (orderDto.getSource() == 2) {
            sourceDimen = searchDimension(MemberSys.TRADE_DIMEN_WAPORDER.getValue());
        }
        // APP订单(无)
		/*if (orderDto.getSource() == 1) {
			sourceDimen = searchDimension(MemberSys.TRADE_DIMEN_WAPORDER.getValue());
		}*/

        if (sourceDimen != null) {
            list.add(sourceDimen);
        }
        // 2支付方式
        if (null!=orderDto.getPaymentType()&&orderDto.getPaymentType()>0) {
            // 线下支付
            if (orderDto.getPaymentType() == 1) {
                payDimen = searchDimension(MemberSys.TRADE_DIMEN_DOWLINE.getValue());
            } else {
                // 金融产品支付
                if (orderDto.getPaymentType() == 3) {
                    B2bEconomicsGoodsDto goodsDto = searchOne(orderDto) ;
                    if (goodsDto != null) {// 1化纤白条、2化纤贷
                        if (goodsDto.getGoodsType() == 1) {
                            payDimen = searchDimension(MemberSys.TRADE_DIMEN_BLNOTE.getValue());
                            System.out.println("===========orderDto.orderNumber=================="+orderDto.getOrderNumber());
                            System.out.println("===========payDimen======================"+payDimen.getDimenName());
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
    	Map<String, Object> map=new HashMap<String, Object>();
    	map.put("goodsType",orderDto.getEconomicsGoodsType());
    	map.put("name", orderDto.getEconomicsGoodsName());
    	map.put("isDelete", 1);
		map.put("status", 1);
		List<B2bEconomicsGoodsDto> list= economicsGoodsDao.searchList(map);
		if (null!=list && list.size()>0) {
			return list.get(0);
		}
		return null;
	
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
                                        B2bOrderDto orderDto, int pointType) {
        List<MemberPointPeriod> periodList = new ArrayList<>();
        B2bMemberDto memberDto = memberDaoEx.getByPk(orderDto.getMemberPk());
        Double memberfbGradeRatio = 1d;
        Double memberemGradeRatio = 1d;

        // 1.1等级系数
        if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
            B2bMemberGradeDto gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(
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
        List<B2bDimensionalityPresent> presentList = getPresentList(list, (B2bOrderDtoMa) orderDto, memberfbGradeRatio,
                memberemGradeRatio, ArithUtil.round(fbShellNumberWeighting,2),ArithUtil.round(empiricalValueWeighting,2));
        dimensionalityPresentDao.insertList(presentList);

        //赠送周期
        //说明：交易维度只有一次赠送；每日赠送，每周赠送取消
		/*if (period !=1) {
			String contactTel="";
			String purchaserPk = orderDto.getPurchaserPk();
			if(purchaserPk!=null) {
				B2bCompanyDtoEx company = b2bCompanyExtDao.getByCompanyPk(purchaserPk);
				if(company!=null&&company.getContactsTel()!=null)
					contactTel=company.getContactsTel();
			}
			MemberPointPeriod p = new MemberPointPeriod();
			p.setPeriodType(period);
			p.setFibreShellNumber(fbBase);
			p.setEmpiricalValue(emBase);
			p.setCompanyPk(orderDto.getPurchaserPk());
			p.setMemberPk(orderDto.getMemberPk());
			p.setInsertTime(new Date());
			p.setExpValue(point.getExpValue());
			p.setFiberValue(point.getFiberValue());
			p.setCompanyName(orderDto.getPurchaserName());
			p.setContactsTel(contactTel);
			p.setOrderNumber(orderDto.getOrderNumber());
			p.setFbShellNumberWeighting(fbShellNumberWeighting);
			p.setEmpiricalValueWeighting(empiricalValueWeighting);
			p.setType(1);
			p.setIsDelete(1);
			p.setJson(JsonUtils.convertToString(periodList));
			mongoTemplate.insert(p);
		}*/
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
                B2bCompanyDtoEx company = b2bCompanyExtDao.getByCompanyPk(purchaserPk);
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


    private boolean isCanAddPoint(List<B2bDimensionalityRewardDto> list) {
        Boolean flag = true;
        if (list.size() > 0) {
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i).getPeriodType().intValue() == list.get(i - 1).getPeriodType().intValue()
                            && list.get(i).getEmpiricalValue().intValue() == list.get(i - 1).getEmpiricalValue().intValue()
                            && list.get(i).getFibreShellNumber().intValue() == list.get(i - 1).getFibreShellNumber().intValue()
                            && list.get(i).getEmpiricalRatio().doubleValue() == list.get(i - 1).getEmpiricalRatio().doubleValue()
                            && list.get(i).getFibreShellRatio().doubleValue() == list.get(i - 1).getFibreShellRatio().doubleValue()
                            ) {

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
     * 查找对应的维度值
     *
     * @param dimenType
     * @return
     */
    private B2bDimensionalityRewardDto searchDimension(String dimenType) {
        B2bDimensionalityRewardDto dto = null;
            String[] strArr = dimenType.split("_");
            Map<String, Object> map = new HashMap<>();
            map.put("dimenCategory", strArr[0]);
            map.put("dimenType", strArr[1]);
            map.put("isDelete", 1);
            map.put("isVisable", 1);
            List<B2bDimensionalityRewardDto> list = b2bDimensionalityRewardDao.searchList(map);
            if (list != null && list.size() > 0) {
                dto = list.get(0);
            }
        return dto;
    }


//    private void addErrorRecord(RestCode restCode, MangoMemberPoint point) {
//        if (restCode.getCode().equals("S999")) {
//            MemberPointErrorInfo error = new MemberPointErrorInfo();
//            try {
//                BeanUtils.copyProperties(error, point);
//            } catch (IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            mongoTemplate.insert(error);
//        }
//    }

    /**
     * @param memberPk
     * @param companyPk
     * @param point
     * @param dto
     * @param pointType
     */
    private void insertMember(String memberPk, String companyPk, MangoMemberPoint point, B2bDimensionalityRewardDto dto,
                              Integer pointType, String thing) {
        B2bMemberDto memberDto = memberDaoEx.getByPk(memberPk);
        B2bCompanyDto companyDto = b2bCompanyExtDao.getByPk(companyPk);
        if(null != memberDto && null != companyDto){
        	Double memberfbGradeRatio = 0.00;
        	Double memberemGradeRatio = 0.00;
        	
        	
        	Integer periodType = dto.getPeriodType();
        	String category = dto.getDimenCategory().toString();
        	String dimenType = point.getDimenType();
        	
        	Date currentDate = new Date();
        	String today = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("dimenType", point.getDimenType());
        	map.put("memberPk", memberDto.getPk());
        	if (periodType == 2) map.put("insertTime", today);
        	List<MangoMemberPoint> list = memberPointService.searchPointList(map);
        	// System.out.println("==============list.size=================" + list.size());
        	if (list != null && list.size() > 0) {
        		return;
        	}
        	String parentCompanyPk = "-1";
        	if (category.equals(MemberSys.GOODS_DIMEN.getValue()) || dimenType.equals(MemberSys.ACCOUNT_DIMEN_WARE.getValue()) ||
        			dimenType.equals(MemberSys.ACCOUNT_DIMEN_PLANT.getValue()) || dimenType.equals(MemberSys.ACCOUNT_DIMEN_LGTEP.getValue()) ||
        			dimenType.equals(MemberSys.ACCOUNT_DIMEN_INV.getValue())) {
        		B2bCompanyDtoEx company = b2bCompanyExtDao.getByCompanyPk(companyPk);
        		if (company != null && company.getParentPk() != null && !company.getParentPk().equals("-1")) {
        			
        			parentCompanyPk = company.getParentPk();
        			map = new HashMap<String, Object>();
        			map.put("dimenType", point.getDimenType());
        			map.put("companyPk", companyPk);
        			if (periodType == 2) map.put("insertTime", today);
        			list = memberPointService.searchPointList(map);
        			// System.out.println(list.size() + "child company");
        			map = new HashMap<String, Object>();
        			map.put("dimenType", point.getDimenType());
        			map.put("companyPk", parentCompanyPk);
        			if (periodType == 2) map.put("insertTime", today);
        			List<MangoMemberPoint> list2 = memberPointService.searchPointList(map);
        			// System.out.println(list2.size() + "child company");
        			list.addAll(list2);
        			map = new HashMap<String, Object>();
        			map.put("dimenType", point.getDimenType());
        			map.put("parentCompanyPk", parentCompanyPk);
        			if (periodType == 2) map.put("insertTime", today);
        			List<MangoMemberPoint> list3 = memberPointService.searchPointList(map);
        			//(list3.size() + "parent company");
        			list.addAll(list3);
        			
        		} else if (company != null && company.getParentPk() != null && company.getParentPk().equals("-1")) {
        			map = new HashMap<String, Object>();
        			map.put("dimenType", point.getDimenType());
        			map.put("companyPk", companyPk);
        			if (periodType == 2) map.put("insertTime", today);
        			list = memberPointService.searchPointList(map);
        			//System.out.println(list.size() + "parent  company");
        			map = new HashMap<String, Object>();
        			map.put("dimenType", point.getDimenType());
        			map.put("parentCompanyPk", companyPk);
        			if (periodType == 2) map.put("insertTime", today);
        			List<MangoMemberPoint> list2 = memberPointService.searchPointList(map);
        			//System.out.println(list.size() + "parent company");
        			list.addAll(list2);
        		}
        		if ((list != null && list.size() > 0)) {
        			return;
        		}
        	}
        	// 1.加分所需的系数比例
        	// 1.1等级系数
        	if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
        		B2bMemberGradeDto gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(
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
        	
        	// 5.添加赠送方式:赠送周期类型1.一次赠送，2每天，3每周
        	/*if (dto.getPeriodType() != 1) {
			insertMemberPointPeriod(point, dto, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio,
					totalfbRatio, totalemRatio,thing);
		}*/
        }
    }


    /**
     * @param memberPk
     * @param companyPk
     * @param point
     * @param dto
     * @param pointType
     */
    private void insertMember2(String memberPk, String companyPk, MangoMemberPoint point, B2bDimensionalityRewardDto dto,
                               Integer pointType, String thing) {
        B2bMemberDto memberDto = memberDaoEx.getByPk(memberPk);
        B2bCompanyDto companyDto = b2bCompanyExtDao.getByPk(companyPk);
        Double memberfbGradeRatio = null;
        Double memberemGradeRatio = null;


        Integer periodType = dto.getPeriodType();

        Date currentDate = new Date();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dimenType", point.getDimenType());
        map.put("memberPk", memberDto.getPk());
        map.put("orderNumber", thing);
        if (periodType == 2) map.put("insertTime", today);
        List<MangoMemberPoint> list = memberPointService.searchPointList(map);
        if (list != null && list.size() > 0) {
            return;
        }

        // 1.加分所需的系数比例
        // 1.1等级系数
        if (memberDto.getLevel() != null && !memberDto.getLevel().equals("")) {
            B2bMemberGradeDto gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(
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
        point.setMemberPk(memberPk);
        point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
        point.setExpValue(ArithUtil.round((pointType == 2 ? (-totalEmpiricalValue) : totalEmpiricalValue), 2));
        point.setFiberValue(ArithUtil.round((pointType == 2 ? (-totalfbGradeValue) : totalfbGradeValue), 2));
        point.setOrderNumber(thing);
        mongoTemplate.insert(point);

        // 3. 更新用户总积分
        updateMemberValue(memberPk,totalEmpiricalValue,totalfbGradeValue);

        // 4.插入加分明细
        addB2bDimensionalityPresent(dto, memberDto, companyDto, memberfbGradeRatio, memberemGradeRatio, ArithUtil.round(totalfbRatio,2),
        		ArithUtil.round(totalemRatio,2));

    }


//    private void insertMemberPointPeriod(MangoMemberPoint point, B2bDimensionalityRewardDto dto, B2bMemberDto memberDto,
//                                         B2bCompanyDto companyDto, Double memberfbGradeRatio, Double memberemGradeRatio, Double totalfbRatio,
//                                         Double totalemRatio, String thing) {
//        MemberPointPeriod p = new MemberPointPeriod();
//        p.setRewardPk(dto.getPk());
//        p.setDimenCategory(dto.getDimenCategory());
//        p.setDimenType(dto.getDimenType());
//        p.setPeriodType(dto.getPeriodType());
//        p.setFibreShellNumber(dto.getFibreShellNumber());
//        p.setFibreShellRatio(dto.getFibreShellRatio());
//        p.setEmpiricalValue(dto.getEmpiricalValue());
//        p.setEmpiricalRatio(dto.getEmpiricalRatio());
//        p.setCompanyPk(companyDto.getPk());
//        p.setMemberPk(memberDto.getPk());
//        p.setFbGradeRatio(memberfbGradeRatio);
//        p.setEmGradeRatio(memberemGradeRatio);
//        p.setInsertTime(new Date());
//        p.setExpValue(point.getExpValue());
//        p.setFiberValue(point.getFiberValue());
//        p.setCompanyName(companyDto.getName());
//        p.setDimenName(dto.getDimenName());
//        p.setDimenTypeName(dto.getDimenTypeName());
//        p.setContactsTel(companyDto.getContactsTel());
//        p.setFbShellNumberWeighting(totalfbRatio);
//        p.setEmpiricalValueWeighting(totalemRatio);
//        if (thing != null) {//商品pk
//            p.setGoodsPk(thing);
//        }
//        p.setType(1);
//        p.setIsDelete(1);
//        mongoTemplate.insert(p);
//    }

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
        dimensionalityPresentDao.insert(d);
//        System.out.println("==================添加赠送列表======================");
//        System.out.println("==========B2bDimensionalityRewardDto.pk===============:" + dto.getPk());
    }

    private void updateMemberValue(String memberPk, Double expValue, Double fiberValue) {
    	//System.out.println("==========================更新用户积分===============================："+memberPk);
        B2bMemberDto memberDto = memberDaoEx.getByPk(memberPk);
        B2bMember member = new B2bMember();
        member.setPk(memberPk);
        member.setShell((memberDto.getShell() == null ? 0 : memberDto.getShell()) + ArithUtil.round(fiberValue, 0));
        member.setExperience((memberDto.getExperience() == null ? 0 : memberDto.getExperience()) + ArithUtil.round(expValue, 0));
        member.setFeedTime(new Date());
        memberDaoEx.update(member);
    }

    /**
     * 查询记录
     */
    @Override
    public List<MangoMemberPoint> searchPointList(Map<String, Object> map) {
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
     * 查询用户的总积分
     */
    @Override
    public Map<String, Object> getTotalPointByMember(String memberPk) {
        Double expValue = 0d;
        Double fiberValue = 0d;
        Query query = new Query(Criteria.where("memberPk").is(memberPk));
        List<MangoMemberPoint> list = mongoTemplate.find(query, MangoMemberPoint.class);
        for (MangoMemberPoint mangoMemberPoint : list) {
            expValue = expValue + mangoMemberPoint.getExpValue();
            fiberValue = fiberValue + mangoMemberPoint.getFiberValue();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fiberValue", fiberValue);
        map.put("expValue", expValue);
        return map;
    }

    /**
     * 根据系数查询额外维度
     */
    @Override
    public List<B2bDimensionalityExtrewardDto> getDimensionalityExtReward(String dimenType) {
        String[] strArr = dimenType.split("_");
        Map<String, Object> map = new HashMap<>();
        map.put("dimenCategory", strArr[0]);
        map.put("dimenType", strArr[1]);
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        return b2bDimensionalityExtrewardDaoEx.searchList(map);
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
     * 收藏商品/店铺
     */
    @Override
    public RestCode addPointForThing(String memberPk, String companyPk, String thing, String dimenType) {
        RestCode restCode = RestCode.CODE_0000;
        MangoMemberPoint point = new MangoMemberPoint();
        Criteria criteria = new Criteria();
//        Criteria c1 = new Criteria().where("memberPk").is(memberPk);
////		Criteria c2 = new Criteria().where("orderNumber").is(thing);
//        Criteria c3 = new Criteria().where("dimenType").is(dimenType);
//        criteria.andOperator(c1, c3);
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
            logger.error("收藏商品/店铺", e);
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


    public RestCode addPoint2(String memberPk, String companyPk, Integer pointType,
                              String active, Boolean flag) {
    	RestCode restCode = RestCode.CODE_0000;
        Map<String, Object> map = new HashMap<String, Object>();
        if (flag) {
            map.put("memberPk", memberPk);
        }
        map.put("companyPk", companyPk);
        map.put("dimenType", active);
        List<MangoMemberPoint> list = searchPointList(map);
        if (null == list || list.size() == 0) {
        	restCode = this.addPoint(memberPk, companyPk, pointType, active);
        }
        return restCode;
    }



}
