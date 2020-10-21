package cn.itheima.controller;

import cn.itheima.serviceInterface.OrderService;
import cn.itheima.constant.MessageConstant;
import cn.itheima.constant.RedisMessageConstant;
import cn.itheima.entity.Result;
import cn.itheima.pojo.Order;
import cn.itheima.utils.SMSUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import com.alibaba.druid.util.StringUtils;

import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.controller
 * @ ClassName: OrderController
 * @ Author: 张戈扬
 * @ Date: 2019/8/4 15:53
 * @ Description: 预约控制层
 **/
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "doOrder")
    public Result doOrder(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        //获取用户的验证码
        String validateCode = (String) map.get("validateCode");
        String orderDate = (String) map.get("orderDate");
        //获取Redis的验证码
        String jedisCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + telephone);
        //对比验证码
        Result result = null;
        try {
            if (!StringUtils.isEmpty(validateCode) && !StringUtils.isEmpty(jedisCode) && jedisCode.equals(validateCode)) {
                //一致则调用service进行其他信息验证
                map.put("OrderType", Order.ORDERTYPE_WEIXIN);
                result = orderService.doOrder(map);
                //先验证日期是否可以预约
                if (result.isFlag()) {
                    try {
                        SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
                        return new Result(true, MessageConstant.ORDER_SUCCESS, result.getData());
                    } catch (ClientException e) {
                        e.printStackTrace();
                        return new Result(false, MessageConstant.ORDER_FAIL);
                    }
                }
            } else {
                //不一致则返回错误
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, result.getMessage());
        /*String message = MessageConstant.ORDER_FAIL;
        if(!StringUtils.isEmpty(result.getMessage())){
            message= result.getMessage();
        }
        return new Result(false,message );//data最终要返回一些数据*/
    }

    /**
     * 预约成功后显示预约详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/orderSuccess",method = RequestMethod.POST)
    public Result doOrderSuccess(Integer id){
        try {
            Map map = orderService.doOrderSuccess(id);
            System.out.println(map);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
