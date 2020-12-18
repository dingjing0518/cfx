package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.ActGroupDto;
import cn.cf.model.ActGroup;

public interface GroupService {
    /**
     * 账户管理列表
     * 
     * @param qm
     * @return
     */
    PageModel<ActGroupDto> searchManageAccountList(QueryModel<ActGroupDto> qm);

    /**
     * 删除角色
     * 
     * @param actGroup
     * @return
     * @throws Exception 
     */
    int deleteActGroup(ActGroup actGroup) throws Exception;

    /**
     * 更新/编辑角色
     * 
     * @param actGroup
     * @return
     */
    int updateActGroup(ActGroup actGroup);

    /**
     * 查找角色
     * 
     * @param actGroup
     * @return
     */
    List<ActGroup> findActGroup(ActGroup actGroup);

    /**
     * 根据userId查找角色
     * 
     * @param id
     * @return
     */
    List<ActGroupDto> findByUserId(String id);

    /**
     * 获取全部角色
     * 
     * @return
     */
    List<ActGroupDto> getAllGroupList();

    /**
     * 金融：编辑/新增账户
     * 
     * @param map
     * @return
     */
    int updateActUserGroup(Map<String, Object> map);

    /**
     * 金融：删除账户
     * 
     * @param map
     * @return
     */
    int deleteActListGroup(Map<String, Object> map);

}
