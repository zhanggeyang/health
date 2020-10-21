package cn.itheima.service.impl;

import cn.itheima.serviceInterface.OrderSettingService;
import cn.itheima.dao.OrderSettingDao;
import cn.itheima.pojo.OrderSetting;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: OrderSettingServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/8/1 16:10
 * @ Description: 预约实现类
 **/
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 上传附件
     * @param orderSettingList
     */
    @Override
    public void upload(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                //判断数据库是否存在此记录
                publicMethod(orderSetting);
            }
        }
    }

    private void publicMethod(OrderSetting orderSetting) {
        int row = orderSettingDao.queryRecord(orderSetting.getOrderDate());
        if (row > 0) {
            //更新记录
            orderSettingDao.updateRecord(orderSetting);
        } else {
            //调用dao层方法往数据库写入记录
            orderSettingDao.addRecord(orderSetting);
        }
    }

    /**
     * 根据月份查询当月预约信息
     * @param data
     * @return
     */
    @Override
    public List<Map> getOrderSettingByMonth(String data) {
        if (Integer.parseInt(data.split("-")[1]) < 10) {
            data = data.split("-")[0] + "-" + "0" + data.split("-")[1];
        }
        //返回list集合
        List<OrderSetting> orderList = orderSettingDao.queryOrderInfo(data);
        for (OrderSetting orderSetting : orderList) {
            System.out.println(orderSetting);
        }
        //转化为List<Map>返回
        List<Map> newOrderList = new ArrayList<>();
        for (OrderSetting order : orderList) {
            Map map = new HashMap();
            map.put("date", order.getOrderDate().getDate());
            map.put("number", order.getNumber());
            map.put("reservations", order.getReservations());
            newOrderList.add(map);
        }
        return newOrderList;
    }

    /**
     * 更改某日预约人数
     * @param orderSetting
     */
    @Override
    public void editOrderByDate(OrderSetting orderSetting) {
        this.publicMethod(orderSetting);
    }
}
