package com.xianglei.common_service.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.xianglei.common_service.common.BaseJson;
import com.xianglei.common_service.common.JwtUtils;
import com.xianglei.common_service.common.Tools;
import com.xianglei.common_service.domain.User;
import com.xianglei.common_service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @PostMapping("/login")
    public BaseJson logIn(@RequestBody Map<String, Object> map,
                          HttpServletRequest request) {
        BaseJson baseJson = new BaseJson(false);
        try {
            if (!Tools.isNull(map)) {
                String account = map.get("account") == null ? "" : map.get("account").toString();
                String password = map.get("password") == null ? "" : map.get("password").toString();
                User user = userService.login(account, password);
                if (!Tools.isNull(user)) {
                    HttpSession session = request.getSession();
                    // session 配置有效时间30分钟  可能存在缓存雪崩问题
                    // 更具用户id生成token
                    String token = JwtUtils.generateToken(user.getFlowId());
                    if (Tools.isNull(session.getAttribute("user_flowId"))) {
                        // token存入session session 存入redis
                        session.setAttribute("user_flowId", token);
                        baseJson.setMessage("登录成功");
                        baseJson.setData(token);
                        baseJson.setStatus(true);
                        baseJson.setCode(HttpStatus.OK.value());
                    } else {
                        if (user.getStatus() == 1 && user.getFlowId().endsWith(session.getAttribute("user_flowId").toString())) {
                            baseJson.setMessage("您已经登录过了");
                            baseJson.setStatus(true);
                            baseJson.setCode(HttpStatus.OK.value());
                            logger.info("您已经登录过了:" + user.getFlowId());
                        }
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

        } catch (Exception e) {
            baseJson.setMessage("登录报错:" + e.getMessage());
            baseJson.setCode(-1);
            logger.error("登录报错:{},堆栈信息:{}", e.getMessage(), e);
        }
        return baseJson;
    }

    @RequestMapping("/logout")
    public BaseJson logOut(HttpServletRequest request) {
        BaseJson baseJson = new BaseJson(false);
        try {
            HttpSession session = request.getSession();
            String userFlowId = (String) session.getAttribute("user_flowId");
            if (!StringUtils.isEmpty(userFlowId)) {
                try {
                    userService.logout(userFlowId);
                } catch (Exception e) {
                    logger.error("注销报错:{},堆栈信息:{}", e.getMessage(), e);
                }
                session.removeAttribute("user_flowId");
                baseJson.setMessage("注销成功");
                baseJson.setStatus(true);
                baseJson.setCode(HttpStatus.OK.value());
            } else {
                logger.info("你已注销过了");
                baseJson.setMessage("你已注销过了");
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("注销报错:{},堆栈信息:{}", e.getMessage(), e);
        }
        return baseJson;
    }

}
