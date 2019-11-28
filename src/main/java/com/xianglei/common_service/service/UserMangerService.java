package com.xianglei.common_service.service;

import com.xianglei.common_service.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 人员增删改查
 */
@Component
public interface UserMangerService {
    /**********单人增删改查*************/
    int addUser(User user);
    int deleteUser(String flowId);
    void update(User user);
    User findUser(String flowId);
    /**********查询所有人****************/
    List<User> findAllUser(int isSuperUser);
    /**********批量删除****************/
    int batchDeleteUser(List<String> list);
    /**********按条件查询人员***********/
    List<User> findUserByCondition(int status,int vip,int sexy);
}
