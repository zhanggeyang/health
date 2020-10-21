package cn.itheima.dao;

import cn.itheima.pojo.Member;
import cn.itheima.pojo.Order;
import cn.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    /**
     * 查询用户是否已经预约
     * @param order
     * @return
     */
    List<Order> isRepeatOrder(Order order);

    /**
     * 保存预约表
     * @param order
     */
    void saveOrderInfo(Order order);

    /**
     * 预约成功后查询详情
     * @param id
     * @return
     */
    Map doOrderSuccess(Integer id);
}
