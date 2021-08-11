package com.nj.secretary.services.user.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class User {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;//사용자 이름

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min = 5, max = 20, message = "아이디를 5~20자 사이로 입력해주세요")
    private String userId;//사용자 아이디 : 중복불가

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;//사용자 비밀번호

    private String nickname;//사용자 닉네임 : 중복가능

    @Email(message = "Email should be valid")
    private String email;//이메일 : 본인인증을 위한 수단

    private int kakao;

    private String motto;//좌우명
    private int certificationNo;//인증번호
    private String roles;//사용자권한부여
    private String withdrawalDate;//탈퇴일
    private boolean withdrawalStatus;//탈퇴상태
    private int blindCount;//블라인드처리된 게시물 수
    private String limitStartDate;//공유제한일
    private String limitEndDate;//공유제한일
    private boolean isDisplayTime;//레이아웃 설정
    private boolean isDisplayWeather;//레이아웃 설정
    private boolean isDisplayLocation;//레이아웃 설정
    private int withdrawalReasonId;
    private String isAlarmDiary;
    private String isAlarmMonologue;
    private String withdrawalReasonType;
    private String withdrawalReasonText;


}