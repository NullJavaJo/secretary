package com.nj.secretary.services.monologue.repository;

import java.util.List;

import com.nj.secretary.services.diary.domain.Report;
import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.domain.Question;

public interface MonologueDAO {
	
	
	//Question
	
	public void addQuestion(String questionText) throws Exception;
	
	public Question getQuestionText(int questionId) throws Exception;

	public void deleteQuestion(int questionId) throws Exception;

	public List<Question> getQuestionList() throws Exception;

	public int randomCheck(Monologue monologue) throws Exception;

	public int checkMonologue(String userId) throws Exception;
	
	//monologue
	
	public void addMonologue(Monologue monologue) throws Exception;
	
	public void deleteMonologue(int monologueId) throws Exception;
	
	public List<Monologue> getMonologueList(String userId) throws Exception;
	
	public List<Monologue> getShareMonologueList(String userId) throws Exception;
	
	public List<Monologue> getOtherMonologueList(String userId) throws Exception;

	public void updateMonologue(Monologue monologue) throws Exception;
	
	public List<Monologue> getReportedMonoList();

	public int setBlindMonologue(int num);

	public Monologue getMonologue(int monologueId) throws Exception;
	
	public List<Monologue> getMonologueReportReason(int monologueId);
	
	public void addReport(Report report) throws Exception;

	public int checkReporter(Report report);

	public int reportMonologue(int monologueId);
	
	
	
		
}
