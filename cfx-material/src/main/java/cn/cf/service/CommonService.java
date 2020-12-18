package cn.cf.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.constant.RestCode;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bBillCusgoodsDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.DeliveryBasic;
import cn.cf.entity.DeliveryGoods;
import cn.cf.entity.DeliveryOrder;
import cn.cf.entity.ForB2BLgPriceDto;
import cn.cf.entity.LgDeliveryOrderForB2BDto;
import cn.cf.entity.LgDeliveryOrderForB2BPTDto;
import cn.cf.entity.LgSearchPriceForB2BDto;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.model.B2bOrder;



public interface CommonService {
    /**
     * 商城确认发货(商家承运)
     * @param lgDeliveryOrderForB2BDto
     * @return
     */
    boolean confirmFaHuoForB2BSJ(LgDeliveryOrderForB2BDto lgDeliveryOrderForB2BDto );
    /**
     * 提供给商城查询线路价格
     * @param lgSearchPriceForB2BDto
     * @return
     */
    ForB2BLgPriceDto searchLgPriceForB2B(LgSearchPriceForB2BDto lgSearchPriceForB2BDto);

    /**
     * 商城确认发货（平台承运）
     * @param lgDeliveryOrderForB2BPTDto
     * @return
     */
    boolean confirmFaHuoForB2BPT(LgDeliveryOrderForB2BPTDto lgDeliveryOrderForB2BPTDto );

    /**
     * 商城下单时根据线路pk,阶梯价pk查询运费对外总价
     * @param lgLinePk,线路pk
     * @param lgLineStepPk,阶梯价pk
     * @return
     */
    ForB2BLgPriceDto searchLgPriceForB2BByLinePk(String lgLinePk,String lgLineStepPk );
	/**
	 * 查询会员基础值
	 * @param map(memberPk,companyPk,active)
	 * @return
	 */
	List<MangoMemberPoint>  searchPointList (Map<String, String> map);


	/**
	 * 插入会员基础值
	 * @param memberPk
	 * @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作
	 * @return
	 */
	@Transactional
	RestCode  addPoint (String memberPk,String companyPk,Integer pointType,String active);

	/**
	 * 业务员商品列表：业务员匹配的商品
	 * @param memberPk
	 * @return
	 */
	Map<String, Object> getGoodsByMember(Map<String, Object> map);

	/**
	 * 根据条件查询会员
	 * @param map
	 * @return
	 */
	List<B2bMemberDto> getByMember(Map<String,Object> map);


	/**
	 * 收藏商品/店铺
	 * @param memberPk
	 * @param companyPk
	 * @param thing
	 * @param active
	 * @return
	 */
	RestCode  addPointForThing (String memberPk,String companyPk,String thing,String active);


	/**
	 * 取消收藏商品
	 * @param goodsPk
	 * @param member
	 */
	void cancelThing(String thingPk,String member);
	/**
	 * 获取公司银行卡号
	 * @param supplierPk
	 * @return
	 */
	SysCompanyBankcardDto getCompanyBankCard(String companyPk);
	
	/**
	 * 查询金融产品以及匹配对应的银行卡号
	 * @param purchaserPk
	 * @param supplierPk
	 * @return
	 */
	List<B2bCreditGoodsDtoMa> searchCreditGoodsAndBankCard(String purchaserPk,String supplierPk,String storePk);
	
	/**
	 * 根据授信银行匹配供应商卡号
	 */
	SysCompanyBankcardDto getCompanyBankCard(String supplierPk,String bankPk);
	
	/**
     * 查询借据单号
     * @return
     */
	B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber);
	/**
	 * 获取单个金融产品
	 * @param pk
	 * @return
	 */
	B2bEconomicsGoodsDto getEconomicsGoodsByPk(String name,Integer type);
	
	/**
	 * 查询金融产品
	 * @param pk 金融产品PK
	 * @return
	 */
	B2bEconomicsGoodsDto getEconomicsGoods(String pk);
	
	/**
	 * 根据手机号获取用户
	 * @param mobile
	 * @return
	 */
	B2bMemberDto getMemberByMobile(String mobile);
	
	/**
	 * 根据条件查询业务员
	 * @param companyPk 供应商公司
	 * @param purchaserPk 采购商公司
	 * @param productPk   品名
	 * @param storePk    店铺
	 * @return
	 */
	B2bMemberDto getSaleMan(String companyPk, String purchaserPk,String productPk,String storePk);
	/**
	 * 新增/编辑客户管理
	 * @param dto
	 * @return
	 */
	RestCode addCustomerManagement(B2bCustomerManagementDto dto);
	
	/**
	 * 取消订单
	 * @param orderNumber
	 */
	void cancelOrder(String orderNumber);
	
	/**
	 * 插入交易维度
	 * @param memberPk
	 *  @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作
	 * @param total 总金额
	 * @return
	 */
	@Transactional
	RestCode  addPointForOrder (String orderNumber,int pointType);
	
	
	/**
	 * 给会员加额外奖励积分
	 * @param orderNumber  订单编号
	 * @param pointType  1增加分值；2减少分值
	 * @return
	 */
	@Transactional
	RestCode  addExtPointForOrder (String orderNumber,int pointType);
	
	/**
	 * 获取某一个公司
	 * @param pk 公司pk
	 * @return
	 */
	B2bCompanyDto getCompanyByPk(String pk);
	
	/**
	 * 根据公司pk返回所有子母公司
	 * @param companyType(1:采购商 2:供应商)
	 * @param companyPk(公司pk)
	 * @param returnType(1:返回子母所有 2:返回子公司)
	 * @param map(附加参数)
	 * @return
	 */
	List<B2bCompanyDto> searchCompanyList(Integer companyType,String companyPk,Integer returnType,Map<String,Object> map);
	
	/**
	 *根据条件获取公司
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map);
	
	/**
	 * 根据名称查询公司
	 * @param companyName 公司名称
	 * @return
	 */
	B2bCompanyDto getCompanyByName(String companyName);
	
	
	/**
	 * 插入会员基础值
	 * @param memberPk
	 * @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作  
	 * @param flag 只新增一次  
	 * @return
	 */
	@Transactional
	RestCode  addPointForMember (String memberPk,String companyPk,Integer pointType,String active,Boolean flag);
	/**
	 * 厂区是否占用中
	 * @param plantPk
	 * @return
	 */
	public Integer isDeletePlant(String plantPk);
	/**
	 * 仓库是否占用中
	 * @param warePk
	 * @return
	 */
	public Integer isDeleteWare(String warePk);
	/**
	 * 获取组长
	 * @param pk 用户pk
	 * @return
	 */
	B2bMemberDto getGroupMan(String pk);
	/**
	 * 获取商品信息
	 * @param pk
	 * @return
	 */
	B2bGoodsDtoMa getB2bGoodsDtoMa(String pk);
	/**
	 * 根据当前登录业务助理查询所有绑定的业务员
	 * @param memberPk
	 * @return
	 */
	String[] getEmployeePks(String memberPk);
	/**
	 * 查询线上支付订单信息
	 * @param orderNumber
	 * @return
	 */
	B2bOnlinepayRecordDto getB2bOnlinepayRecordDto(String orderNumber);
	/**
	 * 查询票据产品以及匹配对应的银行卡号
	 * @param purchaserPk
	 * @param supplierPk
	 * @return
	 */
	List<B2bBillCusgoodsDtoMa> searchBillGoodsAndBankCard(String purchaserPk,String supplierPk);
	/**
	 * 查询票据订单状态
	 * @param orderNumber
	 * @return
	 */
	B2bBillOrderDto getBillOrder(String orderNumber);
	/**
	 * 获取供应商票据签约账户
	 * @param supplierPk
	 * @return
	 */
	B2bBillSigncardDto getBillCompanyBankCard(String companyPk,String bankPk);
	/**
	 * 获取票据支付记录下所有票据信息
	 * @param orderNumber
	 * @return
	 */
	List<B2bBillInventoryDto> searchBillInventoryList(String orderNumber);
	/***
	 *复制化纤商品信息，并且同步商品涉及的字典表数据，
	 */
	RestCode DuplicatedGoods(List<B2bOrder> olist);
	
	
	/**
	 * 查询授信表
	 * @param map  参数
	 * @return
	 */
	List<B2bCreditGoodsDto> searchCreditGoods(Map<String, Object> map);
	
	/**
	 * 查询订单提货基础信息
	 * @param orderNumber
	 * @return
	 */
	DeliveryBasic getDeliveryBasic(String orderNumber);
	/**
	 * 查询订单提货列表
	 * @param orderNumber
	 * @param status 状态集合
	 * @return
	 */
	List<DeliveryOrder> searchDeliveryOrderList(String orderNumber,List<Integer> status);
	/**
	 * 提货
	 * @param orderNumber
	 * @param doList(参数：childOrderNumber,deliverBoxes,deliverWeight)
	 * @return
	 */
	String creditDeliveryOrder(String orderNumber,List<DeliveryGoods> doList);
	
	/**
	 * 上传凭证
	 * @param orderNumber
	 * @param imgUrl
	 * @param type 1付款凭证  2提货凭证
	 */
	String savePayvoucher(String orderNumber,String imgUrl,Integer type);
	
	/**
	 * 删除凭证
	 * @param id 凭证id
	 */
	void delPayvoucher(String id);
	
	/**
	 * 查询凭证
	 * @param orderNumber
	 * @param type 1付款凭证 2提货凭证
	 */
	List<B2bPayVoucher> searchPayVoucherList(String orderNumber,Integer type);
	
	/**
	 * 提货确认
	 * @param deliveryNumber 提货单号
	 * @return
	 */
	String sendDeliverOrder(String deliveryNumber);
	
	/**
	 * 发货同步
	 * @param goods
	 */
	void confirmFaHuoForB2B(B2bOrderGoodsDtoMa goods);
	
	/**
	 * 导出提货委托书
	 * @param deliveryNumber
	 * @param filePath
	 * @param resp
	 */
	void downloadDeliveryEntrust(String deliveryNumber,String filePath, HttpServletResponse resp);
	
	/**
	 * 导出客户提货委托单
	 * @param deliveryNumber
	 * @return filePath(文件全路径)
	 * @throws Exception 
	 */
	void exportDeliveryReq(String deliveryNumber,String fliePath, HttpServletResponse resp);

	
}
