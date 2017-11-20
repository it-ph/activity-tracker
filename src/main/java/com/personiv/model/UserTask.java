package com.personiv.model;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTask {
	
	private Long id;
	private Date startDate;
	private Date endDate;
	private User user;
	private Task task;
	private Date createdAt;
	private Date updatedAt;
}
