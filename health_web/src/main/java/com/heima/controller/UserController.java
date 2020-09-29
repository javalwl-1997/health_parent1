package com.heima.controller;

import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUsername")
    public Result getUsername(){
        // SecurityContextHolder security容器的持有者
        // getContext 获取它的容器
        System.out.println("===获取认证==========" + SecurityContextHolder.getContext().getAuthentication().getName());
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("=======用户详情=====" + user.getUsername());
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
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
