package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bBillCustomerApplyExtDto;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bBillSigncardExtDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bBillSigningExtDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.model.B2bBillSigning;
import cn.cf.model.ManageAccount;

public interface B2bBillCustomerService {
	
	/**
	 * 根据公司查询
	 * @param companyPk
	 * @return
	 */
	B2bBillCustomerDto getByCompanyPk(String companyPk);
	/**
	 * 插入审批通过的票据
	 * @param dto
	 * @param two
	 */
	void insertCustomerInfo(B2bBillCustomerApplyExtDto dto, int two);
	/**
	 * 编辑审批通过的票据
	 * @param companyPk
	 */
	void updateCustomerInfo(String companyPk);
	/**
	 * 审核通过的供应商公司
	 * @return
	 */
	List<B2bCompanyDto> supCompanyList();
	
	/**
	 * 供应商签约列表
	 * @param qm
	 * @param pk
	 * @return
	 */
	PageModel<B2bBillSigningExtDto> searchBillSuppSigningList(QueryModel<B2bBillSigningDto> qm, String pk);
	
	/**
	 * 编辑供应商签约
	 * @param model
	 */
	String updateBillSuppSigning(B2bBillSigning model);
	/**
	 * 查询供应商签约账户
	 * @param qm
	 * @param account
	 * @return
	 */
	PageModel<B2bBillSigncardExtDto> getBankCard(QueryModel<B2bBillSigncardExtDto> qm, ManageAccount account);
	
	/**
	 * 删除供应商签约账户
	 * @param pk
	 * @return
	 */
	int delBillSigncard(String pk);
	/**
	 * 校验银行账户是否已存在
	 * @param dto
	 * @return
	 */
	Boolean checkCompanyAndBank(B2bBillSigncardDto dto);
	/**
	 * 编辑
	 * @param dto
	 * @return
	 */
	Map<String, Object> updateBillSigncard(B2bBillSigncardDto dto);

}
