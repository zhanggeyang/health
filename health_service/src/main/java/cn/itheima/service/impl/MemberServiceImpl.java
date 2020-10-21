package cn.itheima.service.impl;

import cn.itheima.serviceInterface.MemberService;
import cn.itheima.dao.MemberDao;
import cn.itheima.pojo.Member;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: MemberServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/8/5 20:01
 * @ Description: 用户实现类
 **/
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;

    /**
     * 查看用户是否为会员
     * @param telephone
     * @return
     */
    @Override
    public Member isMember(String telephone) {
        return memberDao.isMember(telephone);
    }

    /**
     * 处理用户手机登录
     * @param member
     */
    @Override
    public void doRegister(Member member) {
        memberDao.doRegister(member);
    }

    /**
     * 统计会员数量
     * @return
     */
    @Override
    public Map getMemberReport() {
        Map<String,List> map = new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            monthList.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        map.put("months",monthList);
        List<Integer> countList= new ArrayList<>();
        for (String month : monthList) {
             countList.add(memberDao.queryCountByMonth(month));
        }
        map.put("memberCount",countList);
        return map;
    }
}
