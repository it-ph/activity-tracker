package com.personiv.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Group {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<EmployeeUser> getMembers() {
		return members;
	}
	public void setMembers(List<EmployeeUser> members) {
		this.members = members;
	}
	public List<EmployeeUser> getAdmin() {
		return admin;
	}
	public void setAdmin(List<EmployeeUser> admin) {
		this.admin = admin;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	private Long id;
	private String groupName;
	private List<EmployeeUser> members;
	private List<EmployeeUser> admin;
	private Date createdAt;
	private Date updatedAt;
}
