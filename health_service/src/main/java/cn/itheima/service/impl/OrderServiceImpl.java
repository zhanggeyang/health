package cn.itheima.service.impl;

import cn.itheima.serviceInterface.OrderService;
import cn.itheima.constant.MessageConstant;
import cn.itheima.dao.MemberDao;
import cn.itheima.dao.OrderDao;
import cn.itheima.dao.OrderSettingDao;
import cn.itheima.entity.Result;
import cn.itheima.pojo.Member;
import cn.itheima.pojo.Order;
import cn.itheima.pojo.OrderSetting;
import cn.itheima.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: OrderServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/8/4 16:53
 * @ Description: 预约实现类
 **/
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public Result doOrder(Map map) throws Exception {
        //查询当前日期是否可约
        String orderDate = (String) map.get("orderDate");
        System.out.println(orderDate);
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
        //查询当前日期是否可约
        OrderSetting orderSetting = orderSettingDao.isOrderByDate(orderDate);
        String telephone = (String) map.get("telephone");
        if (orderSetting == null) {
            //当前日期不可约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //查询当前日期是否约满
        int reservations = orderSetting.getReservations();
        int number = orderSetting.getNumber();
        if (reservations >= number) {
            //约满
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //检查用户是否为会员，不是则注册并预约，是则完成预约
        Member member = memberDao.isMember(telephone);
        if (member == null) {
            //不是会员注册并预约
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.doRegister(member);
        } else {
            //是会员预约，是否重复预约
            Integer memberId = member.getId();
            Order order = new Order(memberId, DateUtils.parseString2Date(orderDate), null, null, setmealId);
            List<Order> orderRecord = orderDao.isRepeatOrder(order);
            if (orderRecord != null && orderRecord.size() > 0) {
                //已经预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //更新当日预约数量
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.updateOrderCount(orderSetting);

        //保存预约信息
        Order order = new Order(member.getId(),DateUtils.parseString2Date(orderDate), (String) map.get("OrderType"),Order.ORDERSTATUS_NO,setmealId);
        orderDao.saveOrderInfo(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 预约成功后，查询预约详情
     * @param id
     * @return
     */
    @Override
    public Map doOrderSuccess(Integer id) {
        Map map = orderDao.doOrderSuccess(id);
        return map;
    }
}
