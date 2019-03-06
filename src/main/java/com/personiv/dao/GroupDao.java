package com.personiv.dao;

import com.personiv.model.EmployeeTask;
import com.personiv.model.EmployeeUser;
import com.personiv.model.Group;
import com.personiv.model.GroupTask;
import com.personiv.model.User;
import com.personiv.utils.rowmapper.EmployeeTaskRowMapper;
import com.personiv.utils.rowmapper.EmployeeUserRowMapper;
import java.util.ArrayList;
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

@Repository
@Transactional(readOnly=false)
public class GroupDao
  extends JdbcDaoSupport
{
  private JdbcTemplate jdbcTemplate;
  private final Log logger = LogFactory.getLog(getClass());
  @Autowired
  private DataSource dataSource;
  
  @PostConstruct
  private void initialize()
  {
    setDataSource(this.dataSource);
    this.jdbcTemplate = getJdbcTemplate();
  }
  
  public List<Group> getGroups()
  {
    String sql = "SELECT * FROM groups";
    
    List<Group> groups = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(Group.class));
    String memberSql = "SELECT e.id 'empId', "
    				 + "  	   e.employeeNumber 'empNumber', "
    				 + "	   e.firstName 'empFName', "
    				 + "       e.middleName 'empMName', "
    				 + "       e.lastName 'empLName', "
    				 + "       e.suffix 'empSuffix', "
    				 + "       e.email 'empEmail', "
    				 + "       e.position 'empPosition',"
    				 + "       e.department 'empDepartment', "
    				 + "       e.createdAt 'empCreated', "
    				 + "       e.updatedAt 'empUpdated', "
    				 + "       u.id 'userId', "
    				 + "       u.username, "
    				 + "       u.accountNonLocked, "
    				 + "       u.accountNonExpired, "
    				 + "       u.credentialsNonExpired, "
    				 + "       u.enabled, "
    				 + "       u.createdAt 'userCreated', "
    				 + "       u.updatedAt 'userUpdated', "
    				 + "       r.id 'roleId', "
    				 + "       r.role, "
    				 + "       r.createdAt 'roleCreated',"
    				 + "       r.updatedAt 'roleUpdated', "
    				 + "       eu.id 'empUserId', "
    				 + "       eu.createdAt 'empUserCreated', "
    				 + "       eu.updatedAt 'empUserUpdated' "
    				 + "  FROM employee_user eu "
    				 + "  JOIN employees e on eu.employeeId = e.id "
    				 + "  JOIN users u on eu.userId = u.id "
    				 + "  JOIN user_roles ur on ur.userId = u.id "
    				 + "  JOIN roles r on r.id = ur.roleId "
    				 + "  JOIN group_members gm on gm.userId = u.id "
    				 + "  JOIN groups g on gm.groupId = g.id "
    				 + " WHERE g.id = ?";
    
    String adminSql = " SELECT e.id 'empId', "
    		        + "   	   e.employeeNumber 'empNumber',       "
    		        + "		   e.firstName 'empFName', "
    		        + "		   e.middleName 'empMName', "
    		        + "		   e.lastName 'empLName', "
    		        + "		   e.suffix 'empSuffix',"
    		        + "		   e.email 'empEmail',"
    		        + "		   e.position 'empPosition',"
    		        + "		   e.department 'empDepartment',"
    		        + "		   e.createdAt 'empCreated',"
    		        + "		   e.updatedAt 'empUpdated',"
    		        + "		   u.id 'userId',"
    		        + "		   u.username,"
    		        + "		   u.accountNonLocked,"
    		        + "		   u.accountNonExpired,"
    		        + "		   u.credentialsNonExpired,"
    		        + "		   u.enabled,"
    		        + "		   u.createdAt 'userCreated',"
    		        + "		   u.updatedAt 'userUpdated',"
    		        + "		   r.id 'roleId',"
    		        + "		   r.role,"
    		        + "		   r.createdAt 'roleCreated',"
    		        + "		   r.updatedAt 'roleUpdated',"
    		        + "		   eu.id 'empUserId', "
    		        + "		   eu.createdAt 'empUserCreated', "
    		        + "  	   eu.updatedAt 'empUserUpdated'       "
    		        + "	  FROM employee_user eu       "
    		        + "	  JOIN employees e on eu.employeeId = e.id       "
    		        + "	  JOIN users u on eu.userId = u.id       "
    		        + "   JOIN user_roles ur on ur.userId = u.id       "
    		        + "   JOIN roles r on r.id = ur.roleId      "
    		        + "   JOIN group_admins ga on ga.userId = u.id       "
    		        + "   JOIN groups g on ga.groupId = g.id "
    		        + "	 WHERE g.id = ?";
    for (Group g : groups)
    {
      List<EmployeeUser> members = this.jdbcTemplate.query(memberSql, new Object[] { g.getId() }, new EmployeeUserRowMapper());
      g.setMembers(members);
      
      List<EmployeeUser> admins = this.jdbcTemplate.query(adminSql, new Object[] { g.getId() }, new EmployeeUserRowMapper());
      g.setAdmin(admins);
    }
    return groups;
  }
  
  public List<GroupTask> getGroupTask(Long id)
  {
    String sql = "select g.id 'groupId', g.groupName   from groups g  join group_admins ga on ga.groupId = g.id  join users u on ga.userId = u.id where u.id =?";
    
    String empTaskSql = "select e.*,       "
    				  + "		t.taskName, "
    				  + "		t.id taskId,"
    				  + "		t.createdAt taskCreated,"
    				  + "		t.updatedAT taskUpdated,"
    				  + "		ut.id,ut.startDate,"
    				  + "		ut.endDate,"
    				  + "		remarks   "
    				  + "  from employees e   "
    				  + "  join employee_user eu on eu.employeeId = e.id   "
    				  + "  join users u on eu.userId = u.id   "
    				  + "  join group_members gm on gm.userId = u.id   "
    				  + "  join groups g on gm.groupId = g.id  "
    				  + "  join user_tasks ut on ut.userId = u.id   "
    				  + "  join tasks t on ut.taskId = t.id   "
    				  + " where u.id = ?   "
    				  + "order by ut.createdAt desc limit 1; ";
    
    List<GroupTask> groupTasks = this.jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper(GroupTask.class));
    
    String sql2 = "SELECT e.id 'empId',"
    		    + "  	  e.employeeNumber 'empNumber', "
    		    + "   	  e.firstName 'empFName',"
    		    + "       e.middleName 'empMName', "
    		    + "       e.lastName 'empLName',"
    		    + "		  e.suffix 'empSuffix',"
    		    + "		  e.email 'empEmail',"
    		    + "		  e.position 'empPosition',"
    		    + "		  e.department 'empDepartment',"
    		    + "		  e.createdAt 'empCreated',"
    		    + "		  e.updatedAt 'empUpdated',"
    		    + "		  u.id 'userId', "
    		    + "		  u.username,"
    		    + "		  u.accountNonLocked,"
    		    + "		  u.accountNonExpired,"
    		    + "  	  u.credentialsNonExpired,"
    		    + "		  u.enabled,"
    		    + "	      u.createdAt 'userCreated',"
    		    + "		  u.updatedAt 'userUpdated',"
    		    + "		  r.id 'roleId',"
    		    + "		  r.role,"
    		    + "		  r.createdAt 'roleCreated', "
    		    + "		  r.updatedAt 'roleUpdated', "
    		    + "		  eu.id 'empUserId', "
    		    + "		  eu.createdAt 'empUserCreated', "
    		    + "		  eu.updatedAt 'empUserUpdated' "
    		    + "	 FROM employee_user eu "
    		    + "	 JOIN employees e on eu.employeeId = e.id "
    		    + " JOIN users u on eu.userId = u.id "
    		    + " JOIN user_roles ur on ur.userId = u.id "
    		    + " JOIN roles r on r.id = ur.roleId "
    		    + " JOIN group_members gm on gm.userId = u.id "
    		    + " JOIN groups g on gm.groupId = g.id "
    		    + "WHERE g.id = ?";
    for (GroupTask gt : groupTasks)
    {
      List<EmployeeUser> members = this.jdbcTemplate.query(sql2, new Object[] { gt.getGroupId() }, new EmployeeUserRowMapper());
      
      List<EmployeeTask> empTasks = new ArrayList();
      for (EmployeeUser empUser : members)
      {
        EmployeeTask empTask = null;
        try
        {
          empTask = (EmployeeTask)this.jdbcTemplate.queryForObject(empTaskSql, new Object[] { empUser.getUser().getId() }, new EmployeeTaskRowMapper());
          empTasks.add(empTask);
        }
        catch (Exception localException) {}
      }
      gt.setMemberTasks(empTasks);
    }
    return groupTasks;
  }
  
  public Group getGroupById(Long id)
  {
    String sql = "SELECT * FROM groups WHERE id =?";
    Group group = (Group)this.jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Group.class));
    
    return group;
  }
  
  public void addGroup(Group group)
  {
    String sql = "INSERT INTO groups(groupName) VALUES(?)";
    this.jdbcTemplate.update(sql, new Object[] { group.getGroupName() });
  }
  
  public void deleteGroup(Group group)
  {
    String sql = "DELETE FROM groups WHERE id =?";
    this.jdbcTemplate.update(sql, new Object[] { group.getId() });
  }
  
  public void updateGroup(Group group)
  {
    String sql = "UPDATE FROM groups SET groupName = ?,updatedAt = CURRENT_TIMESTAMP WHERE id =?";
    this.jdbcTemplate.update(sql, new Object[] { group.getId() });
  }
  
  public void addGroupMember(Long groupId, Long userId)
  {
    String sql = "INSERT INTO group_members(groupId,userId) VALUES(?,?)";
    this.jdbcTemplate.update(sql, new Object[] { groupId, userId });
  }
  
  public void addGroupAdmin(Long groupId, Long userId)
  {
    String sql = "INSERT INTO group_admins(groupId,userId) VALUES(?,?)";
    this.jdbcTemplate.update(sql, new Object[] { groupId, userId });
  }
  
  public void removeGroupAdmin(Long groupId, Long userId)
  {
    String sql = "DELETE FROM group_admins WHERE groupId =? AND userId =?";
    this.jdbcTemplate.update(sql, new Object[] { groupId, userId });
  }
  
  public void removeGroupMember(Long groupId, Long userId)
  {
    String sql = "DELETE FROM group_members WHERE groupId =? AND userId =?";
    this.jdbcTemplate.update(sql, new Object[] { groupId, userId });
  }
  
  public void editGroup(Group group)
  {
    String sql = "UPDATE groups SET groupName =? WHERE id =?";
    this.jdbcTemplate.update(sql, new Object[] { group.getGroupName(), group.getId() });
  }
}
