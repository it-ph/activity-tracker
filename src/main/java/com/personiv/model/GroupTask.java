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
	private String groupName;
	private List<EmployeeTask> memberTasks;
	
}
