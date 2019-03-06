package com.personiv.controller;

import com.personiv.model.EmployeeUser;
import com.personiv.model.ErrorResponse;
import com.personiv.model.Group;
import com.personiv.model.Role;
import com.personiv.model.User;
import com.personiv.service.EmployeeUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeUserController
{
  @Autowired
  private EmployeeUserService empUserService;
  
  @RequestMapping(path={"/accounts", "/accounts/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<EmployeeUser> getEmployeeUsers()
  {
    return this.empUserService.getEmployeeUsers();
  }
  
  @RequestMapping(path={"/accounts/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public EmployeeUser getEmployeeUserById(@PathVariable("id") Long id)
  {
    return this.empUserService.getEmployeeUserById(id);
  }
  
  @RequestMapping(path={"/accounts/changeRole"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> changeRole(@RequestBody EmployeeUser empUser)
  {
	  empUserService.changeRole(empUser);
    return ResponseEntity.ok(empUser);
  }
  
  
  @RequestMapping(path={"/accounts/supervisors/subbordinates/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<EmployeeUser> getSubbordinates(@PathVariable("id") Long id)
  {
    return this.empUserService.getSubbordinates(id);
  }
  
  @RequestMapping(path={"/group-members"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public List<EmployeeUser> getGroupMemberSelection(@RequestBody Group group)
  {
    return this.empUserService.getGroupMemberSelection(group.getId());
  }
  
  @RequestMapping(path={"/group-admins"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public List<EmployeeUser> getGroupAdminSelection(@RequestBody Group group)
  {
    return this.empUserService.getGroupAdminSelection(group.getId());
  }
  
  @RequestMapping(path={"/accounts/roles"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<Role> getUserRoles()
  {
    return this.empUserService.getUserRoles();
  }
  
  @RequestMapping(path={"/accounts"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> addEmployeeUser(@RequestBody EmployeeUser empUser)
  {
    try
    {
      this.empUserService.addEmployeeUser(empUser);
      
      return ResponseEntity.ok(empUser);
    }
    catch (DataIntegrityViolationException e) {}
    catch(Exception e) {
    	e.printStackTrace();
    }
    return ResponseEntity.status(422).body(new ErrorResponse("Duplicate Entry", empUser));
  }
  
  @RequestMapping(path={"/accounts"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> updateEmployeeUser(@RequestBody EmployeeUser empUser)
  {
    try
    {
      this.empUserService.updateEmployeeUser(empUser);
      return ResponseEntity.ok(empUser);
    }
    catch (DataIntegrityViolationException e) {}
   
    return ResponseEntity.status(422).body(new ErrorResponse("Duplicate Entry", empUser));
  }
  
  @RequestMapping(path={"/accounts/updateUser"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> updateUser(@RequestBody User user)
  {
    try
    {
      this.empUserService.updateUser(user);
      
      return ResponseEntity.ok(user);
    }
    catch (DataIntegrityViolationException e) {}
    return ResponseEntity.status(422).body(new ErrorResponse("Duplicate Entry", user));
  }
  
  @RequestMapping(path={"/accounts/enable"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> enableUser(@RequestBody EmployeeUser user)
  {
    this.empUserService.enableUser(user);
    return ResponseEntity.ok(user);
  }
  
  @RequestMapping(path={"/accounts/resetpassword"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> resetUser(@RequestBody EmployeeUser user)
  {
    this.empUserService.resetUser(user);
    return ResponseEntity.ok(user);
  }
  
  @RequestMapping(path={"/accounts/disable"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> disablUser(@RequestBody EmployeeUser user)
  {
    this.empUserService.disableUser(user);
    return ResponseEntity.ok(user);
  }
}
