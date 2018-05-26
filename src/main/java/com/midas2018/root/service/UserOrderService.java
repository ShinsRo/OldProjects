package com.midas2018.root.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.midas2018.root.model.OrderStatus;
import com.midas2018.root.model.UserOrderVO;
import com.midas2018.root.repository.UserOrderRepository;

@Service
public class UserOrderService {

    @Autowired
    private UserOrderRepository userOrderRepository;

    public List<UserOrderVO> getUserOrderLisAll() {
        return userOrderRepository.getUserOrderLisAll();
    }
    public List<UserOrderVO> getUserOrderListNotCompleteAll() {
        return userOrderRepository.selectUserOrderListNotCompleteAll();
    }

    public List<UserOrderVO> getUserOrderListByUserId(int userId) {
        return userOrderRepository.selectUserOrderListByUserId(userId);
    }

    public void insertUserOder(UserOrderVO userOrderVO) {
        userOrderRepository.insertUserOder(userOrderVO);
    }

    public void updateUserOrder(UserOrderVO userOrderVO) {
        userOrderRepository.updateUserOrder(userOrderVO);
    }

    public void deleteUserOrder(UserOrderVO userOrderVO) {
        userOrderVO.setStatus(OrderStatus.COMPLETE);
        userOrderRepository.deleteUserOrder(userOrderVO);
    }
}
