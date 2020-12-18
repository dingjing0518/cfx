package cn.cf.service.operation;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.entity.B2bInvoiceEntity;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.B2bCompany;
import cn.cf.model.ManageAccount;

public interface CustomerManageService {
	/**
	 * 查询公司
	 * @param companyType 公司类型
	 * @param parentPk 母公司pk
	 * @return
	 */
	List<B2bCompanyExtDto> getB2bCompayDtoByType(Integer companyType,String parentPk);
	
	/**
	 * 查询公司列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bCompanyExtDto> searchCompanyList(QueryModel<B2bCompanyExtDto> qm,ManageAccount account);

	/**
	 * 查询供应商子公司列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bCompanyExtDto> searchCompanySubList(QueryModel<B2bCompanyExtDto> qm,ManageAccount account);
	
	
	/**
	 * 公司管理列表(包括总公司和子公司)
	 * @param qm
	 * @return
	 */
	PageModel<B2bCompanyExtDto> searchCompanyManageList(QueryModel<B2bCompanyExtDto> qm,ManageAccount account);
	
	/**
	 * 根据条件查询Token列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bTokenDto> searchB2bToken(QueryModel<B2bTokenDto> qm);
	
	/**
	 * 会员查询
	 * @param qm
	 * @return
	 */
	PageModel<B2bMemberExtDto> searchb2bMemberExtList(QueryModel<B2bMemberExtDto> qm,ManageAccount account);
	
	
	/**
	 * 会员导出
	 * @param dto
	 * @return
	 */
	List<B2bMemberExtDto> exportB2bMemberExtList(QueryModel<B2bMemberExtDto> dto,ManageAccount account);
	
	/**
	 * 营业执照号是否和其他公司重复
	 * @param isAdd
	 * @param companyExtDto
	 * @return
	 */
	String isExistOrgCode(B2bCompanyExtDto companyExtDto,boolean isAdd);
	
	/**
	 * 公司信息修改
	 * @param companyExtDto
	 * @param adto
	 * @return
	 */
	String updateB2bCompany(B2bCompanyExtDto companyExtDto,ManageAccount adto);
	
	
	/**
	 * 添加公司
	 * @param company
	 * @return
	 */
	String insertB2bCompany(B2bCompany company,ManageAccount adto);
	
	/**
	 * 根据公司获取授信信息
	 * @param companyPk
	 * @return
	 */
	String getCreditByCompanyPk(String companyPk);
	
	
	/**
	 * 导出公司数据
	 * @param qm
	 * @return
	 */
	List<B2bCompanyExtDto> exportB2bCompany(QueryModel<B2bCompanyExtDto> qm,ManageAccount account);


	/**
	 * 保存公司导出信息记录
	 * @param params
	 * @return
	 */
	int saveCompanyExcelToOss(CustomerDataTypeParams params, ManageAccount account);

	/**
	 * 保存会员导出信息记录
	 * @param params
	 * @return
	 */
	int saveMemberExcelToOss(CustomerDataTypeParams params, ManageAccount account);

	
	/**
	 * 公司设置超级管理员查询管理员列表
	 */
	PageModel<B2bMemberDto> getAllMemberList(QueryModel<B2bMemberExtDto> qm,ManageAccount account);
	
	/**
	 * 获取银行信息
	 * @param qm
	 * @return
	 */
	PageModel<SysCompanyBankcardDtoEx> getBankCard(QueryModel<SysCompanyBankcardDtoEx> qm,ManageAccount account);
	
	/**
	 * 获取银行NameCode(bankname)模糊查找
	 * @param dto
	 * @return
	 */
	List<SysBankNamecodeExtDto> getSysBankNameCode(SysBankNamecodeExtDto dto);
	
	/**
	 * 根据银行名称精确查找银行
	 * @param bankname
	 * @return
	 */
	SysBankNamecodeDto getSysBankName(String bankname);
	
	/**
	 * 获取所有SysBankNameCode保存到mongo
	 * @return
	 */
	int getAllSysBankNameCode();
	
	/**
	 * 更新CompanyBankCard表
	 * @param dto
	 * @return
	 */
	Map<String,Object> updateCompanyBankCard(SysCompanyBankcardDto dto);
	
	/**
	 * 公司设置超级管理员
	 * @param memberPk
	 * @param oldMemberPk
	 * @param companyPk
	 * @return
	 */
	int updateSetAdmin(String memberPk,String oldMemberPk,String companyPk);
	/**
	 * 根据公司类型获取角色集合
	 * @param companyType
	 * @return
	 */
	List<B2bRoleExtDto> getB2bRoleList(int companyType);
	/**
	 * 删除银行
	 * @param pk
	 * @return
	 */
	int delSupplierBank(String pk);
	/**
	 * 检查银行是否存在
	 * @param dto
	 * @return
	 */
	Boolean checkCompanyAndBank(SysCompanyBankcardDto dto);

	/**
	 * 修改发票信息
	 * @param entity
	 * @return
	 */
	int updateCompanyInvoice(B2bInvoiceEntity entity);
	
	/**
	 * 查询店铺
	 * @return
	 */
	List<B2bStoreExtDto> getB2bStore();


	/**
	 * 查询总公司
	 * @return
	 */
	B2bCompanyDto getParentCompanyByParentPk(String parentPk);

	List<B2bCompanyExtDto> getPurCompanyList();
	
	
}
