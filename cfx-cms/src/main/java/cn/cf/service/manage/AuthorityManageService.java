/**
 * 
 */
package cn.cf.service.manage;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.entity.BtnTree;
import cn.cf.model.ManageAccount;
import cn.cf.model.ManageRole;

/**
 * @author bin
 */
public interface AuthorityManageService {
    /**
     * 根据用户登录名查询用户信息
     * 
     * @param username
     * @return
     */
    ManageAccountExtDto getManageAccountByLoginName(String username);

    /**
     * 查询显示权限按钮方法，查询出的列表为权限表允许显示的链接及按钮
     * 
     * @param parentPk
     * @param rolePk
     * @param type
     * @param inCouldeBtn
     * @return
     */
    List<ManageAuthorityDto> getManageAuthorityByAccount(String parentPk, String rolePk, int type, int inCouldeBtn, String accout);

    /**
     * @param rolePk
     * @param btnName
     * @return
     */
    List<ManageAuthorityDto> getBtnRoleList(String rolePk, String btnName);

    /**
     * 递归所有下级目录
     * 
     * @param partentPk
     * @return
     */
    List<BtnTree> getBtnByPartentPk(String partentPk, String rolePk);

    /**
     * 根据角色Pk获取角色信息
     * 
     * @param rolePk
     * @return
     */
    ManageRoleDto getManageRoleByPk(String rolePk);

    /**
     * 根据条件查询角色列表
     * 
     * @param qm
     * @return
     */
    PageModel<ManageRoleDto> searchRoleGrid(QueryModel<ManageRoleDto> qm);

    /**
     * 根据名字查询token
     * 
     * @param dto
     * @return
     */
    B2bTokenDto searchB2bTokenByName(B2bTokenExtDto dto);

    /**
     * 编辑token
     * 
     * @param dto
     * @return
     */
    int updateB2bToken(B2bTokenExtDto dto);

    /**
     * 禁用/启用，删除token
     * 
     * @param pk
     * @param isVisable
     * @param isDelete
     * @return
     */
    int updateB2bTokenStatus(String pk, Integer isVisable, Integer isDelete);

    /**
     * 根据角色查询权限
     * 
     * @param rolePk
     * @return
     */
    List<ManageAuthorityDto> getManageAuthorityByRolePk(String rolePk);

    /**
     * 根据角色查询角色
     * 
     * @param name
     * @return
     */
    List<ManageRoleDto> getManageRoleByName(String name);

    /**
     * 编辑角色
     * 
     * @param role
     * @param node
     * @return
     */
    int updateManaegRole(ManageRole role, String node);

    /**
     * 修改密码
     * 
     * @param account
     * @return
     */
    String updatePassword(ManageAccount account);

    /**
     * 查询权限和角色中间表，判断登陆用户是否配置权限
     * @param rolePk
     * @return
     */
    int isAuthority(String rolePk);

    /**
     * 查询所有isBtn为3的权限名称
     * @return
     */
    List<String> getAuthManageIsBtnToThree();


    /**
     * 根据条件查询系统配置列表
     *
     * @param qm
     * @return
     */
    PageModel<SysPropertyDto> searchSysPropertyGrid(QueryModel<SysPropertyDto> qm);

    List<SysPropertyDto> getSysProperty(Map<String,Object> map);

    SysPropertyDto getSysPropertyBypk(String pk);

    /**
     * 根据选择的类型不同查询不同表数据
     * 返回字段取相同别名，统一返回
     * @param type
     * @return
     */
    List<SysPropertyDto> getSysPropertyByType(Integer type,String productPk);

    /**
     * 更新系统配置
     * @param dto
     * @return
     */
    int updateSysPropertyByType(SysPropertyDto dto);

    /**
     * 删除系统配置
     * @param pk
     * @return
     */
    int delSysPropertyByPk(String pk);
}
