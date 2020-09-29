package com.heima.dao;

import com.heima.pojo.User;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/29 11:22<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
public interface UserDao {
    User findByUsername(String username);
}
