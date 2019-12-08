package com.xianglei.common_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.xianglei.temppark_service.mapper")
//开启定时器
@EnableScheduling
public class CommonServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CommonServiceApplication.class, args);
    }
    /*
     * 设置匹配*.json后缀请求
     * @param dispatcherServlet
     * @return-该设置严格指定匹配后缀*.do或.action，但有风险 可能不生效
     *
     *
    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletServletRegistrationBean.addUrlMappings("*.json");
        return servletServletRegistrationBean;
    }
     */
}

