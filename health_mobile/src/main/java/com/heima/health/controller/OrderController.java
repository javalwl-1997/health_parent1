package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.constant.MessageConstant;
import com.heima.health.constant.RedisMessageConstant;
import com.heima.health.entity.Result;
import com.heima.pojo.Order;
import com.heima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/25 19:33<br/>
 * @author Administrator<br />
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired JedisPool jedisPool;
    @PostMapping("/submit")
    /**
     * 订单预约
     * @author: lwl
     * @date: 2020/9/26
     * @param orderInfo:
     * @return: com.heima.health.entity.Result
     */
    public Result submit(@RequestBody Map<String,String> orderInfo){
        //验证校验
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + orderInfo.get("telephone");

        //前端输入的验证码和redis中的验证码是否一致
        Jedis jedis = jedisPool.getResource();
        //1.先从redis中取出验证码和手机号码
        String codeInRedis = jedis.get(key);
        //2.redis中的验证码过期或者没有，提示用户重新获取验证码
        if (StringUtils.isEmpty(codeInRedis)){
            return  new Result(true,"请重新获取验证码！");
        }
        //3.redis中有值，就比较前端输入的和redis中的是否相同
        if (!codeInRedis.equals(orderInfo.get("validateCode"))){
            //不一样，则返回错误，提示验证码输入有误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //防止重复提交，删除验证码
        jedis.del(key);
        //一样，则执行服务层的方法预约,将预约方式返回给前端
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        Order order=orderService.submit(orderInfo);
        //返回订单页面
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }



    @GetMapping("/findById")
    /**
     * 通过id查询订单详情
     * @author: lwl
     * @date: 2020/9/26
     * @param id:
     * @return: com.heima.health.entity.Result
     */
    public  Result findById(int id){
        Map<String,String> orderInfo =orderService.findById(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);

    }

}
