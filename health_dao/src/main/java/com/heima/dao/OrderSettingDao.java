package com.heima.dao;

import com.heima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    /**
     * 通过日期查询预约信息
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    OrderSetting findByOrderDate(Date orderDate);
    /**
     * 更新日期最大预约数
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    void updateNumber(OrderSetting orderSetting);
    /**
     * 插入预约设置
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    void add(OrderSetting orderSetting);
    /**
     * 通过月份获取预约设置数据
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingBetween(@Param("startDate") String startDate,@Param("endDate") String endDate);
        /**
         *更新已预约人数
         * @author: lwl
         * @date: 2020/9/26
         * @param null:
         * @return:
         */

    int editReservationByOrderDate(OrderSetting orderSetting);
}
