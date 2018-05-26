package com.midas2018.root.controller;

import com.midas2018.root.model.*;
import com.midas2018.root.service.CafeMenuService;
import com.midas2018.root.support.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Constant.Menu_API_URL)
public class CafeMenuController {

    @Autowired
    private CafeMenuService cafeMenuService;

    @PostMapping(value = "/insertMenu")
    public int insertMenu(@RequestBody CafeMenuVO cafeMenuVO){
        System.out.println("insertMenu/" + cafeMenuVO);
        return cafeMenuService.insertMenu(cafeMenuVO);
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
