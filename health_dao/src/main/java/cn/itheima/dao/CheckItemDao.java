package cn.itheima.dao;

import cn.itheima.pojo.CheckItem;
import com.github.pagehelper.Page;

public interface CheckItemDao {
    /**
     * 根据条件查询检查项
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 删除检查项
     * @param checkItem
     * @return
     */
    int delete(CheckItem checkItem);

    /**
     * 查询检查项与检查组
     * @param id
     * @return
     */
    long findCountByCheckItemId(Integer id);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 回显数据
     * @param id
     * @return
     */
    CheckItem findByID(Integer id);

    /**
     * 编辑数据
     * @param checkItem
     */
    void edit(CheckItem checkItem);

}