package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.dao.MemberDao;
import com.heima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/28 18:19<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@Service(interfaceClass =MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
  /**
   *统计在某个日期之前，截止的会员数量
   * @author: lwl
   * @date: 2020/10/9
   * @param months:
   * @return: java.util.List<java.lang.Integer>
   */
    public List<Integer> getMemberReport(List<String> months) {
        //遍历月份
        List<Integer> memberCount =new ArrayList<Integer>();
        if (months!=null){
            //循环遍历每个月份，获取当月会员的数量
            for (String month:months){
                String endDate = month + "-31";
                Integer count= memberDao.findMemberCountBeforDate(endDate);
           memberCount.add(count);
            }
        }
        //返回获取的会员数量
        return memberCount;
    }

    /**
     * 饼状图：根据会员性别统计占比
     * @author: lwl
     * @date: 2020/10/9
     * @param :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> getMemberReportBySex() {
        //dao层查询会员性别
        List<Map<String, Object>> resultMap = memberDao.getMemberReportBySex();
        //遍历
        for (Map<String, Object> map : resultMap) {
            if (map.get("name") != null) {
                Integer name = Integer.valueOf((map.get("name") + ""));
                if (name == 1) {
                    map.put("name", "男");
                } else {
                    map.put("name", "女");
                }
            } else{
                map.put("name","未知");
            }
        }
        return resultMap;
    }

    @Override
    /**
     * 饼状图：根据会员年龄统计占比
     * @author: lwl
     * @date: 2020/10/9
     * @param :
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> getMemberAge() {
        //1.从数据库中查询每个会员的生日"yy-MM-dd"
        List<String> ageList = memberDao.findMemberAge();
        //2.遍历结果集,使用时间差SQL语句,计算每个会员的年龄并存进集合中
        List<Integer> list = new ArrayList<>();
        for (String age : ageList) {
            Integer result = memberDao.calcMmeberAge(age);
            list.add(result);
        }
        //3.遍历集合,计算每个会员年龄段的总数
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;
        for (Integer age : list) {
            if (age != null) {
                if (age < 18 && age >= 0) {
                    count1++;
                }
                if (age >= 18 && age < 45) {
                    count2++;
                }
                if (age >= 45 && age < 65) {
                    count3++;
                }
                if (age >= 65) {
                    count4++;
                }
            }else{
                count5++;
            }
        }
        //4.封装结果返回
        Map<String, Object> resultMap = new HashMap();
        List<String> resultlist = new ArrayList<>();
        String key1 = "0~17岁";
        String key2 = "18~45岁";
        String key3 = "46~65岁";
        String key4 = "65岁以上";
        String key5 = "未知";
        resultlist.add(key1);
        resultlist.add(key2);
        resultlist.add(key3);
        resultlist.add(key4);
        resultlist.add(key5);
        Map<String, Object> resultMap1 = new HashMap<>();
        resultMap1.put("name", key1);
        resultMap1.put("value", count1);
        Map<String, Object> resultMap2 = new HashMap<>();
        resultMap2.put("name", key2);
        resultMap2.put("value", count2);
        Map<String, Object> resultMap3 = new HashMap<>();
        resultMap3.put("name", key3);
        resultMap3.put("value", count3);
        Map<String, Object> resultMap4 = new HashMap<>();
        resultMap4.put("name", key4);
        resultMap4.put("value", count4);
        Map<String, Object> resultMap5 = new HashMap<>();
        resultMap5.put("name", key5);
        resultMap5.put("value", count5);
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(resultMap1);
        mapList.add(resultMap2);
        mapList.add(resultMap3);
        mapList.add(resultMap4);
        mapList.add(resultMap5);
        Map<String, Object> result = new HashMap<>();
        result.put("huiyuanAge", resultlist);
        result.put("huiyuanAgeCount", mapList);
        return result;
    }

}
