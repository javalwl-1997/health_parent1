package com.heima.service;

import com.heima.pojo.User;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/29 10:28<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
public interface UserService {
    User findByUsername(String username);
}
