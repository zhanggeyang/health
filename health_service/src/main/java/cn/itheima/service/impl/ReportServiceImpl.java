package cn.itheima.service.impl;

import cn.itheima.serviceInterface.MemberService;
import cn.itheima.serviceInterface.ReportService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: ReportServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/8/8 16:26
 * @ Description: 统计会员实现类
 **/
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService{
    @Autowired
    private MemberService reportService;
    @Override
    public Map getMemberReport() {

        return null;
    }
}
