package com.personiv.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUser {
	private Long id;
	private Employee employee;
	private User user;
	private Date createdAt;
	private Date updatedAt;
}
