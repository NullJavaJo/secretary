package com.nj.secretary.services.monologue.controller;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.service.MonologueService;

@Controller
@RequestMapping("/monologue/*")
public class MonologueController {
	
	@Autowired
	 //@Qualifier("monologueServiceImpl")
	MonologueService monologueService;
	

	@GetMapping("addQuestionText")
	public String addQuestionText() throws Exception {
		System.out.println("monologueController addQuestionText 시작");
		return "home/home";
	}

	@PostMapping("addQuestionText")
	public String addQuestionText(String questionText) throws Exception {
		System.out.println("monologueController addQuestionText  " + questionText);
		monologueService.addQuestionText(questionText);

		return "home/home";

	}
	
	
	@GetMapping("addMonologueText")
	public String addMonologueText() throws Exception{
		System.out.println("monologueController addMonologueText 시작");
		return "monologue/addMonologue";
	}
	
	@PostMapping("addMonologueText")
	public String addMonologueText(String monologueText) throws Exception{
		System.out.println("monologueController   addMonologueText" +monologueText);
		monologueService.addMonologueText(monologueText);
		
		return "monologue/addMonologue";
		
	}
//	
//	
//	@GetMapping("getQuestionText")
//	public String getQuestionText(Model model) throws Exception{
//		List<>
//		System.out.println("monologueController   getQuestionText 시작");
//		
//	}

	
//	@PostMapping("getQuestionText")
//	public String getQuestionText(String questionText) throws Exception{
//		monologueService.addQuestionText(questionText);
//		return "monologue/addMonologue";
//	}
    
	
	//1문 고유 번호랑  1답 고유 번호랑 어케 묶지????음음음음음음흠흠흠
	//1답으로 return 할때 1문 고유번호 같이 넘겨야 1문 내용을 알죠~~
	//그럼 1문 고유번호랑 1문 내용이랑 list? map? 으로 묶어야 하나

}
