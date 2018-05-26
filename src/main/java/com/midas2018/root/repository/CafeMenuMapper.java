package com.midas2018.root.repository;

import com.midas2018.root.model.CafeMenuVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CafeMenuMapper {

    int insertMenu(CafeMenuVO cafeMenuVO);
    int updateMenu(CafeMenuVO cafeMenuVO);
    int deleteMenu(int id);
    CafeMenuVO selectMenu(int id);
    ArrayList<CafeMenuVO> selectAllMenu();

}
