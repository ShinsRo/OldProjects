package com.midas2018.root.repository;

import com.midas2018.root.model.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    public int insertBoard(BoardVO boardVO){
        return boardMapper.insertBoard(boardVO);
    }
    public BoardVO selectBoard(int boardNo){
        return boardMapper.selectBoard(boardNo);
    }
    public ArrayList<BoardVO> selectAllBoard(){
        return boardMapper.selectAllBoard();
    }
    public int deleteBoard(int boardNo){
        return boardMapper.deleteBoard(boardNo);
    }
    public int updateBoard(BoardVO boardVO){
        return boardMapper.updateBoard(boardVO);
    }
}
