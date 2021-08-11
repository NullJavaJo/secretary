
package com.nj.secretary.services.monologue.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nj.secretary.services.diary.domain.Report;
import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.domain.Question;
import com.nj.secretary.services.monologue.repository.MonologueDAO;
import com.nj.secretary.services.monologue.service.MonologueService;

@Service
public class MonologueServiceImpl implements MonologueService {
	
	@Autowired
	@Qualifier("monologueDAOImpl")
	MonologueDAO monologueDAO;

	
	
	
	//=============================Question============================
	@Override
	public void addQuestion(String questionText) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("monologueServiceImpl   addQuestion@@");
		monologueDAO.addQuestion(questionText);
		
	}
	

	@Override
	public Question getQuestionText(int questionId) throws Exception {
		// TODO Auto-generated method stub
		return monologueDAO.getQuestionText(questionId);
	}
	
	
	@Override
	public void deleteQuestion(int monologueId) throws Exception {
		
    	monologueDAO.deleteQuestion(monologueId);
		
		
	}

	
	@Override
	public List<Question> getQuestionList() throws Exception {
		// TODO Auto-generated method stub
		return monologueDAO.getQuestionList();
	}

	@Override
	public int randomCheck(Monologue monologue) throws Exception {
		return monologueDAO.randomCheck(monologue);
	}

	@Override
	public int checkMonologue(String userId) throws Exception {
		return monologueDAO.checkMonologue(userId);
	}


	//==============================Monologue===============================

	
	@Override
	public void addMonologue(Monologue monologue) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("monologueServiceImpl   addMonologueT@@");
		monologueDAO.addMonologue(monologue);
		
	}
	
	
	@Override
	public void updateMonologue(Monologue monologue) throws Exception {
		// TODO Auto-generated method stub
		
		monologueDAO.updateMonologue(monologue);
		
	}


	@Override
	public void deleteMonologue(int monologueId) throws Exception {
		
		monologueDAO.deleteMonologue(monologueId);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Monologue getMonologue(int monologueId) throws Exception {
		// TODO Auto-generated method stub
		return monologueDAO.getMonologue(monologueId);
	}
  
	@Override
	public List<Monologue> getMonologueList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return monologueDAO.getMonologueList(userId);
	}
	
	
  @Override
	public List<Monologue> getReportedMonoList() {

		List<Monologue> list = monologueDAO.getReportedMonoList();
		return list;
	}
  @Override
	public int setBlindMonologue(int num){

		monologueDAO.setBlindMonologue(num);

		return 1;
	}


@Override
public List<Monologue> getShareMonologueList(String userId) throws Exception {
	// TODO Auto-generated method stub
	return monologueDAO.getShareMonologueList(userId);
}


@Override
public List<Monologue> getOtherMonologueList(String userId) throws Exception {
	// TODO Auto-generated method stub
	return monologueDAO.getOtherMonologueList(userId);
}



	@Override
	public List<Monologue> getMonologueReportReason(int monologueId) {
		return monologueDAO.getMonologueReportReason(monologueId);
	}


	@Override
	public void addReport(Report report) throws Exception {
		monologueDAO.addReport(report);
	}

	@Override
	public int checkReporter(Report report) {
		return monologueDAO.checkReporter(report);
	}

	@Override
	public int reportMonologue(int monologueId) {
		return monologueDAO.reportMonologue(monologueId);
	}

}

