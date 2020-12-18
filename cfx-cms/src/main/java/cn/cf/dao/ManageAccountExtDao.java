package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.model.ManageAccount;
import org.apache.ibatis.annotations.Param;

public interface ManageAccountExtDao extends ManageAccountDao{

	/**
	 *  根据条件统计数量
	 * @param map
	 * @return
	 */
	int searchAccounts(Map<String, Object> map);
	
	/**
	 * 根据条件查询数据集合
	 * @param map
	 * @return
	 */
	List<ManageAccountExtDto> searchAccountList(Map<String, Object> map);
	/**
	 * 查询登陆用户
	 * @param username
	 * @return
	 */
	ManageAccountExtDto getManageAccountByLoginName(String username );
	
	/**
	 *启用禁用或删除方法；1.启用 2禁用
	 * @return
	 */

	int updateIsVisableOrDelete(ManageAccount account);
	/**
	 * 编辑
	 * @return
	 */
	int updateManageAccount(ManageAccount account);
	
	/**
	 * 重置后台用户密码，重置密码为默认(123456)
	 * @param pk
	 * @return
	 */
	int rePassword(String pk);

	List<ManageAccountDto> getOnlineAccount();

	List<ManageAccountExtDto> getAllAccountList();

	void updatePassword(ManageAccount account);


	List<ManageAccountDto> getAccountsByRolePk(@Param(value="rolePk") String rolePk);
	
}
