package cn.itheima.dao;

import cn.itheima.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    /**
     * 设置套餐与检查组的关联关系的公共方法
     * @param map
     */
    void setAssociationCheckGroupAndSetMeal(Map<String, Integer> map);

    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 套餐分页
     * @param queryString
     * @return
     */
    Page<Setmeal> selectByCondition(String queryString);

    /**
     * 查询移动端套餐列表
     * @return
     */
    List<Setmeal> findAllSetMeal();


    /**
     * 根据ID查询套餐展示到移动端页面
     * @return
     */
    Setmeal findSetMealById(Integer id);

    /**
     * 根据套餐id查询对应的检查组
     * @param id
     * @return
     */
    List<Integer> findCheckGroupBySetmealId(Integer id);

    /**
     * 根据套餐id清除与检查组的关联关系
     * @param id
     */
    void deleteAssociationBySetmeaId(Integer id);

    /**
     * 根据套餐id删除对应套餐
     * @param id
     */
    void deleteSetmealById(Integer id);

    /**
     * 查询已经预约的套餐名和对应的数量
     * @return
     */
    List<Map<String,Object>> queryOrderedSetmealCountAndName();

}
