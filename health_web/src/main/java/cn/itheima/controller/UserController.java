package cn.itheima.controller;

import cn.itheima.constant.MessageConstant;
import cn.itheima.entity.Result;
import cn.itheima.serviceInterface.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.controller
 * @ ClassName: UserController
 * @ Author: 张戈扬
 * @ Date: 2019/8/7 20:43
 * @ Description: 管理员控制层
 **/
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping(value = "/queryUser",method = RequestMethod.GET)
    public Result queryUser(){
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
