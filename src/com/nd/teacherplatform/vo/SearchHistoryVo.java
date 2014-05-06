package com.nd.teacherplatform.vo;

import java.util.Date;

/**
 * 搜索历史记录实体
 * 
 * @author shj
 * 
 */
public class SearchHistoryVo {

	private int id;
	private int userID;
	private String Keyword;
	private Date OperateDate;

	public SearchHistoryVo() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String Keyword) {
		this.Keyword = Keyword;
	}

	public Date getOperateDate() {
		return OperateDate;
	}

	public void setOperateDate(Date OperateDate) {
		this.OperateDate = OperateDate;
	}
}
