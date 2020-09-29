package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.Result;
import com.heima.health.utils.POIUtils;
import com.heima.pojo.OrderSetting;
import com.heima.service.OrderSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    //打印日志
    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    /**
     * 上传excel文件
     * @author: lwl
     * @date: 2020/9/24
     * @param excelfile:
     * @return: com.heima.health.entity.Result
     */
    public Result upload(MultipartFile excelFile){
        try {
            //获取Excel内容
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //转换为list<OderSetting>
            List<OrderSetting> list = new ArrayList<>(strings.size());
            OrderSetting os=null;
            SimpleDateFormat sdf=new SimpleDateFormat(POIUtils.DATE_FORMAT);
            //日期格式解析
            for (String[] strArr:strings){
                //strArr代表一行记录，0代表日期，1代表可预约数量
                Date parse = sdf.parse(strArr[0]);
                Integer parse1 = Integer.valueOf((strArr[1]));
                os=new OrderSetting(parse,parse1);
                list.add(os);
        }
            //调用业务服务
            orderSettingService.add(list);
            //返回结果给页面
            return  new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch(Exception e) {
//            e.printStackTrace();
            log.error("批量导入预约失败！");
        }
        return  new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

        @GetMapping("/getOrderSettingByMonth")
        /**
         * 通过月份获取预约设置数据
         * @author: lwl
         * @date: 2020/9/24
         * @param month:
         * @return: com.heima.health.entity.Result
         */
    public  Result getOrderSettingByMonth(String month){
        List<Map<String,Integer>> monthData =orderSettingService.getOrderSettingByMonth(month);
        return  new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,monthData);
}

        @PostMapping("/editNumberByDate")
        public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.eidtNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);

        }












}
