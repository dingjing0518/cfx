package cn.cf.service;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractDtoEx;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.Cgoods;
import cn.cf.entity.Corder;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bContractGoods;

public interface B2bContractService {
	
	@Transactional
	void submitOrder(List<B2bContractDtoEx> b2bContract,List<B2bContractGoodsDtoEx> list);
	
	@Transactional
	List<B2bContractDtoEx> submitContract(Corder order,B2bCompanyDto company,B2bMemberDto member,B2bAddressDto address,Map<String, List<Cgoods>> map);

	PageModel<B2bContractDtoEx> searchListByType(String type, B2bStoreDto store, B2bCompanyDto company, Map<String, Object> map, Sessions session, B2bMemberDto memberDto);
	
	Map<String,Object> searchStatusByType(String type, B2bStoreDto store ,B2bCompanyDto company, Map<String,Object> map, Sessions session, B2bMemberDto memberDto);

	int updateContract(B2bContract contract);
	
	@Transactional
	void updateContractStatus(B2bContract contract);
	
	@Transactional
	B2bContractDtoMa updateContractStatus(B2bContract contract,SysCompanyBankcardDto card);
	
	B2bContractDto getB2bContract(String contractNo);
	
	B2bContractDto getB2bContractDetails(String contractNo);
	
	List<B2bContractGoodsDtoEx> exportContractList(String type,B2bStoreDto store, B2bCompanyDto company,Map<String,Object> map,Sessions session,B2bMemberDto memberDto);
	
	B2bContractDto updateContract(B2bContract contract,List<B2bContractGoods> list);
	
	/**
	 * 合同提交审批
	 * @param contractNo  合同编号
	 * @return
	 */
	String submitAudit(String contractNo);
	 /**
		 * 合同商品列表
		 * 重量未转换完的商品
		 * @param req
		 * @return
		 */
	List<B2bContractGoodsDtoEx>  searchListByContractNo(String contractNo);
	/**
	 * 凌晨要关闭的合同
	 * @return
	 */
	List<B2bContractDto> cancelContract();

	
	/**
	 * 自动完成合同
	 * @param days 天数
	 * @param contractStatus 合同状态,1:待付款,2:待审批,3:待发货,4:部分发货，5:待收货
	 */
	void autoCompleteContract(int days,int contractStatus);
	
}
