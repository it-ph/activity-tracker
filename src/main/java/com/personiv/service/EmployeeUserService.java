package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.EmployeeUserDao;
import com.personiv.model.EmployeeUser;
import com.personiv.model.Role;
import com.personiv.model.User;

@Service
public class EmployeeUserService {
	
	@Autowired
	private EmployeeUserDao employeeDao;
	
	public void addEmployeeUser(EmployeeUser empUser) {
		employeeDao.addEmployeeUser(empUser);
	}
	public void updateEmployeeUser(EmployeeUser empUser) {
		employeeDao.updateEmployeeUser(empUser);
	}
	
	public void updateUser(User user) {
		employeeDao.updateUser(user);
	}
	public List<EmployeeUser> getEmployeeUsers() {
		return employeeDao.getEmployeeUsers();
	}
	public void enableUser(EmployeeUser user) {
		employeeDao.enableUser(user);
		
	}
	public void disableUser(EmployeeUser user) {
		employeeDao.disableUser(user);
		
	}
	public List<Role> getUserRoles() {
		return employeeDao.getUserRoles();
	}
	public void resetUser(EmployeeUser user) {
		employeeDao.resetUser(user);
		
	}
	public List<EmployeeUser> getGroupMemberSelection(Long id) {
		return employeeDao.getGroupMemberSelection(id);
	}
	
	public List<EmployeeUser> getGroupAdminSelection(Long id) {
		return employeeDao.getGroupAdminSelection(id);
	}
}
