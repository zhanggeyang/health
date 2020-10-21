package cn.itheima.dao;

import cn.itheima.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    /**
     * 根据角色信息查询权限信息
     * @param id
     * @return
     */
    Set<Permission> queryPremissionByRoleID(Integer id);
}
