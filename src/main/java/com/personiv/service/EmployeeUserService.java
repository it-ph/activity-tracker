package com.personiv.service;

import com.personiv.dao.EmployeeUserDao;
import com.personiv.model.EmployeeUser;
import com.personiv.model.Role;
import com.personiv.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeUserService
{
  @Autowired
  private EmployeeUserDao employeeDao;
  
  public void addEmployeeUser(EmployeeUser empUser)
  {
    this.employeeDao.addEmployeeUser(empUser);
  }
  
  public void updateEmployeeUser(EmployeeUser empUser)
  {
    this.employeeDao.updateEmployeeUser(empUser);
  }
  
  public void updateUser(User user)
  {
    this.employeeDao.updateUser(user);
  }
  
  public List<EmployeeUser> getEmployeeUsers()
  {
    return this.employeeDao.getEmployeeUsers();
  }
  
  public EmployeeUser getEmployeeUserById(Long id)
  {
    return this.employeeDao.getEmployeeUserById(id);
  }
  
  public void enableUser(EmployeeUser user)
  {
    this.employeeDao.enableUser(user);
  }
  
  public void disableUser(EmployeeUser user)
  {
    this.employeeDao.disableUser(user);
  }
  
  public List<Role> getUserRoles()
  {
    return this.employeeDao.getUserRoles();
  }
  
  public void resetUser(EmployeeUser user)
  {
    this.employeeDao.resetUser(user);
  }
  
  public List<EmployeeUser> getGroupMemberSelection(Long id)
  {
    return this.employeeDao.getGroupMemberSelection(id);
  }
  
  public List<EmployeeUser> getGroupAdminSelection(Long id)
  {
    return this.employeeDao.getGroupAdminSelection(id);
  }
  
  public List<EmployeeUser> getSubbordinates(Long id)
  {
    return this.employeeDao.getSubbordinates(id);
  }

	public void changeRole(EmployeeUser empUser) {
		employeeDao.changeRole(empUser);
		
	}
}
