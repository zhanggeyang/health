package cn.itheima.dao;

import cn.itheima.pojo.CheckGroup;
import cn.itheima.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckItem> findAllCheckItem();

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /************************************操作检查组和检查项中间表的公共方法***************************************/
    /**
     * 更新检查组和检查项中间表
     * @param map
     */
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    /**
     * 根据条件查找检查组
     * @param queryString
     * @return
     */
    Page<CheckGroup> selectByCondition(String queryString);

    /************************************编辑检查组***************************************/
    /**
     * 根据ID查询检查组
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
     * 清理旧关系
     * @param id
     */
    void deleteAssociationByCGId(Integer id);

    /**
     * 编辑检查组
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * 删除检查组
     * @param id
     */
    void deleteCheckGroupById(Integer id);

    /**
     * 检查所有的检查组
     * @return
     */
    List<CheckGroup> findAllCheckGroup();


}
