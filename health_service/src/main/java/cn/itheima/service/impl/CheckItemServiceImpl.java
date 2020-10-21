package cn.itheima.service.impl;

import cn.itheima.serviceInterface.CheckItemService;
import cn.itheima.dao.CheckItemDao;
import cn.itheima.entity.PageResult;
import cn.itheima.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.service.impl
 * @ ClassName: CheckItemServiceImpl
 * @ Author: 张戈扬
 * @ Date: 2019/7/28 16:33
 * @ Description:
 **/
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询检查项分页的方法
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> checkItems = checkItemDao.selectByCondition(queryString);
        return new PageResult(checkItems.getTotal(), checkItems.getResult());
    }

    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        try {
            checkItemDao.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    /**
     * 数据回显
     * @param id
     */
    @Override
    public CheckItem findByID(Integer id) {
        return checkItemDao.findByID(id);
    }

    /**
     * 编辑数据
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }
}
