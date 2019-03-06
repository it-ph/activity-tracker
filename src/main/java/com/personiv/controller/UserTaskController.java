package com.personiv.controller;

import com.personiv.model.EmployeeTask;
import com.personiv.model.ErrorResponse;
import com.personiv.model.Period;
import com.personiv.model.Report;
import com.personiv.model.ReportFile;
import com.personiv.model.Task;
import com.personiv.model.TaskHistoryParam;
import com.personiv.model.User;
import com.personiv.model.UserTask;
import com.personiv.service.UserService;
import com.personiv.service.UserTaskService;
import com.personiv.utils.ExcelBuilder;
import com.personiv.utils.JwtTokenUtil;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTaskController
{
  @Autowired
  private UserTaskService empTaskService;
  @Autowired
  private UserService userService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  
  @RequestMapping(path={"/employee-tasks", "/employee-tasks/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public List<UserTask> getUserTasks()
  {
    return this.empTaskService.getUserTasks();
  }
  
  @RequestMapping(path={"/employee-tasks/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public UserTask getUserTask(@PathVariable("id") Long id)
  {
    try
    {
      return this.empTaskService.getUserTask(id);
    }
    catch (EmptyResultDataAccessException e) {}
    return null;
  }
  
  @RequestMapping(path={"/employee-tasks/employee/{empNumber}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  public EmployeeTask getEmployeeTask(@PathVariable("empNumber") String empNumber)
  {
    try
    {
      return this.empTaskService.getEmployeeTask(empNumber);
    }
    catch (EmptyResultDataAccessException e) {}
    return null;
  }
  
  @RequestMapping(path={"/employee-tasks/history"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> getTaskHistory(@RequestBody Period period)
  {
    List<EmployeeTask> userTasks = this.empTaskService.getTaskHistory(period);
    return ResponseEntity.ok(userTasks);
  }
  
  @RequestMapping(path={"/employee-tasks/addRemarks"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> getTaskHistory(@RequestBody EmployeeTask task)
  {
    this.empTaskService.addRemarks(task);
    return ResponseEntity.ok(task);
  }
  
  @RequestMapping(path={"/employee-tasks/user-history"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
  public ResponseEntity<?> getUserTaskHistory(@RequestBody TaskHistoryParam param)
  {
    List<EmployeeTask> userTasks = this.empTaskService.getUserTaskHistory(param);
    
    return ResponseEntity.ok(userTasks);
  }
  
  @RequestMapping(path={"/employee-tasks/myTasks"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> getMyTasks(@RequestHeader("Authorization") String requestHeader)
  {
    User user = null;
    if (requestHeader.startsWith("Bearer "))
    {
      String authToken = requestHeader.substring(7);
      String username = this.jwtTokenUtil.getUsernameFromToken(authToken);
      try
      {
        user = this.userService.getUserByUsername(username);
        
        return ResponseEntity.ok(this.empTaskService.getUserTaskByUserId(user.getId()));
      }
      catch (EmptyResultDataAccessException e)
      {
        return null;
      }
    }
    return ResponseEntity.status(402).body(new ErrorResponse("Authorization required", null));
  }
  
  @RequestMapping(path={"/employee-tasks/addTask"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> addTask(@RequestHeader("Authorization") String requestHeader, @RequestBody Task task)
  {
    User user = null;
    if (requestHeader.startsWith("Bearer "))
    {
      String authToken = requestHeader.substring(7);
      String username = this.jwtTokenUtil.getUsernameFromToken(authToken);
      try
      {
        user = this.userService.getUserByUsername(username);
        
        this.empTaskService.addUserTask(user.getId(), task.getId());
      }
      catch (EmptyResultDataAccessException e)
      {
        return ResponseEntity.status(422).body("No user found");
      }
    }
    else
    {
      return ResponseEntity.status(401).body("Not authorized");
    }
    return ResponseEntity.ok().body(null);
  }
  
  @RequestMapping(path={"/employee-tasks/endTask"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
  public ResponseEntity<?> endUserTask(@RequestBody UserTask empTask)
  {
    this.empTaskService.endUserTask(empTask);
    return ResponseEntity.ok(empTask);
  }
  
  @RequestMapping(path={"/employee-tasks/report/download"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, consumes={"application/json"}, produces={"application/json"})
  public ResponseEntity<?> dowloadReport(@RequestBody Report report, HttpServletResponse response)
  {
    ExcelBuilder builder = new ExcelBuilder(report);
    
    byte[] reportBytes = builder.getBytes();
    
    ReportFile rf = new ReportFile();
    rf.setContents(reportBytes);
    return ResponseEntity.ok(rf);
  }
}
