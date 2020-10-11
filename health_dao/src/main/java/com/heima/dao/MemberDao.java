package com.heima.dao;

import com.heima.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/25 20:51<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    /**
     * 统计在某个日期之前，截止的会员数量
     * @author: lwl
     * @date: 2020/9/28
     * @param null:
     * @return:
     */
    Integer findMemberCountBeforDate(String endDate);
    /**
     * 会员性别报表占比
     * @author: lwl
     * @date: 2020/10/9
     * @param null:
     * @return:
     */
    List<Map<String, Object>> getMemberReportBySex();
    /**
     * 会员年龄报表占比
     * @author: lwl
     * @date: 2020/10/9
     * @param null:
     * @return:
     */
    List<String> findMemberAge();
    /**
     * 计算会员年龄
     * @author: lwl
     * @date: 2020/10/9
     * @param null:
     * @return:
     */
    Integer calcMmeberAge(String age);
    /**
     * @Title
     * 今日新增会员
     * @author lwl
     * @param null
     * @return
     */
    Integer findMemberCountByToday(String reportDate);
    /**
     * @Title
     * 会员总量
     * @author lwl
     * @param null
     * @return
     */
    Integer findMemberTotalCount();
    /**
     * @Title
     * 本周新增会员
     * @author lwl
     * @param null
     * @return
     */
    Integer findThisWeekNewMember(String monday);
    /**
     * @Title
     * 本月新增会员
     * @author lwl
     * @param null
     * @return
     */
    Integer findThisMonthNewMember(String firstDayOfMonth);
}
