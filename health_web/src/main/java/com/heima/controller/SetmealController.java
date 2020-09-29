package com.heima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.entity.Result;
import com.heima.health.utils.QiNiuUtils;
import com.heima.pojo.Setmeal;
import com.heima.service.SetmealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/22 10:48
 * 4
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static final Logger log= LoggerFactory.getLogger(SetmealController.class);
    @Reference
    private SetmealService setmealService;


    /**
     * 分页查询
     * @params
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> PageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,PageResult);
    }



    /**
     * 添加套餐
     * @params
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        //调用业务逻辑添加套餐
        setmealService.add(setmeal,checkgroupIds);
        //返回响应
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }



    @PostMapping("/upload")
    /**
     * 文件上传
     * @author: lwl
     * @date: 2020/9/22
     * @param imgFile:
     * @return: com.heima.health.entity.Result
     */
    public Result upload(MultipartFile imgFile){
        //获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //截取源文件名的后缀名
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID生成随机生成一个唯一的文件名+后缀名
        String uniqueName = UUID.randomUUID().toString() + substring;
        try {
            //调用七牛工具类上传文件
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),uniqueName);
            //响应结果给页面
            Map<String, String> resultMap=new HashMap<String, String>();
            //把domain和imgName放入map集合中
            resultMap.put("domain",QiNiuUtils.DOMAIN);
            resultMap.put("imgName",uniqueName);
            //返回响应结果
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,resultMap);
        } catch (IOException e) {
            log.error("上传图片失败！");
            // e.printStackTrace();
        }
        return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }



    @GetMapping("/findById")
    /**
     * 通过id查询套餐id
     * @author: lwl
     * @date: 2020/9/22
     * @param :
     * @return:
     */
    public  Result findById(int id){
        Setmeal setmeal=setmealService.findById(id);
    Map<String,Object> resultMap =new HashMap<String,Object>();
    resultMap.put("setmeal",setmeal);
    //解决数据表单图片表单回显
         resultMap.put("domain",QiNiuUtils.DOMAIN);
    return  new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }



    @GetMapping("/findCheckGroupIdsBySetmealId")
    /**
     * 通过id查询套餐检查组id
     * @author: lwl
     * @date: 2020/9/22
     * @param id:
     * @return: com.heima.health.entity.Result
     */
    public Result findCheckGroupIdsBySetmealId(int id){
        List<Integer> list=setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }



    /**
     * 修改套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param setmeal:
     * @param checkgroupIds:
     * @return: com.heima.health.entity.Result
     */
    @PostMapping("/update")
    public  Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    @PostMapping("/deleteById")
    /**
     * 删除套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param id:
     * @return: com.heima.health.entity.Result
     */
    public  Result deleteById(int id){
        setmealService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);

    }



}
