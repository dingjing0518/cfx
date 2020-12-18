package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.task.Comment;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.B2bEconomicsCustomerGoodsDto;
import cn.cf.dto.B2bEconomicsDatumDto;
import cn.cf.dto.B2bEconomicsDatumExtDto;
import cn.cf.dto.MemberShip;
import cn.cf.dto.SysBankNamecodeDto;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.entity.ProcessEntity;

public interface CustomerService {
    /**
     * 申请客户管理列表
     * 
     * @param qm
     * @param currentMemberShip
     * @return
     */
    PageModel<B2bEconomicsCustomerExtDto> searchEconCustList(QueryModel<B2bEconomicsCustomerExtDto> qm, MemberShip currentMemberShip);

    /**
     * 新增/編輯客戶完善資料
     * 
     * @param customerExtDto
     * @return
     */
    int updateEconomicsCustomer(B2bEconomicsDatumExtDto customerExtDto);

    /**
     * 分配客户更新
     * 
     * @param customerExtDto
     * @return
     */
    int updateEconomicsAssidirName(B2bEconomicsCustomerExtDto customerExtDto);

    /**
     * 查询公司资料
     * 
     * @param econCustPk
     * @param datumType
     * @return
     */
    List<B2bEconomicsDatumDto> getByEconomicsCustomerPk(String econCustPk, String datumType);

    /**
     * 根据主键pk查询
     * 
     * @param econCustPk
     * @return
     */
    B2bEconomicsCustomerExtDto getByEconomCustPk(String econCustPk);

    /**
     * 根据银行名称查询
     * 
     * @param bankName
     * @return
     */
    List<SysBankNamecodeDto> getSysBankByBankName(String bankName);

    /**
     * 根据主键pk查询
     * 
     * @param econCustPk
     * @return
     */
    B2bEconomicsCustomerDto getByPk(String customerPk);

    /**
     * 多条件查询申请
     * 
     * @param map
     * @return
     */
    B2bEconomicsCustomerExtDto getByMap(Map<String, Object> map);

    /**
     * 编辑申请 
     * 
     * @param customer
     * @return
     */
    int updateEconomics(B2bEconomicsCustomerDto customer);

    /**
     * 根据公司查询申请记录
     * 
     * @param companyPk
     * @param qm
     * @return
     */
    List<B2bEconomicsCustomerExtDto> getByCompanyPkEx(String companyPk);

    /**
     * 提交教申请插入芒果
     * 
     * @param pk
     */
    void insertEconCustomerMongo(String pk);

    /**
     * 根据公司查询mango申请记录
     * 
     * @param companyPk
     * @param qm
     * @param accountPk 
     * @return
     */
    PageModel<EconCustomerMongo> getByCompanyPk(String companyPk, QueryModel<EconCustomerMongo> qm, String accountPk);

    /**
     * 审核结果更新芒果
     * 
     * @param pk
     */
    void updateEconomicsMongo(String pk);

    /**
     * 新增/编辑金融产品完善资料
     * 
     * @param request
     * @param response
     * @param customerExtDto
     * @return
     * @throws Exception
     */
    int updateEconomicsCustomerEx(B2bEconomicsDatumExtDto customerExtDto);

    /**
     * 审批通过更新产品信息
     * 
     * @param pk
     */
    void updateEconomicsMongoForContent(String pk);

    /**
     * 查询芒果历史记录
     * 
     * @param map
     * @param qm
     * @param flag  1审批通过 2审批驳回
     * @param accoub 
     * @return
     */
    PageModel<EconCustomerMongo> getByMap(Map<String, Object> map, QueryModel<EconCustomerMongo> qm, String accountPk, int flag);

    /**
     * 查询申请产品
     * 
     * @param economicsCustomerGoodsPk
     * @return
     */
    List<B2bEconomicsCustomerGoodsDto> getGoodsByEconCustomerPk(String economicsCustomerGoodsPk);

    /**
     * 查询申请产品
     * @param accountPk 
     * 
     * @param economicsCustomerGoodsPk
     * @return
     */
    B2bEconomicsCustomerExtDto getCustGoodsByEconomCustPk(String economCustPk, String accountPk);

    /**
     * 编辑银行额度，优秀时间
     * 
     * @param dto
     * @param status
     * @return
     */
    int updateEconomicsAudit(B2bEconomicsCustomerExtDto dto, Integer status);

	List<ProcessEntity> searchProcess(String processInstanceId, List<Comment> commentList);



}
