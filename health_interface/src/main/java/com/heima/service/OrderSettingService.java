package com.heima.service;

import com.heima.health.HealthException;
import com.heima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     *批量导入预约数量
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    void add(List<OrderSetting> list) throws HealthException;

    /**
     * 通过月份获取预约设置
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
    /**
     * 基于日期的预约设置
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    void eidtNumberByDate(OrderSetting orderSetting) throws  HealthException;
}
