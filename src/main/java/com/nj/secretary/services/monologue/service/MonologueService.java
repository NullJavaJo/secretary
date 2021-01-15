package com.nj.secretary.services.monologue.service;

import org.springframework.stereotype.Service;

@Service
public interface MonologueService {
	
	public void addQuestionText(String questionText) throws Exception;
	
	public void addMonologueText(String monologueText) throws Exception;
}
