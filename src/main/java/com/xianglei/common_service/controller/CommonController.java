package com.xianglei.common_service.controller;

import com.xianglei.common_service.common.BaseJson;
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
/**
 * 描述：登录注销接口，适合手机和后台所有人员 session共享机制
 * 时间：[2019/11/28:11:34]
 * 作者：xianglei
 * params: * @param null
 */
@RestController
@RequestMapping("/user")
// 开启跨域请求 一切默认
@CrossOrigin(maxAge = 3600,origins = "*")
public class CommonController {
    @Autowired
    UserService userService;
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @PostMapping("/login")
    public BaseJson logIn(@RequestParam(value = "account") String account,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        BaseJson baseJson = new BaseJson(false);
        try {
            User user = userService.login(account, password);
            if (!Tools.isNull(user)) {
                HttpSession session = request.getSession();
                if (Tools.isNull(session.getAttribute("user_flowId"))) {
                    session.setAttribute("user_flowId", user.getFlowId());
                    baseJson.setMessage("登录成功");
                    baseJson.setStatus(true);
                    baseJson.setCode(HttpStatus.OK.value());
                }else{
                    if(user.getFlowId().endsWith(session.getAttribute("user_flowId").toString())){
                        baseJson.setMessage("您已经登录过了");
                        baseJson.setStatus(true);
                        baseJson.setCode(HttpStatus.OK.value());
                    }
                }
            }else{
                baseJson.setMessage("账号或密码错误");
                baseJson.setStatus(false);
                baseJson.setCode(HttpStatus.OK.value());
            }
        } catch (Exception e) {
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
                baseJson.setMessage("注销失败");
                baseJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } catch (Exception e) {
            logger.error("注销报错:{},堆栈信息:{}", e.getMessage(), e);
        }
        return baseJson;
    }

}
