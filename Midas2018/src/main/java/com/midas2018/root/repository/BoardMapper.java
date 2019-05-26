package com.midas2018.root.repository;


import com.midas2018.root.model.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BoardMapper {

    int insertBoard(BoardVO boardVO);
    BoardVO selectBoard(int boardNo);
    ArrayList<BoardVO> selectAllBoard();
    int deleteBoard(int boardNo);
    int updateBoard(BoardVO boardVO);
}
