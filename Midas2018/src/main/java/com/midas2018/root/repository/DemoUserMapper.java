package com.midas2018.root.repository;

import com.midas2018.root.model.DemoUserVO;
import com.midas2018.root.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface DemoUserMapper {

    @Select("SELECT * FROM DEMO WHERE id = #{id}")
    DemoUserVO selectUserById(@Param("id") String id);

    User selectUser(@Param("id") long id);
}
