package com.personiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.personiv.model.EmployeeUser;
import com.personiv.model.ErrorResponse;
import com.personiv.model.Group;
import com.personiv.model.Role;
import com.personiv.model.User;
import com.personiv.service.EmployeeUserService;

@RestController
public class EmployeeUserController {

	@Autowired
	private EmployeeUserService empUserService;
	
	@RequestMapping(path= {"/accounts","/accounts/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeUser> getEmployeeUsers(){
		return empUserService.getEmployeeUsers();
	}
	
	@RequestMapping(path="/group-members", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeUser> getGroupMemberSelection(@RequestBody Group group){
		return empUserService.getGroupMemberSelection(group.getId());
	}
	@RequestMapping(path="/group-admins", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeUser> getGroupAdminSelection(@RequestBody Group group){
		return empUserService.getGroupAdminSelection(group.getId());
	}
	
	@RequestMapping(path= "/accounts/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Role> getUserRoles(){
		return empUserService.getUserRoles();
	}

	@RequestMapping(path = "/accounts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addEmployeeUser(@RequestBody EmployeeUser empUser){
		try {
			empUserService.addEmployeeUser(empUser);

			return ResponseEntity.ok(empUser);
		}catch(DataIntegrityViolationException e) {

			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate Entry", empUser));
		}
	}
	
	@RequestMapping(path = "/accounts", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployeeUser(@RequestBody EmployeeUser empUser){
		try {

			empUserService.updateEmployeeUser(empUser);
			return ResponseEntity.ok(empUser);
		}catch(DataIntegrityViolationException e) {
			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate Entry",empUser));
		}
	}
	
	@RequestMapping(path = "/accounts/updateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody User user){
		try {

			empUserService.updateUser(user);

			return ResponseEntity.ok(user);
		}catch(DataIntegrityViolationException e) {
			return ResponseEntity.status(422).body(new ErrorResponse("Duplicate Entry",user));
		}
	}
	
	@RequestMapping(path= "/accounts/enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enableUser(@RequestBody EmployeeUser user){
		empUserService.enableUser(user);
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(path= "/accounts/resetpassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetUser(@RequestBody EmployeeUser user){
		empUserService.resetUser(user);
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(path= "/accounts/disable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> disablUser(@RequestBody EmployeeUser user){
		empUserService.disableUser(user);
		return ResponseEntity.ok(user);
	}
}
