package com.nj.secretary.services.diary.impl;

import com.nj.secretary.services.calendar.domain.Calendar;
import com.nj.secretary.services.calendar.domain.IsDiary;
import com.nj.secretary.services.diary.domain.AttachFile;
import com.nj.secretary.services.diary.domain.Diary;
import com.nj.secretary.services.diary.domain.Report;
import com.nj.secretary.services.diary.repository.DiaryDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("diaryDAOImpl")
public class DiaryDAOImpl implements DiaryDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void addDiary(Diary diary){

        System.out.println("addDiary in DiaryDAOImpl start");
        sqlSession.insert("DiaryMapper.addDiary", diary);
    }

    @Override
    public List<Diary> getDiaryList(Diary diary) {

        System.out.println("getDiaryList in DiaryDAOImpl start");

        List<Diary> list = sqlSession.selectList("DiaryMapper.getDiaryList", diary);


        return list;
    }

    @Override
    public List<Diary> getTagDiaryList(String userId) {

        List<Diary> list = sqlSession.selectList("DiaryMapper.getTagDiaryList", userId);

        return list;
    }

    @Override
    public List<Diary> getOthersDiaryList(Diary diary) {

        List<Diary> list = sqlSession.selectList("DiaryMapper.getOthersDiaryList",diary);

        return list;
    }

    @Override
    public void moveToBin(int diaryId) {
        sqlSession.update("DiaryMapper.moveToBin",diaryId);
    }

    @Override
    public List<Diary> getBinList(String userId) {

        return sqlSession.selectList("DiaryMapper.getBinList",userId);
    }

    @Override
    public Diary getDiary(int diaryNumber) {
        return sqlSession.selectOne("DiaryMapper.getDiary",diaryNumber);
    }

    @Override
    public void updateDiary(Diary diary) {
        sqlSession.update("DiaryMapper.updateDiary",diary);
    }

    @Override
    public void addTag(String tag) {
        sqlSession.insert("DiaryMapper.addTag", tag);
    }

    @Override

    public List<Diary> getReportedDiaryList() {
        List<Diary> list = sqlSession.selectList("DiaryMapper.getReportedDiaryList");

        return list;
    }

    @Override
    public int setBlindDiary(int num) {
        sqlSession.update("DiaryMapper.setBlindDiary", num);
        return 1;
    }

    @Override
    public List<IsDiary> getDiaryEmotion(Calendar calendar) {
        return sqlSession.selectList("DiaryMapper.getDiaryEmotion",calendar);

    }

    @Override
    public void addImage(Diary diary) {
        sqlSession.insert("DiaryMapper.addImage",diary);
    }

    @Override
    public int deleteDiary(int diaryId) {
        return sqlSession.delete("DiaryMapper.deleteDiary",diaryId);
    }

    @Override
    public int recoverDiary(int diaryId) {
        return sqlSession.update("DiaryMapper.recoverDiary",diaryId);
    }


    @Override
    public int reportDiary(int diaryId) {
        return sqlSession.update("DiaryMapper.reportDiary",diaryId);
    }

    @Override
    public int addReport(Report report) {
        return sqlSession.insert("DiaryMapper.addReport",report);
    }

    @Override
    public int checkReporter(Report report) {
        return sqlSession.selectOne("DiaryMapper.checkReporter",report);
    }

    @Override
    public void deleteThumb(int diaryId) {
        sqlSession.delete("DiaryMapper.deleteThumb",diaryId);
    }

    @Override
    public void updateTag(AttachFile attachFile) {
        sqlSession.insert("DiaryMapper.updateTag",attachFile);
    }

    @Override
    public List<Diary> getDiaryReportReason(int diaryId) {
        return sqlSession.selectList("DiaryMapper.getDiaryReportReason", diaryId);
    }

    @Override
    public String getCount(Object message) {
        return sqlSession.selectOne("DiaryMapper.getCount", message);
    }

    @Override
    public List<Diary> getTagedList(Map map) {
        return sqlSession.selectList("DiaryMapper.getTagedList", map);
    }

    @Override
    public int likeDiary(int diaryId) {
        return sqlSession.update("DiaryMapper.likeDiary",diaryId);
    }

    @Override
    public int addLike(Diary diary) {
        return sqlSession.insert("DiaryMapper.addLike",diary);
    }

    @Override
    public int checkLike(Diary diary) {
        return sqlSession.selectOne("DiaryMapper.checkLike",diary);
    }

    @Override
    public int deleteTag(int diaryId) {
        return sqlSession.delete("DiaryMapper.deleteTag",diaryId);
    }

    @Override
    public List<AttachFile> getTags(int diaryId) {
        List<AttachFile> list = sqlSession.selectList("DiaryMapper.getTags", diaryId);
        return list;
    }
    @Override
    public void updateImage(Diary diary) {
        sqlSession.update("DiaryMapper.updateImage",diary);
    }

    @Override
    public String getEmotion(int emotionId) {
        return sqlSession.selectOne("DiaryMapper.getEmotion",emotionId);
    }

    @Override
    public int isThumb(int diaryId) {
        return sqlSession.selectOne("DiaryMapper.isThum",diaryId);
    }

    @Override
    public void addThumb(Diary diary) {
        sqlSession.insert("DiaryMapper.addThumb",diary);
    }
}
