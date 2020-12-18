package cn.cf.service.enconmics;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bBillCustomerApplyDto;
import cn.cf.dto.B2bBillCustomerApplyExtDto;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.MemberShip;
import cn.cf.entity.BillCustomerMongo;

public interface BillCustomerApplyService {
	/**
	 * 客户管理
	 * @param qm
	 * @param currentMemberShip
	 * @return
	 */
	PageModel<B2bBillCustomerApplyExtDto> searchBillCustomerApplyList(QueryModel<B2bBillCustomerApplyExtDto> qm,
			MemberShip currentMemberShip);
	/**
	 * 更新票据申请表
	 * @param dto
	 * @return
	 */
	int updateBillCustomer(B2bBillCustomerApplyExtDto dto);
	
	/**
	 *  保存审批历史记录
	 * @param pk
	 */
	void insertBillCustomerMongo(String pk);
	
	/**
	 * 待审批
	 * @param start
	 * @param limit
	 * @param groupId
	 * @param pk
	 * @return
	 */
	PageModel<B2bBillCustomerApplyExtDto> searchBillTask(int start, int limit, String groupId, String pk);
	
	/**
	 * 查询审批记录
	 * @param map
	 * @param qm
	 * @param pk
	 * @param i
	 * @return
	 */
	PageModel<BillCustomerMongo> getByMap(Map<String, Object> map, QueryModel<BillCustomerMongo> qm, String pk, int i);
	
	/**
	 * 查询申请的产品
	 * 
	 * @param billCustPk
	 * @param pk
	 * @return
	 */
	B2bBillCustomerApplyExtDto getCustGoodsByCustomerPk(String billCustPk, String pk);
	
	
	B2bBillCustomerApplyExtDto getDtoByMap(Map<String, Object> map);
	
	/**
	 * 编辑借据的芒果申请记录
	 * @param pk
	 */
	void updateBillMongo(String pk);
	
	/**
	 * 根据公司名称查询借据申请记录
	 * @param companyPk
	 * @param qm
	 * @param pk
	 * @return
	 */
	PageModel<BillCustomerMongo> getByCompanyPk(String companyPk, QueryModel<BillCustomerMongo> qm, String pk);
	
	int updateAuditBillCustomer(B2bBillCustomerApplyExtDto dto, int status);


}
