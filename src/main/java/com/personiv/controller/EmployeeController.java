package com.personiv.controller;

import com.personiv.model.Employee;
import com.personiv.model.ErrorResponse;
import com.personiv.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController
{
  @Autowired
  private EmployeeService employeeService;
  
  @RequestMapping(path={"/employees", "/employees/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<Employee> getEmployees()
  {
    return this.employeeService.getEmployees();
  }
  
  @RequestMapping(path={"/employees/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public Employee getEmployee(@PathVariable("id") Long id)
  {
    try
    {
      return this.employeeService.getEmployee(id);
    }
    catch (EmptyResultDataAccessException e)
    {
      return null;
    }
    catch (NumberFormatException ex) {}
    return null;
  }
  
  @RequestMapping(path={"/employees/addEmployee"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> addEmployee(@RequestBody Employee employee)
  {
    try
    {
      this.employeeService.addEmployee(employee);
    }
    catch (DataIntegrityViolationException e)
    {
      ErrorResponse err = new ErrorResponse("Duplicate Entry", employee);
      e.printStackTrace();
      return ResponseEntity.status(422).body(err);
    }
    return ResponseEntity.ok(employee);
  }
  
  @RequestMapping(path={"/employees/editEmployee"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> editEmployee(@RequestBody Employee employee)
  {
    try
    {
      this.employeeService.editEmployee(employee);
    }
    catch (DataIntegrityViolationException e)
    {
      ErrorResponse err = new ErrorResponse("Duplicate Entry", employee);
      return ResponseEntity.status(422).body(err);
    }
    return ResponseEntity.ok(employee);
  }
}
