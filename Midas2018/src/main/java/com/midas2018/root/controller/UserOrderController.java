package com.midas2018.root.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.model.UserOrderVO;
import com.midas2018.root.service.UserOrderService;
import com.midas2018.root.support.Constant;

@RestController
@RequestMapping(Constant.USER_ORDER_API_URI)
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("/getUserOrderLisAll")
    public ResultContainer<List<UserOrderVO>> getUserOrderLisAll() {
        return new ResultContainer<>(userOrderService.getUserOrderLisAll());
    }

    @GetMapping("/getUserOrderListNotCompleteAll")
    public ResultContainer<List<UserOrderVO>> getUserOrderListNotCompleteAll() {
        return new ResultContainer<>(userOrderService.getUserOrderListNotCompleteAll());
    }

    @GetMapping("/getUserOrderListBookingByUserId")
    public ResultContainer<List<UserOrderVO>> getUserOrderListBookingByUserId(@RequestParam("userId") Integer userId) {
        return new ResultContainer<>(userOrderService.getUserOrderListBookingByUserId(userId));
    }

    @PostMapping("insertUserOrder")
    public void insertUserOder(@RequestBody UserOrderVO userOrderVO) {
        userOrderService.insertUserOder(userOrderVO);
    }

    @PostMapping("updateUserOrder")
    public void updateUserOrder(@RequestBody UserOrderVO userOrderVO) {
        userOrderService.updateUserOrder(userOrderVO);
    }

    @PostMapping("deleteUserOrderByUserId")
    public void deleteUserOrderByUserId(@RequestBody UserOrderVO userOrderVO) {
        userOrderService.deleteUserOrderByUserId(userOrderVO);
    }


}
