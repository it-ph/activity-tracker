package com.personiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.personiv.model.ErrorResponse;
import com.personiv.model.Task;
import com.personiv.model.User;
import com.personiv.model.UserTask;
import com.personiv.service.UserService;
import com.personiv.service.UserTaskService;
import com.personiv.utils.JwtTokenUtil;

@RestController
public class UserTaskController {
	
	@Autowired
	private UserTaskService empTaskService;
	
	@Autowired
	private UserService userService;
	
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@RequestMapping(path= {"/employee-tasks","/employee-tasks/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserTask> getUserTasks(){
		return empTaskService.getUserTasks();
	}
	
	@RequestMapping(path= {"/employee-tasks/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserTask getUserTask(@PathVariable("id") Long id){
		try {

			return empTaskService.getUserTask(id);
			
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@RequestMapping(path= {"/employee-tasks/myTasks"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMyTasks(@RequestHeader(value="Authorization")String requestHeader){
	User user = null;
		
		if(requestHeader.startsWith("Bearer ")) {
			
			String authToken = requestHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(authToken);	
			
			try {
				System.out.println("USERNAME: "+username);
				user = userService.getUserByUsername(username);		
				
				System.out.println("USER: "+user);	
				

				return ResponseEntity.ok(empTaskService.getUserTaskByUserId(user.getId()));
				
			}catch(EmptyResultDataAccessException e){
				return null;
			}		
			
		}else {
			return ResponseEntity.status(402).body(new ErrorResponse("Authorization required",null));
		}
	}
	
	@RequestMapping(path= {"/employee-tasks/addTask"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTask(@RequestHeader(value="Authorization")String requestHeader,@RequestBody Task task){
		
		User user = null;
		
		if(requestHeader.startsWith("Bearer ")) {
			
			String authToken = requestHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(authToken);	
			
			try {
				System.out.println("USERNAME: "+username);
				user = userService.getUserByUsername(username);		
				
				System.out.println("USER: "+user);	
				
				empTaskService.addUserTask(user.getId(), task.getId());
				
			}catch(EmptyResultDataAccessException e){
				return ResponseEntity.status(422).body("No user found");
			}
			
			
		}else {
			return ResponseEntity.status(401).body("Not authorized");
		}
		return ResponseEntity.ok().body(null);
	}
	
	
	@RequestMapping(path= {"/employee-tasks/endTask"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> endUserTask(@RequestBody UserTask empTask){
		empTaskService.endUserTask(empTask);
		return ResponseEntity.ok(empTask);
	}
	
}
