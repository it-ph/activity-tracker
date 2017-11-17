package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.TaskDao;
import com.personiv.model.Task;

@Service
public class TaskService {
	
	@Autowired
	private TaskDao taskDao;

	public List<Task> getTasks() {
		return taskDao.getTasks();
	}

	public Task getTask(Long id) {
		return taskDao.getTask(id);
	}

	public void addTask(Task task) {
		taskDao.addTask(task);
	}
	public void updateTask(Task task){
		taskDao.updateTask(task);
	}
}
