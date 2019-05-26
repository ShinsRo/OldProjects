package com.midas2018.root.service;

import com.midas2018.root.model.CafeMenuVO;
import com.midas2018.root.repository.CafeMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CafeMenuService {

    @Autowired
    private CafeMenuRepository cafeMenuRepository;

    public int insertMenu(CafeMenuVO cafeMenuVO){
        return cafeMenuRepository.insertMenu(cafeMenuVO);
    }
    public int updateMenu(CafeMenuVO cafeMenuVO){
        return cafeMenuRepository.updateMenu(cafeMenuVO);
    }
    public int deleteMenu(int id){ return cafeMenuRepository.deleteMenu(id); }
    public CafeMenuVO selectMenu(int id){
        return cafeMenuRepository.selectMenu(id);
    }
    public ArrayList<CafeMenuVO> selectAllMenu(){
        return cafeMenuRepository.selectAllMenu();
    }
}
