package cn.itheima.service.impl;

import cn.itheima.dao.PermissionDao;
import cn.itheima.dao.RoleDao;
import cn.itheima.dao.UserDao;
import cn.itheima.pojo.Permission;
import cn.itheima.pojo.Role;
import cn.itheima.pojo.User;
import cn.itheima.serviceInterface.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: UserServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/8/7 16:26
 * @ Description: 用户服务实现类
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User queryUserByName(String username) {
        User user = userDao.queryUserByName(username);
        if (user != null) {
            //获取角色
            Set<Role> roleSet = roleDao.queryRolesByUID(user.getId());
            if (roleSet != null && roleSet.size() > 0) {
                for (Role role : roleSet) {
                    //获取权限
                    Set<Permission> permissionSet = permissionDao.queryPremissionByRoleID(role.getId());
                    //为角色授权
                    role.setPermissions(permissionSet);
                }
            }
            user.setRoles(roleSet);
        }
        return user;
    }
}
