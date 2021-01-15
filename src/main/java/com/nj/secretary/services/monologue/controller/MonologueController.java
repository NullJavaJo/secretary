package com.nj.secretary.services.monologue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nj.secretary.services.monologue.service.MonologueService;

@Controller
public class MonologueController {
	
	@Autowired
	MonologueService monologueService;
	
	
	@RequestMapping("home")
	public String addQuestionText(String questionText) throws Exception{
		return "home.html";
	}
	
}
