package com.heima.dao;

import com.heima.pojo.Member;

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
}
