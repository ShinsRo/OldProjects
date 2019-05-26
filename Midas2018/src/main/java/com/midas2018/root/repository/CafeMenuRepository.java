package com.midas2018.root.repository;

import com.midas2018.root.model.CafeMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CafeMenuRepository {

    @Autowired
    private CafeMenuMapper cafeMenuMapper;

    public int insertMenu(CafeMenuVO cafeMenuVO){
        return cafeMenuMapper.insertMenu(cafeMenuVO);
    }
    public int updateMenu(CafeMenuVO cafeMenuVO){
        return cafeMenuMapper.updateMenu(cafeMenuVO);
    }
    public int deleteMenu(int id) { return cafeMenuMapper.deleteMenu(id); }
    public CafeMenuVO selectMenu(int id){
        return cafeMenuMapper.selectMenu(id);
    }
    public ArrayList<CafeMenuVO> selectAllMenu(){
        return cafeMenuMapper.selectAllMenu();
    }
}
