package com.personiv.service;

import com.personiv.dao.UserDao;
import com.personiv.model.Role;
import com.personiv.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
  @Autowired
  private UserDao userDao;
  
  public List<User> getNonAdminUsers()
  {
    return this.userDao.getNonAdminUsers();
  }
  
  public void resetPassword(User user)
  {
    this.userDao.resetPassword(user);
  }
  
  public List<User> getusers()
  {
    return this.userDao.getUsers();
  }
  
  public User getUserByUsername(String username)
  {
    return this.userDao.getUserByUsername(username);
  }
  
  public List<Role> getUserRoles()
  {
    return this.userDao.getRoles();
  }
}
