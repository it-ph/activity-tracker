package com.personiv.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.personiv.model.EmployeeTask;
import com.personiv.model.EmployeeUser;
import com.personiv.model.Group;
import com.personiv.model.GroupTask;
import com.personiv.utils.rowmapper.EmployeeTaskRowMapper;
import com.personiv.utils.rowmapper.EmployeeUserRowMapper;

@Repository
@Transactional(readOnly = false)
public class GroupDao  extends JdbcDaoSupport{

	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
    
    public List<Group> getGroups(){
    	String sql = "SELECT * FROM groups";
    	
    	List<Group> groups = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Group>(Group.class));
    	String memberSql = "call _proc_getGroupMembers(?)";
    	String adminSql = "call _proc_getGroupAdmins(?)";
    	
    	for(Group g: groups) {
    		
    		List<EmployeeUser> members = jdbcTemplate.query(memberSql, new Object[] {g.getId()},new EmployeeUserRowMapper());
    		g.setMembers(members);
    		
    		
    		List<EmployeeUser> admins = jdbcTemplate.query(adminSql, new Object[] {g.getId()},new EmployeeUserRowMapper());
    		g.setAdmin(admins);
    	}
 
    	return groups;
    }
    
    public List<GroupTask> getGroupTask(Long id){
    	
    	String sql = "select g.id 'groupId', g.groupName " + 
    	   			 "  from groups g" + 
    				 "  join group_admins ga on ga.groupId = g.id" + 
    				 "  join users u on ga.userId = u.id"+
    				 " where u.id =?";

    	
    	String empTaskSql ="select e.*,"+
    					   "       t.taskName, t.id taskId,t.createdAt taskCreated,t.updatedAT taskUpdated,"+
    					   "       ut.startDate,ut.endDate " + 
		    			   "  from employees e " + 
		    			   "  join employee_user eu on eu.employeeId = e.id " + 
		    			   "  join users u on eu.userId = u.id " + 
		    			   "  join group_members gm on gm.userId = u.id " + 
		    			   "  join groups g on gm.groupId = g.id" + 
		    			   "  join user_tasks ut on ut.userId = u.id " + 
		    			   "  join tasks t on ut.taskId = t.id " + 
		    			   "  where u.id = ? " + 
		    			  "  order by ut.createdAt desc limit 1; ";
    
    	
    	List<GroupTask> groupTasks = jdbcTemplate.query(sql,new Object[] {id}, new BeanPropertyRowMapper<GroupTask>(GroupTask.class));
    	
    
    	
    	for(GroupTask gt: groupTasks) {
    		List<EmployeeUser> members = jdbcTemplate.query("call _proc_getGroupMembers(?)", new Object[] {gt.getGroupId()},new EmployeeUserRowMapper());
    		
    		List<EmployeeTask> empTasks = new ArrayList<>();
    		
    		for(EmployeeUser empUser: members) {
    			
    			EmployeeTask empTask = null;
    			
    			try {
    				empTask = jdbcTemplate.queryForObject(empTaskSql, new Object[] {empUser.getUser().getId()},new EmployeeTaskRowMapper());
    				empTasks.add(empTask);
    				
    			}catch(Exception e) {
    				//e.printStackTrace();
    			}
    			
    		}
    		
    		System.out.println("MEMBERS: "+members.size());
    		gt.setMemberTasks(empTasks);
    		
    	}
    	
    	
    	
    	
    	
    	return groupTasks;
    }
    
    
    public Group getGroupById(Long id) {
    	String sql = "SELECT * FROM groups WHERE id =?";
    	Group group = jdbcTemplate.queryForObject(sql,new Object[] {id},new BeanPropertyRowMapper<Group>(Group.class));

    	return group;
    }
    
    public void addGroup(Group group) {
    	String sql = "INSERT INTO groups(groupName) VALUES(?)";
    	jdbcTemplate.update(sql,group.getGroupName());
    }
    
    public void deleteGroup(Group group) {
    	String sql = "DELETE FROM groups WHERE id =?";
    	jdbcTemplate.update(sql,new Object[] {group.getId()});
    }
    
   public void updateGroup(Group group) {
	   String sql = "UPDATE FROM groups SET groupName = ?,updatedAt = CURRENT_TIMESTAMP WHERE id =?";
   	   jdbcTemplate.update(sql,new Object[] {group.getId()});
   }
   
   public void addGroupMember(Long groupId,Long userId) {
	   String sql = "INSERT INTO group_members(groupId,userId) VALUES(?,?)";
	   jdbcTemplate.update(sql,new Object[] {groupId,userId});
   }
   
   public void addGroupAdmin(Long groupId,Long userId) {
	   String sql = "INSERT INTO group_admins(groupId,userId) VALUES(?,?)";
	   jdbcTemplate.update(sql,new Object[] {groupId,userId});
   }

	public void removeGroupAdmin(Long groupId, Long userId) {
		  String sql = "DELETE FROM group_admins WHERE groupId =? AND userId =?";
		  jdbcTemplate.update(sql,new Object[] {groupId,userId});	
	}
   
	public void removeGroupMember(Long groupId, Long userId) {
		  String sql = "DELETE FROM group_members WHERE groupId =? AND userId =?";
		  jdbcTemplate.update(sql,new Object[] {groupId,userId});	
	}

	public void editGroup(Group group){
		String sql = "UPDATE groups SET groupName =? WHERE id =?";
		jdbcTemplate.update(sql,new Object[] {group.getGroupName(),group.getId()});	
		
	}
 


   
}
