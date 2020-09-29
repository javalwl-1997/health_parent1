package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.entity.Result;
import com.heima.pojo.CheckGroup;
import com.heima.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/21 15:29
 * 4
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    /**
     * 添加检查组
     * @params
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        //调用服务添加检查组
        checkGroupService.add(checkGroup,checkitemIds);
        //返回响应结果
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    /**
     * 检查组分页查询
     * @params
     * @return
     */
    @PostMapping("/findPage")
    public  Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 通过id查询
     * @params
     * @return
     */
    @GetMapping("/findById")
    public  Result findById(int id){
        //查询检查组信息
      CheckGroup checkGroup= checkGroupService.findById(id);
      //返回响应结果
        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);

    }
    /**
     *通过检查项id选中的检查项id集合
     * @params
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
     List<Integer> checkitems= checkGroupService.findCheckItemIdsByCheckGroupId(id);
     return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitems);
    }
    /**
     * 修改检查组
     * @params
     * @return
     */
    @PostMapping("/update")
    public  Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        //调用服务修改检查组
        checkGroupService.update(checkGroup,checkitemIds);
        //响应结果
        return  new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }
    /**
     * 查询所有
     * @params
     * @return
     */
    @GetMapping("/findAll")
    public  Result findAll(){
        List<CheckGroup> list =checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
    /**
     * 删除通过id删除
     * @params
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkGroupService.deleteById(id);
        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS);
    }

}
