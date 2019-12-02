package com.xianglei.common_service.controller;

import com.xianglei.common_service.common.BaseJson;
import com.xianglei.common_service.common.Tools;
import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.service.UserMangerService;
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

    @PostMapping("/addUser")
    private BaseJson addUser(@RequestBody User user) {
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
    private BaseJson updateUser(@RequestBody User user) {
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
    private BaseJson findUser(@RequestBody Map<String, Integer> map) {
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
            }
        } catch (Exception e) {
            logger.error("人员查询接口错误:{}\n堆栈信息:{}", e.getMessage(), e);
            baseJson.setMessage("服务端内部错误:" + e.getMessage());
            baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return baseJson;
    }

    @PostMapping("/batchDeleteUser")
    private BaseJson batchDeleteUser(@RequestBody List<String> list) {
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

}
