package com.personiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.personiv.model.ErrorResponse;
import com.personiv.model.Period;
import com.personiv.model.Task;
import com.personiv.service.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	

	
	@RequestMapping(path= "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Task> getTasks(){
		return taskService.getTasks();
	}
	
	@RequestMapping(path= "/tasks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task getTask(@PathVariable("id") Long id){
		return taskService.getTask(id);
	}
	
	@RequestMapping(path= "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTask(@RequestBody Task task){
		try {
			taskService.addTask(task);
		}catch(DataIntegrityViolationException e) {
			ErrorResponse err = new ErrorResponse("Duplicate Entry",task);
			return ResponseEntity.status(422).body(err);
		}
		return ResponseEntity.ok(task);
	}
	


	@RequestMapping(path= "/tasks", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTask(@RequestBody Task task){
		
		try {
			taskService.updateTask(task);
			
		}catch(DataIntegrityViolationException e) {
			ErrorResponse err = new ErrorResponse("Duplicate entry",task);
			return ResponseEntity.status(422).body(err);
		}
		
		return ResponseEntity.ok(task);
	}
	

}
