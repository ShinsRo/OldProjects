package com.midas2018.root.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.midas2018.root.model.User;
import com.midas2018.root.model.UserStatus;
import com.midas2018.root.model.UserVO;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select(value = "SELECT * FROM user WHERE email = #{email}")
    User findUserByEmail(@Param("email") String email);

    void signup(UserVO user);

    UserVO selectUserByEmail(@Param("email") String email);

    UserStatus selectUserStatusByUserId(@Param("Id") int id);

    UserVO signin(@Param("email") String email, @Param("password") String password);

    @Select(value = "SELECT * FROM user WHERE id BETWEEN #{from} AND #{to}")
    List<UserVO> getUserList(@Param("from") int from, @Param("to") int to);
}
