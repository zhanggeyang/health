package cn.itheima.security;

import cn.itheima.pojo.Permission;
import cn.itheima.pojo.Role;
import cn.itheima.pojo.User;
import cn.itheima.serviceInterface.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.security
 * @ ClassName: SecurityManager
 * @ Author: 张戈扬
 * @ Date: 2019/8/7 15:46
 * @ Description: 权限控制类
 **/
@Component
public class SecurityManager implements UserDetailsService {
    /**
     * 用户权限控制
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据username查询用户，不存在则返回null
        User user = userService.queryUserByName(username);
        if (user == null) {
            return null;
        }
        //不为空进行授权，一个用户可能对应多个权限，用集合封装
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        //查询权限，进行封装
        //获取当前用户的角色信息
        Set<Role> roleSet = user.getRoles();
        //对角色集合进行遍历
        for (Role role : roleSet) {
            Set<Permission> permissionSet = role.getPermissions();
            //对权限信息进行遍历
            for (Permission permission : permissionSet) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        //创建UserDetails对象返回
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorityList);
        return userDetails;

    }
}
