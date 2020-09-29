package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.dao.MemberDao;
import com.heima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
}
