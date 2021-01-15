package com.nj.secretary.services.monologue.repository;

import com.nj.secretary.services.monologue.domain.Monologue;

public interface MonologueDAO {
	
	//Insert
	public void addQuestionText(String questionText) throws Exception;
	
	public void addMonologueText(String monologueText) throws Exception;
	
		
}
