package com.personiv.dao;

import com.personiv.model.Task;
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
public class TaskDao
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
  
  public List<Task> getTasks()
  {
    String query = "SELECT * FROM tasks ORDER BY createdAt desc";
    return this.jdbcTemplate.query(query, new BeanPropertyRowMapper<Task>(Task.class));
  }
  
  public Task getTask(Long id)
  {
    String query = "SELECT * FROM tasks where id =?";
    return (Task)this.jdbcTemplate.queryForObject(query, new Object[] { id }, new BeanPropertyRowMapper<Task>(Task.class));
  }
  
  public void addTask(Task task)
  {
    String query = "INSERT INTO tasks(taskName) VALUES(?)";
    this.jdbcTemplate.update(query, new Object[] { task.getTaskName() });
  }
  
  public void updateTask(Task task)
  {
    String query = "UPDATE tasks SET taskName = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
    this.jdbcTemplate.update(query, new Object[] { task.getTaskName(), task.getId() });
  }
  
  public List<Task> getPreferredTasks(Long id)
  {
    String sql = "SELECT t.* FROM tasks t JOIN task_preferrences tp on tp.taskId = t.id WHERE tp.userId =? ";
    return this.jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper<Task>(Task.class));
  }
  
  public void addPreferredTask(Task task, Long userId)
  {
    String sql = "INSERT INTO task_preferrences(taskId,userId) VALUES(?,?)";
    this.jdbcTemplate.update(sql, new Object[] { task.getId(), userId });
  }
  
  public void deleteTaskPref(Task task, Long userId)
  {
    String sql = "DELETE from task_preferrences WHERE taskId =? AND userId=?";
    this.jdbcTemplate.update(sql, new Object[] { task.getId(), userId });
  }
}
