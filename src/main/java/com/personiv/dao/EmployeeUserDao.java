package com.personiv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.EmployeeUser;
import com.personiv.model.Role;
import com.personiv.utils.rowmapper.EmployeeUserRowMapper;
@Repository
@Transactional(readOnly = false)
public class EmployeeUserDao extends JdbcDaoSupport{
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;
   
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }

    public List<EmployeeUser> getEmployeeUsers(){
    	String sql = "call _proc_getEmployeeUsers()";
    	return jdbcTemplate.query(sql, new EmployeeUserRowMapper());
    }
    
	public void addEmployeeUser(EmployeeUser empUser) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String userSql = "INSERT INTO users(username,password) VALUES(?,?)";
		
		String initials = empUser.getEmployee().getFirstName().toUpperCase().charAt(0)+""+
						  (empUser.getEmployee().getMiddleName().length() > 0 ? empUser.getEmployee().getMiddleName().toUpperCase().charAt(0)+"":"")+
						  (empUser.getEmployee().getLastName().length() > 0 ?empUser.getEmployee().getLastName().toUpperCase().charAt(0)+"":"");
		
		String password = passwordEncoder().encode(initials+ empUser.getEmployee().getEmployeeNumber());
		//String password =initials+ empUser.getEmployee().getEmployeeNumber();
		
		//insert new user row
    	jdbcTemplate.update(
    	    	new PreparedStatementCreator() {
    				@Override
    				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    					PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);						
    					ps.setString(1, empUser.getEmployee().getEmployeeNumber());
    					ps.setString(2,password);
    				 return ps;
    				}
    	    		
    	    	},
    	    	keyHolder);
    	
    	Long userId = (Long)keyHolder.getKey();
    	
		String empSql ="INSERT INTO employee_user(userId,employeeId)VALUES(?,?)";
		
		String roleSql ="INSERT INTO user_roles(userId,roleId) VALUES(?,?)";

		//add role to this user
		jdbcTemplate.update(roleSql,new Object[] {userId,empUser.getUser().getRole().getId()});
		
		//link this user to an employee
		jdbcTemplate.update(empSql,new Object[] {userId,empUser.getEmployee().getId()});
		
	}
	
	public void updateEmployeeUser(EmployeeUser empUser) {
		String sql ="UPDATE employee_user set userId = ?, employeeId = ?";
		jdbcTemplate.update(sql,new Object[] {empUser.getEmployee().getId(),empUser.getEmployee().getId()});
		
	}

	public void enableUser(EmployeeUser user) {
		String sql ="UPDATE users set enabled = 1 WHERE id =?";
		jdbcTemplate.update(sql,new Object[] {user.getUser().getId()});
	}
	
	public void disableUser(EmployeeUser user) {
		String sql ="UPDATE users set enabled = 0 WHERE id =?";
		jdbcTemplate.update(sql,new Object[] {user.getUser().getId()});
	}

	public List<Role> getUserRoles() {
		String sql ="SELECT * FROM roles WHERE roles.role != 'ADMIN'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
	}

	public void resetUser(EmployeeUser empUser) {
		// TODO Auto-generated method stub

		String initials = empUser.getEmployee().getFirstName().toUpperCase().charAt(0)+""+
						  (empUser.getEmployee().getMiddleName().length() > 0 ? empUser.getEmployee().getMiddleName().toUpperCase().charAt(0)+"":"")+
						  (empUser.getEmployee().getLastName().length() > 0 ?empUser.getEmployee().getLastName().toUpperCase().charAt(0)+"":"");
		
		String password = passwordEncoder().encode(initials+ empUser.getEmployee().getEmployeeNumber());
		
		String sql ="UPDATE users set password = ? WHERE id =?";
		
		jdbcTemplate.update(sql,new Object[] {password,empUser.getUser().getId()});
	}

	public List<EmployeeUser> getGroupMemberSelection(Long id) {
		String sql = "call _proc_getGroupMemberSelection(?)";
		return jdbcTemplate.query(sql,new Object[] {id}, new EmployeeUserRowMapper());
	}
	
	public List<EmployeeUser> getGroupAdminSelection(Long id) {
		String sql = "call _proc_getGroupAdminSelection(?)";
		return jdbcTemplate.query(sql,new Object[] {id}, new EmployeeUserRowMapper());
	}
	
}
