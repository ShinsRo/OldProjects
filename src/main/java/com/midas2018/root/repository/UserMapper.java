package com.midas2018.root.repository;

import com.midas2018.root.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select(value = "SELECT * FROM member WHERE email = #{email}")
    User findUserByEmail(@Param("email") String email);

    void userRegister(User user);

    @Select(value = "SELECT COUNT(*) FROM member WHERE email = #{email}")
    int isThereEmail(@Param("email") String email);
}
