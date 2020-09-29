package com.heima.service;

import com.heima.health.HealthException;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.pojo.CheckItem;
import com.sun.xml.internal.ws.handler.HandlerException;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/18 19:12
 * 4
 */
public interface CheckItemService {
    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();
    /**
     * 添加
     * @params
     * @return void
     */
    void add(CheckItem checkItem);
    /**
     * 分页查询
     * @params
     * @return com.heima.health.entity.PageResult<com.heima.pojo.CheckItem>
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
    /**
     * 通过id查询
     * @params
     * @return com.heima.pojo.CheckItem
     */
    CheckItem findById(int id);

    void deleteById(int id) throws HealthException;

    void update(CheckItem checkItem);
}
