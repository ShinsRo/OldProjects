package com.midas2018.root.controller;

import com.midas2018.root.model.BoardVO;
import com.midas2018.root.service.BoardService;
import com.midas2018.root.support.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Constant.BOARD_API_URL)
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping(value = "/insertBoard")
    public int insertBoard(@RequestBody BoardVO boardVO){
        System.out.println("insertBoard");
        return boardService.insertBoard(boardVO);
    }

    @PostMapping(value = "/updateBoard")
    public int updateBoard(@RequestBody BoardVO boardVO){
        System.out.println("updateBoard");
        return boardService.updateBoard(boardVO);
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

    @GetMapping(value = "/deleteBoard")
    public int deleteBoard(@RequestParam int boardNo){
        System.out.println("deleteBoard");
        return boardService.deleteBoard(boardNo);
    }
}
