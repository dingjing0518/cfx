package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;

import cn.cf.dto.*;
import cn.cf.entity.CreditInfo;
import cn.cf.entity.CreditInvoiceDataTypeParams;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.ManageAccount;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.model.B2bEconomicsBank;
import cn.cf.model.B2bOrder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EconomicsCreditService {

	/**
	 *  授信银行列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bEconomicsBankExtDto> economicsBankList(QueryModel<B2bEconomicsBankExtDto> qm);

	/**
	 *  编辑、新增银行
	 * @param bank
	 * @return
	 */
	int inserOrUpdateEconomicsBank(B2bEconomicsBank bank);
	/**
	 * 
	 * 查询银行授信
	 * @param map
	 * @return
	 */
	PageModel<B2bEconomicsBankCompanyDto> searchAuthorizedBanks(Map<String, Object> map);
	/**
	 * 根据公司pk查询银行授信
	 * @param companyPk
	 * @return
	 */
	List<B2bEconomicsBankCompanyDto> getEconomicsBankCompanyByPk(String companyPk);
	/**
	 * 多条件查询银行授信
	 * @param map
	 * @return
	 */
	List<B2bEconomicsBankCompanyDto> getEconomicsBankCompanyByMap(Map<String, Object> map);
	
	
	/**
	 * 编辑订单
	 * @param order
	 * @return
	 */
	int updateOrder(B2bOrder order);
	/**
	 * 根据pk查询phone
	 * @param pk
	 * @return
	 */
	B2bCreditDto getB2bcreditByPk(String pk);
	/**
	 *  根据pk查询credit
	 * @param pk
	 * @return
	 */
	B2bCreditDto getcreditByPk(String pk);
	/**
	 *  编辑授信平台额度
	 * @param creditGoodsExtDto
	 * @return
	 */
	int updateCreditCustomer(B2bCreditGoodsExtDto creditGoodsExtDto);
	/**
	 * 授信额度列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bCreditExtDto> searchCreditList(QueryModel<B2bCreditExtDto> qm);


	/**
	 *导出授信客户管理发票查询
	 * @param params
	 * @param account
	 * @return
	 */
	int saveCreditInvioceExcelToOss(CreditInvoiceDataTypeParams params, ManageAccount account);


	/**
	 * 查询发票列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bCreditInvoiceExtDto> searchCreditInvoiceList(QueryModel<B2bCreditInvoiceExtDto> qm);


	/**
	 * 查询税务机关数据
	 * @param taxAuthorityName
	 * @return
	 */
	List<B2bTaxAuthorityDto> getB2bTaxAuthority(String taxAuthorityName);

	/**
	 * 申请证书委托文件导入
	 * @param file
	 * @return
	 */
	String importTaxAuthority(String pk,String taxNature, String taxAuthorityName,String taxAuthorityCode, MultipartFile file);


	/**
	 * 票据申请客户列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bBillCustomerExtDto> searchBillList(QueryModel<B2bBillCustomerExtDto> qm);


	/**
	 * 票据产品申请客户列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bBillCusgoodsExtDto> searchBillCustGoodsList(QueryModel<B2bBillCusgoodsExtDto> qm);

	/**
	 * 
	 * 查询授信产品
	 * @param map
	 * @return
	 */
	PageModel<B2bCreditGoodsExtDto> searchCreditGoods(Map<String, Object> map);
	/**
	 * 
	 * //TODO 添加方法功能描述
	 * @param companyPk
	 * @param status
	 * @param remarks
	 * @return
	 */
	int updateCreditStatusAndRemarks(String companyPk, int status, String remarks);


	B2bCreditDto getByCompanyPk(String companyPk);
	/**
	 * 最新审核通过的信息插入b2b_credit,b2b_credit_goods
	 * //TODO 添加方法功能描述
	 * @param dto
	 * @param status
	 */
	void insertCreditInfo(B2bEconomicsCustomerExtDto dto, int status);
	/**
	 * 最新审核通过的信息更新至b2b_credit,b2b_credit_goods
	 * @param companyPk
	 */
	@Transactional
	void updateEconCustomerToCredit(String companyPk);
	/**
	 * 
	 * 审核授信状态更新（b2b_credit，b2b_credit_goods，b2b_economics_customer）
	 * @param companyPk
	 * @param creditStatus
	 * @return
	 */
	int updateCreditStatus(String companyPk, Integer creditStatus);
	
	/**
	 * 根据creditPk查询金融产品
	 * //TODO 添加方法功能描述
	 * @param map
	 * @return
	 */
	List<B2bCreditGoodsExtDto> searchCreditGoodsByCreditPk(Map<String, Object> map);
	
	/**
	 * 授信的禁用启用
	 * @param cExtDto
	 * @return
	 */
	int updateCreditIsVisable(B2bCreditGoodsExtDto cExtDto);



	/**
	 * 票据客户审核状态修改
	 * @param billExtDto
	 * @return
	 */
	int updateBillAndGoods(B2bBillCustomerExtDto billExtDto);
	/**
	 * 票据客户产品审核状态修改
	 * @param billExtDto
	 * @return
	 */
	int updateCustGoodsStatus(B2bBillCusgoodsExtDto billExtDto);
	/**
	 * 授信客户管理-客户申请
	 * @param info
	 * @param pk
	 * @return
	 */
	int updateCreditApply(CreditInfo info, String pk);


}
