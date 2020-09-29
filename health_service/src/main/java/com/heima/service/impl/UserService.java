package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.dao.UserDao;
import com.heima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/29 11:20<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@Service(interfaceClass = UserService.class)
public class UserService implements com.heima.service.UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
