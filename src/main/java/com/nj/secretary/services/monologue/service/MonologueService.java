package com.nj.secretary.services.monologue.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface MonologueService {
	
	public void addQuestionText(String questionText) throws Exception;
	
	public int getQuestionId(int questionId) throws Exception;
	
	public String getQuestionText(String questionText) throws Exception;
	
	public void addMonologueText(String monologueText) throws Exception;
}
