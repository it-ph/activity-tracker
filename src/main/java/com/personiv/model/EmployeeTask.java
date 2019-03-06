package com.personiv.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTask {
	private Long id;
	private Date startDate;	
	private Date endDate;
	private Employee employee;
	private Task task;
	private String remarks;
}
