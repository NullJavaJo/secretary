package com.nj.secretary.services.diary.impl;

import com.nj.secretary.services.calendar.domain.Calendar;
import com.nj.secretary.services.calendar.domain.IsDiary;
import com.nj.secretary.services.diary.domain.AttachFile;
import com.nj.secretary.services.diary.domain.Diary;
import com.nj.secretary.services.diary.domain.Report;
import com.nj.secretary.services.diary.repository.DiaryDAO;
import com.nj.secretary.services.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    @Qualifier("diaryDAOImpl")
    DiaryDAO diaryDAO;

    @Override
    public void addDiary(Diary diary){

        diaryDAO.addDiary(diary);
    }

    @Override
    public List<Diary> getDiaryList(Diary diary) {
        System.out.println("getDiaryList in DiaryServiceImpl start");
        List<Diary> list = diaryDAO.getDiaryList(diary);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);

        System.out.println("getDiaryList in DiaryServiceImpl finish");
        return list;
    }



    @Override
    public List<Diary> getTagDiaryList(String userId) {

        List<Diary> list = diaryDAO.getTagDiaryList(userId);


        return list;
    }


    @Override
    public List<Diary> getOthersDiaryList(Diary diary) {

        List<Diary> list = diaryDAO.getOthersDiaryList(diary);
        return list;
    }


    @Override
    public void moveToBin(int diaryId) {

        diaryDAO.moveToBin(diaryId);

    }

    @Override
    public List<Diary> getBinList(String userId) {

        return diaryDAO.getBinList(userId);
    }

    @Override
    public List<IsDiary> getDiaryEmotion(Calendar calendar) {
        return diaryDAO.getDiaryEmotion(calendar);
    }

    @Override
    public void addImage(Diary diary) {
        diaryDAO.addImage(diary);
    }

    @Override
    public int deleteDiary(int diaryId) {

        return diaryDAO.deleteDiary(diaryId);
    }

    @Override
    public int recoverDiary(int diaryId) {
        return diaryDAO.recoverDiary(diaryId);
    }

    @Override
    public int reportDiary(int diaryId) {

        return diaryDAO.reportDiary(diaryId);
    }

    @Override
    public int addReport(Report report) {
        return diaryDAO.addReport(report);
    }

    @Override
    public int checkReporter(Report report) {
        return diaryDAO.checkReporter(report);
    }

    @Override
    public int checkLike(Diary diary) {
        return diaryDAO.checkLike(diary);
    }

    @Override
    public Diary getDiary(int diaryNumber) {

        return diaryDAO.getDiary(diaryNumber);
    }

    @Override
    public void updateDiary(Diary diary) {

        diaryDAO.updateDiary(diary);
    }

    @Override
    public void addTag(String tag) {

        diaryDAO.addTag(tag);
    }

    @Override
    public List<Diary> getReportedDiaryList() {

        List<Diary> list =  diaryDAO.getReportedDiaryList();
        return list;
    }

    @Override
    public int setBlindDiary(int num) {
        diaryDAO.setBlindDiary(num);
        return 1;
    }

    @Override
    public List<Diary> getDiaryReportReason(int diaryId) {
        return diaryDAO.getDiaryReportReason(diaryId);
    }


    @Override
    public String getCount(Object message) {
        return diaryDAO.getCount(message);

    }

    @Override
    public List<Diary> getTagedList(Map map) {
        return diaryDAO.getTagedList(map);
    }

    @Override
    public int likeDiary(int diaryId) {
        return diaryDAO.likeDiary(diaryId);
    }

    @Override
    public int addLike(Diary diary) {
        return diaryDAO.addLike(diary);
    }

    @Override
    public int deleteTag(int diaryId) {
        return diaryDAO.deleteTag(diaryId);
    }

    @Override
    public List<AttachFile> getTags(int diaryId) {
        return diaryDAO.getTags(diaryId);
    }
    @Override
    public void updateImage(Diary diary) {
        diaryDAO.updateImage(diary);
    }

    @Override
    public void addThumb(Diary diary) {
        diaryDAO.addThumb(diary);
    }

    @Override
    public int isThumb(int diaryId) {
        return diaryDAO.isThumb(diaryId);
    }

    @Override
    public void deleteThumb(int diaryId) {
        diaryDAO.deleteThumb(diaryId);
    }

    @Override
    public String getEmotion(int emotionId) {
        return diaryDAO.getEmotion(emotionId);
    }

    @Override
    public void updateTag(AttachFile attachFile) {
        diaryDAO.updateTag(attachFile);
    }
}
