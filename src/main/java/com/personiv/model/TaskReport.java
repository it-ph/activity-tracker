package com.personiv.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaskReport {
	private String employeeNumber;
	private String employee;
	private String task;
	private Date start;
	private Date end;
	private String duration;
}
