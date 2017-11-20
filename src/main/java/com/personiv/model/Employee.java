package com.personiv.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private Long id;
	private String employeeNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String email;
	private String department;
	private String position;
	private Date createdAt;
	private Date updatedAt;
	
}
