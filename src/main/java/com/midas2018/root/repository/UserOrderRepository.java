package com.midas2018.root.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.midas2018.root.model.UserOrderVO;

@Repository
public class UserOrderRepository {
    @Autowired
    private UserOrderMapper userOrderMapper;

    public List<UserOrderVO> getUserOrderLisAll() {
        return userOrderMapper.getUserOrderLisAll();
    }
    public List<UserOrderVO> selectUserOrderListNotCompleteAll() {
        return userOrderMapper.selectUserOrderListNotCompleteAll();
    }

    public List<UserOrderVO> selectUserOrderListByUserId(int userId) {
        return userOrderMapper.selectUserOrderListByUserId(userId);
    }

    public void insertUserOder(UserOrderVO userOrderVO) {
        userOrderMapper.insertUserOrder(userOrderVO);
    }

    public void updateUserOrder(UserOrderVO userOrderVO) {
        userOrderMapper.updateUserOrderByUserId(userOrderVO);
    }

    public void deleteUserOrder(UserOrderVO userOrderVO) {
        userOrderMapper.deleteUserOrderByUserId(userOrderVO);
    }

}
