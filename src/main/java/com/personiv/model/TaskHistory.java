package com.personiv.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaskHistory {
	private String team;
	private String employeeId;
	private String employeeName;
	private String task;
	private Date taskStart;
	private Date taskEnd;
	private float duration;
}
