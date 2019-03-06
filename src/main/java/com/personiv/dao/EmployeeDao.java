package com.personiv.dao;

import com.personiv.model.Employee;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=false)
public class EmployeeDao
  extends JdbcDaoSupport
{
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private DataSource dataSource;
  
  @PostConstruct
  private void initialize()
  {
    setDataSource(this.dataSource);
    this.jdbcTemplate = getJdbcTemplate();
  }
  
  public List<Employee> getEmployees()
  {
    String query = "SELECT e.*  "+ 
    			   "  FROM employees e "+
    			   " ORDER BY e.id DESC";
    
    return this.jdbcTemplate.query(query, new BeanPropertyRowMapper<Employee>(Employee.class));
  }
  
  public Employee getEmployee(Long id)
  {
    String query = "SELECT e.*  \t\t  FROM employees e \t\t WHERE e.id = ?";
    
    return (Employee)this.jdbcTemplate.queryForObject(query, new Object[] { id }, new BeanPropertyRowMapper<Employee>(Employee.class));
  }
  
  public Employee getEmployeeByUserId(Long userId)
  {
    String query = "call _proc_getEmployeeByUserId(?)";
    return (Employee)this.jdbcTemplate.queryForObject(query, new Object[] { userId }, new BeanPropertyRowMapper<Employee>(Employee.class));
  }
  
  public void addEmployee(Employee employee)
  {
    String query = "INSERT INTO employees(employeeNumber,firstName,middleName,lastName,suffix,email,department,position) VALUES(?,?,?,?,?,?,?,?)";
    this.jdbcTemplate.update(query, new Object[] {
      employee.getEmployeeNumber(), 
      employee.getFirstName(), 
      employee.getMiddleName(), 
      employee.getLastName(), 
      employee.getSuffix(), 
      employee.getEmail(), 
      employee.getDepartment(), 
      employee.getPosition() });
  }
  
  public void editEmployee(Employee employee)
  {
    String query = "UPDATE employees SET employeeNumber =?,firstName =?,middleName =?,lastName =?, suffix=?, email=?, department =?, position =?,updatedAT = CURRENT_TIMESTAMP WHERE id =?";
    this.jdbcTemplate.update(query, new Object[] {
      employee.getEmployeeNumber(), 
      employee.getFirstName(), 
      employee.getMiddleName(), 
      employee.getLastName(), 
      employee.getSuffix(), 
      employee.getEmail(), 
      employee.getDepartment(), 
      employee.getPosition(), 
      employee.getId() });
  }
}
