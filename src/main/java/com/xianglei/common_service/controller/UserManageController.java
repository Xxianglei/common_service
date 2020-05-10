package com.xianglei.common_service.controller;

import com.xianglei.common_service.common.BaseJson;
import com.xianglei.common_service.common.ListParamUtils;
import com.xianglei.common_service.common.Tools;
import com.xianglei.common_service.domain.Car;
import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.domain.UserAndCar;
import com.xianglei.common_service.mapper.CarMapper;
import com.xianglei.common_service.service.UserMangerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 描述：后台管理人员对于人员的管理api
 * <p>
 * 时间：[2019/11/28:11:35]
 * 作者：xianglei
 * params: * @param null
 */
@RestController
@RequestMapping("/manager")
public class UserManageController {

    private Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    UserMangerService userMangerService;
    @Autowired
    CarMapper carMapper;

    @PostMapping("/addUser")
    private BaseJson addUser(@RequestBody User user) {
        BaseJson baseJson = new BaseJson(false);
        try {

            int nums = userMangerService.addUser(user);
            if (nums == 0) {
                baseJson.setMessage("用户已存在");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("新增成功");
                baseJson.setStatus(true);
                baseJson.setData(nums);
                baseJson.setCode(HttpStatus.OK.value());
                logger.info(user.getFlowId() + ":注册成功");
            }
        } catch (Exception e) {
            logger.error("人员新增接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/deleteUser")
    private BaseJson deleteUser(@RequestBody Map<String, String> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String flowId = map.get("flowId") == null ? "" : map.get("flowId").toString();
                int nums = userMangerService.deleteUser(flowId);
                if (nums > 0) {
                    baseJson.setMessage("删除成功");
                } else {
                    baseJson.setMessage("没有数据删除");
                    logger.warn("没有数据删除:{}", flowId);
                }
                baseJson.setStatus(true);
                baseJson.setData(nums);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setStatus(true);
                baseJson.setMessage("参数不能为空");
                logger.warn("参数不能为空");
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("人员删除接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/updateUser")
    private BaseJson updateUser(@RequestBody User user) {
        BaseJson baseJson = new BaseJson(false);
        try {
            userMangerService.update(user);
            baseJson.setMessage("更新成功");
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
            logger.info("数据更新成功");
        } catch (Exception e) {
            logger.error("人员更新接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/updateUserAndCar")
    private BaseJson updateUserAndCar(@RequestBody UserAndCar userAndCar) {
        BaseJson baseJson = new BaseJson(false);
        try {
            User user = new User();
            user.setFlowId(userAndCar.getFlowId());
            user.setName(userAndCar.getName());
            user.setPhone(userAndCar.getPhone());
            user.setAge(userAndCar.getAge());
            if (userAndCar.getSexy().equals("男")) {
                user.setSexy("0");
            } else {
                user.setSexy("1");
            }
            Car car = new Car();
            car.setUserId(userAndCar.getFlowId());
            car.setCarNum(userAndCar.getCarNum());
            car.setColor(userAndCar.getColor());
            car.setModel(userAndCar.getModel());
            userMangerService.update(user, car);
            baseJson.setMessage("更新成功");
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
            logger.info("数据更新成功");
        } catch (Exception e) {
            logger.error("人员更新接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findUser")
    private BaseJson findUser(@RequestBody Map<String, String> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String flowId = map.get("flowId") == null ? "" : map.get("flowId").toString();
                User user = userMangerService.findUser(flowId);
                baseJson.setMessage("查询成功");
                baseJson.setData(user);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("参数不可为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
                logger.warn("参数不可为空");
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }
    @PostMapping("/findUserByName")
    private BaseJson findUserByName(@RequestBody Map<String, String> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String name = map.get("name") == null ? "" : map.get("name").toString();
                List<User> userByName = userMangerService.findUserByName(name);
                baseJson.setMessage("查询成功");
                baseJson.setData(userByName);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("参数不可为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
                logger.warn("参数不可为空");
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findAllUser")
    private BaseJson findAllUser(@RequestBody Map<String, Integer> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                int isSuperUser = map.get("isSuperUser") == null ? 0 : map.get("isSuperUser");
                List<User> userList = userMangerService.findAllUser(isSuperUser);
                baseJson.setMessage("查询成功");
                baseJson.setData(userList);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("你的参数为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
                logger.warn("参数不可为空");
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/batchDeleteUser")
    private BaseJson batchDeleteUser(@RequestBody ListParamUtils<String> param) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(param)) {
                List<String> list = param.getList();
                int success = userMangerService.batchDeleteUser(list);
                if (success > 0) {
                    baseJson.setMessage("批量删除成功");
                    baseJson.setData(success);
                } else {
                    baseJson.setMessage("没有可删除数据");
                    logger.warn("没得数据可以删除");
                }

            } else {
                baseJson.setMessage("参数不能为空");
                logger.warn("参数不能为空");
            }
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());

        } catch (Exception e) {
            logger.error("人员批量删除接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findUserByCondition")
    private BaseJson findUserByCondition(@RequestBody Map<String, Integer> map) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                int status = map.get("status") == null ? 0 : map.get("status");
                int vip = map.get("vip") == null ? 0 : map.get("vip");
                int sexy = map.get("sexy") == null ? 0 : map.get("sexy");
                List<User> userByCondition = userMangerService.findUserByCondition(status, vip, sexy);
                baseJson.setMessage("查询成功");
                baseJson.setData(userByCondition);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                logger.warn("参数为空");
                baseJson.setMessage("参数为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("人员条件接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @RequestMapping("/findUserAndCar")
    private BaseJson findUserAndCar(@RequestParam String flowId) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!StringUtils.isEmpty(flowId)) {
                User user = userMangerService.findUser(flowId);
                Car car = carMapper.findCar(flowId);
                UserAndCar userAndCar = new UserAndCar();
                userAndCar.setAge(user.getAge());
                if(Tools.isNotNull(car)){
                    userAndCar.setCarNum(car.getCarNum());
                    userAndCar.setColor(car.getColor());
                    userAndCar.setModel(car.getModel());
                }
                userAndCar.setName(user.getName());
                userAndCar.setPhone(user.getPhone());
                userAndCar.setSexy(user.getSexy().equals("0") ? "男" : "女");
                baseJson.setMessage("查询成功");
                baseJson.setData(userAndCar);
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                baseJson.setMessage("参数不可为空");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
                logger.warn("参数不可为空");
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }
}
