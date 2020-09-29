package com.heima.dao;

import com.github.pagehelper.Page;
import com.heima.pojo.CheckItem;
import com.sun.xml.internal.ws.handler.HandlerException;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/18 19:27
 * 4
 */
public interface CheckItemDao {
    /**
 * 查询 所有检查项
 * @return
 */
    List<CheckItem> findAll();
    void add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);
    void deleteById(int id);

    int findCountByCheckItemId(int id);

    CheckItem findById(int id);

    void update(CheckItem checkItem);
}
