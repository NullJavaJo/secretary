package com.nj.secretary.services.monologue.repository;

import com.nj.secretary.services.monologue.domain.Monologue;


public interface MonologueDAO {
	
	
	public void addQuestionText(String questionText) throws Exception;
	
//	public int getQeustionId(int questionId) throws Exception;
	
	
	public void addMonologueText(String monologueText) throws Exception;
	
		
}
