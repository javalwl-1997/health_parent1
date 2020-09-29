package com.heima.service;

import com.heima.health.HealthException;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.pojo.CheckGroup;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/21 15:33
 * 4
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @params
     * @return
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);
    /**
     * 分页查询
     * @params
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();

    void deleteById(int id) throws HealthException;
}
