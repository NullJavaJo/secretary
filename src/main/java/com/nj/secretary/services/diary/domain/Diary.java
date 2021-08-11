package com.nj.secretary.services.diary.domain;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class Diary {
    private int diaryId;
    private String userId;
    private  int emotionNo;
    private String emotionName;
    private String emotionImg;
    private int locationX;
    private int locationY;
    private String location;
    private String weather;
    private int fileId;
    private String fileName;
    private String imageName;
    private String fileStatus;
    private char blindStatus;
    private char deleteStatus;
    private String diaryTitle;
    private String diaryText;
    private int likeCount;
    private Date deleteDate;
    private String shareStatus;
    private Date diaryDate;
    private int reportCount;
    private String sortCondition;
    private String postGroup = "diary";
    private String reportReasonId;
    private String reporterId;
    private java.util.Date reportDate;
    private String reportText;
    private int startNum;
    private int endNum;

    public Diary() { }



}
