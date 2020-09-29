package com.heima.controller;

import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.sql.SQLOutput;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/29 15:03<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/user")
public class UserController {
    //
    @GetMapping("/getUsername")
    public Result getUsername(){
        //SecurityContextHolder Security 容器持有者
        System.out.println("****认证方式****"+SecurityContextHolder.getContext().getAuthentication().getName());
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("****使用者详情****"+userDetails.getUsername());
        return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,userDetails.getUsername());
    }
    @RequestMapping("/loginSuccess")
    public Result loginSuccess(){
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

    @RequestMapping("/loginFail")
    public Result loginFail(){
        return new Result(false, "用户名或密码不正确");
    }

}
