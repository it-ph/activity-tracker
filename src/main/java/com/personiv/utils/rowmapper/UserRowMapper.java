package com.personiv.utils.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.personiv.model.Role;
import com.personiv.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long userId = rs.getLong("id");
		String username = rs.getString("username");
		String password = rs.getString("password");
		boolean nonLocked = rs.getBoolean("accountNonLocked");
		boolean nonExpired = rs.getBoolean("accountNonExpired");
		boolean credNonExpired = rs.getBoolean("credentialsNonExpired");
		boolean enabled = rs.getBoolean("enabled");
		Date createdAt = rs.getTimestamp("createdAt");
		Date updatedAt = rs.getTimestamp("updatedAt");
		
		Long roleId = rs.getLong("roleId");
		String roleName = rs.getString("role");
		Date roleCreated = rs.getTimestamp("roleCreated");
		Date roleUpdated = rs.getTimestamp("roleUpdated");
		
		Role role = new Role(roleId,roleName,roleCreated,roleUpdated);
		User u = new User(userId,username,password,role,nonLocked,nonExpired,credNonExpired,enabled,createdAt,updatedAt);
		
		return u;
	}

}
