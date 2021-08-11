package com.nj.secretary.services.monologue.impl;

import java.util.List;

import com.nj.secretary.services.diary.domain.Report;
import com.nj.secretary.services.monologue.domain.Monologue;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.domain.Question;
import com.nj.secretary.services.monologue.repository.MonologueDAO;

@Repository("monologueDAOImpl")
public class MonologueDAOImpl implements MonologueDAO{
	
	@Autowired
	SqlSession sqlsession;
	
	
	
	
	
	public MonologueDAOImpl() {
              System.out.println("default constructor �θ���~~~"+getClass());
 	}
	
	//=============================Question============================
	
	
	@Override
	public void addQuestion(String questionText) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("monologueDAOImpi  addQuestionText~~");
		sqlsession.insert("MonologueMapper.addQuestion", questionText);
	}
	
	
	@Override
	public List<Question> getQuestionList() throws Exception {
		// TODO Auto-generated method stub
		
		return sqlsession.selectList("MonologueMapper.getQuestoinList");
	}

	@Override
	public int randomCheck(Monologue monologue) throws Exception {
		return sqlsession.selectOne("MonologueMapper.randomCheck",monologue);
	}

	@Override
	public int checkMonologue(String userId) throws Exception {
		return sqlsession.selectOne("MonologueMapper.checkMonologue",userId);
	}


	@Override
	public Question getQuestionText(int questionId) throws Exception {
		
		return sqlsession.selectOne("MonologueMapper.getQuestionText", questionId);
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void deleteQuestion(int questionId) throws Exception {
		
		sqlsession.delete("MonologueMapper.deleteQuestion", questionId); 
		
	}
	
	
	
	
	//==============================Monologue===============================
	
	@Override
	public void addMonologue(Monologue monologue) throws Exception {
		System.out.println("monologueDAOImpl  addMonologueText~~~");
		sqlsession.insert("MonologueMapper.addMonologue", monologue);
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMonologue(Monologue monologue) throws Exception {
		
		sqlsession.update("MonologueMapper.updateMonologue", monologue);
		
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteMonologue(int monologueId) throws Exception {
		

		sqlsession.delete("MonologueMapper.deleteMonologue", monologueId);

		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Monologue> getMonologueList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectList("MonologueMapper.getMonologueList", userId);
	}


	@Override
	public List<Monologue> getReportedMonoList() {
		List<Monologue> list = sqlsession.selectList("MonologueMapper.getReportedMonoList");

		return list;
  	}
  
	@Override
	public Monologue getMonologue(int monologueId) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("MonologueMapper.getMonologue", monologueId);
	}


	@Override
	public int setBlindMonologue(int num) {

		sqlsession.update("MonologueMapper.setBlindMonologue", num);

		return 1;
	}

	@Override
	public List<Monologue> getShareMonologueList(String userId) throws Exception {
		
		return sqlsession.selectList("MonologueMapper.getShareMonologueList", userId);
	}

	@Override
	public List<Monologue> getOtherMonologueList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectList("MonologueMapper.getOtherMonologueList", userId);
	}


	@Override
	public List<Monologue> getMonologueReportReason(int monologueId) {
		return sqlsession.selectList("MonologueMapper.getMonologueReportReason", monologueId);
	}

	@Override
	public void addReport(Report report) throws Exception {
		sqlsession.insert("MonologueMapper.addReport", report);
		
	}

	@Override
	public int checkReporter(Report report) {
		return sqlsession.selectOne("MonologueMapper.checkReporter",report);
	}

	@Override
	public int reportMonologue(int monologueId) {
		return sqlsession.update("MonologueMapper.reportMonologue",monologueId);
	}
}
