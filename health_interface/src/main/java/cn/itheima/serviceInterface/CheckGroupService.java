package cn.itheima.serviceInterface;

import cn.itheima.entity.PageResult;
import cn.itheima.pojo.CheckGroup;
import cn.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    /**
     * 检查所有的检查组
     * @return
     */
    List<CheckItem> findAllCheckItem();

    /**
     * 添加检查项和更新中间表
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, List<Integer> checkitemIds);

    /**
     * 检查组分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 查询当前选项卡对应的检查组信息
     * @param id
     * @return
     */
    CheckGroup findCGById(Integer id);

    /**
     * 查询当前选项卡检查组对应的所有的检查项信息
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组和中间表的关联关系
     * @param id
     */
    void delete(Integer id);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAllCheckGroup();

}
