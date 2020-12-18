package cn.cf.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bCompany;

public interface MemberCompanyService {
	
	B2bCompanyDtoEx getCompany(String companyPk);
	/**
	 * 完善公司信息
	 * @param dto 公司修改后的内容
	 * @param member 当前登录会员
	 * @return 返回公司信息
	 */
	@Transactional
	B2bCompany perfectCompanyDto(B2bCompanyDtoEx dto,B2bMemberDto member);
	/**
	 * 新增公司
	 * @param dto 公司修改后的内容
	 * @param member 当前登录会员
	 * @return 返回公司信息
	 */
	@Transactional
	B2bCompany addCompany(B2bCompanyDtoEx dto,B2bMemberDto member);
	/**
	 * 根据公司pk返回所有子母公司pk
	 * @param companyType(1:采购商 2:供应商)
	 * @param returnType(1:返回子母所有 2:返回子公司)
	 * @return
	 */
	String[] getCompanyPks(B2bCompanyDto company, Integer companyType, Integer returnType);
	
	/**
	 * 客户管理列表
	 * @param map
	 * @return
	 */
	PageModel<B2bCompanyDtoEx> searchCustomerList(Map<String, Object> map);
	
	
	/**
	 * 供应商入驻
	 * @param dto 公司信息
	 * @param memberDto  会员
	 * @return
	 */
	String supplierSettled(B2bCompanyDto dto,B2bMemberDto memberDto,String sessionId,Sessions session);
	
	/**
	 * 名称查询公司
	 * @param name
	 * @return
	 */
	B2bCompanyDto getCompanyByName(String name);
	
	/**
	 * 新增或更新公司
	 * @param company
	 * @param type
	 */
	void insertOrUpdateCompany(B2bCompany company ,Integer type);
	
	void updateParentPk(String oldParentpk,String newParentPk);
	
	/**
	 * 查询公司
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getCompanyList(Map<String, Object> map);
	
	B2bCompanyDto getByPk(String companyPk);

}
