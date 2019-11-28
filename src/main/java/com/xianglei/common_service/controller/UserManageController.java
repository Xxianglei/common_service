package com.xianglei.common_service.controller;

import com.xianglei.common_service.common.BaseJson;
import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.service.UserMangerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addUser")
    private BaseJson addUser(User user) {
        BaseJson baseJson = new BaseJson(false);
        try {
            int nums = userMangerService.addUser(user);
            baseJson.setMessage("新增成功");
            baseJson.setStatus(true);
            baseJson.setData(nums);
            baseJson.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("人员新增接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/deleteUser")
    private BaseJson deleteUser(@RequestParam("flowId") String flowId) {
        BaseJson baseJson = new BaseJson(false);
        try {
            int nums = userMangerService.deleteUser(flowId);
            if (nums > 0) {
                baseJson.setMessage("删除成功");
            } else {
                baseJson.setMessage("没有数据删除");
            }
            baseJson.setStatus(true);
            baseJson.setData(nums);
            baseJson.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("人员删除接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/updateUser")
    private BaseJson updateUser(User user) {
        BaseJson baseJson = new BaseJson(false);
        try {
            userMangerService.update(user);
            baseJson.setMessage("更新成功");
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("人员更新接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findUser")
    private BaseJson findUser(String flowId) {
        BaseJson baseJson = new BaseJson(false);
        try {
            User user = userMangerService.findUser(flowId);
            baseJson.setMessage("查询成功");
            baseJson.setData(user);
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/findAllUser")
    private BaseJson findAllUser(int isSuperUser) {
        BaseJson baseJson = new BaseJson(false);
        try {
            List<User> userList = userMangerService.findAllUser(isSuperUser);
            baseJson.setMessage("查询成功");
            baseJson.setData(userList);
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/batchDeleteUser")
    private BaseJson batchDeleteUser(@RequestParam("list") List<String> list) {
        BaseJson baseJson = new BaseJson(false);
        try {
            int success = userMangerService.batchDeleteUser(list);
            if (success > 0) {
                baseJson.setMessage("批量删除成功");
                baseJson.setData(success);
            } else {
                baseJson.setMessage("没有可删除数据");
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
    private BaseJson findUserByCondition(int status, int vip, int sexy) {
        BaseJson baseJson = new BaseJson(false);
        try {
            List<User> userByCondition = userMangerService.findUserByCondition(status, vip, sexy);
            baseJson.setMessage("查询成功");
            baseJson.setData(userByCondition);
            baseJson.setStatus(true);
            baseJson.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("人员条件接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

}
