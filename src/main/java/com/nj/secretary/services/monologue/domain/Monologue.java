package com.nj.secretary.services.monologue.domain;

import java.util.Date;

public class Monologue{
	
	//field
	private String userId;           //사용자 아이디:중복X
//	private String questionText;     //1문내용
	private String monologueText;    //1답내용
//	private int questionId;          //1문고유번호
	private int monologueId;         //1답고유번호
	private int reportCount;         //신고수
	private boolean shareStatus;     //공유상태 0:default:false
	private boolean blindStatus;     //블라인드상태 0:default:false
	private Date monologueDate;      //1답작성일
	
	
	//constructor
	public Monologue() {
		
	}


	//Method
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getMonologueText() {
		return monologueText;
	}


	public void setMonologueText(String monologueText) {
		this.monologueText = monologueText;
	}




	public int getMonologueId() {
		return monologueId;
	}


	public void setMonologueId(int monologueId) {
		this.monologueId = monologueId;
	}


	public int getReportCount() {
		return reportCount;
	}


	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}


	public boolean isShareStatus() {
		return shareStatus;
	}


	public void setShareStatus(boolean shareStatus) {
		this.shareStatus = shareStatus;
	}


	public boolean isBlindStatus() {
		return blindStatus;
	}


	public void setBlindStatus(boolean blindStatus) {
		this.blindStatus = blindStatus;
	}


	public Date getMonologueDate() {
		return monologueDate;
	}


	public void setMonologueDate(Date monologueDate) {
		this.monologueDate = monologueDate;
	}


	@Override
	public String toString() {
		return "Monologue [userId=" + userId + ", monologueText=" + monologueText + ", monologueId=" + monologueId
				+ ", reportCount=" + reportCount + ", shareStatus=" + shareStatus + ", blindStatus=" + blindStatus
				+ ", monologueDate=" + monologueDate + "]";
	}


	
	
	
	
	
}



//
//
//import java.io.Serializable;
//import java.util.Date;
//
//import lombok.Data;
//
//@Data
//public class Monologue implements Serializable {
//	
//	private String userId;           //사용자 아이디:중복X
//	private String questionText;     //1문내용
//	private String monologueText;    //1답내용
//	private int questionId;          //1문고유번호
//	private int monologueId;         //1답고유번호
//	private int reportCount;         //신고수
//	private boolean shareStatus;     //공유상태 0:default:false
//	private boolean blindStatus;     //블라인드상태 0:default:false
//	private Date monologueDate;      //1답작성일
//	
//	
//	
//	
//	
//	
//}
