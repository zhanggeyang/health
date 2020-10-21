package cn.itheima.dao;

import cn.itheima.pojo.User;

public interface UserDao {
    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    User queryUserByName(String username);

}
