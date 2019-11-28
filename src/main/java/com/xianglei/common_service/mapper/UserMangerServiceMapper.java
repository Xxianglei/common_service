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
            "<if test ='password != null '> `PASSWORD` = #{password}, </if>"+
            "<if test ='account != null '> `ACCOUNT` = #{account}, </if>"+
            "<if test ='phone != null '> `PHONE` = #{phone}, </if>"+
            "<if test ='status != null '> `PHONE` = #{status}, </if>"+
            "<if test ='vip != null '> `VIP` = #{vip} ,</if>"+
            "<if test ='age != null '> `AGE` = #{age} </if>"+
            " WHERE `FLOW_ID` = #{flowId} ;</script>")
    void update( User user);

    @Select("SELECT * FROM `BS_USER` WHERE FLOW_ID=#{flowId};")
    User findUser(String flowId);

    @Select("SELECT * FROM `BS_USER` WHERE SUPER_ROOT=#{isSuperUser};")
    List<User> findAllUser(int isSuperUser);

    @Delete("<script> DELETE FROM `BS_USER` WHERE `FLOW_ID` IN" +
            "<foreach collection = 'list' separator = ',' open = '(' close = ')' item = 'flowId'> " +
            "#{flowId}"+
            "</foreach> ;</script>")
    int batchDeleteUser(List<String> list);

    @Select("<script> SELECT * FROM `BS_USER` WHERE 1=1 " +
            "<if test ='status != null '> AND `STATUS` = #{status} </if>" +
            "<if test ='vip != null '> AND `VIP` = #{vip} </if>" +
            "<if test ='sexy != null '> AND `SEXY` = #{sexy} </if>; </script>")
    List<User> findUserByCondition(int status, int vip,int sexy);

}
