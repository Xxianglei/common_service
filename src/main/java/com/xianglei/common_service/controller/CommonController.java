package com.xianglei.common_service.controller;

import com.xianglei.common_service.common.BaseJson;
import com.xianglei.common_service.common.JwtUtils;
import com.xianglei.common_service.common.Tools;
import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述：登录注销接口，适合手机和后台所有人员 session共享机制
 * 时间：[2019/11/28:11:34]
 * 作者：xianglei
 * params: * @param null
 */
@RestController
@RequestMapping("/user")
// 开启跨域请求 一切默认
@CrossOrigin(maxAge = 3600, origins = "*")
public class CommonController {
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @PostMapping("/login")
    public BaseJson logIn(@RequestBody Map<String, Object> map,
                          HttpServletRequest request) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String account = map.get("account") == null ? "" : map.get("account").toString();
                String password = map.get("password") == null ? "" : map.get("password").toString();
                String token = null;
                User user = userService.login(account, password);
                if (!Tools.isNull(user)) {
                    // 更具用户id生成token
                    if (user.getStatus() == 0) {
                        token = JwtUtils.generateToken(user.getFlowId());
                        // token存入 存入redis  默认30 分钟
                        redisTemplate.opsForValue().set(token, token);
                        redisTemplate.expire(token, 30, TimeUnit.MINUTES);
                        baseJson.setMessage("登录成功");
                        baseJson.setToken(token);
                        baseJson.setStatus(true);
                        baseJson.setCode(HttpStatus.OK.value());
                    } else if (user.getStatus() == 1) {
                        baseJson.setMessage("您已经登录过了");
                        baseJson.setStatus(true);
                        baseJson.setCode(HttpStatus.OK.value());
                        logger.info("您已经登录过了:" + user.getFlowId());
                    }
                } else {
                    logger.warn("账号或密码错误");
                    baseJson.setMessage("账号或密码错误");
                    baseJson.setStatus(false);
                    baseJson.setCode(HttpStatus.OK.value());
                }
            } else {
                logger.warn("请求参数为空");
                baseJson.setMessage("参数不可为空");
                baseJson.setStatus(false);
                baseJson.setCode(HttpStatus.OK.value());
            }

        } catch (
                Exception e) {
            baseJson.setMessage("登录报错:" + e.getMessage());
            baseJson.setCode(-1);
            logger.error("登录报错:{},堆栈信息:{}", e.getMessage(), e);
        }
        return baseJson;
    }

    @RequestMapping("/logout")
    public BaseJson logOut(@RequestHeader(required = true) String tokens) {
        BaseJson baseJson = new BaseJson(false);
        try {
            // 拿到redis里面的token值
            String flowId = JwtUtils.getFlowId(tokens);
            if (!StringUtils.isEmpty(flowId)) {
                try {
                    if (userService.checkStatusIsZero(flowId)) {
                        baseJson.setMessage("你已经下线了");
                    } else {
                        if(redisTemplate.hasKey(tokens))
                        redisTemplate.delete(tokens);
                        userService.logout(flowId);
                        baseJson.setMessage("退出成功");
                    }
                    baseJson.setStatus(true);
                    baseJson.setCode(HttpStatus.OK.value());
                } catch (Exception e) {
                    logger.error("注销报错:{},堆栈信息:{}", e.getMessage(), e);
                    baseJson.setMessage("注销失败");
                    baseJson.setStatus(true);
                    baseJson.setCode(HttpStatus.OK.value());
                }
            } else {
                logger.info("你的token为空，无法鉴权");
                baseJson.setMessage("你的token为空");
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("注销报错:{},堆栈信息:{}", e.getMessage(), e);
        }
        return baseJson;
    }

    /**
     * 判断是否是超级用户
     *
     * @param flowId
     * @return
     */
    @RequestMapping("/checkSuper")
    public boolean checkIsSuper(@RequestParam("flowId") String flowId) {
        boolean verify = JwtUtils.verify(flowId);
        String flowIdRight = flowId;
        if (verify)
            flowIdRight = JwtUtils.getFlowId(flowId);
        int isSuper = userService.checkUser(flowIdRight);
        return isSuper == 1;
    }
}
