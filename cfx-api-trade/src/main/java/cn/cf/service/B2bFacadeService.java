package cn.cf.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bAuctionOfferDtoEx;
import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.ExportOrderDto;
import cn.cf.dto.PageModelAuctionGoods;
import cn.cf.dto.PageModelAuctionGoodsByMember;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.CallBackContract;
import cn.cf.entity.CallBackOrder;
import cn.cf.entity.DeliveryDto;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.entity.Sessions;

public interface B2bFacadeService {
	
	/**
	 * 普通商品上架至竞拍
	 * @param goodsPks  商品pk
	 * @param auctionPks  场次pk
	 * @param activityTime  活动日期
	 * @return
	 */
	RestCode upToAuction(String goodsPks,String auctionPks,Date activityTime);
	
	
	
	/**
	 * 竞拍中的商品列表
	 * 
	 * @param map
	 * @return
	 */
	PageModelAuctionGoods<B2bAuctionGoodsDtoEx> searchAuctionGoodsList(Map<String, Object> map);
	
	
	/**
	 * 竞拍商品更新为正常商品
	 * @param goodsPks
	 * @return
	 */
	RestCode downToNormal(String goodsPks,String type);
	
	/**
	 * 设置最低起拍量
	 * @param goodsPks  商品pk
	 * @return
	 */
	RestCode setMinimumBoxes(String goodsPks,int minimumBoxes);
	
	
	/**
	 * 设置场次
	 * @param goodsPks
	 * @param auctionPk
	 * @param activityTime
	 * @return
	 */
	RestCode setAuction(String goodsPks,String auctionPk,Date activityTime);
	
	
	/**
	 * 代拍 - 竞拍中的商品列表
	 * 
	 * @param map
	 * @return
	 */
	PageModelAuctionGoodsByMember<B2bAuctionGoodsDtoEx> auctionGoodsListByMember(Map<String, Object> map,String memberPk ,String companyPk);
	
	
	/**
	 * 一键加价
	 * @param pks  活动场次pk
	 * @param companyPk  代拍公司pk
	 * @param operator  操作人
	 * @param saleMan  业务员
	 * @param andMore  加价的价钱
	 * @return
	 */
	@Transactional
	RestCode auctionOfferOneStep(String pks,String companyPk, B2bMemberDto operator, B2bMemberDto saleMan, Double andMore);
	
	
	
	/**
	 * 某个场次-某个业务员的出价记录
	 * 
	 * @param map
	 * @return
	 */
	PageModel<B2bAuctionOfferDtoEx> auctionOfferRecordsByMember(Map<String, Object> map);
	
	
	/**
	 * 代拍
	 * @param pk  竞拍场次pk
	 * @param boxes  竞拍箱数
	 * @param tareWeight  箱重
	 * @param auctionPrice 竞拍价格
	 * @param operator 操作人
	 * @param saleman 业务员
	 * @param companyPk  代拍公司pk
	 * @return
	 */
	@Transactional
	RestCode auctionOffer(String pk, Integer boxes, Double tareWeight, Double auctionPrice, B2bMemberDto operator,B2bMemberDto saleman  ,String companyPk);
	
	/**
	 * 采购商订单查询
	 * 
	 * @param map
	 * @param company
	 * @return
	 */
	PageModel<B2bOrderDtoEx> searchPuOrderList(Map<String, Object> map, B2bCompanyDto company);

	/**
	 * 供应商订单查询
	 * 
	 * @param map
	 * @param company
	 * @return
	 */
	PageModel<B2bOrderDtoEx> searchSpOrderList(Map<String, Object> map,B2bStoreDto store, B2bCompanyDto company);

	/**
	 * 业务员订单查询
	 * 
	 * @param map
	 * @param company
	 * @param memberDto
	 * @return
	 */
	PageModel<B2bOrderDtoEx> searchEmOrderList(Map<String, Object> map, Sessions session);

	/**
	 * 查询订单数量
	 * @param map
	 * @param company
	 * @param memberDto
	 * @param isAdmin
	 * @return
	 */
	Map<String,Object> searchOrderCounts(Map<String, Object> map, Sessions session);
	
	
	/**
	 * 订单导出列表查询
	 * @param map
	 * @param company
	 * @param memberDto
	 * @param isAdmin
	 * @return
	 */
	List<ExportOrderDto> exportOrderList(Map<String, Object> map,Sessions session);
	/**
	 * 订单提交
	 * 
	 * @param orders
	 *            json数组
	 * @param c
	 *            当前登录人公司
	 * @param m
	 *            当前登录人
	 * @param source
	 *            订单来源
	 * @return
	 */
	CallBackOrder submitOrder(String orders, B2bCompanyDto c, B2bMemberDto m, int source);
	/**
	 * 订单支付
	 * @param orderNumber
	 * @param paymentPk 支付方式
	 * @return
	 */
	String payOrder(B2bMemberDto memberDto, String orderNumber,Integer paymentType,String paymentName);
	/**
	 * 确认收款
	 * @param orderNumber
	 * @return
	 */
	String receivablesOrder(B2bMemberDto member,String orderNumber);
	
	/**
	 * 订单发货
	 * @param orderNumber
	 * @param orders 部分发货详情
	 * @return
	 */
	String deliverOrder(B2bMemberDto member,String orderNumber,String orders);
	
	/**
	 * 订单完成
	 * @param orderNumber
	 * @return
	 */
	RestCode completeOrder(B2bMemberDto member,String orderNumber);
	
	/**
	 * 订单取消
	 * @param orderNumber
	 * @return
	 */
	String cancelOrder(B2bMemberDto member, String orderNumber,Boolean flag);
	
	/**
	 * 订单修改价格
	 * @param member
	 * @param orderNumber
	 * @param orders
	 * @return
	 */
	String  updateOrderPrice(B2bMemberDto member,String orderNumber,String orders);
	
	/**
	 * 订单详情
	 * @param orderNumber
	 * @return
	 */
	String orderDetails(String orderNumber);
	/**
	 * 上传付款凭证
	 * @param orderNumber
	 * @param imgUrl
	 * @return
	 */
	@Transactional
	String uploadVoucher(B2bMemberDto member,String orderNumber,String imgUrl,Integer type);
	
	/**
	 * 删除付款凭证
	 * @param member 登录会员
	 * @param id  付款凭证Id
	 * @param type 1:订单，2：合同
	 * @return
	 */
	String delVoucher(B2bMemberDto member,String id);
	/**
	 * 合同提交
	 * @param contacts json数组
	 * @param c 当前登录人公司
	 * @param m 当前登录人
	 * @param source 订单来源
	 * @return
	 */
	@Transactional
	CallBackContract submitContract(String contacts, B2bCompanyDto c, B2bMemberDto m, Integer source);
	/**
	 * 合同上传付款凭证
	 * @param member
	 * @param orderNumber
	 * @param imgUrl
	 * @return
	 */
	String uploadVoucherByContract(B2bMemberDto member,String orderNumber,String imgUrl,Integer type);
	/**
	 * 合同提交审核
	 * @param contractNo
	 * @return
	 */
	String auditContract(B2bMemberDto member,String contractNo,Integer type);
	/**
	 * 合同支付
	 * @param contractNo
	 * @return
	 */
	String payContract(B2bMemberDto member,String contractNo,Integer paymentType,String paymentName);
	/**
	 * 合同发货
	 * @param member
	 * @param contractNo
	 * @param orders
	 * @return
	 */
	String deliverContract(B2bMemberDto member,String contractNo,String orders);
	/**
	 * 合同完成
	 * @param orderNumber
	 * @return
	 */
	RestCode completeContract(B2bMemberDto member,String orderNumber);
	/**
	 * 合同取消
	 * @param orderNumber
	 * @return
	 */
	String cancelContract(B2bMemberDto member, String contract);
	
    /**
     * 订单推送crm
     * @param orderNumber
     * @param storePk
     * @param type
     */
    void sendErp(String orderNumber,String storePk,Integer type);
    /**
	 * 拼团转化订单
	 * @param bindOrderPk
	 * @param member
	 * @return
	 */
	String bindToOrder(String bindOrderPk,B2bMemberDto member);
	/**
	 * 首次进行某种操作添加积分
	 * @param memberPk
	 * @param companyPk
	 * @param dimenType
	 */
	void  addPoint( String memberPk,String companyPk,String dimenType);
	
	
	/**
	 * 添加拼团活动
	 * @param bindGoodsDto 拼团活动信息
	 * @param goodsPks  商品pk
	 * @return
	 */
	RestCode addBind(B2bBindDto b2bBindDto,String goodsPks);
	
	/**
	 * 编辑拼团活动
	 * @param b2bBindDto
	 * @param goodsPks
	 * @return
	 */
	RestCode editBind(B2bBindDto b2bBindDto,String goodsPks);
	
    /**
     * 删除拼团活动
     * @param bindPk
     * @return
     */
    RestCode delBind(String bindPk);
    
    /**
     *	查询供选择拼团商品
     * @param map
     * @return
     */
    PageModel<B2bGoodsDtoEx> searchBindGoodsList(Map<String, Object> map);
    
    
    /**
     * 查询拼团中的商品
     * @param map
     * @return
     */
    PageModel<B2bBindGoodsDtoEx> searchGoodsListOnBind(Map<String, Object> map);
    
    
    /**
     * 拼团商品设置为正常商品
     * @param goodsPks
     * @return
     */
    RestCode backToNormalGoods(String goodsPks,String type);
    
    
    /**
     * 设置拼团活动
     * @param goodsPks
     * @param bindPk
     * @return
     */
    RestCode setBindingActivity(String goodsPks,String bindPk);
    
    /**
     * 上架/下架  拼团活动
     * @param bindPk  拼团pk
     * @param isUpDown 上下架 1:上架， 2:下架
     * @return
     */
    RestCode upDownBinding(String bindPk,int isUpDown);
    
   /**
    *  删除某个拼团活动下的商品
    * @param bindPk
    * @param goodsPk
    * @return
    */
    RestCode delGoodsInBind(String bindPk, String goodsPk);
    
    /**
     * 清空拼团
     */
    void updateOverdueBind();
    
    void  afterOrder(BatchOrder bo, Porder o);

    void afterContract(B2bMemberDto m, List<B2bContractDtoEx> contractList);
/***
 * 合同订单
 * @param contractNo
 * @param orders
 * @return
 */
    @Transactional
	String contractToOrder(String contractNo,B2bMemberDto member,  String orders,String address);


    /***
     * 
     * @param orders json数组
     * @param company 当前登陆人公司
     * @param member  当前登陆人
     * @param source 订单来源
     * @return
     */
    CallBackOrder submitReserve(String orders, B2bCompanyDto company,
		B2bMemberDto member, int source);
    /**
     * 订单转换
     * @param o
     * @param list
     */
    RestCode transformationOrder(Porder o,List<Pgoods> list);
    /**
     * 预约单转订单
     * @param orderNumber
     * @param member
     * @return
     */
	String reservesToOrder(String orderNumber, B2bMemberDto member);
	/**
	 * 提货详情
	 * @param orderNumber
	 * @param status
	 * @return
	 */
	DeliveryDto searchDeliveryDto(String orderNumber,Integer status);
	/**
	 * 提货申请
	 * @param orderNumber
	 * @param json
	 * @return
	 */
	String createDeliveryOrder(String orderNumber,String json);
    
}
