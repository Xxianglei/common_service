package com.xianglei.common_service.mapper;

import com.xianglei.common_service.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMangerServiceMapper {

    @Insert("INSERT INTO `BS_USER` ( `FLOW_ID`,`NAME`,`PASSWORD`,`ACCOUNT`,`PHONE`,`STATUS`,`VIP`,`SUPER_ROOT`,`AGE`,`SEXY`) VALUES( #{flowId}, #{name},#{password},#{account},#{phone},0,#{vip}, #{superRoot},#{age},#{sexy}) ; ")
    int addUser(User user);

    @Delete("DELETE FROM `BS_USER` WHERE `FLOW_ID` = #{flowId} ;")
    int deleteUser(String flowId);

    @Update("<script> UPDATE `BS_USER` SET " +
            "<if test ='name != null '> `NAME` = #{name},</if>" +
            "<if test ='password != null '> `PASSWORD` = #{password}, </if>" +
            "<if test ='account != null '> `ACCOUNT` = #{account}, </if>" +
            "<if test ='phone != null '> `PHONE` = #{phone}, </if>" +
            "<if test ='status != null '> `STATUS` = #{status}, </if>" +
            "<if test ='vip != null '> `VIP` = #{vip} ,</if>" +
            "<if test ='sexy != null '> `SEXY` = #{sexy} ,</if>" +
            "<if test ='age != null '> `AGE` = #{age} </if>" +
            " WHERE `FLOW_ID` = #{flowId} ;</script>")
    void update(User user);

    @Select("SELECT * FROM `BS_USER` WHERE FLOW_ID=#{flowId};")
    @Results({
            @Result(column = "FLOW_ID", property = "flowId"),
            @Result(column = "CREATE_DATE", property = "createDate"),
            @Result(column = "SUPER_ROOT", property = "superRoot")
    })
    User findUser(String flowId);

    @Select("SELECT * FROM `BS_USER` WHERE SUPER_ROOT=#{isSuperUser};")
    @Results({
            @Result(column = "FLOW_ID", property = "flowId"),
            @Result(column = "CREATE_DATE", property = "createDate"),
            @Result(column = "SUPER_ROOT", property = "superRoot")
    })
    List<User> findAllUser(int isSuperUser);

    @Delete("<script> DELETE FROM `BS_USER` WHERE `FLOW_ID` IN" +
            "<foreach collection = 'list' separator = ',' open = '(' close = ')' item = 'flowId'> " +
            "#{flowId}" +
            "</foreach> ;</script>")
    int batchDeleteUser(List<String> list);

    @Select("<script> SELECT * FROM `BS_USER` WHERE 1=1 " +
            "<if test ='status != null '> AND `STATUS` = #{status} </if>" +
            "<if test ='vip != null '> AND `VIP` = #{vip} </if>" +
            "<if test ='sexy != null '> AND `SEXY` = #{sexy} </if>; </script>")
    List<User> findUserByCondition(int status, int vip, int sexy);

    @Select("SELECT FLOW_ID FROM `BS_USER` where STATUS=1;")
    @Results({
            @Result(column = "FLOW_ID", property = "flowId")
    })
    List<String> findAllUserNoPrama();

    @Select("SELECT FLOW_ID FROM `BS_USER` where ACCOUNT=#{phone};")
    String findFlowIdByPhone(String phone);
    @Select("SELECT * FROM `BS_USER` WHERE NAME like CONCAT('%',#{isSuperUser},'%');")
    @Results({
            @Result(column = "FLOW_ID", property = "flowId"),
            @Result(column = "CREATE_DATE", property = "createDate"),
            @Result(column = "SUPER_ROOT", property = "superRoot")
    })
    List<User> findUserByName(String name);
}
