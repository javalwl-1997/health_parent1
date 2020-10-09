package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.Result;
import com.heima.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/10/9 14:37<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;
    /**
     * 饼状图：根据会员性别统计占比
     * @author: lwl
     * @date: 2020/10/9
     * @param :
     * @return: com.itheima.health.entity.Result
     */
    @GetMapping("/getMemberReportBySex")
    public Result getMemberReportBySex(){
        List<Map<String,Object>> resultList =memberService.getMemberReportBySex();
        List<String> huiyuanSex =new ArrayList<String>();
        for (Map<String,Object> map:resultList){
            String name =map.get("name")+"";
            huiyuanSex.add(name);
        }
        Map resultMap =new HashMap();
        resultMap.put("huiyuanSex",huiyuanSex);
        resultMap.put("huiyuanCount",resultList);
        return  new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);
    }

    /**
     *饼状图：根据会员年龄统计占比
     * @author: lwl
     * @date: 2020/10/9
     * @param :
     * @return: com.itheima.health.entity.Result
     */
    @GetMapping("/getMemberAge")
    public Result getMemberAge() {
        Map<String, Object> resultMap = memberService.getMemberAge();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
    }
}
