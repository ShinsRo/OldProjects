package com.midas2018.root.service;

import com.midas2018.root.model.BoardVO;
import com.midas2018.root.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public int insertBoard(BoardVO boardVO){
        return boardRepository.insertBoard(boardVO);
    }

    public BoardVO selectBoard(int boardNo){
        return boardRepository.selectBoard(boardNo);
    }

    public ArrayList<BoardVO> selectAllBoard(){
        return boardRepository.selectAllBoard();
    }

    public int deleteBoard(int boardNo) {
        return boardRepository.deleteBoard(boardNo);
    }

    public int updateBoard(BoardVO boardVO){
        return boardRepository.updateBoard(boardVO);
    }

}
