package cn.cf.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.entity.CustomerDataImport;
import cn.cf.entity.Sessions;
import cn.cf.entry.CustomerImport;

public interface MemberFacadeService {
	/**
	 * 登录
	 * 
	 * @param mobile 手机号
	 * @param password 密码
	 * @param sessionId sessionId
	 * @param source 来源  1：pc;2:wap;3:app
	 * @return
	 */
	String login(String mobile, String password, String sessionId,int source);
	
	
	/**
	 * 免验证登录
	 * @param mobile
	 * @param sessionId
	 * @return
	 */
	String noVerificationLogin(String mobile,String erpToken,String sessionId,Integer source);

	/**
	 * 注册
	 * 
	 * @param member
	 * @param sessionId
	 * @return
	 */
	String register(B2bMemberDtoEx member, String sessionId,Integer source);

	/**
	 * 退出
	 * 
	 * @param sessionId
	 * @param mobile
	 * @return
	 */
	String logout(Sessions session);

	/**
	 * 重置密码
	 */
	String resetPassword(String mobile, String password);

	/**
	 * 修改密码
	 * 
	 * @param mobile
	 * @param password
	 * @param newPassWord
	 * @return
	 */
	String updatePassword(String mobile, String password, String newPassword);

	/**
	 * 完善公司信息
	 * 
	 * @param companyDto
	 * @param memberDto
	 * @return
	 */
	String prefectCompany(B2bCompanyDtoEx companyDto, B2bMemberDto memberDto, Sessions session);

	/**
	 * 查询商品详情
	 *
	 * @param pk
	 * @param memberPk
	 * @param auctionPk
	 *            竞拍场次 （非必填）
	 * @return
	 */
	B2bGoodsDto getB2bGoodsByPk(String pk, String memberPk, String auctionPk);

	/**
	 * 分配业务员
	 * 
	 * @param cusS
	 * @return
	 */
	RestCode addCustomerSaleMan(B2bCustomerSalesmanDto cus);
	
	@Transactional
	RestCode addMember(B2bMemberDtoEx dto);

	RestCode updateMember(B2bMemberDtoEx dto, Sessions session);

	/**
	 * 客户导入
	 *
	 * @param cusS
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class })
	RestCode customerImport(String filePath, String storePk);

	
	String importCustomerData(CustomerDataImport customerDataImport);
	
	PageModel<CustomerDataImport> searchCustomerDataImport(String storePk,Integer start,Integer limit);
	
	RestCode importCustomer(List<CustomerImport> list,String storePk);

}
