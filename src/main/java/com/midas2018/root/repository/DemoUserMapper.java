package com.midas2018.root.repository;

import com.midas2018.root.model.DemoUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DemoUserMapper {

    @Select("SELECT * FROM DEMO WHERE id = #{id}")
    DemoUserVO selectUserById(@Param("id") String id);
}
