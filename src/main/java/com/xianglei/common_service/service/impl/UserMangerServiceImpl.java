package com.xianglei.common_service.service.impl;

import com.xianglei.common_service.common.Tools;
import com.xianglei.common_service.controller.CommonController;
import com.xianglei.common_service.domain.Car;
import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.mapper.CarMapper;
import com.xianglei.common_service.mapper.UserMangerServiceMapper;
import com.xianglei.common_service.service.UserMangerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserMangerServiceImpl implements UserMangerService {

    private Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    UserMangerServiceMapper userMangerServiceMapper;
    @Autowired
    CarMapper carMapper;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int addUser(User user) {
        int result = 0;
        String phone = user.getPhone();
        String flowId = userMangerServiceMapper.findFlowIdByPhone(phone);
        if (StringUtils.isEmpty(flowId)) {
            UUID uuid = UUID.randomUUID();
            user.setFlowId(uuid.toString());
            result = userMangerServiceMapper.addUser(user);
        }
        return result;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int deleteUser(String flowId) {
        return userMangerServiceMapper.deleteUser(flowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void update(User user) {
        userMangerServiceMapper.update(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void update(User user, Car car) {
        userMangerServiceMapper.update(user);
        Car carMapperCar = carMapper.findCar(car.getUserId());
        if (Tools.isNull(carMapperCar)) {
            car.setFlowId(UUID.randomUUID().toString());
            carMapper.insertMyCar(car);
        } else {
            carMapper.update(car);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findUser(String flowId) {
        return userMangerServiceMapper.findUser(flowId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAllUser(int isSuperUser) {
        return userMangerServiceMapper.findAllUser(isSuperUser);
    }

    @Override
    public List<String> findAllUserNoPrama() {
        return userMangerServiceMapper.findAllUserNoPrama();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int batchDeleteUser(List<String> list) {
        return userMangerServiceMapper.batchDeleteUser(list);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findUserByCondition(int status, int vip, int sexy) {
        return userMangerServiceMapper.findUserByCondition(status, vip, sexy);
    }

    @Override
    public List<User> findUserByName(String name) {

        return userMangerServiceMapper.findUserByName(name);
    }
}
