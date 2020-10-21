package cn.itheima.service.impl;

import cn.itheima.serviceInterface.CheckGroupService;
import cn.itheima.dao.CheckGroupDao;
import cn.itheima.entity.PageResult;
import cn.itheima.pojo.CheckGroup;
import cn.itheima.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: CheckGroupServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/7/29 15:37
 * @ Description: 检查组实现类
 **/
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    /************************************添加检查组***************************************/
    /**
     * 查询所有的检查组
     * @return
     */
    @Override
    public List<CheckItem> findAllCheckItem() {
        List<CheckItem> checkGroupList = checkGroupDao.findAllCheckItem();
        return checkGroupList;
    }
    /************************************添加检查组***************************************/
    /**
     * 添加检查项和更新中间表
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void add(CheckGroup checkGroup, List<Integer> checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        this.setAssociation(checkGroup.getId(),checkitemIds);
    }
    /************************************检查组分页***************************************/
    /**
     * 检查组分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> checkGroups = checkGroupDao.selectByCondition(queryString);
        return new PageResult(checkGroups.getTotal(),checkGroups.getResult());
    }

    /************************************编辑检查组***************************************/
    /**
     * 根据ID查询检查组，数据回显
     * @param id
     * @return
     */
    @Override
    public CheckGroup findCGById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findCGById(id);
        return checkGroup;
    }

    /**
     * 查询当前选项卡检查组对应的所有的检查项信息，数据回显
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> checkItemList = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        System.out.println(checkItemList);
        return checkItemList;
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociationByCGId(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setAssociation(checkGroup.getId(), Arrays.asList(checkitemIds));
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
    }

    /**
     * 删除检查组和中间表关系
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //先删除关联组
        checkGroupDao.deleteAssociationByCGId(id);
        checkGroupDao.deleteCheckGroupById(id);
    }

    /**
     * 查询所有的检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAllCheckGroup() {
        List<CheckGroup> checkGroupList = checkGroupDao.findAllCheckGroup();
        return checkGroupList;
    }

    /************************************操作检查组和检查项中间表的公共方法***************************************/
    /**
     * 更新检查组和检查项的公共方法
     * @param checkGroupID
     * @param checkitemIds
     */
    public void setAssociation(Integer checkGroupID, List<Integer> checkitemIds){
        if (checkitemIds != null && checkitemIds.size() > 0) {
            for (Integer checkitemID : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupID);
                map.put("checkitem_id",checkitemID);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
