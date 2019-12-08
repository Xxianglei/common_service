package com.xianglei.common_service.service.impl;


import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.mapper.UserMapper;
import com.xianglei.common_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(User user) {
        User normalUser = userMapper.getUserFromNomal(user);
        return normalUser;
    }

    @Override
    public User login(String account, String password) {
        User user = userMapper.login(account, password);
        userMapper.update(account, password);
        return user;
    }

    @Override
    public void logout(String userFlowId) throws Exception {
         int index = userMapper.logout(userFlowId);
         if(index==0){
             throw new Exception("未查到该用户");
         }
    }

    @Override
    public int checkUser(String flowId) {
        return userMapper.checkUser(flowId);
    }

    @Override
    public boolean checkStatusIsZero(String flowId) {
        return userMapper.checkStatus(flowId)==0;
    }
}
