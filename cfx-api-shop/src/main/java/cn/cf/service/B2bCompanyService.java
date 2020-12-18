package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.model.B2bCompany;

public interface B2bCompanyService {
	/**
	 * 新增公司
	 * @param company
	 * @return
	 */
	int addCompany(B2bCompany company);
	
	/**
	 * 编辑公司
	 * @param company
	 * @return
	 */
	RestCode updateCompany(B2bCompany company);
	
	/**
	 * 获取某一个公司
	 * @param pk
	 * @return
	 */
	B2bCompanyDtoEx getCompany(String pk);
	
	/**
	 * 根据名称查询公司
	 * @param companyName 公司名称
	 * @return
	 */
	B2bCompanyDtoEx getCompanyByName(String companyName);

	
	/**
	 * 计算公司被收藏的数量
	 * @param storePk
	 * @return
	 */
	Integer countCollectCom(String storePk);

	
	/**
	 * 根据公司pk返回所有子母公司
	 * @param companyType(1:采购商 2:供应商)
	 * @param companyPk(公司pk)
	 * @param returnType(1:返回子母所有 2:返回子公司)
	 * @param map(附加参数)
	 * @return
	 */
	List<B2bCompanyDtoEx> searchCompanyList(Integer companyType,String companyPk,Integer returnType,Map<String,Object> map);
	
	/**
	 * 根据公司pk返回所有子母公司pk
	 * @param companyType(1:采购商 2:供应商)
	 * @param company
	 * @param returnType(1:返回子母所有 2:返回子公司)
	 * @return
	 */
	String[] getCompanyPks(B2bCompanyDto company, Integer companyType,Integer returnType );

	/**
	 * 新增子公司
	 * @param model
	 * @return
	 */
	RestCode addSubCompany(B2bCompany model);


	/**
	 *根据条件获取公司
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map);


	B2bCompanyDto getByCustomerPk(String customerPk);
	
	/**
	 * 可添加采购商列表
	 * @param companyPk
	 * @return
	 */
	List<B2bCompanyDtoEx> searchPurchaserCompany(Map<String, Object> map);
	
	
	String[] findCompanys(String companyPk,Integer companyType);
	
	B2bCompanyDtoEx getCompanyDto(String pk);
	
	/**
	 * 根据公司pk分页返回子母公司
	 * @param companyType(1:采购商 2:供应商)
	 * @param companyPk(公司pk)
	 * @param returnType(1:返回子母所有 2:返回子公司)
	 * @param map(附加参数)
	 * @return
	 */
	 PageModel<B2bCompanyDtoEx> searchCompanyListByLimit(Integer companyType,String companyPk,
			 Integer returnType,Map<String,Object> map,Integer auditStatus,Integer auditSpStatus);

	
	 /**
	  * 更新公司的发票信息
	  * @param companyDto
	  * @param memberPk
	  * @return
	  */
	 String updateInvoice(B2bCompanyDto companyDto,String memberPk);
	 
	 
}
