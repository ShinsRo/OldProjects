package com.midas2018.root.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.midas2018.root.model.User;
import com.midas2018.root.model.UserStatus;
import com.midas2018.root.model.UserVO;

@Mapper
public interface UserMapper {

    @Select(value = "SELECT * FROM user WHERE email = #{email}")
    User findUserByEmail(@Param("email") String email);

    List<UserVO> selectUserListAll();

    void signup(UserVO user);

    UserVO selectUserByEmail(@Param("email") String email);

    UserStatus selectUserStatusByUserId(@Param("Id") int id);

    UserVO signin(@Param("email") String email, @Param("password") String password);

    void updateUser(UserVO user);

    void deleteUser(UserVO user);
}
