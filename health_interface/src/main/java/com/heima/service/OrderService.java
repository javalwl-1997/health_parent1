package com.heima.service;

import com.heima.health.HealthException;
import com.heima.pojo.Order;

import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/28 19:53<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
public interface OrderService {
    Order submit(Map<String, String> orderInfo)throws HealthException;

    Map<String, String> findById(int id);
}
