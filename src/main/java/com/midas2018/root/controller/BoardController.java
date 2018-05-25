package com.midas2018.root.controller;

import com.midas2018.root.model.BoardVO;
import com.midas2018.root.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping(value = "/insertBoard")
    public int insertBoard(@RequestBody BoardVO boardVO){
        System.out.println("insertBoard");
        return boardService.insertBoard(boardVO);
    }

    @GetMapping(value = "/selectBoard")
    public BoardVO selectBoard(@RequestParam int boardNo){
        System.out.println("selectBoard");
        return boardService.selectBoard(boardNo);
    }

    @GetMapping(value = "/selectAllBoard")
    public ArrayList<BoardVO> selectBoard(){
        System.out.println("selectAllBoard");
        return boardService.selectAllBoard();
    }
}
