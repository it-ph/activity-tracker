package com.personiv.controller;

import com.personiv.model.ErrorResponse;
import com.personiv.model.Task;
import com.personiv.model.User;
import com.personiv.service.TaskService;
import com.personiv.service.UserService;
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
public class TaskController
{
  @Autowired
  private TaskService taskService;
  @Autowired
  private UserService userService;
  
  @RequestMapping(path={"/tasks"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<Task> getTasks()
  {
    return this.taskService.getTasks();
  }
  
  @RequestMapping(path={"/tasks/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public Task getTask(@PathVariable("id") Long id)
  {
    return this.taskService.getTask(id);
  }
  
  @RequestMapping(path={"/tasks"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> addTask(@RequestBody Task task)
  {
    try
    {
      this.taskService.addTask(task);
    }
    catch (DataIntegrityViolationException e)
    {
      ErrorResponse err = new ErrorResponse("Duplicate Entry", task);
      return ResponseEntity.status(422).body(err);
    }
    return ResponseEntity.ok(task);
  }
  
  @RequestMapping(path={"/tasks"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT}, produces={"application/json"})
  public ResponseEntity<?> updateTask(@RequestBody Task task)
  {
    try
    {
      this.taskService.updateTask(task);
    }
    catch (DataIntegrityViolationException e)
    {
      ErrorResponse err = new ErrorResponse("Duplicate entry", task);
      return ResponseEntity.status(422).body(err);
    }
    return ResponseEntity.ok(task);
  }
  
  @RequestMapping(path={"/tasks/preferrences"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public ResponseEntity<?> gePreferredTask(Principal principal)
  {
    User user = null;
    List<Task> preferredTasks = null;
    try
    {
      user = this.userService.getUserByUsername(principal.getName());
      if (user != null) {
        preferredTasks = this.taskService.getPreferredTasks(user.getId());
      }
    }
    catch (EmptyResultDataAccessException localEmptyResultDataAccessException) {}
    return ResponseEntity.ok(preferredTasks);
  }
  
  @RequestMapping(path={"/tasks/preferrences"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, consumes={"application/json"}, produces={"application/json"})
  public ResponseEntity<?> addPreferredTask(@RequestBody Task task, Principal principal)
  {
    User user = null;
    try
    {
      user = this.userService.getUserByUsername(principal.getName());
      if (user != null) {
        this.taskService.addPreferredTask(task, user.getId());
      }
    }
    catch (EmptyResultDataAccessException localEmptyResultDataAccessException) {}
    return ResponseEntity.ok(task);
  }
  
  @RequestMapping(path={"/tasks/preferrences"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes={"application/json"}, produces={"application/json"})
  public ResponseEntity<?> deletePreferredTask(@RequestBody Task task, Principal principal)
  {
    User user = null;
    try
    {
      user = this.userService.getUserByUsername(principal.getName());
      if (user != null) {
        this.taskService.deletePreferredTask(task, user.getId());
      }
    }
    catch (EmptyResultDataAccessException localEmptyResultDataAccessException) {}
    return ResponseEntity.ok(task);
  }
}
