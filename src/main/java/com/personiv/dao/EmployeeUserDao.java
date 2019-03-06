package com.personiv.dao;

import com.personiv.model.Employee;
import com.personiv.model.EmployeeUser;
import com.personiv.model.Role;
import com.personiv.model.User;
import com.personiv.utils.rowmapper.EmployeeUserRowMapper;

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

@Repository
@Transactional(readOnly=false)
public class EmployeeUserDao
  extends JdbcDaoSupport
{
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private DataSource dataSource;
  
  @Bean
  BCryptPasswordEncoder passwordEncoder()
  {
    return new BCryptPasswordEncoder();
  }
  
  @PostConstruct
  private void initialize()
  {
    setDataSource(this.dataSource);
    this.jdbcTemplate = getJdbcTemplate();
  }
  
  public List<EmployeeUser> getEmployeeUsers()
  {
    String sql = "SELECT e.id 'empId', "
			    + "  	 e.employeeNumber 'empNumber', "
				+ "  	 e.firstName 'empFName',    "
				+ " 	 e.middleName 'empMName', "
				+ " 	 e.lastName 'empLName', "
				+ " 	 e.suffix 'empSuffix', "
				+ " 	 e.email 'empEmail', "
				+ " 	 e.position 'empPosition', "
				+ " 	 e.department 'empDepartment', "
				+ " 	 e.createdAt 'empCreated', "
				+ " 	 e.updatedAt 'empUpdated',   "
				+ " 	 u.id 'userId',"
				+ " 	 u.username, "
				+ " 	 u.accountNonLocked, "
				+ " 	 u.accountNonExpired, "
				+ " 	 u.credentialsNonExpired, "
				+ " 	 u.enabled,"
				+ " 	 u.createdAt 'userCreated', "
				+ " 	 u.updatedAt 'userUpdated', "
				+ " 	 r.id 'roleId', "
				+ " 	 r.role, "
				+ " 	 r.createdAt 'roleCreated',"
				+ " 	 r.updatedAt 'roleUpdated', "
				+ " 	 eu.id 'empUserId', "
				+ " 	 eu.createdAt 'empUserCreated', "
				+ " 	 eu.updatedAt 'empUserUpdated' "
				+ " FROM employees e "
				+ " JOIN employee_user eu on eu.employeeId = e.id "
				+ " JOIN users u on eu.userId = u.id "
				+ " JOIN user_roles ur on ur.userId = u.id "
				+ " JOIN roles r on ur.roleId = r.id "
				+ "ORDER BY eu.createdAt desc";
    
    return this.jdbcTemplate.query(sql, new EmployeeUserRowMapper());
  }
  
  public void addEmployeeUser(EmployeeUser empUser)
  {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    
    String userSql = "INSERT INTO users(username,password) VALUES(?,?)";
    
  
    String initials = (empUser.getEmployee().getFirstName().toUpperCase().charAt(0)) + ""+
    				  (empUser.getEmployee().getMiddleName().length() > 0 ? empUser.getEmployee().getMiddleName().toUpperCase().charAt(0) : "") + 
    				  (empUser.getEmployee().getLastName().length() > 0 ? empUser.getEmployee().getLastName().toUpperCase().charAt(0) : "");
    
    String password = passwordEncoder().encode(initials + empUser.getEmployee().getEmployeeNumber());
    
//    this.jdbcTemplate.update(
//      new EmployeeUserDao.1(this, userSql, empUser, password), 
//      
//      keyHolder);
    
 	jdbcTemplate.update(
	    	new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);	
					ps.setString(1,empUser.getEmployee().getEmployeeNumber());
					ps.setString(2,password);
				 return ps;
				}
	    		
	    	},
	    	keyHolder);
    
    Long userId = (Long)keyHolder.getKey();
    
    String empSql = "INSERT INTO employee_user(userId,employeeId)VALUES(?,?)";
    
    String roleSql = "INSERT INTO user_roles(userId,roleId) VALUES(?,?)";
    
    this.jdbcTemplate.update(roleSql, new Object[] { userId, empUser.getUser().getRole().getId() });
    
    this.jdbcTemplate.update(empSql, new Object[] { userId, empUser.getEmployee().getId() });
  }
  
  public void updateEmployeeUser(EmployeeUser empUser)
  {
    String sql = "UPDATE employee_user set userId = ?, employeeId = ?";
    this.jdbcTemplate.update(sql, new Object[] { empUser.getEmployee().getId(), empUser.getEmployee().getId() });
  }
  
  public void updateUser(User user)
  {
    String sql = "UPDATE users  set password = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
    String password = passwordEncoder().encode(user.getPassword());
    
    this.jdbcTemplate.update(sql, new Object[] { password, user.getId() });
  }
  
  public void enableUser(EmployeeUser user)
  {
    String sql = "UPDATE users set enabled = 1 WHERE id =?";
    this.jdbcTemplate.update(sql, new Object[] { user.getUser().getId() });
  }
  
  public void disableUser(EmployeeUser user)
  {
    String sql = "UPDATE users set enabled = 0 WHERE id =?";
    this.jdbcTemplate.update(sql, new Object[] { user.getUser().getId() });
  }
  
  public List<Role> getUserRoles()
  {
    String sql = "SELECT * FROM roles WHERE roles.role != 'ADMIN'";
    return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
  }
  
  public void resetUser(EmployeeUser empUser)
  {

    String initials = empUser.getEmployee().getFirstName().toUpperCase().charAt(0) +""+
    				 (empUser.getEmployee().getMiddleName().length() > 0 ? empUser.getEmployee().getMiddleName().toUpperCase().charAt(0) : "") + 
    				 (empUser.getEmployee().getLastName().length() > 0 ? empUser.getEmployee().getLastName().toUpperCase().charAt(0) : "");
    
    String password = passwordEncoder().encode(initials + empUser.getEmployee().getEmployeeNumber());
    
    String sql = "UPDATE users set password = ? WHERE id =?";
    
    this.jdbcTemplate.update(sql, new Object[] { password, empUser.getUser().getId() });
  }
  
  public List<EmployeeUser> getGroupMemberSelection(Long id)
  {
    String sql = "SELECT e.id 'empId',"
    			+ "   	 e.employeeNumber 'empNumber', "
    			+ "		 e.firstName 'empFName',"
    			+ "  	 e.middleName 'empMName',"
    			+ "	     e.lastName 'empLName', "
    			+ "		 e.suffix 'empSuffix', "
    			+ "      e.email 'empEmail', "
    			+ "      e.position 'empPosition',"
    			+ "      e.department 'empDepartment',"
    			+ "      e.createdAt 'empCreated',"
    			+ "      e.updatedAt 'empUpdated',"
    			+ "      u.id 'userId',"
    			+ "      u.username, "
    			+ "      u.accountNonLocked,"
    			+ "      u.accountNonExpired,"
    			+ "      u.credentialsNonExpired,"
    			+ "      u.enabled,"
    			+ "      u.createdAt 'userCreated',"
    			+ "      u.updatedAt 'userUpdated',"
    			+ "      r.id 'roleId', "
    			+ "      r.role, "
    			+ "      r.createdAt 'roleCreated',"
    			+ "      r.updatedAt 'roleUpdated',"
    			+ "      eu.id 'empUserId', "
    			+ "      eu.createdAt 'empUserCreated', "
    			+ "      eu.updatedAt 'empUserUpdated' "
    			+ " FROM employees e "
    			+ " JOIN employee_user eu on eu.employeeId = e.id "
    			+ " JOIN users u on eu.userId = u.id "
    			+ " JOIN user_roles ur on ur.userId = u.id "
    			+ " JOIN roles r on ur.roleId = r.id "
    			+ "WHERE NOT EXISTS(SELECT gm.id FROM group_members gm  WHERE gm.userId =  u.id AND gm.groupId =? ) AND r.role = 'USER'";
    
    return this.jdbcTemplate.query(sql, new Object[] { id }, new EmployeeUserRowMapper());
  }
  
  public List<EmployeeUser> getGroupAdminSelection(Long id)
  {
    String sql = "SELECT e.id 'empId',"
    		   + "		 e.employeeNumber 'empNumber', "
    		   + " 	     e.firstName 'empFName',"
    		   + "  	 e.middleName 'empMName',"
    		   + "	     e.lastName 'empLName',"
    		   + "		 e.suffix 'empSuffix',"
    		   + "	     e.email 'empEmail',"
    		   + "  	 e.position 'empPosition',"
    		   + "	     e.department 'empDepartment',"
    		   + "	     e.createdAt 'empCreated',"
    		   + "	     e.updatedAt 'empUpdated', "
    		   + "		 u.id 'userId',"
    		   + "		 u.username, "
    		   + "		 u.accountNonLocked, "
    		   + " 		 u.accountNonExpired, "
    		   + "		 u.credentialsNonExpired, "
    		   + "		 u.enabled, "
    		   + "	     u.createdAt 'userCreated',"
    		   + "		 u.updatedAt 'userUpdated',"
    		   + "		 r.id 'roleId', "
    		   + "		 r.role,"
    		   + "		 r.createdAt 'roleCreated',"
    		   + "		 r.updatedAt 'roleUpdated', "
    		   + "		 eu.id 'empUserId', "
    		   + "		 eu.createdAt 'empUserCreated',"
    		   + "		 eu.updatedAt 'empUserUpdated'"
    		   + "  FROM employees e"
    		   + "  JOIN employee_user eu on eu.employeeId = e.id"
    		   + "  JOIN users u on eu.userId = u.id "
    		   + "  JOIN user_roles ur on ur.userId = u.id"
    		   + "  JOIN roles r on ur.roleId = r.id "
    		   + " WHERE NOT EXISTS (SELECT ga.id FROM group_admins ga  WHERE ga.userId =  u.id AND ga.groupId =?) AND r.role = 'SUPERVISOR'";
    
    return this.jdbcTemplate.query(sql, new Object[] { id }, new EmployeeUserRowMapper());
  }
  
  public List<EmployeeUser> getSubbordinates(Long supervisorId)
  {
    String sql = "SELECT   e.id 'empId',"
    		   + "  	   e.employeeNumber 'empNumber',"
    		   + "		   e.firstName 'empFName', "
    		   + "		   e.middleName 'empMName', "
    		   + "		   e.lastName 'empLName',"
    		   + "		   e.suffix 'empSuffix',"
    		   + "		   e.email 'empEmail', "
    		   + "		   e.position 'empPosition', "
    		   + "		   e.department 'empDepartment', "
    		   + "		   e.createdAt 'empCreated', "
    		   + "		   e.updatedAt 'empUpdated', "
    		   + "		   u.id 'userId',"
    		   + "		   u.username,"
    		   + "		   u.accountNonLocked,"
    		   + "		   u.accountNonExpired, "
    		   + "		   u.credentialsNonExpired,"
    		   + "		   u.enabled, "
    		   + "		   u.createdAt 'userCreated',"
    		   + "		   u.updatedAt 'userUpdated',"
    		   + "		   r.id 'roleId',"
    		   + "		   r.role, "
    		   + "		   r.createdAt 'roleCreated', "
    		   + "		   r.updatedAt 'roleUpdated',"
    		   + "		   eu.id 'empUserId',"
    		   + "		   eu.createdAt 'empUserCreated',"
    		   + "		   eu.updatedAt 'empUserUpdated',"
    		   + "		   ga.groupId,ga.userId "
    		   + "	  FROM employees e"
    		   + "	  JOIN employee_user eu on eu.employeeId = e.id"
    		   + "	  JOIN users u on eu.userId = u.id "
    		   + "    JOIN user_roles ur on ur.userId = u.id "
    		   + "    JOIN roles r on ur.roleId = r.id "
    		   + "    JOIN group_members gm on gm.userId = u.id "
    		   + "    JOIN groups g ON gm.groupId = g.id"
    		   + "    JOIN group_admins ga on ga.groupId = g.id "
    		   + "   WHERE ga.userId = ? "
    		   + "   GROUP BY e.id ";
    
    return this.jdbcTemplate.query(sql, new Object[] { supervisorId }, new EmployeeUserRowMapper());
  }
  
  public EmployeeUser getEmployeeUserById(Long id)
  {
    String sql = "SELECT e.id 'empId', "
    		   + "   	 e.employeeNumber 'empNumber',"
    		   + "	     e.firstName 'empFName', "
    		   + "		 e.middleName 'empMName', "
    		   + "		 e.lastName 'empLName', "
    		   + "		 e.suffix 'empSuffix',"
    		   + "	     e.email 'empEmail', "
    		   + "		 e.position 'empPosition',"
    		   + "		 e.department 'empDepartment',"
    		   + "		 e.createdAt 'empCreated',"
    		   + "		 e.updatedAt 'empUpdated',"
    		   + "		 u.id 'userId', "
    		   + "		 u.username, "
    		   + "		 u.accountNonLocked, "
    		   + "		 u.accountNonExpired,"
    		   + "  	 u.credentialsNonExpired, "
    		   + "		 u.enabled, "
    		   + "		 u.createdAt 'userCreated', "
    		   + "		 u.updatedAt 'userUpdated', "
    		   + "		 r.id 'roleId', "
    		   + "		 r.role, "
    		   + "		 r.createdAt 'roleCreated', "
    		   + "		 r.updatedAt 'roleUpdated', "
    		   + "		 eu.id 'empUserId', "
    		   + "		 eu.createdAt 'empUserCreated',"
    		   + "		 eu.updatedAt 'empUserUpdated' "
    		   + " FROM employees e "
    		   + " JOIN employee_user eu on eu.employeeId = e.id "
    		   + " JOIN users u on eu.userId = u.id"
    		   + " JOIN user_roles ur on ur.userId = u.id "
    		   + " JOIN roles r on ur.roleId = r.id "
    		   + "WHERE eu.id = ?";
    
    return (EmployeeUser)this.jdbcTemplate.queryForObject(sql, new Object[] { id }, new EmployeeUserRowMapper());
  }

public void changeRole(EmployeeUser empUser) {
	String sql ="UPDATE user_roles SET roleId = ? WHERE userId =?";
	jdbcTemplate.update(sql,new Object[] {
		empUser.getUser().getRole().getId(),
		empUser.getUser().getId()
	});
	
}
}
