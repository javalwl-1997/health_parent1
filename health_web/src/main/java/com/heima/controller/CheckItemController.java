package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.entity.Result;
import com.heima.pojo.CheckItem;
import com.heima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/18 19:08
 * 4
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    /**
     * 查询所有的检查项
     * @params
     * @return
     */
    @Reference
    private CheckItemService checkItemService;
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItemList = checkItemService.findAll();
        return  new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemList);
    }

    /**
     * 添加检查项
     * @params
     * @return void
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        //调用添加
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);

    }
    /**
     * 分页条件查询
     * @params
     * @return void
     */
    @PostMapping("/findPage")
    public  Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务分页查询
         PageResult <CheckItem> pageResult =checkItemService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }


    /**
     * 编辑修改检查项
     * @params
     * @return com.heima.health.entity.Result
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务通过id查询检查项信息
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }
    /**
     *修改编辑检查项
     * @params
     * @return
     */
    @PostMapping("/update")
    public  Result update(@RequestBody CheckItem  checkitem){
        checkItemService.update(checkitem);
        //返回操作结果
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }



    /**
     * 删除
     * @params
     * @return
     */
    @PostMapping("/deleteById")
    public  Result deleteById(int id){

        checkItemService.deleteById(id);
        return  new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
