package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.Result;
import com.heima.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/28 17:59<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    /**
     * 会员数量折线图
     * @author: lwl
     * @date: 2020/9/28
     * @param null:
     * @return:
     */

    @Reference
    private MemberService memberService;
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        //构建12个月的数据
        Calendar calendar =Calendar.getInstance();
        //使用Calendar对日期数据操作，对年-1，循环12次
        calendar.add(Calendar.YEAR,-1);
        //构建月
        List<String> months =new ArrayList<String>(12);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        //循环
        for (int i = 0; i < 12; i++) {
            //每次+1月
            calendar.add(Calendar.MONTH,1);
            //只需要年和月
            Date date=calendar.getTime();
            //得到2020-1
            String monthDate = sdf.format(date);
            months.add(monthDate);
        }
        //调用服务逻辑查询
        List<Integer> memberCount =memberService.getMemberReport(months);
        //把查询到的日期年月份、会员数量放入map
        Map<String,Object> resultMap =new HashMap<String,Object>();
        resultMap.put("months",months);
        resultMap.put("memberCount",memberCount);
        //返回前端
        return  new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);
    }

//    @GetMapping("/getSetmealReport")

}
