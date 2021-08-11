package com.nj.secretary.services.diary.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.nj.secretary.services.alarm.domain.Alarm;
import com.nj.secretary.services.alarm.service.AlarmService;
import com.nj.secretary.services.diary.domain.AttachFile;
import com.nj.secretary.services.diary.domain.Diary;
import com.nj.secretary.services.diary.service.DiaryService;
import com.google.gson.JsonObject;
import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.service.MonologueService;
import com.nj.secretary.services.user.domain.User;
import com.nj.secretary.services.user.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/diary/*")
public class DiaryController {

    @Autowired
    DiaryService diaryService;
    @Autowired
    MonologueService monologueService;
    @Autowired
    UserService userService;
    int startNumber = 0;
    int endNumber = startNumber+12;
    @GetMapping("addDiary")
    public String addDiary(){
        System.out.println("addDiary Start");
        return "diary/emotion";
    }

    @PostMapping("addDiarys")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String addDiary(@ModelAttribute("diary") Diary diary, Model model, @RequestParam(required = false) String tag_text,HttpSession session){
        System.out.println("shareStatus : " + diary.getShareStatus());
        if(session.getAttribute("user")==null){
            return "user/login";
        }
        User user = (User)session.getAttribute("user");
        diary.setUserId(user.getUserId());
        if(diary.getShareStatus().trim().equals("0,1")) {
            diary.setShareStatus("1");
        }
        System.out.println("diary : " + diary);
        System.out.println("다이어리 내용들 : " + diary.getDiaryTitle() + diary.getDiaryText());
        diaryService.addDiary(diary);
        diary.setEmotionImg(diaryService.getEmotion(diary.getEmotionNo()));
        System.out.println("tag : " + tag_text);
        if(tag_text == null) {
            System.out.println("값이 null임");
        }else{
            String[] tags = tag_text.split(",");
            List<String> tagsList = new ArrayList<String>();
            List<AttachFile> attachList = new ArrayList<AttachFile>();

            for (String tag : tags) {
                if (tag.trim() == "" || tag.trim() == null || tag.trim().length() == 0) {
                } else {
                    tagsList.add(tag);
                }
            }
            for (String tag : tagsList) {
                AttachFile attachFile = new AttachFile();
                diaryService.addTag(tag);
                System.out.println("들어가는 태그 : " + tag);
                attachFile.setFileName(tag);
                attachList.add(attachFile);
                System.out.println(attachFile);
                System.out.println(attachList);
            }
            System.out.println("tagsList : " + tagsList);
            System.out.println(attachList);
            model.addAttribute("tagList",attachList);
        }

        if(diary.getDiaryText().contains("src=")){
            if(diary.getDiaryText().contains("jpeg")){
                diary.setFileName(diary.getDiaryText().substring(diary.getDiaryText().indexOf("src=")+5,diary.getDiaryText().indexOf("src=")+65));
            }else{
                diary.setFileName(diary.getDiaryText().substring(diary.getDiaryText().indexOf("src=")+5,diary.getDiaryText().indexOf("src=")+64));
            }
            System.out.println(diary.getFileName());
            diaryService.addImage(diary);
        }
        model.addAttribute("user", "2");

        System.out.println("다이어리 추가 완료");

        return "diary/getDiary";
    }

    @GetMapping("write")
    public String writeDiary(Diary diary){
        System.out.println("들어왔다 가야지");

        return "diary/boardWrite";
    }

    @PostMapping(value = "fileUpload", produces = "application/json")
    @ResponseBody
    public JsonObject fileUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        System.out.println("파일 업로드 시작");

        JsonObject jsonObject = new JsonObject();

        String fileRoot = "./src/main/resources/static/images/diaryImage/";    //저장될 파일 경로로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
        System.out.println("오리지널 파일 이름 : " + originalFileName);
        // 랜덤 UUID+확장자로 저장될 savedFileName
        String savedFileName = UUID.randomUUID() + extension;

        System.out.println("저장될 파일 이름 : " + savedFileName);
        File targetFile = new File(fileRoot + savedFileName);

        System.out.println("타겟 파일 이름 : " + targetFile);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/images/diaryImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");
            System.out.println("addproperty해준 경로 : " + fileRoot);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	// 실패시 저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        System.out.println("파일 업로드 끝" + jsonObject);
        return jsonObject;

    }

    @GetMapping(value = "getDiaryList")
    public String listDiary(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "user/login";
        }
        System.out.println("listDiary start in controller");
        User user = (User)session.getAttribute("user");
        Diary diary = new Diary();
        diary.setUserId(user.getUserId());
        diary.setStartNum(startNumber);
        diary.setEndNum(endNumber);
        System.out.println(user);
       List<Diary> list = diaryService.getDiaryList(diary);
       System.out.println("list : " + list);
        model.addAttribute("list", list);
        model.addAttribute("user",user);
        System.out.println("listDiary controller 완료");
        return "diary/getDiaryList";
    }

    @GetMapping("getDiary")
    public String getDiary(@RequestParam("diaryNo") int diaryNo,Model model,HttpSession session){
        System.out.println(diaryNo);
        User user = (User)session.getAttribute("user");
        System.out.println(user);
        if(user.getUserId().equals(diaryService.getDiary(diaryNo).getUserId())){ //본인
            model.addAttribute("user", "0");
        }else{ //타인
            model.addAttribute("user", "1");
        }
        System.out.println(diaryService.getDiary(diaryNo));
        List<AttachFile> list = diaryService.getTags(diaryNo);
        System.out.println("tags list : " + list);
        model.addAttribute("tagList", list);
        model.addAttribute("diary",diaryService.getDiary(diaryNo));
        model.addAttribute("role", user.getRoles());
        return "diary/getDiary";
    }

    @GetMapping("updateDiary")
    public String updateDiary(@RequestParam("diaryId") int diaryNo, Model model,HttpSession session){
        System.out.println(diaryNo);
        System.out.println("update Diary");

        model.addAttribute("diary",diaryService.getDiary(diaryNo));
        return "diary/updateDiary";
    }

    @PostMapping("updateDiary")
    public String updateDiary(@ModelAttribute Diary diary, HttpSession session,Model model,@RequestParam(required = false) String tag_text){
        System.out.println("shareStatus : " + diary.getShareStatus());

        if(session.getAttribute("user")==null){
            return "user/login";
        }
        User user = (User)session.getAttribute("user");
        if(diary.getShareStatus().trim().equals("0,1") || diary.getShareStatus().trim().equals("1,1")) {
            diary.setShareStatus("1");
        }else{
            diary.setShareStatus("0");
        }
        if(diary.getDiaryText().contains("src=")){
            if(diary.getDiaryText().contains("jpeg")){
                diary.setFileName(diary.getDiaryText().substring(diary.getDiaryText().indexOf("src=")+5,diary.getDiaryText().indexOf("src=")+65));
            }else{
                diary.setFileName(diary.getDiaryText().substring(diary.getDiaryText().indexOf("src=")+5,diary.getDiaryText().indexOf("src=")+64));
            }
            if(diaryService.isThumb(diary.getDiaryId()) == 0){
                diaryService.addThumb(diary);
            }else{
                diaryService.updateImage(diary);
            }
            System.out.println(diary.getFileName());
        }else{
            diaryService.deleteThumb(diary.getDiaryId());
        }

        if(tag_text == null) {
            System.out.println("값이 null임");
        }else{
            String[] tags = tag_text.split(",");
            List<String> tagsList = new ArrayList<String>();
            List<AttachFile> attachList = new ArrayList<AttachFile>();

            for (String tag : tags) {
                if (tag.trim() == "" || tag.trim() == null || tag.trim().length() == 0) {
                } else {
                    tagsList.add(tag);
                }
            }
            for (String tag : tagsList) {
                AttachFile attachFile = new AttachFile();
                System.out.println("들어가는 태그 : " + tag);
                attachFile.setDiaryId(diary.getDiaryId());
                attachFile.setFileName(tag);
                attachList.add(attachFile);
                diaryService.updateTag(attachFile);
            }
        }

        diaryService.updateDiary(diary);
        Diary diary2 = diaryService.getDiary(diary.getDiaryId());
        if(user.getUserId().equals(diary2.getUserId())){ //본인
            model.addAttribute("user", "0");
        }else{ //타인
            model.addAttribute("user", "1");
        }
        List<AttachFile> list = diaryService.getTags(diary.getDiaryId());
        System.out.println("tags list : " + list);
        model.addAttribute("tagList", list);
        System.out.println("diary : " + diary);
        System.out.println("다이어리 내용들 : " + diary.getDiaryTitle() + diary.getDiaryText());
        model.addAttribute("diary",diary2);
        System.out.println("다이어리 추가 완료");

        return "diary/getDiary";

    }

    @GetMapping("binDiaryList")
    public String binDiaryList(HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            return "user/login";
        }

        model.addAttribute("diaryList",diaryService.getBinList(user.getUserId()));
        return "diary/binDiary";
    }


    @GetMapping("adminPost")
    public String adminPost(Model model){
        System.out.println("admin Post start");
            List<Diary> diaryList = diaryService.getReportedDiaryList();
        System.out.println("diaryList get finish");
            List<Monologue> monoList = monologueService.getReportedMonoList();
        System.out.println("diaryList : " + diaryList);
        System.out.println("monoList : " + monoList);

        model.addAttribute("diaryList", diaryList);
        model.addAttribute("monoList", monoList);

        return "diary/adminPost";
    }

    @PostMapping("deleteDiary")
    public String deleteDiary(int diaryId, Model model,HttpSession session){
        System.out.println(diaryId);
        int delete = diaryService.deleteDiary(diaryId);
        int deleteTag = diaryService.deleteTag(diaryId);
        if(session.getAttribute("user")==null){
            return "user/login";
        }
        System.out.println("listDiary start in controller");

        Diary diary = new Diary();
        diary.setStartNum(0);
        diary.setEndNum(12);
        User user = (User)session.getAttribute("user");
        diary.setUserId(user.getUserId());
        System.out.println(user);
        List<Diary> list = diaryService.getDiaryList(diary);
        System.out.println("list : " + list);
        model.addAttribute("list", list);
        model.addAttribute("user",user);
        System.out.println("listDiary controller 완료");

        return "diary/getDiaryList";

    }



    
}