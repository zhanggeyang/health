package cn.itheima.controller;

import cn.itheima.serviceInterface.CheckItemService;
import cn.itheima.constant.MessageConstant;
import cn.itheima.entity.PageResult;
import cn.itheima.entity.QueryPageBean;
import cn.itheima.entity.Result;
import cn.itheima.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ ProjectName: health_parent
 * @ PackageName: cn.itheima.controller
 * @ ClassName: CheckItemController
 * @ Author: 张戈扬
 * @ Date: 2019/7/28 16:09
 * @ Description:
 **/
@RestController
@RequestMapping(value = "/checkItem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 检查项分页
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    public Result delete (Integer id) {
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 回显数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/findByID", method = RequestMethod.GET)
    public Result findByID (Integer id) {
        try {
            CheckItem checkItem = checkItemService.findByID(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

}
