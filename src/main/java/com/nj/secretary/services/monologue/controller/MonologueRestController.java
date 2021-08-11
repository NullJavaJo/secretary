package com.nj.secretary.services.monologue.controller;

import com.nj.secretary.services.alarm.domain.Alarm;
import com.nj.secretary.services.alarm.service.AlarmService;
import com.nj.secretary.services.diary.domain.Report;
import com.nj.secretary.services.diary.domain.Translate;
import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.service.MonologueService;
import com.nj.secretary.services.user.domain.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/restMonologue/*")
public class MonologueRestController {

    @Autowired
    MonologueService monologueService;
    @Autowired
    AlarmService alarmService;

    @GetMapping("getReportedMonologueList")
    public List<Monologue> adminPost(Model model){
        System.out.println("admin post in MonologueRestController start");
        List<Monologue> monologueList = monologueService.getReportedMonoList();

        model.addAttribute("monologueList", monologueList);

        return monologueList;
    }

    @PostMapping("addMonologue")
    public String addMonologue(@RequestBody Monologue monologue,HttpSession session) throws Exception {
        User user = (User)session.getAttribute("user");
        monologue.setUserId(user.getUserId());
        monologue.setShareStatus("0");
        System.out.println(monologue);
        monologueService.addMonologue(monologue);
        return "테스트중입니다.";
    }


    @PostMapping("setBlindMonologue")
    public int setBlindMonologue(@RequestBody String json){

        System.out.println("json check : " + json);
        String data[] = json.split("&");
        int monologueId = Integer.parseInt(data[0].split("=")[1]);
        String userId = data[1].split("=")[1];
        System.out.println("monologueId : " + monologueId);
        System.out.println("userId : " + userId);

        Alarm alarm = new Alarm();
        alarm.setUserId(userId);
        alarm.setMonologueId(monologueId);
        alarm.setAlarmType("2");
        alarm.setAlarmText("블라인드 처리된 1문1답이 있어요!");

       monologueService.setBlindMonologue(monologueId);
       alarmService.blindMonologueAlarm(alarm);
        System.out.println("setBlindMonologue from MonologueRestController finish");
        return 1;
    }

    @GetMapping("getMonologueReportReason")
    public List<Monologue> getMonologueReportReason(@RequestParam("monologueId") int monologueId){
        System.out.println("getMonologueReportReason in monologueRestController start");
        System.out.println("parameter check : " + monologueId);

        List<Monologue> list = monologueService.getMonologueReportReason(monologueId);
        System.out.println("getMonologueReportReason in monologueRestController Finish");

        return list;
    }

    @PostMapping("translateMonologue")
    public String translateMonologue(@RequestBody Translate translate){
        System.out.println(translate.getText());
        String clientId = "dKcP13Dm48SnFKYaKHjP";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "EI6YagCrnn";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(translate.getText(), "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+translate.getSource()+"&target="+translate.getTarget()+"&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();
            System.out.println(response.toString());
            Map<String, String> map = new HashMap<>();
            map.put("monologue", response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        return "";

    }

    @PostMapping("translateQuestion")
    public String translateQuestion(@RequestBody Translate translate){
        System.out.println(translate.getText());
        String clientId = "dKcP13Dm48SnFKYaKHjP";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "EI6YagCrnn";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(translate.getQuestion(), "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+translate.getSource()+"&target="+translate.getTarget()+"&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        return "";

    }
    
    @PostMapping("monologueReport")
    public String monologueReport(@RequestBody Report report, HttpSession session) throws Exception{
    	System.out.println("monologueReport restController에서 확인===="+report);
    	User user = (User)session.getAttribute("user");
    	report.setReporterId(user.getUserId());
    	if(monologueService.checkReporter(report) > 0){
    	    return "이미 신고한 게시물입니다.";
        }else{
    	    if(monologueService.reportMonologue(report.getMonologueId())==0){
    	        return "신고하는데 문제가 생겼습니다.";
            }else {
                monologueService.addReport(report);

                return "신고가 완료 되었습니다.";
            }
        }
    }
    

    @GetMapping("getOtherMonologueList")
	public List<Monologue> getOtherMonologueList(@RequestParam("shareStatus") String shareStatus, @RequestParam("userId") String userId, Model model, HttpSession session) throws Exception{
		System.out.println("getOtherMonologueList 시작해유");
//		  if(session.getAttribute("user")==null){
//	        }
		
		//User user = (User)session.getAttribute("user");
		
		System.out.println("getOtherMonologueList 확인이니이이이잉 :::" + shareStatus +":::::"+ userId);
		
		List<Monologue> otherMonologueList=monologueService.getOtherMonologueList(userId);
		
		
		model.addAttribute("otherMonologueList", otherMonologueList);
		
		System.out.println("monologueController getOtherMonologueList  ::  "+otherMonologueList);
		
		return otherMonologueList;
		
	}
    
    @GetMapping("getMonologueList")
	public List<Monologue> getMonologueList(@RequestParam("userId") String user) throws Exception{
    	System.out.println("monologueList restController 시작");
		
		List<Monologue> monologueList=monologueService.getMonologueList(user);
				
		System.out.println("monologue restController getMonologueList  ::  " + monologueList);
	
		return monologueList;
		
	}
    
	@GetMapping("deleteMonologue")
	public String deleteMonologue( Model model, HttpSession session) throws Exception{
		System.out.println("deleteMonologue GepMapping 시작 합니다잉~~");
		
		  if(session.getAttribute("user")==null){
	            return "user/login";
	        }
		
		User user = (User)session.getAttribute("user");
		
		
//		session.setAttribute("userId", "hyoeun");
//		
//		String userId=(String)session.getAttribute("userId");
		
		System.out.println("deleteMonologue에서 userId 확인이니이이이잉 :::"+user);
		
	    List<Monologue> monologueList02=monologueService.getMonologueList(user.getUserId());
		
		model.addAttribute("monologueList", monologueList02);
		
		System.out.println("monologueController deleteMonologue  ::  "+monologueList02);
		
		return "monologue/getMonologueList";
	}
    
    @GetMapping("deleteMonologue01")
	public String deleteMonologue(@RequestParam("monologueId") int monologueId, Model model, HttpSession session) throws Exception{
		System.out.println("deleteMonologue PostMapping 시작 합니다잉~~");
		
		monologueService.deleteMonologue(monologueId);
		
		System.out.println("deleteMonologue monologueId 확인 ::::  "+monologueId);
		
	  //  List<Monologue> monologueList=monologueService.getMonologueList((session.getAttribute("user")).toString());
		
		//model.addAttribute("monologueList", monologueList);
		
		//System.out.println("monologueController deleteMonologue  ::  "+monologueList);
		
		return "삭제하시겠습니까";
	}
}
