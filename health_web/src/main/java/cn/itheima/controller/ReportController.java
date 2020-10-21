package cn.itheima.controller;

import cn.itheima.constant.MessageConstant;
import cn.itheima.entity.Result;
import cn.itheima.serviceInterface.MemberService;
import cn.itheima.serviceInterface.ReportService;
import cn.itheima.serviceInterface.SetMealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.controller
 * @ ClassName: ReportController
 * @ Author: 张戈扬
 * @ Date: 2019/8/8 16:18
 * @ Description: 统计控制类
 **/
@RestController
@RequestMapping(value = "/report")
public class ReportController {
    @Reference
    private SetMealService setMealService;
    @Reference
    private MemberService memberService;

    @RequestMapping(value = "/getSetmealReport", method = RequestMethod.GET)
    public Result getSetmealReport() {
        try {
            Map map = setMealService.getSetmealReport();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/getMemberReport",method = RequestMethod.GET)
    public Result getMemberReport() {
        try {
            Map map = memberService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }
}
