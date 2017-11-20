package com.personiv.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Group {
	private Long id;
	private String groupName;
	private List<EmployeeUser> members;
	private List<EmployeeUser> admin;
	private Date createdAt;
	private Date updatedAt;
}
