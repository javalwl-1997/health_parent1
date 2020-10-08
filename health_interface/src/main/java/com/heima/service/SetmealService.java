package com.heima.service;

import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;

import com.heima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/22 10:58
 * 4
 */
public interface SetmealService {
    /**
     * 套餐分页查询
     * @params
     * @return com.heima.health.entity.PageResult<com.heima.pojo.Setmeal>
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 新增套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param null:
     * @return:
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);
    /**
     * 通过id查询检查组id集合
     * @author: lwl
     * @date: 2020/9/22
     * @param null:
     * @return:
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);
    /**
     * 通过id查询套餐信息
     * @author: lwl
     * @date: 2020/9/22
     * @param null:
     * @return:
     */
    Setmeal findById(int id);
    /**
     * 修改套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param null:
     * @return:
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);
    /**
     * 删除套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param null:
     * @return:
     */
    void deleteById(int id);
    /**
     * 获取套餐所有图片
     * @author: lwl
     * @date: 2020/9/22
     * @param null:
     * @return:
     */
    List<String> findImags();
    /**
     * 查询所有套餐
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    List<Setmeal> findAll();

    Setmeal findDetailById2(int id);

    Setmeal findDetailById(int id);

    Setmeal findDetailById3(int id);
    /**
     * 统计套餐个数
     * @author: lwl
     * @date: 2020/10/8
     * @param null:
     * @return:
     */
    List<Map<String, Object>> getSetmealReport();
}
