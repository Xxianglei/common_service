package com.xianglei.common_service.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 描述：用户/管理实体
 * 时间：[2019/11/27:10:45]
 * 作者：xianglei
 * params: FLOW_IDvarchar(64) NOT NULL流水号
 * NAMEvarchar(64) NULL姓名
 * PASSWORDvarchar(64) NOT NULL密码
 * ACCOUNTvarbinary(64) NOT NULL账号
 * CREATE_TIMEdatetime NULL创建时间
 * PHONEvarchar(64) NULL手机号
 * STATUSint(1) NULL0/1下线/上线
 * VIPint(1) NULL0/1 会员/非会员
 */
@Component
public class User implements Serializable {

    private static final long serialVersionUID = -4240023762185445691L;
    private String flowId;
    private String name;
    private String password;
    private String account;
    private String createDate;
    private String phone;
    private String status;

    private String vip;
    private String superRoot;
    private int age;
    private String sexy;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    public String getSuperRoot() {
        return superRoot;
    }

    public void setSuperRoot(String superRoot) {
        this.superRoot = superRoot;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
}
