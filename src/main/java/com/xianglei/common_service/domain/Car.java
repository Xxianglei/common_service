package com.xianglei.common_service.domain;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/23 20:40
 * com.xianglei.common_service.domain
 * @Description:
 */
public class Car {

    String userId;
    String model;
    String carNum;
    String color;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
