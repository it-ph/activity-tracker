package com.personiv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupEmployeeUser {
	private Group group;
	private EmployeeUser employeeUser;
}
