package edu.mum.rideshare.data;


import java.util.List;

import edu.mum.rideshare.model.SysUser;


public class QueryFormDataUser extends QueryFormData{
	
	private String roleId; //
	private String loginName; //
	
	private String password;
	private SysUser  user; //
	
	private String firstName; //
	private String lastName; //
	
	
	private String templateData; //
	private String message; //
	private String userID; //
	
	private String isAdmin;
	
	private String deptId;
	private String deptName;
	
	private String fpStatus;
	
	private String seq;
	private List<QueryFormDataUser> subList;
	
	private String passwordOld;

	
	
	
	public String getPasswordOld() {
		return passwordOld;
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getFpStatus() {
		return fpStatus;
	}
	public void setFpStatus(String fpStatus) {
		this.fpStatus = fpStatus;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getTemplateData() {
		return templateData;
	}
	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<QueryFormDataUser> getSubList() {
		return subList;
	}
	public void setSubList(List<QueryFormDataUser> subList) {
		this.subList = subList;
	}
	
	
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
