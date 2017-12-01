package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.UserTaskDao;
import com.personiv.model.EmployeeTask;
import com.personiv.model.Period;
import com.personiv.model.UserTask;

@Service
public class UserTaskService {
	
	@Autowired
	private UserTaskDao empTaskDao;
	
	public List<UserTask> getUserTasks(){
		return empTaskDao.getUserTasks();
	}
	public UserTask getUserTask(Long id) {
		return empTaskDao.getUserTask(id);
	}
	public List<UserTask> getUserTaskByUserId(Long id){
		return empTaskDao.getUserTaskByUserId(id);
	}

	public void addUserTask(Long userId,Long taskId) {
		empTaskDao.addUserTask(userId, taskId);
	}
	public void endUserTask(UserTask userTask) {
		empTaskDao.endUserTask(userTask);
	}

	public List<EmployeeTask> getTaskHistory(Period period) {
		return empTaskDao.getTaskHistory(period);
	}
}
