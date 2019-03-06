package com.personiv.service;

import com.personiv.dao.UserTaskDao;
import com.personiv.model.EmployeeTask;
import com.personiv.model.Period;
import com.personiv.model.TaskHistoryParam;
import com.personiv.model.UserTask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTaskService
{
  @Autowired
  private UserTaskDao empTaskDao;
  
  public List<UserTask> getUserTasks()
  {
    return this.empTaskDao.getUserTasks();
  }
  
  public UserTask getUserTask(Long id)
  {
    return this.empTaskDao.getUserTask(id);
  }
  
  public List<EmployeeTask> getUserTaskByUserId(Long id)
  {
    return this.empTaskDao.getUserTaskByUserId(id);
  }
  
  public void addUserTask(Long userId, Long taskId)
  {
    this.empTaskDao.addUserTask(userId, taskId);
  }
  
  public void endUserTask(UserTask userTask)
  {
    this.empTaskDao.endUserTask(userTask);
  }
  
  public List<EmployeeTask> getTaskHistory(Period period)
  {
    return this.empTaskDao.getTaskHistory(period);
  }
  
  public List<EmployeeTask> getUserTaskHistory(TaskHistoryParam param)
  {
    return this.empTaskDao.getUserTaskHistory(param);
  }
  
  public EmployeeTask getEmployeeTask(String empNumber)
  {
    return this.empTaskDao.getEmployeeTask(empNumber);
  }
  
  public void addRemarks(EmployeeTask task)
  {
    this.empTaskDao.addRemarks(task);
  }
}
