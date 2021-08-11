package com.nj.secretary.services.diary.repository;

import com.nj.secretary.services.calendar.domain.Calendar;
import com.nj.secretary.services.calendar.domain.IsDiary;
import com.nj.secretary.services.diary.domain.AttachFile;
import com.nj.secretary.services.diary.domain.Diary;
import com.nj.secretary.services.diary.domain.Report;

import java.util.List;
import java.util.Map;

public interface DiaryDAO {

    public void addDiary(Diary diary);

    public List<Diary> getDiaryList(Diary diary) ;

    public  List<Diary> getTagDiaryList(String userId);

    public List<Diary> getOthersDiaryList(Diary diary);

    public void moveToBin(int diaryId);

    public List<Diary> getBinList(String userId);

    public Diary getDiary(int DiaryNumber);

    public void updateDiary(Diary diary);

    public void addTag(String tag);

    public List<Diary> getReportedDiaryList();

    public int setBlindDiary(int num);

    public List<IsDiary> getDiaryEmotion(Calendar calendar);

    public void addImage(Diary diary);


    public List<Diary> getDiaryReportReason(int diaryId);

    public String getCount(Object message);

    public List<Diary> getTagedList(Map map);

    public int likeDiary(int diaryId);

    public int addLike(Diary diary);

    public int checkLike(Diary diary);

    public int deleteTag(int diaryId);

    public List<AttachFile> getTags(int diaryId);

    public void updateImage(Diary diary);

    public String getEmotion(int emotionId);

    public int isThumb(int diaryId);

    public void addThumb(Diary diary);

    public int deleteDiary(int diaryId);

    public int recoverDiary(int diaryId);

    public int reportDiary(int diaryId);

    public int addReport(Report report);

    public int checkReporter(Report report);

    public void deleteThumb(int diaryId);

    public void updateTag(AttachFile attachFile);
}
