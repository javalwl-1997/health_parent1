package com.heima.dao;

import com.github.pagehelper.Page;
import com.heima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/21 15:40
 * 4
 */
public interface CheckGroupDao {
    /**
     * 添加检查组
     * @params
     * @return void
     */
    void add(CheckGroup checkGroup);
    /**
     * 添加检查组于检查项的关系
     * 参数类型相同时，要取别名，或者放入map
     * @params
     * @return
     */
    void  addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);
    /**
     * 条件查询
     * @params
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void update(CheckGroup checkGroup);

    void deleteGroupCheckItem(Integer id);

    List<CheckGroup> findAll();

    int findSetmealCountByCheckGroupId(int id);

    void deleteById(int id);
}
