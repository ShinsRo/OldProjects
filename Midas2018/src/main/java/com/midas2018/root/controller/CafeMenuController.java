package com.midas2018.root.controller;

import com.midas2018.root.model.*;
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
    public int insertMenu(@RequestBody CafeMenuVO cafeMenuVO){
        System.out.println("insertMenu/" + cafeMenuVO);
        return cafeMenuService.insertMenu(cafeMenuVO);
    }
    @PostMapping(value = "/updateMenu")
    public int updateMenu(@RequestBody CafeMenuVO cafeMenuVO){
        System.out.println("updateMenu/" + cafeMenuVO);
        return cafeMenuService.updateMenu(cafeMenuVO);
    }
    @GetMapping(value = "/deleteMenu")
    public int deleteMenu(@RequestParam int id){
        System.out.println("deleteMenu/" + id);
        return cafeMenuService.deleteMenu(id);
    }
    @GetMapping(value = "/selectMenu")
    public CafeMenuVO selectMenu(@RequestParam int id){
        System.out.println("selectMenu/" + id);
        return cafeMenuService.selectMenu(id);
    }
    @GetMapping(value = "/selectAll")
    public ArrayList<CafeMenuVO> selectAllMenu(){
        System.out.println("selectAll/");
        return cafeMenuService.selectAllMenu();
    }


}
