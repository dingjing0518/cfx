package cn.cf.service.marketing;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.model.B2bManageRegion;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingCompany;

public interface McustomerManageService {
	
	/**
	 * 根据类型查询后台人员管理
	 * @param type
	 * @return
	 */
	List<MarketingPersonnelDto> getPersonByType(Integer type);
	
	/**
	 * 根据增值条件查询公司信息
	 * @param qm
	 * @param type 1 采购洽谈中的  2订单已完成的
	 * @param accountPk 
	 * @return
	 */
	PageModel<B2bCompanyExtDto> searchCompanyListByMarrieddealOrder( QueryModel<B2bCompanyExtDto> qm, ManageAccount accountPk);
	
	/**
	 * 根据角色pk获取拥有权限
	 * @param rolePk
	 * @param btnName
	 * @return
	 */
	List<ManageAuthorityDto> getBtnRoleList(String rolePk,String btnName);
	
	/**
	 * 分配线下业务员
	 * @param marketingCompany
	 * @return
	 */
	int insertOrUpdateMarketingCompany(MarketingCompany marketingCompany);
	
	/**
	 * 获取账号列表
	 * @return
	 */
	List<ManageAccountDto> getOnline();
	
	/**
	 * 获取所有付款方式
	 * @return
	 */
	List<B2bPaymentDto> getPaymentList();
	/**
	 * 获取订单
	 * @param qm
	 * @return
	 */
	PageModel<B2bOrderExtDto> searchOrderMList(QueryModel<B2bOrderExtDto> qm);
	/**
	 * 导出订单
	 * @param qm
	 * @param i 
	 * @return
	 */
	List<B2bOrderExtDto> exportOrderMList(QueryModel<B2bOrderExtDto> qm, int type);


	int saveOrderMExcelToOss(OrderDataTypeParams params, ManageAccount account);

	/**
	 * 查询大区管理列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bManageRegionExtDto> searchManageRegionList(QueryModel<B2bManageRegionExtDto> qm);

	/**
	 * 查询大区管理列表
	 * @param pk
	 * @return
	 */
	B2bManageRegionDto getSearchManageRegionByPk(String pk);

	/**
	 * 修改大区管理
	 * @param qm
	 * @return
	 */
	int updateManageRegion(B2bManageRegionExtDto qm);

	/**
	 * 删除大区管理
	 * @param pk
	 * @return
	 */
	int delManageRegion(String pk);
	
	/**
	 * 
	 * 分配店铺业务员
	 * @param qm
	 * @param adto
	 * @return
	 */
    PageModel<B2bCompanyExtDto> searchStoreListByAccount(QueryModel<B2bCompanyExtDto> qm, ManageAccount adto);

    List<MarketingPersonnelDto> getDistributePerson();
    
    /**
     * 根据公司信息查找业务员
     * @param map
     * @return
     */
	ManageAccountDto getAccountByMap(Map<String, Object> map);
	
	/**
	 * 查询有效的区域经理
	 * @param map
	 * @return
	 */
	List<ManageAccountExtDto> getRegionalAccountByMap();
	
}
