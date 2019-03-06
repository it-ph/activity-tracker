package com.personiv.dao;

import com.personiv.model.EmployeeTask;
import com.personiv.model.Period;
import com.personiv.model.TaskHistoryParam;
import com.personiv.model.UserTask;
import com.personiv.utils.rowmapper.EmployeeTaskRowMapper;
import com.personiv.utils.rowmapper.UserTaskMapper;
import java.io.PrintStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=false)
public class UserTaskDao
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
  
  public void endTask(UserTask task)
  {
    String query = "UPDATE user_tasks SET endDate = CURRENT_TIMESTAMP, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
    this.jdbcTemplate.update(query, new Object[] { task.getId() });
  }
  
  public List<UserTask> getUserTasks()
  {
    String sql = "call _proc_getUserTasks()";
    
    return this.jdbcTemplate.query(sql, new UserTaskMapper());
  }
  
  public UserTask getUserTask(Long id)
  {
    String sql = "call _proc_getUserTasksById(?)";
    
    return (UserTask)this.jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserTaskMapper());
  }
  
  public List<EmployeeTask> getUserTaskByUserId(Long id)
  {
    String sql = "SELECT ut.id,"
    		   + "		ut.startDate,"
    		   + "		ut.endDate,"
    		   + "		ut.createdAt,"
    		   + "		ut.updatedAt,ut.remarks,"
    		   + "		u.id 'userId',"
    		   + "		u.username 'userName',"
    		   + "		u.accountNonLocked 'nonLocked',"
    		   + "		u.accountNonExpired 'nonExpired',"
    		   + "		u.credentialsNonExpired 'credNonExpired',"
    		   + "		u.enabled,"
    		   + "		u.createdAt 'userCreated',"
    		   + "		u.updatedAt 'userUpdated',"
    		   + "		r.id 'roleId',"
    		   + "		r.role,"
    		   + "		r.createdAt 'roleCreated',"
    		   + "		r.updatedAt 'roleUpdated',"
    		   + "		t.id 'taskId',"
    		   + "		t.taskName,"
    		   + "		t.createdAt 'taskCreated',"
    		   + "		t.updatedAt 'taskUpdated'"
    		   + " FROM user_tasks ut "
    		   + " JOIN users u ON ut.userId = u.id "
    		   + " JOIN user_roles ur  ON ur.userId = u.id "
    		   + " JOIN roles r ON ur.roleId = r.id"
    		   + " JOIN tasks t ON ut.taskId = t.id "
    		   + "WHERE u.id = ?"
    		   + "ORDER BY ut.id desc";
    
    sql = "SELECT ut.id,"
    	+ "		  ut.startDate,"
    	+ "		  ut.endDate,"
    	+ "		  ut.createdAt,"
    	+ "		  ut.updatedAt,"
    	+ "		  ut.remarks, "
    	+ "		  e.*,"
    	+ "		  u.id 'userId',"
    	+ "		  u.username 'userName',"
    	+ "		  u.accountNonLocked 'nonLocked',"
    	+ "		  u.accountNonExpired 'nonExpired',"
    	+ "		  u.credentialsNonExpired 'credNonExpired',"
    	+ "		  u.enabled,"
    	+ "		  u.createdAt 'userCreated',"
    	+ "		  u.updatedAt 'userUpdated',"
    	+ "		  r.id 'roleId',"
    	+ "		  r.role,"
    	+ "		  r.createdAt 'roleCreated',"
    	+ "		  r.updatedAt 'roleUpdated',"
    	+ "		  t.id 'taskId',"
    	+ "		  t.taskName,"
    	+ "		  t.createdAt 'taskCreated',"
    	+ "		  t.updatedAt 'taskUpdated' "
    	+ "  FROM user_tasks ut "
    	+ "  JOIN users u ON ut.userId = u.id "
    	+ "  JOIN user_roles ur  ON ur.userId = u.id "
    	+ "  JOIN roles r ON ur.roleId = r.id "
    	+ "  JOIN tasks t ON ut.taskId = t.id "
    	+ "  JOIN employee_user eu ON eu.userId = u.id "
    	+ "  JOIN employees e ON eu.employeeId = e.id "
    	+ " WHERE u.id =? AND Date(ut.createdAt) >= SUBDATE(CURDATE(),1) "
    	+ " ORDER BY ut.id DESC";
    
    return this.jdbcTemplate.query(sql, new Object[] { id }, new EmployeeTaskRowMapper());
  }
  
  public void endUserTask(UserTask userTask)
  {
    String query = "UPDATE user_tasks set endDate = CURRENT_TIMESTAMP WHERE id =?";
    this.jdbcTemplate.update(query, new Object[] { userTask.getId() });
  }
  
  public void addUserTask(Long userId, Long taskId)
  {
    String query = "INSERT INTO user_tasks(userId,taskId,startDate) values(?,?,CURRENT_TIMESTAMP)";
    this.jdbcTemplate.update(query, new Object[] { userId, taskId });
  }
  
  public EmployeeTask getEmployeeTask(String employeeNumber)
  {
    String query = "SELECT ut.id,"
    			 + "	   ut.startDate,"
    			 + "	   ut.endDate,"
    			 + "	   ut.createdAt,"
    			 + "	   ut.updatedAt,"
    			 + "	   ut.remarks,"
    			 + "	   e.*,"
    			 + "	   u.id 'userId',"
    			 + "	   u.username 'userName',"
    			 + "	   u.accountNonLocked 'nonLocked',"
    			 + "	   u.accountNonExpired 'nonExpired',"
    			 + "	   u.credentialsNonExpired 'credNonExpired',"
    			 + "	   u.enabled,"
    			 + "	   u.createdAt 'userCreated',"
    			 + "	   u.updatedAt 'userUpdated', "
    			 + "	   r.id 'roleId',"
    			 + "	   r.role,"
    			 + "	   r.createdAt 'roleCreated',"
    			 + "       r.updatedAt 'roleUpdated',"
    			 + "	   t.id 'taskId',"
    			 + "	   t.taskName,"
    			 + "       t.createdAt 'taskCreated',"
    			 + "       t.updatedAt 'taskUpdated' "
    			 + "  FROM user_tasks ut "
    			 + "  JOIN users u ON ut.userId = u.id "
    			 + "  JOIN user_roles ur  ON ur.userId = u.id "
    			 + "  JOIN roles r ON ur.roleId = r.id "
    			 + "  JOIN tasks t ON ut.taskId = t.id "
    			 + "  JOIN employee_user eu ON eu.userId = u.id "
    			 + "  JOIN employees e ON eu.employeeId = e.id "
    			 + " WHERE e.employeeNumber =?      "
    			 + " ORDER BY ut.id DESC LIMIT 1";
    
    return (EmployeeTask)this.jdbcTemplate.queryForObject(query, new Object[] { employeeNumber }, new EmployeeTaskRowMapper());
  }
  
  public List<EmployeeTask> getTaskHistory(Period period)
  {
	  System.out.println(period);
    String query = "SELECT ut.id,"
    			 + "	   ut.startDate,"
    			 + "	   ut.endDate,"
    			 + "	   ut.createdAt,"
    			 + "	   ut.updatedAt,"
    			 + "	   ut.remarks,"
    			 + "       e.*,"
    			 + "	   u.id 'userId',"
    			 + "	   u.username 'userName',"
    			 + "       u.accountNonLocked 'nonLocked',"
    			 + "	   u.accountNonExpired 'nonExpired',"
    			 + "	   u.credentialsNonExpired 'credNonExpired',"
    			 + "	   u.enabled,"
    			 + "	   u.createdAt 'userCreated',"
    			 + "	   u.updatedAt 'userUpdated',"
    			 + "	   r.id 'roleId',"
    			 + "	   r.role,"
    			 + "	   r.createdAt 'roleCreated',"
    			 + "	   r.updatedAt 'roleUpdated',"
    			 + "	   t.id 'taskId',"
    			 + "	   t.taskName,"
    			 + "	   t.createdAt 'taskCreated',"
    			 + "	   t.updatedAt 'taskUpdated' "
    			 + "  FROM user_tasks ut "
    			 + "  JOIN users u ON ut.userId = u.id "
    			 + "  JOIN user_roles ur  ON ur.userId = u.id"
    			 + "  JOIN roles r ON ur.roleId = r.id "
    			 + "  JOIN tasks t ON ut.taskId = t.id "
    			 + "  JOIN employee_user eu ON eu.userId = u.id"
    			 + "  JOIN employees e ON eu.employeeId = e.id "
    			 + " WHERE (? <=ut.endDate AND ? >= ut.startDate)";
    
    return this.jdbcTemplate.query(query, new Object[] { period.getStart(), period.getEnd() }, new EmployeeTaskRowMapper());
  }
  
  public List<EmployeeTask> getUserTaskHistory(TaskHistoryParam param)
  {
    String query = " SELECT e.*, "
    		     + "		t.id taskId, "
    		     + "		t.taskName, "
    		     + "		t.createdAt taskCreated, "
    		     + "		t.updatedAt taskUpdated, "
    		     + "		ut.id, "
    		     + "		ut.startDate, "
    		     + "		ut.endDate,"
    		     + "		ut.remarks    "
    		     + "   FROM user_tasks ut    "
    		     + "   JOIN tasks t ON ut.taskId = t.id  "
    		     + "   JOIN group_members gm ON ut.userId = gm.userId   "
    		     + "   JOIN group_admins ga ON gm.groupId = ga.groupId  "
    		     + "   JOIN employee_user eu ON ut.userId = eu.userId   "
    		     + "   JOIN employees e ON eu.employeeId = e.id "
    		     + "  WHERE DATE(ut.createdAt) = DATE(?)  AND ut.userId = ?  AND ga.userId = ?   GROUP BY ut.id   ORDER BY ut.createdAt DESC ";
    
    return this.jdbcTemplate.query(query, new Object[] { param.getDate(), param.getUserId(), param.getSupervisorId() }, new EmployeeTaskRowMapper());
  }
  
  public void addRemarks(EmployeeTask task)
  {
    System.out.println("TASK: " + task.getId());
    String sql = "UPDATE user_tasks SET remarks =? WHERE id=?";
    this.jdbcTemplate.update(sql, new Object[] { task.getRemarks(), task.getId() });
  }
}
