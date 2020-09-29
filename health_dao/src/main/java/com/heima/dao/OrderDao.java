package com.heima.dao;

import com.heima.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/25 20:51<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
public interface OrderDao {
    //预约订单查询
    List<Order> findByCondition(Order order);
    //添加预约订单
    void add(Order order);
    //通过id查询订单详情
    Map findById4Detail(Integer id);
}
