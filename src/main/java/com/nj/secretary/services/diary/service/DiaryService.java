package com.nj.secretary.services.diary.service;

import com.nj.secretary.services.calendar.domain.Calendar;
import com.nj.secretary.services.calendar.domain.IsDiary;
import com.nj.secretary.services.diary.domain.AttachFile;
import com.nj.secretary.services.diary.domain.Diary;
import com.nj.secretary.services.diary.domain.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DiaryService {

    public void addDiary(Diary diary);

    public List<Diary> getDiaryList(Diary diary);

    public List<Diary> getOthersDiaryList(Diary diary);

    public Diary getDiary(int diaryNumber);

    public void updateDiary(Diary diary);

    public void addTag(String tag);

    public List<Diary> getTagDiaryList(String userId);

    public void moveToBin(int diaryId);

    public List<Diary> getReportedDiaryList();

    public int setBlindDiary(int num);

    public List<Diary> getBinList(String userId);

    public List<IsDiary> getDiaryEmotion(Calendar calendar);


    public List<Diary> getDiaryReportReason(int diaryId);

    public String getCount(Object message);

    public void addImage(Diary diary);

    public List<Diary> getTagedList(Map map);

    public int likeDiary(int diaryId);

    public int addLike(Diary diary);

    public int deleteTag(int diaryId);

    public List<AttachFile> getTags(int diaryId);

    public void updateImage(Diary diary);

    public void addThumb(Diary diary);

    public int isThumb(int diaryId);

    public void deleteThumb(int diaryId);

    public String getEmotion(int emotionId);

    public void updateTag(AttachFile attachFile);














    public int deleteDiary(int diaryId);

    public int recoverDiary(int diaryId);

    public int reportDiary(int diaryId);

    public int addReport(Report report);

    public int checkReporter(Report report);

    public int checkLike(Diary diary);





}
