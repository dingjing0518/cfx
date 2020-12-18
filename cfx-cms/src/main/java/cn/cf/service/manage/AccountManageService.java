/**
 * 
 */
package cn.cf.service.manage;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bManageRegionDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.ManageRoleDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.dto.MarketingPersonnelExtDto;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingPersonnel;

/**
 * @author bin
 * 
 */
public interface AccountManageService {
	
	/**
	 * 根据Pk查询
	 * @param pk
	 * @return
	 */
	ManageAuthorityDto getManageAuthorityByPk(String pk);
	
	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	List<ManageRoleDto> getManageRoleByName(String name);
	
	/**
	 * 查询列表
	 * @param qm
	 * @return
	 */
	PageModel<ManageAccountExtDto> searchManageAccountList(
			QueryModel<ManageAccountExtDto> qm);
	/**
	 * 启用禁用，删除方法
	 * @param map
	 * @return
	 */
	int updateIsVisableOrDelete(ManageAccount model);

	/**
	 * 编辑
	 * @param model
	 * @return
	 */
	String updateManageAccount(ManageAccount model);

	/**
	 * 密码重置
	 * @param pk
	 * @return
	 */
	int updateRePassword(String pk);
	
	public List<ManageAccountDto> getOnline();
	
    
    public List<MarketingPersonnelDto> getPersonByType(Integer type);
    /**
     * 
     * 人员管理列表
     * @param qm
     * @param accountPk
     * @return
     */
	PageModel<MarketingPersonnelExtDto> searchPersonnelList(QueryModel<MarketingPersonnelExtDto> qm, String accountPk);
	/**
	 * 
	 *新增
	 * @param marketingPersonnel
	 * @return
	 * @throws Exception
	 */
	int insertPersonnel(MarketingPersonnel marketingPersonnel);
	/**
	 * 获取所以有效账户
	 * @return
	 */
	List<ManageAccountExtDto> getAllAccountList();

	/**
	 * 
	 * 获取未删除的区域
	 * @return
	 */
    List<B2bManageRegionDto> getRegionAllList();
    /**
     * 
     * 获取未删除未禁用的区域
     * @return
     */
    List<B2bManageRegionDto> getRegionNoIsVisiablList();

    /**
     * 获取所以被删除的账号
     * @return
     */
    List<ManageAccountDto>  getAllNoisDeleteAccountList();

       
	
	
}
