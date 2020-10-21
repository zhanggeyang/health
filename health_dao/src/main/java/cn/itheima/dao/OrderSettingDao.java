package cn.itheima.dao;

import cn.itheima.pojo.Order;
import cn.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    /**
     * 查询是否有相同预约记录
     * @param orderDate
     * @return
     */
    int queryRecord(Date orderDate);

    /**
     * 更新预约记录
     * @param orderSetting
     */
    void updateRecord(OrderSetting orderSetting);

    /**
     * 添加预约记录
     * @param orderSetting
     */
    void addRecord(OrderSetting orderSetting);

    /**
     * 根据月份查询当月预约信息
     * @param data
     * @return
     */
    List<OrderSetting> queryOrderInfo(String data);

    /**
     * 根据日期查看是否可以预约
     * @param orderDate
     * @return
     */
    OrderSetting isOrderByDate(String orderDate);

    /**
     * 更新预约数量
     * @param orderSetting
     */
    void updateOrderCount(OrderSetting orderSetting);

}
