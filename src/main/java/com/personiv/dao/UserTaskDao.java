package com.personiv.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.EmployeeTask;
import com.personiv.model.Period;
import com.personiv.model.Task;
import com.personiv.model.UserTask;
import com.personiv.utils.rowmapper.EmployeeTaskRowMapper;
import com.personiv.utils.rowmapper.UserTaskMapper;


@Repository
@Transactional(readOnly = false)
public class UserTaskDao  extends JdbcDaoSupport{
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
    
    
    public List<UserTask> getUserTasks(){
    	
    	String sql ="call _proc_getUserTasks()";
	    	
    	return jdbcTemplate.query(sql, new UserTaskMapper());
    }


	public UserTask getUserTask(Long id) {
		String sql ="call _proc_getUserTasksById(?)";
	    	
    	return jdbcTemplate.queryForObject(sql,new Object[] {id}, new UserTaskMapper());

	}


	public List<UserTask> getUserTaskByUserId(Long id) {
		String sql ="call _proc_getTasksByUser(?)";
	    	
    	return jdbcTemplate.query(sql,new Object[] {id}, new UserTaskMapper());

	}


	public void endUserTask(UserTask userTask) {
		String query ="UPDATE user_tasks set endDate = CURRENT_TIMESTAMP WHERE id =?";
		jdbcTemplate.update(query,new Object[] {userTask.getId()});
	}
	
	public void addUserTask(Long userId,Long taskId) {
		String query = "INSERT INTO user_tasks(userId,taskId,startDate) values(?,?,CURRENT_TIMESTAMP)";
		jdbcTemplate.update(query,new Object[] {userId,taskId});
	}


	public List<EmployeeTask> getTaskHistory(Period period) {
		String query = "call _proc_getTaskHistory(?,?)";		
		return jdbcTemplate.query(query,new Object[] {period.getStart(),period.getEnd()}, new EmployeeTaskRowMapper());
	}



}
