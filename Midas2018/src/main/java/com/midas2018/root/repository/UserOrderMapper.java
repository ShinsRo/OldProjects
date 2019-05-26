package com.midas2018.root.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.midas2018.root.model.UserOrderVO;

@Mapper
public interface UserOrderMapper {

    List<UserOrderVO> getUserOrderLisAll();

    List<UserOrderVO> selectUserOrderListNotCompleteAll();

    List<UserOrderVO> selectUserOrderListBookingByUserId(@Param("userId") int userId);

    void insertUserOrder(UserOrderVO userOrderVO);

    void updateUserOrderByUserId(UserOrderVO userOrderVO);

    void deleteUserOrderByUserId(UserOrderVO userOrderVO);
}
