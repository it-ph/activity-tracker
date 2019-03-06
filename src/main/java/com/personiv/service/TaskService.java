package com.personiv.service;

import com.personiv.dao.TaskDao;
import com.personiv.model.Task;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService
{
  @Autowired
  private TaskDao taskDao;
  
  public List<Task> getTasks()
  {
    return this.taskDao.getTasks();
  }
  
  public Task getTask(Long id)
  {
    return this.taskDao.getTask(id);
  }
  
  public void addTask(Task task)
  {
    this.taskDao.addTask(task);
  }
  
  public void updateTask(Task task)
  {
    this.taskDao.updateTask(task);
  }
  
  public List<Task> getPreferredTasks(Long id)
  {
    return this.taskDao.getPreferredTasks(id);
  }
  
  public void addPreferredTask(Task task, Long userId)
  {
    this.taskDao.addPreferredTask(task, userId);
  }
  
  public void deletePreferredTask(Task task, Long id)
  {
    this.taskDao.deleteTaskPref(task, id);
  }
}
