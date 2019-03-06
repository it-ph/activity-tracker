package com.personiv.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.Period;
import com.personiv.model.TaskHistory;
import com.personiv.model.TaskSummary;

@Repository
@Transactional(readOnly=false)
public class SupervisorDao   extends JdbcDaoSupport{
	
	  private JdbcTemplate jdbcTemplate;
	  private final Log logger = LogFactory.getLog(getClass());
	  
	  @Autowired
	  private DataSource dataSource;
	  
	  @PostConstruct
	  private void initialize(){
	    setDataSource(this.dataSource);
	    this.jdbcTemplate = getJdbcTemplate();
	  }
	  
	  public List<TaskHistory> getSubbordinateHistory(Period period, Long id) {
		  
		  String sql ="SELECT ga.userId, g.groupName as team,"+
		  		 	  "       e.employeeNumber as employeeId,"+
				      "       CONCAT(e.firstName,' ', COALESCE(e.middleName,''),' ', e.lastName) as employeeName, "+
		  		 	  "       e.id, t.taskName as task,"+
				      "       ut.startDate as taskStart, "+
		  		 	  "       ut.endDate as taskEnd, "+
				      "       TIME_TO_SEC(TIMEDIFF(ut.endDate,ut.startDate))/3600  as duration" + 
				  	  "  FROM user_tasks ut" + 
				  	  "  JOIN tasks t ON ut.taskId = t.id" + 
				  	  "  JOIN users u ON ut.userId = u.id" + 
				  	  "  JOIN employee_user eu ON eu.userId = u.id" + 
				  	  "  JOIN employees e ON e.id = eu.employeeId" + 
				  	  "  JOIN group_members gm ON gm.userId = u.id" + 
				      "  JOIN groups g ON gm.groupId = g.id " + 
				  	  "  JOIN group_admins ga ON g.id = ga.groupId " + 
				  	  " WHERE ga.userId = ? AND DATE(ut.createdAt)  <= ? AND DATE(ut.createdAt) >= ? AND ut.endDate IS NOT NULL ";
		  return jdbcTemplate.query(sql,new Object[] { 
				  id,
				  period.getEnd(),
				  period.getStart()
		  		 }, new BeanPropertyRowMapper<TaskHistory>(TaskHistory.class));
	  }
	  public List<TaskSummary> getSubbordinateTaskSummary(Period period, Long id) {
		  
		  String sql ="SELECT ga.userId, g.groupName as team,"+
		  		 	  "       e.employeeNumber as employeeId,"+
				      "       CONCAT(e.lastName,', ', e.firstName,' ', COALESCE(e.middleName,'')) as employeeName, "+
		  		 	  "       e.id, t.taskName as task,"+
				      "       ut.startDate as taskStart, "+
		  		 	  "       ut.endDate as taskEnd, "+
				      "       SUM(TIME_TO_SEC(TIMEDIFF(ut.endDate,ut.startDate))/3600)  as duration" + 
				  	  "  FROM user_tasks ut" + 
				  	  "  JOIN tasks t ON ut.taskId = t.id" + 
				  	  "  JOIN users u ON ut.userId = u.id" + 
				  	  "  JOIN employee_user eu ON eu.userId = u.id" + 
				  	  "  JOIN employees e ON e.id = eu.employeeId" + 
				  	  "  JOIN group_members gm ON gm.userId = u.id" + 
				      "  JOIN groups g ON gm.groupId = g.id " + 
				  	  "  JOIN group_admins ga ON g.id = ga.groupId " + 
				  	  " WHERE ga.userId = ? AND DATE(ut.createdAt)  <= ? AND DATE(ut.createdAt) >= ? AND ut.endDate IS NOT NULL "+
				  	  "GROUP BY ut.taskId, ut.userId "+
				  	  "ORDER BY e.lastName ASC ";
		  return jdbcTemplate.query(sql,new Object[] { 
				  id,
				  period.getEnd(),
				  period.getStart()
		  		 }, new BeanPropertyRowMapper<TaskSummary>(TaskSummary.class));
	  }

}
