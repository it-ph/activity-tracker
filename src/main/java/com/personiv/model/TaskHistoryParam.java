package com.personiv.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaskHistoryParam {
	private Long userId;
	private Long supervisorId;	
	private Date date;
}
