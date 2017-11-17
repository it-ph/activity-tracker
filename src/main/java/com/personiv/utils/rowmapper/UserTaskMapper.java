package com.personiv.utils.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.personiv.model.UserTask;
import com.personiv.model.Role;
import com.personiv.model.Task;
import com.personiv.model.User;
public class UserTaskMapper implements RowMapper<UserTask> {

	@Override
	public UserTask mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Long id = rs.getLong("id");
		
		Long userId = rs.getLong("userId");
		String userName = rs.getString("userName");
		boolean nonLocked = rs.getBoolean("nonLocked");
		boolean nonExpired = rs.getBoolean("nonExpired");
		boolean credNonExpired = rs.getBoolean("credNonExpired");
		boolean enabled = rs.getBoolean("enabled");
		Date userCreated = rs.getTimestamp("userCreated");
		Date userUpdated = rs.getTimestamp("userUpdated");
		
		Long taskId = rs.getLong("taskId");
		String taskName = rs.getString("taskName");
		Date taskCreated = rs.getTimestamp("taskCreated");
		Date taskUpdated = rs.getTimestamp("taskUpdated");
		
		Long roleId = rs.getLong("roleId");
		String roleName = rs.getString("role");
		Date roleCreated = rs.getTimestamp("roleCreated");
		Date roleUpdated = rs.getTimestamp("roleUpdated");
		
		
		Task task = new Task(taskId,taskName,taskCreated,taskUpdated);
		Role role = new Role(roleId,roleName,roleCreated,roleUpdated);
		
		User user = new User(userId,userName,null,role,nonLocked,nonExpired,credNonExpired,enabled,userCreated,userUpdated);
		
		Date startDate = rs.getTimestamp("startDate");
		Date endDate = rs.getTimestamp("endDate");
		Date createdAt = rs.getTimestamp("createdAt");
		Date updatedAt = rs.getTimestamp("updatedAt");
		
		UserTask userTask = new UserTask(id,startDate,endDate,user,task,createdAt,updatedAt);
		return userTask;
	}

}
