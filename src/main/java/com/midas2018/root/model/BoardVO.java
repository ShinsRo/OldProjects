package com.midas2018.root.model;

import lombok.Data;

import java.util.Date;

@Data
public class BoardVO {
    private int boardNo;
    private String title;
    private String content;
    private String userId;
    private Date registerDate;
}
