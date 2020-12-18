/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.entity.B2bInvoiceEntity;
import cn.cf.entity.MemberDataReport;
import cn.cf.model.MarketingCompany;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
public interface B2bCompanyExtDao extends B2bCompanyDao {

	/**
	 * 根据条件统计公司数量
	 * 
	 * @param map
	 * @return
	 */
	int searchGridCountExt(Map<String, Object> map);

	/**
	 * 根据条件搜索公司
	 * 
	 * @param map
	 * @return
	 */
	List<B2bCompanyExtDto> searchGridExt(Map<String, Object> map);


	/**
	 * 查询导出数据量
	 *
	 * @param map
	 * @return
	 */
	int searchGridSupplierExtCounts(Map<String, Object> map);

	/**
	 * 根据条件搜索公司(供应商)
	 *
	 * @param map
	 * @return
	 */
	List<B2bCompanyExtDto> searchGridSupplierExt(Map<String, Object> map);


	/**
	 * 查询供应商子公司公司列表
	 *
	 * @param map
	 * @return
	 */
	List<B2bCompanyExtDto> searchGridSupplierSubExt(Map<String, Object> map);
	
	
	/**
	 * 根据条件搜索公司
	 * 
	 * @param pk
	 * @return
	 */
	B2bCompanyExtDto getByPkExt(String pk);

	/**
	 * 营销中心采购商查询
	 * 
	 * @param map
	 * @return
	 */
	int searchCompanyCount(Map<String, Object> map);

	List<B2bCompanyExtDto> searchAuditCompayList(Map<String, Object> map);

	int searchCompayCountsByOrder(Map<String, Object> map);

	/**
	 * 更新公司信息
	 * 
	 * @param extDto
	 * @return
	 */
	int updateCompany(B2bCompanyExtDto extDto);


	/**
	 * 更新公司发票信息
	 *
	 * @param entity
	 * @return
	 */
	int updateCompanyInvoice(B2bInvoiceEntity entity);

	/**
	 * 更新父公司
	 * 
	 * @param extDto
	 * @return
	 */
	int updateCompanyByparentPk(B2bCompanyExtDto extDto);

	/**
	 * gen
	 * 
	 * @param companyPk
	 * @return
	 */
	List<B2bCompanyDto> searchCompanyByParentAndChild(String companyPk);

	/**
	 * 判断是否存在相同OrganizationCode的公司
	 */
	List<B2bCompanyExtDto> isExistOrganizationCode(Map<String, Object> map);

	/**
	 * 判断是否存在相同名称的公司
	 * 
	 * @param map
	 * @return
	 */
	int isExistCompanyVaild(Map<String, Object> map);

	/**
	 * 公司管理统计数据
	 * 
	 * @param map
	 * @return
	 */
	int searchPurchaserCompanyCount(Map<String, Object> map);

	/**
	 * 公司管理列表
	 * 
	 * @param map
	 * @return
	 */
	List<B2bCompanyExtDto> searchPurchaserCompanyList(Map<String, Object> map);

	/**
	 * 查询好友公司
	 * 
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getCompanyDto(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<ManageAuthorityDto> getBtnManageAuthorityByRolePk(Map<String, Object> map);

	/**
	 * 分配线下业务员所需方法
	 * 
	 * @param map
	 * @return
	 */
	int marketingCompanyCountsByPk(Map<String, Object> map);

	/**
	 * 分配线下业务员所需方法
	 * 
	 * @param map
	 * @return
	 */
	int updateMarketingCompany(MarketingCompany marketingCompany);

	/**
	 * 分配线下业务员所需方法
	 * 
	 * @param map
	 * @return
	 */
	int insertMarketingCompany(MarketingCompany marketingCompany);
	
	/**
	 * 查询前端注册公司的第一个人
	 * @param pk
	 * @return
	 */
	B2bCompanyExtDto getMemberCompanyByPk(String companyPk);

	int countCheckCompany(Map<String, Object> map);

	MemberDataReport searchOnedayData(@Param("date") String date);

    List<B2bCompanyDto> getByBlock(Map<String, Object> map);

    List<B2bCompanyExtDto> searchAuditStoreList(Map<String, Object> map);

    int searchStoreCount(Map<String, Object> map);

}
