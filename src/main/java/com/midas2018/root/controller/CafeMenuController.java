package com.midas2018.root.controller;

import com.midas2018.root.model.CafeMenuVO;
import com.midas2018.root.model.CategoryVO;
import com.midas2018.root.model.OptionVO;
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
    public int insertMenu(CafeMenuVO cafeMenuVO){
        System.out.println("insertMenu/" + cafeMenuVO);

        CafeMenuVO tmp = new CafeMenuVO();
        CategoryVO ct = new CategoryVO(); ct.setId(1);
        OptionVO ot = new OptionVO(); ot.setName("shot"); ot.setPrice(500); ot.setCount(1);
        tmp.setCategory(ct);
        tmp.setOption(new OptionVO[]{ot});
        tmp.setName("Ice");
        return cafeMenuService.insertMenu(tmp);
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
