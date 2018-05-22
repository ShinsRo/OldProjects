package com.midas2018.root.repository;

import com.midas2018.root.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select(value = "SELECT * FROM member WHERE email = #{email}")
    Member findUserByEmail(@Param("email") String email);

    void memberRegister(Member member);

    @Select(value = "SELECT COUNT(*) FROM member WHERE email = #{email}")
    int isExistEmail(@Param("email") String email);
}
