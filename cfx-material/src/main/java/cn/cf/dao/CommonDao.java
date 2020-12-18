/**
 * 
 */
package cn.cf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
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
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.entity.B2bBillCusgoodsDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entity.LgMemberDeliveryOrderDtoMa;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.model.B2bDimensionalityPresent;
import cn.cf.model.B2bGoodsBrand;
import cn.cf.model.B2bMember;
import cn.cf.model.B2bPlant;
import cn.cf.model.B2bWare;
import cn.cf.model.B2bWarehouseNumber;
import cn.cf.model.LgDeliveryOrder;
import cn.cf.model.LgDeliveryOrderGoods;
import cn.cf.model.LgTrackDetail;

/**
 * @author bin
 * 
 */
public interface CommonDao {
	B2bMemberDto getMemberByPk(java.lang.String pk);

	B2bMemberDto getMemberByMobile(java.lang.String mobile);

	List<B2bMemberDto> searchMemberBySmsRoleM(Map<String, String> map);

	List<B2bMemberDto> getAdminM(String companyPk);


	SysSmsTemplateDto getTemplateByName(String smsCode);

	List<B2bDimensionalityRewardDto> searchDimensionalityRewardList(Map<String, Object> map);

	B2bCompanyDto getCompanyByPk(String pk);

	B2bMemberGradeDto getDtoByGradeNumber(java.lang.Integer gradeNumber);

	void updateMember(B2bMember member);

	void insertDimensionalityPresent(B2bDimensionalityPresent d);

	List<B2bCustomerSalesmanDto> searchGoodsBySaleMan(Map<String, Object> map);

	List<B2bMemberDto> searchMemberList(Map<String, Object> map);

	LgCompanyDto getLgCompanyByPk(java.lang.String pk);

	SysRegionsDto getRegionsByNameExM(Map<String, Object> map);

	int getDeliveryCountByOrderPkM(String orderPk);

	String getPkByDeliveryNumberM(String orderPk);

	void updateDeliveryNumberByPkM(@Param("pk")String pk, @Param("deliveryNumber")String deliveryNumber);

	List<String> lgRolematchMembersM(Map<String, Object> par);

	void insertLgMemberDeliveryOrder(LgMemberDeliveryOrderDtoMa memberDeliveryOrderDtoEx);

	void deleteLgDeliveryOrder(String deliveryPk);

	void insertLgTrackDetail(LgTrackDetail lgTrackDetail);

	int insertLgDeliveryOrder(LgDeliveryOrder lgDeliveryOrder);

	int insertLgDeliveryOrderGoods(LgDeliveryOrderGoods lgOrderGoods);

	LgLineDto getLgLineByPk(String lgLinePk);

	LgLinePriceDto getLgLinePriceByPk(String string);

	List<SysCompanyBankcardDto> searchCompanyBankcardList(Map<String, Object> map);

	List<B2bCreditGoodsDtoMa> getCreditGoods(Map<String, Object> map);

	B2bLoanNumberDto getLoanNumberByOrderNumber(String orderNumber);

	List<B2bEconomicsGoodsDto> searchEconomicsGoodsList(Map<String, Object> map);

	List<B2bCustomerSalesmanDto> searchMemberBySaleMan(Map<String, Object> map);

	List<B2bMemberDto> getAdminByParent(String companyPk);

	int isCustomerReaped(Map<String, Object> mapC);

	int updateB2bCustomerManagement(B2bCustomerManagement model);

	int insertB2bCustomerManagement(B2bCustomerManagement model);

	B2bOrderDtoMa getByOrderNumber(String orderNumber);

	List<B2bOrderGoodsDtoMa> searchOrderGoodsList(Map<String, Object> map);

	void insertB2bDimensionalityPresentList(List<B2bDimensionalityPresent> list);

	/**
	 * 根据维度类别查询所有维度
	 * 
	 * @param dimenCategory
	 *            维度类别
	 * @return
	 */
	List<B2bDimensionalityExtrewardDto> getExtrewardByDimenCategory(String dimenCategory);

	/**
	 * 查询在一定时间范围内累计成交供应商家数量
	 * 
	 * @param map
	 * @return
	 */
	Integer selectTotalSupCountInPeriodTime(Map<String, Object> map);

	/**
	 * 查询在一定时间范围内累计成交供应商家数量,排除本次订单
	 * 
	 * @param map
	 * @return
	 */
	Integer checkLastTotalSupCountInPeriodTime(@Param("memberPk") String memberPk, @Param("periodTime") int periodTime,
			@Param("orderNumber") String orderNumber, @Param("periodTimeStart") Date periodTimeStart);

	List<B2bCompanyDto> searchCompanyList(Map<String, Object> map);

	int isDeletePlantByB(Map<String, Object> m);

	int isDeleteWareByB(Map<String, Object> m);

	/**
	 * 根据用户pk  随机获取一名组长
	 * @param pk
	 * @return
	 */
	B2bMemberDto getGroupMan(String pk);
	
	B2bGoodsDtoMa getB2bGoodsDtoMa(String pk);
	
	List<B2bMemberGroupDto> getMemberGroupList(String memberPk);

	B2bOnlinepayRecordDto getB2bOnlinepayRecordDto(String orderNumber);

	List<B2bBillCusgoodsDtoMa> getBillCusgoods(Map<String,Object> map);
	
	B2bBillGoodsDto getBillGoods(String pk);

	B2bBillOrderDto getBillOrder(String orderNumber);
	
	List<B2bBillSigncardDto> searchBillSigncardList(Map<String, Object> map);

	List<B2bBillInventoryDto> searchBillInventoryList(String orderNumber);

	void insertB2bGood(B2bGoodsDto good);

	B2bStoreDto getStoreByPk(String pk);

	B2bGoodsBrandDto getGoodsBrandByPk(String pk);

	List<B2bGoodsBrandDto> searchGoodsBrandList(Map<String, Object> map);

	void insertB2bGoodsBrand(B2bGoodsBrand goodsbrand);

	B2bPlantDto getPlantByPk(String pk);

	List<B2bPlantDto> searchPlantList(Map<String, Object> map);

	void insertPlant(B2bPlant plant);

	B2bWareDto getWareByPk(String pk);

	List<B2bWareDto> searchWareList(Map<String, Object> map);

	void insertB2bWare(B2bWare model);

	List<B2bWarehouseNumberDto> searchB2bWarehouseNumberList(Map<String, Object> numMap);

	void insertB2bWarehouseNumber(B2bWarehouseNumber number);

	int isGoodsRepeated(Map<String, Object> map);

	B2bStoreDto getStoreByCompanyPk(String pk);
	
	/**
	 * 查询授信表
	 * @param map  参数
	 * @return
	 */
	List<B2bCreditGoodsDto> searchCreditGoods(Map<String, Object> map);
	
	LogisticsModelDto getLogisticsModel(String pk);

 
}
