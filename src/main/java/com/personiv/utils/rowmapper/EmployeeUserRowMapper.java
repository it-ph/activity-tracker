package com.personiv.utils.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.personiv.model.Employee;
import com.personiv.model.EmployeeUser;
import com.personiv.model.Role;
import com.personiv.model.User;

public class EmployeeUserRowMapper implements RowMapper<EmployeeUser>{

	@Override
	public EmployeeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Long empId = rs.getLong("empId");
		String empNumber = rs.getString("empNumber");
		String empFName = rs.getString("empFName");
		String empMName = rs.getString("empMName");
		String empLName = rs.getString("empLName");
		String empSuffix = rs.getString("empSuffix");
		String empEmail = rs.getString("empEmail");
		String empPosition = rs.getString("empPosition");
		String empDepartment = rs.getString("empDepartment");
		Date empCreated = rs.getTimestamp("empCreated");
		Date empUpdated = rs.getTimestamp("empUpdated");
		
		Employee emp = new Employee(empId,empNumber,empFName,empMName,empLName,empSuffix,empEmail,empDepartment,empPosition,empCreated,empUpdated);
		
		
		Long userId = rs.getLong("userId");
		String username = rs.getString("username");
		String password = null;
		boolean nonLocked = rs.getBoolean("accountNonLocked");
		boolean nonExpired = rs.getBoolean("accountNonExpired");
		boolean credentialsNonExpired = rs.getBoolean("credentialsNonExpired");
		boolean enabled = rs.getBoolean("enabled");
		Date userCreated = rs.getTimestamp("userCreated");
		Date userUpdated = rs.getTimestamp("userUpdated");
		
		Long roleId = rs.getLong("roleId");
		String role = rs.getString("role");
		Date roleCreated = rs.getTimestamp("roleCreated");
		Date roleUpdated = rs.getTimestamp("roleUpdated");
		
		Role r = new Role(roleId,role,roleCreated,roleUpdated);
		
		User user = new User(userId,username,password,r,nonLocked,nonExpired,credentialsNonExpired,enabled,userCreated,userUpdated);
		
		Long empUserId = rs.getLong("empUserId");
		Date empUserCreated  = rs.getTimestamp("empUserCreated");
		Date empUserUpdated = rs.getTimestamp("empUserUpdated");
		
		EmployeeUser empUser = new EmployeeUser(empUserId,emp,user,empUserCreated,empUserUpdated);
		return empUser;
	}

}
