package cn.itheima.serviceInterface;

import cn.itheima.entity.PageResult;
import cn.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    /**
     * 新增检查套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, List<Integer> checkgroupIds);

    /**
     * 套餐分页的方法
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPgae(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 移动端查询所有套餐
     * @return
     */
    List<Setmeal> findAllSetMeal();

    /**
     * 根据id查询套餐展示到移动端页面
     * @param id
     * @return
     */
    Setmeal findSetMealById(Integer id);

    /**
     * 根据套餐ID查询对应的检查组信息
     * @return
     * @param id
     */
    List<Integer> findCheckGroupBySetmealId(Integer id);

    /**
     * 删除套餐
     * @param id
     */
    void delete(Integer id);

    /**
     * 汇总套餐数量，制作套餐报表
     * @return
     */
    Map getSetmealReport();
}
