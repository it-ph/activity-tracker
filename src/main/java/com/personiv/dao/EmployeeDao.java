package com.personiv.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.Employee;

@Repository
@Transactional(readOnly = false)
public class EmployeeDao  extends JdbcDaoSupport{
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
   
    public List<Employee> getEmployees(){
    	
    	String query = "call _proc_getEmployees()";
    	
    	return jdbcTemplate.query(query, new BeanPropertyRowMapper<Employee>(Employee.class));
    }
    
    public Employee getEmployee(Long id) {
    	String query = "call _proc_getEmployeeById(?)"; 	
    	return jdbcTemplate.queryForObject(query,new Object[] {id},new BeanPropertyRowMapper<Employee>(Employee.class));
    	
    }
    
    public Employee getEmployeeByUserId(Long userId) {
    	String query ="call _proc_getEmployeeByUserId(?)";
    	return jdbcTemplate.queryForObject(query, new Object[] {userId},new BeanPropertyRowMapper<Employee>(Employee.class));
    	
    }

	public void addEmployee(Employee employee) {
		String query ="INSERT INTO employees(employeeNumber,firstName,middleName,lastName,suffix,email,department,position) VALUES(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(query,new Object[] {
												employee.getEmployeeNumber(),
												employee.getFirstName(),
												employee.getMiddleName(),
												employee.getLastName(),
												employee.getSuffix(),
												employee.getEmail(),
												employee.getDepartment(),
												employee.getPosition()
												});
		
		
		
	}

	public void editEmployee(Employee employee) {
		String query ="UPDATE employees SET employeeNumber =?,firstName =?,middleName =?,lastName =?, suffix=?, email=?, department =?, position =?,updatedAT = CURRENT_TIMESTAMP WHERE id =?";
		jdbcTemplate.update(query,new Object[] {
				employee.getEmployeeNumber(),
				employee.getFirstName(),
				employee.getMiddleName(),
				employee.getLastName(),
				employee.getSuffix(),
				employee.getEmail(),
				employee.getDepartment(),
				employee.getPosition(),
				employee.getId()
		});
	}




   
}
