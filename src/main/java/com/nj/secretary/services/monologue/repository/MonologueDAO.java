package com.nj.secretary.services.monologue.repository;



public interface MonologueDAO {
	
	
	public void addQuestionText(String questionText) throws Exception;
	
	public String getQuestionText(String questionText) throws Exception;

	public int getQeustionId(int questionId) throws Exception;
	
	public void addMonologueText(String monologueText) throws Exception;
	
	
		
}
