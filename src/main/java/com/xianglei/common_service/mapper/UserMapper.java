package com.xianglei.common_service.mapper;


import com.xianglei.common_service.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT \n" +
            "*\n" +
            "FROM\n" +
            "`BS_USER` \n" +
            "WHERE FLOW_ID = #{flowId}\n"
    )
    @Results({
            @Result(column = "CREATE_DATE",property = "createDate"),
            @Result(column = "FLOW_ID",property = "flowId")
    })
    User getUserFromNomal(User user);

    @Select("SELECT \n" +
            "*\n" +
            "FROM\n" +
            "`BS_USER` \n" +
            "WHERE ACCOUNT =#{account} \n" +
            "AND PASSWORD =#{password}\n" +
            "\n")
    @Results({
            @Result(column = "CREATE_DATE",property = "createDate"),
            @Result(column = "FLOW_ID",property = "flowId")
    })
    User login(String account, String password);

    @Update("UPDATE \n" +
            "`BS_USER` \n" +
            "SET\n" +
            "`STATUS` = 0\n" +
            "WHERE `FLOW_ID` = #{userFlowId} ;")
    int logout(String userFlowId);

    @Update("UPDATE \n" +
            "`BS_USER` \n" +
            "SET\n" +
            "`STATUS` = 1\n" +
            "WHERE ACCOUNT =#{account} \n" +
            "AND PASSWORD =#{password};")
    void update(String account, String password);

    @Select("select SUPER_ROOT from BS_USER where FLOW_ID = #{flowId}")
    @Results({
            @Result(column = "SUPER_ROOT",property = "superRoot")
    })
    int checkUser(String flowId);
}
