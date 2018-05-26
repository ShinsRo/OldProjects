package com.midas2018.root.controller;

import com.midas2018.root.model.CafeMenuBean;
import com.midas2018.root.model.CafeMenuVO;
import com.midas2018.root.service.CafeMenuService;
import com.midas2018.root.support.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Constant.Menu_API_URL)
public class CafeMenuController {

    @Autowired
    private CafeMenuService cafeMenuService;

    @PostMapping(value = "/insertMenu")
    public int insertMenu(@RequestBody CafeMenuBean cafeMenuBean){
        System.out.println("insertMenu/" + cafeMenuBean);
        return 1;
        //return cafeMenuService.insertMenu(cafeMenuVO);
    }
    public int updateMenu(CafeMenuVO cafeMenuVO){
        return cafeMenuService.updateMenu(cafeMenuVO);
    }
    public CafeMenuVO selectMenu(@RequestParam int id){
        return cafeMenuService.selectMenu(id);
    }
    public ArrayList<CafeMenuVO> selectAllMenu(){
        return cafeMenuService.selectAllMenu();
    }


}
