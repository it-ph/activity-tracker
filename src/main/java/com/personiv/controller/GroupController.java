package com.personiv.controller;

import com.personiv.model.EmployeeUser;
import com.personiv.model.ErrorResponse;
import com.personiv.model.Group;
import com.personiv.model.GroupEmployeeUser;
import com.personiv.model.GroupTask;
import com.personiv.model.User;
import com.personiv.service.GroupService;
import com.personiv.service.UserService;
import com.personiv.utils.JwtTokenUtil;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController
{
  @Autowired
  private GroupService groupService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UserService userService;
  
  @RequestMapping(path={"/groups", "/groups/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<Group> getGroups()
  {
    return this.groupService.getGroups();
  }
  
  @RequestMapping(path={"/groups/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public Group getGroupById(@PathVariable("id") Long id)
  {
    try
    {
      return this.groupService.getGroupById(id);
    }
    catch (EmptyResultDataAccessException e) {}
    return null;
  }
  
  @RequestMapping(path={"/groups"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> addGroups(@RequestBody Group group)
  {
    try
    {
      this.groupService.addGroup(group);
      return ResponseEntity.ok(group);
    }
    catch (DataIntegrityViolationException ex)
    {
      return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry", group));
    }
    catch (Exception e) {}
    return ResponseEntity.status(500).body(group);
  }
  
  @RequestMapping(path={"/groups"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT}, produces={"application/json"})
  public ResponseEntity<?> editGroup(@RequestBody Group group)
  {
    try
    {
      this.groupService.editGroup(group);
      return ResponseEntity.ok(group);
    }
    catch (DataIntegrityViolationException ex)
    {
      return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry", group));
    }
    catch (Exception e) {}
    return ResponseEntity.status(500).body(group);
  }
  
  @RequestMapping(path={"/group-task/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<GroupTask> groupTask(@PathVariable("id") Long id)
  {
    return this.groupService.getGroupTask(id);
  }
  
  @RequestMapping(path={"/groups/addMember"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> addGroupMember(@RequestBody GroupEmployeeUser groupEmpUser)
  {
    try
    {
      this.groupService.addGroupMember(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
      return ResponseEntity.ok(groupEmpUser);
    }
    catch (DataIntegrityViolationException ex) {}
    return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry", groupEmpUser));
  }
  
  @RequestMapping(path={"/groups/addAdmin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> addGroupAddAdmin(@RequestBody GroupEmployeeUser groupEmpUser)
  {
    try
    {
      this.groupService.addGroupAdmin(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
      return ResponseEntity.ok(groupEmpUser);
    }
    catch (DataIntegrityViolationException ex) {}
    return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry", groupEmpUser));
  }
  
  @RequestMapping(path={"/groups/removeAdmin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> removeGroupAddAdmin(@RequestBody GroupEmployeeUser groupEmpUser)
  {
    this.groupService.removeGroupAdmin(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
    return ResponseEntity.ok(groupEmpUser);
  }
  
  @RequestMapping(path={"/groups/removeMember"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> removeGroupAddMember(@RequestBody GroupEmployeeUser groupEmpUser)
  {
    this.groupService.removeGroupMember(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
    return ResponseEntity.ok(groupEmpUser);
  }
  
  @RequestMapping(path={"/group-task"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<GroupTask> getMyTasks(Principal principal)
  {
    User user = null;
    List<GroupTask> groupTask = null;
    try
    {
      user = this.userService.getUserByUsername(principal.getName());
      groupTask = this.groupService.getGroupTask(user.getId());
    }
    catch (EmptyResultDataAccessException localEmptyResultDataAccessException) {}
    return groupTask;
  }
}
