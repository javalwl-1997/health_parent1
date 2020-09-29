package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.dao.MemberDao;
import com.heima.dao.OrderDao;
import com.heima.dao.OrderSettingDao;
import com.heima.health.HealthException;
import com.heima.health.constant.MessageConstant;
import com.heima.pojo.Member;
import com.heima.pojo.Order;
import com.heima.pojo.OrderSetting;
import com.heima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/25 20:09<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@Service(interfaceClass = OrderService.class )
public class OrderServiceImpl implements OrderService {


    /**
     * 预约提交
     * @author: lwl
     * @date: 2020/9/25
     * @param orderInfo:
     * @return: com.heima.pojo.Order
     *
     */
    @Autowired
    //查询判断t_order是否存在重复预约，没有就添加
    private OrderDao orderDao;
    @Autowired
    //查询预约时间和修改预约订单数据
    private OrderSettingDao orderSettingDao;
    @Autowired
    //通过手机号查询会员信息，没有就通过预约添加
    private MemberDao memberDao;
    @Override
    @Transactional
    public Order submit(Map<String, String> orderInfo)throws  HealthException  {

        //1.通过日期查询预约设置信息
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //从前端获取预约日期
        Date oD=null;
        try {
            oD=sdf.parse(orderInfo.get("orderDate"));
        } catch (ParseException e) {
        // e.printStackTrace();
            throw new HealthException("日期格式不正确，请选择正确的日期");
        }
        OrderSetting orderSetting=orderSettingDao.findByOrderDate(oD);
        //如果日期不存在则提示报错
        if (null==orderSetting){
            throw new HealthException("所选日期不能预约,请选择其他日期");
        }
        //日期存在，但是预约额已满，提示报错
        if (orderSetting.getReservations()>orderSetting.getNumber()){
            throw new HealthException("当天的预约额已满，请选择其他日期");
        }
        //2.判断是否重复预约
        String telephone = orderInfo.get("telephone");
        //通过手机号，查询会员信息
        Member member= memberDao.findByTelephone(telephone);
        //会员信息存在，去查询是否重复预约
        Order order =new Order();
        order.setOrderDate(oD);
        order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));
        if (null!=member){
            order.setMemberId(member.getId());
            //判断是否重复预约
          List<Order>orderList = orderDao.findByCondition(order);
          if (null!=orderList && orderList.size()>0){
              throw  new HealthException("您已经预约过该套餐，请勿重复预约！");
          }
        }else {
            //会员信息不存在
            //添加会员信息
             member = new Member();
            //从前端获取会员信息
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            member.setIdCard(orderInfo.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setPassword("123456");
            member.setRemark("通过预约自动注册");
            //添加会员到数据库
            memberDao.add(member);
            order.setMemberId(member.getId());
        }
        //可预约订单
        //预约类型
        order.setOrderType(orderInfo.get("orderType"));
        //添加预约的状态
        order.setOrderStatus(Order.ORDERSTATUS_NO);
            orderDao.add(order);
        //预约成功，则更新预约数据+1
        int  rowCount= orderSettingDao.editReservationByOrderDate(orderSetting);
        if (rowCount==0){
            throw  new HealthException(MessageConstant.ORDER_FULL);
        }
        //返回新预约的订单对象
        return order;
    }

    @Override
    /**
     *通过id查询订单详情
     * @author: lwl
     * @date: 2020/9/26
     * @param id:
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> findById(int id) {
        return orderDao.findById4Detail(id);
    }

}
