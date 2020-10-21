package cn.itheima.service.impl;

import cn.itheima.serviceInterface.SetMealService;
import cn.itheima.dao.SetMealDao;
import cn.itheima.entity.PageResult;
import cn.itheima.pojo.Setmeal;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: SetMealServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/7/31 17:35
 * @ Description: 套餐接口的实现类
 **/
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;

    /**
     * 添加检查套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, List<Integer> checkgroupIds) {
        //添加检查套餐
        setMealDao.add(setmeal);
        //往中间表写入关联
        if (checkgroupIds != null && checkgroupIds.size() > 0) {
            this.setAssociation(setmeal, checkgroupIds);
        }
    }

    /**
     * 套餐页分页
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPgae(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setMealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 移动端查询所有套餐
     *
     * @return
     */
    @Override
    public List<Setmeal> findAllSetMeal() {
        List<Setmeal> setmealList = setMealDao.findAllSetMeal();
        return setmealList;
    }

    /**
     * 根据id查询套餐展示到移动端页面
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findSetMealById(Integer id) {
        Setmeal setmeal = setMealDao.findSetMealById(id);
        return setmeal;
    }

    /**
     * 根据套餐ID查找检查组ID
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupBySetmealId(Integer id) {
        List<Integer> integerList = setMealDao.findCheckGroupBySetmealId(id);
        return integerList;
    }

    /**
     * 删除套餐
     * @param id
     */
    @Override
    public void delete(Integer id) {
        setMealDao.deleteAssociationBySetmeaId(id);
        setMealDao.deleteSetmealById(id);
    }

    /**
     * 制作套餐报表
     *
     * @return
     */
    @Override
    public Map getSetmealReport() {
        //套餐名集合
        List<String> nameList = new ArrayList<>();
        //套餐数集合[{name:value},...]
        List<Map<String,Object>> countList = new ArrayList<>();
        //存放套餐数量
        Map<String, Object> map = new HashMap<>();
        countList = setMealDao.queryOrderedSetmealCountAndName();
        map.put("setmealCount",countList);
        for (Map<String, Object> countAndName : countList) {
            String setmealName = (String) countAndName.get("name");
            nameList.add(setmealName);
        }
        map.put("setmealNames",nameList);
        return map;
    }

    /**
     * 操作套餐表和检查组表的公共方法
     *
     * @param setmeal
     * @param checkgroupIds
     */
    public void setAssociation(Setmeal setmeal, List<Integer> checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmeal_Id", setmeal.getId());
            map.put("checkgroup_Id", checkgroupId);
            setMealDao.setAssociationCheckGroupAndSetMeal(map);
        }
    }
}
