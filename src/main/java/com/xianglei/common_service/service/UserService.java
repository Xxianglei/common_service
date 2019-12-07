package com.xianglei.common_service.service;


import com.xianglei.common_service.domain.User;
import org.springframework.stereotype.Component;

/**
 * 描述：获取用户信息
 * 时间：[2019/11/27:11:38]
 * 作者：xianglei
 * params: * @param null
 */
@Component
public interface UserService {
    User getUser(User user);

    /**
     * 登录服务
     * @param account
     * @param password
     * @return
     */
    User login(String account, String password);

    /**
     * 注销服务
     * @param userFlowId
     */
    void logout(String userFlowId) throws Exception;

    int checkUser(String flowId);

    boolean checkStatus(String flowId);
}
