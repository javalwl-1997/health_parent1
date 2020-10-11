package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.Result;
import com.heima.service.MemberService;
import com.heima.service.ReportService;
import com.heima.service.SetmealService;
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
    @Reference
    private  SetmealService setmealService;
    @Reference
    private ReportService reportService;
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

    /**
     * 套餐预约占比
     * @author: lwl
     * @date: 2020/10/8
     * @param :
     * @return: com.heima.health.entity.Result
     */
    @GetMapping("/getSetmealReport")
    public  Result getSetmealReport(){
        //service统计套餐预约个数
        List<Map<String,Object>> reportData = setmealService.getSetmealReport();
        Map<String, Object> resultMap =new HashMap<String, Object>();
        //抽取套餐名称
        List<String> setmealNames=new ArrayList<String>();
        if (null!=reportData&&reportData.size()>0){
            for (Map<String, Object> data:reportData){
                setmealNames.add((String)data.get("name"));
            }
        }
        resultMap.put("setmealName",setmealNames);
        resultMap.put("setmealCount",reportData);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);

    }



    /**
     * @Title getBusinessReportData
     * 运营数据统计
     * @author lwl
     * @param
     * @return com.heima.health.entity.Result
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String,Object> businessReport =reportService.getBusinessReportData();
        return  new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,businessReport);

    }


}
