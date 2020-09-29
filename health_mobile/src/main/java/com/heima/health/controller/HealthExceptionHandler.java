package com.heima.health.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.heima.health.HealthException;
import com.heima.health.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/21 10:56
 * 4
 */
@RestControllerAdvice
public class HealthExceptionHandler {
    //log日志输出异常
    private static final Logger log = LoggerFactory.getLogger(HealthExceptionHandler.class);
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e){
        log.error("违反业务逻辑",e);
        //抛出自定义异常
        return new Result(false,e.getMessage());
    }
    /**
     * 未知的系统异常
     * @params
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleHealthException(Exception e){
        log.error("发生异常",e);
        return new Result(false,"系统繁忙，稍后再试....");
    }
}
