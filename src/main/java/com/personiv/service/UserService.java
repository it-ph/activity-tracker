package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.UserDao;
import com.personiv.model.Role;
import com.personiv.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public List<User> getNonAdminUsers(){
		return userDao.getNonAdminUsers();
	}

	public void resetPassword(User user) {
		userDao.resetPassword(user);
		
	}

	public List<User> getusers() {
		return userDao.getUsers();
	}
	
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	public List<Role> getUserRoles() {
		return userDao.getRoles();
	}
}
