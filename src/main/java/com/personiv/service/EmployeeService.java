package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.EmployeeDao;
import com.personiv.model.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	public List<Employee> getEmployees(){
		return employeeDao.getEmployees();
	}
	
	public Employee getEmployee(Long id) {
		return employeeDao.getEmployee(id);
	}
	
	public Employee getEmployeeByUserId(Long userId) {
		return employeeDao.getEmployeeByUserId(userId);
	}

	public void addEmployee(Employee employee) {
		employeeDao.addEmployee(employee);
	}

	public void editEmployee(Employee employee) {
		employeeDao.editEmployee(employee);
		
	}


}
