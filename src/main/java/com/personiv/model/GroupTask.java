package com.personiv.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTask {
	
	private Long groupId;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<EmployeeTask> getMemberTasks() {
		return memberTasks;
	}
	public void setMemberTasks(List<EmployeeTask> memberTasks) {
		this.memberTasks = memberTasks;
	}
	private String groupName;
	private List<EmployeeTask> memberTasks;
	
}
