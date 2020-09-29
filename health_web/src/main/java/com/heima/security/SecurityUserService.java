package com.heima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.pojo.Permission;
import com.heima.pojo.Role;
import com.heima.pojo.User;
import com.heima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/29 10:26<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@Component
public class SecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;
    @Override
    /**
     * 用户认证和权限实现
     * @author: lwl
     * @date: 2020/9/29
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用服务逻辑查询数据库的用户信息
        User user=userService.findByUsername(username);
        if (null!=user){
            //用户存在
            //获取用户权限集合
            List<GrantedAuthority> authorityList=new ArrayList<GrantedAuthority>();
            GrantedAuthority authority=null;
            //获取用户所有角色
            Set<Role> roles= user.getRoles();
            //遍历
            if (null!=roles){
                for (Role role:roles){
                    authority=new SimpleGrantedAuthority(role.getKeyword());
                    authorityList.add(authority);
                    //角色权限集合
                    Set<Permission> permissions=role.getPermissions();
                    if (null!=permissions){
                        for (Permission permission:permissions){
                            //授权
                            authority=new SimpleGrantedAuthority(permission.getKeyword());
                            authorityList.add(authority);
                        }
                    }
                }
            }
            //认证用户信息
            return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorityList);
        }
        return null;
    }
}
