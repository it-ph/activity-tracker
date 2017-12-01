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

import com.personiv.model.Period;
import com.personiv.model.Task;

@Repository
@Transactional(readOnly = false)
public class TaskDao extends JdbcDaoSupport{
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
    
    public List<Task> getTasks(){
    	String 	query = "SELECT * FROM tasks ORDER BY createdAt desc";
    	return jdbcTemplate.query(query,new BeanPropertyRowMapper<Task>(Task.class));
    }

	public Task getTask(Long id) {
		String query = "SELECT * FROM tasks where id =?";
		return jdbcTemplate.queryForObject(query,new Object[] {id},new BeanPropertyRowMapper<Task>(Task.class));
	}

	public void addTask(Task task){
		String query = "INSERT INTO tasks(taskName) VALUES(?)";
		jdbcTemplate.update(query,new Object[] {task.getTaskName()});
		
	}
	public void updateTask(Task task){
		String query = "UPDATE tasks SET taskName = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
		jdbcTemplate.update(query,new Object[] {task.getTaskName(),task.getId()});
	}

	

	
   
}
