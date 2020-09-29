package com.heima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.utils.QiNiuUtils;
import com.heima.service.SetmealService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CleanImgJob")
public class CleanImgJob {
    //订阅服务
    @Reference
    private SetmealService setmealService;

    public  void cleanImg(){
        //获取七牛上所有的图片
        List<String> img7Niu= QiNiuUtils.listFile();
        //获取数据路中套餐的所有图片
        List<String> imgInDb= setmealService.findImags();
        //七牛中移除数据库中的照片
        img7Niu.removeAll(imgInDb);
        //删除
        String[] needDelete = img7Niu.toArray(new String[]{});
        QiNiuUtils.removeFiles(needDelete);

    }


}
