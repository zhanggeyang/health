package cn.itheima.serviceInterface;

import cn.itheima.entity.PageResult;
import cn.itheima.pojo.CheckItem;

public interface CheckItemService {
    /**
     * 根据条件查询检查项
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 删除检查项
     * @param id
     */
    void delete(Integer id);

    /**
     * 数据回显
     * @param id
     */
    CheckItem findByID(Integer id);

    /**
     * 编辑数据
     * @param checkItem
     */
    void edit(CheckItem checkItem);
}
