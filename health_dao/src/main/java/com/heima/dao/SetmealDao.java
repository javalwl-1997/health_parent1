package com.heima.dao;

import com.github.pagehelper.Page;
import com.heima.pojo.CheckGroup;
import com.heima.pojo.CheckItem;
import com.heima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/22 11:06
 * 4
 */
public interface SetmealDao {

    Page<Setmeal> findByCondition(String queryString);

    void add(Setmeal setmeal);

    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    List<Integer> findCheckGroupIdsBySetmealId(int id);

    Setmeal findById(int id);

    void update(Setmeal setmeal);

    void deleteSetmealCheckGroup(Integer id);

    int findCountBySetmealId(int id);

    void deleteById(int id);

    List<String> findImages();

    List<Setmeal> findAll();

    Setmeal findDetailById(int id);

    Setmeal findDetailById2(int id);

    List<CheckGroup> findCheckGroupBySetmealId(int id);

    List<CheckItem> findCheckItemsByCheckGroupId(Integer id);
}
