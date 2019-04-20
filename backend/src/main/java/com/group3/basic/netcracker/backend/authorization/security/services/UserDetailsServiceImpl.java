package com.group3.basic.netcracker.backend.authorization.security.services;

import com.group3.basic.netcracker.backend.usertable.dao.daoimpl.UserDaoImpl;
import com.group3.basic.netcracker.backend.usertable.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDaoImpl jdbcTemplateUsersDao = context.getBean(UserDaoImpl.class);
        User user = jdbcTemplateUsersDao.findByUsername(username);

        return UserPrinciple.build(user);
    }
}