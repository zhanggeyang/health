package cn.itheima.controller;

import cn.itheima.serviceInterface.OrderSettingService;
import cn.itheima.constant.MessageConstant;
import cn.itheima.entity.Result;
import cn.itheima.pojo.OrderSetting;
import cn.itheima.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.controller
 * @ ClassName: OrderSettingController
 * @ Author: 张戈扬
 * @ Date: 2019/8/1 15:50
 * @ Description: 预约
 **/
@RestController
@RequestMapping(value = "/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 上传预约表单
     * @param excelFile
     * @return
     */
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            List<String[]> rowList = POIUtils.readExcel(excelFile);
            if (rowList != null && rowList.size() > 0) {
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] row : rowList) {
                    OrderSetting orderSetting = new OrderSetting(new Date(row[0]), Integer.parseInt(row[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.upload(orderSettingList);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据月份获取该月的预约表
     * @param data
     * @return
     */
    @RequestMapping(value = "/getOrderSettingByMonth",method = RequestMethod.POST)
    public Result getOrderSettingByMonth(String data){
        try {
           List<Map> orderList = orderSettingService.getOrderSettingByMonth(data);
           return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/editOrderByDate",method = RequestMethod.POST)
    public Result editOrderByDate(@RequestBody OrderSetting orderSetting){
        //调用service方法进行修改
        try {
            orderSettingService.editOrderByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
