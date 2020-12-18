package cn.cf.dao;

import cn.cf.dto.B2bRoleMenuDto;
import cn.cf.model.B2bRoleMenu;

import java.util.List;

public interface B2bRoleMenuExDao {
    List<B2bRoleMenuDto> getRoleMenuByRolePk(String rolePk);
    void deleteByB2bRoleMenuPk(B2bRoleMenu menu);
}
