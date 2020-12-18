package cn.cf.service.logistics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.LgMenuDto;
import cn.cf.dto.LgRoleMenuDto;
import cn.cf.dto.LgRoleTypeDto;
import cn.cf.model.LgRoleType;

public interface LgRoleTypeService {

    /**
     * 角色类型管理列表
     * 
     * @param qm
     * @return
     */
    PageModel<LgRoleTypeDto> searchLgRoleTypeGrid(QueryModel<LgRoleTypeDto> qm);

    /**
     * 根据rolepk查询该角色有哪些权限
     * 
     * @param rolePk
     * @return
     */
    List<LgRoleMenuDto> getLgRoleMenuByRolepk(String rolePk);

    /**
     * @param id
     * @param companyType
     * @return
     */
    List<LgMenuDto> getLgMenuByParentPk(String id, Integer companyType);

    /**
     * 检查角色名称是否已经存在
     * 
     * @param name
     * @return
     */
    LgRoleTypeDto getLgRoleByName(String name);

    /**
     * 保存角色树
     * 
     * @param role
     * @param node
     * @return
     */
    String saveLgRole(LgRoleType role, String node);

}
