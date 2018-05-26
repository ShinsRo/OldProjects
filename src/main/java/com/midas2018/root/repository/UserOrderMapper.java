package com.midas2018.root.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.midas2018.root.model.UserOrderVO;

@Mapper
public interface UserOrderMapper {

    List<UserOrderVO> selectUserOrderList();

    void insertUserOrder(UserOrderVO userOrderVO);
}
