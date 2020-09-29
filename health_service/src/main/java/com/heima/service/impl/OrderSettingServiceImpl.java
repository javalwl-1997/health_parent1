package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.dao.OrderSettingDao;
import com.heima.health.HealthException;
import com.heima.pojo.OrderSetting;
import com.heima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl  implements OrderSettingService {
    //dao层接口
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    @Transactional
    /**
     * 批量导入预约数量
     * @author: lwl
     * @date: 2020/9/24
     * @param list:
     * @return: void
     */
    public void add(List<OrderSetting> list) throws HealthException {
        //遍历预约集合
        if (null!=list && list.size()>0){
            for (OrderSetting orderSetting: list) {
                //通过日期查询预约设置信息
                OrderSetting ods = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //存在
                if (null != ods) {
                    //判断已预约数量是否大于要设置的最大预约数量
                    if (orderSetting.getNumber() < ods.getReservations()) {
                        //是，提示报错
                        throw new HealthException("最大预约数量不能小于已预约数量");
                    }
                        //否，通过日期更新最大的预约数
                        orderSettingDao.updateNumber(orderSetting);
                }else {
                    //不存在
                    //插入预约数量
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    /**
     * 通过月份获取预约设置
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        //拼接月份日期
        String startDate =month +"-01";
        String endDate =month +"-31";
     List<Map<String, Integer>> monthDate= orderSettingDao.getOrderSettingBetween(startDate,endDate);
        return monthDate;
    }
    /**
     * 基于日期的预约设置
     * @author: lwl
     * @date: 2020/9/24
     * @param
     * @return:
     */
    @Override
    public void eidtNumberByDate(OrderSetting orderSetting) throws HealthException {
        //通过日期查询预约数据
        OrderSetting byOrderDate = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //判断是否存在
        if (null != byOrderDate) {
            //判断已预约数量是否大于要设置的最大预约数量
            if (orderSetting.getNumber() < byOrderDate.getReservations()) {
                //是，提示报错
                throw new HealthException("最大预约数量不能小于已预约数量");
            }
            //否，通过日期更新最大的预约数
            orderSettingDao.updateNumber(orderSetting);
        }else {
            //不存在
            //插入预约数量
            orderSettingDao.add(orderSetting);
        }

    }

}
