package cn.itheima.serviceInterface;

import cn.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 上传Excel附件
     * @param orderSettingList
     */
    void upload(List<OrderSetting> orderSettingList);

    /**
     * 根据月份查询当月预约信息
     * @param data
     * @return
     */
    List<Map> getOrderSettingByMonth(String data);

    /**
     * 更改某日预约人数
     * @param orderSetting
     */
    void editOrderByDate(OrderSetting orderSetting);

}
