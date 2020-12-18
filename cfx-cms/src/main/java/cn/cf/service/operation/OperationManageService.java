package cn.cf.service.operation;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface OperationManageService {
	
	/**
	 * 厂家品牌查询列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bGoodsBrandExtDto> searchGoodsBrandList(QueryModel<B2bGoodsBrandExtDto> qm,ManageAccount account);
	
	
	/**
	 * 查询订单列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bOrderExtDto> searchOrderList(QueryModel<B2bOrderExtDto> qm, ManageAccount account);
	
	/**
	 * 修改厂家品牌（包括审核和删除）
	 * @param extDto
	 * @return
	 */
	String updateGoodsBrand(B2bGoodsBrandExtDto extDto);
	
	/**
	 * 新增厂家品牌
	 * @param brand
	 * @return
	 */
	String insertGoodsBrand(B2bGoodsBrandExtDto brand);
	/**
	 * 查询所有店铺，添加厂家品牌时需选择店铺
	 * @return
	 */
	List<B2bStoreDto> searchStoreList();


	/**
	 * 查询所有店铺
	 * @return
	 */
	List<B2bStoreDto> searchAllStoreList();
	
	/**
	 * 查询所有品牌
	 * @return
	 */
	List<B2bGoodsBrandDto> searchBrandList();


	/**
	 * 查询所有品牌关联店铺
	 * @return
	 */
	List<B2bGoodsBrandDto> searchDistinctGoodsBrandList();

	/**
	 * 根据brandPk查询所有品牌
	 * @return
	 */
	List<B2bGoodsBrandDto> searchGoodsBrandListByBrandPk(String brandPk);


	
	/**
	 * 查询付款方式
	 * @return
	 */
	List<B2bPaymentDto> getPaymentList();
	
	/**
	 * 订单下载合同
	 * @param orderNumber
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	B2bOrderExtDto exportOrderPDF(String orderNumber,HttpServletResponse resp,HttpServletRequest req) throws Exception;
	
	/**
	 * 上传付款凭证
	 * @param b2bOrder
	 */
	void exportPaymentVoucher(B2bOrderExtDto b2bOrder);
	

	/**
	 * 关闭订单
	 * @param orderNumber
	 */
	Integer closeOrder(String orderNumber,String closeReason);
	
	/**
	 * 订单商品查询
	 * @param qm
	 * @return
	 */
	PageModel<B2bOrderGoodsExtDto> searchOrderGoodsList(QueryModel<B2bOrderGoodsExtDto> qm);
	
	/**
	 * 修改bannner
	 * @param banner
	 * @return
	 */
	int updateBanner(SysBanner banner);
	
	/**
	 * 添加banner
	 * @param banner
	 * @return
	 */
	int insertBanner(SysBanner banner);
	
	/**
	 * banner列表
	 */
	PageModel<SysBannerExtDto>searchBannerList(QueryModel<SysBannerExtDto> qm);
	
	/**
	 * 供应商推荐列表
	 * @param qm
	 * @return
	 */
	PageModel<SysSupplierRecommedExtDto> searchSupplierRecommedList(QueryModel<SysSupplierRecommedExtDto> qm,ManageAccount account);
	
	/**
	 * 供应商推荐修改
	 * @param dto
	 * @return
	 */
	int updateSupplierRecommed(SysSupplierRecommedExtDto dto);

	/**
	 * 供应商推荐修改
	 * @param dto
	 * @return
	 */
	int updateSupplierRecommedStatus(SysSupplierRecommedExtDto dto);
	
	/**
	 * 添加供应商推荐
	 * @param recommed
	 * @return
	 */
	int insertSupplierRecommed(SysSupplierRecommed recommed );
	
	/**
	 * 物流方式列表
	 * @param qm
	 * @return
	 */
	PageModel<LogisticsModelDto> searchLogisticsModelList(QueryModel<LogisticsModelDto> qm);
	
	/**
	 * 修改物流方式
	 * @param model
	 * @return
	 */
	int updateLogisticModel(LogisticsModel model);
	
	/**
	 * 付款方式列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bPaymentDto> searchb2bPaymentList(QueryModel<B2bPaymentDto> qm);
	
	/**
	 * 修改付款方式
	 * @param b2bPayment
	 * @return
	 */
	int updatePayment(B2bPayment b2bPayment);



	/**
	 * 搜索角色列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bRoleDto> searchB2bRoleGrid(QueryModel<B2bRoleDto> qm);

	/**
	 * 根据查询前端权限列表
	 * @param rolePk
	 * @return
	 */
	List<B2bRoleMenuDto> getb2broleMenuByRolepk(String rolePk);
	/**
	 * 根据查询前端权限列表
	 * @param id
	 * @param companyType
	 * @return
	 */
	List<B2bMenuDto> getB2bMenuByParentPk(String id,Integer companyType);
	
	/**
	 * 保存角色修改
	 * @param role
	 * @param node
	 * @return
	 */
	String saveB2bRole(B2bRole role, String node);
	/**
	 *搜索banner列表
	 * @param qm
	 * @return
	 */
	PageModel<SysBannerExtDto> searchBannerdata(QueryModel<SysBannerExtDto> qm);

	/**
	 * 新增banner图
	 * @param banner
	 * @return
	 */
	int addSysBanner(SysBanner banner);
	/**
	 * 根据公司类型和父pk查询公司集合
	 * @param companyType
	 * @param parentPk
	 * @return
	 */
	List<B2bCompanyDto> getB2bCompanyDtoByType(Integer companyType,String parentPk);
	
	/**
	 * 搜索供应商推荐列表
	 * @param qm
	 * @return
	 */
	PageModel<SysSupplierRecommedExtDto> searchSuppREdata(QueryModel<SysSupplierRecommedExtDto> qm);
	
	/**
	 * 搜索物流方式列表
	 * @param qm
	 * @return
	 */
	PageModel<LogisticsModelDto> logisticsModelList(QueryModel<LogisticsModelDto> qm);

	/**
	 * 搜索付款方式列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bPaymentDto> searchb2bPaymentdata(QueryModel<B2bPaymentDto> qm);

	/**
	 * 导出订单条数
	 * @param qm
	 * @return
	 */
	int exportOrderListCount(QueryModel<B2bOrderExtDto> qm);

	/**
	 * 导出订单商品
	 * @param qm
	 * @return
	 */
	List<B2bOrderExtDto> exportOrderGoodsList(QueryModel<B2bOrderExtDto> qm,ManageAccount account);


	/**
	 * 导出订单商品
	 * @param params
	 * @param account
	 * @return
	 */
	int saveExcelToOss(OrderDataTypeParams params, ManageAccount account);

	/**
	 * 导出订单信息
	 * @param params
	 * @param account
	 * @return
	 */
	int saveOrderGoodsExcelToOss(OrderDataTypeParams params, ManageAccount account);


	/**
	 * 导出订单
	 * @param qm
	 * @param type 1:导出条数；2：导出
	 * @return
	 */
//	List<B2bOrderExtDto> exportOrderList(QueryModel<B2bOrderExtDto> qm,ManageAccount account, int type);
	
	/**
	 * 根据pk查询公司对象
	 * @param companyPk
	 * @return
	 */
	B2bCompanyDto getByCompanyPk(String companyPk);


	/**
	 * 查询菜单栏列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bMemubarManageExtDto> searchMemuManageList(QueryModel<B2bMemubarManageExtDto> qm);

	/**
	 * 删除菜单栏列表
	 * @param pk
	 * @return
	 */
	int delMemuManageList(String pk);

	/**
	 * 编辑菜单栏列表
	 * @param extDto
	 * @return
	 */
	int editMemuManageList(B2bMemubarManageExtDto extDto);

	/**
	 * 导出纱线合同
	 * @param orderNumber
	 * @param resp
	 * @param req
	 * @return
	 * @throws Exception
	 */
	B2bOrderExtDto exportYarnOrderPDF(String orderNumber, HttpServletResponse resp, HttpServletRequest req) throws Exception;


	/**
	 * 查询导出订单列表
	 * @param qm
	 * @return
	 */
	PageModel<SysExcelStoreExtDto> searchExcelStoreList(QueryModel<SysExcelStoreExtDto> qm);

	/**
	 * 导出报表
	 * @param params
	 * @param account
	 * @param methodName
	 * @param name
	 * @param flag 
	 */
	int saveReportExcelToOss(ReportFormsDataTypeParams params, ManageAccount account, String methodName, String name, int flag);

	/**
	 * 导出会员报表
	 * @param params
	 * @param account
	 * @param methodName
	 * @param name
	 * @param flag 
	 */
	int saveCustomerExcelToOss(CustomerDataTypeParams params, ManageAccount account, String methodName, String name, int flag);

	/**
	 * 订单导出
	 * @param params
	 * @param account
	 * @param string
	 * @param string2
	 * @param i
	 * @return
	 */
	int saveOrderExcelToOss(OrderDataTypeParams params, ManageAccount account, String methodName, String name, int flag);

	/**
	 * 根据订单号查询商品
	 * @param orderNumber
	 * @return 
	 */
	List<B2bOrderGoodsExtDto> searchOrderGoods(String orderNumber);

	/**
	 * 更新提货订单状态
	 * @param deliveryNumber
	 * @param status
	 */
	void sureDeliveryOrderStatus(String deliveryNumber, Integer status);


}
