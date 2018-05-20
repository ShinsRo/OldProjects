package com.midas2018.root._demo;

import com.midas2018.root._demo.domain.DemoUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DemoUserMapper {

    @Select("SELECT * FROM DEMO WHERE id = #{id}")
    DemoUserVO findUserById(@Param("id") String id);

    @Select("SELECT * FROM DEMO")
    List<DemoUserVO> getUserList();
}
