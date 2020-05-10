package com.xianglei.common_service.service;

import com.xianglei.common_service.domain.Car;
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
    void update(User user, Car car);
    User findUser(String flowId);
    /**********查询所有人****************/
    List<User> findAllUser(int superUser);
    /**********查询所有人
     * @return****************/
    List<String> findAllUserNoPrama();
    /**********批量删除****************/
    int batchDeleteUser(List<String> list);
    /**********按条件查询人员***********/
    List<User> findUserByCondition(int status,int vip,int sexy);

    /**
     * 根据姓名查询用户
     * @param name
     * @return
     */
    List<User> findUserByName(String name);
}
