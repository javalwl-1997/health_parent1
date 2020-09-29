package com.heima.health.controller;

import com.heima.health.constant.MessageConstant;
import com.heima.health.constant.RedisMessageConstant;
import com.heima.health.entity.Result;
import com.heima.health.utils.SMSUtils;
import com.heima.health.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/25 17:54<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/validateCode")
    /**
     * 预约的验证码
     * @author: lwl
     * @date: 2020/9/25
     * @param null:
     * @return:
     */
public class ValidateCodeController {
    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);
    @Autowired
    private JedisPool jedisPool;
    @PostMapping("/send4Order")
    /**
     * 预约提交
     * @author: lwl
     * @date: 2020/9/25
     * @param telephone:
     * @return: com.heima.health.entity.Result
     */
    public Result send4Order(String telephone){
        //获取redis连接池
        Jedis jedis = jedisPool.getResource();
        //判断是否已发送，通过key,获取redis中的验证码
       String key= RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone;
        String codeInRedis = jedis.get(key);
        //存在，已发送
        if (!StringUtils.isEmpty(codeInRedis)){
            return  new Result(false,"验证码已发送，注意查收！");
        }

        //未发送,生成验证码
        String validateCode = ValidateCodeUtils.generateValidateCode(6) + "";

        try {
            //调用smsUtils发送
            log.debug("给手机号码:{} 发送验证码:{}",telephone,validateCode);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            log.debug("给手机号码:{} 发送验证码:{}",telephone,validateCode);
            //把验证码存入redis中,设置有效时间为10分钟
            jedis.setex(key,60*10,validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
//            e.printStackTrace();
            log.debug(String.format("给手机号码:%s 发送验证码:%s 发送失败",telephone,validateCode),e);
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }finally {
            jedis.close();
        }


    }

}
