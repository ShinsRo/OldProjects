package com.midas2018.root.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.midas2018.root.model.UserOrderVO;

@Repository
public class UserOrderRepository {
    @Autowired
    private UserOrderMapper userOrderMapper;

    public List<UserOrderVO> selectUserOrderList() {
        return userOrderMapper.selectUserOrderList();
    }

    public void insertUserOder(UserOrderVO userOrderVO) {
        userOrderMapper.insertUserOrder(userOrderVO);
    }

}
