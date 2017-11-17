package com.personiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.personiv.model.Employee;
import com.personiv.model.ErrorResponse;
import com.personiv.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(path = {"/employees","/employees/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getEmployees(){
		return employeeService.getEmployees();
	}
	
	@RequestMapping(path = "/employees/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getEmployee(@PathVariable("id") Long id){
		
		try {
			return employeeService.getEmployee(id);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}catch(NumberFormatException ex) {
			return null;
		}
	}
	
	@RequestMapping(path = "/employees/addEmployee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
		try{
			employeeService.addEmployee(employee);
		}catch(Exception e) {
			ErrorResponse err = new ErrorResponse("Duplicate Entry",employee);
			e.printStackTrace();
			return ResponseEntity.status(422).body(err);
		}
		return ResponseEntity.ok(employee);
	}
	
	@RequestMapping(path = "/employees/editEmployee", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editEmployee(@RequestBody Employee employee){
		
		try{
			employeeService.editEmployee(employee);
		}catch(Exception e) {
			ErrorResponse err = new ErrorResponse("Duplicate Entry",employee);
			System.out.println(e.getMessage());
			return ResponseEntity.status(422).body(err);
		}
		return ResponseEntity.ok(employee);
	}

	
}
