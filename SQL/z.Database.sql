DROP TABLE ALARM;
DROP TABLE USERS;
DROP TABLE DIARY;
DROP TABLE CALENDAR;
DROP TABLE MONOLOGUE;
DROP TABLE QUESTIONS;
DROP TABLE TODOLIST;
DROP TABLE WITHDRAWALREASONS;
DROP TABLE GOALS;
DROP TABLE EMOTIONS;
DROP TABLE REPORT;
DROP TABLE ATTACHFILE;
DROP TABLE WEATHER;
DROP TABLE LIKES;


DROP sequence ALARM_SEQ;
DROP sequence DIARY_SEQ;
DROP sequence CALENDAR_SEQ;
DROP sequence MONOLOGUE_SEQ;
DROP sequence QUESTIONS_SEQ;
DROP sequence TODOLIST_SEQ;
DROP sequence WITHDRAWALREASONS_SEQ;
DROP sequence GOALS_SEQ;
DROP sequence EMOTIONS_SEQ;
DROP sequence REPORT_SEQ;
DROP sequence attachfile_seq;
DROP sequence WEATHER_SEQ;


CREATE SEQUENCE diary_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE monologue_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE calendar_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE goal_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE alarm_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE questions_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE todolist_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE withdrawalreasons_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE emotion_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE report_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE attachfile_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE weather_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE image_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE like_seq START WITH 1 INCREMENT BY 1;





CREATE TABLE ALARM
(
    ALARM_ID NUMBER(8) NOT NULL PRIMARY KEY,
    DIARY_ID NUMBER(8),
    MONOLOGUE_ID	NUMBER(8),
    USER_ID 	VARCHAR(100) NOT NULL,
    CHECK_STATUS	CHAR(1) DEFAULT 0,
    ALARM_TYPE	CHAR(1) NOT NULL,
    ALARM_TEXT	VARCHAR(100)
);

create table Image
(
    IMAGE_ID   NUMBER(8) not null
        constraint IMAGE_PK
        primary key,
    DIARY_ID   NUMBER(8),
    IMAGE_NAME VARCHAR2(100)
);

create table LIKES
(
    LIKE_ID      NUMBER(8) not null
        constraint LIKE_PK
        primary key,
    DIARY_ID     NUMBER(8),
    MONOLOGUE_ID NUMBER(8),
    LIKER_ID     VARCHAR2(30)
);


create table USERS
(
    USER_ID              VARCHAR2(30)  not null
        primary key,
    PASSWORD             VARCHAR2(30)  not null,
    USER_NAME            VARCHAR2(30)  not null,
    ROLES                 VARCHAR2(20) default 'USER',
    NICKNAME             VARCHAR2(30),
    EMAIL                VARCHAR2(100) not null,
    WITHDRAWAL_REASON_ID NUMBER(8),
    MOTTO                VARCHAR2(100),
    IS_ALARM_DIARY       CHAR         default 1,
    IS_ALARM_MONOLOGUE   CHAR         default 1,
    BLIND_COUNT          NUMBER(2),
    WITHDRAWAL_DATE      DATE,
    WITHDRAWAL_STATUS    CHAR         default 0,
    LIMIT_START_DATE     DATE,
    LIMIT_END_DATE       DATE,
    IS_DISPLAY_TIME      CHAR         default 1,
    IS_DISPLAY_WEATHER   CHAR         default 1,
    IS_DISPLAY_LOCATION  CHAR         default 1,
    KAKAO                NUMBER       default 0
);

CREATE TABLE DIARY
(
    DIARY_ID	NUMBER(8)	NOT NULL PRIMARY KEY,
    EMOTION_ID	NUMBER(8)	NOT NULL,
    USER_ID		VARCHAR2(30)	NOT NULL,
    BLIND_STATUS	CHAR(1)		DEFAULT 0,
    DELETE_STATUS	CHAR(1)		DEFAULT 0,
    DIARY_TITLE	VARCHAR2(30)	NOT NULL,
    DIARY_TEXT	CLOB		NOT NULL,
    LOCATION	VARCHAR2(200)	NOT NULL,
    WEATHER	VARCHAR2(200)	NOT NULL,
    LIKE_COUNT	NUMBER(5)	 DEFAULT 0,
    DELETE_DATE	DATE,
    SHARE_STATUS	CHAR(1)		 DEFAULT 0,
    REPORT_COUNT	NUMBER(5) DEFAULT 0,
    DIARY_DATE	DATE	NOT NULL,
    SORT_CONDITION	NUMBER(3)
);


create table CALENDAR
(
    CALENDAR_ID      NUMBER(8)    not null
        constraint CALENDAR_PK
        primary key,
    USER_ID          VARCHAR2(30) not null,
    CALENDAR_TITLE   VARCHAR2(50) not null,
    CALENDAR_DETAIL  VARCHAR2(200),
    START_DATE       VARCHAR2(20) not null,
    END_DATE         VARCHAR2(20),
    START_TIME       VARCHAR2(10),
    END_TIME         VARCHAR2(10),
    BACKGROUND_COLOR VARCHAR2(10),
    ALL_DAY          CHAR default null,
    IMAGE_URL        VARCHAR2(50)
);


CREATE TABLE MONOLOGUE
(
    MONOLOGUE_ID         NUMBER(8) ,
    QUESTION_ID              NUMBER(8)      NOT NULL,
    USER_ID                     VARCHAR2(30)    NOT NULL,
    SHARE_STATUS           CHAR(1)             DEFAULT 0,
    BLIND_STATUS            CHAR(1)                DEFAULT 0,
    MONOLOGUE_TEXT    VARCHAR2(2000)     NOT NULL,
    REPORT_COUNT        NUMBER(8)     DEFAULT 0,
    MONOLOGUE_DATE      DATE           NOT NULL,
    CONSTRAINT MONOLOGUE_PK PRIMARY KEY (MONOLOGUE_ID)
);


CREATE TABLE Questions
(
    question_id NUMBER(8) PRIMARY KEY,
    question_text VARCHAR2(500) NOT NULL
);



CREATE TABLE TODOLIST
(
    TODOLIST_ID NUMBER(8) PRIMARY KEY,
    USER_ID VARCHAR2(30) NOT NULL,
    FINISH_STATUS CHAR(1) DEFAULT 0,
    TODOLIST_TEXT VARCHAR2(100) NOT NULL,
    TODOLIST_DATE DATE NOT NULL
);


CREATE TABLE WITHDRAWALREASONS
(
    WITHDRAWAL_REASON_ID	NUMBER(8)	PRIMARY KEY,
    WITHDRAWAL_REASON_TYPE	CHAR(1)		NOT NULL,
    WITHDRAWAL_REASON_TEXT	VARCHAR2(1000)
);

CREATE TABLE GOALS
(
    GOAL_ID	NUMBER(8)	PRIMARY KEY,
    USER_ID		VARCHAR2(30)	NOT NULL,
    GOAL_TEXT	VARCHAR2(50),
    GOAL_DATE	DATE
);


CREATE TABLE EMOTIONS
(
    EMOTION_ID	NUMBER(8)	PRIMARY KEY,
    EMOTION_NAME	VARCHAR2(20) NOT NULL,
    EMOTION_IMG	VARCHAR2(100) NOT NULL
);


CREATE TABLE REPORT
(
    report_id NUMBER(8) PRIMARY KEY,
    report_reason_id CHAR(1) NOT NULL,
    diary_id NUMBER(8),
    monologue_id NUMBER(8),
    reporter_id VARCHAR2(30) NOT NULL,
    report_date DATE NOT NULL,
    report_text VARCHAR2(500)
);


CREATE TABLE AttachFile
(
    file_id NUMBER(8) PRIMARY KEY,
    diary_id NUMBER(8) NOT NULL,
    file_name VARCHAR2(100),
    file_status char(1) NOT NULL
);


CREATE TABLE WEATHER
(
    WEATHER_ID	NUMBER(8)	PRIMARY KEY,
    WEATHER_NAME	VARCHAR2(20)	NOT NULL,
    WEATHER_IMG	VARCHAR2(100)	NOT NULL
);

INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (1, '기쁨', '/images/emotions/really.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (2, '슬픔', '/images/emotions/sad.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (3, '화남', '/images/emotions/angry.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (4, '사랑', '/images/emotions/love.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (5, '심각', '/images/emotions/disappoint.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (6, '핑크', '/images/emotions/pink.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (7, '그냥그럼', '/images/emotions/soso.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (8, '아주좋음', '/images/emotions/very smile.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (9, '조금 핑크', '/images/emotions/little pink.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (10, '좋아요', '/images/emotions/goooood.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (11, '죽음', '/images/emotions/dead.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (12, '아픔', '/images/emotions/sick.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (13, '뭐야', '/images/emotions/wtf.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (14, '걱정', '/images/emotions/worry.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (15, '놀람', '/images/emotions/surprise.png');
INSERT INTO EMOTIONS (EMOTION_ID, EMOTION_NAME, EMOTION_IMG) VALUES (16, '무표정', '/images/emotions/nothing.png');


회원관리

INSERT
INTO USERS (user_id, user_name, password, email, role, nickname, withdrawal_reason_id, motto, is_alarm_diary, is_alarm_monologue, blind_count, withdrawal_date, withdrawal_status, limit_start_date, limit_end_date, is_display_time, is_display_weather, is_display_location)
VALUES ('admin','admin','1234','admin@secretary.com', 'admin', '관리자', null, '웃으면 복이와요', 1, 1, null, null, null, null, null, 1, 1, 1);

INSERT
INTO USERS (user_id, user_name, password, email, role, nickname, withdrawal_reason_id, motto, is_alarm_diary, is_alarm_monologue, blind_count, withdrawal_date, withdrawal_status, limit_start_date, limit_end_date, is_display_time, is_display_weather, is_display_location)
VALUES ('manager','manager','1234','manager@secretary.com', 'admin', '매니저', null, 'Napoleon Said....', 1, 1, null, null, null, null, null, 1, 1, 1);

INSERT
INTO USERS (user_id, user_name, password, email, role, nickname, withdrawal_reason_id, motto, is_alarm_diary, is_alarm_monologue, blind_count, withdrawal_date, withdrawal_status, limit_start_date, limit_end_date, is_display_time, is_display_weather, is_display_location)
VALUES ('user01','김유저','1111','user01@secretary.com', 'user', '나는유저다', null, '포기는 배추 셀 때나 쓰는 말', 1, 1, null, null, null, null, null, 1, 1, 1);

commit;