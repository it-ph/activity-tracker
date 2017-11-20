package com.personiv.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.personiv.model.ErrorResponse;
import com.personiv.model.Group;
import com.personiv.model.GroupEmployeeUser;
import com.personiv.model.GroupTask;
import com.personiv.model.User;
import com.personiv.service.GroupService;
import com.personiv.service.UserService;
import com.personiv.utils.JwtTokenUtil;

@RestController
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path= {"/groups","/groups/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Group> getGroups(){
		return groupService.getGroups();
	}
	
	
	
	@RequestMapping(path= {"/groups/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Group getGroupById(@PathVariable("id") Long id){
		
		try {
			return groupService.getGroupById(id);
			
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@RequestMapping(path= "/groups", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<?> addGroups(@RequestBody Group group){

		try {
			groupService.addGroup(group);
			return ResponseEntity.ok(group);
		}
		catch(DataIntegrityViolationException  ex){
			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry",group));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(group);
		}
	}
	@RequestMapping(path= "/groups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<?> editGroup(@RequestBody Group group){
		
		try {
			groupService.editGroup(group);
			return ResponseEntity.ok(group);
		}
		catch(DataIntegrityViolationException  ex){
			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry",group));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(group);
		}
	}
	@RequestMapping(path= "/group-task/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public List<GroupTask> groupTask(@PathVariable("id") Long id){
		return groupService.getGroupTask(id);
	}
	@RequestMapping(path= "/groups/addMember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<?> addGroupMember(@RequestBody GroupEmployeeUser groupEmpUser) {
		
		try{

			groupService.addGroupMember(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());	
			return ResponseEntity.ok(groupEmpUser);
		}catch(DataIntegrityViolationException  ex){
			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry",groupEmpUser));
		}
		
	}

	@RequestMapping(path= "/groups/addAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<?> addGroupAddAdmin(@RequestBody GroupEmployeeUser groupEmpUser) {
		try {

			groupService.addGroupAdmin(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
			return ResponseEntity.ok(groupEmpUser);
			
		}catch(DataIntegrityViolationException  ex){
			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate entry",groupEmpUser));
		}
	}
	
	@RequestMapping(path= "/groups/removeAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<?> removeGroupAddAdmin(@RequestBody GroupEmployeeUser groupEmpUser) {
		groupService.removeGroupAdmin(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
		return ResponseEntity.ok(groupEmpUser);
	}
	
	@RequestMapping(path= "/groups/removeMember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<?> removeGroupAddMember(@RequestBody GroupEmployeeUser groupEmpUser) {
		groupService.removeGroupMember(groupEmpUser.getGroup().getId(), groupEmpUser.getEmployeeUser().getUser().getId());
		return ResponseEntity.ok(groupEmpUser);
	}
	@RequestMapping(path= "/group-task", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public List<GroupTask> getMyTasks(Principal principal){
		
		User user = null;
		List<GroupTask> groupTask = null;
		try {
			user = userService.getUserByUsername(principal.getName());
			groupTask = groupService.getGroupTask(user.getId());
			
		}catch(EmptyResultDataAccessException e){
			
		}
//		if(requestHeader.startsWith("Bearer ")) {
//			
//			String authToken = requestHeader.substring(7);
//			String username = jwtTokenUtil.getUsernameFromToken(authToken);	
//			
//			try {
//				System.out.println("USERNAME: "+username);
//				user = userService.getUserByUsername(username);		
//				
//				System.out.println("USER: "+user);	
//				
//				groupTask = groupService.getGroupTask(user.getId());
//
//				
//			}catch(EmptyResultDataAccessException e){
//			
//			}		
//			
//		}
		return groupTask;
	}
}
