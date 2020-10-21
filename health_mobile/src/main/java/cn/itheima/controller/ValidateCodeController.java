package cn.itheima.controller;

import cn.itheima.constant.MessageConstant;
import cn.itheima.constant.RedisConstant;
import cn.itheima.constant.RedisMessageConstant;
import cn.itheima.entity.Result;
import cn.itheima.utils.SMSUtils;
import cn.itheima.utils.ValidateCodeUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.controller
 * @ ClassName: ValidateCodeController
 * @ Author: 张戈扬
 * @ Date: 2019/8/4 15:40
 * @ Description: 验证码控制层
 **/
@RestController
@RequestMapping(value = "/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/validate4Order")
    public Result Validate4Order(String telephone) {
        String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        //给用户发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
            System.out.println("发送的验证码为" + validateCode);
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER + telephone, 3 * 60, validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping(value = "send4Login",method = RequestMethod.POST)
    public Result Validate4Login(String telephone){
        String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        try {
            System.out.println("发送的验证码为" + validateCode);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN + telephone,5*60,validateCode);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
