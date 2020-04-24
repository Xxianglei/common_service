package com.xianglei.common_service.mapper;

import com.xianglei.common_service.domain.Car;
import com.xianglei.common_service.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/23 20:39
 * com.xianglei.common_service.mapper
 * @Description:
 */
@Mapper
public interface CarMapper {
    @Select("SELECT CAR_NUM,COLOR,MODEL FROM `BS_USER_CAR` WHERE USER_ID=#{flowId} LIMIT 1;")
    @Results({
            @Result(column = "CAR_NUM", property = "carNum"),
            @Result(column = "COLOR", property = "color"),
            @Result(column = "MODEL", property = "model")
    })
    Car findCar(String flowId);

    @Update("<script> UPDATE `BS_USER_CAR` SET " +
            "<if test ='carNum != null '> `CAR_NUM` = #{carNum}, </if>" +
            "<if test ='color != null '> `COLOR` = #{color} ,</if>" +
            "<if test ='model != null '> `MODEL` = #{model} </if>" +
            " WHERE `USER_ID` = #{userId} ;</script>")
    void update(Car car);
}
