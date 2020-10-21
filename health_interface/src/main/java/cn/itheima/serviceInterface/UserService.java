package cn.itheima.serviceInterface;

import cn.itheima.pojo.User;

public interface UserService {
    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    User queryUserByName(String username);
}
