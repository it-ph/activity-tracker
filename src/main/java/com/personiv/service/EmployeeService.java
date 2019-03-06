package com.personiv.service;

import com.personiv.dao.EmployeeDao;
import com.personiv.model.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService
{
  @Autowired
  private EmployeeDao employeeDao;
  
  public List<Employee> getEmployees()
  {
    return this.employeeDao.getEmployees();
  }
  
  public Employee getEmployee(Long id)
  {
    return this.employeeDao.getEmployee(id);
  }
  
  public Employee getEmployeeByUserId(Long userId)
  {
    return this.employeeDao.getEmployeeByUserId(userId);
  }
  
  public void addEmployee(Employee employee)
  {
    this.employeeDao.addEmployee(employee);
  }
  
  public void editEmployee(Employee employee)
  {
    this.employeeDao.editEmployee(employee);
  }
}
