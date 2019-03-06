package com.personiv.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaskSummary {
	private String employeeId;
	private String employeeName;
	private String team;
	private String task;
	private float duration;
}
