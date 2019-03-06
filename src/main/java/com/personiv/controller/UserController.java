package com.personiv.controller;

import com.personiv.model.User;
import com.personiv.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
  @Autowired
  private UserService userService;
  
  @RequestMapping(path={"/users/getNonAdminUsers"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<User> getNonAdminUsers()
  {
    return this.userService.getNonAdminUsers();
  }
  
  @RequestMapping(path={"/users"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<User> getUsers()
  {
    return this.userService.getusers();
  }
}
