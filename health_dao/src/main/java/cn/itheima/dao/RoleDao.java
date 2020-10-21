package cn.itheima.dao;

import cn.itheima.pojo.Role;

import java.util.Set;

public interface RoleDao {
    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    Set<Role> queryRolesByUID(Integer id);
}
