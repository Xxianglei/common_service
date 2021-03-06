//package com.xianglei.common_service.timejob;
//
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.xianglei.common_service.common.JwtUtils;
//import com.xianglei.common_service.common.Tools;
//import com.xianglei.common_service.common.utils.RedisUtil;
//import com.xianglei.common_service.domain.User;
//import com.xianglei.common_service.service.UserMangerService;
//import com.xianglei.common_service.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Component
//public class ScheduledJob {
//    private static Logger logger = LoggerFactory.getLogger(ScheduledJob.class);
//    @Autowired
//    RedisUtil redisUtil;
//    @Autowired
//    UserMangerService userMangerService;
//    @Autowired
//    UserService userService;
//
//    /**
//     * 定时任务方法
//     * 没三十分钟清理一次token失效或redis不存在的用户
//     *
//     * @Scheduled:设置定时任务 cron属性：cron表达式。定时任务触发是时间的一个字符串表达形式
//     */
//    @Scheduled(cron = "0 0/1 * * * ?")
//    public void scheduledMethod() throws Exception {
//        logger.info("Scheduled定时清理非法在线用户触发，时间：{}", new Date());
//        List<String> allOnlineUsers = userMangerService.findAllUserNoPrama();
//        // 获取在线用户
//        Set<String> keys = redisUtil.getKeys();
//        if (Tools.isEmpty(keys) && !allOnlineUsers.isEmpty()) {
//            for (String flowId : allOnlineUsers) {
//                logger.info("Redis缓存清空同步任务\n" +
//                        "强制下线---->时间：{}", new Date());
//                userService.logout(flowId);
//            }
//        } else {
//            for (String OnlineflowId : allOnlineUsers) {
//                boolean tag = false;
//                for (String key : keys) {
//                    if (JwtUtils.verify(key)) {
//                        String flowId = JwtUtils.getFlowId(key);
//                        if (OnlineflowId.equals(flowId)) {
//                            tag = true;
//                            break;
//                        }
//                    }
//                }
//                if (!tag) {
//                    // 数据库中用户在线，但是token已经没有了，失效了，则强制下线
//                    logger.info("部分用户：{}token失效\n" +
//                            "强制下线---->时间：{}", OnlineflowId, new Date());
//                    userService.logout(OnlineflowId);
//                }
//            }
//        }
//    }
//}
